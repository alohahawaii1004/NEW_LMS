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
  -     시스템 신규 사용자 추가
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="sysUsrWriteRootLayer" class="container-fluid" data-opener-data="<c:out value='${param.openerData}'/>"
     data-json="<c:out value='${param.jsonData}'/>">
    <div class="p-2">
        <form class="form-horizontal" id="sysUsrWriteForm" name="sysUsrWriteForm">
            <input type="hidden" name="orgId" data-col="orgId">
            <input type="hidden" name="systemId" data-col="systemId">
            <div class="row">
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">사용자 이름 <i class="fa fa-check text-danger"></i></label>
                        <div class="col-sm-10 particle-form">
                            <input type="text" class="particle-input" name="nmKo" placeholder="user name"
                                   data-col="nmKo" maxlength="80" required>
                        </div>
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
            <button type="button" class="btn btn-outline-primary btn-sm" id="sysUsrWriteCloseBtn"><i
                    class="fas fa-times"></i> 닫기
            </button>
            <button type="button" class="btn btn-primary btn-sm" id="sysUsrWriteSaveBtn"><i class="fa fa-pencil"></i>
                등록
            </button>
        </div>
    </div>
</div>
<script src="<c:url value='/js/module/support/system/sysUserWrite.js?v='/><c:out value='${sysTimeMillis}'/>"></script>