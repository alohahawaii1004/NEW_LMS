/*
 * @(#)DefaultUserDetailSvc.java     2022-02-10(010) 오전 7:53
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
package com.vertexid.viself.login;

import com.vertexid.commons.utils.JsonUtil;
import com.vertexid.commons.utils.ParamMap;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.hr.SysLoginVO;
import com.vertexid.viself.hr.UserMngSvc;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

import static org.springframework.security.access.vote.AuthenticatedVoter.IS_AUTHENTICATED_FULLY;

/**
 * <b>Description</b>
 * <pre>
 *     기본 사용자 상세 정보 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class DefaultUserDetailSvc extends BaseSvc implements
        UserDetailsService {
    private static final String SEPARATOR = ",";
    private static final String ACTIVE_STS = "Y";

    @Resource
    UserMngSvc userMngSvc;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        log.debug("username......." + username);

        // 1. 사용자 정보 얻기
        SysLoginVO loginVO = getUserInfo(username);

        // 2. 사용자 권한 설정
        if (null != loginVO) {
            loginVO.setAuthorities(getAuthorities(loginVO));
            // 사용자 활성화 정보
            loginVO.setEnabled("Y".equals(loginVO.getUseYn()));
        }

        return loginVO;
    }

    private SysLoginVO getUserInfo(String username) {

        Map<String, Object> params = new HashMap<>();
        params.put("userId", username);

        ParamMap<String, Object> resultParam = userMngSvc.getData(params);

        if (null == resultParam || resultParam.isEmpty()) {
            log.warn("NO DATA");
            return null;
        }
        if (!ACTIVE_STS.equals(resultParam.get("useYn"))) {
            log.warn("NOT ACTIVATION USER:"+resultParam.get("loginId"));
            return null;
        }

        SysLoginVO userInfo;

        Jackson2ObjectMapperBuilder object2Mapper = new Jackson2ObjectMapperBuilder();
        try {
            userInfo = (object2Mapper.build().readValue(JsonUtil.getJsonStringFromMap(resultParam), SysLoginVO.class));
            log.debug(userInfo.toString());

            // set user detail info
            userInfo.setSys(userInfo.isUserAuth("CMM_SYS"));
            userInfo.setUsername(userInfo.getLoginId());
            userInfo.setPassword(userInfo.getLoginPwd());
            userInfo.setIsj("CMM_ISJ".equals(userInfo.getAuthCd()));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return userInfo;
    }

    private Collection<GrantedAuthority> getAuthorities(SysLoginVO loginVO) {
        String strAuthority = loginVO.getAuthCd();
        if(StringUtils.isEmpty(strAuthority)){
            return null;
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        String[] authList = strAuthority.split(SEPARATOR);

        for (String authority : authList) {
            authorities.add(new SimpleGrantedAuthority(authority));
        }// end of for

        // 기본 권한: IS_AUTHENTICATED_FULLY
        authorities.add(new SimpleGrantedAuthority(IS_AUTHENTICATED_FULLY));

        return authorities;
    }
}
