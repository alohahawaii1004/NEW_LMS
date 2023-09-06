/*
 * @(#)SimpleLoginDTO.java     2021-01-12(012) 오전 8:18
 *
 * Copyright 2021 JAYU.space
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
package com.vertexid.viself.login;

import com.vertexid.viself.security.BaseLoginDTO;
import org.apache.commons.lang3.StringUtils;

/**
 * <b>Description</b>
 * <pre>
 *     로그인 정보를 가지는 VO
 *     UserDTO 기반으로 설정,
 *     기타 경우 해당 코드를 참고로 BaseLoginDTO를 상속해서 구현하길 권장
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class SimpleLoginVO extends BaseLoginDTO {
    private static final long serialVersionUID = 5947111944502498041L;
    private static final String USE_FLAG = "Y";

    /**
     * 사용자 아이디
     */
    private String userId;

    /**
     * 사용자 이름
     */
    private String userNm;

    /**
     * 사용자유형
     */
    private String userTpCd;

    /**
     * 사용자 사용여부
     */
    private String useEnable;

    /**
     * 사용자 암호
     */
    private String userPwd;

    /**
     * 임시 암호
     */
    private String tempPwd;

    public SimpleLoginVO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        setUsername(userId);
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getUserTpCd() {
        return userTpCd;
    }

    public void setUserTpCd(String userTpCd) {
        this.userTpCd = userTpCd;
    }

    public String getUseEnable() {
        return useEnable;
    }

    public void setUseEnable(String useEnable) {
        this.useEnable = useEnable;
        setEnabled(USE_FLAG.equals(useEnable));
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;

        log.debug("userPwd..."+userPwd);

        if (StringUtils.isNotBlank(userPwd)) {
            log.debug("??? userPwd..."+userPwd);
            setPassword(userPwd);
        }
    }

    public String getTempPwd() {
        return tempPwd;
    }

    public void setTempPwd(String tempPwd) {
        this.tempPwd = tempPwd;

        log.debug("tempPwd..."+tempPwd);

        if (StringUtils.isBlank(this.userPwd) &&
                StringUtils.isNotBlank(tempPwd)) {
            log.debug("??? tempPwd..."+tempPwd);
            setPassword(tempPwd);
        }
    }
}
