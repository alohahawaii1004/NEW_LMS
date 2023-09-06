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
  -     권한 멤버 수정
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
<div class="container-fluid p-2" id="authMemberRootLayer" data-opener-data="<c:out value='${param.openerData}'/>">
    <h5><i class="fa fa-users"></i> <span data-term="L.멤버"></span></h5>
    <form class="form-horizontal" name="authMemberForm1" id="authMemberForm1" method="post">
        <input type="hidden" name="authCd" data-col="authCd" value="<c:out value='${param.authCd}'/>">
        <div class="row">
            <div class="col-md-12">
                <table id="authMemberList1"></table>
                <div style="text-align: center;">
                    <span id="authMemberInfo"></span>
                </div>
            </div>
        </div>
        <div class="row p-1" id="authMemberBtnLayer">
            <div class="col-md-6">
            </div>
            <div class="col-md-6 text-right">
                <button type="button" class="btn btn-sm btn-outline-primary" id="authMemberCloseBtn1" data-term="B.CLOSE"><i class="fa fa-close"></i> </button>
                <button type="button" class="btn btn-sm btn-outline-danger" id="authMemberDelBtn1" data-term="B.DELETE" style="display:none;"><i class="fa fa-minus"></i> </button>
                <button type="button" class="btn btn-sm btn-primary" id="authMemberAddBtn1" data-term="B.ADD" style="display:none;"><i class="fa fa-plus"></i> </button>
            </div>
        </div>
    </form>
</div>
<script src="<c:url value='/js/module/viself/mng/auth/authMember.js?v='/><c:out value='${sysTimeMillis}'/>"></script>