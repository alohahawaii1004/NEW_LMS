/*
 * @(#)codeMng.js    
 *
 * Copyright 2022 JAYU.space
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
 * <b>Description</b>
 * <pre>
 *    코드 관리 화면
 * </pre>
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
$(document).ready(function () {

    console.info("[Loading Module: 코드 관리].......................");

    var MODULE_NAMESPACE = "SELF.code.codeMng";
    jayu.setNS(MODULE_NAMESPACE);
    jayu.appModules.SELF.code.codeMng = (function(){
        "use strict";

        var module = module || {};

        var MODULE_NAME = "codeMng";
        var MODULE_ID = "#"+MODULE_NAME;

        var config = {
            topLayerId: MODULE_NAME + "Top0",
            mainLayerID: MODULE_NAME + "Main0",
            codeTypeMngID: MODULE_NAME + "List1",
            codeTypeBtnID: MODULE_NAME + "ListFrm1",
            dtlCodeMngID: MODULE_NAME + "List2",
            dtlCodeBtnID: MODULE_NAME + "ListFrm2"
        };

        var sizeObj = jayu.getSize();
        var BUTTON_HEIGHT = 50;
        var gridHeight = sizeObj.height - 100 - BUTTON_HEIGHT;
        gridHeight = sizeObj.height - 110 - BUTTON_HEIGHT;

        var getGridHeight = function(){
            "use strict";
            sizeObj = jayu.getSize();
            return (sizeObj.height - 110 - BUTTON_HEIGHT);
        };

        var masterSchFrm = {
            jqObj: $(MODULE_ID + "SchFrm1")
        };

        var detailSchFrm = {
            jqObj: $(MODULE_ID + "SchFrm2")
        };

        var pagerTemplate = function(data, common){
            "use strict";

            var current = data.page + 1;

            var pagerHtml = "";
            pagerHtml += " <select name=\"PageSize\"><option>10</option></select>";
            pagerHtml += " | ";
            pagerHtml += " <button name=\"FirstPage\" type=\"button\" title=\"WRD.첫페이지\" class=\"btn btn-sm i18n\" data-i18n=\"WRD.첫페이지\"><i class=\"fa fa-step-backward\"></i></button>";
            pagerHtml += " <button name=\"PrevPage\" type=\"button\" title=\"WRD.이전페이지\" class=\"btn btn-sm i18n\" data-i18n=\"WRD.이전페이지\"><i class=\"fa fa-caret-left\"></i></button>";
            // pagerHtml += common.first();
            // pagerHtml += common.prev();
            pagerHtml += " | ";
            pagerHtml += " Page <input type=\"text\" name=\"page\" class=\"text-center i18n\" style=\"width: 40px;\" placeholder=\"WRD.페이지\" data-i18n=\"WRD.페이지\" value=\""+current+"\"> of "+data.limit;
            pagerHtml += " | ";
            pagerHtml += " <button name=\"NextPage\" type=\"button\" title=\"WRD.다음페이지\" class=\"btn btn-sm i18n\" data-i18n=\"WRD.다음페이지\"><i class=\"fa fa-caret-right\"></i></button>";
            pagerHtml += " <button name=\"LastPage\" type=\"button\" title=\"WRD.끝페이지\" class=\"btn btn-sm i18n\" data-i18n=\"WRD.끝페이지\"><i class=\"fa fa-step-forward\"></i></button>";
            // pagerHtml += common.next();
            // pagerHtml += common.last();
            pagerHtml += " | ";
            pagerHtml += " <button name=\"Reload\" type=\"button\" title=\"WRD.새로고침\" class=\"btn btn-sm i18n\" data-i18n=\"WRD.새로고침\"><i class=\"fa fa-redo\"></i></button>";

            var pagerHtml2 = "";
            pagerHtml2 += " <select name=\"PageSize\"><option>10</option></select>";
            pagerHtml2 += " | ";
            pagerHtml2 += " <span name=\"FirstPage\"  title=\"WRD.첫페이지\" class=\"btn btn-sm i18n\" data-i18n=\"WRD.첫페이지\"><i class=\"fa fa-step-backward\"></i></span>";
            pagerHtml2 += " <span name=\"PrevPage\"  title=\"WRD.이전페이지\" class=\"btn btn-sm i18n\" data-i18n=\"WRD.이전페이지\"><i class=\"fa fa-caret-left\"></i></span>";
            // pagerHtml2 += common.first();
            // pagerHtml2 += common.prev();
            pagerHtml2 += " | ";
            pagerHtml2 += " Page <input type=\"text\" name=\"page\" class=\"text-center i18n\" style=\"width: 40px;\" placeholder=\"WRD.페이지\" data-i18n=\"WRD.페이지\" value=\""+current+"\"> of "+data.limit;
            pagerHtml2 += " | ";
            pagerHtml2 += " <span name=\"NextPage\"  title=\"WRD.다음페이지\" class=\"btn btn-sm i18n\" data-i18n=\"WRD.다음페이지\"><i class=\"fa fa-caret-right\"></i></span>";
            pagerHtml2 += " <span name=\"LastPage\"  title=\"WRD.끝페이지\" class=\"btn btn-sm i18n\" data-i18n=\"WRD.끝페이지\"><i class=\"fa fa-step-forward\"></i></span>";
            // pagerHtml2 += common.next();
            // pagerHtml2 += common.last();
            pagerHtml2 += " | ";
            pagerHtml2 += " <span name=\"Reload\"  title=\"WRD.새로고침\" class=\"btn btn-sm i18n\" data-i18n=\"WRD.새로고침\"><i class=\"fa fa-redo\"></i></span>";

            var pagerHtml3 = "";

            return pagerHtml3;
        };

        var clientPager = {
            view: "pager",
            id: MODULE_NAME + "Pager1",
            container: MODULE_NAME + "Pager1",
            // stand alone 모드
            master: false,
            size: jayu.default.pageSize,
            // template: "{common.first()} {common.prev()} {common.pages()} {common.next()} {common.last()}"
            template: function(data, common){
                "use strict";
                return pagerTemplate(data, common);
            }
        };


        var searchGrid = function(mngObj, params){
            "use strict";
            if(mngObj && mngObj.uiObj){
                var grid = mngObj.uiObj;

                // clear grid
                grid.clearAll();

                grid.load(function () {
                    "use strict";
                    return webix.ajax().post(mngObj.SEARCH_URL, params).then(function (data) {
                        if (data) {
                            return data.json();
                        }
                    });
                });
            }
        };

        /**
         * row 삭제
         * @param mngObj manager Object- code type, detail code
         */
        var deleteRow = function(mngObj){
            "use strict";
            if(mngObj && mngObj.uiObj){

                var grid = mngObj.uiObj;

                // 그리드에 오버레이된 메시지가 있으면 지운다.
                grid.hideOverlay();

                if(grid.count() === 0){
                    return;
                }

                var rowId = grid.getSelectedId(true);
                if(jayu.isEmpty(rowId) || rowId.length === 0){
                    webix.alert(jayu.replaceMsg("MSG.필수선택", "WRD.ROW"));
                    return;
                }

                webix.confirm(jayu.getTerm("MSG.삭제여부")).then(function(result){
                    "use strict";
                    var params = grid.getItem(rowId);

                    if(jayu.crud.CREATE === params.cud){
                        // 신규 데이터는 화면에서만 삭제
                        grid.remove(rowId);
                    }else{
                        params.cud = jayu.crud.DELETE;
                        webix.ajax().post(mngObj.DELETE_URL, params).then(function(data){
                            "use strict";
                            var rtnJson = data.json();
                            webix.alert(jayu.replaceMsg(rtnJson.msg));
                            if(rtnJson.errYn !== "E"){
                                mngObj.search();
                            }
                        });
                    }
                });
            }
        };

        /**
         * 그리드 저장
         * @param mngObj manager Object- code type, detail code
         */
        var saveGrid = function(mngObj){
            "use strict";
            var grid = (mngObj) ? mngObj.uiObj: null;

            // console.log(grid.count());

            if(jayu.isNotEmpty(grid) && grid.validate() && grid.count() > 0){
                webix.confirm(jayu.replaceMsg("MSG.저장여부")).then(function(result){
                    "use strict";
                    // [주의] : back-end(DTO/VO) 에도 list로 정의
                    var params = {
                        list: grid.serialize()
                    };

                    /*
                     * webix grid(datatable)에서 내용리스트를 serialize 하여 back-end로 전송할 경우
                     * JSON String 으로 전송해야한다.
                     *
                     * back-end 는 Spring 의 경우 @RequestBody 를 이용해서 받을 수 있다.
                     */
                    webix.ajax().headers({
                        // JSON String 으로 전송하기위해 header를 설정해 준다.
                        "Content-type":"application/json"
                    }).post(mngObj.SAVE_URL, JSON.stringify(params)).then(function(data){
                        "use strict";
                        var rtnJson = data.json();

                        webix.alert(jayu.replaceMsg(rtnJson.msg));

                        if(rtnJson.errYn !== "E"){
                            mngObj.search();
                        }
                    });
                });
            }
        };

        /**
         * 엑셀 출력
         * @param mngObj manager Object- code type, detail code
         */
        var exportExcel = function(mngObj){
            "use strict";
            var grid = (mngObj) ? mngObj.uiObj: null;
            if(jayu.isNotEmpty(grid) && grid.count() > 0){
                jayu.webix.toExcel(mngObj.uiObj, mngObj.excelFileName);
            }
        };

        var codeTypeMng = {
            ID: config.codeTypeMngID,
            SEARCH_URL: jayu.sysname + "/jyself/code/CodeMng/codeType/list/json",
            SAVE_URL: jayu.sysname + "/jyself/code/CodeMng/codeType/save/json",
            DELETE_URL: jayu.sysname + "/jyself/code/CodeMng/codeType/delete/json",
            uiObj: null,
            excelFileName: "codeTypeList",
            grid: {
                // webix UI widget type
                view: "datatable",
                // the ID of a widget
                id: config.codeTypeMngID,
                // an HTML container (or its ID) where the component should be initialized
                container: config.codeTypeMngID,
                // (option) UI css class name
                css: "webix_header_border webix_data_border", // 그리드 내부 구분선 표시 css class
                height: gridHeight,
                // (PRO option) adds a header menu that helps to control the column visibility
                headermenu: true,
                select: "row",
                editable: true,
                editaction: "dblclick",
                // configures columns of the table
                columns: [{
                    id:"index", header:{ text:"No.", height: 28}, css:{"text-align":"center"}, fillspace: 8
                }, {
                    id: "cud", header: "cud", hidden: true
                }, {
                    id: "tpId", header: jayu.getTerm("WRD.유형"), css:{"text-align":"left"}, fillspace: 25, editor:"text", template:"#!tpId#"
                }, {
                    id: "tpNm", header: jayu.getTerm("WRD.이름"), css:{"text-align":"left"}, fillspace: 25, editor:"text", template:"#!tpNm#"
                }, {
                    id: "remark", header: jayu.getTerm("WRD.설명"), css:{"text-align":"left"}, fillspace: 32, editor:"text", template:"#!remark#"
                }, {
                    id: "useYn", header: jayu.getTerm("WRD.사용여부"), css:{"text-align":"center"}, fillspace: 10, editor:"select", options: {"Y":"Use","N":"No Use"}
                }],
                // set of validation rules for the component
                rules: {
                    tpId: webix.rules.isNotEmpty,
                    tpNm: webix.rules.isNotEmpty
                },

                // defines schemes for data processing
                scheme: {
                    /**
                     * Static index columns
                     */
                    $init: function(obj){
                        "use strict";
                        obj.idx = this.count();
                    }
                },
                // allows attaching custom handlers to inner events of the component
                on: {
                    onBeforeLoad: function(){
                        "use strict";
                        this.showOverlay(jayu.getTerm("MSG.로딩중")+"...");
                    },
                    onAfterLoad: function(){
                        "use strict";
                        this.hideOverlay();
                        if (!this.count()){
                            this.showOverlay(jayu.replaceMsg("MSG.NODATA"));
                        }
                    },
                    onBeforeEditStart: function(id){
                        "use strict";
                        var item = this.getItem(id.row);

                        if(typeof item.cud === "undefined" && (id.column === "tpId")){

                            // 읽어온 데이터(undefined) 이거나 PK 칼럼인경우 수정 불가
                            return false;
                        }else{

                            // 생성된건이 아니라면 수정상태로 변경
                            if(item.cud !== jayu.crud.CREATE){
                                item.cud = jayu.crud.UPDATE;
                            }
                        }
                    },

                    /**
                     * Dynamic index columns
                     *
                     * $$('mytable').data.attachEvent('onStoreUpdated', function(){...})
                     */
                    "data->onStoreUpdated": function(){
                        "use strict";
                        this.data.each(function(obj, i){
                            obj.index = i+1;
                        });
                    }
                }
            },
            buttons: {
                id: config.codeTypeBtnID,
                view: "template",
                content: config.codeTypeBtnID
            },
            /**
             * 코드유형 그리드 초기화
             * (그리드 단독사용시)
             */
            initGrid: function(){
                this.uiObj = webix.ui(this.grid);
            }, // end of initGrid

            resize: function(){
                "use strict";
                if(this.uiObj){
                    this.uiObj.adjust();
                }
            },

            /**
             * 상세코드 조회를 위한 코드 유형정보 설정
             * @param id
             */
            setCodeTpInfo: function(id){
                "use strict";
                var cdTypeGrid = this.uiObj;
                var item = cdTypeGrid.getItem(id);

                if(item.cud !== jayu.crud.CREATE){
                    var dtlSchFrm = detailSchFrm.jqObj;
                    dtlSchFrm.trigger("reset");
                    dtlSchFrm.find("input[name=tpId]").val(item.tpId);
                    dtlSchFrm.find("input[name=tpNm]").val(item.tpNm);

                    // dtlCdFrm.find("input[name=tpId]").change();
                }
            },

            search: function(){
                "use strict";
                // search condition
                var params = masterSchFrm.jqObj.serialize();
                searchGrid(this, params);
            },

            addRow: function(){
                "use strict";
                var cdTypeMng = this;
                if(this.uiObj){
                    jayu.webix.addRow(cdTypeMng.ID, cdTypeMng.uiObj, {
                        useYn: "Y",
                        cud: jayu.crud.CREATE
                    });
                }
            },

            delRow: function(){
                "use strict";
                deleteRow(this);
            },

            save: function(){
                "use strict";
                saveGrid(this);
            },

            toExcel: function(){
                "use strict";
                exportExcel(this);
            }
        };

        var dtlSchFrm = {
            jqObj: $(MODULE_ID + "SchFrm2"),
            reset: function(){
                this.jqObj.reset();
            }
        };

        var dtlCodeMng = {
            ID: config.dtlCodeMngID,
            SEARCH_URL: jayu.sysname + "/jyself/code/CodeMng/code/list/json",
            SAVE_URL: jayu.sysname + "/jyself/code/CodeMng/code/save/json",
            DELETE_URL: jayu.sysname + "/jyself/code/CodeMng/code/delete/json",
            uiObj: null,
            excelFileName: "codeList",
            grid: {
                view: "datatable",
                id: config.dtlCodeMngID,
                container: config.dtlCodeMngID,
                css: "webix_header_border webix_data_border",
                height: gridHeight,
                headermenu: true,
                select: "row",
                editable: true,
                editaction: "dblclick",
                columns: [{
                    id: "index", header:"No.", css:{"text-align":"center"}, fillspace: 5
                }, {
                    id: "tpId", header: jayu.getTerm("WRD.유형"), css:{"text-align":"left"}, fillspace: 20, template:"#!tpId#"
                }, {
                    id: "parntCd", header: jayu.getTerm("WRD.부모코드"), css:{"text-align":"left"}, fillspace: 10, template:"#!parntCd#", hidden: true
                }, {
                    id: "cd", header: jayu.getTerm("WRD.코드"), css:{"text-align":"left"}, fillspace: 20, editor:"text", template:"#!cd#"
                }, {
                    id: "cdNm", header: jayu.getTerm("WRD.이름"), css:{"text-align":"left"}, fillspace: 20, editor:"text", template:"#!cdNm#"
                }, {
                    id: "sortSn", header: jayu.getTerm("WRD.순서"), css:{"text-align":"center"}, fillspace: 8, editor:"text", template:"#!sortSn#", format: webix.i18n.intFormat
                }, {
                    id: "remark", header: jayu.getTerm("WRD.설명"), css:{"text-align":"left"}, fillspace: 20, editor:"text", template:"#!remark#"
                }, {
                    id: "useYn", header: jayu.getTerm("WRD.사용여부"), css:{"text-align":"center"}, fillspace: 7, editor:"select", options: {"Y":"Use","N":"No Use"}
                }],
                rules: {
                    tpId: webix.rules.isNotEmpty,
                    cd: webix.rules.isNotEmpty,
                    cdNm: webix.rules.isNotEmpty
                },
                scheme: {
                    $init: function (obj) {
                        "use strict";
                        obj.index = this.count();
                    }
                },
                on: {
                    onBeforeLoad: function () {
                        "use strict";
                        this.showOverlay(jayu.getTerm("MSG.로딩중") + "...");
                    },
                    onAfterLoad: function () {
                        "use strict";
                        this.hideOverlay();
                        if (!this.count()) {
                            this.showOverlay(jayu.replaceMsg("MSG.NODATA"));
                        }
                    },
                    onBeforeEditStart: function (id) {
                        "use strict";
                        var item = this.getItem(id.row);

                        if (typeof item.cud === "undefined" && (id.column === "tpId" || id.column === "cd")) {

                            // 읽어온 데이터(undefined) 이거나 PK 칼럼인경우 수정 불가
                            return false;
                        } else {

                            // 생성된건이 아니라면 수정상태로 변경
                            if (item.cud !== jayu.crud.CREATE) {
                                item.cud = jayu.crud.UPDATE;
                            }
                        }
                    },
                    "data->onStoreUpdated": function () {
                        "use strict";
                        this.data.each(function (obj, i) {
                            obj.index = i + 1;
                        });
                    }
                }
            },
            buttons: {
                id: config.dtlCodeBtnID,
                view: "template",
                content: config.dtlCodeBtnID
            },
            initGrid: function(){
                this.uiObj = webix.ui(this.grid);
            },
            resize: function(){
                "use strict";
                if(this.uiObj){
                    this.uiObj.adjust();
                }
            },

            search: function(){
                "use strict";
                var tpId = (detailSchFrm.jqObj).serializeObject().tpId;
                if(jayu.isEmpty(tpId)){
                    webix.alert(jayu.replaceMsg("MSG.필수선택","WRD.유형"));
                    return;
                }

                var params = detailSchFrm.jqObj.serialize();
                searchGrid(this, params);
            },

            addRow: function(){
                "use strict";
                var dtlCdMng = this;
                if(dtlCdMng.uiObj){
                    var tpId = (detailSchFrm.jqObj).serializeObject().tpId;

                    if(jayu.isEmpty(tpId)){
                        webix.alert(jayu.replaceMsg("MSG.필수선택","WRD.유형"));
                        return;
                    }

                    jayu.webix.addRow(dtlCdMng.ID, dtlCdMng.uiObj, {
                        tpId: tpId,
                        useYn: "Y",
                        cud: jayu.crud.CREATE
                    });
                }
            },

            delRow: function(){
                "use strict";
                deleteRow(this);
            },

            save: function(){
                "use strict";
                saveGrid(this);
            },

            toExcel: function(){
                "use strict";
                exportExcel(this);
            }
        };

        var layout = {
            id: config.mainLayerID,
            container: config.mainLayerID,
            view: "accordion",
            type: "line",
            multi: "mixed",
            height: sizeObj.height,
            cols: [{
                id: "codeType",
                header: "Code Type",
                body: {
                    rows: [
                        codeTypeMng.grid,
                        codeTypeMng.buttons
                    ]
                }
            }, {
                view: "resizer",
                // HACK
                // mixed 모드 동작을 위해 추가
                collapsed: true
            }, {
                id: "code",
                header: "Code List",
                body: {
                    rows: [
                        dtlCodeMng.grid,
                        dtlCodeMng.buttons
                    ]
                }
            }]
        };

        var setCodeTypeEvent= function(){
            "use strict";

            // 메인 검색폼 전송
            masterSchFrm.jqObj.on("submit", function(){
                "use strict";
                codeTypeMng.search();
                return false;
            })

            // 코드유형 아이템 클릭
            var cdTypeGrid = codeTypeMng.uiObj;

            /**
             * 신규 생성 아이템이 아닐경우 상세 검색폼에 정보 입력
             */
            cdTypeGrid.attachEvent("onItemClick", function(id, e, node){
                "use strict";
                codeTypeMng.setCodeTpInfo(id);
            });

            // 검색 버튼 클릭
            $(MODULE_ID+"SchBtn1").on("click", function(){
                "use strict";
                codeTypeMng.search();
            });

            // 행추가
            $(MODULE_ID+"AddRowBtn1").on("click", function(){
                "use strict";
                codeTypeMng.addRow();
            });

            // 행삭제
            $(MODULE_ID+"DelRowBtn1").on("click", function(){
                "use strict";
                codeTypeMng.delRow();
            });

            // 저장
            $(MODULE_ID+"SaveBtn1").on("click", function(){
                "use strict";
                codeTypeMng.save();
            });

            // 엑셀
            $(MODULE_ID+"ExcelBtn1").on("click", function(){
                "use strict";
                codeTypeMng.toExcel();
            });
        };

        var setDetailCodeEvent = function(){
            "use strict";

            // 상세 리스트 검색
            var dtlSchFrm = detailSchFrm.jqObj;
            var tpId = dtlSchFrm.find("input[name=tpId]");
            tpId.on("change", function(){
                "use strict";

                if(jayu.isNotEmpty(this.value)){
                    dtlCodeMng.search();
                }
            });

            // 행추가
            $(MODULE_ID+"AddRowBtn2").on("click", function(){
                "use strict";
                dtlCodeMng.addRow();
            });

            // 행삭제
            $(MODULE_ID+"DelRowBtn2").on("click", function(){
                "use strict";
                dtlCodeMng.delRow();
            });

            // 저장
            $(MODULE_ID+"SaveBtn2").on("click", function(){
                "use strict";
                dtlCodeMng.save();
            });

            // 엑셀
            $(MODULE_ID+"ExcelBtn2").on("click", function(){
                "use strict";
                dtlCodeMng.toExcel();
            });


        };

        module.init = function(){
            "use strict";
            module.pager1 = webix.ui(clientPager);
            module.layout = webix.ui(layout);
            codeTypeMng.uiObj = $$(codeTypeMng.ID);
            dtlCodeMng.uiObj = $$(dtlCodeMng.ID);

            setDetailCodeEvent();
            setCodeTypeEvent();
        };

        module.resize = function(){
            "use strict";
            if(module.layout){
                // module.layout.adjust();

                // INFO
                // 내부 그리드는 초기 설정시 높이가 설정되어 있기 때문에 다시 resize 해야 함
                sizeObj = jayu.getSize();
                gridHeight = (sizeObj.height - 110 - BUTTON_HEIGHT);
                $$(layout.id).define("height", sizeObj.height);
                $$(codeTypeMng.ID).define("height", gridHeight);
                $$(dtlCodeMng.ID).define("height", gridHeight);

                $$(layout.id).resize(true);
            }
        };

        return {
            init: function(){
                "use strict";
                module.init();
            },
            getName: function(){
                "use strict";
                return MODULE_NAME;
            },
            resize: function(){
                "use strict";
                module.resize();
            }
        };
    }());

    var codeMng = jayu.getNS(MODULE_NAMESPACE);
    codeMng.init();

    jayu.appMap.set(codeMng.getName(), codeMng);
}());
