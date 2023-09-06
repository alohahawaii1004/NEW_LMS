<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2022-07-19(019)
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
  -     권한관리 뷰
  --%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div class="container-fluid" id="authMngViewRootLayer" data-opener-data="<c:out value='${param.openerData}'/>" data-cbkey="<c:out value='${param.cbKey}'/>">
<%--    <h4 class="text-danger"><strong><i class="fa-solid fa-shield-halved"></i> <span data-term="L.권한정보"></span></strong></h4>--%>
    <div class="p-2">
        <form class="form-horizontal" name="authMngViewForm1" id="authMngViewForm1" method="post">
            <input type="hidden" name="authCd" value="<c:out value='${param.authCd}'/>">
            <input type="hidden" name="popupTitle" value="권한정보">
            <input type="hidden" name="openerData">
            <input type="hidden" name="_csrf" value="<c:out value='${_csrf.token}'/>">
            <h4 class="text-default"><span><i class="fa fa-file-text"></i> 일반사항</span></h4>
            <div class="row">
                <div class="col-md-12 border">
                    <div class="row">
                        <label class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right" data-term="L.명칭"></label>
                        <div class="col-sm-4">
                            <span class="align-middle" data-col="authNm"></span>
                        </div>
                        <label class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right" data-term="L.코드"></label>
                        <div class="col-sm-4">
                            <span class="align-middle" data-col="authCd"></span>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 border">
                    <div class="row">
                        <label class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right" data-term="L.사용여부"></label>
                        <div class="col-sm-10">
                            <span class="badge align-middle" data-col="useYnNm"></span>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 border">
                    <div class="row">
                        <label class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right" data-term="L.비고"></label>
                        <div class="col-sm-10">
                            <span data-col="remark"></span>
                        </div>
                    </div>
                </div>
            </div>
            <h4 class="text-default"><span><i class="fa fa-sitemap"></i> 접근가능메뉴</span></h4>
            <div class="row">
                <div class="col-md-12 border">
                    <div class="row" data-col="menuNms" style="padding-left: 170px;">
                    </div>
                </div>
            </div>
            <h4 class="text-default"><span><i class="fa fa-users"></i> 멤버</span></h4>
            <div class="row">
                <div class="col-md-12 border">
                    <div class="row">
                        <div data-col="memberNms" style="padding-left: 170px;"></div>
                    </div>
                </div>
                <div class="col-md-12 col-sm-12 buttonlist" id="authMngViewBtnLayer">
                    <div class="right">
                    </div>
                </div>
            </div>
            <div class="row p-2">
                <div class="col-md-6"></div>
                <div class="col-md-6 text-right">
                        <button type="button" class="btn btn-sm btn-outline-primary" id="authMngViewCloseBtn1" data-term="B.CLOSE"><i class="fa fa-close"></i> </button>
                        <button type="button" class="btn btn-sm btn-outline-danger" id="authMngViewDeleteBtn1" style="display: none;"><i class="fa fa-minus"></i> <span data-term="B.DELETE"></span> </button>
                        <button type="button" class="btn btn-sm btn-primary" id="authMngViewModifyBtn1" data-term="B.MODIFY"><i class="fa fa-pencil"></i> </button>
                </div>
            </div>
        </form>
    </div>
</div>
<script src="<c:url value='/js/module/viself/mng/auth/authView.js?v='/><c:out value='${sysTimeMillis}'/>"></script>