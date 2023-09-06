/*
 * @(#)paragon.column.js     2022-03-17(017) 오전 8:40
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
 * 컬럼 설정 라이브러리
 * [의존성: paragonCmm.js, paragon.html.js,  js-storage, jquery Easy UI(모달)]
 * Description:
 *     그리드의 컬럼 정보를 설정, 설정된 정보를 로컬저장소에 저장하고 불러온다.
 */
(function (j) {
    "use strict";
    var COLINFO_STORAGE_NAMESPACE = "paragonGridColumns";
    var COLINFO_MODAL_ID = "columnManagerModal";
    var COL_MNG_DIALOG_URL = "/cmm/modal/columnMngModal.include";

    // column 저장소
    var colStorage = Storages.initNamespaceStorage(COLINFO_STORAGE_NAMESPACE);
    var nsLocalColStorage = colStorage.localStorage;

    /**
     * 컬럼정보 저장
     * @param gridId grid id
     * @param cols columns(map)
     */
    var setCols = function (gridId, cols) {
        nsLocalColStorage.set(gridId, cols);
    };

    /**
     * 컬럼정보 얻기
     * @param gridId grid id
     * @returns {string|null} column map
     */
    var getCols = function (gridId) {
        if (nsLocalColStorage && !nsLocalColStorage.isEmpty(gridId)) {
            return nsLocalColStorage.get(gridId);
        } else {
            return null;
        }
    };

    /**
     * 컬럼매니저(모달) 열기
     * @param gridId grid id
     * @param cbFnc callback function
     */
    var openColMng = function (gridId, cbFnc) {

        var options = {
            modalId: COLINFO_MODAL_ID,
            href: paragonCmm.getUrl(COL_MNG_DIALOG_URL),
            title: "Column Info.",
            params: {
                modalId: COLINFO_MODAL_ID,
                gridId: gridId
            },
            cbFnc: cbFnc,
            width: 1152,
            height: "auto",
            resizable: true,
            top: "20px"
        };

        if (paragonCmm.isMobile()) {
            options = $.extend(options, {
                resizable: false,
                width: "80%",
                height: "50%",
                buttons: [{
                    id: "selectAllBtn",
                    text: "<i class=\"fa fa-check\"></i> 전체선택"
                }, {
                    id: "colsInitBtn",
                    text: "<i class=\"fa fa-refresh\"></i> 리셋"
                }, {
                    id: "colsSaveBtn",
                    text: "<i class=\"fa fa-save\"></i> 저장"
                }]
            });
        }

        htmlUtils.openDialog(options);
    };

    /**
     * 컬럼관리 정보
     * @param options
     * @returns {*}
     * @constructor
     */
    var ColumnMngInfo = function (options) {
        var defaultInfo = {
            gridId: null,
            idAttr: "field",
            nameAttr: "title",
            // 초기 컬럼(field)들
            initCols: [],
            // 히든  데이터
            hiddenDatas: [],
            // 모든 컬럼 데이터
            allColDatas: [],
            // 생성이후 컬럼지정으로 인해 변경 되는 값
            viewColDatas: []
        };
        var colMngInfo = $.extend({}, defaultInfo, options);

        if (!Array.isArray(colMngInfo.initCols)) {
            throw new Error("initCols가 배열이 아닙니다.");
        }
        if (!Array.isArray(colMngInfo.hiddenDatas)) {
            throw new Error("hiddenDatas 가 배열이 아닙니다.");
        }
        if (!Array.isArray(colMngInfo.allColDatas)) {
            throw new Error("allColDatas 가 배열이 아닙니다.");
        }
        if (!Array.isArray(colMngInfo.viewColDatas)) {
            throw new Error("viewColDatas 가 배열이 아닙니다.");
        }

        if (colMngInfo.viewColDatas.length === 0) {
            var tempArray = [];
            colMngInfo.initCols.forEach(function (id) {
                colMngInfo.allColDatas.some(function (col) {
                    if (col[colMngInfo.idAttr] === id) {
                        tempArray.push(col);
                    }
                });
            });

            colMngInfo.viewColDatas = colMngInfo.hiddenDatas.concat(tempArray);
        } else {
            var tempArray = [];
            colMngInfo.viewColDatas.forEach(function (vcol) {
                var id = vcol[colMngInfo.idAttr];
                colMngInfo.allColDatas.some(function (col) {
                    if (col[colMngInfo.idAttr] === id) {
                        tempArray.push(col);
                    }
                });
            });

            colMngInfo.viewColDatas = colMngInfo.hiddenDatas.concat(tempArray);
        }

        return colMngInfo;
    };

    /**
     * 컬럼 관리 객체
     *
     * @param options
     * @returns {{getViewColDatas: (function(): []), getNameAttr: (function(): string), getAllColDatas: (function(): []), getGridId: (function(): null|*), getIdAttr: (function(): string|*), getCols: getCols, getInitCols: (function(): []), setCols: setCols, setViewColDatas: setViewColDatas, openColMng: openColMng}}
     * @constructor
     */
    var ColumnMngObj = function (options) {

        var opt = $.extend(getCols(options.gridId), options);
        var colMngInfo = new ColumnMngInfo(opt);
        setCols(options.gridId, colMngInfo);

        /**
         * 표시 컬럼 설정
         * @param viewCols 표시컬럼 colIdAttr 배열
         */
        var setViewColDatas = function (viewCols) {
            var tempArray = [];
            viewCols.forEach(function (id) {
                colMngInfo.allColDatas.some(function (col) {
                    if (col[colMngInfo.idAttr] === id) {
                        tempArray.push(col);
                    }
                });
            });
            colMngInfo.viewColDatas = colMngInfo.hiddenDatas.concat(tempArray);
        };

        return {
            setViewColDatas: setViewColDatas,
            getViewColDatas: function () {
                // 변경된 내용을 다시 불러온다.
                colMngInfo = getCols(colMngInfo.gridId);
                return colMngInfo.viewColDatas;
            },
            getAllColDatas: function () {
                return colMngInfo.allColDatas;
            },
            getInitCols: function () {
                return colMngInfo.initCols;
            },
            getGridId: function () {
                return colMngInfo.gridId;
            },
            getIdAttr: function () {
                return colMngInfo.idAttr;
            },
            getNameAttr: function () {
                return colMngInfo.nameAttr;
            },
            // 컬럼설정
            setCols: function () {
                setCols(colMngInfo.gridId, colMngInfo);
            },
            // 컬럼얻기
            getCols: function () {
                colMngInfo = getCols(colMngInfo.gridId);
            },
            // 컬럼정보 열기(모달)
            openColMng: openColMng
        };
    };

    j.column = (function () {
        return {
            createObj: function (options) {
                return new ColumnMngObj(options);
            }
        };
    })();

})(paragonCmm);