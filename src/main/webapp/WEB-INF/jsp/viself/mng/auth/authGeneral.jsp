<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2022-07-20(020)
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
  -     권한일반 수정
  --%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<style>
    .switchbutton-on {
        background-color: #dff0d8;
        color: #3c763d;
    }
    .switchbutton-off {
        background-color: #f2dede;
        color: #a94442;
    }
</style>
<div class="container-fluid p-2" id="authGeneralRootLayer" data-opener-data="<c:out value='${param.openerData}'/>">
    <h5><i class="fa fa-file-text"></i> <span data-term="L.일반사항"></span></h5>
    <form class="form-horizontal" name="authGeneralForm1" id="authGeneralForm1" method="post">
        <input type="hidden" name="authCd" data-col="authCd" value="<c:out value='${param.authCd}'/>">
        <div class="row">
            <div class="col-md-12 border">
                <div class="row">
                    <label class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right" data-term="L.코드"></label>
                    <div class="col-sm-10">
                        <span data-col="authCd"></span>
                    </div>
                </div>
            </div>
            <div class="col-md-12 border">
                <div class="row">
                    <label for="authNm"
                           class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right" data-term="L.명칭"></label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control form-control-sm" id="authNm"
                               name="authNm" data-col="authNm" placeholder="명칭" maxlength="80">
                    </div>
                </div>
            </div>
            <div class="col-md-12 border">
                <div class="row">
                    <label for="remark"
                           class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right" data-term="L.비고"></label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control form-control-sm" id="remark"
                               name="remark" data-col="remark" placeholder="비고" maxlength="2000">
                    </div>
                </div>
            </div>
            <div class="col-md-12 border">
                <div class="row">
                    <label for="ordNo"
                           class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right" data-term="L.정렬순서"></label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control form-control-sm" id="ordNo"
                               name="ordNo" data-col="ordNo" placeholder="명칭" maxlength="80">
                    </div>
                </div>
            </div>
            <div class="col-md-12 border">
                <div class="row">
                    <label for="useYnSwitch"
                           class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right" data-term="L.사용여부"></label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control form-control-sm" id="useYnSwitch"
                               name="useYnSwitch" placeholder="명칭" maxlength="80">
                        <input type="hidden" name="useYn" data-col="useYn">
                    </div>
                </div>
            </div>
        </div>
    </form>
    <div class="row p-1" id="authGeneralBtnLayer">
        <div class="col-md-6"></div>
        <div class="col-md-6 text-right">
                <button type="button" class="btn btn-sm btn-outline-primary" id="authGeneralCloseBtn1" data-term="B.CLOSE"><i class="fa fa-close"></i> </button>
                <button type="button" class="btn btn-sm btn-primary" id="authGeneralSaveBtn1" data-term="B.SAVE"><i class="fa fa-plus"></i> </button>
        </div>
    </div>
</div>
<script src="<c:url value='/js/module/viself/mng/auth/authGeneral.js?v='/><c:out value='${sysTimeMillis}'/>"></script>