/*
 * @(#)authMenu.js     2022-07-20(020) 오전 10:48:51
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

var AuthMenuModel = function(){
    "use strict";
    var GET_DATA_URL = paragonCmm.getUrl("/viself/auth/AuthMenu/accessList/json");
    var UPDATE_URL = paragonCmm.getUrl("/viself/auth/AuthMenu/save/json");

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

var AuthMenu = function(){
    "use strict";
    // attributes ================================================
    var SYS_AUTHS = "CMM_ISJ,CMM_SYS,SYS_DEV";
    var model = new AuthMenuModel();
    var $rootNode = $("#authMenuRootLayer");
    var $form = $("#authMenuForm1", $rootNode);
    var $authNm = $("input[name=authNm]", $rootNode);
    // var $typeCombo = $("select[name=allowYn]", $rootNode);
    var $typeCombo = $("input[name=allowYnSwitch]", $rootNode);
    var $allowYn = $("input[name=allowYn]", $rootNode);
    var $grid = $("#authMenuList1", $rootNode);
    var $saveBtn = $("#authMenuSaveBtn1", $rootNode);

    var opts = $.extend({
        authCd: null
    }, $rootNode.data("opener-data"));

    var comboCodes = [{
        code: "NONE",
        name: "None"
    },{
        code: "ALLOW",
        name: "Allow"
    }];


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

    var getGridHeight = function(){
        var ENURI = 140;
        var TAB_HEIGHT = 100; //$searchFrm.innerHeight();
        var windowHeight = window.innerHeight;
        // console.log("???...."+(windowHeight - SCH_HEIGHT - ENURI));
        return windowHeight - TAB_HEIGHT - ENURI;
    };

    var checkSysAuth = function(cd){
        if(SYS_AUTHS.indexOf(cd) !== -1){
            // $delBtn.show();
            $authNm.attr("readonly", true);
        }
    };

    var disableChkSave = function(){
        $typeCombo.switchbutton('disable');
        $saveBtn.attr("disabled", true);
    };

    var enableChkSave = function(){
        $typeCombo.switchbutton('enable');
        $saveBtn.attr("disabled", false);
    };

    var initCombo = function(){
        // init combo
        // htmlUtils.getCodeSelectOpt({
        //     targetId: $typeCombo,
        //     parentCd: "NO_PARENT",
        //     jsonData: comboCodes,
        //     initdata: "|"+paragonCmm.getLang("L.CBO_SELECT"),
        //     valNm: "code",
        //     txtNm: "name"
        // });

        // init switchbutton
        $typeCombo.switchbutton({
            onText: "ALLOW",
            offText: "NONE",
            width: 130,
            onChange: function(checked){
                var useYn = (checked) ? "ALLOW" : "NONE";
                $allowYn.val(useYn);
            }
        });
        disableChkSave();
    };

    var toTree = function(rootId, data){
        var root = (rootId) ? rootId: "menu";
        var list = [].concat(data);
        var map = {}, node, roots = [], i;

        list.forEach(function(itm, idx){
            // initialize the map: menuId 에 대한 list index 저장
            map[itm.menuId] = idx;
            // initialize the children
            itm.children = [];
        });

        list.forEach(function(itm, idx){
            if (itm.parentMenuId !== root) {
                // if you have dangling branches check that map[node.parentId] exists
                var tmpIdx = map[itm.parentMenuId];
                list[tmpIdx].children.push(itm);
            }else{
                roots.push(itm);
            }
        });

        return roots;
    };

    var accessStyler = function(val, row, index){

        var tempVal = filterXSS(val);

        if(tempVal === "ALLOW"){
            return {style:"background-color: #dff0d8;color: #3c763d;"};
        }else {
            return {style:"background-color: #f2dede;color: #a94442;"};
        }
    };

    var accessCombo = {
        type: "combobox",
        options: {
            valueField: "code",
            textField: "name",
            editable: false,
            data: comboCodes,
            onChange: function(newValue,oldValue){
                console.log("on change");
                console.log("newValue...."+newValue);
                console.log("oldValue...."+oldValue);
                if (paragonCmm.isNotEmpty(editingId) && paragonCmm.isNotEmpty(oldValue)){
                    $grid.treegrid("endEdit", editingId);

                    // save
                    // saveAccess(editingId, newValue);
                }
            }
        }
    };

    var initGrid = function(){
        $grid.treegrid({
            url: "",
            idField: "menuId",
            treeField: "langCd",
            height: getGridHeight(),
            rownumbers: true,
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            //loadFilter: menuLoadFilter,
            columns: [[{
                field: "chk", checkbox: true, width: "1%", toExcel: false
            },{
                field: "langCd", title: "NAME", formatter: paragonCmm.getLang, width: "80%"
            }, {
                field: "menuId", title: "id", formatter: filterXSS, width: "10%", hidden: true
            },{
                field: "alwCd", title: "ACCESS", formatter: filterXSS, align: "center", width: "18%", editor: accessCombo, styler: accessStyler
            }]],
            onClickRow: function(row){
                console.debug("on click row");
            },
            onUnselect: function(row){
            },
            onDblClickRow: function(idx, row){
            },
            onCheck: function(idx, row){
                enableChkSave();
            },
            onCheckAll: function(rows){
                enableChkSave();
            },
            onUncheck: function(idx, row){
                var rows = $grid.treegrid("getChecked");
                if(rows.length === 0){
                    disableChkSave();
                }
            },
            onUncheckAll: function (rows){
                disableChkSave();
            }
        });
    };

    var loadTreeData = function(){
        model.getData({
            param: opts,
            cbFnc: function(json){
                if (json.errYn === "E") {
                    alert(json.msg);//-- 오류처리
                    return false;
                }
                if (json.data) {
                    $grid.treegrid("loadData", toTree("ROOT", json.data));
                }
            }
        });
    };

    var validate = function(){

        var rows = $grid.treegrid("getChecked");
        if(rows.length === 0){
            $.messager.alert("Warning", "변경할 건을 선택해 주세요.", "warning");
            return false;
        }

        return true;
    };


    var saveData = function(){
        if(!validate()){
            return false;
        }

        $.messager.alert("Warning", "권한 정보가 바뀝니다.", "warning", function (){
            $.messager.confirm("Confirm",'정말 저장하시겠습니까?', function(r){
                if(r){

                    var rows = $grid.treegrid("getChecked");
                    rows.forEach(function(itm){
                        itm.alwCd = $allowYn.val();
                    });

                    model.saveData({
                        param: {
                            authCd: opts.authCd,
                            alwCd: $allowYn.val(),
                            list: rows
                        },
                        cbFnc: function(json){
                            if (json.errYn === "E") {
                                alert(json.msg);//-- 오류처리
                                return false;
                            }
                            loadTreeData();
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
                case "authMenuSaveBtn1":
                    saveData();
                    break;
                case "authMenuCloseBtn1":
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
        initGrid()
    };
    var loadForm = function(){
        loadTreeData();
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
    var authMenu = new AuthMenu();
    authMenu.init();
});