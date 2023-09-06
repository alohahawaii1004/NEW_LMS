<%@page import="com.vertexid.spring.utils.BaseProperties"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.vertexid.spring.utils.SessionUtils"%>
<%@page import="com.vertexid.commons.utils.CommonConstants"%>
<%@page import="com.vertexid.viself.hr.SysLoginVO"%>
<%@ page import="com.vertexid.viself.hr.SysLoginVO" %>
<%@include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<%
SysLoginVO loginUser = (SysLoginVO)SessionUtils.getLoginVO();
if(loginUser == null){
	loginUser = new SysLoginVO();
}
String siteLocale = loginUser.getSiteLocale();

%>
<input type="hidden" name="mailSendSol" value="<c:out value="${param.solMasUid}"/>" >
<input type="hidden" name="mailSendDoc" value="<c:out value="${param.docUid}"/>" >
<input type="hidden" name="mailSendStu" value="<c:out value="${param.stuCd}"/>" >

<!--
메일 링크주소 예시
http://127.0.0.1:8094/main/mailQuickLink?solMasUid=F6ECADEC87134879AEEC101C42B9AA60&docUid=F4C52DBD492FA468279234A374312440&stuCd=PR_IB_REQ_WRT
 -->
<script type="text/javascript">
var redirectPage = function(data){

    var url = "";
    if(data.type == 'fileDownload') {
        url = "/ips/ip/ipTepIndex.popup";

    }else if(data.todoType == "APRV"){
        url = "/paragon/def/defstuform/defStuFormTotal.popup";
    }else{

        if(data.stuCd.startsWith("CS")){
            url = "/ips/cs/csTepIndex.popup";

        }else if(data.stuCd.startsWith("PS")){
            url = "/ips/ps/psTepIndex.popup";

        }else if(data.stuCd.startsWith("DL")){
            url = "/ips/dl/dlTepIndex.popup";

        }else if(data.stuCd.startsWith("DF")){
            url = "/paragon/def/defstuform/defStuFormTotal.popup";

        }else if(data.stuCd.startsWith("TM")){
            url = "/ips/ip/ipTepIndex.popup";

        }else if(data.stuCd.startsWith("TH")){
            url = "/ips/ip/ipTepIndex.popup";

        }else if(data.stuCd.startsWith("BY")){
            url = "/ips/cost/costTepIndex.popup";

        }else if(data.stuCd.startsWith("PA")){
            url = "/ips/pa/paTepIndex.popup";
    //      url = "/paragon/def/defstuform/defStuFormTotal.popup";
        }else if(data.stuCd.startsWith("TT")){
            url = "/ips/tt/ttTepIndex.popup";
        }else if(data.stuCd.startsWith("PR")){
            url = "/ips/ip/ipTepIndex.popup";
        }else if(data.stuCd.startsWith("UP")){
            url = "/ips/up/upTepIndex.popup";
        }else if(data.stuCd.startsWith("VA")){
            url = "/ips/va/vaTepIndex.popup";
        }
    }

     var imsiForm = $("<form method='POST'>").attr("action",paragonCmm.getUrl(url));
     imsiForm.append($("<input type='hidden' name='solMasUid'>").val(data.solMasUid));
     imsiForm.append($("<input type='hidden' name='docUid'>").val(data.docUid));
     imsiForm.append($("<input type='hidden' name='stuCd'>").val(data.stuCd));
     imsiForm.append($("<input type='hidden' name='openerData'>").val(JSON.stringify(data)));
     imsiForm.append($("<input type='hidden' name='docUid'>").val(data.docUid));
     imsiForm.append($("<input type='hidden' name='_csrf'>").val($("meta[name='_csrf']").attr("content")));
/*      paragonCmm.openWindow("", "1280", "750", "POP_VIEW_"+data.solMasUid, "yes", "yes", "");
     imsiForm.attr("target","POP_VIEW_"+data.solMasUid); */
     imsiForm.appendTo("body");
     imsiForm.submit();
     imsiForm.remove();
}

var data = {
		solMasUid : $("input[name='mailSendSol']").val(),
		docUid : $("input[name='mailSendDoc']").val(),
		stuCd : $("input[name='mailSendStu']").val(),
}

redirectPage(data);

</script>