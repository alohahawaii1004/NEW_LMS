/*
 * @(#)MenuSvc.java     2020-09-18(018) 오전 10:00
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
package com.vertexid.viself.auth;

import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 *     권한관련 메뉴 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class AuthMenuSvc extends BaseSvc {

    public static final String NAMESPACE = "com.vertexid.viself.auth.AuthMenu";

    @Resource(name = "cmmDAO")
    private CmmDAO cmmDAO;

    private String errMsg;

    @Transactional(readOnly = true)
    public <E> List<E> getList(Map<String, Object> params) {

        // 1. 사용자 권한 정보 얻기

        // 2. 권한에 따른 메뉴 정보 얻기
//        params.put("useEnable", "Y");

        log.debug("PARAMS......................................" + params);

        return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "list"), params);
    }

    @Transactional(readOnly = true)
    public <E> List<E> getAllList(Map<String, Object> params) {

        // 1. 사용자 권한 정보 얻기

        // 2. 권한에 따른 메뉴 정보 얻기
//        params.put("useEnable", "Y");

        log.debug("PARAMS......................................" + params);

        return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "allList"), params);
    }

    public String insert(AuthMenuDTO params) {
        String result = "";
        String authCd = params.getAuthCd();

        if (StringUtils.isBlank(authCd)) {
            result = "권한 코드가 없습니다.";
            log.error("Error.Input parameters............." + params);
            throw new RuntimeException(result);
        }

        List<AuthMenuDTO> list = params.getList();
        int iSize = (null == list || list.isEmpty()) ? 0 : list.size();
        if (iSize > 0) {

            // 1. 해당 권한 코드관련 메뉴정보 삭제
            cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "delete"), params);

            // 2. 변경된 메뉴정보 입력
            for (int i = 0; i < iSize; i += 1) {
                AuthMenuDTO tmpDTO = list.get(i);
                tmpDTO.setAuthCd(authCd);
                cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insert"), tmpDTO);
                if (ERROR_RESULT.equals(tmpDTO.getErrCd())) {
                    result = tmpDTO.getErrMsg();
                    log.error("Error.Input parameters............." + tmpDTO);
                    throw new RuntimeException(result);
                }
            }
        }
        return result;
    }

    /**
     * 메뉴권한 삭제
     *
     * @param params
     * @return
     */
    @SuppressWarnings("ThrowableNotThrown")
    public String deleteMenu(Map<String, Object> params) {

        String result = "";
        try {
            AuthMenuDTO authMenuDTO = new AuthMenuDTO();
            authMenuDTO.setMenuId((String) params.get("menuId"));

            if (deleteCheck(authMenuDTO)) {
                authMenuDTO.setErrCd(ERROR_RESULT);
                authMenuDTO.setErrMsg(errMsg);
                authMenuDTO.setErrYn(Boolean.toString(true));
            } else {
                cmmDAO.delete(
                        cmmDAO.getStmtByNS(NAMESPACE, "deleteForMenuIdPath"),
                        authMenuDTO);
            }

            if (ERROR_RESULT.equals(authMenuDTO.getErrCd())) {
                result = authMenuDTO.getErrMsg();
                log.error("Error.Delete parameters............." + authMenuDTO);
                new Exception(result);
            }
        } catch (Exception e) {
            log.error("Error............................" + e.getMessage());
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
        }
        return result;
    }

    private boolean deleteCheck(AuthMenuDTO param) {

        // 데이터 존재여부 검사: parentMenuId
        Map<String, Object> tmpParam = new HashMap<>();
        tmpParam.put("menuId", param.getMenuId());
        List<Object> list = cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "authList"),  tmpParam);

        if (null != list && !list.isEmpty()) {
            return false;
        } else {
            errMsg = "It has sub-data.";
            return true;
        }

    }

    public void deleteMenuByAuth(AuthMenuDTO menuDTO) {
        cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "delete"), menuDTO);
    }

    public String saveData(AuthMenuDTO params) {

        String result = "";
        String authCd = params.getAuthCd();

        if (StringUtils.isBlank(authCd)) {
            result = "권한 코드가 없습니다.";
            log.error("Error.Input parameters............." + params);
            throw new RuntimeException(result);
        }

        List<AuthMenuDTO> list = params.getList();
        for(AuthMenuDTO tmpDTO: list){
            tmpDTO.setAuthCd(authCd);

            // 1. 해당 권한 코드관련 메뉴정보 삭제
            cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "delete"), tmpDTO);
            // 2. 변경된 메뉴정보 입력
            cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insert"), tmpDTO);

            if (ERROR_RESULT.equals(tmpDTO.getErrCd())) {
                result = tmpDTO.getErrMsg();
                log.error("Error.Input parameters............." + tmpDTO);
                throw new RuntimeException(result);
            }
        }// end of for

        return result;
    }
}
