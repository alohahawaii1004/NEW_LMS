/*
 * @(#)dashboard.js
 *
 * Copyright 2022 JAYU.space
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

/**
 * <b>Description</b>
 * <pre>
 *    메인 데시보드 화면
 * </pre>
 * @author 강세원
 * @auther 백서윤
 */
"use strict";
var dashBoard = (function () {
    "use strict";
    // ==============================================
    // attributes
    // ----------------------------------------------


    var $dashBoardRoot = $("#dashBoardRoot");

    // ==============================================
    // private functions
    // ----------------------------------------------

    /**
     * todoList obj
     * @type {{search: search, initGrid: initGrid}}
     */
    var todoListCard = (function(gridObj){

        var GRID_URL = paragonCmm.getUrl("/paragon/main/ips/todoList/json");
        var GRID_CB_KEY = "dashBoardTodo";
        // var GRID_HEIGHT =

        var getQueryParams = function(){
            return {
                schSolTpCd: $("#schSolTp").val()
            }
        };

        var initGrid = function(){
            gridObj.datagrid({
                method: "post",
                url: GRID_URL,
                queryParams: getQueryParams(),
                loadFilter: paragonCmm.easyuiLoadFilter,
                height: "200px",
                striped : true,
                fitColumns : true,
                nowrap: true,
                multiSort : false,
                remoteSort: true,
                rownumbers: true,
                singleSelect: true,
                checkOnSelect: true,
                selectOnCheck: true,
                pagination : true,
                pageSize: 10,
                pagePosition: 'bottom',
                emptyMsg: "NO DATA",
                columns: [[{
                    field: "solMasUid", hidden: true
                },{
                    field: "mngNo", title: paragonCmm.getLang("L.관리번호"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "10%"
                },{
                    field: "solTpLangCd", title: paragonCmm.getLang("L.구분"), formatter: paragonCmm.getLang, halign: "CENTER", align: "CENTER", width: "10%"
                },{
                    field: "docNmLangCd", title: paragonCmm.getLang("L.유형"), formatter: paragonCmm.getLang, halign: "CENTER", align: "CENTER", width: "10%"
                },{
                    field: "title", title: paragonCmm.getLang("L.제목"), formatter: filterXSS, halign: "CENTER", align: "LEFT", width: "30%"
                },{
                    field: "reqUserNm", title: paragonCmm.getLang("L.주발명자"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "10%"
                },{
                    field: "regUserNm", title: paragonCmm.getLang("L.작성자"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "10%"
                },{
                    field: "regDte", title: paragonCmm.getLang("L.작성일자"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "10%"
                },{
                    field: "procLangCd", title: paragonCmm.getLang("L.상태"), formatter: paragonCmm.getLang, halign: "CENTER", align: "CENTER", width: "10%"
                }]],
                onDblClickRow: function(index, row){

                    var cbFnc = paragonCmm.getCbFnc(GRID_CB_KEY);
                    if (typeof cbFnc != "function") {
                        paragonCmm.setCbFnc(GRID_CB_KEY, search);
                    }

                    // .goView(\"@{solMasUid}\",\"@{stuCd}\",\"@{stuDtl}\",\"@{docUid}\",\"@{todoType}\")

                    paragonIps.goPopupIndex({
                        solMasUid: row.solMasUid,
                        docUid: row.docUid,
                        stuCd: row.stuCd,
                        stuDtl: row.stuDtl,
                        todoType: row.todoType,
                        openerData: {
                            cbKey: GRID_CB_KEY
                        }
                    });
                }
            });
        };

        var search = function(){
            gridObj.datagrid("load", getQueryParams());
        };

        return {
            initGrid: initGrid,
            search: search,
            cbKey: GRID_CB_KEY
        };
    })($("#todoList", $dashBoardRoot));


    /**
     * 공지사항
     * @type {{init: init, goMore: null, openNotice: null}}
     */
    var noticeCard = (function(){

        //공지사항
        var BOARD_URL = paragonCmm.getUrl("/paragon/main/Main/boardList/json");
        var BOARD_VIEW_POPUP_URL = paragonCmm.getUrl("/paragon/bbs/bbsMasView.popup");
        var boardTblId = "boardTbl";
        var $moreBtn = $("#noticeMore");
        var $boardTbl = $("#"+boardTblId);


        var openView = function(bbsUid){
            var openerData = {
                isNew: "FALSE",
                bbsUid: bbsUid,
                dashBoardTodoChk: "Y"
            };

            htmlUtils.openPopup({
                url: BOARD_VIEW_POPUP_URL,
                targetName: "POPUP_VIEW",
                popupTitle: "공지사항",
                popWidth: 1024,
                popHeight: 540,
                params: {
                    bbsUid: bbsUid
                }
            });
        };

        var openNotice = function(){
            var popupList = [];
            var popupViewAuth = [];
            var data = paragonCmm.getSearchQueryParams();
            paragonCmm.callAjax(BOARD_URL, data, function (json) {
                data = paragonCmm.easyuiLoadFilter(json);
                var bbsList = data.rows;
                var iLen = bbsList.length;
                for (var i = 0; i <= iLen - 1; i++) {
                    var bbsPopupYn = bbsList[i].bbsPopupYn;
                    if (bbsPopupYn == "Y") {
                        popupList.push(bbsList[i].bbsUid);
                        popupViewAuth.push(bbsList[i].bbsViewAuth);
                    }
                }// end of for

                if (paragonCmm.getCookie("toDate") != "ok") {
                    var popupLen = popupList.length;

                    //팝업을 띄울 리스트  : popupList
                    //popupList.bbsAuth 에 허가되는 권한이 있음.
                    for (var i = 0; i <= popupLen - 1; i++) {
                        var openerData = {
                            isNew: "FALSE",
                            bbsUid: popupList[i],
                            openType: "MAIN",
                            dashBoardChk: "Y"
                        };

                        var authcode = [];
                        if (loginInfo.authCd.includes(",")) {

                            authcode = loginInfo.authCd.split(",");
                        } else {
                            authcode[0] = loginInfo.authCd;
                        }

                        var popupcode = [];
                        if (popupViewAuth[0].includes(",")) {

                            popupcode = popupViewAuth[0].split(",");
                        } else {
                            popupcode[0] = popupViewAuth[0];
                        }

                        for (var j = 0; j < popupcode.length; j++) {
                            //    0
                            for (var k = 0; k < authcode.length; k++) {
                                //    0
                                if (authcode[k] == popupcode[j]) {
                                    // var imsiForm = $("<form method='POST'>").attr("action", paragonCmm.getUrl("/paragon/bbs/bbsMasView.popup"));
                                    // imsiForm.append($("<input type='hidden' name='_csrf'>").val($("meta[name='_csrf']").attr("content")));
                                    // imsiForm.append($("<input type='hidden' name='openerData'>").val(JSON.stringify(openerData)));
                                    // paragonCmm.openWindow("", "1024", "580", "POPUP_VIEW", "yes", "yes", "");
                                    // imsiForm.attr("target", "POPUP_VIEW");
                                    // imsiForm.appendTo("body");
                                    // imsiForm.submit();
                                    // imsiForm.remove();
                                }
                            }
                        }
                    }
                }

            });
        };



        var setEvent = function(){

            $moreBtn.off("click");
            $moreBtn.on("click", function(){
                main.goPage({
                    langCd: "L.공지사항",
                    accesUrl: '/paragon/bbs/bbsList',
                    bbsTpCd: "CMM_BBS_NOTICE"
                });
            });
        };

        var init = function(pageNo){
            if (typeof pageNo === "undefined" ) {
                pageNo = 1;
            }
            var data = paragonCmm.getSearchQueryParams();
            data["page"] = pageNo;
            data["rows"] = "3";

            paragonCmm.callAjax(BOARD_URL, data, function (json) {
                for (var i = 0; i < json.data.length; i++) {
                    json.data[i].bbsTit = paragonCmm.getTrunc(json.data[i].bbsTit, 20, "..");
                }
                var data = paragonCmm.easyuiLoadFilter(json);
                var page = "";
                var rowCnt = "";
                htmlUtils.createTableRow(boardTblId, data, page, rowCnt, "dashBoard.loadBoard");


                setEvent();
            });
        };

        return{
            openNotice: openNotice,
            openView: openView,
            goMore: null,
            init: init
        };
    })();


    /**
     * 특허현황
     * @type {{init: init}}
     */
    var ipStatsCard = (function(){
        var loginInfo = paragonCmm.formMap.get("loginInfo");

        var STATS_POP_URL = paragonCmm.getUrl("/paragon/cmm/procCntPop.popup");
        var COST_INFO_POP_URL = paragonCmm.getUrl("/paragon/cmm/procCntPop.popup");
        //지재권 현황
        var IP_STAT_URL = paragonCmm.getUrl("/ips/main/Dashboard/selectIpsStat/json");

        // 현황 리스트 템플릿
        var $bdTmpl = $("#bdTmpl");
        var $headTmpl = $("#headTmpl");
        var $bodyTmpl = $("#bodyTmpl");

        //지재권 현황 테이블
        var $thTb = $("#thTb");
        
        var TITLE = {
            APP_READY: paragonCmm.getLang("L.출원준비(발명신고/선행기술조사)"),
            APP: paragonCmm.getLang("L.출원"),
            REVIEW: paragonCmm.getLang("L.심사중"),
            REG: paragonCmm.getLang("L.등록"),
        };

        var setEvent = function(){
            $thTb.off("click", "a");
            $thTb.on("click", "a", function(){
                console.debug($(this));
                var metas = $(this).data("meta").split(",");
                if(metas.length === 2){
                    htmlUtils.openPopup({
                        url: STATS_POP_URL,
                        targetName: $(this).data("row"),
                        popupTitle: TITLE[$(this).data("row")] + " 현황",
                        popWidth: 1280,
                        popHeight: 750,
                        params: {
                            ipTpCd: metas[0],
                            schStu: metas[1]
                        }
                    });
                }
            });
        };

        var loadData = function(){
//        if(loginInfo.isChr || loginInfo.isIsj){ //-- 담당자

            var sData = {};
//            $("#thTb").empty();
            sData.ipTpCd = "TH";
            paragonCmm.callAjax(IP_STAT_URL, sData, function (json) {
                var ttdata = json.data[0];
                if (ttdata != null) {
                    $("#APP_READY", "#thTb").html('<a href="javascript:void(0);" data-meta="TH,APP_READY" data-row="APP_READY">' + ttdata.appReady + '</a>');
                    $("#APP", "#thTb").html('<a href="javascript:void(0);" data-meta="TH,APP" data-row="APP">' + ttdata.app + '</a>');
                    $("#REVIEW", "#thTb").html('<a href="javascript:void(0);" data-meta="TH,REVIEW" data-row="REVIEW">' + ttdata.review + '</a>');
                    $("#REG", "#thTb").html('<a href="javascript:void(0);" data-meta="TH,REG" data-row="REG">' + ttdata.reg + '</a>');
                }
                setEvent();
            })
            sData.ipTpCd = "TM";
            paragonCmm.callAjax(IP_STAT_URL, sData, function (json) {
                var ttdata = json.data[0];
                if (ttdata != null) {
                    $("#APP_READY", "#tmTb").html('<a href="javascript:void(0);" data-meta="TM,APP_READY" data-row="APP_READY">' + ttdata.appReady + '</a>');
                    $("#APP", "#tmTb").html('<a href="javascript:void(0);" data-meta="TM,APP" data-row="APP">' + ttdata.app + '</a>');
                    $("#REVIEW", "#tmTb").html('<a href="javascript:void(0);" data-meta="TM,REVIEW" data-row="REVIEW">' + ttdata.review + '</a>');
                    $("#REG", "#tmTb").html('<a href="javascript:void(0);" data-meta="TM,REG" data-row="REG">' + ttdata.reg + '</a>');
                }
                setEvent();
            })

            //발명자인터뷰 테이블
//            var $thTb = $("#csTb");
//            var sData = {};
//            $("#csTb").empty();
//            $bdTmpl.tmpl("").appendTo($thTb);
//            paragonCmm.callAjax(CS_STAT_URL,sData, function(json){
//                if($(json.data).length > 0){
//                    var headWidth = 100/$(json.data).length;
//                    $(json.data).each(function(i, o) {
//                        if(o != null) {
//                            o.width = headWidth;
//                            $headTmpl.tmpl(o).appendTo($("#statHead", "#csTb"));
//                            $bodyTmpl.tmpl(o).appendTo( $("#statBody","#csTb"));
//                        }
//                    });
//                }
//            })

            //-- 비용청구 상태(사무소)
            var $thTb = $("#byTbl");
            if ($thTb.length > 0) {
                paragonCmm.callAjax(BY_STAT_URL, sData, function (json) {
                    var ttdata = json.data[0];
                    $("#BY_REQ", $thTb).html('<a href="javascript:void(0);" data-row="BY_REQ">' + ttdata.byReq + '</a>');
                    $("#BY_REJ", $thTb).html('<a href="javascript:void(0);" data-row="BY_REJ">' + ttdata.byRej + '</a>');
                    $("#BY_CFM", $thTb).html('<a href="javascript:void(0);" data-row="BY_CFM">' + ttdata.byCfm + '</a>');
                    $("#BY_TAX", $thTb).html('<a href="javascript:void(0);" data-row="BY_TAX">' + ttdata.byTax + '</a>');
                    $("a", $thTb).off();
                    $("a", $thTb).on("click", function () {
                        var keyWrd = $(this).data("row");
                        htmlUtils.openPopup({
                            url: COST_INFO_POP_URL,
                            targetName: "POP_WRITE_" + keyWrd,
                            popupTitle: "비용",
                            popWidth: 1280,
                            popHeight: 750,
                            openerData: {
                                schTp: keyWrd
                            }
                        });
                    });
                });
            }
        };

        var init = function(){
            loadData();
        };

        return{
            init: init
        };
    })();

    /**
     * 일정
     * @type {{init: init}}
     */
    var calendarCard = (function(){

        //당해년도 연간계획/실적
        var CAL_CNT_URL = paragonCmm.getUrl("/ips/cmm/scdl/ScdlMas/selectCnt/json");
        var SCDL_INFO_POP_URL = paragonCmm.getUrl("/ips/cmm/scdl/scdlInfo.popup");

        var arrMonth = [];
        var arrDay = [];
        var arrCnt = [];
        var curMonth = new Date();
        var data = paragonCmm.getSearchQueryParams();

        var goScdlInfo = function (date) {

            var currentDate = date.getFullYear() + '-' + ('0' + (date.getMonth() + 1)).slice(-2) + '-' + ('0' + (date.getDate())).slice(-2);

            htmlUtils.openPopup({
                url: SCDL_INFO_POP_URL,
                targetName: "SCDL_LIST",
                popupTitle: "일정",
                popWidth: 1280,
                popHeight: 750,
                openerData: {
                    currentDate: currentDate
                }
            });
        }

        var initCalendar = function(){
            $('#scdlCalendar').calendar({
                current: new Date(),
                // width: 390,
                // height: 300,
                yearSuffix: "년" ,//달력의 년도 부분 뒤에 붙는 텍스트
                monthNamesShort: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'], //달력의 월 부분 텍스트
                monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'], //달력의 월 부분 Tooltip 텍스트
                dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'], //달력의 요일 부분 텍스트
                dayNames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'], //달력의 요일 부분 Tooltip 텍스트
//            ,formatter: checkScdl
                styler: function (date) {
                    var d = date.getFullYear() + '-' + (('0' + (date.getMonth() + 1)).slice(-2)) + '-' + ('0' + (date.getDate())).slice(-2)
                    var dToDay = curMonth.getFullYear() + '-' + (('0' + (curMonth.getMonth() + 1)).slice(-2)) + '-' + (('0' + (curMonth.getDate())).slice(-2))
                    var rtnStyle = "";
                    $(arrCnt).each(function (i, o) {
                        var calChk2 = (('0' + (date.getMonth() + 1)).slice(-2)) + '-' + ('0' + (date.getDate())).slice(-2)
                        if (o === calChk2) {
                            rtnStyle = 'background-color:#ededed';
                        }
                        if (d === dToDay) {
                            rtnStyle = 'background-color:#39c';
                        }
                    })
                    return rtnStyle;
                },
                onSelect: function (date) {
                    goScdlInfo(date);
                }
            });
        };

        var loadData = function(){
            paragonCmm.callAjax(CAL_CNT_URL, data, function (json) {
                $(json.data).each(function (i, o) {
                    if (o != null) {
                        arrDay.push(o.daycnt);
                        arrMonth.push(o.monthcnt);
                        arrCnt.push(o.cnt);
                    }
                });

                initCalendar();
            });
        };

        var init = function(){
            loadData();
        };

        return {
            init: init
        };
    })();

    /**
     * 바로가기
     * @type {{init: init}}
     */
    var shortCutCard = (function(){

        var DEF_STU_FORM_WRITE_POPUP_URL = paragonCmm.getUrl("/paragon/def/defstuform/defStuFormWrite.popup");
        var IP_AGREE_POPUP_URL = paragonCmm.getUrl("/ips/ip/ipAgree.popup");
        var DGN_AGREE_POPUP_URL = paragonCmm.getUrl("/ips/ip/ipAgreeDj.popup");
        var SEL_IP_URL = paragonCmm.getUrl("/ips/cmm/ip/ipInfo.modal");

        var $shortCuts = $("#shortCuts");

        var setCbFnc = function(){
            var cbFnc = paragonCmm.getCbFnc(todoListCard.cbKey);
            if (typeof cbFnc != "function") {
                paragonCmm.setCbFnc(todoListCard.cbKey, todoListCard.search);
            }
        };

        var goLink = function(obj){

            console.debug(obj);

            var url = obj.data("url");
            console.debug(url);
            var strUrl = String(url);
            var urlPostfix = strUrl.substring(strUrl.lastIndexOf("."));

            var targetName = url;
            var popupTitle = obj.text();
            var params = {};
            var openerData = {
                cbKey : todoListCard.cbKey
            }

            setCbFnc();

            switch (urlPostfix){
                case ".file":
                    // TODO 메뉴얼 다운로드 보안검토 필요
                    var fileNm = url.replaceAll(".file", "");
                    htmlUtils.formDownload(fileNm);
                    break;
                case ".agree":
                    openerData.popupTitle = popupTitle;
                    htmlUtils.openPopup({
                        url: IP_AGREE_POPUP_URL,
                        targetName: targetName,
                        popupTitle: popupTitle,
                        popWidth: 1280,
                        popHeight: 750,
                        openerData: openerData,
                        params: params
                    });
                    break;
                case ".agreeDj":
                    openerData.popupTitle = popupTitle;
                    htmlUtils.openPopup({
                        url: DGN_AGREE_POPUP_URL,
                        targetName: targetName,
                        popupTitle: popupTitle,
                        popWidth: 1280,
                        popHeight: 750,
                        openerData: openerData,
                        params: params
                    });
                    break;
                case ".popup":
                    params.stuCd = url.replaceAll(".popup", "");
                    // def... 로 감, 상태 값 포함.
                    htmlUtils.openPopup({
                        url: DEF_STU_FORM_WRITE_POPUP_URL,
                        targetName: targetName,
                        popupTitle: popupTitle,
                        popWidth: 1280,
                        popHeight: 750,
                        openerData: openerData,
                        params: params
                    });
                    break;
                case ".ipSearch":
                    var schTp = url.replaceAll(".ipSearch", "");
                    htmlUtils.openModal({
                        title: paragonCmm.getLang("L.대상선택"),
                        width: 900,
                        height: 650,
                        href: SEL_IP_URL,
                        modalId: "DLREQWRITE",
                        contentId: "ipSearch",
                        reqParam: {
                            schTp: schTp
                        },
                        cbFnc: function(data){
                            // def... 로 감, 상태 값, solMasUid 포함.
                            htmlUtils.openPopup({
                                url: DEF_STU_FORM_WRITE_POPUP_URL,
                                targetName: schTp,
                                popupTitle: popupTitle,
                                popWidth: 1280,
                                popHeight: 750,
                                openerData: {
                                    cbKey : todoListCard.cbKey
                                },
                                params: {
                                    solMasUid: data[0].solMasUid,
                                    stuCd: schTp
                                }
                            });
                        }
                    });
                    break;
                default:
                    main.goPage(paragonCmm.getUrl(url), {});
                    break;
            }
        };

        var setEvent = function(){
            $shortCuts.on("click", "button", function(){
                goLink($(this));
            });
        };

        var init = function(){
            setEvent();
        };

        return{
            init: init
        };
    })();



    var $schSolTp = $("#solTpArea", "#dashboardTodo");

    //기한관리
    var DUE_URL = paragonCmm.getUrl("/ips/due/DueMas/recentList/json");
    var dueTblId = "dueTbl";


    //발명자인터뷰 현황
    var CS_STAT_URL = paragonCmm.getUrl("/ips/main/Dashboard/selectCsStat/json");

    //비용청구 현황
    var BY_STAT_URL = paragonCmm.getUrl("/ips/main/Dashboard/selectByStat/json");

    // @deprecated
    var initForm = function () {
        if ($schSolTp.length > 0) {
            var str = "|" + paragonCmm.getLang("L.CBO_ALL") + "^CS|발명자인터뷰^IP|지재권^CO|비용^TT|기술이전^DF|품의^DL|심의";
            $schSolTp.html(htmlUtils.getSelect("schSolTp", "schSolTp", str, ""));
        }
    };

    var loadCards = function(){
        // todoList
        todoListCard.initGrid();
        // 특허현황
        ipStatsCard.init();
        // 공지사항
        noticeCard.init();
        // 일정
        calendarCard.init();
        // 바로가기
        shortCutCard.init();

        // 담당자 정보
    };

    // TODO 분쟁정보 쪽 팝업 확인 필요
    var loadPop = function (ipTpCd, schStu) {
        var imsiForm = $("<form method='POST'>").attr("action", paragonCmm.getUrl("/paragon/cmm/procCntPop.popup"));
        imsiForm.append($("<input type='hidden' name='schStu'>").val(schStu));
        imsiForm.append($("<input type='hidden' name='ipTpCd'>").val(ipTpCd));
        /*imsiForm.append($("<input type='hidden' name='docUid'>").val(paragonCmm.getRandomUUID()));*/
//        imsiForm.append($("<input type='hidden' name='openerData'>").val(JSON.stringify(openerData)));
        imsiForm.append($("<input type='hidden' name='_csrf'>").val($("meta[name='_csrf']").attr("content")));
        paragonCmm.openWindow("", "1280", "750", "POP_WRITE_" + schStu, "yes", "yes", "");
        imsiForm.attr("target", "POP_WRITE_" + schStu);
        imsiForm.appendTo("body");
        imsiForm.submit();
        imsiForm.remove();
    }
    var loadTtPop = function (schStu) {
        var imsiForm = $("<form method='POST'>").attr("action", paragonCmm.getUrl("/paragon/cmm/ttProcCntPop.popup"));
        imsiForm.append($("<input type='hidden' name='schStu'>").val(schStu));
        /*imsiForm.append($("<input type='hidden' name='docUid'>").val(paragonCmm.getRandomUUID()));*/
//        imsiForm.append($("<input type='hidden' name='openerData'>").val(JSON.stringify(openerData)));
        imsiForm.append($("<input type='hidden' name='_csrf'>").val($("meta[name='_csrf']").attr("content")));
        paragonCmm.openWindow("", "1280", "750", "POP_WRITE_" + schStu, "yes", "yes", "");
        imsiForm.attr("target", "POP_WRITE_" + schStu);
        imsiForm.appendTo("body");
        imsiForm.submit();
        imsiForm.remove();
    }

    var loadStat = function () {



//        };

    };

    var loadCalendar = function () {

    }


    //기한관리 load
    var loadDue = function (pageNo) {

        var loginInfo = paragonCmm.formMap.get("loginInfo");
        if (!loginInfo.isChr && !loginInfo.isSys) { //-- 담당자
            return false;
        }
        if (pageNo == undefined) pageNo = 1;
        var data = paragonCmm.getSearchQueryParams();
        data["page"] = pageNo;
        data["rows"] = "12";


        paragonCmm.callAjax(DUE_URL, data, function (json) {
            for (var i = 0; i < json.data.length; i++) {
                json.data[i].dueTit = paragonCmm.getTrunc(json.data[i].dueTit, 20, "..");
            }
            var data = paragonCmm.easyuiLoadFilter(json);
            var page = "";
            var rowCnt = "";
            htmlUtils.createTableRow(dueTblId, data, page, rowCnt, "dashBoard.loadDue");
        });

    }

    //공지사항popup load
    var loadBoardPopup = function () {
        // var data = paragonCmm.getSearchQueryParams();
        //
        // var loginInfo = paragonCmm.formMap.get("loginInfo");
        //
        // var popupList = [];
        // var popupViewAuth = [];
        // paragonCmm.callAjax(BOARD_URL, data, function (json) {
        //
        //     data = paragonCmm.easyuiLoadFilter(json);
        //     var bbsList = data.rows;
        //     var iLen = bbsList.length;
        //     for (var i = 0; i <= iLen - 1; i++) {
        //         var bbsPopupYn = bbsList[i].bbsPopupYn;
        //         if (bbsPopupYn == "Y") {
        //             popupList.push(bbsList[i].bbsUid);
        //             popupViewAuth.push(bbsList[i].bbsViewAuth);
        //         }
        //     }
        // });
        // var popupLen = popupList.length;
        // if (paragonCmm.getCookie("toDate") != "ok") {
        //
        //     //팝업을 띄울 리스트  : popupList
        //     //popupList.bbsAuth 에 허가되는 권한이 있음.
        //     for (var i = 0; i <= popupLen - 1; i++) {
        //         var openerData = {
        //             isNew: "FALSE",
        //             bbsUid: popupList[i],
        //             openType: "MAIN",
        //             dashBoardChk: "Y"
        //         };
        //
        //
        //         //첫번째 포문 -> BBSVIEWAUTH -> ,
        //         //두번째 포문 -> LOGININFO에서 USER_AUTH -> ,
        //         // 1번이라도 통과가 되며 POPUP을 띄운다.
        //
        //         var authcode = [];
        //         if (loginInfo.authCd.includes(",")) {
        //
        //             authcode = loginInfo.authCd.split(",");
        //         } else {
        //             authcode[0] = loginInfo.authCd;
        //         }
        //         console.log(authcode);
        //         var popupcode = [];
        //
        //         for (var i = 0; i < popupViewAuth.length; i++) {
        //             if (popupViewAuth[i].includes(",")) {
        //                 popupcode = popupViewAuth[i].split(",");
        //             } else if (popupViewAuth[i]) {
        //                 popupcode[i + 1] = popupViewAuth[i];
        //             }
        //         }
        //         console.log(popupcode);
        //
        //
        //         for (var j = 0; j < popupcode.length; j++) {
        //             //    0
        //             for (var k = 0; k < authcode.length; k++) {
        //                 //    0
        //                 if (authcode[j] == popupcode[j]) {
        //                     var imsiForm = $("<form method='POST'>").attr("action", paragonCmm.getUrl("/paragon/bbs/bbsMasView.popup"));
        //                     //imsiForm.append($("<input type='hidden' name='bbsUid'>").val(bbsUid));
        //                     imsiForm.append($("<input type='hidden' name='_csrf'>").val($("meta[name='_csrf']").attr("content")));
        //                     imsiForm.append($("<input type='hidden' name='openerData'>").val(JSON.stringify(openerData)));
        //                     paragonCmm.openWindow("", "1024", "580", "POPUP_VIEW", "yes", "yes", "");
        //                     imsiForm.attr("target", "POPUP_VIEW");
        //                     imsiForm.appendTo("body");
        //                     imsiForm.submit();
        //                     imsiForm.remove();
        //                 }
        //             }
        //         }
        //     }
        // }
    }

    // //공지사항 글 바로가기
    // var goBoardView = function (bbsUid) {
    //     noticeCard.openView(bbsUid);
    // }

    // //기한관리 글 바로가기
    // var goDueView = function (solMasUid, stuCd, stuDtl) {
    //
    //     var openerData = {
    //         isNew: "FALSE",
    //         solMasUid: solMasUid,
    //         dashBoardTodoChk: "Y"
    //     };
    //
    //     var data = {};
    //     data.solMasUid = solMasUid;
    //     data.stuCd = stuCd;
    //     data.stuDtl = stuDtl;
    //     data.openerData = openerData;
    //     paragonIps.goPopupIndex(data);
    //
    // }


    var getNoticeIco = function (txt) {
        return '<i class="fa fa-caret-right"></i> ' + txt;
    }
    var setEvent = function () {

        //-- 바로가기 클릭
        // $(".banner_box").off();
        // $(".banner_box").on("click", function () {
        //     goQuickLink($(this));
        // }).on("mouseover", function () {
        //     $(this).css({"box-shadow": "4px 3px 3px #8f8f8f", "color": "#323232"});
        // }).on("mouseout", function () {
        //     $(this).css({"box-shadow": "", "color": "#818181"});
        // });

        $("#schSolTp").off();
        $("#schSolTp").on("change", function () {
            todoListCard.search();
        });
        // $("#menualDown").off();
        // $("#menualDown").on("click", function () {
        //     var fileNm = $(this).data("url");
        //     htmlUtils.formDownload(fileNm);
        // });
    }


    // ==============================================
    // public functions
    // ----------------------------------------------
    var init = function () {

        initForm();

        loadCards();

        // loadDue();
        loadBoardPopup();
        // loadStat();
        // loadCalendar();
        setEvent();

        paragonCmm.convertLang($(document));

    }
    return {
        init: init,
        // initForm: initForm,
        loadPop: loadPop,
        // loadTtPop: loadTtPop,
        // loadCalendar: loadCalendar,
        loadBoard: function(){
            noticeCard.init();
        },
        // goDueView: goDueView,
        // loadBoardPopup: loadBoardPopup,
        goBoardView: function(bbsUid){
            noticeCard.openView(bbsUid);
        },
        getNoticeIco: getNoticeIco
    }
})();

$(function(){
    "use strict";
    dashBoard.init();
});
