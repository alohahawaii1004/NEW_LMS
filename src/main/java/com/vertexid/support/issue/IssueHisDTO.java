/*
 * @(#)IssueHisDTO.java     2023-04-03 오후 2:20
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

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class IssueHisDTO extends CmmDTO {
    private static final long serialVersionUID = 4854422801659203943L;

    private String issueId;
    private String hisDte;
    private String actor;
    private String issueStuCd;
    private String content;
    private String requestParam;

    public IssueHisDTO() {
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getHisDte() {
        return hisDte;
    }

    public void setHisDte(String hisDte) {
        this.hisDte = hisDte;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getIssueStuCd() {
        return issueStuCd;
    }

    public void setIssueStuCd(String issueStuCd) {
        this.issueStuCd = issueStuCd;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }
}
