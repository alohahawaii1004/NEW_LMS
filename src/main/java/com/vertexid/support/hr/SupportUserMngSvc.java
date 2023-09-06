/*
 * @(#)SupportUserMngSvc.java     2023-03-14 014 오후 12:19:39
 *
 * Copyright 2023 JAYU.space
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
package com.vertexid.support.hr;

import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.auth.AuthMemberDTO;
import com.vertexid.viself.auth.AuthMemberSvc;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import com.vertexid.viself.hr.PasswordDTO;
import com.vertexid.viself.hr.SimpleUserDTO;
import com.vertexid.viself.hr.SysLoginVO;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class SupportUserMngSvc extends BaseSvc {

    public static final String NAMESPACE = "com.vertexid.support.hr.UserMng";

    public static final String BANNED_ID = "ADMIN,GUEST,MANAGER,SERVICE,SYSTEM";

    public static final String SEPARATOR = ",";
    public static final String USER = "USER";

    @Resource
    CmmDAO cmmDAO;

    @Resource
    AuthMemberSvc authMemberSvc;

    private void insertData(SimpleUserDTO param) {
        if (checkInsert(param)) {
            insert(param);
            saveAuth(param);
        }
    }

    private void saveAuth(SimpleUserDTO param) {

        String[] authCds = {};
        if (StringUtils.isNotBlank(param.getUserAuth())) {
            authCds = ArrayUtils.addAll(authCds,
                    StringUtils.split(param.getUserAuth(), SEPARATOR));
        }

//        if(StringUtils.isNotBlank(param.getAdminAuth())){
//            authCds = ArrayUtils.addAll(authCds, StringUtils.split(param.getAdminAuth(), SEPARATOR));
//        }

        authMemberSvc.saveUserAuths(makeAuthParam(authCds, param));
    }

    private AuthMemberDTO makeAuthParam(String[] authCds, SimpleUserDTO param) {

        AuthMemberDTO dto = new AuthMemberDTO();
        dto.setMbrTpCd(USER);
        dto.setMbrId(param.getLoginId());

        List<AuthMemberDTO> list = new ArrayList<>();
        for (String authCd : authCds) {
            // 기본권한(CMM_ISJ: 일반사용자) 제외
            if (!authCd.equals(CMM_ISJ.getCd())) {
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

    private int insert(SimpleUserDTO param) {
        return cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insert"), param);
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
        return true;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getUserData(SimpleUserDTO param) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", param.getLoginId());
        return getData(paramMap);
    }

    @Transactional(readOnly = true)
    public <T> T getData(Map<String, Object> params) {
        return cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "userData"), params);
    }

    private boolean isExist(SimpleUserDTO param) {
        Map<String, Object> data = getUserData(param);
        return (null != data && !data.isEmpty());
    }

    public String createUser(SimpleUserDTO param) {
        if (StringUtils.isBlank(param.getLoginId())) {
            String msg = "LOGIN_ID 값이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        String msg;
        if (isExist(param)) {
            msg = "이미 아이디가 존재합니다.";
            return msg;
        }

        insertData(param);
        return null;
    }

    public String updateUser(SimpleUserDTO param) {
        if (StringUtils.isBlank(param.getLoginId())) {
            String msg = "LOGIN_ID 값이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        String msg;
        if (!isExist(param)) {
            msg = "사용자가 존재하지 않습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        updateData(param);
        return null;
    }

    private void updateData(SimpleUserDTO param) {
        if (checkInsert(param)) {
            update(param);
            saveAuth(param);
        }
    }

    private int update(SimpleUserDTO param) {
        return cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "update"), param);
    }

    public String resetPassword(SimpleUserDTO simpleUserDTO) {
        return null;
    }

    public String updatePassword(PasswordDTO param) {

        if (chkChPwd(param)) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            param.setNewPwd(encoder.encode(param.getNewPwd()));
            updatePwd(param);
        }

        return null;
    }

    private boolean chkChPwd(PasswordDTO param) {
        String msg;
        if (StringUtils.isEmpty(param.getLoginId())) {
            msg = "대상 사용자가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }
        if (StringUtils.isEmpty(param.getNewPwd())) {
            msg = "신규 암호가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }
        if (StringUtils.isEmpty(param.getCfmPwd())) {
            msg = "확인 암호가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }
        if (!(param.getNewPwd()).equals(param.getCfmPwd())) {
            msg = "암호가 올바르지 않습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        SysLoginVO loingInfo = (SysLoginVO) SessionUtils.getLoginVO();
        if (loingInfo == null) {
            msg = "로그인 정보가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }
        if (!(param.getLoginId()).equals(loingInfo.getLoginId()) &&
                !loingInfo.containsAuthorities("CMM_SYS,SM_ADMIN")) {
            msg = "권한이 올바르지 않습니다.";
            log.error(msg);
            throw new RuntimeException(msg,
                    new Throwable("loginInfo...." + loingInfo));
        }

        return true;
    }

    private void updatePwd(PasswordDTO param) {
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updatePwd"), param);
    }

    public void updateECert(SimpleUserDTO param) {
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updateECert"), param);
    }
}
