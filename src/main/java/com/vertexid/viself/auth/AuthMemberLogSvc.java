/*
 * @(#)AuthMemberLogSvc.java     2022-10-31(031) 오후 4:18:12
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
package com.vertexid.viself.auth;

import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <b>Description</b>
 * <pre>
 *     권한변경 이력 Service
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class AuthMemberLogSvc extends BaseSvc {

    public static final String NAMESPACE = "com.vertexid.viself.auth.AuthMemberLog";

    @Resource
    CmmDAO cmmDAO;

    /**
     * 권한변경 로그 기록
     * @param param MBR_TP, MBR_ID
     */
    public void writeLog(AuthMemberDTO param) {
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insert"), param);
    }
}
