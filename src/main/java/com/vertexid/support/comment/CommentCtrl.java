/*
 * @(#)CommentCtrl.java     2023-04-04 오전 7:30
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
package com.vertexid.support.comment;

import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.base.BaseCtrl;
import com.vertexid.viself.hr.SysLoginVO;
import org.apache.commons.lang3.StringUtils;
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
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class CommentCtrl extends BaseCtrl {

    @Resource
    CommentSvc commentSvc;

    @Resource
    CommentMailSvc commentMailSvc;

    @RequestMapping(value = "/support/cmm/comment/getList/json",
            method = RequestMethod.POST)
    public String getCommentList(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {

        modelMap.clear();
        SysLoginVO loingInfo = (SysLoginVO) SessionUtils.getLoginVO();
        param.put("loginInfo", loingInfo);
        modelMap.addAttribute(DATA.getAttributeId(), commentSvc.getList(param));

        return JSON_VIEW.getViewId();
    }

    @RequestMapping(value = "/support/cmm/comment/insertCmt/json",
            method = RequestMethod.POST)
    public String insertCmt(ModelMap modelMap,
            @RequestBody
            CommentDTO param) {

        String rslt = commentSvc.insertCmt(param);
        if(StringUtils.isEmpty(rslt)){
            commentMailSvc.insertMail(param);
        }

        return getTransactionJsonView(modelMap, rslt);
    }
}
