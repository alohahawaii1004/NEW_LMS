<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2023-03-14 014
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
  -     공통 시스템 (리스트)
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="smSysMngRootLayer" data-opener-data="<c:out value='${param.openerData}'/>"
     data-json="<c:out value='${param.jsonData}'/>">
    <div class="row">
        <div class="col-md-6">
        </div>
        <div class="col-md-6 text-right">
        </div>
    </div>
    <div class="clearfix border rounded bg-gray-light p-2">
        <div class="row">
            <div class="col-md-11">
                <form id="smSysMngSchFrm0" method="post" class="form-horizontal">
                    <div class="row" id="bbsSearchArea">
                        <c:if test="${loginInfo.containsAuthorities('CMM_SYS,SM_MNG,SM_ADMIN')}">
                        <div class="form-group col-md-12">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block"
                                       data-term="L.회사명"></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control form-control-sm" name="schOrgNm"
                                           placeholder="Org. Name">
                                </div>
                            </div>
                        </div>
                        </c:if>
                        <div class="form-group col-md-6">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block"
                                       data-term="L.시스템명"></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control form-control-sm" name="schSysNm"
                                           placeholder="Sys. Name">
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-6">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block"
                                       data-term="L.시스템구분"></label>
                                <div class="col-sm-9">
                                    <select name="schPrdTpCd" style="width:90%;"></select>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-md-1 d-none d-sm-block align-self-center">
                <div class="row p-1">
                    <button class="btn btn-outline-primary btn-sm p-1" type="button" id="smSysMngResetBtn1"><i
                            class="fa fa-undo"></i> RESET
                    </button>
                </div>
                <div class="row p-1">
                    <button class="btn btn-primary btn-sm p-1" type="button" id="smSysMngSearchBtn1"><i
                            class="fa fa-search"></i> SEARCH
                    </button>
                </div>
            </div>
            <div class="col-sm-3 d-block d-sm-none">
                <button class="btn btn-primary btn-sm" type="button" id="smSysMngSearchSmBtn"><i
                        class="fa fa-search"></i> SEARCH
                </button>
                <button class="btn btn-outline-primary btn-sm" type="button" id="smSysMngResetSmBtn"><i
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
        </div>
        <div class="row">
            <div class="col-lg-12">
                <table id="smSysMngList1"></table>
            </div>
        </div>
        <div class="row p-1">
            <div class="col-md-6">
            </div>
            <div class="col-md-6 text-right">
                <button type="button" class="btn btn-outline-primary btn-sm" id="systemListCloseBtn" data-term="B.CLOSE"><i
                        class="fa fa-times"></i> </button>
                <button type="button" class="btn btn-primary btn-sm" id="systemListSelectBtn" data-term="B.SELECT"><i
                        class="fa fa-check"></i> </button>
            </div>
        </div><!-- // grid btn layer -->
    </div>
</div>
<script src="<c:url value='/js/module/support/cmm/systemList.js?v='/><c:out value='${sysTimeMillis}'/>"></script>