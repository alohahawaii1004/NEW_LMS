/*
 * @(#)IssueMailSvc.java     2023-04-03 오후 3:18
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
package com.vertexid.support.issue;

import com.vertexid.commons.utils.FetchForm;
import com.vertexid.commons.utils.IDUtils;
import com.vertexid.commons.utils.ParamMap;
import com.vertexid.paragon.mail.CmmMailSvc;
import com.vertexid.paragon.mail.MailDTO;
import com.vertexid.spring.utils.BaseProperties;
import com.vertexid.viself.base.BaseSvc;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 *     이슈 관리 메일 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
public class IssueMailSvc extends BaseSvc {

    public static final String FORM_PATH = "/form/mail/sys_mail_support.html";

    @Resource
    CmmMailSvc cmmMailSvc;

    @Resource
    IssueMngSvc mngSvc;

    public void insertMail(String issueId, String sendType) {

        IssueStuInfo issueStuInfo = IssueStuInfo.findBy(sendType);
        if (issueStuInfo == null) {
            String msg = "올바른 유형이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg,
                    new Throwable("sendType..." + sendType));
        }

        ParamMap<String, Object> issueInfo = getIssueInfo(issueId);
        if (issueInfo == null) {
            String msg = "이슈 정보가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg,
                    new Throwable("issueId..." + issueId));
        }

        MailDTO mailDTO = getMailDTO(issueInfo, issueStuInfo);
        if (StringUtils.isNotEmpty(mailDTO.getRec())) {
            cmmMailSvc.insertData(mailDTO);
        }
    }

    private MailDTO getMailDTO(ParamMap<String, Object> issueInfo,
            IssueStuInfo issueStuInfo) {
        MailDTO mailDTO = new MailDTO();

        mailDTO.setEmailUid(IDUtils.getRandomUUID());
        mailDTO.setRelUid(issueInfo.getString("issueId"));
        mailDTO.setTit(getTitle(issueInfo, issueStuInfo));
        mailDTO.setContent(getContent(issueInfo, issueStuInfo));
        mailDTO.setSend(BaseProperties.get("sys.admin.email"));
        mailDTO.setRec(getRec(issueInfo, issueStuInfo));
        mailDTO.setHtmlUseYn("Y");

        return mailDTO;
    }

    private String getRec(ParamMap<String, Object> issueInfo,
            IssueStuInfo issueStuInfo) {
        String recType = issueStuInfo.getMailRecType();
        List<ParamMap<String, Object>> mailList;
        if ("mgr".equals(recType)) {
            mailList = mngSvc.getManagerMailList(issueInfo);
        } else {
            mailList = mngSvc.getReqMailList(issueInfo);
        }

        if(mailList == null && mailList.isEmpty()){
            return null;
        }

        StringBuilder sbMail = new StringBuilder();
        int chkIdx = 0;
        for (ParamMap<String, Object> mailInfo : mailList) {
            if (chkIdx != 0) {
                sbMail.append(",");
            }
            /*
             * 메일주소 형식
             * "%USER_NAME%" <%EMAIL_ADDRESS%>
             */
            sbMail.append("\"").append(mailInfo.getString("mailNm"))
                    .append("\"");
            sbMail.append("<").append(mailInfo.getString("email")).append(">");
            chkIdx += 1;
        }

        return sbMail.toString();
    }

    private String getContent(ParamMap<String, Object> issueInfo,
            IssueStuInfo issueStuInfo) {
        FetchForm fetchForm = new FetchForm(FORM_PATH);
        try {
            String title = getTitle(issueInfo, issueStuInfo);
            fetchForm.setParam("TITLE", title);
            fetchForm.setParam("CONTENT", title);
//            fetchForm.setParam("SITE_URL", title);
            return fetchForm.parseForm();
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    private String getTitle(ParamMap<String, Object> issueInfo,
            IssueStuInfo issueStuInfo) {
        StringBuilder sbTitle = new StringBuilder();
        sbTitle.append("[").append(issueInfo.getString("reqNo")).append("] ")
                .append(issueStuInfo.getMailTitlePostFix());
        return sbTitle.toString();
    }

    private <T> T getIssueInfo(String issueId) {
        Map<String, Object> param = new HashMap<>();
        param.put("issueId", issueId);
        return mngSvc.getIssueInfo(param);
    }
}
