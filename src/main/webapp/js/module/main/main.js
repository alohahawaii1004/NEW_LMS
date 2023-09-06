/*
 * @(#)main.js     2021-04-07(031) 오후 12:30
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
var MainModel = function(){
    "use strict";

    var PRIVACY_POLICY_URL = "";

    var MAX_LANG_VER_URL = paragonCmm.getUrl("/viself/mlangSvc/listMaxVersion/json");
    var GET_AUTH_MENU_LIST_URL = paragonCmm.getUrl("/viself/auth/AuthMenu/list/json");
    var HOME_URL = paragonCmm.getUrl("/");

    var cmmCallAjax = function(url, options){
        var opts = $.extend({
            params: null,
            cbFnc: function(json){}
        },options);

        paragonCmm.callAjax(url, opts.params, opts.cbFnc);
    };

    var loadLastMLang = function(options){
        cmmCallAjax(MAX_LANG_VER_URL, options);
    };

    var loadAuthMenu = function(options){
        cmmCallAjax(GET_AUTH_MENU_LIST_URL, options)
    }

    return {
        loadLastMLang: loadLastMLang,
        loadAuthMenu: loadAuthMenu,
        getHomeUrl: function(){
            return HOME_URL;
        },
        getPrivacyPolicyURL: function(){
            return PRIVACY_POLICY_URL;
        }
    };
};

var main = (function(){
    "use strict";

    // ==============================================
    // attributes
    // ----------------------------------------------
    var model = new MainModel();
    var loginInfo = paragonCmm.formMap.get("loginInfo");
    var $navAccordion = $("#nav-accordion");
    var $contentHeader = $(".mainContentHeader");
    var $contentTitle = $(".mainContentTitle");
    var $contentBreadcrumb = $(".mainContentBreadcrumb");
    var $content = $(".mainContent");
    var $pushMenu = $("[data-widget=pushmenu]");
    var menuList = [];
    var PATH_SEPARATOR = "≫";
    var CLASS_NAME_IS_OPENING = 'menu-is-opening';
    var CLASS_NAME_OPEN = 'menu-open';

    // ==============================================
    // private functions
    // ----------------------------------------------

    var loadMLang = function(){
        // cmmLocalStorage.set("SITE_LOCALE", "KO");
        paragonCmm.setSiteLocale("KO");

        //-- 다국어 최종 버전 가져오기
        model.loadLastMLang({
            cbFnc: function(json){
                var data = [].concat(json);
                var listMaxVersion = data[0].maxVersion;
                var oldVersion = paragonCmm.getLangVersion();
                //-- 최종 버전으로 로드
                if (typeof oldVersion === undefined || oldVersion !== listMaxVersion) {
                    paragonCmm.initLangStorage(listMaxVersion);
                }
            }
        });
    };

    var getDashboardUrl = function(){
        var url = "/main/dashboard";
        return url;
    };

    var initMain = function(){

        // 다국어 로드
        loadMLang();

        // 사용자 정보 표시
        $("#userNm").html(filterXSS(loginInfo.nmKo));

        // loadMain({
        //     accesUrl: getDashboardUrl()
        // });
    };

    // var doMenuClick = function(data){
    //     console.debug(data);
    // };



    /**
     * EasyUI 가 panel 을 남발하기 때문에 초기화 시켜 버림
     */
    var gcEasyUI = function () {
        var divs = $("body").children("div");
        var iLen = (divs) ? divs.length : 0;
        for (var i = 0; i < iLen; i += 1) {
            var tmpObj = divs[i];
            if ($(tmpObj).hasClass("panel")) {
                $(tmpObj).remove();
            }

            if ($(tmpObj).hasClass("window-shadow")) {
                $(tmpObj).remove();
            }

            if ($(tmpObj).hasClass("window-mask")) {
                $(tmpObj).remove();
            }
        }// end of for
    };

    var openPopup = function(options){

    };

    var setTitle = function(title){
        $contentTitle.html(filterXSS(title));
    };

    var makeBreadcrumb = function(menuInfo){
        var $li = $('<li class="breadcrumb-item"></li>');
        // <li class="breadcrumb-item"><a href="#">Home</a></li>
        // <li class="breadcrumb-item active">Dashboard vee1</li>

        if(menuInfo.active){
            $li.text(paragonCmm.getLang(menuInfo.langCd));
            $li.addClass("active");
        }else{
            $li.html('<a href="#">'+paragonCmm.getLang(menuInfo.langCd)+'</a>');
            // TODO add meta data
        }

        return $li;
    };

    var makeHomeBreadcrumb = function(){
        var $li = $('<li class="breadcrumb-item"></li>');
        // $li.html('<a href="'+model.getHomeUrl()+'">HOME</a>');
        $li.html('<a href="'+model.getHomeUrl()+'"><i class="fa fa-home"></i></a>');
        return $li;
    };

    var setBreadcrumbs = function(options){
        if(paragonCmm.isNotEmpty(options.menuIdPath)){
            $contentBreadcrumb.html("");
            $contentBreadcrumb.append(makeHomeBreadcrumb());
            var menuIds = String(options.menuIdPath).split(PATH_SEPARATOR);
            menuIds.forEach(function(itm){
                if(paragonCmm.isNotEmpty(itm)){
                    var menuInfo;
                    if(options.menuId === itm){
                        menuInfo = $.extend({}, options);
                        menuInfo.active = true;
                    }else{
                        menuList.some(function(mnu){
                            if(mnu.menuId === itm){
                                menuInfo = mnu;
                            }
                        });
                        menuInfo.active = false;
                    }
                    $contentBreadcrumb.append(makeBreadcrumb(menuInfo));
                }
            });
        }
    };

    var loadContent = function(options){
        if(paragonCmm.isNotEmpty(options.accesUrl)){
            $content.html("");

            var loadUrl = options.accesUrl;
            if(!loadUrl.endsWith(".include")){
                loadUrl += ".include";
            }
            loadUrl = paragonCmm.getUrl(loadUrl);
            // console.debug("loadUrl....."+loadUrl);

            //-- 뒤로가기 설정용
            if (!options.hasOwnProperty("isBack")) {
                var arrUrl = loadUrl.split("/");
                var urlState = window.location.protocol + "//" + window.location.host + window.location.pathname;
                urlState += "#" + arrUrl[$(arrUrl).length - 1];
                var tempParams = $.extend({}, options);
                tempParams.url = loadUrl;

                history.pushState(tempParams, "#" + arrUrl[$(arrUrl).length - 1], urlState);
            }

            $content.load(loadUrl, options, function(){
                paragonCmm.convertLang($content);
            });
        }
    };

    var hideContentTitle = function(){
        $contentHeader.hide();
    };

    /**
     * 메인페이지에 통체로 띄울때 사용
     * @param options 관련 parameters
     */
    var loadMain = function(options){
        console.debug(".....................loadMain");
        hideContentTitle();
        loadContent(options);
    };

    /**
     * 타이틀, Breadcrumbs 를 표시하고 페이지를 로딩할때 사용
     * @param options 관련 parameters
     */
    var loadPage = function(options){
        console.debug(".....................loadPage");

        // 1. set title
        setTitle(paragonCmm.getLang(options.langCd));

        // 2. set bread crumbs
        setBreadcrumbs(options);
        if(!$contentHeader.is(':visible')){
            $contentHeader.show();
        }

        // 3. load content
        loadContent(options);
    };

    var goPage = function(options){

        // 2. easy ui panel 초기화
        gcEasyUI();

        // 1. 파라메터 정리
        var opts = $.extend({
            accesUrl: null,
            langCd: null,
            text: null,
            langCdPath: null,
            jsonData: null,
            open_type: null
        }, options);

        console.debug(opts);

        if(paragonCmm.isNotEmpty(opts.jsonData)){
            var jsonData = [].concat(JSON.parse(opts.jsonData));
            opts = $.extend(opts, jsonData[0]);
        }
        console.debug(opts);

        if(paragonCmm.isEmpty(opts.accesUrl)){
            console.debug("no access url........");
            // return false;
        }else{
            switch(opts.open_type){
                case "POPUP":
                    // open popup
                    openPopup(opts);
                    break;
                case "MAIN":
                    loadMain(opts);
                    break;
                case "DASHBOARD":
                    break;
                default:
                    // load page
                    loadPage(opts);
                    break;
            }
        }
    };

    // var setTreeView = function(data){
    //     // 1. set menu
    //     $navAccordion.treeview({
    //         levels: 1,
    //         data: getMenuTree(data),
    //         backColor: "orange",
    //         collapseIcon: "fa fa-minus",
    //         expandIcon: "fa fa-plus"
    //     });
    //
    //     // 2. set menu Event
    //     $navAccordion.on("nodeSelected ", function(event, node){
    //         goPage(node);
    //         return false;
    //     });
    // };

    // var open1stIdx = -1;

    var leftMenu = (function(){

        var menuInfo = {};
        var menuArray = [];

        /**
         * menu html 구성
         * @param datas menu data
         * @returns {*|jQuery|HTMLElement}
         */
        var setMenuHtml = function(datas){

            var makeMenuTitle = function(itm){
                var $a = $('<a href="#" class="nav-link"></a>');

                // 1. add nav-icon
                if(itm.levelNo === 1){
                    var icon = "fas fa-circle";
                    if(paragonCmm.isNotEmpty(itm.menuIcon)){
                        icon = filterXSS(itm.menuIcon);
                    }
                    var $icon = $('<i class="nav-icon '+icon+'"></i>');
                    $a.append($icon);
                }

                // 2. add nav title
                var $p = $('<p></p>');
                // 2.1. add title
                $p.html(paragonCmm.getLang(itm.langCd));
                // 2.2. child icon
                if(itm.nodes) {
                    $p.append($('<i class="right fas fa-angle-left"></i>'));
                }
                $a.append($p);

                $a.data("menu-options", itm);

                return $a;
            };

            var addMenu = function(itm){
                // console.debug(itm);
                var $menu = $('<li class="nav-item"></li>');
                $menu.append(makeMenuTitle(itm));
                if(itm.nodes){
                    $menu.append(addSubMenu(itm.nodes));
                }
                return $menu;
            };

            var addSubMenu = function(nodes){
                var $subMenu = $('<ul class="nav nav-treeview"></ul>');
                nodes.forEach(function(itm){
                    $subMenu.append(addMenu(itm));
                });
                return $subMenu;
            };

            var menuHtml = '';
            menuHtml += '<ul class="nav nav-pills nav-sidebar flex-column nav-child-indent nav-compact nav-collapse-hide-child" data-widget="treeview" role="menu">';
            menuHtml += '</ul>';
            var $navSidebar = $(menuHtml);
            datas.forEach(function(itm){
                $navSidebar.append(addMenu(itm));
            });
            return $navSidebar;
        };

        /**
         * list to tree
         * @param data
         * @returns {*[]}
         */
        var getMenuTree = function(data){
            var datas = data || [];
            var treeArray = [];
            if(menuList !== data){
                menuList = JSON.parse(JSON.stringify(data));
            }

            var setTree = function(nodes, item, idx){
                if(Array.isArray(nodes)){
                    return nodes.some(function (node) {
                        if (node.menuId === item.parentMenuId) {
                            // node.nodes = [].concat(node.nodes);
                            node.nodes = node.nodes || [];

                            var tmpNode = datas.splice(idx, 1);
                            tmpNode[0].text = paragonCmm.getLang(tmpNode[0].langCd);
                            return node.nodes.push(tmpNode[0]);
                        }
                        return setTree(node.nodes, item, idx);
                    });
                }
            };

            var limitIdx = 0;
            while(datas.length > 0 && limitIdx < 1000){
                limitIdx += 1;
                datas.some(function (item, index) {
                    if (paragonCmm.isEmpty(item.parentMenuId)) {

                        var node = datas.splice(index, 1);
                        node[0].text = paragonCmm.getLang(node[0].langCd);
                        return treeArray.push(node[0]);
                    }
                    return setTree(treeArray, item, index);
                });
            }

            console.debug(treeArray);
            return treeArray;
        };

        var setAdminLteTreeView = function(data){
            // 1. set menu html
            $navAccordion.append(setMenuHtml(getMenuTree(data)));

            // 2. init adminlte treeView
            // console.debug("init adminlte treeView");
            // HACK
            // 아래 코드가 없을경우 브라우저 마다 treeView 가 오동작 할 수 있음
            $('[data-widget="treeview"]').Treeview("init");

            // 2. set menu event
            console.debug("set menu event");
            $navAccordion.on("click", ".nav-link", function(){
                console.debug("nav-link click!!");
                console.debug($(this));

                // set class active
                $(".nav-link", $navAccordion).removeClass("active");
                $(this).addClass("active");

                var node = $(this).data("menu-options");
                console.debug("node info......");
                console.debug(node);
                goPage(node);
            });

            // 2.1. 1st level event : 이벤트 적용 안됨
            // $('#adminLteTreeView').on("expanded.lte.treeview", function(e){
            //
            //     console.debug("expanded.lte.treeview!!");
            //     console.debug(e);
            // });
        };

        var setMenuInfo = function(data){
            menuArray = [].concat(JSON.parse(JSON.stringify(data)));
            menuArray.forEach(function(menu){
                menuInfo[menu.menuId] = menu;
            });

            console.debug(menuArray);
            console.debug(menuInfo);
        };

        var setMenu = function(data){

            // setTreeView(data);
            setMenuInfo(data);

            setAdminLteTreeView(data);
        };

        var getMenuInfo = function(menuId){

            console.debug(menuInfo);
            console.debug("menuInfo....");
            console.debug(menuInfo[menuId]);

            return menuInfo[menuId];
        };

        var init = function(){
            model.loadAuthMenu({
                cbFnc: function(json){
                    // console.debug(json);
                    setMenu(json.data);
                }
            });
        };

        return {
            init: init,
            getMenuInfo: getMenuInfo
        };
    })();

    var pageResize = function(){
        $(".easyui-tabs").tabs('resize');        //-- tab resize
        $('.datagrid-f').datagrid('resize');    //-- data-grid resize
        $(".panel-body").panel("resize");
    }

    var setBtnEvent = function(){
        $("#privacyPolicy").on("click",function(){
            paragonCmm.openWindow(model.getPrivacyPolicyURL(), "1250", "800", "", "yes", "yes", "");
        });

        $("[data-btn=changePwdBtn]").on("click", function(){
            let options = {
                title: "암호변경",
                href: paragonCmm.getUrl("/cmm/modal/updatePwd.include"),
                width: 576,
                height: "auto",
                top: "10px",
                params:{
                    loginId: loginInfo.loginId
                },
                cbFnc: function(data){
                    console.debug(data);
                }
            };

            htmlUtils.openDialog(options);
        });
    };

    var setEvent = function(){
        // resize event

        // $(document).on('collapsed.lte.pushmenu', function(){
        //     console.debug("collapsed.lte.pushmenu");
        //     'collapsed-done.lte.pushmenu' 에서 처리
        //     pageResize();
        // });

        $(document).on('collapsed-done.lte.pushmenu', function(){
            // console.debug("collapsed-done.lte.pushmenu");
            pageResize();
        });
        $(document).on('shown.lte.pushmenu', function(){
            // console.debug("shown.lte.pushmenu");
            // 기본 300 이므로 동일하게 설정
            setTimeout(function(){
                pageResize();
            }, 300);
        });

        setBtnEvent();
    };

    let goDashboard = function(){
        console.debug()
        goPage(leftMenu.getMenuInfo("ISSUE_LIST"));
    };

    // ==============================================
    // public functions
    // ----------------------------------------------

    var init = function(){
        initMain();
        leftMenu.init();
        setEvent();
        goDashboard();
    };

    return {
        init: init,
        getMenuInfo: function(menuId){
            return leftMenu.getMenuInfo(menuId);
        },
        goPage: goPage
    };
})();

$(document).ready(function(){
    "use strict";
    main.init();
});