/*
 * @(#)AuthenticationManagerFactory.java     2021-07-22(022) 오전 9:11
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

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * <b>Description</b>
 * <pre>
 *     인증 매니저 팩토리
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Component
public class AuthenticationManagerFactory {

    @Resource
    private List<AuthenticationManager> authenticationManagerList;

    public AuthenticationManager getAuthenticationManager(String type) {
        for (AuthenticationManager authenticationManager : authenticationManagerList) {
            if (type.equals(
                    authenticationManager.getAuthenticationManagerType())) {
                return authenticationManager;
            }
        }// end of for
        return null;
    }
}
