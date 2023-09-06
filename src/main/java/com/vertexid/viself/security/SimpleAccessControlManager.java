/*
 * @(#)SimpleAccessControlManager.java     2021-01-21(021) 오후 4:34
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
package com.vertexid.viself.security;

import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.base.BaseSvc;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.security.access.AccessDecisionVoter.*;
import static org.springframework.security.access.vote.AuthenticatedVoter.IS_AUTHENTICATED_FULLY;

/**
 * <b>Description</b>
 * <pre>
 *     접근제어 매니저
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Component
public class SimpleAccessControlManager extends BaseSvc {

    private static Logger log =
            LoggerFactory.getLogger(SimpleAccessControlManager.class);
    private static final String PARAM_SESSION = "session";

    public static final String AUTH_MNG_TYPE_PARAM = "type";

    private String contextPath;
    private Map<String, Object> logParams;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private final RedirectStrategy redirectStrategy =
            new SimpleRedirectStrategy();

    //    @Resource(name = "simpleAuthenticationManager")
    AuthenticationManager authenticationManager;

    @Resource
    AuthenticationManagerFactory authenticationManagerFactory;

    @Resource
    SimpleCSRFTokenManager simpleCSRFTokenManager;

    @Resource
    SimpleAuthenticationVoter simpleAuthenticationVoter;

    @Resource
    SimpleRoleVoter simpleRoleVoter;

    @Resource
    SimpleSecurityEnvSvc simpleSecurityEnvSvc;

    @Resource
    SimpleAccessDeniedHandler simpleAccessDeniedHandler;

    private SimpleSecurityEnvVO simpleSecurityEnvVO;

    private SimpleReloadableSecurityMetadataSource
            simpleReloadableSecurityMetadataSource;
    /**
     * ANT 패턴으로 된 검사예외 URI set
     */
    private Set<String> ignoreURISet;
    private String loginURI = "/login";
    private String loginProcURI = "/login/proc";
    private String logoutURI = "/logout";
    private String expiredURI = "/login";
    private String idParamName = "encId";
    private String pwParamName = "encPw";

    public SimpleAccessControlManager(SimpleSecurityEnvVO simpleSecurityEnvVO) {
        this.simpleSecurityEnvVO = simpleSecurityEnvVO;
        init();
    }

    /**
     * 초기화
     */
    private void init() {
        if (null == simpleSecurityEnvVO) {
            simpleSecurityEnvVO = simpleSecurityEnvSvc.getEnv();
        }
        setDefaultProperties();
    }

    /**
     * Simple Security 기본설정
     */
    private void setDefaultProperties() {

        String paramLoginURI = simpleSecurityEnvVO.getLoginURI();
        String paramLoginProcURI = simpleSecurityEnvVO.getLoginProcURI();
        String paramLogoutURI = simpleSecurityEnvVO.getLogoutURI();
        String paramExpiredURI = simpleSecurityEnvVO.getExpiredURI();
        String paramIdParamName = simpleSecurityEnvVO.getIdParamName();
        String paramPwParamName = simpleSecurityEnvVO.getPwParamName();
        ignoreURISet = simpleSecurityEnvVO.getIgnoreURIs();

        if (StringUtils.isNotEmpty(paramLoginURI)) {
            loginURI = paramLoginURI;
        }
        if (StringUtils.isNotEmpty(paramLoginProcURI)) {
            loginProcURI = paramLoginProcURI;
        }
        if (StringUtils.isNotEmpty(paramLogoutURI)) {
            logoutURI = paramLogoutURI;
        }
        if (StringUtils.isNotEmpty(paramExpiredURI)) {
            expiredURI = paramExpiredURI;
        }
        if (StringUtils.isNotEmpty(paramIdParamName)) {
            idParamName = paramIdParamName;
        }
        if (StringUtils.isNotEmpty(paramPwParamName)) {
            pwParamName = paramPwParamName;
        }
    }

    public void setSimpleReloadableSecurityMetadataSource(
            SimpleReloadableSecurityMetadataSource simpleReloadableSecurityMetadataSource) {
        this.simpleReloadableSecurityMetadataSource =
                simpleReloadableSecurityMetadataSource;
    }

    /**
     * 접근 검사
     *
     * @param request  request
     * @param response response
     * @return true: 접근허가, other: 접근차단(차단 핸들링)
     */
    public boolean checkAccess(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {

        logParams = new LinkedHashMap<String, Object>();
        boolean notIgnoreFlag = true;


        // 0. set contextPath
        HttpServletRequest req;
        HttpServletResponse res;
        if (request instanceof HttpServletRequest) {
            req = (HttpServletRequest) request;
            res = (HttpServletResponse) response;
        } else {
            log.warn("is not HttpServletRequest");
            return false;
        }

        try {

            // 1. set contextPath
            setContextPath(req);

            // 2. get URI
            String uri = req.getRequestURI();


            logParams.put("METHOD", req.getMethod());
            logParams.put("IS AJAX", SessionUtils.isAjaxRequest(req));
            logParams.put("CONTENT TYPE", req.getContentType());
            logParams.put("HEADER INFO", getHeaders(req));

            // 3. check ignore URI
            if (checkIgnoreURI(uri)) {
                notIgnoreFlag = false;
                return true;
            }

            log.info("checkAccess start ================================" + uri);
            // 4. check CSRF Token
            log.info("check CSRF Token......" + simpleSecurityEnvVO.isCsrfYn());
            if (simpleSecurityEnvVO.isCsrfYn()) {
                log.info("METHOD....................." + req.getMethod());
                log.info("check CSRF Token URI....................." + uri);
                simpleCSRFTokenManager.checkToken(req, res);
            }

            // 5. access control with authentication
            log.info("access control with authentication.............." + uri);

            String type =
                    SimpleAuthenticationManager.AUTHENGICATION_MANAGER_TYPE;
            if (uri.indexOf(loginProcURI) != -1) {
                type = StringUtils.defaultString(
                        req.getParameter(AUTH_MNG_TYPE_PARAM),
                        SimpleAuthenticationManager.AUTHENGICATION_MANAGER_TYPE);
            }
            log.info("authentication manager type....................." + type);

            authenticationManager =
                    authenticationManagerFactory.getAuthenticationManager(type);
            return decideAccess(req, res, uri, getAuthorities(req));

        } catch (Exception e) {
            e.printStackTrace();
            logParams.put("EXCEPTION", e);
            log.error(e.getMessage());

            if (e instanceof AccessDeniedException) {
                simpleAccessDeniedHandler.handle(req, res,
                        (AccessDeniedException) e);
                return false;
            } else {
                throw new RuntimeException(e.getMessage());
            }

        } finally {
            if (notIgnoreFlag) {
                writeSimpleLog();
            }
        }

    }

    /**
     * 접근제어
     *
     * @param req         request
     * @param res         response
     * @param uri         uri
     * @param authorities 로그인된 pricipal(본인) 정보(권한) collection
     * @return true: 허가, other: 불가
     */
    private boolean decideAccess(HttpServletRequest req,
            HttpServletResponse res, String uri,
            Collection<GrantedAuthority> authorities)
            throws IOException, ServletException {
        String chkUri = getCheckURI(uri);
        int voteResult = ACCESS_ABSTAIN;
        String strVoteResult = "ACCESS_ABSTAIN";

        Collection<ConfigAttribute> allowCollection =
                simpleReloadableSecurityMetadataSource.getAttributes(req);

        if (allowCollection == null) {
            log.warn("check decideAccess url............" + chkUri);
            log.warn("allowCollection is null");
            if (simpleSecurityEnvVO.isAllowIfAllAbstainDecisions()) {
                logParams.put("isAllowIfAllAbstainDecisions",
                        "ALLOW: allow URL collection is null");
                return true;
            } else {
                throw new AccessDeniedException("Access Denied", new Throwable(
                        "allow URL collection is null. URL:" + chkUri));
            }
        }

        // URL 에 관한 컬랙션
        log.info("urlAllowCollection...." + allowCollection);
        // 사용자 컬랙션
        log.info("userAuthorities........" + authorities);

        // 1. vote authentication
        int authResult =
                simpleAuthenticationVoter.vote(authorities, allowCollection);
        log.info("authVoterResult...." + authResult);

        // 2. vote role
        int roleResult = simpleRoleVoter.vote(authorities, allowCollection);
        log.info("roleVoterResult...." + roleResult);

        //
        int result = authResult + roleResult;
        if (result > 0) {
            voteResult = ACCESS_GRANTED;
            strVoteResult = "ACCESS_GRANTED";
        } else if (result < 0) {
            voteResult = ACCESS_DENIED;
            strVoteResult = "ACCESS_DENIED";
        }

        logParams.put("AUTHORITIES", authorities);
        logParams.put("CHECK AUTHENTICATED", authResult);
        logParams.put("CHECK ROLE", roleResult);
        logParams.put("RESULT", result);
        logParams.put("VOTE RESULT", strVoteResult);

        log.info("VOTE RESULT.............." + strVoteResult);
        log.info("checkAccess end ================================" + chkUri);

        if (voteResult == ACCESS_GRANTED) {
            if (StringUtils.isNotEmpty(loginProcURI) &&
                    loginProcURI.equals(chkUri)) {
                logParams.put("LOGIN PROC URI", loginProcURI);
                log.info("go login ..............");
                procLogin(req, res);
                return false;
            } else if (StringUtils.isNotEmpty(logoutURI) &&
                    logoutURI.equals(chkUri)) {
                logParams.put("LOGOUT URI", logoutURI);
                log.info("go logout ..............");
                procLogout(req, res);
                return false;
            }
            return true;
        } else if (voteResult == ACCESS_DENIED) {
            throw new AccessDeniedException("Access Denied", new Throwable(
                    "the result of vote is 'access denied'. URL:" + chkUri));
        } else {
            if (simpleSecurityEnvVO.isAllowIfAllAbstainDecisions()) {
                logParams.put("isAllowIfAllAbstainDecisions", "ALLOW");
                return true;
            } else {
                throw new AccessDeniedException("Access Denied", new Throwable(
                        "the result of vote is 'access abstain'. URL:" +
                                chkUri));
            }
        }
    }

    /**
     * 로그아웃 처리
     *
     * @param req
     * @param res
     * @throws IOException
     */
    private void procLogout(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        authenticationManager.logoutProc(req, res);
    }


    /**
     * 로그인 처리
     *
     * @param req request
     * @param res response
     */
    private void procLogin(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(idParamName, req.getParameter(idParamName));
        params.put(pwParamName, req.getParameter(pwParamName));
        authenticationManager.loginProc(req, res, params);
    }

    /**
     * context path 설정
     *
     * @param request request
     */
    private void setContextPath(HttpServletRequest request) {
        if (StringUtils.isEmpty(contextPath)) {
            contextPath = request.getContextPath();
        }
        logParams.put("CONTEXT PATH", contextPath);
    }


    /**
     * 로그인 정보 얻기
     *
     * @param req request
     * @return 권한 정보 (athorities)
     */
    @SuppressWarnings("unchecked")
    private Collection<GrantedAuthority> getAuthorities(
            HttpServletRequest req) {
        BaseLoginDTO loginDTO = authenticationManager.getLoginInfo(req);
        Collection<GrantedAuthority> authorities = null;
        if (loginDTO != null) {
            logParams.put("PRINCIPAL", loginDTO.getUsername());
            authorities =
                    (Collection<GrantedAuthority>) loginDTO.getAuthorities();
            GrantedAuthority athenticated =
                    new SimpleGrantedAuthority(IS_AUTHENTICATED_FULLY);
            if (!authorities.contains(athenticated)) {
                authorities.add(athenticated);
            }
        } else {
            logParams.put("PRINCIPAL", new SimpleGrantedAuthority(
                    AuthenticatedVoter.IS_AUTHENTICATED_ANONYMOUSLY));
            authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(
                    AuthenticatedVoter.IS_AUTHENTICATED_ANONYMOUSLY));
        }

        return authorities;
    }

    /**
     * write (console or file) log
     */
    private void writeSimpleLog() {

//        StringBuffer sbInfo = new StringBuffer();
//        sbInfo.append("\n    ============================================");
//        sbInfo.append("\n    SIMPLE SECURITY FILTER INFO");
//        sbInfo.append("\n    --------------------------------------------");
//
//        Iterator<String> keyItr = logParams.keySet().iterator();
//
//        while (keyItr.hasNext()) {
//            String tmpKey = keyItr.next();
//            if (PARAM_SESSION.equals(tmpKey)) {
//                sbInfo.append("\n    ").append(tmpKey).append(" : ")
//                        .append(((HttpSession) logParams.get(tmpKey)).getId());
//            } else {
//                sbInfo.append("\n    ").append(tmpKey).append(" : ")
//                        .append(logParams.get(tmpKey));
//            }
//
//        }
//
//        sbInfo.append("\n    ============================================");
//
//        log.debug(sbInfo.toString());
    }

    /**
     * 보안검사 제외 URI 여부 확인
     *
     * @param uri uri
     * @return true: 제외, other: 검사
     */
    private boolean checkIgnoreURI(String uri) {

        String chkURI = getCheckURI(uri);
        Iterator<String> iterator = ignoreURISet.iterator();

        while (iterator.hasNext()) {
            String tmpPattern = iterator.next();
            if (antPathMatcher.match(tmpPattern, chkURI)) {
                logParams.put("CHECK IGNORE URI", "true");
                return true;
            }
        }// end of while

        logParams.put("CHECK IGNORE URI", "false");
        return false;
    }

    /**
     * 패턴검사를 할 URI 즉 contextPath 를 제거한 URI 반환
     *
     * @param uri 원본 URI
     * @return 검사할 URI
     */
    private String getCheckURI(String uri) {
        logParams.put("CHECK URI", uri.replaceFirst(contextPath, ""));
        return uri.replaceFirst(contextPath, "");
    }

    private Map<String, Object> getHeaders(HttpServletRequest request) {
        Map<String, Object> rtnHeader = new HashMap<String, Object>();
        if (true) {
            Enumeration<String> enumHeader = request.getHeaderNames();

            while (enumHeader.hasMoreElements()) {
                String name = (String) enumHeader.nextElement();
                rtnHeader.put(name, request.getHeader(name));
            }//end of while

        } else {
            String[] minHeaders =
                    {"x-requested-with", "user-agent", "content-type", "Origin",
                            "accept-language"};
            int hLen = minHeaders.length;
            for (int i = 0; i < hLen; i += 1) {
                rtnHeader.put(minHeaders[i], request.getHeader(minHeaders[i]));
            }
        }
        return rtnHeader;
    }
}
