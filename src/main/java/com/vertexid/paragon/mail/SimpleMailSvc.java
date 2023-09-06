/*
 * @(#)SimpleMailSvc.java     2022-11-15(015) 오전 10:53:59
 *
 * Copyright 2022 JaYu.space
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
package com.vertexid.paragon.mail;

import com.vertexid.commons.utils.FetchForm;
import com.vertexid.commons.utils.IDUtils;
import com.vertexid.commons.utils.ParamMap;
import com.vertexid.spring.utils.BaseProperties;
import com.vertexid.spring.utils.CmmProperties;
import com.vertexid.viself.base.BaseSvc;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 *     메일 발송 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class SimpleMailSvc extends BaseSvc {

    private static final String STR_SEPARATOR = ",";
    public static final String FORM_PATH = "/form/mail/sys_mail.html";

    private static final int CNT_UNIT = 10;

    @Resource
    CmmMailSvc cmmMailSvc;


    String name = BaseProperties.get("sys.admin.name");
    String email = BaseProperties.get("sys.admin.email");
    
    String sendAdmin = "\"" + name + "\"" + "<" + email + ">" ;

    public String sendMessage(Map<String, Object> param) throws Exception {
        String errMsg = "";
        return errMsg;
    }
    
    private void sendMsgToUserList(MailDTO mailDTO,
            List<ParamMap<String, Object>> tempList) {
        mailDTO.setEmailUid(IDUtils.getRandomUUID());
        // 2. to
        mailDTO.setRec(getMailInfo(tempList));
        cmmMailSvc.insertData(mailDTO);
    }


    private String makeContent(Map<String, Object> param) throws Exception {
        FetchForm fetFormContent = new FetchForm(FORM_PATH);
        fetFormContent.setParam("TITLE", String.valueOf(param.get("mailTit")));
        fetFormContent.setParam("CONTENT",
                StringUtils.replace(String.valueOf(param.get("mailContent")),
                        "\n", "<br>"));
        fetFormContent.setParam("SITE_URL", CmmProperties.getSystemUrl());
        return fetFormContent.parseForm();
    }

    @Transactional(readOnly = true)
    public String getMailInfo(List<ParamMap<String, Object>> userList) {

        /*
         * 메일주소 형식
         * 1. "%USER_NAME%" <%EMAIL_ADDRESS%>
         * 2. %EMAIL_ADDRESS%
         */
        int chkIdx = 0;
        StringBuilder sbMailInfo = new StringBuilder();
        for (ParamMap<String, Object> userInfo : userList) {

            String email = userInfo.getString("email");
            String userName = userInfo.getString("nmKo");

            if (StringUtils.isNotBlank(email)) {
                if (chkIdx > 0) {
                    sbMailInfo.append(STR_SEPARATOR);
                }
//                int mailAddType = 1;
//                if (mailAddType == 1) {
                if (StringUtils.isNotBlank(userName)) {
                    sbMailInfo.append("\"").append(userName).append("\"");
                }
                sbMailInfo.append("<").append(email).append(">");
//                } else {
//                    sbMailInfo.append(email);
//                }
                chkIdx += 1;
            }
        }// end of for

        return sbMailInfo.toString();
    }
}
