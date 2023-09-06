/*
 * @(#)SysMgrMngCtrl.java     2023-03-29 오전 9:08
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

import com.vertexid.mybatis.ListPerPageFunction;
import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.base.BaseCtrl;
import com.vertexid.viself.base.PagingParamFunction;
import com.vertexid.viself.hr.SysLoginVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Map;

import static com.vertexid.commons.view.ViewType.JSON_VIEW;
import static com.vertexid.viself.base.ModelAttribute.DATA;

/**
 * <b>Description</b>
 * <pre>
 *     시스템 담당자 컨트롤러
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class SysMgrMngCtrl extends BaseCtrl {

    @Resource
    ListPerPageFunction listPerPageFunction;

    @Resource
    PagingParamFunction pagingParamFunction;

    @Resource
    SysMgrMngSvc sysMgrMngSvc;

    @RequestMapping(value = "/support/system/sysMgrMng/update/json",
            method = RequestMethod.POST)
    public String updateAttribute(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {

        SysMgrDTO sysMgrDTO = new SysMgrDTO();
        sysMgrDTO.setSystemId(String.valueOf(param.get("systemId")));
        sysMgrDTO.setLoginId(String.valueOf(param.get("loginId")));

        String target = String.valueOf(param.get("target"));

        return getTransactionJsonView(modelMap,
                sysMgrMngSvc.update(target, sysMgrDTO));
    }

    @RequestMapping(value = "/support/system/sysMgrMng/delete/json",
            method = RequestMethod.POST)
    public String delete(ModelMap modelMap,
            @RequestBody
            SysMgrDTO param) {
        return getTransactionJsonView(modelMap, sysMgrMngSvc.delete(param));
    }

    @RequestMapping(value = "/support/system/sysMgrMng/listPerPage/json",
            method = RequestMethod.POST)
    public String getListPerPage(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {

        modelMap.clear();
        SysLoginVO loingInfo = (SysLoginVO) SessionUtils.getLoginVO();
        param.put("loginInfo", loingInfo);
        pagingParamFunction.setOrderParam(param);
        listPerPageFunction.setNamespace(SysMgrMngSvc.NAMESPACE);
        modelMap.addAttribute(DATA.getAttributeId(),
                listPerPageFunction.getListPerPage(param));

        return JSON_VIEW.getViewId();
    }

    //
    @RequestMapping(value = "/support/system/sysMgrMng/insert/json",
            method = RequestMethod.POST)
    public String addManagers(ModelMap modelMap,
            @RequestBody
            SysMgrDTO param) {
        return getTransactionJsonView(modelMap, sysMgrMngSvc.addManagers(param));
    }
}
