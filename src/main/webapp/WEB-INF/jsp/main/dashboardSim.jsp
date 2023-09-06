<%--
  -
  - Copyright 2022 JAYU.space
  -
  - Licensed under the Apache License, Version 2.0 (the "License");
  - you may not use this file except in compliance with the License.
  - You may obtain a copy of the License at
  -
  - http://www.apache.org/licenses/LICENSE-2.0
  -
  - Unless required by applicable law or agreed to in writing, software
  - distributed under the License is distributed on an "AS IS" BASIS,
  - WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  - See the License for the specific language governing permissions and
  - limitations under the License.
  -
  - @(#)
  - Description: 데시보드
  - accumulate(acc) : 누계/누적
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<%@page import="com.vertexid.spring.utils.SessionUtils"%>
<%@page import="com.vertexid.viself.hr.SysLoginVO"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>

<%
	SysLoginVO loginUser 	=  (SysLoginVO) SessionUtils.getLoginVO();
	String siteLocale		=   loginUser.getSiteLocale();
	String capitalizeSiteLocale = StringUtils.capitalize(siteLocale.toLowerCase());// ko >> Ko
%>
<%--
--%>
    <div class="row mt mb" id="dashboardStat" style="min-width:1610px">
    <!-- SERVER STATUS PANELS -->
    </div>
<%-- Todo 시작 --%>
    <div class="row" id="dashboardTodo" style="min-width:1610px">
    <!-- TWITTER PANEL -->
      <div class="col-md-12">
        <div class="white-panel pn-todo" style="min-height:820px;">
        <!----------------------------------->
          <ul class="nav nav-tabs">
            <li class="active"><a href="#todo" data-toggle="tab">심의 대상</a></li>
          </ul>
          <div class="tab-content">
          	<div class="buttonlist" style="height: 30px;">
				<div class="left" style="margin-left: 1%;">
					<h5 class="sub2_title" data-term="L.평가_문항"><i class="fa fa-caret-square-o-right"></i> </h5>
				</div>
				<div class="right" style="margin-right: 1%;">
					<span class="ui_btn medium icon" onClick="htmlUtils.formDownload('PIMS_심의위원_Manual.pdf');"><i class="fa fa-file"><a href="javascript:void(0)" >심의 메뉴얼</a></i></span>
					<span class="ui_btn medium icon" id="writeBtn" style="display: none;"><a href="javascript:void(0)" data-term="L.평가진행"></a></span>
				</div>
			</div>
            <div class="tab-pane fade in active" id="todo">
            <!---- TO DO 테이블 ----->
              <table class="table table-bordered table-striped" id="todoTbl">
                <thead>
                  <tr data-opt='{"onClick":"dashBoard.goView(\"@{solMasUid}\",\"@{dlIpMasUid}\",\"@{dlPpUid}\",\"@{relPpUid}\",\"@{ipSolMasUid}\",\"@{dlProgCd}\")"}'>
                  	<input type="hidden" id="solMasUid" name="solMasUid" value=""/>
                  	<input type="hidden" id="dlProgCd" name="dlProgCd" value=""/>
                    <th style="width:3%" data-opt='{"align":"center","col":"ROWSEQ"}'>No.</th>
                    <th style="width:10%" data-opt='{"align":"center","col":"dspNo"}' data-term="L.관리번호"></th>
                    <th style="width:6%" data-opt='{"align":"center","col":"dlAnnoLangCd", "formatter": "paragonCmm.getLang"}' data-term="L.발표/서면"></th>
                    <th style="width:*" data-opt='{"align":"left","col":"titKo"}' data-term="L.발명의명칭"></th>
                    <th style="width:15%" data-opt='{"align":"center","col":"reqDeptNm", "formatter": "paragonCmm.getLang"}' data-term="L.전공"></th>
                    <th style="width:10%"   data-opt='{"align":"center","col":"reqUserNm"}' data-term="L.주발명자"></th>
                    <th style="width:6%"   data-opt='{"align":"center","col":"evalScore"}' data-term="L.평가점수"></th>
                    <th style="width:25%" data-opt='{"align":"center","col":"remark"}' data-term="L.비고"></th>
                  </tr>
                </thead>
                <tbody id="todoTblBody">
                </tbody>
              </table>
              <%-- 페이지 목록 --%>
			  <div class="pagelist" id="todoTblPage"></div>
              <!---- /TO DO 테이블 ----->
            </div>
          <div class="tab-pane fade" id="test">Test area</div>
          </div>
          <!----------------------------------->
        </div>
      </div>
      <!-- / TWITTER PANEL -->
    </div>
    <!-- /row -->

<!-- 테이블템플릿  -->
<script id="headTmpl" type="text/x-jquery-tmpl">
	<th style="width:\${width}%">\${paragonCmm.getLang(langCd)}</th>
</script>
<script id="bodyTmpl" type="text/x-jquery-tmpl">
	<td style="text-align:center;"><a href="javascript:void(0);" onclick="dashBoard.loadPop('\${cd}')" data-row="\${cd}">\${cnt}</a></td>
</script>
<script id="bdTmpl" type="text/x-jquery-tmpl">
<thead>
	<tr id="statHead">
	</tr>
</thead>
	<tr id="statBody">
	</tr>
</script>
<%-- <link rel="stylesheet" href="<c:url value='/js/vendor/jqwidgets/12.0.1/styles/jqx.base.css'/>" type="text/css" /> --%>
<script src="<c:url value='/js/module/main/dashboardSim.js'/>?ver=<spring:eval expression="@cmmProperties.getProperty('js.version')"/>"></script>