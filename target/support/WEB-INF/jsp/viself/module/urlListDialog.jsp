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
  -     URL 검색/선택용 다이얼로그
  --%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<form id="moduleMngPop2Frm0" name="moduleMngPop2Frm0" data-opener-data="<c:out value='${param.openerData}'/>">
    <input type="hidden" name="cud" value="C"/>
    <div id="moduleMngPop2List1"></div>
    <div class="clearfix p-2 buttonlist" style="padding: 2px;">
        <div class="datatable-btns right">
            <button type="button" id="moduleMngPop2CloseBtn1" class="btn btn-default btn-sm i18n" data-term="L.닫기"><i class="fa fa-window-close"></i> </button>
            <button type="button" id="moduleMngPop2ConfirmBtn1" class="btn btn-primary btn-sm i18n" data-term="L.추가"><i class="fa fa-save"></i> </button>
        </div>
    </div>
</form>
<script src="<c:url value='/js/module/viself/module/urlListDialog.js'/>"></script>
<script type="text/javascript">
    $(document).ready(function(){
        "use strict";
        var urlListDialog = new UrlListDialog();
        urlListDialog.init();
    });
</script>