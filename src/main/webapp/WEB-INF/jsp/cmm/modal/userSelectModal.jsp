<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2022-03-22(022)
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
  -     (공용) 사용자 선택 모달(다이얼로그)
  --%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<style>
    .s-area {
        border: 1px solid #C3C3C74D;
    }
    #treeArea {
        /*width:96%;*/
        height:330px;
        vertical-align: top;
        /*border:1px solid #CDCDCD;*/
        overflow: auto;
        box-shadow: 0 2px 1px rgb(0 0 0 / 20%);
        min-height: 330px;
        /*margin:0px 7px;*/
    }
</style>
<div class="container-fluid" id="userSelect" data-opener-data="<c:out value='${param.openerData}'/>">
    <div class="clearfix">
        <div class="row">
            <div class="col-md-6 p-1">
                <div class="clearfix border rounded bg-gray-light p-2">
                    <form class="form-horizontal" id="userSelectForm1" method="post" onsubmit="return false;">
                        <input type="hidden" name="parentCd" data-type="search" />
                        <input type="hidden" name="deptTpCd" data-type="search" />
                        <input type="hidden" name="_csrf" value="<c:out value='${_csrf.token}'/>">
                        <input type="hidden" id="cmsUserDeptCd" name="cmsUserDeptCd" data-type="search" value="<c:out value='${param.cmsUserDeptCd }'/>">
                        <div class="row">
                            <div class='col-sm-3'>
                                <select class="form-control form-control-sm" name="schField" id="codeMngSchField"></select>
                            </div>
                            <div class='col-sm-6'>
                                <input class="form-control form-control-sm" type="text" name="schValue" id="schValue">
                            </div>
                            <div class='col-sm-3'>
                                <button type="button" class="btn btn-sm btn-primary" id="userSelectSearchBtn" data-term="B.SEARCH"><i class="fa fa-search"></i> </button>
                            </div>
                        </div>
                    </form>
                </div>
                <div id="treeArea" class="border rounded p-2"></div>
            </div>
            <div class="col-md-6 p-1">
                <h7 data-term="L.사용자_선택"><i class="fa-solid fa-users"></i> </h7>
                <table class="table table-sm table-striped table-hover">
                    <colgroup>
                        <col style="width:6%;" />
                        <col style="width:94%;" />
                    </colgroup>
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col" class="text-center" data-term="L.사용자_정보"></th>
                    </tr>
                    </thead>
                    <tbody id="hrUserList">
                    </tbody>
                </table>
                <div class="p-1 text-right">
                </div>
            </div>
        </div>
    </div>
    <div class="clearfix">
        <div class="p-1 text-right">
                    <button type="button" class="btn btn-sm btn-primary" id="userSelectBtn1" data-term="L.선택"><i class="fa fa-plus"></i> </button>
        </div>
    </div>
</div>
<script id="addUserTmpl" type="text/x-jquery-tmpl">
<tr data-meaning="apvr_tr" id="sel_\${userNo}">
	<td align="center">
		<i class="fa fa-times" style="color:red;cursor:pointer;" onClick="$('#sel_\${userNo}').remove();"></i>
	</td>
	<td align="center" data-meaning="data_td">
		\${dspNm}
		<input type="hidden" name="userRows" value="\${JSON.stringify($item.data)}"/>
		<input type="hidden" name="userNo" value="\${userNo}"/>
	</td>
</tr>
</script>
<script src="<c:url value='/js/module/cmm/modal/userSelectModal.js?v='/><c:out value='${sysTimeMillis}'/>"></script>