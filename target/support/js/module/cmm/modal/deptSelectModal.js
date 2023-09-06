/*
 * @(#)userSelectModal.js     2022-03-22(022) 오후 4:10
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

var DeptSelectModel = function () {
    "use strict";

    var HR_TREE_URL = "/paragon/hr/DeptMng/treeList/json";

    var getUserInfo = function (options) {
        var opt = $.extend({
            params: {},
            cbFnc: function () {
            }
        }, options);

        paragonCmm.callAjax(paragonCmm.getUrl(HR_TREE_URL), opt.params, opt.cbFnc);
    };

    return {
        getHrTreeUrl: function () {
            return paragonCmm.getUrl(HR_TREE_URL);
        },
        getUserInfo: getUserInfo
    };
};

var DeptSelect = function () {
    "use strict";

    var model = new DeptSelectModel();

    var $rootNode = $("#deptSelect");
    var $form = $("#deptSelectForm1");
    var $treeArea = $("#treeArea");
    var $hrDeptList = $("#hrDeptList");

    var openerData = $rootNode.data("opener-data");
    var opt = $.extend({"selectType":""}, openerData);

    var initForm = function () {
    };

    var drawTree = function () {
        $treeArea.html("");
        var arrDEPTH = [];

        // tree 불러올 값 설정
        paragonCmm.treeInitSet(model.getHrTreeUrl(), "DEPT", JSON.stringify(arrDEPTH), opt.selectType);

        // 그려넣을 ID, 루트ID, 최종
        paragonCmm.treeDrawChild("treeArea", "", {
            usrSeq: "1", // 1: 팀/부서
            deptTpCd: "00" // 00: 내부부서
        });
    };

    var searchDept = function () {

        paragonCmm.treeDrawChild("treeArea", "", paragonCmm.getSearchQueryParams($form),function(){
        	$treeArea.html("");
        });
    };

    var closeWindow = function (data) {
        var $modal = $("#" + openerData.modalId);
        var cbFncClose = $modal.data("callback");
        if (typeof cbFncClose === "function") {
            // 모달일 경우 닫기
            cbFncClose(data);
        } else {
            // 팝업일 경우 닫기
            self.close();
        }
    };

    var selectDept = function () {
        var data = [];
        //-- 결재자 정보 수집
        var rowsApr = $("td[data-meaning='data_td']", "#hrDeptList");
        rowsApr.each(function (i, tr) {
            var rowData = {};
            //-- input TAG 처리
            var $input = $("input:hidden[name='deptRow']", $(tr));
            var rowData = JSON.parse($input.val());
            rowData.inOut = 'Y';
            data.push(rowData);
        });
		if(data == ''){
	        $.messager.confirm("Confirm", '부서가 선택되지 않았습니다.', function (r) {
	            if (r) {
			        closeWindow(data);
	            }else{
					return false;
				}
	        });
		}else{
	        closeWindow(data);
		}
    };
    var setFormEvent = function () {

        $form.on("keyup", "input", function (e) {
            if (e.keyCode == 13 ){
            	paragonCmm.showBackDrop();
        		searchDept();
        		paragonCmm.hideBackDrop();
            }
            return false;
        });
	};

    var setBtnEvent = function () {
        $rootNode.off("click", "button");
        $rootNode.on("click", "button", function () {
            var btnId = $(this).attr("id");
            switch (btnId) {
                case "deptSelectSearchBtn":
                    searchDept();
                    break;
                case "deptSelectBtn1":
                    selectDept();
                    break;
                default:
                    break;
            }
            return false;
        });
    };

    var loadData = function () {
        var depts = opt.deptDatas;
        if (depts) {
            depts.forEach(function (dept) {
                model.getUserInfo({
                    params: {deptCd: dept.deptCd,
                    dspNm: dept.dspNm
                    },
                    cbFnc: function (json) {
                        setUserInfo(json.data[0]);
                    }
                });
            });
        }
    };

    var chkUserId = function (json) {
        var data = json;
        var arrObj = $("input:hidden[name='deptCd']", "#hrDeptList");
        var rtnBool = true;
        arrObj.each(function (i, d) {
            if (data.deptCd == $(d).val()) {
                rtnBool = false;
            }
        });
        return rtnBool;
    }

    var setUserInfo = function (jsonData) {
        if (chkUserId(jsonData)) {
            var data = jsonData;
            data.dspNm = data["nm" + paragonCmm.toPascalCase(paragonCmm.getSiteLocale())];

            // 단일선택일 경우
            if (opt.isSingle) {
                $hrDeptList.empty();
            }

            $("#addDeptTmpl").tmpl(data).appendTo("#hrDeptList");
        }
    };

    var selectNode = function (codeId) {

        var codGrp = $("#" + codeId);
        var jsonData = {};
        jsonData["deptCd"] = codGrp.children("input:hidden[name='deptCd']").val();
        jsonData["nm" + paragonCmm.toPascalCase(paragonCmm.getSiteLocale())] = codGrp.children("input:hidden[name='nmKo']").val();

        //-- 이미 선택된 사용자인지 여부 체크!
        setUserInfo(jsonData);
        paragonCmm.tableTrDragSet("hrDeptList");
    };

    var init = function () {
        initForm();
        $treeArea.html("");
        drawTree();
        loadData();
        setEvent();
    };

    var setEvent = function(){
        setFormEvent();
        setBtnEvent();
    };

    return {
        init: init,
        selectNode: selectNode
    };
};

var DEPT = DEPT || {};
$(document).ready(function () {
    "use strict";
    console.info(".....................[Loading Module: 사용자 선택]");
    DEPT = new DeptSelect();
    DEPT.init();
});