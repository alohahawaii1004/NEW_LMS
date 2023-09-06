/*
 * @(#)ModuleUrlDTO.java     2021-03-17(017) 오후 2:35
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
package com.vertexid.viself.module;

import com.vertexid.viself.base.CmmDTO;

import java.util.List;

/**
 * <b>Description</b>
 * <pre>
 *     Module Url DTO
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class ModuleUrlDTO extends CmmDTO {

    private static final long serialVersionUID = -3115886597147932665L;

    private String moduleId;
    private String accesUrl;
    private String repreUrlEnable;

    private List<ModuleUrlDTO> list;

    public ModuleUrlDTO() {
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getAccesUrl() {
        return accesUrl;
    }

    public void setAccesUrl(String accesUrl) {
        this.accesUrl = accesUrl;
    }

    public String getRepreUrlEnable() {
        return repreUrlEnable;
    }

    public void setRepreUrlEnable(String repreUrlEnable) {
        this.repreUrlEnable = repreUrlEnable;
    }

    public List<ModuleUrlDTO> getList() {
        return list;
    }

    public void setList(List<ModuleUrlDTO> list) {
        this.list = list;
    }
}
