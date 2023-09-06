<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<script type="text/javascript"
        src="<c:url value='/js/vendor/jquery-easyui/extension/datagrid-cellediting/datagrid-cellediting.js'/>?ver=<spring:eval expression="@cmmProperties.getProperty('js.version')"/>"></script>
<div class="clearfix border rounded bg-gray-light p-2">
    <div class="row">
        <div class="col-md-11">
            <form id="mLangMngForm" method="post">
                <div class="row" id="bbsSearchArea">
                    <div class="form-group col-md-4">
                        <div class="row">
                            <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block"
                                   data-term="L.코드"></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control form-control-sm" name="langCd"
                                       placeholder="code">
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <div class="row">
                            <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block">KO</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control form-control-sm" name="ko"
                                       placeholder="한국어">
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <div class="row">
                            <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block">EN</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control form-control-sm" name="en"
                                       placeholder="English">
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <div class="row">
                            <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block">JA</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control form-control-sm" name="ja"
                                       placeholder="日本語">
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <div class="row">
                            <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block">ZH</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control form-control-sm" name="zh"
                                       placeholder="中囯语">
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <div class="row">
                            <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block">TYPE</label>
                            <div class="col-sm-9">
                                <label class="radio-inline"><input type="radio" name="TYPE" id="TYPE_1" value=""
                                                                   data-type="search" onClick="MLANG.doSearch()"
                                                                   checked/> 전체</label>
                                <label class="radio-inline"><input type="radio" name="TYPE" id="TYPE_2" value="L"
                                                                   data-type="search" onClick="MLANG.doSearch()"/>
                                    라벨</label>
                                <label class="radio-inline"><input type="radio" name="TYPE" id="TYPE_3" value="M"
                                                                   data-type="search" onClick="MLANG.doSearch()"/>
                                    메시지</label>
                                <label class="radio-inline"><input type="radio" name="TYPE" id="TYPE_4" value="B"
                                                                   data-type="search" onClick="MLANG.doSearch()"/>
                                    버튼</label>
                                <label class="radio-inline"><input type="radio" name="TYPE" id="TYPE_5" value="R"
                                                                   data-type="search" onClick="MLANG.doSearch()"/>
                                    라디오</label>
                                <label class="radio-inline"><input type="radio" name="TYPE" id="TYPE_6" value="D"
                                                                   data-type="search" onClick="MLANG.doSearch()"/>
                                    문서</label>
                                <label class="radio-inline"><input type="radio" name="TYPE" id="TYPE_&" value="C"
                                                                   data-type="search" onClick="MLANG.doSearch()"/>
                                    코드</label>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-md-1 d-none d-sm-block align-self-center">
            <div class="row p-1">
                <button class="btn btn-outline-primary btn-sm p-1" type="button" id="issueMngResetBtn1" onclick="MLANG.doInit(document.form1)"><i
                        class="fa fa-undo"></i> RESET
                </button>
            </div>
            <div class="row p-1">
                <button class="btn btn-primary btn-sm p-1" type="button" id="issueMngSearchBtn1" onclick="MLANG.doSearch();"><i
                        class="fa fa-search"></i> SEARCH
                </button>
            </div>
        </div>
        <div class="col-sm-3 d-block d-sm-none">
            <button class="btn btn-primary btn-sm" type="button" id="issueMngSearchSmBtn"><i
                    class="fa fa-search"></i> SEARCH
            </button>
            <button class="btn btn-outline-primary btn-sm" type="button" id="issueMngResetSmBtn"><i
                    class="fa fa-undo"></i> RESET
            </button>
        </div>
    </div>
</div>
<div class="data-table clearfix">
    <div class="row p-1">
        <div class="col-md-6">
            <select name="ADD_TYPE" id="ADD_TYPE" class="form-control input-sm" style="width:80px;display: inline-block;">
                <option value="l">라벨</option>
                <option value="M">메시지</option>
                <option value="B">버튼</option>
                <option value="R">라디오</option>
                <option value="D">문서</option>
                <option value="C">코드</option>
            </select>
            <input type="text" id="ADD_CODE" class="form-control input-sm" size="40" onkeydown="MLANG.doProc(true)"
                   style="width:180px;display: inline-block;"/>
            <button class="btn btn-primary btn-sm" type="button" onClick="MLANG.doProc();" data-term="B.ADD"><i class="fa fa-plus"></i> </button>
        </div>
        <div class="col-md-6 text-right">
            <div class="main-btn-grp1">
<%--                <button class="btn btn-primary btn-sm" type="button" onClick="paragonCmm.openWindow('/viself/mlang/mLangMngList.popup',1024,700,'Lang List',true,true);" id="openPopup"><i class="fa fa-list-ul"></i> POPUP</button>--%>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <table id="listTable" class="easyui-datagrid"
                   data-options="singleSelect:true,
							  striped:true,
							  fitColumns:false,
							  rownumbers:true,
							  multiSort:true,
							  pagination:true,
							  pageList:[10,50,100,200],
							  pagePosition:'bottom',
							  selectOnCheck:false,
							  checkOnSelect:false,
							  url:'',
							  method:'post'"
                   style="height:auto">
                <thead>
                <tr>
                    <th data-options="field:'langUid',width:0,hidden:true"></th>
                    <th data-options="field:'langCd',width:300,halign:'CENTER',align:'LEFT',editor:'text',sortable:true">
                        코드
                    </th>
                    <th data-options="field:'ko',width:300,halign:'CENTER',align:'LEFT',editor:'text',sortable:true">
                        KO
                    </th>
                    <th data-options="field:'en',width:300,halign:'CENTER',align:'LEFT',editor:'text',sortable:true">
                        EN
                    </th>
                    <th data-options="field:'ja',width:300,halign:'CENTER',align:'LEFT',editor:'text',sortable:true">
                        JA
                    </th>
                    <th data-options="field:'zh',width:300,halign:'CENTER',align:'LEFT',editor:'text',sortable:true">
                        ZH
                    </th>
                </tr>
            </table>
        </div>
    </div>
    <div class="row p-1">
        <div class="col-md-6">
        </div>
        <div class="col-md-6 text-right">
        </div>
    </div><!-- // grid btn layer -->
</div>
<script>
    function Mlang() {

        var $form = $("#mLangMngForm");
        var doSearch = function (isCheckEnter) {
            if (isCheckEnter && event.keyCode != 13) {
                return;
            }
            $("#listTable").datagrid("load", $form.serializeObject());
        }
        var modifyLang = function (row, index, changes) {
            changes["langUid"] = row["langUid"];
            $.ajax({
                url: paragonCmm.getUrl("/viself/mlang/MLangMng/updateData/json")
                , type: "POST"
                , dataType: "json"
                , data: changes
            })
                .done(function (data) {

                    if (Object.keys(data).length > 0) {
                        doSearch();
                    } else {
                        alert("동일한코드명이존재합니다");
                        reject();
                    }
                })
                .fail(function () {
                });
        }
        var doInit = function (frm) {
            frm.reset();
        }
        var doProc = function (isCheckEnter) {
            if (isCheckEnter && event.keyCode != 13) {
                return;
            }
            $.messager.confirm("Confirm", paragonCmm.getLang("M.ALERT_SUBMIT", "L.저장"), function (r) {
                if ($("#ADD_CODE").val() != "" && r) {
                    var data = {};
                    data["langCd"] = $("#ADD_TYPE").val() + "." + $("#ADD_CODE").val()
                    $.ajax({
                        url: paragonCmm.getUrl("/viself/mlang/MLangMng/saveData/json")
                        , type: "POST"
                        , dataType: "json"
                        , data: data
                    })
                        .done(function (data) {
                            if (Object.keys(data).length > 0) {
                                $.messager.alert({
                                    title: "Info",
                                    msg: "처리되었습니다",
                                    icon: "info",
                                    fn: function () {
                                        $("input[name=ko]").val("");
                                        $("input[name=en]").val("");
                                        $("input[name=zh]").val("");
                                        $("input[name=ja]").val("");
                                        $("input[name=langCd]").val($("#ADD_CODE").val());
                                        $("#ADD_CODE").val("");
                                        $("input:radio[name='TYPE']").eq(0).prop("checked", true);
                                        doSearch();
                                    }
                                });
                            } else {
                                alert(paragonCmm.getLang("M.동일한코드가존재합니다확인해주세요"));
                            }

                        })
                        .fail(function () {
                            alert(paragonCmm.getLang("M.처리중_오류발생", "L.수정"));
                        });
                }
            });
        }
        var reject = function () {
            $('#listTable').datagrid('rejectChanges');
        }

        var loadGrid = function () {
            $('#listTable').datagrid({
                url: paragonCmm.getUrl('/viself/mlang/MLangMng/list/json'),
                loadFilter: paragonCmm.easyuiLoadFilter,
                onBeforeLoad: function () {
                    paragonCmm.showBackDrop();
                },
                onLoadSuccess: function (json) {
                    paragonCmm.hideBackDrop();
                },
                onLoadError: function () {
                    paragonCmm.hideBackDrop();
                },
                onAfterEdit: function (index, row, changes) {
                    if (Object.keys(changes).length > 0) {//객체의 키의 갯수(IE:8은 동작안함)
                        $.messager.confirm("Confirm", paragonCmm.getLang("M.ALERT_SUBMIT", "L.변경"), function (r) {
                            if (r) {
                                modifyLang(row, index, changes);
                            } else {
                                reject()
                            }
                        })
                    }
                }
            });

            $('#listTable').datagrid('enableCellEditing').datagrid('gotoCell', {
                index: 0,
                field: 'langCd'

            });
        }
        var setEvent = function () {
            $("input:text", $form).off();
            $("input:text", $form).on("keyup", function (e) {
                doSearch(true);
                return false;
            });
        }
        var init = function () {
            $('#listTabs-LIST').css('width', '100%');
            loadGrid();
            if (opener) {
                $(window).bind('resize', $('.datagrid-f').datagrid('resize'));
            } else {
                $("#openPopup").show();
            }

            setEvent();
        }
        return {
            init: init,
            loadGrid: loadGrid,
            doProc: doProc,
            doSearch: doSearch
        }
    }

    var MLANG = new Mlang();
    MLANG.init();
</script>