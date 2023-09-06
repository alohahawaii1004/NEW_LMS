/*
 * @(#)IssueReqSvc.java     2023-03-11 011 오후 7:39:55
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

import com.vertexid.commons.utils.ParamMap;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
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
public class IssueReqSvc extends BaseSvc {

    public static final String NAMESPACE = "com.vertexid.support.issue.IssueReq";

    @Resource
    CmmDAO cmmDAO;

    public void insertReq(IssueReqDTO param) {
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insertReq"), param);
    }


    public void insertRcv(IssueReqDTO param) {
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "insertRcv"), param);
    }

    public void updateStu(IssueReqDTO param) {
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updateStu"), param);
    }

    public void deleteIssue(IssueReqDTO param) {
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "deleteIssue"), param);
    }

    public void updateReq(IssueReqDTO param) {
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updateReq"), param);
    }
}
