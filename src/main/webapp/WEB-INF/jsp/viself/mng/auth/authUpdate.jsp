<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2022-07-19(019)
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
  -     권한 정보 수정
  --%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div class="container-fluid" id="authInfoUpdateRootLayer" data-opener-data="<c:out value='${param.openerData}'/>" data-json="<c:out value='${param.jsonData}'/>">
<%--    <h4 class="text-danger"><strong><i class="fa-sharp fa-solid fa-shield"></i> <span data-term="L.권한정보"></span></strong></h4>--%>
    <div id="authInfoUpdateTabs">
    </div>
</div>
<script src="<c:url value='/js/module/viself/mng/auth/authUpdate.js?v='/><c:out value='${sysTimeMillis}'/>"></script>