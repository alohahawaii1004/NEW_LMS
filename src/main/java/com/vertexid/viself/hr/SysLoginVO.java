package com.vertexid.viself.hr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;
import com.vertexid.spring.utils.BaseProperties;
import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.security.BaseLoginDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SysLoginVO extends BaseLoginDTO {
    private static final long serialVersionUID = -5276392619627642679L;

    public SysLoginVO() {
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * 접속 시스템 유형
     */


    private String name = "";
    private String addr = "";
    private String companyName = "";
    private String groupName = "";
    private String dutyName = "";
    private String posName = "";
    private String userTpName = "";
    private String dspName = "";

    public void setDspName(String dspName) {
        this.dspName = dspName;
    }

    public String getName() {
        if ("Y".equals(BaseProperties.get("fnc.userNameDisplay.useYn"))) {
            return dspName;
        } else {
            return name;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String positionName) {
        this.posName = positionName;
    }

    public String getUserTypeName() {
        return userTpName;
    }

    public void setUserTpName(String userTypeName) {
        this.userTpName = userTypeName;
    }


    // 사용자 번호 사번
    private String userNo = "";

    // 부서 코드
    private String deptCd = "";

    // 로그인 ID
    private String loginId = "";

    // 로그인 비밀번호
    private String loginPwd = "";

    // 명칭 한글
    private String nmKo = "";

    // 명칭 영문
    private String nmEn = "";

    // 명칭 일문
    private String nmJa = "";

    // 명칭 중문
    private String nmZh = "";

    // 표시 명칭 한글
    private String dspNmKo = "";

    // 표시 명칭 영문
    private String dspNmEn = "";

    // 표시 명칭 일문
    private String dspNmJa = "";

    // 표시 명칭 중문
    private String dspNmZh = "";

    // 주민 등록 번호 주민번호
    private String residentRegNo = "";

    // 이메일
    private String email = "";

    // 전화 번호
    private String telNo = "";

    // 모바일 번호
    private String mobileNo = "";

    // 주소 한글
    private String addrKo = "";

    // 주소 영문
    private String addrEn = "";

    // 주소 일문
    private String addrJa = "";

    // 주소 중문
    private String addrZh = "";

    // 직급 코드
    private String posCd = "";

    // 직급 언어 코드
    private String posLangCd = "";

    // 직책 코드
    private String dutyCd = "";

    // 직책 언어 코드
    private String dutyLangCd = "";

    // 사용자 유형
    private String userTpCd = "";

    // 사용자 유형 언어 코드
    private String userTpLangCd = "";

    // 부서 명칭 한글
    private String deptNmKo = "";

    // 부서 명칭 영문
    private String deptNmEn = "";

    // 부서 명칭 일문
    private String deptNmJa = "";

    // 부서 명칭 중문
    private String deptNmZh = "";

    // 사업장 코드 외부일일경우 사업장 코드
    private String corpCd = "";

    // 사업장 코드 외부일일경우 사업장 코드
    private String corpNm = "";

    // 사업장 유형 외부 사용자일때 사업장 유형
    private String corpType = "";

    // 부서 코드 경로
    private String deptCdPath = "";

    // 부서 명칭 경로 한글
    private String deptNmPathKo = "";

    // 부서 명칭 경로 영문
    private String deptNmPathEn = "";

    // 부서 명칭 경로 일문
    private String deptNmPathJa = "";

    // 부서 명칭 경로 중문
    private String deptNmPathZh = "";

    // 팀장 YN
    private String chiefYn = "";

    // 성별코드
    private String sexCd = "";

    // 성별코드명
    private String sexLangCd = "";

    // 사이트 로케일
    private String siteLocale = "";

    // 권한 코드
    private String authCd = "";

    // 권한 코드
    private String authLangCd = "";

    // 사용 여부
    private String useYn = "";

    // 로그인 아이피
    private String loginIp = "";

    // 암호 시작 일자
    private String pwdStDte = "";

    // 암호 종료 일자
    private String pwdEdDte = "";

    // 등록 일자
    private String regDte = "";

    // 등록 ID
    private String regLoginId = "";

    // 수정 일자
    private String uptDte = "";

    // 수정 ID
    private String uptLoginId = "";

    // 국적 코드
    private String natCd = "";

    // 국정 명칭
    private String natNm = "";

    // 시스템담당 권한
    private boolean isSys ;

    // 특허팀 담당자 권한
    private boolean isChr ;

    // 산학협력단 사용자 권한
    private boolean isTju ;

    // 기술사업팀 사용자 권한
    private boolean isSmk ;

    // 비용 담당자 권한
    private boolean isBIChr ;

    // 사무소 권한
    private boolean isAgc ;


    // 일반사용자 권한
    private boolean isIsj ;

    // 심의위원 권한
    private boolean isSim ;

    // 리턴 ID
    private String returnId ;

    private String cert;



    public String getAuthLangCd() {
		return authLangCd;
	}

	public void setAuthLangCd(String authLangCd) {
		this.authLangCd = authLangCd;
	}

	public String getDspName() {
		return dspName;
	}

	public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getDeptCd() {
        return deptCd;
    }

    public void setDeptCd(String deptCd) {
        this.deptCd = deptCd;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getNmKo() {
        return nmKo;
    }

    public void setNmKo(String nmKo) {
        this.nmKo = nmKo;
    }

    public String getNmEn() {
        return nmEn;
    }

    public void setNmEn(String nmEn) {
        this.nmEn = nmEn;
    }

    public String getNmJa() {
        return nmJa;
    }

    public void setNmJa(String nmJa) {
        this.nmJa = nmJa;
    }

    public String getNmZh() {
        return nmZh;
    }

    public void setNmZh(String nmZh) {
        this.nmZh = nmZh;
    }

    public String getDspNmKo() {
		return dspNmKo;
	}

	public void setDspNmKo(String dspNmKo) {
		this.dspNmKo = dspNmKo;
	}

	public String getDspNmEn() {
		return dspNmEn;
	}

	public void setDspNmEn(String dspNmEn) {
		this.dspNmEn = dspNmEn;
	}

	public String getDspNmJa() {
		return dspNmJa;
	}

	public void setDspNmJa(String dspNmJa) {
		this.dspNmJa = dspNmJa;
	}

	public String getDspNmZh() {
		return dspNmZh;
	}

	public void setDspNmZh(String dspNmZh) {
		this.dspNmZh = dspNmZh;
	}

	public String getResidentRegNo() {
        return residentRegNo;
    }

    public void setResidentRegNo(String residentRegNo) {
        this.residentRegNo = residentRegNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddrKo() {
        return addrKo;
    }

    public void setAddrKo(String addrKo) {
        this.addrKo = addrKo;
    }

    public String getAddrEn() {
        return addrEn;
    }

    public void setAddrEn(String addrEn) {
        this.addrEn = addrEn;
    }

    public String getAddrJa() {
        return addrJa;
    }

    public void setAddrJa(String addrJa) {
        this.addrJa = addrJa;
    }

    public String getAddrZh() {
        return addrZh;
    }

    public void setAddrZh(String addrZh) {
        this.addrZh = addrZh;
    }

    public String getPosCd() {
        return posCd;
    }

    public void setPosCd(String positionCd) {
        this.posCd = positionCd;
    }

    public String getPosLangCd() {
        return posLangCd;
    }

    public void setPosLangCd(String positionLangCd) {
        this.posLangCd = positionLangCd;
    }

    public String getDutyCd() {
        return dutyCd;
    }

    public void setDutyCd(String dutyCd) {
        this.dutyCd = dutyCd;
    }

    public String getDutyLangCd() {
        return dutyLangCd;
    }

    public void setDutyLangCd(String dutyLangCd) {
        this.dutyLangCd = dutyLangCd;
    }

    public String getUserTpCd() {
        return userTpCd;
    }

    public void setUserType(String userTpCd) {
        this.userTpCd = userTpCd;
    }

    public String getUserTpLangCd() {
        return userTpLangCd;
    }

    public void setUserTypeLangCd(String userTpLangCd) {
        this.userTpLangCd = userTpLangCd;
    }

    public String getDeptNmKo() {
        return deptNmKo;
    }

    public void setDeptNmKo(String deptNmKo) {
        this.deptNmKo = deptNmKo;
    }

    public String getDeptNmEn() {
        return deptNmEn;
    }

    public void setDeptNmEn(String deptNmEn) {
        this.deptNmEn = deptNmEn;
    }

    public String getDeptNmJa() {
        return deptNmJa;
    }

    public void setDeptNmJa(String deptNmJa) {
        this.deptNmJa = deptNmJa;
    }

    public String getDeptNmZh() {
        return deptNmZh;
    }

    public void setDeptNmZh(String deptNmZh) {
        this.deptNmZh = deptNmZh;
    }

    public String getCorpCd() {
        return corpCd;
    }

    public void setCorpCd(String corpCd) {
        this.corpCd = corpCd;
    }

    public String getCorpNm() {
    	return corpNm;
    }

    public void setCorpNm(String corpNm) {
    	this.corpNm = corpNm;
    }

    public String getCorpType() {
        return corpType;
    }

    public void setCorpType(String corpType) {
        this.corpType = corpType;
    }

    public String getDeptCdPath() {
        return deptCdPath;
    }

    public void setDeptCdPath(String deptCdPath) {
        this.deptCdPath = deptCdPath;
    }

    public String getDeptNmPathKo() {
        return deptNmPathKo;
    }

    public void setDeptNmPathKo(String deptNmPathKo) {
        this.deptNmPathKo = deptNmPathKo;
    }

    public String getDeptNmPathEn() {
        return deptNmPathEn;
    }

    public void setDeptNmPathEn(String deptNmPathEn) {
        this.deptNmPathEn = deptNmPathEn;
    }

    public String getDeptNmPathJa() {
        return deptNmPathJa;
    }

    public void setDeptNmPathJa(String deptNmPathJa) {
        this.deptNmPathJa = deptNmPathJa;
    }

    public String getDeptNmPathZh() {
        return deptNmPathZh;
    }

    public void setDeptNmPathZh(String deptNmPathZh) {
        this.deptNmPathZh = deptNmPathZh;
    }

    public String getChiefYn() {
        return chiefYn;
    }

    public void setChiefYn(String chiefYn) {
        this.chiefYn = chiefYn;
    }

    public String getSexCd() {
		return sexCd;
	}

	public void setSexCd(String sexCd) {
		this.sexCd = sexCd;
	}

	public String getSexLangCd() {
		return sexLangCd;
	}

	public void setSexLangCd(String sexLangCd) {
		this.sexLangCd = sexLangCd;
	}

	public String getSiteLocale() {
        return siteLocale;
    }

    public void setSiteLocale(String siteLocale) {
        this.siteLocale = siteLocale;
    }

    public String getAuthCd() {
        return authCd;
    }

    public void setAuthCd(String authCd) {
        this.authCd = authCd;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useEnable) {
        this.useYn = useEnable;
    }


    public String getLoginIp() {
        if (StringUtils.isEmpty(loginIp)) {
            loginIp = SessionUtils.getRemoteIP();
        }

        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
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


    public String getNatCd() {
		return natCd;
	}

	public void setNatCd(String natCd) {
		this.natCd = natCd;
	}

	public String getNatNm() {
		return natNm;
	}

	public void setNatNm(String natNm) {
		this.natNm = natNm;
	}

	public String getUserTpName() {
		return userTpName;
	}

	public void setUserTpCd(String userTpCd) {
		this.userTpCd = userTpCd;
	}

	public void setUserTpLangCd(String userTpLangCd) {
		this.userTpLangCd = userTpLangCd;
	}

	public String getPwdStDte() {
		return pwdStDte;
	}

	public void setPwdStDte(String pwdStDte) {
		this.pwdStDte = pwdStDte;
	}

	public String getPwdEdDte() {
		return pwdEdDte;
	}

	public void setPwdEdDte(String pwdEdDte) {
		this.pwdEdDte = pwdEdDte;
	}

    // VSysUser 모델 복사
    public void CopyData(SysLoginVO param) {
        this.userNo = param.getUserNo();
        this.deptCd = param.getDeptCd();
        this.loginId = param.getLoginId();
        this.loginPwd = param.getLoginPwd();
        this.nmKo = param.getNmKo();
        this.nmEn = param.getNmEn();
        this.nmJa = param.getNmJa();
        this.nmZh = param.getNmZh();
        this.residentRegNo = param.getResidentRegNo();
        this.email = param.getEmail();
        this.telNo = param.getTelNo();
        this.mobileNo = param.getMobileNo();
        this.addrKo = param.getAddrKo();
        this.addrEn = param.getAddrEn();
        this.addrJa = param.getAddrJa();
        this.addrZh = param.getAddrZh();
        this.posCd = param.getPosCd();
        this.posLangCd = param.getPosLangCd();
        this.dutyCd = param.getDutyCd();
        this.dutyLangCd = param.getDutyLangCd();
        this.userTpCd = param.getUserTpCd();
        this.userTpLangCd = param.getUserTpLangCd();
        this.deptNmKo = param.getDeptNmKo();
        this.deptNmEn = param.getDeptNmEn();
        this.deptNmJa = param.getDeptNmJa();
        this.deptNmZh = param.getDeptNmZh();
        this.corpCd = param.getCorpCd();
        this.corpType = param.getCorpType();
        this.deptCdPath = param.getDeptCdPath();
        this.deptNmPathKo = param.getDeptNmPathKo();
        this.deptNmPathEn = param.getDeptNmPathEn();
        this.deptNmPathJa = param.getDeptNmPathJa();
        this.deptNmPathZh = param.getDeptNmPathZh();
        this.chiefYn = param.getChiefYn();
        this.siteLocale = param.getSiteLocale();
        this.authCd = param.getAuthCd();
        this.useYn = param.getUseYn();
        this.loginIp = param.getLoginIp();
        this.pwdStDte = param.getPwdStDte();
        this.pwdEdDte = param.getPwdEdDte();
        this.regDte = param.getRegDte();
        this.regLoginId = param.getRegLoginId();
        this.uptDte = param.getUptDte();
        this.uptLoginId = param.getUptLoginId();
    }

    /**
     * 사이트 로케일 변경 처리
     *
     * @param SiteLocale
     */
    public void changeSiteLocale(String SiteLocale) {
    	setSiteLocale(SiteLocale);

    	if ("EN".equals(getSiteLocale())) {			//영문
    		setName(getNmEn());
    		setDspName(getDspNmEn());
    		setAddr(getAddrEn());
    		setGroupName(getDeptNmEn());
    		setCompanyName(getDeptNmEn());

    	}else if ("JA".equals(getSiteLocale())) { 	//일문
    		setName(getNmJa());
    		setDspName(getDspNmJa());
    		setAddr(getAddrJa());
    		setGroupName(getDeptNmJa());
    		setCompanyName(getDeptNmJa());

    	}else if ("ZH".equals(getSiteLocale())) {	//중문
    		setName(getNmZh());
    		setDspName(getDspNmZh());
    		setAddr(getAddrZh());
    		setGroupName(getDeptNmZh());
    		setCompanyName(getDeptNmZh());

    	}else {
    		setName(getNmKo());
    		setDspName(getDspNmKo());
    		setAddr(getAddrKo());
    		setGroupName(getDeptNmKo());
    		setCompanyName(getDeptNmKo());

    	}
    }//end changeSiteLocale()


    /**
     * 사용자 권한 - 현재 로그인한 사용자의 권한 유무 반환
     * @param authCodes 사용자 권한 코드 (※ 여러 개일 경우 쉼표[,]로 구분하여 입력)
     * @return
     */
    public boolean isUserAuth(String authCodes) {
    	if ( StringUtils.isEmpty(authCodes)) return false;

		String[] authCodeArr = authCodes.split(",");
		String[] arrAuth	 = this.authCd.split(",");
//		log.debug("chk:"+ authCodes);
//		log.debug("auth:"+ this.authCd);
		boolean bool = false;
		for (int i = 0; i < authCodeArr.length; i++) {

			if(StringUtils.isEmpty(authCodeArr[i])) continue;
//
			log.debug("authChk IDX:"+ Arrays.binarySearch(arrAuth, authCodeArr[i]));

			bool = (Arrays.binarySearch(arrAuth, authCodeArr[i]) > -1);
//			log.debug("authChk bool:"+ bool);

			if(bool) break;
		}

		return bool;
    }

    /**
     * 시스템 담당자여부
     */
	public boolean isSys() {
		return isSys;
	}
	public void setSys(boolean isSys) {
		this.isSys = isSys;
	}

	/**
	 * 특허담당자여부
	 * @return
	 */
	public boolean isChr() {
		return isChr;
	}
	public void setChr(boolean isChr) {
		this.isChr = isChr;
	}

	/**
	 * 비용담당자여부
	 * @return
	 */
	public boolean isBIChr() {
		return isBIChr;
	}
	public void setBIChr(boolean isBIChr) {
		this.isBIChr = isBIChr;
	}

	/**
	 * 사무소담당자여부
	 * @return
	 */
	public boolean isAgc() {
		return isAgc;
	}
	public void setAgc(boolean isAgc) {
		this.isAgc = isAgc;
	}


	public boolean isTju() {
		return isTju;
	}

	public void setTju(boolean isTju) {
		this.isTju = isTju;
	}

	public boolean isSmk() {
		return isSmk;
	}

	public void setSmk(boolean isSmk) {
		this.isSmk = isSmk;
	}

	/**
	 * 순수 일반사용자 여부
	 * @return
	 */
	public boolean isIsj() {
		return isIsj;
	}
	public void setIsj(boolean isIsj) {
		this.isIsj = isIsj;
	}

	/**
	 * 순수 일반사용자 여부
	 * @return
	 */
	public boolean isSim() {
		return isSim;
	}
	public void setSim(boolean isSim) {
		this.isSim = isSim;
	}

	public String sessionId;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String session_id) {
		this.sessionId = session_id;
	}

	public String getReturnId() {
		return returnId;
	}

	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }
}
