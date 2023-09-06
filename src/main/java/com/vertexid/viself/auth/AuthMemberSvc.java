/*
 * @(#)AuthMemberSvc.java     2022-03-15(015) 오전 8:37
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * <b>Description</b>
 * <pre>
 *     Auth Member 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class AuthMemberSvc extends BaseSvc {

    public static final String NAMESPACE =
            "com.vertexid.viself.auth.AuthMember";

    private String errMsg;

    @Resource
    CmmDAO cmmDAO;

    @Resource
    AuthMemberLogSvc authMemberLogSvc;

    private void setError(AuthMemberDTO params) {
        params.setErrCd(ERROR_RESULT);
        params.setErrMsg(errMsg);
        params.setErrYn(Boolean.toString(true));
    }

    /**
     * 권한멤버 추가에 따른 처리
     *
     * @param params authCd, mbrId
     * @return error message
     */
    public String addMember(AuthMemberDTO params) {

        String result = "";

        if (log.isInfoEnabled()) {
            log.info("Input parameters............." + params);
        }

        // 1. 권한 멤버 추가
        insert(params);

        // 2. 사용자 권한 반영
        updateUserAuth(params);

        // 3. 권한변경 로그 기록
        authMemberLogSvc.writeLog(params);

        if (ERROR_RESULT.equals(params.getErrYn())) {
            result = params.getErrMsg();
            log.error("Error.Input parameters............." + params);
            throw new RuntimeException(result);
        }

        return result;

    }

    /**
     * 권한멤버 제외에 따른 처리
     *
     * @param params authCd, mbrId
     * @return error message
     */
    public String delMember(AuthMemberDTO params) {
        String result = "";

        if (log.isInfoEnabled()) {
            log.info("Input parameters............." + params);
        }

        // 1. 권한 멤버 삭제
        deleteAuthMember(params);

        // 2. 사용자 정보에 권한 반영
        updateUserAuth(params);

        // 3. 권한변경 로그 기록
        authMemberLogSvc.writeLog(params);

        if (ERROR_RESULT.equals(params.getErrYn())) {
            result = params.getErrMsg();
            log.error("Error.Input parameters............." + params);
            throw new RuntimeException(result);
        }

        return result;
    }

    private void updateUserAuth(AuthMemberDTO params) {
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updateAuthUser"), params);
    }

    @Transactional(readOnly = true)
    public <E> List<E> getMemberList(Map<String, Object> param) {
        return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "list"), param);
    }

    private void deleteAuthMember(AuthMemberDTO memberDTO) {

        if (StringUtils.isNotBlank(memberDTO.getMbrId()) ||
                StringUtils.isNotBlank(memberDTO.getAuthCd())) {
            cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "delete"), memberDTO);
        } else {
            log.warn("필수 파라메터가 없습니다.:" + memberDTO);
        }
    }


    /**
     * 사용자 권한 변경에 따른 권한멤버 정보 변경
     *
     * @param params list(authCd, mbrId)
     * @return error message
     */
    public String save(AuthMemberDTO params) {
        String result = "";

        if (log.isInfoEnabled()) {
            log.info("Input parameters............." + params);
        }

        // 1. insert auths
        List<AuthMemberDTO> list = params.getList();
        insertAuthList(list);

        // 2. save auth history
        // 2.1. get members from list
        List<AuthMemberDTO> logList = new ArrayList<>();
        Set<String> mbrIdSet = new HashSet<>();

        // 2.2. 기본 멤버
        if(StringUtils.isNotBlank(params.getMbrId())){
            mbrIdSet.add(params.getMbrId());
            logList.add(params);
        }

        // 2.3. 관련 멤버 추가
        for (AuthMemberDTO memberDTO : list) {
            if (!mbrIdSet.contains(memberDTO.getMbrId())) {
                mbrIdSet.add(memberDTO.getMbrId());
                logList.add(memberDTO);
            }
        }// end of for

        // 2.4. save auth log
        for (AuthMemberDTO memberDTO : logList) {
            authMemberLogSvc.writeLog(memberDTO);
        }

        if (ERROR_RESULT.equals(params.getErrYn())) {
            result = params.getErrMsg();
            log.error("Error.Input parameters............." + params);
            throw new RuntimeException(result);
        }

        return result;
    }

    private void insertAuthList(List<AuthMemberDTO> list) {
        for (AuthMemberDTO tmpDTO : list) {

            // 1. save auth member
            saveAuthMember(tmpDTO);

            // 2. update user info's auth data
//            updateUserAuth(tmpDTO);


        }// end of for
    }

    private void saveAuthMember(AuthMemberDTO params) {

        if (hasInfo(params)) {
            update(params);
        } else {
            insert(params);
        }
    }

    private void insert(AuthMemberDTO params) {
        if (checkData(params)) {
            cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insertAuthUser"),
                    params);
        }
    }

    private void update(AuthMemberDTO params) {
        if (checkData(params)) {
            cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "update"), params);
        }
    }

    private boolean checkData(AuthMemberDTO param) {
        if (StringUtils.isEmpty(param.getAuthCd())) {
            errMsg = "There is no AuthCd.";
            setError(param);
            return false;
        }

        if (StringUtils.isEmpty(param.getMbrTpCd())) {
            errMsg = "There is no MemberTpCd.";
            setError(param);
            return false;
        }

        if (StringUtils.isEmpty(param.getMbrId())) {
            errMsg = "There is no MemberId.";
            setError(param);
            return false;
        }

        return true;
    }

    @Transactional(readOnly = true)
    boolean hasInfo(AuthMemberDTO params) {
        Map<String, Object> rtnData =
                cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "count"), params);
        Integer cnt = new Integer(rtnData.get("cnt").toString());
        return (cnt > 0);
    }

    /**
     * 권한멤버에서 삭제
     *
     * @param params list(authCd, mbrId)
     * @return error message
     */
    public String delete(AuthMemberDTO params) {

        String result = "";

        List<AuthMemberDTO> list = params.getList();
        for (AuthMemberDTO tmpDTO : list) {
            delMember(tmpDTO);

            if (ERROR_RESULT.equals(tmpDTO.getErrCd())) {
                result = tmpDTO.getErrMsg();
                log.error("\n    Error.Input parameters: \n    " + tmpDTO);
                throw new RuntimeException(result);
            }

        }// end of for

        return result;
    }

    /**
     * 권한 삭제에 따른 멤버정보 삭제
     *
     * @param memberDTO auth cd
     */
    public void deleteAuthMembers(AuthMemberDTO memberDTO) {

        // 1. 사용자리스트 정보 얻기
        Map<String, Object> param = new HashMap<>();
        param.put("authCd", memberDTO.getAuthCd());
        List<Map<String, Object>> memberList = getMemberList(param);

        // 2. 멤버 삭제
        deleteAuthMember(memberDTO);

        // 3. 사용자 권한 변경
        for (Map<String, Object> memberMap : memberList) {

            AuthMemberDTO mamberParam = new AuthMemberDTO();
            mamberParam.setMbrTpCd(String.valueOf(memberMap.get("mbrTpCd")));
            mamberParam.setMbrId(String.valueOf(memberMap.get("mbrId")));

            // 3.1. 사용자 정보에 권한 반영
            updateUserAuth(mamberParam);

            // 3.2. 권한 변경이력 반영
            authMemberLogSvc.writeLog(mamberParam);
        }// end of for
    }

    /**
     * 사용자 권한 저장
     *
     * @param params mbrTp, mbrId, userNm, list(mbrTp, mbrId, userNm, authCd)
     */
    public String saveUserAuths(AuthMemberDTO params) {

        String result = "";

        if (log.isInfoEnabled()) {
            log.info("Input parameters............." + params);
        }

        // 1. delete auth member(사용자 정보에서 수정시)
        AuthMemberDTO mamberParam = new AuthMemberDTO();
        mamberParam.setMbrTpCd(params.getMbrTpCd());
        mamberParam.setMbrId(params.getMbrId());
        deleteAuthMember(mamberParam);

        // 2. save user's auths
        save(params);

        return result;
    }
}
