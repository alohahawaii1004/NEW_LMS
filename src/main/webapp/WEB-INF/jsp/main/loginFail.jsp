<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2023-06-19(019)
  -
  - Copyright 2023 JaYu.space
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
  -
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div class="container-fluid text-center">
    <h1 class="">Login failed</h1>
    <h3 class="display-3">Check your status.</h3>
</div>
<script>
    $(function(){
        "use strict";

        if(paragonCmm.isNotEmpty(opener)){
            self.close();
        }else{
            $.messager.alert("Warning","로그인에 실패했습니다.","Warning");
        }
    });
</script>