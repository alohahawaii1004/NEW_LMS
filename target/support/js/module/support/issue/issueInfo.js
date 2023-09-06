/*
 * @(#)issueInfo.js     2023-03-12 012 오후 12:13:16
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

    let IssueInfoModel = function(){

        const ISSUE_INFO_URL = paragonCmm.getUrl("/support/issue/viewInfo/json");
        const ISSUE_DELETE_URL = paragonCmm.getUrl("/support/issue/deleteIssue/json");
        const ISSUE_MODIFY_URL = paragonCmm.getUrl("/support/issue/issueReqWrite.popup");
        const ISSUE_RCV_URL = paragonCmm.getUrl("/support/issue/issueRcvWrite.popup");
        const ISSUE_RSLT_URL = paragonCmm.getUrl("/support/issue/issueRsltWrite.popup");
        const ISSUE_CFM_URL = paragonCmm.getUrl("/support/issue/issueCfmWrite.popup");

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
            deleteData: function(options){
                callAjax(options, ISSUE_DELETE_URL);
            },
            getModifyUrl: function(){
                return ISSUE_MODIFY_URL;
            },
            getRcvUrl: function(){
                return ISSUE_RCV_URL;
            },
            getRsltUrl: function(){
                return ISSUE_RSLT_URL;
            },
            getCfmUrl: function(){
                return ISSUE_CFM_URL;
            }
        };
    };

    let SysCommentModel = function(){

        const SYS_COMMENT_LIST_URL = paragonCmm.getUrl("/support/cmm/comment/getList/json");
        const COMMENT_WRITE_URL = paragonCmm.getUrl("/support/cmm/commentWrite.popup");

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
                callAjax(options, SYS_COMMENT_LIST_URL);
            },
            getCommentUrl: function(){
                return COMMENT_WRITE_URL;
            }
        };
    };

    let SysComment = function (id, relId){
        let model = new SysCommentModel();
        let $root = $("#"+id);
        let $messages = $(".direct-chat-messages", $root);

        let setData = function(data){
            console.debug(data);
            let comments = [].concat(data);
            let strHtml = "";
            let msgClass = "";
            let nameClass = "";
            let timestampClass = "";
            let msgIcon = "";
            comments.forEach(function(itm){
                // msgIcon = '<i class="fa-solid fa-user-tie" style="color: #003A9D"></i> ';
                msgIcon = '<span class="badge badge-default" style="background-color: #B8D7FF; color: #003A9D;"><i class="fa-solid fa-user-tie"></i></span> ';
                if(itm.regTp === "U"){
                    msgIcon = '<i class="fa-solid fa-user"></i> ';
                }
                msgClass = "";
                nameClass = "float-left";
                timestampClass = "float-right";

                if(itm.cmtCateCd === "R"){
                    msgClass = "right";
                    nameClass = "float-right";
                    timestampClass = "float-left";
                }
                strHtml += '<div class="direct-chat-msg '+msgClass+'">';
                strHtml += '    <div class="direct-chat-infos clearfix">';
                strHtml += '        <span class="direct-chat-name '+nameClass+'">'+msgIcon+filterXSS(itm.cmtRegNm)+'</span>';
                strHtml += '        <span class="direct-chat-timestamp '+timestampClass+'">'+filterXSS(itm.strRegDte)+'</span>';
                strHtml += '    </div>';
                strHtml += '    <div class="direct-chat-text">';
                strHtml += itm.cmtConts;
                strHtml += '<div id="cmtFiles_'+filterXSS(itm.cmtId)+'"></div>';
                strHtml += '    </div>';
                strHtml += '</div>';
            });

            $("span[data-col=commentsCnt]").html(comments.length);

            $messages.html(strHtml);

            // CMM/CMM

            comments.forEach(function(itm){
                htmlUtils.loadFileView("cmtFiles_"+itm.cmtId, {
                    relUid: itm.cmtId,
                    fileTpCd: "CMM/CMM",
                    defaultRelUid: "",
                    requiredYn: ""
                });
            });
        };

        let openPopup = function(){

            let cbFnc  = paragonCmm.getCbFnc("comment_"+relId);
            if(typeof cbFnc != "function"){
                paragonCmm.setCbFnc("comment_"+relId, init);
            }
            htmlUtils.openPopup({
                url: model.getCommentUrl(),
                targetName: "POPUP_CMT_WRITE_"+relId,
                popupTitle: "의견등록",
                popWidth: 864,
                popHeight: 540,
                openerData: {
                    cbKey: "comment_"+relId,
                    relId: relId,
                    relTpCd: "ISSUE"
                }
            });
        };

        let loadData = function(){
            model.loadData({
                params: {
                    relId: relId
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
            $root.off("click", "button");
            $root.on("click", "button", function(){
                let btnId = $(this).data("btn");
                switch (btnId){
                    case "addComment":
                        openPopup(btnId);
                        break;
                    default:
                        console.debug("not support btnId:"+btnId);
                        return;
                }

                return false; // 이벤트 확산금지
            });
        };

        let init = function(){
            loadData();
            setEvent();
        };
        return {
            init: init
        };
    };

    let IssueInfo = function(){
        // ==============================================
        // attributes
        // ----------------------------------------------
        const ISSUE_ACTIONS = {
            CLOSE: {
                title: "닫기",
                icon: "fas fa-times",
                cssClass: "btn-outline-primary"
            }, DELETE: {
                title: "삭제",
                icon: "fa-solid fa-minus",
                cssClass: "btn-outline-danger"
            }, MODIFY: {
                title: "수정",
                icon: "fa fa-pencil",
                cssClass: "btn-outline-primary"
            }, RCV: {
                title: "접수",
                icon: "fa fa-pencil",
                cssClass: "btn-outline-primary"
            }, RSLT: {
                title: "완료",
                icon: "fa fa-pencil",
                cssClass: "btn-outline-primary"
            }, CFM: {
                title: "검수",
                icon: "fa fa-pencil",
                cssClass: "btn-outline-primary"
            }
        };
        let model = new IssueInfoModel();
        let $rootNode = $("#issueInfoRootLayer");
        let openerData = $.extend({
            issueUid: null
        },$rootNode.data("opener-data"));


        // ==============================================
        // private functions
        // ----------------------------------------------
        let closeForm = function(data){
            if(typeof(opener) !== "undefined") {
                let cbKey = openerData.cbKey;
                let openerCbFnc = opener.paragonCmm.getCbFnc(cbKey);
                if(typeof openerCbFnc === "function" && typeof data !== "undefined"){
                    openerCbFnc(data);
                }
                self.close();
            }
        };

        let setData = function(data){
            console.debug(data);

            // 1. set layer
            let iStuCd = parseInt(data.issueStuCd, 10);
            if(iStuCd >= 50){
                $("#rcvViewLayer", $rootNode).show();
            }
            if(iStuCd >= 80){
                $("#rsltViewLayer", $rootNode).show();
            }
            if(iStuCd >= 90){
                $("#cfmViewLayer", $rootNode).show();
            }

            let stuClass = "bg-light";
            switch(iStuCd){
                case 10:
                    stuClass = "bg-warning";
                    break;
                case 50:
                    stuClass = "bg-info";
                    break;
                case 80:
                    stuClass = "bg-success";
                    break;
                case 91:
                    stuClass = "bg-danger";
                    break;
                default:
                    break;
            }
            if(stuClass !== "bg-light"){

                $("[data-col=issueStuNm]").addClass(stuClass);
            }

            // 2. set data
            let keys = Object.keys(data);
            keys.forEach(function(key){
                switch (key) {
                    case "rcvExpManday":
                        $("[data-col='" + key + "']", $rootNode).html(filterXSS(data[key])+" MD");
                        break;
                    case "rsltRealManday":
                        $("[data-col='strRsltRealManday']", $rootNode).html(filterXSS(data[key]) +"/"+filterXSS(data["rcvExpManday"])+" MD");
                        break;
                    case "reqIssueConts":
                        $("[data-col='" + key + "']", $rootNode).html(data[key]);
                        break;
                    case "rcvComment":
                        $("[data-col='" + key + "']", $rootNode).html(data[key]);
                        break;
                    case "rsltRport":
                        $("[data-col='" + key + "']", $rootNode).html(data[key]);
                        break;
                    case "cfmComment":
                        $("[data-col='" + key + "']", $rootNode).html(data[key]);
                        break;
                    default:
                        $("[data-col='" + key + "']", $rootNode).html(filterXSS(data[key]));
                        break;
                }
            });

            // 3. set action

            let actions = ["CLOSE"];
            if(paragonCmm.isNotEmpty(data.nextAction)){
                actions = actions.concat(data.nextAction.split(","));
            }
            console.debug(actions);
            let iLen = actions.length - 1;

            let strHtml = "";
            let cssClass = "";
            actions.forEach(function(itm, idx){
                cssClass = ISSUE_ACTIONS[itm].cssClass;
                if(idx === iLen){
                    cssClass = "btn-primary";
                }
                strHtml += ' <button type="button" class="btn btn-sm '+cssClass+'" data-btn="'+itm+'"><i class="'+ISSUE_ACTIONS[itm].icon+'"></i> '+ISSUE_ACTIONS[itm].title+'</button> '
            });
            $("#issueBtnLayer", $rootNode).html(strHtml);
        };

        let openPopup = function(btnId){

            let url = "";
            switch (btnId){
                case "MODIFY":
                    url = model.getModifyUrl();
                    break;
                case "RCV":
                    url = model.getRcvUrl();
                    break;
                case "RSLT":
                    url = model.getRsltUrl();
                    break;
                case "CFM":
                    url = model.getCfmUrl();
                    break;
                default :
                    break;
            }

            if(paragonCmm.isNotEmpty(url)){
                var cbFnc  = paragonCmm.getCbFnc("issueInfo_"+openerData.issueId);
                if(typeof cbFnc != "function"){
                    paragonCmm.setCbFnc("issueInfo_"+openerData.issueId, init);
                }
                htmlUtils.openPopup({
                    url: url,
                    targetName: "POPUP_ISSUE_ACT_"+openerData.issueId,
                    popupTitle: "이슈 "+ISSUE_ACTIONS[btnId].title,
                    popWidth: 864,
                    popHeight: 540,
                    openerData: {
                        cbKey: "issueInfo_"+openerData.issueId,
                        issueId: openerData.issueId
                    }
                });
            }
        };

        let deleteIssue = function(){
            $.messager.confirm("Confirm",'정말 삭제하시겠습니까?',function(r) {
                if (r) {
                    model.deleteData({
                        params: {
                            issueId: openerData.issueId
                        },
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
                let btnId = $(this).data("btn");
                switch (btnId){
                    case "CLOSE":
                        closeForm();
                        break;
                    case "DELETE":
                        deleteIssue();
                        break;
                    case "MODIFY":
                        openPopup(btnId);
                        break;
                    case "RCV":
                        openPopup(btnId);
                        break;
                    case "RSLT":
                        openPopup(btnId);
                        break;
                    case "CFM":
                        openPopup(btnId);
                        break;
                    case "CFM":
                        openPopup(btnId);
                        break;
                    default:
                        console.debug("not support btnId:"+btnId);
                        return;
                }

                return false; // 이벤트 확산금지
            });
        };

        let loadAttach = function(){

            htmlUtils.loadFileView("reqFiles", {
                relUid: openerData.issueId,
                fileTpCd: "SD/REQ",
                defaultRelUid: openerData.issueId,
                requiredYn: ""
            });

            htmlUtils.loadFileView("rcvFiles", {
                relUid: openerData.issueId,
                fileTpCd: "SD/RCV",
                defaultRelUid: openerData.issueId,
                requiredYn: ""
            });

            htmlUtils.loadFileView("rsltFiles", {
                relUid: openerData.issueId,
                fileTpCd: "SD/MNG",
                defaultRelUid: openerData.issueId,
                requiredYn: ""
            });

            htmlUtils.loadFileView("cfmFiles", {
                relUid: openerData.issueId,
                fileTpCd: "SD/CFM",
                defaultRelUid: openerData.issueId,
                requiredYn: ""
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
                        closeForm({});
                        return false;
                    } else  {
                        setData(json.data);
                    }
                }
            });

            loadAttach();

            let sysCmt = new SysComment("issueComments", openerData.issueId);
            sysCmt.init();
        };

        let setEvnt = function(){
            setBtnEvent();
        };

        // ==============================================
        // public functions
        // ----------------------------------------------
        let init = function(){
            // if(paragonCmm.isEmpty(openerData.issueUid)){
            //     closeForm();
            // }
            loadData();
            setEvnt();
        };

        return {
            init: init
        };
    };

    let issueInfo = new IssueInfo();
    issueInfo.init();
});