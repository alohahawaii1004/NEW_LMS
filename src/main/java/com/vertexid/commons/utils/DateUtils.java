/*
 * @(#)DateUtils.java     2023-02-19 019 오후 1:46:37
 *
 * Copyright 2023 JAYU.space
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * <b>Description</b>
 * <pre>
 *     날짜 관련 유틸
 *     * Java 8 부터 java.time 패키지 덕에 좋아지긴 했지만...
 *     joda time 이 더 좋은건 어쩔 수 없는듯.
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class DateUtils {

    /**
     * logger
     */
    protected static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * return DEFAULT DATE FORMAT date string
     * @param s string date (DEFAULT DATE FORMAT:date only)
     * @param day add days
     * @return date string
     */
    public static String parseAdd(String s, int day) {
        LocalDate dateTime = LocalDate.parse(s, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
        return dateTime.plusDays(day).format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
    }

    /**
     * Returns whether a character date is valid
     * @param dateStr string date(date only)
     * @param dateFormatter date format(date only)
     * @return true: is valid, other: is not valid
     */
    public static boolean isValid(String dateStr, String dateFormatter) {
        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(dateFormatter));
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
