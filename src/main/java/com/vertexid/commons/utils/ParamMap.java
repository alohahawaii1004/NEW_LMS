/*
 * @(#)ParamMap.java     
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
package com.vertexid.commons.utils;

import static com.vertexid.commons.utils.CaseConverter.ConvertCaseType.CAMEL_CASE;
import static com.vertexid.commons.utils.CaseConverter.ConvertCaseType.NO_CASE;

import java.util.HashMap;

/**
 * <b>Description</b>
 * <pre>
 * Key의 표현을 camel case를 기본으로 하는 Map
 * 주로 Select 문의 결과용으로 사용할 목적으로 제작됨
 * </pre>
 * @param <K>
 * @param <V>
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class ParamMap<K, V> extends HashMap<K, V> {

    private static final long serialVersionUID = -5345636322055412872L;

//    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * key case 유형(default camel case)
     */
    private CaseConverter.ConvertCaseType keyCaseType = CAMEL_CASE;

    /**
     * string value 여부 (default false)
     */
    private boolean stringValueFlag = false;

    /**
     * 기본생성자로 Key는 camel case로 value는 Object로 구성
     */
    public ParamMap() {
    }

    /**
     * 생성시에 key의 case 유형을 설정할 수 있음
     * value는 Object로 설정
     *
     * @param keyCaseType Key의 case 유형
     */
    public ParamMap(CaseConverter.ConvertCaseType keyCaseType) {
        this.keyCaseType = keyCaseType;
    }

    /**
     * value형태를 String으로 할지 여부를 설정할 수 있음
     *
     * @param stringValueFlag true: String, other: Object
     */
    public ParamMap(boolean stringValueFlag) {
        this.stringValueFlag = stringValueFlag;
    }

    /**
     * Key의 case와 Value를 String으로 할지 설정할 수 있음
     *
     * @param keyCaseType     key의 case 유형
     * @param stringValueFlag true: String value, other: Object value
     */
    public ParamMap(CaseConverter.ConvertCaseType keyCaseType, boolean stringValueFlag) {
        this.keyCaseType = keyCaseType;
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

//    /**
//     * Key 의 case 유형을 재설정한다.
//     * [주의] case 유형설정만 변경되었을 뿐 내부 데이터가 변경되지는 않는다.
//     * @param convertCaseType case 유형
//     */
//    public void setConvertCaseType(CaseConverter.ConvertCaseType convertCaseType) {
//        this.convertCaseType = convertCaseType;
//    }

    /**
     * Value를 String으로 변환 여부를 설정한다.
     *
     * @param stringValueFlag true: String, other: Object
     */
    public void setStringValueFlag(boolean stringValueFlag) {
        this.stringValueFlag = stringValueFlag;
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
     * String 으로 값을 가져오기
     *
     * @param key key
     * @return String value
     */
    public String getString(Object key) {
         return (null == super.get(key)) ? null : String.valueOf(super.get(key));
    }

    @Override
    @SuppressWarnings("unchecked")
    public V put(K key, V value) {

        if (NO_CASE.equals(this.keyCaseType)) {
            return super.put(key, value);
        } else {
            return super
                    .put((K)CaseConverter.toCase((String) key, this.keyCaseType),
                            value);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(Object key) {

        if(stringValueFlag){
            //noinspection unchecked
            return (V) getString(key);
        }else{

            return super.get(key);
        }

    }
}
