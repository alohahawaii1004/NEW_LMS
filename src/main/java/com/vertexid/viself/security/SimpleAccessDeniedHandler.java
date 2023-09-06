/*
 * @(#)SimpleAccessDeniedHandler.java     2021-03-24(024) 오후 4:46
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

import com.vertexid.spring.utils.BaseProperties;
import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.base.BaseHandler;
import net.sf.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * <b>Description</b>
 * <pre>
 *     접근불가 핸들러
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Component
public class SimpleAccessDeniedHandler extends BaseHandler {

    private SimpleSecurityEnvVO simpleSecurityEnvVO;

    private RedirectStrategy redirectStrategy = new SimpleRedirectStrategy();

    @Resource
    SimpleSecurityEnvSvc simpleSecurityEnvSvc;

    private String redirectPage;
    private String deniedReason = "expired";

    public void handle(HttpServletRequest request,
            HttpServletResponse response, AccessDeniedException exception)
            throws IOException, ServletException {

        log.error("ACCESS DENIED");
        log.error("exception URI......"+ request.getRequestURI());
        log.error("IS AJAX......"+ SessionUtils.isAjaxRequest(request));
        log.error("exception......"+ exception.getMessage());

        if(null == simpleSecurityEnvVO){
            simpleSecurityEnvVO = simpleSecurityEnvSvc.getEnv();
        }

//        Authentication authentication = SessionUtils.getAuthentication();
        BaseLoginDTO rtnInfo = getLoginInfo(request);

        redirectPage = simpleSecurityEnvVO.getExpiredURI();
        if(null != rtnInfo){
            deniedReason = "forbidden";
            redirectPage = BaseProperties.get("security.defaultURI");
        }

        log.error("authentication......"+ rtnInfo);
        log.error("redirectPage......"+ redirectPage);

        if (null != rtnInfo && exception instanceof MissingCsrfTokenException) {
            /* Handle as a session timeout (redirect, etc).
            Even better if you inject the InvalidSessionStrategy
            used by your SessionManagementFilter, like this:
            invalidSessionStrategy.onInvalidSessionDetected(request, response);
            */

            deniedReason = "missing";
            handleMissCsrfTokenException(request, response, exception);

        } else {
            /* Redirect to a error page, send HTTP 403, etc. */

            handleFobidden(request, response, exception);
        }

//        throw new RuntimeException("Access Denied");
    }

    /**
     * 접근 금지 처리
     * @param request request
     * @param response response
     * @param exception exception
     */
    private void handleFobidden(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
            throws IOException {

        // REVIEW 기록할 것인가?

        log.error("Exception...!!!"+exception.toString());
        if (SessionUtils.isAjaxRequest(request)) {

            // 접근 금지 메시지

            // AJAX 인 경우
            handleAjax(request, response, exception);

        }else{
            // 접근 금지 페이지 표시
            redirectPage(request, response);
        }
    }

    private void redirectPage(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        redirectStrategy.sendRedirect(request, response, redirectPage);
    }

    /**
     * CSRF 토큰 오류 처리
     * @param request request
     * @param response response
     * @param exception exception
     */
    private void handleMissCsrfTokenException(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
            throws IOException {

        log.warn("Exception...???"+exception.toString());
        if (SessionUtils.isAjaxRequest(request)) {

            // 세션종료, 다시 로그인 권고, Front-end 에서 로그인 화면으로 전환 처리 필요

            // AJAX 인 경우
            handleAjax(request, response, exception);

        }else{
            // 로그인 화면으로 전환 처리 필요
            redirectPage(request, response);
        }

    }

    private void handleAjax(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) {

        JSONObject jsonObject = new JSONObject();

        if (exception instanceof MissingCsrfTokenException) {
            jsonObject.put("MISSING_YN", "Y");
        }else{
            jsonObject.put("ACCESS_DENY", "Y");
        }


        jsonObject.put("FORBIDDEN", "N");
        if("forbidden".equals(deniedReason)){
            jsonObject.put("FORBIDDEN", "Y");
        }

        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonObject);
            response.getWriter().flush();

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private BaseLoginDTO getLoginInfo(HttpServletRequest req) {
        BaseLoginDTO rtnInfo = (BaseLoginDTO) SessionUtils.getLoginVO();
        if (null == rtnInfo) {
            HttpSession session = req.getSession();
            rtnInfo = (BaseLoginDTO) SessionUtils.getLoginVO(session);
        }
        return rtnInfo;
    }
}
