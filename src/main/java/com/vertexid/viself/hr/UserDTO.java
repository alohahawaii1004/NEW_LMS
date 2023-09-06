/*
 * @(#)UserDTO.java     
 *
 * Copyright 2022 JAYU.space
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

package com.vertexid.viself.hr;

import java.util.List;

import com.vertexid.viself.base.CmmDTO;

/**
 * 사용자 DTO
 * @author 양기화(梁起華: Yang, Ki Hwa)
 */
public class UserDTO extends CmmDTO {
    private static final long serialVersionUID = 4635589865784563353L;

    /**
     * 사용자 아이디
     */
    private String userId;

    /**
     * 사용자 이름
     */
    private String userNm;
    
    /**
     * 사용자 이름
     */
    private String userNmEn;

    /**
     * 사용자유형
     */
    private String userTpCd;
    
    /**
     * 사용자 국적명
     */
    private String natNm;

    /**
     * 사용자 사용여부
     */
    private String useEnable;

    private String userPwd;

    private String tempPwd;
    //변경할 pw
    private String chgPwd;

    private List<UserDTO> list;

    public UserDTO() {
    }

    public UserDTO(String userId, String userNm) {
        this.userId = userId;
        this.userNm = userNm;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getUserTpCd() {
        return userTpCd;
    }

    public void setUserTpCd(String userTpCd) {
        this.userTpCd = userTpCd;
    }

    public String getUseEnable() {
        return useEnable;
    }

    public void setUseEnable(String useEnable) {
        this.useEnable = useEnable;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getTempPwd() {
        return tempPwd;
    }

    public void setTempPwd(String tempPwd) {
        this.tempPwd = tempPwd;
    }

    public List<UserDTO> getList() {
        return list;
    }

    public void setList(List<UserDTO> list) {
        this.list = list;
    }

	public String getChgPwd() {
		return chgPwd;
	}

	public void setChgPwd(String chgPwd) {
		this.chgPwd = chgPwd;
	}

	public String getUserNmEn() {
		return userNmEn;
	}

	public void setUserNmEn(String userNmEn) {
		this.userNmEn = userNmEn;
	}

	public String getNatNm() {
		return natNm;
	}

	public void setNatNm(String natNm) {
		this.natNm = natNm;
	}


}
