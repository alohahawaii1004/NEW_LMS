/*
 * @(#)systemWrite.js     2023-03-13 013 오전 11:04:35
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

var SmSysMngWriteModel = function(){
    "use strict";
    const LOAD_DATA_URL = paragonCmm.getUrl("/support/system/systemMng/loadData/json");
    const SAVE_URL = paragonCmm.getUrl("/support/system/systemMng/saveData/json");

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

var SmSysMngWrite = function(){
    "use strict";
    // ==============================================
    // attributes
    // ----------------------------------------------
    let model = new SmSysMngWriteModel();
    let $rootNode = $("#smSysMngWriteRootLayer");
    let $writeForm = $("#smSysMngWriteForm", $rootNode);
    let $orgNm = $("input[name=orgNm]", $writeForm);
    let $orgId = $("input[name=orgId]", $writeForm);
    let $tpCombo = $("select[name=productTpCd]", $writeForm);
    let $smStartDte = $("input[name=smStartdt]", $writeForm);
    let $smEndDte = $("input[name=smEnddt]", $writeForm);
    let openerData = $.extend({
        orgId: null,
        systemId: null,
        cbKey: null
    }, $rootNode.data("opener-data"));

    // ==============================================
    // private functions
    // ----------------------------------------------

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
            sCtrlName: "sysDesc",
            sCtrlId: "sysDesc",
            sHeight: "250px"
        });
    };

    let setRadioVal = function (key, val) {
        $("input:radio[name=" + key + "]:input[value=" + val + "]", $rootNode).attr("checked", true);
    };

    let openModal = function(){

        let options = {
            title: "회사선택",
            href: paragonCmm.getUrl("/support/cmm/customerList.include"),
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

    let setContent = function(data){

        if(paragonCmm.isNotEmpty(data)){
            $("#smSysMngWriteSaveBtn").html('<i class="fa fa-pencil"></i> 저장');
        }

        let keys = Object.keys(data);

        keys.forEach(function(key){
            switch (key) {
                case "sysDesc":
                    paragonCmm.setEditorValue(key, data[key]);
                    $("[data-col='"+key+"']", $rootNode).val(data[key]);
                    break;
                case "productTpCd":
                    $tpCombo.combobox("select", data[key]);
                    break;
                case "smStartdt":
                    $smStartDte.datebox("setValue", data[key]);
                    break;
                case "smEnddt":
                    $smEndDte.datebox("setValue", data[key]);
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

        if(paragonCmm.isNotEmpty(openerData.orgId)){

        }else{
            model.loadData({
                params: {
                    orgId: openerData.orgId,
                    systemId: openerData.systemId
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
        }

    };

    let isValidate = function(data){
        console.debug(data);
        if(paragonCmm.isEmpty(data.orgId)){
            $.messager.alert("Warning", paragonCmm.getLang("M.필수_입력사항_입니다", paragonCmm.getLang('L.이름')),"warning", function () {
                $("#smSysMngWriteOrgSchBtn", $writeForm).focus();
            });
            return false;
        }
        if(paragonCmm.isEmpty(data.systemName)){
            $.messager.alert("Warning", paragonCmm.getLang("M.필수_입력사항_입니다", paragonCmm.getLang('L.이름')),"warning", function () {
                $("input:text[name='systemName']", $writeForm).focus();
            });
            return false;
        }
        if(paragonCmm.isEmpty(data.productTpCd)){
            $.messager.alert("Warning", paragonCmm.getLang("M.필수_입력사항_입니다", paragonCmm.getLang('L.이름')),"warning", function () {
                $("select[name='productTpCd']", $writeForm).focus();
            });
            return false;
        }
        if(paragonCmm.isEmpty(data.smStartdt) || paragonCmm.isEmpty(data.smEnddt)){
            $.messager.alert({
                title: "Warning",
                msg: paragonCmm.getLang("M.필수_입력사항_입니다", "지원기간"),
                icon: "warning",
                fn: function () {
                    $smStartDte.datebox('textbox').focus();
                }
            });
            return false;
        }

        var fromDate = new Date(data.smStartdt);
        var toDate = new Date(data.smEnddt);

        if ( fromDate.getTime() > toDate.getTime()) {
            $.messager.alert({
                title: "Warning",
                msg: "기간이 올바르지 않습니다.",
                icon: "warning",
                fn: function () {
                    $smStartDte.datebox('textbox').focus();
                }
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
                case "smSysMngWriteOrgSchBtn":
                    openModal();
                    break;
                case "smSysMngWriteCloseBtn":
                    closeForm();
                    break;
                case "smSysMngWriteSaveBtn":
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

        $tpCombo.combobox({
            mode: 'remote',
            valueField:'value',
            textField:'label',
            prompt: "System Type",
            loader: function (param, succ) {
                let sField = "LANG_CD";
                // if (!param.q) {
                //     sField = "LANG_CD";
                //     // return;
                // }
                $.ajax({
                    url: paragonCmm.getUrl("/viself/code/CodeMng/listCode/json"),
                    method: "post",
                    data: {
                        schField: sField,
                        schValue: param.q,
                        parentCd: "SD_SYSTEM_GBN"
                    },
                    dataType: 'json',
                    success: function (data) {
                        var rows = $.map(data.data, function (item) {
                            return {
                                label: item.langCd,
                                value: item.cd,
                                code: item.cdAbb
                            };
                        });
                        succ(rows);
                    }
                })
            }
        });

        initEditor();
        // 지원일
        $smStartDte.datebox({validType: "validDate", prompt: "Input Support Date"});// easyui
        $smEndDte.datebox({validType: "validDate", prompt: "Input Support Date"});// easyui
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
    let systemWrite = new SmSysMngWrite();
    systemWrite.init();
    window.resizeTo(864,700);
});