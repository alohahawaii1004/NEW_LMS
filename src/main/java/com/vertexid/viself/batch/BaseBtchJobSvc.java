/*
 * @(#)BaseBtchJobSvc.java     2021-07-13(013) 오후 5:03
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
package com.vertexid.viself.batch;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Value;

import com.vertexid.viself.base.BaseSvc;

/**
 * <b>Description</b>
 * <pre>
 *     Batch 작업을 수행하는 기본 Batch 작업 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public abstract class BaseBtchJobSvc extends BaseSvc implements BatchJobSvc {

    @Value(value = "#{cmmProperties['batch.yn']}")
    private String batchJobYn;

    /**
     * Batch 작업 수행여부 얻기
     * @return string 으로된 Batch 작업 수행여부
     */
    public String getBatchJobYn() {
        return batchJobYn;
    }

    /**
     * Batch 작업 수행여부
     * @return true: 수행, other: 수행하지 않음
     */
    public boolean isBatchJobFlag() {
        try{
            return BooleanUtils.toBooleanObject(this.batchJobYn);
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Batch 작업 비수행여부
     * @return true: 수행하지 않음, other: 수행
     */
    public boolean isNotBatchJobFlag(){
        return !isBatchJobFlag();
    }
}
