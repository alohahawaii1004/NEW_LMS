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
var TtProcCntPop = function(){

    /**
     * 결재현황 view model
     */
    var model = (function(){

    	var columns = [{
            field: "solMasUid", title: "솔루션", hidden: true
        }, {
            field: "docUid", title: "문서UUID", hidden:true
        }, {
        	field: "stuCd", title: "상태코드", hidden:true
        }, {
        	field: "stuDtl", title: "상세상태", hidden:true
        }, {
        	field: "mngNo", title: paragonCmm.getLang("L.관리번호"), align:"center", halign:"center", width: "15%"
        }, {
            field: "ttTit", title:paragonCmm.getLang("L.기술명"), styler:formatLink, halign:"center", formatter: filterXSS, width: "30%", sortable:true
        }, {
        	field: "ttTpLangCd", title: paragonCmm.getLang("L.기술이전유형명"), align:"center", halign:"center", formatter: paragonCmm.getLang, width: "10%"
        }, {
        	field: "ttTpLangCd", title: paragonCmm.getLang("L.권리구분"), align:"center", halign:"center", formatter: paragonCmm.getLang, width: "10%", sortable:true
        }, {
        	field: "docNmLangCd", title: paragonCmm.getLang("L.상태"), align:"center", halign:"center", formatter: paragonCmm.getLang, width: "10%"
        }, {
        	field: "chgNm", title: paragonCmm.getLang("L.담당자명"), align:"center", halign:"center", formatter: filterXSS, width: "10%", sortable:true
        }, {
        	field: "chgTell", title: paragonCmm.getLang("L.담당자연락처"), align:"center", halign:"center", formatter: filterXSS, width: "10%"
        }, {
            field: "regDte", title: paragonCmm.getLang("L.등록일"), align:"center", halign:"center", formatter: paragonCmm.getLang, width: "10%", sortable:true
        }];

    	var schStu = $("#schStu").val();

    	var TT_PROC_URL = paragonCmm.getUrl("/ips/main/Dashboard/selectTtProcList/json");
        /**
         * 그리드 URL
         * @returns {*} 그리드 URL
         */
        var getGridUrl = function(){
    		return TT_PROC_URL;
        };
        var getColumns = function(){
        	return columns;
        };

        return{
            getGridUrl: getGridUrl,
            getColumns: getColumns
        };
    })();

    var $grid = $("#ttProcTable");

	// 사무소선택 데이터
	var $divRoot 	= $("#ttProcSearch");
	var $searchFrm = $("#ttProcSearchForm");
	var $cbRow 		= {}; //-- 최종선택 변리사 정보
	// 다이얼로그
    var $dialog = {};

    /**
     * 모달 다이얼로그 초기화
     */
    var initModal = function(){
        $dialog = document.getElementById("regUserPop1");
        if(!$dialog){
            var DIALOG_HTML = "<div id='regUserPop1'/>"
            $("body").append(DIALOG_HTML);
        }
        $dialog = $("#regUserPop1");
        $dialog.html("");
    };

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

    	var openerData = {
				isNew : "FALSE",
				solMasUid : row.solMasUid,
				dashBoardTodoChk : "Y"
			};

		var data = {};
    	data.solMasUid = row.solMasUid;
    	data.stuCd = row.stuCd;
    	data.stuDtl = row.stuDtl;
    	data.openerData = openerData;
    	paragonIps.goPopupIndex(data);
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
    	$("#ttProcSchBtn").off();	//-- 이벤트 초기화
    	$("#ttProcSchBtn").on("click",function(){
			doSearch();
		});
		$("input:text", $searchFrm).off();
		$("input:text", $searchFrm).on("keyup",function(){
			doSearch(true);
		});
		$("#ttProcInitBtn").off();			//-- 초기화버튼
		$("#ttProcInitBtn").on("click",function(){
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

    var goWrite = function(row){

    	var cbFnc = function(data){
    		//-- 테이블 초기화
    		//$tbody.html("");
    		//-- 콜백 함수
    		$tmpl.tmpl(data).appendTo($tbody);
    	}
    	var arrSelUser = [];
		$("input[name='_pati_loginId']").each(function(i,d){
			arrSelUser.push($(d).val());
		});

		var param = {
				openerData: JSON.stringify({
    				"cud":'C',
    				"gridId":"ttProcTable",
    				"srcFormId":"ttProcSearchForm"
    			})
		};
		/*if(typeof row === 'object'){

			param = {
	    			openerData: JSON.stringify({
	    				"extInvUid":row.extInvUid,
	    				"cud":'U',
	    				"grid":$grid,
	    				"srcForm":"procSearchForm"
	    			})
	    	};
		}*/

//    	param.natCd = $("#_pati_hopeNatCd", $particleRoot).val();
//    	param.multiYn = "Y"; //-- 다중체크 여부

    	initModal();

    	$dialog.dialog({
            iconCls: "fa fa-info-circle",
            title: paragonCmm.getLang("L.외부발명자등록"),
            // top: 100,
            width:700,
		    height:450,
            cache: false,
            modal: true,
            href: paragonCmm.getUrl("/ips/ext/extInvWrt.modal"),
            method: "post",
            queryParams: param,
            onClose: function () {
                // HACK 이벤트를 지우지 않으면 계속 쌓인다.
                // resetEvent();
                $dialog.dialog("destroy");
            },
            onLoad: function () {
                // include 페이지 최상위 DOM id
                var $DIALOG_ROOT_NODE = $("#extInvWrtDiv");
                // close callback function
                var close = function () {
                    $dialog.dialog("close");
                };

                // 1. 가운데에서 뜨기
                $dialog.dialog("center");

                // 2. 다국어 설정
                paragonCmm.convertLang($dialog);

                // 3. callback function 설정
                $DIALOG_ROOT_NODE.data("callback", cbFnc);

                // 4. callback function(close) 설정
                $DIALOG_ROOT_NODE.data("callback-close", close);
            }
        });

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

      /*//--기본컬럼 지정
		var initVal = "extInvUid,natCd,natNm,nmKo,nmEn,deptNm,position,telno";
		var colsParam = {
				"allColumns" : model.getColumns(),
				"initVal" : initVal,
				"selectCol" : initVal,
				"actionCd" : "IPS_IP",
				"gridID" : $grid,
				"jsonURL" : model.getGridUrl(),
				"queryParams": getQueryParams()

		}
		var colsSetup = new ColsSetup(colsParam);
		colsSetup.loadCols(initVal);*/

		/*htmlUtils.setColsProperties(colsParam);
		htmlUtils.loadCols(initVal);*/

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
	var ttProcCntPop = new TtProcCntPop();
	ttProcCntPop.init();
});

