/*
 * @(#)paragon.base.js     2023-04-14 오후 2:13
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

// String
(function (p) {
    "use strict";

    var stringUtils = (function () {
        /**
         * 문자열 좌우공백 제거
         * @param str
         * 참고 URL : https://rocabilly.tistory.com/11
         */
        var getTrim = function (val) {
            if (paragonCmm.isEmpty(val)) {
                return '';
            }
            if (typeof val === 'number') {
                val = val + '';
            }
            if (typeof val === 'string') {
                val = val.replace(/(^\s*)|(\s*$)/gi, "");
            }
            return val;
        };

        /**
         * 문자열의 길이가 제한된 길이보다 길 경우 자르고 말줄임표 삽입
         * @param str 문자열
         * @param len 길이
         * @param apos 말줄임표
         * @return
         * 참고 URL :  https://develop-obm.tistory.com/28
         */
        var getTrunc = function (str, len, apos) {
            var res = "";

            if (apos === undefined || apos === null) {
                apos = "...";
            }

            len = parseInt(len, 10);

            if (str !== null) {
                if (str.length > len) {
                    res = str.substring(0, len);
                    if (apos !== "") {
                        res += apos;
                    }
                } else {
                    res = str;
                }
            }

            return res;
        };


        /**
         * UUID 생성
         * @param separ 구분자 [기본값:(없음)]
         * @param isUpperCase 대문자 변환여부 [기본값:true]
         * @returns
         * 참고 URL : https://romeoh.tistory.com/entry/javascript-UUID-%EC%83%9D%EC%84%B1%ED%95%98%EA%B8%B0
         *           https://noritersand.github.io/javascript/javascript-uuid-%EC%83%9D%EC%84%B1-%ED%95%A8%EC%88%98/
         */
        var getRandomUUID = function (separ, isUpperCase) {
            var s4 = function () {
                var array = new Uint32Array(10);
                // if (navigator.sayswho.startsWith("IE")) {
                //     window.msCrypto.getRandomValues(array);
                // } else {
                //     window.crypto.getRandomValues(array);
                // }
                crypto.getRandomValues(array);
                var a = array[1];
                var result = Math.floor((1 + a) * 0x10000).toString(16).substring(1);
                return result.substring(1, 5);
            };

            if (separ === undefined) {
                separ = "";
            }
            if (isUpperCase === undefined) {
                isUpperCase = true;
            }

            var res = s4() + s4() + separ + s4() + separ + s4() + separ + s4() + separ + s4() + s4() + s4();
            if (isUpperCase) {
                res = res.toUpperCase();
            }

            return res;
        };

        var getNumberOnly = function (val) {
            if (typeof val === "undefined" || val === null) {
                return "0";
            }
            // HACK 문자열로 변환
            val = val + "";
            return val.replace(/[^-0-9.]/g, "");
        };

        /**
         * 금액 콤마 표시
         * @param obj 입력상자 객체 또는 문자열
         * @param maxPoint 소수점 이하 자릿수 제한값 [-1:무제한, 0:허용안함(기본값)]
         * @returns {*|string}
         * 참고 URL :http://egloos.zum.com/fortunejth/v/2156384
         *          https://hianna.tistory.com/441
         *          https://lnsideout.tistory.com/entry/jQuery%EC%A0%9C%EC%9D%B4%EC%BF%BC%EB%A6%AC-%EC%88%AB%EC%9E%90%EC%B2%9C%EB%8B%A8%EC%9C%84%EC%BD%A4%EB%A7%88-%EC%B0%8D%EA%B8%B0-%EB%B0%8F-%EC%A0%9C%EA%B1%B0
         */
        var getFormatCurrency = function (obj, maxPoint) {
            var dp = maxPoint;
            if (typeof maxPoint === "undefined" || typeof maxPoint === "object" || maxPoint < 0) {
                dp = 0;
            }
            var ts = ",", ds = ".";

            if (paragonCmm.isEmpty(obj)) {
                return "";
            }

            try {

                var tmp = obj.toString();
                if (tmp.indexOf(ts) !== -1) {
                    tmp = tmp.replace(/\,/g, '');
                }
                var bnum = new Big(tmp);
                var arr = bnum.toFixed(dp).split('.');
                arr[0] = arr[0].replace(/\B(?=(\d{3})+(?!\d))/g, ts === null ? ',' : ts + '');

                return arr.join(ds === null ? '.' : ds + '');
            } catch (e) {
                console.error(e + ":[" + obj + "]");
                return "";
            }
        };

        return {
            getTrim: getTrim,
            getTrunc: getTrunc,
            getRandomUUID: getRandomUUID,
            getNumberOnly: getNumberOnly,
            getFormatCurrency: getFormatCurrency
        };
    })();

    p.getTrim = stringUtils.getTrim;
    p.getTrunc = stringUtils.getTrunc;
    p.getRandomUUID = stringUtils.getRandomUUID;
    p.getNumberOnly = stringUtils.getNumberOnly;
    p.getFormatCurrency = stringUtils.getFormatCurrency;
})(paragonCmm);

(function (p) {
    "use strict";

    var convertUtils = (function () {

        var toPascalCase = function (string) {
            var words = string.match(/[a-z]+/gi);
            if (!words) {
                return '';
            }
            return words
                .map(function (word) {
                    return word.charAt(0).toUpperCase() + word.substr(1).toLowerCase();
                })
                .join('');
        };

        /**
         * 대상 문자열을 폼값 출력용 문자열로 변환
         * @param str
         * @returns
         * 참고 URL :  https://pracon.tistory.com/154
         */
        var convertForInput = function (str) {
            if (str === undefined || str === null) {
                str = "";
            } else {
                str = "" + str;
            }

            str = str.replace(/\&/g, "&amp;");
            str = str.replace(/\#/g, "&#35;");
            str = str.replace(/\"/g, "&#34;");
            str = str.replace(/\'/g, "&#39;");
            str = str.replace(/\</g, "&#60;");
            str = str.replace(/\>/g, "&#62;");
            str = str.replace(/\\/g, "&#92;");

            return str;
        };

        /**
         * 대상 문자열을 뷰 출력용 문자열로 변환
         * @param str
         * @returns
         * 참고 URL :  https://pracon.tistory.com/154
         */
        var convertForView = function (str) {
            if (str === undefined || str === null) {
                str = "";
            } else {
                str = "" + str;
            }
            var replaceAll = function (str, searchStr, replaceStr) {
                return str.split(searchStr).join(replaceStr);
            }
            str = replaceAll(str, "\&", "&amp;");
            str = replaceAll(str, "\"", "&#34;");
            str = replaceAll(str, "\'", "&#39;");
            str = replaceAll(str, "<", "&#60;");
            str = replaceAll(str, ">", "&#62;");
//    str = replaceAll(str, "\\", "&#92;");
            str = replaceAll(str, "  ", "&#32;&#32;");
            str = replaceAll(str, "\t", "&#32;&#32;&#32;");
            str = replaceAll(str, "\r\n", "<br />");
            str = replaceAll(str, "\n", "<br />");

            return str;
        };

        var convertForJavascript = function (str) {
            if (str === undefined || str === null) {
                str = "";
            } else {
                str = "" + str;
            }

            str = str.replace(/\\/g, "\\\\");
            str = str.replace(/\"/g, "\\\"");
            str = str.replace(/\'/g, "\\'");
            str = str.replace(/\(/g, "\\(");
            str = str.replace(/\)/g, "\\)");
            str = str.replace(/\r/g, "\\r");
            str = str.replace(/\n/g, "\\n");
            str = str.replace(/\t/g, "\\t");
            str = str.replace(/\f/g, "\\f");

            //alert("convertForJavascript => "+ str);

            return str;
        };

        return {
            toPascalCase: toPascalCase,
            convertForInput: convertForInput,
            convertForView: convertForView,
            convertForJavascript: convertForJavascript
        };
    })();

    p.toPascalCase = convertUtils.toPascalCase;
    p.convertForInput = convertUtils.convertForInput;
    p.convertForView = convertUtils.convertForView;
    p.convertForJavascript = convertUtils.convertForJavascript;
})(paragonCmm);

// ajax
(function (p) {
    "use strict";

    var ajaxUtils = (function () {

        var FILE_SAVE_PROC_URL = paragonCmm.getUrl("/paragon/file/File/saveProc/json");
        var FILE_DELETE_URL = paragonCmm.getUrl("/paragon/file/File/beDelete/json");

        var showBackDrop = function () {
            $("#backdrop").remove();
            $("#backdrop-mask").remove();

            var pLeft = Math.ceil(window.innerWidth / 2) - 26;
            var pTop = window.scrollY + Math.ceil(window.innerHeight / 2) - 37;

            var t0 = '';
            t0 += '<div id="backdrop" style="display:block; left:' + pLeft + 'px; top:' + pTop + 'px; position:absolute; z-index:9002;">';
            t0 += '    <div class="fa-4x centered">';
            t0 += '        <i class="fa fa-spinner fa-spin"></i>';
            t0 += '    </div>';
            t0 += '</div>';
            t0 += '<div id="backdrop-mask" class="window-mask" style="display:block; z-index:9003; position:fixed;"></div>';

            $(t0).appendTo("body");
        };

        var hideBackDrop = function () {
            $("#backdrop").remove();
            $("#backdrop-mask").remove();
        };

        var callAjax = function (url, data, callback, async, errorBack) {

            async = (async === undefined || async === null) ? false : async;
            var callFnc = function () {
                $.ajax({
                    url: url,
                    async: async,
                    type: "POST",
                    data: JSON.stringify(data),
                    dataType: "json",
                    contentType: "application/json"
                })
                    .done(function (json) {
                        paragonCmm.hideBackDrop();
                        //-- 콜백함수 리턴
                        callback(json);
                    })
                    .fail(function (jqXHR, textStatus, errorThrown) {
                        console.log(jqXHR);
                        console.log(textStatus);
                        console.log(errorThrown);
                        var msg = "처리에 실패 하였습니다.";
                        msg += "\n관리자에게 문의 하세요.";
                        //        msg += "\nERROR CODE:"+errorThrown;
                        //        msg += "\nACTION CODE:"+airAction;
                        //        msg += "\nMODE CODE:"+airMode;
                        //        msg += "\nCALLBACK:"+callback;
                        //        msg += "\nDATA:"+data;

                        // easy ui 종속
                        $.messager.alert("Error", msg, "error", function () {
                            paragonCmm.hideBackDrop();
                            if (errorBack) {
                                errorBack();
                            }
                        });
                    });
            };


            // paragonCmm.showBackDrop();
            // setTimeout(function(){
            //     callFnc();
            // }, 0);
            callFnc();
        };

        var submitAjax = function (url, data, callback, async, errorBack) {
            async = (async === undefined || async === null) ? false : async;

            /**
             * (내부함수) 파일업로드 처리
             */
            var uploadFiles = function () {
                //-- 첨부할 파일이 있는지 체크
                if (!jQuery.isEmptyObject(paragonFile.arrFileInfo)) {

                    var ctrlUuids = $("input:hidden[name='_attachFileCtrlUuid']");
                    var sol_mas_uid = ($("input:hidden[name='solMasUid']").val() !== undefined) ? $("input:hidden[name='solMasUid']").val() : "";
                    var bool = true;

                    var fileLen = $(ctrlUuids).length;
                    var iCnt = 0;

                    //--파일 유형 갯수만큼 each
                    $(ctrlUuids).each(function (i, o) {
                        if (!bool) {
                            return false
                        }
                        ;
                        var fileUrl = FILE_SAVE_PROC_URL;
                        // fileUrl += "?fileTpCd="+ paragonFile.fileInit[$(o).val()].fileTpCd;
                        // fileUrl += "&relUid="+paragonFile.fileInit[$(o).val()].relUid;
                        // fileUrl += "&sol_mas_uid="+ sol_mas_uid;
                        // fileUrl += "&randomFileNameYn="+paragonFile.fileInit[$(o).val()].randomFileNameYn;
                        // fileUrl += "&overwriteYn="+paragonFile.fileInit[$(o).val()].overwriteYn;

                        var formData = new FormData();
                        var files = paragonFile.arrFileInfo[$(o).val()].files;
                        $(files).each(function (i, o) {
                            formData.append('file', o);
                        });

                        var fileList = [];
                        var lis = $("li", "div[class='file_Element_" + $(o).val() + "']");
                        var infos = paragonFile.arrFileInfo[$(o).val()].infos;
                        $(lis).each(function (i, li) {
                            var liID = $(li).attr("id");
                            $(infos).each(function (j, o) {
                                if (liID === o.atchUid) {
                                    fileList.push(o);
                                    return false;
                                }
                            });
                        });

                        // fileUrl += "&fileInfos="+encodeURIComponent(JSON.stringify(fileList));
                        formData.append('fileTpCd', paragonFile.fileInit[$(o).val()].fileTpCd);
                        formData.append('relUid', paragonFile.fileInit[$(o).val()].relUid);
                        formData.append('sol_mas_uid', sol_mas_uid);
                        formData.append('fileInfos', JSON.stringify(fileList));

                        (function (i) {
                            $.ajax({
                                url: fileUrl
                                , enctype: 'multipart/form-data'
                                , processData: false
                                , contentType: false
                                , async: false
                                , type: "POST"
                                , data: formData
                            }).done(function (json) {
                                iCnt += 1;

                                if (iCnt === fileLen) {
                                    // call ajax
                                    paragonCmm.callAjax(url, data, callback, async, function () {
                                        if (errorBack) {
                                            errorBack();
                                        }
                                    });
                                }

                            }).fail(function (jqXHR, textStatus, errorThrown) {
                                bool = false;
                                console.log(jqXHR);
                                console.log(textStatus);
                                console.log(errorThrown);
                                var msg = "첨부파일 업로드에 실패 하였습니다.";
                                msg += "\n관리자에게 문의 하세요.";
                                alert(msg, function () {
                                    paragonCmm.hideBackDrop();

                                    if (errorBack) {
                                        errorBack();
                                    }
                                });
                            });
                        })(i);
                    });
                } else {
                    // 첨부파일이 없을 경우 처리
                    paragonCmm.callAjax(url, data, callback, async, function () {
                        if (errorBack) {
                            errorBack();
                        }
                    });
                }
            };

            /**
             * (내부함수) 파일삭제 처리
             */
            var deleteFiles = function () {
                var fileUrl = FILE_DELETE_URL;
                var delData = {
                    delAtchs: paragonFile.delFileInfo.join(",")
                };
                $.ajax({
                    url: fileUrl,
                    async: false,
                    dataType: "json",
                    data: delData,
                    type: "POST"
                }).done(function (json) {
                    // 업로드 처리 호출
                    uploadFiles();
                }).fail(function (jqXHR, textStatus, errorThrown) {
                    var msg = "처리에 실패 하였습니다.";
                    msg += "\n관리자에게 문의 하세요.";
                    $.messager.alert("Error", msg, "error", function () {
                        paragonCmm.hideBackDrop();
                        if (errorBack) {
                            errorBack();
                        }
                    });
                });
            };

            paragonCmm.showBackDrop();
            setTimeout(function () {
                //-- 첨부는 없으면서 파일 삭제 꺼리가 있을 경우 삭제 처리후 후처리 진행.
                if (paragonFile.delFileInfo.length > 0) {
                    // 파일 삭제후 진행
                    deleteFiles();
                } else {
                    // 그냥 진행
                    uploadFiles();
                }
            }, 0);
        };

        return {
            showBackDrop: showBackDrop,
            hideBackDrop: hideBackDrop,
            callAjax: callAjax,
            submitAjax: submitAjax
        };
    })();
    p.showBackDrop = ajaxUtils.showBackDrop;
    p.hideBackDrop = ajaxUtils.hideBackDrop;
    p.callAjax = ajaxUtils.callAjax;
    p.submitAjax = ajaxUtils.submitAjax;
})(paragonCmm);


// 다국어
(function (p) {
    "use strict";

    var cmmLocalStorage = Storages.localStorage;
    var langMas = {}, noLangMas = [];

    var mLang = (function () {

        var MLANG_INIT_URL = paragonCmm.getUrl('/viself/mlangSvc/MLangInit/json');
        var DELETE_NO_LANG_URL = paragonCmm.getUrl("/viself/mlangSvc/deleteNoLang/json");
        var NOT_YET_INSERT_URL = paragonCmm.getUrl("/viself/mlangSvc/notYetInsert/json");
        var NOT_YET_SELECT_INSERT_URL = paragonCmm.getUrl("/viself/mlangSvc/notYetSelectInsert/json");


        var getLangVersion = function () {
            return cmmLocalStorage.get("LANG_VERSION");
        };

        /**
         * lnagMas 값 초기화
         */
        var initLangMas = function () {
            var langVersion = getLangVersion();
            if (cmmLocalStorage.get(langVersion) !== null && cmmLocalStorage.get(langVersion) !== "") {
                langMas = cmmLocalStorage.get(langVersion);
            }
        };

        /**
         * siteLocal 반환
         */
        var getSiteLocale = function () {
            return cmmLocalStorage.get("SITE_LOCALE");
        };

        var setSiteLocale = function (siteLocale) {
            cmmLocalStorage.set("SITE_LOCALE", siteLocale);
        };

        /**
         * lnagMas 값 초기화
         */
        var initLangStorage = function (langVerSion) {

            //-- 기존 저장 스토리지 삭제
            var oldVersion = getLangVersion();
            cmmLocalStorage.remove(oldVersion);
            var siteLocale = getSiteLocale().toLowerCase();
            var data = {
                NO_PAGE: "Y",
                siteLocale: siteLocale
            };
            data["NO_PAGE"] = "Y";
            this.callAjax(MLANG_INIT_URL, data, function (json) {
                langMas = {};
                $(json.data).each(function (i, d) {
//            var lang = {};
//            lang["KO"] = d.ko;
//            $.each(d, function(key, val){
//                lang[key.toUpperCase()] = val;
//            });
//            console.log(lang);
                    langMas[d.langCd] = d[siteLocale];
//            langMas[d.langCd] = lang ;
                });
                cmmLocalStorage.set(langVerSion, langMas);
                cmmLocalStorage.set("LANG_VERSION", langVerSion);
            }, false);
        };


        /**
         * 미등록 다국어 추가
         */
        var setNoLang = function (langCd) {

            if (langCd.startsWith("L.")
                || langCd.startsWith("B.")
                || langCd.startsWith("M.")
                || langCd.startsWith("C.")
            ) {
                //-- 기존 저장 스토리지 삭제
                noLangMas.push(langCd);
            }

        };

        /**
         * 로컬 스토리지에서 다국어 가져오기
         * @param langCd  다국어코드
         * @param label      라벨
         */
        var getLang = function (langCd, label) {
//    console.log("langCd:"+langCd);
//    console.log("label:"+label);
            var rtnLang = "";

            if (langCd === undefined || langCd === null) {
                return "";
            }

            if (langMas === null) {

            } else if (jQuery.isEmptyObject(langMas)) {
                initLangMas();
            }

            var arrLangCd = []; //-- 다중 다국어 일경우 각각 구하기
            var separ = ""; //-- 다중 다국어 일경우 구분자
            if (langCd) {
                if (langCd.indexOf("≫") > -1) {
                    arrLangCd = langCd.split("≫");
                    separ = "≫";
                } else if (langCd.indexOf(",") > -1) {
                    arrLangCd = langCd.split(",");
                    separ = ",";
                }
            }

            if (arrLangCd.length > 0) {
                var tmpArrStr = [];
                $(arrLangCd).each(function (i, v) {
                    if ("" !== v) {
                        var tempStr = "";
                        if (langMas.hasOwnProperty(v)) {
//                    tempStr =  langMas[v][paragonCmm.getSiteLocale()];
                            tempStr = langMas[v];
                            tempStr = (tempStr === "") ? "NG:" + v : tempStr; //-- 등록되있으나 번역되지 않음
                        } else {
                            if (langCd.startsWith("L.")
                                || langCd.startsWith("B.")
                                || langCd.startsWith("M.")
                                || langCd.startsWith("C.")
                            ) {
                                tempStr = v;
                                setNoLang(v);

                            } else {
                                tempStr = v; //-- 넘어온값이 다국어 코드가 아닐때
                            }
                        }
                        tmpArrStr.push(tempStr);
                    }
                });
                rtnLang = tmpArrStr.join(separ);

                //-- 다중 다국어가 아닐때
            } else {

                var getLabel = function (label) {//-- 라벨이 있을경우
                    var rtnLabel = "";
                    if (label !== undefined && typeof label === 'string') {
//                console.log(label);
                        if (langMas.hasOwnProperty(label)) {
//                    rtnLabel = langMas[label][paragonCmm.getSiteLocale()];
                            rtnLabel = langMas[label];
                        } else {
                            rtnLabel = label;
                        }
                    }

                    return rtnLabel;
                }

                if (langMas.hasOwnProperty(langCd)) {
//            rtnLang = langMas[langCd][paragonCmm.getSiteLocale()];
                    rtnLang = langMas[langCd];
                    if (label !== undefined) {
                        rtnLang = (getLabel(label) === "") ? rtnLang : rtnLang.replaceAll("[LABEL]", getLabel(label));
                        if ("KO" === getSiteLocale() && typeof label === 'string') {    //-- 한국어 일때 라벨 종성에 따른 뒷 문자열 변환
                            //--종성에 따른 은/는 이/가 을/를 구분 반환
                            var getKorWordByJongSung = function (word, fistVal, secondVal, defaultStr) {
                                var lastWord = word.charCodeAt(word.length - 1, word.length);

                                //-- 한글 제일 처음과 끝의 법위밖일 경우는 오류
                                if (lastWord < 0xAC00 || lastWord > 0xD7A3) {
                                    return defaultStr;
                                }

                                var selectVal = (lastWord - 0xAC00) % 28 > 0 ? fistVal : secondVal;

                                return selectVal;
                            }
                            rtnLang = rtnLang.replaceAll("을(를)", getKorWordByJongSung(label, "을", "를", "을(를)"));
                            rtnLang = rtnLang.replaceAll("이(가)", getKorWordByJongSung(label, "이", "가", "이(가)"));
                            rtnLang = rtnLang.replaceAll("은(는)", getKorWordByJongSung(label, "은", "는", "은(는)"));
                        }

                    }
                    rtnLang = (rtnLang === "") ? "NG:" + langCd : rtnLang; //-- 등록되있으나 번역되지 않음
                } else {
                    if (langCd && (langCd.startsWith("L.")
                        || langCd.startsWith("B.")
                        || langCd.startsWith("M.")
                        || langCd.startsWith("C."))
                    ) {
                        rtnLang = langCd;                            //-- 사전등록되어 있지 않음
                        setNoLang(langCd);
                    } else {
                        rtnLang = langCd;                            //-- 다국어 형식이 아닐때
                    }
                }
            }

            return rtnLang.replace(/\\n/g, "<br>");


        };

        var updateLangMas = function () {
            if ($(noLangMas).length > 0) {
                var final_data = [];
                $.each(noLangMas, function (i, value) {
                    if (final_data.indexOf(value) === -1) final_data.push(value);
                });

                paragonCmm.callAjax(DELETE_NO_LANG_URL, {}, function () {
                });
                $(final_data).each(function (i, d) {
                    var data = {};
                    data.noLang = d;
                    data.uuid = paragonCmm.getRandomUUID();
                    paragonCmm.callAjax(NOT_YET_INSERT_URL, data, function () {
                    });
                });

                paragonCmm.callAjax(NOT_YET_SELECT_INSERT_URL, {}, function () {
                });

//        console.log(final_data);
            }
        };

        /**
         * 다국어 변환 작업
         *   - parentObj : 부모 오브젝트(해당 영역만 영역만 변경이 필요 할때.
         */
        var convertLang = function (parentObj) {
            parentObj = ((parentObj === undefined || parentObj === "") ? "" : parentObj);

            var target;
            if (parentObj !== "") {
                target = $("[data-term]", $(parentObj));
            } else {
                target = $("[data-term]");
            }

            target.each(function (i, e) {
                var $span = $("<span class='langSpan'>");

                if ($(e).prop('tagName') === "IMG") {
                    $(e).attr("title", getLang($(e).data("term")));
                } else if ($(e).prop('tagName') === "I") {
                    $(e).attr("title", getLang($(e).data("term")));
                } else if ($(e).prop('tagName') === "LABEL") {
                    $(e).attr("title", getLang($(e).data("term")));
                    $(e).find(".langSpan").remove();
                    $(e).append($span.append(getLang($(e).data("term"))));
                } else if ($(e).prop('tagName') === "A") {
                    $(e).html("");
                    $(e).append(getLang($(e).data("term")));
                } else if ($(e).prop('tagName') === "DIV") {
                    $(e).attr("title", getLang($(e).data("term")));
                } else if ($(e).prop('tagName') === "INPUT") {
                    if ($(e).attr('type') === "button") {
                        $(e).val(getLang($(e).data("term")));
                    } else if ($(e).attr('type') === "text") {
                        if ($(e).attr('placeholder') !== undefined && $(e).attr('placeholder') !== "") {
                            $(e).attr('placeholder', getLang($(e).data("term")));
                        } else {
                            $(e).val(getLang($(e).data("term")));
                        }
                    }
                } else {
                    $(e).find(".langSpan").remove();
                    $(e).append($span.append(getLang($(e).data("term"))));
                }
            });

//    this.updateLangMas();
        };

        return {
            getLangVersion: getLangVersion,
            setSiteLocale: setSiteLocale,
            initLangMas: initLangMas,
            getSiteLocale: getSiteLocale,
            initLangStorage: initLangStorage,
            setNoLang: setNoLang,
            //updateLangMas: updateLangMas,
            getLang: getLang,
            convertLang: convertLang
        };
    })();

    p.getLangVersion = mLang.getLangVersion;
    p.setSiteLocale = mLang.setSiteLocale;
    p.initLangMas = mLang.initLangMas;
    p.getSiteLocale = mLang.getSiteLocale;
    p.initLangStorage = mLang.initLangStorage;
    p.setNoLang = mLang.setNoLang;
    p.getLang = mLang.getLang;
    p.convertLang = mLang.convertLang;
//p.updateLangMas = mLang.updateLangMas;

})(paragonCmm);

// Form
(function (p) {
    "use strict";

    var formUtils = (function () {

        /**
         * 첨부파일 - 유효성 체크 (필수 첨부)
         */
        var validateAttachFile = function (fileTpCd) {

            if ($("input:hidden[value='" + fileTpCd + "']").length > 0) {
                var fileCnt = paragonFile.arrFileInfo[$("input:hidden[value='" + fileTpCd + "']").parent().find("input:hidden[name='_attachFileCtrlUuid']").val()].infos.length;
                if (fileCnt === undefined || fileCnt === 0) {
                    //alert("첨부파일을 등록해주세요.");
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        };

        /**
         * 날짜 입력값 시작일 종료일 체크
         * @param startDte
         * @param endDte
         */
        var valiDateFromTo = function (startDte, endDte) {
            var res = false;

            if ("" !== startDte && "" !== endDte) {
                if (startDte > endDte) {
                    res = true;
                    return res;
                }
            }
            return res;
        };

        /**
         * 날짜 입력값 유효성 체크 (※ 형식에 맞지 않으면 null 값 리턴)
         * @param dateValue
         * @param dateSepar
         * 참고 URL :  https://hoony-devblog.tistory.com/15
         */
        var validateDateValue = function (dateValue, dateSepar) {
            var res = null;

            if (dateValue === "") {
                res = "";
            } else {
                if (dateSepar === undefined) dateSepar = "-";

                //dateValue = dateValue.replace("/"+ dateSepar +"/gi", "");
                dateValue = dateValue.replaceAll(dateSepar, "");

                if (dateValue.length === 8 && !isNaN(dateValue)) {
                    var yyyy = dateValue.substr(0, 4);
                    var mm = dateValue.substr(4, 2);
                    var dd = dateValue.substr(6, 2);

                    if (parseInt(yyyy, 10) <= 1900 || parseInt(mm) <= 12 || parseInt(dd) <= 31) {
                        res = yyyy + dateSepar + mm + dateSepar + dd;
                    }
                }
            }

            return res;
        };

        /**
         * 날짜 입력 객체 유효성 체크
         * @param dateObj
         * @param dateSepar
         */
        var validateDateObject = function (dateObj, dateSepar) {
            var res = validateDateValue(dateObj.value, dateSepar);
            if (res === null) {
                alert("날짜를 형식에 맞게 다시 입력해주세요.");
                dateObj.value = "";
                dateObj.focus();
            } else {
                dateObj.value = res;
            }
        };

        /**
         * 입력 가능한 최대 문자열 길이 체크 (※ 대상 컨트롤의 onblur 이벤트에 사용하세요~!)
         * @param input 대상 컨트롤 객체
         * @param len 최대값(단위:바이트)
         * 참고 URL :https://deersoul6662.tistory.com/41
         *          https://rocabilly.tistory.com/11
         *          http://egloos.zum.com/fortunejth/v/2156384
         */
        var validateMaxLength = function (input, len) {
            var val = input.value;

            if (val !== "") {
                var byteLength = 0;
                for (var idx = 0; idx < val.length; idx++) {
                    var oneChar = escape(val.charAt(idx));
                    if (oneChar.length === 1) {
                        byteLength++;
                    } else if (oneChar.indexOf("%u") !== -1) {
                        byteLength += 3; //유니코드 문자는 3byte
                    } else if (oneChar.indexOf("%") !== -1) {
                        byteLength += 2; //기타 특수문자는 2byte
                    }

                    if (byteLength > parseInt(len)) {
                        alert("입력 가능한 최대 문자열 길이(" + len + " 바이트)를 초과했습니다.\n초과한 문자는 잘립니다.");
                        input.value = val.substring(0, idx);

                        p.displayByte($(input).attr("id"));

                        return;
                    }
                }
            }
        };

        /**
         * 숫자 여부 체크
         * @param obj
         * @param str
         * @returns {Boolean}
         * 참고 URL : https://gocoder.tistory.com/2341
         *           https://fabric004.tistory.com/20
         */
        var validateNumber = function (obj, str) {
            var regStr = new RegExp("^[0-9]*$");
            if (regStr.test(str)) {
                return true;
            } else {
                alert("숫자만 입력 가능합니다.");
                obj.value = "";
                return false;
            }
        };

        /**
         * 숫자/콤마만 허용
         * @param obj
         * @param str
         * @returns {Boolean}
         * 참고 URL : https://gocoder.tistory.com/2341
         *           https://fabric004.tistory.com/20
         *           https://gaemi606.tistory.com/entry/JS-%EC%9E%90%EC%A3%BC%EC%93%B0%EB%8A%94-%EC%A0%95%EA%B7%9C%EC%8B%9D-%EC%BD%A4%EB%A7%88%EC%B0%8D%EA%B8%B0-%EC%88%AB%EC%9E%90%EB%A7%8C-%EC%9E%85%EB%A0%A5%EB%B0%9B%EA%B8%B0-%EB%93%B1-%ED%95%A8%EC%88%98
         */
        var validateCommaNumber = function (obj, str) {
            var regStr = new RegExp("^[0-9,]*$");
            if (regStr.test(str)) {
                return true;
            } else {
                alert("'숫자' 와 ',' 만 입력 가능합니다.");
                obj.value = "";
                return false;
            }
        };

        /**
         * 숫자/소수점만 허용
         * @param obj
         * @param str
         * @returns {Boolean}
         * 참고 URL : https://gocoder.tistory.com/2341
         *           https://fabric004.tistory.com/20
         *           https://gaemi606.tistory.com/entry/JS-%EC%9E%90%EC%A3%BC%EC%93%B0%EB%8A%94-%EC%A0%95%EA%B7%9C%EC%8B%9D-%EC%BD%A4%EB%A7%88%EC%B0%8D%EA%B8%B0-%EC%88%AB%EC%9E%90%EB%A7%8C-%EC%9E%85%EB%A0%A5%EB%B0%9B%EA%B8%B0-%EB%93%B1-%ED%95%A8%EC%88%98
         */
        var validatePointNumber = function (obj, str) {
            if (str === null || str === "") {
                return;
            }
            var regStr = new RegExp("^[0-9.]*$");
            if (regStr.test(str)) {
                return true;
            } else {
                alert("'숫자' 와 '.' 만 입력 가능합니다.");
                obj.value = "";
                return false;
            }

        };

        /**
         * 특수문자 입력여부 체크
         * @param obj
         * @returns {Boolean}
         * 참고 URL : https://simsimhaningyeo.tistory.com/178
         *           https://blog.naver.com/dev_dog/222200212544
         */
        var validateSpecialChars = function (obj) {
            var str = "";
            if (typeof (obj) === "object") {
                str = obj.value;
            } else {
                str = obj;
            }

            if (str.indexOf("\\") > -1 || str.indexOf("^") > -1 || str.indexOf("|") > -1 || str.indexOf("≪") > -1 || str.indexOf("≫") > -1) {
                alert("특수문자(\\^|≪≫)는 사용할 수 없습니다.");
                str = str.replaceAll("\\", "");
                str = str.replaceAll("^", "");
                str = str.replaceAll("|", "");
                str = str.replaceAll("≪", "");
                str = str.replaceAll("≫", "");
                if (typeof (obj) === "object") {
                    obj.focus();
                }

                return false;
            } else {
                return true;
            }
        };


        var getToday = function () {
            var today = new Date();
            var month = today.getMonth() + 1;
            var strToday = today.getFullYear();
            strToday += "-" + ((month < 10) ? "0" + month : month);
            var date = today.getDate();
            strToday += "-" + ((date < 10) ? "0" + date : date);
            return strToday;
        };
        var string = function (len) {
            var s = '', i = 0;
            while (i++ < len) {
                s += "0";
            }
            return s;
        };
        var zfStr = function (len, numStr) {
            return string(len - numStr.length) + numStr;
        };

        var zf = function (len, num) {
            return zfStr(len, num.toString());
        };
        var getDateFormat = function (f) {
            var d = new Date();

            if (!d.valueOf()) {
                return " ";
            }

            var weekKorName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];

            var weekKorShortName = ["일", "월", "화", "수", "목", "금", "토"];

            var weekEngName = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];

            var weekEngShortName = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

            return f.replace(/(yyyy|yy|MM|dd|KS|KL|ES|EL|HH|hh|mm|ss|a\/p)/gi, function ($1) {

                var h;

                switch ($1) {

                    case "yyyy":
                        return d.getFullYear(); // 년 (4자리)

                    case "yy":
                        return zf(2, d.getFullYear() % 1000); // 년 (2자리)

                    case "MM":
                        return zf(2, d.getMonth() + 1); // 월 (2자리)

                    case "dd":
                        return zf(2, d.getDate()); // 일 (2자리)

                    case "KS":
                        return weekKorShortName[d.getDay()]; // 요일 (짧은 한글)

                    case "KL":
                        return weekKorName[d.getDay()]; // 요일 (긴 한글)

                    case "ES":
                        return weekEngShortName[d.getDay()]; // 요일 (짧은 영어)

                    case "EL":
                        return weekEngName[d.getDay()]; // 요일 (긴 영어)

                    case "HH":
                        return zf(2, d.getHours()); // 시간 (24시간 기준, 2자리)

                    case "hh":
                        return zf(2, ((h = d.getHours() % 12) ? h : 12)); // 시간 (12시간 기준, 2자리)

                    case "mm":
                        return zf(2, d.getMinutes()); // 분 (2자리)

                    case "ss":
                        return zf(2, d.getSeconds()); // 초 (2자리)

                    case "a/p":
                        return d.getHours() < 12 ? "오전" : "오후"; // 오전/오후 구분

                    default:
                        return $1;

                }

            });
        };

        /**
         * 검색 쿼리 파라메터 반환
         */
        var getSearchQueryParams = function (frm) {
            var arrObj = null;
            if (typeof (frm) !== 'undefined') {
                arrObj = $("[data-type='search']", $(frm));
            } else {
                arrObj = $("[data-type='search']", "form[name='form1']");
            }
            // console.log(arrObj);
            var jsondata = {"schFlag": "Y"};
            arrObj.each(function () {
                var tagNm = $(this).get(0).tagName;
                if ("SELECT" === tagNm) {
                    var oNm = $(this).eq(0).attr("name");
                    var oVal = $("select[name=" + oNm + "] option:selected").val();
                    if (oNm === undefined) {
                        oNm = $(this).eq(0).attr("id");
                    }

                    oVal = (function (obj) {
                            var arr = [];
                            $(obj).find("option:selected").each(function (i, e) {
                                arr.push($(this).val());
                            });
                            return arr.join(",");
                        }
                    )($(this))


                    var jObj = "{\"" + oNm + "\" : \"" + oVal + "\" }";
                    $.extend(jsondata, jQuery.parseJSON(jObj));
                } else {
                    var oTyp = $(this).eq(0).attr("type");
                    var oNm = $(this).eq(0).attr("name");
                    var oCls = $(this).eq(0).attr("class");
                    var obj;
                    var oVal = "";
                    if (oTyp === "checkbox") {

                        if (typeof (frm) !== 'undefined') {
                            obj = $("input:checkbox[name='" + oNm + "']:checked", $(frm));
                        } else {
                            obj = $("input:checkbox[name='" + oNm + "']:checked", "form[name='form1']");
                        }

                    } else if (oTyp === "radio") {

                        if (typeof (frm) !== 'undefined') {
                            obj = $("input:radio[name='" + oNm + "']:checked", $(frm));
                        } else {
                            obj = $("input:radio[name='" + oNm + "']:checked", "form[name='form1']");
                        }

                    } else {

                        if (typeof (frm) !== 'undefined') {
                            obj = $("input[name='" + oNm + "']", $(frm));
                        } else {
                            obj = $("input[name='" + oNm + "']", "form[name='form1']");
                        }

                    }
                    if (obj.length > 1) {
                        var imsi = [];
                        $(obj).each(function (i, e) {
                            imsi.push($(e).val());
                        });
                        oVal = "{\"" + oNm + "\" : " + JSON.stringify(imsi) + " }";
                    } else {
                        if ("cost" === oCls) {

                            oVal = "{\"" + oNm + "\" : \"" + obj.val().replaceAll(",", "") + "\" }";
                        } else {
                            oVal = "{\"" + oNm + "\" : \"" + obj.val() + "\" }";

                        }
                    }
                    $.extend(jsondata, jQuery.parseJSON(oVal));
                }
            });

            return jsondata;
        }

        return {
            getSearchQueryParams: getSearchQueryParams,
            validateAttachFile: validateAttachFile,
            validateMaxLength: validateMaxLength,
            validateSpecialChars: validateSpecialChars,
            validateNumber: validateNumber,
            validateCommaNumber: validateCommaNumber,
            validatePointNumber: validatePointNumber,
            validateDateValue: validateDateValue,
            validateDateObject: validateDateObject,
            valiDateFromTo: valiDateFromTo,
            getDateFormat: getDateFormat,
            getToday: getToday
        };
    })();
    p.getSearchQueryParams = formUtils.getSearchQueryParams;
    p.validateAttachFile = formUtils.validateAttachFile;
    p.validateMaxLength = formUtils.validateMaxLength;
    p.validateSpecialChars = formUtils.validateSpecialChars;
    p.validateNumber = formUtils.validateNumber;
    p.validateCommaNumber = formUtils.validateCommaNumber;
    p.validatePointNumber = formUtils.validatePointNumber;
    p.validateDateValue = formUtils.validateDateValue;
    p.validateDateObject = formUtils.validateDateObject;
    p.valiDateFromTo = formUtils.valiDateFromTo;
    p.getDateFormat = formUtils.getDateFormat;
    p.getToday = formUtils.getToday;


})(paragonCmm);

// ParagonHtml
(function (p) {
    "use strict";

    var htmlUtils = (function () {
        /**
         *  드레그 앤 드랍
         *  tBodyId 테이블의 <tbody> 테그의 아이디
         *     - 테이블 아이디를 적으면 <thead> 부분까지 드레그앤 드롭 됨.(안되!!)
         *   //참고 사이트  = https://isocra.com/2008/02/table-drag-and-drop-jquery-plugin
         */
        var tableTrDragSet = function (tBodyId, dragstopFunc) {
            //여기부터 TDnD시작
            var $tbody = "";
            if (typeof tBodyId === "string") {
                $tbody = $("#" + tBodyId);
            } else if (typeof tBodyId === "object") {
                $tbody = tBodyId;
            } else {
                return false;
            }
            // Make a nice striped effect on the table
//     $("#tb tr:even').addClass('alt')"); 동적으로 만든 부분이라 에러가 나와서 동적 부분에 class 추가.
            $tbody.tableDnD({
                onDragClass: "myDragClass",
                onDragStop: function (table, row) {
                    if (typeof dragstopFunc === "function") {
                        dragstopFunc();
                    }
                },
                onDragStart: function (table, row) {
                    $('#debugArea').html("Started dragging row " + row.id);
                }
            });
        };

        return {
            tableTrDragSet: tableTrDragSet
        }
    })();

    p.tableTrDragSet = htmlUtils.tableTrDragSet;

})(paragonCmm);

// Opener Utils(Opener Callback)
(function (p) {
    "use strict";

    var cbFnc = new Map();

    var openerUtils = (function () {

        var openWindow = function (url, width, height, name, scrollbars, resizable, etc) {
            var left = 0;
            var top = 0;

            if (url === undefined || url === "") {
                url = "about:blank";
            }

            if (String(width) === "100%") {
                width = window.screen.availWidth - 20;
            } else {
                left = (window.screen.availWidth / 2) - (width / 2);
            }

            if (String(height) === "100%") {
                height = window.screen.availHeight;
            } else {
                top = (window.screen.availHeight / 2) - (height / 2);
            }

            if (name === undefined) {
                name = "";
            }

            if (scrollbars === "yes" || scrollbars === "1") {
                scrollbars = "yes";
            } else {
                scrollbars = "no";
            }

            if (resizable === "yes" || resizable === "1") {
                resizable = "yes";
            } else {
                resizable = "no";
            }

            if (etc === undefined) {
                etc = "";
            } else {
                etc = "," + etc;
            }

            var a = window.open(encodeURI(url), name, "left=" + left + ",top=" + top + ",width=" + width + ",height=" + height + ",scrollbars=" + scrollbars + ",resizable=" + resizable + ",toolbar=no,location=no,directories=no,status=no,menubar=no" + etc);

            $(a).focus();

            return a;
        };

        /**
         * 팝업 콜백함수 지정
         */
        var setCbFnc = function (key, fnc) {

            var tmpKey = key + "";
            var keyPrefix = tmpKey.substring(0, tmpKey.indexOf("-"));

            if (p.isNotEmpty(keyPrefix)) {
                // opener 가 변경되어 유사 key 로 callback 이 등록 될 경우 js 오류 발생
                // 이전 등록된 동일 opener 의 불필요한 callback 삭제
                cbFnc.forEach(function (itm, k) {
                    var tmp = k + "";
                    if (tmp.indexOf(keyPrefix) !== -1) {
                        cbFnc.delete(k);
                    }
                });
            }
            cbFnc.set(key, fnc);
        };

        /**
         * 팝업 콜백함수 가져오기
         */
        var getCbFnc = function (key, param) {
            var fnc = cbFnc.get(key);
            return fnc;
        };

        /**
         * 팝업 콜백함수 호출
         */
        var runCbFnc = function (key, param) {
            var fnc = cbFnc.get(key);
            if (typeof fnc === "function") {
                fnc(param);
            }
        };

        /**
         * 팝업용 callback 함수를 위한 key 생성
         * - opener 가 변경되어 유사 key 로 callback 이 등록 될 경우 js 오류 발생
         * @param type key prefix
         * @returns {string} prefix_uuid 형태의 key
         */
        var makeTypeKey = function (type) {
            return type + '-' + p.getRandomUUID();
        };

        return {
            setCbFnc: setCbFnc,
            getCbFnc: getCbFnc,
            runCbFnc: runCbFnc,
            makeTypeKey: makeTypeKey,
            openWindow: openWindow
        };
    })();

    p.setCbFnc = openerUtils.setCbFnc;
    p.getCbFnc = openerUtils.getCbFnc;
    p.runCbFnc = openerUtils.runCbFnc;
    p.makeTypeKey = openerUtils.makeTypeKey;
    p.openWindow = openerUtils.openWindow;
})(paragonCmm);

/**
 * X-Free editor 용 배열
 * @type {*[]}
 */
var XfreeEditor = XfreeEditor || {};

//Editor
(function (p) {
    "use strict";

    var editorUtils = (function () {

        /**
         * 기본에디터 타입 얻기
         * @returns {string}
         */
        var getDefaultEditorType = function () {
            return p.DEFAULT_EDITOR_TYPE;
        };

        /**
         * 에디터에 입력한 값 가져오기
         * @param id 에디터 컨트롤 아이디
         * @param isTextOnly 텍스트만 가져오기 여부 [true:텍스트만 반환, false:HTML 텍스트 반환(기본값)]
         * @param productName 제품명 [FCKEDITOR:FCKeditor(기본값)]
         * @returns {String}
         */
        var setEditorValue = function (id, editorVal, editorType) {
            var res = "";
            editorType = ((editorType === undefined || editorType === "") ? p.DEFAULT_EDITOR_TYPE : editorType);
            if (editorType === "CKEDITOR") {
                CKEDITOR.instances[id].setData(editorVal);

            } else if (editorType === "DEXT5") {


            } else if (editorType === "NAMO") {


            } else if (editorType === "X_FREE") {
                var keys = Object.keys(XfreeEditor);
                keys.some(function (key, idx) {
                    var itm = XfreeEditor[key];
                    if (itm.getRenderId() === id) {
                        itm.setBodyValue(editorVal);
                    }
                });
            }

            return res;
        };

        /**
         * 에디터에 입력한 값 가져오기
         * @param id 에디터 컨트롤 아이디
         * @param isTextOnly 텍스트만 가져오기 여부 [true:텍스트만 반환, false:HTML 텍스트 반환(기본값)]
         * @param productName 제품명 [FCKEDITOR:FCKeditor(기본값)]
         * @returns {String}
         */
        var getEditorValue = function (id, editorType, isTextOnly) {
            var res = "";

            editorType = ((editorType === undefined || editorType === "") ? p.DEFAULT_EDITOR_TYPE : editorType);

            if (editorType === "CKEDITOR") {
                res = new Function("return CKEDITOR.instances." + id + ".getData()")();

            } else if (editorType === "DEXT5") {

                res = DEXT5.isEmpty(id)

            } else if (editorType === "NAMO") {

                res = new Function("return CrossEditor" + id + ".GetTextValue()")();

            } else if (editorType === "X_FREE") {

                res = XfreeEditor[id].getTextValue().trim();
            } else {
                res = document.getElementById(id).value;
            }

            return res;
        };

        /**
         * 에디터에 따른 포커스 처리
         * @param id 에디터 컨트롤 아이디
         * @param isTextOnly 텍스트만 가져오기 여부 [true:텍스트만 반환, false:HTML 텍스트 반환(기본값)]
         * @param productName 제품명 [FCKEDITOR:FCKeditor(기본값)]
         * @returns {String}
         */
        var getEditorFocus = function (id, editorType) {
            var res = "";

            editorType = ((editorType === undefined || editorType === "") ? p.DEFAULT_EDITOR_TYPE : editorType);

            if (editorType === "CKEDITOR") {
                CKEDITOR.instances[id].focus();

            } else if (editorType === "DEXT5") {
                $('#' + id).focus();

            } else if (editorType === "NAMO") {
                res = new Function("return CrossEditor" + id + ".SetFocusEditor()")(); // 크로스에디터 Focus 이동

            } else if (editorType === "X_FREE") {
                // res = new Function("return XfreeEditor" + id + ".setFocus()")();

                res = XfreeEditor[id].setFocus();

            } else {
                res = document.getElementById(id).fucus();
            }

            return res;
        };

        /**
         * 에디터에 따른 서브밋 전처리
         * @param id 에디터 컨트롤 아이디
         * @param isTextOnly 텍스트만 가져오기 여부 [true:텍스트만 반환, false:HTML 텍스트 반환(기본값)]
         * @param productName 제품명 [FCKEDITOR:FCKeditor(기본값)]
         * @returns {String}
         */
        var setEditorSubmit = function (editorType) {
            var res = "";

            editorType = ((editorType === undefined || editorType === "") ? p.DEFAULT_EDITOR_TYPE : editorType);
            switch (editorType) {
                case "DEXT5":
                    $(DextEditor).each(function (i, el) {
                        $("textarea[name='" + el.editorName + "']").eq(0).val(DEXT5.getBodyValue(el));
                    });
                    break;
                case "NAMO":
                    /*
                     * 나모웹 에디터 처리 Submit 처리
                     *   나모에디터를 사용하지 않아도 Exception 발생하지 않으므로 코드 지우기 있기? 없기?
                     */
                    $(CrossEditor).each(function (i, el) {
                        $("textarea[name='" + el.editorName + "']").eq(0).val(el.GetBodyValue());
                    });
                    break;
                case "X_FREE":
                    /*
                     *   X free 에디터  Submit 처리
                     *   X free 사용하지 않아도 Exception 발생하지 않으므로 코드 지우기 있기? 없기?
                     */
                    var keys = Object.keys(XfreeEditor);
                    keys.forEach(function (key, idx) {
                        var el = XfreeEditor[key];
                        $("textarea[name='" + el.getRenderId() + "']").eq(0).val(el.getBodyValue());
                    });
                    break;
                default:
                    // console.log(Object.keys(CKEDITOR.instances));
                    var keys = Object.keys(CKEDITOR.instances);
                    keys.forEach(function (key, idx) {
                        CKEDITOR.instances[key].updateElement();
                    });
                    /* 2023-02-15 Ksj 아래 for in 문 안먹어서 임시로 key foreach로 변경
                    for (instance in CKEDITOR.instances) {
                        CKEDITOR.instances[instance].updateElement();
                    }// end of for
                    */
                    break;
            }
        };

        return {
            getDefaultEditorType: getDefaultEditorType,
            getEditorFocus: getEditorFocus,
            getEditorValue: getEditorValue,
            setEditorValue: setEditorValue,
            setEditorSubmit: setEditorSubmit
        };
    })();

    p.getDefaultEditorType = editorUtils.getDefaultEditorType;
    p.getEditorFocus = editorUtils.getEditorFocus;
    p.getEditorValue = editorUtils.getEditorValue;
    p.setEditorValue = editorUtils.setEditorValue;
    p.setEditorSubmit = editorUtils.setEditorSubmit;
})(paragonCmm);

// EasyUI JQuery
(function (p) {
    "use strict";

    var easyUiUtils = (function () {

        /**
         * easyui  그리드 데이터 Filter
         */
        var easyuiLoadFilter = function (json) {
            var data = {};

            if (json.hasOwnProperty("data") && json.data.length > 0) {
                data["total"] = json.data[0].totCnt;
                data["rows"] = json.data;
                // if(typeof json.data[0].totCnt === "undefined" && Array.isArray(json.data)){
                //     data["total"] = json.data.length;
                // }
            } else if (json.hasOwnProperty("data") && json.data.length === 0) {
                data["total"] = 0;
                data["rows"] = [];
            } else if (json.hasOwnProperty("data") && json.data.hasOwnProperty("list")) {
                data["total"] = json.data.totalCount.totCnt;
                data["rows"] = json.data.list;
            } else {
                data = json;
            }

            return data;
        };

        /**
         * easyui  콤보박스등 데이터 Filter
         * data.id = 아이디 컬럼명
         * data.txt = Label 컬럼명
         * data.initVal = 초기셋팅 값
         * data.initTxt = 초기셋팅 Label
         * return {{"id":"1","text":"첫번쨰"},{"id":"2","text":"두번쨰"}}
         */
        var easyuiLoadFilterForData = function (json) {
            var data = [];
            var id = (json.hasOwnProperty("id")) ? json.id : "";
            var txt = (json.hasOwnProperty("txt")) ? json.txt : "";
            var initVal = (json.hasOwnProperty("initVal")) ? json.initVal : "";
            var initTxt = (json.hasOwnProperty("initTxt")) ? json.initTxt : "";
            var selectVal = (json.hasOwnProperty("selectVal")) ? json.selectVal : "";

            if (json.hasOwnProperty("data") && json.data.length > 0) {
                if (initTxt !== null && initTxt !== "") {
                    data.push({"id": initVal, "text": initTxt});
                }
                $(json.data).each(function (i, v) {
                    var rtn = {};

                    rtn["id"] = v[id];
                    rtn["text"] = paragonCmm.getLang(v[txt]);
                    if (selectVal !== "" && selectVal === v[id]) {
                        rtn["selected"] = "true";
                    }
                    data.push(rtn);
                });
            } else if (json.hasOwnProperty("data") && json.data.length === 0) {
                if (initTxt !== null && initTxt !== "") {
                    data.push({"id": initVal, "text": initTxt});
                }
            } else {
                data = json;
            }
            return data;
        };

        return {
            easyuiLoadFilter: easyuiLoadFilter,
            easyuiLoadFilterForData: easyuiLoadFilterForData
        };
    })();

    p.easyuiLoadFilter = easyUiUtils.easyuiLoadFilter;
    p.easyuiLoadFilterForData = easyUiUtils.easyuiLoadFilterForData;

})(paragonCmm);

// tree
var treeProperties = {};
(function (p) {
    "use strict";


    var ICON_DOWN = paragonCmm.getUrl("/img/ico/icon-down.png");
    var ICON_RIGHT = paragonCmm.getUrl("/img/ico/icon-right.png");

    var jTree = (function () {

        var treeInitSet = function (url, mode, depth, selectType) {
            var arrDepth = [];
            if (typeof (depth) !== "undefined") {
                arrDepth = JSON.parse(depth);
            }
            treeProperties.URL = url;
            treeProperties.Tmpl = "addTreeTmpl_" + mode;
            treeProperties.defaultDEPTH = arrDepth;
            treeProperties.nowDEPTH = 0;
            treeProperties.mode = mode;
            treeProperties.selectType = selectType;

            if (mode === "SKILL") {
                treeProperties.defaultLev = 4;
            } else if (mode === "DEPT") {
                treeProperties.defaultLev = 0;
            } else {
                treeProperties.defaultLev = 0;
            }

            console.debug(treeProperties);
        };

        var treeAppend = function (id, data) {
            $("#" + id).append($("#" + treeProperties.Tmpl).tmpl(data.data));
        }
        var treeDrawChild = function (id, rootCodeId, jsonData, cbFnc) {
            var json = {};
            if (jsonData) {
                json = jsonData;
            }
            if (rootCodeId && rootCodeId !== "") {
                json["parentCd"] = rootCodeId;
            } else {
                json["parentCd"] = "";
            }
            p.callAjax(treeProperties.URL, json, function (data) {
                if (typeof cbFnc === "function") cbFnc();
                treeAppend(id, data);
                if (treeProperties.defaultDEPTH.length > 0 && treeProperties.defaultDEPTH.length > treeProperties.nowDEPTH) {
                    nodeOpenClick(treeProperties.defaultDEPTH[treeProperties.nowDEPTH]);
                    treeProperties.nowDEPTH = treeProperties.nowDEPTH + 1;
                } else if (treeProperties.defaultDEPTH.length > 0 && treeProperties.defaultDEPTH.length === treeProperties.nowDEPTH) {
                    var deptCd = treeProperties.defaultDEPTH[treeProperties.defaultDEPTH.length - 1];
                    $("#treeArea").scrollTop(0);
                    var offtop = ($("#" + id).position() !== undefined) ? $("#" + id).position().top : 0;
                    $("#treeArea").scrollTop(offtop - 150);
                }

            }, true, function () {
                alert("트리를 그리는 도중 에러가 발생 하였습니다.\n관리자에게 문의 하세요.\n" + errorThrown);
            });
//    $.ajax({
//        url: treeProperties.URL
//        , type: "POST"
//        , dataType: "json"
//    	, async : true
//        , data: json
//
//    })
//    .done(function(data) {
//        treeAppend(id,data);
//        if(treeProperties.defaultDEPTH.length > 0 &&  treeProperties.defaultDEPTH.length >  treeProperties.nowDEPTH){
//            nodeOpenClick(treeProperties.defaultDEPTH[treeProperties.nowDEPTH]);
//            treeProperties.nowDEPTH = treeProperties.nowDEPTH+1;
//        }else if(treeProperties.defaultDEPTH.length > 0 &&  treeProperties.defaultDEPTH.length ===  treeProperties.nowDEPTH){
//            var deptCd = treeProperties.defaultDEPTH[treeProperties.defaultDEPTH.length-1];
//            $("#treeArea").scrollTop(0);
//            var offtop = ($("#"+id).position() !== undefined)? $("#"+id).position().top:0;
//            $("#treeArea").scrollTop(offtop-150);
//        }
//    })
//    .fail(function(jqXHR, textStatus, errorThrown) {
//        alert("트리를 그리는 도중 에러가 발생 하였습니다.\n관리자에게 문의 하세요.\n"+errorThrown);
//    });
        };

        var setPadding = function (lev) {
            var left = (lev - treeProperties.defaultLev);
            if (left > 0) {
                return 15;
            } else {
                return 0;
            }
        };
        var nodeOpenClick = function (id) {
            id = (id === undefined || id === null ? "" : id);
            if (id !== "") {
                var childNode = $("#" + id).children("div [data-id='div_" + id + "']");
                if (typeof ($("#" + id).children("div [data-id='div_" + id + "']").css("display")) === 'undefined') {
                    $("#img_" + id).attr("src", ICON_DOWN);
                    paragonCmm.treeDrawChild(id, id, treeProperties.searchPram);
                } else if ($("#" + id).children("div [data-id='div_" + id + "']").css("display") === "block") {
                    $("#img_" + id).attr("src", ICON_RIGHT);
                    childNode.hide();
                } else {
                    $("#img_" + id).attr("src", ICON_DOWN);
                    childNode.show();
                }
            }
        };

        return {
            treeInitSet: treeInitSet,
            treeDrawChild: treeDrawChild,
            treeAppend: treeAppend,
            setPadding: setPadding,
            nodeOpenClick: nodeOpenClick
        };
    })();
    p.treeInitSet = jTree.treeInitSet;
    p.treeDrawChild = jTree.treeDrawChild;
    p.treeAppend = jTree.treeAppend;
    p.setPadding = jTree.setPadding;
    p.nodeOpenClick = jTree.nodeOpenClick;
})(paragonCmm);

$(function () {
    "use strict";
    // i18n 처리
    paragonCmm.convertLang();
});