/*
 * @(#)commentWrite.js     2023-04-04 오전 8:22
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

    let CommentWriteModel = function(){
        const ISSUE_INFO_URL = paragonCmm.getUrl("/support/issue/viewInfo/json");
        const CMT_WRITE_URL = paragonCmm.getUrl("/support/cmm/comment/insertCmt/json");
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
                submitAjax(options, CMT_WRITE_URL);
            }
        };
    };

    let CommentWrite = function(){
        // ==============================================
        // attributes
        // ----------------------------------------------
        const COMMENT_FILE_TYPE = "CMM/CMM";
        let model = new CommentWriteModel();
        let $rootNode = $("#commentWriteRootLayer");

        let $writeForm = $("#commentWriteForm", $rootNode);
        let openerData = $.extend({
            cbKey: null,
            relId: null,
            relTpCd: null,
            cmtId: paragonCmm.getRandomUUID()
        },$rootNode.data("opener-data"));
        $("input[name=relId]", $writeForm).val(filterXSS(openerData.relId));
        $("input[name=cmtId]", $writeForm).val(filterXSS(openerData.cmtId));
        $("input[name=relTpCd]", $writeForm).val(filterXSS(openerData.relTpCd));

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
                sCtrlName: "cmtConts",
                sCtrlId: "cmtConts",
                sHeight: "250px"
            });
        };

        let initFile = function(){
            htmlUtils.loadFileHtml("commentFile", {
                relUid: openerData.cmtId,
                fileTpCd: COMMENT_FILE_TYPE,
                defaultRelUid: ""
            });	//-- 첨부파일 로드
        };

        let setData = function(data){
            let keys = Object.keys(data);
            keys.forEach(function(key){
                switch (key) {
                    default:
                        $("[data-col='" + key + "']", $rootNode).html(filterXSS(data[key]));
                        break;
                }
            });
        }

        let saveData = function(){
            paragonCmm.setEditorSubmit("");
            let data = $writeForm.serializeObject();
            $.messager.confirm("Confirm", paragonCmm.getLang("M.ALERT_SUBMIT", "L.의견등록"), function (r) {
                if (r) {
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
            });
        };

        let setBtnEvent = function(){
            $rootNode.off("click", "button");
            $rootNode.on("click", "button", function(){
                let btnId = $(this).attr("id");
                switch (btnId){
                    case "commentWriteCloseBtn":
                        closeForm();
                        break;
                    case "commentWriteSaveBtn":
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
        };
        let loadData = function(){
            model.loadData({
                params: {
                    issueId: openerData.relId
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

    let commentWrite = new CommentWrite();
    commentWrite.init();
});