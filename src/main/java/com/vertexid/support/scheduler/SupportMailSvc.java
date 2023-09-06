/*
 * @(#)SupportMailSvc.java     2023-04-04 오후 3:13
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

import com.vertexid.paragon.mail.CmmMailSvc;
import com.vertexid.viself.base.BaseSvc;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Component
public class SupportMailSvc extends BaseSvc {

    @Resource
    CmmMailSvc cmmMailSvc;

    @Resource
    SupportMailSender supportMailSender;

    public void doWork(String batchJobType) {

        // 1. 메일 발송대상 얻기
        Map<String, Object> param = new HashMap<>();
        param.put("sendYn", "N");
        List<Map<String, Object>> targetList = cmmMailSvc.getMailList(param);

        // 2. 메일 발송
        for (Map<String, Object> mailInfo : targetList) {
            supportMailSender.sendEMail(mailInfo);
        }
    }
}
