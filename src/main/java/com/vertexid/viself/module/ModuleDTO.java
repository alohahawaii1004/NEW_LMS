/*
 * @(#)ModuleDTO.java     2021-03-16(016) 오후 2:28
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
 *     Module DTO
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class ModuleDTO extends CmmDTO {
    private static final long serialVersionUID = 4933546668213614121L;

    private String moduleId;
    private String moduleDesc;
    private String useYn;

    private List<ModuleDTO> list;

    public ModuleDTO() {
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleDesc() {
        return moduleDesc;
    }

    public void setModuleDesc(String moduleDesc) {
        this.moduleDesc = moduleDesc;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public List<ModuleDTO> getList() {
        return list;
    }

    public void setList(List<ModuleDTO> list) {
        this.list = list;
    }
}
