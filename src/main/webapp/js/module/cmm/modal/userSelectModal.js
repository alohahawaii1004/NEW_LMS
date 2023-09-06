/*
 * @(#)userSelectModal.js     2022-03-22(022) 오후 4:10
 *
 * Copyright 2022 JAYU.space
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

var UserSelectModel = function () {
    "use strict";

    var HR_TREE_URL = "/paragon/hr/HrMng/treeList/json";

    var getUserInfo = function(options){
        var opt = $.extend({
            params: {},
            cbFnc: function(){}
        },options);

        paragonCmm.callAjax(paragonCmm.getUrl(HR_TREE_URL), opt.params, opt.cbFnc);
    };

    return {
        getHrTreeUrl: function () {
            return paragonCmm.getUrl(HR_TREE_URL);
        },
        getUserInfo: getUserInfo
    };
};

var UserSelect = function () {
    "use strict";

    var model = new UserSelectModel();

    var $rootNode = $("#userSelect");
    var $form = $("#userSelectForm1");
    var $schCombo = $("#codeMngSchField");
    var $treeArea = $("#treeArea");
    var $hrUserList = $("#hrUserList");

    var openerData = $rootNode.data("opener-data");
    var opt = $.extend({
        isSingle: false
    }, openerData);

    var initForm = function () {
        htmlUtils.initializeSelectJson($schCombo, [{
                code: "",
                value: "--전체--"
            }, {
                code: "0",
                value: "이름"
            }, {
                code: "1",
                value: "부서"
            }],
            "",
            "code",
            "value");
    };

    var drawTree = function () {
        $treeArea.html("");
        var arrDEPTH = [];

        // tree 불러올 값 설정
        paragonCmm.treeInitSet(model.getHrTreeUrl(), "USER", JSON.stringify(arrDEPTH), "");

        // 그려넣을 ID, 루트ID, 최종
        // CMS 제출대상에서 DEPTCD 값을 넘겨줬으면 해당 부서인원만 조회되도록
        if(!paragonCmm.isEmpty($("#cmsUserDeptCd").val())){
            paragonCmm.treeDrawChild("treeArea", "", {
                cmsUserDeptCd: $("#cmsUserDeptCd").val()
            });
        }else{
            paragonCmm.treeDrawChild("treeArea", "", {
                deptTpCd: "00" // 00: 내부부서
            });
        }
    };

    var searchUser = function () {
		console.log(paragonCmm.getSearchQueryParams($form));
        paragonCmm.treeDrawChild("treeArea", "", paragonCmm.getSearchQueryParams($form),function(){
            $treeArea.empty();
        });
    };

    var closeWindow = function(data){
        var $modal = $("#"+openerData.modalId);
        var cbFncClose = $modal.data("callback");
        if(typeof cbFncClose === "function"){
            // 모달일 경우 닫기
            cbFncClose(data);
        }else{
            // 팝업일 경우 닫기
            self.close();
        }
    };

    var selectUser = function(){
        var data = [];
        //-- 결재자 정보 수집
        var rowsApr = $("td[data-meaning='data_td']", "#hrUserList");
        rowsApr.each(function(i, tr){
            var rowData = {};
            //-- input TAG 처리
            var $input = $("input:hidden[name='userRows']", $(tr));
            var rowData = JSON.parse($input.val());
            rowData.inOut = 'Y';
            data.push(rowData);
        });

        if(data == ''){
            $.messager.confirm("Confirm", '사용자가 선택되지 않았습니다.', function (r) {
                if (r) {
                    closeWindow(data);
                }else{
                    return false;
                }
            });
        }else{
            closeWindow(data);
        }
    };

    var setFormEvent = function () {
        $form.on("keyup", "input", function (e) {
            if (e.keyCode == 13){
                searchUser();
            return false;
            }
        });
    };

    var setBtnEvent = function () {


        $rootNode.off("click", "button");
        $rootNode.on("click", "button", function () {
            var btnId = $(this).attr("id");
            switch (btnId) {
                case "userSelectSearchBtn":
                    searchUser();
                    break;
                case "userSelectBtn1":
                    selectUser();
                    break;
                default:
                    break;
            }
            return false;
        });
    };

    var setEvent = function(){
        setFormEvent();
        setBtnEvent();
    };
    var loadData = function(){
        var users = opt.userDatas;
        if(users){
            users.forEach(function (user){
                model.getUserInfo({
                    params: {uuid: user.userId},
                    cbFnc: function(json){
                        setUserInfo(json.data[0]);
                    }
                });
            });
        }
    };

    var chkUserId = function (json) {
        var data = json;
        var arrObj = $("input:hidden[name='userNo']", "#hrUserList");
        var rtnBool = true;
        arrObj.each(function (i, d) {
            if (data.userNo == $(d).val()) {
                rtnBool = false;
            }
        });
        return rtnBool;
    }

    var setUserInfo = function(jsonData){
        // 초기 세팅 ID가 없으면 에러가 발생함
        if(jsonData != null) {
            if (chkUserId(jsonData)) {
                var data = jsonData;
                data.dspNm = data["dspNm" + paragonCmm.toPascalCase(paragonCmm.getSiteLocale())];
                // console.log(data);

                // 단일선택일 경우
                if (opt.isSingle) {
                    $hrUserList.empty();
                }

                $("#addUserTmpl").tmpl(data).appendTo("#hrUserList");
            }
        }
    };

    var selectNode = function (codeId) {
        var codGrp = $("#" + codeId);
        var obj = codGrp.children("input:hidden[name='userRows']");
        var jsonData = JSON.parse($(obj).val());

        //-- 이미 선택된 사용자인지 여부 체크!
        setUserInfo(jsonData);
        paragonCmm.tableTrDragSet("hrUserList");
    };

    var init = function () {
        initForm();
        $treeArea.html("");
        drawTree();
        loadData();
        setEvent();
    };

    return {
        init: init,
        selectNode: selectNode
    };
};

var USER = USER || {};
$(document).ready(function () {
    "use strict";
    console.info(".....................[Loading Module: 사용자 선택]");
    USER = new UserSelect();
    USER.init();
});