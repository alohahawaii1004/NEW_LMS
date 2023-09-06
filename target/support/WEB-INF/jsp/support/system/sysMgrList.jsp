<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2023-03-28
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
  -     담당자 리스트
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="sysMgrListRootLayer" data-opener-data="<c:out value='${param.openerData}'/>"
     data-json="<c:out value='${param.jsonData}'/>">
    <div class="row">
        <div class="col-md-6">
        </div>
        <div class="col-md-6 text-right">
        </div>
    </div>
    <div class="clearfix border rounded bg-gray-light p-2">
        <div class="row">
            <div class="col-md-9">
                <form id="sysMgrListSchFrm0" method="post" class="form-horizontal">
                    <div class="row" id="bbsSearchArea">
                        <div class="form-group col-md-12">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block"
                                       data-term="L.담당자"></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control form-control-sm" name="schMgrNm"
                                           placeholder="Manager Name">
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-md-3 d-none d-sm-block">
                <div class="row p-1">
                    <button class="btn btn-outline-primary btn-sm m-1" type="button" id="sysMgrListResetBtn1"><i
                            class="fa fa-undo"></i> RESET
                    </button>
                    <button class="btn btn-primary btn-sm m-1" type="button" id="sysMgrListSearchBtn1"><i
                            class="fa fa-search"></i> SEARCH
                    </button>
                </div>
            </div>
            <div class="col-sm-3 d-block d-sm-none">
                <button class="btn btn-primary btn-sm" type="button" id="sysMgrListSearchSmBtn"><i
                        class="fa fa-search"></i> SEARCH
                </button>
                <button class="btn btn-outline-primary btn-sm" type="button" id="sysMgrListResetSmBtn"><i
                        class="fa fa-undo"></i> RESET
                </button>
            </div>
        </div>
    </div>
    <div class="data-table clearfix">
        <div class="row p-1">
            <div class="col-md-6">
            </div>
            <div class="col-md-6 text-right">
            </div>
        </div><!-- // grid btn layer -->
        <div class="row">
            <div class="col-lg-12">
                <table id="sysMgrListList1"></table>
            </div>
        </div>
        <div class="row p-1">
            <div class="col-md-6">
            </div>
            <div class="col-md-6 text-right">
                <button type="button" class="btn btn-outline-primary btn-sm" id="sysMgrListCloseBtn1" data-term="B.CLOSE"><i
                        class="fa fa-times"></i> </button>
                <button type="button" class="btn btn-primary btn-sm" id="sysMgrListAddBtn1" data-term="B.ADD"><i
                        class="fa fa-plus"></i> </button>
            </div>
        </div>
    </div>
</div>
<script src="<c:url value='/js/module/support/system/sysMgrList.js?v='/><c:out value='${sysTimeMillis}'/>"></script>