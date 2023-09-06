<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2022-12-30(030)
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
  - Description:
  -     검색폼 샘플
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>

<h1>Form Sample 1</h1>
<p id="emailHelp" class="form-text text-muted">
    검색폼
</p>
<div class="clearfix border rounded bg-gray-light p-2">
    <div class="row">
        <div class="col-md-11">
            <form class="form-horizontal">
                <div class="row">
                    <div class="form-group col-md-3">
                        <div class="row">
                            <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control form-control-sm" id="first_name"
                                       name="first_name" placeholder="창고코드">
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-md-3">
                        <div class="row">
                            <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control form-control-sm" id="first_name"
                                       name="first_name" placeholder="창고코드">
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-md-3">
                        <div class="row">
                            <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control form-control-sm" id="first_name"
                                       name="first_name" placeholder="창고코드">
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-md-3">
                        <div class="row">
                            <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control form-control-sm" id="first_name"
                                       name="first_name" placeholder="창고코드">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-3">
                        <div class="row">
                            <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control form-control-sm" id="first_name"
                                       name="first_name" placeholder="창고코드">
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-md-3">
                        <div class="row">
                            <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control form-control-sm" id="first_name"
                                       name="first_name" placeholder="창고코드">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-3">
                        <div class="row">
                            <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control form-control-sm" id="first_name"
                                       name="first_name" placeholder="창고코드">
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-md-3">
                        <div class="row">
                            <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control form-control-sm" id="first_name"
                                       name="first_name" placeholder="창고코드">
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-md-3">
                        <div class="row">
                            <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control form-control-sm" id="first_name"
                                       name="first_name" placeholder="창고코드">
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-md-1 align-self-center">
            <button class="btn btn-default btn-sm"><i class="fa fa-undo"></i> RESET</button>
            <button class="btn btn-primary btn-sm"><i class="fa fa-search"></i> SEARCH</button>
        </div>
    </div>
</div>
<h1>Form Sample 1-1</h1>
<p id="emailHelp" class="form-text text-muted">
    검색폼 반응형 고려
    소형화면에서 label 사라지고 버튼도 변경됨
</p>
<div class="clearfix border rounded bg-gray-light p-2">
    <div class="row">
        <div class="col-md-11">
            <form class="form-horizontal">
                <div class="row" id="">
                    <div class="form-group col-md-4">
                        <div class="row">
                            <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block">창고코드</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control form-control-sm" id="first_name"
                                       name="first_name" placeholder="창고코드">
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <div class="row">
                            <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block">창고코드</label>
                            <div class="col-sm-9">
                                <div class='form-inline'>
                                    <input type="text" class="form-control form-control-sm" id="first_name"
                                           name="first_name" placeholder="창고코드" style='width:46%;'>
                                    <span class='p-1'> ~ </span>
                                    <input type="text" class="form-control form-control-sm" id="first_name"
                                           name="first_name" placeholder="창고코드" style='width:46%;'>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <div class="row">
                            <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block">창고코드</label>
                            <div class="col-sm-9">
                                <select name='first_name' class='form-control form-control-sm'>
                                    <option>창고코드</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <div class="row">
                            <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block">창고코드</label>
                            <div class="col-sm-9">
                                <div class='form-inline'>
                                    <select name='first_name' class='form-control form-control-sm'>
                                        <option>창고코드</option>
                                    </select>&nbsp;&nbsp;
                                    <select name='first_name' class='form-control form-control-sm'>
                                        <option>창고코드</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <div class="row">
                            <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block">창고코드</label>
                            <div class="col-sm-9">
                                <div class="form-check">
                                    <div class="row">
                                        <div class="col-sm-6">
                                            <input class="form-check-input form-control-sm" type="checkbox" id="gridCheck1" style="margin-top:0;">
                                            <label class="form-check-label col-form-label-sm" for="gridCheck1">
                                                Example checkbox
                                            </label>
                                        </div>
                                        <div class="col-sm-6">
                                            <input class="form-check-input form-control-sm" type="checkbox" id="gridCheck2" style="margin-top:0;">
                                            <label class="form-check-label col-form-label-sm" for="gridCheck2">
                                                Example checkbox
                                            </label>
                                        </div>
                                        <div class="col-sm-6">
                                            <input class="form-check-input form-control-sm" type="checkbox" id="gridCheck3" style="margin-top:0;">
                                            <label class="form-check-label col-form-label-sm" for="gridCheck3">
                                                Example checkbox
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <div class="row">
                            <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block">창고코드</label>
                            <div class="col-sm-9">
                                <div class='form-inline'>
                                    <input type="text" class="form-control form-control-sm" id="first_name"
                                           name="first_name" placeholder="YYYY-MM-DD" style='width:46%;'>
                                    <span class='p-1'> ~ </span>
                                    <input type="text" class="form-control form-control-sm" id="first_name"
                                           name="first_name" placeholder="YYYY-MM-DD" style='width:46%;'>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <div class="row">
                            <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block">창고코드</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control form-control-sm" id="first_name"
                                       name="first_name" placeholder="창고코드">
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <div class="row">
                            <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block">창고코드</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control form-control-sm" id="first_name"
                                       name="first_name" placeholder="창고코드">
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <div class="row">
                            <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block">창고코드</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control form-control-sm" id="first_name"
                                       name="first_name" placeholder="창고코드">
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-md-1 d-none d-sm-block align-self-center">
            <button class="btn btn-default btn-sm"><i class="fa fa-undo"></i> RESET</button>
            <button class="btn btn-primary btn-sm"><i class="fa fa-search"></i> SEARCH</button>
        </div>
        <div class="col-sm-3 d-block d-sm-none">
            <button class="btn btn-primary btn-sm"><i class="fa fa-search"></i> SEARCH</button>
            <button class="btn btn-default btn-sm"><i class="fa fa-undo"></i> RESET</button>
        </div>
    </div>
</div>
<h1>Form Sample 2</h1>
<p id="emailHelp" class="form-text text-muted">
    버튼 하단 좌측
</p>
<div class="clearfix border rounded bg-gray-light p-2">
    <form class="form-horizontal">
        <div class="row">
            <div class="form-group col-md-3">
                <div class="row">
                    <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                               placeholder="창고코드">
                    </div>
                </div>
            </div>
            <div class="form-group col-md-3">
                <div class="row">
                    <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                               placeholder="창고코드">
                    </div>
                </div>
            </div>
            <div class="form-group col-md-3">
                <div class="row">
                    <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                               placeholder="창고코드">
                    </div>
                </div>
            </div>
            <div class="form-group col-md-3">
                <div class="row">
                    <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                               placeholder="창고코드">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-3">
                <div class="row">
                    <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                               placeholder="창고코드">
                    </div>
                </div>
            </div>
            <div class="form-group col-md-3">
                <div class="row">
                    <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                               placeholder="창고코드">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-3">
                <div class="row">
                    <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                               placeholder="창고코드">
                    </div>
                </div>
            </div>
            <div class="form-group col-md-3">
                <div class="row">
                    <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                               placeholder="창고코드">
                    </div>
                </div>
            </div>
            <div class="form-group col-md-3">
                <div class="row">
                    <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                               placeholder="창고코드">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-3">
                <button class="btn btn-default btn-sm"><i class="fa fa-undo"></i> RESET</button>
                <button class="btn btn-primary btn-sm"><i class="fa fa-search"></i> SEARCH</button>
            </div>
        </div>
    </form>
</div>
<h1>Form Sample 3</h1>
<p id="emailHelp" class="form-text text-muted">
    버튼 하단 우측
</p>
<div class="clearfix border rounded bg-gray-light p-2">
    <form class="form-horizontal">
        <div class="row">
            <div class="form-group col-md-3">
                <div class="row">
                    <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                               placeholder="창고코드">
                    </div>
                </div>
            </div>
            <div class="form-group col-md-3">
                <div class="row">
                    <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                               placeholder="창고코드">
                    </div>
                </div>
            </div>
            <div class="form-group col-md-3">
                <div class="row">
                    <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                               placeholder="창고코드">
                    </div>
                </div>
            </div>
            <div class="form-group col-md-3">
                <div class="row">
                    <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                               placeholder="창고코드">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-3">
                <div class="row">
                    <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                               placeholder="창고코드">
                    </div>
                </div>
            </div>
            <div class="form-group col-md-3">
                <div class="row">
                    <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                               placeholder="창고코드">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-3">
                <div class="row">
                    <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                               placeholder="창고코드">
                    </div>
                </div>
            </div>
            <div class="form-group col-md-3">
                <div class="row">
                    <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                               placeholder="창고코드">
                    </div>
                </div>
            </div>
            <div class="form-group col-md-3">
                <div class="row">
                    <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm">창고코드</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                               placeholder="창고코드">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-6">
            </div>
            <div class="form-group col-md-6 text-right">
                <button class="btn btn-default btn-sm"><i class="fa fa-undo"></i> RESET</button>
                <button class="btn btn-primary btn-sm"><i class="fa fa-search"></i> SEARCH</button>
            </div>
        </div>
    </form>
</div>
<h1>Form Sample 4</h1>
<p id="emailHelp" class="form-text text-muted">
    Bootstrap 4 grid
</p>
<div class="clearfix border rounded bg-gray-light p-2">
    <form>
        <div class="row">
            <div class="form-group col-md-3">
                <label for="first_name" class="col-form-label col-form-label-sm">창고코드</label>
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
            <div class="form-group col-md-3">
                <label for="first_name" class="col-form-label col-form-label-sm">창고코드</label>
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
            <div class="form-group col-md-3">
                <label for="first_name" class="col-form-label col-form-label-sm">창고코드</label>
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
            <div class="form-group col-md-3">
                <label for="first_name" class="col-form-label col-form-label-sm">창고코드</label>
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-3">
                <label for="first_name" class="col-form-label col-form-label-sm">창고코드</label>
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
            <div class="form-group col-md-3">
                <label for="first_name" class="col-form-label col-form-label-sm">창고코드</label>
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-3">
                <label for="first_name" class="col-form-label col-form-label-sm">창고코드</label>
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
            <div class="form-group col-md-3">
                <label for="first_name" class="col-form-label col-form-label-sm">창고코드</label>
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
            <div class="form-group col-md-3">
                <label for="first_name" class="col-form-label col-form-label-sm">창고코드</label>
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-6">
            </div>
            <div class="form-group col-md-6 text-right">
                <button class="btn btn-default btn-sm"><i class="fa fa-undo"></i> RESET</button>
                <button class="btn btn-primary btn-sm"><i class="fa fa-search"></i> SEARCH</button>
            </div>
        </div>
    </form>
</div>
<h1>Form Sample 5</h1>
<p id="emailHelp" class="form-text text-muted">
    Bootstrap 4 form-row
</p>
<div class="clearfix border rounded bg-gray-light p-2">
    <form>
        <div class="form-row">
            <div class="form-group col-md-3">
                <label for="first_name" class="col-form-label col-form-label-sm">창고코드</label>
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
            <div class="form-group col-md-3">
                <label for="first_name" class="col-form-label col-form-label-sm">창고코드</label>
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
            <div class="form-group col-md-3">
                <label for="first_name" class="col-form-label col-form-label-sm">창고코드</label>
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
            <div class="form-group col-md-3">
                <label for="first_name" class="col-form-label col-form-label-sm">창고코드</label>
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-3">
                <label for="first_name" class="col-form-label col-form-label-sm">창고코드</label>
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
            <div class="form-group col-md-3">
                <label for="first_name" class="col-form-label col-form-label-sm">창고코드</label>
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-3">
                <label for="first_name" class="col-form-label col-form-label-sm">창고코드</label>
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
            <div class="form-group col-md-3">
                <label for="first_name" class="col-form-label col-form-label-sm">창고코드</label>
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
            <div class="form-group col-md-3">
                <label for="first_name" class="col-form-label col-form-label-sm">창고코드</label>
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
        </div>
        <div class="form-row">
            <div class="col-md-6">
            </div>
            <div class="col-md-6 text-right">
                <button class="btn btn-default btn-sm"><i class="fa fa-undo"></i> RESET</button>
                <button class="btn btn-primary btn-sm"><i class="fa fa-search"></i> SEARCH</button>
            </div>
        </div>
    </form>
</div>
<h1>Form Sample 6</h1>
<p id="emailHelp" class="form-text text-muted">
    Bootstrap 4 input group
</p>
<div class="clearfix border rounded bg-gray-light p-2">
    <form>
        <div class="form-row">
            <div class="form-group col-md-3">
                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">창고코드</span>
                    </div>
                    <input type="text" class="form-control">
                </div>
            </div>
            <div class="form-group col-md-3">
                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">창고코드코드코드</span>
                    </div>
                    <input type="text" class="form-control">
                </div>
            </div>
            <div class="form-group col-md-3">
                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">창고코드</span>
                    </div>
                    <input type="text" class="form-control">
                </div>
            </div>
            <div class="form-group col-md-3">
                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">창고코드</span>
                    </div>
                    <input type="text" class="form-control">
                </div>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-3">
                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">창고코드</span>
                    </div>
                    <input type="text" class="form-control">
                </div>
            </div>
            <div class="form-group col-md-3">
                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">창고코드</span>
                    </div>
                    <input type="text" class="form-control">
                </div>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-3">
                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">창고코드</span>
                    </div>
                    <input type="text" class="form-control">
                </div>
            </div>
            <div class="form-group col-md-3">
                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">창고코드</span>
                    </div>
                    <input type="text" class="form-control">
                </div>
            </div>
            <div class="form-group col-md-3">
                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">창고코드</span>
                    </div>
                    <input type="text" class="form-control">
                </div>
            </div>
        </div>
        <div class="form-row">
            <div class="col-md-6">
            </div>
            <div class="col-md-6 text-right">
                <button class="btn btn-default btn-sm"><i class="fa fa-undo"></i> RESET</button>
                <button class="btn btn-primary btn-sm"><i class="fa fa-search"></i> SEARCH</button>
            </div>
        </div>
    </form>
</div>
<h1>Form Sample 7</h1>
<p id="emailHelp" class="form-text text-muted">
    Bootstrap 4 placeholder only
</p>
<div class="clearfix border rounded bg-gray-light p-2">
    <form>
        <div class="form-row">
            <div class="form-group col-md-3">
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
            <div class="form-group col-md-3">
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
            <div class="form-group col-md-3">
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
            <div class="form-group col-md-3">
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-3">
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
            <div class="form-group col-md-3">
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-3">
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
            <div class="form-group col-md-3">
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
            <div class="form-group col-md-3">
                <input type="text" class="form-control form-control-sm" id="first_name" name="first_name"
                       placeholder="창고코드">
            </div>
        </div>
        <div class="form-row">
            <div class="col-md-6">
            </div>
            <div class="col-md-6 text-right">
                <button class="btn btn-default btn-sm"><i class="fa fa-undo"></i> RESET</button>
                <button class="btn btn-primary btn-sm"><i class="fa fa-search"></i> SEARCH</button>
            </div>
        </div>
    </form>
</div>



<div class="container-fluid">
    <div class="clearfix p-2">
        <form class="form-horizontal">
            <div class="row">
                <div class="form-group col-md-4 border-bottom">
                    <div class="row">
                        <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block border-left bg-gray-light">창고코드</label>
                        <div class="col-sm-9 border-left">
                            <input type="text" class="form-control form-control-sm" id="first_name"
                                   name="first_name" placeholder="창고코드">
                        </div>
                    </div>
                </div>
                <div class="form-group col-md-4 border-bottom">
                    <div class="row">
                        <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block border-left">창고코드</label>
                        <div class="col-sm-9 border-left">
                            <input type="text" class="form-control form-control-sm" id="first_name"
                                   name="first_name" placeholder="창고코드">
                        </div>
                    </div>
                </div>
                <div class="form-group col-md-4 border-bottom">
                    <div class="row">
                        <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block border-left">창고코드</label>
                        <div class="col-sm-9 border-left">
                            <input type="text" class="form-control form-control-sm" id="first_name"
                                   name="first_name" placeholder="창고코드">
                        </div>
                    </div>
                </div>
                <div class="form-group col-md-4 border-bottom">
                    <div class="row">
                        <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block border-left">창고코드</label>
                        <div class="col-sm-9 border-left">
                            <input type="text" class="form-control form-control-sm" id="first_name"
                                   name="first_name" placeholder="창고코드">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-4">
                    <div class="row">
                        <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block">창고코드</label>
                        <div class="col-sm-9 border-left border-bottom">
                            <input type="text" class="form-control form-control-sm" id="first_name"
                                   name="first_name" placeholder="창고코드">
                        </div>
                    </div>
                </div>
                <div class="form-group col-md-4 border">
                    <div class="row">
                        <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block border-left border-bottom">창고코드</label>
                        <div class="col-sm-9 border-left border-bottom">
                            <input type="text" class="form-control form-control-sm" id="first_name"
                                   name="first_name" placeholder="창고코드">
                        </div>
                    </div>
                </div>
                <div class="form-group col-md-4 border">
                    <div class="row">
                        <label for="first_name" class="col-sm-3 col-form-label col-form-label-sm d-none d-sm-block">창고코드</label>
                        <div class="col-sm-9 border-left border-bottom">
                            <input type="text" class="form-control form-control-sm" id="first_name"
                                   name="first_name" placeholder="창고코드">
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>