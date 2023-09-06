/*
 * @(#)ListPerPageFunction.java     2022-05-24(024) 오전 7:37:38
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

import com.vertexid.viself.base.BaseSvc;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 *     페이징 리스트 값을 구해오는 컴포넌트
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Component
public class ListPerPageFunction extends BaseSvc {

    @Resource
    ListPerPageSvc listPerPageSvc;

    private String namespace = "";

    /**
     * set MyBatis namespace
     * @param namespace MyBatis namespace
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * get list per page
     * @param param search parameters
     * @return result object(Map: RETURN_KEY_LIST, RETURN_KEY_TOTAL_COUNT)
     */
    public synchronized Object getListPerPage(Map<String, Object> param) {
        return listPerPageSvc.getListPerPage(param, namespace);
    }

    public synchronized Object getListPerPage(Map<String, Object> param, String listId, String countId) {
        return listPerPageSvc.getListPerPage(param, namespace, listId, countId);
    }
}
