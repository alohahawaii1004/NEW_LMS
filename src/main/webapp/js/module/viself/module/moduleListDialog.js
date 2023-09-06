/*
 * @(#)moduleListDialog.js     2023-01-30(030) 오전 9:59:57
 *
 * Copyright 2023 JaYu.space
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

var ModuleListModel = function(){
    "use strict";

    var GRID_URL = paragonCmm.getUrl("/viself/module/moduleMng/moduleList/json");

    return {
        getGridUrl: function(){
            return GRID_URL;
        }
    };
};

var ModuleList = function(){
    "use strict";

    var model = new ModuleListModel();

    //-- Root ID
    var $divRoot = $("#moduleListRoot");
    // 오프너 데이터
    var openerData = $divRoot.data("opener-data");
    var $modalRoot = $("#"+openerData.modalId);
    var $moduleList = $("#ModuleListTable");
    var $form = $("#moudleListform1");

    var closeWindow = function(moduleId){
        var $modal = $("#"+openerData.modalId);
        var cbFncClose = $modal.data("callback");
        if(typeof cbFncClose === "function"){
            // 모달일 경우 닫기
            cbFncClose(moduleId);
        }else{
            // 팝업일 경우 닫기
            self.close();
        }
    };



    var getQueryParams = function(){
        var schParams = $form.serializeObject();
        return schParams;
    };

    var getGridHeight = function(){
        var ENURI = 250;
        var SCH_HEIGHT = 0; //$searchFrm.innerHeight();
        var windowHeight = window.innerHeight;
        return windowHeight - SCH_HEIGHT - $("header").innerHeight() - $("h3").innerHeight() - ENURI;
    };

    var loadTable = function(pageNo){
        $moduleList.datagrid({
            url: model.getGridUrl(),
            method: "post",
            queryParams: getQueryParams(),
            loadFilter: paragonCmm.easyuiLoadFilter,
            height: getGridHeight(),
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
            pageSize: (function(){
                if(getGridHeight() > 500){
                    return 20;
                }
                return 10;
            })(),
            columns:[[{
                field: "chk", checkbox: true, width: "1%", toExcel: false
            }, {
                field: "moduleId", title: "Module", formatter: filterXSS, width: "40%"
            }, {
                field: "moduleDesc", title: "Description", formatter: filterXSS, width: "59%"
            }]],
            onDblClickRow: function(index, row){
                addModule();
            }
        });
    }

    var addModule = function(){
        var rows = $moduleList.datagrid("getChecked");
        if(!rows || rows.length === 0){
            return;
        }

        $.messager.confirm("Confirm",'정말 선택하시겠습니까?',function(r) {
            if(r){
                closeWindow(rows[0].moduleId);
            }
        });
    };

    var search = function(){
        $moduleList.datagrid("load", getQueryParams());
    };

    var setMobileBtnEvent = function(){
        var options = $modalRoot.dialog("options");
        options.buttons.forEach(function(btn){
            var btnId = btn.id;
            switch(btnId){
                case "selectBtn": // 저장 및 적용
                    btn.handler = addModule;
                    break;
                default:
                    break;
            }
            $("#"+btnId).on("click", btn.handler);
        });

        $divRoot.off("click", "button");
        $divRoot.on("click", "button", function(){
            var btnId = $(this).attr("id");
            switch(btnId){
                case "moduleSearchBtn":
                    search();
                    break;
                default:
                    break;
            }

            // HACK
            // bootstrap 의 기본 동작을 위해서 이벤트 전파를 차단하지 않는다.
            // return false;
        });
    };

    var setPcBtnEvent = function(){
        $divRoot.off("click", "button");
        $divRoot.on("click", "button", function(){
            var btnId = $(this).attr("id");
            switch(btnId){
                case "moduleSearchBtn":
                    search();
                    break;
                case "moduleSelectBtn":
                    addModule();
                    break;
                default:
                    break;
            }

            // HACK
            // bootstrap 의 기본 동작을 위해서 이벤트 전파를 차단하지 않는다.
            // return false;
        });
    };

    var attachEvents = function(){

        $form.off("submit");
        $form.on("submit", function (){
            search();
            return false;
        });

        $form.on("keyup", "input", function (e){
            if(e.keyCode === 13){
                search();
            }
            return false;
        });

        if(paragonCmm.isMobile()){
            setMobileBtnEvent();
        }else{
            setPcBtnEvent();
        }


    };


    var init = function(){

        if(paragonCmm.isMobile()){
            $("#btnLayer", $divRoot).hide();
        }

        loadTable();
        attachEvents();
    }

    return {
        init: init
    };
};

$(document).ready(function(){
    "use strict";
    var moduleList = new ModuleList();
    moduleList.init();
});