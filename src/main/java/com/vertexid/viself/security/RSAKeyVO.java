/*
 * @(#)RSAKeyVO.java     
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

import com.vertexid.viself.base.BaseVO;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * RSA key VO
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class RSAKeyVO extends BaseVO {

    private static final long serialVersionUID = -5961833516457076962L;

    /** 개인키 */
    private final PrivateKey privateKey;

    /** 공개키 */
    private final PublicKey publicKey;

    /** 공개키 정수부 */
    private final String publicKeyModulus;

    /** 공개키 지수부 */
    private final String publicKeyExponent;

    public RSAKeyVO(final PrivateKey privateKey, final PublicKey publicKey,
            final String publicKeyModulus, final String publicKeyExponent) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.publicKeyModulus = publicKeyModulus;
        this.publicKeyExponent = publicKeyExponent;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public String getPublicKeyModulus() {
        return publicKeyModulus;
    }

    public String getPublicKeyExponent() {
        return publicKeyExponent;
    }

}
