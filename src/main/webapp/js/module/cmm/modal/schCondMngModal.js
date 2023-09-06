/*
 * @(#)schCondMngModal.js     2023-01-06(006) 오후 4:35:40
 *
 * Copyright 2023 JaYu.space
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

var CondsInfo = function(){
    "use strict";

    //-- Root ID
    var $divRoot = $("#condsInfo");
    // 오프너 데이터
    var openerData = $divRoot.data("opener-data");
    var $modalRoot = $("#"+openerData.modalId);
    var $schTxt      = $("input[name='schConds']", $divRoot);

    var $source = $("#source");
    var $target = $("#target");
    var condData = paragonCmm.condition.createObj({gridId: openerData.gridId});
    var idAttr = condData.getIdAttr();
    var nameAttr = condData.getNameAttr();
    var allCondDatas = condData.getAllCondDatas();
    var initCondArray = condData.getInitConds();
    var DEFAULT_CLASS = "";
    var CHECK_CLASS = "bg-teal";
    var CLONE_CLASS = "bg-lightblue";

    //-- 검색 모달 닫기
    var closeWindow = function(){
        var $modal = $("#"+openerData.modalId);
        var cbFncClose = $modal.data("callback");
        if(typeof cbFncClose === "function"){
            // 모달일 경우 닫기
            cbFncClose(true);
        }else{
            // 팝업일 경우 닫기
            self.close();
        }
    };

    var saveConds = function(){
        var viewCols = [];
        $target.find("div").each(function(idx, itm){
            var id = $(itm).attr("id");
            id = id.replace("tgt_", "");
            viewCols.push(id);
        });
        condData.setViewCondDatas(viewCols);
        condData.setConds();
        closeWindow();
    };

    var searchTxt = function(schTxt){
        $("div", $source).hide();
        $("div[data-text*='"+schTxt+"']", $source).show();
    }

    var initForm = function(){
        console.debug("init sch cond mng......");
        if(paragonCmm.isMobile()){
            $("#btnLayer", $divRoot).hide();
        }

        // 1. source 설정
        $source.empty();

        var allColHtml = "";
        allCondDatas.forEach(function(itm,idx){
            var colNm = filterXSS(itm[nameAttr]);
            allColHtml += '<div class="col-sm-2 pb-1" id="src_'+filterXSS(itm[idAttr])+'" data-text="'+colNm+'">';
            allColHtml += '<span class="form-control text-truncate text-xs pt-2 text-center text-truncate font-weight-bold"';
            allColHtml += ' title="'+colNm+'">';
            allColHtml += colNm;
            allColHtml += '</span>';
            allColHtml += '</div>';
        });
        $source.html(allColHtml);
    };

    var loadForm = function(){
        $target.empty();
        var viewColDatas = condData.getViewCondDatas();
        var viewColHtml = "";
        viewColDatas.forEach(function(itm){
            var colNm = filterXSS(itm[nameAttr]);
            var colId = filterXSS(itm[idAttr]);
            viewColHtml += '<div class="col-sm-4 pb-1" id="tgt_'+filterXSS(itm[idAttr])+'" data-text="'+colNm+'">';
            viewColHtml += '<span class="form-control text-truncate text-xs pt-2 text-center text-truncate bg-lightblue font-weight-bold"';
            viewColHtml += ' title="'+colNm+'">';
            viewColHtml += colNm;
            viewColHtml += '</span>';
            viewColHtml += '</div>';

            $("#src_"+colId).find("span").removeClass(DEFAULT_CLASS);
            $("#src_"+colId).find("span").addClass(CHECK_CLASS);
        });

        $target.html(viewColHtml);
        $target.sortable();
    };

    var clickSrcCol = function(obj){
        var objId = $(obj).attr("id");
        var id = objId.replace("src_", "");

        if($(obj).find("span").hasClass(CHECK_CLASS)){

            $(obj).find("span").removeClass(CHECK_CLASS);
            $(obj).find("span").addClass(DEFAULT_CLASS);
            $("#tgt_"+id).remove();

        }else{
            var clone =  $(obj).clone();
            clone.attr("id", "tgt_"+id);
            clone.removeClass("col-sm-2");
            clone.addClass("col-sm-4");
            clone.find("span").addClass(CLONE_CLASS);
            $target.append(clone);
            var tmpHtml = $target.html();
            // $target.empty();
            // $target.html(tmpHtml);

            $(obj).find("span").removeClass(DEFAULT_CLASS);
            $(obj).find("span").addClass(CHECK_CLASS);
        }

    };

    var dblClickTgCol = function(obj){
        var objId = $(obj).attr("id");
        var id = objId.replace("tgt_", "");
        var $src = $("#src_"+id);
        $src.find("span").removeClass(CHECK_CLASS);
        $src.find("span").addClass(DEFAULT_CLASS);
        $("#tgt_"+id).remove();
    };

    var initConds = function(){
        $target.empty();
        $source.find("span").removeClass(CHECK_CLASS);
        $source.find("span").addClass(DEFAULT_CLASS);

        initCondArray.forEach(function(itm){
            // 주의: initColsArray 은 id 로만 이루어진 배열
            $("#src_"+itm).trigger("click");
        });
    };

    var selectAll = function(){
        allCondDatas.forEach(function(itm){
            var $src = $("#src_"+filterXSS(itm[idAttr]));
            if(!$src.find("span").hasClass(CHECK_CLASS)){
                $src.trigger("click");
            }
        });
    };

    var setPcBtnEvent = function(){
        $divRoot.off("click", "button");
        // 1. 버튼 클릭 이벤트
        $divRoot.on("click", "button", function(){
            var btnId = $(this).attr("id");
            switch(btnId){
                case "selectAllBtn": // 초기화
                    selectAll();
                    break;
                case "condsInit": // 초기화
                    initConds();
                    break;
                case "condsSave": // 저장 및 적용
                    saveConds();
                    break;
                default:
                    break;
            }

            return false;
        });
    };

    var setMobileBtnEvent = function(){
        var options = $modalRoot.dialog("options");
        // console.debug(options);
        options.buttons.forEach(function(btn){
            var btnId = btn.id;
            switch(btnId){
                case "selectAllBtn": // 초기화
                    btn.handler = selectAll;
                    break;
                case "condsInit": // 초기화
                    btn.handler = initConds;
                    break;
                case "condsSave": // 저장 및 적용
                    btn.handler = saveConds;
                    break;
                default:
                    break;
            }
            $("#"+btnId).on("click", btn.handler);
        });
    };

    var setEvent = function(){
        if(paragonCmm.isMobile()){
            setMobileBtnEvent();
        }else{
            setPcBtnEvent();
        }

        // 2. source li click 이벤트
        $source.off("click", "div");
        $source.on("click", "div", function(){
            clickSrcCol(this);
            return false;
        });

        $target.off("dblclick", "div");
        $target.on("dblclick", "div", function(){
            dblClickTgCol(this);
            return false;
        });

        $schTxt.off();
        $schTxt.on("keyup",function(e){
            if($(this).val().length == 0){
                //-- 전체 보이기
                $("div", $source).show();
            }else if($(this).val().length > 1){
                searchTxt($(this).val());
            }
        });
    };

    var init = function(){
        initForm();
        loadForm();
        setEvent();
    };

    return {
        init: init
    };
};

$(document).ready(function(){
    "use strict";
    var condsInfo = new CondsInfo();
    condsInfo.init();
});