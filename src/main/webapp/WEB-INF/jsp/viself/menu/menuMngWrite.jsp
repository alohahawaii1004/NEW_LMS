<%--
  - 메뉴 수정
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.vertexid.spring.utils.SessionUtils" %>
<%@ page import="com.vertexid.viself.hr.SysLoginVO" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<!-- 자동 완성 기능을 위한 js 로드 -->
<%-- <script type="text/javascript" src="<c:url value='/js/vendor/jquery-ui/1.12.1/jquery-ui.js'/>"></script> --%>
<%-- <link rel="stylesheet" type="text/css" href="<c:url value='/js/vendor/jquery-ui/1.12.1/themes/base/jquery-ui.css'/>" /> --%>
<%
    //-- 로그인 사용자 정보 셋팅
    SysLoginVO loginUser = (SysLoginVO) SessionUtils.getLoginVO();
    String siteLocale            = loginUser.getSiteLocale();
%>
<div id="menuMngWriteRootLayer">
    <%-- search conditions --%>
    <div class="row">
        <div class="col-md-6">
        </div>
        <div class="col-md-6 text-right">
        </div>
    </div>
    <div class="clearfix border rounded bg-gray-light p-2">
        <div class="row">
            <div class="col-md-12">
                <form class="form-horizontal" name="menuMngWriteform1" id="menuMngWriteform1" method="post">
                    <input type="hidden" name="updateMode" id="updateMode" value='<c:out value="${param.updateMode}" />' />
                    <input type="hidden" name="reqMenuId" id="reqMenuId" value="<c:out value='${param.menuId}'/>" />
                    <input type="hidden" name="reqParentMenuId" id="reqParentMenuId" value="<c:out value='${param.parentMenuId}'/>" />
                    <input type="hidden" name="sel_uuid_path" id="sel_uuid_path" value='<c:out value="${param.sel_uuid_path}" />' />
                    <div class="row">
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block" data-term="L.상위메뉴"></label>
                                <div class="col-sm-9">
                                    <input type="text" name="parentMenuId" id="parentMenuId" class="form-control form-control-sm" placeholder="Parent Menu ID">
                                    <small class="form-text text-muted">직접 입력하세요.(※ 미입력시 현재 코드 정보를 최상위 정보로 등록)</small>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="data-table clearfix">
        <div class="row p-1">
            <div class="col-md-6">
            </div>
            <div class="col-md-6 text-right">
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12 table-responsive" id="div_CODE_WRITE_LIST">
                <table class="table table-bordered table-sm bg-white">
                    <colgroup>
                        <col style="width:5%;" />
                        <col style="width:10%;" />
                        <col style="width:15%;" />
                        <col style="width:10%;" />
                        <col style="width:15%;" />
                        <col style="width:35%;" />
                        <col style="width:5%;" />
                        <col style="width:5%;" />
                    </colgroup>
                    <tr>
                        <th class="text-center">정렬</th>
                        <th class="text-center">메뉴ID</th>
                        <th class="text-center">다국어코드</th>
                        <th class="text-center">모듈 ID</th>
                        <th class="text-center">메뉴 ICON</th>
                        <th class="text-center">Json Data</th>
                        <th class="text-center">상태</th>
                        <!--                         <th class="text-center">추가<br/>입력</th> -->
                        <th class="text-center">
                            삭제
                        </th>
                    </tr>
                    <tbody id="MENU_INPUT_LIST"></tbody>
                </table>
            </div>
        </div>
        <div class="row p-1">
            <div class="col-md-6">
                <button type="button" class="btn btn-sm btn-primary" id="menuMngWriteBtnSave" data-term="B.SAVE"><i class="fa fa-save"></i> </button>
                <button type="button" class="btn btn-sm btn-outline-primary" id="menuMngWriteBtnList" data-term="B.LIST"><i class="fa fa-list-ul"></i> </button>
                <button type="button" class="btn btn-sm btn-outline-primary" id="menuListAdd" data-meaning="code_list_add" data-term="B.ADD"><i class="fa fa-plus"></i> </button>
            </div>
            <div class="col-md-6 text-right">
            </div>
        </div><!-- // grid btn layer -->
    </div>
</div>
<script id="TMPL_MENU_WIRTE" type="text/x-jquery-tmpl">
    <tr align="center" data-meaning="\${ordNo}" class="menuRow">
        <td scope="row">\${ordNo}</td>
        <td>
            <input type="text" class="form-control form-control-sm" name="menuId" id="menuId_\${ordNo}" value="\${menuId}" onfocus="this.select();" onblur="paragonCmm.validateSpecialChars(this);" >
        </td>
        <td>
            <input type="text" class="autoLangCd" style="width:100%;" name="langCd" value="\${langCd}" >
        </td>
        <td><input type="text" class="form-control form-control-sm" name="moduleId" value="\${moduleId}" data-index="\${ordNo}" readonly="readonly"></td>
        <td><input type="text" class="form-control form-control-sm" name="menuIcon" value="\${menuIcon}" ></td>
        <td><input type="text" class="form-control form-control-sm" name="jsonData" value="\${jsonData}" ></td>
        <td>
            <select name="useYn" class="form-control form-control-sm">
                <option value="1" title="" {{if useYn == "1"}}selected{{/if}} class="text-info">정상</option>
                <option value="0" title="" {{if useYn == "0"}}selected{{/if}} class="text-danger">폐기</option>
                <option value="2" title="" {{if useYn == "2"}}selected{{/if}} class="text-light-gray">숨김</option>
            </select>
        </td>
        <td align="center">
            <button type="button" class="btn btn-sm btn-outline-danger" onclick="menuWrite.removeCodLine('\${menuId}','\${childCnt}',\${authCnt});">
                <i class="fa fa-minus" ></i>
            </button>
            <input type="hidden" name="cud" id="cud_\${ordNo}" value="\${cud}">
        </td>
    </tr>
</script>
<script src="<c:url value='/js/module/viself/menu/menuMngWrite.js'/>"></script>
<%--// 글쓰기 작업 시 세션타임아웃 방지 처리 //--%>
