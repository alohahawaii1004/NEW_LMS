<%--
  - Author: Yang, Ki Hwa
  - Date: 20. 6. 25.
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
  - Description: ax5ui 용 HTML5 기반의 메인페이지 템플릿
  - @deprecated 사용하지 않음
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- meta info -->
        <%@include file="/WEB-INF/tiles/include/metaInfo.jsp"%>

        <!-- style links -->
        <%@include file="/WEB-INF/tiles/include/cssLink.jsp"%>
    </head>
    <body class="bg-light">
        <form id="logoutFrm" name="logoutFrm" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        </form>
        <nav class="navbar navbar-expand navbar-dark bg-secondary">
            <a href="<c:url value="/main"/>" class="navbar-brand font-weight-bold"> <i class="fa fa-piggy-bank"></i> PORCO ROSSO</a>
            <div class="nav-item">
                <a id="sidebarToggle" class="nav-link sidebar-toggle mr-3" href="#"><i class="fa fa-bars"></i></a>
<%--                <a class="nav-link sidebar-toggle" href="#"><span class="navbar-toggler-icon"></span></a>--%>
            </div>
            <div class="navbar-collapse collapse">
                <ul class="navbar-nav ml-auto">
                    <!--<li class="nav-item"><a href="#" class="nav-link"><i class="fa fa-envelope"></i> 5</a></li>
                    <li class="nav-item"><a href="#" class="nav-link"><i class="fa fa-bell"></i> 3</a></li>-->
                    <li class="nav-item dropdown">
                        <a href="#" id="dd_user" class="nav-link dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> <span id="loginNm"></span></a>
                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dd_user">
                            <a href="#" class="dropdown-item" id="profile">Profile</a>
                            <a href="#" class="dropdown-item" id="logout">Logout</a>
                        </div>
                    </li>
                    <!--<li class="nav-item">
                        <a href="#" class="nav-link" id="test"><i class="fa fa-cogs"></i> </a>
                    </li>-->
                </ul>
            </div>
        </nav><!-- top -->

        <div class="d-flex mh-100 mw-100 w-auto h-auto" id="mainConts">
            <%--@include file="/WEB-INF/tiles/include/left_menu.jsp"--%>
            <div class="sidebar dark-sidebar bg-dark" id="sidebar"></div>

            <div class="content p-1" id="dashboard"></div><!-- dashboard -->
            <div class="content" id="content" data-ax5docker="dockerContent" style="width: 100%;"></div><!-- content-->
        </div><!-- // content -->

<%--        <div off-canvas="left-menu right shift" class="sidebar sidebar-dark bg-dark" id="sidebar"></div>--%>

        <aside class="card ctrl-sidebar" id="ctrlSidebar">
            <div class="card-header clearfix">
                <div id="asideTitle" class="float-left h3"></div>
                <div class="float-right">
                    <button id="asideCloseBtn" type="button" title="WRD.닫기" class="btn 18n" data-i18n="WRD.닫기"><i class="fa fa-times"></i></button>
                </div>
            </div>
            <div class="card-body" id="asideBody">
            </div>
        </aside><!-- // ctrl sidebar: info or control -->

        <form id="MainPopupFrm1" name="MainPopupFrm1" class="hidden">
            <input type="hidden" name="popTitle">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        </form>
        <div id="CmmPopFrmDiv" class="hidden"></div>

        <!-- js links -->
        <%@include file="/WEB-INF/tiles/include/jsLink.jsp"%>
        <%--@include file="/WEB-INF/tiles/include/js_jayu_link.jsp"--%>
        <%@include file="/WEB-INF/tiles/include/jsRsaLink.jsp"%>
        <script type="text/javascript" src="<c:url value='/js/module/main/main.js'/>"></script>
    </body>
</html>