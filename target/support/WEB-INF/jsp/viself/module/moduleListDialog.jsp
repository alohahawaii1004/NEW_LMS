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
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<div class="container-fluid" id="moduleListRoot" data-opener-data="<c:out value='${param.openerData}'/>">
    <div class="row">
        <div class="col-md-6">
        </div>
        <div class="col-md-6 text-right">
        </div>
    </div>
    <div class="clearfix border rounded bg-gray-light p-2">
        <div class="row">
            <div class="col-md-10">
                <form class="form-horizontal" id="moudleListform1" name="moudleListform1" method="post">
                    <div class="row">
                        <div class="form-group col-md-12">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block" data-term="L.모듈"></label>
                                <div class="col-sm-9">
                                    <input type="text" name="moduleId" id="moduleId" maxlength="30" class="form-control form-control-sm" data-type="search"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-md-2">
                <div class="row p-1">
                    <button class="btn btn-primary btn-sm p-1" type="button" id="moduleSearchBtn"><i class="fa fa-search"></i> SEARCH</button>
                </div>
            </div>
        </div>
    </div>
    <%-- data table --%>
    <div class="data-table clearfix">
        <div class="row p-1">
        </div>
        <div class="row">
            <div class="col-lg-12">
                <table id="ModuleListTable"></table>
            </div>
        </div>
        <div class="form-row p-1" id="btnLayer">
            <div class="col-md-6">
            </div>
            <div class="col-md-6 text-right">
                <button class="btn btn-primary btn-sm" id="moduleSelectBtn" data-term="L.선택"><i class="fa fa-check"></i>
                </button>
            </div>
        </div>
    </div>
</div>
<script src="<c:url value='/js/module/viself/module/moduleListDialog.js'/>"></script>
<%--
<script>

function Module_List(){

    var loadTable = function(pageNo){
        if(pageNo == undefined) pageNo =1;
        var data = paragonCmm.getSearchQueryParams($("#moudleListform1"));
        data["page"] = pageNo;
        data["rows"] = "10";

        paragonCmm.callAjax("/viself/module/moduleMng/moduleList/json",data, function(json){
          var data = paragonCmm.easyuiLoadFilter(json);
          var page = pageNo;
          var rowCnt = 10;
          console.log(data);
          htmlUtils.createTableRow("ModuleListTable", data, page, rowCnt, "moduleList.loadTable");

        });
    }
    var setCallback;
    var attachEvents = function(){
        $("#moduleListProc").off();
        $("#moduleListProc").on("click",function(){
            var $chkId = $("input:radio[name='chkModule']:checked");
            if($chkId.length == 0){
                alert("모듈을 선택해 주세요.");
                return false;
            }
            if(typeof setCallback === "function"){
                setCallback($chkId.val());
            }
        });
        $("#moduleSearchBtn").off();
        $("#moduleSearchBtn").on("click",function(){
            loadTable(1);
        });
        $("#moduleId").off();
        $("#moduleId").on("keyup",function(){
            if (isCheckEnter && event.keyCode != 13) {
                return;
            }
            loadTable(1);
        });
    }
    var init = function(initFunc, callbackFunc){
        if(typeof callbackFunc === "function"){
            setCallback = callbackFunc;
        }
        loadTable();
        attachEvents();
    }
    return {
        init:init,
        loadTable:loadTable
    }
}



</script>
--%>