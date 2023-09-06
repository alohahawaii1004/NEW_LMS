/*
 * @(#)CmmProperties.java     2020-12-29(029) 오전 8:12
 *
 * Copyright 2020 JAYU.space
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
package com.vertexid.spring.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.vertexid.viself.base.SystemOperationType.LOCAL;
import static com.vertexid.viself.base.SystemOperationType.PROD;

/**
 * <b>Description</b>
 * <pre>
 *     공용 프로퍼티
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class CmmProperties {

    private static final String KEY_SEPERATOR = ".";
    private static final String USE_FLAG = "Y";
    private static Logger log = LoggerFactory.getLogger(CmmProperties.class);

    /**
     * 공용 프로퍼티 키
     */
    public static class CmmPropertiesKey {
        /**
         * 운영모드
         */
        public static final String OPERTION_MODE = "sys.operation.mode";

        /**
         * 소스 루트경로
         */
        public static final String SRC_ROOT_PATH = "base.srcRootPath";

        /**
         * 솔루션
         */
        public static final String SOLUTION_NAME = "base.solution";

        /**
         * 기본 언어코드(ISO 639-1)
         */
        public static final String DEFAULT_LANG = "cmm.lang.default";

        /**
         * 캐릭텃 셋
         */
        public static final String CHAR_SET = "cmm.lang.charSet";

        /**
         * 시스템 타이틀
         */
        public static final String SYSTEM_TITLE = "sys.title";

        /**
         * 관리자 계정이름
         */
        public static final String ADMIN_NAME = "sys.admin.name";

        /**
         * 기본 컨텐트 URL
         */
        public static final String DEFAULT_CONTENT_URL =
                "base.defaultContentUrl";

        /**
         * 시스템 프로토콜
         */
        public static final String SYS_PROTOCOL = "sys.protocol";

        /**
         * 시스템 도메인
         */
        public static final String SYS_DOMAINNAME = "sys.domainName";

        /**
         * 시스템 도메인
         */
        public static final String SYS_PORT = "sys.port";

        /**
         * 시스템 컨텍스트 경로
         */
        public static final String SYS_CONTEXT_PATH = "sys.contextPath";

        /**
         * 기본 페이지 ROW 크기
         */
        public static final String DEFAULT_PAGE_ROW_COUNT =
                "cmm.paging.default.rowCount";

        /**
         * 기본 페이지 카운트
         */
        public static final String DEFAULT_PAGE_COUNT =
                "cmm.paging.default.pageCount";

        /**
         * 첨부파일 저장소 루트 경로
         */
        public static final String SAVE_ROOT_PATH = "attach.saveRootPath";

        /**
         * 첨부 최대 파일 크기
         */
        public static final String MAX_FILE_SIZE = "attach.default.maxFileSize";

        /**
         * 일괄 다운로드 기능
         */
        public static final String BATCH_DOWNLOAD_USE = "fnc.batchDownload.useYn";

        /**
         * 시스템 관리자 이메일
         */
        public static final String ADMIN_EMAIL = "sys.admin.email";

        /**
         * 결재인터페이스 사용여부
         */
        public static final String APRV_USE_YN = "aprv.useYn";

        /**
         * DRM 사용 여부
         */
        public static final String DRM_USE_YN = "drm.useYn";

        /**
         * DRM  KEY 파일 경로
         */
        public static final String DRM_FSDINIT_PATH = "drm.FsdinitPath";

        public static final String MAIL_YN = "mail.useYn";



    }

    /**
     * 시스템 운영모드 반환
     *
     * @return SystemOpertionType 참조
     */
    public static String getOperationMode() {
        return BaseProperties.get(CmmPropertiesKey.OPERTION_MODE);
    }

    /**
     * 소스 루트 경로 얻기
     *
     * @return 소스 루트 경로
     */
    public static String getSrcRootPath() {
        return BaseProperties.get(CmmPropertiesKey.SRC_ROOT_PATH);
    }

    /**
     * 운영모드 여부
     *
     * @return true: 운영모드, other: 기타모드
     */
    public static boolean isProd() {
        return PROD.getTypeCode().equals(getOperationMode());
    }

    /**
     * 로컬모드 여부
     *
     * @return true: 로컬모드, other: 그 외
     */
    public static boolean isLocal() {
        return LOCAL.getTypeCode().equals(getOperationMode());
    }

    /**
     * 운영모드 아님 여부
     *
     * @return true: 운영모드 아님, other: 운영모드
     */
    public static boolean isNotProd() {
        return !isProd();
    }

    /**
     * 로컬모드 아님 여부
     *
     * @return true: 로컬아님, other: 로컬
     */
    public static boolean isNotLocal() {
        return !isLocal();
    }

    /**
     * 솔루션 이름 반환
     *
     * @return 솔루션 이름
     */
    public static String getSolutionName() {
        return BaseProperties.get(CmmPropertiesKey.SOLUTION_NAME);
    }

    /**
     * 기본 언어코드 반환
     *
     * @return 기본 언어코드(ISO 639-1)
     */
    public static String getDefaultLang() {
        return BaseProperties.get(CmmPropertiesKey.DEFAULT_LANG);
    }

    /**
     * 케릭터 셋 반환
     *
     * @return 설정된 케릭터 셋
     */
    public static String getCharSet() {
        return BaseProperties.get(CmmPropertiesKey.CHAR_SET);
    }

    /**
     * 시스템 타이틀 반환
     *
     * @param languageCode 언어 코드(ISO 639-1)
     * @return 시스템 타이틀
     */
    public static String getSystemTitle(String languageCode) {
        return BaseProperties
                .get(CmmPropertiesKey.SYSTEM_TITLE + KEY_SEPERATOR +
                        languageCode.toLowerCase());

    }

    /**
     * 관리자 계정이름 반환
     *
     * @return 관리자 계정이름
     */
    public static String getAdminName() {
        return BaseProperties.get(CmmPropertiesKey.ADMIN_NAME);
    }

    /**
     * 관리자 email 반환
     *
     * @return 관리자 email
     */
    public static String getAdminEmail() {
        return BaseProperties.get(CmmPropertiesKey.ADMIN_EMAIL);
    }

    /**
     * 발신전용 메일 반환
     * - admin name, admin email 정보를 이용해서 발신전용 메일 정보를 반환
     *
     * @return 발신전용 메일
     */
    public static String getSenderOnlyMailAddr() {
        String adminName = getAdminName();
        String adminMail = getAdminEmail();
        if (StringUtils.isNotBlank(adminName)) {
            StringBuffer sb = new StringBuffer();
            sb.append("\"").append(adminName).append("\" ").append("<")
                    .append(adminMail).append(">");

            adminMail = sb.toString();
        }

        return adminMail;
    }

    /**
     * 기본 컨텐츠 URL
     *
     * @return 기본 컨텐츠 URL
     */
    public static String getDefaultContentUrl() {
        return BaseProperties.get(CmmPropertiesKey.DEFAULT_CONTENT_URL);
    }

    /**
     * 시스템 URL 반환
     *
     * @return 시스템 URL
     */
    public static String getSystemUrl() {
        StringBuffer sbUrl = new StringBuffer();
        sbUrl.append(BaseProperties.get(CmmPropertiesKey.SYS_PROTOCOL))
                .append(BaseProperties.get(CmmPropertiesKey.SYS_DOMAINNAME));
        if(StringUtils.isNotBlank(BaseProperties.get(CmmPropertiesKey.SYS_PORT))) {
        	sbUrl.append(":"+BaseProperties.get(CmmPropertiesKey.SYS_PORT));
        }
        sbUrl.append(BaseProperties.get(CmmPropertiesKey.SYS_CONTEXT_PATH));

        return sbUrl.toString();
    }

    /**
     * 기본 페이지 Row 갯수 반환
     *
     * @return 페이지 Row 갯수
     */
    public static Integer getDefaultRowCount() {
        return Integer.parseInt(
                BaseProperties.get(CmmPropertiesKey.DEFAULT_PAGE_ROW_COUNT));
    }

    /**
     * 기본 페이지 갯수 반환
     * @return 페이지 갯수
     */
    public static Integer getDefaultPageCount() {
        return Integer.parseInt(
                BaseProperties.get(CmmPropertiesKey.DEFAULT_PAGE_COUNT));
    }

    /**
     * 첨부 파일 저장 루트 경로 반환
     * @return 첨부 파일 저장 루트 경로
     */
    public static String getSaveRootPath(){
        return BaseProperties.get(CmmPropertiesKey.SAVE_ROOT_PATH);
    }

    /**
     * 첨부 기본최대 크기
     * @return 첨부 기본최대 크기
     */
    public static Integer getDefaultMaxFileSize(){
        return Integer.parseInt(BaseProperties.get(CmmPropertiesKey.MAX_FILE_SIZE));
    }

    /**
     * 첨부 기본최대 갯수
     * @return 첨부 기본최대 갯수
     */
    public static Integer getDefaultMaxFileCount(){
        return Integer.parseInt(BaseProperties.get(CmmPropertiesKey.MAX_FILE_SIZE));
    }

    /**
     * 일괄 다운로드 사용여부 반환
     * @return true: 일괄다운로드 사용, other: 사용하지 않음
     */
    public static Boolean useBatchDownload(){
        return USE_FLAG.equals(BaseProperties.get(CmmPropertiesKey.BATCH_DOWNLOAD_USE));
    }

    /**
     * 결재 I/F 사용여부
     * @return Y: 사용, other: 사용하지 않음
     */
    public static String getAprvUseYn(){
        return BaseProperties.get(CmmPropertiesKey.APRV_USE_YN);
    }
    /**
     * DRM key file 위치
     * @return key file 위치
     */
    public static String getDrmFsdinitPath(){
        return BaseProperties.get(CmmPropertiesKey.DRM_FSDINIT_PATH);
    }
    /**
     * DRM 사용 여부
     * @return Y:사용, other: 사용하지 않음
     */
    public static String getDrmUseYn(){
        return BaseProperties.get(CmmPropertiesKey.DRM_USE_YN);
    }
    /**
     * DRM 사용 여부
     * @return Y:사용, other: 사용하지 않음
     */
    public static String getMailYn(){
        return BaseProperties.get(CmmPropertiesKey.MAIL_YN);
    }
}
