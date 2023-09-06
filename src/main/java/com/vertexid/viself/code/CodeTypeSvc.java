/*
 * @(#)CodeTypeSvc.java     2020-04-23 오후 3:57
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

import static com.vertexid.viself.base.CmmDTO.CudType.CREATE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;

/**
 * <b>Description</b>
 * 코드유형 서비스
 *
 * @author Yang, Ki Hwa
 */
@Transactional
@Service("codeTypeSvc")
public class CodeTypeSvc extends BaseSvc {

    private static final String NAMESPACE =
            "com.vertexid.viself.code.CodeMng";
    private String errMsg;

    @Resource(name = "cmmDAO")
    private CmmDAO cmmDAO;

    public String save(CodeTypeDTO params) {
        String result = "";

        List<CodeTypeDTO> list = params.getList();
        int iSize = (null == list || list.isEmpty()) ? 0 : list.size();

        for (int i = 0; i < iSize; i += 1) {

            CodeTypeDTO tmpDTO = list.get(i);

            if (StringUtils.isNotEmpty(tmpDTO.getCud())) {

                saveCodeTypeInfo(tmpDTO);

                if (log.isInfoEnabled()) {
                    log.info("Input parameters............." + tmpDTO);
                }

                if (ERROR_RESULT.equals(tmpDTO.getErrCd())) {
                    result = tmpDTO.getErrMsg();
                    log.error("\n    Error.Input parameters: \n    " + tmpDTO);
                    // HACK
                    // RuntimeException 을 발생시켜서 전체 Rollback 시킨다.
                    throw new RuntimeException(result);
                }
            }
        }// end of for

//        try {
//        } catch (Exception e) {
//            log.error("Error............................" + e.getMessage());
//            // 명시적 Rollback
//            TransactionAspectSupport.currentTransactionStatus()
//                    .setRollbackOnly();
//        }

        return result;
    }

    private void saveCodeTypeInfo(CodeTypeDTO param) {

        if (checkData(param)) {

            if (CREATE.equals(param.getCud())) {
                cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insertCodeType"),
                        param);
            } else {
                cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updateCodeType"),
                        param);
            }

        } else {
            param.setErrCd(ERROR_RESULT);
            param.setErrMsg(errMsg);
            param.setErrYn(Boolean.toString(true));
        }

    }

    /**
     * 입력값 검사
     *
     * @param param 입력파라메터
     * @return true: 정상, false: 오류
     */
    private boolean checkData(CodeTypeDTO param) {

        if (StringUtils.isEmpty(param.getTpId())) {
            errMsg = "There is no tpId.";
            return false;
        }

        if (StringUtils.isEmpty(param.getTpNm())) {
            errMsg = "There is no tpNm.";
            return false;
        }

        return true;
    }

    public String delete(CodeTypeDTO params) {

        String result = "";

        List<CodeTypeDTO> list = params.getList();
        int iSize = (null == list || list.isEmpty()) ? 0 : list.size();

        for (int i = 0; i < iSize; i += 1) {
            CodeTypeDTO tmpDTO = list.get(i);

            deleteCodeTypeInfo(tmpDTO);

            if (ERROR_RESULT.equals(tmpDTO.getErrCd())) {
                result = tmpDTO.getErrMsg();
                log.error("\n    Error.Input parameters: \n    " + tmpDTO);
                throw new RuntimeException(result);
            }

        }// end of for

        return result;
    }

    private void deleteCodeTypeInfo(CodeTypeDTO param) {

        if (hasChild(param)) {
            param.setErrCd(ERROR_RESULT);
            param.setErrMsg("has children data");
            param.setErrYn(Boolean.toString(true));
        } else {
            cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "deleteCodeType"),
                    param);
        }
    }

    private boolean hasChild(CodeTypeDTO param) {

        Map<String, Object> tmpParam = new HashMap<String, Object>();
        tmpParam.put("tpId", param.getTpId());
        List<Object> list =
                cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "listDtlCode"),
                        tmpParam);

        log.debug("has children..........................." + list);

        return (null != list && !list.isEmpty());
    }
}
