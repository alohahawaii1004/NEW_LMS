/*
 * @(#)urlDialog.js     2021-03-31(031) 오후 2:50
 *
 * Copyright 2021 JAYU.space
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

var UrlMngDialog = function(){

    var code = {
        useYnOptions: [
            {id: "Y", value: "Use"},
            {id: "N", value: "No Use"}
        ],
        alwDivOptions: [
            {id: "IS_AUTHENTICATED_FULLY", value: "Authenticated"},
            {id: "IS_AUTHENTICATED_ANONYMOUSLY", value: "Anonymously"}
        ],
    };

    /**
     * 팝업 높이
     */
    var POP_HEIGHT = 450;

    /**
     * 팝업 너비
     */
    var POP_WIDTH= 600;

    var $grid = $("#urlMngList1");
    var $dialog = $("#urlMngPop1");
    var $dialogSaveBtn = $("#urlMngPop1SaveBtn1");
    var $dialogDelBtn = $("#urlMngPop1DeleteBtn1");
    var $dialogCloseBtn = $("#urlMngPop1CloseBtn1");
    var $dialogForm = $("#urlMngPop1Frm0");
    var $dialogAlwDivCombo = $("#urlMngPop1Select1");
    var $dialogUseYnCombo = $("#urlMngPop1Select2");
    var $row = null;
    var $model = null;
    var search = null;
    var openerData = null;

    /**
     * is empty
     * @param obj
     * @returns {boolean} true:null or undefined or "" other: not empty
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
     * 콤보 옵션 추가
     * @param comboObj 콤보 Obj
     * @param options [{id:, value:}] 형태의 추가될 옵션 정보
     */
    var setOptions = function (comboObj, options) {
        comboObj.empty();
        var iLen = options.length;

        for (var i = 0; i < iLen; i += 1) {
            var option = $('<option value="' + options[i].id + '">' + options[i].value + '</option>');
            comboObj.append(option);
        } // end of for
    };

    var resetEvent = function(){
        $dialogSaveBtn.off();
        $dialogDelBtn.off();
        $dialogCloseBtn.off();
    };

    var initDialog = function(){
        openerData = $dialog.data("openerData");
        $row = openerData.row;
        $model = openerData.model;
        search = openerData.cbFnc;

        $dialog.addClass("hidden");
        $dialog.removeClass("hidden");
        $dialog.dialog({
            iconCls: "fa fa-info-circle",
            title: "URL Info.",
            width: POP_WIDTH,
            height: POP_HEIGHT,
            cache: false,
            method: "post",
            modal: true,
            onClose: function(){
                // HACK 이벤트를 지우지 않으면 계속 쌓인다.
                resetEvent();
            }
        });
    };

    var setContent = function(){


        setOptions($dialogUseYnCombo, code.useYnOptions);
        setOptions($dialogAlwDivCombo, code.alwDivOptions);

        // console.log($row);

        if(isEmpty($row)){
            $dialogDelBtn.addClass("hidden");
            $dialogForm.find("input[name=cud]").val("C");
            $dialogForm.find("input[name=accesUrl]").attr("readonly", false);
            $dialogForm.find("input[name=accesUrl]").val("");
            $dialogForm.find("input[name=urlDesc]").val("");
            $dialogForm.find("select[name=alwDiv]").val("");
            $dialogForm.find("select[name=useEnable]").val("Y");
        }else{
            $dialogDelBtn.removeClass("hidden");
            $dialogForm.find("input[name=cud]").val("U");
            $dialogForm.find("input[name=accesUrl]").attr("readonly", true);
            $dialogForm.find("input[name=accesUrl]").val(filterXSS($row.accesUrl));
            $dialogForm.find("input[name=urlDesc]").val(filterXSS($row.urlDesc));
            $dialogForm.find("select[name=alwDiv]").val(filterXSS($row.alwDiv));
            $dialogForm.find("select[name=useEnable]").val(filterXSS($row.useEnable));
        }

    };

    var save = function(){
        // TODO 필수 값 검사

        $.messager.confirm("Confirm",'정말 저장하시겠습니까?',function(r) {
            if(r){
                var data = $dialogForm.serializeObject();
                var cbFnc = function(){
                    search();
                    closeDialog();
                };
                $model.saveData(data, cbFnc);
            }
        });
    };

    var delUrl = function(){
        if(isEmpty($row)){
            return;
        }

        $.messager.confirm("Confirm",'정말 삭제하시겠습니까?\n시스템에 문제가 발생할 수 있습니다.',function(r) {
            if(r){
                var data = {
                    list: [$row]
                };
                var cbFnc = function(){
                    search();
                    closeDialog();
                };
                $model.deleteData(data, cbFnc);
            }
        });
    };

    var closeDialog = function(){
        $dialog.dialog("close");
    };

    var setEvent = function(){
        $dialogSaveBtn.on("click", function(e){
            console.log("$dialogSaveBtn click..............");
            save();
            return false;
        });

        $dialogDelBtn.on("click", function(e){
            delUrl();
            return false;
        });

        $dialogCloseBtn.on("click", function(e){
            closeDialog();
            return false;
        });
    };

    var open = function(){
        initDialog();
        setContent();
        setEvent();
    };

    return{
        open: open
    };
};

$(document).ready(function () {
    "use strict";
    console.info("[Loading Module: URL관리 Dialog].....................");

    var urlMngDialog = new UrlMngDialog();
    urlMngDialog.open();
}());