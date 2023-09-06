/*
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
import io.jsonwebtoken.JwsHeader;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * JWT: JSON Web Token VO
 *
 * [IETF RFC 7519]
 * - https://tools.ietf.org/html/rfc7519#section-4.1.3
 *
 * @author Yang, Ki Hwa
 *
 */
public class JSONWebTokenVO extends BaseVO {

    private static final long serialVersionUID = -6653633147215970885L;

    /**
     * JWT header
     */
    private Map<String, Object> header;

    /**
     * JWT header type
     */
    private String typ = JwsHeader.JWT_TYPE;

    /**
     * JWT header algorithm
     */
    private String alg;

    /**
     * JWT payloads
     */
    private Map<String, Object> payloads;

    /**
     * JWT payloads registered claim issuer
     */
    private String iss;

    /**
     * JWT payloads registered claim subject
     */
    private String sub;

    /**
     * JWT payloads registered claim audience
     */
    private String aud;

    /**
     * JWT payloads registered claim expiration
     */
    private Date exp;

    /**
     * JWT payloads registered claim not before
     */
    private Date nbf;

    /**
     * JWT payloads registered claim id of jwt
     */
    private Date iat;

    /**
     * jwt
     */
    private String jwt;

    /**
     * verification result
     */
    private boolean verificationResult;

    public JSONWebTokenVO(Map<String, Object> header,
            Map<String, Object> payloads) {
        this.header = new HashMap<String, Object>(header);
        this.payloads = new HashMap<String, Object>(payloads);
    }

    public JSONWebTokenVO(String jwt) {
        this.jwt = jwt;
    }

    public Map<String, Object> getHeader() {
        return new HashMap<String, Object>(header);
    }

    public String getTyp() {
        return typ;
    }

    public String getAlg() {
        return alg;
    }

    public Map<String, Object> getPayloads() {
        return new HashMap<String, Object>(payloads);
    }

    public String getIss() {
        return iss;
    }

    public String getSub() {
        return sub;
    }

    public String getAud() {
        return aud;
    }

    public Date getExp() {
        return exp;
    }

    public Date getNbf() {
        return nbf;
    }

    public Date getIat() {
        return iat;
    }

    public String getJwt() {
        return jwt;
    }

    public boolean isVerificationResult() {
        return verificationResult;
    }
}
