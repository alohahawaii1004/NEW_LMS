<%--
  - 게시판
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<style>
    .pop-notice {
        background-color: #f2dede;
        color: #a94442;
    }
</style>
<div id="bbsList" data-opener-data="<c:out value='${param.openerData}'/>"
     data-json="<c:out value='${param.jsonData}'/>">
    <div class="row">

    </div>
    <div class="clearfix border rounded bg-gray-light p-2">
        <div class="row">
            <div class="col-md-11">
                <form class="form-horizontal" id="bbsListForm">
                    <input type="hidden" name="bbsTpCd" data-col="bbsTp" value="<c:out value='${param.bbsTpCd}'/>">
                    <div class="row" id="bbsSearchArea">
                        <div class="form-group col-md-4" id="bbsViewCtgryLayer" style="display: none;">
                            <div class="row">
                                <label class="col-lg-2 col-md-2"><strong data-term="L.유형"></strong></label>
                                <div class="col-lg-8 col-md-8">
                                    <select class="form-control form-control-sm" id="bbsSchType" name="bbsSchType"></select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-lg-2 col-md-2"><strong data-term="L.등록일자"></strong></label>
                                <div class="col-lg-9 col-md-9">
                                    <div class="form-inline">
                                        <input type="text" name="schFromRegDte" class="form-control form-control-sm" style='width:46%;'>
                                        <span class='p-1'> ~ </span>
                                        <input type="text" name="schToRegDte" class="form-control form-control-sm" style='width:46%;'>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-8">
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="form-inline">
                                        <select class="form-control form-control-sm" id="bbsSchGbn" name="bbsSchGbn"></select>
                                        <input type="text" name="bbsSchWord" class="form-control form-control-sm" data-type="search" style="width:90%;">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-md-1 d-none d-sm-block align-self-center">
                <div class="row p-1">
                    <button class="btn btn-outline-secondary btn-sm p-1" type="button" id="bbsInitBtn"><i class="fa fa-undo"></i> RESET</button>
                </div>
                <div class="row p-1">
                    <button class="btn btn-primary btn-sm p-1" type="button" id="bbsSchBtn"><i class="fa fa-search"></i> SEARCH</button>
                </div>
            </div>
            <div class="col-sm-3 d-block d-sm-none">
                <button class="btn btn-primary btn-sm" type="button" id="ipListSearchSmBtn"><i class="fa fa-search"></i> SEARCH</button>
                <button class="btn btn-outline-secondary btn-sm" type="button" id="ipListResetSmBtn"><i class="fa fa-undo"></i> RESET</button>
            </div>
        </div>
    </div>
    <div class="data-table clearfix">
        <div class="row p-1">
            <div class="col-md-6">
            </div>
            <div class="col-md-6 text-right">
                <button type="button" class="btn btn-primary btn-sm" id="writeBtn" data-term="B.WRITE"
                        style="display: none;"><i class="fa fa-plus"></i></button>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <table id="listTable"></table>
            </div>
        </div>
    </div>
</div>
<div id="bbsViewModal" style="width:600px;height:400px;display: none;"></div>
<div id="bbsWriteModal" style="width:600px;height:400px;display: none;"></div>
<script src="<c:url value='/js/module/paragon/bbs/bbsList.js' />"></script>