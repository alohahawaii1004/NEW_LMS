/*
 * @(#)sysMgrList.js     2023-03-29 오후 12:03
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
$(function(){
    "use strict";

    let SysMgrListModel = function(){

        const MGR_LIST_URL = paragonCmm.getUrl("/support/system/sysMgrMng/listPerPage/json");
        const ADD_MGR_URL = paragonCmm.getUrl("/support/system/sysMgrMng/insert/json");

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

        let submitAjax = function(options, url){
            let opts = getOpts(options, url);
            paragonCmm.submitAjax(opts.url, opts.params, opts.cbFnc, opts.async, opts.errorBack);
        };

        return {
            getListUrl: function(){
                return MGR_LIST_URL;
            },
            addManager: function(options){
                callAjax(options, ADD_MGR_URL);
            }
        };
    };

    let SysMgrList = function(){
        // ==============================================
        // attributes
        // ----------------------------------------------
        let model = new SysMgrListModel();
        let $rootNode = $("#sysMgrListRootLayer");
        let $schForm = $("#sysMgrListSchFrm0", $rootNode);
        let $grid = $("#sysMgrListList1", $rootNode);
        let openerData = $.extend({},$rootNode.data("opener-data"));

        // ==============================================
        // private functions
        // ----------------------------------------------
        let closeForm = function (data) {
            let $modal = $("#" + openerData.modalId);
            let cbFncClose = $modal.data("callback");
            if (typeof cbFncClose === "function") {
                // 모달일 경우 닫기
                cbFncClose(data);
            } else {
                // 팝업일 경우 닫기
                self.close();
            }
        };

        let getQueryParams = function(){
            return $.extend(openerData, $schForm.serializeObject());
        }

        let initGrid = function(){
            $grid.datagrid({
                url: model.getListUrl(),
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
                height: 400,
                singleSelect: false,
                checkOnSelect: true,
                selectOnCheck: true,
                columns: [[
                    {field: "chk", checkbox: true, width: "1%", toExcel: false}
                    , {field: "loginId", title: paragonCmm.getLang("L.아이디"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "24%" ,sortable: true}
                    , { field: "mgrNm", title: paragonCmm.getLang("L.이름"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "30%" ,sortable: true}
                    , { field: "mainCnt", title: paragonCmm.getLang("L.주담당"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "15%" ,sortable: true}
                    , { field: "subCnt", title: paragonCmm.getLang("L.부담당"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "15%" ,sortable: true}
                    , { field: "ttlCnt", title: paragonCmm.getLang("L.총계"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "15%" ,sortable: true}
                ]]
            });
        };

        let search = function(){
            $grid.datagrid("load", getQueryParams());
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

        let addManager = function(){

            let rows = $grid.datagrid("getChecked");

            if(!rows.length){
                return false;
            }

            $.messager.confirm("Confirm",'정말 추가하시겠습니까?',function(r){
                if (r){
                    let mgrs = JSON.parse(JSON.stringify(rows));
                    mgrs.forEach(function(itm){
                        itm.systemId = openerData.systemId;
                    });
                    model.addManager({
                        params: {
                            list: mgrs
                        },
                        cbFnc: function(json){
                            if (json.errYn === "E") {
                                //-- 오류처리
                                $.messager.alert("Error", json.msg,"error");
                                return false;
                            } else  {
                                if(typeof json.data === "undefined"){
                                    json.data = {};
                                }
                                closeForm(json.data);
                            }
                        }
                    });
                }
            });
        };

        let setBtnEvent = function(){
            $rootNode.off("click", "button");
            $rootNode.on("click", "button", function(){
                var btnId = $(this).attr("id");
                switch (btnId){
                    case "sysMgrListSearchSmBtn":
                    case "sysMgrListResetBtn1":
                        $schForm[0].reset();
                        break;
                    case "sysMgrListSearchSmBtn":
                    case "sysMgrListSearchBtn1":
                        search();
                        break;
                    case "sysMgrListCloseBtn1":
                        closeForm();
                        break;
                    case "sysMgrListAddBtn1":
                        addManager();
                        break;
                    default:
                        console.warn("not support btnId:"+btnId);
                        break;
                }

                return false; // 이벤트 확산금지
            });
        };

        let initForm = function(){
            initGrid();
        };
        let setEvent = function(){
            setFormEvent();
            setBtnEvent();
        };

        // ==============================================
        // public functions
        // ----------------------------------------------
        let init = function(){
            initForm();
            setEvent();
        };
        return {
            init: init
        };
    };

    let sysMgrList = new SysMgrList();
    sysMgrList.init();
});