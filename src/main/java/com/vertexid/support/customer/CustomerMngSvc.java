/*
 * @(#)CustomerMngSvc.java     2023-03-13 013 오전 9:56:46
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
package com.vertexid.support.customer;

import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class CustomerMngSvc extends BaseSvc {

    public static final String NAMESPACE =
            "com.vertexid.support.customer.CustomerMng";

    @Resource
    CmmDAO cmmDAO;

    public String saveInfo(CustomerDTO param) {
        String msg = "";

        if(isExist(param)){
            updateData(param);
        }else{
            insertData(param);
        }

        return msg;
    }

    private void insertData(CustomerDTO param) {

        if(StringUtils.isEmpty(param.getOrgId())){
            param.setOrgId(newOrgId());
        }
        if(isValidate(param)){
            insertMas(param);
            saveMeta(param);
            updateMV(param);
        }
    }

    private boolean isValidate(CustomerDTO param) {
        String msg;
        if(StringUtils.isEmpty(param.getOrgId())){
            msg = "orgId 가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        if(StringUtils.isEmpty(param.getNmKo())){
            msg = "org 이름이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }
        return true;
    }

    private String newOrgId() {
        return cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "getNewOrgId"), null);
    }

    public void updateData(CustomerDTO param) {
        if(isValidate(param)) {
            updateMas(param);
            saveMeta(param);
            updateMV(param);
        }
    }

    private void updateMV(CustomerDTO param) {
        cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "deleteVOrg"), null);
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insertVOrg"), null);
    }

    public void saveMeta(CustomerDTO param){
        if(isExistMeta(param)){
            updateMeta(param);
        }else{
            insertMeta(param);
        }
    }

    private void insertMeta(CustomerDTO param) {
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insertMeta"), param);
    }

    private void updateMeta(CustomerDTO param) {
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updateMeta"), param);
    }

    private boolean isExistMeta(CustomerDTO param) {
        Map<String, Object> data = getMetaData(param);
        return (data != null && !data.isEmpty());
    }

    private boolean isExist(CustomerDTO param) {
        Map<String, Object> data = getData(param);
        return (data != null && !data.isEmpty());
    }

    @Transactional(readOnly = true)
    public <T> T getMetaData(CustomerDTO param) {
        return cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "getMetaData"), param);
    }

    private void updateMas(CustomerDTO param) {
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updateMas"), param);
    }

    private void insertMas(CustomerDTO param) {
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insertMas"), param);
    }

    @Transactional(readOnly = true)
    public <T> T getData(CustomerDTO param){
        return cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "loadData"), param);
    }
}
