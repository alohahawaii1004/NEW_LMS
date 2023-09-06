/*
 * @(#)SysIfLogSvc.java     2023-01-03
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
package com.vertexid.paragon.security;

import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <b>Description</b>
 * <pre>
 *     인터페이스 로그 Service
 * </pre>
 *
 * @author kdk
 */
@Service
@Transactional
public class SysIfLogSvc extends BaseSvc {
    public static final String NAMESPACE = "com.vertexid.paragon.log.SysIfLog";

    @Resource
    CmmDAO cmmDAO;

    public String saveData(SysIfLogDTO param) {
    	insert(param);
        return null;
    }


    private void insert(SysIfLogDTO param) {
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insert"), param);
    }
}
