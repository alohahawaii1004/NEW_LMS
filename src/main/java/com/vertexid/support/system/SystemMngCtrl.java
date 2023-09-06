/*
 * @(#)SystemMngCtrl.java     2023-03-14 014 오후 2:07:38
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
import com.vertexid.support.customer.CustomerDTO;
import com.vertexid.support.customer.CustomerMngSvc;
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
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class SystemMngCtrl extends BaseCtrl {
    @Resource
    ListPerPageFunction listPerPageFunction;

    @Resource
    PagingParamFunction pagingParamFunction;

    @Resource
    SystemMngSvc systemMngSvc;

    @RequestMapping(value = "/support/system/systemMng/listPerPage/json",
            method = RequestMethod.POST)
    public String getListPerPage(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {

        modelMap.clear();
        SysLoginVO loingInfo = (SysLoginVO) SessionUtils.getLoginVO();
        param.put("loginInfo", loingInfo);
        pagingParamFunction.setOrderParam(param);
        listPerPageFunction.setNamespace(SystemMngSvc.NAMESPACE);
        modelMap.addAttribute(DATA.getAttributeId(),
                listPerPageFunction.getListPerPage(param));

        return JSON_VIEW.getViewId();
    }

    @RequestMapping(value = "/support/system/systemMng/saveData/json")
    public String saveCustomerInfo(ModelMap modelMap,
            @RequestBody
            SystemDTO param) {
        return getTransactionJsonView(modelMap,
                systemMngSvc.saveInfo(param));
    }

    @RequestMapping(value = "/support/cmm/system/loadData/json")
    public String getSystemInfo(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {
        modelMap.clear();
        SysLoginVO loingInfo = (SysLoginVO) SessionUtils.getLoginVO();
        param.put("loginInfo", loingInfo);

        modelMap.addAttribute(DATA.getAttributeId(),
                systemMngSvc.getSystemData(param));

        return JSON_VIEW.getViewId();
    }
}
