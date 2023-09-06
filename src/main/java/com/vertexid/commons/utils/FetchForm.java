package com.vertexid.commons.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vertexid.viself.base.SystemPropertiesVO;

/**
 * <b>Description</b>
 * <pre>
 * 폼(파일) 처리 유틸
 *
 * 사용 방법
 *
 * - From File :
 *     텍스트 형태의 파일이어야 한다.
 *     치환될 부분은 ${파라메터명} 으로 구성한다.
 *
 *     Sample Form File )
 *
 *     form.html
 *
 *     이 세상에서 가장 착한 사람은 ${name}이다.
 *
 *
 *     Sample Code )
 *
 *     FetchForm fFrom = new FetchForm("D:/sample/form.html");
 *     fForm.setParam("name","홍길동");
 *     logger.debug(fForm.parseForm());
 *
 *     Sample Result )
 *
 *      이 세상에서 가장 착한 사람은 홍길동이다.
 *
 * </pre>
 *
 * @author Yang, Ki Hwa
 */
public class FetchForm {

    private String strForm = "";
    private String strFile = "";
    private Charset cs = StandardCharsets.UTF_8 ;
    private String baseCs = "UTF-8" ;

    private final Map<String, Object> htParams = new HashMap<>();

    /**
     * logger
     */
    protected static Logger logger = LoggerFactory.getLogger(FetchForm.class);

    public FetchForm() {

    }


    /**
     * @param file 폼파일
     */
    public FetchForm(String file) {
        this.strFile = file;
    }


    private void readFile() {
        try {

            if (!"".equals(strFile)) {
            /*
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(strFile), "UTF-8"));
            StringBuffer sbTmp = new StringBuffer();
            String strTmp = "";

            while ((strTmp = in.readLine()) != null) {
               sbTmp.append(strTmp);
            }
            in.close();

            strForm = sbTmp.toString();
            */
                logger.debug("FetchForm Path:" + strFile);
                InputStream inputStream = FetchForm.class.getResourceAsStream(
                        strFile);  //this.getClass().getClassLoader().getResourceAsStream(strFile);
                BufferedReader in;
                if (inputStream != null) {
                    in = new BufferedReader(new InputStreamReader(inputStream,baseCs));
                }else{
                    // TODO error
                    throw new Exception("input stream is null");
                }

                StringBuilder sbTmp = new StringBuilder();
                String strTmp;

                while ((strTmp = in.readLine()) != null) {
                    sbTmp.append(strTmp);
                }
                in.close();

                strForm = sbTmp.toString();

            }

        } catch (Exception e) {
            logger.error("Connection Exception occurred:"+e.getMessage());
        }
    }


    /**
     * 폼파일의 내용을 얻는다.
     *
     * @return 폼파일내용
     */
    public String getForm() {

        readFile();

        return strForm;
    }

    /**
     *
     * <pre>
     * 변경할 케릭터 셋을 셋팅함 기본은 UTF-8
     * </pre>
     * @param key
     * @param val
     */
    public void setCharSet(String charSet) {
    	this.baseCs = charSet;
    }

    /**
     * <pre>
     * 파라메터를 세팅한다.
     * </pre>
     *
     * @param key 키
     * @param val 키 값
     */
    public void setParam(String key, String val) {
        if (new SystemPropertiesVO().isNotProd()) {
            logger.debug("KEY=" + key);
            logger.debug("VAL=" + val);
        }
        // DEBUG

        htParams.put(key, val);
    }

    /**
     * 파라메터 한번에 셋팅
     *
     * @param param parameter
     */
    public void setParamAll(Map<String, Object> param) {
        // DEBUG
//        String[] keys = (String[]) param.keySet().toArray();
//        for (String key : keys) {
//            htParams.put(key, param.get(key).toString());
//        }
       htParams.putAll(param);
    }


    /**
     * 파싱된 폼내용을 얻는다.
     *
     * @return 파싱된 내용
     */
    public String parseForm() throws Exception {

        readFile();

        return parse();
    }

    /**
     * 파싱된 폼내용을 얻는다.
     *
     * @return 파싱된 폼내용
     */
    public String parseFormString(String inputString) throws Exception {

        strForm = inputString;

        return parse();
    }

    private String parse() throws Exception {

        if (strForm == null || strForm.length() == 0) {
            throw new Exception("Form is Not Exist");
        }

        String strParseData;
        StringBuilder sbForm = new StringBuilder();

        while (strForm.length() > 0) {

            int position = strForm.indexOf("${");

            if (position == -1) {
                sbForm.append(strForm);
                break;
            }

            if (position != 0) {
                sbForm.append(strForm, 0, position);
            }

            if (strForm.length() == position + 2) {
                break;
            }

            String remainStr = strForm.substring(position + 2);

            int markEndPos = remainStr.indexOf("}");

            if (markEndPos == -1) {
                break;
            }

            String key = remainStr.substring(0, markEndPos);
            String val = (String) htParams.get(key);

            if (val != null) {
                sbForm.append(val);
            }
            if (remainStr.length() == markEndPos + 1) {
                break;
            }
            strForm = remainStr.substring(markEndPos + 1);
        }// end of while

        strParseData = sbForm.toString().trim();

        return strParseData;
    }

}
