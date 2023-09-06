/*
 * @(#)bbsView.js     2022-07-27(027) 오후 3:54:47
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

//오늘 더이상 보지않기!
function closeWin(seq) {
    paragonCmm.setCookie(seq, "ok", 1);
    self.close();
}

var BbsView = function () {
    "use strict";

    var model = (function(){

        var BBS_WRITE_FORM_URL = paragonCmm.getUrl("/paragon/bbs/bbsWrite.popup");
        var BBS_GET_DATA_URL = paragonCmm.getUrl("/paragon/bbs/BbsMas/getData/json");
        var BBS_DEL_URL = paragonCmm.getUrl("/paragon/bbs/BbsMas/delete/json");

        var getData = function (options){
            var opts = $.extend({
                url: BBS_GET_DATA_URL,
                param: null,
                cbFnc: function(json){}
            }, options);

            paragonCmm.callAjax(opts.url, opts.param, opts.cbFnc);
        };

        var deleteData = function(options){
            var opts = $.extend({
                url: BBS_DEL_URL,
                param: null,
                cbFnc: function(json){}
            }, options);

            paragonCmm.callAjax(opts.url, opts.param, opts.cbFnc);
        };

        return{
            deleteData: deleteData,
            getData: getData,
            getWriteUrl: function(){
                return BBS_WRITE_FORM_URL;
            }
        };
    })();

    var $rootNode = $("#bbsView");
    var $form = $("#bbsViewForm");
    var openerData = $form.data("opener-data");
    var loginInfo = paragonCmm.formMap.get("loginInfo");
    var PERMISSION_AUTHS = ["LMS_BCD", "LMS_BJD", "CMM_SYS"];
    var opts = $.extend({
        bbsUid: $("input[name=bbsUid]", $form).val(),
        bbsTpCd: $("input[name=bbsTpCd]", $form).val(),
        bbsCd: $("input[name=bbsCd]", $form).val(),
        cbKey: $("#bbsViewForm").data("cbkey")
    },openerData);

    var $bbsViewForm = $("#bbsViewForm");
    var $btnDelete = $("#btnDelete");
    var $btnModify = $("#btnModify");
    var $bbsUid = openerData.bbsUid
    var $openType = openerData.openType;
    var $bbsRegLoginId;
    var $bbsLoginId = $("#regLoginId").val();
    
    var $ctgryLayer = $('#bbsViewCtgryLayer');
    var $topYnLayer = $("#bbsViewTopYnLayer");
    var $popupYnLayer = $("#bbsViewPopupYnLayer");
    var $bbsHeaderTitle = $("#bbsHeaderTitle span.langSpan");
    var $bbsHeaderCont = $("#bbsHeaderCont span.langSpan");

    var isAuth = false;

    if ($openType == "MAIN") {
        $("#checkDiv").show();
    } else {
        $("#checkDiv").hide();
    }

    var closeForm = function(data){
        if(typeof(opener) !== "undefined") {
            var cbKey = opts.cbKey;
            var openerCbFnc = opener.paragonCmm.getCbFnc(cbKey);
            if(typeof openerCbFnc === "function"){
                openerCbFnc(data);
            }
        }
        if(typeof(opener) !== "undefined") {
            self.close();
        }
    };

    var chkAuthority = function(){
        return PERMISSION_AUTHS.some(function(itm){
            return (loginInfo.authCd.indexOf(itm) !== -1);
        });
    };

    var doModify = function (openerData) {
        var imsiForm = $("<form method='POST'>").attr("action", paragonCmm.getUrl("/paragon/bbs/bbsWrite.popup"));
        //imsiForm.append($("<input type='hidden' name='bbsUid'>").val(openerData.bbsUid));
        imsiForm.append($("<input type='hidden' name='_csrf'>").val($("meta[name='_csrf']").attr("content")));
        imsiForm.append($("<input type='hidden' name='openerData'>").val(JSON.stringify(openerData)));
        imsiForm.appendTo("body");
        imsiForm.submit();
        imsiForm.remove();

    };

    var doDelete = function (openerData) {
        var data = {};
        data.bbsUid = openerData.bbsUid

        paragonCmm.callAjax(paragonCmm.getUrl("/paragon/bbs/BbsMas/delete/json"), data, function (json) {
            if (json.errYn === "E") {
                //-- 오류처리
                alert(json.msg);
                return false;
            } else if (json.errYn === "S") {
                htmlUtils.showMsg(json.msg);

                setTimeout(function(){
                    if (openerData.dashBoardTodoChk != "Y") {
                        window.opener.BBSLIST.doSearch();
                    } else {
                        parent.window.opener.dashBoard.loadBoard();
                    }
                },1000);
            }
        });
        window.close();
    };
    var setEvent = function () {
        //수정
        $("#btnModify").on("click", function () {
            confirm(paragonCmm.getLang("M.ALERT_SUBMIT", "L.수정"), function (r) {
                if (r) {
                    doModify(openerData);
                    //window.close();
                }
            })
        });
        //삭제
        $("#btnDelete").on("click", function () {
            confirm(paragonCmm.getLang("M.ALERT_SUBMIT", "L.삭제"), function (r) {
                if (r) {
                    doDelete(openerData);
                }
            })
        });

        //오늘 창닫기
        if ($("#showCheck").prop("checked")) {
            paragonCmm.setCookie(seq, "ok", 1);
            window.close();
        }
    }
    //-- 첨부파일 Form 로드
    var loadForm = function () {

        var options = {}

        options.relUid = $bbsUid;        //-- 관례 키 UID
        options.fileTpCd = "LMS/BBS";    //-- 파일 유형 코드
        options.defaultRelUid = "";        //-- 기본 로드 첨부파일(수정시)
        htmlUtils.loadFileView("bbsFile", options);    //-- 첨부파일 로드

    }

    var initBtn = function(){
        if(chkAuthority()){
            $btnModify.show();
            $btnDelete.show();
        }
    };
    
    var setContent = function(data){
        var keys = Object.keys(data);
        keys.forEach(function (key) {
            switch (key) {
                case "strRegDte":
                case "regDspNm":
                case "strUptDte":
                case "uptDspNm":
                case "bbsCtgryNm":
                case "bbsNm":
                    $("[data-col='"+key+"']", $rootNode).html(paragonCmm.getLang(filterXSS(data[key])));
                    break;
                case "bbsTopYn":
                    $("[data-col='"+key+"']", $rootNode).html(data[key]==="Y"?"사용":"미사용");
                    break;
                case "bbsPopupYn":
                    $("[data-col='"+key+"']", $rootNode).html(data[key]==="Y" ? "사용":"미사용");
                    break;
                case "bbsContent":
                    $("[data-col='"+key+"']", $rootNode).html(data[key]);
                    break;
                default:
                    $("[data-col='"+key+"']", $rootNode).html(filterXSS(data[key]));
                    break;
            }
        });
    };

    var loadContent = function(){
        model.getData({
            param: opts,
            cbFnc: function(json){
                if (json.errYn === "E") {
                    alert(json.msg);//-- 오류처리
                    return false;
                }
                if (json.data) {
                    setContent(json.data);
                }
            }
        });
    };

    var loadFile = function(){
        htmlUtils.loadFileView("bbsFile", {
            relUid: opts.bbsUid,
            fileTpCd: "LMS/BBS",
            defaultRelUid: opts.bbsUid,
            requiredYn: ""
        });
    };

    var deleteData = function(){
        $.messager.confirm("Confirm",'정말 삭제하시겠습니까?', function(r){
            if(r){
                model.deleteData({
                    param: opts,
                    cbFnc: function(data){
                        if (data.errYn === "E") {
                            //-- 오류처리
                            $.messager.alert("Error", data.msg,"error");
                            return false;
                        } else {
                            htmlUtils.showMsg(data);
                            setTimeout(closeForm, 1000, data);
                        }
                    }
                });
            }
        });
    };
    var openModify = function(){
        $("input[name=openerData]").val(JSON.stringify(opts));
        $form.attr("action", model.getWriteUrl());
        $form.submit();
    };

    var setBtnEvent = function(){
        $rootNode.off("click", "button");
        $rootNode.on("click", "button", function(){
            var btnId = $(this).attr("id");
            switch (btnId){
                case "btnDelete":
                    deleteData();
                    break;
                case "btnModify":
                    openModify();
                    break;
                case "btnClose":
                    if(typeof(opener) !== "undefined") {
                        self.close();
                    }
                    break;
                default:
                    console.warn("not support btnId:"+btnId);
                    break;
            }

            return false; // 이벤트 확산금지
        });
    };

    var initForm = function(){
        
        // 게시판 코드에 따라
        if(opts.bbsCd !== "undefined") {
            // 공지사항인 경우
            if ( opts.bbsCd === "BOARD_NOTICE" ) {
                $topYnLayer.show();
                $popupYnLayer.show();
            }
            // 자료실인 경우
            else if ( opts.bbsCd === "BOARD_FILE" ) {
                $ctgryLayer.show();
            }

            // FAQ인 경우
            else if ( opts.bbsCd === "BOARD_FAQ" ) {
                $bbsHeaderTitle.text(paragonCmm.getLang("L.질문"));
                $bbsHeaderCont.text(paragonCmm.getLang("L.답변"));
            }
        }

        initBtn();
    };
    var loadForm = function(){
        loadContent();
        loadFile();
    };
    var setEvent = function(){
        setBtnEvent();
    };

    // public functions ======================
    var init = function () {
        initForm();
        loadForm();
        setEvent();
    }
    return {
        init: init
    }
}

$(document).ready(function () {
	var bbsMasView = new BbsView();
	bbsMasView.init();
});    