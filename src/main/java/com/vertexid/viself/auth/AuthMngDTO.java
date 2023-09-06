/*
 * @(#)CodeDTO.java     2020-09-24(024) 오후 2:21
 *
 * Copyright 2020 JAYU.space
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

import com.vertexid.viself.base.CmmDTO;

import java.util.List;

/**
 * <b>Description</b>
 * <pre>
 *     권한관리 DTO
 * </pre>
 *
 * @author 강세원
 */
public class AuthMngDTO extends CmmDTO {

    private static final long serialVersionUID = -7772027497755878219L;

    private String authCd;
    private String authNm;
    private String authTpCd;
    private String ordNo;
    private String remark;

    private String useYn;

    private List<AuthMngDTO> list;

    public AuthMngDTO() {
    }

    public String getAuthCd() {
        return authCd;
    }

    public void setAuthCd(String authCd) {
        this.authCd = authCd;
    }


    public String getAuthNm() {
        return authNm;
    }

    public void setAuthNm(String authNm) {
        this.authNm = authNm;
    }

    public String getAuthTpCd() {
        return authTpCd;
    }

    public void setAuthTpCd(String authTpCd) {
        this.authTpCd = authTpCd;
    }

    public String getOrdNo() {
        return ordNo;
    }

    public void setOrdNo(String ordNo) {
        this.ordNo = ordNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public List<AuthMngDTO> getList() {
        return list;
    }

    public void setList(List<AuthMngDTO> list) {
        this.list = list;
    }



}
