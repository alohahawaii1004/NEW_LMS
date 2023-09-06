/*
 * @(#)systemList.js     2023-03-14 014 오후 1:52:22
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

var SystemListModel = function(){
    "use strict";
    const LIST_URL = paragonCmm.getUrl("/support/system/systemMng/listPerPage/json");
    const WRITE_URL = paragonCmm.getUrl("/support/system/systemWrite.popup");
    const VIEW_URL = paragonCmm.getUrl("/support/system/systemView.popup");

    return {
        getListUrl: function(){
            return LIST_URL;
        },
        getWriteUrl: function(){
            return WRITE_URL;
        },
        getViewUrl: function(){
            return VIEW_URL;
        }
    };
};

var SystemList = function(){
    "use strict";
    // ==============================================
    // attributes
    // ----------------------------------------------
    const OBJ_ID = "smSysMng";
    let model = new SystemListModel();
    let $rootNode = $("#smSysMngRootLayer");
    let $schForm = $("#smSysMngSchFrm0", $rootNode);
    let $grid = $("#smSysMngList1", $rootNode);
    let $schPrdTpCombo = $("select[name=schPrdTpCd]", $schForm);
    // ==============================================
    // private functions
    // ----------------------------------------------
    let initSchObj = function(){

        // init combo
        // htmlUtils.getCodeSelectOpt({
        //     targetId: $schPrdTpCombo,
        //     parentCd: "SD_SYSTEM_GBN",
        //     initdata: "|" + "Select System Type",
        //     valNm: "cdAbb",
        //     txtNm: "langCd"
        // });

        $schPrdTpCombo.combobox({
            mode: 'remote',
            valueField:'value',
            textField:'label',
            prompt: "System Type",
            loader: function (param, succ) {
                let sField = "LANG_CD";
                if (!param.q) {
                    sField = "";
                    // return;
                }

                $.ajax({
                    url: paragonCmm.getUrl("/viself/code/CodeMng/listCode/json"),
                    data: {
                        schField: sField,
                        schValue: param.q,
                        parentCd: "SD_SYSTEM_GBN"
                    },
                    dataType: 'json',
                    success: function (data) {
                        var rows = $.map(data.data, function (item) {
                            return {
                                label: item.langCd,
                                value: item.cd,
                                code: item.cdAbb
                            };
                        });
                        succ(rows);
                    }
                })
            }
        });

        // 지원일
        $("input[name=schFromSmDte]", $schForm).datebox({validType: "validDate", prompt: "Input Support Date"});// easyui
        $("input[name=schToSmDte]", $schForm).datebox({validType: "validDate", prompt: "Input Support Date"});// easyui
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

    let openView = function(row){

        let systemId = (paragonCmm.isNotEmpty(row))? row.systemId : "";
        let targetName = "SYS_INFO_"+systemId;
        let url = model.getViewUrl();
        if(paragonCmm.isEmpty(systemId)){
            url = model.getWriteUrl();
        }

        htmlUtils.openPopup({
            url: url,
            targetName: targetName,
            popupTitle: "시스템정보",
            popWidth: 1152,
            popHeight: 700,
            openerData: {
                cbKey: OBJ_ID,
                systemId: systemId
            },
            params: null
        });
    };

    var useYnStyler = function(value,row,index){
        var style = "background-color: #f2dede;color: #a94442;";
        if (value === "Y"){
            style = 'background-color: #dff0d8;color: #3c763d;';
        }
        return style;
    };

    var expYnStyler = function(value,row,index){
        var style = "";
        if (row.expYn === "Y"){
            style = 'background-color: #f2dede;color: #a94442;';
        }
        return style;
    };

    let initGrid = function(){
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
                {field: "systemId", title: paragonCmm.getLang("L.아이디"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "8%" }
                , { field: "orgNm", title: paragonCmm.getLang("L.회사명"), formatter: filterXSS, halign: "CENTER", align: "LEFT", width: "15%" }
                , { field: "systemName", title: paragonCmm.getLang("L.시스템명"), formatter: filterXSS, halign: "CENTER", align: "LEFT", width: "20%" }
                , { field: "productTpNm", title: paragonCmm.getLang("L.시스템구분"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "15%" }
                , { field: "smStDte", title: paragonCmm.getLang("L.지원시작일"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "8%" }
                , { field: "smEdDte", title: paragonCmm.getLang("L.지원종료일"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "8%", styler: expYnStyler }
                , { field: "SM_ED_DTE", title: paragonCmm.getLang("L.담당자"), formatter: filterXSS, halign: "CENTER", align: "LEFT", width: "26%" }
            ]],
            onDblClickRow: function (index, row) {
                openView(row);
            }
        });
    };

    let search = function(){
        $grid.datagrid("load", getQueryParams());
    };

    let addSystem = function(){
        openView();
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
        $rootNode.off("click", "button");
        $rootNode.on("click", "button", function(){
            var btnId = $(this).attr("id");
            switch (btnId){
                case "smSysMngResetSmBtn":
                case "smSysMngResetBtn1":
                    $schForm[0].reset();
                    break;
                case "smSysMngSearchSmBtn":
                case "smSysMngSearchBtn1":
                    search();
                    break;
                case "smSysMngAddBtn1":
                    addSystem();
                    break;
                default:
                    console.warn("not support btnId:"+btnId);
                    break;
            }

            return false; // 이벤트 확산금지
        });
    };

    let initForm = function(){
        initSchObj();
        initGrid();
    };
    let loadForm = function(){};
    let setEvent = function(){
        setFormEvent();
        setBtnEvent();
    };
    let setCbFnc = function(){
        var cbFnc = paragonCmm.getCbFnc(OBJ_ID);
        if (typeof cbFnc != "function") {
            paragonCmm.setCbFnc(OBJ_ID, search);
        }
    };
    // ==============================================
    // public functions
    // ----------------------------------------------
    let init = function(){
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
    let systemList = new SystemList();
    systemList.init();
});