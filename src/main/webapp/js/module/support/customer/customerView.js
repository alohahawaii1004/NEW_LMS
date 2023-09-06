/*
 * @(#)customerView.js     2023-03-13 013 오전 11:04:23
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


var CustomerViewModel = function(){
    "use strict";
    const LOAD_DATA_URL = paragonCmm.getUrl("/support/customer/customerMng/loadData/json");
    const SYS_LIST_URL = paragonCmm.getUrl("/support/system/systemMng/listPerPage/json");
    const SYS_VIEW_URL = paragonCmm.getUrl("/support/system/systemView.popup");
    const SYS_WRITE_URL = paragonCmm.getUrl("/support/system/systemWrite.popup");
    const WRITE_FROM_URL = paragonCmm.getUrl("/support/customer/customerWrite.popup");

    let getOpts = function(options, url){
        return $.extend({
            url: url,
            params: null,
            cbFnc: function (json) {
            },
            async: false,
            errorBack: function(){}
        }, options);
    };
    let callAjax = function(options, url){
        let opts = getOpts(options, url);
        paragonCmm.callAjax(opts.url, opts.params, opts.cbFnc);
    };

    let submitAjax = function(options, url){
        let opts = getOpts(options, url);
        paragonCmm.submitAjax(opts.url, opts.params, opts.cbFnc, opts.async, opts.errorBack);
    };

    let loadData = function(options){
        callAjax(options, LOAD_DATA_URL);
    };

    let loadSysList = function(options){
        callAjax(options, SYS_LIST_URL);
    };

    return {
        loadData: loadData,
        loadSysList: loadSysList,
        getSysViewUrl: function(){
            return SYS_VIEW_URL;
        },
        getSysWriteUrl: function(){
            return SYS_WRITE_URL;
        },
        getUpdateUrl: function(){
            return WRITE_FROM_URL;
        }
    };
};

var CustomerView = function(){
    "use strict";
    // ==============================================
    // attributes
    // ----------------------------------------------
    let model = new CustomerViewModel();
    let $rootNode = $("#customerMngViewRootLayer");
    let $form = $("#customerMngViewForm", $rootNode);
    let $sysListTable = $("#sysListTable", $rootNode);
    let $sysListBody = $("tbody", $sysListTable);
    let $sysListPagination = $("#sysListPagination", $rootNode);
    let $sysListPage = $("#sysListPage", $rootNode);
    let $customerMngSysForm = $("#customerMngViewForm", $rootNode);
    let openerData = $.extend({
        orgId: null,
        cbKey: null
    }, $rootNode.data("opener-data"));

    // ==============================================
    // private functions
    // ----------------------------------------------
    let closeForm = function(){
        if(typeof(opener) !== "undefined") {
            self.close();
        }
    };

    let submitForm = function(options){
        let opts = $.extend({
            title: null,
            param: null,
            url: null
        }, options);

        console.debug(opts);

        $("input[name=popupTitle]", $customerMngSysForm).val(opts.title);
        $("input[name=openerData]", $customerMngSysForm).val(JSON.stringify(opts.param));
        $customerMngSysForm.attr("action", opts.url);
        $customerMngSysForm.submit();
    };

    let systemList = (function(orgId){

        $customerMngSysForm.attr("action", model.getSysViewUrl());
            $("input[name=popupTitle]", $customerMngSysForm).val("시스템정보");

        let setNoDataRow = function(){
            return "<tr><td colspan='5' class='text-center'>NO DATA</td></tr>";
        };

        let setDataRow = function(itm){
            let strRow = "<tr data-sys-id='"+filterXSS(itm.systemId)+"'>";
            strRow += "<td>"+filterXSS(itm.systemId)+"</td>";
            strRow += "<td>"+filterXSS(itm.systemName)+"</td>";
            strRow += "<td>"+filterXSS(itm.productTpNm)+"</td>";
            strRow += "<td>"+filterXSS(itm.smStDte)+"</td>";

            let rmClass = "";
            if(itm.rmSmDay < 0){
                // rmClass = "class='alert-danger'"
                // rmClass = "style='background-color: #dff0d8;color: #3c763d;'"
                rmClass = "style='background-color: #f2dede;color: #a94442;'"
            }

            strRow += "<td "+rmClass+">"+filterXSS(itm.smEdDte)+"</td>";
            strRow += "</tr>";
            return strRow;
        };

        let setSysList = function(data){
            let strHtml = "";
            if(Array.isArray(data.list)){
                if(data.list.length === 0){
                    strHtml += setNoDataRow();
                }else{
                    data.list.forEach(function(itm){
                        strHtml += setDataRow(itm);
                    });
                }
            }
            $sysListBody.html("");
            $sysListBody.html(strHtml);
        };

        let search = function(){
            model.loadSysList({
                params: {
                    schOrgId: openerData.orgId,
                    page: $sysListPage.val()
                },
                cbFnc: function(json){
                    if (json.errYn === "E") {
                        //-- 오류처리
                        $.messager.alert("Error", json.msg,"error");
                        return false;
                    } else  {
                        setSysList($.extend({}, json.data));

                        $sysListPagination.pagination("refresh", {
                            total: json.data.totalCount.totCnt // 검색 총 건수
                        });
                    }
                }
            });
        };

        let initPagination = function(){
            $sysListPagination.pagination({
                total: 0,
                showPageList: false,
                showRefresh: false,
                pageSize: 10,
                pageNumber: 20,
                displayMsg: '',
                layout:['list','sep','first','prev','links','next','last','sep','refresh'],
                onSelectPage: function (pageNo, pageSize) {
                    $(this).pagination('loading');
                    $sysListPage.val(pageNo);
                    $(this).pagination('loaded');
                    search();
                }
            });
        };

        let newSystem = function(){
            submitForm({
                title: "시스템정보",
                param: {
                    orgId: openerData.orgId
                },
                url: model.getSysWriteUrl()
            });
        };

        let setEvent = function(){
            $("#addSystemBtn").on("click", function(){
                newSystem();
            });
            $sysListBody.on("click", "tr", function(){
                // go system info
                console.debug($(this).data("sys-id"));

                if(paragonCmm.isEmpty($(this).data("sys-id"))){
                    newSystem();
                }else{
                    submitForm({
                        title: "시스템정보",
                        param: {
                            systemId: $(this).data("sys-id")
                        },
                        url: model.getSysViewUrl()
                    });
                }
            });
        };

        let init = function(){
            initPagination();
            setEvent();
            search();

        };

        return {
            init: init
        };
    })(openerData.orgId);




    let setContent = function(data){
        let keys = Object.keys(data);

        keys.forEach(function(key){
            switch (key) {
                case "orgDesc":
                    // paragonCmm.setEditorValue("orgDescEditor", data[key]);
                    $("[data-col='"+key+"']", $rootNode).html(data[key]);
                    break;
                case "useYn":
                    let badgeColor = "#a94442";
                    let badgeBgColor = "#f2dede";
                    let badgeText = "사용하지 않음";
                    if(data[key] === "Y"){
                        badgeColor = "#3c763d";
                        badgeBgColor = "#dff0d8";
                        badgeText = "사용";
                    }
                    $("[data-col='"+key+"']", $rootNode).html(badgeText);
                    $("[data-col='"+key+"']", $rootNode).css("color", badgeColor);
                    $("[data-col='"+key+"']", $rootNode).css("background-color", badgeBgColor);
                    break;
                default:
                    $("[data-col='"+key+"']", $rootNode).html(filterXSS(data[key]));
                    break;
            }
        });
    };

    let loadData = function () {
        console.debug(openerData);
        model.loadData({
            params: {
                orgId: openerData.orgId
            },
            cbFnc: function(json){
                if (json.errYn === "E") {
                    //-- 오류처리
                    $.messager.alert("Error", json.msg,"error");
                    return false;
                } else  {
                    setContent($.extend({}, json.data));
                }
            }
        });

        systemList.init();
    };

    var openModify = function(){
        submitForm({
            title: "고객정보",
            param: openerData,
            url: model.getUpdateUrl()
        });
    };

    // let setFormEvent = function(){
    //     $form.on("submit", function(){
    //         return false;
    //     });
    // };
    let setBtnEvent = function(){
        $rootNode.off("click", "button");
        $rootNode.on("click", "button", function(){
            let btnId = $(this).attr("id");
            switch (btnId){
                case "customerMngViewCloseBtn":
                    closeForm();
                    break;
                case "customerMngViewGoModifyBtn":
                    openModify();
                    break;
                default:
                    console.warn("not support btnId:"+btnId);
                    return;
            }

            return false; // 이벤트 확산금지
        });
    };

    let initForm = function(){
        // CKEDITOR.instances['orgDescEditor'].disableAutoInline = true;
        // CKEDITOR.instances['orgDescEditor'].inline( 'orgDescEditor' );
    };
    let loadForm = function(){
        loadData();
    };
    let setEvent = function(){
        // setFormEvent();
        setBtnEvent();

        // CKEDITOR.instances["orgDescEditor"].on("instanceDestroyed", function(){
        //     console.debug("ckeditor");
        // });
    };

    // ==============================================
    // public functions
    // ----------------------------------------------
    var init = function(){
        initForm();
        loadForm();
        setEvent();
    };
    return {
        init: init
    };
};

$(function(){
    "ues strict";
    let customerView = new CustomerView();
    customerView.init();
});