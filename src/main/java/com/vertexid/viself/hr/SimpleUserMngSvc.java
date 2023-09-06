/*
 * @(#)SimpleUserMngSvc.java     2022-07-26(026) 오전 10:33:20
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
package com.vertexid.viself.hr;

import com.vertexid.viself.auth.AuthMemberDTO;
import com.vertexid.viself.auth.AuthMemberSvc;
import com.vertexid.viself.base.CmmDAO;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vertexid.viself.auth.BaseRoleType.CMM_ISJ;

/**
 * <b>Description</b>
 * <pre>
 *     사용자 관리 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class SimpleUserMngSvc extends UserMngSvc {

    public static final String NAMESPACE = "com.vertexid.viself.hr.SimpleUserMng";

    public static final String BANNED_ID = "ADMIN,GUEST,MANAGER,SERVICE,SYSTEM";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String HR_USER = "00";
    public static final String NOT_HR_USER = "99";
    public static final String SEPARATOR = ",";
    public static final String USER = "USER";

    @Resource
    CmmDAO cmmDAO;

    @Resource
    AuthMemberSvc authMemberSvc;

    public String saveData(SimpleUserDTO param) {
        // simple check
        if (StringUtils.isBlank(param.getLoginId())) {
            String msg = "LOGIN_ID 값이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        Map<String, Object> data = getUserData(param);
        if (null == data || data.isEmpty()) {
            param.setUserTpCd(NOT_HR_USER);
            insertData(param);
        } else {
            if(HR_USER.equals(data.get("userTpCd"))){
                String msg = "인사정보 사용자 입니다.";
                log.error(msg);
                throw new RuntimeException(msg);
            }
            param.setUserTpCd(NOT_HR_USER);
            updateData(param, NOT_HR_USER);
        }

        return null;
    }

    public String updateHRData(SimpleUserDTO param) {
        // simple check
        if (StringUtils.isBlank(param.getLoginId())) {
            String msg = "LOGIN_ID 값이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        Map<String, Object> data = getUserData(param);
        if (null == data || data.isEmpty()) {
            String msg = "사용자가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        if(!HR_USER.equals(data.get("userTpCd"))){
            String msg = "인사정보 사용자가 아닙니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        param.setUserTpCd(HR_USER);
        updateData(param, HR_USER);

        return null;
    }

    private void updateData(SimpleUserDTO param, String userTpCd) {
        if(checkUpdate(param, userTpCd)){
            if(!HR_USER.equals(userTpCd)){ // 인사정보 사용자는 권한외에 업데이트 할 수 없다.
                update(param);
            }
            saveAuth(param);
            updateUserView(param);
        }
    }

    private void update(SimpleUserDTO param) {
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "update"), param);
    }

    private boolean checkUpdate(SimpleUserDTO param, String userTpCd) {
        String msg;
        if(HR_USER.equals(userTpCd)){
            if (StringUtils.isBlank(param.getLoginId())) {
                msg = "필수 값이 없습니다.";
                log.error(msg);
                throw new RuntimeException(msg, new Throwable("LOGIN_ID 값이 없습니다."));
            }
            if (StringUtils.indexOfIgnoreCase(BANNED_ID, param.getLoginId()) !=
                    -1) {
                msg = "올바른 값이 아닙니다.";
                log.error(msg);
                throw new RuntimeException(msg, new Throwable(
                        "LOGIN_ID 로 사용할 수 없습니다.:" + param.getLoginId()));
            }
            if (StringUtils.isBlank(param.getNmKo())) {
                msg = "필수 값이 없습니다.";
                log.error(msg);
                throw new RuntimeException(msg, new Throwable("NM_KO 값이 없습니다."));
            }
        }else{
            return checkInsert(param);
        }
        return true;
    }

    private void insertData(SimpleUserDTO param) {
        if (checkInsert(param)) {
            insert(param);
            saveAuth(param);
            updateUserView(param);
        }
    }

    private void saveAuth(SimpleUserDTO param) {

        String [] authCds = {};
        if(StringUtils.isNotBlank(param.getUserAuth())){
            authCds = ArrayUtils.addAll(authCds, StringUtils.split(param.getUserAuth(), SEPARATOR));
        }

        if(StringUtils.isNotBlank(param.getAdminAuth())){
            authCds = ArrayUtils.addAll(authCds, StringUtils.split(param.getAdminAuth(), SEPARATOR));
        }

        authMemberSvc.saveUserAuths(makeAuthParam(authCds, param));
    }

    private AuthMemberDTO makeAuthParam(String[] authCds, SimpleUserDTO param) {

        AuthMemberDTO dto = new AuthMemberDTO();
        dto.setMbrTpCd(USER);
        dto.setMbrId(param.getLoginId());

        List<AuthMemberDTO> list = new ArrayList<>();
        for(String authCd: authCds){
            // 기본권한(CMM_ISJ: 일반사용자) 제외
            if(!authCd.equals(CMM_ISJ.getCd())){
                AuthMemberDTO tmpDto = new AuthMemberDTO();
                tmpDto.setAuthCd(authCd);
                tmpDto.setMbrTpCd(USER);
                tmpDto.setMbrId(param.getLoginId());
                tmpDto.setUserNm(param.getNmKo());

                list.add(tmpDto);
            }
        }
        dto.setList(list);
        return dto;
    }

    private void updateUserView(SimpleUserDTO param) {
        cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "deleteVUser"), param);
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "selectVInsert"), param);
    }

    private void insert(SimpleUserDTO param) {
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insert"), param);
    }

    private boolean checkInsert(SimpleUserDTO param) {
        String msg;
        if (StringUtils.isBlank(param.getLoginId())) {
            msg = "필수 값이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg, new Throwable("LOGIN_ID 값이 없습니다."));
        }
        if (StringUtils.indexOfIgnoreCase(BANNED_ID, param.getLoginId()) !=
                -1) {
            msg = "올바른 값이 아닙니다.";
            log.error(msg);
            throw new RuntimeException(msg, new Throwable(
                    "LOGIN_ID 로 사용할 수 없습니다.:" + param.getLoginId()));
        }
        if (StringUtils.isBlank(param.getNmKo())) {
            msg = "필수 값이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg, new Throwable("NM_KO 값이 없습니다."));
        }
        if (StringUtils.isBlank(param.getEmail())) {
            msg = "필수 값이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg, new Throwable("EMAIL 값이 없습니다."));
        }
        if (StringUtils.isBlank(param.getUserAuth())) {
            msg = "필수 값이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg, new Throwable("권한정보가 없습니다."));
        }
        if (StringUtils.isBlank(param.getUseStDte()) &&
                StringUtils.isBlank(param.getUseEdDte())) {
            msg = "필수 값이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg, new Throwable("사용기간 값이 없습니다."));
        }

        if (StringUtils.isNotBlank(param.getUseStDte()) &&
                StringUtils.isNotBlank(param.getUseEdDte())) {
            DateTime stDte = DateTimeFormat.forPattern(DATE_FORMAT)
                    .parseDateTime(param.getUseStDte());
            DateTime edDte = DateTimeFormat.forPattern(DATE_FORMAT)
                    .parseDateTime(param.getUseEdDte());

            Days checkDays = Days.daysBetween(stDte, edDte);
            if (checkDays.getDays() < 0) {
                msg = "올바른 값이 아닙니다.";
                log.error(msg);
                throw new RuntimeException(msg,
                        new Throwable("사용기간이 올바르지 않습니다.:" + param));
            }
        }

        return true;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getUserData(SimpleUserDTO param) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", param.getLoginId());
        return getData(paramMap);
    }

    @Override
    @Transactional(readOnly = true)
    public <T> T getData(Map<String, Object> params) {
        return cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "userData"), params);
    }
}
