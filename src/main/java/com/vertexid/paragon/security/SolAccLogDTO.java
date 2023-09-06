/*
 * @(#)SolAccLogDTO.java     2022-07-25
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
package com.vertexid.paragon.security;

import com.vertexid.viself.base.CmmDTO;

/**
 * <b>Description</b>
 * <pre>
 *     솔루션(마스터 컨텐츠) 열람로그 DTO
 * </pre>
 *
 * @author SEOYOON
 */
public class SolAccLogDTO extends CmmDTO {
    private static final long serialVersionUID = 2026988324243960586L;

    private String logUid;
    private String tpCd;
    private String tit;
    private String mngNo;
    private String reqCorpCd;
    private String reqCorpNm;
    private String reqDeptCd;
    private String reqDeptNm;
    private String reqNm;
    private String reqId;
    private String accCorpCd;
    private String accCorpNm;
    private String accDeptCd;
    private String accDeptNm;
    private String accNm;
    private String accId;
    private String accDte;

    

    public String getReqNm() {
		return reqNm;
	}


	public void setReqNm(String reqNm) {
		this.reqNm = reqNm;
	}


	public String getReqId() {
		return reqId;
	}


	public void setReqId(String reqId) {
		this.reqId = reqId;
	}


	public String getAccCorpCd() {
		return accCorpCd;
	}


	public void setAccCorpCd(String accCorpCd) {
		this.accCorpCd = accCorpCd;
	}


	public String getAccCorpNm() {
		return accCorpNm;
	}


	public void setAccCorpNm(String accCorpNm) {
		this.accCorpNm = accCorpNm;
	}


	public String getAccDeptCd() {
		return accDeptCd;
	}


	public void setAccDeptCd(String accDeptCd) {
		this.accDeptCd = accDeptCd;
	}


	public String getAccDeptNm() {
		return accDeptNm;
	}


	public void setAccDeptNm(String accDeptNm) {
		this.accDeptNm = accDeptNm;
	}


	public String getAccNm() {
		return accNm;
	}


	public void setAccNm(String accNm) {
		this.accNm = accNm;
	}


	public String getAccId() {
		return accId;
	}


	public void setAccId(String accId) {
		this.accId = accId;
	}


	public String getAccDte() {
		return accDte;
	}


	public void setAccDte(String accDte) {
		this.accDte = accDte;
	}


	public SolAccLogDTO() {
    }


    public String getLogUid() {
		return logUid;
	}


	public void setLogUid(String logUid) {
		this.logUid = logUid;
	}


	public String getTpCd() {
        return tpCd;
    }

    public void setTpCd(String tpCd) {
        this.tpCd = tpCd;
    }

    public String getTit() {
        return tit;
    }

    public void setTit(String tit) {
        this.tit = tit;
    }


	public String getMngNo() {
		return mngNo;
	}


	public void setMngNo(String mngNo) {
		this.mngNo = mngNo;
	}


	public String getReqCorpCd() {
		return reqCorpCd;
	}


	public void setReqCorpCd(String reqCorpCd) {
		this.reqCorpCd = reqCorpCd;
	}


	public String getReqCorpNm() {
		return reqCorpNm;
	}


	public void setReqCorpNm(String reqCorpNm) {
		this.reqCorpNm = reqCorpNm;
	}


	public String getReqDeptCd() {
		return reqDeptCd;
	}


	public void setReqDeptCd(String reqDeptCd) {
		this.reqDeptCd = reqDeptCd;
	}


	public String getReqDeptNm() {
		return reqDeptNm;
	}


	public void setReqDeptNm(String reqDeptNm) {
		this.reqDeptNm = reqDeptNm;
	}

}
