/*
 * @(#)managerStats.js     2023-06-09(009) 오전 9:00
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

$(function () {
    "ues strict";
    "ues strict";
    let ManagerStatusModel = function () {

        const MANAGER_STATS_URL = paragonCmm.getUrl("/support/stats/manager/json");
        const SYSTEM_STATS_URL = paragonCmm.getUrl("/support/stats/system/json");

        let getOpts = function (options, url) {
            return $.extend({
                url: url,
                params: null,
                cbFnc: function (json) {
                },
                async: false,
                errorBack: function () {
                }
            }, options);
        };
        let callAjax = function (options, url) {
            let opts = getOpts(options, url);
            paragonCmm.callAjax(opts.url, opts.params, opts.cbFnc);
        };

        return {
            getManagerStats: function (options) {
                callAjax(options, MANAGER_STATS_URL);
            },
            getSystemStats: function (options) {
                callAjax(options, SYSTEM_STATS_URL);
            }
        };
    };
    let ManagerStatus = function () {
        // ==============================================
        // attributes
        // ----------------------------------------------
        let model = new ManagerStatusModel();
        let $rootLayer = $("#managerStatsRootLayer");
        let $schForm = $("#managerStatsSchFrm0", $rootLayer);
        let $schIssueTpCd = $("select[name=schIssueTpCd]", $schForm);
        let $schIssueDomainCd = $("select[name=schIssueDomainCd]", $schForm);
        let $schIssueStuCd = $("select[name=schIssueStuCd]", $schForm);
        let $grid = $("#managerStatsList1", $rootLayer);
        let $fromDate = $("input[name=schFromReqDte]", $schForm);
        let $toDate = $("input[name=schToReqDte]", $schForm);
        let $managerStats = $("#managerStats");
        let $customerStats = $("#customerStats");
        let $managerStatsBreadcrumb = $("#managerStatsBreadcrumb");
        let $chart1 = $("#chart1");
        let $chart2 = $("#chart2");
        let chart1;
        let chart2;
        let mgrStats;
        let systemStats;

        let managerStatsData;
        let systemStatsData;
        let managerStatsParams;
        let systemStatsParams;
        let systemDtlStatsParams;

        // ==============================================
        // private functions
        // ----------------------------------------------

        /**
         * 콤보박스(select) 설정
         * @param parentCd parent code
         * @param comboObj 콤보 Object
         * @param initTxt 초기값 명칭
         * @param valNm cd, cdAbb
         */
        let setComboBox = function (parentCd, comboObj, initTxt, valNm) {

            valNm = (typeof valNm === "undefined") ? "cdAbb" : valNm;

            let initNm = (typeof initTxt === "undefined") ? paragonCmm.getLang("L.CBO_SELECT") : initTxt;

            htmlUtils.getCodeSelectOpt({
                targetId: comboObj,
                parentCd: parentCd,
                initdata: "|" + initNm,
                valNm: valNm,
                txtNm: "langCd"
            });
        };

        let getDefDate = function (addDay) {
            var today = new Date();

            var defDate = new Date(today.setDate(today.getDate() + addDay));

            var month = defDate.getMonth() + 1;
            var strToday = defDate.getFullYear();
            strToday += "-" + ((month < 10) ? "0" + month : month);
            var date = today.getDate();
            strToday += "-" + ((date < 10) ? "0" + date : date);
            return strToday;
        };

        let dateboxValidator = function (date) {
            if (date >= paragonCmm.getToday()) {
                return false;
            } else {
                return true;
            }
        };

        let setDefaultDte = function () {

            // 요청일
            $fromDate.datebox({validType: "validDate", prompt: "Search Request Date"});// easyui
            $toDate.datebox({validType: "validDate", prompt: "Search Request Date"});// easyui
            // default date
            $fromDate.datebox("setValue", String(getDefDate(-90)));
            $toDate.datebox("setValue", String(getDefDate(-1)));
        };

        // 표1(담당자별 현황) 그리기: 담당자, 진행유형(건수)
        // 표2(담당자-고객별 현황) 그리기: 고객, 진행유형(건수)
        // 차트1: 담당자, 진행유형(건수)
        // 차트2: 기간별, 고객, 건수

        let initSchObj = function () {

            console.debug("init sch obj....")

            // 1. 요청구분
            setComboBox("REQ_GUBUN", $schIssueTpCd, "Select Issue Type");
            // 2. 요청분야
            setComboBox("REQ_BUNYA", $schIssueDomainCd, "Select Issue Domain");
            // 3. 진행상태
            setComboBox("REQ_STATUS", $schIssueStuCd, "Select Issue Status");
            $schIssueStuCd.find("option[value='10']").remove();

            setDefaultDte();
        };

        const destroyChart = function(chartObj){
            if (chartObj instanceof Chart) {
                chartObj.destroy();
            }
        };

        let makeChartConfig = function(options, chartObj){

            let opts = $.extend({
                title: '',
                type: 1,
                labels: [],
                rcvs: [],
                rslts: [],
                cfms: []
            }, options);

            let data = {
                labels: opts.labels,
                datasets: [
                    {
                        label: '처리중',
                        data: opts.rcvs,
                        backgroundColor: "#86ace3",
                    },
                    {
                        label: '검수대기',
                        data: opts.rslts,
                        backgroundColor: "#fce094",
                    },
                    {
                        label: '완료',
                        data: opts.cfms,
                        backgroundColor: "#b0de96",
                    },
                ]
            };

            let config = {
                type: 'bar',
                data: data,
                options: {
                    plugins: {
                        title: {
                            display: true,
                            text: opts.title
                        },
                    },
                    responsive: true
                }
            };

            if(opts.type === 1){
                config = {
                    type: 'bar',
                    data: data,
                    options: {
                        indexAxis: 'y',
                        plugins: {
                            title: {
                                display: true,
                                text: opts.title
                            },
                        },
                        responsive: true,
                        scales: {
                            x: {
                                stacked: true,
                            },
                            y: {
                                stacked: true
                            }
                        }
                    }
                };
            }

            return config;
        };

        let makeMgrStatsRow = function (data) {
            let bodyHtml = '';
            let footHtml = '';
            if (Array.isArray(data)) {

                let rcvCnt, rsltCnt, cfmYCnt, cfmNCnt, cfmCnt, rowTtl, rcvTtl, rsltTtl, cfmYTtl,
                    cfmNTtl, cfmTtl, ttl;

                rcvTtl = new Big(0);
                rsltTtl = new Big(0);
                cfmYTtl = new Big(0);
                cfmNTtl = new Big(0);
                cfmTtl = new Big(0);
                ttl = new Big(0);
                data.forEach(function (itm) {

                    rcvCnt = filterXSS(itm.rcvCnt);
                    rsltCnt = filterXSS(itm.rsltCnt);
                    cfmYCnt = filterXSS(itm.cfmYCnt);
                    cfmNCnt = filterXSS(itm.cfmNCnt);
                    cfmCnt = filterXSS(itm.cfmCnt);
                    rowTtl = filterXSS(itm.ttl);

                    rcvCnt = (rcvCnt === "") ? 0 : rcvCnt;
                    rsltCnt = (rsltCnt === "") ? 0 : rsltCnt;
                    cfmYCnt = (cfmYCnt === "") ? 0 : cfmYCnt;
                    cfmNCnt = (cfmNCnt === "") ? 0 : cfmNCnt;
                    cfmCnt = (cfmCnt === "") ? 0 : cfmCnt;
                    rowTtl = (rowTtl === "") ? 0 : rowTtl;

                    rcvTtl = rcvTtl.plus(rcvCnt);
                    rsltTtl = rsltTtl.plus(rsltCnt);
                    cfmYTtl = cfmYTtl.plus(cfmYCnt);
                    cfmNTtl = cfmNTtl.plus(cfmNCnt);
                    cfmTtl = cfmTtl.plus(cfmCnt);
                    ttl = ttl.plus(rowTtl);

                    bodyHtml += '<tr data-options="' + filterXSS(itm.rcvId) + '">';
                    bodyHtml += '<th class="text-center" scope="row" data-options="mgr,' + filterXSS(itm.rcvId) + '">' + filterXSS(itm.mgrNm) + '</th>';
                    bodyHtml += '<td class="text-center" data-options="rcv,' + filterXSS(itm.rcvId) + '">' + rcvCnt + '</td>';
                    bodyHtml += '<td class="text-center" data-options="cfm,' + filterXSS(itm.rcvId) + '">' + rsltCnt + '</td>';
                    bodyHtml += '<td class="text-center" data-options="fin,' + filterXSS(itm.rcvId) + '">' + cfmYCnt + '</td>';
                    bodyHtml += '<td class="text-center" data-options="fin,' + filterXSS(itm.rcvId) + '">' + cfmNCnt + '</td>';
                    bodyHtml += '<td class="text-center" data-options="fin,' + filterXSS(itm.rcvId) + '">' + cfmCnt + '</td>';
                    bodyHtml += '<td class="text-center" data-options="ttl,' + filterXSS(itm.rcvId) + '">' + rowTtl + '</td>';
                    bodyHtml += '</tr>';
                });

                footHtml += '<tr>';
                footHtml += '<th class="text-center" scope="row">TOTAL</th>';
                footHtml += '<th class="text-center">' + rcvTtl + '</th>';
                footHtml += '<th class="text-center">' + rsltTtl + '</th>';
                footHtml += '<th class="text-center">' + cfmYTtl + '</th>';
                footHtml += '<th class="text-center">' + cfmNTtl + '</th>';
                footHtml += '<th class="text-center">' + cfmTtl + '</th>';
                footHtml += '<th class="text-center">' + ttl + '</th>';
                footHtml += '</tr>';

                $managerStats.find("tfoot").html(footHtml);
            }
            return bodyHtml;
        };

        let makeMgrDtlStatsRow = function (data) {
            let bodyHtml = '';
            let footHtml = '';
            if (Array.isArray(data)) {

                let rcvCnt, rsltCnt, cfmYCnt, cfmNCnt, cfmCnt, rowTtl, rcvTtl, rsltTtl, cfmYTtl,
                    cfmNTtl, cfmTtl, ttl;

                rcvTtl = new Big(0);
                rsltTtl = new Big(0);
                cfmYTtl = new Big(0);
                cfmNTtl = new Big(0);
                cfmTtl = new Big(0);
                ttl = new Big(0);
                data.forEach(function (itm) {

                    rcvCnt = filterXSS(itm.rcvCnt);
                    rsltCnt = filterXSS(itm.rsltCnt);
                    cfmYCnt = filterXSS(itm.cfmYCnt);
                    cfmNCnt = filterXSS(itm.cfmNCnt);
                    cfmCnt = filterXSS(itm.cfmCnt);
                    rowTtl = filterXSS(itm.ttl);

                    rcvCnt = (rcvCnt === "") ? 0 : rcvCnt;
                    rsltCnt = (rsltCnt === "") ? 0 : rsltCnt;
                    cfmYCnt = (cfmYCnt === "") ? 0 : cfmYCnt;
                    cfmNCnt = (cfmNCnt === "") ? 0 : cfmNCnt;
                    cfmCnt = (cfmCnt === "") ? 0 : cfmCnt;
                    rowTtl = (rowTtl === "") ? 0 : rowTtl;

                    rcvTtl = rcvTtl.plus(rcvCnt);
                    rsltTtl = rsltTtl.plus(rsltCnt);
                    cfmYTtl = cfmYTtl.plus(cfmYCnt);
                    cfmNTtl = cfmNTtl.plus(cfmNCnt);
                    cfmTtl = cfmTtl.plus(cfmCnt);
                    ttl = ttl.plus(rowTtl);

                    bodyHtml += '<tr data-options="' + filterXSS(itm.systemId) + '">';
                    bodyHtml += '<th class="text-center" scope="row" data-options="mgr,' + filterXSS(itm.systemId) + '">' + filterXSS(itm.orgNm) + '</th>';
                    bodyHtml += '<th class="text-center" scope="row" data-options="mgr,' + filterXSS(itm.systemId) + '">' + filterXSS(itm.systemName) + '</th>';
                    bodyHtml += '<td class="text-center" data-options="rcv,' + filterXSS(itm.systemId) + '">' + rcvCnt + '</td>';
                    bodyHtml += '<td class="text-center" data-options="cfm,' + filterXSS(itm.systemId) + '">' + rsltCnt + '</td>';
                    bodyHtml += '<td class="text-center" data-options="fin,' + filterXSS(itm.systemId) + '">' + cfmYCnt + '</td>';
                    bodyHtml += '<td class="text-center" data-options="fin,' + filterXSS(itm.systemId) + '">' + cfmNCnt + '</td>';
                    bodyHtml += '<td class="text-center" data-options="fin,' + filterXSS(itm.systemId) + '">' + cfmCnt + '</td>';
                    bodyHtml += '<td class="text-center" data-options="ttl,' + filterXSS(itm.systemId) + '">' + rowTtl + '</td>';
                    bodyHtml += '</tr>';
                });

                footHtml += '<tr>';
                footHtml += '<th class="text-center" colspan="2" scope="row">TOTAL</th>';
                footHtml += '<th class="text-center">' + rcvTtl + '</th>';
                footHtml += '<th class="text-center">' + rsltTtl + '</th>';
                footHtml += '<th class="text-center">' + cfmYTtl + '</th>';
                footHtml += '<th class="text-center">' + cfmNTtl + '</th>';
                footHtml += '<th class="text-center">' + cfmTtl + '</th>';
                footHtml += '<th class="text-center">' + ttl + '</th>';
                footHtml += '</tr>';

                $customerStats.find("tfoot").html(footHtml);
            }
            return bodyHtml;
        };

        let setBreadcrumb = function (mgrId) {
            let breadcrumbHtml = '';
            mgrStats = null;

            managerStatsData.some(function (itm) {
                if (itm.rcvId === mgrId) {
                    mgrStats = itm;
                }
            });

            if (paragonCmm.isNotEmpty(mgrStats)) {
                breadcrumbHtml += '<li class="breadcrumb-item mngStatsHome"><a href="#">담당자현황</a></li>'
                breadcrumbHtml += '<li class="breadcrumb-item active">' + filterXSS(mgrStats.mgrNm) + '</li>';
            }
            $managerStatsBreadcrumb.html(breadcrumbHtml);

            $(".mngStatsHome").off("click");
            $(".mngStatsHome").on("click", function () {
                setMgrStats();
            });
        };

        let makeSysDtlStatsChart = function (datas) {
            let labels = [];
            let rcvs = [];
            let rslts = [];
            let cfms = [];
            datas.forEach(function (itm) {

                if (paragonCmm.isNotEmpty(systemDtlStatsParams.unitDay)) {
                    labels.push(filterXSS(itm.issueYear) + "-" + filterXSS(itm.issueMm) + "-" + filterXSS(itm.issueDd));
                } else if (paragonCmm.isNotEmpty(systemDtlStatsParams.unitMonth)) {
                    labels.push(filterXSS(itm.issueYear) + "-" + filterXSS(itm.issueMm));
                } else {
                    labels.push(filterXSS(itm.issueYear));
                }

                rcvs.push(itm.rcvCnt);
                rslts.push(itm.rsltCnt);
                cfms.push(itm.cfmCnt);
            });

            destroyChart(chart2);
            chart2 = new Chart($chart2, makeChartConfig({
                title: filterXSS(systemStats.orgNm) + " " + filterXSS(systemStats.systemName) + " 처리현황",
                type: 2,
                labels: labels,
                rcvs: rcvs,
                rslts: rslts,
                cfms: cfms
            }));
        };

        let drillDownSystem = function (options) {

            let opts = {
                systemId: null,
                unitYear: "default",
                unitMonth: "default",
                unitDay: null,
                issueType: null
            };
            systemDtlStatsParams = $.extend({}, systemStatsParams, opts, options);

            console.debug(systemDtlStatsParams);

            // set dtlTbl
            model.getSystemStats({
                params: systemDtlStatsParams,
                cbFnc: function (data) {

                    let datas;
                    if (data.errYn === "E") {
                        //-- 오류처리
                        $.messager.alert("Error", data.msg, "error");
                    } else {
                        datas = [].concat(data.data);
                    }

                    makeSysDtlStatsChart(datas);
                }
            });
        };

        let makeMgrDtlStatsTbl = function (data) {
            let bodyHtml = "";
            if (paragonCmm.isEmpty(data)) {
                bodyHtml = "<tr><td colspan='4' class='text-center'>NO DATA</td>";
            } else {
                bodyHtml = makeMgrDtlStatsRow(data);
            }

            $managerStats.hide();
            $customerStats.show();
            $customerStats.find("tbody").html(bodyHtml);
            $customerStats.off("click", "tr");
            $customerStats.on("click", "tr", function () {
                let systemId = $(this).data("options");
                console.debug(systemId);

                systemStatsData.some(function (itm){
                    if(systemId === itm.systemId){
                        systemStats = itm;
                    }
                });

                drillDownSystem({systemId: systemId});
            });
        };


        let makeMgrDtlStatsChart = function (datas) {
            let labels = [];
            let rcvs = [];
            let rslts = [];
            let cfms = [];
            datas.forEach(function (itm) {
                labels.push(filterXSS(itm.orgNm) + ' ' + filterXSS(itm.systemName));
                rcvs.push(itm.rcvCnt);
                rslts.push(itm.rsltCnt);
                cfms.push(itm.cfmCnt);
            });

            destroyChart(chart1);
            chart1 = new Chart($chart1, makeChartConfig({
                title: filterXSS(mgrStats.mgrNm) + " 담당 시스템별 처리현황",
                labels: labels,
                rcvs: rcvs,
                rslts: rslts,
                cfms: cfms
            }));
        };

        let makeMgrDtlStats = function (datas) {
            makeMgrDtlStatsTbl(datas);
            makeMgrDtlStatsChart(datas);
            console.debug(datas);
        };

        let drillDownManager = function (mgrId) {
            // set breadcrumb
            setBreadcrumb(mgrId);
            systemStatsParams = $.extend({}, managerStatsParams, {rcvId: mgrId});

            // set dtlTbl
            model.getSystemStats({
                params: systemStatsParams,
                cbFnc: function (data) {

                    if (data.errYn === "E") {
                        //-- 오류처리
                        $.messager.alert("Error", data.msg, "error");
                    } else {
                        systemStatsData = [].concat(data.data);
                    }

                    makeMgrDtlStats(systemStatsData);
                }
            });
        };

        let makeMgrStatsTbl = function (data) {
            let bodyHtml = "";
            if (paragonCmm.isEmpty(data)) {
                bodyHtml = "<tr><td colspan='7' class='text-center'>NO DATA</td>";
            } else {
                bodyHtml = makeMgrStatsRow(data);
            }

            $managerStats.show();
            $customerStats.hide();

            $managerStats.find("tbody").html(bodyHtml);
            $managerStats.off("click", "tr");
            $managerStats.on("click", "tr", function () {
                let mgrId = $(this).data("options");
                console.debug(mgrId);
                drillDownManager(mgrId);
            });
        };

        let makeMgrStatsChart = function () {

            let labels = [];
            let rcvs = [];
            let rslts = [];
            let cfms = [];
            managerStatsData.forEach(function (itm) {
                labels.push(itm.mgrNm);
                rcvs.push(itm.rcvCnt);
                rslts.push(itm.rsltCnt);
                cfms.push(itm.cfmCnt);
            });

            destroyChart(chart1);
            chart1 = new Chart($chart1, makeChartConfig({
                title: "담당자별 처리현황",
                labels: labels,
                rcvs: rcvs,
                rslts: rslts,
                cfms: cfms
            }));
        };

        let setMgrStats = function () {
            setBreadcrumb();
            makeMgrStatsTbl(managerStatsData);
            makeMgrStatsChart();
            destroyChart(chart2);
        };

        let getQueryParams = function () {
            return $schForm.serializeObject();
        };

        let search = function () {
            managerStatsParams = getQueryParams();
            console.debug(managerStatsParams);
            model.getManagerStats({
                params: managerStatsParams,
                cbFnc: function (data) {

                    if (data.errYn === "E") {
                        //-- 오류처리
                        $.messager.alert("Error", data.msg, "error");
                    } else {
                        managerStatsData = [].concat(data.data);
                    }
                    setMgrStats();
                }
            });
        };

        let setFormEvent = function () {
            $schForm.on("submit", function () {
                search();
                return false;
            });

            $schForm.on("keyup", "input", function (e) {
                if (e.keyCode === 13) {
                    search();
                }
                return false;
            });
        };

        let setBtnEvent = function () {
            $rootLayer.off("click", "button");
            $rootLayer.on("click", "button", function () {
                let btnId = $(this).attr("id");
                switch (btnId) {
                    case "managerStatsResetSmBtn":
                    case "managerStatsResetBtn1":
                        $schForm.clearForm();
                        setDefaultDte();
                        break;
                    case "managerStatsSearchSmBtn":
                    case "managerStatsSearchBtn1":
                        search();
                        break;
                    // case "managerStatsExcelBtn1":
                    //     expExcel();
                    //     break;
                    default:
                        console.warn("not support btnId:" + btnId);
                        return;
                }

                return false; // 이벤트 확산금지
            });
        };

        let initForm = function () {
            initSchObj();
        };

        let loadForm = function () {
        };

        let setEvent = function () {
            setFormEvent();
            setBtnEvent();
        };

        // ==============================================
        // public functions
        // ----------------------------------------------
        let init = function () {
            initForm();
            loadForm();
            setEvent();
        };

        return {
            init: init
        };
    };

    let status = new ManagerStatus();
    status.init();
});