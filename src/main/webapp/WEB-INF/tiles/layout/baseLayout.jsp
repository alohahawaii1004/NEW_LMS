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
<%try{%>
    <%--
      - [options]
      - layout-fixed: footer 고정
      -
      - [촤측 메뉴 아이콘 표시]
      - sidebar-collapse: 좌측 사이드바 접기
      - 해당 클래스 없애면 아이콘 표시 하지 않음
      - ! 아이콘 표시 모드 일때 하위메뉴 indent 적용되지 않음
      - sidebar-mini: 좌측 사이드 메뉴가 줄어들었을 경우 아이콘 표시(일반크기 화면)
      - sidebar-mini-md: 좌측 사이드 메뉴가 줄어들었을 경우 아이콘 표시(중간크기 화면)
      - sidebar-mini-xs: 좌측 사이드 메뉴가 줄어들었을 경우 아이콘 표시(초소형 화면)
      --%>
    <body class="hold-transition sidebar-mini layout-fixed sidebar-collapse">
        <tiles:insertAttribute name="page.body"/>
    </body>
</html>
<%}catch(Exception e){e.printStackTrace();}%>
