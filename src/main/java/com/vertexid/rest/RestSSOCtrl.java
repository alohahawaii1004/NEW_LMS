/*
 * @(#)RestSSOCtrl.java     2022-04-12(012) 오후 3:15
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
package com.vertexid.rest;

import com.vertexid.commons.crypto.RSACryptographicUtil;
import com.vertexid.spring.utils.BaseProperties;
import com.vertexid.support.security.SupportSSOAuthenticationManager;
import com.vertexid.viself.base.BaseCtrl;
import com.vertexid.viself.base.ModelAttribute;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@RestController
public class RestSSOCtrl extends BaseCtrl {

    @Resource
    SupportSSOAuthenticationManager supportSSOAuthenticationManager;

    @RequestMapping(value = "/rest/sso",
            method = RequestMethod.GET)
    public ResponseEntity<Object> checkSsoLogin(ModelMap model,
            HttpServletRequest req, HttpServletResponse res,
            @RequestParam
            Map<String, Object> params) {

        model.clear();

        log.info("POST params...." + params);
        log.info("POST params...." + BaseProperties.get("security.privateKey"));
        log.info("POST params...." + BaseProperties.get("security.publicKey"));


        try {
            supportSSOAuthenticationManager.loginProc(req, res, params);
        } catch (IOException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        } catch (ServletException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }


        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("MSG", null);
        data.put("CNT", 1);
        data.put("RTN", "S");
        dataList.add(data);

        result.put("data", dataList);

        model.addAttribute(ModelAttribute.RESULT.getAttributeId(), result);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @RequestMapping(value = "/main/ssoLogin2.do",
            method = RequestMethod.GET)
    public ResponseEntity<Object> oldSsoLogin(ModelMap model,
            HttpServletRequest req, HttpServletResponse res,
            @RequestParam
            Map<String, Object> params) {

        model.clear();

        try {
            supportSSOAuthenticationManager.loginProc(req, res, params);
        } catch (IOException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        } catch (ServletException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }


        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("MSG", null);
        data.put("CNT", 1);
        data.put("RTN", "S");
        dataList.add(data);

        result.put("data", dataList);

        model.addAttribute(ModelAttribute.RESULT.getAttributeId(), result);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/sso",
            method = RequestMethod.POST)
    public ResponseEntity<Object> loginSSO(ModelMap model,
            @RequestParam
            Map<String, Object> params) {

        model.clear();

        log.info("POST params...." + params);
        log.info("POST params...." + BaseProperties.get("security.privateKey"));
        log.info("POST params...." + BaseProperties.get("security.publicKey"));

        RSACryptographicUtil util = new RSACryptographicUtil();

        String strPubKey = BaseProperties.get("security.publicKey");
        try {
            boolean vf = util.verifySign(util.pubKeyFromString(strPubKey), String.valueOf(params.get("cert")), String.valueOf(params.get("compky"))+","+String.valueOf(params.get("id")));

            log.info("vf....."+vf);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("MSG", null);
        data.put("CNT", 1);
        data.put("RTN", "S");
        dataList.add(data);

        result.put("data", dataList);

        model.addAttribute(ModelAttribute.RESULT.getAttributeId(), result);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/sso",
            method = RequestMethod.PUT)
    public ResponseEntity<Object> updateSsoLogin(ModelMap model,
            @RequestParam
            Map<String, Object> params) {
        model.clear();

        log.info("params...." + params);

        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("update-count", 1);
        dataList.add(data);

        result.put("data", dataList);

        model.addAttribute(ModelAttribute.RESULT.getAttributeId(), result);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /**
     * deCode
     *
     * @param param
     * @return
     */
    public String deCode(String param) {
        StringBuffer sb = new StringBuffer();
        int iPos = 0;
        boolean bFlg = true;

        if (StringUtils.isNotBlank(param)) {
            if (param.length() > 1) {
                while (bFlg) {
                    String sLength = param.substring(iPos, ++iPos);
                    int iLength = 0;

                    try {
                        iLength = Integer.parseInt(sLength);
                    } catch (Exception e) {
                        iLength = 0;
                    }

                    String sCode = "";

                    if ((iPos + iLength) > param.length()) {
                        sCode = param.substring(iPos);
                    } else {
                        sCode = param.substring(iPos, (iPos + iLength));
                    }

                    iPos += iLength;

                    sb.append(((char) Integer.parseInt(sCode)));

                    if (iPos >= param.length()) {
                        bFlg = false;
                    }
                }
            }
        } else {
            param = "";
        }

        return sb.toString();
    }
}
