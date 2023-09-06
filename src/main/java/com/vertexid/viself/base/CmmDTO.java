/*
 * @(#)CmmDTO.java     
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

package com.vertexid.viself.base;

import com.vertexid.spring.utils.SessionUtils;

/**
 * 공용 DTO
 * @author Yang, Ki Hwa
 */
public class CmmDTO extends BaseVO{
    private static final long serialVersionUID = 9070205907361546283L;

    /**
     * Create, Update, Delete flag
     */
    private String cud;

    /**
     * Error Y/N flag
     */
    private String errYn;

    /**
     * Error code
     */
    private String errCd;

    /**
     * Error message
     */
    private String errMsg;

    private Object loginInfo;

    /**
     * CUD(Transaction) 유형
     * @author Yang, Ki Hwa
     */
    public static class CudType {
        /**
         * create type
         */
        public static final String CREATE = "C";

        /**
         * update type
         */
        public static final String UPDATE = "U";

        /**
         * delete type
         */
        public static final String DELETE = "D";
    }

    public CmmDTO() {
    }

    public String getCud() {
        return cud;
    }

    public void setCud(String cud) {
        this.cud = cud;
    }

    public String getErrYn() {
        return errYn;
    }

    public void setErrYn(String errYn) {
        this.errYn = errYn;
    }

    public String getErrCd() {
        return errCd;
    }

    public void setErrCd(String errCd) {
        this.errCd = errCd;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getLoginInfo() {

        if(null == loginInfo){
            loginInfo = SessionUtils.getLoginVO();
        }
        return loginInfo;
    }
}
