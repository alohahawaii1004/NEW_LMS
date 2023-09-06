/*
 * @(#)userList.js     2022-07-25(025) 오전 11:08:48
 *
 * Copyright 2022 JaYu.space
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
    let SysUserListModel = function(){

        const USER_LIST_URL = paragonCmm.getUrl("/support/user/userMng/listPerPage/json");
        // const ADD_USER_URL = paragonCmm.getUrl("/support/system/sysUserMng/insert/json");

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

        return{
            getListUrl: function(){
                return USER_LIST_URL;
            },
            addUser: function(options){
                // callAjax(options, ADD_USER_URL);
            }
        };
    };

    let SysUserList = function(){
        // ==============================================
        // attributes
        // ----------------------------------------------
        let model = new SysUserListModel();
        let $rootNode = $("#sysUsrListRootLayer");
        let $schForm = $("#sysUsrListSchFrm0", $rootNode);
        let $grid = $("#sysUsrListList1", $rootNode);
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
                    , {field: "loginId", title: paragonCmm.getLang("L.아이디"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "39%"}
                    , { field: "userNm", title: paragonCmm.getLang("L.이름"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "30%"}
                    , { field: "orgNm", title: paragonCmm.getLang("L.조직"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "30%"}
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

        let addUser = function(){
            let rows = $grid.datagrid("getSelections");
            if(rows.length === 0){
                $.messager.confirm("Confirm", '사용자가 선택되지 않았습니다.', function (r) {
                    if (r) {
                        closeForm({});
                    }else{
                        return false;
                    }
                });
            }else{
                closeForm(rows);
            }
        };

        let setBtnEvent = function(){
            $rootNode.off("click", "button");
            $rootNode.on("click", "button", function(){
                var btnId = $(this).attr("id");
                switch (btnId){
                    case "sysUsrListSearchSmBtn":
                    case "sysUsrListResetBtn1":
                        $schForm[0].reset();
                        break;
                    case "sysUsrListSearchSmBtn":
                    case "sysUsrListSearchBtn1":
                        search();
                        break;
                    case "sysUsrListCloseBtn1":
                        closeForm();
                        break;
                    case "sysUsrListAddBtn1":
                        addUser();
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

    let sysUserList = new SysUserList();
    sysUserList.init();
});