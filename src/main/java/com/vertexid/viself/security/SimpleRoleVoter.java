/*
 * @(#)SimpleRoleVoter.java     2021-03-24(024) 오전 10:34
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

import com.vertexid.viself.base.BaseSvc;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * <b>Description</b>
 * <pre>
 *     Role(Authority)에 대한 voter
 *     Spring Security Role Voter 참고(copycat)
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Component
public class SimpleRoleVoter extends BaseSvc {

    /**
     * Voter 지원 여부 반환
     * @param attribute 검사할 속성
     * @return true: 지원, other: 지원하지 않음
     */
    private boolean supports(ConfigAttribute attribute) {
//        log.debug("attribute.getAttribute() != null.........."+(attribute.getAttribute() != null));
        return attribute.getAttribute() != null;
    }

    /**
     * 인증(authentication) 에 따른 접근 확인
     *
     * @param authorities     권한
     * @param allowCollection 접근가능 collection
     * @return 1: 허가, 0: 보류, -1: 불가
     */
    public int vote(
            Collection<GrantedAuthority> authorities, Collection<ConfigAttribute> allowCollection) {

//        log.debug("[RoleVoter]..........");
//        log.debug("authorities.........."+authorities);
//        log.debug("allowCollection.........."+allowCollection);

        if (authorities == null) {
            return -1;
        } else {
            int result = 0;
            Iterator var6 = allowCollection.iterator();

            while(true) {
                ConfigAttribute attribute;
                do {
                    if (!var6.hasNext()) {
                        return result;
                    }

                    attribute = (ConfigAttribute)var6.next();
                } while(!supports(attribute));

                result = -1;
                Iterator var8 = authorities.iterator();

                while(var8.hasNext()) {
                    GrantedAuthority authority = (GrantedAuthority)var8.next();
                    if (attribute.getAttribute().equals(authority.getAuthority())) {
                        return 1;
                    }
                }
            }
        }
    }
}
