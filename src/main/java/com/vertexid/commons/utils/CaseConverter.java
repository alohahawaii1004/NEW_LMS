/*
 * @(#)CaseConverter.java     
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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>Description</b>
 * <pre>
 *     변수명등에 대한 표기법(case)에 대한 컨버터
 *
 *     Upper case: 대문자(예: TESTCLASS)
 *     Lower case: 소문자(예: testclass)
 *     Camel case: 낙타 표기법(예: testClass)
 *     Pascal case: 파스칼 표기법(예: TestClass)
 *     Snake case: 뱀 표기법(예: TEST_CLASS, test_class)
 *     Cobol case: 코볼 표기법(예: TEST-CLASS, test-class)
 *
 *
 *     참고 : https://github.com/toolpage/java-case-converter
 *     - add : pascal case, pot(lower) case
 *     - change : snake(upper) case, camel case
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class CaseConverter {

    protected static Logger log = LoggerFactory.getLogger(CaseConverter.class);

    private static final String HYPHEN = "-";
    private static final String UNDERSCORE = "_";
    private static final String SPACE = " ";
    private static final String EMPTY = "";
    private static final String BEFORE_SPACE = " $0";

    /**
     * Case to be converted
     *
     * @author 양기화(梁起華 Yang, Ki Hwa)
     */
    public enum ConvertCaseType {
        UPPER_CASE, LOWER_CASE, CAMEL_CASE, PASCAL_CASE, SNAKE_CASE, COBOL_CASE, NO_CASE
    }

    /**
     * 검사용 정규식
     */
    private static class ConverterRegEx {

        /**
         * 공백 1개이상 정규식
         */
        public static final String FIND_MORE_SPACE = "\\s{1,}";

        /**
         * 시작 공백 정규식
         */
        public static final String FIND_START_SPACE = "^\\s";

        /**
         * 끝 공백 정규식
         */
        public static final String FIND_END_SPACE = "\\s$";

        /**
         * 대문자만(숫자제외) 정규식
         */
        public static final String FIND_UPPER_CASE_ONLY = "([A-Z])+";

        /**
         * camel case 검사 정규식
         */
        static final String CHK_CAMEL_CASE = "[a-z]{1}[A-Za-z0-9]+";

        /**
         * pascal case 검사 정규식
         */
        static final String CHK_PASCAL_CASE = "[A-Z]{1}[A-Za-z0-9]+";

        /**
         * upper snake case 검사 정규식
         */
        static final String CHK_UPPER_SNAKE_CASE = "([A-Z0-9]|_)+";

        /**
         * lower snake case 검사 정규식
         */
        static final String CHK_LOWER_SNAKE_CASE = "([a-z0-9]|_)+";

        /**
         * upper COBOL case 검사 정규식
         */
        static final String CHK_UPPER_COBOL_CASE = "([A-Z0-9]|-)+";

        /**
         * lower COBOL case 검사 정규식
         */
        static final String CHK_LOWER_COBOL_CASE = "([a-z0-9]|-)+";

        /**
         * 영숫자 제외 정규식
         */
        static final String EXCEPT_ALPHA_NUMERIC = "[^a-zA-Z0-9]";

        /**
         * upper case 검사 정규식
         */
        static final String CHK_UPPER_CASE = "([A-Z0-9])+";

        /**
         * lower case 검사 정규식
         */
        static final String CHK_LOWER_CASE = "([a-z0-9])+";

        /**
         * 공백영숫자 단어 검색 정규식
         */
        static final String FIND_SPACE_ALPHA_NUMERIC_WORD =
                "[_\\s]([a-z0-9]){1}";

        /**
         * 공백 정규식
         */
        static final String FIND_SPACE = "\\s";
    }

    /**
     * same StringUtils.upperCase
     *
     * @param value original text
     * @return change text
     */
    public static String upperCase(final String value) {
        return StringUtils.upperCase(value);
    }

    /**
     * same StringUtils.lowerCase
     *
     * @param value original text
     * @return change text
     */
    public static String lowerCase(final String value) {
        return StringUtils.lowerCase(value);
    }


    /**
     * camel case로 변환
     *
     * @param value original text
     * @return change text
     */
    public static String camelCase(final String value) {
        if (value == null) {
            return null;
        }

        if (isCamelCase(value)) {
            return value;
        }

        // 영숫자 제외 모두 공백으로 변환한뒤 소문자로 치환
        String rtnValue = getPreConvert(value);
        Matcher m =
                Pattern.compile(ConverterRegEx.FIND_SPACE_ALPHA_NUMERIC_WORD)
                        .matcher(rtnValue);
        StringBuffer sb = new StringBuffer();

        int i = 0;
        while (m.find()) {
            if (i == 0) {
                m.appendReplacement(sb, m.group(1));
            } else {
                m.appendReplacement(sb, m.group(1).toUpperCase());
            }
            i += 1;
        }
        m.appendTail(sb);
        rtnValue = sb.toString();
        rtnValue = rtnValue.replaceAll(ConverterRegEx.FIND_SPACE, EMPTY);

//        String rtnValue = pascalCase(value);
//        rtnValue = StringUtils.uncapitalize(rtnValue);

        return (StringUtils.isNotEmpty(rtnValue)) ? rtnValue : null;
    }

    /**
     * 영숫자를 제외하고 모두 공백으로 변환한 후 소문자로 치환
     *
     * @param value original text
     * @return 선가공 문자열
     */
    private static String getPreConvert(String value) {
        // 영숫자 제외 모두 공백으로 변환 : 첫글자를 대문자로 바꾸기 위해 맨앞 공백 추가

//        String rtnValue = SPACE + value.replaceAll(ConverterRegEx.CHK_UPPER_CASE, BEFORE_SPACE);
        String rtnValue = SPACE + value.replaceAll(ConverterRegEx.FIND_UPPER_CASE_ONLY, BEFORE_SPACE);
        rtnValue = rtnValue.replaceAll(ConverterRegEx.EXCEPT_ALPHA_NUMERIC, SPACE);

        return rtnValue.toLowerCase();
    }

    /**
     * pascal case로 변환
     *
     * @param value original text
     * @return change text
     */
    public static String pascalCase(final String value) {
        if (value == null) {
            return null;
        }

        if (isPascalCase(value)) {
            return value;
        }

        // 영숫자 제외 모두 공백으로 변환한뒤 소문자로 치환
        String rtnValue = getPreConvert(value);

        Matcher m =
                Pattern.compile(ConverterRegEx.FIND_SPACE_ALPHA_NUMERIC_WORD)
                        .matcher(rtnValue);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, m.group(1).toUpperCase());
        }
        m.appendTail(sb);
        rtnValue = sb.toString();
        rtnValue = rtnValue.replaceAll(ConverterRegEx.FIND_SPACE, EMPTY);

        return (StringUtils.isNotEmpty(rtnValue)) ? rtnValue : null;
    }

    /**
     * snake case로 변환
     *
     * @param value original text
     * @param lowerFlag lower case flag
     * @return be changed snake case text
     */
    public static String snakeCase(final String value, boolean lowerFlag) {
        if (value == null) {
            return null;
        }

        if (isSnakeCase(value)) {
            if(lowerFlag){
                return value.toLowerCase();
            }
            return value.toUpperCase();
        }

        // 영숫자 제외 모두 공백으로 변환한뒤 소문자로 치환
        String rtnValue = getPreConvert(value);

        // 공백 1개이상은 공백 1개로 치환
        rtnValue = rtnValue.replaceAll(ConverterRegEx.FIND_MORE_SPACE, SPACE);

        // 시작 공백 제거
        rtnValue = rtnValue.replaceAll(ConverterRegEx.FIND_START_SPACE, EMPTY);

        // 끝 공백 제거
        rtnValue = rtnValue.replaceAll(ConverterRegEx.FIND_END_SPACE, EMPTY);

        // 공백 "_"로 치환
        rtnValue = rtnValue.replaceAll(ConverterRegEx.FIND_SPACE, UNDERSCORE);

        // 대문자로 치환
        rtnValue = rtnValue.toUpperCase();
        if(lowerFlag){
            rtnValue = rtnValue.toLowerCase();
        }

        return (StringUtils.isNotEmpty(rtnValue)) ? rtnValue : null;
    }

    /**
     * snake case로 변환
     *
     * @param value original text
     * @return be changed upper snake case text
     */
    public static String snakeCase(final String value) {

        return snakeCase(value, false);
    }

    /**
     * snake case로 변환
     *
     * @param value original text
     * @return be changed lower snake case text
     */
    public static String lowerSnakeCase(final String value) {
        return snakeCase(value, true);
    }

    /**
     * COBOL 케이스로 변환
     *
     * @param value original text
     * @return be changed upper COBOL case text
     */
    public static String cobolCase(final String value) {

        return cobolCase(value, false);
    }

    /**
     * COBOL 케이스로 변환
     *
     * @param value original text
     * @return be changed lower COBOL case text
     */
    public static String loweCobolCase(final String value) {

        return cobolCase(value, true);
    }

    /**
     * COBOL 케이스로 변환
     *
     * @param value original text
     * @return be changed upper COBOL case text
     */
    public static String cobolCase(final String value, boolean lowerFlag) {

        String rtnValue = value;

        if (rtnValue == null) {
            return null;
        }

        if (isCOBOLCase(rtnValue)) {
            if(lowerFlag){
                return rtnValue.toLowerCase();
            }
            return rtnValue.toUpperCase();
        }

        rtnValue = snakeCase(value);

        // "_"를 "-"로 치환
        rtnValue = rtnValue.replaceAll(UNDERSCORE, HYPHEN);

        if(lowerFlag){
            return rtnValue.toLowerCase();
        }

        return (StringUtils.isNotEmpty(rtnValue)) ? rtnValue : null;
    }

    /**
     * camel case : 첫글자 소문자 단어바뀔때마다 대문자
     *
     * @param value string
     * @return true: camel case, other: null or not camel case
     */
    public static boolean isCamelCase(final String value) {
        if (value == null) {
            return false;
        }

        /*
         * 첫글자 소문자여부
         * 글자, 숫자 외 다른 문자 포함 여부
         */
        return value.matches(ConverterRegEx.CHK_CAMEL_CASE);
    }

    /**
     * pascal case : 첫글자 대문자 나머지 카멜케이스
     *
     * @param value string
     * @return true: pascal case, other: null or not pascal case
     */
    public static boolean isPascalCase(final String value) {
        if (value == null) {
            return false;
        }

        /*
         * 첫글자 대문자여부
         * 글자, 숫자 외 다른 문자 포함 여부
         */
        return value.matches(ConverterRegEx.CHK_PASCAL_CASE);
    }

    /**
     * snake case :
     *
     * @param value string
     * @return true: snake case, other: null or not pascal case
     */
    public static boolean isSnakeCase(final String value) {
        if (value == null) {
            return false;
        }

        return (value.matches(ConverterRegEx.CHK_LOWER_SNAKE_CASE) ||
                value.matches(ConverterRegEx.CHK_UPPER_SNAKE_CASE));
    }

    /**
     * 케밥 케이스 라고도 부르는 하이픈 사용 표시방식
     *
     * @param value string
     * @return true: COBOL case, other: null or not pascal case
     */
    public static boolean isCOBOLCase(final String value) {
        if (value == null) {
            return false;
        }
        return (value.matches(ConverterRegEx.CHK_LOWER_COBOL_CASE) ||
                value.matches(ConverterRegEx.CHK_UPPER_COBOL_CASE));
    }

    /**
     * 케이스 변경
     *
     * @param value    string
     * @param caseType to case type
     * @return changed case text
     */
    public static String toCase(final String value, final ConvertCaseType caseType) {

        switch (caseType) {
            case UPPER_CASE:
                return upperCase(value);

            case LOWER_CASE:
                return lowerCase(value);

            case PASCAL_CASE:
                return pascalCase(value);

            case CAMEL_CASE:
                return camelCase(value);

            case SNAKE_CASE:
                return snakeCase(value);

            case COBOL_CASE:
                return cobolCase(value);

            case NO_CASE:
                return value;

            default:
                // WARNING
//                log.warn("Wrong type");

                return null;
        } // end of switch
    }
}
