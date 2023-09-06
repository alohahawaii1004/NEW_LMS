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
  -     (공용) 부서 선택 모달(다이얼로그)
  --%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<style>
    .s-area {
        border: 1px solid #C3C3C74D;
    }
    #treeArea {
        width:96%;
        height:330px;
        vertical-align: top;
        border:1px solid #CDCDCD;
        overflow: auto;
        box-shadow: 0 2px 1px rgb(0 0 0 / 20%);
        min-height: 330px;
        margin:0px 7px;
    }
</style>
<div class="row p-5" id="deptSelect" data-opener-data="<c:out value='${param.openerData}'/>">
    <div class="col-md-6 col-sm-6">
<%--        <span><strong><i class="fa fa-caret-square-o-right"></i> 사용자 선택</strong></span>--%>
        <div class="row">
            <div class="pn-sub2-top" style="padding: 10px 0 10px 0;margin: 0 14px 0 14px;">
                <!-- Search AREA -->
                <div class="table_area_n1">
                    <form id="deptSelectForm1" method="post" onsubmit="return false;">
                        <input type="hidden" name="parentCd" data-type="search" />
                        <input type="hidden" name="deptTpCd" data-type="search" />
    					<input type="hidden" name="_csrf" value="<c:out value='${_csrf.token}'/>">
                        <input type="hidden" name="schField" data-type="search" value="1"/>
                        <table>
                            <tr>
                                <td class="body form-inline">
                                    <div class="col-md-9">
                                        <label data-term="L.부서명"></label>
                                        <input type="text" name="schValue" id="schValue" value="" data-type="search"  class="form-control input-sm" style="width:80%"/>
                                    </div>
                                    <div class="col-md-3">
                                        <button type="button" class="btn btn-default btn-sm" id="deptSelectSearchBtn" data-term="B.SEARCH"><i class="fa fa-search"></i> </button>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
        <div>
            <div class="" style="text-align: left;">
                <div id="treeArea"></div>
            </div>
        </div>
    </div>
    <div class="col-md-6 col-sm-6">
<%--        <sapn><strong><i class="fa fa-caret-square-o-right"></i> 사용자</strong></sapn>--%>
        <div class="" style="min-height: 400px;">
            <div class="buttonlist">
                <div class="left">
                    <h6 class="sub2_title" data-term="L.부서선택"><i class="fa-solid fa-sitemap"></i> </h6>
                </div>
                <div class="right">
                </div>
            </div>
            <table class="list">
                <colgroup>
                    <col style="width:6%;" />
                    <col style="width:94%;" />
                </colgroup>
                <thead>
                <tr>
                    <th colspan="1"></th>
                    <th data-term="L.부서_팀"></th>
                </tr>
                </thead>
                <tbody id="hrDeptList">
                </tbody>
            </table>
        </div>
    </div>
    <div class="clearfix">
        <div class="col-md-12 text-right mt">
            <button type="button" class="btn btn-primary" id="deptSelectBtn1" data-term="L.선택"><i class="fa fa-plus"></i> </button>
        </div>
    </div>
</div>
<script id="addDeptTmpl" type="text/x-jquery-tmpl">
<tr data-meaning="apvr_tr" id="sel_\${deptCd}">
	<td align="center">
		<i class="fa fa-times" style="color:red;cursor:pointer;" onClick="$('#sel_\${deptCd}').remove();"></i>
	</td>
	<td align="center" data-meaning="data_td">
		\${dspNm}
		<input type="hidden" name="deptRow" value="\${JSON.stringify($item.data)}"/>
		<input type="hidden" name="dspNm" value="\${dspNm}"/>
		<input type="hidden" name="deptCd" value="\${deptCd}"/>
	</td>
</tr>
</script>
<script src="<c:url value='/js/module/cmm/modal/deptSelectModal.js?v='/><c:out value='${sysTimeMillis}'/>"></script>