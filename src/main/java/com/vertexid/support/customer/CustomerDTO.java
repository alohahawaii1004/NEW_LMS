/*
 * @(#)CustomerDTO.java     2023-03-13 013 오후 1:33:10
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
package com.vertexid.support.customer;

import com.vertexid.viself.base.CmmDTO;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class CustomerDTO extends CmmDTO {
    private static final long serialVersionUID = -2100031553945946143L;

    private String orgId;

    private String ordNo;
    private String nmKo;
    private String nmEn;
    private String nmJa;
    private String nmZh;
    private String parentOrgId;
    private String orgTpCd = "OG";
    private String useYn;
    private String orgDesc;

    public CustomerDTO() {
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrdNo() {
        return ordNo;
    }

    public void setOrdNo(String ordNo) {
        this.ordNo = ordNo;
    }

    public String getNmKo() {
        return nmKo;
    }

    public void setNmKo(String nmKo) {
        this.nmKo = nmKo;
    }

    public String getNmEn() {
        return nmEn;
    }

    public void setNmEn(String nmEn) {
        this.nmEn = nmEn;
    }

    public String getNmJa() {
        return nmJa;
    }

    public void setNmJa(String nmJa) {
        this.nmJa = nmJa;
    }

    public String getNmZh() {
        return nmZh;
    }

    public void setNmZh(String nmZh) {
        this.nmZh = nmZh;
    }

    public String getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(String parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public String getOrgTpCd() {
        return orgTpCd;
    }

    public void setOrgTpCd(String orgTpCd) {
        this.orgTpCd = orgTpCd;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getOrgDesc() {
        return orgDesc;
    }

    public void setOrgDesc(String orgDesc) {
        this.orgDesc = orgDesc;
    }
}
