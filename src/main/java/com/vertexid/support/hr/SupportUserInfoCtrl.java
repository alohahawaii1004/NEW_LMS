/*
 * @(#)SupportUserInfoCtrl.java     2023-03-10 010 오후 2:40:41
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

import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.base.BaseCtrl;
import com.vertexid.viself.base.ModelAttribute;
import com.vertexid.viself.hr.SysLoginVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.vertexid.commons.view.ViewType.JSON_VIEW;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class SupportUserInfoCtrl extends BaseCtrl {

    /**
     * 현재 로그인 사용자 가져오기
     *
     * @param req
     * @param model
     * @return
     */
    @RequestMapping("/paragon/cmm/getSessionUser")
    public String getUserInfo(HttpServletRequest req, ModelMap model) {

        SysLoginVO loginUser = (SysLoginVO) SessionUtils.getLoginVO();
        Map<String, Object> param = new HashMap<>();
        param.put("loginId", loginUser.getLoginId());
        param.put("nmKo", loginUser.getNmKo());

        log.debug(param.toString());

        model.clear();
        model.addAttribute(ModelAttribute.DATA.getAttributeId(), param);

        return JSON_VIEW.getViewId();
    }

}
