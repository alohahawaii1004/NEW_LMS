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
  - Description: 데시보드
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
        <div class="col-md-12" id="dashboardTodo">
            <div class="card-body">
                <ul class="nav nav-tabs" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link active bg-danger" id="todoTab" data-toggle="tab" data-target="#todoTabPanel" type="button" role="tab" aria-controls="todoTabPanel" aria-selected="true">
                            <strong>To Do</strong>
                        </button>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane fade show active" id="todoTabPanel" role="tabpanel" aria-labelledby="todoTab">
                        <table id="todoList"></table>
                    </div>
                </div>
            </div>
        </div>
    </div><!-- // 1st row -->
    <div class="row">
        <div class="card-deck col-md-12 pb-3">
            <div class="card col-md-8">
<%--                <div class="card-header">--%>
<%--                    --%>
<%--                    <div class="card-tools">--%>
<%--                    </div>--%>
<%--                </div>--%>
                <div class="card-body">
                    <h3 class="card-title pb-1"><strong>특허 진행</strong></h3>
                    <table class="table border-top border-bottom table-sm" id="thTb">
                        <colgroup>
                            <col style="width: 25%;">
                            <col style="width: 25%;">
                            <col style="width: 25%;">
                            <col style="width: 25%;">
                        </colgroup>
                        <thead>
                        <tr>
                            <th class="text-center" data-term="L.출원준비(발명신고/선행기술조사)"></th>
                            <th class="text-center" data-term="L.출원"></th>
                            <th class="text-center" data-term="L.심사중"></th>
                            <th class="text-center" data-term="L.등록"></th>
                        </tr>
                        </thead>
                        <tr>
                            <td id="APP_READY" style=" text-align: center;">0</td>
                            <td id="APP" style=" text-align: center;">0</td>
                            <td id="REVIEW" style=" text-align: center;">0</td>
                            <td id="REG" style=" text-align: center;">0</td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="card col-md-4">
                <div class="card-header">
                    <h3 class="card-title">공지사항</h3>
                    <div class="card-tools">
                        <button type="button" class="btn btn-default btn-xs rounded-circle" id="noticeMore"><i class="fa-solid fa-ellipsis"></i></button>
                    </div>
                </div>
                <div class="card-body">
                    <table class="table table-sm" id="boardTbl">
                        <colgroup>
                            <col style="width:70%">
                            <col style="width:30%">
                        </colgroup>
                        <thead>
                        <tr data-opt='{"onClick":"dashBoard.goBoardView(\"@{bbsUid}\")","class":"list_text"}'>
                            <th style="width:70%" hidden="true"
                                data-opt='{"align":"left","col":"bbsTit","formatter":"dashBoard.getNoticeIco","class":"title"}'></th>
                            <th style="width:30%" hidden="true"
                                data-opt='{"align":"center","col":"bbsRegDte","class":"date"}'></th>
                        </tr>
                        </thead>
                        <tbody id="boardTblBody">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div><!-- // 2nd row -->
    <div class="row">
        <div class="card-deck col-md-12">
            <div class="card col-md-3 shadow-none bg-transparent" ><!-- style="background-color: #f4f6f9;"-->
                <div class="card-header">
                    일정
                </div>
                <div class="card-body">
                    <div id="scdlCalendar" class="w-100 h-100"></div>
                </div>
            </div>
            <div class="card-body col-md-9">
                <div class="row pb-3" style="margin-top: -15px;">
                    <div class="card col-md-12" id="shortCuts">
                        <input type="hidden" id="regDeptCd" value="<%=loginUser.getDeptCd()%>">
                        <div class="card-header">
                            <strong>바로가기</strong>
                        </div>
                        <div class="card-body p-3">
                            <div class="row">
                                <div class="col-sm-12 pb-1"><button class="btn btn-outline-danger form-control" data-url="TH_REQ.agree"><i class="fa-solid fa-file-pen"></i>  (특허/실용신안)직무발명 신고</button></div>
                                <div class="col-sm-4 pb-1"><button class="btn btn-outline-primary form-control" data-url="TH_DJ_REQ.agreeDj"><i class="fa-solid fa-file-pen"></i>  디자인 신청</button></div>
                                <div class="col-sm-4 pb-1"><button class="btn btn-outline-primary form-control" data-url="TM_REQ.popup"><i class="fa-solid fa-file-pen"></i>  상표 신청</button></div>
                                <div class="col-sm-4 pb-1"><button class="btn btn-outline-primary form-control" data-url="PR_REQ_WRT.popup"><i class="fa-solid fa-file-pen"></i>  저작권(일반, 프로그램) 신청</button></div>
                            </div>
                            <div class="row">
                                <div class="col-sm-4 pb-1"><button class="btn btn-outline-primary form-control" data-url="PA_G_PRE_REQ.popup"><i class="fa-solid fa-house"></i>  (특허)국내우선권주장 출원</button></div>
                                <div class="col-sm-4 pb-1"><button class="btn btn-outline-primary form-control" data-url="PA_U_PRE_REQ.popup"><i class="fa-solid fa-globe"></i>  (특허)해외우선권주장 출원</button></div>
                                <div class="col-sm-4 pb-1"><button class="btn btn-outline-secondary form-control" data-url="PIMS_발명자_Manual.pdf.file"><i class="fa fa-download"></i>  발명자 메뉴얼</button></div>
                            </div>
                            <div class="row">
                                <div class="col-sm-4 pb-1"><button class="btn btn-outline-primary form-control" data-url="TH_PRJ_UPT_REQ.ipSearch"><i class="fa fa-exchange"></i>  과제변경 신청</button></div>
                                <div class="col-sm-4 pb-1"><button class="btn btn-outline-primary form-control" data-url="TH_APLY_UPT_REQ.ipSearch"><i class="fa fa-exchange"></i>  출원인변경 신청</button></div>
                                <div class="col-sm-4 pb-1"><button class="btn btn-outline-primary form-control" data-url="TH_INV_UPT_REQ.ipSearch"><i class="fa fa-exchange"></i>  발명자변경 신청</button></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="card col-md-12">
                        <div class="card-header">
                            산학협력단 담당자
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-4">
                                    <div class="info-box shadow-none">
                                        <span class="info-box-icon"><i class="fa-regular fa-lightbulb"></i></span>
                                        <div class="info-box-content">
                                            <span class="info-box-text">특허관리</span>
                                            <span class="info-box-number">담당자
                                        주무관<br>(000-000-0000,chr001@korea.ac.kr)</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="info-box shadow-none">
                                        <span class="info-box-icon"><i class="fa-solid fa-sack-dollar"></i></span>
                                        <div class="info-box-content">
                                            <span class="info-box-text">특허비용</span>
                                            <span class="info-box-number">비용담당자
                                        주무관<br>(000-000-0000,chr001@korea.ac.kr)</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="info-box shadow-none">
                                        <span class="info-box-icon"><i class="fa-solid fa-gear"></i></span>
                                        <div class="info-box-content">
                                            <span class="info-box-text">특허총괄</span>
                                            <span class="info-box-number">관리자
                                        변리사<br>(000-000-0000,chr001@korea.ac.kr)</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div><!-- // 3rd row -->
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
<script src="<c:url value='/js/module/main/dashboard.js'/>?ver=<spring:eval expression="@cmmProperties.getProperty('js.version')"/>"></script>