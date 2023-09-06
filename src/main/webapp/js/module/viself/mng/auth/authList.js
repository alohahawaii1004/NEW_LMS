/*
 * @(#)authList.js     2022-07-19(019) 오후 1:23:58
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

var AuthMngListModel = function(){
    "use strict";

    var LIST_URL = paragonCmm.getUrl("/viself/auth/AuthMng/listPerPage/json");
    var VIEW_URL = paragonCmm.getUrl("/viself/mng/auth/authView.popup");
    var SAVE_URL = paragonCmm.getUrl("/viself/auth/AuthMng/insert/json");

    var code = {
        useYnOptions: [
            {id: "Y", value: "Use"},
            {id: "N", value: "No Use"}
        ]
    };

    var saveData = function(options){
        var opts = $.extend({
            url: SAVE_URL,
            params: null,
            cbFnc: function (json) {}
        }, options);

        paragonCmm.submitAjax(opts.url, opts.params, opts.cbFnc);
    };

    var getUseYnOptions = function(){
        return code.useYnOptions.slice();
    };

    return {
        getGridUrl: function(){
            return LIST_URL;
        },
        getViewUrl: function(){
            return VIEW_URL;
        },
        getUseYnOptions: getUseYnOptions,
        saveData: saveData
    };
};

var AuthMngList = function(){
    "use strict";
    // attributes ======================================

    var objId = "authMng"; // popup call back
    var model = new AuthMngListModel();
    var $rootNode = $("#authMngRootLayer");
    var $form = $("#authMngSchFrm0");
    var $typeCombo = $("select[name=schUseYn]", $form);
    var $grid = $("#authMngList1");
    var $btnGrp1 = $(".main-btn-grp1", $rootNode);
    var $btnGrp2 = $(".main-btn-grp2", $rootNode);

    var opts = $.extend({
        bbsTp: null,
        bbsCd: null,
        bbsNm: null
    }, $rootNode.data("json"));
    objId = paragonCmm.makeTypeKey(objId);
    console.debug(opts);

    // private functions ===============================
    var getGridHeight = function(){
        var ENURI = 100;
        var SCH_HEIGHT = 230; //$searchFrm.innerHeight();
        var windowHeight = window.innerHeight;
        // console.log("???...."+(windowHeight - SCH_HEIGHT - ENURI));
        return windowHeight - SCH_HEIGHT - ENURI;
    };

    var getPageSize = function(){
        var gHeight = getGridHeight();

        // console.log(gHeight);
        if(gHeight > 400){
            return 20;
        }
        return 10;
    };

    var showBaseBtnGrp = function(){
        $btnGrp1.show();
        $btnGrp2.hide();
    };
    var showSaveBtnGrp = function(){
        $btnGrp2.show();
        $btnGrp1.hide();
    };

    var initSchObj = function(){
        // init combo
        htmlUtils.getCodeSelectOpt({
            targetId: $typeCombo,
            parentCd: "USE_YN",
            initdata: "|" + paragonCmm.getLang("L.CBO_SELECT"),
            valNm: "cdAbb",
            txtNm: "langCd"
        });

    };

    var openView = function(row){

        htmlUtils.openPopup({
            url: model.getViewUrl(),
            targetName: "POP_"+row.authCd,
            popupTitle: "권한정보",
            popWidth: 1152,
            popHeight: 600,
            openerData: $.extend({
                cbKey : objId
            }, row),
            params: {}
        });

    };

    var addAuth = function(){
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

    var getQueryParams = function(){
        return $form.serializeObject();
    };

    var useYnStyler = function(value,row,index){
        var style = "background-color: #f2dede;color: #a94442;";
        if (value === "Y"){
            style = 'background-color: #dff0d8;color: #3c763d;';
        }
        return style;
    };

    var initGrid = function(){
        $grid.edatagrid({
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
            checkOnSelect: false,
            selectOnCheck: false,
            pagination : true,
            pagePosition: "bottom",
            pageSize: getPageSize(),
            emptyMsg: "NO DATA",
            columns:[[{
                field: "authCd", title: paragonCmm.getLang("L.코드"), formatter: filterXSS, halign: "CENTER", align: "LEFT", width: "20%", sortable: true,
                editor: {
                    type: "validatebox",
                    options: {
                        validType: ["length[0, 50]"],
                        required:true
                    }
                }
            }, {
                field: "authNm", title: paragonCmm.getLang("L.명칭"), formatter: filterXSS, halign: "CENTER", align: "LEFT", width: "20%", sortable: true,
                editor: {
                    type: "validatebox",
                    options: {
                        validType: ["length[0, 80]"],
                        required:true
                    }
                }
            }, {
                field: "remark", title: paragonCmm.getLang("L.비고"), formatter: filterXSS, halign: "CENTER", align: "LEFT", width: "40%", sortable: false,
                editor: {
                    type: "validatebox",
                    options: {
                        validType: ["length[0, 2000]"]
                    }
                }
            }, {
                field: "useYn", title: paragonCmm.getLang("L.사용여부"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "20%", styler: useYnStyler, sortable: true,
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
            }]],
            onClickCell: function(idx, field, value){
                if(field !== "del"){
                    $grid.datagrid("selectRow", idx);
                    openView($grid.datagrid("getSelected"));
                }
            },
            onDblClickRow: function(){
                $grid.edatagrid("cancelRow");
            },
            onDblClickCell: function(){
                $grid.edatagrid("cancelRow");
            }
        });
    };

    var search = function(){
        console.debug("call search....");
        $grid.datagrid("load", getQueryParams());
    };

    var setCbFnc = function() {
        // 4. 팝업을 위한 콜백 설정
        paragonCmm.setCbFnc(objId, search);
    };

    var saveAuth = function(){
        $grid.edatagrid("saveRow");
        var rows = $grid.edatagrid('getChanges');

        var chkValidate = function(row){
            if(paragonCmm.isEmpty(row.authCd)){
                $.messager.alert("Warning","필수 값이 없습니다.", "warning");
                return false;
            }
            if(paragonCmm.isEmpty(row.authNm)){
                $.messager.alert("Warning","URL 값이 없습니다.", "warning");
                return false;
            }
            if(paragonCmm.isEmpty(row.useYn)){
                $.messager.alert("Warning","Use 값이 없습니다.", "warning");
                return false;
            }
            return true;
        };

        if(rows[0] && chkValidate(rows[0])){
            $.messager.confirm("Confirm",'정말 저장하시겠습니까?',function(r) {
                if(r){
                    model.saveData({
                        params: rows[0],
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

    var cancelEdit = function(){
        $grid.edatagrid("cancelRow");
        //var rows = $grid.edatagrid('getChanges');
        $grid.edatagrid("reload");
    };

    var setFormEvent = function(){
        $form.on("submit", function(){
            search();
            return false;
        });
        $form.on("keyup", "input", function (e) {
            if (e.keyCode === 13){
                search();
            }
            return false;
        });
    };
    var setBtnEvent = function(){
        $rootNode.off("click", "button");
        $rootNode.on("click", "button", function(){
            var btnId = $(this).attr("id");
            switch (btnId){
                case "authMngResetBtn1":
                    $form[0].reset();
                    break;
                case "authMngSearchBtn1":
                    search();
                    break;
                case "authMngAddBtn1":
                    addAuth();
                    // toggleInfo();
                    break;
                case "authMngAcceptBtn1":
                    saveAuth();
                    break;
                case "authMngCancelBtn1":
                    cancelEdit();
                    showBaseBtnGrp();
                    break;
                default:
                    console.warn("not support btnId:"+btnId);
                    break;
            }

            return false; // 이벤트 확산금지
        });
    };
    var initForm = function(){
        initSchObj();
        initGrid();
        showBaseBtnGrp();
    };
    var loadForm = function(){};
    var setEvent = function(){
        setFormEvent();
        setBtnEvent();
    };

    // public functions ================================
    var init = function(){
        initForm();
        loadForm();
        setEvent();
        setCbFnc();
    };

    return {
        init: init
    };
};

$(document).ready(function(){
    "use strict";
    var authMngList = new AuthMngList();
    authMngList.init();
});