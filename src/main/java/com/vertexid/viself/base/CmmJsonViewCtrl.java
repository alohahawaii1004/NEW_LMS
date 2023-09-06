/*
 * @(#)CmmJsonViewCtrl.java     
 *
 * Copyright 2022 JAYU.space
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

package com.vertexid.viself.base;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.hr.SysLoginVO;

/**
 * <b>Description</b>
 * <pre>
 *     URL 패턴에 따라 모델을 연결하고 JSON을 반환하는 컨트롤러
 *     조회용 뷰 컨트롤러
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class CmmJsonViewCtrl extends BaseCtrl {

    @Resource
    CmmJsonViewSvc cmmJsonViewSvc;

    @Resource
    PagingParamFunction pagingParamFunction;
    /**
     * 서브 모듈에 액션이 있는 경우
     *
     * @param system   시스템
     * @param app      앱
     * @param module   모듈
     * @param sub      서브모듈
     * @param resource resource
     * @param action   액션
     * @param model    모델
     * @param params   request parameter
     * @return JSON view
     */
    @RequestMapping("/{system}/{app}/{module}/{sub}/{resource}/{action}/json")
    public String view(
            @PathVariable
                    String system,
            @PathVariable
                    String app,
            @PathVariable
                    String module,
            @PathVariable
                    String sub,
            @PathVariable
                    String resource,
            @PathVariable
                    String action, SessionStatus status, HttpServletRequest req,
            ModelMap model,
            @RequestParam
                    Map<String, Object> params,
                    HttpSession session) {

        SysLoginVO loginUser = (SysLoginVO)SessionUtils.getLoginVO();
        params.put("loginInfo",  loginUser);
        pagingParamFunction.setOrderParam(params);
        log.debug("prams..........." + params);
        log.debug("path..........." + req.getRequestURI());

        BaseModelVO modelInfo =
                new BaseModelVO(system, app, module, sub, resource, action);
        return getJsonView(modelInfo, model, params);
    }

    /**
     * 서브 모듈에 액션이 있는 경우
     *
     * @param system   시스템
     * @param app      앱
     * @param module   모듈
     * @param resource resource
     * @param action   액션
     * @param model    모델
     * @param params   request parameter
     * @return JSON view
     */
    @RequestMapping("/{system}/{app}/{module}/{resource}/{action}/json")
    public String view(
            @PathVariable
                    String system,
            @PathVariable
                    String app,
            @PathVariable
                    String module,
            @PathVariable
                    String resource,
            @PathVariable
                    String action, SessionStatus status, HttpServletRequest req,
            ModelMap model,
            @RequestParam
                    Map<String, Object> params,
                    HttpSession session) {

        SysLoginVO loginUser = (SysLoginVO)SessionUtils.getLoginVO();
        params.put("loginInfo",  loginUser);
        pagingParamFunction.setOrderParam(params);
        log.debug("prams..........." + params);
        log.debug("path..........." + req.getRequestURI());

        BaseModelVO modelInfo =
                new BaseModelVO(system, app, module, null, resource, action);
        return getJsonView(modelInfo, model, params);
    }

    /**
     * 서브 모듈에 액션이 있는 경우
     *
     * @param system   시스템
     * @param app      앱
     * @param resource resource
     * @param action   액션
     * @param model    모델
     * @param params   request parameter
     * @return JSON view
     */
    @RequestMapping("/{system}/{app}/{resource}/{action}/json")
    public String view(
            @PathVariable
                    String system,
            @PathVariable
                    String app,
            @PathVariable
                    String resource,
            @PathVariable
                    String action, SessionStatus status, HttpServletRequest req,
            ModelMap model,
            @RequestParam
                    Map<String, Object> params,
                    HttpSession session) {

        SysLoginVO loginUser = (SysLoginVO)SessionUtils.getLoginVO();
        params.put("loginInfo",  loginUser);
        pagingParamFunction.setOrderParam(params);
        log.debug("prams..........." + params);
        log.debug("path..........." + req.getRequestURI());

        BaseModelVO modelInfo =
                new BaseModelVO(system, app, null, null, resource, action);
        return getJsonView(modelInfo, model, params);
    }

    /**
     * 서브 모듈에 액션이 있는 경우
     *
     * @param system   시스템
     * @param resource resource
     * @param action   액션
     * @param model    모델
     * @param params   request parameter
     * @return JSON view
     */
    @RequestMapping("/{system}/{resource}/{action}/json")
    public String view(
            @PathVariable
                    String system,
            @PathVariable
                    String resource,
            @PathVariable
                    String action, SessionStatus status, HttpServletRequest req,
            ModelMap model,
            @RequestParam
                    Map<String, Object> params,
                    HttpSession session) {

        SysLoginVO loginUser = (SysLoginVO)SessionUtils.getLoginVO();
        params.put("loginInfo",  loginUser);
        pagingParamFunction.setOrderParam(params);
        log.debug("prams..........." + params);
        log.debug("path..........." + req.getRequestURI());

        BaseModelVO modelInfo =
                new BaseModelVO(system, null, null, null, resource, action);
        return getJsonView(modelInfo, model, params);
    }

    /**
     * 서브 모듈에 액션이 있는 경우
     *
     * @param resource resource
     * @param action   액션
     * @param model    모델
     * @param params   request parameter
     * @return JSON view
     */
    @RequestMapping("/{resource}/{action}/json")
    public String view(
            @PathVariable
                    String resource,
            @PathVariable
                    String action, SessionStatus status, HttpServletRequest req,
            ModelMap model,
            @RequestParam
                    Map<String, Object> params,
                    HttpSession session) {

        SysLoginVO loginUser = (SysLoginVO)SessionUtils.getLoginVO();
        params.put("loginInfo",  loginUser);
        pagingParamFunction.setOrderParam(params);
        log.debug("prams..........." + params);
        log.debug("path..........." + req.getRequestURI());

        BaseModelVO modelInfo =
                new BaseModelVO(null, null, null, null, resource, action);
        return getJsonView(modelInfo, model, params);
    }

//    /**
//     * 조회서비스를 이용해서 모델에 값을 설정
//     * @param modelInfo model info
//     * @param model model
//     * @param params parameters
//     * @return JSON view
//     */
//    private String getJsonView(BaseModelVO modelInfo, ModelMap model,
//            Map<String, Object> params) {
//
//        model.clear();
//        model.addAttribute(ModelAttribute.DATA.getAttributeId(),
//                cmmJsonViewSvc.getData(modelInfo, params));
//
//        return ViewType.JSON_VIEW.getViewId();
//    }

//    /**
//     * 에러 JSON 페이지 처리
//     * @param model 모델
//     * @param params 파라메터
//     * @return JSON view
//     */
//    @RequestMapping("/error/json")
//    public String viewError(ModelMap model,
//            @RequestParam
//                    Map<String, Object> params) {
//
//        log.debug("ERROR JSON>>>>>>>>>>>>>>>>>>>>>>>"+ params);
//
//        String result = "";
//        model.clear();
//        model.addAttribute(ERROR_FLAG.getAttributeId(), ERROR_RESULT);
//        if (isProd()) {
//            result = ERROR.getMsgCode();
//        } else {
//            result = "";
//        }
//
//        model.addAttribute(MSG.getAttributeId(), result);
//
//        return ViewType.JSON_VIEW.getViewId();
//    }

}
