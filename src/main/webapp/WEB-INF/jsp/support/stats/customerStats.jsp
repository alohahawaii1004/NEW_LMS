<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2023-06-12(012)
  -
  - Copyright 2023 JaYu.space
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
  -     현황/통계 > 고객사 현황
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="customerStatsRootLayer" data-opener-data="<c:out value='${param.openerData}'/>"
     data-json="<c:out value='${param.jsonData}'/>">
    <div class="clearfix border rounded bg-gray-light p-2">
        <div class="row">
            <div class="col-md-11">
                <form id="customerStatsSchFrm0" method="post" class="form-horizontal">
                    <input type="hidden" name="statsType" value="system">
                    <div class="row" id="bbsSearchArea">
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block"
                                       data-term="L.이슈일자"></label>
                                <div class="col-sm-9">
                                    <div class="form-inline">
                                        <input type="text" name="schFromReqDte" class="form-control form-control-sm"
                                               style='width:46%;'>
                                        <span class='p-1'> ~ </span>
                                        <input type="text" name="schToReqDte" class="form-control form-control-sm"
                                               style='width:46%;'>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block"
                                       data-term="L.회사명"></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control form-control-sm" name="schOrgNm"
                                           placeholder="Org. Name">
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block"
                                       data-term="L.시스템명"></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control form-control-sm" name="schSysNm"
                                           placeholder="Sys. Name">
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block"
                                       data-term="L.시스템구분"></label>
                                <div class="col-sm-9">
                                    <select class="form-control form-control-sm" name="schPrdTpCd"></select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block"
                                       data-term="L.요청구분"></label>
                                <div class="col-sm-9">
                                    <select class="form-control form-control-sm" name="schIssueTpCd"></select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block"
                                       data-term="L.요청분야"></label>
                                <div class="col-sm-9">
                                    <select class="form-control form-control-sm" name="schIssueDomainCd"></select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block"
                                       data-term="L.진행상태"></label>
                                <div class="col-sm-9">
                                    <select class="form-control form-control-sm" name="schIssueStuCd"></select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block"
                                       data-term="L.담당자"></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control form-control-sm" name="schMgrUserNm"
                                           placeholder="Search Manager Name">
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block"
                                       data-term="L.요청자"></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control form-control-sm" name="schReqUserNm"
                                           placeholder="Search User Name">
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-md-1 d-none d-sm-block align-self-center">
                <div class="row p-1">
                    <button class="btn btn-outline-primary btn-sm p-1" type="button" id="customerStatsResetBtn1"><i
                            class="fa fa-undo"></i> RESET
                    </button>
                </div>
                <div class="row p-1">
                    <button class="btn btn-primary btn-sm p-1" type="button" id="customerStatsSearchBtn1"><i
                            class="fa fa-search"></i> SEARCH
                    </button>
                </div>
            </div>
            <div class="col-sm-3 d-block d-sm-none">
                <button class="btn btn-primary btn-sm" type="button" id="customerStatsSearchSmBtn"><i
                        class="fa fa-search"></i> SEARCH
                </button>
                <button class="btn btn-outline-primary btn-sm" type="button" id="customerStatsResetSmBtn"><i
                        class="fa fa-undo"></i> RESET
                </button>
            </div>
        </div>
    </div>
    <div class="data-table clearfix">
        <div class="row p-1">
            <div class="col-md-6">
                <ol class="breadcrumb text-xs bg-gray-light" id="customerStatsBreadcrumb">
                    <%--                    <li class="breadcrumb-item"><a href="/"><i class="fa fa-home"></i></a></li>--%>
                    <%--                    <li class="breadcrumb-item"><a href="#">현황/통계</a></li>--%>
                    <%--                    <li class="breadcrumb-item active">담당자 현황</li>--%>
                </ol>
            </div>
            <div class="col-md-6 text-right">
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <table class="table table-sm table-hover text-sm border-bottom bg-white" id="customerStats"
                       style="display:none;">
                    <caption>List of Customers</caption>
                    <colgroup>
                        <col style="width: 15%;">
                        <col style="width: 15%;">
                        <col style="width: 10%;">
                        <col style="width: 10%;">
                        <col style="width: 10%;">
                        <col style="width: 10%;">
                        <col style="width: 10%;">
                        <col style="width: 10%;">
                        <col style="width: 10%;">
                    </colgroup>
                    <thead>
                    <tr>
                        <th class="text-center" scope="col" rowspan="2">고객사</th>
                        <th class="text-center" scope="col" rowspan="2">System</th>
                        <th class="text-center" scope="col" rowspan="2">요청</th>
                        <th class="text-center" scope="col" rowspan="2">처리중</th>
                        <th class="text-center" scope="col" rowspan="2">검수대기</th>
                        <th class="text-center" scope="col" colspan="3">완료</th>
                        <th class="text-center" scope="col" rowspan="2">total</th>
                    </tr>
                    <tr>
                        <th class="text-center" scope="col">검수</th>
                        <th class="text-center" scope="col">반려</th>
                        <th class="text-center" scope="col">합계</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th scope="row">Manager</th>
                        <th scope="row">Manager</th>
                        <td>Received</td>
                        <td>Confirm</td>
                        <td>Finalized</td>
                        <td>total</td>
                    </tr>
                    </tbody>
                    <tfoot></tfoot>
                </table>
            </div>
        </div>
        <div class="row p-1">
            <div class="col-md-12" id="customerStatsChart" style="display:none;">
                <canvas id="chart1"></canvas>
            </div>
        </div>
        <div class="row p-1" id="customerDtlStatsChart" style="display:none;">
            <div class="col-md-3">
                <canvas id="chart2"></canvas>
            </div>
            <div class="col-md-9">
                <canvas id="chart3"></canvas>
            </div>
            <div class="col-md-3">
                <canvas id="chart4"></canvas>
            </div>
            <div class="col-md-9">
                <canvas id="chart5"></canvas>
            </div>
        </div>
    </div>
</div>
<script src="<c:url value='/js/module/support/stats/customerStats.js?v='/><c:out value='${sysTimeMillis}'/>"></script>