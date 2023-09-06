/*
 * @(#)moduleMng.js     2021-03-16(016) 오전 8:54
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

/*
 모듈-URL 관리
 */
var ModuleMngCommon = function(){
    "use strict";
    var getGridHeight = function(){
        var ENURI = 240;
        var SCH_HEIGHT = 0; //$searchFrm.innerHeight();
        var windowHeight = window.innerHeight;
        return windowHeight - SCH_HEIGHT - ENURI;
    };

    var getPageSize = function(){
        var gHeight = getGridHeight();

        // console.log(gHeight);
        if(gHeight > 500){
            return 20;
        }
        return 10;
    };


    var initModal = function(modalId){

        modalId = (typeof modalId === "undefined")? "modalPop" : modalId;

        var $dialog = document.getElementById(modalId);
        if(!$dialog){
            var DIALOG_HTML = "<div id='"+modalId+"'/>"
            $("body").append(DIALOG_HTML);
        }
        $dialog = $("#"+modalId);
        $dialog.html("");

        return $dialog;
    };

    return {
        getGridHeight: getGridHeight,
        initModal: initModal,
        getPageSize: getPageSize
    };
};

/**
 * 모듈 URL 모델
 */
var ModuleUrlViewModel = function(){

    var MODULE_URL_SEARCH_URL = paragonCmm.getUrl("/viself/module/moduleMng/moduleUrlList/json");
    var URL_LIST_DIALOG_URL = paragonCmm.getUrl("/viself/module/urlListDialog.include");
    var DELETE_URL = paragonCmm.getUrl("/viself/module/moduleMng/deleteModuleUrls/json");
    var MODULE_URL_SAVE_URL = paragonCmm.getUrl("/viself/module/moduleMng/saveModuleUrl/json");

    var searchUrl = function(param, cbFnc){
        paragonCmm.callAjax(MODULE_URL_SEARCH_URL, param, cbFnc);
    };

    var deleteUrls = function(param, cbFnc){
        paragonCmm.callAjax(DELETE_URL, param, cbFnc);
    };

    var saveUrl = function(param, cbFnc){
        paragonCmm.callAjax(MODULE_URL_SAVE_URL, param, cbFnc);
    };

    var getUrlDialogUrl = function(){
        return URL_LIST_DIALOG_URL;
    };

    return {
        searchUrl: searchUrl,
        deleteUrls: deleteUrls,
        saveUrl: saveUrl,
        getUrlDialogUrl: getUrlDialogUrl
    };
};

/**
 * 모듈 URL 관리
 * @returns {{init: init, search: search}}
 * @constructor
 */
var ModuleUrlMng = function(){
    "use strict";

    var $rootDom = $("#moduleMngLayer2");
    var $urlGrid = $("#moduleMngList2");
    var $searchFrm = $("#moduleMngSchFrm2");
    var $urlListModuleId = $searchFrm.find("input[name=moduleId]");
    var mCmm = new ModuleMngCommon();
    var vModel = new ModuleUrlViewModel();

    var setMaster = function(row){
        vModel.saveUrl({
            cud: "U",
            moduleId: row.moduleId,
            accesUrl: row.accesUrl
        }, function(data){
            htmlUtils.showMsg(data)
            search();
        });
    };

    var getQueryParams = function(moduleId){

        if(typeof moduleId !== "undefined"){
            $urlListModuleId.val(moduleId);
        }

        return $searchFrm.serializeObject();
    };

    var initGrid = function(){
        $urlGrid.datagrid({
            url: "",
            method: "post",
            // queryParams: getQueryParams(),
            loadFilter: paragonCmm.easyuiLoadFilter,
            height: mCmm.getGridHeight(),
            striped : true,
            fitColumns : true,
            nowrap: true,
            multiSort : true,
            remoteSort: true,
            rownumbers: true,
            singleSelect: true,
            checkOnSelect: false,
            selectOnCheck: false,
            pagination : true,
            pagePosition: "bottom",
            pageSize: mCmm.getPageSize(),
            columns:[[{
                field: "chk", checkbox: true, width: "1%", toExcel: false
            },{
                field: "moduleId", formatter: filterXSS, hidden:true
            },{
                field: "accesUrl", title: "URL", formatter: filterXSS, width: "40%"
            }, {
                field: "urlDesc", title: "Description", formatter: filterXSS, width: "48.5%"
            }, {
                field: "repreUrlYn", title: "Master", formatter: filterXSS, width: "9%"
            }]],
            onDblClickRow: function(index, row){
                if(row.repreUrlYn === "N"){
                    // 마스터 설정
                    setMaster(row);
                }
            }
        });
    };

    var search = function(moduleId){
        vModel.searchUrl(getQueryParams(moduleId), function(data){
            $urlGrid.datagrid("loadData", data);
        });
    };

    var openAddUrlDialog = function(){

        var params = getQueryParams();

        if(params.moduleId === ""){
            return false;
        }

        var $dialog = mCmm.initModal();

        var dialogId = $dialog.attr("id");

        $dialog.dialog({
            iconCls: "fa fa-info-circle",
            title: "URL Info.",
            width: 576,
            height: 525,
            method: "post",
            cache: false,
            modal: true,
            href: vModel.getUrlDialogUrl(),
            // maximizable: true,
            // maximized: true,
            queryParams: {
                openerData: JSON.stringify(getQueryParams())
            },
            onClose: function(){
                // HACK 이벤트를 지우지 않으면 계속 쌓인다.
                // 1. HTML 지우기
                $dialog.dialog("destroy");
                // 2. 이벤트 지우기
                $("#"+dialogId+" *").off();
            },
            onLoad: function(){
                // 1. 가운데 열기
                $dialog.dialog("center");

                // 2. 다국어 설정
                paragonCmm.convertLang($dialog);

                // 3. call-back 설정
                // include 페이지 최상위 DOM id
                var $DIALOG_ROOT_NODE = $("#moduleMngPop2Frm0");
                $DIALOG_ROOT_NODE.data("callback", function(isSchFlag){
                    if(isSchFlag){
                        search();
                    }
                    $dialog.dialog("close");
                });
            }
        });
    };

    var deleteUrls = function (){
        var rows = $urlGrid.datagrid("getChecked");
        if(!rows || rows.length === 0){
            return;
        }
        var param = $.extend({},getQueryParams(),{
            list: rows
        });
        $.messager.confirm("Confirm",'정말 삭제하시겠습니까?',function(r) {
            if(r){
                var cbFnc = function(data){
                    htmlUtils.showMsg(data);
                    search();
                }

                vModel.deleteUrls(param, cbFnc);
            }
        });
    };

    var initForm = function(){
        initGrid();
    };

    var setEvent = function(){
        $rootDom.find("button").off();
        $rootDom.on("click", "button", function(){
            var btnId = $(this).attr("id");
            switch (btnId){
                case "moduleMngAddRowBtn2":
                    openAddUrlDialog();
                    break;
                case "moduleMngDeleteRowBtn2":
                    deleteUrls();
                    break;
                default:
                    break;
            }
            return false;
        });
    };

    var init = function(){
        // 1. init form
        initForm();

        // 2. load data
        // 3. set event
        setEvent();
    };

    return {
        init: init,
        search: search
    };
};

var ModuleMngViewModel = function(){
    "use strict";

    // 모듈 조회
    var MODULE_SEARCH_URL = "/viself/module/moduleMng/moduleList/json";

    // 모듈 다이얼로그 URL
    var MODUEL_DIALOG_URL = "/viself/module/moduleDialog.include";

    var getModuleListUrl = function(){
        return paragonCmm.getUrl(MODULE_SEARCH_URL);
    };

    var getModuleDialogUrl = function(){
        return paragonCmm.getUrl(MODUEL_DIALOG_URL);
    };

    return{
        getModuleListUrl: getModuleListUrl,
        getModuleDialogUrl: getModuleDialogUrl
    };
};

/**
 * 모듈 관리
 * @returns {{init: init}}
 * @constructor
 */
var ModuleMng = function(){
    "use strict";

    var MODULE_NAME = "moduleMng";
    var MODULE_ID = "#"+MODULE_NAME;
    var $rootDom = $(MODULE_ID+"Layer1");
    var $moduleGrid = $(MODULE_ID + "List1");
    var $searchFrm = $(MODULE_ID + "SchFrm0");

    var mCmm = new ModuleMngCommon();
    var moduleUrlMng = null;
    var model = new ModuleMngViewModel();

    var openModuleInfo = function(moduleData){
        // 1. init modal
        var $dialog = mCmm.initModal();

        // 2. call-back 설정
        $dialog.data("callback", function(isSchFlag){
            if(isSchFlag){
                searchModule();
            }
            $dialog.dialog("close");
        });

        // 2. open modal
        $dialog.dialog({
            iconCls: "fa fa-info-circle",
            title: "Module Info.",
            width: 576,
            height: 200,
            method: "post",
            cache: false,
            modal: true,
            href: model.getModuleDialogUrl(),
            // maximizable: true,
            // maximized: true,
            queryParams: {
                modalId: $dialog.attr("id"), // call-back 을 얻기위해 id 전달
                openerData: JSON.stringify(moduleData)
            },
            onClose: function(){
                // HACK 이벤트를 지우지 않으면 계속 쌓인다.
                // 1. HTML 지우기
                $dialog.dialog("destroy");
                // 2. 이벤트 지우기
                $("#moduleMngPop1 *").off();
            },
            onLoad: function(){
                // 1. 가운데 열기
                $dialog.dialog("center");

                // 2. 다국어 설정
                paragonCmm.convertLang($dialog);
            }
        });
    };

    var searchModuleUrl = function(moduleId){
        if(moduleUrlMng === null){
            initModuleList();
        }
        moduleUrlMng.search(moduleId);
    };

    var getQueryParams = function(){
        // return $searchFrm.serializeObject();
        return null;
    };

    var initGrid = function(){

        $moduleGrid.datagrid({
            url: model.getModuleListUrl(),
            method: "post",
            queryParams: getQueryParams(),
            loadFilter: paragonCmm.easyuiLoadFilter,
            height: mCmm.getGridHeight(),
            striped : true,
            fitColumns : true,
            nowrap: true,
            multiSort : true,
            remoteSort: true,
            rownumbers: true,
            singleSelect: true,
            checkOnSelect: true,
            selectOnCheck: true,
            pagination : true,
            pagePosition: "bottom",
            pageSize: mCmm.getPageSize(),
            columns:[[{
                field: "chk", checkbox: true, width: "1%", toExcel: false
            },{
                field: "moduleId", title: "Module", formatter: filterXSS, width: "40%"
            }, {
                field: "moduleDesc", title: "Description", formatter: filterXSS, width: "48.5%"
            }, {
                field: "useYn", title: "Use", formatter: filterXSS, width: "10%"
            }]],
            onLoadSuccess:function(data){
                console.log(data);
            },
            onDblClickRow: function(index, row){
                // 다이얼로그 오픈
                openModuleInfo(row);
            },
            onSelect: function(index, row){
                // 관련 URL 조회
                searchModuleUrl(row.moduleId);
            }
        });
    };

    var searchModule = function(){
        $moduleGrid.datagrid("load", getQueryParams());
    };

    var initModuleList = function(){
        moduleUrlMng = new ModuleUrlMng();
        moduleUrlMng.init();
    };

    var initForm = function(){
        initModuleList();
        initGrid();
    };

    var setEvent = function(){
        $rootDom.find("button").off();
        $rootDom.on("click", "button", function(){
            var btnId = $(this).attr("id");
            switch (btnId){
                case "moduleMngAddRowBtn1":
                    openModuleInfo();
                    break;
                default:
                    break;
            }
            return false;
        });
    };

    var init = function(){
        // init form
        initForm();
        // load data

        // set event
        setEvent();
    };

    return {
        init: init
    };
};

$(document).ready(function(){
    "use strict";
    var moduleMng = new ModuleMng();
    moduleMng.init();
});
