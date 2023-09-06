/*
 * @(#)SimpleSecurityEnvSvc.java     2021-03-24(024) 오전 11:20
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

import com.vertexid.spring.utils.BaseProperties;
import com.vertexid.viself.base.BaseSvc;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * <b>Description</b>
 * <pre>
 *     Simple Security 환경변수 로딩 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
public class SimpleSecurityEnvSvc extends BaseSvc {


    public SimpleSecurityEnvVO getEnv() {

        SimpleSecurityEnvVO envVO = new SimpleSecurityEnvVO();

        envVO.setLoginURI(BaseProperties.get("security.loginURI"));
        envVO.setLoginProcURI(BaseProperties.get("security.loginProcURI"));
        envVO.setLogoutURI(BaseProperties.get("security.logoutURI"));
        envVO.setExpiredURI(BaseProperties.get("security.expiredURI"));
        envVO.setIdParamName(BaseProperties.get("security.idParamName"));
        envVO.setPwParamName(BaseProperties.get("security.pwParamName"));
        envVO.setIgnoreURIs(new HashSet<>(BaseProperties.getList("security.ignoreURIs")));

        String csrfYn = BaseProperties.get("security.csrfYn");
        if (csrfYn.equalsIgnoreCase("Y")) {
            csrfYn = "true";
        }

        envVO.setCsrfYn(Boolean.parseBoolean(csrfYn));

        envVO.setCsrfIgnoreURIs(new HashSet<>(
                BaseProperties.getList("security.csrf.ignoreURIs")));

        envVO.setAllowIfAllAbstainDecisions(Boolean.parseBoolean(
                BaseProperties.get("security.allowIfAllAbstainDecisions")));

        return envVO;
    }
}
