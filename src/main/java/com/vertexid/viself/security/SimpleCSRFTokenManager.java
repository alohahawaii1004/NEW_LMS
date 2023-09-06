/*
 * @(#)SimpleCSRFTokenManager.java     2021-02-04(004) 오후 3:30
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

import com.vertexid.viself.base.BaseSvc;
import org.springframework.security.web.csrf.*;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/**
 * <b>Description</b>
 * <pre>
 *     CSRF Token 처리
 *     copycat Spring Security CSRFFilter
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Component
public class SimpleCSRFTokenManager extends BaseSvc {

    @Resource
    SimpleSecurityEnvSvc simpleSecurityEnvSvc;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * CSRF 검사 제외대상
     */
    @SuppressWarnings("unchecked")
    private final HashSet<String> allowedMethods =
            new HashSet(Arrays.asList("GET", "HEAD", "TRACE", "OPTIONS"));

    /**
     * TOKEN 저장소
     */
    private final CsrfTokenRepository tokenRepository;

    public SimpleCSRFTokenManager() {
        tokenRepository = new HttpSessionCsrfTokenRepository();
    }

    /**
     * request method 가 CSRF 검사대상 인지 여부 반환
     *
     * @param request 검사할 request
     * @return true: CSRF 검사대상, other: 검사대상 아님
     */
    private boolean match(HttpServletRequest request) {

        return (!allowedMethods.contains(request.getMethod()) &&
                !ignoringAntMatchers(request.getRequestURI()));
    }

    /**
     * CSRF Token 검사
     *
     * @param request  request
     * @param response response
     */
    public void checkToken(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        CsrfToken csrfToken = this.tokenRepository.loadToken(request);
        boolean missingToken = csrfToken == null;
        if (missingToken) {
            csrfToken = this.tokenRepository.generateToken(request);
            this.tokenRepository.saveToken(csrfToken, request, response);
        }
        request.setAttribute(CsrfToken.class.getName(), csrfToken);
        request.setAttribute(csrfToken.getParameterName(), csrfToken);

//        log.info("CSRF CHECK IGNORE......................." + match(request));

        if (match(request)) {

            String actualToken = request.getHeader(csrfToken.getHeaderName());
            if (actualToken == null) {
                actualToken =
                        request.getParameter(csrfToken.getParameterName());
            }

            if (!csrfToken.getToken().equals(actualToken)) {
                if (log.isWarnEnabled()) {
                    log.warn(
                            "\n   o(T^T)o...........Invalid CSRF token found for " +
                                    request.getRequestURI());
                    log.warn("missingToken? " + missingToken);
                }

                if (missingToken) {
                    throw new MissingCsrfTokenException(actualToken);
                } else {
                    throw new InvalidCsrfTokenException(csrfToken, actualToken);
                }
            }
            log.info("(^_^)b............Valid CSRF token found for " +
                    request.getRequestURI());
        }
    }

    public boolean ignoringAntMatchers(String uri) {

        SimpleSecurityEnvVO simpleSecurityEnvVO = simpleSecurityEnvSvc.getEnv();
        Iterator<String> iterator =
                simpleSecurityEnvVO.getCsrfIgnoreURIs().iterator();

        while (iterator.hasNext()) {
            String tmpPattern = iterator.next();
            if (antPathMatcher.match(tmpPattern, uri)) {
                log.info("CSRF CHECK IGNORE URI........................."+uri);
                return true;
            }
        }// end of while

        return false;
    }
}
