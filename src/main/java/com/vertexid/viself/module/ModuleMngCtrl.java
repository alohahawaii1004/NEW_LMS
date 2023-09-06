/*
 * @(#)ModuleMngCtrl.java     2021-03-16(016) 오후 2:03
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

import com.vertexid.viself.base.BaseCtrl;
import com.vertexid.viself.security.SimpleReloadableSecurityMetadataSource;
import com.vertexid.viself.security.SimpleSecureObjectSvc;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.vertexid.commons.view.ViewType.JSON_VIEW;
import static com.vertexid.viself.base.MessageCode.COMPLETE;
import static com.vertexid.viself.base.MessageCode.ERROR;
import static com.vertexid.viself.base.ModelAttribute.ERROR_FLAG;
import static com.vertexid.viself.base.ModelAttribute.MSG;

/**
 * <b>Description</b>
 * <pre>
 *     모듈 관리 컨트롤러
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class ModuleMngCtrl extends BaseCtrl {

    @Resource
    private ModuleMngSvc moduleMngSvc;

    @Resource
    private SimpleSecureObjectSvc simpleSecureObjectSvc;

    @Resource
    private SimpleReloadableSecurityMetadataSource
            simpleReloadableSecurityMetadataSource;

    @RequestMapping(value = "/viself/module/moduleMng/save/json",
            method = RequestMethod.POST)
    public String saveModule(ModelMap model, HttpServletRequest req,
                    ModuleDTO params) {

        model.clear();

        String result = moduleMngSvc.save(params);

        model.addAttribute(ERROR_FLAG.getAttributeId(), ERROR_RESULT);

        if (StringUtils.isEmpty(result)) {
            result = COMPLETE.getMsgCode();
            model.addAttribute(ERROR_FLAG.getAttributeId(), COMPLETE_RESULT);
        } else if (isProd()) {
            result = ERROR.getMsgCode();
        }

        model.addAttribute(MSG.getAttributeId(), result);

        return JSON_VIEW.getViewId();
    }

    @RequestMapping(value = "/viself/module/moduleMng/delete/json",
            method = RequestMethod.POST)
    public String deleteModule(ModelMap model,
            @RequestBody
                    ModuleDTO params) throws Exception {
        model.clear();

        String result = moduleMngSvc.delete(params);

        model.addAttribute(ERROR_FLAG.getAttributeId(), ERROR_RESULT);

        if (StringUtils.isEmpty(result)) {
            result = COMPLETE.getMsgCode();
            model.addAttribute(ERROR_FLAG.getAttributeId(), COMPLETE_RESULT);
        } else if (isProd()) {
            result = ERROR.getMsgCode();
        }

        model.addAttribute(MSG.getAttributeId(), result);

        // reload security
        reloadSecurity();

        return JSON_VIEW.getViewId();
    }

    private void reloadSecurity() throws Exception {
        // reload
        simpleReloadableSecurityMetadataSource.setSecureObjectSvc(simpleSecureObjectSvc);
        simpleReloadableSecurityMetadataSource.reload();
    }

    @RequestMapping(value = "/viself/module/moduleMng/saveModuleUrl/json",
            method = RequestMethod.POST)
    public String saveModuleUrl(ModelMap model, HttpServletRequest req,
            @RequestBody
                    ModuleUrlDTO params) throws Exception {
        model.clear();

        String result = moduleMngSvc.saveUrl(params);

        model.addAttribute(ERROR_FLAG.getAttributeId(), ERROR_RESULT);

        if (StringUtils.isEmpty(result)) {
            result = COMPLETE.getMsgCode();
            model.addAttribute(ERROR_FLAG.getAttributeId(), COMPLETE_RESULT);
        } else if (isProd()) {
            result = ERROR.getMsgCode();
        }

        model.addAttribute(MSG.getAttributeId(), result);

        // reload security
        reloadSecurity();

        return JSON_VIEW.getViewId();
    }

    @RequestMapping(value = "/viself/module/moduleMng/deleteModuleUrls/json",
            method = RequestMethod.POST)
    public String deleteModuleUrls(ModelMap model, @RequestBody
            ModuleUrlDTO params) throws Exception {
        model.clear();

        String result = moduleMngSvc.deleteUrls(params);

        model.addAttribute(ERROR_FLAG.getAttributeId(), ERROR_RESULT);

        if (StringUtils.isEmpty(result)) {
            result = COMPLETE.getMsgCode();
            model.addAttribute(ERROR_FLAG.getAttributeId(), COMPLETE_RESULT);
        } else if (isProd()) {
            result = ERROR.getMsgCode();
        }

        model.addAttribute(MSG.getAttributeId(), result);

        // reload security
        reloadSecurity();

        return JSON_VIEW.getViewId();
    }

}
