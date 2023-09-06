<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2022-02-14(014)
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
  -     부모코드 선택
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<%
    //-- 기타값 셋팅
    String schFieldCodestr         = "LANG_CD|코드명^CD|코드";
%>
<style>
    #treeCodeArea {
        /*width:96%;*/
        height:330px;
        vertical-align: top;
        /*border:1px solid #CDCDCD;*/
        overflow: auto;
        box-shadow: 0 2px 1px rgb(0 0 0 / 20%);
        min-height: 330px;
    }
</style>
<div class="container-fluid">
    <div class="clearfix">
        <div class="row">
            <div class="col-md-6 p-1">
                <div class="clearfix border rounded bg-gray-light p-2">
                    <form class="form-horizontal" name="schForm" id="schForm" method="post" onSubmit="return false;">
                        <input type="hidden" name="_csrf" value="<c:out value='${_csrf.token}'/>">
                        <div class="row">
                            <div class='col-sm-3'>
                                <select id="schField" name="schField"></select>
                            </div>
                            <div class='col-sm-6'>
                                <input class="form-control form-control-sm" type="text" name="schValue" id="schValue">
                            </div>
                            <div class='col-sm-3'>
                                <button type="button" class="btn btn-sm btn-primary" id="cdSearchBtn" data-term="B.SEARCH"><i class="fa fa-search"></i> </button>
                            </div>
                        </div>
                    </form>
                </div>
                <div id="treeCodeArea" class="border rounded p-2"></div>
            </div>
            <div class="col-md-6 p-1">
                <h7 data-term="B.SELECT"> </h7>
                <table class="table table-sm table-striped table-hover">
                    <colgroup>
                        <col style="width:8%;" />
                        <col style="width:32%;" />
                        <col style="width:60%;" />
                    </colgroup>
                    <thead>
                    <tr>
                        <th scope="col"><input type="checkbox" name="chkSignListAll" id="chkSignListAll" /></th>
                        <th scope="col" colspan="2" class="text-center" data-term="L.코드"></th>
                    </tr>
                    </thead>
                    <tbody id="tbodyCodeList">
                    </tbody>
                </table>
                <div class="p-1 text-right">
                </div>
            </div>
        </div>
    </div>
    <div class="clearfix">
        <div class="p-1 text-right">
                    <button type="button" class="btn btn-sm btn-outline-danger" id="selCdDelete" data-term="L.삭제"><i class="fa fa-minus"></i> </button>
                    <button type="button" class="btn btn-sm btn-primary" onclick="CODE.doConfirm();" data-term="L.선택"><i class="fa fa-plus"></i> </button>
        </div>
    </div>
</div>
<script type="text/javascript">

    function ParentCodeSelect() {
        var opt = {
            "multiSelect":"Y",             // 다중선택여부
            "selectType":"CHILD_ONLY",    // 최하위자식만 선택가능 null 값이면 부모도 선택 가능
            "parentCd":"ROOT"             // 최초 부모 코드
        };

        var $codeTreeArea = $("#treeCodeArea");

        var getQeuryParams = function(){
            return $("#schForm").serializeObject();
        };

        var treeLoadFilter = function(treeData, parent){

            var data = [].concat(treeData);
            if(treeData.data){
                data = [].concat(treeData.data);
            }

            data.forEach(function(itm, idx){
                data[idx].id = itm.cd;
                data[idx].text = paragonCmm.getLang(itm.langCd);
                data[idx].state = (itm.childCnt > 0) ? "closed" : "open";
                data[idx].attributes = itm;
            });
            return data;
        };

        var initTree = function(){

            console.debug("initTree....")

            $codeTreeArea.tree({
                url: paragonCmm.getUrl("/viself/code/CodeMng/listCode/json"),
                queryParams: getQeuryParams(),
                loadFilter: treeLoadFilter,
                onClick: function(node){
                    console.debug(node);

                    changeCodeView(node);
                }
            });
        };

        var drawTree = function(){
            var arrDEPTH = [];
//             if($("#sel_uuid_path").val() != ""){
//                 arrDEPTH = $("#sel_uuid_path").val().split("≫");
//             }
            //
            paragonCmm.treeInitSet("/viself/code/codeMng/listCode/json","CODE",JSON.stringify(arrDEPTH),opt.selectType);

            //그려넣을 id, 루트id, 최종
            paragonCmm.treeDrawChild("treeCodeArea",opt.parentCd);
        }
        var chkNode = function(data){
            // var data = JSON.parse(json);
            var arrObj = $("input:hidden[name='cd']","#tbodyCodeList");
            var rtnBool = true;
            arrObj.each(function(i, d){
                if(data.cd == $(d).val()){
                    rtnBool = false;
                }
            });

            return rtnBool;

        }
        var changeCodeView = function(data) {
            //-- 이미 선택된 사용자인지 여부 체크!
            if(chkNode(data)){
                // var data = JSON.parse(json);
                if("N" === opt.multiSelect){
                    $("#tbodyCodeList").html("");
                }
                $("#addCodeTmpl").tmpl(data).appendTo("#tbodyCodeList");
            }
        }
        var selectNode = function(code_id){

            var codGrp = $("#"+code_id);
            var obj = codGrp.children("input:hidden");
            var jsonData = "";
            $(obj).each(function(i, d){
                if(i > 0){jsonData+=",";}
                jsonData+= $(this).attr("name")+":\""+$(this).val()+"\"";
            });
            new Function("return data ={"+jsonData+"}")();
            changeCodeView(JSON.stringify(data));
        }
        var doSearch = function(isCheckEnter){
            if (isCheckEnter && event.keyCode != 13) {
                return;
            }

            var opts = {
                url: paragonCmm.getUrl("/viself/code/CodeMng/listCode/json"),
                params: getQeuryParams(),
                cbFnc: function(json){
                    $codeTreeArea.tree("loadData", { data: treeLoadFilter(json) });
                }
            };
            paragonCmm.callAjax(opts.url, opts.params, opts.cbFnc);
        }

        var doConfirm =  function() {
            var aprv = $("[data-meaning='code_tr']");
            if(aprv.length == 0){
                alert(paragonCmm.getLang("M.선택해주세요","L.코드"));
                return;
            }
            var json = [];
            aprv.each(function(i,e){
                var trTag = $(e);
                var obj = trTag.find("input:hidden");

                var jsonData = {};
                $(obj).each(function(i, d){
                    jsonData[$(d).attr("name")] = $(d).val();
                });
                json.push(jsonData);
            });

            var callbackFunction = $("#callbackFunction").val();

            if(typeof callBackFunc === "function"){
                callBackFunc(json);
            }
        }

        var initForm = function(){
            // init combo
            var $comboObj = $("#schField");
            htmlUtils.getCodeSelectOpt({
                targetId: $comboObj,
                parentCd: "NO_PARENT",
                jsonData: [{
                    cd: "LANG_CD",
                    txt: "코드명"
                },{
                    cd: "CD",
                    txt: "코드"
                }],
                initdata: "",
                valNm: "cd",
                txtNm: "txt"
            });
            $comboObj.addClass("form-control");
            $comboObj.addClass("form-control-sm");
        };

        var attchmentEvent = function(){
            $("#schValue").off();
            $("#schValue").on("keyup",function(){
                doSearch(true);
            });
            $("#cdSearchBtn").off();
            $("#cdSearchBtn").on("click", function(){
                doSearch();
            });
            $("#chkSignListAll").off();
            $("#chkSignListAll").on("click", function(){
                var bool = $(this).prop("checked");
                $("input:checkbox[name='chkSignList']").prop("checked", bool);
            });
            $("#selCdDelete").off();
            $("#selCdDelete").on("click", function(){
                $("input:checkbox[name='chkSignList']:checked").each(function(i, e){
                    $("#"+$(this).val()).remove();
                });
            });
        }
        var callBackFunc;
        var init = function (initFunc, callFunc) {

            console.debug("init......");
            initForm();

            attchmentEvent();
            if(typeof initFunc === "function"){
                $.extend(opt, initFunc());
            }
            if(typeof callFunc === "function"){
                callBackFunc = callFunc;
            }
            // drawTree();
            initTree();
        };
        return{
            init : init,
            selectNode:selectNode,
            doConfirm : doConfirm
        }
    }

    var CODE = new ParentCodeSelect();
    // $(function(){
    //     CODE.init();
    // });
</script>
<script id="addCodeTmpl" type="text/x-jquery-tmpl">
<tr data-meaning="code_tr" id="sel_\${cd}">
    <td align="center">
        <input type="checkbox"  value="sel_\${cd}" name="chkSignList" >
    </td>
    <td style="text-align:center" colspan="2">
        \${paragonCmm.getLang(langCd)}
        <input type="hidden" name="cd"            value="\${cd}">
        <input type="hidden" name="cdAbb"            value="\${cdAbb}">
        <input type="hidden" name="ordNo"            value="\${ordNo}">
        <input type="hidden" name="langCd"            value="\${langCd}">
        <input type="hidden" name="parentCd"        value="\${parentCd}">
        <input type="hidden" name="parentLangCd"    value="\${parentLangCd}">
        <input type="hidden" name="levelNo"        value="\${levelNo}">
        <input type="hidden" name="childCnt"        value="\${childCnt}">
        <input type="hidden" name="cdPath"            value="\${cdPath}">
        <input type="hidden" name="langCdPath"    value="\${langCdPath}">
        <input type="hidden" name="cdAttr1"        value="\${cdAttr1}">
        <input type="hidden" name="cdAttr2"        value="\${cdAttr2}">
        <input type="hidden" name="cdAttr3"        value="\${cdAttr3}">
        <input type="hidden" name="cdAttr4"        value="\${cdAttr4}">
        <input type="hidden" name="cdData"            value="\${cdData}">
        <input type="hidden" name="useYn"        value="\${useYn}">
        <input type="hidden" name="attrDesc1"    value="\${attrDesc1}">
        <input type="hidden" name="attrDesc2"    value="\${attrDesc2}">
        <input type="hidden" name="attrDesc3"    value="\${attrDesc3}">
        <input type="hidden" name="attrDesc4"    value="\${AttrDesc4}">
    </td>
</tr>