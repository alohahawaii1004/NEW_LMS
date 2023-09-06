/*
 * @(#)issueReqWrite.js     2023-03-30 오후 1:38
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
    let IssueReqWriteModel = function(){

        const ISSUE_INFO_URL = paragonCmm.getUrl("/support/issue/viewInfo/json");
        const GET_SYS_INFO_URL = paragonCmm.getUrl("/support/cmm/system/loadData/json");
        const SYS_SEARCH_URL = paragonCmm.getUrl("/support/cmm/systemList.include");
        const SAVE_URL = paragonCmm.getUrl("/support/issue/insertReq/json");

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
            paragonCmm.submitAjax(opts.url, opts.params, opts.cbFnc, true, opts.errorBack);
        };

        return {
            loadData: function(options){
                callAjax(options, ISSUE_INFO_URL);
            },
            getSysSchUrl: function(){
                return SYS_SEARCH_URL;
            },
            getSystemInfo: function(options){
                callAjax(options, GET_SYS_INFO_URL);
            },
            saveData: function(options){
                submitAjax(options, SAVE_URL);
            }
        };
    };

    let IssueReqWrite = function(){
        // ==============================================
        // attributes
        // ----------------------------------------------
        const REQ_ISSUE_FILE_TYPE = "SD/REQ";
        let model = new IssueReqWriteModel();
        let $rootNode = $("#issueReqWriteRootLayer");
        let $writeForm = $("#issueReqWriteForm", $rootNode);
        let $issueTpCombo = $("select[name=reqIssueTpCd]", $writeForm);
        let $issueDomainCombo = $("select[name=reqIssueDomainCd]", $writeForm);
        let $reqEndDte = $("input[name=reqEndDte]", $writeForm);
        let $systemNm = $("input[name=systemNm]", $writeForm);
        let $systemId = $("input[name=systemId]", $writeForm);
        let openerData = $.extend({
            issueId: null,
            cbKey: null
        }, $rootNode.data("opener-data"));
        let writerInfo = paragonCmm.formMap.get("loginInfo");
        let issueId = (paragonCmm.isEmpty(openerData.issueId)) ? paragonCmm.getRandomUUID(): openerData.issueId;
        openerData.issueId = issueId;
        $("input[name=issueId]").val(issueId);

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

        let initEditor = function(){
            //editor
            htmlUtils.getHtmlEditor({
                sCtrlName: "reqIssueConts",
                sCtrlId: "reqIssueConts",
                sHeight: "250px"
            });
        };

        let initFile = function(){
            htmlUtils.loadFileHtml("issueReqFile", {
                relUid: issueId,
                fileTpCd: REQ_ISSUE_FILE_TYPE,
                defaultRelUid: issueId
            });	//-- 첨부파일 로드
        };

        let initCombo = function(){
            // 1. 요청구분
            setComboBox("REQ_GUBUN", $issueTpCombo, "Select Issue Type");
            // 2. 요청분야
            setComboBox("REQ_BUNYA", $issueDomainCombo, "Select Issue Domain");
            $issueDomainCombo.val("PR").prop("selected", true);
        };

        let initDatebox = function(){
            $reqEndDte.datebox({validType: "validDate", prompt: "desired completion date"});
        };

        let initSystem = function(){
            model.getSystemInfo({
                param: null,
                cbFnc: function(json){
                    if (json.errYn === "E") {
                        //-- 오류처리
                        // $.messager.alert("Error", json.msg,"error");
                        return false;
                    } else  {
                        if(paragonCmm.isNotEmpty(json.data)){
                            $systemId.val(json.data.systemId);
                            $systemNm.val(json.data.systemName);
                            $("#issueReqWriteSysSchBtn").attr("disabled", true);
                        }
                    }
                }
            });
        };

        let openSysSch = function(){
            let options = {
                title: "시스템선택",
                href: model.getSysSchUrl(),
                width: 864,
                height: "auto",
                top: "10px",
                cbFnc: function(data){

                    console.debug(data);
                    let datas = [].concat(data);

                    $systemId.val(datas[0].systemId);
                    $systemNm.val(datas[0].systemName);
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

        let setData = function(data){
            if(paragonCmm.isNotEmpty(data)){
                let keys = Object.keys(data);
                keys.forEach(function(key){
                    switch (key) {
                        case "issueTpCd":
                            $issueTpCombo.val(data[key]).prop("selected", true);
                            break;
                        case "issueDomainCd":
                            $issueDomainCombo.val(data[key]).prop("selected", true);
                            break;
                        case "reqEndDte":
                            $reqEndDte.datebox("setValue", filterXSS(data[key]));
                            break;
                        default:
                            $("[data-col='" + key + "']", $rootNode).val(filterXSS(data[key]));
                            break;
                    }
                });
            }
        };

        let chkValidate = function(data){
            if(paragonCmm.isEmpty(data.reqIssueTitle)){
                $.messager.alert("Warning", paragonCmm.getLang("M.필수_입력사항_입니다", paragonCmm.getLang('L.제목')),"warning", function () {
                    $("input:text[name='reqIssueTitle']", $writeForm).focus();
                });
                return false;
            }

            if(paragonCmm.isEmpty(data.systemId)){
                $.messager.alert("Warning", paragonCmm.getLang("M.필수_입력사항_입니다", paragonCmm.getLang('L.시스템')),"warning", function () {
                    $("#issueReqWriteSysSchBtn", $writeForm).focus();
                });
                return false;
            }
            return true;
        };

        let saveData = function(){
            paragonCmm.setEditorSubmit("");
            let data = $writeForm.serializeObject();
            if(chkValidate(data)){
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
                    case "issueReqWriteSysSchBtn":
                        openSysSch();
                        break;
                    case "issueReqWriteCloseBtn":
                        closeForm();
                        break;
                    case "issueReqWriteSaveBtn":
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
            initFile();
            initCombo();
            initDatebox();
            initSystem();
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
            setFormEvent();
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

    let issueReqWrite = new IssueReqWrite();
    issueReqWrite.init();
});