/*
 * @(#)SimpleUserMngCtrl.java     2022-07-25(025) 오후 3:17:52
 *
 * Copyright 2022 JaYu.space
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
package com.vertexid.viself.hr;

import com.vertexid.mybatis.ListPerPageFunction;
import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.auth.AuthMngSvc;
import com.vertexid.viself.base.BaseCtrl;
import com.vertexid.viself.base.PagingParamFunction;
import com.vertexid.viself.security.RSAAttributeKey;
import com.vertexid.viself.security.RSACryptographicSvc;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.PrivateKey;
import java.util.Map;

import static com.vertexid.commons.view.ViewType.JSON_VIEW;
import static com.vertexid.viself.base.ModelAttribute.DATA;

/**
 * <b>Description</b>
 * <pre>
 *     사용자관리 Controller
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class SimpleUserMngCtrl extends BaseCtrl {

    private static final String FORWARD_HR_VIEW =
            "forward:/viself/mng/hr/userView.popup";
    private static final String FORWARD_NOT_HR_VIEW =
            "forward:/viself/mng/hr/userWrite.popup";
    @Resource
    AuthMngSvc authMngSvc;

    @Resource
    SimpleUserMngSvc simpleUserMngSvc;

    @Resource
    RSACryptographicSvc rsaCryptographicSvc;

    @Resource
    ListPerPageFunction listPerPageFunction;

    @Resource
    PagingParamFunction pagingParamFunction;

    @RequestMapping(value = "/viself/mng/hr/AuthMng/list/json",
            method = RequestMethod.POST)
    public String getAuthList(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {

        modelMap.clear();
        SysLoginVO loingInfo = (SysLoginVO) SessionUtils.getLoginVO();
        param.put("loginInfo", loingInfo);
        modelMap.addAttribute(DATA.getAttributeId(), authMngSvc.getList(param));

        return JSON_VIEW.getViewId();
    }

    @RequestMapping(value = "/viself/mng/hr/UserMng/listPerPage/json",
            method = RequestMethod.POST)
    public String getListPerPage(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {

        modelMap.clear();
        SysLoginVO loingInfo = (SysLoginVO) SessionUtils.getLoginVO();
        param.put("loginInfo", loingInfo);
        pagingParamFunction.setOrderParam(param);
        listPerPageFunction.setNamespace(SimpleUserMngSvc.NAMESPACE);
        modelMap.addAttribute(DATA.getAttributeId(),
                listPerPageFunction.getListPerPage(param));

        return JSON_VIEW.getViewId();
    }

    // /viself/mng/hr/UserMng/save/json

    @RequestMapping(value = "/ssc/hr/UserMng/save/json",
            method = RequestMethod.POST)
    public String saveUser(ModelMap modelMap, HttpServletRequest req,
            @RequestBody
            SimpleUserDTO param) {
        return getTransactionJsonView(modelMap,
                simpleUserMngSvc.saveData(decryptParams(req, param)));
    }

    private SimpleUserDTO decryptParams(HttpServletRequest req, SimpleUserDTO param) {
        HttpSession session = req.getSession();
        if (null == session.getAttribute(RSAAttributeKey.PRIVATE_KEY)) {
            throw new RuntimeException("NO PRIVATE_KEY IN SESSION");
        }

        PrivateKey privateKey =
                (PrivateKey) session.getAttribute(RSAAttributeKey.PRIVATE_KEY);

        param.setLoginId(
                rsaCryptographicSvc.decrypt(privateKey, param.getLoginId()));
        param.setNmKo(rsaCryptographicSvc.decrypt(privateKey, param.getNmKo()));
        param.setTelNo(
                rsaCryptographicSvc.decrypt(privateKey, param.getTelNo()));
        param.setEmail(
                rsaCryptographicSvc.decrypt(privateKey, param.getEmail()));
        param.setUserAuth(
                rsaCryptographicSvc.decrypt(privateKey, param.getUserAuth()));
        param.setAdminAuth(
                rsaCryptographicSvc.decrypt(privateKey, param.getAdminAuth()));
        param.setMemo(rsaCryptographicSvc.decrypt(privateKey, param.getMemo()));
        param.setUseStDte(
                rsaCryptographicSvc.decrypt(privateKey, param.getUseStDte()));
        param.setUseEdDte(
                rsaCryptographicSvc.decrypt(privateKey, param.getUseEdDte()));

        if(log.isDebugEnabled()){
            log.debug(param.toString());
        }

        return param;
    }

    @RequestMapping(value = "/viself/mng/hr/UserMng/update/json",
            method = RequestMethod.POST)
    public String updateUser(ModelMap modelMap, HttpServletRequest req,
            @RequestBody
            SimpleUserDTO param) {
        return getTransactionJsonView(modelMap,
                simpleUserMngSvc.updateHRData(decryptParams(req, param)));
    }

    @RequestMapping(value = "/viself/mng/hr/userInfo/json",
            method = RequestMethod.POST)
    public String chkUserInfo(
            @RequestParam
            Map<String, Object> param) {

        param.put("userId", param.get("loginId"));
        Map<String, Object> data = simpleUserMngSvc.getData(param);
        String forwardUrl = FORWARD_NOT_HR_VIEW;
        if (SimpleUserMngSvc.HR_USER.equals(data.get("userTpCd"))) {
            forwardUrl = FORWARD_HR_VIEW;
        }

        return forwardUrl;
    }

    @RequestMapping(value = "/viself/mng/hr/UserMng/getData/json",
            method = RequestMethod.POST)
    public String getUserData(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {
        modelMap.clear();
        param.put("userId", param.get("loginId"));
        modelMap.addAttribute(DATA.getAttributeId(),
                simpleUserMngSvc.getData(param));
        return JSON_VIEW.getViewId();
    }
}
