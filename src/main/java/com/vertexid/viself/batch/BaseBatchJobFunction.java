/*
 * @(#)BaseBatchJobFunction.java     2023-05-25 오전 8:07
 *
 * Copyright 2023 JaYu.space
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

import com.vertexid.viself.base.BaseSvc;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Component
public class BaseBatchJobFunction extends BaseSvc {

    @Value(value = "#{cmmProperties['batch.yn']}")
    private String batchJobYn;

    @Resource
    BatchJobCollisionAvoidSvc batchJobCollisionAvoidSvc;

    /**
     * Batch 작업 수행여부
     *
     * @return true: 수행, other: 수행하지 않음
     */
    public boolean isBatchJobFlag() {
        try {
            return BooleanUtils.toBooleanObject(this.batchJobYn);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Batch 작업 비수행여부
     *
     * @return true: 수행하지 않음, other: 수행
     */
    public boolean isNotBatchJobFlag() {
        return !isBatchJobFlag();
    }

    public void runBatchJon(String batchJobName, Object objSvc) {

        if (StringUtils.isEmpty(batchJobName)) {
            String msg = "JOB Name 이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        if (isNotBatchJobFlag()) {
            log.info(
                    "[We don't use batch job: Change the value of 'batch.yn' to 'Y' for it to work.] - " +
                            batchJobName);
            return;
        }

        // 2. 배치작업 중복 여부 확인 및 배치시작 등록
        if (batchJobCollisionAvoidSvc.startJob(batchJobName) != 0) {
            // 동일작업이 이미 시작중: skip
            return;
        }

        try {

            TaskSvc taskSvc;
            if(objSvc instanceof TaskSvc){
                taskSvc = (TaskSvc)objSvc;
                taskSvc.doWork(batchJobName);
            }else{
                String msg = "Service 유형이 올바르지 않습니다.";
                log.error(msg);
                throw new RuntimeException(msg);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            batchJobCollisionAvoidSvc.endJob(batchJobName);
        }
    }
}
