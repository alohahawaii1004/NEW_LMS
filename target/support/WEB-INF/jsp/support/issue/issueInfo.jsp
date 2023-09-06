<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2023-03-12 012
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
  -    이슈 정보(뷰)
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="issueInfoRootLayer" class="container-fluid" data-opener-data="<c:out value='${param.openerData}'/>"
     data-json="<c:out value='${param.jsonData}'/>">
    <div class="p-2">
        <div class="row">
            <div class="col-md-10">
                <h3><i class="fa-solid fa-headset text-gray"></i> <small class="text-sm" data-col="reqNo"></small> <span data-col="reqIssueTitle"></span>  </h3>
            </div>
            <div class="col-md-2 text-right">
                <span class="badge badge-light border rounded"><i class="fa-solid fa-layer-group"></i> <span data-col="issueTpNm"></span></span>
                <span class="badge badge-light border rounded"><i class="fa-solid fa-circle-info"></i> <span data-col="issueDomainNm"></span></span>
                <span class="badge badge-light border rounded" data-col="issueStuNm"></span>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <span class="badge badge-light border rounded" id="sysMgrViewGoUser"><i class="fa-solid fa-user"></i> <span data-col="reqNm"></span></span>
                <span class="badge badge-light border rounded" id="sysMgrViewGoSystem"><i class="fa-solid fa-computer text-lightblue"></i> <span data-col="systemName"></span></span>
                <span class="badge badge-light border rounded" id="sysMgrViewGoCustomer"><i class="fa-regular fa-building text-orange"></i> <span data-col="orgNm"></span></span>
                <span class="badge badge-light border rounded" title="요청일"><i class="fa-solid fa-pen-to-square"></i> <span data-col="strReqDte"></span></span>
            </div>
            <div class="col-md-6 text-right">
                <span class="badge badge-light border rounded" title="완료희망일"><i class="fa-solid fa-calendar-check"></i> <span data-col="strReqEndDte"></span></span>
            </div>
        </div>
        <p>
            <button type="button" class="btn btn-sm btn-link" data-target="#reqIssueConts" aria-expanded="false" aria-controls="reqIssueConts"><i class="fa-solid fa-angle-down"></i> 이슈내용</button>
        </p>
        <div class="collapse show" id="reqIssueConts">
            <div>
                <div data-col="reqIssueConts" id="reqIssueContsEditor" ></div>
                <div class="p-2" id="reqFiles"></div>
            </div>
        </div>
        <hr>
    </div>
    <div class="p-2" id="rcvViewLayer" style="display: none;">
        <div class="row">
            <div class="col-md-6">
                <span class="badge badge-light border rounded" ><i class="fa-solid fa-user-tie"></i> <span data-col="rcvNm"></span></span>
                <span class="badge badge-light border rounded" title="접수일"><i class="fa-solid fa-pen-to-square"></i> <span data-col="strRcvDte"></span></span>
            </div>
            <div class="col-md-6 text-right">
                <span class="badge badge-light border rounded" title="완료예정일"><i class="fa-solid fa-calendar-check"></i> <span data-col="strSchedulEndDte"></span></span>
                <span class="badge badge-light border rounded" title="예상공수"><i class="fa-solid fa-person-digging"></i> <span data-col="rcvExpManday"></span></span>
            </div>
        </div>
        <p>
            <button type="button" class="btn btn-sm btn-link" data-target="#rcvComment" aria-expanded="false" aria-controls="rcvComment"><i class="fa-solid fa-angle-down"></i> 접수내용</button>
        </p>
        <div class="collapse show" id="rcvComment">
            <div>
                <div data-col="rcvComment" id="rcvCommentEditor" ></div>
                <div class="p-2" id="rcvFiles"></div>
            </div>
        </div>
        <hr>
    </div>
    <div class="p-2" id="rsltViewLayer" style="display: none;">
        <div class="row">
            <div class="col-md-6">
            </div>
            <div class="col-md-6 text-right">
                <span class="badge badge-light border rounded" title="완료일"><i class="fa-solid fa-pen-to-square"></i> <span data-col="strRsltDte"></span></span>
                <span class="badge badge-light border rounded" title="실제공수"><i class="fa-solid fa-person-digging"></i> <span data-col="strRsltRealManday"></span></span>
            </div>
        </div>
        <p>
            <button type="button" class="btn btn-sm btn-link" data-target="#rsltRport" aria-expanded="false" aria-controls="rsltRport"><i class="fa-solid fa-angle-down"></i> 결과보고</button>
        </p>
        <div class="collapse show" id="rsltRport">
            <div>
                <div data-col="rsltRport" id="rsltReportEditor" ></div>
                <div class="p-2" id="rsltFiles"></div>
            </div>
        </div>
        <hr>
    </div>
    <div class="p-2" id="cfmViewLayer" style="display: none;">
        <div class="row">
            <div class="col-md-6">
                <span class="badge badge-light border rounded"><i class="fa-solid fa-flag-checkered"></i> <span data-col=""></span></span>
            </div>
            <div class="col-md-6 text-right">
                <span class="badge badge-light border rounded" title="검수일"><i class="fa-solid fa-pen-to-square"></i> <span data-col="strCfmDte"></span></span>
                <span class="badge badge-light border rounded"><i class="fa-solid fa-check"></i> <span data-col="cfmTpNm"></span></span>
                <span class="badge badge-light border rounded"><i class="fa-solid fa-star"></i> <span data-col="cfmSvcVal"></span></span>
            </div>
        </div>
        <p>
            <button type="button" class="btn btn-sm btn-link" data-target="#cfmComment" aria-expanded="false" aria-controls="cfmComment"><i class="fa-solid fa-angle-down"></i> 검수내용</button>
        </p>
        <div class="collapse show" id="cfmComment">
            <div>
                <div data-col="cfmComment" id="cfmCommentEditor" ></div>
                <div class="p-2" id="cfmFiles"></div>
            </div>
        </div>
        <hr>
    </div>
    <div class="p-2"  id="issueComments" data-rel-id="">
        <p>
            <button type="button" class="btn btn-sm btn-link" data-toggle="collapse" data-target="#comments" aria-expanded="false" aria-controls="comments"><i class="fa-solid fa-comments"></i> Comments</button>
            <span class="badge badge-light border rounded" data-col="commentsCnt">0</span>
        </p>
        <div class="direct-chat direct-chat-warning collapse" id="comments">
            <div class="direct-chat-messages"></div>
            <div class="p-1">
                <button type="button" class="btn btn-sm btn-outline-default" data-btn="addComment"><i class="fa-solid fa-comment-dots"></i>  Add a comment</button>
            </div>
        </div>
    </div>
    <div class="row mt p-1">
        <div class="col-md-6">
        </div>
        <div class="col-md-6 text-right" id="issueBtnLayer">
        </div>
    </div>
</div>
<script src="<c:url value='/js/module/support/issue/issueInfo.js?v='/><c:out value='${sysTimeMillis}'/>"></script>