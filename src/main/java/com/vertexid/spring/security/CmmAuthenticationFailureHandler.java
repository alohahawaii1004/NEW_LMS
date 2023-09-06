/*
 * @(#)CmmAuthenticationFailureHandler.java     2021-01-13(013) 오전 10:10
 *
 * Copyright 2021 JAYU.space
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
package com.vertexid.spring.security;

import com.vertexid.spring.utils.CmmProperties;
import com.vertexid.viself.base.BaseHandler;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <b>Description</b>
 * <pre>
 *     인증실패(로그인 실패) 핸들러
 *     [참고]
 *          - https://zgundam.tistory.com/53?category=430446
 *          - https://to-dy.tistory.com/92?category=720806
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class CmmAuthenticationFailureHandler extends BaseHandler implements
        AuthenticationFailureHandler {

    /**
     * 로그인 아이디 파라메터 이름
     */
    private String loginIdName;
    /**
     * 로그인 암호 파라메터 이름
     */
    private String loginPasswordName;
    /**
     * 로그인 성공시 리다이렉트 주소가 저장된 파라메터 이름
     */
    private String loginDirectName;
    /**
     * 오류 메시지 파라메터 이름
     */
    private String exceptionMsgName;
    /**
     * 기본 실패 이동 URL
     */
    private String defaultFailureUrl;
    /**
     * 오류 메시지
     */
    private String failMsg;

    public CmmAuthenticationFailureHandler() {
        loginIdName = "encId";
        loginPasswordName = "encPw";
        loginDirectName = "loginRedirect";
        exceptionMsgName = "securityExceptionMsg";
        defaultFailureUrl = "/login";
        failMsg = "msg";
    }

    public String getLoginIdName() {
        return loginIdName;
    }

    public void setLoginIdName(String loginIdName) {
        this.loginIdName = loginIdName;
    }

    public String getLoginPasswordName() {
        return loginPasswordName;
    }

    public void setLoginPasswordName(String loginPasswordName) {
        this.loginPasswordName = loginPasswordName;
    }

    public String getLoginDirectName() {
        return loginDirectName;
    }

    public void setLoginDirectName(String loginDirectName) {
        this.loginDirectName = loginDirectName;
    }

    public String getExceptionMsgName() {
        return exceptionMsgName;
    }

    public void setExceptionMsgName(String exceptionMsgName) {
        this.exceptionMsgName = exceptionMsgName;
    }

    public String getDefaultFailureUrl() {
        return defaultFailureUrl;
    }

    public void setDefaultFailureUrl(String defaultFailureUrl) {
        this.defaultFailureUrl = defaultFailureUrl;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        log.error("AuthenticationFailure.....................");

        // 계정 잠금 기능 TODO

        // error log
        if(CmmProperties.isLocal()){
            writeLog(request, exception);
        }

        // page redirect
        request.getRequestDispatcher(defaultFailureUrl)
                .forward(request, response);
    }

    private void writeLog(HttpServletRequest request,
            AuthenticationException exception) {
        String loginId = request.getParameter(loginIdName);
        String loginPasswd = request.getParameter(loginPasswordName);
        String loginRedirect = request.getParameter(loginDirectName);

        StringBuffer sbMsg = new StringBuffer();
        sbMsg.append("\n    ====================");
        sbMsg.append("\n    Login Fail");
        sbMsg.append("\n    --------------------");
        sbMsg.append("\n    ").append("* error massage:").append(exception.getMessage());
        sbMsg.append("\n    ").append("* login id:").append(loginId);
        sbMsg.append("\n    ").append("* pqssword:").append(loginPasswd);
        sbMsg.append("\n    ").append("* redirect:").append(loginRedirect);
        sbMsg.append("\n    ====================");

        log.error(sbMsg.toString());

    }


}
