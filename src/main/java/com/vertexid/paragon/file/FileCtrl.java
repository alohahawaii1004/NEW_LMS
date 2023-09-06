package com.vertexid.paragon.file;

import com.vertexid.commons.utils.CommonConstants;
import com.vertexid.commons.utils.ParamMap;
import org.apache.commons.lang3.StringUtils;
import com.vertexid.spring.utils.BaseProperties;
import com.vertexid.spring.utils.CmmProperties;
import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.base.BaseCtrl;
import com.vertexid.viself.hr.SysLoginVO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import static com.vertexid.commons.view.ViewType.JSON_VIEW;
import static com.vertexid.viself.base.MessageCode.COMPLETE;
import static com.vertexid.viself.base.MessageCode.ERROR;
import static com.vertexid.viself.base.ModelAttribute.ERROR_FLAG;
import static com.vertexid.viself.base.ModelAttribute.MSG;

@Controller
public class FileCtrl extends BaseCtrl {

    public static final String ZIP_INFO = "ZIP_INFO";
    public static final String ZIP_META = "ZIP_META";
    @Resource
    FileSvc fileSvc;

    @Resource
    DrmSvc drmSvc;

    @Resource
    BatchDownloadAuthoritySvc batchDownloadAuthoritySvc;

    @Resource
    FileDownLogSvc fileDownLogSvc;

    /**
     * 파일 작성 폼
     *
     * @param model  model
     * @param params parameters
     * @return view
     */
    @RequestMapping("/paragon/file/fileWrite")
    public String writeForm(ModelMap model,
            @RequestParam
            Map<String, Object> params) {

        String defMaxCnt = BaseProperties.get("attach.default.maxFileCount");
        int maxCnt = Integer.parseInt(defMaxCnt);
        int paramMaxCnt = Integer.parseInt(
                StringUtils.defaultString(StringUtils.defaultIfEmpty((String)params.get("maxFileCount"),
                        defMaxCnt)));

        if (maxCnt < paramMaxCnt) {
            paramMaxCnt = maxCnt;
        }

        log.debug("params..........??????" + params);
        model.addAttribute("saveRootPath",
                BaseProperties.get("attach.saveRootPath"));
        model.addAttribute("maxFileSize",
                BaseProperties.get("attach.default.maxFileSize"));
        model.addAttribute("maxFileCount", Integer.toString(paramMaxCnt));
        model.addAttribute("allowExt",
                StringUtils.defaultString(StringUtils.defaultIfEmpty((String)params.get("allowExt"),
                        BaseProperties.get("attach.default.allowExt"))));
        model.addAttribute("denyExt",
                StringUtils.defaultString(StringUtils.defaultIfEmpty((String)params.get("denyExt"),
                        BaseProperties.get("attach.default.denyExt"))));

        model.addAttribute("viewResult", fileSvc.getFileViewList(params));
        model.addAttribute("defaultViewResult",
                fileSvc.getFileDefaultViewList(params));
        model.addAttribute("relUid", params.get("relUid"));
        model.addAttribute("defaultRelUid", params.get("defaultRelUid"));
        model.addAttribute("fileTpCd", params.get("fileTpCd"));
        model.addAttribute("requiredYn", params.get("requiredYn"));
        model.addAttribute("pdfConvYn", params.get("pdfConvYn"));
        model.addAttribute("height", params.get("height"));

        return "/paragon/file/fileWrite";
    }

    /**
     * 파일 View 폼
     *
     * @param model  model
     * @param params parameters
     * @return view
     */
    @RequestMapping("/paragon/file/fileView")
    public String viewForm(ModelMap model,
            @RequestParam
            Map<String, Object> params) {


        log.debug("params..........??????" + params);
        model.addAttribute("saveRootPath",
                BaseProperties.get("attach.saveRootPath"));
        model.addAttribute("maxFileSize",
                BaseProperties.get("attach.default.maxFileSize"));
        model.addAttribute("maxFileCount",
                BaseProperties.get("attach.default.maxFileCount"));
        model.addAttribute("allowExt",
                StringUtils.defaultString(StringUtils.defaultIfEmpty((String)params.get("allowExt"),
                        BaseProperties.get("attach.default.allowExt"))));
        model.addAttribute("denyExt",
                StringUtils.defaultString(StringUtils.defaultIfEmpty((String)params.get("denyExt"),
                        BaseProperties.get("attach.default.denyExt"))));

        model.addAttribute("viewResult", fileSvc.getFileViewList(params));
        model.addAttribute("defaultViewResult",
                fileSvc.getFileDefaultViewList(params));
        model.addAttribute("relUid", params.get("relUid"));
        model.addAttribute("defaultRelUid", params.get("defaultRelUid"));
        model.addAttribute("fileTpCd", params.get("fileTpCd"));
        model.addAttribute("requiredYn", params.get("requiredYn"));
        model.addAttribute("pdfConvYn", params.get("pdfConvYn"));

        return "/paragon/file/fileView";
    }


    /**
     * 파일 삭제 로직
     *
     * @param params parameters
     * @return view
     */
    @RequestMapping("/paragon/file/File/beDelete/json")
    public String doProc(
            @RequestParam
            Map<String, Object> params) {
        log.debug("params..........??????" + params);
        fileSvc.doDeleteFileProc(params);
        return JSON_VIEW.getViewId();
    }


    /**
     * 파일 업로드 로직
     *
     * @param req    request
     * @param model  model
     * @param params parameters
     * @return view
     */
    @RequestMapping("/paragon/file/File/saveProc/json")
    public String doProc(HttpServletRequest req, ModelMap model,
            @RequestParam
            Map<String, Object> params) {

        SysLoginVO loginUser = (SysLoginVO) SessionUtils.getLoginVO();
        params.put(CommonConstants.SESSION_USER, loginUser);
        log.debug("params..........??????" + params);
        model.addAttribute("result", fileSvc.getUploadResult(req, params));
        log.debug("result..........??????" + model.get("result"));

        return JSON_VIEW.getViewId();
    }
//    /**
//     * 타 시스템 파일 다운 링크 로직
//     * @param params parameters
//     * @return view
//     */
//    @RequestMapping("/ips/fileDown")
//    public ModelAndView fileDown(
//            HttpServletRequest  request,
//            @RequestParam
//            Map<String, Object> params) {
//
//        //-- 파라메터 셋팅
//        boolean fileUnpackingBoolean = false;
//        String currentRequestUrl = request.getServerName().toString();
//        String reqIp             = request.getRemoteAddr();
//
//        //-- 내부 외부망 확인
//        if(currentRequestUrl.startsWith("ips.dgmif")){    //- 내부
//            fileUnpackingBoolean = true;
//        }else if(currentRequestUrl.startsWith("kips.kmedihub")){ //-- 외부
//            fileUnpackingBoolean = false;
//        }
//        if(!fileUnpackingBoolean) {
//            throw new RuntimeException("외부에서는 해당링크를 사용할 수 없습니다.  접근시도IP : "+ reqIp);
//        }
//        List<Object> fileList = fileSvc.getFileViewList(params);
//        ParamMap<String, Object> fileMap = new ParamMap<String, Object>();
//
//        if(fileList != null && !fileList.isEmpty()) {
//            fileMap = (ParamMap<String, Object>)fileList.get(0);
//        }
//
//        String saveRoot = CmmProperties.getSaveRootPath();
//        String fileTpCd  = fileMap.getString("fileTpCd");
//        String fileSaveNm  = fileMap.getString("fileSaveNm");
//        String fileNm  = fileMap.getString("fileNm");
//        String savePath = saveRoot + "/"+fileTpCd+"/" + fileSaveNm;
//        fileMap.put("savePath", saveRoot + "/"+fileTpCd);
//
//        try {
//            fileNm = URLDecoder.decode(fileNm, "UTF-8");
//        } catch (UnsupportedEncodingException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//
//        String realPath =  saveRoot + "/TMP/" + fileNm;
//
//        log.info("@@@@@@@@@@@@@@@FileDown(1) START@@@@@@@@@@@@@@@");
//
//        if(CmmProperties.getDrmUseYn().equals("N")) {
//            savePath = saveRoot + "/"+fileTpCd+"/"+fileSaveNm;
//        }else {
//            if(fileUnpackingBoolean) {
//                savePath = saveRoot + "/"+fileTpCd+"/"+fileSaveNm;
//            }else {
//                realPath = saveRoot + "/"+fileTpCd+"/" + "/DEC/"+ fileNm;
//                savePath = saveRoot + "/"+fileTpCd+"/" + "/DEC/"+fileSaveNm;
//
//                Map<String,Object> drmLogMap = new HashMap<>();
//                drmLogMap = DrmUtil.unPackingFile(fileMap);
//
//
//                SysLoginVO loginUser = (SysLoginVO)SessionUtils.getLoginVO();
//                drmLogMap.put("logUid", StringUtils.getRandomUUID());
//                drmLogMap.put("typeCd", "dec");
//                drmLogMap.put("savePath", saveRoot + "/"+fileTpCd );
//                drmLogMap.put("fileNm", fileNm );
//                drmLogMap.put("loginId", loginUser.getLoginId());
//                drmLogMap.put("loginIp", loginUser.getLoginIp());
//
//                //drmLog 인서트
//                cmmDAO.insert(cmmDAO.getStmtByNS(DRM_NAMESPACE,"insert"),drmLogMap);
//            }
//        }
//        // 로그 정보 입력해야함.
//
//        InputStream inputStream = FileCtrl.class.getResourceAsStream(savePath);
//
//        File saveFile = new File(savePath);
//        File realFile = new File(realPath);
//        try {
//            FileUtils.copyFile(saveFile, realFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//            }
//
//        log.debug(realFile.getName());
//        log.debug(realPath);
//
//        log.info("@@@@@@@@@@@@@@@FileDown(1) END@@@@@@@@@@@@@@@");
//
//        return new ModelAndView("download", "downloadFile", realFile);
//    }
    /**
     * 파일 다운로드
     *
     * @param params parameters
     * @return view
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/paragon/file/download")
    public ModelAndView fileDownload(
            @RequestParam
            Map<String, Object> params) {

        List<Object> fileList = fileSvc.getFileViewList(params);
        ParamMap<String, Object> fileMap = new ParamMap<>();

        if (fileList != null && !fileList.isEmpty()) {
            fileMap = (ParamMap<String, Object>) fileList.get(0);
        }

        String saveRoot = CmmProperties.getSaveRootPath();
        String fileTpCd = fileMap.getString("fileTpCd");

        // DRM 처리
        if (CmmProperties.getDrmUseYn().equals("Y")) {
            fileMap.put("savePath", saveRoot + File.separator + fileTpCd);
            drmSvc.unPackingFile(fileMap);
        }
        // 마이그레이션 파일 처리
        String migFilePath = "";
        if(StringUtils.isNotEmpty(fileMap.getString("migFilePath"))) {    //-- 마이그레이션 첨부 파일
            migFilePath = fileMap.getString("migFilePath");
            log.debug("MIG_FILE_PATH::"+migFilePath);
            int startIdx = migFilePath.indexOf("outputFile");
            migFilePath = migFilePath.substring(startIdx, migFilePath.length());
            log.debug("Convert MIG_FILE_PATH::"+migFilePath);
            migFilePath = "old"+File.separator + migFilePath; //-- 마이그레이션의 경우 기본경로 하위 old 폴더 하위부터 진행.
            log.debug("End MIG_FILE_PATH::"+migFilePath);
        }

        // 다운로드 파일
        String fileSaveNm = fileMap.getString("fileSaveNm");
        String savePath = saveRoot + File.separator + fileTpCd + File.separator + fileSaveNm;
        if(StringUtils.isNotEmpty(migFilePath)) {    //-- 마이그레이션 첨부 경로
            savePath = saveRoot + File.separator + migFilePath;
        }
        log.debug("SAVE_PATH:"+savePath);
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {
            String msg = "첨부파일이 존재하지 않습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }
        fileMap.put("downloadFile", saveFile);

        fileDownLogSvc.writeLog(fileMap, params);

        return new ModelAndView("download", "fileInfo", fileMap);
    }

    /**
     * 파일 양식 다운로드
     *
     * @param params parameters
     * @return view
     */
    @RequestMapping("/paragon/file/download/form")
    public ModelAndView formDownload(
            @RequestParam
            Map<String, Object> params) {
        ParamMap<String, Object> fileMap = new ParamMap<>();
        String saveRoot = CmmProperties.getSaveRootPath();
        String fileNm = (String) params.get("fileNm");
        String tempPath = saveRoot + "/TMP/" + fileNm;
        try {
            fileNm = URLDecoder.decode(fileNm, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        String fullPath = "/form/form/" + fileNm;
        InputStream inputStream = FileCtrl.class.getResourceAsStream(fullPath);
        File file = new File(tempPath);
        if (inputStream != null) {
            try {
//                file = File.createTempFile(StringUtils.split(fileNm, ".")[0], "."+StringUtils.split(fileNm, ".")[1]);
                FileUtils.copyInputStreamToFile(inputStream, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.debug(file.getName());
        log.debug(fullPath);

        log.info("@@@@@@@@@@@@@@@FileDown(1) END@@@@@@@@@@@@@@@");
        fileMap.put("downloadFile", file);
        fileMap.put("fileNm", file.getName());
        return new ModelAndView("download", "fileInfo", fileMap);
    }

    /**
     * 일괄다운로드를 위한 zip 파일 만들기
     *
     * @param modelMap zip 파일 uid 를 넘기기 위한 model
     * @param param    zip 파일을 만들기 위한 parameter(relUid, fileTpCd, solMasUid)
     * @return json view
     */
    @RequestMapping(value = "/paragon/file/download/getMulti",
            method = RequestMethod.POST)
    public String makeMulti(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {

        modelMap.clear();
        String errorFlag = COMPLETE_RESULT;
        String result = "";


        // make zip file and get zip info
        // 1. make zip file
        FileDTO zipInfo = null;

        //check authority
        if(batchDownloadAuthoritySvc.checkAuthority(param)){
            zipInfo = fileSvc.getZipInfo(param);
        }else{
            String msg = "권한이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        if(zipInfo == null){
            result = "압축파일 생성에 실패 했습니다.";
            log.error("MSG:"+result);
            if (isProd()) {
                result = ERROR.getMsgCode();
            }
            errorFlag = ERROR_RESULT;
        }

        if (StringUtils.isEmpty(result)) {
            result = COMPLETE.getMsgCode();

            // 마이그레이션 파일 처리
            String migFilePath = "";
            if(StringUtils.isNotEmpty(zipInfo.getMigFilePath())) {    //-- 마이그레이션 첨부 파일
                migFilePath = zipInfo.getMigFilePath();
                log.debug("MIG_FILE_PATH::"+migFilePath);
                int startIdx = migFilePath.indexOf("outputFile");
                migFilePath = migFilePath.substring(startIdx, migFilePath.length());
                log.debug("Convert MIG_FILE_PATH::"+migFilePath);
                migFilePath = "old"+File.separator + migFilePath; //-- 마이그레이션의 경우 기본경로 하위 old 폴더 하위부터 진행.
                log.debug("End MIG_FILE_PATH::"+migFilePath);
            }

            ParamMap<String, Object> fileMap = new ParamMap<>();
            String saveRoot = CmmProperties.getSaveRootPath();
            String fileSaveNm = zipInfo.getFileSaveNm();
            String fileTpCd = zipInfo.getFileTpCd();
            String savePath =
                    saveRoot + File.separator + fileTpCd + File.separator +
                            fileSaveNm;
            if(StringUtils.isNotEmpty(migFilePath)) {    //-- 마이그레이션 첨부 경로
                savePath = saveRoot + File.separator + migFilePath;
            }
            File saveFile = new File(savePath);
            if (!saveFile.exists()) {
                String msg = "첨부파일이 존재하지 않습니다.";
                log.error(msg);
                throw new RuntimeException(msg);
            }
            fileMap.put("fileNm", zipInfo.getFileNm());
            fileMap.put("downloadFile", saveFile);
            fileMap.put("fileSaveNm", zipInfo.getFileSaveNm());

            HttpSession session = SessionUtils.getSession();
            session.setAttribute(ZIP_INFO, fileMap);
            session.setAttribute(ZIP_META, param);
            modelMap.addAttribute("zipUid", zipInfo.getFileSaveNm());
        } else{
            modelMap.addAttribute(ERROR_FLAG.getAttributeId(), errorFlag);
        }
        modelMap.addAttribute(MSG.getAttributeId(), result);

        return JSON_VIEW.getViewId();
    }

    /**
     * 다중파일 다운로드(다운로드 후 해당 파일 삭제)
     *
     * @param params parameters
     * @return view
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/paragon/file/download/multi")
    public ModelAndView multiDownload(
            @RequestParam
            Map<String, Object> params) throws IOException {

        String zipUid = String.valueOf(params.get("zipUid"));
        HttpSession session = SessionUtils.getSession();
        ParamMap<String, Object> fileMap =
                (ParamMap<String, Object>) session.getAttribute(ZIP_INFO);

        Map<String, Object> meta = (Map<String, Object>)session.getAttribute(ZIP_META);

        fileDownLogSvc.writeLog(fileMap, meta);

        return new ModelAndView("download", "fileInfo", fileMap);
    }
}
