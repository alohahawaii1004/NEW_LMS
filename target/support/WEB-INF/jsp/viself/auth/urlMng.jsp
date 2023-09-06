<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2021-03-31(031)
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
  -     접근 URL 등록 관리
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="urlMngRootLayer">
    <%-- search conditions --%>
    <div class="row">
        <div class="col-md-6">
        </div>
        <div class="col-md-6 text-right">
        </div>
    </div>
    <div class="clearfix border rounded bg-gray-light p-2">
        <div class="row">
            <div class="col-md-11">
                <form id="urlMngSchFrm0" method="post" class="form-horizontal">
                    <div class="row" id="bbsSearchArea">
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block" data-term="L.검색어"></label>
                                <div class="col-sm-9">
                                    <input type="text" name="schWord" id="schWord" class="form-control form-control-sm" placeholder="Search Word">
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block">AccessType</label>
                                <div class="col-sm-9">
                                    <select id="urlMngSelect1" name="alwDiv" class="form-control form-control-sm" data-type="search" ></select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block">Use</label>
                                <div class="col-sm-9">
                                    <select id="urlMngSelect2" name="useYn" class="form-control form-control-sm" data-type="search"></select>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-md-1 d-none d-sm-block align-self-center">
                <div class="row p-1">
                    <button class="btn btn-outline-primary btn-sm p-1" type="button" id="urlMngResetBtn1"><i class="fa fa-undo"></i> RESET</button>
                </div>
                <div class="row p-1">
                    <button class="btn btn-primary btn-sm p-1" type="button" id="urlMngSearchBtn1"><i class="fa fa-search"></i> SEARCH</button>
                </div>
            </div>
            <div class="col-sm-3 d-block d-sm-none">
                <button class="btn btn-primary btn-sm" type="button" id="urlMngSearchSmBtn"><i class="fa fa-search"></i> SEARCH</button>
                <button class="btn btn-outline-primary btn-sm" type="button" id="urlMngResetSmBtn"><i class="fa fa-undo"></i> RESET</button>
            </div>
        </div>
    </div>
    <%-- data table --%>
    <div class="data-table clearfix">
        <div class="row p-1">
            <div class="col-md-6">
            </div>
            <div class="col-md-6 text-right">
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <table id="urlMngList1"></table>
            </div>
        </div>
        <div class="row p-1">
            <div class="col-md-6">
                <div class="main-btn-grp1">
                    <button type="button" class="btn btn-sm btn-primary" id="urlMngAddRowBtn1" data-term="L.추가"><i class="fa fa-plus"></i> </button>
                    <button type="button" class="btn btn-sm btn-outline-danger" id="urlMngDeleteRowBtn1" data-term="L.삭제"><i class="fa fa-minus"></i> </button>
                    <button type="button" class="btn btn-sm btn-outline-primary" id="urlMngExcelBtn1" ><i class="fa fa-file"></i> Excel</button>
                </div>
                <div class="main-btn-grp2">
                    <button type="button" class="btn btn-sm btn-primary" id="urlMngAcceptBtn1" data-term="L.저장"><i class="fa fa-check"></i> </button>
                    <button type="button" class="btn btn-sm btn-outline-danger" id="urlMngCancelBtn1" data-term="L.취소"><i class="fa fa-ban"></i> </button>
                </div>
            </div>
            <div class="col-md-6 text-right">
            </div>
        </div><!-- // grid btn layer -->
    </div>
</div>
<script src="<c:url value='/js/module/viself/auth/urlMng.js'/>?v=<c:out value='${sysTimeMillis}'/>"></script>
