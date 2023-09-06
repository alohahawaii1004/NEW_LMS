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

    let CodeMngModel = function(){

        const TREE_URL = paragonCmm.getUrl("/viself/code/CodeMng/listCode/json");
        const CODE_WRITE_URL = paragonCmm.getUrl("/viself/code/codeMngWrite.include");
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
            getWriteUrl: function(){
                return CODE_WRITE_URL;
            },
            getTreeUrl: function (){
                return TREE_URL;
            },
            search: function(options){
                callAjax(options, TREE_URL);
            }
        };
    };

    let CodeMng = function(){

        let model = new CodeMngModel();
        let $rootDom = $("#codeMngRootLayer");
        let $codeTreeArea = $("#codeTreeArea");
        let $schForm = $("#codeMngSchForm");
        let $comboObj = $("#codeMngSchField");
        let opt = {
            "selectType":"",    // 최하위자식만 선택가능 null 값이면 부모도 선택 가능
            "parentCd":"ROOT"   // 최초 부모 코드
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
                data[idx].id = itm.cd;
                data[idx].text = paragonCmm.getLang(itm.langCd);
                data[idx].state = (itm.childCnt > 0) ? "closed" : "open";
                data[idx].attributes = itm;
            });

            console.debug(data);

            return data;
        };

        let changeCodeView = function(data) {
            // var data = JSON.parse(json);
            $("#codeMngform1 input:hidden[name='cd']").val(data.cd);
            $("#codeMngform1 input:hidden[name='parentCd']").val(data.parentCd);

            document.getElementById("txtNamePath").innerHTML     = paragonCmm.getLang(data.langCdPath);
            document.getElementById("txtCd").innerHTML             = paragonCmm.convertForView(data.cd);
            document.getElementById("txtCdAbb").innerHTML         = paragonCmm.convertForView(data.cdAbb);
            document.getElementById("txtLangCd").innerHTML         = paragonCmm.convertForView(data.langCd);
            document.getElementById("txtCdNm").innerHTML         = paragonCmm.getLang(data.langCd);
            document.getElementById("txtCodeAttr1").innerHTML    = paragonCmm.convertForView(data.cdAttr1);
            document.getElementById("txtCodeAttr2").innerHTML    = paragonCmm.convertForView(data.cdAttr2);
            document.getElementById("txtCodeAttr3").innerHTML    = paragonCmm.convertForView(data.cdAttr3);
            document.getElementById("txtCodeAttr4").innerHTML    = paragonCmm.convertForView(data.cdAttr4);
            document.getElementById("txtCodeAttr1Desc").innerHTML    = paragonCmm.convertForView(data.attrDesc1);
            document.getElementById("txtCodeAttr2Desc").innerHTML    = paragonCmm.convertForView(data.attrDesc2);
            document.getElementById("txtCodeAttr3Desc").innerHTML    = paragonCmm.convertForView(data.attrDesc3);
            document.getElementById("txtCodeAttr4Desc").innerHTML    = paragonCmm.convertForView(data.attrDesc4);
            document.getElementById("txtCodeData").innerHTML    = paragonCmm.convertForView(data.cdData);
            document.getElementById("txtOrderNo").innerHTML    = paragonCmm.convertForView(data.ordNo);
            document.getElementById("txtStatusName").innerHTML     = paragonCmm.convertForView(data.useEnable);
            document.getElementById("selCdPath").value     = paragonCmm.convertForView(data.cdPath);

            //-- 시스템 전용 코드일 경우 수정/삭제 버튼 디스플레이 처리
            var btnWriteObj = document.getElementById("codeMngBtnWrite");
            var btnModifyObj = document.getElementById("codeMngBtnModify");
            var btnModify_childObj = document.getElementById("codeMngBtnModify_child");
            var btnDeleteObj = document.getElementById("codeMngBtnDelete");

            if (data.useYn == "S") {    //-- 시스템 전용일경우 삭제 버튼 없앰.. 현재 미구현(Y/N만있음) 20210806 강세원
                btnModifyObj.style.display = "";
                btnModify_childObj.style.display = "";
                btnDeleteObj.style.display = "none";
            } else {
                btnModifyObj.style.display = "";
                btnModify_childObj.style.display = "";
                btnDeleteObj.style.display = "";
                // btnWriteObj.style.display = "none";
            }
        };

        let initTree = function(){
            $codeTreeArea.tree({
                url: model.getTreeUrl(),
                queryParams: getQeuryParams(),
                loadFilter: treeLoadFilter,
                onClick: function(node){
                    console.debug(node);

                    changeCodeView(node);
                }
            });
        };

        let doSearch = function(){

            console.debug("doSearch..........")

            model.search({
                params: getQeuryParams(),
                cbFnc: function(json){
                    console.debug(json)
                    $codeTreeArea.tree("loadData", { data: treeLoadFilter(json) });
                }
            });
        };

        let goModify = function(frm, updateMode) {
            if (frm.cd.value === "" && updateMode !== "ONE") {
                return;
            }
            if("ONE" === updateMode){
                updateMode = "CHILD"
                frm.cd.value = "";
            }
            let params = $(frm).serializeJSON();
            params.updateMode = updateMode;
            params.langCd = "L.코드관리";
            params.accesUrl = model.getWriteUrl();
            main.goPage(params);
        };

        let goDelete = function(frm) {
            if (frm.cd.value == "") {
                alert(paragonCmm.getLang("M.ALERT_SELECT", "L.삭제할코드정보"));
                return;
            }
            var msg = paragonCmm.getLang("L.선택하신코드및하위코드정보를완전히삭제") ;
            $.messager.confirm("Confirm", msg, function (r) {
                if(r){
                    paragonCmm.showBackDrop();
                    var params = $("#codeMngform1").serializeObject();
                    $.ajax({
                        url: paragonCmm.getUrl("/viself/code/CodeMng/deleteCode/json")
                        , type: "POST"
                        , dataType: "json"
                        , contentType: "application/json"
                        , data: JSON.stringify(params)
                    })
                        .done(function(data) {
                            paragonCmm.hideBackDrop();
                            if(data.errYn !== "E"){
                                alert(paragonCmm.getLang(data.msg), function(){
                                    $("#codeTreeArea").html("");
                                    drawTree();
                                    paragonCmm.hideBackDrop();
                                });
                            }else{
                                $.messager.alert("Warning", paragonCmm.getLang(data.msg), "warning");
                            }
                        })
                        .fail(function() {
                            alert("오류");
                            //승인처리 도중 에러가 발생했습니다.
                            paragonCmm.hideBackDrop()
                        });

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
            $rootDom.on("click", "button", function(){
                var btnId = $(this).attr("id");
                switch(btnId){
                    case "cdSearchBtn":
                        doSearch();
                        break;
                    case "codeMngBtnWrite": // 레벨1 수정 버튼
                        goModify(document.codeMngform1,'ONE');
                        break;
                    case "codeMngBtnModify": // 수정버튼
                        goModify(document.codeMngform1,'');
                        break;
                    case "codeMngBtnModify_child": // 하위코드 수정
                        goModify(document.codeMngform1,'CHILD');
                        break;
                    case "codeMngBtnDelete":
                        goDelete(document.codeMngform1);
                        break;
                    default:
                        break;
                }
                return false;
            });

            $schForm.on("submit", function(){
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

    let codeMng = new CodeMng();
    codeMng.init();
});