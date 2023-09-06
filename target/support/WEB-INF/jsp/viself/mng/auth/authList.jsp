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
  -     권한관리 리스트
  --%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="authMngRootLayer" data-opener-data="<c:out value='${param.openerData}'/>" data-json="<c:out value='${param.jsonData}'/>">
    <div class="row">
        <div class="col-md-6">
        </div>
        <div class="col-md-6 text-right">
        </div>
    </div>
    <div class="clearfix border rounded bg-gray-light p-2">
        <div class="row">
            <div class="col-md-11">
                <form id="authMngSchFrm0" method="post" class="form-horizontal">
                    <div class="row" id="bbsSearchArea">
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block" data-term="L.성명"></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control form-control-sm" name="schMbrNm" placeholder="User Name">
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block" data-term="L.사용여부"></label>
                                <div class="col-sm-9">
                                    <select class="form-control form-control-sm" name="schUseYn"></select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block" data-term="L.코드"></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control form-control-sm" name="schAuthCd" placeholder="Role Code">
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block" data-term="L.명칭"></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control form-control-sm" name="schAuthNm" placeholder="Role Name">
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-md-1 d-none d-sm-block align-self-center">
                <div class="row p-1">
                    <button class="btn btn-outline-primary btn-sm p-1" type="button" id="authMngResetBtn1"><i class="fa fa-undo"></i> RESET</button>
                </div>
                <div class="row p-1">
                    <button class="btn btn-primary btn-sm p-1" type="button" id="authMngSearchBtn1"><i class="fa fa-search"></i> SEARCH</button>
                </div>
            </div>
            <div class="col-sm-3 d-block d-sm-none">
                <button class="btn btn-primary btn-sm" type="button" id="authMngSearchSmBtn"><i class="fa fa-search"></i> SEARCH</button>
                <button class="btn btn-outline-primary btn-sm" type="button" id="authMngResetSmBtn"><i class="fa fa-undo"></i> RESET</button>
            </div>
        </div>
    </div>
    <div class="data-table clearfix">
        <div class="row p-1">
            <div class="col-md-6">
            </div>
            <div class="col-md-6 text-right">
                <div class="main-btn-grp1">
                    <button type="button" class="btn btn-primary btn-sm" id="authMngAddBtn1" data-term="B.REG"><i class="fa fa-plus"></i> </button>
                </div>
                <div class="main-btn-grp2">
                    <button type="button" class="btn btn-primary btn-sm" id="authMngAcceptBtn1" data-term="L.확인"><i class="fa fa-check"></i> </button>
                    <button type="button" class="btn btn-outline-danger btn-sm" id="authMngCancelBtn1" data-term="L.취소"><i class="fa fa-ban"></i> </button>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <table id="authMngList1"></table>
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
<script src="<c:url value='/js/module/viself/mng/auth/authList.js?v='/><c:out value='${sysTimeMillis}'/>"></script>