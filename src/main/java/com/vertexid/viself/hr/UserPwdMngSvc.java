/*
 * @(#)UserPwdMngSvc.java     2020-10-27(027) 오전 9:28
 *
 * Copyright 2020 JAYU.space
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

import javax.annotation.Resource;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <b>Description</b>
 * <pre>
 *     사용자 패스워드 관리 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class UserPwdMngSvc extends BaseSvc {

    private static final String NAMESPACE = "com.vertexid.viself.hr.UserMng";
    private static final String POST_FIX = "!@#1234";

    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    String today = format.format(new Date());

    private String SIM_POST_FIX = today;

    private String errMsg;

    @Resource(name = "cmmDAO")
    private CmmDAO cmmDAO;

    public void setTempPwd(UserDTO params){
        // 1. generate temp pwd
        params.setTempPwd(getTempPwd(params));
        params.setChgPwd(getTempPwd(params));

        // 2. update temp pwd
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updateTmpPwd"), params);
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updateVsysPwd"), params);
    }

    private String getTempPwd(UserDTO params) {
        String orgTmpPwd = params.getUserId() + POST_FIX;
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(orgTmpPwd);
    }
    
    public void setChgPwd(UserDTO params){
        // 1. generate temp pwd
        params.setChgPwd(getChgPwd(params));

        // 2. update temp pwd
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updatePwd"), params);
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updateVsysPwd"), params);
    }

    private String getChgPwd(UserDTO params) {
        String chgPwd = params.getChgPwd();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(chgPwd);
    }
    
//    /********************************** 심의위원 암호설정 시작 **********************************/
    /**
     * @deprecated  추후 삭제 or 대체 예정
     */
    public void setTempPpPwd(UserDTO params){
        // 1. generate temp pwd
        params.setTempPwd(getTempPpPwd(params));
        params.setChgPwd(getTempPpPwd(params));

        // 2. update temp pwd
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updatePwd"), params);
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updateVsysPwd"), params);
    }

    /**
     * @deprecated  추후 삭제 or 대체 예정
     */
    private String getTempPpPwd(UserDTO params) {
    	String orgTmpPwd = "";
    	if("D".equals(params.getCud())) {
    		orgTmpPwd = POST_FIX;
    	}else {
    		orgTmpPwd = params.getUserId()+SIM_POST_FIX;
    	}
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(orgTmpPwd);
    }

    /**
     * @deprecated  추후 삭제 or 대체 예정
     */
    //심의위원 암호 기한설정
    public void setTermPpPwd(UserDTO params){

        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updatePwdTerm"), params);
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updateVsysPwdTerm"), params);
    }
    
//    /********************************** 심의위원 암호설정 종료 **********************************/
}
