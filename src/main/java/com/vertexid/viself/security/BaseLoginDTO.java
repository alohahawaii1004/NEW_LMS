/*
 * @(#)BaseLoginDTO.java     2021-02-02(002) 오전 10:25
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
package com.vertexid.viself.security;

import com.vertexid.viself.base.CmmDTO;
import com.vertexid.viself.login.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * <b>Description</b>
 * <pre>
 *     UserDetails를 구현한 기본 Login DTO
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class BaseLoginDTO extends CmmDTO implements UserDetails {
    private static final long serialVersionUID = 5467335629827610875L;

    private String username;
    private String password;
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;
    private Collection<GrantedAuthority> authorities;

    private static class ChekAuthorityUtil {

        public static final String SEPARATOR = ",";

        public static boolean containsAuthorities(Collection<GrantedAuthority> authorities, String chkAuthorities){
            if(null == authorities || null == chkAuthorities){
                return false;
            }
            String [] chkArray = chkAuthorities.split(SEPARATOR);
            for(String chk: chkArray){
                if(authorities.contains(new SimpleGrantedAuthority(chk))){
                    return true;
                }
            }// end of for
            return false;
        }
    }

    public BaseLoginDTO() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    /**
     * 권한 여부 검사
     * @param authority 권한문자열(여러건일 경우 콤마','로 구분)
     * @return true 권한있음, other 권한 없음
     */
    public boolean containsAuthorities(String authority) {
        return ChekAuthorityUtil.containsAuthorities(this.authorities, authority);
    }

    public boolean containsAuthorities(GrantedAuthority authority) {
        if (null == authorities) {
            return false;
        }
        return authorities.contains(authority);
    }

    public boolean isSuperuser(){
        return containsAuthorities(RoleType.SUPER.getAuthority());
    }

    public boolean isDeveloper(){
        return containsAuthorities(RoleType.DEV.getAuthority());
    }
}
