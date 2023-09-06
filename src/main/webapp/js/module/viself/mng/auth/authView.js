/*
 * @(#)authView.js     2022-07-20(020) 오전 8:38:58
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

var AuthMngViewModel = function(){
    "use strict";

    var DEL_URL = paragonCmm.getUrl("/viself/auth/AuthMng/delete/json");
    var UPDATE_FORM_URL = paragonCmm.getUrl("/viself/mng/auth/authUpdate.popup");
    var GET_DATA_URL = paragonCmm.getUrl("/viself/auth/AuthMng/getData/json");

    var getData = function (options){
        var opts = $.extend({
            url: GET_DATA_URL,
            param: null,
            cbFnc: function(json){}
        }, options);

        paragonCmm.callAjax(opts.url, opts.param, opts.cbFnc);
    };

    var deleteData = function(options){
        var opts = $.extend({
            url: DEL_URL,
            param: null,
            cbFnc: function(json){}
        }, options);

        paragonCmm.callAjax(opts.url, opts.param, opts.cbFnc);
    };
    return {
        getData: getData,
        deleteData: deleteData,
        getUpdateUrl: function(){
            return UPDATE_FORM_URL;
        }
    };
};

var AuthMngView = function(){
    "use strict";
    // attributes ============================
    // 기본 사용자 권한
    var CMM_ISJ = "CMM_ISJ";
    var SYS_AUTHS = "CMM_ISJ,CMM_SYS,SYS_DEV";
    var model = new AuthMngViewModel();
    var $rootNode = $("#authMngViewRootLayer");
    var $form = $("#authMngViewForm1");
    var $delBtn = $("#authMngViewDeleteBtn1");

    var opts = $.extend({
        authCd: $("input[name=authCd]", $form).val(),
        cbKey: $rootNode.data("cbkey")
    }, $rootNode.data("opener-data"));

    // private functions =====================
    var closeForm = function(data){
        if(typeof(opener) !== "undefined") {
            var cbKey = opts.cbKey;
            var openerCbFnc = opener.paragonCmm.getCbFnc(cbKey);
            if(typeof openerCbFnc === "function"){
                openerCbFnc(data);
            }
        }
        if(typeof(opener) !== "undefined") {
            self.close();
        }
    };

    var deleteData = function(){
        $.messager.alert("Warning", "관련 정보들이 삭제됩니다.", "warning", function (){
            $.messager.confirm("Confirm",'정말 삭제하시겠습니까?', function(r){
                if(r){
                    model.deleteData({
                        param: opts,
                        cbFnc: closeForm
                    });
                }
            });
        });
    };

    var openModify = function(){
        $("input[name=openerData]").val(JSON.stringify(opts));
        $form.attr("action", model.getUpdateUrl());
        $form.submit();
    };

    var makeMembers = function(data){

        console.debug(data);

        if(paragonCmm.isEmpty(data)){
            return "NO MEMBER";
        }

        var rtnHtml = "";
        var members = data.split(",");
        members.forEach(function(itm, idx){
            rtnHtml += "<span class='col-md-3 col-xs-4'><i class='fa fa-user' data-idx='"+idx+"'></i> "+filterXSS(itm.trim())+"</span>";
        });
        return rtnHtml;
    };

    var setDefaultMember = function(){
        $("[data-col='memberNms']", $rootNode).html("<span class='col-md-3 col-xs-4'><i class='fa fa-users'></i> 모든사용자(기본권한)</span>");
    };

    var setMemberStatus = function(data){

        console.debug(data);

        if(paragonCmm.isEmpty(data)){
            return "NO MEMBER";
        }

        var rtnHtml = "";
        var members = data.split(",");
        members.forEach(function(itm, idx){
            if(itm.trim() === "N"){
                $("[data-idx='"+idx+"']").addClass("alert-danger");
            }else{
                //$("[data-idx='"+idx+"']").addClass("alert-success");
            }
        });
        return rtnHtml;
    };

    var makeMenus = function(data){

        console.debug(data);

        if(paragonCmm.isEmpty(data)){
            return "NO MENU";
        }

        var rtnHtml = "";
        var menus = data.split(",");
        menus.forEach(function(itm){
            var menuInfo = itm.split("-");
            console.debug(menuInfo);
            var paddingSize = Big(menuInfo[0].trim()).times(20).toString();
            rtnHtml += "<span class='col-md-12 col-xs-12' style='padding-left:"+paddingSize+"px;'>- "+paragonCmm.getLang(menuInfo[1])+"</span>";
        });
        return rtnHtml;
    };

    var checkSysAuth = function(cd){
        if(SYS_AUTHS.indexOf(cd) === -1){
            $delBtn.show();
        }
    };

    var setContent = function(data){

        var authTp = data.authTpCd;
        if("USER" === authTp){
            var $obj = $(".text-danger");
            $obj.removeClass();
            $obj.addClass("text-success");
        }

        var keys = Object.keys(data);
        keys.forEach(function (key) {
            switch (key) {
                case "memberNms":
                    $("[data-col='"+key+"']", $rootNode).html(makeMembers(data[key]));
                    break;
                case "menuNms":
                    $("[data-col='"+key+"']", $rootNode).html(makeMenus(data[key]));
                    break;
                case "useYnNm":
                    $("[data-col='"+key+"']", $rootNode).html(paragonCmm.getLang(data[key]));
                    break;
                case "useYn":
                    var badgeClass = "alert-success";
                    if("Y" !== data[key]){
                        badgeClass = "alert-danger";
                    }
                    $("[data-col=useYnNm]", $rootNode).addClass(badgeClass);
                    break;
                case "authCd":
                    checkSysAuth(data[key]);
                    $("input[name='"+key+"']", $rootNode).val(filterXSS(data[key]));
                    // HACK no break;

                default:
                    $("[data-col='"+key+"']", $rootNode).html(filterXSS(data[key]));
                    break;
            }
        });

        if(CMM_ISJ === data.authCd){
            setDefaultMember();
        }else{
            setMemberStatus(filterXSS(data["memberUse"]));
        }
    };

    var loadContent = function(){
        model.getData({
            param: opts,
            cbFnc: function(json){
                setContent(json.data);
            }
        });
    };

    var setBtnEvent = function(){
        $rootNode.off("click", "button");
        $rootNode.on("click", "button", function(){
            var btnId = $(this).attr("id");
            switch (btnId){
                case "authMngViewDeleteBtn1":
                    deleteData();
                    break;
                case "authMngViewModifyBtn1":
                    openModify();
                    break;
                case "authMngViewCloseBtn1":
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
    };

    var setEvent = function(){
        setBtnEvent();
    };

    // public functions ======================
    var init = function(){
        initForm();
        loadForm();
        setEvent();
    };

    return {
        init: init
    };
};

$(document).ready(function(){
    "use strict";
    var authMngView = new AuthMngView();
    authMngView.init();
});