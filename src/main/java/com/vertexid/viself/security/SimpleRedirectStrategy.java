/*
 * @(#)SimpleRedirectStrategy.java     2021-02-03(003) 오후 12:32
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

import org.springframework.security.web.DefaultRedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <b>Description</b>
 * <pre>
 *     RedirectStrategy
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class SimpleRedirectStrategy extends DefaultRedirectStrategy {

    public SimpleRedirectStrategy() {
        super();
    }

    @Override
    public void sendRedirect(HttpServletRequest request,
            HttpServletResponse response, String url) throws IOException {
        super.sendRedirect(request, response, url);
    }
}
