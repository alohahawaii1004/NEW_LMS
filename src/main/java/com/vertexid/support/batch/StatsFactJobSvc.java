/*
 * @(#)StatsFactJobSvc.java     2023-06-13(013) 오전 11:32
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
package com.vertexid.support.batch;

import com.vertexid.viself.batch.BaseBatchJobFunction;
import com.vertexid.viself.batch.BaseBtchJobSvc;

import javax.annotation.Resource;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class StatsFactJobSvc extends BaseBtchJobSvc {

    public static final String BATCH_JOB_TYPE = "COLLECT_FACT_DATA";

    @Resource
    BaseBatchJobFunction baseBatchJobFunction;

    @Resource
    StatsFactTaskSvc statsFactTaskSvc;

    @Override
    public String getBatchJobType() {
        return null;
    }

    @Override
    public void execBatchJob() {
        baseBatchJobFunction.runBatchJon(BATCH_JOB_TYPE, statsFactTaskSvc);
    }
}
