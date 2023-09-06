/*
 * @(#)UserMngCtrl.java     2023-03-14 014 오전 10:55:43
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
package com.vertexid.support.hr;

import com.vertexid.mybatis.ListPerPageFunction;
import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.auth.AuthMngSvc;
import com.vertexid.viself.base.BaseCtrl;
import com.vertexid.viself.base.BaseModelVO;
import com.vertexid.viself.base.PagingParamFunction;
import com.vertexid.viself.hr.PasswordDTO;
import com.vertexid.viself.hr.SimpleUserDTO;
import com.vertexid.viself.hr.SysLoginVO;
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
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class SupportUserMngCtrl extends BaseCtrl {

    @Resource
    AuthMngSvc authMngSvc;

    @Resource
    SupportUserMngSvc supportUserMngSvc;

    @Resource
    RSACryptographicSvc cryptographicSvc;

    @Resource
    ListPerPageFunction listPerPageFunction;

    @Resource
    PagingParamFunction pagingParamFunction;

    @RequestMapping(value = "/support/user/userMng/listPerPage/json",
            method = RequestMethod.POST)
    public String getListPerPage(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {

        modelMap.clear();
        SysLoginVO loingInfo = (SysLoginVO) SessionUtils.getLoginVO();
        param.put("loginInfo", loingInfo);
        pagingParamFunction.setOrderParam(param);
        listPerPageFunction.setNamespace(SupportUserMngSvc.NAMESPACE);
        modelMap.addAttribute(DATA.getAttributeId(),
                listPerPageFunction.getListPerPage(param));

        return JSON_VIEW.getViewId();
    }

    @RequestMapping(value = "/support/user/userMng/authList/json",
            method = RequestMethod.POST)
    public String getAuthorityList(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {
        BaseModelVO modelInfo =
                new BaseModelVO("viself", "auth", null, null, "AuthMng",
                        "list");
        return getJsonView(modelInfo, modelMap, param);
    }


    @RequestMapping(value = "/support/user/userMng/loadData/json",
            method = RequestMethod.POST)
    public String getUserInfo(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {
        BaseModelVO modelInfo =
                new BaseModelVO("support", "hr", null, null, "userMng",
                        "loadData");
        return getJsonView(modelInfo, modelMap, param);
    }

    @RequestMapping(value = "/support/user/userMng/create/json",
            method = RequestMethod.POST)
    public String createUserInfo(ModelMap modelMap, HttpServletRequest req,
            @RequestBody
            SimpleUserDTO param) {
        return getTransactionJsonView(modelMap,
                supportUserMngSvc.createUser(decryptParams(req, param)));
    }

    @RequestMapping(value = "/support/user/userMng/update/json",
            method = RequestMethod.POST)
    public String udpateUserInfo(ModelMap modelMap, HttpServletRequest req,
            @RequestBody
            SimpleUserDTO param) {
        return getTransactionJsonView(modelMap,
                supportUserMngSvc.updateUser(decryptParams(req, param)));
    }

    @RequestMapping(value = "/cmm/modal/user/updatePassword/json",
            method = RequestMethod.POST)
    public String resetPassword(ModelMap modelMap, HttpServletRequest req,
            @RequestBody
            PasswordDTO param) {
        return getTransactionJsonView(modelMap,
                supportUserMngSvc.updatePassword(decryptPwdParam(req, param)));
    }

    private PasswordDTO decryptPwdParam(HttpServletRequest req, PasswordDTO param) {
        HttpSession session = req.getSession();
        if (null == session.getAttribute(RSAAttributeKey.PRIVATE_KEY)) {
            throw new RuntimeException("NO PRIVATE_KEY IN SESSION");
        }

        PrivateKey privateKey =
                (PrivateKey) session.getAttribute(RSAAttributeKey.PRIVATE_KEY);
        param.setLoginId(
                cryptographicSvc.decrypt(privateKey, param.getLoginId()));
        param.setOldPwd(cryptographicSvc.decrypt(privateKey, param.getOldPwd()));
        param.setNewPwd(cryptographicSvc.decrypt(privateKey, param.getNewPwd()));
        param.setCfmPwd(
                cryptographicSvc.decrypt(privateKey, param.getCfmPwd()));

        if (log.isDebugEnabled()) {
            log.debug(param.toString());
        }

        return param;
    }

    private SimpleUserDTO decryptParams(HttpServletRequest req,
            SimpleUserDTO param) {
        HttpSession session = req.getSession();
        if (null == session.getAttribute(RSAAttributeKey.PRIVATE_KEY)) {
            throw new RuntimeException("NO PRIVATE_KEY IN SESSION");
        }

        PrivateKey privateKey =
                (PrivateKey) session.getAttribute(RSAAttributeKey.PRIVATE_KEY);
        param.setLoginId(
                cryptographicSvc.decrypt(privateKey, param.getLoginId()));
        param.setNmKo(cryptographicSvc.decrypt(privateKey, param.getNmKo()));
        param.setEmail(cryptographicSvc.decrypt(privateKey, param.getEmail()));
        param.setUserAuth(
                cryptographicSvc.decrypt(privateKey, param.getUserAuth()));
        param.setMemo(cryptographicSvc.decrypt(privateKey, param.getMemo()));
        param.setOrgId(cryptographicSvc.decrypt(privateKey, param.getOrgId()));
        param.setUseYn(cryptographicSvc.decrypt(privateKey, param.getUseYn()));

        if (log.isDebugEnabled()) {
            log.debug(param.toString());
        }

        return param;
    }

}
