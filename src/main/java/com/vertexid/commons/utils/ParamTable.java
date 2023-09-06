/*
 * @(#)ParamTable.java     2020-01-08 오후 1:22
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
package com.vertexid.commons.utils;

import java.util.Hashtable;

/**
 * <b>Description</b>
 * <pre>
 *     Key의 표현을 camel case를 기본으로 하는 Table
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class ParamTable<K, V> extends Hashtable<Object, Object> {

    /**
     * 
     */
    private static final long serialVersionUID = 3874094131898867499L;

//    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * key case 유형(default camel case)
     */
    private CaseConverter.ConvertCaseType keyCaseType = CaseConverter.ConvertCaseType.CAMEL_CASE;

    /**
     * string value 여부 (default false)
     */
    private boolean stringValueFlag = false;

    /**
     * 기본생성자로 Key는 camel case로 value는 Object로 구성
     */
    public ParamTable() {
    }

    /**
     * Key의 case와 Value를 String으로 할지 설정할 수 있음
     *
     * @param keyCaseType     key의 case 유형
     * @param stringValueFlag true: String value, other: Object value
     */
    public ParamTable(CaseConverter.ConvertCaseType keyCaseType, boolean stringValueFlag) {
        this.keyCaseType = keyCaseType;
        this.stringValueFlag = stringValueFlag;
    }

    /**
     * 생성시에 key의 case 유형을 설정할 수 있음
     * value는 Object로 설정
     *
     * @param keyCaseType Key의 case 유형
     */
    public ParamTable(CaseConverter.ConvertCaseType keyCaseType) {
        this.keyCaseType = keyCaseType;
    }

    /**
     * value형태를 String으로 할지 여부를 설정할 수 있음
     *
     * @param stringValueFlag true: String, other: Object
     */
    public ParamTable(boolean stringValueFlag) {
        this.stringValueFlag = stringValueFlag;
    }

    /**
     * 설정된 Key의 Case 유형을 반환
     *
     * @return case 유형 반환
     */
    public CaseConverter.ConvertCaseType getKeyCaseType() {
        return keyCaseType;
    }

    /**
     * 현재 설정된 value에 대한 string 변환여부를 반환한다.
     *
     * @return true: String, other: Object
     */
    public boolean isStringValueFlag() {
        return stringValueFlag;
    }

    /**
     * Value를 String으로 변환 여부를 설정한다.
     *
     * @param stringValueFlag true: String, other: Object
     */
    public void setStringValueFlag(boolean stringValueFlag) {
        this.stringValueFlag = stringValueFlag;
    }

    /**
     * String 으로 값을 가져오기
     *
     * @param key key
     * @return String value
     */
    public String getString(Object key) {
        return String.valueOf(super.get(key));
    }

    @Override
    public synchronized Object get(Object key) {
        if (stringValueFlag) {
            return getString(key);
        } else {
            return super.get(key);
        }
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        if (CaseConverter.ConvertCaseType.NO_CASE.equals(this.keyCaseType)) {
            return super.put(key, value);
        } else {
            return super
                    .put(CaseConverter.toCase((String) key, this.keyCaseType),
                            value);
        }
    }
}
