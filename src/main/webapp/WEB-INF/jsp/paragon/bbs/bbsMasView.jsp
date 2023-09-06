<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<%@page import="com.vertexid.spring.utils.SessionUtils"%>
<%@page import="com.vertexid.viself.base.ModelAttribute"%>
<%@page import="com.vertexid.commons.utils.ParamMap"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.vertexid.commons.utils.CommonConstants"%>
<%@page import="com.vertexid.viself.hr.SysLoginVO"%>
<%
	SysLoginVO loginUser 	=     (SysLoginVO) SessionUtils.getLoginVO();
	String siteLocale			= loginUser.getSiteLocale();
	String capitalizeSiteLocale = StringUtils.capitalize(siteLocale.toLowerCase());// ko >> Ko


%>
<div class="content-panel p-3" id="bbsView">
	<input type="hidden" name="bbsUid" value=""/>
	<h5 class="sub1_title" data-term="L.공지사항"><i class="fa fa-file-text"></i> </h5>
	<form name="bbsViewForm" id="bbsViewForm" data-opener-data="<c:out value='${param.openerData}'/>"></form>
	<table class="basic">
		<colgroup>
			<col style="width:15%;">
			<col style="width:35%;">
			<col style="width:15%;">
			<col style="width:35%;">
		</colgroup>
		<tr>
		    <th data-term="L.작성일" style="text-align:center"></th>
			<td data-col="regDte"></td>
		    <th data-term="L.작성자" style="text-align:center"></th>
			<td data-col="regLoginNm"></td>
		</tr>
		<tr>
		    <th data-term="L.제목" style="text-align:center"></th>
			<td colspan="3" data-col="bbsTit">
			</td>
		</tr>
		<tr id="bbsPopupStDtebbsTopYn">
			<th data-term ="L.상단게시여부" style="text-align:center"></th>
			<td data-col="bbsTopYn">
<!-- 				<input type="radio" name="bbsTopYn" value="Y"/>사용
				<input type="radio" name="bbsTopYn" value="N" checked="checked"/>미사용 -->
			</td>
			<th data-term="L.메인팝업기간" style="text-align:center"></th>
			<td><span class="separ" data-col="bbsPopupStDte"></span> ~ <span class="separ" data-col="bbsPopupEdDte"></span>
			</td>
		</tr>
		<tr id="bbsViewAuth">
			<th class="th4" style="text-align:center">문서조회권한</th>
			<td colspan="3" data-col="bbsViewAuth">
			</td>
			</tr>
		<tr>
		    <th data-term="L.내용" style="text-align:center"></th>
			<td colspan="3" data-col="bbsContent" style="height:300px; vertical-align: top;"></td>
		</tr>
		<tr>
		    <th data-term="L.첨부파일" style="text-align:center"></th>
			<td colspan="3" id="bbsFile"></td>
		</tr>
		</table>
		<div class="buttonlist">
			<div class="left" style="display:none;margin-right:20px;" id="checkDiv">
				<!-- <input type="checkbox" id="showCheck" />&nbsp; 오늘 더 이상 보지 않기 -->
				<label><input type="checkbox" name="deadline" id="deadline" value="toDate" onclick="javascript:closeWin(this.value)"> 오늘 더 이상 보지 않기</label>
			</div>
			<div class="right" style="display:none;" id="buttonDiv">
			<!-- 수정버튼 작성자/게시판담당자/시스템담당자일 경우 보이기 -->
				<span class="ui_btn medium icon" id="btnModify" name="btnModify"><i class="fa fa-edit"><a href="javascript:void(0)"  data-term="B.MODIFY"></a></i></span>
				<span class="ui_btn medium icon" id="btnDelete" name="btnDelete"><i class="fa fa-minus"><a href="javascript:void(0)"  data-term="B.DELETE"></a></i></span>
<!--  				<span class="ui_btn medium icon" id="btnList"><i class="fa fa-list-ul" ><a href="javascript:void(0)" data-term="B.LIST"></a></i></span>
 -->		</div>
		</div>
</div>
<script type="text/javascript">
//오늘 더이상 보지않기!
function closeWin(seq){
	paragonCmm.setCookie(seq, "ok", 1);
	self.close();
}

var BbsMasView = function(){

	var openerData = $("#bbsViewForm").data("opener-data");
	var loginInfo = opener.paragonCmm.formMap.get("loginInfo");

	var bbsViewForm = $("#bbsViewForm");
	var $bbsUid = openerData.bbsUid
	var $openType = openerData.openType;
	var $bbsRegLoginId ;
	var $bbsLoginId = $("#regLoginId").val()

	var isAuth = false;

	if($openType == "MAIN"){
		$("#checkDiv").show();
	} else {
		$("#checkDiv").hide();
	}


	var loadData = function(jsonData){

		var data = $.extend({}, openerData, jsonData);
		$("#bbsView").find("input[name=bbsUid]").html(data.bbsUid);
		paragonCmm.callAjax(paragonCmm.getUrl("/paragon/bbs/BbsMas/selectAuth/json"), data, function(json){
			var master = json.data;
			var str = "";

			if(typeof json === "object"){
				//-- 마스터
				$.each(master, function(key, val){
					if(key == "bbsViewAuth"){
						if( val.indexOf("IPS_TBD") >-1 ||
							val.indexOf("IPS_TJD") >-1  ||
							val.indexOf("IPS_BYD") >-1 ){
							if(str != "")str+=","
							str += "지식재산권팀";
						}
 						if(val.indexOf("IPS_SMK") > -1 ){
							if(str != "")str+=","
							str += "기술사업화팀";
						}
 						if(val.indexOf("IPS_TJU") > -1 ){
							if(str != "")str+=","
							str += "산학협력단";
						}
 						if(val.indexOf("IPS_SIM") > -1 ){
							if(str != "")str+=","
							str += "심의위원";
						}
 						if(val.indexOf("IPS_SJD") > -1 ){
							if(str != "")str+=","
							str += "대리인";
						}
						 if(val.indexOf("CMM_ISJ") > -1 ){
							if(str != "")str+=","
							str += "발명자";
						}
						$("[data-col='bbsViewAuth']", "#bbsView").html(str);
					}else{
						$("[data-col='"+key+"']", "#bbsView").html(val);
						if(json.data["bbsTopYn"] == "Y"){
							$("[data-col='bbsTopYn']", "#bbsView").html("사용");
						} else {
							$("[data-col='bbsTopYn']", "#bbsView").html("미사용");
						}

					}

				});

				var bbsRegLoginId = master["regLoginId"];

				if(loginInfo.loginId == bbsRegLoginId){
					isAuth = true;
				}

				if(loginInfo.isSys){
					isAuth = true;
				}
				if(isAuth && data.dashBoardChk != "Y") {
					$('#buttonDiv').show();
				}else{
					$("#buttonDiv").hide();
				}

				if(loginInfo.authCd=="CMM_ISJ"){
					console.log("yes!!!!!!!!!!!!!!!");
					$("#bbsViewAuth").hide();
					$("#bbsPopupStDtebbsTopYn").hide();
				}

			}
		});
	}

	var doModify = function(openerData){
        var imsiForm = $("<form method='POST'>").attr("action",paragonCmm.getUrl("/paragon/bbs/bbsWrite.popup"));
        //imsiForm.append($("<input type='hidden' name='bbsUid'>").val(openerData.bbsUid));
        imsiForm.append($("<input type='hidden' name='_csrf'>").val($("meta[name='_csrf']").attr("content")));
        imsiForm.append($("<input type='hidden' name='openerData'>").val(JSON.stringify(openerData)));
        imsiForm.appendTo("body");
        imsiForm.submit();
        imsiForm.remove();

	};

	var doDelete = function(openerData){
		var data = {};
		data.bbsUid = openerData.bbsUid

		paragonCmm.callAjax(paragonCmm.getUrl("/paragon/bbs/BbsMas/delete/json"), data, function(json){
			if(json.errYn === "E"){
                //-- 오류처리
                alert(json.msg);
                return false;
            }else if (json.errYn === "S") {
            	alert(json.msg);
				if(openerData.dashBoardTodoChk != "Y") {
					window.opener.BBSLIST.doSearch();
				}else {
					parent.window.opener.dashBoard.loadBoard();
				}
            }
		});
		window.close();
	};
	var attchmentEvent = function(){
		//수정
		$("#btnModify").on("click",function(){
			confirm(paragonCmm.getLang("M.ALERT_SUBMIT","L.수정"), function(r){
				if (r){
					doModify(openerData);
					//window.close();
				}
			})
		});
		//삭제
		$("#btnDelete").on("click",function(){
			confirm(paragonCmm.getLang("M.ALERT_SUBMIT","L.삭제"), function(r){
				if (r){
					doDelete(openerData);
				}
			})
		});

		//오늘 창닫기
		if($("#showCheck").prop("checked")){
			paragonCmm.setCookie(seq, "ok", 1);
			window.close();
		}
	}
	//-- 첨부파일 Form 로드
	var loadForm = function(){

		var options = {}

		options.relUid = $bbsUid;		//-- 관례 키 UID
		options.fileTpCd = "IPS/BBS";	//-- 파일 유형 코드
		options.defaultRelUid = "";		//-- 기본 로드 첨부파일(수정시)
		htmlUtils.loadFileView("bbsFile", options);	//-- 첨부파일 로드

	}
	var init = function(){
		attchmentEvent();
		loadForm();
		loadData();					//-- 초기 데이터 및 설정
	}
	return{
		init:init
	}
}
var BbsMasView = new BbsMasView();
$(function(){
	BbsMasView.init();
})

</script>