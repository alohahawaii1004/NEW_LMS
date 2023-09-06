package com.vertexid.core.vo;


/**
 * @Class Name  : ProcRtnVo.java
 * @Description : 프로시져 처리 결과
 * 
 * @Modification Information  
 * @
 * @ Date           Author       Description
 * @ -------------  -----------  -------------
 * @ * @ 2023.09.06    윤태영    최초생성
 */
public class ProcRtnVo {
	/** 프로시져 실행결과 코드 */
	private String retnCd;
	/** 프로시져 실행결과 메시지 */
	private String retnMsg;
	/** 처리 성공 여부 */
	private String succYn;
	
	/** getter retnCd */
	public String getRetnCd() {
		return retnCd;
	}
	/** setter retnCd */
	public void setRetnCd(String retnCd) {
		this.retnCd = retnCd;
	}
	/** getter retnMsg */
	public String getRetnMsg() {
		return retnMsg;
	}
	/** setter retnMsg */
	public void setRetnMsg(String retnMsg) {
		this.retnMsg = retnMsg;
	}
	/** getter succYn */
	public String getSuccYn() {
		return succYn;
	}
	/** setter succYn */
	public void setSuccYn(String succYn) {
		this.succYn = succYn;
	}
}
