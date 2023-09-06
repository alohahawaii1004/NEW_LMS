/*
 * @(#)AuthoritySvc.java     2021-01-13(013) 오전 9:57
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

import java.util.List;

/**
 * <b>Description</b>
 * <pre>
 *     권한 관리 구성시에 권한에 대한 서비스 인터페이스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public interface AuthoritySvc {

    /**
     * 권한(Role, Authority) 리스트 얻기
     * @param username 사용자 id
     * @param <E> 권한 유형
     * @return 권한리스트
     */
    <E> List<E> getAuthorities(String username);
}
