/*
 * @(#)CKEditorImageSvc.java     2022-02-08(008) 오전 9:13
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
package com.vertexid.paragon.file;

import com.vertexid.commons.utils.IDUtils;
import com.vertexid.spring.utils.BaseProperties;
import com.vertexid.viself.base.BaseSvc;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 *     CKEditor 용 이미지 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class CKEditorImageSvc extends BaseSvc {
    public static final String IMAGE_FOLDER = "EDITOR-IMG";
    public static final String IMAGE_URL = "/editor/viewImg";

    @Resource
    FileSvc fileSvc;

    public Map<String, Object> upload(HttpServletRequest req) {
        Map<String, Object> rtnMap = new HashMap<>();

        String uid = IDUtils.getRandomUUID();
        DateTime dateTime = new DateTime();
        String nowMonth = dateTime.toString("yyyyMM");

        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        if (!isMultipart) {
            log.error("이미지 파일 업로드가 아닙니다.");
            throw new RuntimeException("이미지 파일 업로드가 아닙니다.");
        }

        try {
            String savePath =
                    BaseProperties.get("attach.saveRootPath") + File.separator +
                            IMAGE_FOLDER + File.separator + nowMonth;
            String tempPath = BaseProperties.get("attach.saveTempPath");

            log.info("savePath...." + savePath);
            log.info("tempPath...." + tempPath);

            FileUtils.forceMkdir(new File(savePath));
            FileUtils.forceMkdir(new File(tempPath));

            DiskFileItemFactory factory = new DiskFileItemFactory();
            File folder = new File(tempPath);
            factory.setRepository(folder);

            int itemCount = 0;
//            List<Object> fileinfosFilter = new ArrayList<>();

            ServletFileUpload upload = new ServletFileUpload(factory);
            List<?> items = upload.parseRequest(req);
            String fileName;
            String ckUploadImgFile;
            String imgUrl;
            for (Object o : items) {
                FileItem item = (FileItem) o;
                // 첨부파일 인것 만
                if (!item.isFormField()) {
                    fileName = item.getName();

                    // 확장자 검사
                    fileSvc.validateIsAllowFileExt(fileName);

                    ckUploadImgFile =
                            savePath + File.separator + uid + "_" + fileName;
                    log.info("ckUploadImgFile...." + ckUploadImgFile);
                    item.write(new File(ckUploadImgFile));

                    imgUrl = IMAGE_URL + "?uid=" + uid + "&fileName=" +
                            fileName + "&nowMonth=" + nowMonth;

                    rtnMap.put("fileName", fileName);
                    rtnMap.put("url", imgUrl);
                    itemCount += 1;
                }
            }// end of for

            log.info("itmCnt.................." + itemCount);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return rtnMap;
    }

    public void viewImage(String nowMonth, String uid, String fileName,
            HttpServletResponse res) {
        String savePath =
                BaseProperties.get("attach.saveRootPath") + File.separator +
                        IMAGE_FOLDER + File.separator + nowMonth;
        String ckUploadImgFile =
                savePath + File.separator + uid + "_" + fileName;

        fileSvc.viewImage(ckUploadImgFile, res);
    }
}
