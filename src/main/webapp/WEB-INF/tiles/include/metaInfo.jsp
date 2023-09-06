<%--
  - Author: Yang, Ki Hwa
  -
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
  - Description: 일반페이지용 메타정보
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<!--
  - bootstrap 4. AdminLTE 3.2 Dashboard (MIT License)
  -->
    <!-- default meta info -->
    <meta charset="UTF-8"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

    <meta name="referrer" content="strict-origin-when-cross-origin" />
    <!-- Responsive meta tag -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>

    <meta name="keywords" content=""/>


    <%-- CSRF Token --%>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <title><spring:eval expression="@cmmProperties.getProperty('sys.name')"/></title>
    <link rel="icon" href="<c:url value="/img/favicon.ico"/>" type='image/x-icon'>