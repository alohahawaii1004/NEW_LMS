<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.vertexid.spring.utils.CmmProperties"%>
<%@page import="com.vertexid.spring.utils.BaseProperties"%>
<%@page import="com.vertexid.commons.utils.CommonConstants"%>
<%@page import="com.vertexid.viself.hr.SysLoginVO"%>
<%@ page import="com.vertexid.spring.utils.SessionUtils" %>
<%@include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	<table class="basic">
		<thead>
			<tr>
				<th style="text-align: center;">
					<h2>개인(기업)정보 수집‧이용‧제공 동의서</h2>
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>
				인제대학교 산학협력단은 교직원의 지식재산권 직무발명신고와 관련하여 ｢특허법 시행령」 제19조의 2,  ｢특허법」 제42조, ｢개인정보보호법」 제15조 제1항제1호, 제17조제1항제1호, 제23조제1호, 제24조제1항제1호에 따라 아래와 같이 개인(기업)정보의 수집·이용 및 제3자 제공에 관하여 귀하의 동의를 얻고자 합니다.
				</td>
			</tr>
			<tr>
				<td>
					1. 수집․이용에 관한 사항
					<br/>
					<br/>
  					&nbsp;&nbsp;□ 수집·이용 목적
  					<br/>
  					<br/>
   					&nbsp;&nbsp;&nbsp;&nbsp;◦ ｢지식재산권의 직무발명신고」와 관련하여 출원 및 등록 업무와 실적 관리를 목적으로 합니다.
   					<br/>
   					<br/>
  					&nbsp;&nbsp;□ 수집·이용할 항목
  					<br/>
   					<br/>
   					&nbsp;&nbsp;&nbsp;&nbsp;◦ 필수항목
   					<br/>
   					<br/>
    				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 개인식별정보(성명, 소속(직위), 생년월일, 성별, 주소, 전화번호, 휴대폰 번호, 이메일 주소 등)
    				<br/>
   					<br/>
					&nbsp;&nbsp;□ 보유․이용기간
					<br/>
   					<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;◦ 위 개인정보는 인제대학교 지식재산권관리시스템에서 일회성으로 처리하여 DB에 저장하지 않습니다.
					<br/>
   					<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;◦ 단, 아래의 항목은 지식재산권 실적 관리를 목적으로 영구 보관할 수 있습니다.
					<br/>
   					<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 성명, 소속(직위)
					<br/>
   					<br/>
					&nbsp;&nbsp;□ 동의를 거부할 권리 및 동의를 거부할 경우의 불이익
					<br/>
   					<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;◦ 위 개인정보 중 필수항목의 수집․이용에 관한 동의는 지식재산권 출원 및 등록 업무 수행을 위해 필수적이므로 이에 동의하셔야 이후 절차를 진행할 수 있습니다.
					<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;단, 동의하지 않으시는 경우 업무지연 및 행정오류가 발생할 수 있습니다.
					<br/>
   					<br/>
					 2. 제3자 제공에 관한 사항
					<br/>
   					<br/>
					&nbsp;&nbsp;□ 제공받는 자
					<br/>
   					<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;◦ 특허청, 특허대리인(특허법률사무소 등)
					<br/>
   					<br/>
					&nbsp;&nbsp;□ 제공받는 자의 이용 목적
					<br/>
   					<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;◦ 지식재산권 출원 및 등록 업무
					<br/>
   					<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;◦ 지식재산권 출원 및 등록 대리 업무
					<br/>
   					<br/>
					&nbsp;&nbsp;□ 제공할 개인정보의 항목
					<br/>
   					<br/>
					&nbsp;&nbsp;◦ 수집·이용에 동의한 정보 중 업무 목적 달성을 위해 필요한 정보에 한함
					<br/>
   					<br/>
					&nbsp;&nbsp;□ 제공받은 자의 개인정보 보유․이용 기간
					<br/>
   					<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;◦ 위 개인정보는 제공된 날부터 수집⋅이용 목적이 종료되는 때까지 보유⋅이용되며 단, 정보주체가 개인정보 삭제를 요청할 경우 지체 없이 파기합니다.
					<br/>
   					<br/>
					&nbsp;&nbsp;□ 동의를 거부할 권리 및 동의를 거부할 경우의 불이익
					<br/>
   					<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;◦ 위 개인정보의 제공 동의를 거부할 권리가 있으며, 동의를 거부 시 업무지연 및 행정오류가 발생할 수 있습니다.
				</td>
			</tr>
		</tbody>
	</table>
	<table>
		<tbody>
			<tr>
				<div class="buttonlist">
					<div class="left">
					</div>
					<div class = "right">
						<span class="ui_btn medium icon procBtn" id="closeBtn" onclick="window.close();" ><i class="fa fa-close"><a href="javascript:void(0)" data-term="B.CLOSE"></a></i></span>
					</div>
				</div>
			</tr>
		</tbody>
	</table>
</body>
</html>