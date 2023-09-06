package com.vertexid.core.vo;
import java.util.Date;

public abstract class AbstractVo {

	// audit 항목
	private String regUsrId;
	private Date regDt;
	private String updUsrId;
	private Date updDt;
	
	private String strRegDt;		//등록 일시(dd-MM-yyyy) 
	private String strUpdDt;		//수정 일시(dd-MM-yyyy)
	private String regUsrNm;		//등록자 명
	private String updUsrNm;		//수정자 명
	
	private String delYn; 			//삭제 여부
	private String useYn;			//사용 여부

	// page 항목
	private int pageSize;
	private int pageNo;

	// Nexacro Grid
	private String _chk; // 0: not check 1: check
	private String _rowtype; // C:Create U:Update D:Delete N:Normal or Null
	
	// client id, ip
	private static String clientIp;
	private static String clientId;
	
	/**
	 * mariadb limit 사용시  시작 번호(offset)
	 * @author 윤태영
	 * @desc 메소드설명
	 * @return
	 */
	private int getStartNo(){
		return (pageNo -1) * pageSize;
	}
	
	public String getRegUsrId() {
		return regUsrId;
	}
	public void setRegUsrId(String regUsrId) {
		this.regUsrId = regUsrId;
	}
	public Date getRegDt() {
		return regDt;
	}
	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}
	public String getUpdUsrId() {
		return updUsrId;
	}
	public void setUpdUsrId(String updUsrId) {
		this.updUsrId = updUsrId;
	}
	public Date getUpdDt() {
		return updDt;
	}
	public void setUpdDt(Date updDt) {
		this.updDt = updDt;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public String get_chk() {
		return _chk;
	}
	public void set_chk(String _chk) {
		this._chk = _chk;
	}
	public String get_rowtype() {
		return _rowtype;
	}
	public void set_rowtype(String _rowtype) {
		this._rowtype = _rowtype;
	}
	

	/** getter clientIp */
	public static String getClientIp() {
		return clientIp;
	}

	/** setter clientIp */
	public static void setClientIp(String clientIp) {
		AbstractVo.clientIp = clientIp;
	}

	/** getter clientId */
	public static String getClientId() {
		return clientId;
	}

	/** setter clientId */
	public static void setClientId(String clientId) {
		AbstractVo.clientId = clientId;
	}	

	public String getStrRegDt() {
		return strRegDt;
	}

	public void setStrRegDt(String strRegDt) {
		this.strRegDt = strRegDt;
	}

	public String getStrUpdDt() {
		return strUpdDt;
	}

	public void setStrUpdDt(String strUpdDt) {
		this.strUpdDt = strUpdDt;
	}

	public String getRegUsrNm() {
		return regUsrNm;
	}

	public void setRegUsrNm(String regUsrNm) {
		this.regUsrNm = regUsrNm;
	}

	public String getUpdUsrNm() {
		return updUsrNm;
	}

	public void setUpdUsrNm(String updUsrNm) {
		this.updUsrNm = updUsrNm;
	}
	
	@Override
	public String toString() {
		return "AbstractVo [regUsrId=" + regUsrId 
				+ ", regDt=" + regDt 
				+ ", updUsrId=" + updUsrId 
				+ ", updDt=" + updDt 
				+ ", pageSize=" + pageSize 
				+ ", pageNo=" + pageNo 
				+ ", _chk=" + _chk
				+ ", _rowtype=" + _rowtype
				+ ", strRegDt=" + strRegDt
				+ ", strUpdDt=" + strUpdDt
				+ ", regUsrNm=" + regUsrNm
				+ ", updUsrNm=" + updUsrNm
				+ "]";
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

}
