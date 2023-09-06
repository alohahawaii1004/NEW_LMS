<%--
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
  - Description: 데시보드 (특허사무소 권한용)
  - accumulate(acc) : 누계/누적
  --%>
<%@ page import="com.vertexid.spring.utils.SessionUtils" %>
<%@ page import="com.vertexid.viself.hr.SysLoginVO" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<%
    SysLoginVO loginUser = (SysLoginVO) SessionUtils.getLoginVO();
    String siteLocale = loginUser.getSiteLocale();
    String capitalizeSiteLocale = StringUtils.capitalize(siteLocale.toLowerCase());// ko >> Ko
%>
<div id="dashBoardRoot">
    <div class="row">
        <div class="card-deck col-md-12">
            <div class="card col-md-9 shadow-none bg-transparent border-bottom">
                <div class="card-body">
                    <div class="col-md-12" id="shortCuts">
                        <div class="row">
                            <h6>바로가기</h6>
                        </div>
                        <div class="row">
                            <div class="col-sm-3 pb-1">
                                <button class="btn btn-outline-primary form-control" data-url="IP_ALL_LIST"><i
                                        class="fa-solid fa-list-check"></i> 출원 목록
                                </button>
                            </div>
                            <div class="col-sm-3 pb-1">
                                <button class="btn btn-outline-primary form-control" data-url="IP_EX_LIST"><i
                                        class="fa-solid fa-list-check"></i> 심사청구 대상
                                </button>
                            </div>
                            <div class="col-sm-3 pb-1">
                                <button class="btn btn-outline-primary form-control" data-url="IP_OA_LIST"><i
                                        class="fa-solid fa-list-check"></i> 중간사건 대상
                                </button>
                            </div>
                            <div class="col-sm-3 pb-1">
                                <button class="btn btn-outline-primary form-control" data-url="IP_SU_LIST"><i
                                        class="fa-solid fa-list-check"></i> 소송진행 현황
                                </button>
                            </div>
                            <div class="col-sm-3 pb-1">
                                <button class="btn btn-outline-primary form-control" data-url="IP_RG_LIST"><i
                                        class="fa-solid fa-list-check"></i> 등록결정 대상
                                </button>
                            </div>
                            <div class="col-sm-3 pb-1">
                                <button class="btn btn-outline-primary form-control" data-url="IP_EXT_LIST"><i
                                        class="fa-solid fa-list-check"></i> 연차유지 대상
                                </button>
                            </div>
                            <div class="col-sm-3 pb-1">
                                <button class="btn btn-outline-primary form-control" data-url="BY_REQ.popup"><i
                                        class="fa-solid fa-list-check"></i> 비용지급 신청
                                </button>
                            </div>
                            <div class="col-sm-3 pb-1">
                                <button class="btn btn-outline-primary form-control" data-url="TH_IDS_REW.ipSearch"><i
                                        class="fa-solid fa-list-check"></i> IDS제출 보고
                                </button>
                            </div>
                            <div class="col-sm-3 pb-1">
                                <button class="btn btn-outline-secondary form-control"
                                        data-url="PIMS_특허사무소_Manual.pdf.file"><i class="fa fa-download"></i> 사무소 매뉴얼
                                </button>
                            </div>
                        </div>
                    </div><!-- // short cut -->
                    <div class="col-md-12" id="ipStats">
                        <div class="row">
                            <h6>비용청구현황</h6>
                            <table class="table table-bordered table-sm" id="byTbl">
                                <colgroup>
                                    <col style="width:25%">
                                    <col style="width:25%">
                                    <col style="width:25%">
                                    <col style="width:25%">
                                </colgroup>
                                <tr>
                                    <th class="text-center">비용청구요청</th>
                                    <th class="text-center">비용검토반려</th>
                                    <th class="text-center">비용확인</th>
                                    <th class="text-center">세금계산서정보</th>
                                </tr>
                                <tr>
                                    <td class="text-center" id="BY_REQ"></td>
                                    <td class="text-center" id="BY_REJ"></td>
                                    <td class="text-center" id="BY_CFM"></td>
                                    <td class="text-center" id="BY_TAX"></td>
                                </tr>
                            </table>
                        </div>
                        <div class="row">
                            <h6>특허 진행</h6>
                            <table class="table table-bordered table-sm" id="thTb">
                                <colgroup>
                                    <col style="width:25%">
                                    <col style="width:25%">
                                    <col style="width:25%">
                                    <col style="width:25%">
                                </colgroup>
                                <tr>
                                    <th class="text-center" data-term="L.출원준비"></th>
                                    <th class="text-center" data-term="L.출원"></th>
                                    <th class="text-center" data-term="L.심사중"></th>
                                    <th class="text-center" data-term="L.등록"></th>
                                </tr>
                                <tr>
                                    <td class="text-center" id="APP_READY"></td>
                                    <td class="text-center" id="APP"></td>
                                    <td class="text-center" id="REVIEW"></td>
                                    <td class="text-center" id="REG"></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card col-md-3 shadow-none bg-transparent border-bottom">
                <div class="card-body">
                    <h6>일정</h6>
                    <div id="scdlCalendar" class="w-100" style="height: 100%;"></div>
                </div>
            </div><!-- // calendar -->
        </div>
    </div><!-- // 1st row -->
    <div id="dashboardTodo" class="pt-2">
        <div class="row pt-1">
            <div class="col-md-12">
                <h6>To Do</h6>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <table id="todoList"></table>
            </div>
        </div>
    </div><!-- // 2nd row -->
</div>
<!-- 테이블템플릿 -->
<script id="headTmpl" type="text/x-jquery-tmpl">
    <th style="width:\${width}%">\${paragonCmm.getLang(langCd)}</th>
</script>
<script id="bodyTmpl" type="text/x-jquery-tmpl">
    <td style="text-align:center;"><a href="javascript:void(0);" onclick="dashBoard.loadPop('\${cd}')" data-row="\${cd}">\${cnt}</a></td>
</script>
<script id="bdTmpl" type="text/x-jquery-tmpl">
<thead>
    <tr id="statHead">
    </tr>
</thead>
    <tr id="statBody">
    </tr>
</script>
<%-- <link rel="stylesheet" href="<c:url value='/js/vendor/jqwidgets/12.0.1/styles/jqx.base.css'/>" type="text/css" /> --%>
<script src="<c:url value='/js/module/main/dashboardAgc.js'/>?ver=<spring:eval expression="@cmmProperties.getProperty('js.version')"/>"></script>