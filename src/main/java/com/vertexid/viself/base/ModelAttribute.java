/*
 * @(#)ModelAttribute.java     
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

package com.vertexid.viself.base;

/**
 * 모델에서 주로사용하는 속성
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public enum ModelAttribute {
    MSG("msg"),
    DATA("data"),
    ERROR_FLAG("errYn"),
    RESULT("result"),
    REQUEST("requestData"),
    RESPONSE("responseData")
    ;

    private final String attributeId;

    ModelAttribute(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeId() {
        return attributeId;
    }
}
