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
  - Description: include용 JS Link
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<%--
  - ==========================================================
  - HTML5 Boilerplate 라이브러리
  - ----------------------------------------------------------
  --%>
<!-- modernizer -->
<script src="<c:url value='/js/vendor/modernizr-3.6.0.min.js'/>"></script>
<!-- plugins -->
<script src="<c:url value='/js/plugins.js'/>"></script>
<%--
  - ==========================================================
  - jqeury 라이브러리
  - ----------------------------------------------------------
  --%>
<!-- jqeury -->
<script src="<c:url value='/js/vendor/jquery/3.5.1/jquery-3.5.1.min.js'/>"></script>
<script src="<c:url value='/js/vendor/jquery/plugin/jquery-tablednd/1.0.3/jquery.tablednd.js'/>"></script>
<script src="<c:url value='/js/vendor/jquery/plugin/jquery-templates/1.4.2/jquery.tmpl.js'/>"></script>
<!-- jQuery - UI -->
<script src="<c:url value='/js/vendor/jquery-ui/1.13.2/jquery-ui.min.js'/>"></script>

<%--
  - ==========================================================
  - admin Lte 라이브러리
  - ----------------------------------------------------------
  --%>
<!-- jQuery -->
<%--<script src="<c:url value='/js/vendor/admin-lte/3.2.0/plugins/jquery/jquery.min.js'/>"></script>--%>
<!-- jQuery UI -->
<%--<script src="<c:url value='/js/vendor/admin-lte/3.2.0/plugins/jquery-ui/jquery-ui.min.js'/>"></script>--%>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
    $.widget.bridge('uibutton', $.ui.button)
</script>
<!-- Bootstrap 4 -->
<script src="<c:url value='/js/vendor/admin-lte/3.2.0/plugins/bootstrap/js/bootstrap.bundle.min.js'/>"></script>
<!-- ChartJS -->
<%--<script src="<c:url value='/js/vendor/admin-lte/3.2.0/plugins/chart.js/Chart.min.js'/>"></script>--%>
<!-- Sparkline -->
<%--<script src="<c:url value='/js/vendor/admin-lte/3.2.0/plugins/sparklines/sparkline.js'/>"></script>--%>
<!-- JQVMap -->
<script src="<c:url value='/js/vendor/admin-lte/3.2.0/plugins/jqvmap/jquery.vmap.min.js'/>"></script>
<script src="<c:url value='/js/vendor/admin-lte/3.2.0/plugins/jqvmap/maps/jquery.vmap.usa.js'/>"></script>
<!-- jQuery Knob Chart -->
<script src="<c:url value='/js/vendor/admin-lte/3.2.0/plugins/jquery-knob/jquery.knob.min.js'/>"></script>
<!-- daterangepicker -->
<script src="<c:url value='/js/vendor/admin-lte/3.2.0/plugins/moment/moment.min.js'/>"></script>
<%--<script src="<c:url value='/js/vendor/admin-lte/3.2.0/plugins/daterangepicker/daterangepicker.js'/>"></script>--%>
<!-- Tempusdominus Bootstrap 4 -->
<%--<script src="<c:url value='/js/vendor/admin-lte/3.2.0/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js'/>"></script>--%>
<!-- Summernote -->
<%--<script src="<c:url value='/js/vendor/admin-lte/3.2.0/plugins/summernote/summernote-bs4.min.js'/>"></script>--%>
<!-- overlayScrollbars -->
<%--<script src="<c:url value='/js/vendor/admin-lte/3.2.0/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js'/>"></script>--%>
<!-- AdminLTE App -->
<script src="<c:url value='/js/vendor/admin-lte/3.2.0/js/adminlte.js'/>"></script>
<!-- AdminLTE for demo purposes -->
<%--<script src="<c:url value='/js/vendor/admin-lte/3.2.0/js/demo.js'/>"></script>--%>
<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
<%--<script src="<c:url value='/js/vendor/admin-lte/3.2.0/js/pages/dashboard.js'/>"></script>--%>

<%--
  - ==========================================================
  - appliction 추가 라이브러리
  - ----------------------------------------------------------
  --%>
<!-- big.js: 자바스크립트 연산 라이브러리 -->
<script src="<c:url value='/js/vendor/bigjs/6.1.1/big.js'/>"></script>
<!-- jQWidgets -->
<%--<script src="<c:url value='/js/vendor/jqwidgets/13.1.0/jqx-all.js'/>"></script>--%>
<!-- js Storage -->
<script src="<c:url value='/js/vendor/js-storage/1.0.4/js.storage.js'/>"></script>

<!-- Ck Editor -->
<%--<script src="<c:url value='/js/vendor/ckeditor/4.21.0/ckeditor.js'/>"></script>--%>

<!-- 보안 : xss util https://github.com/leizongmin/js-xss -->
<script src="<c:url value='/js/vendor/js-xss/1.0.14/xss.min.js'/>"></script>

<!-- easy-ui -->
<script src="<c:url value='/js/vendor/jquery-easyui/1.9.15/jquery.easyui.min.js'/>"></script>
<script src="<c:url value='/js/vendor/jquery-easyui/1.9.15/locale/easyui-lang-ko.js'/>"></script>
<script src="<c:url value='/js/vendor/jquery-easyui/extension/datagrid-export/xlsx.full.min.js'/>"></script>
<script src="<c:url value='/js/vendor/jquery-easyui/extension/datagrid-export/datagrid-export.js'/>"></script>
<script src="<c:url value='/js/vendor/jquery-easyui/extension/datagrid-cellediting/datagrid-cellediting.js'/>"></script>
<script src="<c:url value='/js/vendor/jquery-easyui/extension/jquery-easyui-edatagrid/jquery.edatagrid.js'/>"></script>

<!-- [주의!] 아래는 순서 준수!! -->
<%-- 공통 스크립트 --%>
<script src="<c:url value='/js/vendor/paragon/4.5.0/plugins/jquery.js'/>"></script>
<script src="<c:url value='/js/vendor/paragon/4.5.0/plugins/easyui.js'/>"></script>
<script src="<c:url value='/js/vendor/paragon/4.5.0/paragon.cmm.min.js'/>"></script>
<script src="<c:url value='/js/vendor/paragon/4.5.0/paragon.config.js'/>"></script>
