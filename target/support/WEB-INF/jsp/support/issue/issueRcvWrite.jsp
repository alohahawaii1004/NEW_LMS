<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2023-03-30
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
  -     이슈 접수
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="issueRcvWriteRootLayer" class="container-fluid" data-opener-data="<c:out value='${param.openerData}'/>"
     data-json="<c:out value='${param.jsonData}'/>">
    <div class="p-2">
        <form class="form-horizontal" name="issueRcvWriteForm" id="issueRcvWriteForm">
            <input type="hidden" name="issueId" data-col="issueId">
            <div class="row">
                <div class="col-md-12 particle-row">
                    <h3><i class="fa-solid fa-headset"></i> <small class="text-sm" data-col="reqNo"></small> <span data-col="reqIssueTitle"></span>  </h3>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">이슈 구분 <i class="fa fa-check text-danger"></i></label>
                        <div class="col-sm-4 particle-form">
                            <select name="rcvIssueTpCd" class="particle-input" style="width:98%;"></select>
                        </div>
                        <label class="col-sm-2 particle-label">이슈 분야 <i class="fa fa-check text-danger"></i></label>
                        <div class="col-sm-4 particle-form">
                            <select name="rcvIssueDomainCd" class="particle-input" style="width:98%;"></select>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">예상 공수(MD)</label>
                        <div class="col-sm-4 particle-form">
                            <input type="text" class="form-control particle-input" name="rcvExpManday" placeholder="estimated working days"
                                   data-col="rcvExpManday" maxlength="5">
                        </div>
                        <label class="col-sm-2 particle-label">예상 완료일</label>
                        <div class="col-sm-4 particle-form">
                            <input type="text" name="schedulEndDte" class="form-control form-control-sm" style='width:200px;'>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">접수의견</label>
                        <div class="col-sm-10 particle-form">
                            <textarea name="rcvComment" id="rcvComment" data-col="rcvComment" style='display:none;'></textarea>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">첨부파일</label>
                        <div class="col-sm-10 particle-form">
                            <div id="issueRcvFile"></div>
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
            <button type="button" class="btn btn-outline-primary btn-sm" id="issueRcvWriteCloseBtn"><i
                    class="fas fa-times"></i> 닫기
            </button>
            <button type="button" class="btn btn-primary btn-sm" id="issueRcvWriteSaveBtn"><i class="fa fa-pencil"></i>
                접수
            </button>
        </div>
    </div>
</div>
<script src="<c:url value='/js/module/support/issue/issueRcvWrite.js?v='/><c:out value='${sysTimeMillis}'/>"></script>