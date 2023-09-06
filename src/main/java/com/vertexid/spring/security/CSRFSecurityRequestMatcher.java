/*
 * @(#)CSRFSecurityRequestMatcher.java     
 *
 * Copyright 2022 JAYU.space
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

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * <b>Description</b>
 * <pre>
 *     Spring Security 의 CSRF Token용 커스텀 RequestMatcher
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class CSRFSecurityRequestMatcher implements RequestMatcher {

    @Override
    public boolean matches(HttpServletRequest request) {
//        if(allowedMethods.matcher(request.getMethod()).matches()){
//
//            return false;
//
//        }
//
//
//        return !unprotectedMatcher.matches(request);

        return false;
    }
}
