/*
 * @(#)aprvStats.js     2021-06-24(024) 오전 8:34
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
 * IMS 결재함(현황)
 * @returns {{init: init}}
 * @constructor
 */
var ProcCntPop = function(){

    /**
     * 결재현황 view model
     */
    var model = (function(){

    	var columns = [{
            field: "dlMasUid", title: "솔루션", hidden: true
        }, {
        	field: "solMasUId", title: "솔루션", hidden:true
        }, {
        	field: "dlProgCd", title: "상태코드", hidden:true
        }, {
        	field: "mngNo", title: paragonCmm.getLang("L.심의번호"), align:"center", halign:"center", width: "10%"
        }, {
            field: "dlTit", title: paragonCmm.getLang("L.제목"), align:"center", halign:"center", formatter: filterXSS, width: "25%"
        }, {
        	field: "dlPpNms", title: paragonCmm.getLang("L.심의위원"), align:"center", halign:"center", formatter: filterXSS, width: "20%"
        }, {
        	field: "dlPrearngeDte", title: paragonCmm.getLang("L.심의예정일"), align:"center", halign:"center", formatter: filterXSS, width: "10%"
        }, {
        	field: "dlDte", title: paragonCmm.getLang("L.심의일자"), align:"center", halign:"center", formatter: filterXSS, width: "10%"
        }, {
        	field: "dlProgLangCd", title: paragonCmm.getLang("L.상태"), align:"center", halign:"center", formatter: paragonCmm.getLang, width: "10%"
        }];

    	var schStu = $("#schStu").val();

    	var DL_PROC_URL = paragonCmm.getUrl("/ips/main/Dashboard/selectDlProcList/json");
    	var URL_VIEW = "/ips/dl/dlMasView.popup";
    	var URL_TMP_VIEW = "/ips/dl/dlTmpView.popup";
        /**
         * 그리드 URL
         * @returns {*} 그리드 URL
         */
        var getGridUrl = function(){

        	return DL_PROC_URL;
        };
        var getViewUrl = function(){
        	return paragonCmm.getUrl(URL_VIEW);
        };
        var getTmpViewUrl = function(){
        	return paragonCmm.getUrl(URL_TMP_VIEW);
        };
        var getColumns = function(){
        	return columns;
        };

        return{
            getGridUrl: getGridUrl,
            getViewUrl: getViewUrl,
            getTmpViewUrl: getTmpViewUrl,
            getColumns: getColumns
        };
    })();

    var $grid = $("#procTable");

	// 사무소선택 데이터
	var $divRoot 	= $("#procSearch");
	var $searchFrm = $("#procSearchForm");
	var $cbRow 		= {}; //-- 최종선택 변리사 정보
	// 다이얼로그
    var $dialog = {};


	//리턴 데이터
	var retData = {
			extInvUid:'',
			natCd:'',
			natNm:'',
			nmKo :'',
			deptNm :'',
			position :'',
			telno :''
	};
	// 오프너 데이터
    var openerData = $divRoot.data("opener-data");

    /**
     * 그리드 높이 구하기
     * @returns {number}
     */
    var getGridHeight = function () {
        var ENURI = 250;
        var SCH_HEIGHT = 0; //$searchFrm.innerHeight();
        var windowHeight = window.innerHeight;
        return windowHeight - SCH_HEIGHT - $("header").innerHeight() - $("h3").innerHeight() - ENURI;
    };

    /**
     * 폼 설정 초기화
     */
    var initForm = function(){

    	setEvent();	//-- 이벤트 등록

    };

    /**
     * 조회 파라메터 얻기
     * @returns {*} 조회 파라메터
     */
    var getQueryParams = function(){
        return $searchFrm.serializeObject();
    };

    /**
     * 그리드 초기화
     */
    var initGrid = function(){
        $grid.datagrid({
            url: model.getGridUrl(),
            method: "post",
            queryParams: getQueryParams(),
            loadFilter: paragonCmm.easyuiLoadFilter,
            height: getGridHeight(),
            striped : true,
            fitColumns : true,
            nowrap: true,
            multiSort : true,
            remoteSort: true,
            rownumbers: true,
            singleSelect: true,
            pagination : true,
            pagePosition: "bottom",
            pageSize: (function(){
                if(getGridHeight() > 500){
                    return 20;
                }
                return 10;
            })(),
            columns:[model.getColumns()],
            onClickRow: function(index, row){
            	addInventor(row);
            }
        });
    };

    var addInventor = function(row){

    	if(row.dlProgCd == 'TM'){
    		var imsiForm = $("<form method='POST'>").attr("action",model.getTmpViewUrl());
            imsiForm.append($("<input type='hidden' name='solMasUid'>").val(row.solMasUid));
            imsiForm.append($("<input type='hidden' name='openerData'>").val(JSON.stringify(openerData)));
            imsiForm.append($("<input type='hidden' name='_csrf'>").val($("meta[name='_csrf']").attr("content")));
            paragonCmm.openWindow("", "1174", "750", "POP_VIEW_"+row.solMasUid, "yes", "yes", "");
            imsiForm.attr("target","POP_VIEW_"+row.solMasUid);
            imsiForm.appendTo("body");
            imsiForm.submit();
            imsiForm.remove();
    	}else{
    		var imsiForm = $("<form method='POST'>").attr("action",model.getViewUrl());
            imsiForm.append($("<input type='hidden' name='solMasUid'>").val(row.solMasUid));
            imsiForm.append($("<input type='hidden' name='openerData'>").val(JSON.stringify(openerData)));
            imsiForm.append($("<input type='hidden' name='_csrf'>").val($("meta[name='_csrf']").attr("content")));
            paragonCmm.openWindow("", "1174", "750", "POP_VIEW_"+row.solMasUid, "yes", "yes", "");
            imsiForm.attr("target","POP_VIEW_"+row.solMasUid);
            imsiForm.appendTo("body");
            imsiForm.submit();
            imsiForm.remove();
    	}
	}

    /**
     * 조회
     */
    var search = function(){
        $grid.datagrid("load", getQueryParams());
    };

	function formatLink(val, row, index) {
		val = 'cursor:pointer;'
		return val;
	}

    /**
     * 이벤트 설정
     */
    var setEvent = function(){
    	$("#procSchBtn").off();	//-- 이벤트 초기화
    	$("#procSchBtn").on("click",function(){
			doSearch();
		});
		$("input:text", $searchFrm).off();
		$("input:text", $searchFrm).on("keyup",function(){
			doSearch(true);
		});
		$("#procInitBtn").off();			//-- 초기화버튼
		$("#procInitBtn").on("click",function(){
			$searchFrm[0].reset();
            return false;
		});


		$("#confirmBtn").off();
		$("#confirmBtn").on("click",function(){
			var row = [];
			var spans = $("span", $selArea);

			$(spans).each(function(i, e){
				var json ={};
				$(e).data("row").userNo = $(e).data("row").extInvUid;
				$(e).data("row").invLoginId = $(e).data("row").extInvUid;
				$(e).data("row").dspNmKo = $(e).data("row").nmKo;
				$(e).data("row").nmEn = $(e).data("row").nmEn;
				$(e).data("row").natCd = $(e).data("row").natCd;
				$(e).data("row").natNm = $(e).data("row").natNm;
				$(e).data("row").invDeptNm = $(e).data("row").deptNm;
				$(e).data("row").inOut = 'N';
				row.push($(e).data("row"));

			});
			if($(row).length == 0){
				alert(paragonCmm.getLang('M.선택건없음'));
				return false;
			}
			var cbFnc = $divRoot.data("callback");
	        if(typeof cbFnc === "function"){

	        	cbFnc(row);
	            closeWindow();
	        }else{
	            // TODO opener.cbFnc ....
	        }

		});

    };

    var doSearch = function(isCheckEnter){
		if (isCheckEnter && event.keyCode != 13) {
	        return;
	    }
		$grid.datagrid("load", getQueryParams());
	}


    //-- 검색 모달 닫기
	var closeWindow = function(){
        var cbFncClose = $divRoot.data("callback-close");
        if(typeof cbFncClose === "function"){
            // 모달일 경우 닫기
            cbFncClose();
        }else{
            // 팝업일 경우 닫기
            self.close();
        }
    };

    /**
     * 현황초기화
     */
    var init = function(){

        // 폼초기화
        initForm();

        // 그리드 초기화
        initGrid();

        // 이벤트 설정
        setEvent();

        paragonCmm.convertLang($(document));

    };

    return {
        init: init,
        search:search
    };
};


$(document).ready(function(){
	var procCntPop = new ProcCntPop();
	procCntPop.init();
});

