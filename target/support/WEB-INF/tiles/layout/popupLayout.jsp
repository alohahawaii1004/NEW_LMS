<%--
  - Author: Yang, Ki Hwa
  -
  -
  - Copyright 2020 JAYU.space
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
  - Description: HTML5 기반의 로그인 페이지 템플릿
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <!-- meta info -->
        <%@include file="/WEB-INF/tiles/include/metaInfo.jsp"%>
        <!-- style links -->
        <%@include file="/WEB-INF/tiles/include/cssLink.jsp"%>
        <!-- js links -->
        <%@include file="/WEB-INF/tiles/include/jsLink.jsp"%>
        <%@include file="/WEB-INF/tiles/include/jsRsaLink.jsp"%>
    </head>
    <body onload="window.focus();">
    <nav class="navbar navbar-vidblack" id="popupNavbar" style="display:none">
        <h3 class="card-title text-bold" id="popupTitle" data-term="<c:out value="${param.popupTitle}"/>"></h3>
        <div class="card-tools">
            <button type="button" class="btn btn-vidblack rounded-circle" id="popupCloseBtn">
                <i class="fas fa-times"></i>
            </button>
        </div>
    </nav>
    <tiles:insertAttribute name="page.body"/>
    </body>
    <script>
        $(function(){
            "use strict";
            var $popupNavbar = $("#popupNavbar");
            if(paragonCmm.isNotEmpty($("#popupTitle").data("term"))){
                $popupNavbar.show();
                paragonCmm.convertLang($popupNavbar);
                $("#popupCloseBtn").on("click", function(){
                    self.close();
                });
            }
        });
    </script>
</html>
