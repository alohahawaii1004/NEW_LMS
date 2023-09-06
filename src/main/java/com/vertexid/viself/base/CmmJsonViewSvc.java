/*
 * @(#)CmmJsonViewSvc.java     
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
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <b>Description</b>
 * <pre>
 *     공통 조회뷰
 * </pre>
 */
@Service
@Transactional
public class CmmJsonViewSvc extends BaseSvc {

    @Resource(name = "cmmDAO")
    CmmDAO cmmDAO;

    @Resource
    PagingParamFunction pagingParamFunction;

    /**
     * 데이터 얻기
     * @param modelInfo model info
     * @param params parameters
     * @return data(s)
     */
    @Transactional(readOnly = true)
    public Object getData(BaseModelVO modelInfo, Map<String, Object> params) {
        if (null == modelInfo) {
            // WARN
            if (log.isWarnEnabled()) {
                log.warn("..................................modelInfo is null");
            }
            return null;
        }

        ActionType actionType = modelInfo.getActionType();

        if (actionType == ActionType.DATA) {
            return getOne(modelInfo, params);
        } else if (actionType == ActionType.LIST || actionType == ActionType.SELECT) {
            return getList(modelInfo, params);
        } else {
            // WARN
            if (log.isWarnEnabled()) {
                log.warn(".................................actionType is null");
            }
            return null;
        }
    }

    @Transactional(readOnly = true)
    public <E> List<E> getList(BaseModelVO modelInfo, Map<String, Object> params) {

        pagingParamFunction.setOrderParam(params);
        return cmmDAO.getList(modelInfo, params);
    }

    @Transactional(readOnly = true)
    public <T> T getOne(BaseModelVO modelInfo, Map<String, Object> params) {
        return cmmDAO.getOne(modelInfo, params);
    }
}
