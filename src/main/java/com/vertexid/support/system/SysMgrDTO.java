/*
 * @(#)SysMgrDTO.java     2023-03-29 오전 9:30
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

import java.util.List;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class SysMgrDTO extends CmmDTO {
    private static final long serialVersionUID = -5739627109868227223L;

    private String systemId;
    private String loginId;
    private String priority;
    private String mailSndYn;

    private List<SysMgrDTO> list;

    public SysMgrDTO() {
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getMailSndYn() {
        return mailSndYn;
    }

    public void setMailSndYn(String mailSndYn) {
        this.mailSndYn = mailSndYn;
    }

    public List<SysMgrDTO> getList() {
        return list;
    }

    public void setList(List<SysMgrDTO> list) {
        this.list = list;
    }
}
