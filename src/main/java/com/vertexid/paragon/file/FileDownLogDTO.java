/*
 * @(#)FileDownLogDTO.java     2022-11-15(015) 오후 5:03:09
 *
 * Copyright 2022 JaYu.space
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
package com.vertexid.paragon.file;

import com.vertexid.viself.base.CmmDTO;

/**
 * <b>Description</b>
 * <pre>
 *     첨부파일 로그 DTO
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class FileDownLogDTO extends CmmDTO {
    private static final long serialVersionUID = 6161312015262596300L;

    private String logUid;
    private String downDte;
    private String downLoginId;
    private String atchName;
    private String atchUid;
    private String fileSaveNm;
    private String atchMeta;

    public FileDownLogDTO() {
    }

    public String getLogUid() {
        return logUid;
    }

    public void setLogUid(String logUid) {
        this.logUid = logUid;
    }

    public String getDownDte() {
        return downDte;
    }

    public void setDownDte(String downDte) {
        this.downDte = downDte;
    }

    public String getDownLoginId() {
        return downLoginId;
    }

    public void setDownLoginId(String downLoginId) {
        this.downLoginId = downLoginId;
    }

    public String getAtchName() {
        return atchName;
    }

    public void setAtchName(String atchName) {
        this.atchName = atchName;
    }

    public String getAtchUid() {
        return atchUid;
    }

    public void setAtchUid(String atchUid) {
        this.atchUid = atchUid;
    }

    public String getFileSaveNm() {
        return fileSaveNm;
    }

    public void setFileSaveNm(String fileSaveNm) {
        this.fileSaveNm = fileSaveNm;
    }

    public String getAtchMeta() {
        return atchMeta;
    }

    public void setAtchMeta(String atchMeta) {
        this.atchMeta = atchMeta;
    }
}
