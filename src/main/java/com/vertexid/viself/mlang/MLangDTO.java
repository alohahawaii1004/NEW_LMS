/*
 * @(#)MLangDTO.java     2022-03-14(014) 오후 1:19
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
package com.vertexid.viself.mlang;

import com.vertexid.viself.base.CmmDTO;

import java.util.List;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class MLangDTO extends CmmDTO {

    private static final long serialVersionUID = -9159788460725456592L;

    private String langUid;
    private String langCd;
    private String ko;
    private String en;
    private String ja;
    private String zh;

    private List<MLangDTO> list;

    public MLangDTO() {
    }

    public String getLangUid() {
        return langUid;
    }

    public void setLangUid(String langUid) {
        this.langUid = langUid;
    }

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }

    public String getKo() {
        return ko;
    }

    public void setKo(String ko) {
        this.ko = ko;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getJa() {
        return ja;
    }

    public void setJa(String ja) {
        this.ja = ja;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public List<MLangDTO> getList() {
        return list;
    }

    public void setList(List<MLangDTO> list) {
        this.list = list;
    }
}
