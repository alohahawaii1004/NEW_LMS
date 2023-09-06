<%--
  - 코드 수정
  --%>
<%@ page import="com.vertexid.viself.hr.SysLoginVO"%>
<%@ page import="com.vertexid.spring.utils.SessionUtils" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<%
    //-- 로그인 사용자 정보 셋팅
    SysLoginVO loginUser     =     (SysLoginVO) SessionUtils.getLoginVO();
    String siteLocale            = loginUser.getSiteLocale();

    String schFieldCodestr         = "Y|사용^N|미사용";

%>
<div id="codeMngWirteRootLayer">
    <div class="clearfix border rounded bg-gray-light p-2">
        <div class="row">
            <div class="col-md-12">
                <form class="form-horizontal" name="codeMngWriteForm1" id="codeMngWriteForm1" method="post">
                    <input type="hidden" name="reqParentCd" id="reqParentCd" value="<c:out value='${param.parentCd}'/>" />
                    <input type="hidden" name="reqCd" id="reqCd" value="<c:out value='${param.cd}'/>" />
                    <input type="hidden" name="updateMode" id="updateMode" value='<c:out value="${param.updateMode}" />' />
                    <input type="hidden" name="selCdPath" id="selCdPath" value='<c:out value="${param.selCdPath}" />' />
                    <div class="row">
                        <div class="form-group col-md-4">
                            <div class="row">
                                <label class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block" data-term="L.상위코드"></label>
                                <div class="col-sm-9">
                                    <input type="text" name="parentCd" id="parentCd" class="form-control form-control-sm" placeholder="Parent Code" readonly>
                                    <small class="form-text text-muted">(※ 미입력시 현재 코드 정보를 최상위 정보로 등록)</small>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <button type="button" class="btn btn-sm btn-default" id="parentCodeSearchBtn" data-term="B.SELECT"><i class="fa fa-search"></i> </button>
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
            <div class="col-md-12 col-sm-12 pb-2">
                <div class="white-panel donut-chart" id="div_CODE_SUB_DATA" style="display:none;">
                    <h6 class="sub2_title"><i class="fa fa-caret-square-o-right"></i> 세부설정</h6>
                    <table class="table table-bordered table-sm bg-white">
                        <colgroup>
                            <col style="width: 10%;"/>
                            <col style="width: 15%;"/>
                            <col style="width: 10%;"/>
                            <col style="width: 15%;"/>
                            <col style="width: 10%;"/>
                            <col style="width: 15%;"/>
                            <col style="width: 10%;"/>
                            <col style="width: 15%;"/>
                        </colgroup>
                        <tr>
                            <th>코드 속성값1</th>
                            <td><input type="text" name="tmpCdAttr1"  id="tmpCdAttr1" data-meaning="TMP"   class="form-control form-control-sm" value="" onblur="paragonCmm.validateSpecialChars(this);" /></td>
                            <th>코드 속성값2</th>
                            <td><input type="text" name="tmpCdAttr2"  id="tmpCdAttr2" data-meaning="TMP"   class="form-control form-control-sm" value="" onblur="paragonCmm.validateSpecialChars(this);" /></td>
                            <th>코드 속성값3</th>
                            <td><input type="text" name="tmpCdAttr3"  id="tmpCdAttr3" data-meaning="TMP"   class="form-control form-control-sm" value="" onblur="paragonCmm.validateSpecialChars(this);" /></td>
                            <th>코드 속성값4</th>
                            <td><input type="text" name="tmpCdAttr4"  id="tmpCdAttr4" data-meaning="TMP"   class="form-control form-control-sm" value="" onblur="paragonCmm.validateSpecialChars(this);" /></td>
                        </tr>
                        <tr>
                            <th>설명</th>
                            <td><input type="text" name="tmpCdAttrDesc1" id="tmpCdAttrDesc1" data-meaning="TMP"   class="form-control form-control-sm" value="" onblur="paragonCmm.validateSpecialChars(this);" /></td>
                            <th>설명</th>
                            <td><input type="text" name="tmpCdAttrDesc2" id="tmpCdAttrDesc2" data-meaning="TMP"   class="form-control form-control-sm" value="" onblur="paragonCmm.validateSpecialChars(this);" /></td>
                            <th>설명</th>
                            <td><input type="text" name="tmpCdAttrDesc3" id="tmpCdAttrDesc3" data-meaning="TMP"   class="form-control form-control-sm" value="" onblur="paragonCmm.validateSpecialChars(this);" /></td>
                            <th>설명</th>
                            <td><input type="text" name="tmpCdAttrDesc4" id="tmpCdAttrDesc4" data-meaning="TMP"   class="form-control form-control-sm" value="" onblur="paragonCmm.validateSpecialChars(this);" /></td>
                        </tr>
                        <tr>
                            <th>코드데이터</th>
                            <td colspan="7"><textarea name="tmpCdData" id="tmpCdData" class="form-control form-control-sm" data-meaning="TMP" onkeyup="paragonCmm.validateMaxLength(this, '4000');"></textarea></td>
                        </tr>
                    </table>
                    <div class="buttonlist">
                        <input type="hidden" name="tmpIndex" id="tmpIndex" data-meaning="TMP" value=""/>
                        <button type="button" class="btn btn-sm btn-primary" id="subInputEnd" ><i class="fa fa-check"></i> 입력완료</button>
                    </div>
                </div>
            </div>
            <div class="col-lg-12 table-responsive" id="div_CODE_WRITE_LIST">
                <table class="table table-bordered table-sm bg-white">
                    <colgroup>
                        <col style="width:5%;" />
                        <col style="width:20%;" />
                        <col style="width:20%;" />
                        <col style="width:25%;" />
                        <col style="width:10%;" />
                        <col style="width:10%;" />
                        <col style="width:10%;" />
                    </colgroup>
                    <tr>
                        <th class="text-center">정렬</th>
                        <th class="text-center">코드</th>
                        <th class="text-center">코드약어</th>
                        <th class="text-center">다국어코드</th>
                        <th class="text-center">코드상태</th>
                        <th class="text-center">추가입력</th>
                        <th class="text-center">삭제</th>
                    </tr>
                    <tbody id="CODE_INPUT_LIST"></tbody>
                </table>
            </div>
        </div>
        <div class="row p-1">
            <div class="col-md-6">
                <button type="button" class="btn btn-sm btn-primary" id="codeMngWriteBtnSave" data-term="B.SAVE"><i class="fa fa-save"></i> </button>
                <button type="button" class="btn btn-sm btn-outline-primary" id="codeMngWriteBtnClose" data-term="B.LIST"><i class="fa fa-list-ul"></i> </button>
                <button type="button" class="btn btn-sm btn-outline-primary" id="codeMngWriteBtnAdd" data-meaning="code_list_add" data-term="B.ADD"><i class="fa fa-plus"></i> </button>
            </div>
            <div class="col-md-6 text-right">
            </div>
        </div><!-- // grid btn layer -->
    </div>
</div>
<script id="TMPL_CODE_WIRTE" type="text/x-jquery-tmpl">
    <tr align="center" data-meaning="\${rn}">
        <td>\${rn}</td>
        <td>
            <input type="text" name="cd" id="cd_\${rn}" class="form-control form-control-sm" value="\${cd}" onfocus="this.select();" onblur="paragonCmm.validateSpecialChars(this);" />
        </td>
        <td><input type="text" name="cdAbb"   class="form-control form-control-sm" value="\${cdAbb}" onblur="paragonCmm.validateSpecialChars(this);" /></td>
        <td><input type="text" name="langCd"  class="form-control form-control-sm autoLangCd_\${rn}" style="width:100%;" value="\${langCd}" onblur="paragonCmm.validateSpecialChars(this);" /></td>
        <td>
            {{html CODDWRITE.getUseYnHtml(useYn)}}
        </td>
        <td>
            <button type="button" class="btn btn-sm btn-outline-primary" onclick="CODDWRITE.subDataOpen('\${rn}');"><i class="fa fa-plus"></i></button>
        </td>
        <td align="center">
            <button type="button" class="btn btn-sm btn-outline-danger" onclick="CODDWRITE.removeCodLine('\${rn}','\${childCnt}');"><i class="fa fa-minus"></i></button>
            <input type="hidden" name="cud" id="cud_\${rn}" value="\${cud}"/>
            <input type="hidden" name="cdAttr1" id="cdAttr1_\${rn}" value="\${cdAttr1}"/>
            <input type="hidden" name="cdAttr2" id="cdAttr2_\${rn}" value="\${cdAttr2}"/>
            <input type="hidden" name="cdAttr3" id="cdAttr3_\${rn}" value="\${cdAttr3}"/>
            <input type="hidden" name="cdAttr4" id="cdAttr4_\${rn}" value="\${cdAttr4}"/>
            <input type="hidden" name="attrDesc1" id="attrDesc1_\${rn}" value="\${attrDesc1}"/>
            <input type="hidden" name="attrDesc2" id="attrDesc2_\${rn}" value="\${attrDesc2}"/>
            <input type="hidden" name="attrDesc3" id="attrDesc3_\${rn}" value="\${attrDesc3}"/>
            <input type="hidden" name="attrDesc4" id="attrDesc4_\${rn}" value="\${attrDesc4}"/>
            <input type="hidden" name="cdData" id="cdData_\${rn}" value="\${cdData}"/>
        </td>
    </tr>
</script>
<script src="<c:url value='/js/module/viself/code/codeMngWrite.js'/>"></script>
<%--// 글쓰기 작업 시 세션타임아웃 방지 처리 //--%>
