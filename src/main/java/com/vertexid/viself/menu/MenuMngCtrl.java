/*
 * @(#)MenuMngCtrl.java     2020-09-09(009) 오후 1:02
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

import static com.vertexid.commons.view.ViewType.JSON_VIEW;
import static com.vertexid.viself.base.MessageCode.COMPLETE;
import static com.vertexid.viself.base.MessageCode.ERROR;
import static com.vertexid.viself.base.ModelAttribute.ERROR_FLAG;
import static com.vertexid.viself.base.ModelAttribute.MSG;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vertexid.viself.hr.SysLoginVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.auth.AuthMenuSvc;
import com.vertexid.viself.base.BaseCtrl;
import com.vertexid.viself.base.ModelAttribute;

/**
 * <b>Description</b>
 * <pre>
 *     메뉴관리 컨트롤러
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class MenuMngCtrl extends BaseCtrl {

    @Resource
    private MenuMngSvc menuMngSvc;

    @Resource
    private AuthMenuSvc authMenuSvc;

    /**
     * 동적 메뉴 조회
     *
     * @param model  모델
     * @param params request parameter
     * @return JSON view
     */
    @RequestMapping("/viself/menu/MenuMng/list/json")
    public String view(HttpSession session, HttpServletRequest req,
            ModelMap model,
            @RequestParam
                    Map<String, Object> params) {

        SysLoginVO loginUser = (SysLoginVO)SessionUtils.getLoginVO();
        params.put("authCd",  loginUser.getAuthCd());

        log.info("authCd........\n" + loginUser.getLoginId());
        log.info("params........\n" + params);
        model.clear();

        model.addAttribute(ModelAttribute.DATA.getAttributeId(), menuMngSvc.getList(params));

        return JSON_VIEW.getViewId();
    }

    /**
     * 메뉴 정보 저장 처리
     *
     * @param status 세션 상태
     * @param req    request
     * @param model  모델
     * @param params 파라메터
     * @return 처리결과 정보
     */
    @RequestMapping(value = "/viself/menu/MenuMng/saveMenu/json",
            method = RequestMethod.POST)
    public String save(SessionStatus status, HttpServletRequest req,
            ModelMap model,
            @RequestBody
                    MenuDTO params) {

        log.info("params........\n" + params.toString());
        model.clear();

        String result = menuMngSvc.save(params);

        if (StringUtils.isEmpty(result)) {
            // 정상처리시 메시지 표시
            model.addAttribute(ModelAttribute.MSG.getAttributeId(),
                    COMPLETE.getMsgCode());
        } else {
            if (isNotProd()) {
                // 운영이 아닐 경우 에러 메시지 표시
                model.addAttribute(ModelAttribute.MSG.getAttributeId(), result);
            } else {
                // 운영일 경우 기본 에러 메시지 표시
                model.addAttribute(ModelAttribute.MSG.getAttributeId(),
                        ERROR.getMsgCode());
            }
            model.addAttribute(ModelAttribute.ERROR_FLAG.getAttributeId(),
                    ERROR.getResultCode());
        }

        log.info("result.....\n" + model);

        return JSON_VIEW.getViewId();
    }

    /**
     * 메뉴 삭제
     * @param model
     * @param params
     * @return
     */
    @RequestMapping(value = "/viself/menu/MenuMng/deleteAll/json")
    public String delete(ModelMap model,
            @RequestParam
            Map<String, Object> params) {

        model.clear();
        //-- 관련 권한 먼저 삭제
        String result = authMenuSvc.deleteMenu(params);
        model.addAttribute(ERROR_FLAG.getAttributeId(), ERROR_RESULT);

        //-- 메뉴 삭제
        result = menuMngSvc.deleteAll(params);
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
}
