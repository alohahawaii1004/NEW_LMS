<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2022-07-20(020)
  -
  - Copyright 2022 JaYu.space
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
  -     권한접근메뉴 수정
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
<div class="container-fluid p-2" id="authMenuRootLayer" data-opener-data="<c:out value='${param.openerData}'/>">
    <h5><i class="fa fa-sitemap"></i> <span data-term="L.접근가능메뉴"></span></h5>
    <form class="form-horizontal" name="authMenuForm1" id="authMenuForm1" method="post">
        <input type="hidden" name="authCd" data-col="authCd" value="<c:out value='${param.authCd}'/>">
        <div class="row">
            <div class="col-md-12">
                <table id="authMenuList1"></table>
            </div>
        </div>
        <div class="row p-1" id="authMenuBtnLayer">
            <div class="col-md-6">
            </div>
            <div class="col-md-6 text-right">
                <div class="row">
                    <label class="col-sm-4 col-form-label col-form-label-sm"><strong>체크 접근여부</strong></label>
                    <div class="col-sm-4">
                        <input name="allowYnSwitch" data-col="allowYnSwitch" class="form-control form-control-sm">
                        <input type="hidden" name="allowYn" data-col="allowYn">
                    </div>
                    <div class="col-sm-4">
                        <button type="button" class="btn btn-sm btn-outline-primary" id="authMenuCloseBtn1" data-term="B.CLOSE"><i class="fa fa-close"></i> </button>
                        <button type="button" class="btn btn-sm btn-primary" id="authMenuSaveBtn1" data-term="B.SAVE"><i class="fa fa-plus"></i> </button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<script src="<c:url value='/js/module/viself/mng/auth/authMenu.js?v='/><c:out value='${sysTimeMillis}'/>"></script>