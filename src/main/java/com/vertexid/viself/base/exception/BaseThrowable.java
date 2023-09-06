/*
 * @(#)BaseThrowable.java     2020-10-26(026) 오전 10:33
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
package com.vertexid.viself.base.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class BaseThrowable extends Throwable{
    private static final long serialVersionUID = 2156183760367808922L;

    /**
     * logger
     */
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    public BaseThrowable() {
    }

    public BaseThrowable(String message) {
        super(message);
    }

    public BaseThrowable(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseThrowable(Throwable cause) {
        super(cause);
    }

    public BaseThrowable(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
