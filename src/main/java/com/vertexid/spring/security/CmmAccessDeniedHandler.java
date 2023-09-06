/*
 * @(#)CmmAccessDeniedHandler.java     2021-01-13(013) 오후 5:15
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <b>Description</b>
 * <pre>
 *     TODO
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class CmmAccessDeniedHandler extends BaseHandler implements
        AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
            HttpServletResponse response, AccessDeniedException exception)
            throws IOException, ServletException {

        log.error("exception......"+ exception.getMessage());

        Authentication authentication = SessionUtils.getAuthentication();



        if (exception instanceof MissingCsrfTokenException) {
            /* Handle as a session timeout (redirect, etc).
            Even better if you inject the InvalidSessionStrategy
            used by your SessionManagementFilter, like this:
            invalidSessionStrategy.onInvalidSessionDetected(request, response);
            */
        } else {
            /* Redirect to a error page, send HTTP 403, etc. */
        }

        throw new RuntimeException("Access Denied");

        // login 일경우.... .....

    }
}
