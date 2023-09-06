/*
 * @(#)SimpleUserDetailsSvc.java     2021-01-11(011) 오후 4:05
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

import com.vertexid.commons.utils.ParamMap;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.hr.UserMngSvc;
import com.vertexid.viself.security.AuthoritySvc;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static org.springframework.security.access.vote.AuthenticatedVoter.IS_AUTHENTICATED_FULLY;

/**
 * <b>Description</b>
 * <pre>
 *     기본 로그인 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service(value = "simpleUserDetailsSvc")
@Transactional
public class SimpleUserDetailsSvc extends BaseSvc implements UserDetailsService {

    @Resource
    UserMngSvc userMngSvc;

    @Resource(name = "simpleAuthoritySvc")
    AuthoritySvc authoritySvc;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        log.debug("username......." + username);

        // 1. 사용자 정보 얻기
        SimpleLoginVO loginVO = getUserInfo(username);

        // 2. 사용자 권한 설정
        if (null != loginVO) {
            loginVO.setAuthorities(getAuthorities(username));
        }

        if(log.isDebugEnabled()){
            log.debug("login info....."+loginVO.toString());
        }

        return loginVO;
    }

    private Collection<GrantedAuthority> getAuthorities(String username) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        List<Map<String, Object>> authList =
                authoritySvc.getAuthorities(username);
        for (Map<String, Object> authority : authList) {
            authorities.add(new SimpleGrantedAuthority(
                    authority.get("authCd").toString()));
        }// end of for
        authorities.add(new SimpleGrantedAuthority(IS_AUTHENTICATED_FULLY));

        return authorities;
    }

    private SimpleLoginVO getUserInfo(String username) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", username);

        ParamMap<String, Object> rtnMap = userMngSvc.getData(params);

        if (null == rtnMap || rtnMap.isEmpty()) {
            return null;
        }

        SimpleLoginVO loginVO = new SimpleLoginVO();
        loginVO.setUserId(rtnMap.getString("userId"));
        loginVO.setUserNm(rtnMap.getString("userNm"));
        loginVO.setUserPwd(rtnMap.getString("userPwd"));
        // HACK
        // setTempPwd 순서 주의! setUserPwd 다음에 와야함
        loginVO.setTempPwd(rtnMap.getString("tempPwd"));
        loginVO.setUserTpCd(rtnMap.getString("userTpCd"));
        loginVO.setUseEnable(rtnMap.getString("useEnable"));

        log.info("loginInfo......."+loginVO.toString());

        return loginVO;
    }
}
