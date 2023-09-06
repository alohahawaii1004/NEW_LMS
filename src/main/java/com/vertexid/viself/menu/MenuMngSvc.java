/*
 * @(#)MenuMngSvc.java     2020-09-16(016) 오후 1:08
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
package com.vertexid.viself.menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import org.apache.commons.lang3.StringUtils;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;

/**
 * <b>Description</b>
 * <pre>
 *     메뉴 관리 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class MenuMngSvc extends BaseSvc {

    private static final String NAMESPACE =
            "com.vertexid.viself.menu.MenuMng";

    @Resource(name = "cmmDAO")
    private CmmDAO cmmDAO;

    private String errMsg;

    @Transactional(readOnly = true)
    public <E> List<E> getList(Map<String, Object> params) {

        // 1. 사용자 권한 정보 얻기

        // 2. 권한에 따른 메뉴 정보 얻기
//        params.put("useEnable", "Y");

        log.debug("PARAMS......................................"+params);

        return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "list"), params);
    }

    public String save(MenuDTO params) {
        String result = "";

        List<MenuDTO> list = params.getList();

        int iSize = (null == list || list.isEmpty()) ? 0 : list.size();
        String parentCd = params.getParentMenuId();
        boolean isOneLv = false;
        if(StringUtils.isBlank(parentCd)) {
            isOneLv = true;
        }

        try {
            if(iSize > 1 && !isOneLv) {    //-- List가 1개 이상일떄는 부모코드 기준으로 삭제

                //-- 선 삭제 코드
//                cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "deleteForParent"), params);
            }

            for (int i = 0; i < iSize; i += 1) {
                MenuDTO tmpDTO = list.get(i);

//                if(iSize == 1 && !isOneLv) {    //-- List가 1개 개별 수정 모드 이므로 1개만 지운다
//                    cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "delete"), tmpDTO);
//                }
                tmpDTO.setParentMenuId(params.getParentMenuId());
                tmpDTO.setOrdNo(String.valueOf(i));
                if("C".equals(tmpDTO.getCud())) {
                    cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insert"), tmpDTO);
                }else {
                    cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "update"), tmpDTO);
                }

                if (ERROR_RESULT.equals(tmpDTO.getErrCd())) {
                    result = tmpDTO.getErrMsg();
                    log.error("ERROR.INPUT........." + tmpDTO);

                    // DBMS상의 오류가 아닌 프로세스상 강제로 오류 발생
                    throw new RuntimeException(result);
                }

            }// end of for

        } catch (Exception e) {
            log.error("ERROR........................." + e.getMessage());

            // 프로세스상 강제로 오류에 의한 rollback
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
        }

        return result;
    }

    /**
     * 메뉴 삭제
     * @param params
     * @return
     */
    public String deleteAll(Map<String, Object> params) {
        String result = "";

        try{
            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setMenuId((String)params.get("menuId"));

            if(deleteCheck(menuDTO)) {
                menuDTO.setErrCd(ERROR_RESULT);
                menuDTO.setErrMsg(errMsg);
                menuDTO.setErrYn(Boolean.toString(true));
            }else {
                cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "deleteForPath"), menuDTO);
            }

            if (ERROR_RESULT.equals(menuDTO.getErrCd())) {
                result = menuDTO.getErrMsg();
                log.error(
                        "Error.Input parameters............." + menuDTO);
                new Exception(result);
            }

        } catch (Exception e) {
            log.error("Error............................" + e.getMessage());
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
        }

        return result;
    }

    @Transactional(readOnly = true)
    <E> List<E> getTreeMenu(Map<String, Object> params) {
        return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "getTreeList"), params);
    }

    private boolean deleteCheck(MenuDTO param) {

        // 데이터 존재여부 검사: menuId
        Map<String, Object> tmpParam = new HashMap<String, Object>();
        tmpParam.put("menuId", param.getMenuId());
        List<Object> list =
                cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "allList"), tmpParam);

        if(null != list && !list.isEmpty() ){
            return false;
        }else {
            errMsg = "It has sub-data.";
            return true;
        }

    }

}
