<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2023-03-29
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
  -     시스템 SM 담당자 뷰
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="sysMgrViewRootLayer" class="container-fluid" data-opener-data="<c:out value='${param.openerData}'/>"
     data-json="<c:out value='${param.jsonData}'/>">
    <form class="form-horizontal" method="post" id="sysMgrViewForm">
        <input type="hidden" name="popupTitle">
        <input type="hidden" name="openerData">
        <input type="hidden" name="_csrf" value="<c:out value='${_csrf.token}'/>">
    </form>
    <div class="p-2">
        <div class="row">
            <div class="col-md-6">
                <h3><i class="fa-solid fa-user-tie"></i> <span data-col="mgrNm"></span> <small class="text-sm" data-col="loginId"></small> </h3>
            </div>
            <div class="col-md-6 text-right">
                <span class="badge badge-default" data-col="useYn"></span>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <span class="badge badge-light border rounded" id="sysMgrViewGoCustomer"><i class="fa-regular fa-building text-orange"></i> <span data-col="orgNm"></span></span>
            </div>
            <div class="col-md-6 text-right">
                <span class="badge badge-light btn btn-default" data-col="priorityNm"></span>
                <span class="badge badge-default btn btn-default" data-col="mailSndYn"><i class="fa-regular fa-envelope"></i></span>
            </div>
        </div>
    </div>
    <div class="row p-2">
        <div class="col-md-6">

        </div>
        <div class="col-md-6 text-right">
            <button type="button" class="btn btn-outline-danger btn-sm" id="sysMgrViewDeleteBtn"><i class="fas fa-minus"></i> 삭제
            </button>
            <button type="button" class="btn btn-primary btn-sm" id="sysMgrViewCloseBtn"><i class="fas fa-times"></i> 닫기
            </button>
        </div>
    </div>
</div>
<script src="<c:url value='/js/module/support/system/sysMgrView.js?v='/><c:out value='${sysTimeMillis}'/>"></script>