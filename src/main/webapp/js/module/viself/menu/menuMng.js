/*
 * @(#)codeMng.js    
 *
 * Copyright 2022 JAYU.space
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
    let MenuMngModel = function(){

        const TREE_URL         = paragonCmm.getUrl("/viself/menu/MenuMng/allList/json");
        const WRITE_URL         = paragonCmm.getUrl("/viself/menu/menuMngWrite");
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

        return {
            getTreeUrl: function(){
                return TREE_URL;
            },
            getWriteUrl: function(){
                return WRITE_URL;
            },
            search: function(options){
                callAjax(options, TREE_URL);
            }
        };
    };

    let MenuMng = function(){

        let model = new MenuMngModel();
        let $rootDom = $("#menuMngRootLayer");
        let $comboObj = $("#menuMngSchField");
        var $treeAreaId     = $("#menuMngTreeArea");
        var $mngFormId         = $("#menuMngform1");
        var $uuidPathId     = $("#menuMngUuidPath");
        var $schValueId     = $("#menuMngSchValue");
        var $schForm         = $("#menuMngSchForm");
        var $mngFormId         = $("#menuMngform1");
        var $menuId         = $("#menuId");
        var $parentMenuId     = $("#parentMenuId");
        var $setCaptionId     = $("#txtNamePath");
        var $btnModiId         = $("#menuMngbtnModify");
        var $btnModiChildId = $("#menuMngbtnModify_child");


        let goModify = function(frm,updateMode) {

            if ($menuId.val() === "" && updateMode !== "ONE") {
                alert(paragonCmm.getLang("M.ALERT_SELECT","수정할 메뉴정보"));
                return;
            }
            if("ONE" === updateMode){
                updateMode = "CHILD"
                $parentMenuId.val("");
                $menuId.val("");
            }
            let params = $(frm).serializeJSON();
            params.updateMode = updateMode;
            params.langCd = "L.시스템메뉴관리";
            params.accesUrl = model.getWriteUrl();
            main.goPage(params);
        };

        let getQeuryParams = function(){
            return $schForm.serializeObject();
        };

        let treeLoadFilter = function(treeData, parent){
            let data = [].concat(treeData);
            if(treeData.data){
                data = [].concat(treeData.data);
            }

            data.forEach(function(itm, idx){
                data[idx].id = itm.menuId;
                data[idx].text = paragonCmm.getLang(itm.langCd);
                data[idx].state = (itm.childCnt > 0) ? "closed" : "open";
                data[idx].attributes = itm;
            });
            return data;
        };

        let showMenuInfo = function(node){
            let keys = Object.keys(node);

            keys.forEach(function(key){
                switch (key){
                    case "menuId":
                        $("#"+key, $mngFormId).val(filterXSS(node[key]));
                        break;
                    case "parentMenuId":
                        $("#"+key, $mngFormId).val(filterXSS(node[key]));
                        break;
                    case "langCd":
                        $setCaptionId.text(paragonCmm.getLang(node[key]));
                    default:
                        $("#"+key, $mngFormId).text(filterXSS(node[key]));
                        break;
                }
            });

            $btnModiId.show();
            $btnModiChildId.show();
        };

        let initTree = function(){
            $treeAreaId.tree({
                url: model.getTreeUrl(),
                queryParams: getQeuryParams(),
                loadFilter: treeLoadFilter,
                onClick: function(node){
                    showMenuInfo(node);
                }
            });
        };

        let doSearch = function(){
            model.search({
                params: getQeuryParams(),
                cbFnc: function(json){
                    $treeAreaId.tree("loadData", { data: treeLoadFilter(json) });
                }
            });
        };
        let initForm = function(){
            // init combo
            htmlUtils.getCodeSelectOpt({
                targetId: $comboObj,
                parentCd: "NO_PARENT",
                jsonData: [{
                    cd: "LANG_CD",
                    txt: "코드명"
                },{
                    cd: "CD",
                    txt: "코드"
                }],
                initdata: "",
                valNm: "cd",
                txtNm: "txt"
            });
            $comboObj.addClass("form-control");
            $comboObj.addClass("form-control-sm");
        };
        let setEvent = function(){
            $rootDom.find("button").off();
            $rootDom.on("click", "button", function() {
                let btnId = $(this).attr("id");
                switch (btnId){

                    case "menuSearchBtn": // 검색 버튼
                        doSearch();
                        break;

                    case "menuMnglevelOne": // 1레벨 수정 버튼
                        goModify($mngFormId,'ONE');
                        break;

                    case "menuMngbtnModify": // 수정 버튼
                        goModify($mngFormId,'');
                        break;

                    case "menuMngbtnModify_child": // 하위메뉴 버튼
                        goModify($mngFormId,'CHILD');
                        break;

                    default:
                        break;
                }

                return false;
            });

            $schForm.on("submit", function(){
                doSearch(true);
                return false;
            });
        };


        let init = function(){
            initForm();
            setEvent();
            initTree();
        };

        return {
            init: init
        };
    };

    let menuMng = new MenuMng();
    menuMng.init();
});