/*
 * @(#)customerWrite.js     2023-03-13 013 오전 11:04:35
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

var CustomerWriteModel = function(){
    "use strict";
    const LOAD_DATA_URL = paragonCmm.getUrl("/support/customer/customerMng/loadData/json");
    const SAVE_URL = paragonCmm.getUrl("/support/customer/customerMng/saveData/json");

    let getOpts = function(options, url){
        return $.extend({
            url: url,
            params: null,
            cbFnc: function (json) {
            },
            async: false,
            errorBack: function(){}
        }, options);
    };
    let callAjax = function(options, url){
        let opts = getOpts(options, url);
        paragonCmm.callAjax(opts.url, opts.params, opts.cbFnc);
    };

    let submitAjax = function(options, url){
        let opts = getOpts(options, url);
        paragonCmm.submitAjax(opts.url, opts.params, opts.cbFnc, opts.async, opts.errorBack);
    };

    let loadData = function(options){
        callAjax(options, LOAD_DATA_URL);
    };

    let saveData = function(options){
        submitAjax(options, SAVE_URL);
    };

    return {
        loadData: loadData,
        saveData: saveData
    };
};

var CustomerWrite = function(){
    "use strict";
    // ==============================================
    // attributes
    // ----------------------------------------------
    let model = new CustomerWriteModel();
    let $rootNode = $("#customerMngWriteRootLayer");
    let $writeForm = $("#customerMngWriteForm", $rootNode);
    let openerData = $.extend({
        orgId: null,
        cbKey: null
    }, $rootNode.data("opener-data"));

    // ==============================================
    // private functions
    // ----------------------------------------------

    let closeForm = function(data){
        debugger;
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
            sCtrlName: "orgDesc",
            sCtrlId: "orgDesc",
            sHeight: "250px"
        });
    };

    let setRadioVal = function (key, val) {
        $("input:radio[name=" + key + "]:input[value=" + val + "]", $rootNode).attr("checked", true);
    };

    let setContent = function(data){
        if(paragonCmm.isNotEmpty(data)){
            $("#customerMngWriteSaveBtn").html('<i class="fa fa-pencil"></i> 저장');
        }
        let keys = Object.keys(data);

        keys.forEach(function(key){
            switch (key) {
                case "orgDesc":
                    paragonCmm.setEditorValue(key, data[key]);
                    $("[data-col='"+key+"']", $rootNode).val(data[key]);
                    break;
                case "useYn":
                    setRadioVal(key, data[key]);
                    break;
                default:
                    $("[data-col='"+key+"']", $rootNode).val(data[key]);
                    break;
            }
        });
    };

    let loadData = function () {
        model.loadData({
            params: {
                orgId: openerData.orgId
            },
            cbFnc: function(json){
                if (json.errYn === "E") {
                    //-- 오류처리
                    $.messager.alert("Error", json.msg,"error");
                    return false;
                } else  {
                    setContent($.extend({}, json.data));
                }
            }
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
        let data = $writeForm.serializeObject();
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
                case "customerMngWriteCloseBtn":
                    closeForm();
                    break;
                case "customerMngWriteSaveBtn":
                    saveData();
                    break;
                default:
                    console.warn("not support btnId:"+btnId);
                    break;
            }

            return false; // 이벤트 확산금지
        });
    };

    let initForm = function(){
        initEditor();
    };
    let loadForm = function(){
        loadData();
    };
    let setEvent = function(){
        setFormEvent();
        setBtnEvent();
    };

    // ==============================================
    // public functions
    // ----------------------------------------------
    var init = function(){
        initForm();
        loadForm();
        setEvent();
    };
    return {
        init: init
    };
};

$(function(){
    "ues strict";
    let customerWrite = new CustomerWrite();
    customerWrite.init();
});