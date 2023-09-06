/*
 * @(#)AuthMemberCtrl.java     2022-03-15(015) 오전 8:31
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
package com.vertexid.viself.auth;

import com.vertexid.viself.base.BaseCtrl;
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
 *     권한 멤버 컨트롤러
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class AuthMemberCtrl extends BaseCtrl {

    @Resource
    AuthMemberSvc authMemberSvc;

    /**
     * 권한멤버 추가
     * @param model model
     * @param params authCd, mbrId
     * @return json view
     */
    @RequestMapping(value = "/viself/auth/AuthMember/insertAuthUser/json",
            method = RequestMethod.POST)
    public String addMember(ModelMap model,
            @RequestBody
            AuthMemberDTO params) {
        return getTransactionJsonView(model, authMemberSvc.addMember(params));
    }

    /**
     * 권한멤버 제외에
     * @param model model
     * @param params authCd, mbrId
     * @return json view
     */
    @RequestMapping(value = "/viself/auth/AuthMember/delete/json",
            method = RequestMethod.POST)
    public String delMember(ModelMap model,
            @RequestBody
            AuthMemberDTO params) {
        return getTransactionJsonView(model, authMemberSvc.delMember(params));
    }

    /**
     * 권한자 list 얻기
     */
    @RequestMapping(value = "/viself/auth/AuthMember/list/json",
            method = RequestMethod.POST)
    public String getListMember(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {

        modelMap.addAttribute(DATA.getAttributeId(),
                authMemberSvc.getMemberList(param));

        return JSON_VIEW.getViewId();
    }

    /**
     * 사용자 권한 변경에 따른 권한멤버 정보 변경
     * @param model model
     * @param params list(authCd, mbrId)
     * @return json view
     */
    @RequestMapping(value = "/viself/auth/AuthMember/saveMembers/json",
            method = RequestMethod.POST)
    public String saveMembers(ModelMap model,
            @RequestBody
            AuthMemberDTO params) {
        return getTransactionJsonView(model, authMemberSvc.save(params));
    }

    /**
     * 권한멤버에서 삭제
     * @param model model
     * @param params list(authCd, mbrId)
     * @return json view
     */
    @RequestMapping(value = "/viself/auth/AuthMember/deleteMembers/json",
            method = RequestMethod.POST)
    public String deleteAuthMembers(ModelMap model,
            @RequestBody
            AuthMemberDTO params) {
        return getTransactionJsonView(model, authMemberSvc.delete(params));
    }
}
