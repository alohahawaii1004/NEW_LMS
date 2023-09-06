/*
 * @(#)MailBatchJobSvc.java     2023-04-04 오후 3:10
 *
 * Copyright 2023 JAYU.space
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
package com.vertexid.support.scheduler;

import com.vertexid.spring.utils.BaseProperties;
import com.vertexid.viself.batch.BaseBtchJobSvc;
import com.vertexid.viself.batch.BatchJobCollisionAvoidSvc;
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
public class MailBatchJobSvc extends BaseBtchJobSvc {

    private static final String BATCH_JOB_TYPE = "MAIL_BATCH";

    private String sendYn = BaseProperties.get("mail.useYn");

    @Resource
    BatchJobCollisionAvoidSvc batchJobCollisionAvoidSvc;

    @Resource
    SupportMailSvc supportMailSvc;

    @Override
    public String getBatchJobType() {
        return BATCH_JOB_TYPE;
    }

    @Override
    public void execBatchJob() {

        if(!"Y".equals(sendYn)){
            log.info(
                    "[We don't use Send Mail.: Change the value of 'mail.useYn' to 'Y' for it to work.] - " +
                            BATCH_JOB_TYPE);
            return;
        }

        // 1. 배치작업 수행여부 확인
        if(isNotBatchJobFlag()){
            log.info(
                    "[We don't use batch job: Change the value of 'batch.yn' to 'Y' for it to work.] - " +
                            BATCH_JOB_TYPE);
            return;
        }

        // 2. 배치작업 중복 여부 확인 및 배치시작 등록
        if(batchJobCollisionAvoidSvc.startJob(BATCH_JOB_TYPE) != 0){
            // 동일작업이 이미 시작중: skip
            return;
        }

        try{
            supportMailSvc.doWork(BATCH_JOB_TYPE);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }finally {
//            batchJobCollisionAvoidSvc.endJob(BATCH_JOB_TYPE);
        }

    }
}
