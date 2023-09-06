/*
 * @(#)CommentDTO.java     2023-04-04 오전 9:17
 *
 * Copyright 2023 JAYU.space
 *
 * Licensed under the Apache License
 Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing
 software
 * distributed under the License is distributed on an "AS IS" BASIS

 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vertexid.support.comment;

import com.vertexid.viself.base.CmmDTO;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang
 *Ki Hwa)
 */
public class CommentDTO extends CmmDTO {
    private static final long serialVersionUID = -1422458449558971781L;

    private String cmtId;
    private String relId;
    private String relTpCd;
    private String cmtTitle;
    private String cmtConts;
    private String cmtRegId;
    private String cmtRegNm;
    private String cmtRegDte;
    private String parentUid;
    private String useYn;
    private String regDte;
    private String regLoginId;
    private String uptDte;
    private String uptLoginId;

    public CommentDTO() {
    }

    public String getCmtId() {
        return cmtId;
    }

    public void setCmtId(String cmtId) {
        this.cmtId = cmtId;
    }

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public String getRelTpCd() {
        return relTpCd;
    }

    public void setRelTpCd(String relTpCd) {
        this.relTpCd = relTpCd;
    }

    public String getCmtTitle() {
        return cmtTitle;
    }

    public void setCmtTitle(String cmtTitle) {
        this.cmtTitle = cmtTitle;
    }

    public String getCmtConts() {
        return cmtConts;
    }

    public void setCmtConts(String cmtConts) {
        this.cmtConts = cmtConts;
    }

    public String getCmtRegId() {
        return cmtRegId;
    }

    public void setCmtRegId(String cmtRegId) {
        this.cmtRegId = cmtRegId;
    }

    public String getCmtRegNm() {
        return cmtRegNm;
    }

    public void setCmtRegNm(String cmtRegNm) {
        this.cmtRegNm = cmtRegNm;
    }

    public String getCmtRegDte() {
        return cmtRegDte;
    }

    public void setCmtRegDte(String cmtRegDte) {
        this.cmtRegDte = cmtRegDte;
    }

    public String getParentUid() {
        return parentUid;
    }

    public void setParentUid(String parentUid) {
        this.parentUid = parentUid;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getRegDte() {
        return regDte;
    }

    public void setRegDte(String regDte) {
        this.regDte = regDte;
    }

    public String getRegLoginId() {
        return regLoginId;
    }

    public void setRegLoginId(String regLoginId) {
        this.regLoginId = regLoginId;
    }

    public String getUptDte() {
        return uptDte;
    }

    public void setUptDte(String uptDte) {
        this.uptDte = uptDte;
    }

    public String getUptLoginId() {
        return uptLoginId;
    }

    public void setUptLoginId(String uptLoginId) {
        this.uptLoginId = uptLoginId;
    }
}
