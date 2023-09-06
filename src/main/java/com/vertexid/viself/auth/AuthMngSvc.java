/*
 * @(#)AuthMngSvc.java     2022-03-14(014) 오후 4:17
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
package com.vertexid.viself.auth;

import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class AuthMngSvc extends BaseSvc {

    public static final String NAMESPACE = "com.vertexid.viself.auth.AuthMng";
    private static final String SYS_AUTHS = "CMM_ISJ,CMM_SYS,SYS_DEV";

    @Resource(name = "cmmDAO")
    private CmmDAO cmmDAO;

    @Resource
    AuthMenuSvc authMenuSvc;

    @Resource
    AuthMemberSvc authMemberSvc;

    public String add(AuthMngDTO params) {

        String result = "";

        if (log.isInfoEnabled()) {
            log.info("Input parameters............." + params);
        }

        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insert"), params);

        if (ERROR_RESULT.equals(params.getErrYn())) {
            result = params.getErrMsg();
            log.error("Error.Input parameters............." + params);
            throw new RuntimeException(result);
        }

        return result;

    }

    public String update(AuthMngDTO params) {
        String result = "";

        if (log.isInfoEnabled()) {
            log.info("Input parameters............." + params);
        }

        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "update"), params);

        if (ERROR_RESULT.equals(params.getErrYn())) {
            result = params.getErrMsg();
            log.error("Error.Input parameters............." + params);
            throw new RuntimeException(result);
        }

        return result;
    }

    public String delete(AuthMngDTO params) {
        String result = "";

        if (log.isInfoEnabled()) {
            log.info("Input parameters............." + params);
        }

        // 삭제 불가 권한
        if(SYS_AUTHS.contains(params.getAuthCd())){
            params.setErrYn(ERROR_RESULT);
            params.setErrMsg("시스템 권한은 삭제할 수 없습니다.");
        }

//        // 0. 사용자 권한 변경 대상 얻기
//        Map<String, Object> schParam = new HashMap<>();
//        schParam.put("authCd", params.getAuthCd());
//        schParam.put("authMemberTpCd", MEMBER_TYPE_USER);
//        List<Map<String, Object>> memberList = cmmDAO.getList(
//                cmmDAO.getStmtByNS(MEMBER_NAMESPACE, "getAuthorities"),
//                schParam);

        // 1. 권한 메뉴 삭제
        AuthMenuDTO menuDTO = new AuthMenuDTO();
        menuDTO.setAuthCd(params.getAuthCd());
        authMenuSvc.deleteMenuByAuth(menuDTO);

        // 2. 멤버 삭제
        AuthMemberDTO memberDTO = new AuthMemberDTO();
        memberDTO.setAuthCd(params.getAuthCd());
        authMemberSvc.deleteAuthMembers(memberDTO);

        // 3. 권한 코드 삭제
        cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "delete"), params);

        if (ERROR_RESULT.equals(params.getErrYn())) {
            result = params.getErrMsg();
            log.error("Error.Input parameters............." + params);
            throw new RuntimeException(result);
        }

        return result;
    }

    public String updateSort(AuthMngDTO params) {
        String result = "";

        if (log.isInfoEnabled()) {
            log.info("Input parameters............." + params);
        }

        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updateSort"), params);

        if (ERROR_RESULT.equals(params.getErrYn())) {
            result = params.getErrMsg();
            log.error("Error.Input parameters............." + params);
            throw new RuntimeException(result);
        }

        return result;
    }

    @Transactional(readOnly = true)
    public <E> List<E> getList(Map<String, Object> param) {
        return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "list"), param);
    }
}
