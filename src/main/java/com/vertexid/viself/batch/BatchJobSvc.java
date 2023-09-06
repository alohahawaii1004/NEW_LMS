/*
 * @(#)BatchJobSvc.java     2021-07-13(013) 오후 4:58
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

/**
 * <b>Description</b>
 * <pre>
 *     Batch 작업을 수행하는 Service I/F
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public interface BatchJobSvc {

    /**
     * Batch 작업 유형 얻기
     *
     * @return Batch job type
     */
    String getBatchJobType();

    /**
     * Batch 작업 수행
     */
    void execBatchJob();
}
