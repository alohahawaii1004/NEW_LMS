/*
 * @(#)urlMng.js     2021-03-31(031) 오후 12:30
 *
 * Copyright 2021 JAYU.space
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
 * URL 관리 - 다이얼로그 버전
 * @returns {{init: init}}
 * @constructor
 */
var UrlMng = function () {

    var model = (function () {
        var URL_SELECT = "/viself/auth/url-mng/url-list/json";
        var SAVE_URL = "/viself/auth/url-mng/save/json";
        var DELETE_URL = "/viself/auth/url-mng/delete/json";

        var getGridUrl = function(){
            return paragonCmm.getUrl(URL_SELECT);
        };


        var saveData = function(data, cbFnc){
            console.log("URL..."+SAVE_URL);
            console.log("data..."+data);
            paragonCmm.callAjax(paragonCmm.getUrl(SAVE_URL), data, cbFnc);
        };

        // URL 삭제
        var deleteData = function(data, cbFnc){
            paragonCmm.callAjax(paragonCmm.getUrl(DELETE_URL), data, cbFnc);
        }

        return{
            getGridUrl: getGridUrl,
            saveData: saveData,
            deleteData: deleteData
        };
    })();

    var $grid = $("#urlMngList1");
    var $searchFrm = $("#urlMngSchFrm0");
    var $searchBtn = $("#urlMngSearchBtn1");
    var $resetBtn = $("#urlMngResetBtn1");
    var $addBtn = $("#urlMngAddRowBtn1");
    var $delBtn = $("#urlMngDeleteRowBtn1");
    var $excelBtn = $("#urlMngExcelBtn1");
    var $dialog = $("#urlMngPop1");
    var DIALOG_URL = "/viself/auth/url-dialog.include";

    /**
     * easyui 용 기본 loadFilter
     * @param data json data from server
     * @returns {*}
     */
    var loadFilter = function (data) {
        var rtnData = {};
        rtnData.rows = (data.data) ? data.data : [];
        if (data.data && data.data.length > 0) {
            rtnData.total = data.data[0].totCnt;
        }
        return rtnData;
    };

    var openDialog = function(row){
        $dialog.html("");
        var openerData = {
            row: row,
            model: model,
            cbFnc: search
        };
        $dialog.removeData("openerData");
        $dialog.data("openerData", openerData);
        $dialog.load(DIALOG_URL, null, function(res, sts, xhr){
        });
    };

    var getGridHeight = function () {
        var ENURI = 130;
        var SCH_HEIGHT = $searchFrm.innerHeight();
        var windowHeight = window.innerHeight;
        var gridHeight = windowHeight - SCH_HEIGHT - $("header").innerHeight() - $("h3").innerHeight() - ENURI;
        return gridHeight;
    };

    var getPageSize = function(){
        var gHeight = getGridHeight();

        console.log(gHeight);
        if(gHeight > 500){
            return 20;
        }
        return 10;
    };

    var getQueryParams = function () {
        var params = $searchFrm.serializeObject();
        return params;
    };

    var initGrid = function () {
        $grid.datagrid({
            url: model.getGridUrl(),
            method: "post",
            queryParams: getQueryParams(),
            loadFilter: loadFilter,
            height: getGridHeight(),
            striped: true,
            fitColumns: true,
            nowrap: true,
            multiSort: true,
            remoteSort: true,
            rownumbers: true,
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            pagination: true,
            pagePosition: "bottom",
            pageSize: getPageSize(),
            columns: [[{
                field: "chk", checkbox: true, width: "1%", toExcel: false
            }, {
                field: "accesUrl", title: "URL", formatter: filterXSS, width: "40%"
            }, {
                field: "urlDesc", title: "Description", formatter: filterXSS, width: "40%"
            }, {
                field: "alwDiv", title: "Access Type", formatter: filterXSS, width: "15%"
            }, {
                field: "useEnable", title: "Use", formatter: filterXSS, width: "5%"
            }]],
            onDblClickRow: function (index, row) {
                console.log("onDblClickRow.....");
                openDialog(row);
            }
        });
    };

    var search = function(){
        $grid.datagrid("load", getQueryParams());
    };

    var delUrls = function(){
        var rows = $grid.datagrid("getSelections");

        console.log(rows)
        if(!rows){
            return false;
        }

        $.messager.confirm("Confirm",'정말 삭제하시겠습니까?\n시스템에 문제가 발생할 수 있습니다.',function(r) {
            if(r){
                var data = {
                    list: rows
                };
                var cbFnc = function(){
                    search();
                };
                model.deleteData(data, cbFnc);
            }
        });
    };

    /**
     * 엑셀 출력
     */
    var toExcel = function(){
        $grid.datagrid("toExcel", {
            filename: "url-list.xls",
            worksheet: "url list",
            caption: "URL List"
        });
    };

    var setEvent = function(){

        $searchBtn.on("click", function(e){
            search();
            return false;
        });

        // $resetBtn.on("click", function(e){
        //     return false;
        // });

        $addBtn.on("click", function(e){
            openDialog();
            return false;
        });

        $delBtn.on("click", function(e){
            delUrls();
            return false;
        });

        $excelBtn.on("click", function(e){
            toExcel();
            return false;
        });

        $searchFrm.on("submit", function(e){
            search();
            return false;
        });
    };

    var init = function () {
        // 그리드 초기화
        initGrid();

        // 이벤트 설정
        setEvent();
    };

    return {
        init: init
    };
};

$(document).ready(function () {
    "use strict";
    console.info("[Loading Module: URL관리].....................");

    var urlMngApp = new UrlMng();
    urlMngApp.init();
}());