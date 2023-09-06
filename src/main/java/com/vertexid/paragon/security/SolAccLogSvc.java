/*
 * @(#)LmsAccLogSvc.java     2022-07-25
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
package com.vertexid.paragon.security;

import com.vertexid.commons.utils.IDUtils;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import com.vertexid.viself.hr.SysLoginVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <b>Description</b>
 * <pre>
 *     솔루션(마스터 컨텐츠) 열람로그 Service
 * </pre>
 *
 * @author SEOYOON
 */
@Service
@Transactional
public class SolAccLogSvc extends BaseSvc {
    public static final String NAMESPACE = "com.vertexid.paragon.log.SolAccLog";

    @Resource
    CmmDAO cmmDAO;

    public String saveData(SolAccLogDTO param) {

            // insert
            insertData(param);
        
        return null;
    }


    private void insertData(SolAccLogDTO param) {
        if(checkInsert(param)){
            insert(param);
        }
    }

    private void insert(SolAccLogDTO param) {
    	
    	
    	param.setLogUid(IDUtils.getRandomUUID());
    	
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insert"), param);
    }

    private boolean checkInsert(SolAccLogDTO param) {
        String msg;

        //계약 : 법무관리자(LMS_BCD) 법무담당자(LMS_BJD) 계약열람담당자()
        //자문 : 법무관리자(LMS_BCD) 법무담당자(LMS_BJD) 자문열람담당자()
        //협의체 : 법무관리자(LMS_BCD) 법무담당자(LMS_BJD)
        SysLoginVO loginVO = (SysLoginVO)param.getLoginInfo();
        if(null == loginVO){
            msg = "작성자 정보가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg, new Throwable("loginVO 값이 없습니다."));
        }

        return true;
    }



}
