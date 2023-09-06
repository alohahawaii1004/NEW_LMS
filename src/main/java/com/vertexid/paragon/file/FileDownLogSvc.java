/*
 * @(#)FileDownLogSvc.java     2022-11-15(015) 오후 4:48:54
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

import com.vertexid.commons.utils.IDUtils;
import com.vertexid.commons.utils.ParamMap;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 *     첨부파일 다운로드 로그
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class FileDownLogSvc extends BaseSvc {

    public static final String NAMESPACE = "com.vertexid.paragon.file.FileLog";

    @Resource
    CmmDAO cmmDAO;

    public void writeLog(ParamMap<String, Object> fileInfo, Map<String, Object> metaInfo) {

        FileDownLogDTO fileDownLogDTO = new FileDownLogDTO();
        fileDownLogDTO.setLogUid(IDUtils.getDateTimeUUID());
        fileDownLogDTO.setAtchName(fileInfo.getString("fileNm"));
        fileDownLogDTO.setAtchUid(fileInfo.getString("atchUid"));
        fileDownLogDTO.setFileSaveNm(fileInfo.getString("fileSaveNm"));
        fileDownLogDTO.setAtchMeta(String.valueOf(metaInfo));

        insertData(fileDownLogDTO);
    }

    private void insertData(FileDownLogDTO param) {
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insert"), param);
    }
}
