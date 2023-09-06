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
 *     권한관리 DTO
 * </pre>
 *
 * @author 강세원
 */
public class AuthMenuDTO extends CmmDTO {


    private static final long serialVersionUID = 7003987569465246610L;

    private String authCd;
    private String menuId;
    private String alwCd;

    private List<AuthMenuDTO> list;

    public AuthMenuDTO() {
    }

    public String getAuthCd() {
        return authCd;
    }

    public void setAuthCd(String authCd) {
        this.authCd = authCd;
    }
    public String getMenuId() {
        return menuId;
    }
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
    public String getAlwCd() {
        return alwCd;
    }
    public void setAlwCd(String alwCd) {
        this.alwCd = alwCd;
    }

    public List<AuthMenuDTO> getList() {
        return list;
    }
    public void setList(List<AuthMenuDTO> list) {
        this.list = list;
    }

}
