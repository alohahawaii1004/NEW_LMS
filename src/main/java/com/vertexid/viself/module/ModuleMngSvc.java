/*
 * @(#)ModuleMngSvc.java     2021-03-16(016) 오후 2:33
 *
 * Copyright 2021 JAYU.space
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
package com.vertexid.viself.module;

import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import com.vertexid.viself.base.CmmDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.vertexid.viself.base.CmmDTO.CudType.CREATE;
import static com.vertexid.viself.base.CmmDTO.CudType.UPDATE;

/**
 * <b>Description</b>
 * <pre>
 *     모듈 관리 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class ModuleMngSvc extends BaseSvc {

    private static final String NAMESPACE = "com.vertexid.viself.module.ModuleMng";
    private String errMsg;

    @Resource(name = "cmmDAO")
    private CmmDAO cmmDAO;

    public String save(ModuleDTO params) {
        String result = "";

        if(log.isInfoEnabled()){
            log.info("Input parameters............." + params);
        }

        if (CREATE.equals(params.getCud())) {
            insert(params);
        }else if(UPDATE.equals(params.getCud())){
            update(params);
        }

        if (ERROR_RESULT.equals(params.getErrYn())) {
            result = params.getErrMsg();
            log.error("Error.Input parameters............." + params);
            throw new RuntimeException(result);
        }

        return result;
    }

    private void update(ModuleDTO params) {
        if(validate(params)){
            cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updateModule"), params);
        }
    }

    private void insert(ModuleDTO params) {
        if(validate(params)){
            cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insertModule"), params);
        }
    }

    private void setError(CmmDTO params) {
        params.setErrCd(ERROR_RESULT);
        params.setErrMsg(errMsg);
        params.setErrYn(Boolean.toString(true));
    }

    private boolean validate(ModuleDTO params) {
        if (StringUtils.isEmpty(params.getModuleId())) {
            errMsg = "There is no module Id";
            setError(params);
            return false;
        }

        return true;
    }

    public String saveUrl(ModuleUrlDTO params) {
        String result = "";

        if(log.isInfoEnabled()){
            log.info("Input parameters............." + params);
        }

        if (CREATE.equals(params.getCud())) {
            insertUrl(params);
        }else if(UPDATE.equals(params.getCud())){
            updateUrl(params);
        }

        if (ERROR_RESULT.equals(params.getErrYn())) {
            result = params.getErrMsg();
            log.error("Error.Input parameters............." + params);
            throw new RuntimeException(result);
        }

        return result;
    }

    private void updateUrl(ModuleUrlDTO params) {
        // reset master url
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "udpateResetDefUrl"), params);

        // set master url
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "udpateDefaultUrl"), params);
    }

    private void insertUrl(ModuleUrlDTO params) {

        List<ModuleUrlDTO> list = params.getList();
        int iLen = (list != null) ? list.size() : 0;

        for(int i = 0; i < iLen; i += 1){
            ModuleUrlDTO tmpDTO = list.get(i);
            tmpDTO.setModuleId(params.getModuleId());
            cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insertUrl"), tmpDTO);
        }// end of for
    }

    public String delete(ModuleDTO params) {
        String result = "";

        if(log.isInfoEnabled()){
            log.info("Input parameters............." + params);
        }

        if(validate(params)){

            // delete url
            ModuleUrlDTO tmpDTO = new ModuleUrlDTO();
            tmpDTO.setModuleId(params.getModuleId());
            deleteUrl(tmpDTO);

            // delete module
            cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "deleteModule"), params);
        }

        return result;
    }

    public String deleteUrls(ModuleUrlDTO params) {

        String result = "";

        if(log.isInfoEnabled()){
            log.info("Input parameters............." + params);
        }

        List<ModuleUrlDTO> list = params.getList();
        int iLen = (list != null) ? list.size() : 0;

        for(int i = 0; i < iLen; i += 1){
            ModuleUrlDTO tmpDTO = list.get(i);
            tmpDTO.setModuleId(params.getModuleId());
            deleteUrl(tmpDTO);
        }// end of for

        return result;

    }

    private void deleteUrl(ModuleUrlDTO param){
        cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "deleteUrl"), param);
    }
}
