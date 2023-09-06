/*
 * @(#)CmmAuthenticationSuccessHandler.java     2021-01-13(013) 오전 11:37
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

import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.base.BaseHandler;
import com.vertexid.viself.security.BaseLoginDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * <b>Description</b>
 * <pre>
 *     인증 성공 핸들러
 *     [참고]
 *      - https://zgundam.tistory.com/52?category=430446
 *      - https://to-dy.tistory.com/94?category=720806
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class CmmAuthenticationSuccessHandler extends BaseHandler
        implements AuthenticationSuccessHandler {

    private static final String REFERER = "REFERER";

    private static final int USE_TARGET_URL = 1;
    private static final int USE_SESSION_URL = 2;
    private static final int USE_REFERER_URL = 3;
    private static final int USE_DEFAULT_URL = 0;

    private final RequestCache requestCache = new HttpSessionRequestCache();

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * redirect target url parameter
     */
    private String targetUrlParameter;

    /**
     * redirect default url
     */
    private String defaultUrl;

    /**
     * referer 사용여부
     */
    private boolean useReferer;

    /**
     * session url 사용여부
     */
    private boolean useSessionUrl;

    public CmmAuthenticationSuccessHandler() {
        targetUrlParameter = "";
        defaultUrl = "/";
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

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        log.debug("onAuthenticationSuccess.....................");

        // 세션에 저장된 로그인 실패오류 기록 삭제
        clearAuthenticationAttributes(request);

        BaseLoginDTO loginVO = (BaseLoginDTO) SessionUtils.getLoginVO();

        log.debug("loginVO............."+loginVO);

        // 계정잠금 초기화
        resetLockInfo();

        // 로그인 기록
        writeLoginInfo();

        // 리다이렉트
        redirectPage(request, response);
    }

    /**
     * 페이지 리다이렉트
     * @param request request
     * @param response response
     */
    private void redirectPage(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int iRedirectStrategy = decideRedirectStrategy(request, response);

        log.debug("redirect........."+ iRedirectStrategy);

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
     * 기본 URL 로 가기
     * @param request request
     * @param response response
     * @throws IOException IOException
     */
    private void useDefaultUrl(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        redirectStrategy.sendRedirect(request, response, defaultUrl);
    }

    /**
     * REFERER URL 로 가기
     *
     * @param request request
     * @param response response
     * @throws IOException IOException
     */
    private void useRefererUrl(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        String targetUrl = request.getHeader(REFERER);
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /**
     * 세션 URL 로 가기
     *
     * @param request request
     * @param response response
     * @throws IOException IOException
     */
    private void useSessionUrl(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        String targetUrl = savedRequest.getRedirectUrl();

        // DEBUG
        if(log.isDebugEnabled()){
            log.debug("SESSION_URL.........."+targetUrl);
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /**
     * 지정된 URL 로 가기
     *
     * @param request request
     * @param response response
     * @throws IOException IOException
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
     * 인증 성공후 어떤 URL로 redirect 할지 결정
     * @param request request
     * @param response response
     * @return 1: target url, 2: session url, 3: referer, 0: default
     */
    private int decideRedirectStrategy(HttpServletRequest request, HttpServletResponse response) {
        int result;

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if(StringUtils.isNotEmpty(targetUrlParameter)){
            String targetUrl = request.getParameter(targetUrlParameter);

            // DEBUG
            if(log.isDebugEnabled()){
                log.debug("TARGET_URL.........."+targetUrl);
            }

            if(StringUtils.isNotEmpty(targetUrl)){
                result = USE_TARGET_URL;
            }else{
                if(useSessionUrl && savedRequest != null){
                    result = USE_SESSION_URL;
                }else{

                    String refererUrl = request.getHeader(REFERER);

                    if(useReferer && StringUtils.isNotEmpty(refererUrl)){
                        result = USE_REFERER_URL;
                    }else{
                        result = USE_DEFAULT_URL;
                    }
                }
            }

            return result;
        }

        if(useSessionUrl && savedRequest != null){
            result = USE_SESSION_URL;
            return result;
        }

        String refererUrl = request.getHeader(REFERER);

        if(useReferer && StringUtils.isNotEmpty(refererUrl)){

            // DEBUG
            if(log.isDebugEnabled()){
                log.debug("REFERER_URL.........."+refererUrl);
            }

            result = USE_REFERER_URL;
        }else{
            result = USE_DEFAULT_URL;
        }

        return result;
    }

    /**
     * 로그인 기록
     */
    private void writeLoginInfo() {
        // TODO
    }

    /**
     * 계정잠금 데이터 초기화
     */
    private void resetLockInfo() {
        // TODO
    }

    /**
     * 세션에 저장된 로그인 실패오류 기록 삭제
     * @param request request
     */
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        // 세션에 저장된 로그인 실패오류 기록 삭제
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
