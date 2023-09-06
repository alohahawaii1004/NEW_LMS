/*
 * @(#)SystemOperationType.java     2020-04-23 오후 4:41
 *
 * Copyright 2020 JAYU.space
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
package com.vertexid.viself.base;

/**
 * <b>Description</b>
 *
 * @author Yang, Ki Hwa
 */
public enum SystemOperationType {
    LOCAL("LOCAL", "(개발자 개인)개발 운영", "Indivisual Development Mode"),
    DEV("DEV", "(공용)개발 운영", "Development Mode"),
    QAS("QAS", "품질 운영", "Quality Assurance Service Mode"),
    TEST("TEST", "테스트 운영", "Test Mode"),
    EDU("EDU", "교육 운영", "Education Service Mode"),
    PROD("PROD", "서비스 운영", "Biz Operation Mode");

    private final String typeCode;
    private final String typeKoName;
    private final String typeEngName;

    SystemOperationType(String typeCode, String typeKoName,
            String typeEngName) {
        this.typeCode = typeCode;
        this.typeKoName = typeKoName;
        this.typeEngName = typeEngName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public String getTypeKoName() {
        return typeKoName;
    }

    public String getTypeEngName() {
        return typeEngName;
    }
}
