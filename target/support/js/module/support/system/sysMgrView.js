/*
 * @(#)sysMgrView.js     2023-03-29 오전 8:04
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

    let SysMgrViewModel = function(){

        const GET_DATA_URL = paragonCmm.getUrl("/support/system/sysMgrMng/loadData/json");
        const UPDATE_URL = paragonCmm.getUrl("/support/system/sysMgrMng/update/json");
        const DELETE_URL = paragonCmm.getUrl("/support/system/sysMgrMng/delete/json");

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
                callAjax(options, GET_DATA_URL);
            },
            updateData: function(options){
                callAjax(options, UPDATE_URL);
            },
            deleteData: function (options){
                callAjax(options, DELETE_URL);
            }
        };
    };

    let SysMgrView = function(){

        // ==============================================
        // attributes
        // ----------------------------------------------
        let model = new SysMgrViewModel();
        let $rootNode = $("#sysMgrViewRootLayer");
        let openerData = $.extend({},$rootNode.data("opener-data"));

        // ==============================================
        // private functions
        // ----------------------------------------------
        let closeForm = function (data) {
            let $modal = $("#" + openerData.modalId);
            let cbFncClose = $modal.data("callback");
            if (typeof cbFncClose === "function") {
                // 모달일 경우 닫기
                cbFncClose(data);
            } else {
                // 팝업일 경우 닫기
                self.close();
            }
        };

        let setData = function(data){
            let keys = Object.keys(data);
            let badgeColor = "#a94442";
            let badgeBgColor = "#f2dede";
            let cssClass = "bg-danger";
            let badgeText = "주";
            keys.forEach(function (key) {
                switch (key) {
                    case "priority":
                        if(data[key] === "2"){
                            badgeColor = "#3c763d";
                            badgeBgColor = "#dff0d8";
                            badgeText = "부";
                            cssClass = "bg-orange";
                        }
                        $("[data-col='priorityNm']", $rootNode).html(badgeText);
                        $("[data-col='priorityNm']", $rootNode).addClass("color", badgeColor);
                        // $("[data-col='priorityNm']", $rootNode).css("background-color", badgeBgColor);
                        break;
                    case "mailSndYn":
                        badgeColor = "#a94442";
                        badgeBgColor = "#f2dede";
                        badgeText = "차단";
                        if(data[key] === "Y"){                            badgeColor = "#3c763d";
                            badgeBgColor = "#dff0d8";
                            badgeText = "발송";
                        }
                        // $("[data-col='"+key+"']", $rootNode).html(badgeText);
                        $("[data-col='"+key+"']", $rootNode).css("color", badgeColor);
                        $("[data-col='"+key+"']", $rootNode).css("background-color", badgeBgColor);
                        break;
                    case "useYn":
                        badgeColor = "#a94442";
                        badgeBgColor = "#f2dede";
                        badgeText = "퇴사";
                        if(data[key] === "Y"){                            badgeColor = "#3c763d";
                            badgeBgColor = "#dff0d8";
                            badgeText = "정상";
                        }
                        $("[data-col='"+key+"']", $rootNode).html(badgeText);
                        $("[data-col='"+key+"']", $rootNode).css("color", badgeColor);
                        $("[data-col='"+key+"']", $rootNode).css("background-color", badgeBgColor);
                        break;
                    default:
                        $("[data-col='" + key + "']", $rootNode).html(filterXSS(data[key]));
                        break;
                }
            });
        };

        let deleteData = function(){
            $.messager.confirm("Confirm",'정말 삭제하시겠습니까?',function(r){
                if (r){
                    model.deleteData({
                        params: {
                            loginId: openerData.loginId,
                            systemId: openerData.systemId
                        },
                        cbFnc: function(json){
                            if (json.errYn === "E") {
                                //-- 오류처리
                                $.messager.alert("Error", json.msg,"error");
                                return false;
                            } else  {
                                if(typeof json.data === "undefined"){
                                    json.data = {};
                                }
                                closeForm(json.data);
                            }
                        }
                    });
                }
            });
        };

        let toggleData = function(col){
            model.updateData({
                params: {
                    loginId: openerData.loginId,
                    systemId: openerData.systemId,
                    target: col
                },
                cbFnc: function(json){
                    if (json.errYn === "E") {
                        //-- 오류처리
                        $.messager.alert("Error", json.msg,"error");
                        return false;
                    } else  {

                        console.debug(col);

                        if(col === "priorityNm"){

                            if(typeof json.data === "undefined"){
                                json.data = {};
                            }

                            closeForm(json.data);
                        }else{
                            init();
                        }
                    }
                }
            })
        };

        let loadData = function(){
            model.loadData({
                params: {
                    systemId: openerData.systemId,
                    loginId: openerData.loginId
                },
                cbFnc: function(json){
                    if (json.errYn === "E") {
                        alert(json.msg);//-- 오류처리
                        return false;
                    }

                    if(paragonCmm.isNotEmpty(json.data)){
                        setData(json.data);
                    }
                }
            });
        };

        let setEvent = function(){
            $rootNode.off("click", "button");
            $rootNode.on("click", "button", function(){
                let btnId = $(this).attr("id");
                switch (btnId){
                    case "sysMgrViewDeleteBtn":
                        deleteData();
                        break;
                    case "sysMgrViewCloseBtn":
                        closeForm();
                        break;
                    default:
                        console.warn("not support btnId:"+btnId);
                        return;
                }

                return false; // 이벤트 확산금지
            });

            $rootNode.off("click", ".btn");
            $rootNode.on("click", ".btn", function(){
                let col = $(this).data("col");
                switch (col){
                    case "priorityNm":
                    case "mailSndYn":
                        toggleData(col);
                        break;
                    default:
                        console.warn("not support col:"+col);
                        return;
                }
                return false;
            });
        };

        // ==============================================
        // public functions
        // ----------------------------------------------
        let init = function(){
            loadData();
            setEvent();
        };
        return {
            init: init
        };
    };

    let sysMgrView = new SysMgrView();
    sysMgrView.init();
});