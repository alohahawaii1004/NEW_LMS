<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2022-07-25(025)
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
  -     인사정보 사용자 뷰
  --%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="userMngViewRootLayer" data-opener-data="<c:out value='${param.openerData}'/>">
    <h5 class="sub1_title"><i class="fa fa-file-text"></i> <span data-term="L.사용자관리"></span></h5>
    <form name="userMngViewForm1" id="userMngViewForm1" method="post">
        <input type="hidden" name="loginId" data-col="loginId" value="<c:out value='${param.loginId}'/>">
        <input type="hidden" name="nmKo" data-col="nmKo">
        <div class="rowl">
            <div class="col-md-12 col-sm-12">
                <table class="basic">
                    <tr>
                        <th class="th2" data-term="L.이름"> </th>
                        <td class="td2" colspan="3">
                            <span data-col="userNm"></span>
                        </td>
                    </tr>
                    <tr>
                        <th class="th2" data-term="L.소속"> </th>
                        <td class="td2" colspan="3">
                            <span data-col="deptNm"></span>
                        </td>
                    </tr>
                    <tr>
                        <th class="th2" data-term="L.사용자권한"> </th>
                        <td class="td2" colspan="3">
                            <span id="userAuthArea"></span>
                        </td>
                    </tr>
                    <tr>
                        <th class="th2" data-term="L.관리자권한"> </th>
                        <td class="td2" colspan="3">
                            <span id="adminAuthArea"></span>
                        </td>
                    </tr>
                    <tr>
                        <th class="th2" data-term="L.재직상태"> </th>
                        <td class="td2" colspan="3">
                            <span class="badge" data-col="useYnNm"></span>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="col-md-12 col-sm-12 buttonlist" id="userMngViewBtnLayer">
                <div class="right">                    
                    <button type="button" class="btn btn-primary btn-sm" id="userMngViewSaveBtn1" data-term="B.SAVE"><i class="fa fa-plus"></i> </button>
                    <button type="button" class="btn btn-default btn-sm" id="userMngViewCloseBtn1" data-term="B.CLOSE"><i class="fa fa-close"></i> </button>
                </div>
            </div>
        </div>
    </form>
</div>
<%@ include file="/WEB-INF/tiles/include/jsRsaLink.jsp"%>
<script src="<c:url value='/js/module/viself/mng/hr/userView.js?v='/><c:out value='${sysTimeMillis}'/>"></script>