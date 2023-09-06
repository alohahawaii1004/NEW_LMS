/*
 * @(#)CmmRequestWrapper.java     2020-10-30(030) 오전 8:09
 *
 * Copyright 2020 JAYU.space
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vertexid.viself.base;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.vertexid.viself.security.RSAAttributeKey;
import com.vertexid.viself.security.RSACryptographicSvc;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <b>Description</b>
 * <pre>
 *     공통 Request Wrapper
 *
 *     [참고]
 *         "Spring에서 Request를 우아하게 로깅하기"
 *         (https://taetaetae.github.io/2019/06/30/controller-common-logging/)
 * </pre>
 *
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class CmmRequestWrapper extends HttpServletRequestWrapper {

    /**
     * logger
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private PrivateKey privateKey = null;
    private boolean encFlag = false;
    private Set<String> encParamSet;

    private final Charset encoding;
    private byte[] rawData;
    private Map<String, String[]> params = new HashMap<>();

    private String tmpPath;

    SystemPropertiesVO systemPropertiesVO = new SystemPropertiesVO();

    public CmmRequestWrapper(HttpServletRequest request) {
        super(request);

        this.tmpPath = request.getServletPath();

        try{
            // 원래의 파라미터를 저장
            this.params.putAll(request.getParameterMap());
            String charEncoding = request.getCharacterEncoding(); // 인코딩 설정
            this.encoding = StringUtils.isBlank(charEncoding) ? StandardCharsets.UTF_8 : Charset.forName(charEncoding);

            InputStream is = request.getInputStream();
            // InputStream 을 별도로 저장한 다음 getReader() 에서 새 스트림으로 생성
            this.rawData = IOUtils.toByteArray(is);

            // body 파싱
            String collect = this.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

            // body 가 없을경우 로깅 제외
            if (StringUtils.isEmpty(collect)) {
                return;
            }

            // 파일 업로드시 로깅제외
            if (request.getContentType() != null &&  (request.getContentType().contains("multipart/form-data")
                    || request.getContentType().contains("multipart/mixed"))) {
                return;
            }

//            JSONParser jsonParser = new JSONParser();

            ObjectMapper mapper = new ObjectMapper();
            Object parse = mapper.readValue(collect, Object.class);

            if (parse instanceof JSONArray) {
                JSONArray jsonArray = mapper.readValue(collect, JSONArray.class);
                setParameter("requestBody", jsonArray.toString());
            } else {
                JSONObject jsonObject = mapper.readValue(collect, JSONObject.class);

                if(null != jsonObject){
                    Iterator iterator = jsonObject.keySet().iterator();
                    while (iterator.hasNext()) {
                        String key = (String)iterator.next();
                        setParameter(key, jsonObject.get(key).toString().replace("\"", "\\\""));
                    }
                }else{
                    log.warn("...........jsonObject is null(만약 AJAX 호출시 parameter 가 null 인지 확인 바람)");
                }
            }
        }catch (Exception e){
            log.error(e+":"+this.tmpPath);
            throw new RuntimeException(e.getMessage());
        }

        // RSA 처리
        decodeRSA(request);
    }

    public void setParameter(String name, String value) {
        String[] param = {value};
        setParameter(name, param);
    }

    public void setParameter(String name, String[] values) {
        params.put(name, values);
    }

    /**
     * request parameter 에 대한 RSA 복호화
     * !! 주의 !! stream 의 경우 Controller 에서 별도로 복호화
     * @param request
     */
    private void decodeRSA(HttpServletRequest request) {

        // 1. get privateKey
        HttpSession session = request.getSession();
        if (null != session.getAttribute(RSAAttributeKey.PRIVATE_KEY)) {
            privateKey = (PrivateKey) session
                    .getAttribute(RSAAttributeKey.PRIVATE_KEY);
        }

        String encParameters =
                request.getParameter(RSAAttributeKey.RSA_TARGET_PARAMETERS);
//        String encParameters = getParameter(RSAAttributeKey.RSA_TARGET_PARAMETERS);
        encFlag = StringUtils.isNotEmpty(encParameters);

        if (encFlag) {
            encParamSet = Sets.newHashSet(encParameters.split(","));
            if(systemPropertiesVO.isLocal()){
                log.debug("encParamSet..........................." + encParamSet);
                log.debug("privateKey..........................." + privateKey);
            }
        }
    }

    @Override
    public String getParameter(String name) {
        String[] paramArray = getParameterValues(name);
        if (paramArray != null && paramArray.length > 0) {

            String param = paramArray[0];

            // 암호화되어있고 암호화된 파라메터일경우 복호화해서 처리
            // [주의] RequestBody 에서는 Stream으로 데이터가 넘어오기때문에
            // Ctrl에서 별도로 복호화 처리해야함
            if (encFlag && encParamSet.contains(name)) {
                RSACryptographicSvc rsaCryptographicSvc = new RSACryptographicSvc();
                if(systemPropertiesVO.isLocal()){
                    log.debug("name..........................." + name);
                    log.debug("param..........................." + param);
                    log.debug("decparam..........................." +
                            rsaCryptographicSvc.decrypt(privateKey, param));
                }
                return rsaCryptographicSvc.decrypt(privateKey, param);
            }

            return param;
        } else {
            return null;
        }
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(params);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(params.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] result = null;
        String[] dummyParamValue = params.get(name);

        if (dummyParamValue != null) {
            result = new String[dummyParamValue.length];
            System.arraycopy(dummyParamValue, 0, result, 0, dummyParamValue.length);
        }
        return result;
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.rawData);

        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            public boolean isFinished() {
                return false;
            }

            public boolean isReady() {
                return false;
            }
            
//            @Override
            public void setReadListener(ReadListener arg0) {
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), this.encoding));
    }
}
