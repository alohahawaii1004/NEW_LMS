/*
 * @(#)SupportSSOAuthenticationManager.java     2023-04-01 오전 7:37
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

import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.code.CodeSvc;
import com.vertexid.viself.hr.SimpleUserDTO;
import com.vertexid.viself.hr.SysLoginVO;
import com.vertexid.viself.security.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Component
public class SupportSSOAuthenticationManager extends BaseSvc
        implements AuthenticationManager {

    public static final String AUTHENGICATION_MANAGER_TYPE = "SSO";

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

        String loginId = String.valueOf(params.get("id"));
        String encCert = String.valueOf(params.get("cert"));
        String name = String.valueOf(params.get("name"));
        String email = String.valueOf(params.get("email"));

        UserDetails userDtl = userDetailsService.loadUserByUsername(loginId);
        SysLoginVO simpleUserDTO = null;
        if (userDtl instanceof SysLoginVO) {
            simpleUserDTO = (SysLoginVO) userDtl;
            name = deCode(name);
            simpleUserDTO.setName(name);
            simpleUserDTO.setNmKo(name);
            simpleUserDTO.setEmail(email);
        }

        if (null != userDtl && validation(loginId, encCert, simpleUserDTO, req)) {
            // login success
            log.debug("\n..........................login success");
            loginSuccessHandler.successProc(req, res, simpleUserDTO);
        } else {
            // login fail
            log.debug("\n.............................login fail 사용자 없음");
            failMsg = "요청하신 사용자를 찾을 수 없습니다.";
            loginFailHandler.failureProc(req, res, new Exception(failMsg));
        }

    }

    private boolean validation(String loginId, String encCert,
            UserDetails userDtl, HttpServletRequest req) {

        SysLoginVO simpleUserDTO;
        if (userDtl instanceof SysLoginVO) {
            simpleUserDTO = (SysLoginVO)userDtl;

            if(StringUtils.isEmpty(simpleUserDTO.getCert())){
                log.warn("인증서가 없는 사용자 입니다................."+loginId);
                return true;
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.matches(simpleUserDTO.getCert(), deCode(encCert));
        }
        return false;
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
