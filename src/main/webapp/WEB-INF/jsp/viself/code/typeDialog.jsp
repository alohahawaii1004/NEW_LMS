<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2020-09-21(021)
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
  -     유형코드 다이얼로그
  --%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<form id="${module}Pop1Frm0" name="${module}Pop1Frm0">
    <input type="hidden">
    <table class="w-100 h-100">
        <colgroup>
            <col width="20%"/>
            <col width="30%"/>
            <col width="20%"/>
            <col width="30%"/>
        </colgroup>
        <tr>
            <th class="i18n" data-i18n="WRD.유형아이디">WRD.유형아이디</th>
            <td>
                <input type="text" name="tpId" minlength="7" maxlength="20">
                <input type="hidden" name="cud" value="C"/>
            </td>
            <th class="i18n" data-i18n="WRD.유형이름">WRD.유형이름</th>
            <td>
                <input type="text" name="tpNm" minlength="7" maxlength="20">
            </td>
        </tr>
        <tr>
            <th class="i18n" data-i18n="WRD.정렬순번">WRD.정렬순번</th>
            <td>
                <input type="text" name="sortOrd" minlength="7" maxlength="20">
            </td>
            <th class="i18n" data-i18n="WRD.사용여부">WRD.사용여부</th>
            <td><select id="${module}Pop1Select1" name="useEnable" class="w-100"></select></td>
        </tr>
        <tr>
            <th class="i18n" data-i18n="WRD.비고">WRD.비고</th>
            <td colspan="3">
                <input type="text" name="remark" minlength="7" maxlength="20" class="w-100">
            </td>
        </tr>
    </table>
    <div class="clearfix p-2">
        <div class="datatable-btns float-right">
            <button type="button" id="${module}Pop1SaveBtn1" class="btn btn-info btn-sm i18n" data-i18n="WRD.저장"><i class="fa fa-save"></i> WRD.저장</button>
            <button type="button" id="${module}Pop1DeleteBtn1" class="btn btn-sm i18n hidden" data-i18n="WRD.삭제"><i class="fa fa-minus"></i> WRD.삭제</button>
            <button type="button" id="${module}Pop1CloseBtn1" class="btn btn-info btn-sm i18n" data-i18n="WRD.닫기"><i class="fa fa-window-close"></i> WRD.닫기</button>
        </div>
    </div>
</form>