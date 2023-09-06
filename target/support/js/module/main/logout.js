/*
 * @(#)logout.js     2023-04-06 오전 9:30
 *
 * Copyright 2023 JAYU.space
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
$(function(){
    "use strict";

    if(paragonCmm.isNotEmpty(opener)){
        self.close();
    }else{
        $.messager.alert("Info","정상적으로 로그아웃 하셨습니다. 창을 닫아 주세요.","info");
        $("button").show();
        $("button").on("click", function(){
            location.href = "/";
        });
    }
});