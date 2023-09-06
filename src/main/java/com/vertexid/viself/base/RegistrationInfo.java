/*
 * @(#)RegistrationInfo.java     
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

import java.util.Date;

/**
 * <b>Description</b>
 * 등록정보 Domain
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class RegistrationInfo {

    /**
     * 등록 ID
     */
    private String registeredId;

    /**
     * 등록일시
     */
    private Date registerdDatetime;

    public RegistrationInfo() {
    }

    public RegistrationInfo(String registeredId, Date registerdDatetime) {
        this.registeredId = registeredId;
        this.registerdDatetime = registerdDatetime;
    }

    public String getRegisteredId() {
        return registeredId;
    }

    public void setRegisteredId(String registeredId) {
        this.registeredId = registeredId;
    }

    public Date getRegisterdDatetime() {
        return registerdDatetime;
    }

    public void setRegisterdDatetime(Date registerdDatetime) {
        this.registerdDatetime = registerdDatetime;
    }
}
