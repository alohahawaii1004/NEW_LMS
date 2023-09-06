/*
 * @(#)IssueRsltSvc.java     2023-03-11 011 오후 7:40:45
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
public class IssueRsltSvc extends BaseSvc {

    public static final String NAMESPACE = "com.vertexid.support.issue.IssueRslt";

    @Resource
    CmmDAO cmmDAO;

    public void insertRslt(IssueRsltDTO param) {
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insertRslt"), param);
    }

    public void insertCfm(IssueRsltDTO param) {
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "insertCfm"), param);
    }

    public void deleteRslt(IssueRsltDTO param) {
        cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "deleteRslt"), param);
    }
}
