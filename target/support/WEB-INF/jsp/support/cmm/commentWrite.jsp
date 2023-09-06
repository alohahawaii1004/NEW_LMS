<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2023-04-04
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
  -     의견 작성
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="commentWriteRootLayer" class="container-fluid" data-opener-data="<c:out value='${param.openerData}'/>"
     data-json="<c:out value='${param.jsonData}'/>">
    <div class="p-2">
        <form class="form-horizontal" name="commentWriteForm" id="commentWriteForm">
            <input type="hidden" name="relId" data-col="relId">
            <input type="hidden" name="cmtId" data-col="cmtId">
            <input type="hidden" name="relTpCd" data-col="relTpCd">
            <div class="row">
                <div class="col-md-12 particle-row">
                    <h3><i class="fa-solid fa-comment-dots"></i> <small class="text-sm" data-col="reqNo"></small> <span data-col="reqIssueTitle"></span>  </h3>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">의견</label>
                        <div class="col-sm-10 particle-form">
                            <textarea name="cmtConts" id="cmtConts" data-col="cmtConts" style='display:none;'></textarea>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">첨부파일</label>
                        <div class="col-sm-10 particle-form">
                            <div id="commentFile"></div>
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
            <button type="button" class="btn btn-outline-primary btn-sm" id="commentWriteCloseBtn"><i
                    class="fas fa-times"></i> 닫기
            </button>
            <button type="button" class="btn btn-primary btn-sm" id="commentWriteSaveBtn"><i class="fa fa-pencil"></i>
                등록
            </button>
        </div>
    </div>
</div>
<script src="<c:url value='/js/module/support/cmm/commentWrite.js?v='/><c:out value='${sysTimeMillis}'/>"></script>