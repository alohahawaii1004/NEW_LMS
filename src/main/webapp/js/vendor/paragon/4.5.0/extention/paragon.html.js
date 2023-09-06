/*
 * @(#)ParagonHtml.js     2023-02-14 014 오후 5:06:17
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

function ParagonHtml() {
    "use static";
}

ParagonHtml.prototype = {};

var htmlUtils = new ParagonHtml();


(function (hu) {
    "use strict";
    hu.writeFiles = {
        isPostLoad: false,
        loads: []
    };
})(htmlUtils);

(function (hu) {
    "use strict";

    var utils = (function () {

        /**
         * 입력일자 날짜 자동 변경
         */
        var convertDateFormat = function (obj, e) {

            // 37~40 : 방향키, 8 : back space, 46 : del
            if (e.which !== 37 && e.which !== 38 && e.which !== 39 && e.which !== 40 && e.which !== 8 && e.which !== 46) { // 방향키 체크
                //$(this).val($(this).val().replace(/[^0-9\-]/g,""));
                var dateNumText = $.trim($(obj).val()).replace(/[^0-9]/g, "");
                var textLength = dateNumText.length;
                if (textLength < 4) {
                    $(obj).val(dateNumText);
                } else {
                    if (textLength >= 4 && textLength < 6) {
                        $(obj).val(dateNumText.substr(0, 4) + "-" + dateNumText.substr(4, dateNumText.length));
                    } else if (textLength >= 6) {
                        $(obj).val(dateNumText.substr(0, 4) + "-" + dateNumText.substr(4, 2) + "-" + dateNumText.substr(6, 2));
                    }
                }
            }
        };

        /**
         * 날짜입력 컨트롤 보여주기
         */
        var openCalendar = function (dateObj) {

            $(function () {
                if ("KO" === paragonCmm.getSiteLocale()) {
                    $.datepicker.setDefaults($.datepicker.regional['ko']);
                } else {
                    $.datepicker.setDefaults($.datepicker.regional['en']);
                }
                $(dateObj).datepicker({
                    changeMonth: true
                    , changeYear: true
                    , showButtonPanel: true
                    , dateFormat: "yy-mm-dd"
                });
//        $( dateObj ).datepicker({
//                changeMonth: true
//                , changeYear: true
//                , showButtonPanel: true
//                , numberOfMonths: 2
//                , dateFormat : "yy-mm-dd"
//         });

            });

//    document.getElementById(fromId).focus();
        };

        /**
         * 날짜입력 From~To 컨트롤 보여주기
         */
        var openDualCalendar = function (fromObj, toObj) {

            $(function () {
                if ("KO" === paragonCmm.getSiteLocale()) {
                    $.datepicker.setDefaults($.datepicker.regional['ko']);
                } else {
                    $.datepicker.setDefaults($.datepicker.regional['en']);
                }
                var dateFormat = "yy-mm-dd",
                    from = $(fromObj)
                        .datepicker({
                            changeMonth: true
                            , changeYear: true
                            , defaultDate: "+1w"
                            , showButtonPanel: true
                            , numberOfMonths: 2
                        })
                        .on("change", function () {
                            to.datepicker("option", "minDate", getDate(this));
                        }),
                    to = $(toObj).datepicker({
                        changeMonth: true
                        , changeYear: true
                        , defaultDate: "+1w"
                        , showButtonPanel: true
                        , numberOfMonths: 2
                    })
                        .on("change", function () {
                            from.datepicker("option", "maxDate", getDate(this));
                        });

                function getDate(element) {
                    var date;
                    try {
                        date = $.datepicker.parseDate(dateFormat, element.value);
                    } catch (error) {
                        date = null;
                    }

                    return date;
                }

            });

//    document.getElementById(fromId).focus();
        };

        /**
         * 콤보박스 초기화 for JSON
         * @param _target 대상 객체
         * @param data JSON 데이터[{},{},{}]
         * @param initdata 선택된 옵션 값(값이 여러 개일 경우 쉼표(,)로 구분 ex> val1, val2, ...)
         * @param valNm 값속성 컬럼명
         * @param txtNm Text 속성 컬럼명
         */
        var initializeSelectJson = function (_target, data, initdata, valNm, txtNm) {

            // 옵션값 초기화
            initdata = (initdata === undefined ? "" : initdata);
            valNm = (valNm === undefined ? "cd" : valNm);
            txtNm = (txtNm === undefined ? "langCd" : txtNm);
            var target;
            if (typeof _target === "object") {
                target = _target;
            }
//    if(id !== ""){
//        target = $("#"+id);
//    }else if(name !== ""){
//        target = $("select[name='"+name+"']");
//    }
            if (target !== undefined) {
                $(target).find("option").remove();
                $(target).each(function (k, o) {
                    if (initdata !== "") {
                        var initArr = initdata.split("|");
                        var initOpt = $("<option>").val(initArr[0]).text(initArr[1]).data("cd", "");
                        $(o).append(initOpt);
                    }
                    $(data).each(function (i, d) {
                        var opt = $("<option>");
                        opt.val(d[valNm]);
                        if (txtNm.toUpperCase().indexOf("LANGCD") > -1) {
                            opt.text(paragonCmm.getLang(d[txtNm]));
                        } else {
                            opt.text(d[txtNm]);
                        }
                        opt.data("row", d);
                        $(o).append(opt);
                    });

                });
            }

        };

        /**
         * 콤보박스
         * @param name 이름(여러개일경우)
         * @param id 아이디
         * @param codestr 구성용 문자열 ex> {value1}[separ1]{text1}[separ2]{value2}[separ1]{text2} ...
         * @param initdata 선택된 옵션 값(값이 여러 개일 경우 쉼표(,)로 구분 ex> val1, val2, ...)
         * @param etc 기타 속성
         * @param separ1 구분자 1
         * @param separ2 구분자 2
         * @param separ3 구분자 3
         * @returns {string}
         */
        var getSelect = function (name, id, codestr, initdata, etc, separ1, separ2, separ3) {
            var res = "";
            var arrCode, arrInit, arrTemp;
            var strSelected, strValue, strText, strTitle;
// 옵션값 초기화
            initdata = (initdata === undefined ? "" : initdata);
            etc = (etc === undefined ? "" : etc);
            separ1 = (separ1 === undefined ? "|" : separ1);
            separ2 = (separ2 === undefined ? "^" : separ2);
            separ3 = (separ3 === undefined ? "," : separ3);


            arrCode = codestr.split(separ2);
            arrInit = initdata.split(separ3);

            res = "<SELECT NAME=\"" + name + "\"" + (id !== "" ? " id=\"" + id + "\"" : "") + (etc !== "" ? " " + etc : "") + " class='form-control form-control-sm' >";
            for (var i = 0; i < arrCode.length; i++) {
                arrTemp = arrCode[i].split(separ1);
                strSelected = "";
                for (var j = 0; j < arrInit.length; j++) {
                    if (arrTemp[0] === arrInit[j].replace(/(^\s*)|(\s*$)/gi, "")) {
                        strSelected = " selected";
                        break;
                    }
                }

                if (arrTemp.length > 2) {
                    strValue = arrTemp[0];
                    strText = arrTemp[1];
                    strTitle = arrTemp[2];
                } else if (arrTemp.length = 2) {
                    strValue = arrTemp[0];
                    strText = arrTemp[1];
                    strTitle = arrTemp[1];
                } else {
                    strValue = arrTemp[0];
                    strText = arrTemp[0];
                    strTitle = arrTemp[0];
                }

                res += "<OPTION VALUE=\"" + strValue + "\" TITLE=\"" + strTitle + "\" " + strSelected + ">" + strText + "</OPTION>";
            }
            res += "</SELECT>";

            return res;
        };

        /**
         * 체크박스 Html
         * @param name 이름(여러개일경우)
         * @param codestr 체크상자 구성용 문자열 ex> {value1}[separ1]{text1}[separ2]{value2}[separ1]{text2} ...
         * @param initdata 선택된 옵션 값(값이 여러 개일 경우 쉼표(,)로 구분 ex> val1, val2, ...)
         * @param chketc 기타 속성
         * @param lbletc 사용하지 않음
         * @param separ1 구분자 1
         * @param separ2 구분자 2
         * @param separ3 구분자 3
         * @returns {string}
         */
        var getInputCheckbox = function (name, codestr, initdata, chketc, lbletc, separ1, separ2, separ3) {
            var res = "";
            var arrCode, arrInit, arrTemp;
            var strChecked = "";
            // 옵션값 초기화
            initdata = (initdata === undefined ? "" : initdata);
            chketc = (chketc === undefined ? "" : chketc);
            lbletc = (lbletc === undefined ? "" : lbletc);
            separ1 = (separ1 === undefined ? "|" : separ1);
            separ2 = (separ2 === undefined ? "^" : separ2);
            separ3 = (separ3 === undefined ? "," : separ3);

            arrCode = codestr.split(separ2);
            arrInit = initdata.split(separ3);

            for (var i = 0; i < arrCode.length; i++) {
                arrTemp = arrCode[i].split(separ1);
                strChecked = "";
                for (var j = 0; j < arrInit.length; j++) {
                    if (arrTemp[0] === arrInit[j]) {
                        strChecked = "checked";
                        break;
                    }
                }

                var value = arrTemp[0];
                var text = arrTemp[1];
                var title = (arrTemp.length === 2 ? arrTemp[1] : arrTemp[2]);

                if (i !== 0) res += "\r\n";

                res += "<label><input type=\"checkbox\" name=\"" + name + "\" id=\"" + name + i + "\" value=\"" + value + "\" title=\"" + title + "\" " + strChecked + " " + chketc + " />&nbsp;" + text + "</label>";
//          res += "<label for=\""+ name + i +"\" title=\""+ title +"\" style=\"cursor:pointer;\" "+ lbletc +">"+ text +"</label>";
            }

            return res;
        };

        /**
         * 코드 데이터 Str
         * @param data 기본 데이터 셋
         * @param fieldNames 필드명
         * @param defaultCdStr 선택된 옵션 값(값이 여러 개일 경우 쉼표(,)로 구분 ex> val1, val2, ...)
         * @param separ1 구분자 1
         * @param separ2 구분자 2
         * @returns {string}
         */
        var getCodeStrFromData = function (data, fieldNames, defaultCdStr, separ1, separ2) {
            var res = "";
            var arrFields, arrTemp;
            defaultCdStr = (defaultCdStr === undefined ? "" : defaultCdStr);
            separ1 = (separ1 === undefined ? "|" : separ1);
            separ2 = (separ2 === undefined ? "^" : separ2);

            if (defaultCdStr !== null && defaultCdStr !== "") {
                res += defaultCdStr;
            }

            arrFields = fieldNames.split(",");
            $(data).each(function (i, d) {
                if (i > 0 || (i === 0 && res !== "")) {
                    res += separ2;
                }
                $(arrFields).each(function (j, arr) {
                    if (j > 0) {
                        res += separ1;
                    }
                    var txt = d[arr];
                    if (txt.toUpperCase().indexOf(("LANGCD"))) {
                        txt = paragonCmm.getLang(txt);
                    }
                    res += txt;
                });

            });

            return res;
        }

        /**
         * 라디오버튼
         * @param name 라디오버튼 이름
         * @param codestr 라디오버튼 구성용 문자열 ex> {value1}[separ1]{text1}[separ2]{value2}[separ1]{text2} ...
         * @param initdata 선택된 값
         * @param rdoetc 라디오버튼 스타일, 이벤트 등
         * @param lbletc 레이블 스타일, 이벤트 등
         * @param separ1 라디오버튼 구성용 문자열 value-text 구분자 ex> |
         * @param separ2 라디오버튼 구성용 문자열 value-text 그룹 구분자 ex> ^
         * @returns {string}
         */
        var getInputRadio = function (name, codestr, initdata, rdoetc, lbletc, separ1, separ2) {
            var res = "";
            var arrCode, arrTemp;
            var strChecked = "";
// 옵션값 초기화
            initdata = (initdata === undefined ? "" : initdata);
            rdoetc = (rdoetc === undefined ? "" : rdoetc);
            lbletc = (lbletc === undefined ? "" : lbletc);
            separ1 = (separ1 === undefined ? "|" : separ1);
            separ2 = (separ2 === undefined ? "^" : separ2);

            arrCode = codestr.split(separ2);

            for (var i = 0; i < arrCode.length; i++) {
                arrTemp = arrCode[i].split(separ1);
                strChecked = "";
                if (arrTemp[0] === initdata) {
                    strChecked = "checked";
                }

                var value = arrTemp[0];
                var text = arrTemp[1];
                var title = (arrTemp.length === 2 ? arrTemp[1] : arrTemp[2]);

                if (i !== 0) res += "\r\n";
                var radio = "<input type=\"radio\" name=\"" + name + "\" id=\"" + name + i + "\" value=\"" + value + "\" title=\"" + title + "\" " + strChecked + " " + rdoetc + " />&nbsp;";
                res += "<label for=\"" + name + i + "\" title=\"" + title + "\" style=\"cursor:pointer;\" " + lbletc + ">" + radio + text + "</label>";
            }

            return res;
        };

        /**
         * 코드 데이터 select option 생성
         * @param param options 데이터 생성에 필요한 옵션 {parentCd:"SYS_SOL",targetId:"targetId"}
         *       targetId     - 생성대상 Select ID
         *       targetNm     - 생성대상 Select name
         *       parentCd     - 부모코드 CD
         *       initdata     - 옵션절 기본    ( |--전체-- ,|--선택-- )
         *       valNm        - option value 컬럼명
         *       txtNm        - option text 컬럼명
         * @returns {*[]}
         */
        var getCodeSelectOpt = function (param) {
            var options = $.extend({
                targetId: {},
                parentCd: "",
                initdata: "",
                jsonData: [],
                valNm: "cd",
                txtNm: "langCd"
            }, param);
            var rtnData = [];
            var data = {};
            if (options.parentCd === "") {
                htmlUtils.initializeSelectJson(options.targetId, "", options.initdata, options.valNm, options.txtNm);
            } else {
                if ($(options.jsonData).length === 0) {
                    data["parentCd"] = options.parentCd;
                    data["useYn"] = "Y";
                    paragonCmm.callAjax(paragonCmm.getUrl("/viself/code/CodeMng/listCode/json"), data, function (json) {
                        rtnData = json.data;
                        htmlUtils.initializeSelectJson(
                            options.targetId,
                            json.data,
                            options.initdata,
                            options.valNm,
                            options.txtNm
                        );
                    });
                } else {
                    htmlUtils.initializeSelectJson(
                        options.targetId,
                        options.jsonData,
                        options.initdata,
                        options.valNm,
                        options.txtNm
                    );
                }
            }

            return rtnData;
        }

        /**
         * 코드 데이터 option String 값 생성
         * @param param options 데이터 생성에 필요한 옵션 {parentCd:"SYS_SOL",targetId:"targetId"}
         *       parentCd     - 부모코드 CD
         *       initdata     - 옵션절 기본    ( |--전체-- ,|--선택-- )
         *       valNm        - option value 컬럼명
         *       txtNm        - option text 컬럼명
         * @returns {string}
         */
        var getCodeSelectOptStr = function (param) {
            var options = $.extend({
                parentCd: "",
                initdata: "",
                valNm: "cd",
                txtNm: "langCd"
            }, param);
            var data = {};
            var rtnData = "";
            data["parentCd"] = options.parentCd;
            data["useYn"] = "Y";
            if (options.initdata !== "") {
                var initArr = options.initdata.split("|");
                var initOpt = "<option value=" + initArr[0] + ">" + initArr[1] + "</option>";
                rtnData += initOpt;
            }
            paragonCmm.callAjax(paragonCmm.getUrl("/viself/code/CodeMng/listCode/json"), data, function (json) {
                $(json.data).each(function (i, e) {
                    var opt = "<option value=" + e.cdAbb + ">" + paragonCmm.getLang(e.langCd) + "</option>";
                    rtnData += opt;
                });
            });
            return rtnData;
        }

        /**
         * 생성 테이블 페이징 처리
         */
        var createPageDiv = function (id, totalCount, pageNo, rowSize, func) {

            var $pageDiv = $("#" + id + "Page");
            $pageDiv.attr("class", "dataTables_paginate paging_bootstrap pagination");
            $pageDiv.html($("<ul>").attr("id", "pageUl"));

            if (pageNo <= 0) pageNo = 1;
            if (rowSize <= 0) rowSize = 10;
            var pageListSize = 10;
            var lastPageNo = 1;

            var totalPage = Math.ceil(totalCount / rowSize);
            if (pageListSize > totalPage) pageListSize = totalPage;
            var pageGroup = Math.ceil(pageNo / pageListSize);

            var last = pageGroup * pageListSize;    // 화면에 보여질 마지막 페이지 번호
            if (last > totalPage)
                last = totalPage;
            var first = last - (pageListSize - 1);    // 화면에 보여질 첫번째 페이지 번호
            var next = last + 1;
            var prev = first - 1;

            //--first
            $("<li>").append($("<a href='javascript:void(0);'>").attr("onClick", func + "(1)").text(" << ")).appendTo($("#pageUl"));

            if (prev < 1) {
                prev = pageNo - 1;
                if (prev < 1) {
                    prev = 1;
                }
            }
            //--prev
            $("<li>").append($("<a href='javascript:void(0);'>").attr("onClick", func + "(" + prev + ")").text(" < ")).appendTo($("#pageUl"));
            for (var i = first; i <= last; i++) {
                if (i > first) {
                    //$pageDiv.append($("<span class=\"page_separ\">").text("|"));
                }
                if (i === pageNo) {
                    $("<li class='active'>").append($("<a href='javascript:void(0);'>").attr("onClick", func + "(" + i + ")").text(i)).appendTo($("#pageUl"));
//            $("<li>").attr("onClick", func+"("+i+")").append($("<span class=\"page_no\">").text(i)).appendTo($("#pageUl"));
                } else {
                    $("<li>").append($("<a href='javascript:void(0);'>").attr("onClick", func + "(" + i + ")").text(i)).appendTo($("#pageUl"));
//            $("<li>").attr("onClick", func+"("+i+")").text(i).appendTo($("#pageUl"));
                }
            }

            if (next > totalPage) {
                next = pageNo + 1;
                if (next > totalPage) {
                    next = totalPage;
                }
            }
            //--next
            $("<li>").append($("<a href='javascript:void(0);'>").attr("onClick", func + "(" + next + ")").text(" > ")).appendTo($("#pageUl"));
//    $("<li>").attr("onClick", func+"("+next+")").append($("<span> > </span>")).appendTo($("#pageUl"));
            $("<li>").append($("<a href='javascript:void(0);'>").attr("onClick", func + "(" + totalPage + ")").text(" >> ")).appendTo($("#pageUl"));
//    $("<li>").attr("onClick", func+"("+totalPage+")").append($("<span> >> </span>")).appendTo($("#pageUl"));

        }

        /**
         * jsonData 로 테이블 rows 생성 / 페이징 처리
         * @param id   :  적용할 요소 ID
         * @param jsonData  : {"total":n,"rows":[{},{},{}....]} 형식 유지 필요
         *        total : 게시물 총갯수
         *        rows : 실 게시물 데이터
         * @param pageNo  : 현재 페이징 번호 해당 값이 없다면 페이징 하지 않는다.
         * @param rowSize  : row 갯수
         * @param func   : 페이징 함수 명
         */
        var createTableRow = function (id, jsonData, pageNo, rowSize, func) {
            var total = jsonData.total;
            var rows;

            rows = jsonData;
            if (jsonData.rows !== undefined) {
                rows = jsonData.rows;
            }

            var tr = $("tr", "#" + id);
            var $th = $("th", "#" + id);
            var $tbody = $("<tbody id='" + id + "Body'>");

            if (rows !== "" && "NO_RESULT" !== rows.RESULT) {
//        console.log(JSON.stringify(rows));
                if (pageNo !== "undefined" && pageNo > 0 && "NO_RESULT" !== rows.RESULT) {
                    this.createPageDiv(id, total, pageNo, rowSize, func);
                }

                $("#" + id + "Body").remove();
                $(rows).each(function (i, d) {
                    var $tr = $("<tr>");

                    var trOpt = $(tr).data("opt");

                    //-- TR tag에 링크가 존재 하는지 확인
                    if (trOpt && trOpt["onClick"]) {

                        //-- 클릭 이벤트가 존재할 경우
                        var href = trOpt["onClick"];
                        var cnt = (trOpt["onClick"].match(/@{/g) || []).length;
                        for (var i = 0; i < cnt; i++) {
                            var startIdx = (href.indexOf("@{"));
                            var endIdx = (href.indexOf("}", href.indexOf("@{")));
                            var colNm = href.substring(startIdx + 2, endIdx);
                            var fullTxt = href.substring(startIdx, endIdx + 1);

                            href = href.replace(fullTxt, d[colNm]);
                        }
                        $tr.attr("onClick", href);
                        $tr.css("cursor", "pointer");

                    }
                    if (trOpt && trOpt["class"]) {

                        //-- class 요소가 있을 경우 2022-04-13 lok
                        var href = trOpt["class"];
                        var cnt = (trOpt["class"].match(/@{/g) || []).length;
                        for (var i = 0; i < cnt; i++) {
                            var startIdx = (href.indexOf("@{"));
                            var endIdx = (href.indexOf("}", href.indexOf("@{")));
                            var colNm = href.substring(startIdx + 2, endIdx);
                            var fullTxt = href.substring(startIdx, endIdx + 1);

                            href = href.replace(fullTxt, d[colNm]);
                        }
                        $tr.attr("class", href);
                    }
                    $th.each(function (j, e) {

                        var $td = $("<td>");
                        var opt = $(e).data("opt");
                        //-- tr key 설정
                        if (opt["trKey"]) {
                            $tr.attr("id", d[opt["trKey"]]);
                        }
                        //-- CSS
                        if (opt["align"]) {
                            $td.css("text-align", opt["align"]);
                        }
                        if (opt["width"]) {
                            $td.css("width", opt["width"]);
                        }
                        if (opt["style"]) {
                            $td.css(opt["style"]);
                        }
                        if (opt["class"]) {
                            $td.addClass(opt["class"]);
                        }

                        //-- 링크가 존재 하는지 확인
                        if (opt["href"] || opt["onClick"]) {

                            var aT = $("<a>");
                            if (opt["href"]) {                    //-- 링크가 존재 할 경우
                                var href = opt["href"];
                                var cnt = (opt["href"].match(/@{/g) || []).length;
                                for (var i = 0; i < cnt; i++) {
                                    var startIdx = (href.indexOf("@{"));
                                    var endIdx = (href.indexOf("}", href.indexOf("@{")));
                                    var colNm = href.substring(startIdx + 2, endIdx);
                                    var fullTxt = href.substring(startIdx, endIdx + 1);

                                    href = href.replace(fullTxt, d[colNm]);
                                }
                                aT.attr("href", href);
                                aT.attr("target", "_blank");
                                if (opt["col"].toUpperCase().indexOf(("LANGCD")) > -1) {
                                    //-- 언어코드 적용
                                    aT.html(paragonCmm.getLang(d[opt["col"]]));

                                } else {
                                    aT.html(d[opt["col"]]);
                                }

                                $td.append(aT);

                            } else if (opt["onClick"]) {            //-- 클릭 이벤트가 존재할 경우

                                var href = opt["onClick"];
                                var cnt = (opt["onClick"].match(/@{/g) || []).length;
                                for (var i = 0; i < cnt; i++) {
                                    var startIdx = (href.indexOf("@{"));
                                    var endIdx = (href.indexOf("}", href.indexOf("@{")));
                                    var colNm = href.substring(startIdx + 2, endIdx);
                                    var fullTxt = href.substring(startIdx, endIdx + 1);

                                    href = href.replace(fullTxt, d[colNm]);
                                }
                                $td.attr("onClick", href);
                                $td.css("cursor", "pointer");
                                if (opt["col"].toUpperCase().indexOf(("LANGCD")) > -1) {
                                    //-- 언어코드 적용
                                    $td.append(paragonCmm.getLang(d[opt["col"]]));
                                } else {
                                    $td.append(d[opt["col"]]);
                                }

                            }

                        } else {
                            if (opt["inputHidden"]) {                //-- 생성할 hidden TAG가 있을경우
                                var ids = opt["inputHidden"].split(";");
                                $(ids).each(function (i, e) {
                                    var $input = $("<input type=\"hidden\">");
                                    $input.attr("name", ids[i]);
                                    $input.val(d[ids[i]]);
                                    $td.append($input)
                                });
//                        $td.append(d[opt["col"]]);   //-- 아래 로직에서 추가 해 줌으로 여기선 주석
                            }

                            if (opt["html"]) {                        //-- content가 Html일경우 col은 무시된다.
                                var $html = opt["html"];
                                if ("BTN" === $html["type"]) {            //-- html type이 BTN(버튼) 일경우
                                    var $span = $("<span class=\"ui_btn small icon\">");
                                    var $i = $("<i class=\"" + $html["class"] + "\">");
                                    var $a = $("<a href=\"javascript:void(0)\">");
                                    if ($html["title"]) {
                                        $a.text(paragonCmm.getLang($html["title"]));
                                    }
                                    $i.append($a);

                                    var href = $html["callback"];    //-- 버튼에 걸릴 callback 링크
                                    var cnt = ($html["callback"].match(/@{/g) || []).length;
                                    for (var i = 0; i < cnt; i++) {
                                        var startIdx = (href.indexOf("@{"));
                                        var endIdx = (href.indexOf("}", href.indexOf("@{")));
                                        var colNm = href.substring(startIdx + 2, endIdx);
                                        var fullTxt = href.substring(startIdx, endIdx + 1);

                                        href = href.replace(fullTxt, d[colNm]);
                                    }
                                    $i.attr("onClick", href);

                                    $span.append($i);
                                    $td.append($span);
                                } else if ("fileDown" === $html["type"]) {    //-- html type이 파일다운 일경우
                                    var fileNm = d[$html["name"]];
                                    var fileVal = d[$html["value"]];

                                    var arrNm = fileNm.split("/");
                                    var arrVal = fileVal.split("/");
                                    if (fileNm !== "" && arrNm.length > 0) {
                                        var $ul = $("<ul>").attr("class", "attach_file_view");
                                        $(arrNm).each(function (k, a) {
                                            var $li = $("<li class=\"fa fa-save\" style=\"color:#666\">");
                                            var $a = $("<a>");

                                            $a.attr("href", "javascript:paragonCmm.popupAttachFileDownload(\"" + arrVal[k] + "\")");
                                            $a.text(" " + arrNm[k]);

                                            $li.append($a);
                                            $ul.append($li);
                                        });
                                        $td.append($ul);
                                    }
                                } else if ("link" === $html["type"]) {
                                    var $a = $("<a href=\"javascript:void(0);\">");

                                    if ($html["event"]) {
                                        $($html["event"]).each(function (i, e) {

                                            var $event = e;    //-- 이벤트 처리
                                            var $href = $event["href"];
                                            var cnt = ($event["href"].match(/@{/g) || []).length;
                                            for (var i = 0; i < cnt; i++) {
                                                var startIdx = ($href.indexOf("@{"));
                                                var endIdx = ($href.indexOf("}", $href.indexOf("@{")));
                                                var colNm = $href.substring(startIdx + 2, endIdx);
                                                var fullTxt = $href.substring(startIdx, endIdx + 1);

                                                $href = $href.replace(fullTxt, d[colNm]);
                                            }
                                            $a.attr($event["type"], $href);

                                        });
                                    }
                                    $a.text(d[$html["name"]]);
                                    $td.append($a);
                                } else if ("checkbox" === $html["type"]) {    //-- html type이 checkbox 일경우
                                    var $check = $("<input type='checkbox'>");
                                    $check.attr("name", $html["name"]);
                                    if ($html["name"]) { //-- 체크박스의  이름이 있을경우 id 설정
                                        $check.attr("id", $html["name"] + "_" + j);
                                    }
                                    if ($html["valueField"]) {    //-- 체크박스의  Value 값 지정
                                        $check.val(d[$html["valueField"]]);
                                    }
                                    if ($html["checkAll"]) {    //-- 체크박스 전체 체크 여부
                                        var $checkAll = $("<input type='checkbox' id='checkAll'>");
                                        $checkAll.attr("onclick", "($(this).prop('checked'))?$('input:checkbox[name=\"" + $html["name"] + "\"]').prop('checked',true):$('input:checkbox[name=\"" + $html["name"] + "\"]').prop('checked',false)");
                                        $(e).html($checkAll);    //-- th 태그에 추가
                                    }
                                    if ($html["event"]) {
                                        $($html["event"]).each(function (i, e) {

                                            var $event = e;    //-- 이벤트 처리
                                            var $href = $event["href"];
                                            var cnt = ($event["href"].match(/@{/g) || []).length;
                                            for (var i = 0; i < cnt; i++) {
                                                var startIdx = ($href.indexOf("@{"));
                                                var endIdx = ($href.indexOf("}", $href.indexOf("@{")));
                                                var colNm = $href.substring(startIdx + 2, endIdx);
                                                var fullTxt = $href.substring(startIdx, endIdx + 1);

                                                $href = $href.replace(fullTxt, d[colNm]);
                                            }
                                            $check.attr($event["type"], $href);

                                        });

                                    }
                                    $td.append($check);
                                } else if ("radio" === $html["type"]) {    //-- html type이 radio 일경우
                                    var $check = $("<input type='radio'>");
                                    $check.attr("name", $html["name"]);
                                    if ($html["name"]) { //-- 라디오의  이름이 있을경우 id 설정
                                        $check.attr("id", $html["name"] + "_" + j);
                                    }
                                    if ($html["valueField"]) {    //-- 라이도의  Value 값 지정
                                        $check.val(d[$html["valueField"]]);
                                    }
                                    if ($html["event"]) {
                                        $($html["event"]).each(function (i, e) {

                                            var $event = e;    //-- 이벤트 처리
                                            var $href = $event["href"];
                                            var cnt = ($event["href"].match(/@{/g) || []).length;
                                            for (var i = 0; i < cnt; i++) {
                                                var startIdx = ($href.indexOf("@{"));
                                                var endIdx = ($href.indexOf("}", $href.indexOf("@{")));
                                                var colNm = $href.substring(startIdx + 2, endIdx);
                                                var fullTxt = $href.substring(startIdx, endIdx + 1);

                                                $href = $href.replace(fullTxt, d[colNm]);
                                            }
                                            $check.attr($event["type"], $href);

                                        });

                                    }
                                    $td.append($check);

                                } else if ("combobox" === $html["type"]) {    //-- html type이 combobox 일경우
                                    var $combo = $("<select >");
                                    $combo.attr("name", $html["name"]);
                                    if ($html["options"]) {
                                        var opts = $html["options"];
                                        $(opts["data"]).each(function (idx, sub) {
                                            var opt = $("<option>");
                                            opt.val(sub[opts["valueField"]]);
                                            opt.text(sub[opts["textField"]]);

                                            $combo.append(opt);
                                        });
                                    }

                                    if ($html["event"]) {
                                        $($html["event"]).each(function (i, e) {

                                            var $event = e;    //-- 이벤트 처리
                                            var $href = $event["href"];
                                            var cnt = ($event["href"].match(/@{/g) || []).length;
                                            for (var i = 0; i < cnt; i++) {
                                                var startIdx = ($href.indexOf("@{"));
                                                var endIdx = ($href.indexOf("}", $href.indexOf("@{")));
                                                var colNm = $href.substring(startIdx + 2, endIdx);
                                                var fullTxt = $href.substring(startIdx, endIdx + 1);

                                                $href = $href.replace(fullTxt, d[colNm]);
                                            }
                                            $combo.attr($event["type"], $href);

                                        });

                                    }
                                    $combo.val(d[$html["name"]]);
                                    $td.append($combo);

                                } else if ("text" === $html["type"]) {

                                    var $input = $("<input type=\"text\">");

                                    if ($html["event"]) {
                                        $($html["event"]).each(function (i, e) {

                                            var $event = e;    //-- 이벤트 처리
                                            var $href = $event["href"];
                                            var cnt = ($event["href"].match(/@{/g) || []).length;
                                            for (var i = 0; i < cnt; i++) {
                                                var startIdx = ($href.indexOf("@{"));
                                                var endIdx = ($href.indexOf("}", $href.indexOf("@{")));
                                                var colNm = $href.substring(startIdx + 2, endIdx);
                                                var fullTxt = $href.substring(startIdx, endIdx + 1);

                                                $href = $href.replace(fullTxt, d[colNm]);
                                            }
                                            $input.attr($event["type"], $href);

                                        });

                                    }

                                    $input.attr("name", $html["name"]);
                                    $input.attr("class", $html["class"]);
                                    $input.val(d[$html["name"]]);
                                    $td.append($input);
                                }

                            } else if (opt["col"]) {
                                //-- 라인번호 설정
                                if (opt["col"] === "ROWSEQ" && total) {

                                    var pagingRowNo = d["rn"];

                                    $td.append((total - parseInt(pagingRowNo) + 1));

                                } else if (opt["dataTp"]) {                    //-- 데이터 타입이 존재 할 경우

                                    if ("currency" === opt["dataTp"]) {
                                        $td.append(paragonCmm.getFormatCurrency(d[opt["col"]], 0));
                                    } else if ("date" === opt["dataTp"]) {
                                        $td.append(paragonCmm.validateDateValue(d[opt["col"]], 0));
                                    } else if (opt["dataTp"].startsWith("trunc")) {
                                        var len = opt["dataTp"].split("__")[1];
                                        /*if(len > 20){
                                            len = 20;
                                        }*/
                                        //$td.append(paragonCmm.getTrunc( d[opt["col"]], len));
                                        $td.append("<p title=\"" + d[opt["col"]] + "\">" + paragonCmm.getTrunc(d[opt["col"]], len) + "</p>");
                                    } else {
                                        $td.append(d[opt["col"]]);
                                    }
                                } else {
                                    if (opt["col"].toUpperCase().indexOf(("LANGCD")) > -1) {

                                        //-- 언어코드 적용
                                        var txtValue = paragonCmm.getLang(d[opt["col"]]);
                                        if (opt["formatter"]) {
                                            txtValue = (new Function("return " + opt["formatter"] + '("' + txtValue + '");'))();
                                        }
                                        $td.append(txtValue);

                                    } else {

                                        /* 기존 제목인 tit에 추가로 attr("title") 마우스를 대면 생략된 제목을 표시하는 기능 */

                                        var txtValue = d[opt["col"]];
                                        var contentValue = d[opt["tit"]];
                                        if (opt["formatter"]) {
                                            txtValue = (new Function("return " + opt["formatter"] + '("' + txtValue + '");'))();
                                        }
                                        $td.attr('title', contentValue);
                                        $td.append(txtValue);

                                    }

                                }
                            }
                        }
                        $tr.append($td);
                    });
                    $tbody.append($tr);
                });
                $("#" + id).append($tbody);

            } else {
                var $pageDiv = $("#" + id + "Page");
                $pageDiv.html("");
                $("#" + id + "Body").html("");
                var $tr = $("<tr>");
                var $td = $("<td>");
                $td.attr("colspan", $th.length);
                $td.css("text-align", "center");
                $td.text("No result.");
                $tr.append($td);
                $("#" + id + "Body").html($tr);
            }
        }

        /**
         * code 데이터 codeStr 형태로 반환 (값|텍스트^값|텍스트)
         * @param parentCd    : 부모코드
         * @param fields    : 코드 컬럼명 value,text
         * @param inCdAbbs    : 허용할 코드 데이터( 없으면 전체 데이터를 가져오고, 있으면 지정된 데이터만 가져온다. )
         * @param defaultStr: 기본이 되는 데이터 셋 ( |--전체-- ,|--선택-- )
         * @param callBackFnc : 콜백함수
         * @author Options :
         *  */
        var loadCodeStr = function (param) {

            var data = {
                "parentCd": "",
                "useYn": "Y",
                "fields": "cd,langCd",
                "defaultStr": "",
                "callBackFnc": function () {
                }
            };
            $.extend(data, param);

            paragonCmm.callAjax(paragonCmm.getUrl("/viself/code/CodeMng/listCodeForDTO/json"), data, function (json) {
                data.callBackFnc(htmlUtils.getCodeStrFromData(json.data, data.fields, data.defaultStr));
            }, false);
        }

        var postLoadFileHtml = function () {
            var arr = htmlUtils.writeFiles.loads;
            $(arr).each(function (i, e) {
                var targetId = e.targetId;
                var options = e.options;
                targetId = (targetId === undefined || targetId === null ? "" : targetId);
                if (targetId !== "") {
                    var url = paragonCmm.getUrl("/paragon/file/fileWrite");

                    $("#" + targetId).load(url, options);
                } else {
                    console.debug("targetId is null!!");
                }

            });

            htmlUtils.writeFiles.isPostLoad = false;
        };

        /**
         * 파일 업로드 영역 Html load
         * @param targetId    : 파일첨부가 되어야 하는 ID
         * @param options   : 파일이 그려지기위한  옵션값
         *                - 필수  : relUid, fileTpCd, defaultRelUid(수정시 필수)
         */
        var loadFileHtml = function (targetId, options) {
            if (htmlUtils.writeFiles.isPostLoad) {
                htmlUtils.writeFiles.loads.push({
                    "targetId": targetId
                    , "options": options
                });
            } else {
                targetId = (targetId === undefined || targetId === null ? "" : targetId);

                if (targetId !== "") {
                    var url = paragonCmm.getUrl("/paragon/file/fileWrite");

                    $("#" + targetId).load(url, options);
                } else {
                    console.debug("targetId is null!!");
                }
            }
        };

        /**
         * 파일 뷰 영역 Html load
         * @param targetId    : 파일첨부가 되어야 하는 ID
         * @param options   : 파일이 그려지기위한  옵션값
         *                 - 필수  : relUid, fileTpCd, defaultRelUid(수정시 필수)
         */
        var loadFileView = function (targetId, options) {
            targetId = (targetId === undefined || targetId === null ? "" : targetId);

            if (targetId !== "") {
                var url = paragonCmm.getUrl("/paragon/file/fileView");

                $("#" + targetId).load(url, options);
            } else {
                console.debug("targetId is null!!");
            }
        };

        /**
         * code 데이터 codeStr 형태로 반환 (값|텍스트^값|텍스트)
         * @param targetId    : 파일첨부가 되어야 하는 ID
         * @param options    : 파일이 그려지기위한  옵션값
         *                 - 필수  : fileNm
         */
        var fileDownload = function (atchUid) {
            var url = paragonCmm.getUrl("/paragon/file/download?atchUid=" + atchUid);
            location.href = url
        };

        /**
         * 싱글, 멀티 파일 압축 후 다운로드
         * @param options objId: 일괄다운로드 정보 (data-dn-info) 가 있는 object id, list: 다운로드를할 solMasUid 리스트
         */
        var multiDownload = function (options) {

            var param = $.extend({
                objId: null,
                relUid: null,
                fileTpCd: null,
                solMasUid: null,
                list: []
            }, options);

            if (paragonCmm.isNotEmpty(param.objId)) {
                param = $.extend(param, $("#" + param.objId).data("dn-info"));
            }

            var opts = {
                url: paragonCmm.getUrl("/paragon/file/download/getMulti"),
                param: param,
                cbFnc: function (json) {
                    if (json.errYn === "E") {
                        //-- 오류처리
                        $.messager.alert("Error", paragonCmm.getLang(json.msg), "error");
                        return false;
                    }
                    // console.debug(json);
                    if (json.zipUid) {
                        var url = paragonCmm.getUrl("/paragon/file/download/multi?zipUid=" + filterXSS(json.zipUid));
                        location.href = url;
                    }
                }
            };

            $.messager.confirm("Info", "전체 다운로드 하시겠습니까?", function (r) {
                if (r) {
                    paragonCmm.callAjax(opts.url, opts.param, opts.cbFnc);
                }
            });
        };

        /**
         * code 데이터 codeStr 형태로 반환 (값|텍스트^값|텍스트)
         * @param targetId    : 파일첨부가 되어야 하는 ID
         * @param options    : 파일이 그려지기위한  옵션값
         *                 - 필수  : fileNm
         */
        var formDownload = function (fileNm) {
            fileNm = (fileNm === undefined || fileNm === null ? "" : fileNm);

            var url = paragonCmm.getUrl("/paragon/file/download/form?fileNm=" + encodeURIComponent(fileNm));
            location.href = url
        };


// X-FREE EDITER OBJECT
        /**
         * code 데이터 codeStr 형태로 반환 (값|텍스트^값|텍스트)
         * @param targetId    : 에디터가 그려져야 되는 Area ID
         * @param options    : 에디터가 그려지기위한  옵션값
         *                 - 필수  : sCtrlName
         *              - 비필수 : sCtrlId,editorType,sInitData,sCtrlEtc,sWidth,sHeight,sToolbarSet
         */
        var getHtmlEditor = function (params) {

            var sHeight = params.sHeight === "" ? "230px" : params.sHeight;

            var options = $.extend({
                sCtrlName: ""
                , sCtrlId: ""
                , editorType: paragonCmm.getDefaultEditorType()
                , sInitData: ""
                , sCtrlEtc: ""
                , sWidth: "100%"
                , sHeight: sHeight
                , sToolbarSet: "Default"
            }, params);

            switch (options.editorType.toUpperCase()) {
                case "X_FREE":
                    var xfe = new XFE({
                        basePath: paragonCmm.getUrl("/js/vendor/x-free-editor/4.0.1"),
                        width: options.sWidth,
                        height: options.sHeight,
                        ignoreMinHeight: true
                    });
                    xfe.render(options.sCtrlId);
                    XfreeEditor[options.sCtrlId] = xfe;
                    break;
                default:
                    CKEDITOR.replace(options.sCtrlId, {
                        width: options.sWidth,
                        height: options.sHeight,
                        language: paragonCmm.getSiteLocale().toLowerCase()
                    });
                    break;
            }
        };


        /**
         * 공통 모달 띄우기
         */
        var openModal = function (opt) {
            var $dialog = {};
            var defModalId = paragonCmm.getRandomUUID(); //-- 모달의 ID
            var option = {};
            option.cbFnc = function () {
            }                //-- 콜백함수
            option.reqParam = {};                    //-- 모달에 보낼 파라메터
            option.title = "모달제목";                    //-- 모달의 헤더 타이틀
            option.width = 850;                        //-- 모달의 넓이
            option.height = 550;                    //-- 모달의 높이
            option.href = "/test/text.modal";        //-- 모달 로드 URL
            option.modalId = defModalId;
            option.contentId = "modal_" + defModalId;                //-- 모달 내용의 ID
            option.maximizable = false;            //-- 모달 전체창 가능 여부 기본 false

            option = $.extend(option, opt);

            var initModal = function () {
                $dialog = document.getElementById(option.modalId);
                if (!$dialog) {
                    var DIALOG_HTML = "<div id='" + option.modalId + "'/>"
                    $("body").append(DIALOG_HTML);
                }
                $dialog = $("#" + option.modalId);
                $dialog.html("");
            };

            //-- 모달 초기화
            initModal();
            //-- 모달에 전달 Param
            var param = {
                openerData: JSON.stringify(option.reqParam)
            };
            $.extend(param, opt.reqAddParam);
            $dialog.dialog({
                iconCls: "fa fa-info-circle",
                title: option.title,
                // top: 100,
                width: option.width,
                height: option.height,
                href: option.href,
                cache: false,
                modal: true,
                resizable: true,
                maximizable: option.maximizable,
                method: "post",
                queryParams: param,
                buttons: null,
                onClose: function () {
                    // HACK 이벤트를 지우지 않으면 계속 쌓인다.
                    // resetEvent();
                    $dialog.dialog("destroy");
                },
                onLoad: function () {
                    // include 페이지 최상위 DOM id
                    var $DIALOG_ROOT_NODE = $("#" + option.contentId);
                    // close callback function
                    var close = function () {
                        $dialog.dialog("close");
                    };

                    // 1. 가운데에서 뜨기
                    $dialog.dialog("center");

                    // 2. 다국어 설정
                    paragonCmm.convertLang($dialog);

                    // 3. callback function 설정
                    $DIALOG_ROOT_NODE.data("callback", option.cbFnc);

                    // 4. callback function(close) 설정
                    $DIALOG_ROOT_NODE.data("callback-close", close);
                }
            });

        };

        /**
         * 모달 다이얼로그(JQuery Easy UI) 오픈
         *
         * options = {
         *         modalId: 모달아이디,
         *         href: 다이얼로그 URL,
         *         title: 타이틀,
         *         width: 864,
         *         height: 700,
         *         params: 처리 parameter,
         *         cbFnc: callback function
         *     }
         *     [callback 호출 참고]
         *     data("callback"): cbFnc 호출후 dialog close 까지 호출하는 callback
         *     data("callback-only"): cbFnc 만 호출하는 callback
         *     data("callback-close"): close 만 호출하는 callback
         *     [주의]
         *         - JQuery Easy UI dialog 의존성
         *         - JQuery Easy UI dialog properties 중에서
         *           queryParams, onClose, onLoad 강제 설정됨
         *
         * @param options options
         */
        var openDialog = function (options) {

            var opt = $.extend({
                iconCls: "fa fa-info-circle",
                method: "post",
                cache: false,
                modal: true,
                href: null,
                title: null,
                width: 864,
                height: 550,
                modalId: null,
                cbFnc: null,
                params: {}
            }, options);

            // 1. 모달 초기화
            var $dialog = (function () {
                var modalUid = paragonCmm.isNotEmpty(opt.modalId) ? opt.modalId : paragonCmm.getRandomUUID();
                var $dialog = document.getElementById(modalUid);
                if (!$dialog) {
                    var DIALOG_HTML = "<div id='" + modalUid + "'/>"
                    $("body").append(DIALOG_HTML);
                }
                $dialog = $("#" + modalUid);
                $dialog.html("");

                // console.debug("modalUid...."+modalUid);
                // console.debug("id?...."+$dialog.attr("id"));
                opt.params.modalId = modalUid;
                return $dialog;
            })();

            // 2. call-back 설정
            $dialog.data("callback", function (data) {
                if (typeof opt.cbFnc === "function" && typeof data !== "undefined") {
                    opt.cbFnc(data);
                }
                $dialog.dialog("close");
            });
            $dialog.data("callback-only", function (data) {
                if (typeof opt.cbFnc === "function" && typeof data !== "undefined") {
                    opt.cbFnc(data);
                }
            });
            $dialog.data("callback-close", function (data) {
                $dialog.dialog("close");
            });

            // 3. 추가 옵션 설정
            /*
             * 필수 옵션인 modalId, openerData 외에 추가 parameter 를
             */
            opt.queryParams = $.extend(opt.params, {
                modalId: $dialog.attr("id"), // call-back 을 얻기위해 id 전달
                openerData: JSON.stringify(opt.params)
            });

            opt.onClose = function () {
                // HACK 이벤트를 지우지 않으면 계속 쌓인다.
                // 1. HTML 지우기
                $dialog.dialog("destroy");
            };
            if (typeof opt.onLoad === "undefined") {
                opt.onLoad = function () {

                    if (paragonCmm.isEmpty(opt.top) && paragonCmm.isEmpty(opt.left)) {
                        // 1. 가운데 열기
                        $dialog.dialog("center");
                    }

                    // 2. 다국어 설정
                    paragonCmm.convertLang($dialog);
                };
            }

            // 4. open modal
            $dialog.dialog(opt);
        };

        /**
         * ajax 결과로 넘어오는 메시지를 JQuery EasyUI 토스트로 표시
         * @param data {errYn, msg}
         */
        var showMsg = function (data) {

            var opt = $.extend({
                errYn: null,
                msg: null
            }, data);

            if (opt.errYn !== "E") {
                if (paragonCmm.isNotEmpty(opt.msg)) {
                    $.messager.show({
                        title: "Info",
                        msg: paragonCmm.getLang(opt.msg),
                        icon: "info",
                        timeout: 1000
                    });
                }
            } else {
                $.messager.alert("Warning", paragonCmm.getLang(opt.msg), "warning");
                // $.messager.alert("Error", paragonCmm.getLang(opt.msg), "error");
            }
        };

        /**
         * 팝업 오픈
         *
         * options = {
         *         url: 팝업내용 URL,
         *         targetName: 팝업 타겟 이름(default: POP_%UUID%),
         *         popupTitle: 팝업 타이틀,
         *         popWidth: 팝업창 너비(default: 1152),
         *         popHeight: 팝업창 높이(default: 500),
         *         openerData: JSON.stringify 로 전달될 파라메터,
         *         params: 임시폼에서 hidden 으로 전달될 파라메터
         *     }
         * @param options options
         */
        var openPopup = function (options) {
            // 1. options 설정
            var opts = $.extend({
                url: null,
                targetName: "POP_" + paragonCmm.getRandomUUID(),
                popupTitle: "",
                popWidth: 1152,
                popHeight: 500,
                openerData: null,
                params: null
            }, options);

            // console.debug(options);
            // console.debug(opts);

            // 2. 오픈 팝업
            paragonCmm.openWindow("", opts.popWidth, opts.popHeight, opts.targetName, "yes", "yes", "");

            // 3. 임시 폼 생성
            var imsiForm = $("<form method='POST'>").attr("action", opts.url);
            imsiForm.append($("<input type='hidden' name='popupTitle'>").val(opts.popupTitle));
            imsiForm.append($("<input type='hidden' name='openerData'>").val(JSON.stringify(opts.openerData)));
            imsiForm.append($("<input type='hidden' name='_csrf'>").val($("meta[name='_csrf']").attr("content")));

            if (paragonCmm.isNotEmpty(opts.params)) {
                var keys = Object.keys(opts.params);
                keys.forEach(function (key) {
                    imsiForm.append($("<input type='hidden' name='" + key + "'>").val(opts.params[key]));
                });
            }
            imsiForm.attr("target", opts.targetName);
            imsiForm.appendTo("body");


            // 4. 임시 폼 전송 및 삭제
            imsiForm.submit();
            imsiForm.remove();
        };


        /**
         * .icon-info 클래스와 title 속성을 사용한 icon 에 easyui tooltip 을 생성하는 함수
         * @param options rootObj: tooltip 을 적용할 jQuery node Object
         */
        var easyTooltip = function (options) {
            var opts = $.extend({
                rootObj: null
            }, options);

            var $icons = $(".icon-info", opts.rootObj);
            $icons.each(function (idx, itm) {
                $(itm).tooltip({
                    content: paragonCmm.getLang($(itm).attr("title")),
                    onShow: function () {
                        $(this).tooltip('tip').css({
                            // INFO
                            backgroundColor: "#d9edf7",
                            color: "#31708f"

                            // WARNING
                            // backgroundColor: "#fcf8e3",
                            // color: "#8a6d3b"

                            // SUCCESS
                            // backgroundColor: "#dff0d8",
                            // color: "#3c763d"

                            // backgroundColor: '#666',
                            // borderColor: '#666'
                        });
                    }
                });
            });
        };


        return {
            createPageDiv: createPageDiv,
            createTableRow: createTableRow,


            easyTooltip: easyTooltip,
            openDialog: openDialog,
            openModal: openModal,
            openPopup: openPopup,
            showMsg: showMsg,

            openDualCalendar: openDualCalendar,
            openCalendar: openCalendar,

            convertDateFormat: convertDateFormat,
            fileDownload: fileDownload,
            formDownload: formDownload,
            getSelect: getSelect,
            initializeSelectJson: initializeSelectJson,
            getCodeSelectOpt: getCodeSelectOpt,
            getCodeSelectOptStr: getCodeSelectOptStr,
            getCodeStrFromData: getCodeStrFromData,
            getInputCheckbox: getInputCheckbox,
            getInputRadio: getInputRadio,
            getHtmlEditor: getHtmlEditor,
            loadCodeStr: loadCodeStr,
            loadFileHtml: loadFileHtml,
            loadFileView: loadFileView,
            multiDownload: multiDownload,
            postLoadFileHtml: postLoadFileHtml
        };
    })();

    hu.createPageDiv = utils.createPageDiv;
    hu.createTableRow = utils.createTableRow;
    hu.easyTooltip = utils.easyTooltip;
    hu.openDialog = utils.openDialog;
    hu.openModal = utils.openModal;
    hu.openPopup = utils.openPopup;
    hu.showMsg = utils.showMsg;
    hu.openDualCalendar = utils.openDualCalendar;
    hu.openCalendar = utils.openCalendar;
    hu.convertDateFormat = utils.convertDateFormat;
    hu.fileDownload = utils.fileDownload;
    hu.formDownload = utils.formDownload;
    hu.getSelect = utils.getSelect;
    hu.initializeSelectJson = utils.initializeSelectJson;
    hu.getCodeSelectOpt = utils.getCodeSelectOpt;
    hu.getCodeSelectOptStr = utils.getCodeSelectOptStr;
    hu.getCodeStrFromData = utils.getCodeStrFromData;
    hu.getInputCheckbox = utils.getInputCheckbox;
    hu.getInputRadio = utils.getInputRadio;
    hu.getHtmlEditor = utils.getHtmlEditor;
    hu.loadCodeStr = utils.loadCodeStr;
    hu.loadFileHtml = utils.loadFileHtml;
    hu.loadFileView = utils.loadFileView;
    hu.multiDownload = utils.multiDownload;
    hu.postLoadFileHtml = utils.postLoadFileHtml;

})(htmlUtils);