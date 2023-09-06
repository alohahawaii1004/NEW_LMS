<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2022-07-25(025)
  -
  - Copyright 2022 JAYU.space
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
  -     사용자 관리 리스트
  --%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="userMngRootLayer" data-opener-data="<c:out value='${param.openerData}'/>" data-json="<c:out value='${param.jsonData}'/>">
    <div class="row mt">
        <h4 class="table_box_title" data-term="L.사용자관리"> </h4>
        <div class="col-lg-12">
            <div class="table_area_n1">
                <form id="userMngSchFrm0" method="post">
                    <table>
                        <tr>
                            <td>
                                <div class="col-md-12" id="userMngSearchArea">
                                    <div class="form-inline">
                                        <div class="form-group col-md-6">
                                            <label class="col-md-3 gridSchCond"><strong data-term="L.성명"></strong></label>
                                            <div class="col-md-9">
                                                <input type="text" class="form-control" name="schMbrNm" name="schType" style="width: 90%;">
                                            </div>
                                        </div>
                                        <div class="form-group col-md-3">
                                            <label class="col-md-3 gridSchCond"><strong data-term="L.부서"></strong></label>
                                            <div class="col-md-9">
                                                <input type="text" class="form-control" name="schDeptNm" name="schType" style="width: 90%;">
                                            </div>
                                        </div>
                                        <div class="form-group col-md-3">
                                            <label class="col-md-3 gridSchCond"><strong data-term="L.재직상태"></strong></label>
                                            <div class="col-md-9">
                                                <select class="form-control" name="schUseYn" name="schType" style="width: 90%;"></select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-inline">
                                        <div class="form-group col-md-6">
                                            <label class="col-md-3 gridSchCond"><strong data-term="L.관리자권한"></strong></label>
                                            <div class="col-md-9">
                                                <select class="form-control" name="schMngAuth" name="schType" style="width: 90%;"></select>
                                            </div>
                                        </div>
                                        <div class="form-group col-md-3">
                                            <label class="col-md-3 gridSchCond"><strong data-term="L.사용자권한"></strong></label>
                                            <div class="col-md-9">
                                                <select class="form-control" name="schUserAuth" name="schType" style="width: 90%;"></select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                            <td class="text-right">
                            	<button type="reset" class="btn btn-sm btn-default sear_st" id="userMngResetBtn1" data-term="B.INIT"><i class="fas fa-sync"></i> </button>
                            	<p style="pading-top:1px;"></p>
                            	<button type="button" class="btn btn-sm btn-primary sear_st" id="userMngSearchBtn1" data-term="B.SEARCH"><i class="fas fa-search"></i> </button>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="table_top_area">
                <table class="col-md-12">
                    <colgroup>
                        <col style="width:50%;">
                        <col style="width:50%;">
                    </colgroup>
                    <tr>
                        <td class="text-left">
                        </td>
                        <td class="text-right">
                        </td>
                    </tr>
                </table>
            </div>
            <div class="table_area_n2">
                <div class="col-lg-12" id="userMngGridLayer">
                    <table id="userMngList1"></table>
                    <div class="buttonlist">
                        <div class="left">
<%--                            <button type="button" class="btn btn-primary btn-sm" id="userMngAddBtn1" data-term="B.REG"><i class="fa fa-plus"></i> </button>--%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/tiles/include/jsRsaLink.jsp"%>
<script src="<c:url value='/js/module/viself/mng/hr/userList.js?v='/><c:out value='${sysTimeMillis}'/>"></script>