/*
 * @(#)ListPerPageSvc.java     2022-12-08(008) 오전 10:48:59
 *
 * Copyright 2022 JaYu.space
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

import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class ListPerPageSvc extends BaseSvc {

    @Resource
    CmmDAO cmmDAO;

    private String namespace = "";

    /**
     * get Count MyBatis Mapper ID
     */
    public static final String MAPPER_ID_GET_COUNT = "countData";

    /**
     * get List Per Page MyBatis Mapper ID
     */
    public static final String MAPPER_ID_GET_LIST_PER_PAGE = "getListPerPage";

    /**
     * return object key : list
     */
    public static final String RETURN_KEY_LIST = "list";

    /**
     * return object key : total count
     */
    public static final String RETURN_KEY_TOTAL_COUNT = "totalCount";

    @Transactional(readOnly = true)
    public Object getListPerPage(Map<String, Object> param, String namespace) {

        this.namespace = namespace;

        Map<String, Object> rtnMap = new HashMap<>();

        log.info("param...." + param);
        log.info("namespace...." + this.namespace);

        rtnMap.put(RETURN_KEY_LIST, getList(param));
        rtnMap.put(RETURN_KEY_TOTAL_COUNT, count(param));
        return rtnMap;
    }

    @Transactional(readOnly = true)
    public Object getListPerPage(Map<String, Object> param, String namespace,
            String listId, String countId) {

        this.namespace = namespace;

        Map<String, Object> rtnMap = new HashMap<>();

        log.info("param...." + param);
        log.info("namespace...." + this.namespace);

        rtnMap.put(RETURN_KEY_LIST, getList(param, listId));
        rtnMap.put(RETURN_KEY_TOTAL_COUNT, count(param, countId));
        return rtnMap;
    }

    /**
     * get total count ( mapper id: MAPPER_ID_GET_COUNT)
     *
     * @param param search parameters
     * @return count
     */
    @Transactional(readOnly = true)
    public Object count(Map<String, Object> param) {
        return cmmDAO.getOne(
                cmmDAO.getStmtByNS(this.namespace, MAPPER_ID_GET_COUNT), param);
    }

    /**
     * list per page ( mapper id: MAPPER_ID_GET_LIST_PER_PAGE)
     *
     * @param param search parameters
     * @return list
     */
    @Transactional(readOnly = true)
    public Object getList(Map<String, Object> param) {
        return cmmDAO.getList(
                cmmDAO.getStmtByNS(this.namespace, MAPPER_ID_GET_LIST_PER_PAGE),
                param);
    }

    /**
     * get total count ( mapper id: MAPPER_ID_GET_COUNT)
     *
     * @param param search parameters
     * @return count
     */
    @Transactional(readOnly = true)
    public Object count(Map<String, Object> param, String countId) {
        return cmmDAO.getOne(cmmDAO.getStmtByNS(this.namespace, countId),
                param);
    }

    /**
     * list per page ( mapper id: MAPPER_ID_GET_LIST_PER_PAGE)
     *
     * @param param search parameters
     * @return list
     */
    @Transactional(readOnly = true)
    public Object getList(Map<String, Object> param, String listId) {
        return cmmDAO.getList(cmmDAO.getStmtByNS(this.namespace, listId),
                param);
    }
}
