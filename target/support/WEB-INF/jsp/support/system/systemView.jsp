<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2023-03-26
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
<div id="systemMngViewRootLayer" class="container-fluid" data-opener-data="<c:out value='${param.openerData}'/>"
     data-json="<c:out value='${param.jsonData}'/>">
    <form class="form-horizontal" method="post" id="systemMngViewForm">
        <input type="hidden" name="popupTitle">
        <input type="hidden" name="openerData">
        <input type="hidden" name="_csrf" value="<c:out value='${_csrf.token}'/>">
    </form>
    <div class="p-2">
        <div class="row">
            <div class="col-md-9">
                <h3><i class="fa-solid fa-computer text-lightblue"></i> <span data-col="systemName"></span> <small class="text-sm" data-col="productTpNm"></small> </h3>
            </div>
            <div class="col-md-3 text-right">
                <span class="badge badge-default" data-col="supportDate"></span>
                <span class="badge badge-default" data-col="useYn"></span>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <a class="badge badge-light btn btn-default" id="systemMngViewGoCustomer"><i class="fa-regular fa-building text-orange"></i> <span data-col="orgNm"></span></a>
            </div>
            <div class="col-md-6 text-right">
                <span id="sysManagers"></span>
                <span class="badge badge-default btn btn-default" style="background-color:#B8D7FF; color: #003A9D;" id="sysMgrAddBtn"><i class="fa-solid fa-user-tie"></i></span>
            </div>
        </div>
        <p>
            <button type="button" class="btn btn-sm btn-link" data-toggle="collapse" data-target="#sysDesc" aria-expanded="false" aria-controls="sysDesc"><i class="fa-solid fa-angle-down"></i> 비고</button>
        </p>
        <div class="collapse" id="sysDesc">
            <div class="card card-body">
                <div data-col="sysDesc" id="sysDescEditor" ></div>
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-md-9">
                <span class="text-bold"><i class="fa-solid fa-user-group"></i> Users</span>
                <span class="badge badge-default btn btn-default" style="background-color:#B8D7FF; color: #003A9D;" id="sysUsrNewBtn"><i class="fa-solid fa-plus"></i></span>
                <span class="badge badge-outline-default btn btn-default" style="background-color:#B8D7FF; color: #003A9D;" id="sysUsrAddBtn"><i class="fa-solid fa-user-group"></i></span>
            </div>
            <div class="col-md-3 text-right">

            </div>
        </div>
        <div class="table-responsive-sm">
            <table class="table table-sm table-hover text-sm border-bottom" id="sysUserTable">
                <colgroup>
                    <col style="width: 12%;">
                    <col style="width: 12%;">
                    <col style="width: 12%;">
                    <col style="width: 12%;">
                    <col style="width: 42%;">
                    <col style="width: 5%;">
                    <col style="width: 5%;">
                </colgroup>
                <thead>
                <tr>
                    <th>고객사 아이디</th>
                    <th>시스템 아이디</th>
                    <th>사용자 아이디</th>
                    <th>사용자 이름</th>
                    <th class="text-white">인증서</th>
                    <th>보안</th>
                    <th>삭제</th>
                </tr>
                </thead>
                <tbody id="sysUserBody"></tbody>
            </table>
            <input type="hidden" name="page" id="sysUserListPage">
            <div id="sysUserListPagination"></div>
        </div>
    </div>
    <div class="row p-2">
        <div class="col-md-6">

        </div>
        <div class="col-md-6 text-right">
            <button type="button" class="btn btn-outline-primary btn-sm" id="systemMngViewCloseBtn"><i class="fas fa-times"></i> 닫기
            </button>
            <button type="button" class="btn btn-primary btn-sm" id="systemMngViewGoModifyBtn"><i class="fa fa-pencil"></i> 수정</button>
        </div>
    </div>
</div>
<script src="<c:url value='/js/module/support/system/systemView.js?v='/><c:out value='${sysTimeMillis}'/>"></script>