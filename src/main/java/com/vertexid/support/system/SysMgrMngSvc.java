/*
 * @(#)SysMgrMngSvc.java     2023-03-29 오전 9:28
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
package com.vertexid.support.system;

import com.vertexid.commons.utils.ParamMap;
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
 *     서비스 담당자 svc
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class SysMgrMngSvc extends BaseSvc {

    public static final String NAMESPACE =
            "com.vertexid.support.system.SysMgrMng";

    @Resource
    CmmDAO cmmDAO;

    /**
     * 토글 속성에 대한 데이터 업데이트
     * @param target 토글속성(mailSndYn, priorityNm)
     * @param param 담당자 정보(SystemId, LoginId)
     * @return 오류 메시지
     */
    public String update(String target, SysMgrDTO param) {

        SysMgrDTO data = getMgrData(param);
        if(null == data){
            String msg = "데이터가 존재하지 않습니다.";
            log.error(msg);
            throw new RuntimeException(msg, new Throwable("param:"+param));
        }

        switch (target){
            case "priorityNm":
                if("1".equals(data.getPriority())){
                    data.setPriority("2");
                }else{
                    data.setPriority("1");
                }
                updateMgrData(data);
                break;
            case "mailSndYn":
                if("Y".equals(data.getMailSndYn())){
                    data.setMailSndYn("N");
                }else{
                    data.setMailSndYn("Y");
                }
                updateMgrData(data);
                break;
            default:
                String msg = "지원하지 않는 유형입니다.";
                log.error(msg);
                throw new RuntimeException(msg, new Throwable("target:"+target));
        }

        return null;
    }

    private int updateMgrData(SysMgrDTO param) {
        return cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "update"), param);
    }

    @Transactional(readOnly = true)
    public <T> T getMgrData(SysMgrDTO param) {
        return cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "getData"), param);
    }

    public String delete(SysMgrDTO param) {
        cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "delete"), param);
        return null;
    }

    public String addManagers(SysMgrDTO param) {

        List<SysMgrDTO> list = param.getList();
        if(list == null || list.isEmpty()){
            String msg = "데이터가 없습니다.";
            throw new RuntimeException(msg, new Throwable("param.list....."+list));
        }

        for(SysMgrDTO itm: list){
            insertManager(itm);
        }// end of for

        return null;
    }

    private int insertManager(SysMgrDTO param) {
        return cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insert"), param);
    }
}
