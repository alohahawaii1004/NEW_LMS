/*
 * @(#)issueCfmWrite.js     2023-04-03 오전 11:19
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

    let IssueCfmWriteModel = function(){
        const ISSUE_INFO_URL = paragonCmm.getUrl("/support/issue/viewInfo/json");
        const ISSUE_CFM_URL = paragonCmm.getUrl("/support/issue/insertCfm/json");
        const ISSUE_REJECT_URL = paragonCmm.getUrl("/support/issue/insertReject/json");

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
                submitAjax(options, ISSUE_CFM_URL);
            },
            rejectData: function(options){
                submitAjax(options, ISSUE_REJECT_URL);
            }
        };
    };

    let IssueCfmWrite = function(){
        // ==============================================
        // attributes
        // ----------------------------------------------
        const CFM_ISSUE_FILE_TYPE = "SD/CFM";
        let model = new IssueCfmWriteModel();
        let $rootNode = $("#issueCfmWriteRootLayer");
        let $writeForm = $("#issueCfmWriteForm", $rootNode);
        let openerData = $.extend({
            cbKey: null,
            issueId: null
        },$rootNode.data("opener-data"));
        $("input[name=issueId]", $writeForm).val(filterXSS(openerData.issueId));

        const rating_input = document.querySelector('.rating input');
        const rating_star = document.querySelector('.rating_star');

        // ==============================================
        // private functions
        // ----------------------------------------------
        // 별점 드래그 할 때
        rating_input.addEventListener('input', () => {
            rating_star.style.width = `${rating_input.value * 10}%`;
        });

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
                sCtrlName: "cfmComment",
                sCtrlId: "cfmComment",
                sHeight: "250px"
            });
        };

        let initFile = function(){
            htmlUtils.loadFileHtml("issueCfmFile", {
                relUid: "",
                fileTpCd: CFM_ISSUE_FILE_TYPE,
                defaultRelUid: openerData.issueId
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

        let getStarVal = function(){
            let rate = rating_star.style.width;
            rate = rate.replace("%","");
            if(paragonCmm.isEmpty(rate)){
                rate = "0";
            }
            let bigRate = new Big(parseInt(rate,10));
            return (bigRate.div(100)).times(5).round(1).valueOf();
        };

        let saveData = function(){
            paragonCmm.setEditorSubmit("");
            let data = $writeForm.serializeObject();
            data.cfmSvcVal = getStarVal();

            $.messager.confirm("Confirm", paragonCmm.getLang("M.ALERT_SUBMIT", "L.검수"), function (r) {
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

        let rejectData = function(){
            paragonCmm.setEditorSubmit("");
            let data = $writeForm.serializeObject();
            data.cfmSvcVal = getStarVal();

            $.messager.confirm("Confirm", paragonCmm.getLang("M.ALERT_SUBMIT", "L.반려"), function (r) {
                if (r) {
                    model.rejectData({
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
                    case "issueCfmWriteCloseBtn":
                        closeForm();
                        break;
                    case "issueCfmRejectSaveBtn":
                        rejectData();
                        break;
                    case "issueCfmWriteSaveBtn":
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

    let issueCfmWrite = new IssueCfmWrite();
    issueCfmWrite.init();
});