/*
 * @(#)IssueRsltDTO.java     2023-04-03 오전 9:55
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
package com.vertexid.support.issue;

import com.vertexid.viself.base.CmmDTO;

import java.math.BigDecimal;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang
 *Ki Hwa)
 */
public class IssueRsltDTO extends CmmDTO {
    private static final long serialVersionUID = -350086799038796428L;

    private String issueId;
    private String rsltId;
    private String rsltDte;
    private BigDecimal rsltRealManday;
    private String rsltRport;
    private String rsltRealIssueTpCd;
    private String rsltRealIssueDomainCd;
    private String cfmId;
    private String cfmDte;
    private String cfmSvcVal;
    private String cfmComment;
    private String cfmTpCd;
    private String regDte;
    private String regLoginId;
    private String uptDte;
    private String uptLoginId;

    public IssueRsltDTO() {
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getRsltId() {
        return rsltId;
    }

    public void setRsltId(String rsltId) {
        this.rsltId = rsltId;
    }

    public String getRsltDte() {
        return rsltDte;
    }

    public void setRsltDte(String rsltDte) {
        this.rsltDte = rsltDte;
    }

    public BigDecimal getRsltRealManday() {
        return rsltRealManday;
    }

    public void setRsltRealManday(BigDecimal rsltRealManday) {
        this.rsltRealManday = rsltRealManday;
    }

    public String getRsltRport() {
        return rsltRport;
    }

    public void setRsltRport(String rsltRport) {
        this.rsltRport = rsltRport;
    }

    public String getRsltRealIssueTpCd() {
        return rsltRealIssueTpCd;
    }

    public void setRsltRealIssueTpCd(String rsltRealIssueTpCd) {
        this.rsltRealIssueTpCd = rsltRealIssueTpCd;
    }

    public String getRsltRealIssueDomainCd() {
        return rsltRealIssueDomainCd;
    }

    public void setRsltRealIssueDomainCd(String rsltRealIssueDomainCd) {
        this.rsltRealIssueDomainCd = rsltRealIssueDomainCd;
    }

    public String getCfmId() {
        return cfmId;
    }

    public void setCfmId(String cfmId) {
        this.cfmId = cfmId;
    }

    public String getCfmDte() {
        return cfmDte;
    }

    public void setCfmDte(String cfmDte) {
        this.cfmDte = cfmDte;
    }

    public String getCfmSvcVal() {
        return cfmSvcVal;
    }

    public void setCfmSvcVal(String cfmSvcVal) {
        this.cfmSvcVal = cfmSvcVal;
    }

    public String getCfmComment() {
        return cfmComment;
    }

    public void setCfmComment(String cfmComment) {
        this.cfmComment = cfmComment;
    }

    public String getCfmTpCd() {
        return cfmTpCd;
    }

    public void setCfmTpCd(String cfmTpCd) {
        this.cfmTpCd = cfmTpCd;
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
