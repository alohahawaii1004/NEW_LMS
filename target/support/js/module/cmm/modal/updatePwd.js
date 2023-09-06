/*
 * @(#)updatePwd.js     2023-04-06 오후 3:37
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

    let UpdatePwdModel = function(){

        const SAVE_URL = paragonCmm.getUrl("/cmm/modal/user/updatePassword/json");
        const RSA_INIT_URL = paragonCmm.getUrl("/rsa/init");

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

        return {
            initRsa: function(options){
                callAjax(options, RSA_INIT_URL);
            },
            saveData: function(options){
                callAjax(options, SAVE_URL);
            }
        };
    };

    let UpdatePwd = function(){
        // attributes ###################################
        const STR_REG_EXP_UPPER = "A-Z";
        const STR_REG_EXP_LOWER = "a-z";
        const STR_REG_EXP_DIGIT = "0-9";
        const STR_REG_EXP_SPECIAL_CHAR = "`~!@#$%^&*()\\-=_+\\[\\]{};:',./<>?\\\\|\"";
        const REG_EXP_PATTERNS = [STR_REG_EXP_UPPER, STR_REG_EXP_LOWER, STR_REG_EXP_DIGIT, STR_REG_EXP_SPECIAL_CHAR];

        let model = new UpdatePwdModel();
        let $rootNode = $("#usrPwdWriteRootLayer");
        let $writeForm = $("#usrPwdWriteForm", $rootNode);
        // let $oldPwd = $();
        // let $newPwd = $();
        // let $cfmPwd = $();
        let $loginId = $("input[name=loginId]", $writeForm);
        let openerData = $.extend({
            loginId: null
        }, $rootNode.data("opener-data"));


        // private functions ############################
        var closeForm = function (data) {
            var $modal = $("#" + openerData.modalId);
            var cbFncClose = $modal.data("callback");
            if (typeof cbFncClose === "function") {
                // 모달일 경우 닫기
                cbFncClose(data);
            } else {
                // 팝업일 경우 닫기
                self.close();
            }
        };

        let getFormData = function(){
            // CKEDITOR
            let data =  $writeForm.serializeObject();
            data.loginId = openerData.loginId;
            return data;
        };

        let encRSA = function(rsaData){

            // set RSA
            let opts = $.extend({
                publicKeyModulus: null,
                publicKeyExponent: null
            },rsaData);

            // console.debug(opts);

            let rsa = new RSAKey();
            rsa.setPublic(opts.publicKeyModulus, opts.publicKeyExponent);

            // enc params
            let rtnData = {};
            let formDatas = getFormData();
            // console.debug(openerData);
            // console.debug(formDatas);
            let keys = Object.keys(formDatas);
            keys.forEach(function (key) {
                rtnData[key] = rsa.encrypt(formDatas[key]);
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

        let checkPolicy = function(minLength, minMixCount, pw){
            let checkFlag = false;
            let iMinMixCnt = (minMixCount > 4) ? 4 : minMixCount;
            let mixCnt = 0;

            let strRegExp = "";
            strRegExp += "[";
            strRegExp += STR_REG_EXP_UPPER;
            strRegExp += STR_REG_EXP_LOWER;
            strRegExp += STR_REG_EXP_DIGIT;
            strRegExp += STR_REG_EXP_SPECIAL_CHAR;
            strRegExp += "]";
            strRegExp += "{";
            strRegExp += minLength;
            strRegExp += ",}$";
            let policy = new RegExp(strRegExp);

            // DEBUG
            // console.log("pw="+pw);
            // console.log("strRegExp="+strRegExp);
            // console.log("policy.test(pw)="+policy.test(pw));

            if(policy.test(pw)) {

                for (let i = 0; i < 4; i += 1) {

                    strRegExp = "";
                    strRegExp += "(?=.*[";
                    strRegExp += REG_EXP_PATTERNS[i];
                    strRegExp += "])";
                    strRegExp += "[";
                    strRegExp += STR_REG_EXP_UPPER;
                    strRegExp += STR_REG_EXP_LOWER;
                    strRegExp += STR_REG_EXP_DIGIT;
                    strRegExp += STR_REG_EXP_SPECIAL_CHAR;
                    strRegExp += "]";
                    strRegExp += "{";
                    strRegExp += minLength;
                    strRegExp += ",}$";

                    policy = new RegExp(strRegExp);

                    // DEBUG
                    // console.log("strRegExp="+strRegExp);
                    // console.log("policy.test(pw)="+policy.test(pw));

                    if (policy.test(pw)) {
                        mixCnt += 1;
                    }

                    if (mixCnt >= iMinMixCnt) {
                        checkFlag = true;
                        break;
                    }
                }// end of for
            }

            // console.debug("mixCnt="+mixCnt);
            // console.debug("checkPolicy(minLength:"+minLength+",minMixCount:"+minMixCount+")="+checkFlag);

            return checkFlag;
        };

        let isValidate = function(data){
            // if(paragonCmm.isEmpty(data.oldPwd)){
            //     $.messager.alert("Warning", paragonCmm.getLang("M.필수_입력사항_입니다", "이전암호"),"warning", function () {
            //         $("input[name=oldPwd]", $writeForm).focus();
            //     });
            //     return false;
            // }
            if(paragonCmm.isEmpty(data.newPwd)){
                $.messager.alert("Warning", paragonCmm.getLang("M.필수_입력사항_입니다", "신규암호"),"warning", function () {
                    $("input[name=newPwd]", $writeForm).focus();
                });
                return false;
            }
            if(paragonCmm.isEmpty(data.cfmPwd)){
                $.messager.alert("Warning", paragonCmm.getLang("M.필수_입력사항_입니다", "암호확인"),"warning", function () {
                    $("input[name=cfmPwd]", $writeForm).focus();
                });
                return false;
            }

            // if(data.newPwd !== data.cfmPwd){
            //     $.messager.alert("Warning", "암호가 일치하지 않습니다.","warning", function () {
            //         $("input[name=newPwd]", $writeForm).focus();
            //     });
            //     return false;
            // }

            if(!checkPolicy(10, 2, data.newPwd) &&
                    !checkPolicy(8, 3, data.newPwd)){
                $.messager.alert("Warning", "암호는 영문 대/소문자, 숫자, 특수문자 3가지 조합 8자리 이상, 2가지 조합 10자리 이상 입력해주세요.","warning", function () {
                    $("input[name=newPwd]", $writeForm).focus();
                });
                return false;
            }

            return true;
        };

        let changePwd = function (){
            if (!isValidate(getFormData())) {
                return false;
            }

            $.messager.confirm("Confirm", '정말 저장하시겠습니까?', function (r) {
                if (r) {
                    initRSA();
                }
            });
        };

        let initForm = function(){
            $loginId.val(filterXSS(openerData.loginId));
        };

        let setEvent = function(){
            $rootNode.off("click", "button");
            $rootNode.on("click", "button", function () {
                var btnId = $(this).attr("id");
                switch (btnId) {
                    case "usrPwdWriteCloseBtn":
                        closeForm();
                        break;
                    case "usrPwdWriteSaveBtn":
                        changePwd();
                        break;
                    default:
                        break;
                }
                return false;
            });
        };
        // public functions #############################
        let init = function(){
            initForm();
            setEvent();
        };
        return {
            init: init
        };
    };

    let updatePwd = new UpdatePwd();
    updatePwd.init();
});