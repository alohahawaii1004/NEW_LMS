/*
 * @(#)jquery.js     2020-02-21 오후 2:04
 *
 * Copyright 2020 JAYU.space
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

// import JQuery
//import JaYu from "../Config";

/**
 * @deprecated paragonCmm.getTrim 으로 사용
 * @param val
 * @returns {string}
 */
var trim = function(val) {
    if (val==null || val=='undefined') return '';
    if (typeof val == 'number') {
        val = val + '';
    }
    if (typeof val == 'string') {
        val = val.replace(/(^\n*)/g,"");
        val = val.replace(/(^\r*)/g,"");
        val = val.replace(/(^\s*)|(\s*$)/g,"");
    }
    return val;
};
/**
 * jquery attr 셀렉터의 IE8 오동작 보완을 위한 필터
 * 특정 속성을 갖고 있는 객체 필터
 * @deprecated 기본 jquery 사용
 */
$.fn.hasAttr = function(attrNm, exValue) {
    var selector = '[' + attrNm + '][' + attrNm + '!=""]';
    if (trim(exValue) !== '') selector += '[' + attrNm + '!="' + exValue + '"]';
    return $(this).filter(selector);
}

/**
 * @deprecated serializeObject 사용
 * @param options
 * @returns {{}}
 */
//폼 데이터를 JSON 형태로
$.fn.serializeJSON = function(options) {
    var arrayData;
    var objectData = {};
    var tagName = $(this).prop('tagName');
    var opts = $.extend({
        filter:null,
        isList:false
    }, options||{});

    // 초기화
    $(this).find('[data-serialized][data-serialized!=""]').removeAttr('data-serialized');

    // list 형태에 대해 먼저 정리
    var incList = false;
    //if (opts.isList) {
    var tmpListData = {};
    $(this).find('table').hasAttr('data-list-key').each(function() {
        var key = $(this).attr('data-list-key');
        var listData = [];
        $(this).find('tbody > tr').each(function() {
            var rowData = $(this).serializeObject(opts);
            listData.push(rowData);
        });
        tmpListData[key] = listData;
        objectData[key] = listData;
    });
    //}
    if (Object.keys(tmpListData)>0) {
        incList = true;
    }

    var $obj = $(this).find('input,select,textarea').filter('[id][id!=""],[name][name!=""]').filter('[data-serialized!="Y"]');
    if (opts.filter) $obj = $obj.filter(opts.filter);
    $obj.each(function() {
        var nm = trim($(this).attr('name'));
        var id = trim($(this).attr('id'));
        var val = trim($(this).val());
        if (val === '' && $(this).prop('type') === 'textarea') val = trim($(this).text());
        var ty = $(this).attr('type');
        var chk = true;
        if (nm == '') nm = id;
        if (ty == 'radio' || ty == 'checkbox') {
            chk = $(this).prop('checked');
        }

        if (nm!='' && chk) {
            var chkRe = /[a-zA-Z0-9_]+\[[0-9]*\]\./g;
            nm = nm.replaceAll(chkRe, '');

            if (objectData[nm] != null) {
                if (!objectData[nm].push)
                    objectData[nm] = [objectData[nm]];
                objectData[nm].push(val);
                // 테이블이나 list 형태일 경우 리스트 데이터 확인
                if (incList) {
                    //var $table = $('table').has(this).hasAttr('data-list-key').last();
                }
            } else {
                objectData[nm] = val;
            }
        }
        // 확인한놈에 대해 표시
        $(this).attr('data-serialized','Y');
    });
    //}
    return objectData;
};

//폼 데이터를 JSON 형태로
$.fn.tableDataToJson = function(id) {

    var tagName = $(this).prop('tagName');
    var tbody, tr;
    if("TABLE" == tagName){
        tbody = $(this).find("TBODY");
    }else if("TR" == tagName){
        tbody = $(this).parent("TBODY");
    }else if("TD" == tagName){
        tbody = $(this).parent("TBODY");
    }else if("TBODY" == tagName){
        tbody = $(this);
    }
    tr =  $(tbody).children("TR");
    var arrTr = [];
    $(tr).each(function(i, o){
        arrTr.push($(o).serializeJSON())
    });
    return arrTr;
}

/**
 * form 초기화 (hidden 포함)
 * https://gracefullight.dev/2016/12/26/form-reset-%EC%9D%98-input-hidden-%EC%B4%88%EA%B8%B0%ED%99%94-%EB%AC%B8%EC%A0%9C/
 * @returns {*}
 */
$.fn.clearForm = function () {
    return this.each(function () {
        var type = this.type,
            tag = this.tagName.toLowerCase();
        if (tag === 'form') {
            return $(':input', this).clearForm();
        }
        if (
            type === 'text' ||
            type === 'password' ||
            type === 'hidden' ||
            tag === 'textarea'
        ) {
            this.value = '';
        } else if (type === 'checkbox' || type === 'radio') {
            this.checked = false;
        } else if (tag === 'select') {
            // this.selectedIndex = -1;
            this.selectedIndex = 0;
        }
    });
};

/**
 * JQuery CSRF Token 처리
 */
(function($){
    // ajax CSRF Token 처리
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var customHeader = "JaYu";
    var customAjaxValue = "YeoYu";

    $(document).ajaxSend(function (e, xhr, options) {

        if(header && header !== ""){
            xhr.setRequestHeader(header, token);
        }
        xhr.setRequestHeader(customHeader, customAjaxValue);
    });

    // ajax session/CSRF missing 처리
    $(document).ajaxSuccess(function(e, xhr, options, data){
        // var principal = $("meta[name='_principal_header']").attr("content");

        // console.info(xhr.responseText);
        var resTxt = xhr.responseText;
        if(resTxt.indexOf("ACCESS_DENY") !== -1 || resTxt.indexOf("MISSING_YN") !== -1){
            var chk = JSON.parse(xhr.responseText);
            if(chk["ACCESS_DENY"] || chk["MISSING_YN"]){

                console.info("chk.ACCESS_DENY..."+chk["ACCESS_DENY"]);
                console.info("chk.MISSING_YN..."+chk["MISSING_YN"]);
                console.info("chk.FORBIDDEN..."+chk["FORBIDDEN"]);
                // console.info("principal...["+principal+"]");
                console.info(typeof principal);

                // if(principal !== ""){
                //     //console.log("principal is not empty!!");
                //     // alert("Your session has expired.\n Please log in again");
                // }
                if(chk["FORBIDDEN"] === "Y"){
                    console.info("access deny or missing token");
                    // alert("access deny or missing token");
                    // $.messager.alert("Warning","접근이 거부되었거나<br>토큰이 만료되었습니다.","warning");
                    alert("접근이 거부되었거나\n토큰이 만료되었습니다.");
                }else{
                    console.info("Your session has expired. Please log in again");
                    // alert("Your session has expired.\n Please log in again");
                    // $.messager.alert("Warning","세션이 종료 되었습니다.<br> 다시 로그인 하시기 바랍니다.","warning");
                    alert("세션이 종료 되었습니다.\n다시 로그인 하시기 바랍니다.");
                }

                // 접근금지, 세션종료, CSRF Token Missing
                setTimeout(function(){
                    location.reload();
                }, 1000);
                return false;
            }
        }
    });
})(jQuery);

/* // 구 표현식
$(function () {

    // ajax CSRF Token 처리
    // if(jayu.useCsrfTokenFlag){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var customHeader = "JaYu";
    var customAjaxValue = "YeoYu";

    $(document).ajaxSend(function (e, xhr, options) {

        if(header && header !== ""){
            xhr.setRequestHeader(header, token);
        }
        xhr.setRequestHeader(customHeader, customAjaxValue);
    });

    // }

    // ajax session/CSRF missing 처리
    $(document).ajaxSuccess(function(e, xhr, options, data){
        // var principal = $("meta[name='_principal_header']").attr("content");

        // console.info(xhr.responseText);
        var resTxt = xhr.responseText;
        if(resTxt.indexOf("ACCESS_DENY") !== -1 || resTxt.indexOf("MISSING_YN") !== -1){
            var chk = JSON.parse(xhr.responseText);
            if(chk.ACCESS_DENY || chk.MISSING_YN){

                console.info("chk.ACCESS_DENY..."+chk.ACCESS_DENY);
                console.info("chk.MISSING_YN..."+chk.MISSING_YN);
                console.info("chk.FORBIDDEN..."+chk.FORBIDDEN);
                // console.info("principal...["+principal+"]");
                console.info(typeof principal);

                // if(principal !== ""){
                //     //console.log("principal is not empty!!");
                //     // alert("Your session has expired.\n Please log in again");
                // }
                if(chk.FORBIDDEN === "Y"){
                    console.info("access deny or missing token");
                    // alert("access deny or missing token");
                    $.messager.alert("Warning","접근이 거부되었거나 토큰이 만료되었습니다.","warning");
                }else{
                    console.info("Your session has expired. Please log in again");
                    // alert("Your session has expired.\n Please log in again");
                    $.messager.alert("Warning","세션이 종료 되었습니다.<br> 다시 로그인 하시기 바랍니다.","warning");
                    // location.reload();
                }

                // 접근금지, 세션종료, CSRF Token Missing
                location.reload();
                return false;
            }
        }
    });
}());
*/

/**
 * jquery serialize 를 json 형태로 반환
 * 출처: https://cofs.tistory.com/184 [CofS]
 * @returns {*}
 */
jQuery.fn.serializeObject = function () {
    // var obj = null;
    // try {
    //     if (this[0].tagName && this[0].tagName.toUpperCase() === "FORM") {
    //         var arr = this.serializeArray();
    //         if (arr) {
    //             obj = {};
    //             jQuery.each(arr, function () {
    //                 obj[this.name] = this.value;
    //             });
    //         }//if ( arr ) {
    //     }
    // } catch (e) {
    //     alert(e.message);
    // } finally {
    // }
    //
    // return obj;

    var result = {};
    var extend = function (i, element) {
        var node = result[element.name];
        if ('undefined' !== typeof node && node !== null) {
            if ($.isArray(node)) {
                node.push(element.value);
            } else {
                result[element.name] = [node, element.value];
            }
        } else {
            result[element.name] = element.value;
        }
    };

    $.each(this.serializeArray(), extend);
    return result;
};