/*
 * @(#)DetailCodeSvc.java     2020-09-28(028) 오후 12:58
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
package com.vertexid.viself.code;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.vertexid.commons.utils.ParamMap;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import com.vertexid.viself.base.ModelAttribute;

/**
 * <b>Description</b>
 * <pre>
 *     상세코드 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class CodeSvc extends BaseSvc {

    private static final String NAMESPACE =
            "com.vertexid.viself.code.CodeMng";
    private String errMsg;

    @Resource(name = "cmmDAO")
    private CmmDAO cmmDAO;

    public String save(CodeDTO params) {
        String result = "";

        List<CodeDTO> list = params.getList();
        String parentCd = params.getParentCd();
        int iSize = (null == list || list.isEmpty()) ? 0 : list.size();
        if(iSize > 0) {

            for (int i = 0; i < iSize; i += 1) {
                CodeDTO tmpDTO = list.get(i);

                if (StringUtils.isNoneEmpty(tmpDTO.getCud())) {
                    tmpDTO.setOrdNo(String.valueOf(i));
                    tmpDTO.setParentCd(parentCd);

                    saveCodeInfo(tmpDTO);

                    if (log.isInfoEnabled()) {
                        log.info("Input parameters............." + tmpDTO);
                    }

                    if (ERROR_RESULT.equals(tmpDTO.getErrCd())) {
                        result = tmpDTO.getErrMsg();
                        log.error("Error.Input parameters............." + tmpDTO);
                        throw new RuntimeException(result);
                    }
                }
            }// end of for
        }// end of if

        cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "deleteVCode"),null);
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "selectVInsert"),null);

        return result;
    }

    private void saveCodeInfo(CodeDTO param) {
        if (checkData(param)) {
            cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "deleteDtlCode"),
                    param);
            cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insertDtlCode"),
                    param);
        } else {
            param.setErrCd(ERROR_RESULT);
            param.setErrMsg(errMsg);
            param.setErrYn(Boolean.toString(true));
        }
    }

    private boolean checkData(CodeDTO param) {


        if (StringUtils.isEmpty(param.getCd())) {
            errMsg = "There is no cd.";
            return false;
        }

        if (StringUtils.isEmpty(param.getLangCd())) {
            errMsg = "There is no langCd.";
            return false;
        }

        // TODO parentCd 정합성 검사

        return true;
    }

    public String delete(CodeDTO param) {
        String result = "";

        cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "deleteCode"), param);

        if (log.isInfoEnabled()) {
            log.info("Input parameters............." + param);
        }

        if (ERROR_RESULT.equals(param.getErrCd())) {
            result = param.getErrMsg();
            log.error("Error.Input parameters............." + param);
            throw new RuntimeException(result);
        }

        cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "deleteVCode"),param);
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "selectVInsert"),param);


        return result;
    }

    public List<Object> getListCdForDTO(ModelMap model, CodeDTO params) {

        return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "listCodeForDTO"),params);
    }

    public ParamMap<String, Object> getMasterP() {

        Map<String, Object> whereMap = new HashMap<String, Object>();
        whereMap.put("parentCd", "MASTER_P");

        return cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "listCode"),whereMap);
    }
}
