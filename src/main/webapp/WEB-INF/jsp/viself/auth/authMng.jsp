<%--
  - @(#)
  - Description:
  -    시스템 권한 관리
  --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<%
    //-- 로그인 사용자 정보 셋팅
    String schFieldCodestr         = "LANG_CD|이름^CD|코드";
%>
<div class="row mt">
    <h5 class="sub1_title" data-term="L.시스템권한관리"><i class="fa fa-file-text"></i> </h5>
    <div class="col-sm-3">
       <section class="panel" style="border: 0px;">
         <div class="panel-body" style="border: 0px;">
           <ul class="nav nav-pills nav-stacked labels-info ">
                <li>
                <h6 class="sub2_title"><i class="fa fa-file-text"></i> 권한관리</h6>
                    <div class="buttonlist">
                    <div class = "right">
                          <input type="text" class="form-control input-sm" id="addAuthNm" placeholder="권한명"  style="width:110px;display:inline-block;">
                          <input type="text" class="form-control input-sm" id="addAuthCd" placeholder="권한코드"  style="width:80px;display:inline-block;">
                          <span class="ui_btn medium icon" id="addAuthBtn" ><i class="fa fa-pencil" ><a href="javascript:void(0)" data-term="B.REG"></a></i></span>
                          <span class="ui_btn medium icon" id="resAuthBtn" ><i class="fa fa-refresh" ><a href="javascript:void(0)" data-term="B.INIT"></a></i></span>
                          <span class="ui_btn medium icon" id="uptAuthBtn" style="display:none;"><i class="fa fa-pencil" ><a href="javascript:void(0)" data-term="B.MODIFY"></a></i></span>
                    </div>
                </div>
                </li>
             <li>
              <div class="custom-check goleft mt">
                <table class="table table-hover custom-check">
                  <thead>
                      <tr>
                          <th>Auth Nm</th>
                          <th>Auth Cd</th>
                      </tr>
                  </thead>
                  <tbody id="rolesTbody">
                  </tbody>
                </table>
              </div>
              <!-- /table-responsive -->
              <span style="color: red;font-size: 10px;">
                  ※권한 순서상 상단의 권한이 우선적용됩니다.<br/>
                  ex) 시스템관리자,법무담당자 권한을 가진 사용자는 시스템관리자 권한이 기본임<br/>
                  ※일반사용자 권한은 삭제 하면 안됨.(사용자선택 필요없음)
              </span>
             </li>
           </ul>
         </div>
       </section>
       <br/>
       <br/>
       <section class="panel mt row">
         <div class="panel-body" style="border: 0px;">
             <ul class="nav nav-pills nav-stacked labels-info ">
                <li style="width: 100%;">
                    <h6 class="sub2_title"><i class="fa fa-file-text"></i> 권한별 사용자관리</h6>
                <br/>
                    <div class="buttonlist">
                    <div class = "right">
                       <input type="text" id="searchUserKeyword" placeholder="M.소속_또는_이름" data-term="M.소속_또는_이름">
                    </div>
                </div>
                </li>
             <li style="width: 100%;">
              <div class="custom-check goleft mt">
                <table class="table table-hover custom-check">
                 <thead>
                      <tr>
                          <th>Name</th>
                      </tr>
                  </thead>
                  <tbody id="selectUserTbody">
                  </tbody>
                </table>
              </div>
              <!-- /table-responsive -->
             </li>
           </ul>
         </div>
       </section>
     </div>
     <div class="col-sm-8">
       <section class="panel">
         <div class="panel-body minimal" style="border: 0px;padding: 5px;" >
             <div class="mail-option">
                 <h6 class="sub2_title"><i class="fa fa-file-text"></i> 권한별 메뉴관리</h6>
                <ul class="unstyled inbox-pagination">
                   <li>
                           <span class="ui_btn medium icon" id="saveAuthMenuBtn" ><i class="fa fa-pencil" ><a href="javascript:void(0)" data-term="B.SAVE"></a></i></span>
                   </li>
                 </ul>
             </div>
             <table id="AuthMenuTable" class="easyui-treegrid" style="width:100%;height:650px">
                <thead>
                    <tr>
                        <th data-options="field:'alwCd',width:0,hidden:true"></th>
                        <th data-options="field:'langCd',formatter:paragonCmm.getLang, width:'40%'">Name</th>
                        <th data-options="field:'menuId', width:'30%'">Menu ID</th>
                        <th data-options="field:'urlPath', width:'30%'">URL</th>
                    </tr>
                </thead>
            </table>
         </div>
       </section>
     </div>
</div>
<script src="<c:url value="/js/module/viself/auth/authMng.js"/>"></script>