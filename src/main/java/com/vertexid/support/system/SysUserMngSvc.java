/*
 * @(#)SysUserMngSvc.java     2023-03-27 오전 10:50
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
package com.vertexid.support.system;

import com.vertexid.commons.crypto.RSACryptographicUtil;
import com.vertexid.commons.utils.ParamMap;
import com.vertexid.spring.utils.BaseProperties;
import com.vertexid.support.hr.SupportUserMngSvc;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import com.vertexid.viself.hr.SimpleUserDTO;
import com.vertexid.viself.hr.UserMngSvc;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 *     시스템 사용자 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class SysUserMngSvc extends BaseSvc {
    public static final String NAMESPACE =
            "com.vertexid.support.system.SysUserMng";

    @Resource
    CmmDAO cmmDAO;

    @Resource
    SupportUserMngSvc supportUserMngSvc;

    @Resource
    UserMngSvc userMngSvc;

    public String createSystemUser(Map<String, Object> param) {

        // 1. check param
        if (checkParam(param)) {
            // 1.1. generate User Id
            param.put("loginId", getNewUserId());

            // 1.2. create user
            createUser(param);

            // 1.3. insert sys user
            insertSupportSystemUser(param);
        }

        return null;
    }

    private void insertSupportSystemUser(Map<String, Object> param) {

        SupportSysUserDTO newUser = new SupportSysUserDTO();
        newUser.setSystemId(param.get("systemId").toString());
        newUser.setLoginId(param.get("loginId").toString());

        insertData(newUser);
    }

    private void insertData(SupportSysUserDTO param) {
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insert"), param);
    }


    private void createUser(Map<String, Object> param) {

        // 1. set user dto
        SimpleUserDTO newUser = new SimpleUserDTO();
        newUser.setLoginId(param.get("loginId").toString());
        newUser.setOrgId(param.get("orgId").toString());
        newUser.setNmKo(param.get("nmKo").toString());
        newUser.setMemo(param.get("memo").toString());
        newUser.setCert(genECert(param));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        newUser.setEncCert(encoder.encode(newUser.getCert()));

        // 2. crate user
        supportUserMngSvc.createUser(newUser);
    }

    public String genECert(Map<String, Object> param) {

        try {
            RSACryptographicUtil util = new RSACryptographicUtil();

            String sb = param.get("orgId").toString() + "," +
                    param.get("loginId").toString();
            PrivateKey privateKey = util.privateKeyFromString(
                    BaseProperties.get("security.privateKey"));
            return util.createSign(privateKey, sb);

        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    private boolean checkParam(Map<String, Object> param) {

        String msg;
        if (param.get("orgId") == null ||
                StringUtils.isBlank(param.get("orgId").toString())) {
            msg = "필수 값이 없습니다.";
            throw new RuntimeException(msg,
                    new Throwable("no orgId...." + param));
        }

        if (StringUtils.isBlank(param.get("systemId").toString())) {
            msg = "필수 값이 없습니다.";
            throw new RuntimeException(msg,
                    new Throwable("no systemId...." + param));
        }

        if (StringUtils.isBlank(param.get("nmKo").toString())) {
            msg = "필수 값이 없습니다.";
            throw new RuntimeException(msg,
                    new Throwable("no nmKo...." + param));
        }

        return true;

    }

    private String getNewUserId() {
        return cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "getNewUserId"),
                null);
    }

    public String deleteSystemUser(SupportSysUserDTO param) {
        if (checkDelParam(param)) {
            deleteData(param);
        }
        return null;
    }

    private void deleteData(SupportSysUserDTO param) {
        cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "delete"), param);
    }

    private boolean checkDelParam(SupportSysUserDTO param) {
        String msg;
        if (StringUtils.isBlank(param.getLoginId())) {
            msg = "필수 값이 없습니다.";
            throw new RuntimeException(msg,
                    new Throwable("no loginId...." + param));
        }

        if (StringUtils.isBlank(param.getSystemId())) {
            msg = "필수 값이 없습니다.";
            throw new RuntimeException(msg,
                    new Throwable("no systemId...." + param));
        }
        return true;
    }

    public String insertUsers(SupportSysUserDTO param) {
        List<SupportSysUserDTO> list = param.getList();
        for (SupportSysUserDTO userDTO : list) {
            insertData(userDTO);
        }// end of for
        return null;
    }

    public String resetECert(Map<String, Object> param) {

        String msg;
        param.put("userId", param.get("loginId"));
        ParamMap<String, Object> userInfo = userMngSvc.getData(param);
        if (userInfo == null || userInfo.isEmpty()) {
            msg = "사용자가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        SimpleUserDTO sysUser = new SimpleUserDTO();
        sysUser.setLoginId(userInfo.getString("loginId"));
        sysUser.setCert(genECert(userInfo));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        sysUser.setEncCert(encoder.encode(sysUser.getCert()));

        supportUserMngSvc.updateECert(sysUser);

        return null;
    }

    @Transactional(readOnly = true)
    public <E> List<E> getSysUserInfo(String loginId) {
        Map<String, Object> param = new HashMap<>();
        param.put("loginId", loginId);
        return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "getSysUserInfo"),
                param);
    }
}
