/*
 * @(#)StatsFactTaskSvc.java     2023-06-13(013) 오전 11:35
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

import com.vertexid.support.stats.SupportStatsSvc;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.batch.TaskSvc;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
public class StatsFactTaskSvc extends BaseSvc implements TaskSvc {

    @Resource
    SupportStatsSvc supportStatsSvc;

    @Override
    public void doWork(String jobName) {

        log.info("Start Batch Job............"+jobName);

        DateTime today = new DateTime();
        DateTime yesterday = today.minusDays(1);

        Map<String, Object> param = new HashMap<>();
        param.put("inFromDate", yesterday.toString("yyyy-MM-dd"));
        param.put("inToDate", yesterday.toString("yyyy-MM-dd"));
        param.put("inDays", null);

        supportStatsSvc.insertFact(param);

        log.info("End Batch Job............"+jobName);
    }
}
