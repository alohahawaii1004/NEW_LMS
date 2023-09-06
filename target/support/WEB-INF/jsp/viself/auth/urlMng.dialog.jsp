<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2021-03-31(031)
  -
  - Copyright 2021 JAYU.space
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
  -     접근 URL 등록 관리(다이얼로그 버전)
  --%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>

    <div class="content-panel">
        <form id="urlMngSchFrm0" name="urlMngSchFrm0">
            <div class="search">
                <table class="w-100">
                    <colgroup>
                        <col width="10%"/>
                        <col width="20%"/>
                        <col width="10%"/>
                        <col width="20%"/>
                        <col width="10%"/>
                        <col width="15%"/>
                        <col width="20%"/>
                    </colgroup>
                    <tr>
                        <td colspan="6" class="p-1"><input type="text" name="tpNm" class="w-75 i18n" placeholder="WRD.검색어" data-i18n="WRD.검색어"></td>
                        <td class="text-right">
                            <button type="button" id="urlMngSearchBtn1" title="WRD.검색" class="btn btn-theme i18n" data-i18n="WRD.검색"><i class="fa fa-search"></i></button>
                            <button type="reset" id="urlMngResetBtn1" title="WRD.리셋" class="btn btn-theme02 i18n" data-i18n="WRD.리셋"><i class="fa fa-undo"></i></button>
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
    <div class="content-panel">
        <form id="urlMngListFrm1" name="urlMngListFrm1" class="p-1">
            <!-- user list -->
            <table id="urlMngList1"></table><!-- // user list -->
            <div class="clearfix">
                <div id="urlMngPager1" class="float-left w-50"></div>
                <div class="datatable-btns float-right">
                    <button id="urlMngAddRowBtn1" type="button" title="WRD.추가" class="btn btn-theme02 btn-sm i18n" data-i18n="WRD.추가"><i class="fa fa-plus"></i></button>
                    <button id="urlMngDeleteRowBtn1" type="button" title="WRD.삭제" class="btn btn-theme04 btn-sm i18n" data-i18n="WRD.삭제"><i class="fa fa-minus"></i></button>
                    <button id="urlMngExcelBtn1" type="button" title="WRD.엑셀" class="btn btn-theme btn-sm i18n" data-i18n="WRD.엑셀"><i class="fa fa-file-excel"></i></button>
                </div>
            </div>
        </form>
    </div>
<style type="text/css">
    .hidden {
        display: none !important;
    }
</style>
<%-- 신규/수정 다이얼로그 --%>
<div id="urlMngPop1" class="hidden"></div>
<script src="<c:url value='/js/module/viself/auth/urlMng.js'/>"></script>