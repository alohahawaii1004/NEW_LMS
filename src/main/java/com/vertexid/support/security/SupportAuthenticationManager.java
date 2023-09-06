/*
 * @(#)SupportAuthenticationManager.java     2023-03-09 009 오후 4:14:35
 *
 * Copyright 2023 JAYU.space
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
package com.vertexid.support.security;

import com.vertexid.spring.utils.BaseProperties;
import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.code.CodeSvc;
import com.vertexid.viself.security.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 *     Support 인증 매니저
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Component
public class SupportAuthenticationManager extends BaseSvc
        implements AuthenticationManager {
    public static final String AUTHENGICATION_MANAGER_TYPE =
            "CustomerAuthenticationManager";

    private final RedirectStrategy redirectStrategy =
            new SimpleRedirectStrategy();

    private String logoutSuccessUrl;

    private String failMsg = "";

    private SimpleSecurityEnvVO simpleSecurityEnvVO;

    public String getLogoutSuccessUrl() {
        return logoutSuccessUrl;
    }

    public void setLogoutSuccessUrl(String logoutSuccessUrl) {
        this.logoutSuccessUrl = logoutSuccessUrl;
    }

    @Resource(name = "simpleUserDetailsSvc")
    UserDetailsService userDetailsService;

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    LoginSuccessHandler loginSuccessHandler;

    @Resource
    LoginFailHandler loginFailHandler;

    @Resource
    CodeSvc codeSvc;

    @Resource
    SimpleSecurityEnvSvc simpleSecurityEnvSvc;

    @Resource
    RSACryptographicSvc rsaCryptographicSvc;


    @Override
    public String getAuthenticationManagerType() {
        return AUTHENGICATION_MANAGER_TYPE;
    }

    @Override
    public void loginProc(HttpServletRequest req, HttpServletResponse res,
            Map<String, Object> params) throws IOException, ServletException {
        String idParam = BaseProperties.get("security.idParamName");
        String pwParam = BaseProperties.get("security.pwParamName");

        if (null == params.get(idParam) || null == params.get(pwParam)) {
            // login fail
            log.warn("...............id or password is null");
            loginFailHandler.failureProc(req, res,
                    new Exception("id or password is null"));
            return;
        }

        // 복호화
        decryptParams(req, params);

        String loginId = String.valueOf(params.get(idParam));
        String loginPw = String.valueOf(params.get(pwParam));

        UserDetails userDtl = userDetailsService.loadUserByUsername(loginId);
        if (null != userDtl && validation(loginId, loginPw, userDtl, req)) {
            // login success
            log.debug("\n..........................login success");
            loginSuccessHandler.successProc(req, res, userDtl);
        } else {
            // login fail
            log.debug("\n.............................login fail 사용자 없음");
            failMsg = "요청하신 사용자를 찾을 수 없습니다.";
            loginFailHandler.failureProc(req, res, new Exception(failMsg));
        }
    }

    private boolean validation(String loginId, String loginPw,
            UserDetails userDtl, HttpServletRequest req) {
        if (passwordEncoder.matches(loginPw, userDtl.getPassword())) {
            return true;
        }else{
            return false;
            // check license
            // 3. Master PWD
//            ParamMap<String, Object> codeMap = codeSvc.getMasterP();
//            return (codeMap != null && loginPw.equals(codeMap.getString("cdAbb")));
        }
    }

    private void decryptParams(HttpServletRequest req,
            Map<String, Object> params) {

        HttpSession session = req.getSession();
        if (null == session.getAttribute(RSAAttributeKey.PRIVATE_KEY)) {
            throw new RuntimeException("NO PRIVATE_KEY IN SESSION");
        }

        PrivateKey privateKey =
                (PrivateKey) session.getAttribute(RSAAttributeKey.PRIVATE_KEY);

        String idParam = BaseProperties.get("security.idParamName");
        String pwParam = BaseProperties.get("security.pwParamName");
        String encId = String.valueOf(params.get(idParam));
        String encPw = String.valueOf(params.get(pwParam));

        String loginId = rsaCryptographicSvc.decrypt(privateKey, encId);
        String loginPw = rsaCryptographicSvc.decrypt(privateKey, encPw);

        params.put(idParam, loginId);
        params.put(pwParam, loginPw);
    }

    @Override
    public void logoutProc(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        if (StringUtils.isEmpty(logoutSuccessUrl)) {
            if (null == simpleSecurityEnvVO) {
                simpleSecurityEnvVO = simpleSecurityEnvSvc.getEnv();
            }

            logoutSuccessUrl = simpleSecurityEnvVO.getExpiredURI();
        }

        HttpSession session = req.getSession();
        SessionUtils.clearSession(session);
        log.debug("logoutSuccessURL........." + logoutSuccessUrl);
        redirectStrategy.sendRedirect(req, res, logoutSuccessUrl);
    }

    @Override
    public BaseLoginDTO getLoginInfo(HttpServletRequest req) {
        BaseLoginDTO rtnInfo = (BaseLoginDTO) SessionUtils.getLoginVO();
        if (null == rtnInfo) {
            HttpSession session = req.getSession();
            rtnInfo = (BaseLoginDTO) SessionUtils.getLoginVO(session);
        }
        return rtnInfo;
    }
}
