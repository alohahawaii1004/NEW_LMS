/*
 * @(#)TimestampTypeHandler.java     2021-10-08(008) 오후 1:28
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
package com.vertexid.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.StringTypeHandler;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <b>Description</b>
 * <pre>
 *     Mybatis 에서 조회된 jdbcType=TIMESTAMP 를 , javaType=java.lang.Object 으로 변환하기 위한 TypeHandler
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class TimestampTypeHandler extends StringTypeHandler {

    /**
     * logger
     */
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private String dateFormat = DEFAULT_DATE_FORMAT;

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
            String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        Object obj = rs.getDate(columnName);
        if(obj == null){
            return "";
        }
        DateTime dt = new DateTime(obj);
        return dt.toString(dateFormat);
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        Object obj = rs.getDate(columnIndex);
        if(obj == null){
            return "";
        }
        DateTime dt = new DateTime(obj);
        return dt.toString(dateFormat);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        Object obj = cs.getDate(columnIndex);
        if(obj == null){
            return "";
        }
        DateTime dt = new DateTime(obj);
        return dt.toString(dateFormat);
    }

    @Override
    public String getResult(ResultSet rs, String columnName)
            throws SQLException {
        Object obj = rs.getDate(columnName);
        if(obj == null){
            return "";
        }
        DateTime dt = new DateTime(obj);
        return dt.toString(dateFormat);
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        Object obj = rs.getDate(columnIndex);
        if(obj == null){
            return "";
        }
        DateTime dt = new DateTime(obj);
        return dt.toString(dateFormat);
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        Object obj = cs.getDate(columnIndex);
        if(obj == null){
            return "";
        }
        DateTime dt = new DateTime(obj);
        return dt.toString(dateFormat);
    }
}
