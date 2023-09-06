/*
 * @(#)SimpleUserDTO.java     2022-07-26(026) 오전 10:29:54
 *
 * Copyright 2022 JaYu.space
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
package com.vertexid.viself.hr;

/**
 * <b>Description</b>
 * <pre>
 *     사용자 DTO
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class SimpleUserDTO extends UserDTO {

    private static final long serialVersionUID = 6062700858847607427L;

    private String loginId;
    private String nmKo;
    private String telNo;
    private String email;
    private String userAuth;
    private String adminAuth;
    private String memo;
    private String useStDte;
    private String useEdDte;

    private String userTpCd;

    private String orgId;

    private String cert;
    private String encCert;

    private String useYn;

    public SimpleUserDTO() {
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getNmKo() {
        return nmKo;
    }

    public void setNmKo(String nmKo) {
        this.nmKo = nmKo;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(String userAuth) {
        this.userAuth = userAuth;
    }

    public String getAdminAuth() {
        return adminAuth;
    }

    public void setAdminAuth(String adminAuth) {
        this.adminAuth = adminAuth;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getUseStDte() {
        return useStDte;
    }

    public void setUseStDte(String useStDte) {
        this.useStDte = useStDte;
    }

    public String getUseEdDte() {
        return useEdDte;
    }

    public void setUseEdDte(String useEdDte) {
        this.useEdDte = useEdDte;
    }

    @Override
    public String getUserTpCd() {
        return userTpCd;
    }

    @Override
    public void setUserTpCd(String userTpCd) {
        this.userTpCd = userTpCd;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getEncCert() {
        return encCert;
    }

    public void setEncCert(String encCert) {
        this.encCert = encCert;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
}
