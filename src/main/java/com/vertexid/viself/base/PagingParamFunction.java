/*
 * @(#)PagingParamFunction.java     2021-01-07(007) 오후 4:44
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
package com.vertexid.viself.base;

import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 *     페이징 처리를 위한 파라메터 유틸
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public interface PagingParamFunction {

    /**
     * order parameter 처리
     * @param params parameters
     */
    void setOrderParam(Map<String, Object> params);
}
