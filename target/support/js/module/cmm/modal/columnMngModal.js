/*
 * @(#)columnMngModal.js     2023-01-03(003) 오후 3:03:16
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

/**
 * 컬럼 설정
 * @returns {{init: init}}
 * @constructor
 */
var ColsInfo = function(){
    "use strict";

    //-- Root ID
    var $divRoot = $("#colsInfo");
    // 오프너 데이터
    var openerData = $divRoot.data("opener-data");
    var $modalRoot = $("#"+openerData.modalId);

    var $schTxt      = $("input[name='schCols']", $divRoot);

    var $source = $("#source");
    var $target = $("#target");
    var colData = paragonCmm.column.createObj({gridId: openerData.gridId});
    var idAttr = colData.getIdAttr();
    var nameAttr = colData.getNameAttr();
    var allColData = colData.getAllColDatas();
    var initColsArray = colData.getInitCols();
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

    var saveCols = function(){
        var viewCols = [];
        $target.find("div").each(function(idx, itm){
            var id = $(itm).attr("id");
            id = id.replace("tgt_", "");
            viewCols.push(id);
        });
        colData.setViewColDatas(viewCols);
        colData.setCols();
        closeWindow();
    };

    var searchTxt = function(schTxt){
        $("div", $source).hide();
        $("div[data-text*='"+schTxt+"']", $source).show();
    }

    var initForm = function(){
        if(paragonCmm.isMobile()){
            $("#btnLayer", $divRoot).hide();
        }

        // 1. source 설정
        $source.empty();

        var allColHtml = "";
        allColData.forEach(function(itm,idx){
            if(itm['checkbox'] === true){
                // 체크박스 컬럼지정에 보이지 않도록 처리
                var colNm = filterXSS(itm[nameAttr]);
                allColHtml += '<div style="display:none;" class="col-sm-2 pb-1" id="src_'+filterXSS(itm[idAttr])+'" data-text="'+colNm+'">';
                allColHtml += '<span class="form-control text-truncate text-xs pt-2 text-center text-truncate font-weight-bold"';
                allColHtml += ' title="'+colNm+'">';
                allColHtml += colNm;
                allColHtml += '</span>';
                allColHtml += '</div>';
            }else if(itm['hidden'] === true){
                // hidden 속성 컬럼지정에 보이지 않도록 처리
                var colNm = filterXSS(itm[nameAttr]);
                allColHtml += '<div style="display:none;" class="col-sm-2 pb-1" id="src_'+filterXSS(itm[idAttr])+'" data-text="'+colNm+'">';
                allColHtml += '<span class="form-control text-truncate text-xs pt-2 text-center text-truncate font-weight-bold"';
                allColHtml += ' title="'+colNm+'">';
                allColHtml += colNm;
                allColHtml += '</span>';
                allColHtml += '</div>';
            }else{
                var colNm = filterXSS(itm[nameAttr]);
                allColHtml += '<div class="col-sm-2 pb-1" id="src_'+filterXSS(itm[idAttr])+'" data-text="'+colNm+'">';
                allColHtml += '<span class="form-control text-truncate text-xs pt-2 text-center text-truncate font-weight-bold"';
                allColHtml += ' title="'+colNm+'">';
                allColHtml += colNm;
                allColHtml += '</span>';
                allColHtml += '</div>';
            }
        });
        $source.html(allColHtml);
    };

    var loadForm = function(){
        $target.empty();
        var viewColDatas = colData.getViewColDatas();
        var viewColHtml = "";
        viewColDatas.forEach(function(itm){
            var colNm = filterXSS(itm[nameAttr]);
            var colId = filterXSS(itm[idAttr]);
            if(itm['checkbox'] === true){
                // 체크박스 컬럼지정에 보이지 않도록 처리
                viewColHtml += '<div style="display:none;" class="col-sm-2 pb-1" id="tgt_'+filterXSS(itm[idAttr])+'" data-text="'+colNm+'">';
                viewColHtml += '<span class="form-control text-truncate text-xs pt-2 text-center text-truncate bg-lightblue font-weight-bold"';
                viewColHtml += ' title="'+colNm+'">';
                viewColHtml += colNm;
                viewColHtml += '</span>';
                viewColHtml += '</div>';
            }else{
                viewColHtml += '<div class="col-sm-2 pb-1" id="tgt_'+filterXSS(itm[idAttr])+'" data-text="'+colNm+'">';
                viewColHtml += '<span class="form-control text-truncate text-xs pt-2 text-center text-truncate bg-lightblue font-weight-bold"';
                viewColHtml += ' title="'+colNm+'">';
                viewColHtml += colNm;
                viewColHtml += '</span>';
                viewColHtml += '</div>';
            }
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

    var initCols = function(){
        $target.empty();
        $source.find("span").removeClass(CHECK_CLASS);
        $source.find("span").addClass(DEFAULT_CLASS);
        
        initColsArray.forEach(function(itm){
            // 주의: initColsArray 은 id 로만 이루어진 배열
            $("#src_"+itm).trigger("click");
        });
    };

    var selectAll = function(){
        allColData.forEach(function(itm){
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
                case "colsInitBtn": // 초기화
                    initCols();
                    break;
                case "colsSaveBtn": // 저장 및 적용
                    saveCols();
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
                case "colsInitBtn": // 초기화
                    btn.handler = initCols;
                    break;
                case "colsSaveBtn": // 저장 및 적용
                    btn.handler = saveCols;
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
    }

    var init = function(){
        initForm();
        loadForm();
        setEvent();
    }
    return {
        init : init
    }
};
$(document).ready(function(){
    "use strict";
    var colsInfo = new ColsInfo();
    colsInfo.init();
});