<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2020-10-20(020)
  -
  - Copyright 2020 JAYU.space
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
<%@include file="/WEB-INF/tiles/include/includeTagLibs.jsp"%>
<!--
# Design Info
## Error Page – 80s hacker theme

## 1. Author

Robin Selmer

## 2. License

Copyright (c) 2020 by Robin Selmer (https://codepen.io/robinselmer/pen/vJjbOZ)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
-->
<style type="text/css">
    html {
        min-height: 100%;
    }

    body {
        box-sizing: border-box;
        height: 100%;
        background-color: #000000;
        background-repeat: no-repeat;
        background-size: cover;
        font-family: 'Inconsolata', Helvetica, sans-serif;
        font-size: 1.5rem;
        color: rgba(128, 255, 128, 0.8);
        text-shadow:
                0 0 1ex rgba(51, 255, 51, 1),
                0 0 2px rgba(255, 255, 255, 0.8);
    }

    .noise {
        pointer-events: none;
        position: absolute;
        width: 100%;
        height: 100%;
        background-repeat: no-repeat;
        background-size: cover;
        z-index: -1;
        opacity: .02;
    }

    .overlay {
        pointer-events: none;
        position: absolute;
        width: 100%;
        height: 100%;
        background:
                repeating-linear-gradient(
                        180deg,
                        rgba(0, 0, 0, 0) 0,
                        rgba(0, 0, 0, 0.3) 50%,
                        rgba(0, 0, 0, 0) 100%);
        background-size: auto 4px;
        z-index: 1;
    }

    .overlay::before {
        content: "";
        pointer-events: none;
        position: absolute;
        display: block;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        width: 100%;
        height: 100%;
        background-image: linear-gradient(
                0deg,
                transparent 0%,
                rgba(32, 128, 32, 0.2) 2%,
                rgba(32, 128, 32, 0.8) 3%,
                rgba(32, 128, 32, 0.2) 3%,
                transparent 100%);
        background-repeat: no-repeat;
        animation: scan 7.5s linear 0s infinite;
    }

    @keyframes scan {
        0%        { background-position: 0 -100vh; }
        35%, 100% { background-position: 0 100vh; }
    }

    .terminal {
        box-sizing: inherit;
        position: absolute;
        height: 100%;
        width: 900px;
        max-width: 100%;
        padding: 4rem;
        text-transform: uppercase;
    }

    .output {
        color: rgba(128, 255, 128, 0.8);
        text-shadow:
                0 0 1px rgba(51, 255, 51, 0.4),
                0 0 2px rgba(255, 255, 255, 0.8);
    }

    .output::before {
        content: "> ";
    }

    /*
    .input {
      color: rgba(192, 255, 192, 0.8);
      text-shadow:
          0 0 1px rgba(51, 255, 51, 0.4),
          0 0 2px rgba(255, 255, 255, 0.8);
    }

    .input::before {
      content: "$ ";
    }
    */

    a {
        color: #fff;
        text-decoration: none;
    }

    a::before {
        content: "[";
    }

    a::after {
        content: "]";
    }

    .errorcode {
        color: white;
    }
</style>
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
<%--<div class="text-center">

    &lt;%&ndash;<h1>Hmm...</h1>
    <p>looks like something went wrong. </p>
    <p>Don't worry, <br>please go to our homepage. </p>
    <p>
        <a class="btn btn-info mt-4" href="<c:url value='/'/>">Homepage</a>
    </p>&ndash;%&gt;
</div>--%>
<div class="noise"></div>
<div class="overlay"></div>
<div class="terminal">
    <!--<h1>Error <span class="errorcode">404</span></h1>
    <p class="output">The page you are looking for might have been removed, had its name changed or is temporarily unavailable.</p>
    <p class="output">Please try to <a href="#1">go back</a> or <a href="#2">return to the homepage</a>.</p>
    <p class="output">Good luck.</p>-->
    <!--Looks like something went wrong. <br />
                Don't worry, please go to our homepage. -->

    <h1>:( <span class="errorcode"> Oops!! 어이쿠</span></h1>

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