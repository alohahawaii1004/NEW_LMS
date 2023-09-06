/*
 * @(#)AccessLogDTO.java     2023-01-18(018) 오후 3:33:49
 *
 * Copyright 2023 JaYu.space
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
package com.vertexid.viself.base;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class AccessLogDTO extends BaseVO{
    private static final long serialVersionUID = 3344729876496172549L;

    private String logUid;
    private String loginId;
    private String logDte;
    private String accesIp;
    private String sessionId;
    private String charEnc;
    private String contextPath;
    private String reqUri;
    private String httpMethod;
    private String pkgNm;
    private String ctrlNm;
    private String methodNm;
    private String actionTp;
    private String param;
    private String headerInfo;

    public AccessLogDTO() {
    }

    public String getLogUid() {
        return logUid;
    }

    public void setLogUid(String logUid) {
        this.logUid = logUid;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLogDte() {
        return logDte;
    }

    public void setLogDte(String logDte) {
        this.logDte = logDte;
    }

    public String getAccesIp() {
        return accesIp;
    }

    public void setAccesIp(String accesIp) {
        this.accesIp = accesIp;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCharEnc() {
        return charEnc;
    }

    public void setCharEnc(String charEnc) {
        this.charEnc = charEnc;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getReqUri() {
        return reqUri;
    }

    public void setReqUri(String reqUri) {
        this.reqUri = reqUri;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getPkgNm() {
        return pkgNm;
    }

    public void setPkgNm(String pkgNm) {
        this.pkgNm = pkgNm;
    }

    public String getCtrlNm() {
        return ctrlNm;
    }

    public void setCtrlNm(String ctrlNm) {
        this.ctrlNm = ctrlNm;
    }

    public String getMethodNm() {
        return methodNm;
    }

    public void setMethodNm(String methodNm) {
        this.methodNm = methodNm;
    }

    public String getActionTp() {
        return actionTp;
    }

    public void setActionTp(String actionTp) {
        this.actionTp = actionTp;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getHeaderInfo() {
        return headerInfo;
    }

    public void setHeaderInfo(String headerInfo) {
        this.headerInfo = headerInfo;
    }
}
