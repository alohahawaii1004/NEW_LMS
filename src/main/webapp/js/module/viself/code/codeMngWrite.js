/*
 * @(#)codeMng.js    
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
/**
 * <b>Description</b>
 * <pre>
 *    코드 관리 화면
 * </pre>
 * @author 강세원
 */

function CodeWrite() {
    "use strict";
    var codeStr = "";
    var $parentCdSelectModal;

    var model = (function(){

        var GET_LIST_URL = paragonCmm.getUrl("/viself/code/CodeMng/listCode/json");
        var getCodeList = function(options){
            var opts = $.extend({
                url: GET_LIST_URL,
                param: null,
                cbFnc: function(json){}
            }, options);

            paragonCmm.callAjax(opts.url, opts.param, opts.cbFnc);
        };

        return {
            getCodeList: getCodeList
        };
    })();

    /**
     * 목록 페이지로 이동
     */
    var goList = function (frm) {

        var msg = paragonCmm.getLang("M.ALERT_LIST");
        $.messager.confirm("Confirm", msg, function (r) {
            if (r) {
                var params = $(frm).serializeJSON();
                params.langCd = "L.코드관리";
                params.accesUrl = paragonCmm.getUrl("/viself/code/codeMng.include");
                main.goPage(params);
            }
        });
    }

    /**
     * 저장
     */
    var doSubmit = function (frm) {

        var cd = $("#CODE_INPUT_LIST input:text[name='cd']");
        var cdInfos = $("#CODE_INPUT_LIST").tableDataToJson();
        var chkIdx = 0;
        var msg = "";

        var chkFlag = cdInfos.some(function(cdInfo, i){

            // 필수값 검사: 코드
            if(paragonCmm.isEmpty(cdInfo.cd)){
                chkIdx = i;
                msg = paragonCmm.getLang("M.ALERT_INPUT", "L.코드");
                return true;
            }

            // 필수값 검사: 다국어 코드
            if(paragonCmm.isEmpty(cdInfo.langCd)){
                chkIdx = i;
                msg = paragonCmm.getLang("M.ALERT_INPUT", "L.다국어코드");
                return true;
            }

            // 리스트 중복 검사
            var chkDupCd =  cdInfos.some(function(chkCd, j){
                if(i !== j && cdInfo.cd === chkCd.cd){
                    chkIdx = j;
                    msg = "중복된 코드 아이디가 존재합니다.";
                    $("#CODE_INPUT_LIST tr:eq(" + j + ")").find("td").attr("style", "background-color:red");
                    cd[j].focus();
                    return true;
                }
            });
            if(chkDupCd){
                return true;
            }

            // DB 중복검사
            if(cdInfo.cud === "C" ){
                model.getCodeList({
                    param: {
                        cd: cdInfo.cd
                    },
                    cbFnc: function(json){
                        var cds = [].concat(json.data);
                        if(cds.length > 0){
                            chkDupCd = true;
                        }
                    }
                });

                if(chkDupCd){
                    msg = "중복된 코드 아이디가 존재합니다.";
                    $("#CODE_INPUT_LIST tr:eq(" + i + ")").find("td").attr("style", "background-color:red");
                    cd[i].focus();
                    return true;
                }
            }
        });

        if(chkFlag){
            $.messager.alert("Warning", msg, "warning");
            return false;
        }

        if ($("#div_CODE_SUB_DATA").css("display") == "block") {
            alert("추가정보 입력을 완료 하였다면 입력완료 버튼을 클릭해 주세요.");
            return;
        }

        $.messager.confirm("Confirm", paragonCmm.getLang("M.ALERT_SUBMIT", "L.저장"), function (r) {
            if (r) {
                var params = {list: $("#CODE_INPUT_LIST").tableDataToJson()};
                params["parentCd"] = $("#codeMngWriteForm1 input:text[name='parentCd']").val();
                $.ajax({
                    type: "POST",
                    url: paragonCmm.getUrl("/viself/code/CodeMng/saveCode/json"),
                    data: JSON.stringify(params),
                    dataType: "json",
                    contentType: "application/json",
                    success: function (json) {
                        if (json.errYn === "E") {
                            //-- 오류처리
                            alert(json.msg);
                            return false;
                        }
                        goList(document.codeMngWriteForm1);
                    }
                });
            }
        });
    }
    /**
     * 상위 코드 아이디 정보 클리어
     */
    var InitParentCdSelect = function () {
        var opt = {};
        opt["multiSelect"] = "N";
        opt["selectType"] = "ALL";    //기본 최하위만 선택이지만 중간도 선택가능 하도록
        return opt;
    }
    /**
     * 상위 코드 아이디 검색
     */
    var ParentCodeId_Search = function () {
        if (typeof $parentCdSelectModal != "object") {
            $parentCdSelectModal = $("<div>");
        }

        // var parentNode = ($("#updateMode").val() === "CHILD") ? $("#reqCd").val() : $("#reqParentCd").val();
        //-- 코드 선택 모달



        var options = {
            iconCls: 'icon-search',
            width: 864,
            height: "auto",
            resizable: true,
            top: "20px",
            method: "post",
            title: paragonCmm.getLang("L.코드"),
            href: paragonCmm.getUrl("/viself/code/parentCodeSelect.modal"),
            modal: true,
            onClose: function () {
                $parentCdSelectModal.window("destroy");
            },
            onLoad: function () {
                CODE.init(InitParentCdSelect, ParentCodeId_Change);            //-- init,콜백 Function 지정
                paragonCmm.convertLang($parentCdSelectModal);             //-- 다국어 처리
            }
        };

        if(paragonCmm.isMobile()){
            options = $.extend(options, {
                resizable: false,
                width: "80%",
                height: "50%",
                buttons: [{
                    id: "selectBtn",
                    text: "<i class=\"fa fa-check\"></i> 선택"
                }]
            });
        }

        $parentCdSelectModal.window(options);
    }

    /**
     * 상위 코드 아이디 정보 변경
     */
    var ParentCodeId_Change = function (json) {

        console.log(json);
        var parentNode = ($("#updateMode").val() === "CHILD") ? $("#reqCd").val() : $("#reqParentCd").val();
        var reqCd = $("#reqCd").val();
        var selCdPath = $("#selCdPath").val();
        var targetPath = json[0].cdPath;

        if(reqCd !== parentNode){
            selCdPath = selCdPath.replaceAll(reqCd, "");
        }

        // console.log(json);
        // console.log(parentNode);
        // console.log(reqCd);
        // console.log("sourceCdPath....."+selCdPath);
        // console.log("targetCdPath....."+json[0].cdPath);

        if(targetPath !== selCdPath && targetPath.indexOf(selCdPath) !== -1){
            $.messager.alert("Warning","선택할 수 없는 상위코드 입니다.", "warning");
            return false;
        }

        $("#codeMngWriteForm1 input:text[name='parentCd']").val(json[0]["cd"]);
        $parentCdSelectModal.window('close');
    }
    var autoComplete = function (ord) {
        //언어사전에 존재하는 단어가 나오도록 자동완성하는 코드.
        $('.autoLangCd_' + ord).combobox({
            mode: 'remote',
            valueField: 'value',
            textField: 'label',
            loader: function (param, succ) {
                if (!param.q) {
                    return;
                }
                $.ajax({
                    url: paragonCmm.getUrl("/viself/mlang/MLangMng/list/json"),
                    data: {langCd: param.q},
                    dataType: 'json',
                    success: function (data) {
                        var rows = $.map(data.data, function (item) {
                            return {
                                label: item.langCd,
                                value: item.langCd,
                                code: item.langCd
                            };
                        });
                        succ(rows);
                    }
                })
            }
        });

    }

    //코드 라인추가
    var addCodList = function () {
        var codIdx = paragonCmm.getRandomUUID();
        var data = [{
            init: "N",
            childCnt: "0",
            index: codIdx,
            cd: codIdx,
            cdAbb: "",
            langCd: "",
            useYn: "Y",
            cud: "C",
            rn: codIdx
        }];
        $("#TMPL_CODE_WIRTE").tmpl(data).appendTo($("#CODE_INPUT_LIST"));
        return codIdx;
    }

    /*
     * 코드 라인 삭제
     */
    var removeCodLine = function (removeKey, child_cnt) {
        $.messager.confirm("Confirm", paragonCmm.getLang("M.ALERT_SUBMIT", "L.선택하신코드및하위코드정보를완전히삭제"), function (r) {
            if (r) {
                var data = {};
                data.cd = $("#div_CODE_WRITE_LIST tr[data-meaning=" + removeKey + "]").find("input:text[name='cd']").val();
                paragonCmm.callAjax(paragonCmm.getUrl("/viself/code/CodeMng/deleteCode/json"), data, function (json) {
                    $("#div_CODE_WRITE_LIST tr[data-meaning=" + removeKey + "]").remove();
                });
            }
        });
    }

    //코드 추가 정보 입력 활성
    var subDataOpen = function (trIdx) {

        var tm_index = $("#tmpIndex").val();
        //접있을땐 열어 준다.
        if ($("#div_CODE_SUB_DATA").css("display") == "none") {
            setTmpData(trIdx);
            $("#div_CODE_SUB_DATA").show();
        } else {
            //열려있을때 클릭된 Idx 와 Tmp의 Idx가 같으면 숨김
            if (tm_index == trIdx) {
                $("#div_CODE_SUB_DATA").hide();

                //열려있을때 클릭된 Idx 와 Tmp의 Idx가 틀리면 초기화진행후 열어준다.
            } else {
                subDataReset();
                setTmpData(trIdx);
            }
        }
    }

    var setTmpData = function (trIdx) {
        $("#tmpIndex").val(trIdx);
        $("#tmpCdAttr1").val($("#cdAttr1_" + trIdx).val());
        $("#tmpCdAttr2").val($("#cdAttr2_" + trIdx).val());
        $("#tmpCdAttr3").val($("#cdAttr3_" + trIdx).val());
        $("#tmpCdAttr4").val($("#cdAttr4_" + trIdx).val());
        $("#tmpCdAttrDesc1").val($("#attrDesc1_" + trIdx).val());
        $("#tmpCdAttrDesc2").val($("#attrDesc2_" + trIdx).val());
        $("#tmpCdAttrDesc3").val($("#attrDesc3_" + trIdx).val());
        $("#tmpCdAttrDesc4").val($("#attrDesc4_" + trIdx).val());
        $("#tmpCdData").val($("#cdData_" + trIdx).val());
    }
    //코드 추가 정보 데이터 비활성
    var subDataClose = function (trInx) {
        subDataReset();
        $("#tmpIndex").val(trInx);
        $("#div_CODE_SUB_DATA").hide();
    }
    //코드 추가 정보 데이터 초기화
    var subDataReset = function () {
        $("#div_CODE_SUB_DATA INPUT[data-meaning=TMP]").val("");
        $("#div_CODE_SUB_DATA TEXTAREA[data-meaning=TMP]").val("");
    }

    //코드 추가 정보 입력 완료
    var doCompleSubData = function () {

        var trIdx = $("#tmpIndex").val();
        $("#cdAttr1_" + trIdx).val($("#tmpCdAttr1").val());
        $("#cdAttr2_" + trIdx).val($("#tmpCdAttr2").val());
        $("#cdAttr3_" + trIdx).val($("#tmpCdAttr3").val());
        $("#cdAttr4_" + trIdx).val($("#tmpCdAttr4").val());
        $("#attrDesc1_" + trIdx).val($("#tmpCdAttrDesc1").val());
        $("#attrDesc2_" + trIdx).val($("#tmpCdAttrDesc2").val());
        $("#attrDesc3_" + trIdx).val($("#tmpCdAttrDesc3").val());
        $("#attrDesc4_" + trIdx).val($("#tmpCdAttrDesc4").val());
        $("#cdData_" + trIdx).val($("#tmpCdData").val());

        subDataClose();
    }

    //저장항목 로드
    var loadPatentTable = function () {
        var data = {};
        if ("CHILD" == $("#updateMode").val()) {
            data["parentCd"] = $("#reqCd").val();
        } else {
            data["parentCd"] = $("#reqParentCd").val();
            data["cd"] = $("#reqCd").val();
        }
        paragonCmm.callAjax(paragonCmm.getUrl("/viself/code/CodeMng/listCode/json"), data, function (data) {
            console.log(data);
            $("#TMPL_CODE_WIRTE").tmpl(data.data).appendTo($("#CODE_INPUT_LIST"));
            paragonCmm.tableTrDragSet("CODE_INPUT_LIST");
            $("input[name=cd]").attr("readonly", true);
        });
    }

    var getUseYnHtml = function (val) {
        var div = $("<div>");
        var sel = $("<select name='useYn' class=\"form-control form-control-sm\">");
        sel.append($("<option value='Y'>").attr("selected", ("Y" == val)).text("Y"));
        sel.append($("<option value='N'>").attr("selected", ("N" == val)).text("N"));
        div.append(sel);
        return div.html();
    }
    //이벤트 추가
    var attachEvents = function () {

        var $rootDom = $("#codeMngWirteRootLayer");

        $rootDom.find("button").off();
        $rootDom.on("click", "button", function () {
            var btnId = $(this).attr("id");
            console.log(btnId);
            switch (btnId) {
                case "codeMngWriteBtnAdd": //코드 추가 버튼
                    var ord = addCodList();
                    paragonCmm.tableTrDragSet("CODE_INPUT_LIST");
                    autoComplete(ord);
                    break;
                case "codeMngWriteBtnSave": // 저장버튼
                    doSubmit(document.codeMngWriteForm1);
                    break;
                case "codeMngWriteBtnClose": // 목록버튼
                    goList(document.codeMngWriteForm1);
                    break;
                case "parentCodeSearchBtn": // 부모코드 조회 버튼
                    ParentCodeId_Search();
                    break;
                case "subInputEnd": // 입력완료 버튼
                    doCompleSubData();
                    break;
                default:
                    break;
            }
            return false;
        });

        $("#codeMngWriteForm1").on("submit", function(){
            // ParentCodeId_Search();
            return false;
        });
    }

    var init = function () {
        if ("CHILD" == $("#updateMode").val()) {
            $("input:text[name='parentCd']", "#codeMngWriteForm1").val($("#reqCd").val());
        } else {
            $("input:text[name='parentCd']", "#codeMngWriteForm1").val($("#reqParentCd").val());
            $("#codeMngWriteBtnAdd").hide();
        }
        loadPatentTable();
        var tr = $("#CODE_INPUT_LIST tr");
        console.log(tr.length);

        // combobox 삭제
        $("div.panel.combo-p").remove();

        $(tr).each(function (i, o) {
            var ord = $(o).data("meaning");
            autoComplete(ord);

        });
        attachEvents();
    }

    return {
        init: init,
        getUseYnHtml: getUseYnHtml,
        subDataOpen: subDataOpen,
        removeCodLine: removeCodLine
    }
}

var CODDWRITE = new CodeWrite();
$(document).ready(function () {
    "use strict";
    console.info("[Loading Module: 코드관리작성].....................");
    CODDWRITE.init();
});
