/*
 * @(#)userWrite.js     2023-03-28 오전 10:23
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
    let model = (function(){
        const SAVE_URL = paragonCmm.getUrl("/support/user/userMng/update/json");
        const GET_DATA_URL = paragonCmm.getUrl("/support/user/userMng/loadData/json");
        const AUTH_LIST_URL = paragonCmm.getUrl("/support/user/userMng/authList/json");
        const RSA_INIT_URL = paragonCmm.getUrl("/rsa/init");
        const COM_LIST_URL = paragonCmm.getUrl("/support/cmm/customerList.include");

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

        let getData = function(options){
            callAjax(options, GET_DATA_URL);
        };

        let initRsa = function(options){
            callAjax(options, RSA_INIT_URL);
        };

        let saveData = function(options){
            callAjax(options, SAVE_URL);
        };

        let getAuthList = function(options){
            callAjax(options, AUTH_LIST_URL);
        };

        return {
            getComListUrl: function(){
                return COM_LIST_URL;
            },
            getAuthList: getAuthList,
            saveData: saveData,
            initRsa: initRsa,
            getData: getData
        };
    })();

    let userWrite = (function(){
        // attributes ###################################
        const CMM_ISJ = "CMM_ISJ";
        let $rootNode = $("#userMngWriteRootLayer");
        let $userMngWriteForm = $("#userMngWriteForm", $rootNode);
        let $orgId = $("input[name=orgId]", $userMngWriteForm);
        let $orgNm = $("input[name=orgNm]", $userMngWriteForm);
        let $userAuthArea = $("#userAuthArea", $userMngWriteForm);
        let openerData = $.extend({
            loginId: null,
            cbKey: null
        }, $rootNode.data("opener-data"));

        let userAuths = [];
        (function () {



            model.getAuthList({
                cbFnc: function (json) {
                    if (json.errYn === "E") {
                        alert(json.msg);//-- 오류처리
                        return false;
                    }
                    var tmpDatas = [].concat(json.data);
                    tmpDatas.forEach(function (itm) {
                        if(itm.authClassCd === "99"){
                            userAuths.push(itm);
                        }
                    });

                    console.debug(userAuths);
                    $userAuthArea.html(htmlUtils.getInputCheckbox("userAuths", htmlUtils.getCodeStrFromData(userAuths, "authCd,authNm"), CMM_ISJ));

                    // 일반사용자 권한고정
                    $('input[type=checkbox][name=userAuths]', $userAuthArea).change(function() {
                        if ($(this).val() === CMM_ISJ) {
                            $(this).prop("checked", true);
                        }
                    });
                }
            });


        })();

        // private functions ############################
        let closeForm = function(data){
            if(typeof data !== "undefined"){
                if(typeof(opener) !== "undefined") {
                    let cbKey = openerData.cbKey;
                    let openerCbFnc = opener.paragonCmm.getCbFnc(cbKey);
                    if(typeof openerCbFnc === "function"){
                        openerCbFnc(data);
                    }
                }
            }
            if(typeof(opener) !== "undefined") {
                self.close();
            }
        };

        let initEditor = function(){
            //editor
            htmlUtils.getHtmlEditor({
                sCtrlName: "memo",
                sCtrlId: "memo",
                sHeight: "250px"
            });
        };

        let openModal = function(){

            let options = {
                title: "회사선택",
                href: model.getComListUrl(),
                width: 864,
                height: "auto",
                top: "10px",
                cbFnc: function(data){

                    console.debug(data);
                    let datas = [].concat(data);

                    $orgId.val(datas[0].orgId);
                    $orgNm.val(datas[0].orgNm);
                }
            };

            if(paragonCmm.isMobile()) {
                options = $.extend(options, {
                    resizable: false,
                    width: "80%",
                    height: "50%",
                    buttons: [{
                        id: "closeBtn",
                        text: "<i class=\"fa fa-times\"></i> 닫기"
                    }, {
                        id: "selectBtn",
                        text: "<i class=\"fa fa-check\"></i> 선택"
                    }]
                });
            }

            htmlUtils.openDialog(options);
        };

        let setData = function (data){

            if(paragonCmm.isNotEmpty(data)){
                $("#userMngWriteSaveBtn").html('<i class="fa fa-pencil"></i> 저장');
            }

            var keys = Object.keys(data);
            keys.forEach(function (key) {
                switch (key) {
                    case "loginId":
                        $("[data-col='" + key + "']", $rootNode).val(filterXSS(data[key]));
                        $("[data-col='" + key + "']", $rootNode).attr("readonly", true);
                        break;
                    case "useYn":
                        $("input:radio[name='useYn']:radio[value='"+filterXSS(data[key])+"']").prop('checked', true);
                        break;
                    case "userAuthCds":
                        if(paragonCmm.isEmpty(data[key])){
                            break;
                        }
                        var authCds = [].concat(data[key].split(","));
                        var $chBoxes = $("input:checkbox[name=userAuths]");
                        authCds.forEach(function(itm){
                            $($chBoxes).each(function(idx, obj){
                                if(itm === $(obj).val()){
                                    $(obj).attr("checked", true);
                                }
                            });
                        });
                        break;
                    default:
                        $("[data-col='" + key + "']", $rootNode).val(filterXSS(data[key]));
                        break;
                }
            });
        };

        let isValidate = function(data){
            if(paragonCmm.isEmpty(data.loginId)){
                $.messager.alert("Warning", paragonCmm.getLang("M.필수_입력사항_입니다", paragonCmm.getLang('L.ID')),"warning", function () {
                    $("input[name=loginId]", $userMngWriteForm).focus();
                });
                return false;
            }
            if(paragonCmm.isEmpty(data.nmKo)){
                $.messager.alert("Warning", paragonCmm.getLang("M.필수_입력사항_입니다", paragonCmm.getLang('L.이름')),"warning", function () {
                    $("input:text[name='nmKo']", $userMngWriteForm).focus();
                });
                return false;
            }
            if(paragonCmm.isEmpty(data.orgId)){
                $.messager.alert("Warning", paragonCmm.getLang("M.필수_입력사항_입니다", paragonCmm.getLang('L.회사')),"warning", function () {
                    $("#userMngWriteOrgSchBtn", $userMngWriteForm).focus();
                });
                return false;
            }

            return true;
        };

        let getFormData = function(){
            // CKEDITOR
            let params = $userMngWriteForm.serializeObject();
            let tmpAuths = [];
            if(params.userAuths){
                tmpAuths = [].concat(params.userAuths);
            }
            params["userAuth"] = tmpAuths.join(",");
            console.debug(params);
            return params;
        };

        let encRSA = function(rsaData){

            // set RSA
            let opts = $.extend({
                publicKeyModulus: null,
                publicKeyExponent: null
            },rsaData);

            console.debug(opts);

            let rsa = new RSAKey();
            rsa.setPublic(opts.publicKeyModulus, opts.publicKeyExponent);

            // enc params
            let rtnData = {};
            let formDatas = getFormData();
            let keys = Object.keys(formDatas);
            keys.forEach(function (key) {
                if(key !== "userAuths"){
                    rtnData[key] = rsa.encrypt(formDatas[key]);
                }
            });

            return rtnData;
        };

        let encNSaveData = function(rsaData){

            // 데이터 저장
            model.saveData({
                params: encRSA(rsaData),
                cbFnc: function(json) {
                    if (json.errYn === "E") {
                        $.messager.alert("Error", json.msg,"error");
                        return false;
                    }
                    closeForm(json.data);
                }
            });
        };

        let initRSA = function(){
            model.initRsa({
                cbFnc: function(data){
                    encNSaveData(data);
                }
            });
        };
        
        let saveData = function(){

            console.debug("save....");

            paragonCmm.setEditorSubmit("");
            if (!isValidate($userMngWriteForm.serializeObject())) {
                return false;
            }

            $.messager.confirm("Confirm", '정말 저장하시겠습니까?', function (r) {
                if (r) {
                    initRSA();
                }
            });
        };

        let setBtnEvent = function(){
            $rootNode.off("click", "button");
            $rootNode.on("click", "button", function () {
                let btnId = $(this).attr("id");
                switch (btnId){
                    case "userMngWriteOrgSchBtn":
                        openModal();
                        break;
                    case "userMngWriteCloseBtn":
                        closeForm();
                        break;
                    case "userMngWriteSaveBtn":
                        saveData();
                        break;
                    default:
                        console.warn("not support btnId:"+btnId);
                        return;
                }

                return false; // 이벤트 확산금지
            });
        };

        let initForm = function(){
            initEditor();
        };

        let loadData = function(){
            model.getData({
                params: {
                    loginId: openerData.loginId
                },
                cbFnc: function(json){
                    if (json.errYn === "E") {
                        alert(json.msg);//-- 오류처리
                        return false;
                    }

                    if(paragonCmm.isNotEmpty(json.data)){
                        setData(json.data);
                    }
                }
            });
        };

        let setEvent = function(){
            setBtnEvent();
        };

        // public functions #############################
        let init = function(){
            initForm();
            loadData();
            setEvent();
        };

        return {
            init: init
        };
    })();

    userWrite.init();
});