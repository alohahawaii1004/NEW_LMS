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
  - Description: 데시보드(특허담당권한)
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
            <div class="card col-md-6 shadow-none bg-transparent border-bottom">
                <div class="card-body">
                    <div class="row" id="ipStats">
                        <div class="col-md-6">
                            <h6>특허/실용신안 진행</h6>
                            <table class="table table-bordered table-sm" id="thTb">
                                <colgroup>
                                    <col style="width:20%">
                                    <col style="width:20%">
                                    <col style="width:20%">
                                    <col style="width:20%">
                                    <col style="width:20%">
                                </colgroup>
                                <tr>
                                    <th class="text-center" data-term="L.접수"></th>
                                    <th class="text-center" data-term="L.출원준비"></th>
                                    <th class="text-center" data-term="L.출원"></th>
                                    <th class="text-center" data-term="L.심사중"></th>
                                    <th class="text-center" data-term="L.등록"></th>
                                </tr>
                                <tr>
                                    <td class="text-center" id="REQ_REC"></td>
                                    <td class="text-center" id="APP_READY"></td>
                                    <td class="text-center" id="APP"></td>
                                    <td class="text-center" id="REVIEW"></td>
                                    <td class="text-center" id="REG"></td>
                                </tr>
                            </table>
                        </div><!-- // patent stats -->
                        <div class="col-md-6">
                            <h6>상표 진행</h6>
                            <table class="table table-bordered table-sm" id="tmTb">
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
                        </div><!-- // tm stats -->
                        <div class="col-md-6">
                            <h6>디자인 진행</h6>
                            <table class="table table-bordered table-sm" id="djTb">
                                <colgroup>
                                    <col style="width:20%">
                                    <col style="width:20%">
                                    <col style="width:20%">
                                    <col style="width:20%">
                                    <col style="width:20%">
                                </colgroup>
                                <tr>
                                    <th class="text-center" data-term="L.접수"></th>
                                    <th class="text-center" data-term="L.출원준비"></th>
                                    <th class="text-center" data-term="L.출원"></th>
                                    <th class="text-center" data-term="L.심사중"></th>
                                    <th class="text-center" data-term="L.등록"></th>
                                </tr>
                                <tr>
                                    <td class="text-center" id="REQ_REC"></td>
                                    <td class="text-center" id="APP_READY"></td>
                                    <td class="text-center" id="APP"></td>
                                    <td class="text-center" id="REVIEW"></td>
                                    <td class="text-center" id="REG"></td>
                                </tr>
                            </table>
                        </div><!-- // design stats -->
                        <div class="col-md-6">
                            <h6>저작권 진행</h6>
                            <table class="table table-bordered table-sm" id="prTb">
                                <colgroup>
                                    <col style="width:50%">
                                    <col style="width:50%">
                                </colgroup>
                                <tr>
                                    <th class="text-center" data-term="L.출원준비"></th>
                                    <th class="text-center" data-term="L.등록"></th>
                                </tr>
                                <tr>
                                    <td class="text-center" id="APP_READY"></td>
                                    <td class="text-center" id="REG"></td>
                                </tr>
                            </table>
                        </div><!-- // copy right stats -->
                        <div class="col-md-6">
                            <h6>우선권/분할 신청</h6>
                            <table class="table table-bordered table-sm" id="paTb">
                                <colgroup>
                                    <col style="width:30%">
                                    <col style="width:35%">
                                    <col style="width:35%">
                                </colgroup>
                                <tr>
                                    <th class="text-center" data-term="L.접수"></th>
                                    <th class="text-center" data-term="L.국내우선권"></th>
                                    <th class="text-center" data-term="L.해외우선권"></th>
                                </tr>
                                <tr>
                                    <td class="text-center" id="PA_REC"></td>
                                    <td class="text-center" id="PA_G"></td>
                                    <td class="text-center" id="PA_U"></td>
                                </tr>
                            </table>
                        </div><!-- // PC/DA stats -->
                    </div><!-- // ip stats -->
                    <div class="col-md-12" id="shortCuts">
                        <div class="row">
                            <h6>바로가기</h6>
                        </div>
                        <div class="row">
                            <div class="col-sm-3 pb-1"><button class="btn btn-outline-primary form-control" data-url="IP_ALL_LIST"><i class="fa-solid fa-list-check"></i>  출원 목록</button></div>
                            <div class="col-sm-3 pb-1"><button class="btn btn-outline-primary form-control" data-url="IP_PA_LIST"><i class="fa-solid fa-list-check"></i>  우선권/분할 현황</button></div>
                            <div class="col-sm-3 pb-1"><button class="btn btn-outline-primary form-control" data-url="COST_LIST"><i class="fa-solid fa-list-check"></i>  자재권비용 현황</button></div>
                            <div class="col-sm-3 pb-1"><button class="btn btn-outline-secondary form-control" data-url="PIMS_전체메뉴얼.zip.file"><i class="fa fa-download"></i>  전체 매뉴얼</button></div>
                        </div>
                    </div><!-- // short cut -->
                </div>
            </div>
            <div class="card col-md-3 shadow-none bg-transparent border-bottom">
                <div class="card-body">
                    <div class="h-50 ">
                        <div class="row border-bottom">
                            <div class="col-md-6">
                                <h6>기한관리</h6>
                            </div>
                            <div class="col-md-6 text-right">
                                <button type="button" class="btn btn-outline-defalut btn-xs rounded-circle" id="dueMore"><i class="fa-solid fa-ellipsis"></i></button>
                            </div>
                        </div>
                        <div class="row">
                            <table class="table table-sm" id="dueTbl">
                                <thead>
                                <tr data-opt='{"onClick":"dashBoard.goDueView(\"@{solMasUid}\",\"@{stuCd}\",\"@{stuDtl}\")","class":"list_text"}'>
                                    <th hidden="true"
                                        data-opt='{"width":"75%","align":"left","col":"dueTit","formatter":"dashBoard.getNoticeIco","class":"title"}'></th>
                                    <th hidden="true"
                                        data-opt='{"width":"25%","align":"center","col":"strDueDte","class":"date"}'></th>
                                </tr>
                                </thead>
                                <tbody id="dueTblBody">
                                </tbody>
                            </table>
                        </div>
                    </div><!-- // due date -->
                    <div class="h-50 ">
                        <div class="row border-bottom">
                            <div class="col-md-6">
                                <h6>공지사항</h6>
                            </div>
                            <div class="col-md-6 text-right">
                                <button type="button" class="btn btn-outline-defalut btn-xs rounded-circle" id="noticeMore"><i class="fa-solid fa-ellipsis"></i></button>
                            </div>
                        </div>
                        <div class="row">
                            <table class="main_notice table" id="boardTbl">
                                <thead>
                                <tr data-opt='{"onClick":"dashBoard.goBoardView(\"@{bbsUid}\")","class":"list_text"}'>
                                    <th hidden="true"
                                        data-opt='{"width":"75%","align":"left","col":"bbsTit","formatter":"dashBoard.getNoticeIco","class":"title"}'></th>
                                    <th hidden="true"
                                        data-opt='{"width":"25%","align":"center","col":"strBbsRegDte","class":"date"}'></th>
                                </tr>
                                </thead>
                                <tbody id="boardTblBody">
                                </tbody>
                            </table>
                        </div>
                    </div><!-- // notice -->
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
        <div class="row p-1">
            <div class="col-md-10">
                <h6>To Do</h6>
            </div>
            <div class="col-md-2 text-right">
                <select name="schSolTp" id="schSolTp" class="form-control form-control-sm">
                    <option value="" title="--전체--">--전체--</option>
                    <option value="TH" title="특허">특허</option>
                    <option value="SY" title="실용신안">실용신안</option>
                    <option value="DJ" title="디자인">디자인</option>
                    <option value="SP" title="상표">상표</option>
                    <option value="PR" title="저작권(프로그램)">저작권(프로그램)</option>
                    <option value="PB" title="저작권(일반)">저작권(일반)</option>
                    <!--                     <option value="BT" title="배치설계">배치설계</option>
                                        <option value="UP" title="IP고도화">IP고도화</option>
                                        <option value="VA" title="품종">품종</option>
                     -->
                    <option value="CO" title="비용">비용</option>
                </select>
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
<script src="<c:url value='/js/module/main/dashboardChr.js'/>?ver=<spring:eval expression="@cmmProperties.getProperty('js.version')"/>"></script>