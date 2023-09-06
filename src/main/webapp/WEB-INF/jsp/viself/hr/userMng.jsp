<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2020-10-05(005)
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
  - Description: 사용자 관리
  -
  --%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<div id="${module}Top0" class="mw-100 w-auto">
    <form id="${module}SchFrm0" name="${module}SchFrm0">
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
                        <button type="button" id="${module}SearchBtn1" title="WRD.검색" class="btn-dark i18n" data-i18n="WRD.검색"><i class="fa fa-search"></i></button>
                        <button type="reset" id="${module}ResetBtn1" title="WRD.리셋" class="btn-primary i18n" data-i18n="WRD.리셋"><i class="fa fa-undo"></i></button>
                    </td>
                </tr>
                <tr class="hidden">
                    <td colspan="6" class="p-1"><input type="text" name="tpNm" class="w-75 i18n" placeholder="WRD.검색어" data-i18n="WRD.검색어"></td>
                    <td class="text-right">
                        <button type="button" id="${module}SchBtn2" title="WRD.검색" class="btn-primary i18n" data-i18n="WRD.검색"><i class="fa fa-search"></i></button>
                        <%--                        <button type="button" id="${module}ReloadBtn1" title="WRD.리로드" class="btn-dark i18n" data-i18n="WRD.리로드"><i class="fa fa-sync-alt"></i></button>--%>
                        <button type="reset" id="${module}ResetBtn2" title="WRD.리셋" class="btn-dark i18n" data-i18n="WRD.리셋"><i class="fa fa-undo"></i></button>
                    </td>
                </tr>
            </table>
        </div>
        <div class="fold text-center">
            <a href="#" id="${module}SchOn" class="hidden">접기</a>
            <a href="#" id="${module}SchOff" class="off hidden">펼치기</a>
        </div>
    </form>
</div>
<div id="${module}Main0" class=" mh-100 mw-100 w-auto h-auto">
    <!-- user list layer -->
    <div id="${module}Layer1">
        <form id="${module}ListFrm1" name="${module}ListFrm1" class="p-1">
            <!-- user list -->
            <table id="${module}List1"></table><!-- // user list -->
            <div class="clearfix">
                <div id="${module}Pager1" class="float-left w-50"></div>
                <div class="datatable-btns float-right">
                    <button id="${module}AddRowBtn1" type="button" title="WRD.추가" class="btn btn-sm i18n" data-i18n="WRD.추가"><i class="fa fa-plus"></i></button>
                    <button id="${module}DeleteRowBtn1" type="button" title="WRD.삭제" class="btn btn-sm i18n" data-i18n="WRD.삭제"><i class="fa fa-minus"></i></button>
                    <button id="${module}ExcelBtn1" type="button" title="WRD.엑셀" class="btn btn-sm i18n" data-i18n="WRD.엑셀"><i class="fa fa-file-excel"></i></button>
                </div>
            </div>
        </form>
    </div><!-- // user list layer -->
</div>
<div id="${module}Pop1" class="hidden">
    <%-- 신규 다이얼로그 --%>
    <%@include file="userDialog.jsp"%>
</div>
<div id="${module}Pop2" class="hidden">
    <%-- PW 변경 다이얼로그 --%>
</div>
<div id="${module}Pop3" class="hidden">
    <%-- 사용자정보(수정) 다이얼로그 --%>
</div>