<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2022-12-22(022)
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
  - Description:
  -
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div class="wrapper">
    <!-- Preloader -->
    <%--    <div class="preloader flex-column justify-content-center align-items-center">--%>
    <%--        <img class="animation__shake" src="<c:url value="/js/vendor/admin-lte/3.2.0/img/AdminLTELogo.png"/>" alt="AdminLTELogo" height="60" width="60">--%>
    <%--    </div>--%>
    <!-- Navbar -->
    <nav class="main-header navbar navbar-expand navbar-white navbar-light">
        <!-- Left navbar links -->
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" data-widget="pushmenu" href="#" role="button">
                    <div class="d-none d-sm-block" style="margin-top: -10px;">
                        <div class="row">
                            <span class="text-vidred h5"><strong>Vertex ID. 고객지원시스템</strong></span>
                        </div>
                        <div class="row" style="margin-top: -10px;">
                            <small class="text-vidred">VERTEX ID. CUSTOMER SUPPORT SYSTEM</small>
                        </div>
                    </div>
                    <div class="d-block d-sm-none">
                        <i class="fas fa-bars"></i>
                    </div>
                </a>
            </li>
        </ul>
        <!-- Right navbar links -->
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <%--                    <img src="dist/img/user2-160x160.jpg" class="img-circle elevation-2" alt="User Image">--%>
                    <%--                    <i class="fa fa-user"></i>--%>
                    <ul class="list-inline">
                        <li class="list-inline-item font-weight-bold text-info" id="userNm"></li>
                        <li class="list-inline-item"><span class="d-none d-sm-block"> 로그인 하였습니다.</span></li>
                    </ul>

                </a>
            </li>
            <c:if test="${loginInfo.containsAuthorities('CMM_SYS,SM_MNG,SM_ADMIN')}">
            <li class="nav-item d-none d-sm-block">
                <a class="nav-link" href="#" role="button" data-btn="changePwdBtn">
                    <i class="fa-solid fa-shield-halved"></i>
                </a>
            </li>
            </c:if>
            <li class="nav-item">
                <a class="nav-link text-danger" data-widget="logout" href="<c:url value='/logout'/>" role="button">
                    <i class="fa-solid fa-power-off"></i>
                </a>
            </li>
            <li class="nav-item d-none d-sm-block">
                <a class="nav-link" data-widget="fullscreen" href="#" role="button">
                    <i class="fas fa-expand-arrows-alt"></i>
                </a>
            </li>
            <%--            <li class="nav-item">--%>
            <%--                <a class="nav-link" data-widget="control-sidebar" data-controlsidebar-slide="true" href="#"--%>
            <%--                   role="button">--%>
            <%--                    <i class="fas fa-th-large"></i>--%>
            <%--                </a>--%>
            <%--            </li>--%>
        </ul>
    </nav>
    <!-- /.navbar -->

    <!-- Main Sidebar Container -->
    <aside class="main-sidebar sidebar-light-vidred elevation-4">
        <!-- Brand Logo -->
        <a href="<c:url value='/main/main'/>" class="brand-link text-center bg-vidred"><span class="h3">VID</span>
            <%--            <img src="dist/img/AdminLTELogo.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3"--%>
            <%--                 style="opacity: .8">--%>
                        <small class="brand-text font-weight-light">Support</small>
        </a>

        <!-- Sidebar -->
        <div class="sidebar">
            <!-- Sidebar Menu -->
            <nav class="mt-2" id="nav-accordion">
            </nav>
            <!-- /.sidebar-menu -->
        </div>
        <!-- /.sidebar -->
    </aside>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <div class="content-header mainContentHeader">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <h4 class="m-0 mainContentTitle"></h4>
                    </div><!-- /.col -->
                    <div class="col-sm-6">
                        <ol class="breadcrumb float-sm-right text-xs mainContentBreadcrumb">
                        </ol>
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.container-fluid -->
        </div>
        <!-- /.content-header -->

        <!-- Main content -->
        <section class="content">
            <div class="container-fluid mainContent">
            </div><!-- /.container-fluid -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <footer class="main-footer text-sm">
        <div class="row">
            <div class="col-md-6">
                <image src="<c:url value="/img/foot_logo.png"/>"> Copyright © Vertex ID. Customer Support System. All rights reserved.
            </div>
            <div class="col-md-6 text-right">
<%--                <span title="개인정보처리방침" id="privacyPolicy" class="badge badge-vidred" style="cursor: pointer;"><i class="fa-solid fa-shield-halved"></i> 개인정보처리방침</span>--%>
            </div>
        </div>
        <%--        <strong>Copyright &copy; 2014-2021 <a href="https://adminlte.io">AdminLTE.io</a>.</strong>--%>
        <%--        All rights reserved.--%>
    </footer>

    <!-- Control Sidebar -->
    <aside class="control-sidebar control-sidebar-dark">
        <!-- Control sidebar content goes here -->
    </aside>
    <!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->
<script src="<c:url value='/js/module/main/main.js'/>"></script>