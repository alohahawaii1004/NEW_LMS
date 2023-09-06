/*
 * @(#)customerStats.js     2023-06-12(012) 오후 2:54
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
$(function(){
    "use strict";

    const CustomerStatsModel = function(){
        const SYSTEM_STATS_URL = paragonCmm.getUrl("/support/stats/system/json");
        const SYSTEM_TP_STATS_URL = paragonCmm.getUrl("/support/stats/issueTp/json");

        const getOpts = function (options, url) {
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
        const callAjax = function (options, url) {
            let opts = getOpts(options, url);
            paragonCmm.callAjax(opts.url, opts.params, opts.cbFnc);
        };

        return {
            getIssueTpStats: function (options) {
                callAjax(options, SYSTEM_TP_STATS_URL);
            },
            getCustomerStats: function (options) {
                callAjax(options, SYSTEM_STATS_URL);
            },
            getSystemStats: function (options) {
                callAjax(options, SYSTEM_STATS_URL);
            }
        };
    };

    const CustomerStats = function(){
        // ==============================================
        // attributes
        // ----------------------------------------------
        const model = new CustomerStatsModel();
        const $rootLayer = $("#customerStatsRootLayer");
        const $schForm = $("#customerStatsSchFrm0", $rootLayer);
        const $schIssueTpCd = $("select[name=schIssueTpCd]", $schForm);
        const $schIssueDomainCd = $("select[name=schIssueDomainCd]", $schForm);
        const $schIssueStuCd = $("select[name=schIssueStuCd]", $schForm);
        const $schPrdTpCd = $("select[name=schPrdTpCd]", $schForm);
        const $grid = $("#customerStatsList1", $rootLayer);
        const $fromDate = $("input[name=schFromReqDte]", $schForm);
        const $toDate = $("input[name=schToReqDte]", $schForm);
        const $customerStats = $("#customerStats");
        const $customerStatsChart = $("#customerStatsChart");
        const $customerDtlStatsChart = $("#customerDtlStatsChart");
        const $customerStatsBreadcrumb = $("#customerStatsBreadcrumb");
        const $chart1 = $("#chart1");
        const $chart2 = $("#chart2");
        const $chart3 = $("#chart3");
        const $chart4 = $("#chart4");
        const $chart5 = $("#chart5");
        let chart1;
        let chart2;
        let chart3;
        let chart4;
        let chart5;
        let mgrStats;
        let systemStats;

        let customerStatsData;
        let systemStatsData;

        let customerStatsParams;
        let chart2Params;
        let chart3Params;
        let chart4Params;
        let chart5Params;
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
        const setComboBox = function (parentCd, comboObj, initTxt, valNm) {

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

        const getDefDate = function (addDay) {
            let today = new Date();

            let defDate = new Date(today.setDate(today.getDate() + addDay));

            let month = defDate.getMonth() + 1;
            let strToday = defDate.getFullYear();
            strToday += "-" + ((month < 10) ? "0" + month : month);
            let date = today.getDate();
            strToday += "-" + ((date < 10) ? "0" + date : date);
            return strToday;
        };

        const dateboxValidator = function (date) {
            if (date >= paragonCmm.getToday()) {
                return false;
            } else {
                return true;
            }
        };

        const setDefaultDte = function () {

            // 요청일
            $fromDate.datebox({validType: "validDate", prompt: "Search Request Date"});// easyui
            $toDate.datebox({validType: "validDate", prompt: "Search Request Date"});// easyui
            // default date
            $fromDate.datebox("setValue", String(getDefDate(-90)));
            $toDate.datebox("setValue", String(getDefDate(-1)));
        };
        const initSchObj = function(){
            // 1. 요청구분
            setComboBox("REQ_GUBUN", $schIssueTpCd, "Select Issue Type");
            // 2. 요청분야
            setComboBox("REQ_BUNYA", $schIssueDomainCd, "Select Issue Domain");
            // 3. 진행상태
            setComboBox("REQ_STATUS", $schIssueStuCd, "Select Issue Status");
            // 4. 시스템 유형
            setComboBox("SD_SYSTEM_GBN", $schPrdTpCd, "Select System type", "cd");
            $schIssueStuCd.find("option[value='10']").remove();

            setDefaultDte();
        };

        const getQueryParams = function () {
            return $schForm.serializeObject();
        };

        const setBreadcrumb = function (systemId) {
            let breadcrumbHtml = '';
            systemStats = null;

            customerStatsData.some(function (itm) {
                if (itm.systemId === systemId) {
                    systemStats = itm;
                }
            });

            console.debug(systemId)
            console.debug(systemStats)

            if (paragonCmm.isNotEmpty(systemStats)) {
                breadcrumbHtml += '<li class="breadcrumb-item mngStatsHome"><a href="#">고객사 현황</a></li>'
                breadcrumbHtml += '<li class="breadcrumb-item active">'+ filterXSS(systemStats.orgNm) + " : " + filterXSS(systemStats.systemName) + '</li>';
            }
            $customerStatsBreadcrumb.html(breadcrumbHtml);

            $(".mngStatsHome").off("click");
            $(".mngStatsHome").on("click", function () {
                setSystemStats();
            });
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
                reqs: [],
                rcvs: [],
                rslts: [],
                cfms: [],
                ttls: []
            }, options);

            let data = {
                labels: opts.labels,
                datasets: [
                    {
                        label: '요청',
                        data: opts.reqs,
                        backgroundColor: "#fd7e14", //"#17a2b8",
                    },
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

            switch (opts.type){
                case 2:
                    data = {
                        labels: ['요청','처리중','검수대기','완료'],
                        datasets: [
                            {
                                label: opts.labels,
                                data: [opts.reqs, opts.rcvs, opts.rslts, opts.cfms],
                                backgroundColor: ["#fd7e14", "#86ace3", "#fce094", "#b0de96"]
                            }
                        ]
                    };
                    break;
                case 3:
                    data = {
                        labels: opts.labels,
                        datasets: [
                            {
                                label: opts.title,
                                data: opts.ttls
                            }
                        ]
                    };
                    break;
                case 5:
                    data = {
                        labels: opts.labels,
                        datasets: opts.datasets
                    };
                    break;
                default:
                    break;
            }

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

            switch (opts.type){
                case 1:
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
                    break;
                case 2:
                case 3:
                    config = {
                        type: 'pie',
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
                    break;
                default:
                    break;
            }

            console.debug("Chart Config.....");
            console.debug(config);

            return config;
        };

        const makeStatsRow = function (data, tblObj) {
            let bodyHtml = '';
            let footHtml = '';
            if (Array.isArray(data)) {

                let reqCnt, rcvCnt, rsltCnt, cfmYCnt, cfmNCnt, cfmCnt, rowTtl, reqTtl, rcvTtl, rsltTtl, cfmYTtl,
                    cfmNTtl, cfmTtl, ttl, rejectClass;

                reqTtl = new Big(0);
                rcvTtl = new Big(0);
                rsltTtl = new Big(0);
                cfmYTtl = new Big(0);
                cfmNTtl = new Big(0);
                cfmTtl = new Big(0);
                ttl = new Big(0);
                data.forEach(function (itm) {

                    rejectClass = "";

                    reqCnt = filterXSS(itm.reqCnt);
                    rcvCnt = filterXSS(itm.rcvCnt);
                    rsltCnt = filterXSS(itm.rsltCnt);
                    cfmYCnt = filterXSS(itm.cfmYCnt);
                    cfmNCnt = filterXSS(itm.cfmNCnt);
                    cfmCnt = filterXSS(itm.cfmCnt);
                    rowTtl = filterXSS(itm.ttl);

                    reqCnt = (reqCnt === "") ? 0 : reqCnt;
                    rcvCnt = (rcvCnt === "") ? 0 : rcvCnt;
                    rsltCnt = (rsltCnt === "") ? 0 : rsltCnt;
                    cfmYCnt = (cfmYCnt === "") ? 0 : cfmYCnt;
                    cfmNCnt = (cfmNCnt === "") ? 0 : cfmNCnt;
                    cfmCnt = (cfmCnt === "") ? 0 : cfmCnt;
                    rowTtl = (rowTtl === "") ? 0 : rowTtl;

                    if(cfmNCnt !== 0){
                        rejectClass = "text-danger";
                    }

                    reqTtl = reqTtl.plus(reqCnt);
                    rcvTtl = rcvTtl.plus(rcvCnt);
                    rsltTtl = rsltTtl.plus(rsltCnt);
                    cfmYTtl = cfmYTtl.plus(cfmYCnt);
                    cfmNTtl = cfmNTtl.plus(cfmNCnt);
                    cfmTtl = cfmTtl.plus(cfmCnt);
                    ttl = ttl.plus(rowTtl);

                    bodyHtml += '<tr data-options="' + filterXSS(itm.systemId) + '">';
                    bodyHtml += '<th class="text-center" scope="row" data-options="mgr,' + filterXSS(itm.systemId) + '">' + filterXSS(itm.orgNm) + '</th>';
                    bodyHtml += '<th class="text-center" scope="row" data-options="mgr,' + filterXSS(itm.systemId) + '">' + filterXSS(itm.systemName) + '</th>';
                    bodyHtml += '<td class="text-center" data-options="rcv,' + filterXSS(itm.systemId) + '">' + reqCnt + '</td>';
                    bodyHtml += '<td class="text-center" data-options="rcv,' + filterXSS(itm.systemId) + '">' + rcvCnt + '</td>';
                    bodyHtml += '<td class="text-center" data-options="cfm,' + filterXSS(itm.systemId) + '">' + rsltCnt + '</td>';
                    bodyHtml += '<td class="text-center" data-options="fin,' + filterXSS(itm.systemId) + '">' + cfmYCnt + '</td>';
                    bodyHtml += '<td class="text-center '+rejectClass+'" data-options="fin,' + filterXSS(itm.systemId) + '">' + cfmNCnt + '</td>';
                    bodyHtml += '<td class="text-center" data-options="fin,' + filterXSS(itm.systemId) + '">' + cfmCnt + '</td>';
                    bodyHtml += '<td class="text-center" data-options="ttl,' + filterXSS(itm.systemId) + '">' + rowTtl + '</td>';
                    bodyHtml += '</tr>';
                });

                footHtml += '<tr>';
                footHtml += '<th class="text-center" colspan="2" scope="row">TOTAL</th>';
                footHtml += '<th class="text-center">' + reqTtl + '</th>';
                footHtml += '<th class="text-center">' + rcvTtl + '</th>';
                footHtml += '<th class="text-center">' + rsltTtl + '</th>';
                footHtml += '<th class="text-center">' + cfmYTtl + '</th>';
                footHtml += '<th class="text-center">' + cfmNTtl + '</th>';
                footHtml += '<th class="text-center">' + cfmTtl + '</th>';
                footHtml += '<th class="text-center">' + ttl + '</th>';
                footHtml += '</tr>';

                tblObj.find("tfoot").html(footHtml);
            }
            return bodyHtml;
        };

        const getSysStatsRow =function(systemId){
            let row = [];

            if(Array.isArray(customerStatsData)){
                customerStatsData.some(function(itm){
                    if(itm.systemId === systemId){
                        row.push(itm);
                    }
                });
            }

            return row;
        };

        const makeStuPieChart = function(row){
            let labels = [];
            let reqs = [];
            let rcvs = [];
            let rslts = [];
            let cfms = [];
            row.forEach(function (itm) {
                labels.push(filterXSS(itm.orgNm) + ' ' + filterXSS(itm.systemName));
                reqs.push(itm.reqCnt);
                rcvs.push(itm.rcvCnt);
                rslts.push(itm.rsltCnt);
                cfms.push(itm.cfmCnt);
            });

            destroyChart(chart2);
            chart2 = new Chart($chart2, makeChartConfig({
                type: 2,
                title: filterXSS(row[0].orgNm) + ' ' + filterXSS(row[0].systemName)+ " 현황",
                labels: labels,
                reqs: reqs,
                rcvs: rcvs,
                rslts: rslts,
                cfms: cfms
            }));
        };

        const makeStuDailyChart = function(datas){
            let labels = [];
            let reqs = [];
            let rcvs = [];
            let rslts = [];
            let cfms = [];
            datas.forEach(function (itm) {
                if (paragonCmm.isNotEmpty(chart3Params.unitDay)) {
                    labels.push(filterXSS(itm.issueYear) + "-" + filterXSS(itm.issueMm) + "-" + filterXSS(itm.issueDd));
                } else if (paragonCmm.isNotEmpty(chart3Params.unitMonth)) {
                    labels.push(filterXSS(itm.issueYear) + "-" + filterXSS(itm.issueMm));
                } else {
                    labels.push(filterXSS(itm.issueYear));
                }
                reqs.push(itm.reqCnt);
                rcvs.push(itm.rcvCnt);
                rslts.push(itm.rsltCnt);
                cfms.push(itm.cfmCnt);
            });
            destroyChart(chart3);
            chart3 = new Chart($chart3, makeChartConfig({
                type: 0,
                title: filterXSS(datas[0].orgNm) + ' ' + filterXSS(datas[0].systemName)+ " 현황",
                labels: labels,
                reqs: reqs,
                rcvs: rcvs,
                rslts: rslts,
                cfms: cfms
            }));
        };

        const makeTpPieChart = function(row){
            let labels = [];
            let ttls = [];
            row.forEach(function (itm) {
                labels.push(filterXSS(itm.issueTpNm));
                ttls.push(itm.ttl);
            });

            destroyChart(chart4);
            chart4 = new Chart($chart4, makeChartConfig({
                type: 3,
                title: filterXSS(row[0].orgNm) + ' ' + filterXSS(row[0].systemName)+ " 요청구분 현황",
                labels: labels,
                ttls: ttls
            }));
        };

        const makeTpDailyChart = function(datas){
            let labels = [];
            let tpCds = [];
            let tpInfos = [];
            let tpTtls = {};
            let date;
            datas.forEach(function (itm) {
                if (paragonCmm.isNotEmpty(chart5Params.unitDay)) {
                    date = filterXSS(itm.issueYear) + "-" + filterXSS(itm.issueMm) + "-" + filterXSS(itm.issueDd);
                } else if (paragonCmm.isNotEmpty(chart5Params.unitMonth)) {
                    date = filterXSS(itm.issueYear) + "-" + filterXSS(itm.issueMm);
                } else {
                    date = filterXSS(itm.issueYear);
                }

                if(labels.indexOf(date) === -1){
                    labels.push(date);
                }

                if(tpCds.indexOf(itm.issueTpCd) === -1){
                    tpCds.push(itm.issueTpCd);
                    tpInfos[itm.issueTpCd] = itm.issueTpNm;
                    tpTtls[itm.issueTpCd] = [];
                }
            });

            console.debug(tpCds);
            console.debug(tpTtls);

            labels.forEach(function(dte){
                datas.forEach(function (itm) {
                    if (paragonCmm.isNotEmpty(chart5Params.unitDay)) {
                        date = filterXSS(itm.issueYear) + "-" + filterXSS(itm.issueMm) + "-" + filterXSS(itm.issueDd);
                    } else if (paragonCmm.isNotEmpty(chart5Params.unitMonth)) {
                        date = filterXSS(itm.issueYear) + "-" + filterXSS(itm.issueMm);
                    } else {
                        date = filterXSS(itm.issueYear);
                    }

                    if(dte === date) {
                        tpCds.forEach(function(tpCd){
                            if (itm.issueTpCd === tpCd) {
                                tpTtls[itm.issueTpCd].push(itm.ttl);
                            }
                        });
                    }
                });
            });

            console.debug(tpTtls);

            let datasets = [];
            tpCds.forEach(function(itm){
                datasets.push({
                    label: tpInfos[itm],
                    data: tpTtls[itm]
                });
            });

            console.debug(datasets);

            destroyChart(chart5);
            chart5 = new Chart($chart5, makeChartConfig({
                type: 5,
                title: filterXSS(datas[0].orgNm) + ' ' + filterXSS(datas[0].systemName)+ " 요청구분 현황",
                labels: labels,
                datasets: datasets
            }));
        };

        const drillDownSystem = function(systemId){
            // 빵조각 설정
            setBreadcrumb(systemId);
            $customerDtlStatsChart.show();
            $customerStatsChart.hide();

            // 시스템 현황표
            let row = getSysStatsRow(systemId);
            makeSystemStatsTbl(row);

            destroyChart(chart1);

            // 현황 챠트2: 상태별 파이
            makeStuPieChart(row);

            // 현황 챠트3: 일자별 상태
            chart3Params = $.extend({
                systemId: systemId,
                unitYear: "default",
                unitMonth: "default",
                unitDay: null,
                issueType: null
            }, customerStatsParams);
            model.getSystemStats({
                params: chart3Params,
                cbFnc: function(data){
                    if (data.errYn === "E") {
                        //-- 오류처리
                        $.messager.alert("Error", data.msg, "error");
                    } else {
                        makeStuDailyChart(data.data);
                    }
                }
            });

            // 현황 챠트4: 유형별 파이
            chart4Params = $.extend({
                systemId: systemId,
                unitYear: null,
                unitMonth: null,
                unitDay: null,
                issueType: "default"
            }, customerStatsParams);
            model.getIssueTpStats({
                params: chart4Params,
                cbFnc: function(data){
                    if (data.errYn === "E") {
                        //-- 오류처리
                        $.messager.alert("Error", data.msg, "error");
                    } else {
                        makeTpPieChart(data.data);
                    }
                }
            });

            // 현황 챠트5: 일자별 유형
            chart5Params = $.extend({
                systemId: systemId,
                unitYear: "default",
                unitMonth: "default",
                unitDay: null,
                issueType: "default"
            }, customerStatsParams);
            model.getIssueTpStats({
                params: chart5Params,
                cbFnc: function(data){
                    if (data.errYn === "E") {
                        //-- 오류처리
                        $.messager.alert("Error", data.msg, "error");
                    } else {
                        makeTpDailyChart(data.data);
                    }
                }
            });

        };

        const makeSystemStatsTbl = function (data) {
            let bodyHtml = "";
            if (paragonCmm.isEmpty(data)) {
                bodyHtml = "<tr><td colspan='9' class='text-center'>NO DATA</td>";
            } else {
                bodyHtml = makeStatsRow(data, $customerStats);
            }

            $customerStats.show();
            $customerStats.find("tbody").html(bodyHtml);
            $customerStats.off("click", "tr");
            $customerStats.on("click", "tr", function () {
                let systemId = $(this).data("options");
                console.debug(systemId);
                drillDownSystem(systemId);
            });
        };

        const makeSystemStatsChart = function(){
            let labels = [];
            let reqs = [];
            let rcvs = [];
            let rslts = [];
            let cfms = [];
            customerStatsData.forEach(function (itm) {
                labels.push(filterXSS(itm.orgNm) + ' ' + filterXSS(itm.systemName));
                reqs.push(itm.reqCnt);
                rcvs.push(itm.rcvCnt);
                rslts.push(itm.rsltCnt);
                cfms.push(itm.cfmCnt);
            });

            destroyChart(chart1);
            chart1 = new Chart($chart1, makeChartConfig({
                title: "시스템별 현황",
                labels: labels,
                reqs: reqs,
                rcvs: rcvs,
                rslts: rslts,
                cfms: cfms
            }));
        };

        const setSystemStats = function () {
            setBreadcrumb();

            $customerDtlStatsChart.hide();
            $customerStatsChart.show();

            makeSystemStatsTbl(customerStatsData);
            makeSystemStatsChart();

            destroyChart(chart2);
            destroyChart(chart3);
            destroyChart(chart4);
            destroyChart(chart5);
        };

        const search = function(){
            customerStatsParams = getQueryParams();
            model.getCustomerStats({
                params: customerStatsParams,
                cbFnc: function (data) {

                    if (data.errYn === "E") {
                        //-- 오류처리
                        $.messager.alert("Error", data.msg, "error");
                    } else {
                        customerStatsData = [].concat(data.data);
                    }
                    setSystemStats();
                }
            });
        };

        const setFormEvent = function () {
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

        const setBtnEvent = function () {
            $rootLayer.off("click", "button");
            $rootLayer.on("click", "button", function () {
                let btnId = $(this).attr("id");
                switch (btnId) {
                    case "customerStatsResetSmBtn":
                    case "customerStatsResetBtn1":
                        $schForm.clearForm();
                        setDefaultDte();
                        break;
                    case "customerStatsSearchSmBtn":
                    case "customerStatsSearchBtn1":
                        search();
                        break;
                    // case "customerStatsExcelBtn1":
                    //     expExcel();
                    //     break;
                    default:
                        console.warn("not support btnId:" + btnId);
                        return;
                }

                return false; // 이벤트 확산금지
            });
        };

        const initForm = function(){
            initSchObj();
        };

        const setEvent = function () {
            setFormEvent();
            setBtnEvent();
        };

        // ==============================================
        // public functions
        // ----------------------------------------------
        const init = function(){
            initForm();
            setEvent();
        };

        return {
            init: init
        };
    };

    const status = new CustomerStats();
    status.init();
});