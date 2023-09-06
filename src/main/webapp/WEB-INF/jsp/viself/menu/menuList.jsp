<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    //-- 로그인 사용자 정보 셋팅
    String schFieldCodestr = "LANG_CD|이름^CD|코드";
%>
<section class="wrapper site-min-height">
    <h3><i class="fa fa-angle-right" id="content_header" data-term="L.시스템메뉴관리"> </i></h3>
    <div class="content-panel">
        <table class="none">
            <colgroup>
                <col width="48%"/>
                <col width="4%"/>
                <col width="48%"/>
            </colgroup>
            <tr>
                <td valign="top">
                    <br>
                    <form name="schForm" id="schForm" method="post" onSubmit="return false;">
                        <div class="table_cover">
                            <table class="box">
                                <colgroup>
                                    <col width="15%"/>
                                    <col width="65%"/>
                                    <col width="20%"/>
                                </colgroup>
                                <tr>
                                    <th width="15%">
                                        <select id="schField" name="schField"></select>
                                    </th>
                                    <td width="65%">
                                        <input type="text" name="schValue" id="schValue" value="" data-type="search"
                                               class="text width_max" onkeydown="doSearch(true)"/>
                                    </td>
                                    <td class="hbuttons">
                                        <span class="ui_btn medium icon"><i class="fa fa-search" onclick="doSearch()"><a
                                                href="javascript:void(0)" data-term="B.SEARCH"></a></i></span>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </form>
                    <br>
                    <!-- // 코드 정보 트리 // -->
                    <%-- 			<iframe name="iframeCodeTreeList" frameborder="0" width="480" height="580" scrolling="no" src="<%=iframeCodeTreeListSrc.toString()%>"></iframe> --%>
                    <div id="treeArea" class="treeCodeArea"></div>
                </td>
                <td></td>
                <td valign="top">
                    <form name="form1" id="form1" method="post">
                        <input type="hidden" name="updateMode" id="updateMode"/>
                        <div class="table_cover" id="detailView">
                            <table class="basic">
                                <caption><i class="fa fa-angle-right" id="content_header"></i><span
                                        id="txtNamePath"></span></caption>
                                <colgroup>
                                    <col width="20%"/>
                                    <col width="30%"/>
                                    <col width="20%"/>
                                    <col width="30%"/>
                                </colgroup>
                                <tr height="51px">
                                    <th>메뉴 ID</th>
                                    <td colspan="3"><input type="text" id="menuId" name="menuId" class="none"
                                                           readonly="readonly"><input type="hidden" name="sel_uuid_path"
                                                                                      id="sel_uuid_path"
                                                                                      value='<c:out value="${param.sel_uuid_path}" />'/>
                                    </td>
                                </tr>
                                <tr height="51px">
                                    <th>부모 메뉴 ID</th>
                                    <td colspan="3"><input type="text" id="parentMenuId" name="parentMenuId"
                                                           class="none" readonly="readonly"></td>
                                </tr>
                                <tr height="51px">
                                    <th>다국어코드</th>
                                    <td colspan="3"><span id="langCd" class="content"></span></td>
                                </tr>
                                <tr height="51px">
                                    <th>Url Path</th>
                                    <td colspan="3"><span id="urlPath" class="content"></span></td>
                                </tr>
                                <tr height="51px">
                                    <th>JSON_DATA</th>
                                    <td colspan="3"><span id="jsonData" class="content"></span></td>
                                </tr>
                                <tr height="51px">
                                    <th>사용여부</th>
                                    <td colspan="3">0:폐기,1:사용,2:숨김<br/><span id="useYn" class="content"></span></td>
                                </tr>
                            </table>
                        </div>

                        <%-- 버튼 목록 --%>
                        <div class="buttonlist">
                            <span class="ui_btn medium icon" id="levelOne"
                                  onclick="MENU.goModify(document.form1,'CHILD');"><i class="fa fa-edit" onclick=""><a
                                    href="javascript:void(0)">1레벨수정</a></i></span>
                            <span class="ui_btn medium icon" id="btnModify" style="display:none;"
                                  onclick="MENU.goModify(document.form1,'');"><i class="fa fa-edit" onclick=""><a
                                    href="javascript:void(0)" data-term="B.MODIFY"></a></i></span>
                            <span class="ui_btn medium icon" id="btnModify_child" style="display:none;"
                                  onclick="MENU.goModify(document.form1,'CHILD');"><i class="fa fa-edit" onclick=""><a
                                    href="javascript:void(0)">하위코드수정</a></i></span>
                            <%-- 				<span class="ui_btn medium icon" id="btnDelete" style="display:none;" onclick="goDelete(document.form1);"><i class="fa fa-minus" onclick=""><a href="javascript:void(0)"  data-term="B.DELETE"></a></i></span> --%>
                        </div>
                    </form>
                </td>
            </tr>
        </table>
    </div>
</section>
<script type="text/javascript">
    function Menu() {
        var init = function () {

            // init combo
            var $comboObj = $("#schField");
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

            drawTree();
        };
        var drawTree = function () {
            var arrDEPTH = [];
            if ($("#sel_uuid_path").val() != "") {
                arrDEPTH = $("#sel_uuid_path").val().split("≫");
            }
            //tree 불러오기
            paragonCmm.treeInitSet(paragonCmm.getUrl("/viself/menu/MenuMng/allList/json"), "MENU", JSON.stringify(arrDEPTH), '');

            //그려넣을 id, 루트id, 최종
            paragonCmm.treeDrawChild("treeArea", null);
        }

        var selectNode = function (menu_id) {
            var codGrp = $("#" + menu_id);
            var obj = codGrp.children("input:hidden");
            var jsonData = "";
            $(obj).each(function (i, d) {
                if (i > 0) {
                    jsonData += ",";
                }
                jsonData += $(this).attr("name") + ":\"" + $(this).val() + "\"";
                $("#" + $(this).attr("name"), "#form1").text($(this).val())

                if ("menuId" == $(this).attr("name")) {
                    $("#" + $(this).attr("name"), "#form1").val($(this).val())
                }

                if ("parentMenuId" == $(this).attr("name")) {
                    $("#" + $(this).attr("name"), "#form1").val($(this).val())
                }
                if ("langCd" == $(this).attr("name")) {
                    $("#txtNamePath", "#form1").text(paragonCmm.getLang($(this).val()));
                }
            });

            //-- 시스템 전용 코드일 경우 수정/삭제 버튼 디스플레이 처리
            var btnModifyObj = document.getElementById("btnModify");
            var btnModify_childObj = document.getElementById("btnModify_child");
            var btnDeleteObj = document.getElementById("btnDelete");
            var levelOne = document.getElementById("levelOne");

            btnModifyObj.style.display = "";
            btnModify_childObj.style.display = "";
// 		btnDeleteObj.style.display = "";
            levelOne.style.display = "none";
        };
        var goModify = function (frm, updateMode) {
// 			if (frm.menuId.value == "") {
// 				alert(paragonCmm.getLang("M.ALERT_SELECT","L.수정할코드정보" ));
// 				return;
// 			}
            var params = $(frm).serializeJSON();
            params.updateMode = updateMode;
            params.langCd = "L.시스템메뉴관리";
            main.movePage(paragonCmm.getUrl("/viself/menu/menuWrite.include"), params);

// 			frm.updateMode.value = updateMode;
// 			frm.action = "/viself/menu/menuWrite";
// 			frm.submit();
        }
        var goDelete = function () {

        }
        return {
            init: init,
            selectNode: selectNode,
            goModify: goModify,
            goDelete: goDelete
        }
    }

    var MENU = new Menu();
    MENU.init();


    var doSearch = function (isCheckEnter) {
        if (isCheckEnter && event.keyCode != 13) {
            return;
        }
        if ($("#schValue").val() != "") {
            $("#treeArea").html("");
            paragonCmm.treeDrawChild("treeArea", "", paragonCmm.getSearchQueryParams(document.schForm));
        } else {
            $("#treeArea").html("");
            paragonCmm.treeDrawChild("treeArea", "null", "");
        }
    }


    // 	var changeMenuView = function(){

    // 	}
    // 	var drawTree = function(){
    // 		var arrDEPTH = [];
    // 		if($("#sel_uuid_path").val() != ""){
    // 			arrDEPTH = $("#sel_uuid_path").val().split("≫");
    // 		}
    // 		//
    // 		paragonCmm.treeInitSet("SYS_MENU","ALL_LIST_MENU_JSON","MENU",JSON.stringify(arrDEPTH),'');

    // 		//그려넣을 id, 루트id, 최종
    // 	    paragonCmm.treeDrawChild("treeArea","null");
    // 	}
    // 	$(function(){
    // 		drawTree();
    // 	});

</script>