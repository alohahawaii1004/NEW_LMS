/*
 * @(#)SupportStatsCtrl.java     2023-06-09(009) 오전 11:06
 *
 * Copyright 2023 JaYu.space
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
package com.vertexid.support.stats;

import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.base.BaseCtrl;
import com.vertexid.viself.hr.SysLoginVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
public class SupportStatsCtrl extends BaseCtrl {

    @Resource
    SupportStatsSvc supportStatsSvc;

    /**
     * 담당자별 현황
     *
     * @param modelMap model
     * @param param    parameters
     * @return view
     */
    @RequestMapping(value = "/support/stats/manager/json",
            method = RequestMethod.POST)
    public String getManagerStats(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {

        modelMap.clear();
        SysLoginVO loingInfo = (SysLoginVO) SessionUtils.getLoginVO();
        param.put("loginInfo", loingInfo);
        modelMap.addAttribute(DATA.getAttributeId(),
                supportStatsSvc.getManagerStats(param));

        return JSON_VIEW.getViewId();
    }

    /**
     * 시스템별 현황
     *
     * @param modelMap model
     * @param param    parameters
     * @return view
     */
    @RequestMapping(value = "/support/stats/system/json",
            method = RequestMethod.POST)
    public String getSystemStats(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {

        modelMap.clear();
        SysLoginVO loingInfo = (SysLoginVO) SessionUtils.getLoginVO();
        param.put("loginInfo", loingInfo);
        modelMap.addAttribute(DATA.getAttributeId(),
                supportStatsSvc.getSystemStats(param));

        return JSON_VIEW.getViewId();
    }

    /**
     * 유형별 현황
     *
     * @param modelMap model
     * @param param    parameters
     * @return view
     */
    @RequestMapping(value = "/support/stats/issueTp/json",
            method = RequestMethod.POST)
    public String getIssueTpStats(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {

        modelMap.clear();
        SysLoginVO loingInfo = (SysLoginVO) SessionUtils.getLoginVO();
        param.put("loginInfo", loingInfo);
        modelMap.addAttribute(DATA.getAttributeId(),
                supportStatsSvc.getIssueTpStats(param));

        return JSON_VIEW.getViewId();
    }

}
