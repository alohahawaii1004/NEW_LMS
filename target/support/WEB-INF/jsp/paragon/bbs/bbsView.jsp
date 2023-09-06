<%@ page import="com.vertexid.spring.utils.SessionUtils" %>
<%@ page import="com.vertexid.viself.hr.SysLoginVO" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<%
    SysLoginVO loginUser = (SysLoginVO) SessionUtils.getLoginVO();
    String siteLocale = loginUser.getSiteLocale();
%>
<style>
    .text-area{
        min-height: 300px;
    }
</style>
<div class="p-3" id="bbsView">
    <h5 class="sub1_title"><i class="fa fa-file-text"></i> <span data-col="bbsNm"></span></h5>
    <form name="bbsViewForm" id="bbsViewForm" method="post" data-opener-data="<c:out value='${param.openerData}'/>" data-cbkey="<c:out value='${param.cbKey}'/>">
        <input type="hidden" name="bbsUid" value="<c:out value='${param.bbsUid}'/>">
        <input type="hidden" name="bbsTp" value="<c:out value='${param.bbsTpCd}'/>">
        <input type="hidden" name="bbsCd" value="<c:out value='${param.bbsCd}'/>">
        <input type="hidden" name="openerData">
        <input type="hidden" name="_csrf" value="<c:out value='${_csrf.token}'/>">
    </form>
    <table class="basic">
        <colgroup>
            <col style="width:15%;">
            <col style="width:35%;">
            <col style="width:15%;">
            <col style="width:35%;">
        </colgroup>
        <tr>
            <th data-term="L.등록일" style="text-align:center"></th>
            <td data-col="strRegDte"></td>
            <th data-term="L.등록자" style="text-align:center"></th>
            <td data-col="regDspNm"></td>
        </tr>
        <tr>
            <th data-term="L.수정일" style="text-align:center"></th>
            <td data-col="strUptDte"></td>
            <th data-term="L.수정자" style="text-align:center"></th>
            <td data-col="uptDspNm"></td>
        </tr>
        <tr>
            <th data-term="L.제목" style="text-align:center"></th>
            <td colspan="3" data-col="bbsTit">
            </td>
        </tr>
        <tr id="bbsViewCtgryLayer" style="display: none;">
            <th data-term="L.유형" style="text-align:center"></th>
            <td colspan="3" data-col="bbsCtgryNm">
            </td>
        </tr>
        <tr id="bbsViewTopYnLayer" style="display: none;">
            <th data-term="L.상단게시여부" style="text-align:center"></th>
            <td colspan="3" data-col="bbsTopYn"></td>
        </tr>
        <tr id="bbsViewPopupYnLayer" style="display: none;">
            <th data-term="L.메인팝업게시여부" style="text-align:center"></th>
            <td data-col="bbsPopupYn">
                <span data-col="strBbsPopupYn"></span>
            </td>
            <th data-term="L.메인팝업기간" style="text-align:center"></th>
            <td>
                <span data-col="strBbsPopupStDte"></span>
                <span>~</span>
                <span data-col="strBbsPopupEdDte"></span>
            </td>
        </tr>
        <tr>
            <th id="bbsHeaderCont" data-term="L.내용" style="text-align:center"></th>
            <td colspan="3">
                <div data-col="bbsContent" class="text-area"></div>
            </td>
        </tr>
        <tr>
            <th data-term="L.첨부파일" style="text-align:center"></th>
            <td colspan="3" id="bbsFile"></td>
        </tr>
    </table>
    <div class="buttonlist">
        <div class="left" style="display:none;margin-right:20px;" id="checkDiv">
            <%--                <!-- <input type="checkbox" id="showCheck" />&nbsp; 오늘 더 이상 보지 않기 -->--%>
            <%--                &nbsp;<%=HtmlUtil.getInputCheckbox(true, "deadline", "toDate|"+"&nbsp;&nbsp;오늘 더 이상 보지 않기", "", "onclick=\"javascript:closeWin(this.value)\"","style=\"color:blue;\"")%>--%>
        </div>
        <div class="right" id="buttonDiv">            
            <button type="button" class="btn btn-primary btn-sm" id="btnModify" data-term="B.MODIFY" style="display: none;"><i class="fa fa-pencil"></i> </button>
            <button type="button" class="btn btn-default btn-sm" id="btnDelete" data-term="B.DELETE" style="display: none;"><i class="fa fa-minus"></i> </button>
            <button type="button" class="btn btn-default btn-sm" id="btnClose" data-term="B.CLOSE"><i class="fa fa-close"></i> </button>
        </div>
    </div>
</div>
<script src="<c:url value='/js/module/paragon/bbs/bbsView.js?v='/><c:out value='${sysTimeMillis}'/>"></script>