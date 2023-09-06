<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2021-03-16(016)
  -
  - Copyright 2020 JAYU.space
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
  -     모듈 정보 다이얼로그
  --%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<form id="moduleMngPop1Frm0" name="moduleMngPop1Frm0" data-opener-data="<c:out value='${param.openerData}'/>" data-modal-id="<c:out value='${param.modalId}'/>">
    <input type="hidden" name="cud" value="C"/>
    <table class="basic">
        <colgroup>
            <col style="width:20%;"/>
            <col style="width:30%;"/>
            <col style="width:20%;"/>
            <col style="width:30%;"/>
        </colgroup>
        <tr>
            <th class="i18n" data-term="L.아이디"></th>
            <td colspan="3">
                <input type="text" name="moduleId" minlength="7" maxlength="50" class="w-100 form-control">
            </td>
        </tr>
        <tr>
            <th class="i18n" data-term="L.설명"></th>
            <td colspan="3">
                <input type="text" name="moduleDesc" minlength="7" maxlength="1000" class="w-100 form-control">
            </td>
        </tr>
        <tr>
            <th class="i18n" data-term="L.사용여부"></th>
            <td colspan="3">
                <select id="moduleMngPop1Select1" name="useYn" class="w-100 form-control"></select>
            </td>
        </tr>
    </table>
    <div class="clearfix p-2 buttonlist" style="padding: 2px;">
        <div class="right">
            <button type="button" id="moduleMngPop1DeleteBtn1" class="btn btn-light btn-sm i18n hidden" data-term="L.삭제"><i class="fa fa-minus"></i> </button>
            <button type="button" id="moduleMngPop1CloseBtn1" class="btn btn-default btn-sm i18n" data-term="L.닫기"><i class="fa fa-window-close"></i> </button>
            <button type="button" id="moduleMngPop1SaveBtn1" class="btn btn-primary btn-sm i18n" data-term="L.저장"><i class="fa fa-save"></i> </button>
        </div>
    </div>
</form>
<script src="<c:url value='/js/module/viself/module/moduleDialog.js'/>"></script>
<script type="text/javascript">
    $(document).ready(function(){
        "use strict";
        var moduleDialog = new ModuleDialog();
        moduleDialog.init();
    });
</script>