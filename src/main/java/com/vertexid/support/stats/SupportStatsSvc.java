/*
 * @(#)SupportStatsSvc.java     2023-06-09(009) 오전 11:09
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
package com.vertexid.support.stats;

import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class SupportStatsSvc extends BaseSvc {

    public static final String NAMESPACE = "com.vertexid.support.stats.Stats";

    @Resource
    CmmDAO cmmDAO;

    @Transactional(readOnly = true)
    public <E> List<E> getManagerStats(Map<String, Object> param) {
        return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "managerStats"), param);
    }

    @Transactional(readOnly = true)
    public <E> List<E> getSystemStats(Map<String, Object> param) {
        return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "systemStats"), param);
    }

    @Transactional(readOnly = true)
    public <E> List<E> getIssueTpStats(Map<String, Object> param) {
        return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "issueTypeStats"), param);
    }

    public void insertFact(Map<String, Object> param) {
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insertFact"), param);
    }
}
