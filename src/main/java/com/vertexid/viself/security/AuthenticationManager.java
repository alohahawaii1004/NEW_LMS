/*
 * @(#)AuthenticationManager.java     2021-02-02(002) 오전 11:32
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
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 *     인증 매니저 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public interface AuthenticationManager {


    /**
     * 인증 매니저 서비스 유형 얻기
     * @return 인증 매니저 서비스 유형
     */
    String getAuthenticationManagerType();

    /**
     * 로그인 처리
     * @param req request
     * @param res response
     * @param params
     */
    void loginProc(HttpServletRequest req, HttpServletResponse res,
            Map<String, Object> params) throws IOException, ServletException;

    /**
     * 로그아웃 처리
     * @param req request
     * @param res response
     */
    void logoutProc(HttpServletRequest req, HttpServletResponse res)
            throws IOException;

    BaseLoginDTO getLoginInfo(HttpServletRequest req);
}
