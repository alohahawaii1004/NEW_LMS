-- v_issue_fact_raw source

create or replace
algorithm = UNDEFINED view `v_issue_fact_raw` as
select
    `tir`.`ORG_ID` as `ORG_ID`,
    `tir`.`SYSTEM_ID` as `SYSTEM_ID`,
    `tir`.`REQ_ID` as `REQ_ID`,
    ifnull(`tir`.`REQ_NM`, 'NONE') as `REQ_NM`,
    ifnull(`tir`.`RCV_ID`, 'NONE') as `RCV_ID`,
    ifnull(`tir2`.`RSLT_REAL_ISSUE_TP_CD`, ifnull(`tir`.`RCV_ISSUE_TP_CD`, `tir`.`REQ_ISSUE_TP_CD`)) as `ISSUE_TP_CD`,
    ifnull(`tir2`.`RSLT_REAL_ISSUE_DOMAIN_CD`, ifnull(`tir`.`RCV_ISSUE_DOMAIN_CD`, `tir`.`REQ_ISSUE_DOMAIN_CD`)) as `ISSUE_DOMAIN_CD`,
    `tir`.`req_ymd` as `REQ_YMD`,
    `tir`.`rcv_ymd` as `RCV_YMD`,
    `tir2`.`rslt_ymd` as `RSLT_YMD`,
    `tir2`.`cfm_ymd` as `CFM_YMD`,
    weekday(`tir`.`REQ_DTE`) as `REQ_WD`,
    weekday(`tir`.`RCV_DTE`) as `RCV_WD`,
    weekday(`tir2`.`RSLT_DTE`) as `RSLT_WD`,
    weekday(`tir2`.`CFM_DTE`) as `CFM_WD`,
    date_format(`tir`.`REQ_DTE`, '%Y') as `REQ_YYYY`,
    date_format(`tir`.`REQ_DTE`, '%m') as `REQ_MM`,
    date_format(`tir`.`REQ_DTE`, '%d') as `REQ_DD`,
    date_format(`tir`.`RCV_DTE`, '%Y') as `RCV_YYYY`,
    date_format(`tir`.`RCV_DTE`, '%m') as `RCV_MM`,
    date_format(`tir`.`RCV_DTE`, '%d') as `RCV_DD`,
    date_format(`tir2`.`RSLT_DTE`, '%Y') as `RSLT_YYYY`,
    date_format(`tir2`.`RSLT_DTE`, '%m') as `RSLT_MM`,
    date_format(`tir2`.`RSLT_DTE`, '%d') as `RSLT_DD`,
    date_format(`tir2`.`CFM_DTE`, '%Y') as `CFM_YYYY`,
    date_format(`tir2`.`CFM_DTE`, '%m') as `CFM_MM`,
    date_format(`tir2`.`CFM_DTE`, '%d') as `CFM_DD`,
    `tir2`.`CFM_TP_CD` as `CFM_TP_CD`,
    `tir2`.`CFM_SVC_VAL` as `CFM_SVC_VAL`
from
    (`t_issue_req` `tir`
        left join `t_issue_rslt` `tir2` on
        (`tir`.`ISSUE_ID` = `tir2`.`ISSUE_ID`));


-- v_sys_code_temp source

create or replace
algorithm = UNDEFINED view `v_sys_code_temp` as with recursive code_temp as (
select
    `t1`.`CD` as `CD`,
    `t1`.`ORD_NO` as `ORD_NO`,
    `t1`.`CD_ABB` as `CD_ABB`,
    `t1`.`LANG_CD` as `LANG_CD`,
    `t1`.`PARENT_CD` as `PARENT_CD`,
    `t2`.`LANG_CD` as `PARENT_LANG_CD`,
    (
    select
        count(0)
    from
        `t_sys_code`
    where
        `t_sys_code`.`PARENT_CD` = `t1`.`CD`) as `CHILD_CNT`,
    1 as `LEVEL_NO`,
    `t1`.`CD_ATTR_1` as `CD_ATTR_1`,
    `t1`.`CD_ATTR_2` as `CD_ATTR_2`,
    `t1`.`CD_ATTR_3` as `CD_ATTR_3`,
    `t1`.`CD_ATTR_4` as `CD_ATTR_4`,
    `t1`.`ATTR_DESC_1` as `ATTR_DESC_1`,
    `t1`.`ATTR_DESC_2` as `ATTR_DESC_2`,
    `t1`.`ATTR_DESC_3` as `ATTR_DESC_3`,
    `t1`.`ATTR_DESC_4` as `ATTR_DESC_4`,
    `t1`.`CD_DATA` as `CD_DATA`,
    cast(concat(ifnull(`t1`.`CD`, ''), '≫') as char(1000) charset utf8mb4) as `CD_PATH`,
    cast(concat(ifnull(`t1`.`LANG_CD`, ''), '≫') as char(4000) charset utf8mb4) as `LANG_CD_PATH`,
    cast(concat(`t1`.`ORD_NO`, '||', `t1`.`CD`, '≫') as char(4000) charset utf8mb4) as `ORD_CD_PATH`,
    `t1`.`USE_YN` as `USE_YN`,
    `t1`.`REG_DTE` as `REG_DTE`,
    `t1`.`REG_LOGIN_ID` as `REG_LOGIN_ID`,
    `t1`.`UPT_DTE` as `UPT_DTE`,
    `t1`.`UPT_LOGIN_ID` as `UPT_LOGIN_ID`
from
    (`t_sys_code` `t1`
left join `t_sys_code` `t2` on
    (`t1`.`PARENT_CD` = `t2`.`CD`))
where
    `t1`.`PARENT_CD` is null
union all
select
    `t1`.`CD` as `CD`,
    `t1`.`ORD_NO` as `ORD_NO`,
    `t1`.`CD_ABB` as `CD_ABB`,
    `t1`.`LANG_CD` as `LANG_CD`,
    `t1`.`PARENT_CD` as `PARENT_CD`,
    `t2`.`LANG_CD` as `PARENT_LANG_CD`,
    (
    select
        count(0)
    from
        `t_sys_code`
    where
        `t_sys_code`.`PARENT_CD` = `t1`.`CD`) as `CHILD_CNT`,
    `cdl`.`LEVEL_NO` + 1 as `LEVEL_NO`,
    `t1`.`CD_ATTR_1` as `CD_ATTR_1`,
    `t1`.`CD_ATTR_2` as `CD_ATTR_2`,
    `t1`.`CD_ATTR_3` as `CD_ATTR_3`,
    `t1`.`CD_ATTR_4` as `CD_ATTR_4`,
    `t1`.`ATTR_DESC_1` as `ATTR_DESC_1`,
    `t1`.`ATTR_DESC_2` as `ATTR_DESC_2`,
    `t1`.`ATTR_DESC_3` as `ATTR_DESC_3`,
    `t1`.`ATTR_DESC_4` as `ATTR_DESC_4`,
    `t1`.`CD_DATA` as `CD_DATA`,
    concat(ifnull(`cdl`.`CD_PATH`, ''), ifnull(`t1`.`CD`, ''), '≫') as `CD_PATH`,
    concat(ifnull(`cdl`.`LANG_CD_PATH`, ''), ifnull(`t1`.`LANG_CD`, ''), '≫') as `LANG_CD_PATH`,
    concat(ifnull(`cdl`.`ORD_CD_PATH`, ''), `t1`.`ORD_NO`, '||', `t1`.`CD`, '≫') as `ORD_CD_PATH`,
    `t1`.`USE_YN` as `USE_YN`,
    `t1`.`REG_DTE` as `REG_DTE`,
    `t1`.`REG_LOGIN_ID` as `REG_LOGIN_ID`,
    `t1`.`UPT_DTE` as `UPT_DTE`,
    `t1`.`UPT_LOGIN_ID` as `UPT_LOGIN_ID`
from
    ((`t_sys_code` `t1`
left join `t_sys_code` `t2` on
    (`t1`.`PARENT_CD` = `t2`.`CD`))
join `code_temp` `cdl` on
    (`cdl`.`CD` = `t1`.`PARENT_CD`))
)select
            `code_temp`.`CD` as `CD`,
            `code_temp`.`ORD_NO` as `ORD_NO`,
            `code_temp`.`CD_ABB` as `CD_ABB`,
            `code_temp`.`LANG_CD` as `LANG_CD`,
            `code_temp`.`PARENT_CD` as `PARENT_CD`,
            `code_temp`.`PARENT_LANG_CD` as `PARENT_LANG_CD`,
            `code_temp`.`CHILD_CNT` as `CHILD_CNT`,
            `code_temp`.`LEVEL_NO` as `LEVEL_NO`,
            `code_temp`.`CD_ATTR_1` as `CD_ATTR_1`,
            `code_temp`.`CD_ATTR_2` as `CD_ATTR_2`,
            `code_temp`.`CD_ATTR_3` as `CD_ATTR_3`,
            `code_temp`.`CD_ATTR_4` as `CD_ATTR_4`,
            `code_temp`.`ATTR_DESC_1` as `ATTR_DESC_1`,
            `code_temp`.`ATTR_DESC_2` as `ATTR_DESC_2`,
            `code_temp`.`ATTR_DESC_3` as `ATTR_DESC_3`,
            `code_temp`.`ATTR_DESC_4` as `ATTR_DESC_4`,
            `code_temp`.`CD_DATA` as `CD_DATA`,
            `code_temp`.`CD_PATH` as `CD_PATH`,
            `code_temp`.`LANG_CD_PATH` as `LANG_CD_PATH`,
            `code_temp`.`ORD_CD_PATH` as `ORD_CD_PATH`,
            `code_temp`.`USE_YN` as `USE_YN`,
            `code_temp`.`REG_DTE` as `REG_DTE`,
            `code_temp`.`REG_LOGIN_ID` as `REG_LOGIN_ID`,
            `code_temp`.`UPT_DTE` as `UPT_DTE`,
            `code_temp`.`UPT_LOGIN_ID` as `UPT_LOGIN_ID`
 from
            `code_temp`;


-- v_sys_org_temp source

create or replace
algorithm = UNDEFINED view `v_sys_org_temp` as with recursive CTE(ORG_ID,
ORD_NO,
NM_KO,
NM_EN,
NM_JA,
NM_ZH,
ORG_TP_CD,
ORG_TP_LANG_CD,
USE_YN,
LEVEL_NO,
CHILD_CNT,
PARENT_ORG_ID,
PARENT_ORG_NM_KO,
PARENT_ORG_NM_EN,
PARENT_ORG_NM_JA,
PARENT_ORG_NM_ZH,
ORG_ID_PATH,
ORG_NM_PATH_KO,
ORG_NM_PATH_EN,
ORG_NM_PATH_JA,
ORG_NM_PATH_ZH,
REG_DTE,
REG_LOGIN_ID,
UPT_DTE,
UPT_LOGIN_ID) as (
select
    `di`.`ORG_ID` as `ORG_ID`,
    `di`.`ORD_NO` as `ORD_NO`,
    `di`.`NM_KO` as `NM_KO`,
    `di`.`NM_EN` as `NM_EN`,
    `di`.`NM_JA` as `NM_JA`,
    `di`.`NM_ZH` as `NM_ZH`,
    `di`.`ORG_TP_CD` as `ORG_TP_CD`,
    null as `ORG_TP_LANG_CD`,
    `di`.`USE_YN` as `USE_YN`,
    1 as `LEVEL_NO`,
    (
    select
        count(0) as `Expr1`
    from
        `t_sys_org`
    where
        `t_sys_org`.`PARENT_ORG_ID` = `di`.`ORG_ID`) as `CHILD_CNT`,
    `di`.`PARENT_ORG_ID` as `PARENT_ORG_ID`,
    cast('' as char(2000) charset utf8mb4) as `PARENT_ORG_NM_KO`,
    cast('' as char(2000) charset utf8mb4) as `PARENT_ORG_NM_EN`,
    cast('' as char(2000) charset utf8mb4) as `PARENT_ORG_NM_JA`,
    cast('' as char(2000) charset utf8mb4) as `PARENT_ORG_NM_ZH`,
    cast(concat(`di`.`ORD_NO`, '||', ifnull(`di`.`ORG_ID`, ''), '≫') as char(255) charset utf8mb4) as `ORG_ID_PATH`,
    cast(concat(`di`.`ORD_NO`, '||', ifnull(`di`.`NM_KO`, ''), '≫') as char(1000) charset utf8mb4) as `ORG_NM_PATH_KO`,
    cast(concat(`di`.`ORD_NO`, '||', ifnull(`di`.`NM_EN`, ''), '≫') as char(1000) charset utf8mb4) as `ORG_NM_PATH_EN`,
    cast(concat(`di`.`ORD_NO`, '||', ifnull(`di`.`NM_JA`, ''), '≫') as char(1000) charset utf8mb4) as `ORG_NM_PATH_JA`,
    cast(concat(`di`.`ORD_NO`, '||', ifnull(`di`.`NM_ZH`, ''), '≫') as char(1000) charset utf8mb4) as `ORG_NM_PATH_ZH`,
    `di`.`REG_DTE` as `REG_DTE`,
    `di`.`REG_LOGIN_ID` as `REG_LOGIN_ID`,
    `di`.`UPT_DTE` as `UPT_DTE`,
    `di`.`UPT_LOGIN_ID` as `UPT_LOGIN_ID`
from
    `t_sys_org` `di`
where
    `di`.`PARENT_ORG_ID` is null
union all
select
    `di`.`ORG_ID` as `ORG_ID`,
    `di`.`ORD_NO` as `ORD_NO`,
    `di`.`NM_KO` as `NM_KO`,
    `di`.`NM_EN` as `NM_EN`,
    `di`.`NM_JA` as `NM_JA`,
    `di`.`NM_ZH` as `NM_ZH`,
    `di`.`ORG_TP_CD` as `ORG_TP_CD`,
    null as `ORG_TP_LANG_CD`,
    `di`.`USE_YN` as `USE_YN`,
    `cdl`.`LEVEL_NO` + 1 as `LEVEL_NO`,
    (
    select
        count(0) as `Expr1`
    from
        `t_sys_org`
    where
        `t_sys_org`.`PARENT_ORG_ID` = `di`.`ORG_ID`) as `CHILD_CNT`,
    `di`.`PARENT_ORG_ID` as `PARENT_ORG_ID`,
    `cdl`.`NM_KO` as `PARENT_ORG_NM_KO`,
    `cdl`.`NM_EN` as `PARENT_ORG_NM_EN`,
    `cdl`.`NM_JA` as `PARENT_ORG_NM_JA`,
    `cdl`.`NM_ZH` as `PARENT_ORG_NM_ZH`,
    concat(ifnull(`cdl`.`ORG_ID_PATH`, ''), `di`.`ORD_NO`, '||', ifnull(`di`.`ORG_ID`, ''), ' ≫ ') as `ORG_ID_PATH`,
    concat(ifnull(`cdl`.`ORG_NM_PATH_KO`, ''), `di`.`ORD_NO`, '||', ifnull(`di`.`NM_KO`, ''), ' ≫ ') as `ORG_NM_PATH_KO`,
    concat(ifnull(`cdl`.`ORG_NM_PATH_EN`, ''), `di`.`ORD_NO`, '||', ifnull(`di`.`NM_EN`, ''), ' ≫ ') as `ORG_NM_PATH_EN`,
    concat(ifnull(`cdl`.`ORG_NM_PATH_JA`, ''), `di`.`ORD_NO`, '||', ifnull(`di`.`NM_JA`, ''), ' ≫ ') as `ORG_NM_PATH_JA`,
    concat(ifnull(`cdl`.`ORG_NM_PATH_ZH`, ''), `di`.`ORD_NO`, '||', ifnull(`di`.`NM_ZH`, ''), ' ≫ ') as `ORG_NM_PATH_ZH`,
    `di`.`REG_DTE` as `REG_DTE`,
    `di`.`REG_LOGIN_ID` as `REG_LOGIN_ID`,
    `di`.`UPT_DTE` as `UPT_DTE`,
    `di`.`UPT_LOGIN_ID` as `UPT_LOGIN_ID`
from
    (`t_sys_org` `di`
join `cte` `cdl` on
    (`di`.`PARENT_ORG_ID` = `cdl`.`ORG_ID`))
where
    `di`.`PARENT_ORG_ID` is not null
)select
            `vs`.`ORG_ID` as `ORG_ID`,
            `vs`.`ORD_NO` as `ORD_NO`,
            `vs`.`NM_KO` as `NM_KO`,
            `vs`.`NM_EN` as `NM_EN`,
            `vs`.`NM_JA` as `NM_JA`,
            `vs`.`NM_ZH` as `NM_ZH`,
            `vs`.`ORG_TP_CD` as `ORG_TP_CD`,
            `vs`.`ORG_TP_LANG_CD` as `ORG_TP_LANG_CD`,
            `vs`.`USE_YN` as `USE_YN`,
            `vs`.`LEVEL_NO` as `LEVEL_NO`,
            `vs`.`CHILD_CNT` as `CHILD_CNT`,
            `vs`.`PARENT_ORG_ID` as `PARENT_ORG_ID`,
            `vs`.`PARENT_ORG_NM_KO` as `PARENT_ORG_NM_KO`,
            `vs`.`PARENT_ORG_NM_EN` as `PARENT_ORG_NM_EN`,
            `vs`.`PARENT_ORG_NM_JA` as `PARENT_ORG_NM_JA`,
            `vs`.`PARENT_ORG_NM_ZH` as `PARENT_ORG_NM_ZH`,
            `vs`.`ORG_ID_PATH` as `ORG_ID_PATH`,
            `vs`.`ORG_NM_PATH_KO` as `ORG_NM_PATH_KO`,
            `vs`.`ORG_NM_PATH_EN` as `ORG_NM_PATH_EN`,
            `vs`.`ORG_NM_PATH_JA` as `ORG_NM_PATH_JA`,
            `vs`.`ORG_NM_PATH_ZH` as `ORG_NM_PATH_ZH`,
            `vs`.`REG_DTE` as `REG_DTE`,
            `vs`.`REG_LOGIN_ID` as `REG_LOGIN_ID`,
            `vs`.`UPT_DTE` as `UPT_DTE`,
            `vs`.`UPT_LOGIN_ID` as `UPT_LOGIN_ID`
 from
            `cte` `vs`;


-- v_sys_user source

create or replace
algorithm = UNDEFINED view `v_sys_user` as
select
    `tsu`.`LOGIN_ID` as `LOGIN_ID`,
    `tsu`.`ORG_ID` as `ORG_ID`,
    `tsu`.`LOGIN_PWD` as `LOGIN_PWD`,
    `tsu`.`NM_KO` as `NM_KO`,
    `tsu`.`NM_EN` as `NM_EN`,
    `tsu`.`NM_JA` as `NM_JA`,
    `tsu`.`NM_ZH` as `NM_ZH`,
    `tsu`.`EMAIL` as `EMAIL`,
    `tsu`.`USER_TP_CD` as `USER_TP_CD`,
    `F_SYS_CD_ABB_LANG`('USER_TYPE',
                        `tsu`.`USER_TP_CD`) as `USER_TP_LANG_CD`,
    `vsd`.`NM_KO` as `ORG_NM_KO`,
    `vsd`.`NM_EN` as `ORG_NM_EN`,
    `vsd`.`NM_JA` as `ORG_NM_JA`,
    `vsd`.`NM_ZH` as `ORG_NM_ZH`,
    `vsd`.`ORG_ID_PATH` as `ORG_ID_PATH`,
    `vsd`.`ORG_NM_PATH_KO` as `ORG_NM_PATH_KO`,
    `vsd`.`ORG_NM_PATH_EN` as `ORG_NM_PATH_EN`,
    `vsd`.`ORG_NM_PATH_JA` as `ORG_NM_PATH_JA`,
    `vsd`.`ORG_NM_PATH_ZH` as `ORG_NM_PATH_ZH`,
    `tsu`.`SITE_LOCALE` as `SITE_LOCALE`,
    '' as `NAT_CD`,
    '' as `NAT_NM`,
    cast(concat_ws(',', 'CMM_ISJ', ifnull(`auth`.`auths`, '')) as char(255) charset utf8mb4) as `AUTH_CD`,
    case
        `tsu`.`USER_TP_CD` when '00' then `tsu`.`USE_YN`
                           else case
                                    when (current_timestamp() >= ifnull(`tsu`.`USE_ST_DTE`, current_timestamp())
                                        and current_timestamp() < ifnull(`tsu`.`USE_ED_DTE`, current_timestamp()) + interval 1 day) then 'Y'
                                    else 'N'
                                end
    end as `USE_YN`,
    `tsu`.`e_cert` as `CERT`,
    `tsu`.`LOGIN_IP` as `LOGIN_IP`,
    `tsu`.`PWD_ST_DTE` as `PWD_ST_DTE`,
    `tsu`.`PWD_ED_DTE` as `PWD_ED_DTE`,
    `tsu`.`LOGIN_TRY_CNT` as `LOGIN_TRY_CNT`,
    `tsu`.`REG_DTE` as `REG_DTE`,
    `tsu`.`REG_LOGIN_ID` as `REG_LOGIN_ID`,
    `tsu`.`UPT_DTE` as `UPT_DTE`,
    `tsu`.`UPT_LOGIN_ID` as `UPT_LOGIN_ID`
from
    ((`svc_dsk`.`t_sys_user` `tsu`
        left join `svc_dsk`.`v_sys_org` `vsd` on
        (`vsd`.`ORG_ID` = `tsu`.`ORG_ID`))
        left join (
            select
                `svc_dsk`.`t_sys_auth_member`.`MBR_ID` as `MBR_ID`,
                cast(group_concat(`svc_dsk`.`t_sys_auth_member`.`AUTH_CD` order by `svc_dsk`.`t_sys_auth_member`.`AUTH_CD` desc separator ',') as char(255) charset utf8mb4) as `auths`
            from
                `svc_dsk`.`t_sys_auth_member`
            where
                    `svc_dsk`.`t_sys_auth_member`.`MBR_TP_CD` = 'USER'
            group by
                `svc_dsk`.`t_sys_auth_member`.`MBR_ID`) `auth` on
        (`tsu`.`LOGIN_ID` = `auth`.`MBR_ID`));


-- v_sys_user_temp source

create or replace
algorithm = UNDEFINED view `v_sys_user_temp` as
select
    `tsu`.`LOGIN_ID` as `LOGIN_ID`,
    `tsu`.`ORG_ID` as `ORG_ID`,
    `tsu`.`LOGIN_PWD` as `LOGIN_PWD`,
    `tsu`.`NM_KO` as `NM_KO`,
    `tsu`.`NM_EN` as `NM_EN`,
    `tsu`.`NM_JA` as `NM_JA`,
    `tsu`.`NM_ZH` as `NM_ZH`,
    `tsu`.`EMAIL` as `EMAIL`,
    `tsu`.`USER_TP_CD` as `USER_TP_CD`,
    `F_SYS_CD_ABB_LANG`('USER_TYPE',
                        `tsu`.`USER_TP_CD`) as `USER_TP_LANG_CD`,
    `vsd`.`NM_KO` as `ORG_NM_KO`,
    `vsd`.`NM_EN` as `ORG_NM_EN`,
    `vsd`.`NM_JA` as `ORG_NM_JA`,
    `vsd`.`NM_ZH` as `ORG_NM_ZH`,
    `vsd`.`ORG_ID_PATH` as `ORG_ID_PATH`,
    `vsd`.`ORG_NM_PATH_KO` as `ORG_NM_PATH_KO`,
    `vsd`.`ORG_NM_PATH_EN` as `ORG_NM_PATH_EN`,
    `vsd`.`ORG_NM_PATH_JA` as `ORG_NM_PATH_JA`,
    `vsd`.`ORG_NM_PATH_ZH` as `ORG_NM_PATH_ZH`,
    `tsu`.`SITE_LOCALE` as `SITE_LOCALE`,
    '' as `NAT_CD`,
    '' as `NAT_NM`,
    cast(concat_ws(',', 'CMM_ISJ', ifnull(`auth`.`auths`, '')) as char(255) charset utf8mb4) as `AUTH_CD`,
    case
        `tsu`.`USER_TP_CD` when '00' then `tsu`.`USE_YN`
                           else case
                                    when (current_timestamp() >= ifnull(`tsu`.`USE_ST_DTE`, current_timestamp())
                                        and current_timestamp() < ifnull(`tsu`.`USE_ED_DTE`, current_timestamp()) + interval 1 day) then 'Y'
                                    else 'N'
                                end
    end as `USE_YN`,
    `tsu`.`LOGIN_IP` as `LOGIN_IP`,
    `tsu`.`PWD_ST_DTE` as `PWD_ST_DTE`,
    `tsu`.`PWD_ED_DTE` as `PWD_ED_DTE`,
    `tsu`.`LOGIN_TRY_CNT` as `LOGIN_TRY_CNT`,
    `tsu`.`REG_DTE` as `REG_DTE`,
    `tsu`.`REG_LOGIN_ID` as `REG_LOGIN_ID`,
    `tsu`.`UPT_DTE` as `UPT_DTE`,
    `tsu`.`UPT_LOGIN_ID` as `UPT_LOGIN_ID`
from
    ((`svc_dsk`.`t_sys_user` `tsu`
        left join `svc_dsk`.`v_sys_org` `vsd` on
        (`vsd`.`ORG_ID` = `tsu`.`ORG_ID`))
        left join (
            select
                `svc_dsk`.`t_sys_auth_member`.`MBR_ID` as `MBR_ID`,
                cast(group_concat(`svc_dsk`.`t_sys_auth_member`.`AUTH_CD` order by `svc_dsk`.`t_sys_auth_member`.`AUTH_CD` desc separator ',') as char(255) charset utf8mb4) as `auths`
            from
                `svc_dsk`.`t_sys_auth_member`
            where
                    `svc_dsk`.`t_sys_auth_member`.`MBR_TP_CD` = 'USER'
            group by
                `svc_dsk`.`t_sys_auth_member`.`MBR_ID`) `auth` on
        (`tsu`.`LOGIN_ID` = `auth`.`MBR_ID`));


-- v_tree_group source

create or replace
algorithm = UNDEFINED view `v_tree_group` as
select
    0 as `USR_SEQ`,
    0 as `ORG_SEQ`,
    `u`.`LOGIN_ID` as `UUID`,
    `u`.`NM_KO` as `NM_KO`,
    `u`.`NM_EN` as `NM_EN`,
    0 as `CHILD_CNT`,
    `g`.`PARENT_ORG_ID` as `PARENT_UUID`,
    `g`.`ORG_ID` as `PARENT_DEPT_CD`,
    `g`.`NM_KO` as `PARENT_DEPT_NM_PATH_KO`,
    `g`.`NM_EN` as `PARENT_DEPT_NM_PATH_EN`,
        `g`.`LEVEL_NO` + 1 as `LEVEL_NO`,
    `g`.`ORG_ID_PATH` as `ORG_ID_PATH`,
    `g`.`ORG_NM_PATH_KO` as `DEPT_NM_PATH_KO`,
    `g`.`ORG_NM_PATH_EN` as `DEPT_NM_PATH_EN`,
    `u`.`ORG_ID` as `ORG_ID`,
    `u`.`USER_TP_CD` as `DEPT_TP_CD`,
    `u`.`USE_YN` as `USE_YN`
from
    (`svc_dsk`.`v_sys_user` `u`
        join `svc_dsk`.`v_sys_org` `g` on
        (`u`.`ORG_ID` = `g`.`ORG_ID`))
where
        `u`.`USE_YN` = 'Y'
union all
select
    1 as `USR_SEQ`,
    `a`.`ORD_NO` as `ORD_NO`,
    `a`.`ORG_ID` as `UUID`,
    `a`.`NM_KO` as `NM_KO`,
    `a`.`NM_EN` as `NM_EN`,
    (
        select
            count(0)
        from
            (
                select
                    `g`.`ORG_ID` as `PARENT_ORG_ID`
                from
                    (`svc_dsk`.`v_sys_user` `u`
                        join `svc_dsk`.`v_sys_org` `g` on
                        (`u`.`ORG_ID` = `g`.`ORG_ID`))
                where
                        `u`.`USE_YN` = 'Y'
                union all
                select
                    `a`.`PARENT_ORG_ID` as `PARENT_ORG_ID`
                from
                    `svc_dsk`.`v_sys_org` `a`
                where
                        `a`.`USE_YN` = 'Y') `a`
        where
                `a`.`PARENT_ORG_ID` = `a`.`ORG_ID`) as `CHILD_CNT`,
    `a`.`PARENT_ORG_ID` as `PARENT_UUID`,
    `a`.`PARENT_ORG_ID` as `PARENT_DEPT_CD`,
    `b`.`NM_KO` as `NM_KO`,
    `b`.`NM_EN` as `NM_EN`,
    `a`.`LEVEL_NO` as `LEVEL_NO`,
    `a`.`ORG_ID_PATH` as `ORG_ID_PATH`,
    `a`.`ORG_NM_PATH_KO` as `DEPT_NM_PATH_KO`,
    `a`.`ORG_NM_PATH_EN` as `DEPT_NM_PATH_EN`,
    `a`.`ORG_ID` as `ORG_ID`,
    `a`.`ORG_TP_CD` as `DEPT_TP`,
    `a`.`USE_YN` as `USE_YN`
from
    (`svc_dsk`.`v_sys_org` `a`
        left join `svc_dsk`.`v_sys_org` `b` on
        (`a`.`PARENT_ORG_ID` = `b`.`ORG_ID`))
where
        `a`.`USE_YN` = 'Y';


-- v_tree_menu source

create or replace
algorithm = UNDEFINED view `v_tree_menu` as with recursive SYS_TEMP as (
select
    `di`.`MENU_ID` as `MENU_ID`,
    `di`.`PARENT_MENU_ID` as `PARENT_MENU_ID`,
    `t2`.`ORD_NO` as `PARENT_ORD_NO`,
    `di`.`ORD_NO` as `ORD_NO`,
    `di`.`LANG_CD` as `LANG_CD`,
    `di`.`USE_YN` as `USE_YN`,
    `di`.`JSON_DATA` as `JSON_DATA`,
    `di`.`REG_DTE` as `REG_DTE`,
    `di`.`REG_LOGIN_ID` as `REG_LOGIN_ID`,
    `di`.`UPT_DTE` as `UPT_DTE`,
    `di`.`UPT_LOGIN_ID` as `UPT_LOGIN_ID`,
    (
    select
        count(0)
    from
        `t_sys_menu` `smu`
    where
        `smu`.`PARENT_MENU_ID` = `di`.`MENU_ID`) as `CHILD_CNT`,
    `di`.`MODULE_ID` as `MODULE_ID`,
    `di`.`MENU_ICON` as `MENU_ICON`,
    1 as `LEVEL_NO`,
    `mu`.`ACCES_URL` as `ACCES_URL`,
    cast(concat(ifnull(`di`.`MENU_ID`, ''), '≫') as char(4000) charset utf8mb4) as `MENU_ID_PATH`,
    cast(concat(lpad(`di`.`ORD_NO`, 5, '0'), '||', `di`.`MENU_ID`, '≫') as char(4000) charset utf8mb4) as `ORD_MENU_ID_PATH`
from
    ((`t_sys_menu` `di`
left join `t_sys_menu` `t2` on
    (`di`.`PARENT_MENU_ID` = `t2`.`MENU_ID`))
left join `t_sys_module_url` `mu` on
    (`di`.`MODULE_ID` = `mu`.`MODULE_ID`
        and `mu`.`REPRE_URL_YN` = 'Y'))
where
    `di`.`PARENT_MENU_ID` is null
union all
select
    `di`.`MENU_ID` as `MENU_ID`,
    `di`.`PARENT_MENU_ID` as `PARENT_MENU_ID`,
    `t2`.`ORD_NO` as `PARENT_ORD_NO`,
    `di`.`ORD_NO` as `ORD_NO`,
    `di`.`LANG_CD` as `LANG_CD`,
    `di`.`USE_YN` as `USE_YN`,
    `di`.`JSON_DATA` as `JSON_DATA`,
    `di`.`REG_DTE` as `REG_DTE`,
    `di`.`REG_LOGIN_ID` as `REG_LOGIN_ID`,
    `di`.`UPT_DTE` as `UPT_DTE`,
    `di`.`UPT_LOGIN_ID` as `UPT_LOGIN_ID`,
    (
    select
        count(0)
    from
        `t_sys_menu` `smu`
    where
        `smu`.`PARENT_MENU_ID` = `di`.`MENU_ID`) as `CHILD_CNT`,
    `di`.`MODULE_ID` as `MODULE_ID`,
    `di`.`MENU_ICON` as `MENU_ICON`,
    `cdl`.`LEVEL_NO` + 1 as `LEVEL_NO`,
    `mu`.`ACCES_URL` as `ACCES_URL`,
    concat(ifnull(`cdl`.`MENU_ID_PATH`, ''), ifnull(`di`.`MENU_ID`, ''), '≫') as `MENU_ID_PATH`,
    concat(ifnull(`cdl`.`ORD_MENU_ID_PATH`, ''), lpad(`di`.`ORD_NO`, 5, '0'), '||', `di`.`MENU_ID`, '≫') as `ORD_MENU_ID_PATH`
from
    (((`t_sys_menu` `di`
left join `t_sys_menu` `t2` on
    (`di`.`PARENT_MENU_ID` = `t2`.`MENU_ID`))
left join `t_sys_module_url` `mu` on
    (`di`.`MODULE_ID` = `mu`.`MODULE_ID`
        and `mu`.`REPRE_URL_YN` = 'Y'))
join `sys_temp` `cdl` on
    (`di`.`PARENT_MENU_ID` = `cdl`.`MENU_ID`))
where
    `di`.`PARENT_MENU_ID` is not null
)select
            `sys_temp`.`MENU_ID` as `MENU_ID`,
            `sys_temp`.`PARENT_MENU_ID` as `PARENT_MENU_ID`,
            `sys_temp`.`PARENT_ORD_NO` as `PARENT_ORD_NO`,
            `sys_temp`.`ORD_NO` as `ORD_NO`,
            `sys_temp`.`LANG_CD` as `LANG_CD`,
            `sys_temp`.`USE_YN` as `USE_YN`,
            `sys_temp`.`JSON_DATA` as `JSON_DATA`,
            `sys_temp`.`REG_DTE` as `REG_DTE`,
            `sys_temp`.`REG_LOGIN_ID` as `REG_LOGIN_ID`,
            `sys_temp`.`UPT_DTE` as `UPT_DTE`,
            `sys_temp`.`UPT_LOGIN_ID` as `UPT_LOGIN_ID`,
            `sys_temp`.`CHILD_CNT` as `CHILD_CNT`,
            `sys_temp`.`MODULE_ID` as `MODULE_ID`,
            `sys_temp`.`MENU_ICON` as `MENU_ICON`,
            `sys_temp`.`LEVEL_NO` as `LEVEL_NO`,
            `sys_temp`.`ACCES_URL` as `ACCES_URL`,
            `sys_temp`.`MENU_ID_PATH` as `MENU_ID_PATH`,
            `sys_temp`.`ORD_MENU_ID_PATH` as `ORD_MENU_ID_PATH`
 from
            `sys_temp`;