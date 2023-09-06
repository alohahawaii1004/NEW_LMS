/*
 * @(#)paragon.condition.js     2022-03-22(022) 오전 7:57
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
 * 검색 조건 설정 라이브러리
 * [의존성: paragonCmm.js, paragon.html.js,  js-storage, jquery Easy UI(모달)]
 * Description:
 *     조회조건 정보를 설정, 설정된 정보를 로컬저장소에 저장하고 불러온다.
 */
(function(j){
    "use strict";
    var CONDINFO_MODAL_ID = "conditionManagerModal";
    var CONDINFO_STORAGE_NAMESPACE = "paragonGridConditions";
    var COND_MNG_DIALOG_URL = "/cmm/modal/schCondMngModal.include";

    // condition 저장소
    var conStorage = Storages.initNamespaceStorage(CONDINFO_STORAGE_NAMESPACE);
    var nsLocalConStorage = conStorage.localStorage;

    /**
     * 조회조건 저장
     * @param gridId grid id
     * @param conds conditions(map)
     */
    var setConditions = function (gridId, conds) {
        nsLocalConStorage.set(gridId, conds);
    };

    /**
     * 조회조건 얻기
     * @param gridId grid id
     * @returns {string|null} conditions map
     */
    var getConditions = function (gridId) {
        if(nsLocalConStorage && !nsLocalConStorage.isEmpty(gridId)){
            return nsLocalConStorage.get(gridId);
        }else{
            return null;
        }
    };

    /**
     * 조회조건 매니저(모달) 열기
     * @param gridId grid id
     * @param cbFnc callback function
     */
    var openConditionMng = function (gridId, cbFnc) {
        var options = {
            modalId: CONDINFO_MODAL_ID,
            href: paragonCmm.getUrl(COND_MNG_DIALOG_URL),
            title: "Search Condition Info.",
            params: {
                modalId: CONDINFO_MODAL_ID,
                gridId: gridId
            },
            cbFnc: cbFnc,
            width: 1152,
            height: "auto",
            resizable: true,
            top: "20px"
        };

        if(paragonCmm.isMobile()){
            options = $.extend(options, {
                resizable: false,
                width: "80%",
                height: "50%",
                buttons: [{
                    id: "selectAllBtn",
                    text: "<i class=\"fa fa-check\"></i> 전체선택"
                }, {
                    id: "condsInit",
                    text: "<i class=\"fa fa-refresh\"></i> 리셋"
                },{
                    id: "condsSave",
                    text: "<i class=\"fa fa-save\"></i> 저장"
                }]
            });
        }

        htmlUtils.openDialog(options);
    };

    /**
     * 조회조건 정보
     * @param options
     * @returns {*}
     * @constructor
     */
    var ConditionMngInfo = function (options) {
        var defaultInfo = {
            gridId: null,
            idAttr: "id",
            nameAttr: "title",
            initConds: [],
            allCondDatas: [],
            viewCondsDatas: []
        };

        var condMngInfo = $.extend({}, defaultInfo, options);

        if (!Array.isArray(condMngInfo.initConds)) {
            throw new Error("initConds 가 배열이 아닙니다.");
        }
        if (!Array.isArray(condMngInfo.allCondDatas)) {
            throw new Error("allCondDatas 가 배열이 아닙니다.");
        }
        if (!Array.isArray(condMngInfo.viewCondsDatas)) {
            throw new Error("viewCondsDatas 가 배열이 아닙니다.");
        }

        if (condMngInfo.viewCondsDatas.length === 0) {
            condMngInfo.viewCondsDatas = [];
            condMngInfo.initConds.forEach(function (id) {
                condMngInfo.allCondDatas.some(function (col) {
                    if (col[condMngInfo.idAttr] === id) {
                        condMngInfo.viewCondsDatas.push(col);
                    }
                });
            });
        }else{
            var tmpDatas = [];
            condMngInfo.viewCondsDatas.forEach(function(vcol){
                var id = vcol[condMngInfo.idAttr];
                condMngInfo.allCondDatas.some(function(col){
                    if (col[condMngInfo.idAttr] === id) {
                        tmpDatas.push(col);
                    }
                });
            });

            condMngInfo.viewCondsDatas = tmpDatas.concat([]);
        }

        return condMngInfo;
    };

    /**
     * 조회조건 관리 객체
     *
     * @param options
     * @returns {{openConditionMng: openConditionMng, getNameAttr: (function(): string|*), getViewCondDatas: (function(): []), getGridId: (function(): null|*), getIdAttr: (function(): string|*), makeSch: makeSch, setConds: setConds, setViewCondDatas: setViewCondDatas, getAllCondDatas: (function(): []), getInitConds: (function(): []), getConds: getConds}}
     * @constructor
     */
    var ConditionMngObj = function (options) {
        var opt = $.extend(getConditions(options.gridId), options);
        var condMngInfo = new ConditionMngInfo(opt);
        setConditions(options.gridId, condMngInfo);
        var idAttr = condMngInfo.idAttr;
        var nameAttr = condMngInfo.nameAttr;

        var setViewCondDatas = function (viewCols) {
            condMngInfo.viewCondsDatas = [];
            viewCols.forEach(function (id) {
                condMngInfo.allCondDatas.some(function (col) {
                    if (col[condMngInfo.idAttr] === id) {
                        condMngInfo.viewCondsDatas.push(col);
                    }
                });
            });
        };

        var getSchComponentHtml = function(itm){

            var itmType = itm.type;
            var id = itm[idAttr];
            var comHtml = "";
            switch(itmType){
                case "INPUT":
                    comHtml = "<input type='text' name='"+id+"' class='form-control form-control-sm' placeholder='"+filterXSS(itm[nameAttr])+"'>";
                    break;
                case "FROM_TO_INPUT":
                    comHtml += "<div class='form-inline'>";
                    comHtml += "<input type='text' name='"+itm.fromId+"' class='form-control form-control-sm' style='width:46%;' placeholder='"+filterXSS(itm[nameAttr])+"'>";
                    comHtml += " <span class='p-1'> ~ </span> ";
                    comHtml += "<input type='text' name='"+itm.toId+"' class='form-control form-control-sm' style='width:46%;' placeholder='"+filterXSS(itm[nameAttr])+"'>";
                    comHtml += "</div>";
                    break;
                case "SELECT":
                    comHtml = "<select name='"+id+"' class='form-control form-control-sm'></select>";
                    break;
                case "MULTI_SELECT":
                    comHtml += "<div class='form-inline'>";
                    $(itm.opt).each(function(i,obj){
                        comHtml += "<select name='"+obj.targetNm+"' style='width:"+obj.width+";display:inline-block;' class='form-control form-control-sm' data-type='search'></select>";
                        comHtml += "&nbsp;&nbsp;";
                    });
                    comHtml += "</div>";
                    break;
                case "CHECKBOX":
                    comHtml = "<input name='"+id+"' class='form-control form-control-sm'>";
                    break;
                case "FROM_TO_CAL":
                    comHtml += "<div class='form-inline'>";
                    comHtml += "<input name='"+itm.fromId+"' class='form-control form-control-sm' style='width:46%;' placeholder='YYYY-MM-DD'>";
                    comHtml += " <span class='p-1'> ~ </span> ";
                    comHtml += "<input name='"+itm.toId+"' class='form-control form-control-sm' style='width:46%;' placeholder='YYYY-MM-DD'>";
                    comHtml += "</div>";
                    break;
                case "CALENDAR":
                    comHtml = "<input name='"+id+"' class='form-control form-control-sm' style='width:46%;'>"
                    break;
                default:
                    break;
            }

            return comHtml;
        };

        var makeSch = function (schId) {

            condMngInfo = getConditions(condMngInfo.gridId);
            // 1. html 구성
            var strHtml = "";
            var cnt = 0;
            var remain = 0;
            condMngInfo.viewCondsDatas.forEach(function(itm, idx){
                strHtml += "<div class='form-group col-md-4'>"; // form-group 을 없애면 줄간격이 좁아짐
                strHtml += "    <div class='row'>";
                strHtml += "        <label class='col-sm-3 col-form-label col-form-label-sm d-none d-sm-block'>"
                strHtml += filterXSS(itm[nameAttr])
                strHtml += "        </label>"
                strHtml += "        <div class='col-sm-9'>"
                strHtml += getSchComponentHtml(itm);
                strHtml += "        </div>";
                strHtml += "    </div>";
                strHtml += "</div>";
            });

            $(schId).html("");
            $(schId).html(strHtml);
            
            // 2. 컴포넌트 설정
            condMngInfo.viewCondsDatas.forEach(function(itm){
                var itmType = itm.type;
                var id = itm[idAttr];
                var opt = itm.opt;

                switch(itmType){
                    case "SELECT":
                        opt.targetId = $(schId).find("select[name="+id+"]");
                        htmlUtils.getCodeSelectOpt(opt);
                        break;
                    case "MULTI_SELECT":
                        // 1. 콤보 구성
                        $(itm.opt).each(function(i,obj){
                            obj.targetId = $(schId).find("select[name="+obj.targetNm+"]");
                            htmlUtils.getCodeSelectOpt(obj);
                        });

                        // 2. 이벤트 설정
                        (itm.opt).forEach(function(obj, i){
                            var events = [].concat(obj.events);
                            events.forEach(function(eObj, j){
                                if(eObj){
                                    (obj.targetId).on(eObj.type, function(){
                                        var tgId;
                                        if(eObj.targetNm){
                                            tgId = $(schId).find("select[name="+eObj.targetNm+"]");
                                        }
                                        var handler = paragonCmm.getCbFnc(eObj.handlerId);
                                        if(typeof handler === "function"){
                                            handler($(this), tgId);
                                        }
                                        return false;
                                    });
                                }
                            });
                        });
                        break;
                    case "CHECKBOX":
                        var $span = $(schId).find("span[name="+id+"]");

                        /*
                        TODO

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
                         */


                        htmlUtils.loadCodeStr({
                            parentCd: opt.parentCd,
                            fields: opt.fields,
                            callBackFnc: function(str){
                                $span.html(htmlUtils.getInputCheckbox(id, str,""));
                            }
                        });
                        break;
                    case "FROM_TO_CAL":
                        var $from = $(schId).find("input[name="+itm.fromId+"]");
                        var $to = $(schId).find("input[name="+itm.toId+"]");
                        var defaultOpt = {validType: "validDate"};
                        if(itm.hasOwnProperty("formatter")){
                            if (itm["formatter"] === "YearMonth") {
                                defaultOpt = {
                                    formatter: function(date) {
                                        var y = date.getFullYear();
                                        var m = date.getMonth()+1;
                                        return y+'-'+(m<10?('0'+m):m);
                                    }
                                    , parser: function(date) {
                                        if (typeof date === 'string' && date.trim().length === 0) {
                                            return new Date();
                                        }
                                        var t = Date.parse(date+'-01');
                                        if (!isNaN(t)){
                                            return new Date(t);
                                        } else {
                                            return new Date();
                                        }
                                    }
                                }
                            }
                        }
                        $from.datebox(defaultOpt);// easyui
                        $to.datebox(defaultOpt);// easyui
                        break;
                    case "CALENDAR":
                        var $cal = $(schId).find("input[name="+id+"]");
                        $cal.datebox({validType: "validDate"});// easyui
                    default:
                        break;
                }
            });
        };

        return {
            setViewCondDatas: setViewCondDatas,
            getViewCondDatas: function () {
                condMngInfo = getConditions(condMngInfo.gridId)
                return condMngInfo.viewCondsDatas;
            },
            getAllCondDatas: function () {
                return condMngInfo.allCondDatas;
            },
            getInitConds: function () {
                return condMngInfo.initConds;
            },
            getGridId: function(){
                return condMngInfo.gridId;
            },
            getIdAttr: function(){
                return condMngInfo.idAttr;
            },
            getNameAttr: function(){
                return condMngInfo.nameAttr;
            },
            makeSch: makeSch,
            // 컬럼설정
            setConds: function () {
                setConditions(condMngInfo.gridId, condMngInfo);
            },
            // 컬럼얻기
            getConds: function () {
                condMngInfo = getConditions(condMngInfo.gridId);
            },
            // 컬럼정보 열기(모달)
            openConditionMng: openConditionMng
        };
    };

    j.condition = (function () {
        return {
            createObj: function (options) {
                return new ConditionMngObj(options);
            }
        };
    })();
})(paragonCmm);