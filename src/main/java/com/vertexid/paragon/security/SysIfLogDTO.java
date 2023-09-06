/*
 * @(#)SysIfLogDTO.java     2023-01-03
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
 *     인터페이스 로그 DTO
 * </pre>
 *
 * @author SEOYOON
 */
public class SysIfLogDTO extends CmmDTO {
    private static final long serialVersionUID = 2026988324243960586L;

    private String ifId;
    private String ifItemCd;
    private String ifRstCd;
    private String opertParam;
    private String opertRst;
    private String opertStage;
    private String rstDtl;
	public String getIfId() {
		return ifId;
	}
	public void setIfId(String ifId) {
		this.ifId = ifId;
	}
	public String getIfItemCd() {
		return ifItemCd;
	}
	public void setIfItemCd(String ifItemCd) {
		this.ifItemCd = ifItemCd;
	}
	public String getIfRstCd() {
		return ifRstCd;
	}
	public void setIfRstCd(String ifRstCd) {
		this.ifRstCd = ifRstCd;
	}
	public String getOpertParam() {
		return opertParam;
	}
	public void setOpertParam(String opertParam) {
		this.opertParam = opertParam;
	}
	public String getOpertRst() {
		return opertRst;
	}
	public void setOpertRst(String opertRst) {
		this.opertRst = opertRst;
	}
	public String getOpertStage() {
		return opertStage;
	}
	public void setOpertStage(String opertStage) {
		this.opertStage = opertStage;
	}
	public String getRstDtl() {
		return rstDtl;
	}
	public void setRstDtl(String rstDtl) {
		this.rstDtl = rstDtl;
	}
    
}
