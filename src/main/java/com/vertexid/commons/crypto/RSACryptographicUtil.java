/*
 * @(#)RSACryptographicUtil.java     
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
package com.vertexid.commons.crypto;

import com.vertexid.viself.security.RSAKeyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * TODO 작업중
 * <b>Description</b>
 * <pre>
 *     RSA 암호화 유틸
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class RSACryptographicUtil {

    /**
     * RSA
     */
    private static final String RSA = "RSA";

    private String signatureAlogrithm = "SHA512withRSA";

    private int keySize = 1024;

    private ByteArrayUtil util = new ByteArrayUtil();

    public String getSignatureAlogrithm() {
        return signatureAlogrithm;
    }

    public void setSignatureAlogrithm(String signatureAlogrithm) {
        this.signatureAlogrithm = signatureAlogrithm;
    }

    public boolean isHexStrFlag() {
        return this.isHexStrFlag();
    }

    public void setHexStrFlag(boolean hexStrFlag) {
        this.util.setHexStrFlag(hexStrFlag);
    }

    public int getKeySize() {
        return keySize;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    /**
     * logger
     */
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * RSA 키 생성
     *
     * @return
     */
    public RSAKeyVO genRsaKeys() {

        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA);

            // key size
            generator.initialize(keySize);

            KeyPair keyPair = generator.generateKeyPair();

            // 개인키
            PrivateKey privateKey = keyPair.getPrivate();

            // 공개키
            PublicKey publicKey = keyPair.getPublic();

            // 공개키 String으로 변환
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            RSAPublicKeySpec publicKeySpec =
                    keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            // 공개키 정수부
            String pubKeyMod = publicKeySpec.getModulus().toString(16);
            // 공개키 지수부
            String pubKeyExp = publicKeySpec.getPublicExponent().toString(16);

            return new RSAKeyVO(privateKey, publicKey, pubKeyMod, pubKeyExp);

//            RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
//            String priKeyMod = privateKeySpec.getModulus().toString(16);
//            String priKeyExp = privateKeySpec.getPrivateExponent().toString(16);
//
//            String stringPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException.....................\n" + e);
        } catch (InvalidKeySpecException e) {
            log.error("InvalidKeySpecException.....................\n" + e);
        }

        return null;
    }

    public String toStringPrivateKey(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public PrivateKey privateKeyFromString(String strPrivateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytePrivateKey =
                Base64.getDecoder().decode(strPrivateKey.getBytes());
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(
                new PKCS8EncodedKeySpec(bytePrivateKey));
    }

    public String toStringPublicKey(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public PublicKey pubKeyFromString(String strPublicKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytePublicKey =
                Base64.getDecoder().decode(strPublicKey.getBytes());
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(new X509EncodedKeySpec(bytePublicKey));
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
            byte[] encryptedBytes = util.stringToArray(encWord);
            byte[] decryptedBytes = CipherUtil.decrypt(privateKey, encryptedBytes, RSA);
            return new String(decryptedBytes, UTF_8);
        } catch (Exception e) {
            log.error(
                    "ERROR..............................................." + e);
        }
        return null;
    }

    /**
     * @param pubKeyMod hex
     * @param pubKeyExp hex
     * @param plainText
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     */
    public String encrypt(String pubKeyMod, String pubKeyExp, String plainText)
            throws NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException {
        RSAPublicKeySpec publicKeySpec =
                new RSAPublicKeySpec(new BigInteger(pubKeyMod, 16),
                        new BigInteger(pubKeyExp, 16));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return encrypt(keyFactory.generatePublic(publicKeySpec), plainText);
    }

    public String encrypt(String strPubKey, String plainText)
            throws NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException {
        return encrypt(pubKeyFromString(strPubKey), plainText);
    }

    public String encrypt(PublicKey publicKey, String plainText)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        byte[] bytePlain = CipherUtil.encrypt(publicKey, plainText.getBytes(), RSA);
        return util.arrayToString(bytePlain);
    }

    public String createSign(PrivateKey privateKey, String plainInfo)
            throws NoSuchAlgorithmException, InvalidKeyException,
            SignatureException {

        Signature signature = Signature.getInstance(signatureAlogrithm);
        signature.initSign(privateKey); // PKCS#8 is preferred
        signature.update(plainInfo.getBytes(UTF_8));
        byte[] signBytes = signature.sign();
        return util.arrayToString(signBytes);
    }

    public boolean verifySign(PublicKey publicKey, String cert,
            String plainText) {
        try {
            Signature publicSignature =
                    Signature.getInstance(signatureAlogrithm);
            publicSignature.initVerify(publicKey);
            publicSignature.update(plainText.getBytes(UTF_8));
            return publicSignature.verify(util.stringToArray(cert));

        } catch (Exception e) {
            e.printStackTrace();
            log.error(
                    "ERROR..............................................." + e);
        }
        return false;
    }
}
