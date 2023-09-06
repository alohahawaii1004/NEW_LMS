/*
 * @(#)IssueReqDTO.java     2023-03-30 오후 3:55
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
package com.vertexid.support.issue;

import com.vertexid.viself.base.CmmDTO;

import java.math.BigDecimal;

/**
 * <b>Description</b>
 * <pre>
 *     이슈요청-접수 DTO
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class IssueReqDTO extends CmmDTO {
    private static final long serialVersionUID = -5720151632871076890L;

    private String issueId;
    private String orgId;
    private String systemId;
    private String reqNo;
    private String issueStuCd;
    private String reqId;
    private String reqRealId;
    private String reqNm;
    private String reqEmail;
    private String reqDte;
    private String reqIssueTpCd;
    private String reqIssueDomainCd;
    private String reqEndDte;
    private String reqIssueTitle;
    private String reqIssueConts;
    private String rcvId;
    private String rcvDte;
    private String rcvIssueTpCd;
    private String rcvIssueDomainCd;
    private BigDecimal rcvExpManday;
    private String schedulEndDte;
    private String rcvComment;
    private String useYn;
    private String regDte;
    private String regLoginId;
    private String uptDte;
    private String uptLoginId;

    public IssueReqDTO() {
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getReqNo() {
        return reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }

    public String getIssueStuCd() {
        return issueStuCd;
    }

    public void setIssueStuCd(String issueStuCd) {
        this.issueStuCd = issueStuCd;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getReqRealId() {
        return reqRealId;
    }

    public void setReqRealId(String reqRealId) {
        this.reqRealId = reqRealId;
    }

    public String getReqNm() {
        return reqNm;
    }

    public void setReqNm(String reqNm) {
        this.reqNm = reqNm;
    }

    public String getReqEmail() {
        return reqEmail;
    }

    public void setReqEmail(String reqEmail) {
        this.reqEmail = reqEmail;
    }

    public String getReqDte() {
        return reqDte;
    }

    public void setReqDte(String reqDte) {
        this.reqDte = reqDte;
    }

    public String getReqIssueTpCd() {
        return reqIssueTpCd;
    }

    public void setReqIssueTpCd(String reqIssueTpCd) {
        this.reqIssueTpCd = reqIssueTpCd;
    }

    public String getReqIssueDomainCd() {
        return reqIssueDomainCd;
    }

    public void setReqIssueDomainCd(String reqIssueDomainCd) {
        this.reqIssueDomainCd = reqIssueDomainCd;
    }

    public String getReqEndDte() {
        return reqEndDte;
    }

    public void setReqEndDte(String reqEndDte) {
        this.reqEndDte = reqEndDte;
    }

    public String getReqIssueTitle() {
        return reqIssueTitle;
    }

    public void setReqIssueTitle(String reqIssueTitle) {
        this.reqIssueTitle = reqIssueTitle;
    }

    public String getReqIssueConts() {
        return reqIssueConts;
    }

    public void setReqIssueConts(String reqIssueConts) {
        this.reqIssueConts = reqIssueConts;
    }

    public String getRcvId() {
        return rcvId;
    }

    public void setRcvId(String rcvId) {
        this.rcvId = rcvId;
    }

    public String getRcvDte() {
        return rcvDte;
    }

    public void setRcvDte(String rcvDte) {
        this.rcvDte = rcvDte;
    }

    public String getRcvIssueTpCd() {
        return rcvIssueTpCd;
    }

    public void setRcvIssueTpCd(String rcvIssueTpCd) {
        this.rcvIssueTpCd = rcvIssueTpCd;
    }

    public String getRcvIssueDomainCd() {
        return rcvIssueDomainCd;
    }

    public void setRcvIssueDomainCd(String rcvIssueDomainCd) {
        this.rcvIssueDomainCd = rcvIssueDomainCd;
    }

    public BigDecimal getRcvExpManday() {
        return rcvExpManday;
    }

    public void setRcvExpManday(BigDecimal rcvExpManday) {
        this.rcvExpManday = rcvExpManday;
    }

    public String getSchedulEndDte() {
        return schedulEndDte;
    }

    public void setSchedulEndDte(String schedulEndDte) {
        this.schedulEndDte = schedulEndDte;
    }

    public String getRcvComment() {
        return rcvComment;
    }

    public void setRcvComment(String rcvComment) {
        this.rcvComment = rcvComment;
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
