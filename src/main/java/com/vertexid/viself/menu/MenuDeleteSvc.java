/*
 * @(#)MenuDeleteSvc.java     2020-09-28(028) 오후 3:24
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

import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;

/**
 * <b>Description</b>
 * <pre>
 *     메뉴삭제 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class MenuDeleteSvc extends BaseSvc {

    private static final String NAMESPACE =
            "com.vertexid.viself.menu.MenuMng";
    private static final String ROOT_NODE = "main";
    private static final String MENU_NODE = "menu";

    private String errMsg;

    @Resource
    private CmmDAO cmmDAO;

    public void delete(MenuDTO param) {
        if(check(param)){
            param.setErrCd(ERROR_RESULT);
            param.setErrMsg(errMsg);
            param.setErrYn(Boolean.toString(true));
        }else{
            cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "delete"), param);
        }
    }

    private boolean check(MenuDTO param) {

        if (ROOT_NODE.equals(param.getMenuId()) ||
                MENU_NODE.equals(param.getMenuId())) {
            errMsg = "ROOT NODE or MENU ROOT NODE cannot be deleted.";
            return true;
        }

        // 데이터 존재여부 검사: parentMenuId
        Map<String, Object> tmpParam = new HashMap<String, Object>();
        tmpParam.put("parentMenuId", param.getMenuId());
        List<Object> list =
                cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "list"), tmpParam);

        if(null != list && !list.isEmpty() ){
            errMsg = "It has sub-data.";
            return true;
        }

        return false;
    }
}
