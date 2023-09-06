/*
 * @(#)SupportMailSender.java     2023-04-04 오후 3:27
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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vertexid.paragon.mail.CmmMailSvc;
import com.vertexid.paragon.mail.MailDTO;
import com.vertexid.paragon.mail.MailManager;
import com.vertexid.viself.base.BaseSvc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class SupportMailSender extends BaseSvc {

    @Resource
    CmmMailSvc cmmMailSvc;

    @Resource
    MailManager mailManager;

    private static ObjectMapper objectMapper;


    public void sendEMail(Map<String, Object> mailInfo) {

        initObjMapper();

        MailDTO mailDTO;
        mailDTO = objectMapper.convertValue(mailInfo, MailDTO.class);

        try {
            mailManager.setProperties();

            // 보내는 사람
            mailManager.setFrom(mailDTO.getSend());
            // 받는 사람
            mailManager.addTos(mailDTO.getRec());

            // 제목
            mailManager.setSubject(mailDTO.getTit());
            // 내용
            mailManager.setBody(mailDTO.getContent(),
                    ("Y".equals(mailDTO.getHtmlUseYn())));

            //발송
            mailManager.send();

            // 2. 발송기록
            mailDTO.setSendYn("Y");
            updateSendInfo(mailDTO);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateSendInfo(MailDTO param) {
        cmmMailSvc.updateResult(param);
    }

    /**
     * Object Mapper 초기화
     */
    private void initObjMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.configure(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
    }
}
