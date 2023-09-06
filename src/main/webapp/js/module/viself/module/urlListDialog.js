/*
 * @(#)urlListDialog.js     2021-12-24(024) 오전 11:19
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

var UrlListDialogModel = function(){
    "use strict";

    var URL_SEARCH_URL = paragonCmm.getUrl("/viself/module/moduleMng/urlList/json");

    var MODULE_URL_SAVE_URL = paragonCmm.getUrl("/viself/module/moduleMng/saveModuleUrl/json");

    var getSearchUrlListUrl = function(){
        return URL_SEARCH_URL;
    };

    var saveModuleUrl = function(data, cbFnc){
        paragonCmm.callAjax(MODULE_URL_SAVE_URL, data, cbFnc);
    };

    return {
        getSearchUrlListUrl: getSearchUrlListUrl,
        saveModuleUrl: saveModuleUrl
    };
};

/**
 * Url 리스트 다이얼로그
 * @returns {{init: init}}
 * @constructor
 */
var UrlListDialog = function(){
    "use strict";

    var $rootDom = $("#moduleMngPop2Frm0");
    var $dialogGrid = $("#moduleMngPop2List1");
    var vModel = new UrlListDialogModel();
    var callBackFnc;

    var getQueryParams = function(){

        console.log($rootDom.data("opener-data"));

        return $rootDom.data("opener-data");
    };

    var initGrid = function(){
        $dialogGrid.datagrid({
            url: vModel.getSearchUrlListUrl(),
            method: "post",
            queryParams: getQueryParams(),
            loadFilter: paragonCmm.easyuiLoadFilter,
            height: 450,
            striped : true,
            fitColumns : true,
            nowrap: true,
            multiSort : true,
            remoteSort: true,
            rownumbers: true,
            singleSelect: true,
            checkOnSelect: true,
            selectOnCheck: false,
            pagination : true,
            pagePosition: "bottom",
            pageSize: 10,
            columns:[[{
                field: "chk", checkbox: true, width: "1%", toExcel: false
            },{
                field: "accesUrl", title: "URL", formatter: filterXSS, width: "40%"
            }, {
                field: "urlDesc", title: "Description", formatter: filterXSS, width: "57.5%"
            }]]
        });
    };

    var closeModal = function(booleanFlag){
        if(typeof callBackFnc === "function"){
            callBackFnc(booleanFlag);
        }
    };

    var initForm = function(){
        callBackFnc = $rootDom.data("callback");
        initGrid();
    };

    var addUrl = function(){
        var rows = $dialogGrid.datagrid("getChecked");
        if(!rows || rows.length === 0){
            return;
        }
        var param = $.extend({},getQueryParams(),{
            cud: "C",
            list: rows
        });
        $.messager.confirm("Confirm",'정말 저장하시겠습니까?',function(r) {
            if(r){
                var cbFnc = function(data){
                    htmlUtils.showMsg(data);
                    closeModal(true);
                }

                vModel.saveModuleUrl(param, cbFnc);
            }
        });
    };

    var setEvent = function(){
        $rootDom.find("button").off();
        $rootDom.on("click", "button", function(){
            var btnId = $(this).attr("id");
            switch (btnId) {
                case "moduleMngPop2CloseBtn1" :
                    closeModal();
                    break;
                case "moduleMngPop2ConfirmBtn1" :
                    addUrl();
                    break;
                default :
                    break;
            }

            return false; // 이벤트 버블링 종료
        });
    };

    var init = function(){
        initForm();
        setEvent();
    };

    return {
        init: init
    };
};