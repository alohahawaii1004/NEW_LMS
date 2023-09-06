/*
 * @(#)RSAService.java     
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

import com.vertexid.viself.base.BaseSvc;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 *     RSA 처리를 위한 서비스
 *     [참고] https://selfdevelope.tistory.com/183?category=727490 [IT의 신]
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
public class RSACryptographicSvc extends BaseSvc {

    /**
     * RSA
     */
    private static final String RSA = "RSA";

    private int keySize = 1024;

    public int getKeySize() {
        return keySize;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    /**
     * RSA 키 생성
     *
     * @return
     */
    public RSAKeyVO genRsaKeys() {

        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA);

            // key size
            generator.initialize(getKeySize());

            KeyPair keyPair = generator.generateKeyPair();

            // 개인키
            PrivateKey privateKey = keyPair.getPrivate();

            // 공개키
            PublicKey publicKey = keyPair.getPublic();

            // 공개키 String으로 변환
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            RSAPublicKeySpec publicKeySpec = keyFactory
                    .getKeySpec(publicKey, RSAPublicKeySpec.class);
            // 공개키 정수부
            String pubKeyMod = publicKeySpec.getModulus().toString(16);
            // 공개키 지수부
            String pubKeyExp = publicKeySpec.getPublicExponent().toString(16);

            return new RSAKeyVO(privateKey, publicKey, pubKeyMod, pubKeyExp);

        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException.....................\n" + e);
        } catch (InvalidKeySpecException e) {
            log.error("InvalidKeySpecException.....................\n" + e);
        }

        return null;
    }

    /**
     * 복호화
     *
     * @param privateKey
     * @param encWord
     * @return
     */
    public String decrypt(PrivateKey privateKey, String encWord) {

        try {
            Cipher cipher = Cipher.getInstance(RSA);
            byte[] encryptedBytes = hexToByteArray(encWord);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes,
                    StandardCharsets.UTF_8);

        } catch (Exception e) {
            log.warn(
                    "ERROR..............................................." + e);
        }
        return null;
    }

    /**
     * 16진 문자열 byte 배열로 변환
     *
     * @param hex
     * @return
     */
    private byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() % 2 != 0) {
            return new byte[]{};
        }

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
            bytes[(int) Math.floor(i / 2)] = value;
        }
        return bytes;
    }


    /**
     * 세션에 RSA 키를 생성
     * @param req
     * @return
     */
    public Map<String, Object> setRSAKey(HttpServletRequest req) {

        /*
         * login id와 password를 암호화해서 전송하기 위해 RSA 공개키 및 개인키 발급
         */
        RSAKeyVO rsaKeyVO = genRsaKeys();

        // 세션에 키정보 설정
        HttpSession session = req.getSession();
        session.setAttribute(RSAAttributeKey.PRIVATE_KEY,
                rsaKeyVO.getPrivateKey());
        session.setAttribute(RSAAttributeKey.PUBLIC_KEY,
                rsaKeyVO.getPublicKey());

        // 클라이언트에 공개키 전달
        req.setAttribute(RSAAttributeKey.PUBLIC_KEY_MODULUS,
                rsaKeyVO.getPublicKeyModulus());
        req.setAttribute(RSAAttributeKey.PUBLIC_KEY_EXPONENT,
                rsaKeyVO.getPublicKeyExponent());

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put(RSAAttributeKey.PUBLIC_KEY_MODULUS,
                rsaKeyVO.getPublicKeyModulus());
        rtnMap.put(RSAAttributeKey.PUBLIC_KEY_EXPONENT,
                rsaKeyVO.getPublicKeyExponent());

        return rtnMap;
    }
}
