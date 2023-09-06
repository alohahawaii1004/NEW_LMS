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

    const ISSUE_STATS_URL = paragonCmm.getUrl("/support/issue/getStatsData/json");
    const ISSUE_LIST_URL = paragonCmm.getUrl("/support/issue/listPerPage/json");
    const ISSUE_WRITE_URL = paragonCmm.getUrl("/support/issue/issueReqWrite.popup");
    const ISSUE_VIEW_URL = paragonCmm.getUrl("/support/issue/issueInfo.popup");

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
        getIssueList: function(options){
            callAjax(options, ISSUE_LIST_URL);
        },
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
    const ROWS = 10;
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
    let model = new IssueListModel();
    let $rootLayer = $("#issueMngRootLayer");
    let $schForm = $("#issueMngSchFrm0", $rootLayer);
    let $schIssueTpCd = $("select[name=schIssueTpCd]", $schForm);
    let $schIssueDomainCd = $("select[name=schIssueDomainCd]", $schForm);
    let $schIssueStuCd = $("select[name=schIssueStuCd]", $schForm);
    let $grid = $("#issueMngList1", $rootLayer);
    let $issueBody = $("#issueBody", $rootLayer);
    let $issueListPagination = $("#issueListPagination", $rootLayer);
    let $issueListPage = $("#issueListPage", $rootLayer);
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


    let initSchObj = function(){

        console.debug("init sch obj....")

        // 1. 요청구분
        setComboBox("REQ_GUBUN", $schIssueTpCd, "Select Issue Type");
        // 2. 요청분야
        setComboBox("REQ_BUNYA", $schIssueDomainCd, "Select Issue Domain");
        // 3. 진행상태
        setComboBox("REQ_STATUS", $schIssueStuCd, "Select Issue Status");

        setDefaultDte();
    };
    let setDefaultDte = function(){
        // 요청일
        $fromDate.datebox({validType: "validDate", prompt: "Search req. Date"});// easyui
        $toDate.datebox({validType: "validDate", prompt: "Search req. Date"});// easyuis
        // default date
        $fromDate.datebox("setValue", String(getDefDate(-90)));
        $toDate.datebox("setValue", String(paragonCmm.getToday()));
    };

    let setStatsData = function(data){
        let keys = Object.keys(data);
        let cnt = 0;
        keys.forEach(function(key){
            switch (key) {
                case "reqCnt":
                case "rcvCnt":
                case "rsltCnt":
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
        // ISSUE_TP_COLOR.some(function(itm){
        //     if(row.issueTpCd === itm.cd){
        //         style+="background-color: "+itm.bgColor+";";
        //         style+="color: "+itm.color+";";
        //     }
        // });
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

    let setNoDataRow = function(){
        return "<tr><td class='text-center'>NO DATA</td></tr>";
    };

    let setDataRow = function(itm){
        let stuClass = "badge-light";
        switch(itm.issueStuCd){
            case "10":
                stuClass = "badge-warning";
                break;
            case "50":
                stuClass = "badge-info";
                break;
            case "80":
                stuClass = "badge-success";
                break;
            case "91":
                stuClass = "badge-danger";
                break;
            default:
                break;
        }
        let strRow = "<tr data-issue-id='"+filterXSS(itm.issueId)+"'>";
        strRow += '<td>';
        strRow += '<div class="row">';
        strRow += '        <div class="col-md-10">';
        strRow += '            <h5><small class="text-sm" data-col="reqNo">'+filterXSS(itm.reqNo)+'</small> <span data-col="reqIssueTitle">'+filterXSS(itm.reqIssueTitle)+'</span>  </h5>';
        strRow += '        </div>';
        strRow += '        <div class="col-md-2 text-right">';
        strRow += '            <span class="badge '+stuClass+' border rounded" data-col="issueStuNm">'+filterXSS(itm.issueStuNm)+'</span>';
        strRow += '        </div>';
        strRow += '    </div>';
        strRow += '    <div class="row">';
        strRow += '        <div class="col-md-6">';
        strRow += '            <span class="badge badge-light border rounded text-info" id="sysMgrViewGoUser"><i class="fa-solid fa-user"></i> <span data-col="reqNm">'+filterXSS(itm.reqNm)+'</span></span>';
        strRow += '            <span class="badge badge-light border rounded text-info" title="요청일"><i class="fa-solid fa-pen-to-square"></i> <span data-col="strReqDte">'+filterXSS(itm.reqDte)+'</span></span>';
        if(0 !== itm.cmtCnt){
            strRow += '            <span class="badge badge-light border rounded text-pink" title="comments"><i class="fa-solid fa-comment-dots"></i> <span data-col="cmtCnt">'+filterXSS(itm.cmtCnt)+'</span></span>';
        }
        strRow += '            <span class="badge badge-light border rounded text-info" id="sysMgrViewGoSystem"><i class="fa-solid fa-computer text-lightblue"></i> <span data-col="systemName">'+filterXSS(itm.systemName)+'</span></span>';
        // strRow += '            <span class="badge badge-light border rounded" id="sysMgrViewGoCustomer"><i class="fa-regular fa-building text-orange"></i> <span data-col="orgNm">'+filterXSS(itm.orgNm)+'</span></span>';
        if(paragonCmm.isNotEmpty(itm.issueTpNm)) {
            strRow += '            <span class="badge badge-light border rounded text-info"><i class="fa-solid fa-layer-group"></i> <span data-col="issueTpNm">' + filterXSS(itm.issueTpNm) + '</span></span>';
        }
        if(paragonCmm.isNotEmpty(itm.issueDomainNm)) {
            strRow += '            <span class="badge badge-light border rounded text-info"><i class="fa-solid fa-circle-info"></i> <span data-col="issueDomainNm">' + filterXSS(itm.issueDomainNm) + '</span></span>';
        }
        strRow += '        </div>';
        strRow += '        <div class="col-md-6 text-right">';
        strRow += '        </div>';
        strRow += '    </div>';
        strRow += '</td>';
        strRow += '</tr>';
        return strRow;
    };

    let setIssueList = function(data){
        let strHtml = "";
        if(Array.isArray(data.list)){
            if(data.list.length === 0){
                strHtml += setNoDataRow();
            }else{
                data.list.forEach(function(itm){
                    strHtml += setDataRow(itm);
                });
            }
        }
        $issueBody.html("");
        $issueBody.html(strHtml);
    };

    let search = function(){
        let page = $issueListPage.val();
        if(paragonCmm.isEmpty(page)){
            page = 1;
        }
        let params = $.extend({
            page: page,
            rows: ROWS
        }, getQueryParams());
        paragonCmm.showBackDrop();
        model.getIssueList({
            params: params,
            cbFnc: function(json){
                paragonCmm.hideBackDrop();
                if (json.errYn === "E") {
                    //-- 오류처리
                    $.messager.alert("Error", json.msg,"error");
                    return false;
                } else  {
                    setIssueList($.extend({}, json.data));

                    $issueListPagination.pagination("refresh", {
                        total: json.data.totalCount.totCnt // 검색 총 건수
                    });
                }
            }
        });
    };

    let initPagination = function(){
        $issueListPagination.pagination({
            total: 0,
            showPageList: false,
            showRefresh: false,
            pageSize: 10,
            pageNumber: 20,
            // displayMsg: 'displayMsg',
            layout:['list','sep','first','prev','links','next','last','sep','info'],
            onSelectPage: function (pageNo, pageSize) {
                $(this).pagination('loading');
                $issueListPage.val(pageNo);
                $(this).pagination('loaded');
                search();
            }
        });
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

        $rootLayer.off("click", "tr");
        $rootLayer.on("click", "tr", function(){
            var btnId = $(this).data("issue-id");
            console.debug(btnId);
            openView({issueId: btnId});
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
                    $issueListPage.val("1");
                    $schIssueStuCd.val("10");
                    search();
                    break;
                case "schRcvBtn":
                    $schForm.clearForm();
                    $issueListPage.val("1");
                    $schIssueStuCd.val("50");
                    search();
                    break;
                case "schRsltBtn":
                    $schForm.clearForm();
                    $issueListPage.val("1");
                    $schIssueStuCd.val("80");
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

        initPagination();
        search();
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