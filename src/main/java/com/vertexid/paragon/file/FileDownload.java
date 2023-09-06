package com.vertexid.paragon.file;

import com.vertexid.commons.utils.ParamMap;
import com.vertexid.spring.utils.SessionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import static com.vertexid.paragon.file.FileCtrl.ZIP_INFO;

/**
 * file download view
 */
public class FileDownload extends AbstractView {

    /**
     * logger
     */
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @SuppressWarnings({"NullableProblems", "ThrowFromFinallyBlock",
            "unchecked"})
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        ParamMap<String, Object> fileMap =
                (ParamMap<String, Object>) model.get("fileInfo");
        OutputStream out = null;
        FileInputStream fis = null;

        try {

            if (null == fileMap || fileMap.isEmpty()) {
                String msg = "NO DATA";
                log.error(msg);
                throw new RuntimeException(msg);
            }

            File file = (File) fileMap.get("downloadFile");
            String fileName = fileMap.getString("fileNm");
            fileName = URLEncoder.encode(fileName, "UTF-8");
            // 파일명 공백 처리
            fileName = fileName.replaceAll("\\+", "%20");

            response.setContentType(getContentType());
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + fileName + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");

            out = response.getOutputStream();
            fis = new FileInputStream(file);
            FileCopyUtils.copy(fis, out);

        } catch (Exception e) {
            String msg = e.toString();
            log.error(msg);
            throw new RuntimeException(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    String msg = e.toString();
                    log.error(msg);
                    throw new RuntimeException(e);
                }
            }
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    String msg = e.toString();
                    log.error(msg);
                    throw new RuntimeException(e);
                }
            }
        }

        // check zip file and delete
        deleteTmpZip(fileMap);
    }

    private void deleteTmpZip(ParamMap<String, Object> fileMap) {
        HttpSession session = SessionUtils.getSession();
        ParamMap<String, Object> zipFileMap =
                (ParamMap<String, Object>) session.getAttribute(ZIP_INFO);
        if (zipFileMap != null && !zipFileMap.isEmpty() &&
                zipFileMap.equals(fileMap)) {
            session.removeAttribute(ZIP_INFO);
            File file = (File) fileMap.get("downloadFile");
            try {
                FileUtils.delete(file);
            } catch (IOException e) {
                log.error(e.toString());
                throw new RuntimeException(e);
            }
        }
    }
}
