/*
 * @(#)CodeDTO.java     2020-09-24(024) 오후 2:21
 *
 * Copyright 2020 JAYU.space
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
package com.vertexid.viself.auth;

import com.vertexid.viself.base.CmmDTO;

import java.util.List;

/**
 * <b>Description</b>
 * <pre>
 *     권한 사용자 DTO
 * </pre>
 *
 * @author 강세원
 */
public class AuthMemberDTO extends CmmDTO {


    private static final long serialVersionUID = -1133325854937034333L;

    private String authCd;
    private String mbrTpCd;
    private String mbrId;
    private String userNm;

    private List<AuthMemberDTO> list;

    public AuthMemberDTO() {
    }

    public String getAuthCd() {
        return authCd;
    }

    public void setAuthCd(String authCd) {
        this.authCd = authCd;
    }

    public String getMbrTpCd() {
        return mbrTpCd;
    }

    public void setMbrTpCd(String mbrTpCd) {
        this.mbrTpCd = mbrTpCd;
    }

    public String getMbrId() {
        return mbrId;
    }

    public void setMbrId(String mbrId) {
        this.mbrId = mbrId;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public List<AuthMemberDTO> getList() {
        return list;
    }

    public void setList(List<AuthMemberDTO> list) {
        this.list = list;
    }



}
