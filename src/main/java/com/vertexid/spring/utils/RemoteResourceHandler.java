/*
 * @(#)RemoteResourceHandler.java     2022-05-27(027) 오전 9:31:17
 *
 * Copyright 2022 JAYU.space
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
package com.vertexid.spring.utils;

import com.vertexid.viself.base.BaseSvc;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.*;

/**
 * <b>Description</b>
 * <pre>
 *    Spring Component: 원격 리소스 핸들러
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Component
public class RemoteResourceHandler extends BaseSvc {

    private static Map<Integer, HttpClient> httpClients = new HashMap<>();

    //private int MAX_CLIENTS = 10;        // 이 개수만큼 client를 생성하여 돌려가면서 사용. TODO

    private int nowClientId = -1;

    private String encoding;

    private boolean useUrlEncode = true;

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public boolean isUseUrlEncode() {
        return useUrlEncode;
    }

    public void setUseUrlEncode(boolean useUrlEncode) {
        this.useUrlEncode = useUrlEncode;
    }

    private Map<String, String> headerData = new HashMap<>();

    private Map<String, String> commonParams = new HashMap<>();

    private HttpResponse response = null;

    public void addHeaderData(String key, String val) {
        this.headerData.put(key, val);
    }

    public StringBuffer getRemoteSource(String strUrl) throws IOException {
        return getRemoteSource(strUrl, "GET", new HashMap<>(), new ArrayList<>());
    }

    private StringBuffer getRemoteSource(String strUrl, String method,
            HashMap<String, String[]> params, ArrayList<String> sendList)
            throws IOException {
        StringBuffer rtn = new StringBuffer();

        response = sendData(strUrl, method, params, sendList);

        if (response != null) {
            BufferedReader br = null;
            try {
                InputStream contents = response.getEntity().getContent();
                br = new BufferedReader(
                        new InputStreamReader(contents, encoding));

                String line;
                while ((line = br.readLine()) != null) {
                    rtn.append(line);
                    //rtn.append("\n");
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (Exception ex2) {
                    log.error(ex2.getMessage(), ex2);
                }
            }
        }

        return rtn;
    }

    private HttpResponse sendData(String url, String method,
            Map<String, String[]> params, List<String> sendList)
            throws IOException {
        Iterator<String> itKeys;

        method = StringUtils.defaultString(method);

        // Http Client 생성
        HttpClient client = getClient();
        response = null;

        /*
         * 공통 파라미터 설정
         */
        List<NameValuePair> urlParameters = new ArrayList<>();

        itKeys = commonParams.keySet().iterator();
        while (itKeys.hasNext()) {
            String key = itKeys.next();
            String val = commonParams.get(key);
            urlParameters.add(new BasicNameValuePair(key, val));
            //log.debug("공통 파라미터 설정 : " + key + " = " + val);
        }

        /*
         * 추가 파라미터 설정
         */
        if (params != null) {
            itKeys = params.keySet().iterator();
            while (itKeys.hasNext()) {
                String key = itKeys.next();
                if (sendList == null || sendList.contains(key)) {
                    String[] vals = params.get(key);
                    for (String s : vals) {
                        String val = s;
                        /*if (!"UTF-8".equals(encoding.toUpperCase())) {
                            // 대상 인코딩으로 변환 : 필요없다.
                            val = new String(val.getBytes("UTF-8"), encoding);
                        }*/
                        if (useUrlEncode) {
                            val = URLDecoder.decode(val, "UTF-8");
                        }
                        urlParameters.add(new BasicNameValuePair(key, val));
                    }
                }
            }
        }

        // 파라미터 로그 출력
        for (NameValuePair urlParameter : urlParameters) {
            BasicNameValuePair tmp = (BasicNameValuePair) urlParameter;
            String key = tmp.getName();
            String val = tmp.getValue();
            log.debug("전송 파라미터 : " + key + " = " + val);
        }

        /*
          POST 전송
         */
        if ("POST".equalsIgnoreCase(method)) {
            HttpPost post = new HttpPost(url);

            /*
              헤더 데이터 설정
             */
            itKeys = headerData.keySet().iterator();
            while (itKeys.hasNext()) {
                String key = itKeys.next();
                String val = headerData.get(key);
                log.debug("헤더 설정 : " + key + " = " + val);
                post.setHeader(key, val);
            }

            UrlEncodedFormEntity entities =
                    new UrlEncodedFormEntity(urlParameters, encoding);

            //entities.setContentType("EUC-KR");
            entities.setContentEncoding(encoding);

            post.setEntity(entities);

            response = client.execute(post);

            //if (!post.isAborted()) post.abort();    // TODO
            /*
              GET 전송
             */
        } else {
            String query = URLEncodedUtils.format(urlParameters, encoding);
            String uri = url;
            if (StringUtils.isNotEmpty(query)) {
                uri = uri + "?" + query;
            }

            HttpGet getRequest = new HttpGet(uri);

            /*
              헤더 데이터 설정
             */
            itKeys = headerData.keySet().iterator();
            while (itKeys.hasNext()) {
                String key = itKeys.next();
                String val = headerData.get(key);
                getRequest.setHeader(key, val);
            }

            response = client.execute(getRequest);
            //if (!getRequest.isAborted()) getRequest.abort();

        }

        log.debug("\nSending '" + method + "' request to URL : " + url);
        //log.debug("Post parameters : " + post.getEntity());
        log.debug(
                "Response Code : " + response.getStatusLine().getStatusCode());
        log.debug("RESPONSE : " + response.toString());

        return response;
    }

    public void addCommonParams(String key, String val) {
        this.commonParams.put(key, val);
    }


    /**
     * 원격 binary data rendering : 원격지 이미지 같은 것을 랜더링한다.
     * @param url 랜더링할 원격지 URL
     * @param res response
     * @throws IOException IO Exception
     */
    public void renderGetRemoteBin(String url, HttpServletResponse res)
            throws IOException {

        HttpClient httpClient = getClient();
        HttpGet httpGet = new HttpGet(url);

        /*
          헤더 데이터 설정
         */
        for (String key : headerData.keySet()) {
            String val = headerData.get(key);
            httpGet.setHeader(key, val);
        }

        response = httpClient.execute(httpGet);
        renderRemoteBinNew(response, res);

        if (!httpGet.isAborted()) {
            httpGet.abort();
        }
    }

    private void renderRemoteBinNew(HttpResponse response,
            HttpServletResponse res) {
        ServletOutputStream out = null;
        ByteArrayOutputStream byteArrayOutputStream = null;

        byte[] buf = new byte[1024];
        byte[] imgBuf;
        int readByte;
        int length;

        if (response != null) {
            BufferedInputStream bis = null;
            try {
                bis = new BufferedInputStream(
                        response.getEntity().getContent());
                byteArrayOutputStream = new ByteArrayOutputStream();
                out = res.getOutputStream();

                while ((readByte = bis.read(buf)) != -1) {
                    byteArrayOutputStream.write(buf, 0, readByte);
                }// end of while

                imgBuf = byteArrayOutputStream.toByteArray();
                length = imgBuf.length;
                out.write(imgBuf, 0, length);
                out.flush();

            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
                throw new RuntimeException(ex);
            } finally {
                try {
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                    if (bis != null) {
                        bis.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void renderRemoteBin(HttpResponse response, HttpServletResponse res)
            throws IOException {
        ServletOutputStream out = res.getOutputStream();

        if (response != null) {
            //BufferedReader br = null;
            BufferedInputStream bis = null;
            try {
                //br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), encoding));
                bis = new BufferedInputStream(
                        response.getEntity().getContent());

                byte[] bytesIn = new byte[1024];
                int i;
                while ((i = bis.read(bytesIn)) >= 0) {
                    out.write(bytesIn, 0, i);
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            } finally {
                try {
                    if (bis != null) {
                        bis.close();
                    }
                } catch (Exception ex2) {
                    log.error(ex2.getMessage(), ex2);
                }
            }
        }
    }

    private HttpClient getClient() {
        nowClientId = 0;
        if (httpClients.size() <= nowClientId) {

            RequestConfig requestConfig =
                    RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD)
                            .setConnectTimeout(60000).build();

            HttpClient newClient = HttpClientBuilder.create()
                    .setDefaultRequestConfig(requestConfig).build();
            httpClients.put(nowClientId, newClient);
        }

        return httpClients.get(nowClientId);
    }

    public StringBuffer getRemoteSource(String url, String method, HttpServletRequest req, List<String> sendList)
            throws IOException {
        StringBuffer rtn = new StringBuffer();

        /*
         * request에서 parameter 변환
         */
        Map<String, String[]> params = req.getParameterMap();

        response = sendData(url, method, params, sendList);

        if (response != null) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), encoding));

                String line;
                while( (line = br.readLine())!=null ){
                    rtn.append(line);
                    rtn.append("\n");
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (Exception ex2) {
                    log.error(ex2.getMessage(), ex2);
                }
            }
        }

        log.debug(".... RESPONSE STRING:\n"+rtn.toString());

        return rtn;
    }

    public void clearCommonParams() {
        this.commonParams.clear();
    }

    public StringBuffer getRemoteSource(String url, String method, Map<String, String[]> params, List<String> sendList)
            throws IOException {
        StringBuffer rtn = new StringBuffer();

        response = sendData(url, method, params, sendList);

        if (response != null) {
            BufferedReader br = null;
            try {
                InputStream contents = response.getEntity().getContent();
                br = new BufferedReader(new InputStreamReader(contents, encoding));

                String line;
                while( (line = br.readLine())!=null ){
                    rtn.append(line);
                    //rtn.append("\n");
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (Exception ex2) {
                    log.error(ex2.getMessage(), ex2);
                }
            }
        }

        return rtn;
    }
}
