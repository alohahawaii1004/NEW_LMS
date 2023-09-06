<%--
  - 코드관리
  --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String siteLocale            = "KO";
    String schFieldCodestr         = "LANG_CD|코드명^CD|코드";
%>
<style>
    .codeTreeArea {
        width:100%;
        height:561px;
        vertical-align: top;
        border:1px solid #CDCDCD;
        overflow: auto;
    }
</style>
<div id="codeMngRootLayer">
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
                <form class="form-horizontal" id="codeMngSchForm" method="post">
                    <div class="row">
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block" data-term="L.구분"></label>
                                <div class="col-sm-9">
                                    <select id="codeMngSchField" name="schField"></select>
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
                <h6><i class="fa-regular fa-square-caret-right"></i> 코드트리 </h6>
                <div class="mb-3">
                    <div id="codeTreeArea" class="codeTreeArea bg-white"></div>
                </div>
                <div class="p-1">
                    <button type="button" class="btn btn-sm btn-primary" id="codeMngBtnWrite"><i class="fa fa-pencil"></i>
                        1레벨수정
                    </button>
                </div>
            </div>
            <div class="col-md-6 p-1">
                <h6><i class="fa-regular fa-square-caret-right"></i> 선택코드 </h6>
                <form name="codeMngform1" id="codeMngform1" method="post">
                    <input type="hidden" name="parentCd" />
                    <input type="hidden" name="cd" />
                    <table class="table table-bordered bg-white">
                        <colgroup>
                            <col style="width: 20%;"/>
                            <col style="width: 30%;"/>
                            <col style="width: 20%;"/>
                            <col style="width: 30%;"/>
                        </colgroup>
                        <tr height = "51px">
                            <th>코드경로</th>
                            <td colspan="3"><span id="txtNamePath"></span></td>
                        </tr>
                        <tr height = "51px">
                            <th>코드</th>
                            <td colspan="3">
                                <span id="txtCd"></span> 
                                <input type="hidden" name="selCdPath" id="selCdPath" value='<c:out value="${param.selCdPath}" />' >
                            </td>
                        </tr>
                        <tr height = "51px">
                            <th>코드 약어</th>
                            <td colspan="3"><span id="txtCdAbb"></span></td>
                        </tr>
                        <tr height = "51px">
                            <th>다국어코드</th>
                            <td><span id="txtLangCd"></span></td>
                            <th>다국어 명칭</th>
                            <td><span id="txtCdNm"></span></td>
                        </tr>
                        <tr height = "51px">
                            <th>코드Data</th>
                            <td colspan="3"><span id="txtCodeData"></span></td>
                        </tr>
                        <tr height = "51px">
                            <th>코드상태</th>
                            <td colspan="3"><span id="txtStatusName"></span></td>
                        </tr>
                        <tr height = "51px">
                            <th>정렬순서</th>
                            <td colspan="3"><span id="txtOrderNo"></span></td>
                        </tr>
                        <tr height = "51px">
                            <th>코드속성값 설명1</th>
                            <td><span id="txtCodeAttr1Desc"></span></td>
                            <th>코드속성값1</th>
                            <td><span id="txtCodeAttr1"></span></td>
                        </tr>
                        <tr height = "51px">
                            <th>코드속성값 설명2</th>
                            <td><span id="txtCodeAttr2Desc"></span></td>
                            <th>코드속성값2</th>
                            <td><span id="txtCodeAttr2"></span></td>
                        </tr>
                        <tr height = "51px">
                            <th>코드속성값 설명3</th>
                            <td><span id="txtCodeAttr3Desc"></span></td>
                            <th>코드속성값3</th>
                            <td><span id="txtCodeAttr3"></span></td>
                        </tr>
                        <tr height = "51px">
                            <th>코드속성값 설명4</th>
                            <td><span id="txtCodeAttr4Desc"></span></td>
                            <th>코드속성값4</th>
                            <td><span id="txtCodeAttr4"></span></td>
                        </tr>
                    </table>
                </form>
                <div class="p-1">
                    <button type="button" class="btn btn-sm btn-primary" id="codeMngBtnModify" style="display:none;" data-term="B.MODIFY"><i class="fa fa-edit"></i> </button>
                    <button type="button" class="btn btn-sm btn-outline-danger" id="codeMngBtnDelete" style="display:none;" data-term="B.DELETE"><i class="fa fa-minus"></i> </button>
                    <button type="button" class="btn btn-sm btn-outline-primary" id="codeMngBtnModify_child" style="display:none;"><i class="fa fa-edit"></i> 하위코드수정</button>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="codeMngPop1" class="hidden"></div>
<script src="<c:url value='/js/module/viself/code/codeMng.js'/>"></script>