/*
 * @(#)CodeTypeDTO.java     2020-04-23 오후 3:50
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
package com.vertexid.viself.code;

import java.util.List;

import com.vertexid.viself.base.CmmDTO;

/**
 * <b>Description</b>
 *
 * @author Yang, Ki Hwa
 */
public class CodeTypeDTO extends CmmDTO {

    private static final long serialVersionUID = -2421219985696541699L;

    private String tpId;
    private String tpNm;
    private String uptAuthCd;
    private String sortOrd;
    private String remark;
    private String useEnable;
    private List<CodeTypeDTO> list;

    public CodeTypeDTO() {
    }

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getTpNm() {
        return tpNm;
    }

    public void setTpNm(String tpNm) {
        this.tpNm = tpNm;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUseEnable() {
        return useEnable;
    }

    public void setUseEnable(String useEnable) {
        this.useEnable = useEnable;
    }

    public List<CodeTypeDTO> getList() {
        return list;
    }

    public void setList(List<CodeTypeDTO> list) {
        this.list = list;
    }

    public String getSortOrd() {
        return sortOrd;
    }

    public void setSortOrd(String sortOrd) {
        this.sortOrd = sortOrd;
    }

    public String getUptAuthCd() {
        return uptAuthCd;
    }

    public void setUptAuthCd(String uptAuthCd) {
        this.uptAuthCd = uptAuthCd;
    }
}
