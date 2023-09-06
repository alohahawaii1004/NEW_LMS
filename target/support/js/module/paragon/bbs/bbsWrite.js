/*
 * @(#)bbsWrite.js     2022-07-11
 *
 * Copyright 2022 JAYU.space
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

var BbsWriteModel = function(){
    "use strict";

    var SAVE_URL = paragonCmm.getUrl("/paragon/bbs/BbsMas/save/json");
    var BBS_GET_DATA_URL = paragonCmm.getUrl("/paragon/bbs/BbsMas/getData/json");

    var saveData = function(options){
        var opts = $.extend({
            url: SAVE_URL,
            params: null,
            cbFnc: function (json) {}
        }, options);

        paragonCmm.submitAjax(opts.url, opts.params, opts.cbFnc);
    };

    var getData = function (options){
        var opts = $.extend({
            url: BBS_GET_DATA_URL,
            param: null,
            cbFnc: function(json){}
        }, options);

        paragonCmm.callAjax(opts.url, opts.param, opts.cbFnc);
    };

    return {
        saveData: saveData,
        getData: getData
    };
};


var BbsWrite = function () {


    // attributes ===============================================
    var openerData = $("#bbsWriteForm").data("opener-data");
    var model = new BbsWriteModel();
    var $rootNode = $("#bbsWrite");
    var $isNew = openerData.isNew;
    var $form = $("#bbsWriteForm");
    var $bbsUid = $("#bbsUid");
    var $bbsPopupStDte = $("#bbsPopupStDte");
    var $bbsPopupEdDte = $("#bbsPopupEdDte");
    var $typeCombo = $("select[name=bbsCtgryCd]", $form);
    var $saveBtn = $("#btnSave");
    var editorId = "bbsContent";
    var editorNm = "bbsContent";
    
    var $ctgryLayer = $("#bbsViewCtgryLayer");
    var $topYnLayer = $("#bbsViewTopYnLayer");
    var $popupYnLayer = $("#bbsViewPopupYnLayer");
    var $bbsHeaderTitle = $("#bbsHeaderTitle span.langSpan");
    var $bbsHeaderCont = $("#bbsHeaderCont span.langSpan");

    var opts = $.extend({
        bbsUid: null,
        bbsTpCd: null,
        bbsCd: null
    }, openerData);

    // private functions ===========================================
    var closeForm = function(data){
        if (openerData.dashBoardTodoChk != "Y") {
            if(typeof(opener) !== "undefined") {
                var cbKey = opts.cbKey;
                var openerCbFnc = opener.paragonCmm.getCbFnc(cbKey);
                if(typeof openerCbFnc === "function"){
                    openerCbFnc(data);
                }
            }
        }else{
            //대쉬보드일 경우 수정 후 loadBoard를 실행함[reflash]
            parent.window.opener.dashBoard.loadBoard();
        }
        if(typeof(opener) !== "undefined") {
            self.close();
        }
    };

    var doValidation = function (params) {    //-- 저장 필수체크       
        if ($("input:text[name='bbsTit']", $form).val() == "") {
            $.messager.alert("Warning","제목을 입력해주세요.","warning", function () {
                $("input:text[name='bbsTit']", $form).focus();
            });
            return false;
        }
        
        // 자료실인 경우
        if (params.bbsCd === "BOARD_FILE") {
            if(paragonCmm.isEmpty(params.bbsCtgryCd)){
                $.messager.alert({
                    title: "Warning",
                    msg: paragonCmm.getLang("M.필수_입력사항_입니다", paragonCmm.getLang('L.유형')),
                    icon: "warning",
                    fn: function () {
                        $form.find("select[name=bbsCtgryCd]").focus();
                    }
                });
                return false;
            }   
        }

        if(params.bbsPopupYn === "Y"){

            if(paragonCmm.isEmpty(params.bbsPopupStDte) || paragonCmm.isEmpty(params.bbsPopupEdDte)){
                $.messager.alert({
                    title: "Warning",
                    msg: paragonCmm.getLang("M.필수_입력사항_입니다", paragonCmm.getLang('L.메인팝업기간')),
                    icon: "warning",
                    fn: function () {
                        $bbsPopupStDte.datebox('textbox').focus();
                    }
                });
                return false;
            }

            var fromDate = new Date(params.bbsPopupStDte);
            var toDate = new Date(params.bbsPopupEdDte);

            if ( fromDate.getTime() > toDate.getTime()) {
                $.messager.alert({
                    title: "Warning",
                    msg: "기간이 올바르지 않습니다.",
                    icon: "warning",
                    fn: function () {
                        $bbsPopupStDte.datebox('textbox').focus();
                    }
                });
                return false;
            }
        }

        return true;
    }

    var saveData = function () {
        paragonCmm.setEditorSubmit("");
        var params = $form.serializeObject();

        if (!doValidation(params)) {
            return false;
        }
        $.messager.confirm("Confirm", paragonCmm.getLang("M.ALERT_SUBMIT", "L.저장"), function (r) {
            if(r) {

                // 데이터 저장
                model.saveData({
                    params: params,
                    cbFnc: function(data){
                        htmlUtils.showMsg(data);
                        setTimeout(closeForm, 1000);
                    }
                });
            }
        });
    }

    var setContent = function(data){

        var keys = Object.keys(data);
        keys.forEach(function (key) {
            switch (key) {
                case "bbsNm":
                    $("[data-col='"+key+"']", $rootNode).html(paragonCmm.getLang(filterXSS(data[key])));
                    break;
                case "strBbsPopupStDte":
                    $bbsPopupStDte.datebox("setValue", filterXSS(data[key]));
                    break;
                case "strBbsPopupEdDte":
                    $bbsPopupEdDte.datebox("setValue", filterXSS(data[key]));
                    break;
                case "bbsTopYn":
                    $("input:radio[name=" + key + "]:input[value=" + data[key] + "]", $rootNode).attr("checked", true);
                    break;
                case "bbsPopupYn":
                    $("input:radio[name=" + key + "]:input[value=" + data[key] + "]", $rootNode).attr("checked", true);
                    if(data[key] === "Y"){
                        $bbsPopupStDte.datebox("enable");
                        $bbsPopupEdDte.datebox("enable");
                    }
                    break;
                case "strRegDte":
                case "strRegNm":
                    $("[data-col='"+key+"']", $rootNode).html(paragonCmm.getLang(filterXSS(data[key])));
                    break;
                case "bbsContent":
                    paragonCmm.setEditorValue(key, data[key]);
                    $("[data-col='"+key+"']", $rootNode).val(data[key]);
                    break;
                default:
                    $("[data-col='"+key+"']", $rootNode).val(data[key]);
                    break;
            }
        });
    };

    var initCombo = function(){
        // init combo
        htmlUtils.getCodeSelectOpt({
            targetId: $typeCombo,
            parentCd: opts.bbsCd,
            initdata: "|" + paragonCmm.getLang("L.CBO_SELECT"),
            valNm: "cdAbb",
            txtNm: "langCd"
        });
    };

    var initDatebox = function(){
        //날짜컴포넌트 : start date
        $bbsPopupStDte.datebox({
            validType: "validDate"
        });
        //날짜컴포넌트 : end date
        $bbsPopupEdDte.datebox({
            validType: "validDate"
        });
    };

    var initEditor = function(){
        //editor
        htmlUtils.getHtmlEditor({
            sCtrlName: editorNm,
            sCtrlId: editorId,
            sHeight: "250px"
        });
    };

    var loadData = function () {
        setContent(opts);
    };

    var initFile = function(){
        htmlUtils.loadFileHtml("bbsFile", {
            relUid: opts.bbsUid,
            fileTpCd: "LMS/BBS",
            defaultRelUid: ""
        });    //-- 첨부파일 로드
    };

    var setFormEvent = function(){
        $form.on("submit", function(){
            saveData();
            return false;
        });
    };

    var setRadioEvent = function(){
        $("input[name=bbsPopupYn]:radio", $form).on("change", function () {
            $bbsPopupStDte.datebox("setValue", "");
            $bbsPopupEdDte.datebox("setValue", "");
            if ($(this).val() === "Y") {
                $bbsPopupStDte.datebox("enable");
                $bbsPopupEdDte.datebox("enable");
            } else {
                $bbsPopupStDte.datebox("disable");
                $bbsPopupEdDte.datebox("disable");
            }
        })
    };

    var setBtnEvent = function(){
        $rootNode.off("click", "button");
        $rootNode.on("click", "button", function(){
            var btnId = $(this).attr("id");
            switch (btnId){
                case "btnClose":
                    if(typeof(opener) !== "undefined") {
                        self.close();
                    }
                    break;
                case "btnSave":
                    saveData();
                    break;
                default:
                    console.warn("not support btnId:"+btnId);
                    break;
            }

            return false; // 이벤트 확산금지
        });
    };

    var initForm = function () {
        if (paragonCmm.isEmpty(opts.bbsUid)) {
            opts.bbsUid = paragonCmm.getRandomUUID();
        } else {
            model.getData({
                param: opts,
                cbFnc: function (json) {
                    if (json.errYn === "E") {
                        alert(json.msg);//-- 오류처리
                        return false;
                    }
                    if (json.data) {
                        opts = $.extend(opts, json.data);
                    }
                }
            });
        }
        
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
        initCombo();
        initDatebox();
        initEditor();
    };

    //-- 웹에디터 첨부파일 Form 로드
    var loadForm = function () {
        // HACK 순서 중요
        loadData();
        initFile();
    };

    var setEvent = function () {
        setFormEvent();
        setRadioEvent();
        setBtnEvent();
    };

    // public functions ======================
    var init = function () {
        initForm();
        loadForm();                            //-- 양식 로드
        setEvent();
    };

    return {
        init: init
    };
};

$(document).ready(function () {
    var bbsWrite = new BbsWrite();
    bbsWrite.init();
});    