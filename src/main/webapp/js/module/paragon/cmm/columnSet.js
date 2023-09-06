/*
 * @(#)columnSet.js     2021-10-19(024) 오후 14:13
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
"use strict";

/**
 * 동적 컬럼 지정
 * @returns {{init: init}}
 * @constructor
 */

//-- 공통 컬럼 지정
var ColumnSet = function(){

	var colsProp =  {};
	/**
	 * 컬럼지정 기본 셋팅
	 */
	var setProperties = function(){

		$("li", "#source").css({'padding': '5px 5px 5px 5px','border': '1px solid #cdcdcd','min-width': '140px','margin':'0px 0px 10px 10px','background':'#ffffff','cursor':'pointer', 'list-style-type':'none', 'float':'left'})
		$("li", "#source").off();
		$("li", "#source").on('click',function(e){
			$("li", "#source").show();
			$("input[name='schCols']", $("#colsInfo")).val("");
			if($(this).attr("class") == "cols"){
				var clone =  $(this).clone();
				clone.text($(this).text());
				clone.css({
						"background-color":"#468fb5"
						,"font-weight":"bold"
						,"color":"#fff"
					});

				$("#target").append(clone);

				$(this).css({"background-color":"#8edad5","color":"#404040"});
				$(this).attr("class","chkCols");
			}else{
				var id= $(this).attr("id");
				$("#"+id,"#target").remove();

				$(this).css({"background-color":"#fff","color":"#404040"});
				$(this).attr("class","cols");
			}
			e.preventDefault();
		});

		$('#target').sortable();
	}
	/**
	 * 컬럼 읽어 들임 및 그리드 실행 URL, queryParams 를 여기서 지정한다. 기존코드에서는 제거
	 */
	var loadCols = function(){


		var arrData = [];
		var cols = colsProp.selectCols;
		if(typeof colsProp.selectCols != "string"){
			cols = colsProp.initVal;
		}
		//-- hidden/checkbox 선입력
		$(colsProp["allColumns"]).each(function(i, d){
			if(d.hasOwnProperty("hidden")){
				arrData.push(d);
			}
			if(d.hasOwnProperty("checkbox")){
				arrData.push(d);
			}
		});
		var arrCols = cols.split(",");
		$(arrCols).each(function(j){
			//-- 필드 입력
			$(colsProp["allColumns"]).each(function(i, d){
				if(arrCols[j] == d.field){
					arrData.push(d);
				}
			});
		});

		if(arrData.length > 1){
			var data = getQueryParams();
			data.presetData = colsProp.selectCols;
			$(colsProp.gridObj).datagrid({
				url:colsProp["jsonURL"],
				columns:[arrData],
				queryParams:data
			});
		}
	}

	var drawCols = function(){
		var source = $("#source");
		source.empty();
		$('#target').empty();
		var allColumns = colsProp.allColumns;
		$(allColumns).each(function(i, d){
			if(!d.hasOwnProperty("hidden") && !d.hasOwnProperty("checkbox")){
				var $li = $("<li id='"+d.field+"' class='cols' data-text='"+d.title+"'>");
				$li.data("options", JSON.stringify(d));
				$li.html(d.title);
				source.append($li);
			}

		});

		setProperties();
	}

	/**
	 * 개인 컬럼 지정
	 * @param defaultUser 기본 사용자
	 * @param toObj 텍스트 input
	 */
	var initCols = function(type){
		if(type == "init"){
			drawCols();
			var arr = colsProp["initVal"].split(",");
			//-- 기존 선택된 것들 초기화
			$("input:checkbox:checked", "#source").each(function(i, e){
				$(e).parent().trigger('click');
			});
			for(var i = 0; i < arr.length; i++){
				var id = arr[i];
				$("#"+id, "#source").trigger('click');
			}

		}else if(type == "allSelect"){
            $(".chkCols", "#source").trigger('click');
            $(".cols", "#source").trigger('click');
        }else if(type == "allUnselect"){
            $(".chkCols", "#source").trigger('click');
        }else{

			var preData = colsProp.selectCols ;

			if(typeof preData != "string"){
				preData = colsProp.initVal;
			}

			var arr = preData.split(",");

			for(var i = 0; i < arr.length; i++){
				var id = arr[i];
				$("#"+id, "#source").trigger('click');
			}
		}

        $("li", "#target").off();
        $("li", "#target").on('dblclick', function(e){
            var id= $(this).attr("id");
            $("#"+id, "#source").trigger('click');
            e.preventDefault();
        });
	};
	/**
	 * 컬럼 저장처리
	 */
	var preSetSave = function(){

		var preData = "";
		$("li", "#target").each(function(i,e){
			if(i > 0) preData+=",";
			preData += $(e).attr("id");
		});

		paragonCmm.setCookie(colsProp["actionCd"]+"_COL", preData, 365);
		colsProp.selectCols = preData;
		loadCols();
//		var data = {};
//		data.presetUid = paragonCmm.getRandomUUID();
//		data.cud = "C";
//		data.presetTit = "리스트";
//		data.presetContent = "";
//		data.presetData = preData;
//		data.presetTpCd = colsProp["actionCd"]+"_COL";
//
//		paragonCmm.callAjax(paragonCmm.getUrl("/paragon/cmm/savePreset"),data, function(json){
//			colsProp.selectCols = preData;
//			loadCols();
//		});

	}
	/**
	 * 컬럼지정 닫기
	 */
	var closeCols = function(){ //-- 컬럼닫기
		$("#user_cols").window('close');
	}
	/**
	 * 컬럼 사용자 변경 열기
	 */
	var openCols = function(){ //-- 컬럼열기
		var otp = {};
	   	otp.cbFnc = function(data){

	   	}
	   	otp.reqParam = {"allColumns":colsProp.allColumns};
	   	otp.title = paragonCmm.getLang("L.컬럼지정");
	   	otp.width = 1280;
	   	otp.height = 650;
	   	otp.maximizable = true;	//-- 전체 크기 여부
	   	otp.href = paragonCmm.getUrl("/paragon/cmm/colsInfo.modal");
	   	otp.modalId   = "tep"+colsProp.actionCd;
	   	otp.contentId = "colsInfo";

	   	htmlUtils.openModal(otp);
	}
	//-- 그리드 로드
	var init = function(colsParam){

		colsProp = $.extend({}, colsParam);
		console.log(colsProp);
		var presetData = paragonCmm.getCookie(colsProp["actionCd"]+"_COL");
		if(typeof presetData === "string" && presetData != ""){
			colsProp.selectCols = presetData;
		}
		loadCols();

//		var data  = { "presetTpCd"  : colsProp["actionCd"]+"_COL" };
//		paragonCmm.callAjax(paragonCmm.getUrl("/paragon/cmm/preset/PresetInfo/myList/json"),data, function(json){
//			if($(json.data).length > 0){
//				var preData = json.data[0].presetData;
//				colsProp.selectCols = preData;
//				loadCols();
//			}else{
//				loadCols();
//			}
//
//		});
	}
	//-- 지정 컬럼데이터
	var getSelectCols = function(){
		var cols = colsProp.selectCols;
		if(typeof cols == "string" && cols == ""){
			cols = colsProp.initVal;
		}
		return cols;
	}
	//-- 조회데이터 추출
	var getQueryParams = function(){
    	var data = colsProp.searchFormObj.serializeObject();
    	data.presetData = getSelectCols();

        return data;
    };

    //-- 조회
    var doSearch = function(isCheckEnter){

    	if (typeof isCheckEnter === "boolean" && isCheckEnter && event.keyCode != 13) {
			return;
		}

		$(colsProp.gridObj).datagrid("load", getQueryParams());
    };

    //-- 엑셀다운
    var doExcel = function(tit){

    	var data = getQueryParams();
    	paragonCmm.callAjax(colsProp["jsonURL"],data, function(json){
			if(json.errYn === "E"){
				//-- 오류처리
				alert(json.msg);
				return false;
			}
			var date = new Date();
			var year 	= date.getFullYear();
			var month 	= date.getMonth()+1;
			var date	= date.getDate();
			var preTxt = year+"-"+month+"-"+date

			var rows = paragonCmm.easyuiLoadFilter(json);

			$(colsProp.gridObj).datagrid('toExcel', {
				filename: tit+"_"+preTxt+'.xlsx',
				rows: rows.rows,
				worksheet: 'Worksheet'
			});

			columnSet.doSearch();
		});

    };

	return{
		init:init,
		openCols:openCols,
		closeCols:closeCols,
		loadCols:loadCols,
		initCols:initCols,
		drawCols:drawCols,
		preSetSave:preSetSave,
		doSearch:doSearch,
		doExcel:doExcel
	}
}
