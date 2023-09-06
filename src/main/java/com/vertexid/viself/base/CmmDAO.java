/*
 * @(#)CmmDAO.java     
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

package com.vertexid.viself.base;

import java.util.List;

import com.vertexid.mybatis.MyBatisBaseDAO;
import com.vertexid.mybatis.MyBatisDAOFunction;

/**
 * <b>Description</b>
 * <pre>
 *     공용 DAO
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class CmmDAO extends MyBatisBaseDAO {

    /**
     * DAO 에서 null 처리를 위한 blank string
     */
    public static final String BLANK_STR = " ";

    private MyBatisDAOFunction myBatis;

    public void setMyBatis(MyBatisDAOFunction myBatis) {
        this.myBatis = myBatis;
    }

    public <T> T getOne(BaseModelVO modelVO, Object params) {
        return myBatis.select(getStatement(modelVO), params);
    }

    public <T> T getOne(String statement, Object params) {
        return myBatis.select(statement, params);
    }

    public <E> List<E> getList(BaseModelVO modelVO,
                               Object params) {
        return myBatis.selectList(getStatement(modelVO), params);
    }

    public <E> List<E> getList(String statement,
                               Object params) {
        return myBatis.selectList(statement, params);
    }

    public int insert(BaseModelVO modelVO, Object params) {
        return myBatis.insert(getStatement(modelVO), params);
    }

    public int insert(String statement, Object params) {
        return myBatis.insert(statement, params);
    }

    public int update(BaseModelVO modelVO, Object params) {
        return myBatis.update(getStatement(modelVO), params);
    }

    public int update(String statement, Object params) {
        return myBatis.update(statement, params);
    }

    public int delete(BaseModelVO modelVO, Object params) {
        return myBatis.delete(getStatement(modelVO), params);
    }
    public int delete(String statement, Object params) {
        return myBatis.delete(statement, params);
    }


}
