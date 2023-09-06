/*
 * @(#)LoginSuccessHandler.java     2021-02-02(002) 오후 3:19
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <b>Description</b>
 * <pre>
 *     로그인 성공 처리
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public interface LoginSuccessHandler {

    /**
     * 로그인 성공 처리
     * @param request request
     * @param response response
     * @param loginInfo 로그인 정보
     * @param <T> UserDetails 를 구현한 로그인 정보
     */
    <T> void successProc(HttpServletRequest request, HttpServletResponse response, T loginInfo)
            throws IOException;
}
