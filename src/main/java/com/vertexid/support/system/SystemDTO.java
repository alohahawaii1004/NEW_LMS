/*
 * @(#)SystemDTO.java     2023-03-27 오후 2:26
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
package com.vertexid.support.system;

import com.vertexid.viself.base.CmmDTO;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class SystemDTO extends CmmDTO {
    private static final long serialVersionUID = 7534164679708038631L;
    private String systemId;
    private String orgId;
    private String systemName;
    private String productTpCd;
    private String systemUrl;
    private String introduceDt;
    private String smStartdt;
    private String smEnddt;
    private String useYn;
    private String sysDesc;

    public SystemDTO() {
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getProductTpCd() {
        return productTpCd;
    }

    public void setProductTpCd(String productTpCd) {
        this.productTpCd = productTpCd;
    }

    public String getSystemUrl() {
        return systemUrl;
    }

    public void setSystemUrl(String systemUrl) {
        this.systemUrl = systemUrl;
    }

    public String getIntroduceDt() {
        return introduceDt;
    }

    public void setIntroduceDt(String introduceDt) {
        this.introduceDt = introduceDt;
    }

    public String getSmStartdt() {
        return smStartdt;
    }

    public void setSmStartdt(String smStartdt) {
        this.smStartdt = smStartdt;
    }

    public String getSmEnddt() {
        return smEnddt;
    }

    public void setSmEnddt(String smEnddt) {
        this.smEnddt = smEnddt;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getSysDesc() {
        return sysDesc;
    }

    public void setSysDesc(String sysDesc) {
        this.sysDesc = sysDesc;
    }
}
