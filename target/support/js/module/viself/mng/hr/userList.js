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

var UserMngListModel = function(){
    "use strict";

    var LIST_URL = paragonCmm.getUrl("/viself/mng/hr/UserMng/listPerPage/json");
    var WRITE_FORM_URL = paragonCmm.getUrl("/viself/mng/hr/userWrite.popup");
    var VIEW_URL = paragonCmm.getUrl("/viself/mng/hr/userInfo/json");
    var AUTH_LIST_URL = paragonCmm.getUrl("/viself/mng/hr/AuthMng/list/json");
    var RSA_INIT_URL = paragonCmm.getUrl("/rsa/init");

    var getAuthList = function(options){
        var opts = $.extend({
            url: AUTH_LIST_URL,
            params: null,
            cbFnc: function (json) {}
        }, options);

        paragonCmm.submitAjax(opts.url, opts.params, opts.cbFnc);
    };

    var initRsa = function(options){
        var opts = $.extend({
            url: RSA_INIT_URL,
            params: null,
            cbFnc: function (json) {}
        }, options);

        paragonCmm.submitAjax(opts.url, opts.params, opts.cbFnc);

    };

    return {
        getGridUrl: function(){
            return LIST_URL;
        },
        getViewUrl: function(){
            return VIEW_URL;
        },
        getWriteUrl: function(){
            return WRITE_FORM_URL;
        },
        getAuthList: getAuthList,
        initRsa: initRsa
    };
};

var UserMngList = function(){
    "use strict";
    // attributes ======================================

    var objId = "userMng"; // popup call back
    // 일반사용자
    var CMM_ISJ = "CMM_ISJ";
    var model = new UserMngListModel();
    var $rootNode = $("#userMngRootLayer");
    var $form = $("#userMngSchFrm0");
    var $useYnCombo = $("select[name=schUseYn]", $form);
    var $adminAuthCombo = $("select[name=schMngAuth]", $form);
    var $userAuthCombo = $("select[name=schUserAuth]", $form);
    var $userMngAddBtn1 = $("#userMngAddBtn1", $rootNode);
    var $grid = $("#userMngList1");

    var adminAuths = [];
    var userAuths = [];
    (function(){
        model.getAuthList({
            cbFnc: function(json){
                if (json.errYn === "E") {
                    alert(json.msg);//-- 오류처리
                    return false;
                }
                var tmpDatas = [].concat(json.data);
                tmpDatas.forEach(function(itm){
                    switch(itm.authClassCd){
                        case "00":
                            adminAuths.push(itm);
                            break;
                        default:
                            if(itm.authCd !== CMM_ISJ){
                                userAuths.push(itm);
                            }
                            break;
                    }
                });

                initSchObj();
            }
        });
    })();

    var loginInfo = paragonCmm.formMap.get("loginInfo");

    var opts = $.extend({
        bbsTp: null,
        bbsCd: null,
        bbsNm: null
    }, $rootNode.data("json"));
    objId = paragonCmm.makeTypeKey(objId);
    console.debug(opts);

    // if(loginInfo.authCd.indexOf(DBOX_ROLE) !== -1){
    //     $userMngAddBtn1.show();
    // }

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

    var initSchObj = function(){

        // init combo
        htmlUtils.getCodeSelectOpt({
            targetId: $useYnCombo,
            parentCd: "USER_YN",
            initdata: "|" + paragonCmm.getLang("L.CBO_SELECT"),
            valNm: "cdAbb",
            txtNm: "langCd"
        });

        htmlUtils.getCodeSelectOpt({
            targetId: $adminAuthCombo,
            parentCd: "NO_PARENT",
            jsonData: adminAuths,
            initdata: "|" + paragonCmm.getLang("L.CBO_SELECT"),
            valNm: "authCd",
            txtNm: "authNm"
        });

        htmlUtils.getCodeSelectOpt({
            targetId: $userAuthCombo,
            parentCd: "NO_PARENT",
            jsonData: userAuths,
            initdata: "|" + paragonCmm.getLang("L.CBO_SELECT"),
            valNm: "authCd",
            txtNm: "authNm"
        });
    };

    var openPopup = function(options){
        var SCREEN_WIDTH = 1152;//screen.width;
        var SCREEN_HEIGHT = 600;//screen.height;

        console.debug(options);

        var params = $.extend({
            url: model.getWriteUrl(),
            openerData: $.extend({
                "cbKey" : objId
            }, opts)
        }, options);

        console.debug(params);

        var targetName = "POP_"+params.bbsUid;
        if(paragonCmm.isEmpty(options.bbsUid)){
            targetName = "POP_LMS_DOC_BOX_WIRTE";
        }
        paragonCmm.openWindow("", SCREEN_WIDTH, SCREEN_HEIGHT, targetName, "yes", "yes", "");

        var imsiForm = $("<form method='POST'>").attr("action", params.url);
        imsiForm.append($("<input type='hidden' name='loginId'>").val(params.loginId));
        imsiForm.append($("<input type='hidden' name='openerData'>").val(JSON.stringify(params.openerData)));
        imsiForm.append($("<input type='hidden' name='_csrf'>").val($("meta[name='_csrf']").attr("content")));
        imsiForm.attr("target",targetName);
        imsiForm.appendTo("body");
        imsiForm.submit();
        imsiForm.remove();
    };
    var openView = function(row){
        openPopup({
            url: model.getViewUrl(),
            loginId: row.loginId,
            openerData: $.extend({
                "cbKey" : objId
            }, {loginId: row.loginId})
        });
    };
    var openWrite = function(){
        openPopup(opts);
    };

    var useYnStyler = function(value,row,index){
        var style = "background-color: #f2dede;color: #a94442;";
        if (row.useYn === "Y"){
            style = 'background-color: #dff0d8;color: #3c763d;';
        }
        return style;
    };

    var getQueryParams = function(){
        return $form.serializeObject();
    };

    var initGrid = function(){
        $grid.datagrid({
            url: model.getGridUrl(),
            method: "post",
            queryParams: getQueryParams(),
            loadFilter: paragonCmm.easyuiLoadFilter,
            height: getGridHeight(),
            striped : true,
            fitColumns : true,
            nowrap: true,
            multiSort : false,
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
                field: "userTpCd", hidden: true
            }, {
                field: "loginId", title: paragonCmm.getLang("L.아이디"), formatter: paragonCmm.getLang, halign: "CENTER", align: "CENTER", width: "10%"
            },{
                field: "userNm", title: paragonCmm.getLang("L.사용자이름"), formatter: paragonCmm.getLang, halign: "CENTER", align: "CENTER", width: "15%"
            }, {
                field: "deptNm", title: paragonCmm.getLang("L.부서"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "15%"
            }, {
                field: "userAuths", title: paragonCmm.getLang("L.사용자권한"), formatter: filterXSS, halign: "CENTER", align: "LEFT", width: "30%"
            },{
                field: "adminAuths", title: paragonCmm.getLang("L.관리자권한"), formatter: filterXSS, halign: "CENTER", align: "LEFT", width: "20%"
            }, {
                field: "useYnNm", title: paragonCmm.getLang("L.재직상태"), formatter: paragonCmm.getLang, halign: "CENTER", align: "CENTER", width: "10%", styler: useYnStyler
            }]],
            onClickCell: function(idx, field, value){
                $grid.datagrid("selectRow", idx);
                openView($grid.datagrid("getSelected"));
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

    var setFormEvent = function(){
        $form.on("submit", function(){
            search();
            return false;
        });
        $form.on("keyup", "input", function (e) {
            if (e.keyCode == 13){
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
                case "userMngResetBtn1":
                    $form[0].reset();
                    break;
                case "userMngSearchBtn1":
                    search();
                    break;
                case "userMngAddBtn1":
                    openWrite();
                    break;
                default:
                    console.warn("not support btnId:"+btnId);
                    break;
            }

            return false; // 이벤트 확산금지
        });
    };
    var initForm = function(){
        // initSchObj();
        initGrid();
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
    var userMngList = new UserMngList();
    userMngList.init();
});