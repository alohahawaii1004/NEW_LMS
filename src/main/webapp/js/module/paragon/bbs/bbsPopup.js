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

var BbsPopup = function (bbsUid) {
    "use strict";

    var model = (function(){
        var BBS_GET_DATA_URL = paragonCmm.getUrl("/paragon/bbs/BbsMas/getData/json");

        var getData = function (options){
            var opts = $.extend({
                url: BBS_GET_DATA_URL,
                param: null,
                cbFnc: function(json){}
            }, options);

            paragonCmm.callAjax(opts.url, opts.param, opts.cbFnc);
        };

        return{
            getData: getData
        };
    })();

    var $rootNode = $("#bbsView_"+bbsUid);
    var $form = $("#bbsViewForm_"+bbsUid);
    var openerData = $form.data("opener-data");
    var opts = $.extend({
        bbsUid: bbsUid
    },openerData);
    var $modal = $("#" + opts.modalId);
    var cbClose = $modal.data("callback-close");

//오늘 더이상 보지않기!
//     function closeWin(seq) {
//         paragonCmm.setCookie(seq, "ok", 1);
//         self.close();
//     }

    var closeForm = function(data){
        if(paragonCmm.isNotEmpty(opener)) {
            var cbKey = opts.cbKey;
            var openerCbFnc;
            if(paragonCmm.isNotEmpty(cbKey) && opener.paragonCmm){
                openerCbFnc = opener.paragonCmm.getCbFnc(cbKey);
            }
            if(typeof openerCbFnc === "function"){
                openerCbFnc(data);
            }else{
                self.close();
            }
        }else{
            if(typeof cbClose === "function"){
                cbClose();
            }
        }
    };

    var setContent = function(data){
        var keys = Object.keys(data);
        keys.forEach(function (key) {
            switch (key) {
                case "strRegDte":
                case "regDspNm":
                case "bbsCtgryNm":
                case "bbsNm":
                    $("[data-col='"+key+"_"+bbsUid+"']", $rootNode).html(paragonCmm.getLang(filterXSS(data[key])));
                    break;
                case "bbsContent":
                    $("[data-col='"+key+"_"+bbsUid+"']", $rootNode).html(data[key]);
                    break;
                default:
                    $("[data-col='"+key+"_"+bbsUid+"']", $rootNode).html(filterXSS(data[key]));
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
        htmlUtils.loadFileView("bbsFile_"+bbsUid, {
            relUid: opts.bbsUid,
            fileTpCd: "LMS/BBS",
            defaultRelUid: opts.bbsUid,
            requiredYn: ""
        });
    };

    var setBtnEvent = function(){
        $rootNode.off("click", "button");
        $rootNode.on("click", "button", function(){
            var btnId = $(this).attr("id");
            switch (btnId){
                case "btnClose_"+bbsUid:
                    closeForm();
                    break;
                default:
                    console.warn("not support btnId:"+btnId);
                    break;
            }

            return false; // 이벤트 확산금지
        });
    };

    var initForm = function(){};
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
        paragonCmm.convertLang($rootNode);
    }
    return {
        init: init
    }
};
