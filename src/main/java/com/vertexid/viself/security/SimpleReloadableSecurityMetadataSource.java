/*
 * @(#)SimpleReloadableSecurityMetadataSource.java     2021-03-19(019) 오후 1:46
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <b>Description</b>
 * <pre>
 *     권한별 접근 제어정보를 가진 저장소
 *     [참고]
 *     com.vertexid.spring.security.CmmReloadableSecurityMetadataSource
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class SimpleReloadableSecurityMetadataSource {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 권한메타정보(URL기반)
     */
    private final Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;

    /**
     * 권한메타정보 서비스
     */
    private SimpleSecureObjectSvc simpleSecureObjectSvc;

    public SimpleReloadableSecurityMetadataSource(
            Map<RequestMatcher, Collection<ConfigAttribute>> requestMap) {
        this.requestMap = requestMap;
    }

    public void setSecureObjectSvc(SimpleSecureObjectSvc cmmSecureObjectSvc) {
        this.simpleSecureObjectSvc = cmmSecureObjectSvc;
    }

    /**
     * request에 해당하는 권한 정보 반환
     * @param request request
     * @return 권한 정보
     * @throws IllegalArgumentException request 형식 오류
     */
    public Collection<ConfigAttribute> getAttributes(HttpServletRequest request)
            throws IllegalArgumentException {

        log.debug("request.URI.................." + request.getRequestURI());

        Collection<ConfigAttribute> result = null;

        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap
                .entrySet()) {

            if (entry.getKey().matches(request)) {
                result = entry.getValue();
                break;
            }
        } // end of for

        log.debug("result......................." + result);
        return result;
    }

    /**
     * 권한메타정보를 리로드한다.
     */
    public void reload() {

        // 1. 변경된 Url에 대한 Role 정보를 얻는다.
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> reloadedMap =
                simpleSecureObjectSvc.getRolesAndUrl();

        // 2. Iterator
        Iterator<Map.Entry<RequestMatcher, List<ConfigAttribute>>> iterator =
                reloadedMap.entrySet().iterator();

        // 3. 기존권한 삭제
        requestMap.clear();

        // 4. 신규권한 설정
        while (iterator.hasNext()) {
            Map.Entry<RequestMatcher, List<ConfigAttribute>> entry =
                    iterator.next();
            requestMap.put(entry.getKey(), entry.getValue());
        } // end of while

        log.info("Secured Url Resources - Role Mapping reloaded at Runtime!");

    }
}
