/*
 * @(#)IDUtils.java     2023-02-19 019 오후 2:47:19
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

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class IDUtils {

    /**
     * logger
     */
    protected static Logger logger = LoggerFactory.getLogger(IDUtils.class);

    /**
     * UUID 생성
     * @return upper case uuid (no hyphen)
     */
    public static String getRandomUUID() {
        return  String.valueOf(UUID.randomUUID()).replace("-", "").toUpperCase();
    }

    /**
     * date time 이 앞에 붙어 있는 UUID 생성
     * yyyyMMddHHmmssSSSS(18)+UUID(32) = DateTimeUUID(50)
     * @return DateTimeUUID(50)
     */
    public static String getDateTimeUUID() {
        DateTime dateTime = new DateTime();
        String today = dateTime.toString("yyyyMMddHHmmssSSSS").substring(0,18);
        return today + getRandomUUID();
    }
}
