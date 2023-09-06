/*
 * @(#)chrChg.js     2022-02-18
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
 * 담당자변경
 * @returns {{init: init}}
 * @constructor
 */

var ChrChg = function(){

	//-- 사용자 정보
	var userInfo = {};
	userInfo = paragonCmm.formMap.get("loginInfo");

    /**
     * 담당자변경 view model
     */
    var model = (function(){

    	var URL_SELECT = "/ips/ip/IpMas/list/json";
        var URL_CHR_SELECT = "/paragon/cmm/getChrUser";
		var URL_CHR_UPDATE = "/paragon/cmm/chrChgSave";

        /**
         * 그리드 URL
         * @returns {*} 그리드 URL
         */
        var getGridUrl = function(){
            return paragonCmm.getUrl(URL_SELECT);
        };

		//담당자 URL
        var getChrUrl = function(){
            return paragonCmm.getUrl(URL_CHR_SELECT);
        };

		//담당자변경 URL
        var getUpdateUrl = function(){
            return paragonCmm.getUrl(URL_CHR_UPDATE);
        };

        return{
            getGridUrl: getGridUrl,
			getChrUrl: getChrUrl,
			getUpdateUrl: getUpdateUrl

        };
    })();

    var $grid = $("#listTable");
    var $searchFrm = $("#chrChgSchForm");
    var $schBtn = $("#chrChgSchBtn");
    var $initBtn = $("#chrChgInitBtn");
    var $chgBtn = $("#chgBtn");

	//담당자선택
	var $chrNm = $("#chrNm");


//	//-- 사용자 정보
	userInfo = paragonCmm.formMap.get("loginInfo");

    /**
     * 폼 설정 초기화
     */
    var initForm = function(){

		var data = {};
    	data.srcUseYn = "Y";
    	paragonCmm.callAjax(model.getChrUrl(),data, function(json){
    		htmlUtils.initializeSelectJson($chrNm, json.data, "|-- 담당자 선택 --", "loginId", "dspNmKo");
    	});

    	setEvent();	//-- 이벤트 등록

    };

    /**
     * 그리드 초기화
     */
    var initGrid = function(){
        $grid.datagrid({
            url: model.getGridUrl(),
            method: "post",
            queryParams:$searchFrm.serializeObject(),
            loadFilter: paragonCmm.easyuiLoadFilter,
            striped : true,
            fitColumns : false,
            nowrap: true,
            multiSort : true,
            remoteSort: true,
            rownumbers: true,
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            pagination : true,
            pagePosition: "bottom",
            columns:[[
            	{field: "solMasUid", title: "solMasUid", hidden: true},
				{field: "ipTpCd", title: "tpCd", hidden:true},
                {field: "ck", title: "checkBox", align:"center", halign:"center", checkbox:true, width: "1%"},
                {field: "ipTpLangCd", title: paragonCmm.getLang("L.지재권유형"), align:"center", halign:"center", formatter: paragonCmm.getLang, width: "9%"},
                {field: "mngNo", title: paragonCmm.getLang("L.관리번호"), align:"center", halign:"center", width: "10%"},
                {field: "appNo", title: paragonCmm.getLang("L.출원번호"), align:"center", halign:"center", formatter: filterXSS, width: "10%"},
                {field: "ipRegNo", title: paragonCmm.getLang("L.등록번호"), align:"center", halign:"center", formatter: filterXSS, width: "10%"},
                {field: "titKo", title:paragonCmm.getLang("L.발명의명칭_국문"), halign:"center", formatter: filterXSS, width: "25%"},
                {field: "reqUserNm", title: paragonCmm.getLang("L.주발명자"), align:"center", halign:"center", formatter: filterXSS, width: "10%"},
                {field: "chrUserNm", title: paragonCmm.getLang("L.담당자"), align:"center", halign:"center", formatter: filterXSS, width: "13%"},
                {field: "docNmLangCd", title: paragonCmm.getLang("L.진행상태"), align:"center", halign:"center", formatter: paragonCmm.getLang, width: "12%"}
            ]],
            onDblClickRow: function(index, row){
                // openDialog(row);
                var imsiForm = $("<form method='POST'>").attr("action",paragonCmm.getUrl("/ips/ip/ipTepIndex.popup"));
                imsiForm.append($("<input type='hidden' name='solMasUid'>").val(row.solMasUid));
                imsiForm.append($("<input type='hidden' name='docUid'>").val(row.docUid));
                imsiForm.append($("<input type='hidden' name='stuCd'>").val(row.stuCd));
                imsiForm.append($("<input type='hidden' name='_csrf'>").val($("meta[name='_csrf']").attr("content")));
                paragonCmm.openWindow("", "1280", "750", "POP_VIEW_"+row.docUid, "yes", "yes", "");
                imsiForm.attr("target","POP_VIEW_"+row.docUid);
                imsiForm.appendTo("body");
                imsiForm.submit();
                imsiForm.remove();


            }
        });
    };

    /**
     * 이벤트 설정
     */
    var setEvent = function(){

	htmlUtils.getCodeSelectOpt({
	        targetId:$("#schIpTpCd"),
	        parentCd:"IP_TYPE_CD",
	        initdata:"|"+paragonCmm.getLang("L.CBO_ALL"),
	        valNm:"cdAbb",
	        txtNm:"langCd"
	    });

        $schBtn.off();		//-- 검색버튼
        $schBtn.on("click",function(){
        	doSearch();
            return false;
		});
        $("input:text", $searchFrm).off();
		$("input:text", $searchFrm).on("keyup",function(){
			doSearch(true);
		});
		$initBtn.off();			//-- 초기화버튼
		$initBtn.on("click",function(){
			$searchFrm[0].reset();
            return false;
		});
		$("input:text[data-type='search']", $searchFrm).off();			//-- 검색키워드 엔터 이벤트
		$("input:text[data-type='search']", $searchFrm).on("keyup",function(){
			doSearch(true);
            return false;
		});

		$chrNm.on("change",function(){
	    		$("[name='chrChgName']").val($chrNm.find(":selected").text());
				$("[name='chrChgId']").val($chrNm.find(":selected").val());
	    });


    	$chgBtn.off();
		$chgBtn.on("click",function(){

			//선택 건 확인
			var rows = $grid.datagrid("getChecked");
	    	if(rows.length < 1){
	    		alert(paragonCmm.getLang("M.대상선택필요"));
	    		return false;
	    	}

			//변경 담당자 확인
			if($("#chrChgName").val() == '' || $("#chrChgName").val() == 'undefinded'){
				alert("변경 담당자를 선택해주세요.");
	    		return false;
			}

			doChgSave();

		});

    };

	//담당자 변경
	var doChgSave = function(){

		var msg = "선택한 건의 " + paragonCmm.getLang("M.하시겠습니까", "L.담당자_변경");
		var rows = $grid.datagrid("getChecked");

		var chrChgNm = $("#chrChgName").val();
		var chrChgId = $("#chrChgId").val();

		var len = Object.keys(rows).length;

		for(var i = 0; i < len; i++){
			rows[i]["chrChgNm"] = chrChgNm;
			rows[i]["chrChgId"] = chrChgId;
 		}

		confirm(msg, function(r){
			if(r){
				var data = {
						list: rows
				};
				paragonCmm.callAjax(model.getUpdateUrl(), data, function(json){
						//오류
						if(json.errYn === "E"){
							alert(json.msg);
							return false;
						}
				});
				alert(paragonCmm.getLang("M.처리_되었습니다"));
			}
				doSearch();
		});

	}

    var doSearch = function(isCheckEnter){
		if (isCheckEnter && event.keyCode != 13) {
			return;
		}
		$grid.datagrid('load', $searchFrm.serializeObject());

	}

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
        init: init
    };
};


