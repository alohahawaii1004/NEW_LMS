/*
 * @(#)SimpleAuthoritySvc.java     2021-01-13(013) 오전 10:20
 *
 * Copyright 2021 JAYU.space
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
package com.vertexid.viself.login;

import com.vertexid.viself.security.AuthoritySvc;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 *     권한 정보 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class SimpleAuthoritySvc extends BaseSvc implements AuthoritySvc {

    private static final String NAMESPACE =
            "com.vertexid.viself.auth.AuthMember";
    private String errMsg;

    @Resource(name = "cmmDAO")
    private CmmDAO cmmDAO;

    @Override
    @Transactional(readOnly = true)
    public <E> List<E> getAuthorities(String username) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("authMemberId", username);
        params.put("authMemberTpCd", "USER");

        return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "getAuthorities"),
                params);
    }
}
