/*
 * @(#)EasyUiPagingParamFunction.java     2021-01-07(007) 오후 4:47
 *
 * Copyright 2021 JAYU.space
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
package com.vertexid.viself.base.extension;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.vertexid.viself.base.PagingParamFunction;

/**
 * <b>Description</b>
 * <pre>
 *     Easy UI용 페이징 처리
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class EasyUiPagingParamFunction implements PagingParamFunction {

    private static final String STRING_SEPERATOR = ",";

    @Override
    public void setOrderParam(Map<String, Object> params) {
        if(params.containsKey("sort")) {
            String tmpSort = (String) params.get("sort");
            String tmpOrder = (String) params.get("order");
//        String sort = "";
//        String order = "";
//        String[] sorts;
//        String[] orders;
//        if(tmpSort.contains(",")) {
//            params.put("sorts", StringUtils.split(tmpSort, STRING_SEPERATOR));
//        }else {
//            params.put("sorts", tmpSort);
//        }
//        if(tmpOrder.contains(",")) {
//            params.put("orders", StringUtils.split(tmpOrder, STRING_SEPERATOR));
//        }else {
//            params.put("orders", tmpOrder);
//        }

            String[] sorts = (StringUtils.isNotEmpty(tmpSort)) ?
                    StringUtils.split(tmpSort, STRING_SEPERATOR) : null;
            String[] orders = (StringUtils.isNotEmpty(tmpOrder)) ?
                    StringUtils.split(tmpOrder, STRING_SEPERATOR) : null;

            params.put("sorts", sorts);
            params.put("orders", orders);
        }else{
            params.put("sorts", "");
            params.put("orders", "");
        }
    }
}
