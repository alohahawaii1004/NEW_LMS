/*
 * @(#)SimpleSecurityEnvVO.java     2021-03-24(024) 오전 11:05
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

import com.vertexid.viself.base.BaseVO;

import java.util.Set;

/**
 * <b>Description</b>
 * <pre>
 *     Simple Security Filter 환경 정보
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class SimpleSecurityEnvVO extends BaseVO {
    private static final long serialVersionUID = -5634534541944914514L;

    private String loginURI = "/login";
    private String loginProcURI = "/login/proc";
    private String logoutURI = "/logout";
    private String expiredURI = "/login";
    private String idParamName = "encId";
    private String pwParamName = "encPw";
    private Set<String> ignoreURIs;
    private boolean csrfYn;
    private boolean allowIfAllAbstainDecisions;
    private Set<String> csrfIgnoreURIs;

    public SimpleSecurityEnvVO(String loginURI, String loginProcURI,
            String logoutURI, String expiredURI, String idParamName,
            String pwParamName, Set<String> ignoreURIs, boolean csrfYn,
            Set<String> csrfIgnoreURIs, boolean allowIfAllAbstainDecisions) {
        this.loginURI = loginURI;
        this.loginProcURI = loginProcURI;
        this.logoutURI = logoutURI;
        this.expiredURI = expiredURI;
        this.idParamName = idParamName;
        this.pwParamName = pwParamName;
        this.ignoreURIs = ignoreURIs;
        this.csrfYn = csrfYn;
        this.csrfIgnoreURIs = csrfIgnoreURIs;
        this.allowIfAllAbstainDecisions = allowIfAllAbstainDecisions;
    }

    public SimpleSecurityEnvVO() {
    }

    public String getLoginURI() {
        return loginURI;
    }

    public void setLoginURI(String loginURI) {
        this.loginURI = loginURI;
    }

    public String getLoginProcURI() {
        return loginProcURI;
    }

    public void setLoginProcURI(String loginProcURI) {
        this.loginProcURI = loginProcURI;
    }

    public String getLogoutURI() {
        return logoutURI;
    }

    public void setLogoutURI(String logoutURI) {
        this.logoutURI = logoutURI;
    }

    public String getExpiredURI() {
        return expiredURI;
    }

    public void setExpiredURI(String expiredURI) {
        this.expiredURI = expiredURI;
    }

    public String getIdParamName() {
        return idParamName;
    }

    public void setIdParamName(String idParamName) {
        this.idParamName = idParamName;
    }

    public String getPwParamName() {
        return pwParamName;
    }

    public void setPwParamName(String pwParamName) {
        this.pwParamName = pwParamName;
    }

    public Set<String> getIgnoreURIs() {
        return ignoreURIs;
    }

    public void setIgnoreURIs(Set<String> ignoreURIs) {
        this.ignoreURIs = ignoreURIs;
    }

    public boolean isAllowIfAllAbstainDecisions() {
        return allowIfAllAbstainDecisions;
    }

    public void setAllowIfAllAbstainDecisions(
            boolean allowIfAllAbstainDecisions) {
        this.allowIfAllAbstainDecisions = allowIfAllAbstainDecisions;
    }

    public boolean isCsrfYn() {
        return csrfYn;
    }

    public void setCsrfYn(boolean csrfYn) {
        this.csrfYn = csrfYn;
    }

    public Set<String> getCsrfIgnoreURIs() {
        return csrfIgnoreURIs;
    }

    public void setCsrfIgnoreURIs(Set<String> csrfIgnoreURIs) {
        this.csrfIgnoreURIs = csrfIgnoreURIs;
    }
}


