/*
 * @(#)systemView.js     2023-03-26 오후 4:04
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
var SystemViewModel = function () {
    "use strict";

    const LOAD_SYS_INFO_URL = paragonCmm.getUrl("/support/system/systemMng/loadData/json");
    const LOAD_SYS_MNGER_URL = paragonCmm.getUrl("/support/system/sysMgrMng/getMgrList/json");
    const LOAD_SYS_USERS_URL = paragonCmm.getUrl("/support/system/sysUserMng/listPerPage/json");
    const CUSTOMER_VIEW_URL = paragonCmm.getUrl("/support/customer/customerView.popup");
    const SYSTEM_WRITE_URL = paragonCmm.getUrl("/support/system/systemWrite.popup");
    const SYS_MANAGER_VIEW_URL = paragonCmm.getUrl("/support/system/sysMgrView.modal");
    const SYS_MANAGER_ADD_URL = paragonCmm.getUrl("/support/system/sysMgrList.modal");
    const SYS_USER_ADD_URL = paragonCmm.getUrl("/support/system/sysUserList.modal");
    const SYS_USER_CREATE_URL = paragonCmm.getUrl("/support/system/sysUserWrite.modal");
    const DELETE_USER_URL = paragonCmm.getUrl("/support/system/sysUserMng/delete/json");
    const RESET_ECERT_URL = paragonCmm.getUrl("/support/system/sysUserMng/resetECert/json");

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

    let loadSystemInfo = function (options) {
        callAjax(options, LOAD_SYS_INFO_URL);
    };

    let loadManagerInfo = function (options) {
        callAjax(options, LOAD_SYS_MNGER_URL);
    };

    let loadSysUsers = function (options) {
        callAjax(options, LOAD_SYS_USERS_URL);
    };

    return {
        getCustomerViewUrl: function () {
            return CUSTOMER_VIEW_URL;
        },
        getSystemWriteUrl: function () {
            return SYSTEM_WRITE_URL;
        },
        getMgrViewUrl: function () {
            return SYS_MANAGER_VIEW_URL;
        },
        getMgrAddUrl: function () {
            return SYS_MANAGER_ADD_URL;
        },
        getUserAddUrl: function () {
            return SYS_USER_ADD_URL;
        },
        getCreateUserUrl: function () {
            return SYS_USER_CREATE_URL;
        },
        loadSysUsers: loadSysUsers,
        loadManagerInfo: loadManagerInfo,
        loadSystemInfo: loadSystemInfo,
        deleteUser: function (options) {
            callAjax(options, DELETE_USER_URL);
        },
        resetECert: function (options) {
            callAjax(options, RESET_ECERT_URL);
        }
    };
};

var SystemView = function () {
    "use strict";
    // ==============================================
    // attributes
    // ----------------------------------------------
    let model = new SystemViewModel();
    let $rootNode = $("#systemMngViewRootLayer");
    let $systemMngViewForm = $("#systemMngViewForm", $rootNode);
    let $sysUserListPage = $("#sysUserListPage", $rootNode);
    let $sysUserListPagination = $("#sysUserListPagination", $rootNode);
    let $sysUserBody = $("#sysUserBody", $rootNode);
    let openerData = $.extend({
        systemId: null,
        cbKey: null
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

        $("input[name=popupTitle]", $systemMngViewForm).val(opts.title);
        $("input[name=openerData]", $systemMngViewForm).val(JSON.stringify(opts.param));
        $systemMngViewForm.attr("action", opts.url);
        $systemMngViewForm.submit();
    };

    let systemInfo = (function () {

        let $sysManagers = $("#sysManagers");

        let setData = function (data) {
            let keys = Object.keys(data);

            keys.forEach(function (key) {
                switch (key) {
                    case "orgId":
                        openerData.orgId = data[key];
                        break;
                    case "sysDesc":
                        // paragonCmm.setEditorValue("sysDescEditor", data[key]);
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
                    case "expYn":
                        let expYnColor = "#a94442";
                        let expYnBgColor = "#f2dede";
                        if (data[key] !== "Y") {
                            expYnColor = "#3c763d";
                            expYnBgColor = "#dff0d8";
                        }
                        $("[data-col='supportDate']", $rootNode).css("color", expYnColor);
                        $("[data-col='supportDate']", $rootNode).css("background-color", expYnBgColor);
                        break;
                    default:
                        $("[data-col='" + key + "']", $rootNode).html(filterXSS(data[key]));
                        break;
                }
            });
        };

        let setMgrs = function (data) {
            let users = [].concat(data);
            console.debug(users);

            let strHtml = "";
            let badgeColor;
            users.forEach(function (itm) {
                if (paragonCmm.isNotEmpty(itm)) {
                    strHtml += " ";
                    badgeColor = "text-danger";
                    if (itm.priority === "2") {
                        badgeColor = "text-orange";
                    }
                    strHtml += '<span class="badge badge-light m-1 btn btn-default" data-id="' + filterXSS(itm.loginId) + '"><i class="fa-solid fa-user-tie ' + badgeColor + '"></i> ' + filterXSS(itm.nmKo) + '</span>';
                }
            });
            $sysManagers.html("");
            $sysManagers.html(strHtml);

            $sysManagers.off("click", "span");
            $sysManagers.on("click", "span", function () {
                console.debug($(this).data("id"));

                let mgrId = $(this).data("id");
                let options = {
                    modalId: mgrId,
                    href: model.getMgrViewUrl(),
                    title: "담당자정보",
                    params: {
                        modalId: mgrId,
                        loginId: mgrId,
                        systemId: openerData.systemId
                    },
                    cbFnc: function (data) {
                        console.debug("cbfnc....." + data);
                        init();
                    },
                    width: 576,
                    height: "auto",
                    resizable: true,
                    top: "20px"
                };

                if (paragonCmm.isMobile()) {
                    options = $.extend(options, {
                        resizable: false,
                        width: "80%",
                        height: "50%",
                        buttons: [{
                            id: "closeBtn",
                            text: "<i class=\"fa fa-times\"></i> 닫기"
                        }]
                    });
                }

                htmlUtils.openDialog(options);
                return false;
            });
        };

        let loadSysInfo = function () {
            model.loadSystemInfo({
                params: {
                    systemId: openerData.systemId
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

        let loadManagerInfo = function () {
            model.loadManagerInfo({
                params: {
                    systemId: openerData.systemId
                },
                cbFnc: function (json) {
                    if (json.errYn === "E") {
                        //-- 오류처리
                        $.messager.alert("Error", json.msg, "error");
                        return false;
                    } else {
                        setMgrs(json.data);
                    }
                }
            });
        };

        let setEvent = function () {
            // go customer info
            $("#systemMngViewGoCustomer").on("click", function () {
                submitForm({
                    title: "고객정보",
                    param: {
                        orgId: openerData.orgId
                    },
                    url: model.getCustomerViewUrl()
                });
            });

            // open manager modal
            //
        };

        let init = function () {
            loadSysInfo();
            loadManagerInfo();
            setEvent();
        };

        return {
            init: init
        };
    })();

    let sysUserList = (function () {

        let setNoDataRow = function () {
            return "<tr><td colspan='5' class='text-center'>NO DATA</td></tr>";
        };

        let setDataRow = function (itm) {
            let strRow = "<tr data-user-id='" + filterXSS(itm.loginId) + "'>";
            strRow += "<td>" + filterXSS(itm.orgId) + "</td>";
            strRow += "<td>" + filterXSS(itm.systemId) + "</td>";
            strRow += "<td>" + filterXSS(itm.loginId) + "</td>";
            strRow += "<td>" + filterXSS(itm.nmKo) + "</td>";
            strRow += "<td><del>" + filterXSS(itm.encCert) + "</del></td>";
            strRow += "<td><span class='badge badge-default btn btn-default' style='background-color:#f2dede; color: #a94442;' data-secu-id='" + filterXSS(itm.loginId) + "'><i class='fa-solid fa-shield-halved'></i></span></td>";
            strRow += "<td><span class='badge badge-default btn btn-default' style='background-color:#f2dede; color: #a94442;' data-del-id='" + filterXSS(itm.loginId) + "'><i class='fa-solid fa-minus'></i></span></td>";
            strRow += "</tr>";
            return strRow;
        };

        let setUserList = function (data) {
            let strHtml = "";
            if (Array.isArray(data.list)) {
                if (data.list.length === 0) {
                    strHtml += setNoDataRow();
                } else {
                    data.list.forEach(function (itm) {
                        strHtml += setDataRow(itm);
                    });
                }
            }
            $sysUserBody.html("");
            $sysUserBody.html(strHtml);
        };

        let search = function () {
            model.loadSysUsers({
                params: {
                    systemId: openerData.systemId,
                    page: $sysUserListPage.val()
                },
                cbFnc: function (json) {
                    if (json.errYn === "E") {
                        //-- 오류처리
                        $.messager.alert("Error", json.msg, "error");
                        return false;
                    } else {
                        setUserList($.extend({}, json.data));

                        $sysUserListPagination.pagination("refresh", {
                            total: json.data.totalCount.totCnt // 검색 총 건수
                        });
                    }
                }
            });
        };

        let initPagination = function () {
            $sysUserListPagination.pagination({
                total: 0,
                showPageList: false,
                showRefresh: false,
                pageSize: 10,
                pageNumber: 20,
                displayMsg: '',
                layout: ['list', 'sep', 'first', 'prev', 'links', 'next', 'last', 'sep', 'refresh'],
                onSelectPage: function (pageNo, pageSize) {
                    $(this).pagination('loading');
                    $sysUserListPage.val(pageNo);
                    $(this).pagination('loaded');
                    search();
                }
            });
        };

        let openModify = function () {
            submitForm({
                title: "시스템정보",
                param: {
                    systemId: openerData.systemId
                },
                url: model.getSystemWriteUrl()
            });
        };


        let addManager = function () {
            let options = {
                modalId: openerData.systemId,
                href: model.getMgrAddUrl(),
                title: "담당자추가",
                params: {
                    modalId: openerData.systemId,
                    systemId: openerData.systemId
                },
                cbFnc: function (data) {
                    systemInfo.init();
                },
                width: 864,
                height: "auto",
                resizable: true,
                top: "20px"
            };

            if (paragonCmm.isMobile()) {
                options = $.extend(options, {
                    resizable: false,
                    width: "80%",
                    height: "50%",
                    buttons: [{
                        id: "addBtn",
                        text: "<i class=\"fa fa-plus\"></i> 추가"
                    }, {
                        id: "closeBtn",
                        text: "<i class=\"fa fa-times\"></i> 닫기"
                    }]
                });
            }

            htmlUtils.openDialog(options);
        };

        let createUser = function () {
            let options = {
                modalId: openerData.systemId,
                href: model.getCreateUserUrl(),
                title: "사용자추가",
                params: {
                    modalId: openerData.systemId,
                    orgId: openerData.orgId,
                    systemId: openerData.systemId
                },
                cbFnc: function (data) {
                    sysUserList.init();
                },
                width: 864,
                height: "auto",
                resizable: true,
                top: "20px"
            };

            if (paragonCmm.isMobile()) {
                options = $.extend(options, {
                    resizable: false,
                    width: "80%",
                    height: "50%",
                    buttons: [{
                        id: "addBtn",
                        text: "<i class=\"fa fa-plus\"></i> 추가"
                    }, {
                        id: "closeBtn",
                        text: "<i class=\"fa fa-times\"></i> 닫기"
                    }]
                });
            }

            htmlUtils.openDialog(options);
        };

        let addUser = function () {
            let options = {
                modalId: openerData.systemId,
                href: model.getUserAddUrl(),
                title: "사용자추가",
                params: {
                    modalId: openerData.systemId,
                    orgId: openerData.orgId,
                    systemId: openerData.systemId
                },
                cbFnc: function (data) {
                    sysUserList.init();
                },
                width: 864,
                height: "auto",
                resizable: true,
                top: "20px"
            };

            if (paragonCmm.isMobile()) {
                options = $.extend(options, {
                    resizable: false,
                    width: "80%",
                    height: "50%",
                    buttons: [{
                        id: "addBtn",
                        text: "<i class=\"fa fa-plus\"></i> 추가"
                    }, {
                        id: "closeBtn",
                        text: "<i class=\"fa fa-times\"></i> 닫기"
                    }]
                });
            }

            htmlUtils.openDialog(options);
        };

        let deleteUser = function (usrId) {
            $.messager.confirm("Confirm", '정말 삭제하시겠습니까?', function (r) {
                if (r) {
                    model.deleteUser({
                        params: {
                            loginId: usrId,
                            systemId: openerData.systemId
                        },
                        cbFnc: function (json) {
                            if (json.errYn === "E") {
                                //-- 오류처리
                                $.messager.alert("Error", json.msg, "error");
                                return false;
                            } else {
                                if (typeof json.data === "undefined") {
                                    json.data = {};
                                }
                                sysUserList.init();
                            }
                        }
                    });
                }
            });
        };

        let resetECert = function (secuId) {
            $.messager.confirm("Confirm", '인증서가 리셋됩니다.\n진행 하시겠습니까?', function (r) {
                if (r) {
                    model.resetECert({
                        params: {
                            loginId: secuId,
                            systemId: openerData.systemId
                        },
                        cbFnc: function (json) {
                            if (json.errYn === "E") {
                                //-- 오류처리
                                $.messager.alert("Error", json.msg, "error");
                                return false;
                            } else {
                                if (typeof json.data === "undefined") {
                                    json.data = {};
                                }
                                sysUserList.init();
                            }
                        }
                    });
                }
            });
        };

        let setBtnEvent = function () {
            $rootNode.off("click", "button");
            $rootNode.on("click", "button", function () {
                let btnId = $(this).attr("id");
                switch (btnId) {
                    case "systemMngViewCloseBtn":
                        closeForm();
                        break;
                    case "systemMngViewGoModifyBtn":
                        openModify();
                        break;
                    default:
                        console.warn("not support btnId:" + btnId);
                        return;
                }

                return false; // 이벤트 확산금지
            });

            $rootNode.off("click", ".btn");
            $rootNode.on("click", ".btn", function () {
                let btnId = $(this).attr("id");
                switch (btnId) {
                    case "sysMgrAddBtn":
                        addManager();
                        break;
                    case "sysUsrNewBtn":
                        createUser();
                        break;
                    case "sysUsrAddBtn":
                        addUser();
                        break;
                    default:

                        let delId = $(this).data("del-id");
                        let secuId = $(this).data("secu-id");
                        if (paragonCmm.isNotEmpty(delId)) {
                            deleteUser(delId);
                        } else if (paragonCmm.isNotEmpty(secuId)) {
                            resetECert(secuId);
                        } else {
                            console.warn("not support btnId:" + btnId);
                        }
                        return;
                }

                return false; // 이벤트 확산금지
            });
        };

        let setEvent = function () {
            setBtnEvent();
        };

        let init = function () {
            initPagination();
            setEvent();
            search();
        };
        return {
            init: init
        };
    })();

    let init = function () {
        if (paragonCmm.isEmpty(openerData.systemId)) {
            closeForm();
        }
        systemInfo.init();
        sysUserList.init();
    };
    return {
        init: init
    };
};

$(function () {
    "use strict";
    let systemView = new SystemView();
    systemView.init();
});