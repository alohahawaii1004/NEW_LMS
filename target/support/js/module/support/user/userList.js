/*
 * @(#)userList.js     2022-07-25(025) 오전 11:08:48
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

var UserMngListModel = function(){
    "use strict";

    const LIST_URL = paragonCmm.getUrl("/support/user/userMng/listPerPage/json");
    const WRITE_FORM_URL = paragonCmm.getUrl("/support/user/userWrite.popup");
    const VIEW_URL = paragonCmm.getUrl("/support/user/userView.popup");
    const AUTH_LIST_URL = paragonCmm.getUrl("/support/user/userMng/authList/json");
    const RSA_INIT_URL = paragonCmm.getUrl("/rsa/init");

    let getAuthList = function(options){
        let opts = $.extend({
            url: AUTH_LIST_URL,
            params: null,
            cbFnc: function (json) {}
        }, options);

        paragonCmm.submitAjax(opts.url, opts.params, opts.cbFnc);
    };

    let initRsa = function(options){
        let opts = $.extend({
            url: RSA_INIT_URL,
            params: null,
            cbFnc: function (json) {}
        }, options);

        paragonCmm.submitAjax(opts.url, opts.params, opts.cbFnc);

    };

    return {
        getGridUrl: function(){
            return LIST_URL;
        },
        getViewUrl: function(){
            return VIEW_URL;
        },
        getWriteUrl: function(){
            return WRITE_FORM_URL;
        },
        getAuthList: getAuthList,
        initRsa: initRsa
    };
};

var UserMngList = function(){
    "use strict";
    // attributes ======================================

    let OBJ_ID = "userMng"; // popup call back
    // 일반사용자
    const CMM_ISJ = "CMM_ISJ";
    let model = new UserMngListModel();
    let $rootNode = $("#userMngRootLayer");
    let $form = $("#userMngSchFrm0");
    let $useYnCombo = $("select[name=schUseYn]", $form);
    // let $adminAuthCombo = $("select[name=schMngAuth]", $form);
    let $userAuthCombo = $("select[name=schUserAuth]", $form);
    let $userMngAddBtn1 = $("#userMngAddBtn1", $rootNode);
    let $grid = $("#userMngList1");

    let adminAuths = [];
    let userAuths = [];
    let initSchObj = function(){

        // init combo
        htmlUtils.getCodeSelectOpt({
            targetId: $useYnCombo,
            parentCd: "USER_YN",
            initdata: "|" + paragonCmm.getLang("L.CBO_SELECT"),
            valNm: "cdAbb",
            txtNm: "langCd"
        });

        // htmlUtils.getCodeSelectOpt({
        //     targetId: $adminAuthCombo,
        //     parentCd: "NO_PARENT",
        //     jsonData: adminAuths,
        //     initdata: "|" + paragonCmm.getLang("L.CBO_SELECT"),
        //     valNm: "authCd",
        //     txtNm: "authNm"
        // });

        htmlUtils.getCodeSelectOpt({
            targetId: $userAuthCombo,
            parentCd: "NO_PARENT",
            jsonData: userAuths,
            initdata: "|" + "Select Authority",
            valNm: "authCd",
            txtNm: "authNm"
        });
    };

    (function(){
        model.getAuthList({
            cbFnc: function(json){
                if (json.errYn === "E") {
                    alert(json.msg);//-- 오류처리
                    return false;
                }
                let tmpDatas = [].concat(json.data);
                tmpDatas.forEach(function(itm){
                    switch(itm.authClassCd){
                        case "00":
                            adminAuths.push(itm);
                            break;
                        default:
                            if(itm.authCd !== CMM_ISJ){
                                userAuths.push(itm);
                            }
                            break;
                    }
                });

                initSchObj();
            }
        });
    })();

    let loginInfo = paragonCmm.formMap.get("loginInfo");

    let opts = $.extend({
        bbsTp: null,
        bbsCd: null,
        bbsNm: null
    }, $rootNode.data("json"));
    OBJ_ID = paragonCmm.makeTypeKey(OBJ_ID);
    console.debug(opts);

    // if(loginInfo.authCd.indexOf(DBOX_ROLE) !== -1){
    //     $userMngAddBtn1.show();
    // }

    // private functions ===============================
    let getGridHeight = function(){
        let ENURI = 100;
        let SCH_HEIGHT = 230; //$searchFrm.innerHeight();
        let windowHeight = window.innerHeight;
        // console.log("???...."+(windowHeight - SCH_HEIGHT - ENURI));
        return windowHeight - SCH_HEIGHT - ENURI;
    };

    let getPageSize = function(){
        let gHeight = getGridHeight();

        // console.log(gHeight);
        if(gHeight > 400){
            return 20;
        }
        return 10;
    };

    let openView = function(row){
        let loginId = (paragonCmm.isNotEmpty(row))? row.loginId : "";
        let targetName = "USER_INFO_"+loginId;
        let url = model.getViewUrl();
        if(paragonCmm.isEmpty(loginId)){
            url = model.getWriteUrl();
        }

        htmlUtils.openPopup({
            url: url,
            targetName: targetName,
            popupTitle: "사용자정보",
            popWidth: 864,
            popHeight: 540,
            openerData: {
                cbKey: OBJ_ID,
                loginId: loginId
            },
            params: null
        });
    };
    let openWrite = function(){
        openView(opts);
    };

    let useYnStyler = function(value,row,index){
        let style = "background-color: #f2dede;color: #a94442;";
        if (row.useYn === "Y"){
            style = 'background-color: #dff0d8;color: #3c763d;';
        }
        return style;
    };

    let getQueryParams = function(){
        return $form.serializeObject();
    };

    let initGrid = function(){
        $grid.datagrid({
            url: model.getGridUrl(),
            method: "post",
            queryParams: getQueryParams(),
            loadFilter: paragonCmm.easyuiLoadFilter,
            height: getGridHeight(),
            striped : true,
            fitColumns : true,
            nowrap: true,
            multiSort : false,
            remoteSort: true,
            rownumbers: true,
            singleSelect: true,
            checkOnSelect: false,
            selectOnCheck: false,
            pagination : true,
            pagePosition: "bottom",
            pageSize: getPageSize(),
            emptyMsg: "NO DATA",
            columns:[[{
                field: "userTpCd", hidden: true
            }, {
                field: "loginId", title: paragonCmm.getLang("L.아이디"), formatter: paragonCmm.getLang, halign: "CENTER", align: "CENTER", width: "10%"
            },{
                field: "userNm", title: paragonCmm.getLang("L.사용자이름"), formatter: paragonCmm.getLang, halign: "CENTER", align: "CENTER", width: "15%"
            }, {
                field: "orgNm", title: paragonCmm.getLang("L.조직"), formatter: filterXSS, halign: "CENTER", align: "CENTER", width: "15%"
            }, {
                field: "userAuths", title: paragonCmm.getLang("L.사용자권한"), formatter: filterXSS, halign: "CENTER", align: "LEFT", width: "50%"
            }, {
                field: "userYn", title: paragonCmm.getLang("L.상태"), formatter: paragonCmm.getLang, halign: "CENTER", align: "CENTER", width: "10%", styler: useYnStyler
            }]],
            onClickCell: function(idx, field, value){
                $grid.datagrid("selectRow", idx);
                openView($grid.datagrid("getSelected"));
            }
        });
    };

    let search = function(){
        console.debug("call search....");
        $grid.datagrid("load", getQueryParams());
    };

    let setCbFnc = function() {
        // 4. 팝업을 위한 콜백 설정
        paragonCmm.setCbFnc(OBJ_ID, search);
    };

    let setFormEvent = function(){
        $form.on("submit", function(){
            search();
            return false;
        });
        $form.on("keyup", "input", function (e) {
            if (e.keyCode == 13){
                search();
            }
            return false;
        });
    };
    let setBtnEvent = function(){
        $rootNode.off("click", "button");
        $rootNode.on("click", "button", function(){
            let btnId = $(this).attr("id");
            switch (btnId){
                case "userMngResetBtn1":
                    $form[0].reset();
                    break;
                case "userMngSearchBtn1":
                    search();
                    break;
                case "userMngAddBtn1":
                    openWrite();
                    break;
                default:
                    console.warn("not support btnId:"+btnId);
                    break;
            }

            return false; // 이벤트 확산금지
        });
    };
    let initForm = function(){
        // initSchObj();
        initGrid();
    };
    let loadForm = function(){};
    let setEvent = function(){
        setFormEvent();
        setBtnEvent();

    };

    // public functions ================================
    let init = function(){
        initForm();
        loadForm();
        setEvent();
        setCbFnc();
    };

    return {
        init: init
    };
};

$(document).ready(function(){
    "use strict";
    let userMngList = new UserMngList();
    userMngList.init();
});