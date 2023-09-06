/*
 * @(#)authUpdate.js     2022-07-20(020) 오전 10:46:49
 *
 * Copyright 2022 JaYu.space
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

var AuthInfoUpdateModel = function(){
    "use strict";
    var GENERAL_INFO_URL = paragonCmm.getUrl("/viself/mng/auth/authGeneral.include");
    var AUTH_MEMBER_URL = paragonCmm.getUrl("/viself/mng/auth/authMember.include");
    var AUTH_MENU_URL = paragonCmm.getUrl("/viself/mng/auth/authMenu.include");

    return {
        getGeneralInfoUrl: function(){
            return GENERAL_INFO_URL;
        },
        getAuthMemberUrl: function(){
            return AUTH_MEMBER_URL;
        },
        getAuthMenuUrl: function(){
            return AUTH_MENU_URL;
        }
    };
};

var AuthInfoUpdate = function(){
    "use strict";
    // attributes ================================================
    var model = new AuthInfoUpdateModel();
    var $rootNode = $("#authInfoUpdateRootLayer");
    var $sCourtTabs = $("#authInfoUpdateTabs", $rootNode);

    var opts = $.extend({
        authCd: null
    }, $rootNode.data("opener-data"));

    var resizeObj = {
        resizeFlag: false,
        t0repo: [],
        t1repo: []
    };

    var tabs = [{
        href: model.getAuthMenuUrl(),
        title: paragonCmm.getLang("L.접근가능메뉴"),
        tools: [{
            iconCls: 'icon-mini-refresh',
            handler: function () {
                refreshTab(paragonCmm.getLang("L.접근가능메뉴"));
            }
        }]
    },{
        href: model.getAuthMemberUrl(),
        title: paragonCmm.getLang("L.멤버"),
        tools: [{
            iconCls: 'icon-mini-refresh',
            handler: function () {
                refreshTab(paragonCmm.getLang("L.멤버"));
            }
        }]
    }];

    // private functions =========================================
    var refreshTab = function (title) {
        $sCourtTabs.tabs('getTab', title).panel('refresh');
    };

    var selectTab = function(title, idx){

        // 1. t1에 저장
        if(resizeObj.t1repo.indexOf(title) === -1){
            resizeObj.t1repo.push(title);
        }

        if(resizeObj.resizeFlag){
            // 2. t0 에 있으면 refresh 하고  제거
            var idx = resizeObj.t0repo.indexOf(title);
            if(idx !== -1){
                refreshTab(title);
                resizeObj.t0repo.splice(idx, 1);
            }

            // 3. t0 가 비어 있으면 플래그 리셋
            if(resizeObj.t0repo.length === 0){
                resizeObj.resizeFlag = false;
            }
        }
    };

    var resizeTab = function(){
        // 1. t1을 t0에 병합 하고 t1 비우기
        var tmpArray = resizeObj.t0repo.concat(resizeObj.t1repo);
        resizeObj.t0repo = tmpArray.filter(function(itm, idx){
            if(tmpArray.indexOf(itm) === idx){
                return itm;
            }
        });
        resizeObj.t1repo = [];

        // 2. 리사이즈 플래그 설정
        resizeObj.resizeFlag = true;
    };

    var initTabs = function(){
        // 1. tab 기본 설정
        $sCourtTabs.tabs({
            // fit: true,
            plain: true,
            border: false,
            onSelect: function(title, idx){
                // HACK 렌더링이 깨지기 때문에 리프레시
                // refreshTab(title);
                selectTab(title, idx);
            }
        });

        // 2. 탭공통 전달 파라메터
        var queryParams = {
            openerData: JSON.stringify({
                authCd: opts.authCd,
                cbKey : opts.cbKey
            }),
            authCd: opts.authCd
        };

        // 3. 기본탭 추가
        (function () {
            var vTitle = paragonCmm.getLang("L.일반사항");
            $sCourtTabs.tabs("add", {
                href: model.getGeneralInfoUrl(),
                title: vTitle,
                queryParams: queryParams,
                tools: [{
                    iconCls: 'icon-mini-refresh',
                    handler: function () {
                        refreshTab(vTitle);
                    }
                }]
            });
        })();

        tabs.forEach(function (itm) {
            itm.queryParams = queryParams;
            itm.selected = false; // 추가시 로딩 방지
            $sCourtTabs.tabs("add", itm);
        });
    };

    var initForm = function(){
        initTabs();
    };
    var loadForm = function(){};
    var setEvent = function(){
        // 리사이즈 이벤트
        $(window).on("resize", function(){
            // console.debug("resize....!!!");
            $sCourtTabs.tabs("resize");
            resizeTab();
        });
    };

    // public functions ==========================================
    var init = function(){
        initForm();
        loadForm();
        setEvent();
    };
    return {
        init: init
    };
};

$(document).ready(function(){
    "use strict";
    var authInfoUpdate = new AuthInfoUpdate();
    authInfoUpdate.init();
});