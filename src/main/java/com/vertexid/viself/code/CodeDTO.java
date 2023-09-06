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
package com.vertexid.viself.code;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.vertexid.viself.base.CmmDTO;

/**
 * <b>Description</b>
 * <pre>
 *     상세코드 DTO
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class CodeDTO extends CmmDTO {

    private static final long serialVersionUID = -1624397074530410479L;

    private String cd;
    private String ordNo;
    private String cdAbb;
    private String langCd;
    private String parentCd;
    private String cdAttr1;
    private String cdAttr2;
    private String cdAttr3;
    private String cdAttr4;
    private String attrDesc1;
    private String attrDesc2;
    private String attrDesc3;
    private String attrDesc4;
    private String cdData;
    private String useYn;
    private String inCds;
    private String inCdAbbs;

    private List<CodeDTO> list;

    public CodeDTO() {
    }



    public String getCd() {
        return cd;
    }



    public void setCd(String cd) {
        this.cd = cd;
    }



    public String getOrdNo() {
        return ordNo;
    }



    public void setOrdNo(String ordNo) {
        this.ordNo = ordNo;
    }



    public String getCdAbb() {
        return cdAbb;
    }



    public void setCdAbb(String cdAbb) {
        this.cdAbb = cdAbb;
    }



    public String getLangCd() {
        return langCd;
    }



    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }



    public String getParentCd() {
        return parentCd;
    }



    public void setParentCd(String parentCd) {
        this.parentCd = StringUtils.defaultIfBlank(parentCd, null);
    }



    public String getCdAttr1() {
        return cdAttr1;
    }



    public void setCdAttr1(String cdAttr1) {
        this.cdAttr1 = cdAttr1;
    }



    public String getCdAttr2() {
        return cdAttr2;
    }



    public void setCdAttr2(String cdAttr2) {
        this.cdAttr2 = cdAttr2;
    }



    public String getCdAttr3() {
        return cdAttr3;
    }



    public void setCdAttr3(String cdAttr3) {
        this.cdAttr3 = cdAttr3;
    }



    public String getCdAttr4() {
        return cdAttr4;
    }



    public void setCdAttr4(String cdAttr4) {
        this.cdAttr4 = cdAttr4;
    }



    public String getAttrDesc1() {
        return attrDesc1;
    }



    public void setAttrDesc1(String attrDesc1) {
        this.attrDesc1 = attrDesc1;
    }



    public String getAttrDesc2() {
        return attrDesc2;
    }



    public void setAttrDesc2(String attrDesc2) {
        this.attrDesc2 = attrDesc2;
    }



    public String getAttrDesc3() {
        return attrDesc3;
    }



    public void setAttrDesc3(String attrDesc3) {
        this.attrDesc3 = attrDesc3;
    }



    public String getAttrDesc4() {
        return attrDesc4;
    }



    public void setAttrDesc4(String attrDesc4) {
        this.attrDesc4 = attrDesc4;
    }



    public String getCdData() {
        return cdData;
    }



    public void setCdData(String cdData) {
        this.cdData = cdData;
    }



    public String getUseYn() {
        return useYn;
    }



    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public List<CodeDTO> getList() {
        return list;
    }

    public void setList(List<CodeDTO> list) {
        this.list = list;
    }



    public String[] getInCds() {
        return StringUtils.split(inCds,",");
    }



    public void setInCds(String inCds) {
        this.inCds = inCds;
    }



    public String[] getInCdAbbs() {
        return StringUtils.split(inCdAbbs,",");
    }



    public void setInCdAbbs(String inCdAbbs) {
        this.inCdAbbs = inCdAbbs;
    }


}
