<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2023-01-10(010)
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
  -    뷰 팝업 샘플
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<div class="container-fluid">
    <h5><i class="fa fa-file-text"></i> 소송 정보</h5>
    <div class="p-2">
        <div class="form-horizontal">
            <div class="row">
                <div class="col-md-12 border">
                    <div class="row">
                        <label class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">상대방</label>
                        <div class="col-sm-10">
                            <span data-column="" class="align-middle">구글</span>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 border">
                    <div class="row">
                        <label class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">사건명
                        </label>
                        <div class="col-sm-10">
                            <span data-column="" class="align-middle">Java 특허권 침해 소송</span>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 border">
                    <div class="row">
                        <label class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">유형
                            <i class="fa fa-check text-danger"></i></label>
                        <div class="col-sm-10">
                            <span data-column="" class="align-middle">형사서건(수사)/보험료반환</span>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 border">
                    <div class="row">
                        <label class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">주요소송여부 </label>
                        <div class="col-sm-10">
                            <span data-column="" class="align-middle">N</span>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 border">
                    <div class="row">
                        <label class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">보험금
                            소송공시</label>
                        <div class="col-sm-4">
                            <span data-column="" class="align-middle">보험사기 유죄확정판결에 따른 채무부 존재 확인</span>
                        </div>
                        <label class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">금융소비자여부</label>
                        <div class="col-sm-4">
                            <span data-column="" class="align-middle">부동산</span>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 border">
                    <div class="row">
                        <label class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">반소여부</label>
                        <div class="col-sm-4">
                            <span data-column="" class="align-middle">N</span>
                        </div>
                        <label class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">당사지위</label>
                        <div class="col-sm-4">
                            <span data-column="" class="align-middle">피고</span>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 border">
                    <div class="row">
                        <label class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">참조부서(열람기능)</label>
                        <div class="col-sm-4">
                            <div class="row">
                                <span data-column="" class="col-sm-4 align-middle">총무팀</span>
                                <span data-column="" class="col-sm-4 align-middle">인사팀</span>
                                <span data-column="" class="col-sm-4 align-middle">IT지원팀</span>
                                <span data-column="" class="col-sm-4 align-middle">법무팀</span>
                            </div>
                        </div>
                        <label class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">참조자(열람기능)</label>
                        <div class="col-sm-4">
                            <div class="row">
                                <span data-column="" class="col-sm-4 align-middle">김경관</span>
                                <span data-column="" class="col-sm-4 align-middle">최인사</span>
                                <span data-column="" class="col-sm-4 align-middle">스티브 박</span>
                                <span data-column="" class="col-sm-4 align-middle">송영길</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-12 border">
                    <div class="row">
                        <label class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">사건개요</label>
                        <div class="col-sm-10">
                            <span data-column="" class="align-middle">본 소송은 ....<br>
                            그리하여...<br>
                            그렇게 진행되었음.</span>
                        </div>
                    </div>
                </div>

                <div class="col-md-12 border">
                    <div class="row">
                        <label class="col-sm-2 col-form-label col-form-label-sm bg-gray-light border-left border-right">첨부파일</label>
                        <div class="col-sm-10" id="fileCom1">
                            <div class="row">
                                <span data-column="" class="col-sm-11 align-middle">
                                    <div class="row">
                                        <span class="col-sm-12 align-middle"><a href=""><i class="fa fa-save"></i> 계약서.doc</a></span>
                                        <span class="col-sm-12 align-middle"><a href=""><i class="fa fa-save"></i> 참고자료1.doc</a></span>
                                        <span class="col-sm-12 align-middle"><a href=""><i class="fa fa-save"></i> 참고자료2.doc</a></span>
                                    </div>
                                </span>
                                <span data-column="" class="col-sm-1 align-middle text-right">
                                    <button type="button" class="btn btn-outline-primary btn-xs"><i class="fa fa-download"></i></button>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <div class="row p-2">
        <div class="col-md-6">

        </div>
        <div class="col-md-6 text-right">
            <button class="btn btn-outline-secondary btn-sm" type="button"><i class="fa fa-pencil-square"></i> 수정
            </button>
            <button class="btn btn-primary btn-sm" type="button"><i class="fa fa-close"></i> 닫기</button>
        </div>
    </div>
</div>
<script>
    var Sample = function () {
        "use strict";

        var initEditor = function () {
            // CKEDITOR.instances["textEditor1"].setData("");
            // paragonCmm.setEditorValue("textEditor1", "");

            // htmlUtils.getHtmlEditor({
            //     sCtrlName: "textEditor1",
            //     sCtrlId: "textEditor1"
            // });
        };

        var initFile = function () {
            htmlUtils.loadFileView("fileCom1", {
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