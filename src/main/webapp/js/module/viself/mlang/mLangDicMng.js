/*
 * @(#)mLangDicMng.js    
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
 * jquery serialize 를 json 형태로 반환
 * 출처: https://cofs.tistory.com/184 [CofS]
 * @returns {*}
 */
jQuery.fn.serializeObject = function () {
    var obj = null;
    try {
        if (this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
            var arr = this.serializeArray();
            if (arr) {
                obj = {};
                jQuery.each(arr, function () {
                    obj[this.name] = this.value;
                });
            }//if ( arr ) {
        }
    } catch (e) {
        alert(e.message);
    } finally {
    }

    return obj;
};
/**
 * <b>Description</b>
 * <pre>
 *    다국어 관리 화면
 * </pre>
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
$(function(){
	"use strict";
	
	console.info('[Loading: 언어사전 관리]................................');

	jayu.mLangDicMng = (function(){
		var baseModuleURL = "";
		var MODULE_NAME = "mLangDicMng";
        var config = {
			searchFrmID : MODULE_NAME + "SchFrm0",
			mLangTblID : MODULE_NAME + "List1",
			addRowBtnID : MODULE_NAME + "AddRowBtn1",
			delRowBtnID : MODULE_NAME + "DelRowBtn1",
			saveRowBtnID : MODULE_NAME + "SaveBtn1",
			excelRowBtnID : MODULE_NAME + "ExcelBtn1",
			SEARCH_URL: jayu.getName() + "/mlang/MLangDicMng/list/json",
			MODIFY_URL: jayu.getName() + "/mlang/MLangDicMng/modify/json"
		}
		// 다국어 수정 처리
		var modifyLang = function(row,index,changes){
			var UID = row["termCd"];
			$.ajax({
				url: config.MODIFY_URL,
				type: "POST",
				dataType: "json",
				data: changes
			})
			.done(function(data) {
	
				if(Object.keys(data).length > 0){
					doSearch(document.form1);
				}else{
					alert("동일한코드명이존재합니다");
					reject();
				}
		    })
		    .fail(function() {
		    });
	}
		
		//-- 다국어 그리드 로딩
		var initMLangGlist = function(){
			var data = {"total":28,"rows":[
				{"termCd":"FI-SW-01","langCd":"KO","trnslatVal":"Koi","unitcost":"10.00","useEnable":"Y","listprice":"36.50","attr1":"Large","itemid":"EST-1"},
				{"termCd":"K9-DL-01","langCd":"KO","trnslatVal":"Dalmation","unitcost":"12.00","useEnable":"Y","listprice":"18.50","attr1":"Spotted Adult Female","itemid":"EST-10"},
				{"termCd":"RP-SN-01","langCd":"KO","trnslatVal":"Rattlesnake","unitcost":"12.00","useEnable":"Y","listprice":"38.50","attr1":"Venomless","itemid":"EST-11"},
				{"termCd":"RP-SN-01","langCd":"KO","trnslatVal":"Rattlesnake","unitcost":"12.00","useEnable":"Y","listprice":"26.50","attr1":"Rattleless","itemid":"EST-12"},
				{"termCd":"RP-LI-02","langCd":"KO","trnslatVal":"Iguana","unitcost":"12.00","useEnable":"Y","listprice":"35.50","attr1":"Green Adult","itemid":"EST-13"},
				{"termCd":"FL-DSH-01","langCd":"KO","trnslatVal":"Manx","unitcost":"12.00","useEnable":"Y","listprice":"158.50","attr1":"Tailless","itemid":"EST-14"},
				{"termCd":"FL-DSH-01","langCd":"KO","trnslatVal":"Manx","unitcost":"12.00","useEnable":"Y","listprice":"83.50","attr1":"With tail","itemid":"EST-15"},
				{"termCd":"FL-DLH-02","langCd":"KO","trnslatVal":"Persian","unitcost":"12.00","useEnable":"Y","listprice":"23.50","attr1":"Adult Female","itemid":"EST-16"},
				{"termCd":"FL-DLH-02","langCd":"KO","trnslatVal":"Persian","unitcost":"12.00","useEnable":"Y","listprice":"89.50","attr1":"Adult Male","itemid":"EST-17"},
				{"termCd":"AV-CB-01","langCd":"KO","trnslatVal":"Amazon Parrot","unitcost":"92.00","useEnable":"Y","listprice":"63.50","attr1":"Adult Male","itemid":"EST-18"}
			]}
			$('#'+config.mLangTblID).datagrid({
				singleSelect:true,
			    striped:true,
			    fitColumns:false,
			    rownumbers:true,
			    multiSort:true,
			    pagination:true,
			    pageList:[10,50,100,200],
			    pagePosition:'bottom',
			    selectOnCheck:false,
			    checkOnSelect:false,
			    url:config.SEARCH_URL,
//				data:data,
			    columns:[[
			        {field:'termCd',title:'용어코드',width:120,halign:'CENTER',align:'CENTER'},
			        {field:'langCd',title:'언어코드',width:80,halign:'CENTER',align:'CENTER',editor:'text'},
			        {field:'trnslatVal',title:'번역값',width:140,halign:'CENTER',align:'LEFT',editor:'text'},
					{field:'useEnable',title:'사용여부',width:80,halign:'CENTER',align:'CENTER',editor:{
						type:'combobox',
						options:{
							valueField: 'id',
							textField: 'value',
							data: [{
								id: '01',
								value: 'Y'
							},{
								id: '02',
								value: 'N'
							}]
						}
					}},
					{field:'regDtime',title:'등록일시',width:90,halign:'CENTER',align:'CENTER'},
					{field:'regUserId',title:'등록자',width:100,halign:'CENTER',align:'CENTER'},
					{field:'uptDtime',title:'수정일시',width:90,halign:'CENTER',align:'CENTER'},
					{field:'utpUserId',title:'수정자',width:100,halign:'CENTER',align:'CENTER'}
			    ]],
				onAfterEdit:function(index,row, changes){
			    	if(Object.keys(changes).length > 0){ //객체의 키의 갯수(IE:8은 동작안함) 
			    		var message = "M.변경된값을저장하시겠습니까";
			    		confirm(message, function(r){
				    		if(r){
					    		modifyLang(row,index,changes );
				    		}else{
				    			reject()
				    		}
			    		});
			    	}
			    }
			});
			
			$('#'+config.mLangTblID).datagrid('enableCellEditing').datagrid('gotoCell', {
                index: 0,
                field: 'langCd'
            });
			
		};
		var search = function(){
			$('#'+config.mLangTblID).datagrid("load",$("#"+config.searchFrmID).serializeObject());
		}
		var addEvent = function(){
			$(".btn").on("click", function(e){
					e.preventDefault(); //-- 이벤트 동작을 멈춘다.
			});
			// 행추가 이벤트
			$("#"+config.addRowBtnID).on('click', function(){
				
			});

		};
		var loadEasyUIEdit = function(){
            jayu.loadJS("/vendor/jquery-easyui/jquery-easyui-1.9.7/jquery.edatagrid");
		}
		var init = function(){
			// easyUI그리드 수정모듈 로드
			loadEasyUIEdit();
			
			//-- 코드 TP 그리드 최초 로딩
			initMLangGlist();
						
			//-- 클릭 등 이벤트 추가
			addEvent();
			 
		};
		return {
			init : init,
			search : search
		}
	})();
	console.log(jayu.mLangDicMng);
	jayu.mLangDicMng.init();
}());
