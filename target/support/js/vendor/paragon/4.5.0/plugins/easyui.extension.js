/*
 * @(#)easyui.extension.js.js     2022-12-27(027) 오전 7:28:38
 *
 * Copyright 2022 JaYu.space
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

/**
 * EasyUI 그리드 전체데이터 excel 출력
 */
(function(j){
    "use strict";

    var tmpGridDiv = "tempGridDiv";
    var tmpGridId = "tempGrid";
    var fileName;
    var tables = [];
    var gridLength = 0;

    var getToday = function(){
        var today = new Date();
        var month = today.getMonth() + 1;
        var strToday = today.getFullYear();
        strToday += "-" + ((month < 10) ? "0" + month : month);
        strToday += "-" + today.getDate();

        var hours = ('0' + today.getHours()).slice(-2);
        var minutes = ('0' + today.getMinutes()).slice(-2);
        var seconds = ('0' + today.getSeconds()).slice(-2);

        var timeString = hours + '' + minutes  + '' + seconds;

        return strToday+"_"+timeString;
    };
    var opts = {
        grid: null,
        filename: "grid-data-"+getToday()+".xlsx",
        worksheet: "list",
        caption: "List"
    };

    var init = function(){
        // 1. delete old grid
        var $tmpGrid = $("table[data-wedget='tmpGrid']");
        $tmpGrid.each(function(idx, grid){
            var tmpId = $(grid).attr("id");
            
            $("#"+tmpId).datagrid("getPanel").panel("destroy");
            $(grid).remove();
        });
        // 2. remove div
        $("#"+tmpGridDiv).remove();

        // 3. create new div
        var sHtml = "<div id='"+tmpGridDiv+"' style='display: none;'></div>";
        // var sHtml = "<table id='"+tmpGridId+"'></table>";
        $("body").append(sHtml);
        tables = [];
    };

    var exportExcel = function(){
        if(gridLength === tables.length){

            if($("#testExport").length > 0){
                $("#testExport").remove();
            }
            $("body").append($("<div style='display:none;' id='testExport'>"));

            var wb = XLSX.utils.book_new();

            // 시트 순서 정렬
            opts.worksheet.forEach(function(sheet){
                tables.some(function(itm){
                    var sheetName = itm.sheetName;
                    if(sheet === sheetName){
                        wb.SheetNames.push(sheetName);

                        var table = itm.table;
                        $("#testExport").html(table);
                        var ws = XLSX.utils.table_to_sheet(document.getElementById("testExport").getElementsByTagName("table")[0], {raw: true});
                        wb.Sheets[sheetName] = ws;
                    }
                });
            });
            XLSX.writeFile(wb, fileName);

            // 엑셀 초기화
            $("#testExport").remove();
            // 출력 초기화
            init();
            $("#"+tmpGridDiv).remove();
        }
    };

    var loadData = function(grid, idx, sheetName){

        var gridId = tmpGridId + "-" +idx;

        // 1. add table
        $("#"+tmpGridDiv).append("<table id='"+gridId+"' data-wedget='tmpGrid'></table>")

        // 2. get options
        var gridOptions = grid.datagrid("options");

        // 3. make grid
        var $grid = $("#"+gridId);
        $grid.datagrid({
            url: gridOptions.url,
            method: "post",
            queryParams: gridOptions.queryParams,
            loadFilter: paragonCmm.easyuiLoadFilter,
            height: window.innerHeight,
            striped : true,
            fitColumns : true,
            nowrap: true,
            multiSort : true,
            remoteSort: true,
            rownumbers: true,
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            pagination : false,
            pagePosition: "bottom",
            // pageSize: getPageSize(),
            emptyMsg: "NO DATA",
            columns: gridOptions.columns,
            onLoadSuccess: function(data){
                var rows = $grid.datagrid("getRows");
                tables.push({
                    sheetName: sheetName,
                    table: $grid.datagrid("toHtml", rows)
                });

                exportExcel();
            }
        });
    };

    var toExcelAll = function(options){

        // 1. 기존 그리드 삭제
        init();

        opts = $.extend(opts, options);

        opts.grid = [].concat(opts.grid);
        opts.worksheet = [].concat(opts.worksheet);
        var sheetLen = opts.worksheet.length;
        gridLength = opts.grid.length;
        fileName = opts.filename;

        opts.grid.forEach(function(grid, idx ){
            var sheetName = (sheetLen - 1 <= idx) ? opts.worksheet[sheetLen - 1] : opts.worksheet[idx];
            loadData(grid, idx, sheetName);
        });
    };


    j.grid = (function(){
        return {
            toExcelAll: toExcelAll
        };
    })();
})(paragonCmm);