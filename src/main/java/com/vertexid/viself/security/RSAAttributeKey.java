/*
 * @(#)RSAAttributeKey.java     
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

package com.vertexid.viself.security;

/**
 * RSA 사용과 관련한 상수 Key
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class RSAAttributeKey {

    /**
     * RSA 개인키 : session용 attribute
     */
    public static final String PRIVATE_KEY = "__rsaPrivateKey__";

    /**
     * RSA 공개키 : session용 attribute
     */
    public static final String PUBLIC_KEY = "__rsaPublicKey__";

    /**
     * RSA 공개키 정수부 : request attribute
     */
    public static final String PUBLIC_KEY_MODULUS = "publicKeyModulus";

    /**
     * RSA 공개키 지수부 : request attribute
     */
    public static final String PUBLIC_KEY_EXPONENT = "publicKeyExponent";

    /**
     * RSA 암호화 parameter 리스트 : client form parameter
     */
    public static final String RSA_TARGET_PARAMETERS = "encParams";
}
