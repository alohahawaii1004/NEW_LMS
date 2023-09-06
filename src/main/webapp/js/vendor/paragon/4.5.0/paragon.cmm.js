/*
 * @(#)paragon.cmm.js
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

/**
 * 시스템 공용 자바스크립트 도우미 객체
 * @constructor
 */
function ParagonCmm() {
    "use strict";
}

ParagonCmm.prototype = {
};

var paragonCmm = new ParagonCmm();

(function(p){
    "use strict";

    var INFO = {
        name: "Paragon",
        version: "4.5.0",
        license: "Apache-2.0"
    };
    var properties = Object.assign({
        // context Path 가 정의되면 아래 contextPath 값을 변경해준다.
        contextPath: "",
        // 기본 에디터 타입(default: CKEDITOR)이 변경되면 아래 defaultEditorType 값을 변경해준다.
        defaultEditorType: "CKEDITOR"
    });

    var info = (function(){
        var getInfo = function (attr) {
            if (INFO.hasOwnProperty(attr)) {
                return INFO[attr];
            }
            return null;
        };

        var getAttr = function (attr) {
            if (properties.hasOwnProperty(attr)) {
                return properties[attr];
            }
            return null;
        };

        var setAttr = function(attr, val){
            if (properties.hasOwnProperty(attr)) {
                properties[attr] = val;
            }
        };

        return {
            NAME: getInfo("name"),
            VERSION: getInfo("version"),
            LICENSE: getInfo("license"),
            CONTEXT_PATH: getAttr("contextPath"),
            DEFAULT_EDITOR_TYPE: getAttr("defaultEditorType"),
            setAttr: setAttr,
            getAttr: getAttr
        }
    })();

    p.formMap = new Map();
    p.NAME = info.NAME;
    p.VERSION = info.VERSION;
    p.LICENSE = info.LICENSE;
    p.CONTEXT_PATH = info.CONTEXT_PATH;
    p.DEFAULT_EDITOR_TYPE = info.DEFAULT_EDITOR_TYPE;
    p.setAttr = info.setAttr;
    p.getAttr = info.getAttr;
    p.setConfig = function(){
        p.CONTEXT_PATH = p.getAttr("contextPath");
        p.DEFAULT_EDITOR_TYPE = p.getAttr("defaultEditorType");
    };

})(paragonCmm);

(function (p) {
    "use strict";

    var utils = (function () {

        /**
         * context path 가 포함된 url 반환
         * @param srcUrl 변경되기 전 url
         * @returns {*} context path 가 포함된 url
         */
        var getUrl = function (srcUrl) {
            return p.CONTEXT_PATH + srcUrl;
        };

        /**
         *
         * @param obj
         * @returns {boolean}
         */
        var isEmpty = function (obj) {
            try {
                if (obj === null || typeof obj === "undefined" || obj === "") {
                    return true;
                }
            } catch (e) {
                return true;
            }
            return false;
        };

        /**
         *
         * @param obj
         * @returns {boolean}
         */
        var isNotEmpty = function (obj) {
            return !isEmpty(obj);
        };

        /**
         *
         * @returns {boolean}
         */
        var isMobile = function () {
            var windowWidth = window.matchMedia("screen and (max-width: 768px)");
            // console.debug(windowWidth.matches);
            return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) || (windowWidth.matches);
        };

        /**
         *
         * @param name
         * @param value
         * @param expiredays
         */
        var setCookie = function (name, value, expiredays) {
            var todayDate = new Date();
            todayDate.setDate(todayDate.getDate() + expiredays);
            document.cookie = name + "=" + encodeURIComponent(value) + "; path=/; expires=" + todayDate.toGMTString() + ";";

        };

        /**
         *
         * @param name
         * @returns {string}
         */
        var getCookie = function (name) {
            var nameOfCookie = name + '=';
            var x = 0;
            while (x <= document.cookie.length) {
                var y = (x + nameOfCookie.length);
                if (document.cookie.substring(x, y) === nameOfCookie) {
                    var endOfCookie = document.cookie.indexOf(';', y);
                    if (endOfCookie === -1) {
                        endOfCookie = document.cookie.length;
                    }
                    return unescape(document.cookie.substring(y, endOfCookie));
                }
                x = document.cookie.indexOf(' ', x) + 1;
                if (x === 0) break;
            }// end of while

            return '';
        };

        /**
         *
         * @param val
         * @returns {number}
         */
        var chkLength = function (val) {
            var byteLength = 0;
            for (var idx = 0; idx < val.length; idx++) {
                var oneChar = encodeURIComponent(val.charAt(idx));
                if (oneChar.length === 1) {
                    byteLength++;
                } else if (oneChar.indexOf("%u") !== -1) {
                    byteLength += 3; //유니코드 문자는 3byte
                } else if (oneChar.indexOf("%") !== -1) {
                    byteLength += 2; //기타 특수문자는 2byte
                }
            }
            return byteLength;
        };

        /**
         *
         * @param objId
         */
        var displayByte = function (objId) {
            if ($("#" + objId)) {
                var len = this.chkLength($("#" + objId).val());
                $("#byte_" + objId).text(len);
            }
        };

        return {
            isEmpty: isEmpty,
            isNotEmpty: isNotEmpty,
            isMobile: isMobile,
            getUrl: getUrl,
            setCookie: setCookie,
            getCookie: getCookie,
            chkLength: chkLength,
            displayByte: displayByte
        };
    })();

    p.isEmpty = utils.isEmpty;
    p.isNotEmpty = utils.isNotEmpty;
    p.isMobile = utils.isMobile;
    p.getUrl = utils.getUrl;
    p.setCookie = utils.setCookie;
    p.getCookie = utils.getCookie;
    p.chkLength = utils.chkLength;
    p.displayByte = utils.displayByte;
})(paragonCmm);