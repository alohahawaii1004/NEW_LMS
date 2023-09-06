/*
 * @(#)RoleType.java     2021-02-02(002) 오전 10:27
 *
 * Copyright 2021 JAYU.space
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
package com.vertexid.viself.login;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * <b>Description</b>
 * <pre>
 *     시스템에서 기본으로 제공하고 변경불가한 권한 유형
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public enum RoleType {
    /**
     *  개발자
     */
    DEV("DEV"),
    /**
     * 슈퍼유저
     */
    SUPER("SUPER"),
    /**
     * 게스트
     */
    GUEST("GUEST");

    private final String authority;

    RoleType(String authority) {
        this.authority = authority;
    }

    public GrantedAuthority findBy(String authority){
        for (RoleType roleType : values()) {
            if (StringUtils.equals(roleType.toAuthorityString(), authority) ||
                    StringUtils.containsIgnoreCase(authority,
                            roleType.toAuthorityString())) {
                return roleType.getAuthority();
            }
        }// end of for

        return null;
    }

    public String toAuthorityString(){
        return authority;
    }

    public GrantedAuthority getAuthority(){
        return new SimpleGrantedAuthority(authority);
    }
}
