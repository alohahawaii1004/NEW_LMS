
<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<%@page import="com.vertexid.commons.utils.CommonConstants"%>
<%@page import="com.vertexid.viself.hr.SysLoginVO"%>
<%@ page import="com.vertexid.spring.utils.SessionUtils" %>

<%
SysLoginVO loginUser =     (SysLoginVO) SessionUtils.getLoginVO();
String siteLocale = loginUser.getSiteLocale();

%>
<div class="content-panel">
		<h5 class="sub1_title" data-term="L.비밀번호변경"><i class="fa fa-file-text"></i> </h5>
		<div>
			<form id="pwdForm1" method="post">
			<input type="hidden" name="usrDeptCd" id="usrDeptCd" data-col="usrDeptCd" value="<%=loginUser.getDeptCd()%>"/>
			<table class="basic">
				<colgroup>
					<col style="width:12%"/>
					<col style="width:22%"/>
					<col style="width:12%"/>
					<col style="width:22%"/>
				</colgroup>
				<tr>
					<th data-term="L.성명"></th>
					<td data-col="usrNmKo">
						<%=loginUser.getNmKo()%>
					</td>
					<th data-term="L.성명(영문)"></th>
					<td data-col="usrNmEn">
						<%=loginUser.getNmEn() == null ? "" : loginUser.getNmEn()%>
					</td>
				</tr>
				<tr id="idtr">
					<th data-term="L.ID"></th>
					<td data-col="loginId">
						<%=loginUser.getLoginId()%>
						<input type="hidden" id="loginId" name="loginId" data-col="loginId" class="form-control" value="<%=loginUser.getLoginId()%>"/>
					</td>
					<th data-term="L.PASSWORD"></th>
					<td>
						<input type="password" id="loginPwd" name="loginPwd" data-col="loginPwd" class="form-control" value=""/>
					</td>
				</tr>
			</table>
			<span style="color: red;">※비밀번호 유효기간은 3개월입니다.</span>			
			</form>
		</div>
	
		<div class="buttonlist">
			<div class="right">
				<span class="ui_btn medium icon" style="display: none;" id="closeBtn"><i class="fa fa-close" ><a href="javascript:void(0)">취소</a></i></span>
				<span class="ui_btn medium icon" id="savBtn"><i class="fa fa-save" ><a href="javascript:void(0)" data-term="B.SAVE"></a></i></span>
			</div>
		</div>
	</div>

<script src="<c:url value='/js/module/main/pwdChg.js'/>?ver=<spring:eval expression="@cmmProperties.getProperty('js.version')"/>"></script>
