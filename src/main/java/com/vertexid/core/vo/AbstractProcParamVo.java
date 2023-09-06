package com.vertexid.core.vo;

/**
 * @Class Name  : AbstractProcParamVo.java
 * @Description : 프로시져 기본 파라미터 처리용
 * 
 * @Modification Information  
 * @
 * @ Date           Author       Description
 * @ -------------  -----------  -------------
 * @ * @ 2023.09.06    윤태영    최초생성
 */
public abstract class AbstractProcParamVo {
	/** 언어코드 */
	private String langCd;
	/** 사용자 */
	private String usrId;
	/** 프로시져 실행결과 코드 */
	private String retnCd;
	/** 프로시져 실행결과 메시지 */
	private String retnMsg;
	/** 처리 성공 여부 */
	private String succYn;
	
	/** getter langCd */
	public String getLangCd() {
		return langCd;
	}
	/** setter langCd */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}
	/** getter usrId */
	public String getUsrId() {
		return usrId;
	}
	/** setter usrId */
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}
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
	/**
	 * @author 김정민
	 * @desc   기본 프로시져 처리 결과
	 * @return
	 */
	public ProcRtnVo getProcRtn() {
		ProcRtnVo rtn = new ProcRtnVo();
		rtn.setRetnCd(retnCd);
		rtn.setRetnMsg(retnMsg);
		rtn.setSuccYn(succYn);
		return rtn;
	}
}
