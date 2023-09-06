<%--
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
  - Description: 에러페이지
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <!-- meta info -->
    <%@include file="/WEB-INF/tiles/include/metaInfo.jsp"%>

    <!-- style links -->
    <%@include file="/WEB-INF/tiles/include/cssLink.jsp"%>
    <!-- error css -->
    <link rel="stylesheet" href="<c:url value='/css/error.css'/>">
</head>
<body>
<%--<section>
    <div class="container-fluid">
        <div class="row">
            <div class="col d-table">
                <div class="text-center d-table-cell align-middle">
                    <h2 class="font-weight-bold mb-4">Hmm...</h2>
                    <p>looks like something went wrong. </p>
                    <p>Don't worry, please go to our homepage. </p>
                    <a class="btn btn-warning mt-4" href="<c:url value='/'/>">Homepage</a>
                </div>
            </div>
        </div>
    </div>
</section>--%>
<div class="text-center">

    <h1>:( <span class="errorcode"> Oops!!</span></h1>

    <p class="output"><small><em>All those moments will be lost in time, like tears in rain.</em></small></p>
    <br>
    <p class="output">Hmm...</p>
    <p class="output">If you're approaching it normally, something might seem wrong.</p>
    <p class="output">Don't worry, if you have approached normally, please go to <a href="/">our homepage.</a></p>
    <p class="output">Good luck.</p>
    <p><small></small><small>_____________________________________________________________________________________</small></p>
    <c:if test="${opertionType ne 'PROD'}">
        <br>
        <p class="output"><small>
            <c:out value="${requestScope['javax.servlet.error.message']}"/>
        </small></p>
    </c:if>
</div>
</body>
</html>
