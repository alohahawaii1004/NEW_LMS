<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2022-07-25(025)
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
  -     인사정보 사용자 뷰
  --%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="userMngViewRootLayer" class="container-fluid" data-opener-data="<c:out value='${param.openerData}'/>"
     data-json="<c:out value='${param.jsonData}'/>">
    <form class="form-horizontal" method="post" id="userMngViewForm">
        <input type="hidden" name="popupTitle">
        <input type="hidden" name="openerData">
        <input type="hidden" name="_csrf" value="<c:out value='${_csrf.token}'/>">
    </form>
    <div class="p-2">
        <div class="row">
            <div class="col-md-9">
                <h3><i class="fa-solid fa-user"></i> <span data-col="userNm"></span> </h3>
            </div>
            <div class="col-md-3 text-right">
                <span class="badge badge-default" data-col="useYn"></span>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <a class="badge badge-light btn btn-default disabled" id="userMngViewGoCustomer"><i class="fa-regular fa-building text-orange"></i> <span data-col="orgNm"></span></a>
            </div>
            <div class="col-md-6 text-right">
            </div>
        </div>
        <p>
            <button type="button" class="btn btn-sm btn-link" data-toggle="collapse" data-target="#memo" aria-expanded="false" aria-controls="memo"><i class="fa-solid fa-angle-down"></i> 비고</button>
        </p>
        <div class="collapse" id="memo">
            <div class="card card-body">
                <div>
                    ID: <span data-col="loginId" class="text-monospace"></span>
                </div>
                <div data-col="memo" id="memoEditor" ></div>
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-md-9">
                <span class="text-bold"><i class="fa-solid fa-fingerprint"></i> Authorities</span>
            </div>
            <div class="col-md-3 text-right">

            </div>
            <div class="col-md-12">
                <span id="userRoles"></span>
            </div>
        </div>
    </div>
    <div class="row p-2">
        <div class="col-md-6">

        </div>
        <div class="col-md-6 text-right">
            <button type="button" class="btn btn-outline-primary btn-sm" id="userMngViewCloseBtn"><i class="fas fa-times"></i> 닫기
            </button>
            <button type="button" class="btn btn-outline-danger btn-sm" id="userMngViewChangePwBtn" style="display: none;"><i class="fa-solid fa-shield-halved"></i> 패스워드 변경</button>
            <button type="button" class="btn btn-primary btn-sm" id="userMngViewGoModifyBtn"><i class="fa fa-pencil"></i> 수정</button>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/tiles/include/jsRsaLink.jsp"%>
<script src="<c:url value='/js/module/support/user/userView.js?v='/><c:out value='${sysTimeMillis}'/>"></script>