/*
 * @(#)agentWrite.js     2021-08-10
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
 * 사무소관리
 * @returns {{init: init}}
 * @constructor
 */
var PwdChg = function(){

    var CONTEXT_PATH = "";
    var PWD_CHANGE_URL = paragonCmm.getUrl("/viself/hr/userMng/pwdChg/json");
    var ID_CHECK_URL = "/ips/agent/mng/UserMng/checkUserData/json";

    var $saveform = $("#pwdForm1");
    var $savBtn = $("#savBtn");
    var $closeBtn = $("#closeBtn");

    var validation = function(){	//-- 저장 필수체크
		if($("input:text[name='loginPwd']", $saveform).val() == ""){
			alert(paragonCmm.getLang("M.ALERT_SELECT","L.ID"),function(){
				$("input:text[name='loginPwd']", $saveform).focus();
			});
			return false;
		}

		return true;
	}

    var initGrid = function(){

    };


  //-- 로그인 세션 정보 셋팅
	var loadSession = function(){
		var data = {};
		var initAuth = function(json){
			paragonCmm.formMap.set("loginUser",json.data[0] );
		}
    	paragonCmm.callAjax(paragonCmm.getUrl("/paragon/cmm/getSessionUser"), data, initAuth)
	}

    var setEvent = function(){

    	//저장버튼
    	$savBtn.off();
        $savBtn.on("click", function(e){

        	if(!validation()){
            	return false;
            }

            $.messager.confirm("Confirm",paragonCmm.getLang("M.ALERT_SUBMIT","L.변경"), function(r){
				if(r){
					var usrData = $saveform.serializeJSON();

					paragonCmm.submitAjax(PWD_CHANGE_URL,usrData, function(json){
						if(json.errYn === "E"){
							//-- 오류처리
							alert(json.msg);
							return false;
						}else if(json.errYn == 'F'){
							alert(paragonCmm.getLang("M.동일한비밀번호"));
							return false;
						}else{
							alert(paragonCmm.getLang("M.ALERT_DONE", "L.변경"));
							$(".l-btn").on("click",function(){
								 location.reload();
								 loadSession();
								$("#pwdChgModal").window('close');
							});
						}

					});
				}
			});

        });

        $closeBtn.off();
        $closeBtn.on('click',function(){
        	$("#pwdChgModal").window('close');
        });
    };

    var loadData = function(obj){

	}

    var loadInit = function(obj){

    	var userInfo = {};
    	userInfo = paragonCmm.formMap.get("loginInfo");
    	if(userInfo.isAgc && obj.btnYn == 'Y'){
    		$closeBtn.show();
    	}else{
    		$closeBtn.hide();
    	}

    }

    var init = function(obj){
        // 이벤트 설정
        setEvent();
        loadInit(obj);
        loadData(obj);

        paragonCmm.convertLang($(document));
    };

    return{
        init: init
    };
};
var pwdChg = new PwdChg();
//agtId.init();
