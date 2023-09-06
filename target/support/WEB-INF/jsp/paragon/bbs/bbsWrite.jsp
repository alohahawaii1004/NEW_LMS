<%@ page import="com.vertexid.spring.utils.SessionUtils"%>
<%@ page import="com.vertexid.commons.utils.DateUtil"%>
<%@ page import="com.vertexid.viself.hr.SysLoginVO" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<%
    SysLoginVO loginUser =     (SysLoginVO) SessionUtils.getLoginVO();

    // SSC coding
    String chkAuthorities = "LMS_BCD,LMS_BJD,CMM_SYS";
    if(!loginUser.containsAuthorities(chkAuthorities)){
        throw new RuntimeException("권한이 없습니다.");
    }
%>
<div class="" id="bbsWrite">
    <h5 class="sub1_title"><i class="fa fa-file-text"></i> <span data-col="bbsNm"></span></h5>
    <div>
        <form name="bbsWriteForm" id="bbsWriteForm" method="post" data-opener-data="<c:out value='${param.openerData}'/>">
            <input type="hidden" name="bbsUid" data-col="bbsUid">
            <input type="hidden" name="bbsTpCd" data-col="bbsTpCd">
            <input type="hidden" name="bbsCd" data-col="bbsCd">
            <input type="hidden" name="bbsMode" id="bbsMode">
            <table class="basic">
            <colgroup>
                <col style="width:15%;">
                <col style="width:35%;">
                <col style="width:15%;">
                <col style="width:35%;">
            </colgroup>
            <tr>
                <th data-term="L.등록일"></th>
                <td>
                    <span data-col="strRegDte"><%=DateUtil.getCurrentDate()%></span>
                </td>
                <th data-term="L.등록자"></th>
                <td>
                    <span data-col="regDspNm"><%=loginUser.getDspNmKo()%></span>
                </td>
            </tr>
            <tr>
                <th id="bbsHeaderTitle" data-term="L.제목"><i class="fa fa-check icon-required"></i> </th>
                <td colspan="3">
                    <input type="text" id="bbsTit" name="bbsTit" maxlength="255" class="form-control input-sm" data-col="bbsTit" value=""/>
                </td>
            </tr>
            <tr id="bbsViewCtgryLayer" style="display: none;">
                <th data-term="L.유형"><i class="fa fa-check icon-required"></i> </th>
                <td colspan="3">
                    <select name="bbsCtgryCd" data-col="bbsCtgryCd" class="form-control input-sm"></select>
                </td>
            </tr>
            <tr id="bbsViewTopYnLayer" style="display: none;">
                <th data-term="L.상단게시여부"></th>
                <td colspan="3">
                    <div class="form-check form-check-inline p-1">
                        <label class="form-check-label" data-term="L.사용"><input class="form-check-input" type="radio" id="bbsTopY" name="bbsTopYn" value="Y"> </label>
                        <label class="form-check-label" data-term="L.미사용"><input class="form-check-input" type="radio" id="bbsTopN" name="bbsTopYn" value="N" checked> </label>
                    </div>
                </td>
            </tr>
            <tr id="bbsViewPopupYnLayer" style="display: none;">
                <th data-term="L.메인팝업게시여부"></th>
                <td>
                    <div class="form-check form-check-inline p-1">
                        <label class="form-check-label" data-term="L.사용"><input class="form-check-input" type="radio" id="bbsPopupY" name="bbsPopupYn" value="Y"> </label>
                        <label class="form-check-label" data-term="L.미사용"><input class="form-check-input" type="radio" id="bbsPopupN" name="bbsPopupYn" value="N" checked> </label>
                    </div>
                </td>
                <th data-term="L.메인팝업기간"></th>
                <td>
                    <input type="text" name="bbsPopupStDte" id=bbsPopupStDte style="width: 125px; display: inline-block;" disabled>
                    <span>~</span>
                    <input type="text" name="bbsPopupEdDte" id="bbsPopupEdDte" style="width: 125px; display: inline-block;" disabled>
                </td>
            </tr>
            <tr>
                <th id="bbsHeaderCont" data-term="L.내용"></th>
                <td colspan="3">
                    <textarea id="bbsContent" name="bbsContent" data-col="content" style='display:none;'></textarea>
                    <div id="bbsContent"></div>
                </td>
            </tr>
            <tr>
                <th data-term="L.첨부"></th>
                <td colspan="3" id="bbsFile"></td>
            </tr>
        </table>
        <div class="buttonlist">
            <div class="right">                
                <button type="button" class="btn btn-primary btn-sm" id="btnSave" data-term="B.SAVE"><i class="fa fa-plus"></i> </button>
                <button type="button" class="btn btn-default btn-sm" id="btnClose" data-term="B.CLOSE"><i class="fa fa-close"></i> </button>
            </div>
        </div>
        </form>
    </div>
</div>
<script src="<c:url value='/js/module/paragon/bbs/bbsWrite.js?v='/><c:out value='${sysTimeMillis}'/>"></script>