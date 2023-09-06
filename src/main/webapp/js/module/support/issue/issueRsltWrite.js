/*
 * @(#)issueRsltWrite.js     2023-04-03 오전 9:47
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
    let IssueRsltWriteModel = function(){
        const ISSUE_INFO_URL = paragonCmm.getUrl("/support/issue/viewInfo/json");
        const ISSUE_RSLT_URL = paragonCmm.getUrl("/support/issue/insertRslt/json");

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
            loadData: function(options){
                callAjax(options, ISSUE_INFO_URL);
            },
            saveData: function(options){
                submitAjax(options, ISSUE_RSLT_URL);
            }
        };
    };

    let IssueRsltWrite = function(){

        // ==============================================
        // attributes
        // ----------------------------------------------
        const RSLT_ISSUE_FILE_TYPE = "SD/MNG";
        let model = new IssueRsltWriteModel();
        let $rootNode = $("#issueRsltWriteRootLayer");

        let $writeForm = $("#issueRsltWriteForm", $rootNode);
        let $issueTpCombo = $("select[name=rsltRealIssueTpCd]", $writeForm);
        let $issueDomainCombo = $("select[name=rsltRealIssueDomainCd]", $writeForm);
        let openerData = $.extend({
            cbKey: null,
            issueId: null
        },$rootNode.data("opener-data"));
        $("input[name=issueId]", $writeForm).val(filterXSS(openerData.issueId));

        // ==============================================
        // private functions
        // ----------------------------------------------
        let closeForm = function(){
            if(typeof(opener) !== "undefined") {
                let cbKey = openerData.cbKey;
                let openerCbFnc = opener.paragonCmm.getCbFnc(cbKey);
                if(typeof openerCbFnc === "function"){
                    openerCbFnc();
                }
                self.close();
            }
        };

        let initEditor = function(){
            //editor
            htmlUtils.getHtmlEditor({
                sCtrlName: "rsltRport",
                sCtrlId: "rsltRport",
                sHeight: "250px"
            });
        };

        let initFile = function(){
            htmlUtils.loadFileHtml("issueRsltFile", {
                relUid: openerData.issueId,
                fileTpCd: RSLT_ISSUE_FILE_TYPE,
                defaultRelUid: ""
            });	//-- 첨부파일 로드
        };

        /**
         * 콤보박스(select) 설정
         * @param parentCd parent code
         * @param comboObj 콤보 Object
         * @param initTxt 초기값 명칭
         * @param valNm cd, cdAbb
         */
        let setComboBox = function(parentCd, comboObj, initTxt, valNm){

            valNm = (typeof valNm === "undefined") ? "cdAbb" : valNm;

            let initNm = (typeof initTxt === "undefined") ? paragonCmm.getLang("L.CBO_SELECT") : initTxt;

            htmlUtils.getCodeSelectOpt({
                targetId: comboObj,
                parentCd: parentCd,
                initdata:"|"+initNm,
                valNm: valNm,
                txtNm: "langCd"
            });
        };

        let initCombo = function(){
            // 1. 요청구분
            setComboBox("REQ_GUBUN", $issueTpCombo, "Select Issue Type");
            // 2. 요청분야
            setComboBox("REQ_BUNYA", $issueDomainCombo, "Select Issue Domain");
            // $issueDomainCombo.val("PR").prop("selected", true);
        };

        let setData = function(data){
            let keys = Object.keys(data);
            keys.forEach(function(key){
                switch (key) {
                    case "issueTpCd":
                        $issueTpCombo.val(data[key]).prop("selected", true);
                        break;
                    case "issueDomainCd":
                        $issueDomainCombo.val(data[key]).prop("selected", true);
                        break;
                    case "manday":
                        if(paragonCmm.isEmpty($("[data-col='rsltRealManday']", $rootNode).val())){
                            $("[data-col='rsltRealManday']", $rootNode).val(filterXSS(data[key]));
                        }
                        break;
                    default:
                        $("[data-col='" + key + "']", $rootNode).html(filterXSS(data[key]));
                        break;
                }
            });

            // set default val
            $("input[name=rcvExpManday]", $writeForm).val("1.0");
        }

        const validateData = function (data) {

            if (paragonCmm.isEmpty(data.rsltRealIssueTpCd)) {
                $.messager.alert("Warning", paragonCmm.getLang("M.필수_입력사항_입니다", '이슈 구분'), "warning", function () {
                    $issueTpCombo.focus();
                });
                return false;
            }

            if (paragonCmm.isEmpty(data.rsltRealIssueDomainCd)) {
                $.messager.alert("Warning", paragonCmm.getLang("M.필수_입력사항_입니다", '이슈 분야'), "warning", function () {
                    $issueDomainCombo.focus();
                });
                return false;
            }

            return true;
        };

        let saveData = function(){
            paragonCmm.setEditorSubmit("");
            let data = $writeForm.serializeObject();
            if (validateData(data)) {
                $.messager.confirm("Confirm", paragonCmm.getLang("M.ALERT_SUBMIT", "L.완료"), function (r) {
                    if (r) {
                        model.saveData({
                            params: data,
                            cbFnc: function (json) {
                                if (json.errYn === "E") {
                                    //-- 오류처리
                                    $.messager.alert("Error", json.msg, "error");
                                    return false;
                                } else {
                                    closeForm(json);
                                }
                            }
                        });
                    }
                });
            }
        };

        let initForm = function(){
            initEditor();
            initFile();
            initCombo();
        };

        let setBtnEvent = function(){
            $rootNode.off("click", "button");
            $rootNode.on("click", "button", function(){
                let btnId = $(this).attr("id");
                switch (btnId){
                    case "issueRsltWriteCloseBtn":
                        closeForm();
                        break;
                    case "issueRsltWriteSaveBtn":
                        saveData();
                        break;
                    default:
                        console.warn("not support btnId:"+btnId);
                        return;
                }

                return false; // 이벤트 확산금지
            });
        };

        let loadData = function(){
            model.loadData({
                params: {
                    issueId: openerData.issueId
                },
                cbFnc: function(json){
                    if (json.errYn === "E") {
                        //-- 오류처리
                        $.messager.alert("Error", json.msg,"error");
                        return false;
                    } else  {
                        setData(json.data);
                    }
                }
            });
        };

        let setEvent = function(){
            setBtnEvent();
        };
        // ==============================================
        // public functions
        // ----------------------------------------------
        let init = function(){
            initForm();
            loadData();
            setEvent();
        };

        return {
            init: init
        };
    };

    let issueRsltWrite = new IssueRsltWrite();
    issueRsltWrite.init();
});