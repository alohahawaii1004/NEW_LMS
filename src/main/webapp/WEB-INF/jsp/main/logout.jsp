<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2023-04-06
  -
  - Copyright 2023 JAYU.space
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
<div class="container-fluid text-center">
    <h1 class="">You have been logged out</h1>
    <h3 class="display-3">Thank you</h3>
    <hr>
    <button type="button" class="btn btn-secondary" style="display: none;">GO LOGIN</button>
</div>
<script src="<c:url value='/js/module/main/logout.js?v='/><c:out value='${sysTimeMillis}'/>"></script>

<%
     //new com.nexacro.java.xapi.util.JarInfo().info(out);
%>
