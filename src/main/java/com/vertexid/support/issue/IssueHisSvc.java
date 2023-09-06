/*
 * @(#)IssueHisSvc.java     2023-04-03 오후 2:16
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

import javax.annotation.Resource;

/**
 * <b>Description</b>
 * <pre>
 *     이슈 이력 서비스
 *     ! MySQL 의 경우 MyISAM 엔진으로 처리됨
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
public class IssueHisSvc extends BaseSvc {

    public static final String NAMESPACE =
            "com.vertexid.support.issue.IssueHis";

    @Resource
    CmmDAO cmmDAO;

    public void insertHis(IssueHisDTO param){
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insertHis"), param);
    }
}
