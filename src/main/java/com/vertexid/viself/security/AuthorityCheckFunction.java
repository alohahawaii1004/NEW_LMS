/*
 * @(#)AuthorityCheckFunction.java     2022-07-28(028) 오전 8:56:11
 *
 * Copyright 2022 JaYu.space
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

import com.vertexid.spring.utils.SessionUtils;
import org.springframework.stereotype.Component;

/**
 * <b>Description</b>
 * <pre>
 *     권한 검사 Function
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Component
public class AuthorityCheckFunction {

    public static final String SEPARATOR = ",";

    public boolean checkAuthority(final String authorities){
        BaseLoginDTO baseLoginDTO = (BaseLoginDTO) SessionUtils.getLoginVO();
        if(null == baseLoginDTO || null == authorities){
            return false;
        }

        return baseLoginDTO.containsAuthorities(authorities);
    }

    public boolean noAuthority(final String authorities){
        return !checkAuthority(authorities);
    }
}
