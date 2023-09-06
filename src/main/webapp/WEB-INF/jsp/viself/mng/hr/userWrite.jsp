<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2022-07-25(025)
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
  -     신규사용자 작성
  --%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div id="userMngWriteRootLayer" data-opener-data="<c:out value='${param.openerData}'/>">
    <h5 class="sub1_title"><i class="fa fa-file-text"></i> <span data-term="L.사용자관리"></span></h5>
    <form name="userMngWriteForm1" id="userMngWriteForm1" method="post">
        <div class="rowl">
            <div class="col-md-12 col-sm-12">
                <table class="basic">
                    <tr>
                        <th class="th4" data-term="L.ID"><i class="fa fa-check icon-required"></i> </th>
                        <td class="td4">
                            <input type="text" name="loginId" class="form-control input-sm" data-col="loginId" value="<c:out value='${param.loginId}'/>" maxlength="50">
                        </td>
                        <th class="th4" data-term="L.이름"><i class="fa fa-check icon-required"></i> </th>
                        <td class="td4">
                            <input type="text" name="nmKo" class="form-control input-sm" data-col="nmKo" maxlength="80">
                        </td>
                    </tr>
                    <tr>
                        <th class="th4" data-term="L.연락처"> </th>
                        <td class="td4">
                            <input type="text" name="telNo" class="form-control input-sm" data-col="telNo" maxlength="30">
                        </td>
                        <th class="th4" data-term="L.이메일"><i class="fa fa-check icon-required"></i> </th>
                        <td class="td4">
                            <input type="text" name="email" class="form-control input-sm" data-col="email" maxlength="100">
                        </td>
                    </tr>
                    <tr>
                        <th class="th4" data-term="L.사용자권한"><i class="fa fa-check icon-required"></i> </th>
                        <td class="td4" colspan="3">
                            <span id="userAuthArea"></span>
                        </td>
                    </tr>
                    <tr>
                        <th class="th4" data-term="L.사용기간"><i class="fa fa-check icon-required"></i> </th>
                        <td class="td4" colspan="3">
                            <input type="text" name="useStDte" data-col="useStDte" style='width:125px;'>
                            <span>~</span>
                            <input type="text" name="useEdDte" data-col="useEdDte" style='width:125px;'>
                        </td>
                    </tr>
                    <tr>
                        <th class="th4" data-term="L.메모"> </th>
                        <td class="td4" colspan="3">
                            <input name="memo" data-col="memo" class="form-control input-sm" maxlength="2000">
                        </td>
                    </tr>
                </table>
            </div>
            <div class="col-md-12 col-sm-12 buttonlist" id="userMngWriteBtnLayer">
                <div class="right">                    
                    <button type="button" class="btn btn-primary btn-sm" id="userMngWriteSaveBtn1" data-term="B.SAVE"><i class="fa fa-plus"></i> </button>
                    <button type="button" class="btn btn-default btn-sm" id="userMngWriteCloseBtn1" data-term="B.CLOSE"><i class="fa fa-close"></i> </button>
                </div>
            </div>
        </div>
    </form>
</div>
<%@ include file="/WEB-INF/tiles/include/jsRsaLink.jsp"%>
<script src="<c:url value='/js/module/viself/mng/hr/userWrite.js?v='/><c:out value='${sysTimeMillis}'/>"></script>