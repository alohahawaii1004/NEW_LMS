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
  -     이슈요청 등록
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="issueReqWriteRootLayer" class="container-fluid" data-opener-data="<c:out value='${param.openerData}'/>"
     data-json="<c:out value='${param.jsonData}'/>">
    <div class="p-2">
        <form class="form-horizontal" name="issueReqWriteForm" id="issueReqWriteForm">
            <input type="hidden" name="reqNm" data-col="reqNm">
            <input type="hidden" name="issueId" data-col="issueId">
            <div class="row">
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">제목 <i class="fa fa-check text-danger"></i></label>
                        <div class="col-sm-10 particle-form">
                            <input type="text" class="particle-input" name="reqIssueTitle" placeholder="issue title"
                                   data-col="reqIssueTitle" maxlength="10000" required>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">시스템 <i class="fa fa-check text-danger"></i></label>
                        <div class="col-sm-10 particle-form">
                            <div class="input-group">
                                <input type="text" class="form-control form-control-sm" name="systemNm" placeholder="system" data-col="systemName" maxlength="80" readonly>
                                <div class="input-group-append">
                                    <button type="button" class="btn btn-outline-primary btn-sm" id="issueReqWriteSysSchBtn"><i class="fas fa-search"></i></button>
                                </div>
                            </div>
                            <input type="hidden" class="particle-input" name="systemId" data-col="systemId" maxlength="80" required>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">이슈 구분</label>
                        <div class="col-sm-4 particle-form">
                            <select name="reqIssueTpCd" class="particle-input" style="width:98%;"></select>
                        </div>
                        <label class="col-sm-2 particle-label">이슈 분야</label>
                        <div class="col-sm-4 particle-form">
                            <select name="reqIssueDomainCd" class="particle-input" style="width:98%;"></select>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">완료 희망일</label>
                        <div class="col-sm-10 particle-form">
                            <input type="text" name="reqEndDte" class="form-control form-control-sm" style='width:200px;'>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">요청내용</label>
                        <div class="col-sm-10 particle-form">
                            <textarea name="reqIssueConts" id="reqIssueConts" data-col="reqIssueConts" style='display:none;'></textarea>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">첨부파일</label>
                        <div class="col-sm-10 particle-form">
                            <div id="issueReqFile"></div>
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
            <button type="button" class="btn btn-outline-primary btn-sm" id="issueReqWriteCloseBtn"><i
                    class="fas fa-times"></i> 닫기
            </button>
            <button type="button" class="btn btn-primary btn-sm" id="issueReqWriteSaveBtn"><i class="fa fa-pencil"></i>
                요청
            </button>
        </div>
    </div>
</div>
<script src="<c:url value='/js/module/support/issue/issueReqWrite.js?v='/><c:out value='${sysTimeMillis}'/>"></script>