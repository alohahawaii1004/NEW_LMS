-- function f_new_uuid
CREATE FUNCTION `f_new_uuid`() RETURNS char(36) CHARSET utf8
begin
/* ****************************************************************************
   NAME:       f_new_uuid
   PURPOSE:    신규 UUID 생성(UUID ver. 4)

   REVISIONS:
   VER        DATE        AUTHOR           DESCRIPTION
   ---------  ----------  ---------------  ------------------------------------
   1.0        2022/07/14   YKIHW       1. CREATED THIS FUNCTION.
******************************************************************************/
    DECLARE cNewUUID char(36);
    DECLARE cMd5Val char(32);

    set cMd5Val = md5(concat(rand(),now(6)));
    set cNewUUID = concat(left(md5(concat(year(now()),week(now()))),4),left(cMd5Val,4),'-',
        mid(cMd5Val,5,4),'-4',mid(cMd5Val,9,3),'-',mid(cMd5Val,13,4),'-',mid(cMd5Val,17,12));

RETURN cNewUUID;
END


-- function F_SYS_CD_ABB_LANG
CREATE FUNCTION `F_SYS_CD_ABB_LANG`(P_PARENT_CD VARCHAR(50), P_CD_ABB VARCHAR(50)) RETURNS text CHARSET utf8
BEGIN    
/* ****************************************************************************
   NAME:       F_SYS_CD_ABB_LANG
   PURPOSE:    CD_ABB 를 이용해서 해당 코드의 다국어 코드 얻기

   REVISIONS:
   VER        DATE        AUTHOR           DESCRIPTION
   ---------  ----------  ---------------  ------------------------------------
   1.0        2022/07/14   YKIHW       1. CREATED THIS FUNCTION.
******************************************************************************/
    DECLARE V_RES VARCHAR(255);


    SET    V_RES = '';

    IF P_CD_ABB IS NOT NULL THEN
      -- SQLINES LICENSE FOR EVALUATION USE ONLY
SELECT T1.LANG_CD INTO V_RES
FROM T_SYS_CODE T1
WHERE T1.CD_ABB = P_CD_ABB
  AND T1.PARENT_CD = P_PARENT_CD
;

END IF;

RETURN V_RES;

END

-- function F_SYS_CD_ABB_LANGS
CREATE FUNCTION `F_SYS_CD_ABB_LANGS`(P_PARENT_CODE_ID VARCHAR(4000), P_CODE VARCHAR(4000)) RETURNS varchar(4000) CHARSET utf8
BEGIN
/* ****************************************************************************
   NAME:       F_SYS_CD_ABB_LANG
   PURPOSE:    ',' 로 구분한 CD_ABB 를 이용해서 해당 코드의 다국어 코드 얻기

   REVISIONS:
   VER        DATE        AUTHOR           DESCRIPTION
   ---------  ----------  ---------------  ------------------------------------
   1.0        2022/07/14   YKIHW       1. CREATED THIS FUNCTION.
******************************************************************************/
    DECLARE V_RES VARCHAR(1000);

    SET V_RES = '';

    IF IFNULL(P_CODE, ' ') <> ' ' THEN

SELECT
    GROUP_CONCAT(CAST(T1.LANG_CD AS VARCHAR(200)) ORDER BY T1.ORD_NO SEPARATOR ',') INTO V_RES
FROM V_SYS_CODE T1
WHERE T1.PARENT_CD = P_PARENT_CODE_ID
  AND EXISTS (
    SELECT LV.TXT
    FROM (
             SELECT
                 SUBSTRING_INDEX(SUBSTRING_INDEX(A.AUTH, ',', NUMBERS.N), ',',-1) TXT
             FROM (
                      SELECT 1 N
                      UNION ALL
                      SELECT 2
                      UNION ALL
                      SELECT 3
                      UNION ALL
                      SELECT 4
                      UNION ALL
                      SELECT 5
                      UNION ALL
                      SELECT 6
                      UNION ALL
                      SELECT 7
                      UNION ALL
                      SELECT 8
                      UNION ALL
                      SELECT 9
                      UNION ALL
                      SELECT 10
             ) NUMBERS
                 INNER JOIN (
                     SELECT P_CODE AS AUTH
                 ) A
                     ON CHAR_LENGTH(A.AUTH) - CHAR_LENGTH(REPLACE(A.AUTH, ',' , '')) >= NUMBERS.N-1
         ) LV
    WHERE LV.TXT = T1.CD_ABB
)
;


END IF;

RETURN V_RES;

END

-- function F_SYS_CD_LANG
CREATE FUNCTION `F_SYS_CD_LANG`(P_CD VARCHAR(50)) RETURNS text CHARSET utf8
BEGIN
/* ****************************************************************************
   NAME:       F_SYS_CD_LANG
   PURPOSE:    (UUID로 된) CD 값을 이용해서 해당 코드의 다국어 코드 얻기

   REVISIONS:
   VER        DATE        AUTHOR           DESCRIPTION
   ---------  ----------  ---------------  ------------------------------------
   1.0        2022/07/14   YKIHW       1. CREATED THIS FUNCTION.
******************************************************************************/

    DECLARE V_RES VARCHAR(255);

    SET    V_RES = '';

    IF P_CD IS NOT NULL THEN
        SELECT T1.LANG_CD INTO V_RES
        FROM T_SYS_CODE T1
        WHERE T1.CD = P_CD
        ;
    END IF;

RETURN V_RES;

END

-- 함수 F_SYS_CD_LANGS
CREATE FUNCTION `F_SYS_CD_LANGS`(P_CODE VARCHAR(4000)) RETURNS varchar(4000) CHARSET utf8
BEGIN
/* ****************************************************************************
   NAME:       F_SYS_CD_LANGS
   PURPOSE:    (UUID로 된) CD 값들 을 이용해서 해당 코드의 다국어 코드 얻기

   REVISIONS:
   VER        DATE        AUTHOR           DESCRIPTION
   ---------  ----------  ---------------  ------------------------------------
   1.0        2022/07/14   YKIHW       1. CREATED THIS FUNCTION.
******************************************************************************/
    DECLARE V_RES VARCHAR(1000);

    SET V_RES = '';

    IF (P_CODE is not null) THEN

SELECT
    GROUP_CONCAT(CAST(T1.LANG_CD AS VARCHAR(200)) ORDER BY T1.ORD_NO SEPARATOR ',') INTO V_RES
FROM V_SYS_CODE T1
WHERE EXISTS (
    SELECT LV.TXT
    FROM (
             SELECT
                 SUBSTRING_INDEX(SUBSTRING_INDEX(A.AUTH, ',', NUMBERS.N), ',',-1) TXT
             FROM (
                      SELECT 1 N
                      UNION ALL
                      SELECT 2
                      UNION ALL
                      SELECT 3
                      UNION ALL
                      SELECT 4
                      UNION ALL
                      SELECT 5
                      UNION ALL
                      SELECT 6
                      UNION ALL
                      SELECT 7
                      UNION ALL
                      SELECT 8
                      UNION ALL
                      SELECT 9
                      UNION ALL
                      SELECT 10
                  ) NUMBERS
                      INNER JOIN (
                 SELECT P_CODE AS AUTH
             ) A
                                 ON CHAR_LENGTH(A.AUTH) - CHAR_LENGTH(REPLACE(A.AUTH, ',' , '')) >= NUMBERS.N-1
         ) LV
    WHERE LV.TXT = T1.CD
)
;

END IF;

RETURN V_RES;
END

-- 함수 F_SYS_HTML 구조 내보내기

CREATE FUNCTION `F_SYS_HTML`(P_HTML_DATA LONGTEXT) RETURNS longtext CHARSET utf8
BEGIN
/* ****************************************************************************
   NAME:       F_SYS_HTML
   PURPOSE:    HTML 데이터를 일반 텍스트로 반환

   REVISIONS:
   VER        DATE        AUTHOR           DESCRIPTION
   ---------  ----------  ---------------  ------------------------------------
   1.0        2022/07/14   YKIHW       1. CREATED THIS FUNCTION.
******************************************************************************/
    DECLARE V_RTN_TEXT LONGTEXT  DEFAULT  '';
    DECLARE C_REG_EXP VARCHAR(1000)  DEFAULT  '<(/)?([a-zA-Z]*)(s[a-zA-Z]*=[^>]*)?(s)*(/)?>';


    -- SQLINES LICENSE FOR EVALUATION USE ONLY
SELECT regexp_replace(regexp_replace(P_HTML_DATA, '<(/)?([a-zA-Z]*)(s[a-zA-Z]*=[^>]*)?(s)*(/)?>', ''),'\\<.*?\\>','') INTO V_RTN_TEXT
FROM DUAL;

RETURN V_RTN_TEXT;

END

-- 함수 F_SYS_ISSUE_NO 구조 내보내기

CREATE FUNCTION `F_SYS_ISSUE_NO`() RETURNS text CHARSET utf8
BEGIN
/* ****************************************************************************
   NAME:       F_SYS_ISSUE_NO
   PURPOSE:    이슈 NO. 생성하기: 고객센터(Support) 용

   REVISIONS:
   VER        DATE        AUTHOR           DESCRIPTION
   ---------  ----------  ---------------  ------------------------------------
   1.0        2022/07/14   YKIHW       1. CREATED THIS FUNCTION.
******************************************************************************/
   DECLARE V_RES varchar(50);
   SET V_RES = null;

   -- ISSUE_NO: 'ISU-YYMMXXXX'
SELECT  CONCAT('ISU-', DATE_FORMAT(NOW(), '%y%m'), LPAD(IFNULL(MAX(CAST(SUBSTRING(REQ_NO, 9) AS UNSIGNED)), 0) + 1, 4, '0')) INTO V_RES
FROM T_ISSUE_REQ
WHERE REQ_NO LIKE CONCAT('ISU-', DATE_FORMAT(NOW(), '%y%m'), '%')
  AND length (REQ_NO) = 12;

RETURN V_RES;

END

-- 함수 F_SYS_LANG 구조 내보내기

CREATE FUNCTION `F_SYS_LANG`(P_CODE VARCHAR(255), P_LANG VARCHAR(50)) RETURNS text CHARSET utf8
BEGIN
/* ****************************************************************************
   NAME:       F_SYS_LANG
   PURPOSE:    다국어 코드와 언어코드를 이용해서 다국어 코드의 해당 언어값 얻기

   REVISIONS:
   VER        DATE        AUTHOR           DESCRIPTION
   ---------  ----------  ---------------  ------------------------------------
   1.0        2022/07/14   YKIHW       1. CREATED THIS FUNCTION.
******************************************************************************/
    DECLARE V_RES VARCHAR(1000);


    SET    V_RES = '';

    IF P_CODE IS NOT NULL THEN
        SELECT ( CASE P_LANG
                     WHEN 'EN' THEN T1.EN
                     WHEN 'JA' THEN T1.JA
                     WHEN 'ZH' THEN T1.ZH
                     ELSE T1.KO END
                   ) INTO V_RES
        FROM T_SYS_LANG_MAS T1
        WHERE T1.LANG_CD = P_CODE;
    END IF;

RETURN V_RES;

END

-- 함수 F_SYS_ORG_ID 구조 내보내기

CREATE FUNCTION `F_SYS_ORG_ID`() RETURNS text CHARSET utf8
BEGIN
/* ****************************************************************************
   NAME:       F_SYS_ORG_ID
   PURPOSE:    고객사 ID 생성하기: 고객센터(Support) 용

   REVISIONS:
   VER        DATE        AUTHOR           DESCRIPTION
   ---------  ----------  ---------------  ------------------------------------
   1.0        2022/07/14   YKIHW       1. CREATED THIS FUNCTION.
******************************************************************************/
   DECLARE V_RES varchar(50);
   SET V_RES = null;

   -- ORG_ID: 'COM-YYMMXX'
SELECT  CONCAT('COM-', DATE_FORMAT(NOW(), '%y%m'), LPAD(IFNULL(MAX(CAST(SUBSTRING(ORG_ID, 9) AS UNSIGNED)), 0) + 1, 2, '0')) INTO V_RES
FROM T_SYS_ORG
WHERE ORG_ID LIKE CONCAT('COM-', DATE_FORMAT(NOW(), '%y%m'), '%')
  AND length (ORG_ID) = 10;

RETURN V_RES;

END

-- 함수 F_SYS_SYSTEM_ID 구조 내보내기

CREATE FUNCTION `F_SYS_SYSTEM_ID`() RETURNS text CHARSET utf8
BEGIN
/* ****************************************************************************
   NAME:       F_SYS_SYSTEM_ID
   PURPOSE:    고객사 시스템 ID 생성하기: 고객센터(Support) 용

   REVISIONS:
   VER        DATE        AUTHOR           DESCRIPTION
   ---------  ----------  ---------------  ------------------------------------
   1.0        2022/07/14   YKIHW       1. CREATED THIS FUNCTION.
******************************************************************************/
   DECLARE V_RES varchar(50);
   SET V_RES = null;

   -- SYSTEM_ID: 'SYS-YYMMXX'
SELECT  CONCAT('SYS-', DATE_FORMAT(NOW(), '%y%m'), LPAD(IFNULL(MAX(CAST(SUBSTRING(SYSTEM_ID , 9) AS UNSIGNED)), 0) + 1, 2, '0')) INTO V_RES
FROM t_support_sys_info
WHERE SYSTEM_ID LIKE CONCAT('SYS-', DATE_FORMAT(NOW(), '%y%m'), '%')
  AND length (SYSTEM_ID) = 10;

RETURN V_RES;

END

-- 함수 F_SYS_UID 구조 내보내기

CREATE FUNCTION `F_SYS_UID`() RETURNS text CHARSET utf8
BEGIN
/* ****************************************************************************
   NAME:       F_SYS_UID
   PURPOSE:    신규 UUID 생성('-' 하이픈 제거)

   REVISIONS:
   VER        DATE        AUTHOR           DESCRIPTION
   ---------  ----------  ---------------  ------------------------------------
   1.0        2022/07/14   YKIHW       1. CREATED THIS FUNCTION.
******************************************************************************/
    DECLARE V_RES CHAR(32);

    set V_RES ='';

    -- SQLINES LICENSE FOR EVALUATION USE ONLY
    SELECT REPLACE(UUID(), '-', '') INTO V_RES from DUAL;

RETURN V_RES;
END

-- 함수 F_SYS_USER_ID 구조 내보내기

CREATE FUNCTION `F_SYS_USER_ID`() RETURNS text CHARSET utf8
BEGIN
/* ****************************************************************************
   NAME:       F_SYS_USER_ID
   PURPOSE:    고객사 사용자 ID 생성하기: 고객센터(Support) 용

   REVISIONS:
   VER        DATE        AUTHOR           DESCRIPTION
   ---------  ----------  ---------------  ------------------------------------
   1.0        2022/07/14   YKIHW       1. CREATED THIS FUNCTION.
******************************************************************************/
   DECLARE V_RES varchar(50);
   SET V_RES = null;

   -- LOGIN_ID: 'USR-YYMMXX'
SELECT  CONCAT('USR-', DATE_FORMAT(NOW(), '%y%m'), LPAD(IFNULL(MAX(CAST(SUBSTRING(LOGIN_ID, 9) AS UNSIGNED)), 0) + 1, 2, '0')) INTO V_RES
FROM T_SYS_USER
WHERE LOGIN_ID LIKE CONCAT('USR-', DATE_FORMAT(NOW(), '%y%m'), '%')
  AND length (LOGIN_ID) = 10;

RETURN V_RES;

END

-- 함수 F_SYS_VUID 구조 내보내기

CREATE FUNCTION `F_SYS_VUID`() RETURNS text CHARSET utf8
BEGIN
/* ****************************************************************************
   NAME:       F_SYS_USER_ID
   PURPOSE:    년월일시분초가 포함된 신규 UUID 생성(50자)

   REVISIONS:
   VER        DATE        AUTHOR           DESCRIPTION
   ---------  ----------  ---------------  ------------------------------------
   1.0        2022/07/14   YKIHW       1. CREATED THIS FUNCTION.
******************************************************************************/
    DECLARE V_RES CHAR(50);

    DECLARE V_TIMESTAMP VARCHAR(100);

    set V_RES ='';

    -- 18자리: YYYYMMDDHHMISSFFFF
SELECT SUBSTR(DATE_FORMAT(NOW(3),'%Y%m%d%H%i%s%f'), 1, 18)
INTO V_TIMESTAMP
;

-- 50자리 UUID(18자리: YYYYMMDDHHMISSFFFF + 32자리: UUID)
SELECT CONCAT(IFNULL(V_TIMESTAMP, '') ,IFNULL(CAST(REPLACE(UUID(), '-', '') AS VARCHAR(100)), ''))
INTO V_RES
;

RETURN V_RES;
END

-- function F_TOTAL_WEEKDAYS
CREATE FUNCTION `F_TOTAL_WEEKDAYS`(date1 DATE, date2 DATE) RETURNS int(11)
BEGIN
/* ****************************************************************************
   NAME:       F_TOTAL_WEEKDAYS
   PURPOSE:    두 날짜 사이 평일 갯수 얻기

   REVISIONS:
   VER        DATE        AUTHOR           DESCRIPTION
   ---------  ----------  ---------------  ------------------------------------
   1.0        2022/07/14   YKIHW       1. CREATED THIS FUNCTION.
******************************************************************************/
RETURN ABS(DATEDIFF(date2, date1)) + 1
        - ABS(DATEDIFF(ADDDATE(date2, INTERVAL 1 - DAYOFWEEK(date2) DAY),
                       ADDDATE(date1, INTERVAL 1 - DAYOFWEEK(date1) DAY))) / 7 * 2
        - (DAYOFWEEK(IF(date1 < date2, date1, date2)) = 1)
        - (DAYOFWEEK(IF(date1 > date2, date1, date2)) = 7)
END

-- procedure SP_SET_ISSUE_FACT
CREATE PROCEDURE `SP_SET_ISSUE_FACT`(
    IN IN_FROM_DATE VARCHAR(10), -- 필수
    IN IN_TO_DATE VARCHAR(10),
    IN IN_DAYS INT
)
    COMMENT '이슈 FACT 수집 프로시저'
BEGIN
/* ****************************************************************************
   NAME:       SP_SET_ISSUE_FACT
   PURPOSE:    이슈 FACT 수집 프로시저: 고객센터(Support) 용

   REVISIONS:
   VER        DATE        AUTHOR           DESCRIPTION
   ---------  ----------  ---------------  ------------------------------------
   1.0        2022/07/14   YKIHW       1. CREATED THIS FUNCTION.
******************************************************************************/
    -- 변수선언
    DECLARE V_FROM_DATE VARCHAR(10);
    DECLARE V_TMP_DATE VARCHAR(10);
    DECLARE V_TMP_YEAR VARCHAR(4);
    DECLARE V_TMP_MONTH VARCHAR(2);
    DECLARE V_TMP_DAY VARCHAR(2);
    DECLARE V_DAYS INT;
    DECLARE V_IDX INT;
    DECLARE V_LENGTH INT;
    DECLARE V_CHK_DATE VARCHAR(50);

    -- INPUT 검사
    -- 1. iFromDate 가 있는가?
SELECT IFNULL(DAYNAME(IN_FROM_DATE), '') INTO V_CHK_DATE;
SELECT LENGTH(IN_FROM_DATE) INTO V_LENGTH;
IF V_CHK_DATE != '' AND V_LENGTH = 10 THEN
        SET V_FROM_DATE = IN_FROM_DATE;
        SET V_TMP_DATE = V_FROM_DATE;
END IF;


SELECT IFNULL(DAYNAME(IN_TO_DATE), '') INTO V_CHK_DATE;
SELECT LENGTH(IN_TO_DATE) INTO V_LENGTH;
IF V_CHK_DATE != '' AND V_LENGTH = 10 THEN
        -- 2. iToDate 가 있는가?
select timestampdiff(day, V_TMP_DATE, IN_TO_DATE) INTO V_DAYS;
IF V_DAYS < 0 THEN
            SET V_DAYS = -1 * V_DAYS;
            SET V_FROM_DATE = IN_TO_DATE;
            SET V_TMP_DATE = V_FROM_DATE;
END IF;

    ELSEIF IN_DAYS IS NOT NULL THEN
        -- 3. iDays 가 있는가?
        SET V_DAYS = IN_DAYS;
END IF;

    IF IN_FROM_DATE IS NULL
            OR (V_DAYS IS NULL AND IN_TO_DATE IS NULL) THEN
        SIGNAL SQLSTATE '45000' SET MYSQL_ERRNO=30001, MESSAGE_TEXT='최소입력 정보가 없습니다.';
END IF;

    SET V_IDX = 0;
    WHILE V_IDX <= V_DAYS DO

        IF V_IDX != 0 THEN
SELECT DATE_ADD( V_FROM_DATE, interval V_IDX DAY) INTO V_TMP_DATE;
END IF;
select substring( V_TMP_DATE, 1,4) INTO V_TMP_YEAR;
select substring( V_TMP_DATE, 6,2) INTO V_TMP_MONTH;
select substring( V_TMP_DATE, 9,2) INTO V_TMP_DAY;

delete from t_issue_fact
where ISSUE_YEAR = V_TMP_YEAR
  and ISSUE_MM = V_TMP_MONTH
  and ISSUE_DD = V_TMP_DAY
;

INSERT INTO t_issue_fact(
                              ISSUE_YEAR
                            , ISSUE_MM
                            , ISSUE_DD
                            , ISSUE_WD
                            , ORG_ID
                            , SYSTEM_ID
                            , REQ_ID
                            , REQ_NM
                            , RCV_ID
                            , ISSUE_TP_CD
                            , ISSUE_DOMAIN_CD
                            , REQ_CNT
                            , RCV_CNT
                            , RSLT_CNT
                            , CFM_CNT
                            , CFM_Y_CNT
                            , CFM_N_CNT
                            , CFM_SVC_TTL
                            , CFM_SVC_CNT
)
select
    V_TMP_YEAR ISSUE_YEAR
     , V_TMP_MONTH ISSUE_MM
     , V_TMP_DAY ISSUE_DD
     , weekday(V_TMP_DATE) ISSUE_WD
     , ORG_ID
     , SYSTEM_ID
     , REQ_ID
     , REQ_NM
     , RCV_ID
     , ISSUE_TP_CD
     , ISSUE_DOMAIN_CD
     , SUM(
        case
            when REQ_YMD = V_TMP_DATE then 1
            else 0
        end
    )REQ_CNT
     , SUM(
        case
            when RCV_YMD = V_TMP_DATE then 1
            else 0
        end
    )RCV_CNT
     , SUM(
        case
            when RSLT_YMD = V_TMP_DATE then 1
            else 0
        end
    )RSLT_CNT
     , SUM(
        case
            when CFM_YMD = V_TMP_DATE then 1
            else 0
        end
    )CFM_CNT
     , SUM(
        case
            when CFM_YMD = V_TMP_DATE and CFM_TP_CD = '90' then 1
            else 0
        end
    )CFM_Y_CNT
     , SUM(
        case
            when CFM_YMD = V_TMP_DATE and CFM_TP_CD = '91' then 1
            else 0
        end
    )CFM_N_CNT
     , SUM(
        case
            when CFM_YMD = V_TMP_DATE and CFM_SVC_VAL is not NULL then CFM_SVC_VAL
            else 0
        end
    )CFM_SVC_TTL
     , SUM(
        case
            when CFM_YMD = V_TMP_DATE and CFM_SVC_VAL is not NULL then 1
            else 0
        end
    )CFM_SVC_CNT
from v_issue_fact_raw vifr
where REQ_YMD = V_TMP_DATE
   or RCV_YMD = V_TMP_DATE
   or RSLT_YMD = V_TMP_DATE
   or CFM_YMD = V_TMP_DATE
group by ORG_ID
       , SYSTEM_ID
       , REQ_ID
       , REQ_NM
       , RCV_ID
       , ISSUE_TP_CD
       , ISSUE_DOMAIN_CD
;

SET V_IDX = V_IDX + 1;
END WHILE;

END