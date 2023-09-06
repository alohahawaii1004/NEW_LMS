/*
 * @(#)PasswordEncoder.java     
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
package com.vertexid.spring.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

/**
 * <b>Description</b>
 * <pre>
 *     패스워드 암호화 처리
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class PasswordEncoder extends BCryptPasswordEncoder {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public PasswordEncoder() {
        super();
    }

    public PasswordEncoder(int strength) {
        super(strength);
    }

    public PasswordEncoder(int strength, SecureRandom random) {
        super(strength, random);
    }

    @Override
    public String encode(CharSequence rawPassword) {

        // DEBUG
        if (log.isDebugEnabled()){
            log.debug("INPUT PASSWORD : " + rawPassword);
        }

        String encPassword = super.encode(rawPassword);

        // DEBUG
        if (log.isDebugEnabled()){
            log.debug("ENCODED HASH : " + encPassword);
        }

        return encPassword;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        boolean rtn = super.matches(rawPassword, encodedPassword);

        // DEBUG
        if (log.isDebugEnabled()){
//            log.debug("rawPassword ...................["+rawPassword+']');
//            log.debug("encodedPassword ...............["+encodedPassword+']');
//            log.debug("matches .......................["+rtn+']');
        }

        return rtn;
    }
}
