/*
 * @(#)authGeneral.js     2022-07-20(020) 오전 10:48:40
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

var AuthGeneralModel = function(){
    "use strict";
    var GET_DATA_URL = paragonCmm.getUrl("/viself/auth/AuthMng/getData/json");
    var UPDATE_URL = paragonCmm.getUrl("/viself/auth/AuthMng/update/json");

    var getData = function (options){
        var opts = $.extend({
            url: GET_DATA_URL,
            param: null,
            cbFnc: function(json){}
        }, options);

        paragonCmm.callAjax(opts.url, opts.param, opts.cbFnc);
    };

    var saveData = function(options){
        var opts = $.extend({
            url: UPDATE_URL,
            param: null,
            cbFnc: function(json){}
        }, options);

        paragonCmm.callAjax(opts.url, opts.param, opts.cbFnc);
    };

    return {
        getData: getData,
        saveData: saveData
    };
};

var AuthGeneral = function(){
    "use strict";
    // attributes ================================================
    var SYS_AUTHS = "CMM_ISJ,CMM_SYS,SYS_DEV";
    var model = new AuthGeneralModel();
    var $rootNode = $("#authGeneralRootLayer");
    var $form = $("#authGeneralForm1", $rootNode);
    var $authNm = $("input[name=authNm]", $rootNode);
    var $typeCombo = $("input[name=useYnSwitch]", $rootNode);
    var $useYn = $("input[name=useYn]", $rootNode);
    // var $typeCombo = $("select[name=useYn]", $rootNode);

    var opts = $.extend({
        authCd: null
    }, $rootNode.data("opener-data"));


    // private functions =========================================
    var doCallback = function(data){
        if(typeof(opener) !== "undefined") {
            var cbKey = opts.cbKey;
            var openerCbFnc = opener.paragonCmm.getCbFnc(cbKey);
            if(typeof openerCbFnc === "function"){
                openerCbFnc(data);
            }
        }
    };
    var closeForm = function(data){
        doCallback(data);
        if(typeof(opener) !== "undefined") {
            self.close();
        }
    };

    var checkSysAuth = function(cd){
        if(SYS_AUTHS.indexOf(cd) !== -1){
            // $delBtn.show();
            $authNm.attr("readonly", true);
        }
    };

    var initCombo = function(){
        // init combo
        // htmlUtils.getCodeSelectOpt({
        //     targetId: $typeCombo,
        //     parentCd: "USE_YN",
        //     initdata: "",
        //     valNm: "cdAbb",
        //     txtNm: "langCd"
        // });

        // init switchbutton
        $typeCombo.switchbutton({
            onText: paragonCmm.getLang("L.사용"),
            offText: paragonCmm.getLang("L.미사용"),
            width: 100,
            onChange: function(checked){
                var useYn = (checked) ? "Y" : "N";
                $useYn.val(useYn);
            }
        });
    };

    var setContent = function(data){
        var keys = Object.keys(data);
        keys.forEach(function (key) {
            switch (key) {
                case "useYn":
                    if("Y" === data[key]){
                        $typeCombo.switchbutton("check");
                    }else{
                        $typeCombo.switchbutton("uncheck");
                    }
                    $("[data-col='"+key+"']", $rootNode).val(filterXSS(data[key]));
                    console.debug($form.serializeObject());
                    break;
                case "remark":
                case "ordNo":
                case "authNm":
                    $("[data-col='"+key+"']", $rootNode).val(filterXSS(data[key]));
                    break;
                case "authCd":
                    $("input[name='"+key+"']", $rootNode).val(filterXSS(data[key]));
                    checkSysAuth(data[key]);
                // HACK no break;
                default:
                    $("[data-col='"+key+"']", $rootNode).html(filterXSS(data[key]));
                    break;
            }
        });
    };

    var saveData = function(){

        $.messager.alert("Warning", "권한 정보가 바뀝니다.", "warning", function (){
            $.messager.confirm("Confirm",'정말 저장하시겠습니까?', function(r){
                if(r){
                    model.saveData({
                        param: $form.serializeObject(),
                        cbFnc: function(json){
                            if (json.errYn === "E") {
                                alert(json.msg);//-- 오류처리
                                return false;
                            }
                            doCallback();
                        }
                    });
                }
            });
        });
    };

    var setBtnEvent = function(){
        $rootNode.off("click", "button");
        $rootNode.on("click", "button", function(){
            var btnId = $(this).attr("id");
            switch (btnId){
                case "authGeneralSaveBtn1":
                    saveData();
                    break;
                case "authGeneralCloseBtn1":
                    closeForm();
                    break;
                default:
                    console.warn("not support btnId:"+btnId);
                    break;
            }

            return false; // 이벤트 확산금지
        });
    };

    var initForm = function(){
        initCombo();
    };
    var loadForm = function(){
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
    var setEvent = function(){
        setBtnEvent();
    };

    // public functions ==========================================
    var init = function(){
        initForm();
        loadForm();
        setEvent();
        paragonCmm.convertLang($rootNode);
    };
    return {
        init: init
    };
};

$(document).ready(function(){
    "use strict";
    var authGeneral = new AuthGeneral();
    authGeneral.init();
});