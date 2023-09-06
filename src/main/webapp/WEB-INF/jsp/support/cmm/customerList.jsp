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
  -     고객사 현황(리스트)
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="customerMngRootLayer" class="container-fluid" data-opener-data="<c:out value='${param.openerData}'/>"
     data-json="<c:out value='${param.jsonData}'/>">
    <div class="row">
        <div class="col-md-6">
        </div>
        <div class="col-md-6 text-right">
        </div>
    </div>
    <div class="clearfix border rounded bg-gray-light p-2">
        <div class="row">
            <div class="col-md-10">
                <form id="customerMngSchFrm0" method="post" class="form-horizontal">
                    <input type="hidden" name="schUseYn" value="Y">
                    <div class="row" id="bbsSearchArea">
                        <div class="form-group col-md-8">
                            <div class="row">
                                <label class="col-sm-4 col-form-label col-form-label-sm d-none d-sm-block"
                                       data-term="L.회사명"></label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control form-control-sm" name="schOrgNm"
                                           placeholder="Org. Name">
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-md-2 d-none d-sm-block">
                <button class="btn btn-primary btn-sm p-1" type="button" id="customerMngSearchBtn1"><i
                        class="fa fa-search"></i> SEARCH
                </button>
            </div>
            <div class="col-sm-3 d-block d-sm-none">
                <button class="btn btn-primary btn-sm" type="button" id="customerMngSearchSmBtn"><i
                        class="fa fa-search"></i> SEARCH
                </button>
            </div>
        </div>
    </div>
    <div class="data-table clearfix">
        <div class="row p-1">
            <div class="col-md-6">
            </div>
            <div class="col-md-6 text-right">
                <div class="main-btn-grp1">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <table id="customerMngList1"></table>
            </div>
        </div>
        <div class="row p-1">
            <div class="col-md-6">
            </div>
            <div class="col-md-6 text-right">
                <button type="button" class="btn btn-outline-primary btn-sm" id="customerListCloseBtn" data-term="B.CLOSE"><i
                        class="fa fa-times"></i> </button>
                <button type="button" class="btn btn-primary btn-sm" id="customerListSelectBtn" data-term="B.SELECT"><i
                        class="fa fa-check"></i> </button>
            </div>
        </div><!-- // grid btn layer -->
    </div>
</div>
<script src="<c:url value='/js/module/support/cmm/customerList.js?v='/><c:out value='${sysTimeMillis}'/>"></script>