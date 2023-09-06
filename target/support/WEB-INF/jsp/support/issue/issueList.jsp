<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2023-03-10 010
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
  -     이슈현황(리스트)
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="issueMngRootLayer" data-opener-data="<c:out value='${param.openerData}'/>"
     data-json="<c:out value='${param.jsonData}'/>">
    <div class="row">
        <div class="col-lg-4 col-6">
            <div class="small-box bg-warning">
                <div class="inner">
                    <h3 data-col="reqCnt">0</h3>
                    <p>담당자 접수대기</p>
                </div>
                <div class="icon">
                    <i class="fa-solid fa-headset"></i>
                </div>
                <a href="#" class="small-box-footer" data-btn="schReqBtn">
                    More info <i class="fas fa-arrow-circle-right"></i>
                </a>
            </div>
        </div>
        <div class="col-lg-4 col-6">
            <div class="small-box bg-info">
                <div class="inner">
                    <h3 data-col="rcvCnt">0</h3>
                    <p>처리중</p>
                </div>
                <div class="icon">
                    <i class="fa-solid fa-person-digging"></i>
                </div>
                <a href="#" class="small-box-footer" data-btn="schRcvBtn">
                    More info <i class="fas fa-arrow-circle-right"></i>
                </a>
            </div>
        </div>
        <div class="col-lg-4 col-6">
            <div class="small-box bg-success">
                <div class="inner">
                    <h3 data-col="rsltCnt">0</h3>
                    <p>고객사 검수대기</p>
                </div>
                <div class="icon">
                    <i class="fa-solid fa-flag-checkered"></i>
                </div>
                <a href="#" class="small-box-footer" data-btn="schRsltBtn">
                    More info <i class="fas fa-arrow-circle-right"></i>
                </a>
            </div>
        </div>
    </div>
    <div class="clearfix border rounded bg-gray-light p-2">
        <div class="row">
            <div class="col-md-11">
                <form id="issueMngSchFrm0" method="post" class="form-horizontal">
                    <div class="row" id="bbsSearchArea">
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block"
                                       data-term="L.요청일자"></label>
                                <div class="col-sm-9">
                                    <div class="form-inline">
                                        <input type="text" name="schFromReqDte" class="form-control form-control-sm" style='width:46%;'>
                                        <span class='p-1'> ~ </span>
                                        <input type="text" name="schToReqDte" class="form-control form-control-sm" style='width:46%;'>
                                    </div>
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
                                       data-term="L.요청자"></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control form-control-sm" name="schReqUserNm"
                                           placeholder="Search User Name">
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
                                       data-term="L.접수번호"></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control form-control-sm" name="schReqNo"
                                           placeholder="Search Request No.">
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block"
                                       data-term="L.제목"></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control form-control-sm" name="schIssueTitle"
                                           placeholder="Search Title">
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block"
                                       data-term="L.내용"></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control form-control-sm" name="schIssueContent"
                                           placeholder="Search Content">
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-md-1 d-none d-sm-block align-self-center">
                <div class="row p-1">
                    <button class="btn btn-outline-primary btn-sm p-1" type="button" id="issueMngResetBtn1"><i
                            class="fa fa-undo"></i> RESET
                    </button>
                </div>
                <div class="row p-1">
                    <button class="btn btn-primary btn-sm p-1" type="button" id="issueMngSearchBtn1"><i
                            class="fa fa-search"></i> SEARCH
                    </button>
                </div>
            </div>
            <div class="col-sm-3 d-block d-sm-none">
                <button class="btn btn-primary btn-sm" type="button" id="issueMngSearchSmBtn"><i
                        class="fa fa-search"></i> SEARCH
                </button>
                <button class="btn btn-outline-primary btn-sm" type="button" id="issueMngResetSmBtn"><i
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
                <div class="main-btn-grp1">
                    <button type="button" class="btn btn-primary btn-sm" id="issueMngAddBtn1"><i
                            class="fa fa-plus"></i> 새 이슈</button>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
<%--                <table id="issueMngList1"></table>--%>
                <table class="table table-sm table-hover text-sm border-bottom bg-white" id="issueTable">
                    <colgroup>
                        <col style="width: 100%;">
                    </colgroup>
                    <tbody id="issueBody"></tbody>
                </table>
                <input type="hidden" name="page" id="issueListPage">
                <div id="issueListPagination"></div>
            </div>
        </div>
        <div class="row p-1">
            <div class="col-md-6">
            </div>
            <div class="col-md-6 text-right">
            </div>
        </div><!-- // grid btn layer -->
    </div>
</div>
<script src="<c:url value='/js/module/support/issue/issueList.js?v='/><c:out value='${sysTimeMillis}'/>"></script>