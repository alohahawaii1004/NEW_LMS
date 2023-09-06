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


var UrlMng = function () {

    "use strict";

    var model = (function () {
        var URL_SELECT = paragonCmm.getUrl("/viself/auth/url-mng/url-list/json");
        var SAVE_URL = paragonCmm.getUrl("/viself/auth/url-mng/save/json");
        var DELETE_URL = paragonCmm.getUrl("/viself/auth/url-mng/delete/json");

        var code = {
            useYnOptions: [
                {id: "Y", value: "Use"},
                {id: "N", value: "No Use"}
            ],
            alwDivOptions: [
                {id: "IS_AUTHENTICATED_FULLY", value: "Authenticated"},
                {id: "IS_AUTHENTICATED_ANONYMOUSLY", value: "Anonymously"}
            ],
        };

        var getGridUrl = function(){
            return URL_SELECT;
        };

        var saveData = function(options){
            var opts = $.extend({
                url: SAVE_URL,
                param: null,
                cbFnc: function(json){}
            },options);
            paragonCmm.callAjax(opts.url, opts.param, opts.cbFnc);
        };

        // URL 삭제
        var deleteData = function(options){
            var opts = $.extend({
                url: DELETE_URL,
                param: null,
                cbFnc: function(json){}
            },options);
            paragonCmm.callAjax(opts.url, opts.param, opts.cbFnc);
        }

        var getAlwDivOptions = function(){
            return code.alwDivOptions.slice();
        };

        var getUseYnOptions = function(){
            return code.useYnOptions.slice();
        };

        return{
            getGridUrl: getGridUrl,
            saveData: saveData,
            deleteData: deleteData,
            getAlwDivOptions: getAlwDivOptions,
            getUseYnOptions: getUseYnOptions
        };
    })();

    var $rootDom = $("#urlMngRootLayer");
    var $grid = $("#urlMngList1");
    var $searchFrm = $("#urlMngSchFrm0");
    var $btnGrp1 = $(".main-btn-grp1");
    var $btnGrp2 = $(".main-btn-grp2");
    // var $dialog = $("#urlMngPop1");
    // var DIALOG_URL = "/viself/auth/url-dialog.include";

    var initSchForm = function(){
        var alwOptions = model.getAlwDivOptions();
        var useYnOptions = model.getUseYnOptions();

        alwOptions.unshift({id:"", value: "Access Type"});
        useYnOptions.unshift({id:"", value: "Use Y/N"});

        htmlUtils.initializeSelectJson($("#urlMngSelect1"), alwOptions, "", "id", "value");
        htmlUtils.initializeSelectJson($("#urlMngSelect2"), useYnOptions, "", "id", "value");
    };

    var addRow = function(){
        var comboObjs = $(".combo-p");
        if(comboObjs.length > 0){
            $(comboObjs).each(function(){
                $(this).find(".combo-panel").panel('destroy');
            });
        }
        $grid.edatagrid("addRow", {
            // index: 0,
            row: {cud: "C"}
        });

        showSaveBtnGrp();
    };

    var getGridHeight = function () {
        var ENURI = 320;
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

    var getQueryParams = function () {
        return $searchFrm.serializeObject();
    };

    var rowStyler = function(index, row){
        if (row.cud === "C" || row.isNewRecord){
            return "background-color:#6293BB;color:#fff;font-weight:bold;";
        }
    };

    var initGrid = function () {
        $grid.edatagrid({
            url: model.getGridUrl(),
            method: "post",
            queryParams: getQueryParams(),
            loadFilter: paragonCmm.easyuiLoadFilter,
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
            emptyMsg: "NO DATA",
            // rowStyler: rowStyler,
            columns: [[{
                field: "chk", checkbox: true, width: "1%", toExcel: false
            },{
                field: "cud", hidden: true
            }, {
                field: "accesUrl", title: "URL", formatter: filterXSS, width: "40%",
                editor: {
                    type: "validatebox",
                    options: {
                        required:true
                    }
                }
            }, {
                field: "urlDesc", title: "Description", formatter: filterXSS, width: "40%",
                editor: {
                    type: "validatebox",
                    options: {
                        validType: ["length[0, 20]"]
                    }
                }
            }, {
                field: "alwDiv", title: "Access Type", formatter: filterXSS, width: "15%",
                editor: {
                    type: "combobox",
                    options: {
                        editable: false,
                        data: model.getAlwDivOptions(),
                        valueField: "id",
                        textField: "value",
                        keyHandler: $.extend({}, $.fn.combobox.defaults.keyHandler, {
                            down: function(e){
                                $(this).combobox('showPanel');
                                $.fn.combobox.defaults.keyHandler.down.call(this,e);
                            }
                        }),
                        onShowPanel:function(){
                            $(this).combobox('panel').panel('panel').css('z-index',$.fn.menu.defaults.zIndex++);
                        },
                        required:true
                    }
                }
            }, {
                field: "useYn", title: "Use", formatter: filterXSS, width: "5%",
                editor: {
                    type: "combobox",
                    options: {
                        editable: false,
                        data: model.getUseYnOptions(),
                        valueField: "id",
                        textField: "value",
                        keyHandler: $.extend({}, $.fn.combobox.defaults.keyHandler, {
                            down: function(e){
                                $(this).combobox('showPanel');
                                $.fn.combobox.defaults.keyHandler.down.call(this,e);
                            }
                        }),
                        onShowPanel:function(){
                            $(this).combobox('panel').panel('panel').css('z-index',$.fn.menu.defaults.zIndex++);
                        },
                        required:true
                    }
                }
            }]]
        });
    };

    var setGridEvent = function(){
        var options = $grid.edatagrid("options");

        options.onAdd = function(index, row){
            var ed1 = $(this).edatagrid("getEditor", {
                index: index,
                field: "accesUrl"
            });

            $(ed1.target).focus();
            // 아래는 콤보박스일경우
            // var t = $(ed1.target).combobox('textbox').focus();
            // t.focus();
        };

        options.onBeforeEdit= function(index, row){
            if(!row.isNewRecord){
                var col = $(this).edatagrid("getColumnOption","accesUrl");
                col.editor1 = col.editor;
                col.editor = null;
                row.cud = "U";
            }
            showSaveBtnGrp();
        };
        options.onAfterEdit=function(){
            // console.log("after edit");
            var col = $(this).edatagrid('getColumnOption','accesUrl');
            if (col.editor1){
                col.editor = col.editor1;
            }
        };
        options.onCancelEdit=function(){
            var col = $(this).edatagrid('getColumnOption','accesUrl');
            if (col.editor1){
                col.editor = col.editor1;
            }
        };
    };

    var search = function(){
        $grid.datagrid("load", getQueryParams());
    };

    var resetForm = function(){
        $searchFrm[0].reset();
        // $searchFrm.find("input[name=schWord]").val("");
    };

    var delUrls = function(){
        var rows = $grid.datagrid("getSelections");

        console.log(rows);

        if(!rows || rows.length === 0){
            $.messager.alert("Warning", "삭제할 대상을 선택해 주세요.");
            return false;
        }

        $.messager.confirm("Confirm",'정말 삭제하시겠습니까?\n시스템에 문제가 발생할 수 있습니다.',function(r) {
            if(r){
                model.deleteData({
                    param: {
                        list: rows
                    },
                    cbFnc: function(data){
                        htmlUtils.showMsg(data);
                        search();
                    }
                });
            }
        });
    };

    var saveUrl = function(){

        $grid.edatagrid("saveRow");
        var rows = $grid.edatagrid('getChanges');

        var chkValidate = function(row){
            if(typeof row.accesUrl === "undefined"){
                $.messager.alert("Warning","필수 값이 없습니다.", "warning");
                return false;
            }

            if(typeof row.accesUrl === "undefined" || row.accesUrl === ''){
                $.messager.alert("Warning","URL 값이 없습니다.", "warning");
                return false;
            }
            if(typeof row.alwDiv === "undefined" || row.alwDiv === ''){
                $.messager.alert("Warning","Access Type 값이 없습니다.", "warning");
                return false;
            }
            if(typeof row.useYn === "undefined" || row.useYn === ''){
                $.messager.alert("Warning","Use 값이 없습니다.", "warning");
                return false;
            }
            return true;
        };

        if(rows[0] && chkValidate(rows[0])){
            $.messager.confirm("Confirm",'정말 저장하시겠습니까?',function(r) {
                if(r){
                    model.saveData({
                        param: {
                            list: rows
                        },
                        cbFnc: function(data){
                            htmlUtils.showMsg(data);
                            search();
                            showBaseBtnGrp();
                        }
                    });
                }
            });
        }else{
            showBaseBtnGrp();
        }
    };

    /**
     * 엑셀 출력
     */
    var toExcel = function(){
        $grid.edatagrid("toExcel", {
            filename: "url-list.xls",
            worksheet: "url list",
            caption: "URL List"
        });
    };

    var showBaseBtnGrp = function(){
        $btnGrp1.show();
        $btnGrp2.hide();
    };
    var showSaveBtnGrp = function(){
        $btnGrp2.show();
        $btnGrp1.hide();
    };

    var cancelEdit = function(){
        $grid.edatagrid("cancelRow");
        //var rows = $grid.edatagrid('getChanges');
        $grid.edatagrid("reload");
    };

    var setEvent = function(){

        setGridEvent();
        $rootDom.find("button").off();
        $rootDom.on("click", "button", function(){
            var btnId = $(this).attr("id");
            switch(btnId){
                case "urlMngSearchBtn1":
                case "urlMngSearchSmBtn":
                    search();
                    break;
                case "urlMngResetBtn1":
                case "urlMngResetSmBtn":
                    resetForm();
                    break;
                case "urlMngAddRowBtn1":
                    addRow();
                    break;
                case "urlMngDeleteRowBtn1":
                    delUrls();
                    break;
                case "urlMngAcceptBtn1":
                    saveUrl();
                    break;
                case "urlMngCancelBtn1":
                    cancelEdit();
                    showBaseBtnGrp();
                    break;
                case "urlMngExcelBtn1":
                    toExcel();
                    break;
                default:
                    break;
            }
            return false;
        });

        $searchFrm.on("submit", function(){
            search();
            return false;
        });

        // var options = $grid.edatagrid("options");

    };

    var init = function () {
        initSchForm();

        // 그리드 초기화
        initGrid();

        // 이벤트 설정
        setEvent();
        showBaseBtnGrp();
    };

    return {
        init: init
    };
};

$(document).ready(function () {

    console.info("[Loading Module: URL관리].....................");

    var urlMngApp = new UrlMng();
    urlMngApp.init();
});