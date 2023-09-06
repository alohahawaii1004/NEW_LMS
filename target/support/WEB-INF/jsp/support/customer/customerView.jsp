<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2023-03-13 013
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
  -     고객사 뷰
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="customerMngViewRootLayer" class="container-fluid" data-opener-data="<c:out value='${param.openerData}'/>"
     data-json="<c:out value='${param.jsonData}'/>">
    <form class="form-horizontal" method="post" id="customerMngViewForm">
        <input type="hidden" name="popupTitle">
        <input type="hidden" name="openerData">
        <input type="hidden" name="_csrf" value="<c:out value='${_csrf.token}'/>">
    </form>
    <div class="p-2">
        <div class="row">
            <div class="col-md-9">
                <h3><i class="fa-regular fa-building text-orange"></i> <span data-col="orgNm"></span></h3>
            </div>
            <div class="col-md-3 text-right">
                <span class="badge badge-default" data-col="useYn"></span>
            </div>
        </div>
        <p>
            <button type="button" class="btn btn-sm btn-link" data-toggle="collapse" data-target="#orgDesc" aria-expanded="false" aria-controls="orgDesc"><i class="fa-solid fa-angle-down"></i> 비고</button>
        </p>
        <div class="collapse" id="orgDesc">
            <div class="card card-body">
                <div data-col="orgDesc" id="orgDescEditor" ></div>
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-md-9">
                <span class="text-bold"><i class="fa-solid fa-computer"></i> System </span>
                <span class="badge badge-default btn btn-default" style="background-color:#B8D7FF; color: #003A9D;" id="addSystemBtn"><i class="fa-solid fa-plus"></i></span>
            </div>
            <div class="col-md-3 text-right">

            </div>
        </div>
        <div class="table-responsive-sm">
            <table class="table table-sm table-hover text-sm border-bottom" id="sysListTable">
                <colgroup>
                    <col style="width: 15%;">
                    <col style="width: 30%;">
                    <col style="width: 25%;">
                    <col style="width: 15%;">
                    <col style="width: 15%;">
                </colgroup>
                <thead>
                    <tr>
                        <th>아이디</th>
                        <th>시스템명</th>
                        <th>시스템구분</th>
                        <th>지원시작일</th>
                        <th>지원종료일</th>
                    </tr>
                </thead>
                <tbody id="sysListBody"></tbody>
            </table>
            <input type="hidden" name="page" id="sysListPage">
            <div id="sysListPagination"></div>
        </div>
    </div>
    <div class="row p-2">
        <div class="col-md-6">

        </div>
        <div class="col-md-6 text-right">
            <button type="button" class="btn btn-outline-primary btn-sm" id="customerMngViewCloseBtn"><i class="fas fa-times"></i> 닫기
            </button>
            <button type="button" class="btn btn-primary btn-sm" id="customerMngViewGoModifyBtn"><i class="fa fa-pencil"></i> 수정</button>
        </div>
    </div>
</div>
<script src="<c:url value='/js/module/support/customer/customerView.js?v='/><c:out value='${sysTimeMillis}'/>"></script>