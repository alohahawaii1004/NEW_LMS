/*
 * @(#)sysUserWrite.js     2023-03-30 오전 7:34
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

    let SysUserWriteModel = function(){

        const CREATE_SYS_USER_URL = paragonCmm.getUrl("/support/system/sysUserMng/create/json");

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

        return {
            saveData: function(options){
                callAjax(options, CREATE_SYS_USER_URL);
            }
        };
    };

    let SysUserWrite = function(){
        // ==============================================
        // attributes
        // ----------------------------------------------
        let model = new SysUserWriteModel();
        let $rootNode = $("#sysUsrWriteRootLayer");
        let $writeForm = $("#sysUsrWriteForm", $rootNode);
        let openerData = $.extend({},$rootNode.data("opener-data"));
        // $("input[name=orgId]", $writeForm).val(openerData.orgId);
        // $("input[name=systemId]", $writeForm).val(openerData.systemId);

        // ==============================================
        // private functions
        // ----------------------------------------------
        let closeForm = function (data) {
            let $modal = $("#" + openerData.modalId);
            let cbFncClose = $modal.data("callback");
            if (typeof cbFncClose === "function") {
                // 모달일 경우 닫기
                cbFncClose(data);
            } else {
                // 팝업일 경우 닫기
                self.close();
            }
        };

        let initEditor = function(){
            htmlUtils.getHtmlEditor({
                sCtrlName: "memo",
                sCtrlId: "memo",
                sHeight: "250px"
            });
        };

        let isValidate = function(data){
            console.debug(data);
            if(paragonCmm.isEmpty(data.nmKo)){
                $.messager.alert("Warning", paragonCmm.getLang("M.필수_입력사항_입니다", paragonCmm.getLang('L.이름')),"warning", function () {
                    $("input:text[name='nmKo']", $writeForm).focus();
                });
                return false;
            }
            return true;
        };

        let saveData = function(){
            paragonCmm.setEditorSubmit("");
            console.debug($writeForm);
            let data = $.extend($writeForm.serializeObject(), openerData);
            if(isValidate(data)){
                model.saveData({
                    params: data,
                    cbFnc: function(json){
                        if (json.errYn === "E") {
                            //-- 오류처리
                            $.messager.alert("Error", json.msg,"error");
                            return false;
                        } else  {
                            closeForm(json);
                        }
                    }
                });
            }
        };

        let setFormEvent = function(){
            $writeForm.on("submit", function(){
                saveData();
                return false;
            });
        };
        let setBtnEvent = function(){
            $rootNode.off("click", "button");
            $rootNode.on("click", "button", function(){
                let btnId = $(this).attr("id");
                switch (btnId){
                    case "sysUsrWriteCloseBtn":
                        closeForm();
                        break;
                    case "sysUsrWriteSaveBtn":
                        saveData();
                        break;
                    default:
                        console.warn("not support btnId:"+btnId);
                        break;
                }

                return false; // 이벤트 확산금지
            });
        };

        let setEvent = function(){
            setFormEvent();
            setBtnEvent();
        };

        // ==============================================
        // public functions
        // ----------------------------------------------
        let init = function(){
            initEditor();
            setEvent();
        };

        return {
            init: init
        };
    };

    let sysUserWrite = new SysUserWrite();
    sysUserWrite.init();
});