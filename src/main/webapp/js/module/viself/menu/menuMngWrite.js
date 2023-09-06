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
 *    메뉴 관리 화면
 * </pre>
 * @author 강세원
 */
"use strict";

function MenuWrite() {
    var LIST_URL = paragonCmm.getUrl("/viself/menu/menuMng");
    var LOAD_URL = paragonCmm.getUrl("/viself/menu/MenuMng/allList/json");
    var SAVE_URL = paragonCmm.getUrl("/viself/menu/MenuMng/saveMenu/json");
    var DELETE_URL = paragonCmm.getUrl("/viself/menu/MenuMng/deleteAll/json");
    var MLANG_URL = paragonCmm.getUrl("/viself/mlang/MLangMng/list/json");

    var $modalDiv1;
    var $writeForm = $("#menuMngWriteform1");
    var $tmplMenuWirte = $("#TMPL_MENU_WIRTE");
    var $menuInputList = $("#MENU_INPUT_LIST");
    var $cd = $("#MENU_INPUT_LIST input:text[name='cd']");
    var $parentMenuId = $("#menuMngWriteform1 input:text[name='parentMenuId']");
    var $langCds = $("input:text[name='langCd']", $menuInputList);
    var $moduleIds = $("input:text[name='moduleId']", $menuInputList);
    var $reqMenuId = $("#reqMenuId");
    var $reqParentMenuId = $("#reqParentMenuId");

    var $btnListAdd = $("#menuListAdd");
    var $btnSave = $("#menuMngWriteBtnSave");
    var $btnList = $("#menuMngWriteBtnList");


    var goList = function (frm) {

        var msg = paragonCmm.getLang("M.ALERT_LIST");
        $.messager.confirm("Confirm", msg, function (r) {
            if (r) {
                var params = $(frm).serializeJSON();
                params.langCd = "L.시스템메뉴관리";
                params.accesUrl = LIST_URL;
                main.goPage(params);
            }
        });
    }

    /**
     * 저장
     */
    var doSubmit = function () {
        var cd = $cd;

        for (var i = 0; i < cd.length; i++) {

            //CODE_ID Validation
            if (cd[i].value == "") {
                $.messager.alert("Warning", paragonCmm.getLang("M.ALERT_INPUT", "L.코드"), "warning");
                cd[i].focus();
                return;
            }

            //아이디 중복 확인
            for (var j = 0; j < cd.length; j++) {
                if (i == j) {
                    continue;
                } else {
                    if (cd[i].value == cd[j].value) {
                        $.messager.alert("Warning", "중복된 코드 아이디가 존재합니다.", "warning");
                        $("#MENU_INPUT_LIST tr:eq(" + j + ")").find("td").attr("style", "background-color:red");
                        cd[j].focus();
                        return;
                    }
                }
            }
        }
        $.messager.confirm("Confirm", paragonCmm.getLang("M.ALERT_SUBMIT", "L.저장"), function (r) {
            if (r) {
                var params = {list: $menuInputList.tableDataToJson()};
                params["parentMenuId"] = $parentMenuId.val();

                $.ajax({
                    type: "POST",
                    url: SAVE_URL,
                    data: JSON.stringify(params),
                    success: function (json) {
                        if (json.errYn === "E") {
                            //-- 오류처리
                            $.messager.alert("Error", json.msg, "error");
                            return false;
                        }
                        goList($writeForm);
                        if (typeof setCallback === "function") {
                            setCallback(json);
                        }
                    },
                    dataType: "json",
                    contentType: "application/json"
                });
            }
        });
    }
    var autoComplete = function () {
        //언어사전에 존재하는 단어가 나오도록 자동완성하는 코드.
        $('.autoLangCd').combobox({
            mode: 'remote',
            valueField: 'value',
            textField: 'label',
            loader: function (param, succ) {
                console.log(param);
                if (!param.q) {
                    return;
                }
                $.ajax({
                    url: MLANG_URL,
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
                        succ(rows)
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
            urlPath: "",
            moduleId: "",
            langCd: "",
            jsonData: "[{\"key\":\"\",\"val\":\"\"}]",
            useYn: "Y",
            cud: "C",
            ordNo: codIdx
        }];
        $tmplMenuWirte.tmpl(data).appendTo($menuInputList);
    }

    /*
    * 코드 라인 삭제
    */
    var removeCodLine = function (removeKey, child_cnt, authCnt) {
        var msg = "";
        if (child_cnt != "0" || authCnt != "0") {
            msg = "하위 메뉴 또는 관련 권한이 존재 합니다. 삭제하시면 하위메뉴 및 관련권한 까지 모두 삭제 됩니다.\n삭제 하시겠습니까?";
        } else {
            msg = "삭제 하시겠습니까?";
        }
        $.messager.confirm("Confirm", msg, function (r) {
            if (r) {
                var data = {};
                // data.menuId = $("input:text[name='menuId']", "tr[data-meaning=" + removeKey + "]").val();
                data.menuId = removeKey;
                console.log(data);
                paragonCmm.callAjax(DELETE_URL, data, function (json) {
                    if (json.errYn === "E") {
                        //-- 오류처리
                        alert(json.msg);
                        return false;
                    }
                    $("tr[data-meaning=" + removeKey + "]", $menuInputList).remove();
                });
            }
        });
    }

    //저장항목 로드
    var loadPatentTable = function () {
        var data = {};
        if ("CHILD" == $("#updateMode").val()) {
            data["parentCd"] = $reqMenuId.val();
        } else {
            data["menuId"] = $reqMenuId.val();
        }
        paragonCmm.callAjax(LOAD_URL, data, function (data) {
            $tmplMenuWirte.tmpl(data.data).appendTo($menuInputList);
            paragonCmm.tableTrDragSet($menuInputList);
            $("input[name=menuId]").attr("readonly", true);
        });
    }
    var openModal = function (obj) {
        // $(obj).val("");// 클릭시 값 초기화
        // if (typeof $modalDiv1 != "object") {
        //     $modalDiv1 = $("<div>");
        // }
        // $modalDiv1.window({
        //     iconCls: 'icon-search',
        //     width: 850,
        //     height: 500,
        //     title: "Module select",
        //     href: paragonCmm.getUrl("/viself/module/moduleListDialog.modal"),
        //     modal: true,
        //     onClose: function () {
        //         $modalDiv1.window("destroy");
        //     },
        //     onLoad: function () {
        //         moduleList.init(function () {
        //             //-- init Function
        //         }, function (moduleId) {
        //             //-- callback Function
        //             $(obj).val(moduleId);
        //             $modalDiv1.window("destroy");
        //         });            //-- init,콜백 Function 지정
        //         paragonCmm.convertLang($modalDiv1);             //-- 다국어 처리
        //     }
        // });

        var CONDINFO_MODAL_ID = "menuModule";
        var options = {
            modalId: CONDINFO_MODAL_ID,
            href: paragonCmm.getUrl("/viself/module/moduleListDialog.modal"),
            title: "Module select",
            params: {
                modalId: CONDINFO_MODAL_ID
            },
            cbFnc: function(moduleId){
                $(obj).val(moduleId);
            },
            width: 850,
            height: "auto",
            resizable: true,
            top: "20px"
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

        htmlUtils.openDialog(options);


    }
    //이벤트 추가
    var attachEvents = function () {
        //코드 추가 버튼
        $btnListAdd.off();
        $btnListAdd.click(function () {
            addCodList();
            paragonCmm.tableTrDragSet($menuInputList);
            autoComplete();

            $("input:text[name='moduleId']", "#MENU_INPUT_LIST").off();
            $("input:text[name='moduleId']", "#MENU_INPUT_LIST").on("click", function () {
                openModal(this);
            });

        });
        $btnSave.off();
        $btnSave.on("click", function () {
            doSubmit();
        });
        $btnList.off();
        $btnList.on("click", function () {
            goList($writeForm);
        });
        $("input:text[name='moduleId']", "#MENU_INPUT_LIST").off();
        $("input:text[name='moduleId']", "#MENU_INPUT_LIST").on("click", function () {
            openModal(this);
        });
    }

    var init = function () {
        if ("CHILD" == $("#updateMode").val()) {
            $parentMenuId.val($reqMenuId.val());
        } else {
            $parentMenuId.val($reqParentMenuId.val());
        }
        loadPatentTable();
        // combobox 삭제
        $("div.panel.combo-p").remove();
        autoComplete();
        attachEvents();

    }

    return {
        init: init,
        removeCodLine: removeCodLine
    }

}

var menuWrite = new MenuWrite();
$(document).ready(function () {
    console.info("[Loading Module: 메뉴관리작성].....................");
    menuWrite.init();
});
