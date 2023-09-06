/*
 * @(#)SimpleLoginSuccessHandler.java     2021-02-02(002) 오후 3:35
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
package com.vertexid.viself.login;

import com.vertexid.spring.session.SessionListener;
import com.vertexid.spring.utils.BaseProperties;
import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.hr.SysLoginVO;
import com.vertexid.viself.security.LoginSuccessHandler;
import com.vertexid.viself.security.SimpleRedirectStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.vertexid.spring.session.SessionListener.SIMPLE_PRINCIPAL;

/**
 * <b>Description</b>
 * <pre>
 *     로그인 성공 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class SimpleLoginSuccessHandler extends BaseSvc
        implements LoginSuccessHandler, AuthenticationSuccessHandler {

    private static final String REFERER = "REFERER";
    private static final int USE_TARGET_URL = 1;
    private static final int USE_SESSION_URL = 2;
    private static final int USE_REFERER_URL = 3;
    private static final int USE_DEFAULT_URL = 0;

    private final int maxSession = Integer.parseInt(StringUtils.defaultIfBlank(
            BaseProperties.get("security.maxSession"), "1"));

    private String targetUrlParameter;

    private String defaultUrl;

    private boolean useReferer;

    private boolean useSessionUrl;

    private String passwordChangeUrl = "/pw";

    private final RedirectStrategy redirectStrategy =
            new SimpleRedirectStrategy();

    private final RequestCache requestCache = new HttpSessionRequestCache();

    public SimpleLoginSuccessHandler() {
        targetUrlParameter = "";
        defaultUrl = "/main";
        useReferer = false;
        useSessionUrl = false;
    }

    public String getTargetUrlParameter() {
        return targetUrlParameter;
    }

    public void setTargetUrlParameter(String targetUrlParameter) {
        this.targetUrlParameter = targetUrlParameter;
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    public boolean isUseReferer() {
        return useReferer;
    }

    public void setUseReferer(boolean useReferer) {
        this.useReferer = useReferer;
    }

    public boolean isUseSessionUrl() {
        return useSessionUrl;
    }

    public void setUseSessionUrl(boolean useSessionUrl) {
        this.useSessionUrl = useSessionUrl;
    }

    public String getPasswordChangeUrl() {
        return passwordChangeUrl;
    }

    public void setPasswordChangeUrl(String passwordChangeUrl) {
        this.passwordChangeUrl = passwordChangeUrl;
    }

    @Resource
    LoginLogSvc loginLogSvc;

    @Override
    public <T> void successProc(HttpServletRequest request,
            HttpServletResponse response, T loginInfo) throws IOException {

        clearAuthenticationAttributes(request);

        // TODO 계정잠금 초기화
//        resetUserLock(request);

        // 로그인 저장소에 로그인설정
        setLoginInfo(request, loginInfo);

        redirectPage(request, response, loginInfo);
    }

    private <T> void redirectPage(HttpServletRequest request,
            HttpServletResponse response, T loginInfo) throws IOException {

        SimpleLoginVO loginVO;
        if (loginInfo instanceof SimpleLoginVO) {
            loginVO = (SimpleLoginVO) loginInfo;
            log.info("(^_^)b.........login success.");
            if (StringUtils.isNotEmpty(loginVO.getTempPwd())) {
                log.info("(^_^;).........But change your password!");
                loginVO.setCredentialsNonExpired(false);
                setDefaultUrl(getPasswordChangeUrl());
            }
        }

        redirectPage(request, response);
    }

    /**
     * 페이지 리다이렉트
     *
     * @param request  request
     * @param response response
     */
    protected void redirectPage(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        int iRedirectStrategy = decideRedirectStrategy(request, response);

        log.debug("redirect........." + iRedirectStrategy);

        switch (iRedirectStrategy) {
            case USE_TARGET_URL:
                useTargetUrl(request, response);
                break;
            case USE_SESSION_URL:
                useSessionUrl(request, response);
                break;
            case USE_REFERER_URL:
                useRefererUrl(request, response);
                break;
            default:
                useDefaultUrl(request, response);
                break;
        }
    }

    /**
     * 세션과 세션관련 저장소에 로그인 정보를 추가한다.
     *
     * @param request   request
     * @param loginInfo login info
     * @param <T>       BaseLoginDTO를 상속받은 login info
     */
    protected <T> void setLoginInfo(HttpServletRequest request, T loginInfo) {

        HttpSession session = request.getSession();

        // [주의] 세션이 초기화 됨
        SessionUtils.clearSession(session);
        session = request.getSession();

        // set login info
        SysLoginVO sysLoginVO;
        String loginId;
        if (loginInfo instanceof SysLoginVO) {
            sysLoginVO = (SysLoginVO) loginInfo;
            loginId = sysLoginVO.getLoginId();

            SessionListener sessionListener = SessionListener.getInstance();

            // 로그인 중복검사
            if (maxSession != -1) {
                if (sessionListener.isUsing(loginId)) {
                    log.info("이전 로그인을 끊고 접속합니다.");
                    sessionListener.removeSession(loginId);
                }
            }

            session.setAttribute(SIMPLE_PRINCIPAL, loginInfo);
            sessionListener.setSession(session, loginId);

            // 로그인 기록
            loginLogSvc.writeLog(loginId, request);
        } else {
            String msg = "로그인 정보 형식이 다릅니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }
    }

    /**
     * 기본URL로 가기
     *
     * @param request  request
     * @param response response
     * @throws IOException io exception
     */
    private void useDefaultUrl(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        redirectStrategy.sendRedirect(request, response, defaultUrl);
    }

    /**
     * REFERER URL로 가기
     *
     * @param request  request
     * @param response response
     * @throws IOException io exception
     */
    private void useRefererUrl(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        String tagetUrl = request.getHeader(REFERER);
        redirectStrategy.sendRedirect(request, response, tagetUrl);
    }

    /**
     * 세션 URL로 가기
     *
     * @param request  request
     * @param response response
     * @throws IOException io exception
     */
    private void useSessionUrl(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        String targetUrl = savedRequest.getRedirectUrl();

        // DEBUG
        if (log.isDebugEnabled()) {
            log.debug("SESSION_URL.........." + targetUrl);
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /**
     * 지정된 URL로 가기
     *
     * @param request  request
     * @param response response
     * @throws IOException io exception
     */
    private void useTargetUrl(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            requestCache.removeRequest(request, response);
        }

        String targetUrl = request.getParameter(targetUrlParameter);
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /**
     * 전달정책 결정
     *
     * @param request  request
     * @param response response
     * @return (1 : 지정 URL 로 이동, 2 : 세션 URL 로 이동, 3 : referer URL 로 이동, other : 기본 URL 로 이동)
     */
    private int decideRedirectStrategy(HttpServletRequest request,
            HttpServletResponse response) {

        int result;

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (StringUtils.isNoneEmpty(targetUrlParameter)) {
            String targetUrl = request.getParameter(targetUrlParameter);

            // DEBUG
            if (log.isDebugEnabled()) {
                log.debug("TARGET_URL.........." + targetUrl);
            }

            if (StringUtils.isNotEmpty(targetUrl)) {
                result = USE_TARGET_URL;
            } else {

                if (useSessionUrl && savedRequest != null) {
                    result = USE_SESSION_URL;
                } else {

                    String refererUrl = request.getHeader(REFERER);

                    if (useReferer && StringUtils.isNotEmpty(refererUrl)) {
                        result = USE_REFERER_URL;
                    } else {
                        result = USE_DEFAULT_URL;
                    }
                }
            }

            return result;
        }

        if (useSessionUrl && savedRequest != null) {
            result = USE_SESSION_URL;
            return result;
        }

        String refererUrl = request.getHeader(REFERER);

        if (useReferer && StringUtils.isNotEmpty(refererUrl)) {

            // DEBUG
            if (log.isDebugEnabled()) {
                log.debug("REFERER_URL.........." + refererUrl);
            }

            result = USE_REFERER_URL;
        } else {
            result = USE_DEFAULT_URL;
        }

        return result;
    }

//    private void resetUserLock(HttpServletRequest req) {
//    }

    /**
     * 인증 실패속성 삭제
     *
     * @param request request
     */
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        successProc(request, response, authentication.getPrincipal());
    }
}
