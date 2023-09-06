/*
 * @(#)SimpleAuthenticationVoter.java     2021-03-24(024) 오전 9:06
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

import static org.springframework.security.access.vote.AuthenticatedVoter.IS_AUTHENTICATED_ANONYMOUSLY;
import static org.springframework.security.access.vote.AuthenticatedVoter.IS_AUTHENTICATED_FULLY;

/**
 * <b>Description</b>
 * <pre>
 *     인증(Authentication)에 대한 voter
 *     Spring Security Authentication Voter 참고(copycat)
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Component
public class SimpleAuthenticationVoter extends BaseSvc {

    /**
     * Voter 지원 여부 반환
     * @param attribute 검사할 속성
     * @return true: 지원, other: 지원하지 않음
     */
    private boolean supports(ConfigAttribute attribute) {

//        log.debug("attribute.getAttribute() != null.........."+(attribute.getAttribute() != null));
//        log.debug("IS_AUTHENTICATED_FULLY.equals(attribute.getAttribute().........."+(IS_AUTHENTICATED_FULLY.equals(attribute.getAttribute())));
//        log.debug("IS_AUTHENTICATED_ANONYMOUSLY.equals(attribute.getAttribute()).........."+(IS_AUTHENTICATED_ANONYMOUSLY.equals(attribute.getAttribute())));

        return attribute.getAttribute() != null &&
                (IS_AUTHENTICATED_FULLY.equals(attribute.getAttribute()) ||
                        IS_AUTHENTICATED_ANONYMOUSLY
                                .equals(attribute.getAttribute()));
    }

    /**
     * 인증(authentication) 에 따른 접근 확인
     *
     * @param authorities     권한
     * @param allowCollection 접근가능 collection
     * @return 1: 허가, 0: 보류, -1: 불가
     */
    public int vote(Collection<GrantedAuthority> authorities,
            Collection<ConfigAttribute> allowCollection) {

//        log.debug("[AuthenticationVoter]..........");
//        log.debug("authorities.........." + authorities);
//        log.debug("allowCollection.........." + allowCollection);

        int result = 0;
        Iterator itr = allowCollection.iterator();

        ConfigAttribute attribute;
        do {
            do {
                if (!itr.hasNext()) {
//                    log.debug("...........IS_AUTHENTICATED_ANONYMOUSLY");
                    return result;
                }
                attribute = (ConfigAttribute) itr.next();
//                log.debug("attribute....." + attribute);
            } while (!supports(attribute));

            result = -1;
            if (IS_AUTHENTICATED_FULLY.equals(attribute.getAttribute()) &&
                    authorities.contains(new SimpleGrantedAuthority(
                            IS_AUTHENTICATED_FULLY))) {
//                log.debug("...........IS_AUTHENTICATED_FULLY");
                return 1;
            }

//            if(IS_AUTHENTICATED_ANONYMOUSLY.equals(attribute.getAttribute()) && authorities.contains(IS_AUTHENTICATED_ANONYMOUSLY)){
//                log.debug("...........IS_AUTHENTICATED_ANONYMOUSLY");
//                return 1;
//            }
        } while (!IS_AUTHENTICATED_ANONYMOUSLY
                .equals(attribute.getAttribute()) || !authorities.contains(
                new SimpleGrantedAuthority(IS_AUTHENTICATED_ANONYMOUSLY)));

//        log.debug("!!..........IS_AUTHENTICATED_FULLY");
        return 1;
    }

}
