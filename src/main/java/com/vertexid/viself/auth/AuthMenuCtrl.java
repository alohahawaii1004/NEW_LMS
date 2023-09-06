/*
 * @(#)AuthMenuCtrl.java     2020-09-18(018) 오전 9:52
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

import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.base.BaseCtrl;
import com.vertexid.viself.base.ModelAttribute;
import com.vertexid.viself.hr.SysLoginVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.vertexid.commons.view.ViewType.JSON_VIEW;

/**
 * <b>Description</b>
 * <pre>
 *     권한에 따른 메뉴 컨트롤러
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class AuthMenuCtrl extends BaseCtrl {

    @Resource
    private AuthMenuSvc authMenuSvc;

    @Resource
    private ReloadSecurityDataFunction reloadSecurityDataFunction;

    /**
     * 권한에 따른 메뉴 조회
     *
     * @param session 세션 상태
     * @param req    request
     * @param model  모델
     * @param params 파라메터
     * @return 조회결과 정보
     */
    @RequestMapping(value = "/viself/auth/AuthMenu/list/json",
            method = RequestMethod.POST)
    public String view(HttpSession session, HttpServletRequest req,
            ModelMap model,
            @RequestParam
                    Map<String, Object> params) {

        SysLoginVO loginUser = (SysLoginVO)SessionUtils.getLoginVO();
        params.put("authCds",  loginUser.getAuthCd());
        params.put("useYn",  "1");

        log.info("authCd........\n" + loginUser.getLoginId());
        log.info("params........\n" + params);
        model.clear();

        model.addAttribute(ModelAttribute.DATA.getAttributeId(), authMenuSvc.getList(params));

        return JSON_VIEW.getViewId();
    }


    /**
     * 권한에 따른 메뉴 조회
     *
     * @param session 세션 상태
     * @param req    request
     * @param model  모델
     * @param params 파라메터
     * @return 조회결과 정보
     */
    @RequestMapping(value = "/viself/auth/AuthMenu/allList/json",
            method = RequestMethod.POST)
    public String allList(HttpSession session , HttpServletRequest req,
            ModelMap model,
            @RequestParam
                    Map<String, Object> params) {

        SysLoginVO loginUser = (SysLoginVO)SessionUtils.getLoginVO();
        params.put("authCd",  loginUser.getAuthLangCd());

        log.info("params........\n" + loginUser.getLoginId());
        log.info("params........\n" + params);

        model.clear();
        model.addAttribute(ModelAttribute.DATA.getAttributeId(), authMenuSvc.getAllList(params));

        return JSON_VIEW.getViewId();
    }

    /**
     * 권한에 따른 메뉴 저장
     *
     * @param session 세션 상태
     * @param req    request
     * @param model  모델
     * @param params 파라메터
     * @return 조회결과 정보
     */
    @RequestMapping(value = "/viself/auth/AuthMenu/insert/json",
            method = RequestMethod.POST)
    public String insert(HttpSession session , HttpServletRequest req,
            ModelMap model,
            @RequestBody
            AuthMenuDTO params) throws Exception {

//        SysLoginModel loginUser = (SysLoginModel)SessionUtils.getLoginVO();
//        params.put("loginInfo",  loginUser);

        log.info("params........\n" + params);

        // 1. 메뉴저장
        String result = authMenuSvc.insert(params);

        // 2. 권한저장소 반영(reload): 실시간 적용
        reloadSecurityDataFunction.reload();

        model.clear();
        model.addAttribute(ModelAttribute.DATA.getAttributeId(), result);

        return JSON_VIEW.getViewId();
    }

    /**
     * 권한에 따른 메뉴 저장
     *
     * @param session 세션 상태
     * @param req    request
     * @param model  모델
     * @param params 파라메터
     * @return 조회결과 정보
     */
    @RequestMapping(value = "/viself/auth/AuthMenu/save/json",
            method = RequestMethod.POST)
    public String saveData(HttpSession session , HttpServletRequest req,
            ModelMap model,
            @RequestBody
            AuthMenuDTO params) throws Exception {

//        SysLoginModel loginUser = (SysLoginModel)SessionUtils.getLoginVO();
//        params.put("loginInfo",  loginUser);

        log.info("params........\n" + params);

        // 1. 메뉴저장
        String result = authMenuSvc.saveData(params);

        // 2. 권한저장소 반영(reload): 실시간 적용
        reloadSecurityDataFunction.reload();

        model.clear();
        model.addAttribute(ModelAttribute.DATA.getAttributeId(), result);

        return JSON_VIEW.getViewId();
    }


}
