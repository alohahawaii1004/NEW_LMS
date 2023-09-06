/*
 * @(#)SimpleSecurityEnvMapFactoryBean.java     2021-03-24(024) 오전 11:16
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

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class SimpleSecurityEnvMapFactoryBean extends BaseSvc implements
        FactoryBean<SimpleSecurityEnvVO> {

    private SimpleSecurityEnvVO simpleSecurityEnvVO;

    private SimpleSecurityEnvSvc simpleSecurityEnvSvc;

    public void init(){
        simpleSecurityEnvVO = simpleSecurityEnvSvc.getEnv();
    }

    public void setSimpleSecurityEnvSvc(
            SimpleSecurityEnvSvc simpleSecurityEnvSvc) {
        this.simpleSecurityEnvSvc = simpleSecurityEnvSvc;
    }

    @Override
    public SimpleSecurityEnvVO getObject() throws Exception {
        if(null == simpleSecurityEnvVO){
            init();
        }
        return simpleSecurityEnvVO;
    }

    @Override
    public Class<?> getObjectType() {
        return SimpleSecurityEnvVO.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
