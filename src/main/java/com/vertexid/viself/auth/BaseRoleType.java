/*
 * @(#)BaseRoleType.java     2022-10-31(031) 오후 1:56:20
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
package com.vertexid.viself.auth;

/**
 * <b>Description</b>
 * <pre>
 *     시스템 기본 권한
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public enum BaseRoleType {

    CMM_ISJ("CMM_ISJ", "일반사용자"),
    CMM_SYS("CMM_SYS", "시스템관리자"),
    CMM_DEV("CMM_DEV", "시스템개발자");

    private String cd;
    private String nm;

    BaseRoleType(String cd, String nm) {
        this.cd = cd;
        this.nm = nm;
    }

    public String getCd() {
        return cd;
    }

    public String getNm() {
        return nm;
    }
}
