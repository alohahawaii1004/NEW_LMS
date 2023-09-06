/*
 * @(#)bbsList.js     2022-07-11
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

var BbsListModel = function () {
    "use strict";
    var BBS_LIST_URL = paragonCmm.getUrl('/paragon/bbs/BbsMas/listPerPage/json');
    var BBS_WRITE_URL = paragonCmm.getUrl('/paragon/bbs/bbsWrite.popup');
    var BBS_VIEW_URL = paragonCmm.getUrl('/paragon/bbs/bbsMasView.popup');
    return {
        getListUrl: function () {
            return BBS_LIST_URL;
        },
        getViewUrl: function () {
            return BBS_VIEW_URL;
        },
        getWriteUrl: function () {
            return BBS_WRITE_URL;
        }
    };
};

var BbsList = function () {
    "use strict";

    // attributes ======================================
    var loginInfo = paragonCmm.formMap.get("loginInfo");
    var PERMISSION_AUTHS = ["LMS_BCD", "LMS_BJD", "CMM_SYS"];
    var model = new BbsListModel();

    var $rootNode = $("#bbsList");
    var $bbsForm = $("#bbsListForm");
    var $grid = $("#listTable");
    var $resetBtn = $("#bbsInitBtn");
    var $searchBtn = $("#bbsSchBtn");
    var $writeBtn = $("#writeBtn");
    
    var $typeCombo = $("#bbsSchType", $bbsForm); 
    var $gbnCombo = $("#bbsSchGbn", $bbsForm);
    var $ctgryLayer = $("#bbsViewCtgryLayer");
    
    var comboCodes = [{
        typeCd: "ALL",
        typeNm: paragonCmm.getLang("L.CBO_ALL")
    }, {
        typeCd: "TITLE",
        typeNm: paragonCmm.getLang("L.제목")
    },{
        typeCd: "CONTENT",
        typeNm: paragonCmm.getLang("L.내용")
    },{
        typeCd: "REGNM",
        typeNm: paragonCmm.getLang("L.등록자")
    }];
    
    var objId = "lmsDocBox" + paragonCmm.getRandomUUID(); // popup call back

    var opts = $.extend({
        bbsTp: "LMS_BBS_BOARD",
        bbsCd: "BOARD_NOTICE",
        bbsNm: "L.공지사항"
    }, $rootNode.data("json"));
    opts.bbsTpCd = opts.bbsTp;
   
    // private functions ===============================
    var chkAuthority = function(){
        return PERMISSION_AUTHS.some(function(itm){
            return (loginInfo.authCd.indexOf(itm) !== -1);
        });
    };

    var initBbs = function(){
        // load bbs info: bbsName
        $("[data-col=bbsNm]", $rootNode).html(filterXSS(paragonCmm.getLang(opts.bbsNm)));
        $("input[name=bbsTp]", $rootNode).val(filterXSS(opts.bbsTp));
        $("input[name=bbsCd]", $rootNode).val(filterXSS(opts.bbsCd));
        
        // init combo
        htmlUtils.getCodeSelectOpt({
            targetId: $typeCombo,
            parentCd: opts.bbsCd,
            initdata: "|" + paragonCmm.getLang("L.CBO_ALL"),
            valNm: "cdAbb",
            txtNm: "langCd"
        });
        
        // 전체,제목,내용 combo
        htmlUtils.getCodeSelectOpt({
            targetId: $gbnCombo,
            parentCd: "NO_PARENT",
            jsonData: comboCodes,
            initdata: "",
            valNm: "typeCd",
            txtNm: "typeNm"
        });
        
        // 게시판 코드에 따라
        if(opts.bbsCd !== "undefined") {
            // 자료실인 경우
            if ( opts.bbsCd === "BOARD_FILE" ) {
                $ctgryLayer.show();
            }
        }
        
        // 등록일 정보 검색
        $("input[name=schFromRegDte]", $rootNode).datebox({validType: "validDate"});// easyui
        $("input[name=schToRegDte]", $rootNode).datebox({validType: "validDate"});// easyui
    };

    var initBtn = function(){
        if (chkAuthority()) {
            $writeBtn.show();
        }
    };

    var openPopup = function(options){
        var SCREEN_WIDTH = 1152;//screen.width;
        var SCREEN_HEIGHT = 750;//screen.height;

        var params = $.extend({
            url: model.getWriteUrl(),
            bbsUid: null,
            bbsCd: opts.bbsCd,
            bbsTpCd: opts.bbsTp,
            openerData: $.extend({
                "cbKey" : objId
            }, opts)
        }, options);

        var targetName = "POP_"+params.bbsUid;
        if(paragonCmm.isEmpty(options.bbsUid)){
            targetName = "POP_BBS_WIRTE";
        }
        // paragonCmm.openWindow("", SCREEN_WIDTH, SCREEN_HEIGHT, targetName, "yes", "yes", "");
        //
        // var imsiForm = $("<form method='POST'>").attr("action", params.url);
        // imsiForm.append($("<input type='hidden' name='bbsCd'>").val(params.bbsCd));
        // imsiForm.append($("<input type='hidden' name='bbsTpCd'>").val(params.bbsTpCd));
        // imsiForm.append($("<input type='hidden' name='bbsUid'>").val(params.bbsUid));
        // imsiForm.append($("<input type='hidden' name='openerData'>").val(JSON.stringify(params.openerData)));
        // imsiForm.append($("<input type='hidden' name='_csrf'>").val($("meta[name='_csrf']").attr("content")));
        // imsiForm.attr("target",targetName);
        // imsiForm.appendTo("body");
        // imsiForm.submit();
        // imsiForm.remove();

        htmlUtils.openPopup({
            url: params.url,
            targetName: targetName,
            popupTitle: paragonCmm.getLang(opts.bbsNm),
            popWidth: 1024,
            popHeight: 540,
            openerData: params.openerData,
            params: {
                bbsTpCd: params.bbsTpCd,
                bbsUid: params.bbsUid
            }
        });
    }

    var openView = function(row){
        var options = {
            url: model.getViewUrl(),
            bbsCd: row.bbsCd,
            bbsTpCd: row.bbsTp,
            bbsUid: row.bbsUid,
            openerData: $.extend({
                cbKey : objId,
                bbsUid: row.bbsUid
            }, opts)
        };

        openPopup(options);
    };

    var openWrite = function(){
        openPopup(opts);
    };

    var getQueryParams = function () {
        return $bbsForm.serializeObject();
    };

    var getGridHeight = function () {
        var ENURI = 320;
        var SCH_HEIGHT = 0; //$searchFrm.innerHeight();
        var windowHeight = window.innerHeight;
        return windowHeight - SCH_HEIGHT - ENURI;
    };

    var getPageSize = function () {
        var gHeight = getGridHeight();
        if (gHeight > 500) {
            return 20;
        }
        return 10;
    };
    
    var getBbsGridTitle = function () {
        // 게시판 코드에 따라
        if(opts.bbsCd !== "undefined") {
            // FAQ인 경우
            if ( opts.bbsCd === "BOARD_FAQ" ) {
                return paragonCmm.getLang("L.질문");
            }
        }
        return paragonCmm.getLang("L.제목");
    };

    var initGrid = function(){
        $('#listTable').datagrid({
            url: model.getListUrl(),
            singleSelect: true,
            striped: true,
            fitColumns: false,
            rownumbers: true,
            multiSort: true,
            pagination: true,
            pagePosition: 'bottom',
            nowrap: false,
            method: "post",
            queryParams: getQueryParams(),
            loadFilter: paragonCmm.easyuiLoadFilter,
            height: getGridHeight(),
            pageSize: getPageSize(),
            columns: [[
                  {field: "bbsUid", hidden: true}
                , {field: "bbsTpCd", hidden: true}
                , {field: "bbsCd", hidden: true}
                , {field: "regLoginId", hidden: true}
                , {field: "bbsTopYn", hidden: true}
                , {
                    field: "bbsCtgryNm",
                    hidden:(opts.bbsCd === "BOARD_FILE")?false:true,
                    title: paragonCmm.getLang("L.유형"),
                    formatter: paragonCmm.getLang,
                    halign: "CENTER",
                    align: "CENTER",
                    width: "10%"
                }
                , {
                    field: "bbsTit",
                    title: getBbsGridTitle(),
                    formatter: isTopYn,
                    halign: "CENTER",
                    align: "LEFT",
                    width: (opts.bbsCd === "BOARD_FILE")? "45%" : "55%"
                }
                , {
                    field: "regDeptNm",
                    title: paragonCmm.getLang("L.등록부서"),
                    formatter: filterXSS,
                    halign: "CENTER",
                    align: "CENTER",
                    width: "10%"
                }
                , {
                    field: "regDspNm",
                    title: paragonCmm.getLang("L.등록자"),
                    formatter: filterXSS,
                    halign: "CENTER",
                    align: "CENTER",
                    width: "13%"
                }
                , {
                    field: "strRegDte",
                    title: paragonCmm.getLang("L.등록일"),
                    formatter: filterXSS,
                    halign: "CENTER",
                    align: "CENTER",
                    width: "12%"
                }
                , {
                    field: "attachFileCnt",
                    title: paragonCmm.getLang("L.첨부파일"),
                    halign: "CENTER",
                    align: "CENTER",
                    width: "5%"
                }
                , {
                    field: "bbsHitCnt",
                    title: paragonCmm.getLang("L.조회수"),
                    halign: "CENTER",
                    align: "CENTER",
                    width: "5%"
                }
            ]],
            onDblClickRow: function (index, row) {
                openView(row);
            }
        });
    };

    var search = function () {
        $grid.datagrid('load', getQueryParams());
    }

    var setFormEvent = function(){
        $bbsForm.on("keyup", "input", function (e) {
            if (e.keyCode == 13){
                search();
            }
            return false;
        });
    };

    var setBtnEvent = function(){
        $rootNode.off("click", "button");
        $rootNode.on("click", "button", function(){
            var btnId = $(this).attr("id");
            switch (btnId){
                case "bbsInitBtn":
                	$("input[name='schFromRegDte']").val("");
                	$("input[name='schToRegDte']").val("");
                    $bbsForm[0].reset();
                    break;
                case "bbsSchBtn":
                    search();
                    break;
                case "writeBtn":
                    openWrite();
                    break;
                default:
                    console.warn("not support btnId:"+btnId);
                    break;
            }

            return false; // 이벤트 확산금지
        });
    };

    var initForm = function(){
        initBbs();
        initBtn();
        initGrid();
    };

    var loadForm = function () {}
    var setEvent = function () {
        setFormEvent();
        setBtnEvent();
    }
    var setCbFnc = function() {
        // 4. 팝업을 위한 콜백 설정
        paragonCmm.setCbFnc(objId, search);
    };
    var init = function () {
        initForm();
        loadForm();    //-- 폼 로드
        setEvent();    //-- 이벤트 등록
        setCbFnc();
    }
    //-- 상단게시 여부 Y ->  제목 앞 느낌표 추가
    var isTopYn = function (txt, row, idx) {
        txt = (txt == null ? "" : txt);
        if (row.bbsTopYn == "Y") {
            // return '<i class="fa fa-exclamation icon-required"></i>&nbsp&nbsp' + txt;
            return '<span class="pop-notice badge">공지</span> ' + txt;
        } else {
            return txt;
        }
    }

    return {
        init: init,
        isTopYn: isTopYn
    }
};

$(document).ready(function(){
    "use strict";
    var bbsList = new BbsList();
    bbsList.init();
});