<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2022-07-25(025)
  -
  - Copyright 2022 JaYu.space
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
  -     사용자 수정
  --%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="userMngWriteRootLayer" class="container-fluid" data-opener-data="<c:out value='${param.openerData}'/>"
     data-json="<c:out value='${param.jsonData}'/>">
    <div class="p-2">
        <form class="form-horizontal" id="userMngWriteForm">
            <div class="row">
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label"><span data-term="L.ID"></span> <i class="fa fa-check text-danger"></i></label>
                        <div class="col-sm-10 particle-form">
                            <input type="text" class="particle-input" name="loginId" placeholder="login ID" data-col="loginId" maxlength="80" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label"><span data-term="L.이름"></span> <i class="fa fa-check text-danger"></i></label>
                        <div class="col-sm-10 particle-form">
                            <input type="text" class="particle-input" name="nmKo" placeholder="user name" data-col="userNm" maxlength="80" required>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">회사 <i class="fa fa-check text-danger"></i></label>
                        <div class="col-sm-10 particle-form">
                            <div class="input-group">
                                <input type="text" class="form-control form-control-sm" name="orgNm" placeholder="회사명" data-col="orgNm" maxlength="80" readonly>
                                <div class="input-group-append">
                                    <button type="button" class="btn btn-outline-primary btn-sm" id="userMngWriteOrgSchBtn"><i class="fas fa-search"></i></button>
                                </div>
                            </div>
                            <input type="hidden" class="particle-input" name="orgId" data-col="orgId" maxlength="80" required>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label" data-term="L.이메일"></label>
                        <div class="col-sm-10 particle-form">
                            <input type="text" class="particle-input" name="email" placeholder="email" data-col="email" maxlength="90">
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label" data-term="L.사용자권한"></label>
                        <div class="col-sm-10 particle-form">
                            <span id="userAuthArea"></span>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">사용여부</label>
                        <div class="col-sm-10 particle-form">
                            <label class="particle-check">
                                <input type="radio" name="useYn" value="Y" checked> Use
                            </label>
                            <label class="particle-check">
                                <input type="radio" name="useYn" value="N"> No Use
                            </label>
                        </div>
                        <%--<label class="col-sm-2 particle-label">정렬순서</label>
                        <div class="col-sm-4 particle-form">
                            <input type="text" class="particle-input" name="ordNo" placeholder="1~99" data-col="ordNo" maxlength="3">
                        </div>--%>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">메모</label>
                        <div class="col-sm-10 particle-form">
                            <textarea name="memo" id="memo" data-col="memo"
                                      style='display:none;'></textarea>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div class="row p-2">
        <div class="col-md-6">

        </div>
        <div class="col-md-6 text-right">
            <button type="button" class="btn btn-outline-primary btn-sm" id="userMngWriteCloseBtn"><i class="fas fa-times"></i> 닫기
            </button>
            <button type="button" class="btn btn-primary btn-sm" id="userMngWriteSaveBtn"><i class="fa fa-pencil"></i> 저장</button>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/tiles/include/jsRsaLink.jsp"%>
<script src="<c:url value='/js/module/support/user/userModify.js?v='/><c:out value='${sysTimeMillis}'/>"></script>