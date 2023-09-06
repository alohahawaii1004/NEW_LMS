/*
 * @(#)IssueCmtRelMasSvc.java     2023-06-19(019) 오전 8:38
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
package com.vertexid.support.issue;

import com.vertexid.commons.utils.FetchForm;
import com.vertexid.commons.utils.IDUtils;
import com.vertexid.commons.utils.ParamMap;
import com.vertexid.paragon.mail.MailDTO;
import com.vertexid.spring.utils.BaseProperties;
import com.vertexid.support.comment.CommentDTO;
import com.vertexid.support.comment.DefaultCommentRelMasSvc;
import com.vertexid.viself.hr.SysLoginVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service
@Transactional
public class IssueCmtRelMasSvc extends DefaultCommentRelMasSvc {

    public static final String REL_MAS_TYPE = "ISSUE";

    @Override
    public String getType() {
        return REL_MAS_TYPE;
    }

    @Resource
    IssueMngSvc mngSvc;

    @Override
    public MailDTO getMailDTO(CommentDTO param) {
        MailDTO mailDTO = new MailDTO();

        Map<String, Object> sParam = new HashMap<>();
        sParam.put("issueUid", param.getRelId());
        ParamMap<String, Object> issueInfo = mngSvc.getIssueInfo(sParam);
        if(issueInfo == null || issueInfo.isEmpty()){
            String msg = "관련 이슈 정보가 없습니다. "+sParam;
            log.warn(msg);
        }else{

            SysLoginVO writerInfo = (SysLoginVO)param.getLoginInfo();
            if(writerInfo != null){
                mailDTO.setEmailUid(IDUtils.getRandomUUID());
                mailDTO.setRelUid(param.getRelId());
                mailDTO.setTit(getTitle(issueInfo));
                mailDTO.setContent(getContent(issueInfo));
                mailDTO.setSend(BaseProperties.get("sys.admin.email"));
                mailDTO.setRec(getRec(issueInfo, writerInfo));
                mailDTO.setHtmlUseYn("Y");
            }else{
                String msg = "작성자 정보가 없습니다. "+param;
                log.warn(msg);
            }
        }
        return mailDTO;
    }

    private String getTitle(ParamMap<String, Object> issueInfo) {
        return "[" + issueInfo.getString("reqNo") + "] " +
                "이슈에 관련 신규 Comment 가 있습니다.";
    }

    private String getContent(ParamMap<String, Object> issueInfo) {
        FetchForm fetchForm = new FetchForm(FORM_PATH);
        try {
            String title = getTitle(issueInfo);
            fetchForm.setParam("TITLE", title);
            fetchForm.setParam("CONTENT", title);
//            fetchForm.setParam("SITE_URL", title);
            return fetchForm.parseForm();
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    private String getRec(ParamMap<String, Object> issueInfo,
            SysLoginVO writerInfo) {

        String recType = "mgr";
        String reqId = issueInfo.getString("reqId");
        if(reqId.equals(writerInfo.getLoginId())){
            recType = "req";
        }

        List<ParamMap<String, Object>> mailList;
        if ("mgr".equals(recType)) {
            mailList = mngSvc.getManagerMailList(issueInfo);
        } else {
            mailList = mngSvc.getReqMailList(issueInfo);
        }

        if(mailList == null || mailList.isEmpty()){
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
}
