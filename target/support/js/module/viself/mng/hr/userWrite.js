/*
 * @(#)userWrite.js     2022-07-25(025) 오전 11:09:11
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

var UserMngWriteModel = function () {
    "use strict";
    var SAVE_URL = paragonCmm.getUrl("/ssc/hr/UserMng/save/json");
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

var UserMngWrite = function () {
    "use strict";
    // attributes ============================
    // 일반사용자(기본권한)
    var CMM_ISJ = "CMM_ISJ";
    var model = new UserMngWriteModel();
    var $rootNode = $("#userMngWriteRootLayer");
    var $form = $("#userMngWriteForm1");
    var $userAuthArea = $("#userAuthArea", $form);
    var $fromDte = $("input[name=useStDte]", $form);
    var $toDte = $("input[name=useEdDte]", $form);

    var opts = $.extend({
        loginId: null
    }, $rootNode.data("opener-data"));

    var userAuths = [];

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
        // 일반사용자 권한 체크박스 구성
        $userAuthArea.html(htmlUtils.getInputCheckbox("userAuths", htmlUtils.getCodeStrFromData(userAuths, "authCd,authNm")));
    };

    var initCalander = function () {
        $fromDte.datebox({validType: "validDate"});
        $toDte.datebox({validType: "validDate"});
    };

    var setContent = function (data) {
        var keys = Object.keys(data);
        keys.forEach(function (key) {
            switch (key) {
                case "strUseStDte":
                    $fromDte.datebox("setValue", filterXSS(data[key]));
                    break;
                case "strUseEdDte":
                    $toDte.datebox("setValue", filterXSS(data[key]));
                    break;
                case "authCd":
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

    var checkValidation = function () {
        // 1. ID
        if (paragonCmm.isEmpty($form.find("input[name=loginId]").val())) {
            $.messager.alert({
                title: "Warning",
                msg: paragonCmm.getLang("M.필수_입력사항_입니다", paragonCmm.getLang('L.ID')),
                icon: "warning",
                fn: function () {
                    $form.find("input[name=loginId]").focus();
                }
            });
            return false;
        }

        // 2. 이름
        if (paragonCmm.isEmpty($form.find("input[name=nmKo]").val())) {
            $.messager.alert({
                title: "Warning",
                msg: paragonCmm.getLang("M.필수_입력사항_입니다", paragonCmm.getLang('L.이름')),
                icon: "warning",
                fn: function () {
                    $form.find("input[name=nmKo]").focus();
                }
            });
            return false;
        }

        // 3. 이메일
        if (paragonCmm.isEmpty($form.find("input[name=email]").val())) {
            $.messager.alert({
                title: "Warning",
                msg: paragonCmm.getLang("M.필수_입력사항_입니다", paragonCmm.getLang('L.이메일')),
                icon: "warning",
                fn: function () {
                    $form.find("input[name=email]").focus();
                }
            });
            return false;
        }

        // 4. 사용자권한
        var tmpAuths = [];
        var param = $form.serializeObject();
        if(param.userAuths){
            tmpAuths = [].concat(param.userAuths);
        }

        console.debug(param);
        console.debug(tmpAuths);

        if (tmpAuths.length === 0) {
            $.messager.alert({
                title: "Warning",
                msg: paragonCmm.getLang("M.필수_입력사항_입니다", paragonCmm.getLang('L.사용자권한')),
                icon: "warning"
            });
            return false;
        }

        // 5. 사용기간
        var fromDte = $fromDte.datebox("getValue");
        var toDte = $toDte.datebox("getValue");

        if (paragonCmm.isEmpty(fromDte) && paragonCmm.isEmpty(toDte)) {
            $.messager.alert({
                title: "Warning",
                msg: paragonCmm.getLang("M.필수_입력사항_입니다", paragonCmm.getLang('L.사용기간')),
                icon: "warning",
                fn: function () {
                    $fromDte.datebox('textbox').focus();
                }
            });
            return false;
        }

        var fromDate = new Date(fromDte);
        var toDate = new Date(toDte);
        if ( fromDate.getTime() > toDate.getTime()) {
            $.messager.alert({
                title: "Warning",
                msg: "기간이 올바르지 않습니다.",
                icon: "warning",
                fn: function () {
                    $fromDte.datebox('textbox').focus();
                }
            });
            return false;
        }


        return true;
    };

    var getFormData = function(){
        // CKEDITOR
        var params = $form.serializeObject();
        var tmpAuths = [];
        if(params.userAuths){
            tmpAuths = [].concat(params.userAuths);
        }
        params["userAuth"] = tmpAuths.join(",");
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
            if(key !== "userAuths"){
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
        if (!checkValidation()) {
            return false;
        }

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
                case "userMngWriteCloseBtn1":
                    closeForm();
                    break;
                case "userMngWriteSaveBtn1":
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
        // init datebox
        initCalander();
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
    var userMngWrite = new UserMngWrite();
    userMngWrite.init();
});