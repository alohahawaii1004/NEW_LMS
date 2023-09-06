<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2023-01-09(009)
  -
  - Copyright 2023 JAYU.space
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
  -     작성 팝업 샘플
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div class="container-fluid">
    <h5><i class="fa fa-file-text"></i> 소송 정보</h5>
    <div class="p-2">
        <form class="form-horizontal">
            <div class="row">
                <div class="col-md-12 border">
                    <div class="row">
                        <label for="first_name"
                               class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">상대방</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control form-control-sm is-valid" id="first_name"
                                   name="first_name" placeholder="상대방">
                            <small id="passwordHelpBlock" class="form-text text-muted">
                                <i class="fa fa-info-circle text-warning"></i> form help text sample
                            </small>
                            <div class="valid-feedback">
                                폼 성공 메시지 샘플입니다.
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 border">
                    <div class="row">
                        <label for="first_name"
                               class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">사건명
                            <i class="fa fa-check text-danger"></i></label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control form-control-sm is-invalid" id="first_name"
                                   name="first_name" placeholder="사건명" required>
                            <small id="passwordHelpBlock" class="form-text text-muted">
                                폼 지원 메시지 샘플입니다.
                            </small>
                            <div class="invalid-feedback">
                                폼 오류 메시지 샘플입니다.
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 border">
                    <div class="row">
                        <label for="first_name"
                               class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">유형
                            <i class="fa fa-check text-danger"></i></label>
                        <div class="col-sm-10">
                            <div class='form-inline'>
                                <select type="text" class="form-control form-control-sm" id="firstCategory"
                                        name="first_name" placeholder="사건명" required>
                                </select>
                                <select type="text" class="form-control form-control-sm" id="secondCategory"
                                        name="first_name" placeholder="사건명" required>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 border">
                    <div class="row">
                        <label for="first_name"
                               class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">주요소송여부 </label>
                        <div class="col-sm-10">
                            <div class="form-check">
                                <div class="row">
                                    <div class="col-sm-1">
                                        <input class="form-check-input form-control-sm" type="radio" name="majorYn"
                                               id="gridCheck1" style="margin-top:0;">
                                        <label class="form-check-label col-form-label-sm" for="gridCheck1">
                                            Y
                                        </label>
                                    </div>
                                    <div class="col-sm-1">
                                        <input class="form-check-input form-control-sm" type="radio" name="majorYn"
                                               id="gridCheck2" style="margin-top:0;">
                                        <label class="form-check-label col-form-label-sm" for="gridCheck2">
                                            N
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 border">
                    <div class="row">
                        <label for="first_name"
                               class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">보험금
                            소송공시</label>
                        <div class="col-sm-4">
                            <select type="text" class="form-control form-control-sm" id="firstCategory"
                                    name="first_name" placeholder="보험금 소송공시">
                            </select>
                        </div>
                        <label for="first_name"
                               class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">금융소비자여부</label>
                        <div class="col-sm-4">
                            <select type="text" class="form-control form-control-sm" id="firstCategory"
                                    name="first_name" placeholder="금융소비자여부">
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 border">
                    <div class="row">
                        <label for="first_name"
                               class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">반소여부</label>
                        <div class="col-sm-4">
                            <select type="text" class="form-control form-control-sm" id="firstCategory"
                                    name="first_name" placeholder="반소여부">
                            </select>
                        </div>
                        <label for="first_name"
                               class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">당사지위</label>
                        <div class="col-sm-4">
                            <select type="text" class="form-control form-control-sm" id="firstCategory"
                                    name="first_name" placeholder="당사지위">
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 border">
                    <div class="row">
                        <label for="first_name"
                               class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">참조부서(열람기능)</label>
                        <div class="col-sm-4">
                            <select type="text" class="form-control form-control-sm" id="firstCategory"
                                    name="first_name" placeholder="참조부서(열람기능)" size="5" multiple>
                            </select>
                            <div class="left p-1">
                                <button type="button"
                                        id="lms_pati_PTC-LMS-CM-003_schBtn1_<c:out value='${param.docUid}'/>"
                                        class="btn btn-primary btn-xs" data-term="B.SEARCH"><i class="fa fa-search"></i>
                                </button>
                                <button type="button"
                                        id="lms_pati_PTC-LMS-CM-003_resetBtn1_<c:out value='${param.docUid}'/>"
                                        class="btn btn-outline-secondary btn-xs" data-term="B.INIT"><i
                                        class="fa fa-refresh"></i></button>
                                <button type="button"
                                        id="lms_pati_PTC-LMS-CM-003_delBtn1_<c:out value='${param.docUid}'/>"
                                        class="btn btn-outline-danger btn-xs" data-term="B.DELETE"><i
                                        class="fa fa-minus"></i></button>
                            </div>
                        </div>
                        <label for="first_name"
                               class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">참조자(열람기능)</label>
                        <div class="col-sm-4">
                            <select type="text" class="form-control form-control-sm" id="firstCategory"
                                    name="first_name" placeholder="당사지위" size="5" multiple>
                            </select>
                            <div class="left p-1">
                                <button type="button"
                                        id="lms_pati_PTC-LMS-CM-003_schBtn1_<c:out value='${param.docUid}'/>"
                                        class="btn btn-primary btn-xs" data-term="B.SEARCH"><i class="fa fa-search"></i>
                                </button>
                                <button type="button"
                                        id="lms_pati_PTC-LMS-CM-003_resetBtn1_<c:out value='${param.docUid}'/>"
                                        class="btn btn-outline-secondary btn-xs" data-term="B.INIT"><i
                                        class="fa fa-refresh"></i></button>
                                <button type="button"
                                        id="lms_pati_PTC-LMS-CM-003_delBtn1_<c:out value='${param.docUid}'/>"
                                        class="btn btn-outline-danger btn-xs" data-term="B.DELETE"><i
                                        class="fa fa-minus"></i></button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-12 border">
                    <div class="row">
                        <label for="first_name"
                               class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">사건개요</label>
                        <div class="col-sm-10">
                            <textarea name="textEditor1" id="textEditor1" data-col="textEditor1"
                                      style='display:none;'></textarea>
                        </div>
                    </div>
                </div>

                <div class="col-md-12 border">
                    <div class="row">
                        <label for="first_name"
                               class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">첨부파일</label>
                        <div class="col-sm-10">
                            <div id="fileCom1"></div>
                        </div>
                    </div>
                </div>

            </div>
        </form>
    </div>
    <div class="row p-2">
        <div class="col-md-6">

        </div>
        <div class="col-md-6 text-right">
            <button class="btn btn-outline-secondary btn-sm" type="button"><i class="fa fa-pencil-square"></i> 임시저징
            </button>
            <button class="btn btn-primary btn-sm" type="button"><i class="fa fa-pencil"></i> 소송 접수</button>
        </div>
    </div>
</div>
<script>
    var Sample = function () {
        "use strict";

        var initEditor = function () {
            // CKEDITOR.instances["textEditor1"].setData("");
            // paragonCmm.setEditorValue("textEditor1", "");

            htmlUtils.getHtmlEditor({
                sCtrlName: "textEditor1",
                sCtrlId: "textEditor1"
            });
        };

        var initFile = function () {
            htmlUtils.loadFileHtml("fileCom1", {
                relUid: "1234",
                fileTpCd: "test",
                defaultRelUid: ""
            });	//-- 첨부파일 로드
        };


        var init = function () {
            initEditor();
            initFile();
        };

        return {
            init: init
        };
    };

    $(document).ready(function () {
        "use strict";
        var sample = new Sample();
        sample.init();
    });
</script>