/*
 * @(#)authMng.js     2022-03-14(014) 오후 3:56
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

function Auth() {
    //-- 권한 조회
    var loadAuths = function(authCd){
        paragonCmm.callAjax(paragonCmm.getUrl("/viself/auth/AuthMng/list/json"),{}, function(json){
            if(json.errYn === "E"){
                //-- 오류처리
                alert(json.msg);
                return false;
            }
            $("#rolesTbody").html(""); // 초기화
            $(json.data).each(function(i, d){
                var $tr = $("<tr>");
                var $td = $("<td>");
                var $label = $("<label style='margin:0px;'>");
                var $checkbox = $("<input type='radio' class='checked' name='authCd'>").val(d.authCd);
                var $button = $("<button class='close' aria-hidden='true' data-dismiss='alert' type='button'>×</button>");
                $label.append($checkbox).append(" "+d.authNm);
                $td.append($label);
                $td.append($button);
                $tr.append($td);
                var $td2 = $("<td>").append(d.authCd);
                $tr.append($td2);
                $("#rolesTbody").append($tr);
                $($label, $checkbox).on("click",function(){
                    loadAuthMember();
                    loadAuthMenu();
                    //-- 수정모드변경
                    $("#addAuthCd").val(d.authCd).attr("readonly","readonly");
                    $("#addAuthNm").val(d.authNm);
                    $("#addAuthBtn").hide();
                    $("#resAuthBtn").show();
                    $("#uptAuthBtn").show();

                });
                $($button).on("click",function(){
                    delAuthMng(d.authCd);
                });
            });
            if(typeof authCd === "string" && authCd != ""){
                $("input:radio[name='authCd'][value='"+authCd+"']").trigger("click");
            }
            paragonCmm.tableTrDragSet("rolesTbody",function(){
                confirm("메뉴의 순서를 변경하시겠습니까?",function(r){
                    if(r){
                        $("input:radio[name='authCd']").each(function(i, o){
                            var data = {};
                            data.ordNo = i+1;
                            data.authCd = $(o).val();
                            // 순서변경 저장
                            paragonCmm.callAjax(paragonCmm.getUrl("/viself/auth/AuthMng/updateSort/json"),data, function(json){
                                if(json.errYn === "E"){
                                    //-- 오류처리
                                    alert(json.msg);
                                    return false;
                                }
                            });
                        });
                    }
                })
            }); //순서변경
        });
    }
    //-- 권한 추가
    var addAuthMng = function(){
        var authCd = $("#addAuthCd").val();
        var authNm = $("#addAuthNm").val();
        if((authCd != null && authCd != "") && (authNm != null && authNm != "")){
            var data = {};
            data.authCd = authCd;
            data.authNm = authNm;
            data.authTpCd = "USER";
            data.useYn = "Y";

            paragonCmm.callAjax(paragonCmm.getUrl("/viself/auth/AuthMng/insert/json"),data, function(json){
                if(json.errYn === "E"){
                    //-- 오류처리
                    alert(json.msg);
                    return false;
                }
                $("#addAuthCd").val("");
                $("#addAuthNm").val("");
                loadAuths(authCd);

            });
        }
    }
    //-- 권한 수정
    var uptAuthMng = function(){
        var authCd = $("#addAuthCd").val();
        var authNm = $("#addAuthNm").val();
        if((authCd != null && authCd != "") && (authNm != null && authNm != "")){
            var data = {};
            data.authCd = authCd;
            data.authNm = authNm;
            paragonCmm.callAjax(paragonCmm.getUrl("/viself/auth/AuthMng/update/json"),data, function(json){
                if(json.errYn === "E"){
                    //-- 오류처리
                    alert(json.msg);
                    return false;
                }
                $("#addAuthCd").val("");
                $("#addAuthNm").val("");
                loadAuths(authCd);
            });
        }
    }
    //-- 권한삭제
    var delAuthMng = function(authCd){

        if((authCd != null && authCd != "")){
            confirm("삭제 하시면 지정된 사용자,메뉴 정보도 같이 삭제 됩니다.\n 삭제 하시겠습니까?",function(r){
                if(r){
                    var data = {};
                    data.authCd = authCd;
                    paragonCmm.callAjax(paragonCmm.getUrl("/viself/auth/AuthMng/delete/json"),data, function(json){
                        if(json.errYn === "E"){
                            //-- 오류처리
                            alert(json.msg);
                            return false;
                        }
                        loadAuths("CMM_SYS");
                    });
                }
            });
        }
    }
    var treeDataFilter = function(json){
        var data = {};
        var parent = [];
        var child = {};

        if(json.hasOwnProperty("data") && json.data.length > 0){
            data["total"] = json.data[0].totCnt;
            data["rows"] = json.data;

            $(json.data).each(function(i, d){
                if(d.parentMenuId != null && d.parentMenuId != ""){
                    if(child.hasOwnProperty(d.parentMenuId)){
                        child[d.parentMenuId].push(d);
                    }else{
                        child[d.parentMenuId] = [];
                        child[d.parentMenuId].push(d);
                    }
                }else{
                    parent.push(d);
                }
            });
            //--GetParent

        }else if(json.hasOwnProperty("data") && json.data.length == 0){
            data["total"] = 0;
            data["rows"] = [];
        }else{
            data = json;
        }
        var data = [];
        $(parent).each(function(i, d){
            var prnt = d.menuId;
            if(child.hasOwnProperty(prnt)){
                d.children = child[prnt];
            }
            data.push(d);
        });
        return data;
    }
    //-- 권한 메뉴 조회
    var loadAuthMenu = function(){

        var authCd = $("input:radio[name='authCd']:checked").val();
        if(authCd != null && authCd != ""){
            var data = {};
            data.authCd = authCd;// viself.auth.AuthMenu
            data.useYn = "1";
            $('#AuthMenuTable').treegrid({
                url:paragonCmm.getUrl('/viself/auth/AuthMenu/authList/json'),
                idField:'menuId',
                treeField:'langCd',
                checkbox: true,
                cascadeCheck:true,
                method:"post",
                loadFilter:treeDataFilter,
                queryParams:data,
                onLoadSuccess:function(row, data){
                    $(data).each(function(i, e){
                        $("#AuthMenuTable").treegrid('uncheckNode',e.menuId);
                        if(e.hasOwnProperty("children")){
                            $(e.children).each(function(j,d){
                                if(d.alwCd != null && d.alwCd != ""){
                                    $("#AuthMenuTable").treegrid('checkNode',d.menuId);
                                }else{
                                    $("#AuthMenuTable").treegrid('uncheckNode',d.menuId);
                                }
                            });
                        }else{
                            if(e.alwCd != null && e.alwCd != ""){
                                $("#AuthMenuTable").treegrid('checkNode',e.menuId);
                            }else{
                                $("#AuthMenuTable").treegrid('uncheckNode',e.menuId);
                            }
                        }
                    });
                }
            });
        }
    }
    //-- 권한 메뉴 저장
    var saveAuthMenu = function(){
        var authCd = $("input:radio[name='authCd']:checked").val();
        if(authCd != null && authCd != ""){
            var insData = {};
            var row = $("#AuthMenuTable").treegrid('getCheckedNodes');
            var indeterminateRow = $("#AuthMenuTable").treegrid('getCheckedNodes','indeterminate');

            $(indeterminateRow).each(function(i,e){
                row.push(e);
            });

            var list = [];
            $(row).each(function(i, d){
                d.alwCd = "ALLOW";
                list.push(d);
            });
            insData.authCd = authCd;
            insData["list"] = list;
            paragonCmm.callAjax(paragonCmm.getUrl("/viself/auth/AuthMenu/insert/json"),insData, function(insJson){
                if(insJson.errYn === "E"){
                    //-- 오류처리
                    alert(insJson.msg);
                    return false;
                }
                loadAuthMenu();
            });
        }
    }
    //-- 권한 메뉴 삭제
    var delAuthMenu = function(authCd){

        authCd = ((authCd == undefined || authCd == "") ? $("input:radio[name='authCd']:checked").val() : authCd);
        if((authCd != null && authCd != "") || (loginId != null && loginId != "")){
            var data = {};
            data.authCd = authCd;
            paragonCmm.callAjax(paragonCmm.getUrl("/viself/auth/AuthMenu/delete/json"),data, function(json){
                if(json.errYn === "E"){
                    //-- 오류처리
                    alert(json.msg);
                    return false;
                }
                loadAuthMenu();
            });
        }
    }
    //-- 권한 사용자 조회
    var loadAuthMember = function(){

        var authCd = $("input:radio[name='authCd']:checked").val();
        if(authCd != null && authCd != ""){
            $("#selectUserTbody").html(""); // 초기화
            var data = {};
            data.authCd = authCd;//
            paragonCmm.callAjax(paragonCmm.getUrl("/viself/auth/AuthMember/list/json"),data, function(json){
                if(json.errYn === "E"){
                    //-- 오류처리
                    alert(json.msg);
                    return false;
                }
                $(json.data).each(function(i, d){
                    var $tr = $("<tr>").data("id", d.mbrId);
                    var $td = $("<td>");
                    var $label = $("<label style='margin:0px;'>");
                    var $button = $("<button class='close' aria-hidden='true' data-dismiss='alert' type='button'>×</button>");
                    $label.append(" "+d.userNm);
                    $td.append($label);
                    $td.append($button);
                    $tr.append($td);
                    $("#selectUserTbody").append($tr);
                    $($button).on("click",function(){
                        delAuthMember(authCd, d.mbrId);
                    });
                });
            });
        }
    }
    //-- 권한 사용자 추가
    var addAuthMember = function(loginId, dspNmKo){
        var authCd = $("input:radio[name='authCd']:checked").val();
        if((authCd != null && authCd != "") && (loginId != null && loginId != "")){
            var data = {};
            data.authCd = authCd;
            data.mbrTpCd = "USER";
            data.mbrId = loginId;
            data.userNm = dspNmKo;

            paragonCmm.callAjax(paragonCmm.getUrl("/viself/auth/AuthMember/insertAuthUser/json"),data, function(json){        //-- 추가
                if(json.errYn === "E"){
                    //-- 오류처리
                    alert(json.msg);
                    return false;
                }
                loadAuthMember();
                $("#searchUserKeyword").combobox("clear");
            });
        }
    }
    //-- 권한 사용자 삭제
    var delAuthMember = function(authCd, loginId){

        authCd = ((authCd == undefined || authCd == "") ? $("input:radio[name='authCd']:checked").val() : authCd);
        if((authCd != null && authCd != "") || (loginId != null && loginId != "")){
            var data = {};
            data.authCd = authCd;

            if(typeof loginId === "string" && loginId != ""){
                data.mbrId = loginId;
            }
            paragonCmm.callAjax(paragonCmm.getUrl("/viself/auth/AuthMember/delete/json"),data, function(json){
                if(json.errYn === "E"){
                    //-- 오류처리
                    alert(json.msg);
                    return false;
                }
                loadAuthMember();
            });
        }
    }
    //-- 권한 사용자추가 자동완성
    var getAutoAuthMember = function(){
        var selRecord = {};
        $('#searchUserKeyword').combobox({
            width:300,
            mode: 'remote',
            valueField: 'value',
            textField: 'label',
            prompt:paragonCmm.getLang("M.소속_또는_이름"),
            onSelect:function(record){
                if(!jQuery.isEmptyObject(selRecord)){
                    selRecord = {}
                }
                selRecord = record;
            },
            onHidePanel:function(){
                addAuthMember(selRecord.data.loginId,selRecord.data.dspNmKo);
            },
            loader: function(param, succ){
                if (!param.q){return;}
                $.ajax({
                    url: paragonCmm.getUrl("/paragon/hr/HrMng/list/json"),
                    data: {dspNmKo:param.q},
                    dataType: 'json',
                    success: function(data){
                        var rows = $.map(data.data, function(item){
                            return {
                                value:item.dspNmKo,
                                label:item.dspNmKo,
                                data:item
                            };
                        });
                        succ(rows)
                    }
                })
            }
        });
    }
    var attachEvents = function(){
        $("#saveAuthMenuBtn").off();
        $("#saveAuthMenuBtn").on("click",function(){
            saveAuthMenu();
        });
        $("#addAuthBtn").off();
        $("#addAuthBtn").on("click",function(){
            addAuthMng();
        });
        $("#uptAuthBtn").off();
        $("#uptAuthBtn").on("click",function(){
            //-- 권한수정처리
            uptAuthMng();
        });
        $("#resAuthBtn").off();
        $("#resAuthBtn").on("click",function(){
            //-- 등록모드변경
            $("#addAuthCd").val("").removeAttr("readonly");
            $("#addAuthNm").val("");
            $("#addAuthBtn").show();
            $("#resAuthBtn").hide();
            $("#uptAuthBtn").hide();
        });
        getAutoAuthMember();    //-- 사용자추가 자동완성
    }


    var loadForm = function(){
        loadAuths("CMM_SYS");
    }
    var init = function () {
        attachEvents();
        loadForm();
    };
    return{
        init : init
    }
}
var auth = new Auth();
auth.init();