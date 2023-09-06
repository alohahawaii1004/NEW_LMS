/*
 * @(#)CommentSvc.java     2023-04-04 오전 7:31
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
package com.vertexid.support.comment;

import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import org.apache.commons.lang3.StringUtils;
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
public class CommentSvc extends BaseSvc {
    public static final String NAMESPACE = "com.vertexid.support.comment.Comment";

    @Resource
    CmmDAO cmmDAO;

    @Transactional(readOnly = true)
    public <E> List<E> getList(Map<String, Object> param) {
        return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "getList"), param);
    }

    public String insertCmt(CommentDTO param) {
        if(chkInsert(param)){
            insertData(param);
        }
        return null;
    }

    private void insertData(CommentDTO param) {
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insert"), param);
    }

    private boolean chkInsert(CommentDTO param) {
        String msg;
        if(StringUtils.isEmpty(param.getCmtId())){
            msg = "cmt id 가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        if(StringUtils.isEmpty(param.getRelId())){
            msg = "rel id 가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        if(StringUtils.isEmpty(param.getRelTpCd())){
            msg = "rel tp cd 가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        if(StringUtils.isEmpty(param.getCmtConts())){
            msg = "cmt conts 가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        return true;
    }
}
