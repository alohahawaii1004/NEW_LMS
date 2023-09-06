<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2023-03-30
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
  -     이슈 검수
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<style>
    .rating_box {
        display: flex;
    }

    .rating {
        position: relative;
        color: #ddd;
        font-size: 25px;
        text-align: center;
    }

    .rating input {
        position: absolute;
        left: 0;
        right: 0;
        width: 100%;
        height: 100%;
        opacity: 0;
        cursor: pointer;
    }

    .rating_star {
        width: 0;
        color: #ffc107;
        position: absolute;
        left: 0;
        right: 0;
        overflow: hidden;
        pointer-events: none;
    }
</style>
<div id="issueCfmWriteRootLayer" class="container-fluid" data-opener-data="<c:out value='${param.openerData}'/>"
     data-json="<c:out value='${param.jsonData}'/>">
    <div class="p-2">
        <form class="form-horizontal" name="issueCfmWriteForm" id="issueCfmWriteForm">
            <input type="hidden" name="issueId" data-col="issueId">
            <div class="row">
                <div class="col-md-12 particle-row">
                    <h3><i class="fa-solid fa-headset"></i> <small class="text-sm" data-col="reqNo"></small> <span data-col="reqIssueTitle"></span>  </h3>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">만족도</label>
                        <div class="col-sm-4 particle-form rating_box">
                            <div class="rating">
                                ★★★★★
                                <span class="rating_star">★★★★★</span>
                                <input type="range" value="1" step="1" min="0" max="10">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">검수 의견</label>
                        <div class="col-sm-10 particle-form">
                            <textarea name="cfmComment" id="cfmComment" data-col="cfmComment" style='display:none;'></textarea>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-2 particle-label">첨부파일</label>
                        <div class="col-sm-10 particle-form">
                            <div id="issueCfmFile"></div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div class="row p-2">
        <div class="col-md-6">

        </div>
        <div class="col-md-6 text-right">
            <button type="button" class="btn btn-outline-primary btn-sm" id="issueCfmWriteCloseBtn"><i
                    class="fas fa-times"></i> 닫기
            </button>
            <button type="button" class="btn btn-outline-danger btn-sm" id="issueCfmRejectSaveBtn"><i class="fa-solid fa-thumbs-down"></i>
                반려
            </button>
            <button type="button" class="btn btn-primary btn-sm" id="issueCfmWriteSaveBtn"><i class="fa-solid fa-thumbs-up"></i>
                검수
            </button>
        </div>
    </div>
</div>
<script src="<c:url value='/js/module/support/issue/issueCfmWrite.js?v='/><c:out value='${sysTimeMillis}'/>"></script>