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
var InvInfo = function(){

	var SOL_MAS_UID = $("#solMasUid").val();
	var GET_INFO_URL = paragonCmm.getUrl('/paragon/cmm/invInfolist');

	var $tmpl = $("#invInfoTmpl");
	var $tBody = $("#invInfoBody");

    /**
     * 폼 설정 초기화
     */
    var initForm = function(){

    	setEvent();	//-- 이벤트 등록

    };

    var loadData = function(){

    	var msg = paragonCmm.getLang("M.개인정보_접근확인");

		confirm(msg,function(r){

			if(r){

				var data = {};
		    	data.solMasUid = SOL_MAS_UID;
		    	paragonCmm.callAjax(GET_INFO_URL,data,function(json){
		    		if(json.errYn === "E"){
						//-- 오류처리
						alert(json.msg);
						return false;
					}else{
						$(json.data).each(function(i, e){

							var ctzNo = e.citizenNo;
							var getCitizenNo = function(ctzNo){	//-- 주민번호 자릿수 만들기

								if(typeof ctzNo === 'string'){
									if(ctzNo != null && ctzNo != ''){
										var rtnTxt = ctzNo.substring(0, 6) +"-" + ctzNo.substring(6, ctzNo.length) +"******";
										return rtnTxt;
									}
								}else{
									return ctzNo;
								}
							}
							e.citizenNo = getCitizenNo(ctzNo);
							$tmpl.tmpl(e).appendTo($tBody);
						})
					}
		    	})

			}else{
				self.close();
			}

		});



    };

    /**
     * 조회 파라메터 얻기
     * @returns {*} 조회 파라메터
     */
    var getQueryParams = function(){
        return $searchFrm.serializeObject();
    };

    /**
     * 이벤트 설정
     */
    var setEvent = function(){
    	$("#closeBtn").off();	//-- 이벤트 초기화
    	$("#closeBtn").on("click",function(){
    		self.close();
		});

    };

    /**
     * 현황초기화
     */
    var init = function(){

        // 폼초기화
        initForm();
        loadData();
        // 이벤트 설정
        setEvent();

        paragonCmm.convertLang($(document));

    };

    return {
        init: init
    };
};


$(document).ready(function(){
	var invInfo = new InvInfo();
	invInfo.init();
});

