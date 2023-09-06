/*
 * @(#)UserMngSvc.java     
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

package com.vertexid.viself.hr;

import com.vertexid.commons.utils.ParamMap;
import com.vertexid.spring.security.PasswordEncoder;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.vertexid.viself.base.CmmDTO.CudType.CREATE;

/**
 * 사용자 정보 관리 서비스
 */
@Service
@Transactional
public class UserMngSvc extends BaseSvc {

    private static final String NAMESPACE = "com.vertexid.viself.hr.UserMng";
    private String errMsg;

    @Resource(name = "cmmDAO")
    private CmmDAO cmmDAO;

    @Resource
    UserPwdMngSvc userPwdMngSvc;

    @Transactional(readOnly = true)
    public <T> T getData(Map<String, Object> params) {
        return cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "userData"), params);
    }

    public String save(UserDTO params) {
        String result = "";

        if (StringUtils.isNotEmpty(params.getCud())) {
            if (log.isInfoEnabled()) {
                log.info("Input parameters............." + params);
            }

            saveUserInfo(params);

            if (ERROR_RESULT.equals(params.getErrYn())) {
                result = params.getErrMsg();
                log.error("Error.Input parameters............." + params);
                throw new RuntimeException(result);
            }
        }

        return result;
    }

    private void saveUserInfo(UserDTO params) {
        if (checkData(params)) {

            if (CREATE.equals(params.getCud())) {
                // 사용자 정보 입력
                cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insert"), params);

                // 임시 PWD 입력
                userPwdMngSvc.setTempPwd(params);

            } else {
                cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "update"), params);
            }

        } else {
            params.setErrCd(ERROR_RESULT);
            params.setErrMsg(errMsg);
            params.setErrYn(Boolean.toString(true));
        }
    }

    private boolean checkData(UserDTO params) {
        if (StringUtils.isEmpty(params.getUserId())) {
            errMsg = "There is no userId.";
            return false;
        }

        if (StringUtils.isEmpty(params.getUserNm())) {
            errMsg = "There is no userNm.";
            return false;
        }

        return true;
    }

    /**
     * 사용자 비밀번호 변경
     *
     * @param params loginId, loginPwd
     * @return 메시지
     */
    @SuppressWarnings("rawtypes")
    public String changePassword(Map<String, Object> params) {

        String result = "";

        Map<String, Object> map = new HashMap<>();
        map.put("loginId", params.get("loginId"));

        ParamMap resultParam =
                cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "userData"), map);

//        SysLoginVO userInfo = null;

        if (!resultParam.isEmpty()) {
            PasswordEncoder pwEncoder = new PasswordEncoder();
            boolean isSame = pwEncoder.matches((String) params.get("loginPwd"),
                    resultParam.getString("loginPwd"));
            log.debug("PWD CHECK:" +
                    pwEncoder.matches((String) params.get("loginPwd"),
                            resultParam.getString("loginPwd")));

            if (!isSame) {
                UserDTO userDTO = new UserDTO();
                userDTO.setUserId((String) params.get("loginId"));
                userDTO.setTempPwd((String) params.get("loginPwd"));
                userDTO.setChgPwd((String) params.get("loginPwd"));
                userPwdMngSvc.setChgPwd(userDTO);

               /*cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updateTmpPwd"), userDTO);
               cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updatePwd"), userDTO);
               cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updateVsysPwd"), userDTO);*/

               /*cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "deleteVUser"), map);
                  cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "selectVInsert"), map);*/
                result = "success";
            } else {
                result = "fail";
            }

        }
        return result;

    }

}
