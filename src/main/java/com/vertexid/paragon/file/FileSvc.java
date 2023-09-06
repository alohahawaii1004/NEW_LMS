package com.vertexid.paragon.file;

import com.vertexid.commons.utils.*;
import com.vertexid.spring.utils.BaseProperties;
import com.vertexid.spring.utils.CmmProperties;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import com.vertexid.viself.hr.SysLoginVO;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Transactional
public class FileSvc extends BaseSvc {

    private static final String NAMESPACE = "com.vertexid.paragon.file.File";
    private static final String EXT_SEPARATOR = ".";
    private static final String FILE_SEPARATOR = "/";
    public static final String ARCHIVE_PATH = "archive";

    @Resource(name = "cmmDAO")
    private CmmDAO cmmDAO;

    @Resource
    DrmSvc drmSvc;

    public List<Object> getFileViewList(Map<String, Object> params) {
        if (StringUtils.isNotBlank((String) params.get("relUid")) ||
                StringUtils.isNotBlank((String) params.get("solMasUid")) ||
                StringUtils.isNotBlank((String) params.get("atchUid"))) {
            Map<String, Object> whereMap = new HashMap<>();
            whereMap.put("relUid", params.get("relUid"));
            whereMap.put("solMasUid", params.get("solMasUid"));
            whereMap.put("fileTpCd", params.get("fileTpCd"));
            whereMap.put("atchUid", params.get("atchUid"));
            whereMap.put("useYn", "Y");
            return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "list"),
                    whereMap);
        } else {
            return new ArrayList<>();
        }
    }

    public List<Object> getFileDownloadViewList(Map<String, Object> params) {
        if (StringUtils.isNotBlank((String) params.get("relUid")) ||
                StringUtils.isNotBlank((String) params.get("atchUid"))) {
            Map<String, Object> whereMap = new HashMap<>();
            whereMap.put("relUid", params.get("relUid"));
            whereMap.put("solMasUid", params.get("solMasUid"));
            whereMap.put("fileTpCd", params.get("fileTpCd"));
            whereMap.put("atchUid", params.get("atchUid"));
            whereMap.put("useYn", "Y");
            return cmmDAO.getList(
                    cmmDAO.getStmtByNS(NAMESPACE, "listForFileDownload"),
                    whereMap);
        } else {
            return new ArrayList<>();
        }
    }

    public List<Object> getFileDefaultViewList(Map<String, Object> params) {
        if (StringUtils.isNotBlank((String) params.get("defaultRelUid"))) {
            Map<String, Object> whereMap = new HashMap<>();
            String defaultRelUid = (String) params.get("defaultRelUid");
            if (defaultRelUid.contains(",")) {
                whereMap.put("arrRelUid", StringUtils.splitByWholeSeparatorPreserveAllTokens(defaultRelUid, ","));
            } else {
                whereMap.put("relUid", defaultRelUid);
            }
            whereMap.put("solMasUid", params.get("solMasUid"));
            whereMap.put("fileTpCd", params.get("fileTpCd"));
            whereMap.put("useYn", "Y");
            return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "list"),
                    whereMap);
        } else {
            return new ArrayList<>();
        }
    }


    public void doDeleteFileProc(Map<String, Object> params) {

        String atchUids = (String) params.get("delAtchs");
        if (StringUtils.isNotBlank(atchUids)) {
            String[] arrUids = StringUtils.splitByWholeSeparatorPreserveAllTokens(atchUids, ",");
            for (String arrUid : arrUids) {
                ParamMap<String, Object> param = new ParamMap<>();
                param.put("atchUid", arrUid);
                cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "delete"), param);
            }
        }
    }

    public void deleteRelFiles(Map<String, Object> params){
        FileDTO param = new FileDTO();
        param.setRelUid(String.valueOf(params.get("relUid")));
        param.setFileTpCd(String.valueOf(params.get("fileTpCd")));
        cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "deleteRel"), param);
    }

    /**
     * 파일업로드 (* DRM 처리를 수행할 경우 DrmSvc 구현해서 추가)
     *
     * @param request request(Multipart Request)
     * @param params  parameters
     * @return result info
     */
    public Map<String, Object> getUploadResult(HttpServletRequest request,
            Map<String, Object> params) {

        // 1. 파일리스트(업로드 포함) 정보 구성
        List<Object> fileInfoList =
                getFileInfoList(params, uploadFile(request, params));

        // 3. 데이터 기록
        saveData(fileInfoList, params);

        // 4. json 생성
        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("PROC_RESULT", "DONE");
        rtnMap.put("PROC_DATA", fileInfoList);
        rtnMap.put("files", fileInfoList);
        rtnMap.put("PROC_MESSAGE", "파일이 업로드 되었습니다.");

        return rtnMap;
    }

    @SuppressWarnings("unchecked")
    private void saveData(List<Object> fileInfoList,
            Map<String, Object> params) {

        String relUid = (null == params.get("relUid")) ? null :
                String.valueOf(params.get("relUid"));
        String solMasUid = (null == params.get("sol_mas_uid")) ? null :
                String.valueOf(params.get("sol_mas_uid"));
        String fileTpCd = (null == params.get("fileTpCd")) ? null :
                String.valueOf(params.get("fileTpCd"));
        String migFilePath = (null == params.get("migFilePath")) ? null :
        	String.valueOf(params.get("migFilePath"));
        SysLoginVO loginUser =
                (SysLoginVO) params.get(CommonConstants.SESSION_USER);

        int iLen = (fileInfoList == null || fileInfoList.isEmpty()) ? 0 :
                fileInfoList.size();
        for (int i = 0; i < iLen; i += 1) {
            ParamMap<String, Object> fileinfo =
                    (ParamMap<String, Object>) fileInfoList.get(i);

            FileDTO fileDTO = new FileDTO();
            fileDTO.setRelUid(relUid);
            fileDTO.setSolMasUid(solMasUid);
            fileDTO.setFileTpCd(fileTpCd);
            fileDTO.setFileSaveNm(fileinfo.getString("saveFileName"));
            fileDTO.setFileNm(fileinfo.getString("fileName"));
            fileDTO.setFileSize(fileinfo.getString("fileSize"));
            fileDTO.setRegLoginId(loginUser.getLoginId());
            fileDTO.setUptLoginId(loginUser.getLoginId());
            fileDTO.setUseYn("Y");
            fileDTO.setOrdNo((i + 1));
            fileDTO.setMigFilePath(migFilePath);

            if (!"false".equals(fileinfo.getString("isNew"))) {
                // insert
                // HACK isNew 가 true 이거나 link 인경우 생성
                fileDTO.setAtchUid(IDUtils.getRandomUUID());
                insertData(fileDTO);
            } else {
                // update
                fileDTO.setAtchUid(fileinfo.getString("atchUid"));
                updateData(fileDTO);
            }
        }// end of for
    }

    public void updateData(FileDTO fileDTO) {
    	cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "update"), fileDTO);
    }

    private void insertData(FileDTO fileDTO) {
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insert"), fileDTO);
    }

    @SuppressWarnings("unchecked")
    private List<Object> getFileInfoList(Map<String, Object> params,
            List<Object> saveList) {

        List<Object> fileInfoList = new ArrayList<>();

        // 1. hidden 값으로 올라온 리스트(fileInfos)
        List<Object> fileinfos = JsonUtil.parseJsonStringToList(
                StringUtils.defaultString((String)params.get("fileInfos"), null));
        int iLen = (fileinfos == null || fileinfos.isEmpty()) ? 0 :
                fileinfos.size();
        for (int i = 0; i < iLen; i += 1) {
            ParamMap<String, Object> fileinfo =
                    (ParamMap<String, Object>) fileinfos.get(i);
            String strIsNew = fileinfo.getString("isNew");
            if (!strIsNew.equals("true")) {
                // HACK 신규건 제외 처리
                fileInfoList.add(fileinfo);
            }
        }// end of for

        // 2. 업로드된 리스트
        int jLen =
                (saveList == null || saveList.isEmpty()) ? 0 : saveList.size();
        for (int j = 0; j < jLen; j += 1) {
            ParamMap<String, Object> fileinfo =
                    (ParamMap<String, Object>) saveList.get(j);
            fileInfoList.add(fileinfo);
        }// end of for

        return fileInfoList;
    }

    private List<Object> uploadFile(HttpServletRequest request,
            Map<String, Object> params) {
        if (!ServletFileUpload.isMultipartContent(request)) {
            return null;
        }

        // 보안성 강화를 위해 항상 랜덤하게 파일명 생성
//        String randomFileNameYn =
//                StringUtils.convertNull(params.get("randomFileNameYn"));
//        String overwriteYn = StringUtils.convertNull(params.get("overwriteYn"));

        boolean isDrmFlag = (CmmProperties.getDrmUseYn().equals("Y"));
        List<Object> list = new ArrayList<>();

        /*
         * HACK
         *
         * 아래 repository 설정을 하지않을 경우
         * window 에서 tomcat 등의 temp 폴더혹은 tomcat root 를 임시폴더로 잡음
         *     1. 권한이 없을 경우 오류 발생
         *     2. 지저분하게 tomcat root 등 에 임시 파일들 쌓임
         */
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 임시 저장소 설정
        String tempPath = BaseProperties.get("attach.saveTempPath");
        // 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
        File tempFolder = new File(tempPath);
        factory.setRepository(tempFolder);

        String fileTpCd = StringUtils.defaultString((String)params.get("fileTpCd"), null);
        String savePath =
                BaseProperties.get("attach.saveRootPath") + "/" + fileTpCd;
        try {
            FileUtils.forceMkdir(new File(tempPath));
            FileUtils.forceMkdir(new File(savePath));
        } catch (IOException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }
        String fileName;
        String fileSaveName;
        long fileSize;

        List<?> items;
        int itemCount = 0;
        try {
            ServletFileUpload upload = new ServletFileUpload(factory);
            items = upload.parseRequest(request);
            for (Object o : items) {
                FileItem item = (FileItem) o;
                itemCount += 1;
                // 첨부파일 인것 만
                if (!item.isFormField()) {
                    fileSize = item.getSize();
                    fileName = item.getName();
//                    if (fileName.contains("\\")) {
//                        fileName = fileName.substring(
//                                fileName.lastIndexOf("\\") + 1,
//                                fileName.length());
//                        String fileExt = FilenameUtils.getExtension(fileName);
//                    }

                    // 파일 확장자 체크
                    validateIsAllowFileExt(fileName);
                    validateIsDenyFileExt(fileName);

                    fileSaveName =
                            LocalDateTime.now().format(
                                    java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss.S")) + "." +
                                    (int) (new SecureRandom().nextDouble() *
                                            10) + "." + itemCount;
                    // REVIEW 아래코드는 보안상 취약하므로 사용하지 않음
                    /*
                    if ("N".equals(randomFileNameYn) ||
                            "Y".equals(overwriteYn)) {
                        //-- 파일명 랜덤생성 기능을 사용하지 않을 경우, 또는 덮어쓰기 기능을 사용할 경우 원본파일명 사용
                        fileSaveName = fileName;
                    }
                     */
                    File inputFile =
                            new File(savePath + FILE_SEPARATOR + fileSaveName);
                    item.write(inputFile);

                    // DRM 사용시 drmSvc 구현
                    if (isDrmFlag) {
                        drmSvc.packingFile(params, item);
                    }

                    ParamMap<String, Object> fileinfo = new ParamMap<>();
                    fileinfo.put("isNew", "true");
//                    fileinfo.put("fileTpCd", fileTpCd);
//                    fileinfo.put("relUid", relUid);
                    fileinfo.put("saveFileName", fileSaveName);
                    fileinfo.put("fileName", fileName);
                    fileinfo.put("fileSize", fileSize);
                    list.add(fileinfo);
                }
            }// end of for
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void validateIsAllowFileExt(String filename) {
        if (filename != null && !"".equals(filename)) {
            String filter = BaseProperties.get("attach.default.allowExt");
            if (filter != null && !"".equals(filter)) {
                filter = filter.toUpperCase();
                filename = filename.toUpperCase();
                String ext = FilenameUtils.getExtension(filename);
                if ("".equals(ext) || !(filter + ";").contains(ext + ";")) {
                    String msg = "허용되지 않은 파일 확장자입니다. [" + filename +
                            "]\n(※ 허용된 확장자 : " + filter + ")";
                    log.error(msg);
                    throw new RuntimeException(msg);
                }
            }
        }
    }

    private void validateIsDenyFileExt(String filename) {
        if (filename != null && !"".equals(filename)) {
            String filter = BaseProperties.get("attach.default.denyExt");
            if (filter != null && !"".equals(filter)) {
                filter = filter.toUpperCase();
                filename = filename.toUpperCase();
                String ext = FilenameUtils.getExtension(filename);
                if ("".equals(ext) ||
                        (";" + filter + ";").contains(";" + ext + ";")) {
                    String msg = "허용되지 않은 파일 확장자입니다. [" + filename +
                            "]\n(※ 허용되지 않은 확장자 : " + filter + ")";
                    log.error(msg);
                    throw new RuntimeException(msg);
                }
            }
        }
    }

    /**
     * 이미지 파일 뷰로 보여주기
     *
     * @param filePath 이미지 파일 실제 경로
     * @param response response
     */
    @SuppressWarnings("ThrowFromFinallyBlock")
    public void viewImage(final String filePath, HttpServletResponse response) {
        File imgFile = new File(filePath);

        if (!imgFile.isFile()) {

            // TODO 없는 경우 빈 이미지 파일 대체

            String msg = "파일이 존재하지 않습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        byte[] buf = new byte[1024];
        int readByte;
        int length;
        byte[] imgBuf;

        FileInputStream fileInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ServletOutputStream servletOutputStream = null;

        try {
            fileInputStream = new FileInputStream(imgFile);
            byteArrayOutputStream = new ByteArrayOutputStream();
            servletOutputStream = response.getOutputStream();

            while ((readByte = fileInputStream.read(buf)) != -1) {
                byteArrayOutputStream.write(buf, 0, readByte);
            }// end of while

            imgBuf = byteArrayOutputStream.toByteArray();
            length = imgBuf.length;
            servletOutputStream.write(imgBuf, 0, length);
            servletOutputStream.flush();

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            try {
                Objects.requireNonNull(servletOutputStream).close();
                Objects.requireNonNull(byteArrayOutputStream).close();
                Objects.requireNonNull(fileInputStream).close();
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 일괄 다운로드를 위한 zip 파일 만들기
     *
     * @param param zip 파일을 만들기 위한 parameter(relUid, fileTpCd, solMasUid)
     * @return zip 파일 정보
     */
    public FileDTO getZipInfo(Map<String, Object> param) {
        FileDTO fileDTO = null;

        // 1. 파일리스트 얻기
        List<ParamMap<String, Object>> fileList = getFileListByRelInfo(param);
        log.info("fileList........."+fileList);

        if(!fileList.isEmpty()){
            // 2. 압축파일 생성(및 임시 파일삭제)
            fileDTO = getZipFileInfo(fileList, param);
            param.put("zipSrcList", fileList);
        }

        return fileDTO;
    }

    private FileDTO getZipFileInfo(List<ParamMap<String, Object>> fileList, Map<String, Object> param) {

        // 1. 원본파일 복사
        // 1.1. 압축을 위한 임시폴더 생성
        String tmpNm = LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss.S")) + "." +
                (int) (new SecureRandom().nextDouble() *
                        10) + "." + "1";
        //기본저장경로
        String defaultSavePath = CmmProperties.getSaveRootPath();
        String tmpFolder = defaultSavePath + FILE_SEPARATOR + tmpNm;
        String archiveFolder = defaultSavePath + FILE_SEPARATOR + ARCHIVE_PATH;
        try {
            FileUtils.forceMkdir(new File(tmpFolder));
            FileUtils.forceMkdir(new File(archiveFolder));
        } catch (IOException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }

        // 1.2. 실파일 구성
        Set<String> filePathSet = null;
        try {
            filePathSet = getZipTagetFileSet(tmpFolder, fileList);
        } catch (IOException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }

        // 2. 압축파일 구성
        String archiveFilePath = defaultSavePath + FILE_SEPARATOR + ARCHIVE_PATH + FILE_SEPARATOR + tmpNm;
        makeZipFile(archiveFilePath, filePathSet);

        // 3. 압축파일 정보 설정
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileTpCd(ARCHIVE_PATH);
        fileDTO.setFileSaveNm(tmpNm);
        fileDTO.setFileNm(makeZipFileName(fileList.get(0), param));

        // 4. 임시파일 삭제
        removeTempFile(tmpFolder);

        return fileDTO;
    }

    private void removeTempFile(String tmpFolder) {
        File tmpRoot = new File(tmpFolder);
        try {
            FileUtils.deleteDirectory(tmpRoot);
        } catch (IOException e) {
            String msg = e.toString();
            log.error(msg);
            throw new RuntimeException(e);
        }
    }

    /**
     * ZIP 파일 이름
     *  - relUid 가 있을 경우 [솔루션유형_관리번호]파일유형-yyMMdd-HHmmss.zip
     *  - solMasUid 가 있을 경우 [솔루션유형_관리번호 외 건]관련파일-yyMMdd-HHmmss.zip
     *
     * @param relInfo 파일이름을 생성하기 위한 정보(solTpNm, mngNo, fileTpNm)
     * @param param 파일이름을 생성하기 위한 구분정보(relUid, solMasUid)
     * @return zip 파일이름
     */
    private String makeZipFileName(Map<String, Object> relInfo, Map<String, Object> param) {

        String solTpNm = (relInfo.get("solTpNm") != null) ?
                String.valueOf(relInfo.get("solTpNm")) : null;
        String mngNo = (relInfo.get("mngNo") != null) ?
                String.valueOf(relInfo.get("mngNo")) : null;
        String fileTpNm = (relInfo.get("fileTpNm") != null) ?
                String.valueOf(relInfo.get("fileTpNm")) : null;
        fileTpNm = StringUtils.replace(fileTpNm, " ", "");
        DateTime now = new DateTime();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyMMdd-HHmmss");
        String fileDtime = now.toString(fmt);

        String solMasUid = (param.get("solMasUid") != null) ?
                String.valueOf(param.get("solMasUid")) : null;
        if(StringUtils.isNotBlank(solMasUid)){
            String [] solMasUids = StringUtils.split(solMasUid, ",");
            fileTpNm = "첨부파일";
            if(solMasUids.length > 1){
                mngNo += " 외 "+ (solMasUids.length - 1) + "건";
            }
        }

        StringBuilder sbZipFileNm = new StringBuilder();

        if(StringUtils.isNotBlank(solTpNm) || StringUtils.isNotBlank(mngNo)){

            sbZipFileNm.append("[").append(solTpNm);
            if(StringUtils.isNotBlank(mngNo)){
                sbZipFileNm.append("_").append(mngNo);
            }
            sbZipFileNm.append("]");
        }

        if(StringUtils.isNotBlank(fileTpNm)){
            sbZipFileNm.append(fileTpNm).append("-");
        }

        sbZipFileNm.append(fileDtime)
                .append(".zip");

        return sbZipFileNm.toString();
    }

    private void makeZipFile(String zipFilePath, Set<String> filePathSet) {
        ZipOutputStream zipOutputStream = null;
        FileInputStream in = null;
        try{
            zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath));
            byte[] buf = new byte[1024];

            for(String filePath: filePathSet){
                File tmpFile = new File(filePath);
                if(tmpFile.exists()){
                    in = new FileInputStream(tmpFile);
                    zipOutputStream.putNextEntry(new ZipEntry(tmpFile.getName()));

                    //바이트 전송
                    int len;
                    while((len = in.read(buf)) > 0){
                        zipOutputStream.write(buf,0,len);
                    }
                    in.close();
                    zipOutputStream.closeEntry();
                }
            }// end of for
            zipOutputStream.close();

        }catch (Exception e){
            log.error(e.toString());
            throw new RuntimeException(e);
        }finally {
            if(zipOutputStream != null){
                try {
                    zipOutputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * zip 파일을 구성할 파일셋 만들기
     * @param tmpFolder zip 파일을 위한 임시 folder 경로
     * @param fileList 원본 파일셋 정보
     * @return zip 파일을 구성할 파일셋
     */
    private Set<String> getZipTagetFileSet(String tmpFolder, List<ParamMap<String, Object>> fileList)
            throws IOException {
        Set<String> filePathSet = new HashSet<>();
        String defaultSavePath = CmmProperties.getSaveRootPath();
        Map<String, Object> dupChkMap = new HashMap<>();
        for(ParamMap<String, Object> fileMap: fileList){

            // 1.2.1. 원본 경로
            String fileTpCd  = fileMap.getString("fileTpCd");
            String fileSaveNm  = fileMap.getString("fileSaveNm");
            String srcPath = "";
            // 마이그레이션 파일 처리
            String migFilePath = "";
            if(StringUtils.isNotEmpty(fileMap.getString("migFilePath"))) {	//-- 마이그레이션 첨부 파일
            	migFilePath = fileMap.getString("migFilePath");
            	log.debug("MIG_FILE_PATH::"+migFilePath);
            	int startIdx = migFilePath.indexOf("outputFile");
            	migFilePath = migFilePath.substring(startIdx, migFilePath.length());
            	log.debug("Convert MIG_FILE_PATH::"+migFilePath);
            	migFilePath = "old"+File.separator + migFilePath; //-- 마이그레이션의 경우 기본경로 하위 old 폴더 하위부터 진행.
            	log.debug("End MIG_FILE_PATH::"+migFilePath);
            }
            // MIG 파일 경로
           	if(StringUtils.isNotEmpty(fileMap.getString("migFilePath"))) {
           		srcPath = defaultSavePath + File.separator + migFilePath;
//           		srcPath = "old" + fileMap.getString("migFilePath");
           	}else {
           		srcPath = defaultSavePath + FILE_SEPARATOR + fileTpCd + FILE_SEPARATOR + fileSaveNm;
           	}


            // 1.2.2. 복사본 경로
            String solTpNm  = fileMap.getString("solTpNm");
            String mngNo  = fileMap.getString("mngNo");
            String fileTpNm  = fileMap.getString("fileTpNm");
            fileTpNm = StringUtils.replace(fileTpNm, " ", "");
            String fileNm  = fileMap.getString("fileNm");
            String fileNmWithoutExt  = StringUtils.substringBeforeLast(fileNm, EXT_SEPARATOR);
            String fileExt  = StringUtils.substringAfterLast(fileNm, EXT_SEPARATOR);

            // 파일명: [관리번호_파일유형]파일명.ext
            StringBuilder sbTgFileNm = new StringBuilder();
            if(StringUtils.isNotBlank(fileTpNm) || StringUtils.isNotBlank(mngNo)){
                sbTgFileNm.append("[");
                if(StringUtils.isNotBlank(mngNo)){
                    sbTgFileNm.append(mngNo).append("_");
                }
                sbTgFileNm.append(fileTpNm).append("]");
            }
            sbTgFileNm.append(fileNm);

            String tgFileName = sbTgFileNm.toString();
            if(dupChkMap.containsKey(tgFileName)){
                int icnt = (Integer)dupChkMap.get(tgFileName);
                icnt += 1;
                dupChkMap.put(tgFileName, icnt);

                sbTgFileNm = new StringBuilder();
                if(StringUtils.isNotBlank(fileTpNm) || StringUtils.isNotBlank(mngNo)){
                    sbTgFileNm.append("[");
                    if(StringUtils.isNotBlank(mngNo)){
                        sbTgFileNm.append(mngNo).append("_");
                    }
                    sbTgFileNm.append(fileTpNm).append("]");
                }
                sbTgFileNm.append(fileNmWithoutExt).append("(").append(icnt).append(")").append(EXT_SEPARATOR).append(fileExt);
                tgFileName = sbTgFileNm.toString();
            }else{
                dupChkMap.put(tgFileName, 0);
            }

            try {
                tgFileName = URLDecoder.decode(tgFileName, "UTF-8");
            } catch (Exception e) {
                log.error(e.toString());
                throw new RuntimeException(e);
            }
            String targetPath = tmpFolder + FILE_SEPARATOR + tgFileName;

            log.debug("srcPath....."+srcPath);
            log.debug("targetPath.."+targetPath);
            FileUtils.copyFile(new File(srcPath), new File(targetPath));

            filePathSet.add(targetPath);
        }// end of for

        return filePathSet;
    }

    /**
     * 압축할 첨부 Set 구하기
     * @param param parameter(relUid, fileTpCd, solMasUid)
     * @return 압축할 첨부 Set
     */
    @Transactional(readOnly = true)
    <E> List<E> getFileListByRelInfo(Map<String, Object> param) {
        String relUid = (param.get("relUid") != null) ?
                String.valueOf(param.get("relUid")) : null;
        String fileTpCd = (param.get("fileTpCd") != null) ?
                String.valueOf(param.get("fileTpCd")) : null;
        String solMasUid = (param.get("solMasUid") != null) ?
                String.valueOf(param.get("solMasUid")) : null;

        // 1. 파일 리스트 얻기
        if ((StringUtils.isBlank(relUid) || StringUtils.isBlank(fileTpCd)) &&
                StringUtils.isBlank(solMasUid)) {
            String msg = "필수 파라메터가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg, new Throwable(
                    "param:" + param));
        }

        return cmmDAO.getList(
                cmmDAO.getStmtByNS(NAMESPACE, "getFileListByRelInfo"), param);
    }

    public List<Object> getIpAtchFiles(Map<String,Object> params){

        Map<String,Object>resultMap = new HashMap<>();
        Map<String,Object>whereMap = new HashMap<>();

        whereMap.put("solMasUid", params.get("solMasUid"));
//    	resultMap.put("ALL_FILE",cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "allFileForSolMas"), whereMap));
        return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "allFileForSolMas"), whereMap);
        // 발명내용설명서 							IPS/DEV_CONT
//    	whereMap.put("fileTpCd", "IPS/DEV_CONT");
//    	resultMap.put("IPS/DEV_CONT",cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "viewList"), whereMap));
//    	// 권리승계합의서 							IPS/RIGHT_TRANS
//    	whereMap.put("fileTpCd", "IPS/RIGHT_TRANS");
//    	resultMap.put("IPS/RIGHT_TRANS",cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "viewList"), whereMap));
//    	// [발명자 인터뷰] 선행기술조사서				IPS/RESULT/WRT
//    	whereMap.put("fileTpCd", "IPS/RESULT/WRT");
//    	resultMap.put("IPS/RESULT/WRT",cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "viewList"), whereMap));
////    	 [발명자 인터뷰] 발명자인터뷰결과보고서			IPS/CS/REPO/WRT
//    	whereMap.put("fileTpCd", "IPS/CS/REPO/WRT");
//    	resultMap.put("IPS/CS/REPO/WRT",cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "viewList"), whereMap));
////    	 최종명세서								IPS/FIN_REPO
//    	whereMap.put("fileTpCd","IPS/FIN_REPO");
//    	resultMap.put("IPS/FIN_REPO",cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "viewList"), whereMap));
//    	// 출원서류								IPS/APP_DOC
//    	whereMap.put("fileTpCd", "IPS/APP_DOC");
//    	resultMap.put("IPS/APP_DOC",cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "viewList"), whereMap));
//    	// 출원관련서류								IPS/REL_DOC
//    	whereMap.put("fileTpCd", "IPS/REL_DOC");
//    	resultMap.put("IPS/REL_DOC",cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "viewList"), whereMap));
//    	// 출원대표도면								IPS/MAIN_DRAW / TM_REQ
//    	whereMap.put("fileTpCd", "IPS/MAIN_DRAW");
//    	resultMap.put("IPS/MAIN_DRAW",cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "viewList"), whereMap));
//    	// 등록증									IPS/REGISTRATION
//    	whereMap.put("fileTpCd", "IPS/REGISTRATION");
//    	resultMap.put("IPS/REGISTRATION",cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "viewList"), whereMap));
//    	// 등록증									PR/REG/WRT(저작권등록증)
//    	whereMap.put("fileTpCd", "PR/REG/WRT");
//    	resultMap.put("PR/REG/WRT",cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "viewList"), whereMap));
//    	// 등록공고								IPS/REG_NOTI
//    	whereMap.put("fileTpCd", "IPS/REG_NOTI");
//    	resultMap.put("IPS/REG_NOTI",cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "viewList"), whereMap));
//    	// 등록첨부파일								IPS/RG
//    	whereMap.put("fileTpCd", "IPS/RG");
//    	resultMap.put("IPS/RG",cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "viewList"), whereMap));
//    	// 이관첨부파일   							MIG/AT
//    	/*resultMap.put("MIG/FILE",cmmDAO.getList(cmmDAO.getStmtByNS(MIG_NAMESPACE, "listMigFile"), whereMap));*/
//
//    	// --프로그램
//    	// 신청 및 신청면세서 PR/REG
//         whereMap.put("fileTpCd", "PR/REG");
//         resultMap.put("PR/REG",cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "viewList"), whereMap));
//         // 계정 정보 PR/PRI
//         whereMap.put("fileTpCd", "PR/PRI");
//         resultMap.put("PR/PRI",cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "viewList"), whereMap));
//         // 기타(단독신청승낙서) PR/ETC
//         whereMap.put("fileTpCd", "PR/ETC");
//         resultMap.put("PR/ETC",cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "viewList"), whereMap));
//         // 등록파일 PR/RES
//         whereMap.put("fileTpCd", "PR/RES");
//         resultMap.put("PR/RES",cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "viewList"), whereMap));


//    	return resultMap;
    }
}
