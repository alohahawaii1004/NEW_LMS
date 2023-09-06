/*
 * @(#)SysUserMngCtrl.java     2023-03-27 오전 10:49
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
 *     시스템 사용자 컨트롤러
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class SysUserMngCtrl extends BaseCtrl {
    @Resource
    ListPerPageFunction listPerPageFunction;

    @Resource
    PagingParamFunction pagingParamFunction;

    @Resource
    SysUserMngSvc sysUserMngSvc;

    @RequestMapping(value = "/support/system/sysUserMng/listPerPage/json",
            method = RequestMethod.POST)
    public String getListPerPage(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {

        modelMap.clear();
        SysLoginVO loingInfo = (SysLoginVO) SessionUtils.getLoginVO();
        param.put("loginInfo", loingInfo);
        pagingParamFunction.setOrderParam(param);
        listPerPageFunction.setNamespace(SysUserMngSvc.NAMESPACE);
        modelMap.addAttribute(DATA.getAttributeId(),
                listPerPageFunction.getListPerPage(param));

        return JSON_VIEW.getViewId();
    }

    @RequestMapping(value = "/support/system/sysUserMng/targetListPerPage/json",
            method = RequestMethod.POST)
    public String getTargetListPerPage(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {

        modelMap.clear();
        SysLoginVO loingInfo = (SysLoginVO) SessionUtils.getLoginVO();
        param.put("loginInfo", loingInfo);
        pagingParamFunction.setOrderParam(param);
        listPerPageFunction.setNamespace(SysUserMngSvc.NAMESPACE);
        modelMap.addAttribute(DATA.getAttributeId(),
                listPerPageFunction.getListPerPage(param,
                        "getTargetListPerPage", "countTargetData"));

        return JSON_VIEW.getViewId();
    }

    @RequestMapping(value = "/support/system/sysUserMng/create/json",
            method = RequestMethod.POST)
    public String createUser(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {
        return getTransactionJsonView(modelMap,
                sysUserMngSvc.createSystemUser(param));
    }
    //

    @RequestMapping(value = "/support/system/sysUserMng/delete/json",
            method = RequestMethod.POST)
    public String deleteUser(ModelMap modelMap,
            @RequestBody
            SupportSysUserDTO param) {
        return getTransactionJsonView(modelMap,
                sysUserMngSvc.deleteSystemUser(param));
    }

    @RequestMapping(value = "/support/system/sysUserMng/resetECert/json",
            method = RequestMethod.POST)
    public String resetECert(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {
        return getTransactionJsonView(modelMap,
                sysUserMngSvc.resetECert(param));
    }

    @RequestMapping(value = "/support/system/sysUserMng/insert/json",
            method = RequestMethod.POST)
    public String isnertUser(ModelMap modelMap,
            @RequestBody
            SupportSysUserDTO param) {
        return getTransactionJsonView(modelMap,
                sysUserMngSvc.insertUsers(param));
    }
}
