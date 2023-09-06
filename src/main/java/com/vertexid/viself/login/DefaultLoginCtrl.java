/*
 * @(#)DefaultLoginCtrl.java     2022-02-10(010) 오전 7:52
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
package com.vertexid.viself.login;

import com.vertexid.viself.base.BaseCtrl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 *     기본 로그인 컨트롤러
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class DefaultLoginCtrl extends BaseCtrl {
       private static final String LOGIN_FORM_TILES_VIEW = "login.tiles";

    /**
     * 로그인 폼
     *
     * @param req request
     * @param model model
     * @param params parameters
     * @return view
     */
    @RequestMapping("/login.do")
    public String loginForm(HttpServletRequest req, ModelMap model,  @RequestParam  Map<String, Object> params) {
        log.debug("params..........??????" + params);
        return LOGIN_FORM_TILES_VIEW;
        
    }
}
