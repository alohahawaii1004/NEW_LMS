<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<!DOCTYPE html>
<%
String loginStatus  = (String)request.getSession().getAttribute("loginStatus");
out.print(loginStatus);
if("OK".equals(loginStatus)){
%>
<html lang="en">
	 <head>
        <!-- meta info -->
        <%@include file="/WEB-INF/tiles/include/metaInfo.jsp"%>
        <!-- style links -->
        <%@include file="/WEB-INF/tiles/include/cssLoginLink.jsp"%>
        <!-- js links -->
        <%@include file="/WEB-INF/tiles/include/jsLoginLink.jsp"%>
        <%@include file="/WEB-INF/tiles/include/jsRsaLink.jsp"%>
    </head>
<body class="login">
	<form id="loginProc" action="/main/main">
	</form>
	<script>
		$("#loginProc").submit();
	</script>
    </body>
</html>
<%
}else if("FAIL".equals(loginStatus)){
%>
<html lang="en">
    <head>
        <!-- meta info -->
        <%@include file="/WEB-INF/tiles/include/metaInfo.jsp"%>
        <!-- style links -->
        <%@include file="/WEB-INF/tiles/include/cssLoginLink.jsp"%>
        <!-- js links -->
        <%@include file="/WEB-INF/tiles/include/jsLoginLink.jsp"%>
        <%@include file="/WEB-INF/tiles/include/jsRsaLink.jsp"%>
    </head>
    <body class="login">
      		<div class="wrap_login">
      			<div class="login_box">
      				<hi>Login Proc</hi>
      				<div class="notice_area">
						<p>※ 로그인에 실패하였습니다.</p>
					</div>
      			</div>
      		</div>
    </body>
</html>
<%
}else if("LOGOUT".equals(loginStatus)){
%>
<html lang="en">
	 <head>
        <!-- meta info -->
        <%@include file="/WEB-INF/tiles/include/metaInfo.jsp"%>
        <!-- style links -->
        <%@include file="/WEB-INF/tiles/include/cssLoginLink.jsp"%>
        <!-- js links -->
        <%@include file="/WEB-INF/tiles/include/jsLoginLink.jsp"%>
        <%@include file="/WEB-INF/tiles/include/jsRsaLink.jsp"%>
    </head>
<body class="login">
	<form id="loginProc" action="/login.do">
	</form>
	<script>
		$("#loginProc").submit();
	</script>
    </body>
</html>
<%
}
%>