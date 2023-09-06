/*
 * @(#)UrlMngCtrl.java     2021-03-15(015) 오후 4:24
 *
 * Copyright 2021 JAYU.space
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
import com.vertexid.viself.security.SimpleReloadableSecurityMetadataSource;
import com.vertexid.viself.security.SimpleSecureObjectSvc;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <b>Description</b>
 * <pre>
 *     URL 관리
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class UrlMngCtrl extends BaseCtrl {

    @Resource
    private UrlMngSvc urlMngSvc;

    @Resource
    private SimpleSecureObjectSvc simpleSecureObjectSvc;

    @Resource
    private SimpleReloadableSecurityMetadataSource simpleReloadableSecurityMetadataSource;



    @RequestMapping(value = "/viself/auth/url-mng/save/json",
            method = RequestMethod.POST)
    public String saveUrl(ModelMap model, HttpServletRequest req,
            @RequestBody
                    UrlDTO params) throws Exception {

        String rtnString = getTransactionJsonView(model, urlMngSvc.save(params));

        // reload
        simpleReloadableSecurityMetadataSource.setSecureObjectSvc(simpleSecureObjectSvc);
        simpleReloadableSecurityMetadataSource.reload();

        return rtnString;
    }

    @RequestMapping(value = "/viself/auth/url-mng/delete/json",
            method = RequestMethod.POST)
    public String deleteUrl(ModelMap model, HttpServletRequest req,
            @RequestBody
                    UrlDTO params) {
        return getTransactionJsonView(model, urlMngSvc.delete(params));
    }
}
