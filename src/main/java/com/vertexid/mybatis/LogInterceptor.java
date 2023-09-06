/*
 * @(#)LogInterceptor.java     2020-10-22(022) 오전 8:28
 *
 * Copyright 2020 JAYU.space
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
package com.vertexid.mybatis;

import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <b>Description</b>
 * <pre>
 *     Mybatis의 로그를 위한 Interceptor
 *     info 등급이상일 경우 로그 생성
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */

// StatementHandler를 intercept 할 경우
//@Intercepts({@Signature(
//        type = StatementHandler.class,
//        method = "batch",
//        args = {Statement.class}
//), @Signature(
//        type = StatementHandler.class,
//        method = "update",
//        args = {Statement.class}
//), @Signature(
//        type = StatementHandler.class,
//        method = "query",
//        args = {Statement.class, ResultHandler.class}
//)})

// Executor를 intercept 할 경우
@Intercepts({@Signature(type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class,
                        ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class,
                        ResultHandler.class})})
public class LogInterceptor implements Interceptor {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    private String qryUuid;

    private String qryId;

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public LogInterceptor() {
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        // generate Query UUID
        setQueryUUID();

        // logging Query Info
        logQueryInfo(invocation);

        DateTime stDt = new DateTime();
        Object rslt = invocation.proceed();
        DateTime edDt = new DateTime();

        // logging result Info
        logResultInfo(rslt, new Interval(stDt, edDt));

        return rslt;
    }

    /**
     * 결과 로그
     * @param rslt 결과객체
     * @param interval Query 실행시간(Interval)
     */
    @SuppressWarnings("unchecked")
    private void logResultInfo(Object rslt, Interval interval) {
        if (rslt == null || !log.isInfoEnabled()) {
            return;
        }

        String rstSize = "";
        List<Object> logLst = null;
        if (rslt instanceof List) { // SELECT
            logLst = (List<Object>) rslt;
            rstSize = Integer.toString(logLst.size());
        } else if (rslt instanceof Integer) { // INSERT, UPDATE, DELETE
            /*
             * rstSize = rst.toString(); } else {
             */
            rstSize = rslt.toString();
        }

        PeriodFormatter periodFormatter =  new PeriodFormatterBuilder().printZeroAlways()
                .minimumPrintedDigits(2)
                .appendHours()
                .appendSeparator(":")
                .printZeroAlways()
                .minimumPrintedDigits(2)
                .appendMinutes()
                .appendSeparator(":")
                .printZeroAlways()
                .minimumPrintedDigits(2)
                .appendSeconds()
                .toFormatter();

        StringBuffer sbLog = new StringBuffer();
        sbLog.append("\n    ====================================================");
        sbLog.append("\n    Query Result Info (QUERY UUID: ").append(this.qryUuid).append(")");
        sbLog.append("\n    ----------------------------------------------------");
        sbLog.append("\n    * QUERY ID: ").append(this.qryId);
        sbLog.append("\n    * RESULT SIZE: ").append(rstSize);
        sbLog.append("\n    * START TIME: ").append(interval.getStart().toString(DATE_FORMAT));
        sbLog.append("\n    * END TIME: ").append(interval.getEnd().toString(DATE_FORMAT));
        sbLog.append("\n    * EXECUTION TIME: ").append(periodFormatter.print(interval.toPeriod()));
        sbLog.append("\n    * EXECUTION MILLISECONDS: ").append(interval.toDurationMillis())
                .append("ms");
        sbLog.append("\n    ====================================================");

        log.info(sbLog.toString());
    }

    /**
     * set Query UUID
     */
    private void setQueryUUID() {
//        this.qryUuid =
//                StringUtils.replace(UUID.randomUUID().toString(), "-", "");
        this.qryUuid = UUID.randomUUID().toString();
    }

    /**
     * Query 정보 로그
     * @param invocation invocation object
     */
    private void logQueryInfo(Invocation invocation) {
        if (!log.isInfoEnabled()) {
            return;
        }
        StringBuffer sbLog = new StringBuffer();

        //QueryId
        String queryID = ((MappedStatement) invocation.getArgs()[0]).getId();
        this.qryId = queryID;

        //Query Parameter
        Object param = invocation.getArgs()[1];
        //Query String
        String queryString =
                ((MappedStatement) invocation.getArgs()[0]).getBoundSql(param)
                        .getSql();
        sbLog.append("\n    ====================================================");
        sbLog.append("\n    Query Info (QUERY UUID: ").append(this.qryUuid).append(")");
        sbLog.append("\n    ----------------------------------------------------");
        sbLog.append("\n    * QUERY ID: ").append(queryID);
        sbLog.append("\n    * QUERY STRING:\n        ").append(queryString);
        sbLog.append("\n    ----------------------------------------------------");
        sbLog.append("\n    * PARAMS: ").append(param);
        sbLog.append("\n    ====================================================");

        log.info(sbLog.toString());
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
