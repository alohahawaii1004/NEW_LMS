/*
 * @(#)ModificationInfo.java     
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
 * 수정정보 Domain
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class ModificationInfo {

    /**
     * 수정 ID
     */
    private String modificationId;

    /**
     * 수정일시
     */
    private Date modificationDatetime;

    public ModificationInfo() {
    }

    public ModificationInfo(String modificationId, Date modificationDatetime) {
        this.modificationId = modificationId;
        this.modificationDatetime = modificationDatetime;
    }

    public String getModificationId() {
        return modificationId;
    }

    public void setModificationId(String modificationId) {
        this.modificationId = modificationId;
    }

    public Date getModificationDatetime() {
        return modificationDatetime;
    }

    public void setModificationDatetime(Date modificationDatetime) {
        this.modificationDatetime = modificationDatetime;
    }
}
