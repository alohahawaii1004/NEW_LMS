/*
 * @(#)MyBatisDAOFunction.java     
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

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * [토비의 스프링]을 참고로 구성
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class MyBatisDAOFunction extends SqlSessionDaoSupport {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

    /**
     * @param statement namespace + '.' + id
     * @param params
     * @return
     */
    public <T> T select(final String statement, final Object params) {
//        log.info("select statement........"+statement);
//        log.info("select params........"+params);
        return super.getSqlSession().selectOne(statement, params);
    }

    /**
     * @param statement
     * @param params
     * @param <E>
     * @return
     */
    public <E> List<E> selectList(final String statement, final Object params) {
        log.info("selectList statement........"+statement);
        log.info("selectList params........"+params);
        return super.getSqlSession().selectList(statement, params);
    }

    /**
     * @param statement namespace + '.' + id
     * @param params data object
     * @return count of insert data
     */
    public int insert(final String statement, final Object params) {
//        log.info("insert statement........"+statement);
//        log.info("insert params........"+params);
        return super.getSqlSession().insert(statement, params);
    }

    /**
     * @param statement namespace + '.' + id
     * @param params data object
     * @return count of update data
     */
    public int update(final String statement, final Object params) {
//        log.info("update statement........"+statement);
//        log.info("update params........"+params);
        return super.getSqlSession().update(statement, params);
    }

    /**
     * @param statement namespace + '.' + id
     * @param params
     * @return
     */
    public int delete(final String statement, final Object params) {
//        log.info("delete statement........"+statement);
//        log.info("delete params........"+params);
        return super.getSqlSession().delete(statement, params);

    }
}
