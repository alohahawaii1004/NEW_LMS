/*
 * @(#)UrlDTO.java     2021-03-31(031) 오후 1:53
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
package com.vertexid.viself.auth;

import com.vertexid.viself.base.CmmDTO;

import java.util.List;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class UrlDTO extends CmmDTO {
    private static final long serialVersionUID = -4104234561085749500L;

    private String accesUrl;
    private String urlDesc;
    private String alwDiv;
    private String useYn;

    private List<UrlDTO> list;

    public UrlDTO() {
    }

    public String getAccesUrl() {
        return accesUrl;
    }

    public void setAccesUrl(String accesUrl) {
        this.accesUrl = accesUrl;
    }

    public String getUrlDesc() {
        return urlDesc;
    }

    public void setUrlDesc(String urlDesc) {
        this.urlDesc = urlDesc;
    }

    public String getAlwDiv() {
        return alwDiv;
    }

    public void setAlwDiv(String alwDiv) {
        this.alwDiv = alwDiv;
    }


    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public List<UrlDTO> getList() {
        return list;
    }

    public void setList(List<UrlDTO> list) {
        this.list = list;
    }
}
