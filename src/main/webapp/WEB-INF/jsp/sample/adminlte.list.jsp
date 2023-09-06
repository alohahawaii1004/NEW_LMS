<%--
  - Author(s): Yang, Ki Hwa
  - Date: 2023-01-02(002)
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
  -     리스트 샘플
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<%-- search conditions --%>
<div class="row">
    <div class="col-md-6">
    </div>
    <div class="col-md-6 text-right">
        <div class="dropdown">
            <button class="btn btn-link btn-sm dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">
                <i class="fa fa-cog"></i>
                <%--                <i class="fa fa-ellipsis-v d-block d-sm-none"></i>--%>
            </button>
            <div class="dropdown-menu">
                <a class="dropdown-item" href="#">Set Conditions</a>
                <a class="dropdown-item" href="#">Set Columns</a>
            </div>
        </div>
    </div>
</div>
<div class="clearfix search-form">
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
<%-- data table --%>
<div class="data-table clearfix">
    <div class="row">
        <div class="form-group col-md-6">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio11" value="option1">
                <label class="form-check-label" for="inlineRadio11">List 1</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio12" value="option2">
                <label class="form-check-label" for="inlineRadio12">List 2</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio13" value="option2">
                <label class="form-check-label" for="inlineRadio13">List 2</label>
            </div>
        </div>
        <div class="form-group col-md-6 text-right">
            <button class="btn btn-primary btn-sm"><i class="fa fa-search"></i> BUTTON1</button>
            <button class="btn btn-default btn-sm"><i class="fa fa-undo"></i> BUTTON2</button>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <table id="listTable"></table>
        </div>
    </div>
</div>