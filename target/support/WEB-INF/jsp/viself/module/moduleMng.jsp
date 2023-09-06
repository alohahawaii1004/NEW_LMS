<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2021-03-16(016)
  -
  - Copyright 2021 JAYU.space
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
  -      모듈 관리
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="moduleMngRootLayer">
    <div class="clearfix">
        <div class="row">
            <div class="col-md-6 p-1" id="moduleMngLayer1">
                <h6><i class="fa-regular fa-square-caret-right"></i> 모듈목록 </h6>
                <form id="moduleMngListFrm1" name="moduleMngListFrm1">
                    <!-- module list -->
                    <table id="moduleMngList1"></table><!-- // module list -->
                </form>
                <div class="p-1">
                    <button id="moduleMngAddRowBtn1" type="button" title="추가" class="btn btn-primary btn-sm i18n" data-term="L.추가"><i class="fa fa-plus"></i> </button>
                </div>
            </div>
            <div class="col-md-6 p-1" id="moduleMngLayer2">
                <h6><i class="fa-regular fa-square-caret-right"></i> 모듈URL</h6>
                <form id="moduleMngSchFrm2" name="moduleMngSchFrm2" class="d-none">
                    <input type="hidden" name="moduleId" class="">
                    <button type="button" id="moduleMngSearchBtn2" class="btn-primary"><i class="fa fa-search"></i></button>
                </form>
                <form id="moduleMngListFrm2" name="moduleMngListFrm2">
                    <table id="moduleMngList2"></table>
                </form>
                <div class="p-1">
                    <button type="button" title="추가" class="btn btn-sm btn-primary i18n" id="moduleMngAddRowBtn2" data-term="L.추가"><i class="fa fa-plus"></i> </button>
                    <button type="button" title="삭제" class="btn btn-sm btn-outline-danger i18n" id="moduleMngDeleteRowBtn2" data-term="L.삭제"><i class="fa fa-minus"></i> </button>
                    <%--            <button id="moduleMngExcelBtn2" type="button" title="WRD.엑셀" class="btn btn-theme btn-sm i18n" title="to excel" data-i18n="WRD.엑셀"><i class="fa fa-save"></i></button>--%>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="<c:url value='/js/module/viself/module/moduleMng.js'/>"></script>