/*
 * @(#)authMember.js     2022-07-20(020) 오전 10:49:02
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

var AuthMemberModel = function(){
    "use strict";
    // var USER_SEARCH_MODAL = paragonCmm.getUrl("/cmm/modal/userSelectModal.include");
    var USER_SEARCH_MODAL = paragonCmm.getUrl("/support/cmm/userList.modal");
    var GET_DATA_URL = paragonCmm.getUrl("/viself/auth/AuthMember/list/json");
    var UPDATE_URL = paragonCmm.getUrl("/viself/auth/AuthMember/saveMembers/json");
    var DELETE_URL = paragonCmm.getUrl("/viself/auth/AuthMember/deleteMembers/json");

    var deleteData = function (options){
        var opts = $.extend({
            url: DELETE_URL,
            param: null,
            cbFnc: function(json){}
        }, options);

        paragonCmm.callAjax(opts.url, opts.param, opts.cbFnc);
    };

    var saveData = function(options){
        var opts = $.extend({
            url: UPDATE_URL,
            param: null,
            cbFnc: function(json){}
        }, options);

        paragonCmm.callAjax(opts.url, opts.param, opts.cbFnc);
    };

    return {
        deleteData: deleteData,
        saveData: saveData,
        getGridUrl: function(){
            return GET_DATA_URL;
        },
        getUserSchUrl: function () {
            return USER_SEARCH_MODAL;
        }
    };
};

var AuthMember = function(){
    "use strict";
    // attributes ================================================
    // 기본 사용자 권한
    var CMM_ISJ = "CMM_ISJ";
    var SYS_AUTHS = "CMM_ISJ,CMM_SYS,SYS_DEV";
    var model = new AuthMemberModel();
    var $rootNode = $("#authMemberRootLayer");
    var $form = $("#authMemberForm1", $rootNode);
    var $grid = $("#authMemberList1", $rootNode);

    var opts = $.extend({
        authCd: null
    }, $rootNode.data("opener-data"));

    // private functions =========================================
    var doCallback = function(data){
        if(typeof(opener) !== "undefined") {
            var cbKey = opts.cbKey;
            var openerCbFnc = opener.paragonCmm.getCbFnc(cbKey);
            if(typeof openerCbFnc === "function"){
                openerCbFnc(data);
            }
        }
    };
    var closeForm = function(data){
        doCallback(data);
        if(typeof(opener) !== "undefined") {
            self.close();
        }
    };

    var getGridHeight = function(){
        var ENURI = 140;
        var TAB_HEIGHT = 100; //$searchFrm.innerHeight();
        var windowHeight = window.innerHeight;
        // console.log("???...."+(windowHeight - SCH_HEIGHT - ENURI));
        return windowHeight - TAB_HEIGHT - ENURI;
    };

    var checkSysAuth = function(cd){
        if(SYS_AUTHS.indexOf(cd) !== -1){
            // $delBtn.show();
            $authNm.attr("readonly", true);
        }
    };


    var statusStyler = function(val, row, index){
        var tempVal = "";
        switch(row.useYn){
            case "N":
                tempVal = "background-color: #f2dede;color: #a94442;"
                break;
            default:
                tempVal = "background-color: #dff0d8;color: #3c763d;"
                break;
        }
        return tempVal;
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
            multiSort : true,
            remoteSort: true,
            rownumbers: true,
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            pagination : false,
            pagePosition: "bottom",
            // pageSize: getPageSize(),
            emptyMsg: "NO DATA",
            columns: [[{
                field: "chk", checkbox: true, width: "1%", toExcel: false
            }, {
                field: "mbrId", title: "id", formatter: filterXSS, width: "20%"
            },{
                field: "nmKo", title: "NAME", formatter: filterXSS, width: "30%"
            },{
                field: "deptNmKo", title: "DEPT", formatter: filterXSS, width: "30%"
            },{
                field: "stuLangCd", title: "STATUS", formatter: paragonCmm.getLang, align: "center", width: "19%", styler: statusStyler
            }]]
        });
    };

    var search = function(){
        $grid.datagrid("load", getQueryParams());
    };

    var validate = function(){
        var rows = $grid.datagrid("getChecked");
        if(rows.length === 0){
            $.messager.alert("Warning", "삭제할 건을 선택해 주세요.", "warning");
            return false;
        }
        return true;
    };

    var delData = function(){
        if(!validate()){
            return false;
        }

        $.messager.alert("Warning", "권한 정보가 바뀝니다.", "warning", function (){
            $.messager.confirm("Confirm",'정말 삭제하시겠습니까?', function(r){
                if(r){

                    model.deleteData({
                        param: {
                            list: $grid.datagrid("getChecked")
                        },
                        cbFnc: function(json){
                            if (json.errYn === "E") {
                                alert(json.msg);//-- 오류처리
                                return false;
                            }
                            search();
                            doCallback();
                        }
                    });
                }
            });
        });
    };

    var addData = function(data){

        var params = [], tmpArray = [].concat(data);
        tmpArray.forEach(function(itm){
            params.push({
                authCd: opts.authCd,
                mbrTpCd: "USER",
                mbrId: itm.loginId,
                userNm: itm.dspNm
            });
        });

        $.messager.alert("Warning", "권한 정보가 바뀝니다.", "warning", function (){
            $.messager.confirm("Confirm",'정말 저장하시겠습니까?', function(r){
                if(r){
                    model.saveData({
                        param: {
                            list: params
                        },
                        cbFnc: function(json){
                            if (json.errYn === "E") {
                                alert(json.msg);//-- 오류처리
                                return false;
                            }
                            search();
                            doCallback();
                        }
                    });
                }
            });
        });
    };

    var openUserSch = function (){
        var modalUid = paragonCmm.getRandomUUID();
        var options = {
            modalId: opts.authCd + modalUid,
            href: model.getUserSchUrl(),
            title: "Select User",
            params: {
                modalId: opts.authCd + modalUid
            },
            width: 1120,
            // height: 520,
            height: "auto",
            top: "20px",
            cbFnc: function (data) {
                console.debug(data);
                if (paragonCmm.isNotEmpty(data) && data.length > 0) {
                    addData(data);
                }
            }
        };
        if(paragonCmm.isMobile()) {
            options = $.extend(options, {
                resizable: false,
                width: "80%",
                height: "50%"
            });
        }
        htmlUtils.openDialog(options);
    };

    var setBtnEvent = function(){
        $rootNode.off("click", "button");
        $rootNode.on("click", "button", function(){
            var btnId = $(this).attr("id");
            switch (btnId){
                case "authMemberDelBtn1":
                    delData();
                    break;
                case "authMemberAddBtn1":
                    openUserSch();
                    break;
                case "authMemberCloseBtn1":
                    closeForm();
                    break;
                default:
                    console.warn("not support btnId:"+btnId);
                    break;
            }

            return false; // 이벤트 확산금지
        });
    };

    var initForm = function(){
        if(CMM_ISJ === opts.authCd){
            $("#authMemberInfo").html("<span class='col-md-3 col-xs-4'><i class='fa fa-users'></i> 모든사용자(기본권한)</span>");
        }else{
            // 버튼 표시
            $("#authMemberDelBtn1").show();
            $("#authMemberAddBtn1").show();

            // 그리드 초기화
            initGrid()
        }
    };
    var loadForm = function(){
    };
    var setEvent = function(){
        setBtnEvent();
    };

    // public functions ==========================================
    var init = function(){

        console.log(opts);

        initForm();
        loadForm();
        setEvent();
        paragonCmm.convertLang($rootNode);
    };
    return {
        init: init
    };
};

$(document).ready(function(){
    "use strict";
    var authMember = new AuthMember();
    authMember.init();
});