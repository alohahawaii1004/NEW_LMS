/*
 * @(#)LoginFailHandler.java     2021-02-02(002) 오후 3:22
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <b>Description</b>
 * <pre>
 *     로그인 실패 처리
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public interface LoginFailHandler {

    /**
     * 로그인 실패 처리
     * @param request request
     * @param response response
     * @param exception exception
     * @param <E> 실패 오류
     * @throws ServletException redirect 혹은 forward 오류
     * @throws IOException io 오류
     */
    <E> void failureProc(HttpServletRequest request,
            HttpServletResponse response, E exception)
            throws ServletException, IOException;
}
