/*
 * @(#)AESCryptograpicUtil.java     2023-03-09 009 오후 4:47:25
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
package com.vertexid.commons.crypto;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * <b>Description</b>
 * <pre>
 *     참고: https://huisam.tistory.com/entry/aes
 *     참고: https://veneas.tistory.com/entry/JAVA-%EC%9E%90%EB%B0%94-AES-%EC%95%94%ED%98%B8%ED%99%94-%ED%95%98%EA%B8%B0-AES-128-AES-192-AES-256
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class AESCryptograpicUtil {

    private final Logger logger =
            LoggerFactory.getLogger(AESCryptograpicUtil.class);

    private static final String algorithm = "AES";
    //Java에서는 PKCS#5 = PKCS#7이랑 동일
    //자세한 내용은 http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html 참고.
    private static final String BLOCK_N_PADDING =
            algorithm + "/CBC/PKCS5Padding";

    private IvParameterSpec ivSpec;
    private Key keySpec;

    private ByteArrayUtil util = new ByteArrayUtil();

    public AESCryptograpicUtil() {
    }

    public boolean isHexStrFlag() {
        return this.isHexStrFlag();
    }

    public void setHexStrFlag(boolean hexStrFlag) {
        this.util.setHexStrFlag(hexStrFlag);
    }

    public void setIvSpec(IvParameterSpec ivSpec) {
        this.ivSpec = ivSpec;
    }

    public void setKeySpec(Key keySpec) {
        this.keySpec = keySpec;
    }

    public IvParameterSpec getIvSpec() {
        return ivSpec;
    }

    public Key getKeySpec() {
        return keySpec;
    }

    /**
     * 32자리의 키값을 이용하여 SecretKeySpec 생성
     *
     * @param password 절대 유출되서는 안되는 키 값이며, 이것으로 키스펙을 생성
     * @throws UnsupportedEncodingException 지원되지 않는 인코딩 사용시 발생
     * @throws NoSuchAlgorithmException     잘못된 알고리즘을 입력하여 키를 생성할 경우 발생
     * @throws InvalidKeySpecException      잘못된 키 스펙이 생성될 경우 발생
     */
    public void makeKey(String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException,
            InvalidKeySpecException {
        //암호키를 생성하는 팩토리 객체 생성
        SecretKeyFactory factory =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        //다이제스트를 이용하여, SHA-512로 단방향 해시 생성 (salt 생성용)
        MessageDigest digest = MessageDigest.getInstance("SHA-512");

        // C# : byte[] keyBytes = System.Text.Encoding.UTF8.GetBytes(password);
        byte[] keyBytes = password.getBytes(UTF_8);
        // C# : byte[] saltBytes = SHA512.Create().ComputeHash(keyBytes);
        byte[] saltBytes = digest.digest(keyBytes);

        // 256bit (AES256은 256bit의 키, 128bit의 블록사이즈를 가짐.)
        PBEKeySpec pbeKeySpec =
                new PBEKeySpec(password.toCharArray(), saltBytes, 65536, 256);
        Key secretKey = factory.generateSecret(pbeKeySpec);

        // 256bit = 32byte
        byte[] key = new byte[32];
        System.arraycopy(secretKey.getEncoded(), 0, key, 0, 32);
        //AES 알고리즘을 적용하여 암호화키 생성
        SecretKeySpec secret = new SecretKeySpec(key, "AES");
        setKeySpec(secret);
    }

    /**
     * 16자리 초기화벡터 입력하여 ivSpec을 생성한다.
     *
     * @param IV 절대 유출되서는 안되는 키 값이며, 이것으로 키스펙을 생성
     * @throws UnsupportedEncodingException 지원되지 않는 인코딩 사용시 발생
     * @throws NoSuchAlgorithmException     잘못된 알고리즘을 입력하여 키를 생성할 경우 발생
     * @throws InvalidKeySpecException      잘못된 키 스펙이 생성될 경우 발생
     */
    public void makeVector(String IV)
            throws NoSuchAlgorithmException, UnsupportedEncodingException,
            InvalidKeySpecException {
        SecretKeyFactory factory =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] vectorBytes = IV.getBytes("UTF-8");
        byte[] saltBytes = digest.digest(vectorBytes);

        // 128bit
        PBEKeySpec pbeKeySpec =
                new PBEKeySpec(IV.toCharArray(), saltBytes, 65536, 128);
        Key secretIV = factory.generateSecret(pbeKeySpec);

        // 128bit = 16byte
        byte[] iv = new byte[16];
        System.arraycopy(secretIV.getEncoded(), 0, iv, 0, 16);

        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        setIvSpec(ivSpec);
    }

    /**
     * 원본 파일을 복호화해서 대상 파일을 만든다.
     *
     * @param source 원본 파일
     * @param dest   대상 파일
     * @throws Exception exception
     */
    public void decrypt(File source, File dest) throws Exception {
        Cipher c = Cipher.getInstance(BLOCK_N_PADDING);
        c.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        fileProcessing(source, dest, c);
    }


    /**
     * 파일 복호화 처리
     * <pre>
     * Step 1. 생성한 파일의 버퍼를 읽어들임.
     * 2. Base64 인코딩된 문자열 -> Base64 디코딩 Byte[]로 변환
     * 3. Base64 디코딩 Byte[] -> Cipher.update를 사용하여 AES256 Decryption 실행
     * 4. Cipher.doFinal()로 마지막 Padding을 추가.
     *
     * </pre>
     * @param source 원본 파일
     * @param dest   대상 파일
     * @param c      생성된 Cipher 객체 전달
     * @throws Exception exception
     */
    public void fileProcessing(File source, File dest, Cipher c)
            throws Exception {
        InputStream input = null;
        OutputStream output = null;

        try {
            input = new BufferedInputStream(new FileInputStream(source));
            output = new BufferedOutputStream(new FileOutputStream(dest));
            byte[] buffer = new byte[4 * (input.available() / 4)];
            int read = -1;
            while ((read = input.read(buffer)) != -1) {
                byte[] bufferEncoded = buffer;
                if (read != buffer.length) {
                    bufferEncoded = Arrays.copyOf(buffer,
                            read); //버퍼에 읽힌 값을 bufferEncoded에 Array Copy
                }
                byte[] bufferDecoded = Base64.getDecoder()
                        .decode(bufferEncoded); //Base64 Decode
                output.write(c.update(bufferDecoded)); //AES256 Decryption
            }
            output.write(c.doFinal()); // Last Padding add
        } catch (BadPaddingException e) {
//            e.printStackTrace();
            logger.error(e.toString());
            throw new RuntimeException(e);
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public String encrypt(Key key, IvParameterSpec iv, String plainText) {
        byte[] encrpytionByte =
                CipherUtil.encrypt(key, iv, plainText.getBytes(UTF_8),
                        BLOCK_N_PADDING);
        return util.arrayToString(encrpytionByte);
    }

    public String encrypt(String plainText) {
        return encrypt(keySpec, ivSpec, plainText);
    }

    public String decrypt(Key key, IvParameterSpec iv, String encText) {
        byte[] decodeByte = util.stringToArray(encText);
        return new String(CipherUtil.decrypt(key, iv, decodeByte,
                BLOCK_N_PADDING), UTF_8);
    }

    public String decrypt(String encText) {
        return decrypt(keySpec, ivSpec, encText);
    }
}
