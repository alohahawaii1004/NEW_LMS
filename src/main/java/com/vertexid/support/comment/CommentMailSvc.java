/*
 * @(#)CommentMailSvc.java     2023-06-19(019) 오전 8:25
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
package com.vertexid.support.comment;

import com.vertexid.paragon.mail.CmmMailSvc;
import com.vertexid.paragon.mail.MailDTO;
import com.vertexid.viself.base.BaseSvc;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class CommentMailSvc extends BaseSvc {

    @Resource
    CmmMailSvc cmmMailSvc;

    @Resource
    CommentRelFactory commentRelFactory;

    public void insertMail(CommentDTO param) {
        MailDTO mailDTO = getMailDTO(param);
        if (StringUtils.isNotEmpty(mailDTO.getRec())) {
            cmmMailSvc.insertData(mailDTO);
        }
    }

    private MailDTO getMailDTO(CommentDTO param) {

        MailDTO mailDTO = new MailDTO();

        CommentRelMasSvc commentRelMasSvc = commentRelFactory.getMasSvc(
                param.getRelTpCd());

        if(commentRelMasSvc == null){
            String msg = "관련 유형이 없습니다. relTpCd:"+param.getRelTpCd();
            log.warn(msg);
        }else{
            mailDTO = commentRelMasSvc.getMailDTO(param);
        }

        return mailDTO;
    }
}
