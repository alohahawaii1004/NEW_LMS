/*
 * @(#)SimpleUrlResourceMapFactoryBean.java     2021-03-30(030) 오전 10:47
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
package com.vertexid.viself.security;

import com.vertexid.viself.base.BaseSvc;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * <b>Description</b>
 * <pre>
 *     UrlResourceMap Factory Bean
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class SimpleUrlResourceMapFactoryBean extends BaseSvc implements
        FactoryBean<LinkedHashMap<RequestMatcher, List<ConfigAttribute>>> {

    private SimpleSecureObjectSvc simpleSecureObjectSvc;

    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> requestMap;

    public void setSecureObjectSvc(SimpleSecureObjectSvc simpleSecureObjectSvc) {
        this.simpleSecureObjectSvc = simpleSecureObjectSvc;
    }

    public void init() throws Exception{
        requestMap = simpleSecureObjectSvc.getRolesAndUrl();
    }

    @Override
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getObject()
            throws Exception {
        if(null == requestMap){
            requestMap = simpleSecureObjectSvc.getRolesAndUrl();
        }

        return requestMap;
    }

    @Override
    public Class<?> getObjectType() {
        return LinkedHashMap.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
