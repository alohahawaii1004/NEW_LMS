/*
 * @(#)navbar.js     20. 6. 26. 오전 10:25
 *
 * Copyright 2020 JAYU.space
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

(function(){
    "use strict";

    console.info('[Loading: navbar]................................');

    var MODULE_NAME = "navbar";

    // TOP
    // login info display
    var setLoginInfo = function(){
        $("#loginNm").text("Test User");
    };

    // left toggle event



    var init = function(){
        setLoginInfo();
        //toggleLeftMenu();
    };

    init();
})();
