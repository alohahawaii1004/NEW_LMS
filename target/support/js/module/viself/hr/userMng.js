/*
 * @(#)userMng.js     2020-10-05(005) 오후 3:32
 *
 * Copyright 2020 JAYU.space
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

/**
 * 사용자 관리
 */
$(document).ready(function(){
    "use strict";
    console.info("[Loading Module: 사용자 관리].......................");

    // 모듈 네임스페이스: 원래는 JS 네임스페이스로 사용하려 했으나 현재는 미정
    var MODUEL_NAMESPACE = "viself.hr.UserMng";

    // 모듈 이름
    var MODULE_NAME = "userMng";

    // 모듈 아이디
    var MODULE_ID = "#"+MODULE_NAME;

    var useYnOptions = [
        {id: "Y", value: "Use"},
        {id: "N", value: "No Use"}
    ];

    var userDialog = (function(){

        var config = {
            dialogId: MODULE_ID + "Pop1",
            saveURL: jayu.getURL(paragonCmm.getUrl("/viself/hr/userMng/save/json"))
        };

        var $dialogForm = $(config.dialogId+"Frm0");
        var $saveBtn = $(config.dialogId+"SaveBtn1");
        var $deleteBtn = $(config.dialogId+"DeleteBtn1");
        var $closeBtn = $(config.dialogId+"CloseBtn1");
        var $genTmpPwdBtn = $(config.dialogId+"GenTemPwdBtn1");
        var $tmpPwdInfoTr = $(config.dialogId+"TmpPwdTr");

        /**
         * 이벤트 초기화
         */
        var resetEvent = function(){
            $saveBtn.off();
            $deleteBtn.off();
            $closeBtn.off();
        };


        var initDialog = function (){
            // 1. 콤보박스 초기화
            jayu.setOptions($(config.dialogId + "Select1"), useYnOptions);

            // 2. 삭제버튼 숨기기
            $deleteBtn.addClass("hidden");
            $genTmpPwdBtn.addClass("hidden");
            $tmpPwdInfoTr.addClass("hidden");

            // 3. 다이얼로그 ui 초기화
            // HACK 아래와 같이 add, remove 하지 않을 경우 css 깨짐
            $(config.dialogId).addClass("hidden");
            $(config.dialogId).removeClass("hidden");
            $(config.dialogId).dialog({
                iconCls: "fa fa-info-circle",
                title: "User Info.",
                width: jayu.defPopWidth,
                height: jayu.defPopHeight,
                cache: false,
                method: "post",
                modal: true,
                onClose: function(){
                    // HACK 이벤트를 지우지 않으면 계속 쌓인다.
                    resetEvent();
                }
            });
        };

        var setContent = function(row){

            if(jayu.isEmpty(row)){
                $tmpPwdInfoTr.removeClass("hidden");
                $genTmpPwdBtn.addClass("hidden");
                $dialogForm.find("input[name=userId]").attr("readonly", false);
                $dialogForm.find("input[name=cud]").val("C");
                $dialogForm.find("input[name=userId]").val("");
                $dialogForm.find("input[name=userNm]").val("");
                $dialogForm.find("select[name=useEnable]").val("Y");
            }else{
                $tmpPwdInfoTr.addClass("hidden");
                $genTmpPwdBtn.removeClass("hidden");
                $dialogForm.find("input[name=userId]").attr("readonly", true);
                $dialogForm.find("input[name=cud]").val("U");
                $dialogForm.find("input[name=userNm]").val(filterXSS(row.userNm));
                $dialogForm.find("select[name=useEnable]").val(filterXSS(row.useEnable));
            }
        };

        var save = function(row){
            var frmData = $dialogForm.serializeObject();

            // 입력값 검사
            var check = function(){
                // 아이디 필수
                if(jayu.isEmpty(frmData.userId)){
                    $.messager.alert("Warning","아이디는 필수 입니다.", "warning");
                    return false;
                }

                // 이름 필수
                if(jayu.isEmpty(frmData.userNm)){
                    $.messager.alert("Warning","이름은 필수 입니다.", "warning");
                    return false;
                }

                return true;
            };

            // 저장
            if(check()){
                $.messager.confirm("Confirm",'정말 저장하시겠습니까?',function(r){
                    if(r){

                        var saveData = function(rsaData){
                            var params = $dialogForm.serializeObject();
                            // console.log(params);

                            var plainId = params.userId;
                            var plainNm = params.userNm;

                            var publicKeyModulus = rsaData.publicKeyModulus;
                            var publicKeyExponent = rsaData.publicKeyExponent;

                            var rsa = new RSAKey();
                            rsa.setPublic(publicKeyModulus, publicKeyExponent);

                            var encID = rsa.encrypt(plainId);
                            var encNm = rsa.encrypt(plainNm);

                            params.userId = encID;
                            params.userNm = encNm;
                            params.encParams = "userId,userNm";

                            var data = params

                            console.log("??..."+JSON.stringify(data));

                            $.ajax({
                                type: "POST",
                                url: config.saveURL,
                                data: JSON.stringify(data),
                                success: function(data){
                                    if(data.errYn !== "E"){
                                        $.messager.alert("Info",data.msg, "info");
                                        // 조회
                                        userMng.search();
                                        // 닫기
                                        closeDialog();
                                    }else{
                                        $.messager.alert("Warning",data.msg, "warning");
                                    }
                                },
                                dataType: "json",
                                contentType: "application/json"
                            });
                        };

                        var INIT_URL = jayu.getURL("/rsa/init");
                        $.ajax({
                            type: "POST",
                            url: INIT_URL,
                            success: function(data){
                                saveData(data);
                            }
                        });
                    }
                });
            }
        };

        var closeDialog = function(){
            $(config.dialogId).dialog("close");
        };

        var setBtnEvent = function (row){
            // 저장버튼 이벤트
            var $saveBtn = $(config.dialogId +"SaveBtn1");
            $saveBtn.on("click", function(){
                // 저장
                save(row);
            });

            // 삭제버튼 이벤트
            var $deleteBtn = $(config.dialogId +"DeleteBtn1");
            if(jayu.isNotEmpty(row)){
                $deleteBtn.removeClass("hidden");
                $deleteBtn.on("click", function(){
                    // 삭제
                    var data = {
                        list: [row]
                    };
                    userMng.deleteUser(data, closeDialog);
                });
            }

            // 닫기버튼 이벤트
            var $cloaseBtn = $(config.dialogId +"CloseBtn1");
            $cloaseBtn.on("click", function(){
                // 닫기
                closeDialog();
            });
        };

        var open = function(row){
            // 다이얼로그 초기화
            initDialog();

            // 내용 설정
            setContent(row);

            // 버튼이벤트 설정
            setBtnEvent(row);
        };

        return {
            open: open
        };
    })();

    var userMng = (function(){
        var config = {
            searchFormId: MODULE_ID + "SchFrm0",
            searchBtnId: MODULE_ID + "SearchBtn1",
            gridId: MODULE_ID + "List1",
            addBtnId: MODULE_ID + "AddRowBtn1",
            deleteBtnId: MODULE_ID + "AddRowBtn1",
            excelBtnId: MODULE_ID + "ExcelBtn1",
            searchURL: jayu.getURL("/viself/hr/UserMng/list/json"),
            deleteURL: jayu.getURL("/viself/hr/UserMng/delete/json")
        };

        var getGridHeight = function(){
            var ENURI = 10;
            var SCH_HEIGHT = 42;
            var windowHeight = window.innerHeight;
            var gridHeight = windowHeight - SCH_HEIGHT - jayu.navHeight - jayu.tabHeight - jayu.btnLayerHeight - ENURI;
            return gridHeight;
        };

        var getQueryParams = function(){
            var params = $(config.searchFormId).serializeObject();
            return params;
        };

        var initGrid = function(){
            $(config.gridId).datagrid({
                url: "",
                method: "post",
                queryParams: getQueryParams(),
                loadFilter: jayu.easyui.loadFilter,
                height: getGridHeight(),
                striped : true,
                fitColumns : true,
                nowrap: true,
                multiSort : true,
                rownumbers: true,
                singleSelect: true,
                checkOnSelect: false,
                selectOnCheck: false,
                pagination : true,
                pagePosition: "bottom",
                pageSize: 10,
                columns:[[{
                    field: "chk", checkbox: true, width: "1%", toExcel: false
                },{
                    field: "userId", title: "ID", formatter: filterXSS, width: "40%"
                }, {
                    field: "userNm", title: "Name", formatter: filterXSS, width: "50%"
                }, {
                    field: "remark", title: "Remark", formatter: filterXSS, width: "30%", hidden: true
                },{
                    field: "useEnable", title: "Use", formatter: filterXSS, width: "9%"
                }]],
                onDblClickRow: function(index,row){
                    // 다이얼로그 오픈
                    userDialog.open(row);
                }
            });
        };

        var search = function(){
            var opts = $(config.gridId).datagrid("options");
            if(jayu.isEmpty(opts.url)){
                opts.url = config.searchURL;
            }

            $(config.gridId).datagrid("load", getQueryParams());
        };

        var deleteUser = function(rows, cbFnc){
            $.messager.confirm("Confirm",'정말 삭제하시겠습니까?',function(r){
                if (r){
                    $.ajax({
                        type: "POST",
                        url: config.deleteURL,
                        data: JSON.stringify(rows),
                        success: function(data){

                            console.log(data);

                            if(data.errYn !== "E"){
                                $.messager.alert("Info",data.msg, "info");

                                if(typeof cbFnc === "function"){
                                    cbFnc();
                                }

                                // 조회
                                search();
                            }else{
                                $.messager.alert("Warning",data.msg, "warning");
                            }
                        },
                        dataType: "json",
                        contentType: "application/json"
                    });
                }
            });
        };

        var deleteChecked = function(){
            var rows = $(config.gridId).datagrid("getChecked");

            if(jayu.isEmpty(rows) || rows.length === 0){
                $.messager.alert("Warning","삭제할 대상을 선택하세요.", "warning");
                return false;
            }
            var data = {list: rows};
            deleteUser(data);
        };

        var toExcel = function(){
            $(config.gridId).datagrid("toExcel", {
                filename: "user-list.xls",
                worksheet: "user list",
                caption: "User List"
            });
        };

        var setEvent = function(){
            // 조회버튼 클릭
            $(config.searchBtnId).on("click", function(){
                // 검색
                search();
            });

            // 조회폼 서브밋
            $(config.searchFormId).on("submit", function(){
                // 검색
                search();
                return false;
            });

            // 추가버튼 클릭
            $(config.addBtnId).on("click", function(){
                userDialog.open();
            });
            // 삭제버튼 클릭
            $(config.delBtnId).on("click", function(){
                deleteChecked();
            });
            // 엑셀버튼 클릭
            $(config.excelBtnId).on("click", function(){
                toExcel();
            });
        };

        var init = function(){
            initGrid();
            setEvent();
        };

        var resize = function(){
            console.log("..........userMng.resize");
            $(config.gridId).datagrid("resize", {height: getGridHeight()});
        };

        return {
            init: init,
            resize: resize,
            deleteUser: deleteUser,
            search: search
        };
    })();

    jayu.setApp(MODULE_NAME, userMng);
    userMng.init();
}());