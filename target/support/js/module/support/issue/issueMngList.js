/*
 * @(#)issueList.js     2023-03-10 010 오후 4:37:06
 *
 * Copyright 2023 JAYU.space
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


var IssueListModel = function(){
    "use strict";

    const ISSUE_LIST_URL = paragonCmm.getUrl("/support/issue/listPerPage/json");
    const ISSUE_WRITE_URL = paragonCmm.getUrl("/support/issue/issueReqWrite.popup");
    const ISSUE_VIEW_URL = paragonCmm.getUrl("/support/issue/issueInfo.popup");
    const ISSUE_STATS_URL = paragonCmm.getUrl("/support/issue/getStatsData/json");

    let getOpts = function(options, url){
        return $.extend({
            url: url,
            params: null,
            cbFnc: function (json) {
            },
            async: true,
            errorBack: function(){}
        }, options);
    };
    let callAjax = function(options, url){
        let opts = getOpts(options, url);
        paragonCmm.callAjax(opts.url, opts.params, opts.cbFnc, opts.async);
    };

    return {
        getIssueStat: function(options){
            callAjax(options, ISSUE_STATS_URL);
        },
        getListUrl: function(){
            return ISSUE_LIST_URL;
        },
        getWriteUrl: function(){
            return ISSUE_WRITE_URL;
        },
        getViewUrl: function(){
            return ISSUE_VIEW_URL;
        }
    };
};

var IssueList = function(){
    "use strict";
    // ==============================================
    // attributes
    // ----------------------------------------------
    const OBJ_ID = "issueList"; // popup call back
    const ISSUE_TP_COLOR = [{
        cd: "ERR", color: "#a94442", bgColor: "#f2dede"
    // }, {
    //     cd: "NEW", color: "#3c5876", bgColor: "#d8e3f0"
    // }, {
    //     cd: "UPD", color: "#7b6b59", bgColor: "#fff3d2"
    // }, {
    //     cd: "MOD", color: "#ff851b", bgColor: "#ffe2d0"
    // }, {
    //     cd: "REQ", color: "#3c763d", bgColor: "#dff0d8"
    // }, {
    //     cd: "MIG", color: "#ff0c9d", bgColor: "#ffd1eb"
    // }, {
    //     cd: "ETC", color: "#7a1bff", bgColor: "#7a1bff"
    }];

    const ISSUE_STU_COLOR = [{
        cd: "91", color: "#a94442", bgColor: "#f2dede"
        }, {
            cd: "50", color: "#3c5876", bgColor: "#d8e3f0"
        }, {
            cd: "10", color: "#7b6b59", bgColor: "#fff3d2"
        // }, {
        //     cd: "80", color: "#ff851b", bgColor: "#ffe2d0"
        }, {
            cd: "80", color: "#3c763d", bgColor: "#dff0d8"
        // }, {
        //     cd: "MIG", color: "#ff0c9d", bgColor: "#ffd1eb"
        // }, {
        //     cd: "ETC", color: "#7a1bff", bgColor: "#7a1bff"
    }];
    let model = new IssueListModel();
    let $rootLayer = $("#issueMngRootLayer");
    let $schForm = $("#issueMngSchFrm0", $rootLayer);
    let $schIssueTpCd = $("select[name=schIssueTpCd]", $schForm);
    let $schIssueDomainCd = $("select[name=schIssueDomainCd]", $schForm);
    let $schIssueStuCd = $("select[name=schIssueStuCd]", $schForm);
    let $grid = $("#issueMngList1", $rootLayer);
    let $fromDate = $("input[name=schFromReqDte]", $schForm);
    let $toDate = $("input[name=schToReqDte]", $schForm);

    // ==============================================
    // private functions
    // ----------------------------------------------

    /**
     * 콤보박스(select) 설정
     * @param parentCd parent code
     * @param comboObj 콤보 Object
     * @param initTxt 초기값 명칭
     * @param valNm cd, cdAbb
     */
    let setComboBox = function(parentCd, comboObj, initTxt, valNm){

        valNm = (typeof valNm === "undefined") ? "cdAbb" : valNm;

        let initNm = (typeof initTxt === "undefined") ? paragonCmm.getLang("L.CBO_SELECT") : initTxt;

        htmlUtils.getCodeSelectOpt({
            targetId: comboObj,
            parentCd: parentCd,
            initdata:"|"+initNm,
            valNm: valNm,
            txtNm: "langCd"
        });
    };

    let getDefDate = function(addDay){
        var today = new Date();

        var defDate = new Date(today.setDate(today.getDate() + addDay));

        var month = defDate.getMonth() + 1;
        var strToday = defDate.getFullYear();
        strToday += "-" + ((month < 10) ? "0" + month : month);
        var date = today.getDate();
        strToday += "-" + ((date < 10) ? "0" + date : date);
        return strToday;
    };

    let setDefaultDte = function(){

        // 요청일
        $fromDate.datebox({validType: "validDate", prompt: "Search Request Date"});// easyui
        $toDate.datebox({validType: "validDate", prompt: "Search Request Date"});// easyui
        // default date
        $fromDate.datebox("setValue", String(getDefDate(-90)));
        $toDate.datebox("setValue", String(paragonCmm.getToday()));
    };

    let initSchObj = function(){

        console.debug("init sch obj....")

        // 1. 요청구분
        setComboBox("REQ_GUBUN", $schIssueTpCd, "Select Issue Type");
        // 2. 요청분야
        setComboBox("REQ_BUNYA", $schIssueDomainCd, "Select Issue Domain");
        // 3. 진행상태
        setComboBox("REQ_STATUS", $schIssueStuCd, "Select Issue Status");

        // $schIssueStuCd.val("10").prop("selected", true);

        setDefaultDte();
    };

    let setStatsData = function(data){
        let keys = Object.keys(data);
        let cnt = 0;
        keys.forEach(function(key){
            switch (key) {
                case "reqCnt":
                case "rcvCnt":
                case "rjtCnt":
                    cnt = filterXSS(data[key]);
                    if(paragonCmm.isEmpty(cnt)){
                        cnt = 0;
                    }
                    console.debug(cnt);
                    $("[data-col='" + key + "']", $rootLayer).html(cnt);
                    break;
                default:
                    break;
            }
        });
    };

    let initStatus = function(){
        model.getIssueStat({
            cbFnc: function(json){
                if (json.errYn === "E") {
                    //-- 오류처리
                    $.messager.alert("Error", json.msg,"error");
                    return false;
                } else  {
                    setStatsData(json.data);
                }
            }
        });
    };

    let getQueryParams = function(){
        return $schForm.serializeObject();
    };

    var getGridHeight = function () {
        var ENURI = 400;
        var SCH_HEIGHT = 0; //$searchFrm.innerHeight();
        var windowHeight = window.innerHeight;
        return windowHeight - SCH_HEIGHT - ENURI;
    };

    var getPageSize = function () {
        var gHeight = getGridHeight();
        if (gHeight > 700) {
            return 30;
        }else if(gHeight > 500){
            return 20;
        }
        return 10;
    };

    let issueTpStyle = function(value, row, index){
        let style = "";
        ISSUE_TP_COLOR.some(function(itm){
            if(row.issueTpCd === itm.cd){
                style+="background-color: "+itm.bgColor+";";
                style+="color: "+itm.color+";";
            }
        });
        return style;
    };

    let issueStuStyle = function(value, row, index){
        let style = "";
        ISSUE_STU_COLOR.some(function(itm){
            if(row.issueStuCd === itm.cd){
                style+="background-color: "+itm.bgColor+";";
                style+="color: "+itm.color+";";
            }
        });
        return style;
    };

    let openView = function(row){

        let issueId = (paragonCmm.isNotEmpty(row))? row.issueId : "";
        let targetName = "ISSUE_INFO_"+issueId;

        htmlUtils.openPopup({
            url: model.getViewUrl(),
            targetName: targetName,
            popupTitle: "이슈정보",
            popWidth: 1152,
            popHeight: 540,
            openerData: {
                cbKey: OBJ_ID,
                issueId: issueId
            },
            params: {
                issueId: issueId
            }
        });
    };

    let initGrid = function (){
        $grid.datagrid({
            url: model.getListUrl(),
            singleSelect: true,
            striped: true,
            fitColumns: false,
            rownumbers: true,
            multiSort: true,
            pagination: true,
            pagePosition: 'bottom',
            nowrap: false,
            method: "post",
            queryParams: getQueryParams(),
            loadFilter: paragonCmm.easyuiLoadFilter,
            height: getGridHeight(),
            pageSize: getPageSize(),
            columns: [[
                {field: "issueId", hidden: true}
                , {field: "orgId", hidden: true}
                , {field: "systemId", hidden: true}
                , {field: "issueStuCd", hidden: true}
                , {field: "issueTpCd", hidden: true}
                , {
                    field: "reqNo",
                    title: paragonCmm.getLang("L.접수번호"),
                    formatter: filterXSS,
                    halign: "CENTER",
                    align: "CENTER",
                    width: "6%"
                }, {
                    field: "orgNm",
                    title: paragonCmm.getLang("L.회사명"),
                    formatter: filterXSS,
                    halign: "CENTER",
                    align: "CENTER",
                    width: "10%"
                }, {
                    field: "systemName",
                    title: paragonCmm.getLang("L.시스템명"),
                    formatter: filterXSS,
                    halign: "CENTER",
                    align: "CENTER",
                    width: "14%"
                }, {
                    field: "issueStuNm",
                    title: paragonCmm.getLang("L.진행상태"),
                    formatter: filterXSS,
                    styler: issueStuStyle,
                    halign: "CENTER",
                    align: "CENTER",
                    width: "8%"
                }, {
                    field: "issueTpNm",
                    title: paragonCmm.getLang("L.요청구분"),
                    formatter: filterXSS,
                    styler: issueTpStyle,
                    halign: "CENTER",
                    align: "CENTER",
                    width: "8%"
                }, {
                    field: "reqIssueTitle",
                    title: paragonCmm.getLang("L.제목"),
                    formatter: filterXSS,
                    halign: "CENTER",
                    align: "LEFT",
                    width: "25%"
                }, {
                    field: "cmtCnt",
                    title: '<i class="fa-solid fa-comment-dots"></i>',
                    formatter: filterXSS,
                    halign: "CENTER",
                    align: "CENTER",
                    width: "2%"
                }, {
                    field: "reqNm",
                    title: paragonCmm.getLang("L.요청자"),
                    formatter: filterXSS,
                    halign: "CENTER",
                    align: "CENTER",
                    width: "7%"
                }, {
                    field: "reqDte",
                    title: paragonCmm.getLang("L.요청일"),
                    formatter: filterXSS,
                    halign: "CENTER",
                    align: "CENTER",
                    width: "5%"
                }, {
                    field: "rcvNm",
                    title: paragonCmm.getLang("L.담당자"),
                    formatter: filterXSS,
                    halign: "CENTER",
                    align: "CENTER",
                    width: "5%"
                }, {
                    field: "rcvDte",
                    title: paragonCmm.getLang("L.접수일"),
                    formatter: filterXSS,
                    halign: "CENTER",
                    align: "CENTER",
                    width: "5%"
                }, {
                    field: "cfmDte",
                    title: paragonCmm.getLang("L.검수완료일"),
                    formatter: filterXSS,
                    halign: "CENTER",
                    align: "CENTER",
                    width: "5%"
                }
            ]],
            onDblClickRow: function (index, row) {
                openView(row);
            }
        });
    };

    let search = function(){
        $grid.datagrid("load", getQueryParams());
    };

    let reqIssue = function(){
        htmlUtils.openPopup({
            url: model.getWriteUrl(),
            targetName: "REQ_ISSUE_WRITE",
            popupTitle: "이슈요청",
            popWidth: 1152,
            popHeight: 700,
            openerData: {
                cbKey: OBJ_ID
            },
            params: null
        });
    };

    let expExcel = function(){
        // $grid.datagrid("toExcel", {
        //     filename: "issue-list_"+paragonCmm.getToday()+".xls",
        //     worksheet: "issue list",
        //     caption: "Issue List"
        // });

        // 전체 엑셀출력
        paragonCmm.grid.toExcelAll({
            grid: $grid,
            filename: "issue-list_"+paragonCmm.getToday()+".xlsx",
            worksheet: "issue list",
            caption: "Issue List"
        });
    };

    let setFormEvent = function(){
        $schForm.on("submit", function(){
            search();
            return false;
        });

        $schForm.on("keyup", "input", function (e) {
            if (e.keyCode === 13){
                search();
            }
            return false;
        });
    };
    let setBtnEvent = function(){
        $rootLayer.off("click", "button");
        $rootLayer.on("click", "button", function(){
            var btnId = $(this).attr("id");
            switch (btnId){
                case "issueMngResetSmBtn":
                case "issueMngResetBtn1":
                    $schForm.clearForm();
                    setDefaultDte();
                    break;
                case "issueMngSearchSmBtn":
                case "issueMngSearchBtn1":
                    search();
                    break;
                case "issueMngAddBtn1":
                    reqIssue();
                    break;
                case "issueMngExcelBtn1":
                    expExcel();
                    break;
                default:
                    console.warn("not support btnId:"+btnId);
                    return;
            }

            return false; // 이벤트 확산금지
        });

        $rootLayer.off("click", "a");
        $rootLayer.on("click", "a", function(){
            var btnId = $(this).data("btn");
            switch (btnId){
                case "schReqBtn":
                    $schForm.clearForm();
                    // $issueListPage.val("1");
                    $schIssueStuCd.val("10");
                    search();
                    break;
                case "schRcvBtn":
                    $schForm.clearForm();
                    // $issueListPage.val("1");
                    $schIssueStuCd.val("50");
                    search();
                    break;
                case "schRjtBtn":
                    $schForm.clearForm();
                    // $issueListPage.val("1");
                    $schIssueStuCd.val("91");
                    search();
                    break;
                default:
                    console.warn("not support btnId:"+btnId);
                    return;
            }

            return false; // 이벤트 확산금지
        });
    };

    let initForm = function(){
        initSchObj();
        initStatus();

        initGrid();
    };
    let loadForm = function(){};
    let setEvent = function(){
        setFormEvent();
        setBtnEvent();
    };

    let setCbFnc = function(){
        paragonCmm.setCbFnc(OBJ_ID, search);
    };

    // ==============================================
    // public functions
    // ----------------------------------------------
    let init = function(){
        console.debug("init isseuMng....");
        initForm();
        loadForm();
        setEvent();
        setCbFnc();
    };

    return {
        init: init
    };
};

$(function(){
    "use strict";
    let issueList = new IssueList();
    issueList.init();
});