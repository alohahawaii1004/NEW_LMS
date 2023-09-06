/*
 * @(#)SystemMngSvc.java     2023-03-14 014 오후 2:08:47
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

import com.vertexid.commons.utils.ParamMap;
import com.vertexid.support.customer.CustomerDTO;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
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
public class SystemMngSvc extends BaseSvc {
    public static final String NAMESPACE =
            "com.vertexid.support.system.SystemMng";

    @Resource
    CmmDAO cmmDAO;

    public Object getSystemData(Map<String, Object> param) {
        List<ParamMap<String, Object>> list = getUseSystemList(param);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 사용자로 등록 중인 시스템 갯수를 구한다.
     *
     * @param param
     * @return
     */
    private <E> List<E> getUseSystemList(Map<String, Object> param) {
        return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "getUseSystemList"),
                param);
    }

    public String saveInfo(SystemDTO param) {
        String msg = "";

        if (isExist(param)) {
            updateData(param);
        } else {
            insertData(param);
        }

        return msg;
    }

    private void updateData(SystemDTO param) {
        if (isValidate(param)) {
            cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updateMas"), param);
        }
    }

    private void insertData(SystemDTO param) {
        param.setSystemId(newSystemId());
        if (isValidate(param)) {
            cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insertMas"), param);
        }
    }

    private boolean isValidate(SystemDTO param) {
        String msg;
        if (StringUtils.isEmpty(param.getOrgId())) {
            msg = "orgId 가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        if (StringUtils.isEmpty(param.getSystemId())) {
            msg = "systemId 가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        if (StringUtils.isEmpty(param.getSystemName())) {
            msg = "system 이름이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        if (StringUtils.isEmpty(param.getProductTpCd())) {
            msg = "productTpCd 가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        if (StringUtils.isEmpty(param.getSmStartdt())) {
            msg = "SmStartdt 가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        if (StringUtils.isEmpty(param.getSmEnddt())) {
            msg = "SmEnddt 가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        return true;
    }

    private String newSystemId() {
        return cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "getNewSystemId"),
                null);
    }

    private boolean isExist(SystemDTO param) {
        if (StringUtils.isEmpty(param.getSystemId())) {
//            String msg = "필수 파라메터가 없습니다.";
//            log.error(msg);
//            throw new RuntimeException(msg);
            return false;
        }

        Map<String, Object> data = getData(param);
        return (data != null && !data.isEmpty());
    }

    @Transactional(readOnly = true)
    public <T> T getData(SystemDTO param) {
        return cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "loadData"), param);
    }
}
