<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2023-01-06(006)
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
  -     그리드 공용: 조회조건 설정
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<style>
    .scrolling-wrapper {
        overflow-x: auto;
    }
</style>
<div class="container-fluid" id="condsInfo" data-opener-data="<c:out value='${param.openerData}'/>">
    <div class="clearfix border rounded p-1">
        <div class="input-group input-group-sm">
            <input type="text" class="form-control" name="schCols" placeholder="검색어를 입력하면 필터링 됩니다.">
            <div class="input-group-prepend">
                <button type="button" class="btn btn-default btn-xs" id="schBtn"><i class="fa fa-search"></i></button>
            </div>
        </div>
    </div>
    <div class="clearfix border rounded bg-gray-light p-2">
        <div id="source" class="row">
        </div>
    </div>
    <div class="clearfix border rounded bg-gray-light p-2">
        <div class="row scrolling-wrapper m-2" id="target">
        </div>
        <small class="form-text text-danger"> 더블클릭시 선택해제됩니다.</small>
    </div>
    <div class="form-row p-1" id="btnLayer">
        <div class="col-md-6">
        </div>
        <div class="col-md-6 text-right">
            <button class="btn btn-default btn-sm" id="selectAllBtn"><i class="fa fa-check"></i> SELECT ALL</button>
            <button class="btn btn-default btn-sm" id="condsInit" data-term="B.INIT"><i class="fa fa-refresh"></i>
            </button>
            <button class="btn btn-primary btn-sm" id="condsSave" data-term="B.SAVE"><i class="fa fa-save"></i>
            </button>
        </div>
    </div>
</div>
<script src="<c:url value='/js/module/cmm/modal/schCondMngModal.js'/>"></script>