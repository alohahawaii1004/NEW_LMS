/*
 * @(#)CipherUtil.java     2023-03-09 009 오후 5:23:42
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

/**
 * <b>Description</b>
 * <pre>
 *     암복호화 유틸
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class CipherUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CipherUtil.class);

    public static byte[] encrypt(Key key, byte[] inputs, String cipherAlgorithm){
        try{
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(inputs);
        }catch (Exception e){
            LOGGER.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    public static byte[] encrypt(Key key, AlgorithmParameterSpec spec, byte[] inputs, String cipherAlgorithm){
        try{
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);
            return cipher.doFinal(inputs);
        }catch (Exception e){
            LOGGER.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    public static byte[] decrypt(Key key, byte[] inputs, String cipherAlgorithm){
        try{
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(inputs);
        }catch (Exception e){
            LOGGER.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    public static byte[] decrypt(Key key, AlgorithmParameterSpec spec, byte[] inputs, String cipherAlgorithm){
        try{
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            return cipher.doFinal(inputs);
        }catch (Exception e){
            LOGGER.error(e.toString());
            throw new RuntimeException(e);
        }
    }
}
