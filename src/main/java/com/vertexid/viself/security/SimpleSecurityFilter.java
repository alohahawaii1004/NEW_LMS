/*
 * @(#)SimpleSecurityFilter.java     2021-01-20(020) 오후 3:11
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <b>Description</b>
 * <pre>
 *      단순 보안 필터
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Component
public class SimpleSecurityFilter implements Filter {

    /*
     * copycat SpringSecurity
     *
     * spring security 를 공부하면서 유사하게 구성해봄
     * 이 구조를 보고 spring security 를 보면 초큼은 접근이 쉽지 않을까....
     */

    /**
     * logger
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    SimpleAccessControlManager simpleAccessControlManager;

    @Resource
    SimpleAccessDeniedHandler simpleAccessDeniedHandler;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info(".......................init Simple Security Filter!!");
    }

    @Override
    public void doFilter(ServletRequest request,
            ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            if (checkSecurity(request, response)) {
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            log.error("URI....."+((HttpServletRequest)request).getRequestURI());
            log.error(e.getMessage());
            if(e instanceof AccessDeniedException){
                simpleAccessDeniedHandler.handle((HttpServletRequest) request, (HttpServletResponse) response, (AccessDeniedException) e);
            }else{
                log.error(e.toString());
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 접근 제어 검사
     *
     * @param request  request
     * @param response response
     * @return true: 접근허가, other: 접근 불가
     */
    private boolean checkSecurity(ServletRequest request,
            ServletResponse response) throws IOException, ServletException {
//        log.debug(">>>>>>>>>>>>>>>>"+((HttpServletRequest)request).getRequestURI());
        return simpleAccessControlManager.checkAccess(request, response);
    }

    @Override
    public void destroy() {

    }
}
