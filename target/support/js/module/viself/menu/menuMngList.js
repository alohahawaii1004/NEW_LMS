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
/**
 * <b>Description</b>
 * <pre>
 *    메뉴 관리 화면
 * </pre>
 * @author 강세원
 */
"use strict";
function Menu() {

	var TREE_URL 		= paragonCmm.getUrl("/viself/menu/MenuMng/allList/json");
	var WRITE_URL 		= paragonCmm.getUrl("/viself/menu/menuMngWrite.include");
	var treeAreaNm 		= "menuMngTreeArea";
	var $treeAreaId 	= $("#menuMngTreeArea");
	var $moduleModalId 	= $("#menuMngPop1");
	var $uuidPathId 	= $("#menuMngUuidPath");
	var $schValueId 	= $("#menuMngSchValue");
	var $schForm 		= $("#menuMngSchForm");
	var $mngFormId 		= $("#menuMngform1");
	var $menuId 		= $("#menuId");
	var $parentMenuId 	= $("#parentMenuId");
	var $setCaptionId 	= $("#txtNamePath");
	var $btnSchId 		= $("#menuSearchBtn");
	var $btnOneId 		= $("#menuMnglevelOne");
	var $btnModiId 		= $("#menuMngbtnModify");
	var $btnModiChildId = $("#menuMngbtnModify_child");

    var drawTree = function(){
		var arrDEPTH = [];
		if($uuidPathId.val() != ""){
			arrDEPTH = $uuidPathId.val().split("≫");
		}
		//tree 불러오기
		paragonCmm.treeInitSet(TREE_URL,"MENU",JSON.stringify(arrDEPTH),'');

		//그려넣을 id, 루트id, 최종
	    paragonCmm.treeDrawChild(treeAreaNm,null);
	}
    var doSearch = function(isCheckEnter) {
		if (isCheckEnter && event.keyCode != 13) {
			return;
		}
		if($schValueId.val() != ""){
			$treeAreaId.html("");
			paragonCmm.treeDrawChild(treeAreaNm,"", paragonCmm.getSearchQueryParams(document.menuMngSchForm));
		}else{
			$treeAreaId.html("");
			paragonCmm.treeDrawChild(treeAreaNm,"null","");
		}
	}
    var selectNode = function(menu_id){
		var codGrp = $("#"+menu_id);
		var obj = codGrp.children("input:hidden");
		var jsonData = "";
		$(obj).each(function(i, d){
			if(i > 0){jsonData+=",";}
			jsonData+= $(this).attr("name")+":\""+$(this).val()+"\"";
			$("#"+$(this).attr("name") , $mngFormId).text($(this).val())

			if("menuId" == $(this).attr("name")){
				$("#"+$(this).attr("name") , $mngFormId).val($(this).val())
			}

			if("parentMenuId" == $(this).attr("name")){
				$("#"+$(this).attr("name") , $mngFormId).val($(this).val())
			}
			if("langCd" == $(this).attr("name")){
				$setCaptionId.text(paragonCmm.getLang($(this).val()));
			}
		});

		//-- 시스템 전용 코드일 경우 수정/삭제 버튼 디스플레이 처리
		$btnOneId.hide();
		$btnModiId.show();
		$btnModiChildId.show();
	};

	var goModify = function(frm,updateMode) {

		if ($menuId.val() == "" && updateMode != "ONE") {
			alert(paragonCmm.getLang("M.ALERT_SELECT","수정할 메뉴정보"));
			return;
		}
		if("ONE" == updateMode){
			updateMode = "CHILD"
			$parentMenuId.val();
		}
		var params = $(frm).serializeJSON();
		params.updateMode = updateMode;
		params.langCd = "L.시스템메뉴관리";
		main.movePage(WRITE_URL,params );
	}
	var attchmentEvent = function(){

		$schValueId.off();
		$schValueId.on("keyup",function(){
    		doSearch(true);
    		return false;
    	});
		$btnSchId.off();
		$btnSchId.on("click", function(){
    		doSearch();
    		return false;
    	});
		$btnOneId.off();
		$btnOneId.on("click", function(){
    		goModify($mngFormId,'ONE');
    		return false;
    	});
		$btnModiId.off();
		$btnModiId.on("click", function(){
    		goModify($mngFormId,'');
    		return false;
    	});
		$btnModiChildId.off();
		$btnModiChildId.on("click", function(){
    		goModify($mngFormId,'CHILD');
    		return false;
    	});
	}
	var init = function () {
		attchmentEvent();
		drawTree();
    };
	return{
    	init : init,
    	selectNode:selectNode
    }
}
var MENU = new Menu();
	$(document).ready(function () {

	    console.info("[Loading Module: 메뉴관리].....................");

	    MENU.init();
	}());
