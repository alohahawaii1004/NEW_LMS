/*
 * @(#)userView.js     2023-03-28 오전 7:59
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
$(function () {
    "use strict";
    let UserViewModel = function () {

        const LOAD_USER_INFO_URL = paragonCmm.getUrl("/support/user/userMng/loadData/json");
        const WRITE_FORM_URL = paragonCmm.getUrl("/support/user/userModify.popup");
        const PWD_CHANGE_URL = paragonCmm.getUrl("/cmm/modal/updatePwd.include");

        let getOpts = function (options, url) {
            return $.extend({
                url: url,
                params: null,
                cbFnc: function (json) {
                },
                async: true,
                errorBack: function () {
                }
            }, options);
        };
        let callAjax = function (options, url) {
            let opts = getOpts(options, url);
            paragonCmm.callAjax(opts.url, opts.params, opts.cbFnc, opts.async);
        };

        let submitAjax = function (options, url) {
            let opts = getOpts(options, url);
            paragonCmm.submitAjax(opts.url, opts.params, opts.cbFnc, opts.async, opts.errorBack);
        };

        let loadUserInfo = function (options) {
            callAjax(options, LOAD_USER_INFO_URL);
        };

        return {
            getUserWriteUrl: function () {
                return WRITE_FORM_URL;
            },
            getChPwdUrl: function(){
                return PWD_CHANGE_URL;
            },
            loadUserInfo: loadUserInfo
        };
    };

    let UserView = function () {
        // attributes ###################################
        let model = new UserViewModel();
        let $rootNode = $("#userMngViewRootLayer");
        let $userMngViewForm = $("#userMngViewForm", $rootNode);
        let $userRoles = $("#userRoles", $rootNode);

        let openerData = $.extend({
            loginId: null,
            cbKey: null,
            isMgr: false
        }, $rootNode.data("opener-data"));

        let closeForm = function () {
            if (typeof (opener) !== "undefined") {
                self.close();
            }
        };

        let submitForm = function (options) {
            let opts = $.extend({
                title: null,
                param: null,
                url: null
            }, options);

            $("input[name=popupTitle]", $userMngViewForm).val(opts.title);
            $("input[name=openerData]", $userMngViewForm).val(JSON.stringify(opts.param));
            $userMngViewForm.attr("action", opts.url);
            $userMngViewForm.submit();
        };


        let setUserAuths = function (data) {
            let auths = data.split(",");

            console.debug(auths);

            let strHtml = "";
            auths.forEach(function (itm) {
                if (paragonCmm.isNotEmpty(itm)) {
                    strHtml += '<span class="badge badge-light m-1" style="background-color:#B8D7FF; color: #003A9D;"><i class="fa-solid fa-fingerprint"></i> ' + filterXSS(itm) + '</span>';
                }

            });
            $userRoles.html("");
            $userRoles.html(strHtml);


        };

        let userInfo = (function (loginId) {
            let setData = function (data) {
                let keys = Object.keys(data);

                keys.forEach(function (key) {
                    switch (key) {
                        case "memo":
                            // paragonCmm.setEditorValue("memoEditor", data[key]);
                            $("[data-col='" + key + "']", $rootNode).html(data[key]);
                            break;
                        case "useYn":
                            let badgeColor = "#a94442";
                            let badgeBgColor = "#f2dede";
                            let badgeText = "사용하지 않음";
                            if (data[key] === "Y") {
                                badgeColor = "#3c763d";
                                badgeBgColor = "#dff0d8";
                                badgeText = "사용";
                            }
                            $("[data-col='" + key + "']", $rootNode).html(badgeText);
                            $("[data-col='" + key + "']", $rootNode).css("color", badgeColor);
                            $("[data-col='" + key + "']", $rootNode).css("background-color", badgeBgColor);
                            break;
                        case "userAuths":
                            setUserAuths(data[key]);
                            break;
                        default:
                            $("[data-col='" + key + "']", $rootNode).html(filterXSS(data[key]));
                            break;
                    }
                });

                if (paragonCmm.isNotEmpty(data.email) && ((data.userAuthCds.indexOf("SM_MNG") != -1 ||
                        data.userAuthCds.indexOf("SM_ADMIN") != -1 ||
                        data.userAuthCds.indexOf("CMM_SYS") != -1))) {
                    $("#userMngViewChangePwBtn").show();
                }
            };

            let loadUserInfo = function () {
                model.loadUserInfo({
                    params: {
                        loginId: openerData.loginId
                    },
                    cbFnc: function (json) {
                        if (json.errYn === "E") {
                            //-- 오류처리
                            $.messager.alert("Error", json.msg, "error");
                            return false;
                        } else {
                            setData(json.data);
                        }
                    }
                });
            };

            let openModify = function () {
                submitForm({
                    title: "사용자정보",
                    param: {
                        loginId: openerData.loginId
                    },
                    url: model.getUserWriteUrl()
                });
            };

            let changePwd = function () {

                let options = {
                    title: "암호변경",
                    href: model.getChPwdUrl(),
                    width: 576,
                    height: "auto",
                    top: "10px",
                    params:{
                        loginId: openerData.loginId
                    },
                    cbFnc: function(data){
                        console.debug(data);
                    }
                };

                htmlUtils.openDialog(options);
            };

            let setBtnEvent = function () {
                $rootNode.off("click", "button");
                $rootNode.on("click", "button", function () {
                    let btnId = $(this).attr("id");
                    switch (btnId) {
                        case "userMngViewCloseBtn":
                            closeForm();
                            break;
                        case "userMngViewGoModifyBtn":
                            openModify();
                            break;
                        case "userMngViewChangePwBtn":
                            changePwd();
                            break;
                        default:
                            console.warn("not support btnId:" + btnId);
                            return;
                    }

                    return false; // 이벤트 확산금지
                });
            };

            let setEvent = function () {
                setBtnEvent();
            };

            let init = function () {
                loadUserInfo();
                setEvent();
            };
            return {
                init: init
            };
        })();

        // private functions ############################


        // public functions #############################
        let init = function () {
            userInfo.init();
        };

        return {
            init: init
        };
    };

    let userView = new UserView();
    userView.init();
});