<%--
  - 메뉴관리
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<%
    //-- 로그인 사용자 정보 셋팅
    String schFieldCodestr = "LANG_CD|이름^CD|코드";
%>
<style>
    .codeTreeArea {
        width: 100%;
        height:389px;
        vertical-align: top;
        border: 1px solid #CDCDCD;
        overflow: auto;
    }
</style>
<div id="menuMngRootLayer">
    <%-- search conditions --%>
    <div class="row">
        <div class="col-md-6">
        </div>
        <div class="col-md-6 text-right">
        </div>
    </div>
    <div class="clearfix border rounded bg-gray-light p-2">
        <div class="row">
            <div class="col-md-11">
                <form class="form-horizontal" id="menuMngSchForm" method="post">
                    <div class="row">
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block" data-term="L.구분"></label>
                                <div class="col-sm-9">
                                    <select id="menuMngSchField" name="schField"></select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-8">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block" data-term="L.명칭"></label>
                                <div class="col-sm-9">
                                    <input type="text" name="schValue" id="schValue" class="form-control form-control-sm" data-type="search"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-md-1 d-none d-sm-block">
                <button class="btn btn-primary btn-sm" type="button" id="cdSearchBtn"><i class="fa fa-search"></i> SEARCH</button>
            </div>
            <div class="col-sm-3 d-block d-sm-none">
                <button class="btn btn-outline-secondary btn-sm" type="button" id="cdSearchSmBtn"><i class="fa fa-undo"></i> RESET</button>
            </div>
        </div>
    </div>
    <%-- data table --%>
    <div class="clearfix">
        <div class="row">
            <div class="col-md-6 p-1">
                <h6><i class="fa-regular fa-square-caret-right"></i> 메뉴트리 </h6>
                <div class="mb-3">
                    <div id="menuMngTreeArea" class="codeTreeArea bg-white"></div>
                </div>
                <div class="p-1">
                    <button type="button" class="btn btn-sm btn-primary" id="menuMnglevelOne"><i class="fa fa-pencil"></i>
                        1레벨수정
                    </button>
                </div>
            </div>
            <div class="col-md-6 p-1">
                <h6><i class="fa-regular fa-square-caret-right"></i> 선택메뉴 </h6>
                <form name="menuMngform1" id="menuMngform1" method="post">
                    <input type="hidden" name="updateMode" id="updateMode"/>
                    <table class="table table-bordered bg-white">
                        <colgroup>
                            <col style="width: 20%;"/>
                            <col style="width: 30%;"/>
                            <col style="width: 20%;"/>
                            <col style="width: 30%;"/>
                        </colgroup>
                        <tr height="51px">
                            <th>메뉴경로</th>
                            <td colspan="3"><span id="txtNamePath"></span></td>
                        </tr>
                        <tr height="51px">
                            <th>메뉴 ID</th>
                            <td colspan="3">
                                <input type="text" class="form-control form-control-sm" id="menuId" name="menuId" readonly="readonly">
                                <input type="hidden" name="menuMngUuidPath" id="menuMngUuidPath"
                                       value='<c:out value="${param.menuMngUuidPath}" />'/>
                            </td>
                        </tr>
                        <tr height="51px">
                            <th>부모 메뉴 ID</th>
                            <td colspan="3"><input type="text" class="form-control form-control-sm" id="parentMenuId" name="parentMenuId" readonly="readonly"></td>
                        </tr>
                        <tr height="51px">
                            <th>다국어코드</th>
                            <td colspan="3"><span id="langCd"></span></td>
                        </tr>
                        <tr height="51px">
                            <th>Url Path</th>
                            <td colspan="3"><span id="urlPath"></span></td>
                        </tr>
                        <tr height="51px">
                            <th>JSON_DATA</th>
                            <td colspan="3"><span class="align-middle" id="jsonData"></span></td>
                        </tr>
                        <tr height="51px">
                            <th>사용여부
                                <small id="passwordHelpBlock" class="form-text text-muted">
                                    0:폐기,1:사용,2:숨김
                                </small></th>
                            <td colspan="3">
                                <span class="align-middle" id="useYn"></span>

                            </td>
                        </tr>
                    </table>
                </form>
                <div class="p-1">
                    <button type="button" class="btn btn-sm btn-primary" id="menuMngbtnModify" style="display:none;"
                            data-term="B.MODIFY"><i class="fa fa-edit"></i></button>
                    <%--                    <button type="button" class="btn btn-default" id="codeMngBtnDelete" style="display:none;" data-term="B.DELETE"><i class="fa fa-minus"></i> </button>--%>
                    <button type="button" class="btn btn-sm btn-outline-primary" id="menuMngbtnModify_child" style="display:none;"><i
                            class="fa fa-edit"></i> 하위메뉴수정
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="menuMngPop1" class="hidden"></div>
<script src="<c:url value='/js/module/viself/menu/menuMng.js'/>"></script>