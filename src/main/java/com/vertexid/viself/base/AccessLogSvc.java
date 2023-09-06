/*
 * @(#)AccessLogSvc.java     2023-01-18(018) 오후 3:31:44
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
package com.vertexid.viself.base;

import com.vertexid.commons.utils.IDUtils;
import com.vertexid.viself.hr.SysLoginVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

import static com.vertexid.viself.base.ActionType.*;

/**
 * <b>Description</b>
 * <pre>
 *     접근로그 기록 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class AccessLogSvc extends BaseSvc{

    public static final String NAMESPACE = "com.vertexid.viself.log.AccessLog";

    private static final String PACKAGE_SEPERATOR = ".";

    @Resource
    CmmDAO cmmDAO;
    public void writeLog(Map<String, Object> params) {

        Object loginInfo = params.get("LOGIN_INFO");
        SysLoginVO sysLoginVO;
        if (loginInfo instanceof SysLoginVO) {
            sysLoginVO = (SysLoginVO) loginInfo;
            String fullCtrl = params.get("CONTROLLER").toString();
            String pkg = StringUtils.substringBeforeLast(fullCtrl,
                    PACKAGE_SEPERATOR);
            String ctrl = StringUtils.remove(fullCtrl, pkg + PACKAGE_SEPERATOR);

            AccessLogDTO inParam = new AccessLogDTO();
            inParam.setLogUid(IDUtils.getRandomUUID());
            inParam.setLoginId(sysLoginVO.getLoginId());
            inParam.setAccesIp(sysLoginVO.getLoginIp());
            inParam.setPkgNm(pkg);
            inParam.setCtrlNm(ctrl + ".java");
            inParam.setSessionId(String.valueOf(params.get("SESSION ID")));
            inParam.setCharEnc(String.valueOf(params.get("CHAR ENCODING")));
            inParam.setContextPath(String.valueOf(params.get("CONTEXT PATH")));
            inParam.setReqUri(String.valueOf(params.get("REQUEST_URI")));
            inParam.setHttpMethod(String.valueOf(params.get("HTTP_METHOD")));
            inParam.setMethodNm(String.valueOf(params.get("METHOD")));
            inParam.setActionTp(String.valueOf(params.get("ACTION_TYPE")));
            inParam.setParam(String.valueOf(params.get("PARAMS")));
            inParam.setHeaderInfo(String.valueOf(params.get("HEADER INFO")));

            String actionType = inParam.getActionTp();

            if (actionType.equalsIgnoreCase(MODIFY.getActionId()) ||
                    actionType.equalsIgnoreCase(UPDATE.getActionId()) ||
                    actionType.equalsIgnoreCase(INSERT.getActionId()) ||
                    actionType.equalsIgnoreCase(SAVE.getActionId()) ||
                    actionType.equalsIgnoreCase(DELETE.getActionId()) ||
                    actionType.equalsIgnoreCase("doProc")) {
//                insertData(inParam);
            }
        }
    }

    private void insertData(AccessLogDTO inParam) {
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insert"), inParam);
    }
}
