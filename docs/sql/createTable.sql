-- t_issue_fact definition

CREATE TABLE `t_issue_fact` (
    `ISSUE_YEAR` char(4) NOT NULL COMMENT 'Year',
    `ISSUE_MM` char(2) NOT NULL COMMENT 'Month',
    `ISSUE_DD` char(2) NOT NULL COMMENT 'Day',
    `ISSUE_WD` char(1) NOT NULL COMMENT 'DayOfWeek',
    `ORG_ID` varchar(40) NOT NULL COMMENT '조직 ID',
    `SYSTEM_ID` varchar(11) NOT NULL COMMENT '시스템 ID',
    `REQ_ID` varchar(50) NOT NULL COMMENT '요청자 ID',
    `REQ_NM` varchar(255) NOT NULL COMMENT '요청자 이름',
    `RCV_ID` varchar(30) NOT NULL COMMENT '담당자 ID',
    `ISSUE_TP_CD` varchar(5) NOT NULL COMMENT '요청구분-이슈유형',
    `ISSUE_DOMAIN_CD` varchar(5) NOT NULL COMMENT '요청분야',
    `REQ_CNT` int(11) DEFAULT NULL COMMENT '요청 갯수',
    `RCV_CNT` int(11) DEFAULT NULL COMMENT '접수 갯수',
    `RSLT_CNT` int(11) DEFAULT NULL COMMENT '완료 갯수',
    `CFM_CNT` int(11) DEFAULT NULL COMMENT '검수 갯수',
    `CFM_Y_CNT` int(11) DEFAULT NULL COMMENT '검수승인 갯수',
    `CFM_N_CNT` int(11) DEFAULT NULL COMMENT '검수반려 갯수',
    `CFM_SVC_TTL` int(11) DEFAULT NULL COMMENT '검수만족도(총점)',
    `CFM_SVC_CNT` int(11) DEFAULT NULL COMMENT '검수만족도 평가건수',
    `CFM_SVC_AVG` int(11) GENERATED ALWAYS AS (case when `CFM_SVC_CNT` > 0 then round(`CFM_SVC_TTL` / `CFM_SVC_CNT`,1) else 0 end) VIRTUAL,
    `ISSUE_DTE` varchar(10) GENERATED ALWAYS AS (concat(`ISSUE_YEAR`,'-',`ISSUE_MM`,'-',`ISSUE_DD`)) STORED,
    PRIMARY KEY (`ISSUE_YEAR`,`ISSUE_MM`,`ISSUE_DD`,`ORG_ID`,`SYSTEM_ID`,`REQ_ID`,`REQ_NM`,`RCV_ID`,`ISSUE_TP_CD`,`ISSUE_DOMAIN_CD`),
    KEY `t_issue_fact_ISSUE_DTE_IDX` (`ISSUE_DTE`) USING BTREE,
    KEY `t_issue_fact_SYSTEM_ID_IDX` (`SYSTEM_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='[통계]이슈Fact';


-- t_issue_his definition

CREATE TABLE `t_issue_his` (
    `ISSUE_ID` char(32) DEFAULT NULL COMMENT '맵핑 요청건 아이디',
    `HIS_DTE` datetime DEFAULT NULL COMMENT '생성일자',
    `ACTOR` varchar(30) DEFAULT NULL COMMENT '히스토리 작성자',
    `ISSUE_STU_CD` varchar(5) DEFAULT NULL COMMENT '히스토리 작성 시 상태',
    `CONTENT` longtext DEFAULT NULL COMMENT '히스토리 내용',
    `REQUEST_PARAM` longtext DEFAULT NULL COMMENT 'request param'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='요청내역사용이력';


-- t_issue_req definition

CREATE TABLE `t_issue_req` (
    `ISSUE_ID` char(32) NOT NULL COMMENT 'UUID',
    `ORG_ID` varchar(40) DEFAULT NULL COMMENT '조직ID',
    `SYSTEM_ID` varchar(11) DEFAULT NULL COMMENT '1. 요청정보: 시스템ID',
    `REQ_NO` varchar(12) DEFAULT NULL COMMENT '접수번호: 회사약어(2) +년도(4) + 월(2) + SEQ(4) EX) HK201307-0001',
    `ISSUE_STU_CD` varchar(5) DEFAULT NULL COMMENT '처리상태',
    `REQ_ID` varchar(50) DEFAULT NULL COMMENT '1. 요청정보: 등록자',
    `REQ_REAL_ID` varchar(100) DEFAULT NULL COMMENT '1. 요청정보: 등록자',
    `REQ_NM` varchar(255) DEFAULT NULL COMMENT '1. 요청정보: 요청자 이름',
    `REQ_EMAIL` varchar(100) DEFAULT NULL COMMENT '1. 요청정보: 등록자 이메일',
    `REQ_DTE` datetime DEFAULT NULL COMMENT '1. 요청정보: 요청일자',
    `REQ_ISSUE_TP_CD` varchar(5) DEFAULT NULL COMMENT '1. 요청정보: 요청구분-이슈유형',
    `REQ_ISSUE_DOMAIN_CD` varchar(5) DEFAULT NULL COMMENT '1. 요청정보: 요청분야',
    `REQ_END_DTE` datetime DEFAULT NULL COMMENT '1. 요청정보: 완료희망일자',
    `REQ_ISSUE_TITLE` text DEFAULT NULL COMMENT '1. 요청정보: 제목',
    `REQ_ISSUE_CONTS` longtext DEFAULT NULL COMMENT '1. 요청정보: 요청내용',
    `RCV_ID` varchar(30) DEFAULT NULL COMMENT '2. 접수정보: 처리담당자',
    `RCV_DTE` datetime DEFAULT NULL COMMENT '2. 접수정보: 접수일자',
    `RCV_ISSUE_TP_CD` varchar(5) DEFAULT NULL COMMENT '2. 접수정보: 요청구분-이슈유형',
    `RCV_ISSUE_DOMAIN_CD` varchar(5) DEFAULT NULL COMMENT '2. 접수정보: 요청분야',
    `RCV_EXP_MANDAY` decimal(11,1) DEFAULT NULL COMMENT '2. 접수정보: 예상 MAN/DAY',
    `SCHEDUL_END_DTE` datetime DEFAULT NULL COMMENT '2. 접수정보: 완료예정일자',
    `RCV_COMMENT` text DEFAULT NULL COMMENT '2. 접수정보: 담당자의견',
    `USE_YN` varchar(2) DEFAULT 'Y' COMMENT '사용 YN',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    `req_ymd` date GENERATED ALWAYS AS (cast(`REQ_DTE` as date)) VIRTUAL,
    `rcv_ymd` date GENERATED ALWAYS AS (cast(`RCV_DTE` as date)) VIRTUAL,
    PRIMARY KEY (`ISSUE_ID`),
    UNIQUE KEY `t_issue_req_REQ_NO_IDX` (`REQ_NO`),
    KEY `t_issue_req_ISSUE_ID_IDX` (`ISSUE_ID`,`ORG_ID`,`SYSTEM_ID`,`ISSUE_STU_CD`,`REQ_DTE`,`REQ_ISSUE_TP_CD`,`REQ_ISSUE_DOMAIN_CD`,`RCV_ISSUE_TP_CD`,`RCV_ISSUE_DOMAIN_CD`,`USE_YN`),
    KEY `t_issue_req_req_ymd_IDX` (`req_ymd`) USING BTREE,
    KEY `t_issue_req_rcv_ymd_IDX` (`rcv_ymd`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='이슈요청정보';


-- t_support_sys_info definition

CREATE TABLE `t_support_sys_info` (
    `SYSTEM_ID` varchar(50) NOT NULL COMMENT '시스템ID',
    `ORG_ID` varchar(50) DEFAULT NULL COMMENT '조직ID',
    `SYSTEM_NAME` varchar(200) DEFAULT NULL COMMENT '시스템명칭',
    `PRODUCT_TP_CD` varchar(40) DEFAULT NULL COMMENT '제품 유형 코드(LMS, IPS 등)',
    `SYSTEM_URL` varchar(200) DEFAULT NULL COMMENT 'URL주소',
    `INTRODUCE_DT` varchar(8) DEFAULT NULL COMMENT '최초도입일',
    `SM_STARTDT` datetime DEFAULT NULL COMMENT '유지보수시작일',
    `SM_ENDDT` datetime DEFAULT NULL COMMENT '유지보수종료일',
    `USE_YN` varchar(1) DEFAULT NULL COMMENT '사용구분',
    `SYS_DESC` longtext DEFAULT NULL COMMENT '비고',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    PRIMARY KEY (`SYSTEM_ID`),
    KEY `IDX__T_SD_SYSTEM_INFO` (`SYSTEM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='지원 시스템 기본정보';


-- t_support_sys_mgr definition

CREATE TABLE `t_support_sys_mgr` (
    `SYSTEM_ID` varchar(50) NOT NULL COMMENT 'SYSTEM ID',
    `LOGIN_ID` varchar(50) NOT NULL COMMENT '사용자ID',
    `PRIORITY` varchar(1) DEFAULT NULL COMMENT '(담당자 우선 순위) 1: 정, 2: 부',
    `MAIL_SND_YN` varchar(2) DEFAULT NULL COMMENT '담당자 메일수신YN',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    PRIMARY KEY (`SYSTEM_ID`,`LOGIN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='시스템 담당자';


-- t_support_sys_user definition

CREATE TABLE `t_support_sys_user` (
    `SYSTEM_ID` varchar(50) NOT NULL COMMENT 'SYSTEM ID',
    `LOGIN_ID` varchar(50) NOT NULL COMMENT '사용자ID',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    PRIMARY KEY (`SYSTEM_ID`,`LOGIN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='지원시스템 사용자';


-- t_sys_atch_file definition

CREATE TABLE `t_sys_atch_file` (
    `ATCH_UID` char(32) NOT NULL COMMENT '첨부 UUID',
    `SOL_MAS_UID` char(32) DEFAULT NULL COMMENT '솔루션 마스터 UUID',
    `REL_UID` char(32) DEFAULT NULL COMMENT '관련 UUID',
    `ORD_NO` int(11) DEFAULT NULL COMMENT '순서 번호',
    `FILE_TP_CD` varchar(50) DEFAULT NULL COMMENT '파일 유형 코드',
    `FILE_SAVE_NM` varchar(200) DEFAULT NULL COMMENT '파일 저장 명칭',
    `FILE_NM` varchar(200) DEFAULT NULL COMMENT '파일 명칭',
    `FILE_SIZE` int(11) DEFAULT NULL COMMENT '파일 크기',
    `USE_YN` varchar(2) DEFAULT NULL COMMENT '사용 YN',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    `MIG_YN` varchar(1) DEFAULT NULL COMMENT '마이그레이션 유무',
    `MIG_FILE_PATH` varchar(2500) DEFAULT NULL COMMENT '마이그레이션 파일 경로',
    PRIMARY KEY (`ATCH_UID`),
    KEY `IX_t_sys_atch_file` (`REL_UID`,`ATCH_UID`,`FILE_TP_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='첨부 마스터';


-- t_sys_auth_cd definition

CREATE TABLE `t_sys_auth_cd` (
    `AUTH_CD` varchar(50) NOT NULL COMMENT '권한 코드',
    `AUTH_NM` varchar(80) DEFAULT NULL COMMENT '권한 명칭',
    `AUTH_TP_CD` varchar(50) DEFAULT NULL COMMENT '권한 유형 코드',
    `USE_YN` varchar(2) DEFAULT NULL COMMENT '사용 YN',
    `REMARK` varchar(2000) DEFAULT NULL COMMENT '비고',
    `ORD_NO` int(11) DEFAULT NULL COMMENT '순서 번호',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    `AUTH_CLASS_CD` varchar(50) DEFAULT NULL COMMENT '권한 클래스 코드',
    PRIMARY KEY (`AUTH_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='권한 정보';


-- t_sys_auth_member_log definition

CREATE TABLE `t_sys_auth_member_log` (
    `LOG_UID` char(50) NOT NULL COMMENT '로그 UUID',
    `MBR_ID` varchar(50) NOT NULL COMMENT '구성원 아이디',
    `AUTH_CONTENT` longtext DEFAULT NULL COMMENT '권한 내용',
    `REG_DTE` datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT 'NULL' COMMENT '등록 로그인 아이디',
    PRIMARY KEY (`LOG_UID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='사용자별 권한 로그';


-- t_sys_batch_status definition

CREATE TABLE `t_sys_batch_status` (
    `JOB_NM` varchar(500) NOT NULL COMMENT '작업 이름',
    `JOB_ST_DTIME` datetime(6) NOT NULL COMMENT '작업 시작일시',
    PRIMARY KEY (`JOB_NM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='일괄(Batch)작업현황(중복실행방지용)';


-- t_sys_code definition

CREATE TABLE `t_sys_code` (
    `CD` varchar(50) NOT NULL COMMENT '코드',
    `ORD_NO` int(11) DEFAULT NULL COMMENT '순서 번호',
    `CD_ABB` varchar(50) DEFAULT NULL COMMENT '코드 약어',
    `LANG_CD` varchar(255) DEFAULT NULL COMMENT '언어 코드',
    `PARENT_CD` varchar(50) DEFAULT NULL COMMENT '부모 코드',
    `CD_ATTR_1` varchar(50) DEFAULT NULL COMMENT '속성1',
    `CD_ATTR_2` varchar(50) DEFAULT NULL COMMENT '속성2',
    `CD_ATTR_3` varchar(50) DEFAULT NULL COMMENT '속성3',
    `CD_ATTR_4` varchar(50) DEFAULT NULL COMMENT '속성4',
    `ATTR_DESC_1` varchar(1000) DEFAULT NULL COMMENT '속성명1',
    `ATTR_DESC_2` varchar(1000) DEFAULT NULL COMMENT '속성명2',
    `ATTR_DESC_3` varchar(1000) DEFAULT NULL COMMENT '속성명3',
    `ATTR_DESC_4` varchar(1000) DEFAULT NULL COMMENT '속성명4',
    `CD_DATA` varchar(1000) DEFAULT NULL COMMENT '코드 데이터',
    `USE_YN` varchar(2) DEFAULT 'Y' COMMENT '사용 YN',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    PRIMARY KEY (`CD`),
    KEY `IX_T_SYS_CODE_PARENT_CD` (`CD`,`PARENT_CD`,`CD_ABB`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='코드 관리';


-- t_sys_comment definition

CREATE TABLE `t_sys_comment` (
    `CMT_ID` char(32) NOT NULL COMMENT '의견ID',
    `REL_ID` varchar(40) NOT NULL COMMENT '관련ID',
    `REL_TP_CD` varchar(11) NOT NULL COMMENT '관련 유형 코드',
    `CMT_TITLE` text DEFAULT NULL COMMENT '제목',
    `CMT_CONTS` longtext DEFAULT NULL COMMENT '의견내용',
    `CMT_REG_ID` varchar(30) DEFAULT NULL COMMENT '의견 등록(자) 아이디',
    `CMT_REG_NM` varchar(255) DEFAULT NULL COMMENT '의견 등록(자) 이름',
    `CMT_REG_DTE` datetime DEFAULT NULL COMMENT '의견 등록 일시',
    `PARENT_UID` char(32) DEFAULT NULL COMMENT '상위 의견',
    `USE_YN` varchar(2) DEFAULT 'Y' COMMENT '사용 YN',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    PRIMARY KEY (`CMT_ID`),
    UNIQUE KEY `t_sys_comments_PK` (`CMT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='의견정보';


-- t_sys_down_log definition

CREATE TABLE `t_sys_down_log` (
    `LOG_UID` char(50) NOT NULL COMMENT '로그 UUID',
    `DOWN_DTE` datetime NOT NULL DEFAULT current_timestamp() COMMENT '다운로드 일자',
    `DOWN_LOGIN_ID` varchar(50) DEFAULT 'NULL' COMMENT '다운로드 로그인 아이디',
    `ATCH_NAME` varchar(255) NOT NULL COMMENT '파일 이름',
    `ATCH_UID` char(32) DEFAULT NULL COMMENT '파일 UID',
    `FILE_SAVE_NM` varchar(200) DEFAULT NULL COMMENT '파일 저장 이름',
    `ATCH_META` longtext DEFAULT NULL COMMENT '파일 메타',
    PRIMARY KEY (`LOG_UID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='다운로드 로그';


-- t_sys_lang_mas definition

CREATE TABLE `t_sys_lang_mas` (
    `LANG_UID` char(32) NOT NULL COMMENT '언어 UUID',
    `LANG_CD` varchar(255) NOT NULL COMMENT '언어 코드',
    `KO` varchar(1000) DEFAULT NULL COMMENT '한글',
    `EN` varchar(1000) DEFAULT NULL COMMENT '영문',
    `JA` varchar(1000) DEFAULT NULL COMMENT '일문',
    `ZH` varchar(1000) DEFAULT NULL COMMENT '중문',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    PRIMARY KEY (`LANG_UID`),
    UNIQUE KEY `UIX_T_SYS_LANG_MAS` (`LANG_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='다국어 마스터';


-- t_sys_login_log definition

CREATE TABLE `t_sys_login_log` (
    `LOG_UID` char(50) NOT NULL COMMENT '로그 UUID',
    `LOGIN_ID` varchar(50) NOT NULL COMMENT '로그인 아이디',
    `LOGIN_DTE` datetime NOT NULL DEFAULT current_timestamp() COMMENT '로그인 일자',
    `LOGIN_IP` varchar(50) DEFAULT NULL COMMENT '로그인 아이피',
    PRIMARY KEY (`LOG_UID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='사용자 로그인 로그';


-- t_sys_mail_mas definition

CREATE TABLE `t_sys_mail_mas` (
    `EMAIL_UID` char(32) NOT NULL COMMENT '이메일 UUID',
    `REL_UID` char(32) DEFAULT NULL COMMENT '관련 UUID',
    `SOL_MAS_UID` char(32) DEFAULT NULL COMMENT '솔루션 마스터 UUID',
    `STU_CD` varchar(50) DEFAULT NULL COMMENT '상태 코드',
    `STU_DTL` varchar(50) DEFAULT NULL COMMENT '상태 상세',
    `SEND` varchar(255) DEFAULT NULL COMMENT '발신',
    `REC` varchar(4000) DEFAULT NULL COMMENT '수신',
    `REF` varchar(4000) DEFAULT NULL COMMENT '참조',
    `SECU_REF` varchar(4000) DEFAULT NULL COMMENT '보안 참조',
    `TIT` varchar(255) DEFAULT NULL COMMENT '제목',
    `CONTENT` varchar(4000) DEFAULT NULL COMMENT '내용',
    `FILE_REL_UID` char(32) DEFAULT NULL COMMENT '파일 관련 UID',
    `FILE_TP_CD` varchar(50) DEFAULT NULL COMMENT '파일 유형 코드',
    `SEND_DTE` datetime DEFAULT NULL COMMENT '발신 일자',
    `SEND_YN` varchar(2) DEFAULT NULL COMMENT '발신 YN',
    `HTML_USE_YN` varchar(2) DEFAULT NULL COMMENT 'HTML 사용 YN',
    `RESV_DTE` datetime DEFAULT NULL COMMENT '예약 일자',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    PRIMARY KEY (`EMAIL_UID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='메일발송 마스터';


-- t_sys_module definition

CREATE TABLE `t_sys_module` (
    `MODULE_ID` varchar(50) NOT NULL COMMENT '모듈 아이디',
    `MODULE_DESC` varchar(2000) DEFAULT NULL COMMENT '모듈 설명',
    `USE_YN` varchar(2) DEFAULT NULL COMMENT '사용 YN',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    PRIMARY KEY (`MODULE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='모듈마스터';


-- t_sys_org definition

CREATE TABLE `t_sys_org` (
    `ORG_ID` varchar(50) NOT NULL COMMENT '조직 아이디',
    `ORD_NO` int(11) DEFAULT NULL COMMENT '순서번호',
    `NM_KO` varchar(80) DEFAULT NULL COMMENT '명칭 한글',
    `NM_EN` varchar(80) DEFAULT NULL COMMENT '명칭 영문',
    `NM_JA` varchar(80) DEFAULT NULL COMMENT '명칭 일문',
    `NM_ZH` varchar(80) DEFAULT NULL COMMENT '명칭 중문',
    `PARENT_ORG_ID` varchar(50) DEFAULT NULL COMMENT '부모 부서 코드',
    `ORG_TP_CD` varchar(50) DEFAULT NULL COMMENT '조직유형',
    `USE_YN` varchar(2) DEFAULT NULL COMMENT '사용 YN',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    PRIMARY KEY (`ORG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='조직정보';


-- t_sys_org_meta definition

CREATE TABLE `t_sys_org_meta` (
    `ORG_ID` varchar(50) NOT NULL COMMENT '조직 아이디',
    `ORG_DESC` longtext DEFAULT NULL COMMENT '비고',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    PRIMARY KEY (`ORG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='조직 메타 정보';


-- t_sys_url definition

CREATE TABLE `t_sys_url` (
    `ACCES_URL` varchar(255) NOT NULL COMMENT '접속 URL',
    `URL_DESC` varchar(2000) DEFAULT NULL COMMENT 'URL 설명',
    `ALW_DIV` varchar(50) DEFAULT NULL COMMENT '허가 구분',
    `USE_YN` varchar(2) DEFAULT NULL COMMENT '사용 YN',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    PRIMARY KEY (`ACCES_URL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='URL 정보';


-- t_sys_user definition

CREATE TABLE `t_sys_user` (
    `LOGIN_ID` varchar(50) NOT NULL COMMENT '로그인 아이디',
    `LOGIN_PWD` varchar(255) DEFAULT NULL COMMENT '로그인 암호',
    `NM_KO` varchar(80) DEFAULT NULL COMMENT '명칭 한글',
    `NM_EN` varchar(80) DEFAULT NULL COMMENT '명칭 영문',
    `NM_JA` varchar(80) DEFAULT NULL COMMENT '명칭 일문',
    `NM_ZH` varchar(80) DEFAULT NULL COMMENT '명칭 중문',
    `EMAIL` varchar(100) DEFAULT NULL COMMENT '이메일',
    `USER_TP_CD` varchar(50) DEFAULT NULL COMMENT '사용자 유형 코드',
    `SITE_LOCALE` varchar(50) DEFAULT NULL COMMENT '사이트 로케일',
    `ORG_ID` varchar(50) DEFAULT NULL COMMENT '조직 ID',
    `LOGIN_IP` varchar(50) DEFAULT NULL COMMENT '로그인 아이피',
    `PWD_ST_DTE` datetime DEFAULT NULL COMMENT '암호 시작 일자',
    `PWD_ED_DTE` datetime DEFAULT NULL COMMENT '암호 종료 일자',
    `LAST_LOGIN_DTE` datetime DEFAULT NULL COMMENT '최종 로그인 일자',
    `PWD_CHANGE_DTE` datetime DEFAULT NULL COMMENT '암호 변경 일자',
    `TEMP_PWD` varchar(255) DEFAULT NULL COMMENT '임시 암호',
    `LOGIN_TRY_CNT` int(38) DEFAULT NULL COMMENT '로그인 시도 횟수',
    `USE_YN` varchar(2) DEFAULT NULL COMMENT '사용 YN',
    `USE_ST_DTE` datetime DEFAULT NULL COMMENT '사용 시작 일자(부임일자)',
    `USE_ED_DTE` datetime DEFAULT NULL COMMENT '사용 종료 일자(퇴직일자)',
    `MEMO` varchar(2000) DEFAULT NULL COMMENT '메모',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    `old_9w` varchar(500) DEFAULT NULL,
    `e_cert` text DEFAULT NULL COMMENT '인증서',
    `enc_cert` varchar(500) DEFAULT NULL COMMENT '암호화된 인증',
    PRIMARY KEY (`LOGIN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자정보';


-- v_sys_code definition

CREATE TABLE `v_sys_code` (
    `CD` varchar(50) NOT NULL COMMENT '코드',
    `ORD_NO` int(38) DEFAULT NULL COMMENT '순서 번호',
    `CD_ABB` varchar(50) DEFAULT NULL COMMENT '코드 약어',
    `LANG_CD` varchar(255) DEFAULT NULL COMMENT '언어 코드',
    `PARENT_CD` varchar(50) DEFAULT NULL COMMENT '부모 코드',
    `PARENT_LANG_CD` varchar(255) DEFAULT NULL COMMENT '부모 언어 코드',
    `CHILD_CNT` int(38) DEFAULT NULL COMMENT '자식 카운트',
    `LEVEL_NO` int(38) DEFAULT NULL COMMENT '레벨 번호',
    `CD_ATTR_1` varchar(50) DEFAULT NULL COMMENT '속성1',
    `CD_ATTR_2` varchar(50) DEFAULT NULL COMMENT '속성2',
    `CD_ATTR_3` varchar(50) DEFAULT NULL COMMENT '속성3',
    `CD_ATTR_4` varchar(50) DEFAULT NULL COMMENT '속성4',
    `ATTR_DESC_1` varchar(1000) DEFAULT NULL COMMENT '속성명1',
    `ATTR_DESC_2` varchar(1000) DEFAULT NULL COMMENT '속성명2',
    `ATTR_DESC_3` varchar(1000) DEFAULT NULL COMMENT '속성명3',
    `ATTR_DESC_4` varchar(1000) DEFAULT NULL COMMENT '속성명4',
    `CD_DATA` varchar(1000) DEFAULT NULL COMMENT '코드 데이터',
    `CD_PATH` varchar(255) DEFAULT NULL COMMENT '코드 경로',
    `LANG_CD_PATH` varchar(1000) DEFAULT NULL COMMENT '언어 코드 경로',
    `ORD_CD_PATH` varchar(4000) DEFAULT NULL COMMENT '순서 코드 경로',
    `USE_YN` varchar(2) DEFAULT NULL COMMENT '사용 YN',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    PRIMARY KEY (`CD`),
    KEY `IX_V_SYS_CODE_001` (`PARENT_CD`,`USE_YN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='코드정보 상세(일련의 MView)';


-- v_sys_org definition

CREATE TABLE `v_sys_org` (
    `ORG_ID` varchar(50) NOT NULL COMMENT '조직ID',
    `ORD_NO` int(11) DEFAULT NULL COMMENT '순서번호',
    `NM_KO` varchar(80) DEFAULT NULL COMMENT '명칭 한글',
    `NM_EN` varchar(80) DEFAULT NULL COMMENT '명칭 영문',
    `NM_JA` varchar(80) DEFAULT NULL COMMENT '명칭 일문',
    `NM_ZH` varchar(80) DEFAULT NULL COMMENT '명칭 중문',
    `ORG_TP_CD` varchar(50) DEFAULT NULL COMMENT '내부/외부/사무소 등',
    `ORG_TP_LANG_CD` varchar(255) DEFAULT NULL COMMENT '조직 유형 언어 코드',
    `USE_YN` varchar(2) DEFAULT NULL COMMENT '사용 YN',
    `LEVEL_NO` int(11) DEFAULT NULL COMMENT '레벨 번호',
    `CHILD_CNT` int(11) DEFAULT NULL COMMENT '자식 카운트',
    `PARENT_ORG_ID` varchar(50) DEFAULT NULL COMMENT '부모 조직 코드',
    `PARENT_ORG_NM_KO` varchar(80) DEFAULT NULL COMMENT '부모 조직 명칭 한글',
    `PARENT_ORG_NM_EN` varchar(80) DEFAULT NULL COMMENT '부모 조직 명칭 영문',
    `PARENT_ORG_NM_JA` varchar(80) DEFAULT NULL COMMENT '부모 조직 명칭 일문',
    `PARENT_ORG_NM_ZH` varchar(80) DEFAULT NULL COMMENT '부모 조직 명칭 중문',
    `ORG_ID_PATH` varchar(255) DEFAULT NULL COMMENT '조직 코드 경로',
    `ORG_NM_PATH_KO` varchar(1000) DEFAULT NULL COMMENT '조직 명칭 경로 한글',
    `ORG_NM_PATH_EN` varchar(1000) DEFAULT NULL COMMENT '조직 명칭 경로 영문',
    `ORG_NM_PATH_JA` varchar(1000) DEFAULT NULL COMMENT '조직 명칭 경로 일문',
    `ORG_NM_PATH_ZH` varchar(1000) DEFAULT NULL COMMENT '조직 명칭 경로 중문',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    PRIMARY KEY (`ORG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='조직정보 상세(MView대용)';


-- t_issue_rslt definition

CREATE TABLE `t_issue_rslt` (
    `ISSUE_ID` char(32) NOT NULL COMMENT 'UUID',
    `RSLT_ID` varchar(50) DEFAULT NULL COMMENT '3. 결과정보 : 처리담당자',
    `RSLT_DTE` datetime DEFAULT NULL COMMENT '3. 결과정보 : 보고일자',
    `RSLT_REAL_MANDAY` decimal(10,0) DEFAULT NULL COMMENT '3. 결과정보: 실제MAN/DAY',
    `RSLT_RPORT` longtext DEFAULT NULL COMMENT '3. 결과정보 : 작업결과내용',
    `RSLT_REAL_ISSUE_TP_CD` varchar(5) DEFAULT NULL COMMENT '3. 결과정보 : 실제-이슈유형',
    `RSLT_REAL_ISSUE_DOMAIN_CD` varchar(5) DEFAULT NULL COMMENT '3. 결과정보 : 실제 요청분야',
    `CFM_ID` varchar(50) DEFAULT NULL COMMENT '확인자',
    `CFM_DTE` datetime DEFAULT NULL COMMENT '확인일자',
    `CFM_SVC_VAL` decimal(11,1) DEFAULT NULL COMMENT '만족도 , 1:매우만족 2:만족 3:불만족 4:매우불만족',
    `CFM_COMMENT` text DEFAULT NULL COMMENT '평가 이유',
    `CFM_TP_CD` varchar(2) DEFAULT NULL COMMENT '검수구분',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    `rslt_ymd` date GENERATED ALWAYS AS (cast(`RSLT_DTE` as date)) VIRTUAL,
    `cfm_ymd` date GENERATED ALWAYS AS (cast(`CFM_DTE` as date)) VIRTUAL,
    PRIMARY KEY (`ISSUE_ID`),
    KEY `t_issue_rslt_ISSUE_ID_IDX` (`ISSUE_ID`,`CFM_DTE`,`RSLT_REAL_ISSUE_TP_CD`,`RSLT_REAL_ISSUE_DOMAIN_CD`),
    KEY `t_issue_rslt_RSLT_DTE_IDX` (`RSLT_DTE`) USING BTREE,
    KEY `t_issue_rslt_CFM_DTE_IDX` (`CFM_DTE`) USING BTREE,
    KEY `t_issue_rslt_rslt_ymd_IDX` (`rslt_ymd`) USING BTREE,
    KEY `t_issue_rslt_cfm_ymd_IDX` (`cfm_ymd`) USING BTREE,
    CONSTRAINT `t_issue_rslt_FK` FOREIGN KEY (`ISSUE_ID`) REFERENCES `t_issue_req` (`ISSUE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='이슈요청결과';


-- t_sys_auth_member definition

CREATE TABLE `t_sys_auth_member` (
    `AUTH_CD` varchar(50) NOT NULL COMMENT '권한 코드',
    `MBR_TP_CD` varchar(50) NOT NULL COMMENT '구성원 유형 코드',
    `MBR_ID` varchar(50) NOT NULL COMMENT '구성원 아이디',
    `USER_NM` varchar(80) DEFAULT NULL COMMENT '사용자 명칭',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    PRIMARY KEY (`AUTH_CD`,`MBR_TP_CD`,`MBR_ID`),
    CONSTRAINT `FK_AUTH_CD_TO_AUTH_MEMBER` FOREIGN KEY (`AUTH_CD`) REFERENCES `t_sys_auth_cd` (`AUTH_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='권한별 사용자정보';


-- t_sys_menu definition

CREATE TABLE `t_sys_menu` (
    `MENU_ID` varchar(50) NOT NULL COMMENT '메뉴 아이디',
    `PARENT_MENU_ID` varchar(50) DEFAULT NULL COMMENT '부모 메뉴 아이디',
    `ORD_NO` int(11) DEFAULT NULL COMMENT '순서 번호',
    `LANG_CD` varchar(255) DEFAULT NULL COMMENT '언어 코드',
    `USE_YN` varchar(2) DEFAULT NULL COMMENT '사용 YN',
    `JSON_DATA` varchar(2000) DEFAULT NULL COMMENT 'JSON 데이터',
    `MODULE_ID` varchar(50) DEFAULT NULL COMMENT '모듈 아이디',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    `MENU_ICON` varchar(255) DEFAULT NULL COMMENT '메뉴 아이콘',
    PRIMARY KEY (`MENU_ID`),
    KEY `FK_T_SYS_MODULE_TO_T_SYS_MENU` (`MODULE_ID`),
    CONSTRAINT `FK_T_SYS_MODULE_TO_T_SYS_MENU` FOREIGN KEY (`MODULE_ID`) REFERENCES `t_sys_module` (`MODULE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='메뉴 정보';


-- t_sys_module_url definition

CREATE TABLE `t_sys_module_url` (
    `MODULE_ID` varchar(50) NOT NULL COMMENT '모듈 아이디',
    `ACCES_URL` varchar(255) NOT NULL COMMENT '접속 URL',
    `REPRE_URL_YN` varchar(2) DEFAULT NULL COMMENT '대표 URL YN',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    PRIMARY KEY (`MODULE_ID`,`ACCES_URL`),
    KEY `FK_URL_TO_MODULE_URL` (`ACCES_URL`),
    CONSTRAINT `FK_MODULE_TO_MODULE_URL` FOREIGN KEY (`MODULE_ID`) REFERENCES `t_sys_module` (`MODULE_ID`),
    CONSTRAINT `FK_URL_TO_MODULE_URL` FOREIGN KEY (`ACCES_URL`) REFERENCES `t_sys_url` (`ACCES_URL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='모듈URL정보';


-- t_sys_auth_menu definition

CREATE TABLE `t_sys_auth_menu` (
    `AUTH_CD` varchar(50) NOT NULL COMMENT '권한 코드',
    `MENU_ID` varchar(50) NOT NULL COMMENT '메뉴 아이디',
    `ALW_CD` varchar(50) DEFAULT NULL COMMENT '허가 코드',
    `REG_DTE` datetime DEFAULT NULL COMMENT '등록 일자',
    `REG_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '등록 로그인 아이디',
    `UPT_DTE` datetime DEFAULT NULL COMMENT '수정 일자',
    `UPT_LOGIN_ID` varchar(50) DEFAULT NULL COMMENT '수정 로그인 아이디',
    PRIMARY KEY (`AUTH_CD`,`MENU_ID`),
    KEY `FK_MENU_TO_AUTH_MENU` (`MENU_ID`),
    CONSTRAINT `FK_MENU_TO_AUTH_MENU` FOREIGN KEY (`MENU_ID`) REFERENCES `t_sys_menu` (`MENU_ID`),
    CONSTRAINT `FK_T_S_ATH_CD_TO_T_S_ATH_MN` FOREIGN KEY (`AUTH_CD`) REFERENCES `t_sys_auth_cd` (`AUTH_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='권한별 메뉴정보';