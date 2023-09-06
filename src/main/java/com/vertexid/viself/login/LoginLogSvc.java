/*
 * @(#)LoginLogSvc.java     2022-11-03(003) 오후 3:11:00
 *
 * Copyright 2022 JaYu.space
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
package com.vertexid.viself.login;

import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <b>Description</b>
 * <pre>
 *     로그인 기록
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class LoginLogSvc extends BaseSvc {

    public static final String NAMESPACE = "com.vertexid.viself.hr.UserLoginLog";

    @Resource
    CmmDAO cmmDAO;

    public void writeLog(String loginId, HttpServletRequest request) {

        LoginLogDTO loginLogDTO = new LoginLogDTO();
        loginLogDTO.setLoginId(loginId);
        loginLogDTO.setLoginIp(SessionUtils.getRemoteIP(request));

        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insert"), loginLogDTO);
    }
}
