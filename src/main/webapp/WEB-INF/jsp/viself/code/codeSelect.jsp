<%--
  - 공용: 코드선택
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<%@page import="org.apache.commons.lang3.StringUtils" %>
<%
    //-- 로그인 사용자 정보 셋팅
    String siteLocale = "KO";
    String sel_uuid_path = "";

    //-- 기타값 셋팅
    String schFieldCodestr = "LANG_CD|코드명^CD|코드";

%>
<div class="row" id="skillSearch" data-opener-data="<c:out value='${param.openerData}'/>">
    <div class="col-md-6 col-sm-6">
        <div class="row">
            <div class="white-panel pn-sub2-top donut-chart" style="padding: 10px 0 10px 0;margin: 0 14px 0 14px;">
                <!-- Search AREA -->
                <div class="row sub1_search">
                    <form id="skillSchform1" method="post" onsubmit="return false;">
                        <input type="hidden" name="parentCd" data-type="search"/>
                        <div class="col-md-9">
                            <select id="codeMngSchField" name="schField"></select>
                            <%-- 콤보박스는 JS --%>
                            <input type="text" name="schValue" id="schValue" value="" data-type="search"
                                   class="form-control input-sm" style="width:55%"/>
                        </div>
                        <div class="col-md-3">
                            <span class="ui_btn medium icon" id="cdSearchBtn"><i class="fa fa-search"><a
                                    href="javascript:void(0)" data-term="B.SEARCH"></a></i></span>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="row sear_mt">
            <div class="col-md-12 col-sd-12">
                <div class="white-panel" style="text-align: left;">
                    <div id="treeCodeArea"
                         style="width:98%;height:330px;vertical-align: top;border:1px solid #CDCDCD;overflow: auto;box-shadow: 0 2px 1px rgb(0 0 0 / 20%); min-height: 330px;"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6 col-sm-6">
        <div class="white-panel pn-sub1-table donut-chart" style="min-height: 400px;">
            <div class="buttonlist">
                <div class="left">
                    <h6 class="sub2_title" data-term="L.코드_선택"><i class="fa fa-caret-square-o-right"></i></h6>
                </div>
                <div class="right">
                </div>
            </div>
            <table class="list">
                <colgroup>
                    <col width="10%"/>
                    <col width="*"/>
                </colgroup>
                <thead>
                <!--
                <tr>
                    <td colspan="3" style="background-color: #fff">
                        <span style="float:left;color:red;" data-term="M.최종결재선메세지"></span>
                    </td>
                </tr>
                -->
                <tr>
                    <th colspan="1"></th>
                    <th data-term="L.코드"></th>
                </tr>
                </thead>
                <tbody id="tbodyCodeList">
                </tbody>
            </table>
        </div>
        <%-- 버튼 목록 --%>
        <br/>
        <div class="buttonlist">
            <button type="button" class="btn btn-default" id="codeConfirm" data-term="L.확인"><i class="fa fa-check"></i>
            </button>
        </div>
    </div>
</div>
<script id="addCodeTmpl" type="text/x-jquery-tmpl">
<tr data-meaning="code_tr" id="sel_\${cd}">
	<td align="center">
		<i class="fa fa-times" style="color:red;cursor:pointer;" onClick="$('#sel_\${cd}').remove();"></i>
	</td>
	<td style="text-align:center" colspan="2">
		\${paragonCmm.getLang(langCd)}
		<input type="hidden" name="cd"	        value="\${cd}">
		<input type="hidden" name="cdAbb"	        value="\${cdAbb}">
		<input type="hidden" name="ordNo"		    value="\${ordNo}">
		<input type="hidden" name="langCd"	        value="\${langCd}">
		<input type="hidden" name="parentCd"	    value="\${parentCd}">
		<input type="hidden" name="parentLangCd"	value="\${parentLangCd}">
		<input type="hidden" name="levelNo"	    value="\${levelNo}">
		<input type="hidden" name="childCnt"	    value="\${childCnt}">
		<input type="hidden" name="cdPath"	    	value="\${cdPath}">
		<input type="hidden" name="langCdPath"	value="\${langCdPath}">
		<input type="hidden" name="cdAttr1"	    value="\${cdAttr1}">
		<input type="hidden" name="cdAttr2"	    value="\${cdAttr2}">
		<input type="hidden" name="cdAttr3"	    value="\${cdAttr3}">
		<input type="hidden" name="cdAttr4"	    value="\${cdAttr4}">
		<input type="hidden" name="cdData"	    	value="\${cdData}">
		<input type="hidden" name="useYn"	    value="\${useYn}">
		<input type="hidden" name="attrDesc1"	value="\${attrDesc1}">
		<input type="hidden" name="attrDesc2"	value="\${attrDesc2}">
		<input type="hidden" name="attrDesc3"	value="\${attrDesc3}">
		<input type="hidden" name="attrDesc4"	value="\${AttrDesc4}">
	</td>
</tr>

</script>
<script type="text/javascript">
    function Code() {

        // 프로젝트 품목 검색 루트 DIV
        var $divRoot = $("#skillSearch");

        // 오프너 데이터
        var openerData = $divRoot.data("opener-data");
        //-- 검색폼
        var $searchForm = $("#skillSchform1");
        //-- Tbody
        var $tbody = $("#tbodyCodeList");
        //-- Tmpl
        var $tmpl = $("#addCodeTmpl");
        //-- 트리영역
        var $treeArea = $("#treeCodeArea");

        //-- 검색 버튼
        var $btnSearch = $("#cdSearchBtn");
        //-- 확인 버튼
        var $btnConfirm = $("#codeConfirm");
        //-- 코드 선택 옵션
        var opt = {
            "multiSelect": "Y", 			// 다중선택여부
            "selectType": "CHILD_ONLY",	// 최하위자식만 선택가능 null 값이면 부모도 선택 가능
            "parentCd": "ROOT"				// 최초 부모 코드
        };
        /**
         * 초기 옵션 설정
         */
        var initForm = function () {

            $.extend(opt, openerData);

            // init combo
            var $comboObj = $("#codeMngSchField");
            htmlUtils.getCodeSelectOpt({
                targetId: $comboObj,
                parentCd: "NO_PARENT",
                jsonData: [{
                    cd: "LANG_CD",
                    txt: "코드명"
                }, {
                    cd: "CD",
                    txt: "코드"
                }],
                initdata: "",
                valNm: "cd",
                txtNm: "txt"
            });
            $comboObj.addClass("form-control");
            $comboObj.addClass("form-control-sm");
        }
        /**
         * 트리 그리기
         */
        var drawTree = function () {
            var arrDEPTH = [];
// 			if($("#sel_uuid_path").val() != ""){
// 				arrDEPTH = $("#sel_uuid_path").val().split("≫");
// 			}
            //
            paragonCmm.treeInitSet(paragonCmm.getUrl("/viself/code/CodeMng/listCode/json"), "CODE", JSON.stringify(arrDEPTH), opt.selectType);

            //그려넣을 id, 루트id, 최종
            paragonCmm.treeDrawChild("treeCodeArea", opt.parentCd);
        }
        /**
         * 선택된 코드 여부
         */
        var chkCode = function (json) {
            var data = JSON.parse(json);
            var arrObj = $("input:hidden[name='cd']", $tbody);
            var rtnBool = true;
            arrObj.each(function (i, d) {
                if (data.cd == $(d).val()) {
                    rtnBool = false;
                }
            });

            return rtnBool;

        }
        /**
         * 이미 선택된 코드 여부 체크!
         */
        var changeCodeView = function (json) {
            if (chkCode(json)) {
                var data = JSON.parse(json);
                if ("N" == opt.multiSelect) {
                    $tbody.html("");
                }
                $tmpl.tmpl(data).appendTo($tbody);
            }
        }
        /**
         * 코드 트리선택
         */
        var selectNode = function (code_id) {

            var codGrp = $("#" + code_id);
            var obj = codGrp.children("input:hidden");
            var jsonData = "";
            $(obj).each(function (i, d) {
                if (i > 0) {
                    jsonData += ",";
                }
                jsonData += $(this).attr("name") + ":\"" + $(this).val() + "\"";
            });
            new Function("return data ={" + jsonData + "}")();
            changeCodeView(JSON.stringify(data));
        }
        /**
         * 코드 검색
         */
        var doSearch = function (isCheckEnter) {
            if (isCheckEnter && event.keyCode != 13) {
                return;
            }
            if ($("#schValue").val() != "") {
                $treeArea.html("");
                var query = paragonCmm.getSearchQueryParams($searchForm);
                query.parentCd = opt.parentCd;
                paragonCmm.treeDrawChild("treeCodeArea", opt.parentCd, query);
            } else {
                $treeArea.html("");
                paragonCmm.treeDrawChild("treeCodeArea", opt.parentCd, "");
            }
        }
        /**
         * 품목검색 닫기
         */
        var closeWindow = function () {
            var cbFncClose = $divRoot.data("callback-close");
            if (typeof cbFncClose === "function") {
                // 모달일 경우 닫기
                cbFncClose();
            } else {
                // 팝업일 경우 닫기
                self.close();
            }
        };
        /**
         * 코드 선택 확인
         */
        var doConfirm = function () {
            var aprv = $("[data-meaning='code_tr']");
            if (aprv.length == 0) {
                alert(paragonCmm.getLang("M.선택해주세요", "L.코드"));
                return;
            }
            var json = [];
            aprv.each(function (i, e) {
                var trTag = $(e);
                var obj = trTag.find("input:hidden");

                var jsonData = {};
                $(obj).each(function (i, d) {
                    jsonData[$(d).attr("name")] = $(d).val();
                });
                json.push(jsonData);
            });

            var cbFnc = $divRoot.data("callback");
            if (typeof cbFnc === "function") {
                cbFnc(json);
                closeWindow();
            } else {
                // TODO opener.cbFnc ....
            }
        }
        /**
         * 이벤트 설정
         */
        var setEvent = function () {
            $("#schValue").off();
            $("#schValue").on("keyup", function () {
                doSearch(true);
            });
            $btnSearch.off();
            $btnSearch.on("click", function () {
                doSearch();
            });
            $btnConfirm.off();
            $btnConfirm.on("click", function () {
                doConfirm();
            });
        }
        var init = function () {
            initForm();
            setEvent();
            drawTree();
        };
        return {
            init: init,
            selectNode: selectNode
        }
    }

    var CODE = new Code();
    CODE.init();

</script>
