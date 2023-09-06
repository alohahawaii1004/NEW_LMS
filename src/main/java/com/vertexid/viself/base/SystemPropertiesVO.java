/*
 * @(#)SystemPropertiesVO.java     2020-04-23 오후 4:16
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
package com.vertexid.viself.base;

import static com.vertexid.viself.base.SystemOperationType.LOCAL;
import static com.vertexid.viself.base.SystemOperationType.PROD;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * <b>Description</b>
 *
 * properties에 설정된 system 관련 정보
 *
 * @author Yang, Ki Hwa
 */
@Repository("systemPropertiesVO")
public class SystemPropertiesVO extends BaseVO{
    private static final long serialVersionUID = -5547836241775069648L;

    @Value(value = "#{cmmProperties['sys.name']}")
    private String systemName;

    /**
     * 기본페이지 사이즈
     */
    @Value(value = "#{cmmProperties['default.page.size']}")
    private String pageSize;
    /**
     * 기본페이지 행수
     */
    @Value(value = "#{cmmProperties['default.page.rows']}")
    private String pageRows;

    /**
     * 어플리케이션 운영모드
     */
    @Value(value = "#{cmmProperties['sys.operation.mode']}")
    private String systemOperationMode;

    public SystemPropertiesVO() {
    }

    public String getSystemName() {
        return systemName;
    }

    public String getSystemOperationMode() {
        return systemOperationMode;
    }

    /**
     * 개별 개발 모드 여부 반환
     * @return
     */
    public boolean isLocal(){
        return LOCAL.getTypeCode().equals(systemOperationMode);
    }

    /**
     * 실제 서비스 모드 여부 반환
     * @return
     */
    public boolean isProd(){
        return PROD.getTypeCode().equals(systemOperationMode);
    }

    /**
     * 실제 서비스 모드가 아님 여부를 반환
     * @return
     */
    public boolean isNotProd(){
        return !isProd();
    }

    public Integer getPageSize() {
        return Integer.valueOf(pageSize) ;
    }

    public Integer getPageRows() {
        return Integer.valueOf(pageRows) ;
    }
}

