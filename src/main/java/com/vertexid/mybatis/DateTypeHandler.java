/*
 * @(#)DateTypeHandler.java     
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

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <b>Description</b>
 * <br>	Mybatis에서 조회된 jdbcType=DATE 를, javaType=String으로 변환하기 위한 TypeHandler
 *
 * @author ykihwa
 */
public class DateTypeHandler extends BaseTypeHandler<String> {

    /**
     * logger
     */
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    private String dateFormat = "yyyy-MM-dd";

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
