/*
 * @(#)SimpleSecureObjectSvc.java     2021-03-30(030) 오전 10:48
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * <b>Description</b>
 * <pre>
 *     시스템 권한 체크용 리소스에 대한 전체 목록을 로드하는 서비스.
 *
 *  [참고]
 *  - https://zgundam.tistory.com/58?category=430446
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class SimpleSecureObjectSvc extends BaseSvc {
    @Resource
    private AuthSvc authSvc;

    public void setAuthSvc(AuthSvc authSvc) {
        this.authSvc = authSvc;
    }

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getRolesAndUrl() {
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> ret =
                new LinkedHashMap<>();

        LinkedHashMap<Object, List<ConfigAttribute>> data =
                authSvc.getRolesAndUrl();
        Set<Object> keys = data.keySet();
        for (Object key : keys) {
            ret.put((AntPathRequestMatcher) key, data.get(key));
        }// end of for

        return ret;
    }
}
