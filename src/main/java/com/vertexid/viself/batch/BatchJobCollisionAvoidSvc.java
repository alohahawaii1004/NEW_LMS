/*
 * @(#)BatchJobCollisionAvoidSvc.java     2021-07-13(013) 오후 5:15
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

import com.vertexid.commons.utils.ParamMap;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <b>Description</b>
 * <pre>
 *     Batch 작업 충돌 회피 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class BatchJobCollisionAvoidSvc extends BaseSvc {

    private static final int DEADLINE = 1;
    @Resource(name = "cmmDAO")
    private CmmDAO cmmDAO;

    private static final String NAMESPACE =
            "com.vertexid.viself.batch.CommonBatch";

    public int startJob(String batchJobType) {

        String msg;

        // 1. 필수값 검사
        if(StringUtils.isBlank(batchJobType)){
            msg = "JOB 이름이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        BatchJobDTO batchJobDTO = new BatchJobDTO();
        batchJobDTO.setJobName(batchJobType);

        // 2. 기존 작업 검사
        ParamMap<String, Object> oldData = getJobInfo(batchJobDTO);
        if(oldData != null && !oldData.isEmpty()){

            int diffHour = Integer.parseInt(oldData.getString("hDiff"));
            if(diffHour > DEADLINE){
                // 2.2. 기존작업이 있으나 제한시간을 초과한 경우 작업정보 삭제
                msg = "이미 수행중인 작업이 기준시간을 초과 하였습니다."+batchJobType;
//                log.debug(msg);

                deleteJob(batchJobDTO);
            }else{
                msg = "이미 수행중인 작업이 있습니다. ST_DTIME:"+oldData.getString("jobStDtime");
                log.info(msg);
                return -1;
            }
        }

        insertJob(batchJobDTO);

        log.info("BATCH_START........................"+batchJobDTO.toString());
        return 0;
    }

    private void insertJob(BatchJobDTO batchJobDTO) {
        cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "insertBatch"), batchJobDTO);
    }

    private void deleteJob(BatchJobDTO batchJobDTO) {
        cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "deleteBatch"), batchJobDTO);
    }

    public int startJobProcedure(String batchJobType) {

        BatchJobDTO batchJobDTO = new BatchJobDTO();
        batchJobDTO.setJobName(batchJobType);

        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insertBatch"),
                batchJobDTO);

        log.info("BATCH_START........................"+batchJobDTO.toString());
        if(!"S".equals(batchJobDTO.getErrYn())){
            log.info(batchJobDTO.getErrMsg());
            return -1;
        }
        return 0;
    }

    @Transactional(readOnly = true)
    ParamMap<String, Object> getJobInfo(BatchJobDTO batchJobDTO) {
        return cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "getBatchInfo"), batchJobDTO);
    }

    public void endJob(String batchJobType) {
        BatchJobDTO batchJobDTO = new BatchJobDTO();
        batchJobDTO.setJobName(batchJobType);

        deleteJob(batchJobDTO);
        log.info("BATCH_END.........................."+batchJobDTO.toString());
        if(!"S".equals(batchJobDTO.getErrYn())){
            log.warn(batchJobDTO.getErrMsg());
        }
    }
}
