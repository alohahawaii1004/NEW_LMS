/*
 * @(#)userView.js     2022-07-25(025) 오전 11:09:00
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

var HRUserMngWriteModel = function () {
    "use strict";
    var SAVE_URL = paragonCmm.getUrl("/viself/mng/hr/UserMng/update/json");
    var GET_DATA_URL = paragonCmm.getUrl("/viself/mng/hr/UserMng/getData/json");
    var AUTH_LIST_URL = paragonCmm.getUrl("/viself/mng/hr/AuthMng/list/json");
    var RSA_INIT_URL = paragonCmm.getUrl("/rsa/init");

    var getAuthList = function (options) {
        var opts = $.extend({
            url: AUTH_LIST_URL,
            params: null,
            cbFnc: function (json) {
            }
        }, options);

        paragonCmm.callAjax(opts.url, opts.params, opts.cbFnc);
    };

    var saveData = function (options) {
        var opts = $.extend({
            url: SAVE_URL,
            params: null,
            cbFnc: function (json) {
            }
        }, options);

        paragonCmm.callAjax(opts.url, opts.params, opts.cbFnc);
    };

    var initRsa = function(options){
        var opts = $.extend({
            url: RSA_INIT_URL,
            params: null,
            cbFnc: function (json) {}
        }, options);

        paragonCmm.callAjax(opts.url, opts.params, opts.cbFnc);

    };

    var getData = function (options) {
        var opts = $.extend({
            url: GET_DATA_URL,
            param: null,
            cbFnc: function (json) {
            }
        }, options);

        paragonCmm.callAjax(opts.url, opts.param, opts.cbFnc);
    };
    return {
        getAuthList: getAuthList,
        saveData: saveData,
        initRsa: initRsa,
        getData: getData
    };
};

var HRUserMngWrite = function () {
    "use strict";
    // attributes ============================
    // 일반사용자(기본권한)
    var CMM_ISJ = "CMM_ISJ";
    var model = new HRUserMngWriteModel();
    var $rootNode = $("#userMngViewRootLayer");
    var $form = $("#userMngViewForm1");
    var $userAuthArea = $("#userAuthArea", $form);
    var $adminAuthArea = $("#adminAuthArea", $form);

    var opts = $.extend({
        loginId: null
    }, $rootNode.data("opener-data"));

    var userAuths = [];
    var adminAuths = [];

    // getCodeStrFromData

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
                    }else{
                        adminAuths.push(itm);
                    }
                });
            }
        });
    })();

    // private functions =====================
    var closeForm = function (data) {
        if (typeof (opener) !== "undefined") {
            var cbKey = opts.cbKey;
            var openerCbFnc = opener.paragonCmm.getCbFnc(cbKey);
            if (typeof openerCbFnc === "function") {
                openerCbFnc(data);
            }
        }
        if (typeof (opener) !== "undefined") {
            self.close();
        }
    };

    var initCheckbox = function () {
        $userAuthArea.html(htmlUtils.getInputCheckbox("userAuths", htmlUtils.getCodeStrFromData(userAuths, "authCd,authNm")));
        $adminAuthArea.html(htmlUtils.getInputCheckbox("adminAuths", htmlUtils.getCodeStrFromData(adminAuths, "authCd,authNm")));

    };

    var setContent = function (data) {
        var keys = Object.keys(data);
        keys.forEach(function (key) {
            switch (key) {
                case "useYnNm":
                    $("[data-col='"+key+"']", $rootNode).html(paragonCmm.getLang(data[key]));
                    break;
                case "useYn":
                    var badgeClass = "alert-success";
                    if("Y" !== data[key]){
                        badgeClass = "alert-danger";
                    }
                    $("[data-col=useYnNm]", $rootNode).addClass(badgeClass);
                    break;
                case "authCd":
                    if(paragonCmm.isEmpty(data[key])){
                        break;
                    }
                    var authCds = [].concat(data[key].split(","));
                    var $uchBoxes = $("input:checkbox[name=userAuths]");
                    var $achBoxes = $("input:checkbox[name=adminAuths]");
                    authCds.forEach(function(itm){
                        $($uchBoxes).each(function(idx, obj){
                            if(itm === $(obj).val()){
                                $(obj).attr("checked", true);
                            }
                        });
                        $($achBoxes).each(function(idx, obj){
                            if(itm === $(obj).val()){
                                $(obj).attr("checked", true);
                            }
                        });
                    });
                    break;
                case "loginId":
                case "nmKo":
                    $("[data-col='" + key + "']", $rootNode).val(filterXSS(data[key]));
                    break;
                default:
                    $("[data-col='" + key + "']", $rootNode).html(filterXSS(data[key]));
                    break;
            }
        });
    };

    var loadContent = function () {
        if (paragonCmm.isEmpty(opts.loginId)) {
            setContent(opts);
        } else {
            $("input[name=loginId]").attr("readonly", true);
            model.getData({
                param: opts,
                cbFnc: function (json) {
                    setContent(json.data);
                }
            });
        }
    };

    var getFormData = function(){
        // CKEDITOR
        var params = $form.serializeObject();
        var tmpUAuths = [];
        if(params.userAuths){
            tmpUAuths = [].concat(params.userAuths);
        }
        params["userAuth"] = tmpUAuths.join(",");
        var tmpAAuths = [];
        if(params.adminAuths){
            tmpAAuths = [].concat(params.adminAuths);
        }
        params["adminAuth"] = tmpAAuths.join(",");
        console.debug(params);
        return params;
    };

    var encRSA = function(rsaData){

        // set RSA
        var opts = $.extend({
            publicKeyModulus: null,
            publicKeyExponent: null
        },rsaData);

        console.debug(opts);

        var rsa = new RSAKey();
        rsa.setPublic(opts.publicKeyModulus, opts.publicKeyExponent);

        // enc params
        var rtnData = {};
        var formDatas = getFormData();
        var keys = Object.keys(formDatas);
        keys.forEach(function (key) {
            if(key !== "userAuths" && key !== "adminAuths"){
                rtnData[key] = rsa.encrypt(formDatas[key]);
            }
        });

        return rtnData;
    };

    var encNSaveData = function(rsaData){

        // 데이터 저장
        model.saveData({
            params: encRSA(rsaData),
            cbFnc: function(json) {
                if (json.errYn === "E") {
                    alert(json.msg);//-- 오류처리
                    return false;
                }
                closeForm(json.data);
            }
        });
    };

    var initRSA = function(){
        model.initRsa({
            cbFnc: function(data){
                encNSaveData(data);
            }
        });
    };

    var saveData = function () {
        $.messager.confirm("Confirm", '정말 저장하시겠습니까?', function (r) {
            if (r) {
                initRSA();
            }
        });
    };

    var setFormEvent = function () {
        $form.on("submit", function () {
            closeForm();
            return false;
        });

        // check box
        // 일반사용자 기본권한
        $("input:checkbox[value="+CMM_ISJ+"]").attr("checked", true);
        $form.on("change", "input:checkbox", function(e){
            console.debug($(this).val());
            if($(this).val() === CMM_ISJ){
                $(this).prop("checked", true)
            }

            return false;
        });
    };

    var setBtnEvent = function () {
        $rootNode.off("click", "button");
        $rootNode.on("click", "button", function () {
            var btnId = $(this).attr("id");
            switch (btnId) {
                case "userMngViewCloseBtn1":
                    closeForm();
                    break;
                case "userMngViewSaveBtn1":
                    saveData();
                    break;
                default:
                    console.warn("not support btnId:" + btnId);
                    break;
            }

            return false; // 이벤트 확산금지
        });
    };

    var initForm = function () {
        // init check box
        initCheckbox();
    };
    var loadForm = function () {

        loadContent();

        // 1. form 다국어
        paragonCmm.convertLang($rootNode);

    };
    var setEvent = function () {
        setFormEvent();
        setBtnEvent();
    };

    // public functions ======================
    var init = function () {
        initForm();
        loadForm();
        setEvent();
    };
    return {
        init: init
    };
};

$(document).ready(function () {
    "use strict";
    var hrUserMngWrite = new HRUserMngWrite();
    hrUserMngWrite.init();
});