/*
 * @(#)SessionUtils.java     2021-02-02(002) 오전 10:53
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
package com.vertexid.spring.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Objects;

import static com.vertexid.spring.session.SessionListener.SIMPLE_PRINCIPAL;

/**
 * <b>Description</b>
 * <pre>
 *      스프링시큐리티와 연동하여 세션의 로그인 관련 정보를 가져오는 등 session 에 관한 유틸
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class SessionUtils {
    private static final Logger log =
            LoggerFactory.getLogger(SessionUtils.class);

    private static final String ORGIN_HEADER = "Origin";
    private static final String X_REQUESTED_WITH_HEADER = "X-Requested-With";
    private static final String JSON_POSTFIX = "/json";

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(
                RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public static HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    /**
     * 세션객체를 반환
     * (Spring F/W 내에서만 동작)
     * @return 세션
     */
    public static HttpSession getSession() {
        HttpSession session = null;
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest req =getRequest();
            if(req == null){
                req = getCurrentRequest();
            }
            session = req.getSession();
        }else{
            log.warn("req 가 Null 입니다.");
        }
        return session;
    }

    /**
     * 사용자 세션상의 모든 authentication(인증정보) 을 반환
     * (Spring Security 사용시 가능)
     * @return authentication
     */
    public static Authentication getAuthentication() {
        if (null != SecurityContextHolder.getContext().getAuthentication()) {
            return SecurityContextHolder.getContext().getAuthentication();
        } else {
            log.warn("Not Found Spring Security Authentication");
            return null;
        }
    }

    /**
     * 세션에서 로그인 정보 얻기
     *
     * @return 로그인 정보
     */
    public static Object getLoginVO() {
//        if(null != SecurityContextHolder.getContext().getAuthentication()){
//            return Objects.requireNonNull(getAuthentication()).getPrincipal();
//        }
//        log.info("Not Found Spring Security Authentication");
        HttpSession session = getSession();
        if (null == session) {
            return null;
        }
        return session.getAttribute(SIMPLE_PRINCIPAL);
    }

    /**
     * 세션에서 로그인 정보 얻기
     *
     * @return 로그인 정보
     */
    public static Object getLoginVO(HttpSession session) {
        return session.getAttribute(SIMPLE_PRINCIPAL);
    }

    /**
     * AJAX 호출여부 반환( HTML5 기준 ) 혹은 JSON 요청여부
     *
     * @param request request
     * @return true: ajax 호출(혹은 JSON 요청), other: 일반호출
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {

        String orginHeader = request.getHeader(ORGIN_HEADER);
        String xRequestedWithHeader =
                request.getHeader(X_REQUESTED_WITH_HEADER);
        String customHeader = request.getHeader("JaYu");
        String reqPath = request.getRequestURI();

        if (StringUtils.isNotEmpty(xRequestedWithHeader)) {
            log.info("x request Header exist");
            return true;
        }
        if (StringUtils.containsIgnoreCase(reqPath, JSON_POSTFIX)) {
            log.info("'/json' exist");
            return true;
        }
        if (StringUtils.isNotEmpty(customHeader) &&
                customHeader.equals("YeoYu")) {
            log.info("custom Header exist");
            return true;
        }

//        if(StringUtils.isNotEmpty(orginHeader)) {
//            log.info("orgin Header exist");
//            return true;
//        }

        return false;
    }

    /**
     * 세션 삭제(session.invalidate)
     *
     * @param session session
     */
    public static void clearSession(HttpSession session) {

        Enumeration<String> enuKeys = session.getAttributeNames();
        while (enuKeys.hasMoreElements()) {
            String tmpKey = enuKeys.nextElement();
            session.removeAttribute(tmpKey);
        }
        session.invalidate();
    }

    /**
     * 사용자 IP 얻어오기(프록시를 통해서 IP가 들어올 경우도 상정)
     *
     * @param request 요청
     * @return 사용자 IP
     */
    public static String getRemoteIP(HttpServletRequest request) {
        // 이외에도 X-Real-IP 나 X-RealIP 라는 헤더를 사용하는 제품도 있다고 한다.
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 사용자 IP 얻어오기(프록시를 통해서 IP가 들어올 경우도 상정)
     * RequestContextHolder 사용
     *
     * @return 사용자 IP
     */
    public static String getRemoteIP() {

        HttpServletRequest request = null;
        if (RequestContextHolder.getRequestAttributes() != null) {
            request =
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }

        if (request == null) {
            return null;
        }

        // 이외에도 X-Real-IP 나 X-RealIP 라는 헤더를 사용하는 제품도 있다고 한다.
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if(StringUtils.contains(ip, ",")){
            ip = StringUtils.substringBefore(ip, ",");
        }
        return ip;
    }
}
