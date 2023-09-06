package com.vertexid.commons.utils;

public class CommonConstants {

    /**
     * REQUEST PARAMETER - 멀티파트여부
     */
    public static final String IS_MULTIPART         = "IS_MULTIPART";

    /**
     * REQUEST PARAMETER - 다국어 최종 버전
     */
    public static final String LAST_LANG_VERSION     = "lastLangVersion";

    /**
     * REQUEST PARAMETER - 페이지 번호
     */
    public static final String PAGE_NO                 = "page";

    /**
     * REQUEST PARAMETER - 페이지당 행 수
     */
    public static final String PAGE_ROWSIZE         = "rows";

    /**
     * REQUEST PARAMETER - 페이지 정렬 필드
     */
    public static final String PAGE_ORDERBY_FIELD         = "sort";

    /**
     * REQUEST PARAMETER - 페이지 정렬 방식
     */
    public static final String PAGE_ORDERBY_METHOD         = "order";

    /**
     * Request referer site code
     */
    public static final String REFERER_SITE = "REFERER_SITE";

    /**
     * 에러 코드 - 일반
     */
    public static final String ERROR_CODE_DEFAULT        = "DEFAULT";

    /**
     * 에러 코드 - 로그인정보없음
     */
    public static final String ERROR_CODE_NOTLOGIN        = "NOTLOGIN";

    /**
     * 에러 코드 - 다중접속
     */
    public static final String ERROR_CODE_MULTILOGIN        = "MULTILOGIN";

    /**
     * 에러 코드 - 권한없음
     */
    public static final String ERROR_CODE_NOPERMISSION    = "NOPERMISSION";

    /**
     * 구분자 - 연결 경로
     */
    public static final String SEPAR_CONNECTPATH    = "≫";

    /**
     * 구분자 - 코드정보 문자열 값
     */
    public static final String SEPAR_CODESTRVALUE    = "|";

    /**
        * SESSION ATTRIBUTE - 사용자 정보
        */
    public static final String SESSION_USER         = "sessionUser";

    public static String _LOGIN_USER_ID = "_LOGIN_USER_ID";

    public static String _LOGIN_USER_NO = "_LOGIN_USER_NO";

    public static String _LOGIN_CORP_CD = "_LOGIN_CORP_CD";

    public static String _LOGIN_CORP_NM = "_LOGIN_CORP_NM";

    public static String _LOGIN_USER_IP = "_LOGIN_USER_IP";

    public static String _LOGIN_USER_NM = "_LOGIN_USER_NM";

    public static String _IS_CHARGE_USER = "_IS_CHARGE_USER";

    public static String _LOGIN_USER_DPT_CD = "_LOGIN_USER_DPT_CD";

    public static String _LOGIN_USER_DPT_NM = "_LOGIN_USER_DPT_NM";

    public static String _LOGIN_USER_AUTHS = "_LOGIN_USER_AUTHS";

    public static String _JSON_DATA = "_JSON_DATA";

    /**
     * SITE LOCALE - 사이트 언어
     */
    public static final String _SITE_LOCALE         = "siteLocale";


    /**
     * 관리자 권한 코드 - 시스템 관리자
     */
    public static final String ADMIN_AUTH_SYS = "CMM/SYS";
}
