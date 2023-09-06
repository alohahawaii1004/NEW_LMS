/*
 * @(#)ByteArrayUtil.java     2023-03-09 009 오후 5:04:10
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

import java.util.Base64;

/**
 * <b>Description</b>
 * <pre>
 *     String <=> byte array
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class ByteArrayUtil {

    private boolean hexStrFlag = true;

    /**
     * default constructor
     */
    public ByteArrayUtil() {
    }

    /**
     * constructor
     * @param hexStrFlag use hex string flag: default(true)
     */
    public ByteArrayUtil(boolean hexStrFlag) {
        this.hexStrFlag = hexStrFlag;
    }

    /**
     * use hex string
     * @param hexStrFlag true: use, other: no use(Base64)
     */
    public void setHexStrFlag(boolean hexStrFlag) {
        this.hexStrFlag = hexStrFlag;
    }

    public boolean isHexStrFlag() {
        return hexStrFlag;
    }

    /**
     * String to Byte array
     * @param str string
     * @return byte array
     */
    public byte[] stringToArray(String str) {
        if (hexStrFlag) {
            return hexToByteArray(str);
        } else {
            return Base64.getDecoder().decode(str);
        }
    }

    /**
     * byte array to string
     * @param array byte array
     * @return string
     */
    public String arrayToString(byte[] array) {
        if (hexStrFlag) {
            return byteArrayToHex(array);
        } else {
            return Base64.getEncoder().encodeToString(array);
        }
    }


    /**
     * 16진 문자열 byte 배열로 변환
     *
     * @param hex 16진 문자열(Hex String)
     * @return byte 배열(byte array)
     */
    public byte[] hexToByteArray(String hex) {
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
     * byte 배열을 16진 문자열로
     * @param byteArray byte 배열
     * @return 16진 문자열(Hex String)
     */
    public String byteArrayToHex(byte[] byteArray) {
        StringBuffer sb = new StringBuffer(byteArray.length * 2);
        for (byte b : byteArray) {
            sb.append(String.format("%02x", b).toUpperCase());
        }
        return sb.toString();
    }

}
