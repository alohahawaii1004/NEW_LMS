<%@ page import="com.vertexid.commons.utils.HtmlUtil" %>
<%@ page import="com.vertexid.spring.utils.SessionUtils" %>
<%@ page import="com.vertexid.commons.utils.StringUtil" %>
<%@ page import="com.vertexid.viself.hr.SysLoginVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    SysLoginVO loginUser = (SysLoginVO) SessionUtils.getLoginVO();
    String siteLocale = loginUser.getSiteLocale();
    String capitalizeSiteLocale = StringUtil.capitalize(siteLocale.toLowerCase());// ko >> Ko
%>
<style>
    .text-area{
        min-height: 250px;
        padding: 5px;
        border-bottom: dashed 1px #d4edfe;
    }
    #wrapper_<c:out value='${param.bbsUid}'/>{
        height: auto;
        min-height: 100%;
        padding-bottom: 34px;
    }
    #footer_<c:out value='${param.bbsUid}'/>{
        height: 34px;
        position : relative;
        transform : translateY(-100%);
    }
</style>
<div id="bbsView_<c:out value='${param.bbsUid}'/>">
    <form name="bbsViewForm" id="bbsViewForm_<c:out value='${param.bbsUid}'/>" method="post" data-opener-data="<c:out value='${param.openerData}'/>" data-cbkey="<c:out value='${param.cbKey}'/>">
        <input type="hidden" name="bbsUid" value="<c:out value='${param.bbsUid}'/>">
        <input type="hidden" name="_csrf" value="<c:out value='${_csrf.token}'/>">
    </form>
<%--    <h4><strong data-col="bbsTit_<c:out value='${param.bbsUid}'/>"></strong></h4>--%>
    <div id="wrapper_<c:out value='${param.bbsUid}'/>">
        <div data-col="bbsContent_<c:out value='${param.bbsUid}'/>" class="text-area"></div>
        <div>
            <div id="bbsFile_<c:out value='${param.bbsUid}'/>"></div>
        </div>
    </div>
    <div id="footer_<c:out value='${param.bbsUid}'/>">
        <div class="text-right" id="buttonDiv_<c:out value='${param.bbsUid}'/>">
            <button type="button" class="btn btn-default" id="btnClose_<c:out value='${param.bbsUid}'/>" data-term="B.CLOSE"><i class="fa fa-close"></i> </button>
        </div>
    </div>
    <%--<table class="basic">
        <colgroup>
            <col style="width:15%;">
            <col style="width:35%;">
            <col style="width:15%;">
            <col style="width:35%;">
        </colgroup>
        <tr>
            <th data-term="L.제목" style="text-align:center"></th>
            <td colspan="3" data-col="bbsTit_<c:out value='${param.bbsUid}'/>">
            </td>
        </tr>
        <tr style="display: none;">
            <th data-term="L.작성일" style="text-align:center"></th>
            <td data-col="strRegDte_<c:out value='${param.bbsUid}'/>"></td>
            <th data-term="L.작성자" style="text-align:center"></th>
            <td data-col="regDspNm_<c:out value='${param.bbsUid}'/>"></td>
        </tr>
        <tr>
            <th data-term="L.내용" style="text-align:center"></th>
            <td colspan="3">
                <div data-col="bbsContent_<c:out value='${param.bbsUid}'/>" class="text-area"></div>
            </td>
        </tr>
        <tr>
            <th data-term="L.첨부파일" style="text-align:center"></th>
            <td colspan="3" id="bbsFile_<c:out value='${param.bbsUid}'/>"></td>
        </tr>
    </table>
    <div class="buttonlist">
        <div class="left" style="display:none;margin-right:20px;" id="checkDiv_<c:out value='${param.bbsUid}'/>">
        </div>
        <div class="right" id="buttonDiv_<c:out value='${param.bbsUid}'/>">
            <button type="button" class="btn btn-default" id="btnClose_<c:out value='${param.bbsUid}'/>" data-term="B.CLOSE"><i class="fa fa-close"></i> </button>
        </div>
    </div>--%>
</div>
<script src="<c:url value='/js/module/paragon/bbs/bbsPopup.js' />"></script>
<script>
    $(document).ready(function(){
        var bbsMasView = new BbsPopup("<c:out value='${param.bbsUid}'/>");
        bbsMasView.init();
    });
</script>