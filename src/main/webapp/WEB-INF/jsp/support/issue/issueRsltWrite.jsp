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
  -    이슈 완료(결과보고)
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="issueRsltWriteRootLayer" class="container-fluid" data-opener-data="<c:out value='${param.openerData}'/>"
     data-json="<c:out value='${param.jsonData}'/>">
    <div class="p-2">
        <form class="form-horizontal" name="issueRsltWriteForm" id="issueRsltWriteForm">
            <input type="hidden" name="issueId" data-col="issueId">
            <div class="row">
                <div class="col-md-12 particle-row">
                    <h3><i class="fa-solid fa-headset"></i> <small class="text-sm" data-col="reqNo"></small> <span data-col="reqIssueTitle"></span>  </h3>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">이슈 구분 <i class="fa fa-check text-danger"></i></label>
                        <div class="col-sm-4 particle-form">
                            <select name="rsltRealIssueTpCd" class="particle-input" style="width:98%;"></select>
                        </div>
                        <label class="col-sm-2 particle-label">이슈 분야 <i class="fa fa-check text-danger"></i></label>
                        <div class="col-sm-4 particle-form">
                            <select name="rsltRealIssueDomainCd" class="particle-input" style="width:98%;"></select>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">실제 공수(MD)</label>
                        <div class="col-sm-4 particle-form">
                            <input type="text" class="form-control particle-input" name="rsltRealManday" placeholder="real working days"
                                   data-col="rsltRealManday" maxlength="5">
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">작업결과</label>
                        <div class="col-sm-10 particle-form">
                            <textarea name="rsltRport" id="rsltRport" data-col="rsltRport" style='display:none;'></textarea>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">첨부파일</label>
                        <div class="col-sm-10 particle-form">
                            <div id="issueRsltFile"></div>
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
            <button type="button" class="btn btn-outline-primary btn-sm" id="issueRsltWriteCloseBtn"><i
                    class="fas fa-times"></i> 닫기
            </button>
            <button type="button" class="btn btn-primary btn-sm" id="issueRsltWriteSaveBtn"><i class="fa fa-pencil"></i>
                완료
            </button>
        </div>
    </div>
</div>
<script src="<c:url value='/js/module/support/issue/issueRsltWrite.js?v='/><c:out value='${sysTimeMillis}'/>"></script>