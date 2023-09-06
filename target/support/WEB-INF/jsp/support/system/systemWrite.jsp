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
  -     (지원)시스템 등록
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="smSysMngWriteRootLayer" class="container-fluid" data-opener-data="<c:out value='${param.openerData}'/>"
     data-json="<c:out value='${param.jsonData}'/>">
    <div class="p-2">
        <form class="form-horizontal" id="smSysMngWriteForm">
            <input type="hidden" name="systemId" data-col="systemId">
            <div class="row">
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">회사 <i class="fa fa-check text-danger"></i></label>
                        <div class="col-sm-10 particle-form">
                            <div class="input-group">
                                <input type="text" class="form-control form-control-sm" name="orgNm" placeholder="회사명" data-col="orgNm" maxlength="80" readonly>
                                <div class="input-group-append">
                                    <button type="button" class="btn btn-outline-primary btn-sm" id="smSysMngWriteOrgSchBtn"><i class="fas fa-search"></i></button>
                                </div>
                            </div>
                            <input type="hidden" class="particle-input" name="orgId" data-col="orgId" maxlength="80" required>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">시스템명 <i class="fa fa-check text-danger"></i></label>
                        <div class="col-sm-10 particle-form">
                            <input type="text" class="particle-input" name="systemName" placeholder="system name" data-col="systemName" maxlength="80" required>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">시스템 구분 <i class="fa fa-check text-danger"></i></label>
                        <div class="col-sm-10 particle-form">
                            <select name="productTpCd" class="particle-input" style="width:98%;"></select>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">지원기간 <i class="fa fa-check text-danger"></i></label>
                        <div class="col-sm-10 particle-form">
                            <div class="form-inline">
                                <input type="text" name="smStartdt" class="form-control form-control-sm" style='width:46%;'>
                                <span class='p-1'> ~ </span>
                                <input type="text" name="smEnddt" class="form-control form-control-sm" style='width:46%;'>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">시스템 URL</label>
                        <div class="col-sm-10 particle-form">
                            <input type="text" class="particle-input" name="systemUrl" placeholder="system url" data-col="systemUrl" maxlength="80">
                        </div>
                    </div>
                </div>
                <%--<div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">사용여부</label>
                        <div class="col-sm-4 particle-form">
                            <label class="particle-check">
                                <input type="radio" name="useYn" value="Y" checked> Use
                            </label>
                            <label class="particle-check">
                                <input type="radio" name="useYn" value="N"> No Use
                            </label>
                        </div>
                        <label class="col-sm-2 particle-label">정렬순서</label>
                        <div class="col-sm-4 particle-form">
                            <input type="text" class="particle-input" name="ordNo" placeholder="1~99" data-col="ordNo" maxlength="3">
                        </div>
                    </div>
                </div>--%>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">비고</label>
                        <div class="col-sm-10 particle-form">
                            <textarea name="sysDesc" id="sysDesc" data-col="sysDesc"
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
            <button type="button" class="btn btn-outline-primary btn-sm" id="smSysMngWriteCloseBtn"><i class="fas fa-times"></i> 닫기
            </button>
            <button type="button" class="btn btn-primary btn-sm" id="smSysMngWriteSaveBtn"><i class="fa fa-pencil"></i> 등록</button>
        </div>
    </div>
</div>
<script src="<c:url value='/js/module/support/system/systemWrite.js?v='/><c:out value='${sysTimeMillis}'/>"></script>