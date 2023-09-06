<%--
  - Login
  --%>
<%@ page import="com.vertexid.spring.utils.CmmProperties" %>
<%@ page import="com.vertexid.spring.utils.BaseProperties" %>
<%@ page import="com.vertexid.viself.hr.SysLoginVO" %>
<%@ page import="com.vertexid.spring.utils.SessionUtils" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<%

    //-- 파라메터 셋팅
    String idx = null;
    String currentRequestUrl = request.getServerName().toString();
    String remoteIp = StringUtils.defaultString(request.getRemoteAddr());

    String errMsg = StringUtils.defaultString((String) request.getAttribute("msg"));

    //-- 테스트 사용자 로그인 기능 사용여부

    SysLoginVO loginUser = (SysLoginVO) SessionUtils.getLoginVO();
    if (loginUser == null) {
        loginUser = new SysLoginVO();
    }

    String remoteAddr = SessionUtils.getRemoteIP(request);
    boolean bool = CmmProperties.isLocal();
//    if ("220.89.170.154".equals(remoteAddr) || "0:0:0:0:0:0:0:1".equals(remoteAddr) ||
//            "127.0.0.1".equals(remoteAddr)) {
//        bool = true;
//    }
    if ("".equals(loginUser.getLoginId())) {
%>
<div class="container-fluid">
    <div class="row min-vh-100 bg-100">
        <div class="col-6 d-none d-lg-block" style="position: relative;
                width: 100%;
                min-height: 100%;
                background: -webkit-linear-gradient(rgba(0, 0, 0, 0.7),rgba(0, 0, 0, 0.7)), url(<c:url value='/img/login-bg.jpg'/>), no-repeat;
                background-size:cover;">
            <div class="bg-holder">
                <div class="row">
                    <div class="col-12 pl-5">
                        <p class="p-5">
                            <h1 class="text-vidred">Vertex ID. <small class="text-muted"><span class="text-default">고객지원시스템</span></small></h1>
                            <span class="pl-1 text-vidred">Vertex ID. Customer Support System</span>
                        </p>
                    </div>
                </div>
                <%--            <div class="bg-holder" style="background:url(<c:url value='/js/vendor/dashio/img/login-bg.jpg'/>);background-position: 50% 20%;">--%>
            </div>
        </div><!-- // -->
        <div class="col-sm-10 col-md-6 px-sm-0 align-self-center mx-auto py-5">
            <div class="container">
                <div class="card col-md-8 offset-md-2 card-outline card-vidred">
                    <div class="card-header text-center">
                        <span class="h1">Support</span> <small class="text-muted text-default">Paragon 고객지원 시스템</small>
                    </div>
                    <div class="card-body">
                        <form class="form-login" id="loginForm" name="form1" onSubmit="return false;" action="<c:url value='/login/proc'/>">
                            <input type="hidden" name="<c:out value='${_csrf.parameterName}'/>" id="<c:out value='${_csrf.parameterName}'/>" value="<c:out value='${_csrf.token}'/>"/>
                            <p class="login-box-msg">시스템을 이용하기 위해서는 로그인이 필요합니다.</p>
                            <div class="row mb-3">
                                <input type="text" name="loginId" class="form-control" placeholder="User ID" autofocus>
                            </div>
                            <div class="row mb-3">
                                <input type="password" name="loginPwd" class="form-control" placeholder="Password">
                            </div>
                            <%--<div class="row mb-2 pb-2 border-bottom">
                                <div class="col-md-4 text-left">
                                    <label ><input type="checkbox" name="chkSaveId" id="chkSaveId" value="Y"> 아이디저장</label>
                                </div>
                                <div class = "col-md-8 text-right">
                                    <label><input type="radio" name="accType" value="AG" checked="checked"/>사무소</label>
                                    <label><input type="radio" name="accType" value="IG"/>교원/학생</label>
                                    <small class="form-text text-danger">※ 교원/학생의 경우에는 교원/학생을 선택 하세요.</small>
                                </div>
                            </div>
                            <div class="row mb-2">
                                <div class="col-md-12 mb-2">
                                    <span><strong>개인정보정책동의</strong></span>
                                </div>
                                <div class="col-md-8 text-left">
                                    <input type="checkbox" name="chkAgree" id="chkAgree" value="Y"><span>개인정보정책을 읽었고 해당 내용에 동의합니다.</span>
                                </div>
                                <div class="col-md-4 text-right">
                                    <button id="showAgree" class="btn btn-outline-secondary btn-xs">개인정보정책 보기</button>
                                </div>
                            </div>
                            <dl class="row text-sm pt-2 pb-2">
                                <dt class="col-sm-5">접근IP안내 : <%=remoteAddr %></dt>
                                <dd class="col-sm-7 text-right">※외부접속시 해당 IP를 먼저 등록하세요.</dd>
                            </dl>--%>
                            <button id="loginBtn" class="btn btn-vidred btn-block btn-lg" type="submit"><i class="fa fa-lock"></i> 로그인</button>
                        </form>
                    </div>
                </div>
                <form id="encLoginForm" name="encLoginForm" method="post" action="<c:url value='/login/proc'/>" style="display: none;">
                    <input type="hidden" name="loginId" value=""/>
                    <input type="hidden" name="loginPwd" value=""/>
                    <input type="hidden" name="<c:out value='${_csrf.parameterName}'/>" value="<c:out value='${_csrf.token}'/>">
                </form>
                <% if (bool){ %>
                <div class="row">
                    <div class="col-md-2">
                    </div>
                    <div class="card col-md-8 card-warning card-outline">
                        <div class="card-body">
                            <table style="position:relative; width:100%; margin:0 auto; font-size: 12px;line-height: 3em; background-color: #fff;color: #333;border-radius:5px;opacity:0.9">
                                <colgroup>
                                    <col style="width:15%;"/>
                                    <col style="width:auto;"/>
                                </colgroup>
                                <tr>
                                    <th>사용자</th>
                                    <td style="padding:5px;">
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="background-color: #fff; height:2px;"></td>
                                </tr>
                                <tr>
                                    <th>담당자</th>
                                    <td style="padding:5px;">
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="background-color: #fff; height:2px;"></td>
                                </tr>
                                <tr>
                                    <th>관리 부서</th>
                                    <td style="padding:5px;">
                                        <a href="#" style="color:#333;" onClick="javascript:autoLog('admin','1')">관리자 (시스템 관리)</a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <div style=" width:700px; margin:0 auto; padding-top: 2%;color:#fff">

                </div>
                <br /><br />
                <% } %>
            </div> <!-- // container block -->
        </div> <!-- // login block -->
    </div>
</div>
<script>
    $(function(){
        "use strict";
        if(typeof(opener) !== "undefined") {
            self.close();
        }
    });

    /**
     * 로그인 처리
     */
    function doSubmit(frm, isCheckEnter) {
        if (isCheckEnter && event.keyCode != 13) {
            return;
        }

        if (frm.loginId.value == "") {
            /* alert("ID를 입력해주세요.");
            frm.loginId.focus(); */
            alert("ID를 입력해주세요.", function(){
                frm.loginId.focus();
            });
            return;
        }

        if (frm.loginPwd.value == "") {
            /* alert("Password를 입력해주세요.");
            frm.loginPwd.focus(); */
            alert("Password를 입력해주세요.", function(){
                frm.loginPwd.focus();
            });
            return;
        }

        //아이디 저장 체크
        if (frm.chkSaveId.checked) {
            paragonCmm.setCookie("loginId", frm.loginId.value, 7);
        } else {
            paragonCmm.setCookie("loginId", "", 7);
        }

        form1.target = "_self";
        form1.submit();
    }

    var SupportLogin = function(){
        var model = (function(){
            var RSA_INIT_URL = "/rsa/init";

            var encRsa = function(param, cbFnc){
                $.ajax({
                    url: paragonCmm.getUrl(RSA_INIT_URL),
                    type: "POST",
                    dataType: "json",
                    error: function(e){
                        console.log(e);
                    },
                    success: function(data){
                        console.log(data);
                        var publicKeyModulus = data.publicKeyModulus;
                        var publicKeyExponent = data.publicKeyExponent;

                        var rsa = new RSAKey();
                        rsa.setPublic(publicKeyModulus, publicKeyExponent);

                        var loginData = {
                            encId: rsa.encrypt(param.loginId),
                            encPw: rsa.encrypt(param.password)
                        };
                        console.log(typeof cbFnc);
                        console.log(loginData);
                        if(typeof cbFnc === "function"){
                            cbFnc(loginData);
                        }
                    }
                });
            };

            return {
                encRsa: encRsa
            };
        })();

        var $inputForm = $("#loginForm");
        var $loginBtn = $("#loginBtn");
        var $encLoginForm = $("#encLoginForm");
        var $inputLoginId = $inputForm.find("input[name=loginId]");

        var checkForm = function(){
            if ($inputLoginId.val() == "") {
                // alert("ID를 입력해주세요.", function(){
                //     $inputLoginId.focus();
                // });
                $inputLoginId.focus();
                return false;
            }

            if ($inputForm.find("input[name=loginPwd]").val() == "") {
                // alert("Password를 입력해주세요.", function(){
                //     $inputForm.find("input[name=loginPwd]").focus();
                // });
                $inputForm.find("input[name=loginPwd]").focus();
                return false;
            }

            if($inputForm.find("input:checkbox[name=chkSaveId]").is(":checked")) {

                console.log("id save");

                paragonCmm.setCookie("loginId", $inputLoginId.val(), 7);
            } else {
                console.log("id not save");
                paragonCmm.setCookie("loginId", "", 7);
            }

            return true;
        };

        var sendForm = function(param){
            console.log($encLoginForm.length);
            console.log(typeof $encLoginForm);
            $encLoginForm.find("input[name=loginId]").val(param.encId);
            $encLoginForm.find("input[name=loginPwd]").val(param.encPw);
            $encLoginForm.submit();
        };

        var doLogin = function(){
            if(checkForm()){
                var param = {
                    loginId: $inputLoginId.val(),
                    password: $inputForm.find("input[name=loginPwd]").val()
                };

                model.encRsa(param, sendForm);
            }
        };

        var initId = function(){
            var login_id    = paragonCmm.getCookie("loginId");

            if (login_id === "") {
                document.form1.loginId.focus();
            } else {
                $inputForm.find("input:checkbox[name=chkSaveId]").attr("checked", true);
                $inputLoginId.val(login_id);
                $inputLoginId.focus();
            }
        };

        var setEvent = function(){
            $inputForm.on("submit", function(){
                doLogin();
                return false;
            });
            $loginBtn.on("click", function(){
                doLogin();
                return false;
            });
        };
        var init = function(){
            initId();
            setEvent();
        };

        return {
            init: init
        };
    };

    $(document).ready(function(){
        var supportLogin = new SupportLogin();
        supportLogin.init();

        var errMsg = "<%=errMsg%>";
        if(errMsg != ""){
            old_alert(errMsg);
            location.href="/login.do";
        }
    });

    $("#showAgree").on('click',function(){
        window.open('/main/personalAgree', '개인정보 제공동의', 'width=900px,height=950px,scrollbars=yes');
    });
</script>
<% if (bool){ %>
<script src="<c:url value='/js/vendor/paragon/4.5.0/extention/paragon.base.js'/>"></script>
<script type="text/javascript">
    function autoLog(no_str, pwd){
        var frm = document.form1 ;

        frm.loginId.value   = no_str;
        if(pwd  && pwd != ''){
            frm.loginPwd.value  = pwd;
        }else{
            frm.loginPwd.value  = "pnu_run";
        }

        //아이디 저장 체크
//         if (frm.chkSaveId.checked) {
//             paragonCmm.setCookie("loginId", frm.loginId.value, 7);
//         } else {
//             paragonCmm.setCookie("loginId", "", 7);
//         }

//         frm.submit();

        $("#loginBtn").trigger("click");

    }

    function encStr(sParam) {
        sParam = sParam.replace(/(^\s*)|(\s*$)/gi, ""); //trim

        var encode = '';

        // for(var iIndex=0; iIndex<sParam.length; iIndex++) {
        //     var sLength  = ''+sParam.charCodeAt(iIndex);
        //     var sToken = '' + sLength.length;
        //     encode  += sToken + sParam.charCodeAt(iIndex);
        // }
        // codePointAt

        for(var iIndex=0; iIndex<sParam.length; iIndex++) {
            var sLength  = ''+sParam.codePointAt(iIndex);
            var sToken = '' + sLength.length;
            encode  += sToken + sParam.codePointAt(iIndex);
        }

        return encode;
    }

    /**
     *
     * @param compky
     * @param id
     * @param name
     * @param cert
     */
    var openSvcDesk = function(compky, id, name, cert) {
        var vParams = "";

        // var SERVICE_DESK_URL = "http://support.vertexid.com:58082/rest/sso?";
        var SERVICE_DESK_URL = "http://localhost:58080/rest/sso?";

        // var loginNm = loginInfo.nmKo;
//            var email = loginInfo.email;

        vParams += "compky=" + compky;         // 지정한 고객사 고정코드
        vParams += "&id=" + id;                // 지정한 고객사 고정계정
        vParams += "&name=" + encStr(name);    // 이름
        if(paragonCmm.isNotEmpty(cert)){
            vParams += "&cert=" + encStr(cert);    // CERT
        }
        paragonCmm.openWindow(SERVICE_DESK_URL + vParams, "1250", "800", "Service Desk", "yes", "yes", "");
    }
</script>
<%    } %>
<% } else { %>
<form name="form1" method="post" action="<c:url value='/main/main'/>" onsubmit="return false;" >
    <input type="hidden" name="<c:out value='${_csrf.parameterName}'/>" value="<c:out value='${_csrf.token}'/>">
</form>
<script type="text/javascript">
    var frm = document.form1 ;
    frm.submit();
</script>
<%} %>