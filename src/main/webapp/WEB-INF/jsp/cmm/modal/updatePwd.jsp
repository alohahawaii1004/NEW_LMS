<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2023-04-06
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
  -
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="usrPwdWriteRootLayer" class="container-fluid" data-opener-data="<c:out value='${param.openerData}'/>"
     data-json="<c:out value='${param.jsonData}'/>">
    <div class="p-2">
        <form class="form-horizontal" id="usrPwdWriteForm">
            <input type="hidden" name="loginId">
            <div class="row">
<%--                <div class="col-md-12 particle-row">--%>
<%--                    <div class="row">--%>
<%--                        <label class="col-sm-3 particle-label">이전 암호 <i--%>
<%--                                class="fa fa-check text-danger"></i></label>--%>
<%--                        <div class="col-sm-9 particle-form">--%>
<%--                            <input type="password" class="particle-input" name="oldPwd" placeholder="old password"--%>
<%--                                   maxlength="100" autocomplet="off">--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-3 particle-label">신규 암호 <i
                                class="fa fa-check text-danger"></i></label>
                        <div class="col-sm-9 particle-form">
                            <input type="password" class="particle-input" name="newPwd" placeholder="new password"
                                   maxlength="100" autocomplet="off">
                        </div>
                    </div>
                </div>
                <div class="col-md-12 particle-row">
                    <div class="row">
                        <label class="col-sm-3 particle-label">암호 확인 <i
                                class="fa fa-check text-danger"></i></label>
                        <div class="col-sm-9 particle-form">
                            <input type="password" class="particle-input" name="cfmPwd" placeholder="confirm password"
                                   maxlength="100" autocomplet="off">
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
            <button type="button" class="btn btn-outline-primary btn-sm" id="usrPwdWriteCloseBtn"><i
                    class="fas fa-times"></i> 닫기
            </button>
            <button type="button" class="btn btn-primary btn-sm" id="usrPwdWriteSaveBtn"><i class="fa fa-pencil"></i> 변경
            </button>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/tiles/include/jsRsaLink.jsp" %>
<script src="<c:url value='/js/module/cmm/modal/updatePwd.js?v='/><c:out value='${sysTimeMillis}'/>"></script>