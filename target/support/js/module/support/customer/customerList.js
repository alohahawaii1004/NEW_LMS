/*
 * @(#)customerList.js     2023-03-13 013 오전 9:35:41
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

var CustomerListModel = function(){
    "use strict";
    const LIST_URL = paragonCmm.getUrl("/support/customer/customerMng/listPerPage/json");
    const WRITE_URL = paragonCmm.getUrl("/support/customer/customerWrite.popup");
    const VIEW_URL = paragonCmm.getUrl("/support/customer/customerView.popup");

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

var CustomerList = function(){
    "use strict";
    // ==============================================
    // attributes
    // ----------------------------------------------
    const OBJ_ID = "customerList";
    let model = new CustomerListModel();
    let $rootLayer = $("#customerMngRootLayer");
    let $schForm = $("#customerMngSchFrm0", $rootLayer);
    let $grid = $("#customerMngList1", $rootLayer);
    // ==============================================
    // private functions
    // ----------------------------------------------

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

        let orgId = (paragonCmm.isNotEmpty(row))? row.orgId : "";
        let targetName = "ORG_INFO_"+orgId;
        let url = model.getViewUrl();
        if(paragonCmm.isEmpty(orgId)){
            url = model.getWriteUrl();
        }

        htmlUtils.openPopup({
            url: url,
            targetName: targetName,
            popupTitle: "고객정보",
            popWidth: 864,
            popHeight: 540,
            openerData: {
                cbKey: OBJ_ID,
                orgId: orgId
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
                {field: "orgId", title: paragonCmm.getLang("L.아이디"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "10%" }
                , { field: "orgNm", title: paragonCmm.getLang("L.회사명"), formatter: filterXSS, halign: "CENTER", align: "LEFT", width: "80%" }
                , { field: "useYn", title: paragonCmm.getLang("L.사용여부"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "10%", styler: useYnStyler }
            ]],
            onDblClickRow: function (index, row) {
                openView(row);
            }
        });
    };

    let search = function(){
        $grid.datagrid("load", getQueryParams());
    };

    let addCustomer = function(){
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
        $rootLayer.off("click", "button");
        $rootLayer.on("click", "button", function(){
            var btnId = $(this).attr("id");
            switch (btnId){
                case "customerMngResetSmBtn":
                case "customerMngResetBtn1":
                    $schForm[0].reset();
                    break;
                case "customerMngSearchSmBtn":
                case "customerMngSearchBtn1":
                    search();
                    break;
                case "customerMngAddBtn1":
                    addCustomer();
                    break;
                default:
                    console.warn("not support btnId:"+btnId);
                    break;
            }

            return false; // 이벤트 확산금지
        });
    };

    let setCbFnc = function(){
        var cbFnc  = paragonCmm.getCbFnc(OBJ_ID);
        if(typeof cbFnc !== "function"){
            paragonCmm.setCbFnc(OBJ_ID, search());
        }
    };

    let initForm = function(){
        initGrid();
    };
    let loadForm = function(){};
    let setEvent = function(){
        setFormEvent();
        setBtnEvent();
        // set callback info
        setCbFnc();
    };
    // ==============================================
    // public functions
    // ----------------------------------------------
    let init = function(){
        initForm();
        loadForm();
        setEvent();
    };
    return {
        init: init
    };
};

$(function(){
    "use strict";
    let customerList = new CustomerList();
    customerList.init();
});