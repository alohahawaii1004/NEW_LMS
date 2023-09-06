/*
 * @(#)BbsMasSvc.java     2022-07-28(028) 오전 9:54:32
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
package com.vertexid.paragon.bbs;

import com.vertexid.commons.utils.ParamMap;
import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import com.vertexid.viself.hr.SysLoginVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 *     게시판 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class BbsMasSvc extends BaseSvc {

    public static final String NAMESPACE = "com.vertexid.paragon.bbs.BbsMas";

    @Resource(name = "cmmDAO")
    private CmmDAO cmmDAO;

    public String getNewReqNo(BbsMasDTO bbsMasDTO) {
        return cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "maxRegNo"), bbsMasDTO);
    }


    //-- 작성자 및 관리자 체크
    public boolean AuthCheck(Map<String, Object> params) {
        boolean result = true;
        SysLoginVO loginUser =     (SysLoginVO) SessionUtils.getLoginVO();
        ParamMap<String,Object> resultMap = cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "selectOne"), params);

        if(!loginUser.getLoginId().equals(resultMap.getString("regLoginId"))) {
            result = false;
        }

        if(!"UPDATE".equals((String)params.get("bbsMode"))) {
            if(!result && loginUser.isSys()) {
                result = true;
            }
        }

        return result;
    }


    //게시글 저장
//    public String saveBbs(Map<String, Object> params) {
//        SysLoginVO loginUser =     (SysLoginVO) SessionUtils.getLoginVO();
//        if(loginUser.isChr() || loginUser.isSys()) {
//            params.put("loginInfo", loginUser);
//            cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insert"), params);
//        } else {
//            String msg = "M.권한이_없습니다.";
//            return msg;
//        }
//        return null;
//    }

    public String saveBbs(BbsMasDTO param) {

        // simple check
        if(StringUtils.isBlank(param.getBbsUid())){
            String msg = "BBS_UID 값이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        Map<String, Object> data = getData(param);
        if(null == data || data.isEmpty()){
            insertData(param);
        }else{
            updateData(param);
        }

        return null;
    }

    //게시글 수정
    public String updateBbs(Map<String, Object> params) {
        Map<String, Object> parmas = params;
        if(AuthCheck(parmas)) {
            cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "update"), params);
        } else {
            String msg = "M.권한이_없습니다.";
            return msg;
        }
        return null;
    }
    //게시글 삭제
    public String deleteBbs(Map<String, Object> params) {
        Map<String, Object> parmas = params;
        if(AuthCheck(parmas)) {
            cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "delete"), params);
        } else {
            String msg = "M.권한이_없습니다.";
            return msg;
        }
        return null;
    }
    //게시글 상세보기
    public ParamMap<String,Object> getbbsMasView(Map<String, Object> params) {
        return cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "selectOne"), params);
    }
    
     //권한 출력하기 
    public ParamMap<String, Object> getbbsAuth(Map<String, Object> params) { 
        return cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "selectAuth"), params); 
    }
     
    
    //게시글 리스트 조회
    public List<ParamMap<String,Object>> getList(Map<String, Object> params){
        return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "list"), params);

    }

    private void updateData(BbsMasDTO param) {
        if(checkUpdate(param)){
            update(param);
        }
    }

    private void update(BbsMasDTO param) {
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "update"), param);
    }

    private boolean checkUpdate(BbsMasDTO param) {
        String msg;
        if(StringUtils.isBlank(param.getBbsUid())){
            msg = "필수 값이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg, new Throwable("BBS_UID 값이 없습니다."));
        }

        Map<String, Object> data = getData(param);
        if(null == data || data.isEmpty()){
            msg = "데이터가 없습니다.(no data)";
            log.error(msg);
            throw new RuntimeException(msg, new Throwable("Update 할 대상이 없습니다."));
        }

        // REVIEW 작성자 검사 는 Controller 에서 하는 것으로

        return checkInsert(param);
    }

    @Transactional(readOnly = true)
    <T> T getData(Object param) {
        return cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "getData"), param);
    }

    private void insertData(BbsMasDTO param) {
        if(checkInsert(param)){
            insert(param);
        }
    }

    private void insert(BbsMasDTO param) {
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insert"), param);
    }

    private boolean checkInsert(BbsMasDTO param) {
        String msg;
        if(StringUtils.isBlank(param.getBbsTpCd())){
            msg = "필수 값이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg, new Throwable("BBS_TP 값이 없습니다."));
        }
        if(StringUtils.isBlank(param.getBbsTit())){
            msg = "필수 값이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg, new Throwable("TIT 값이 없습니다."));
        }
        return true;
    }

    public String deleteData(BbsMasDTO param) {
        if(checkDelete(param)){
            delete(param);
        }
        return null;
    }

    private void delete(BbsMasDTO param) {
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "delete"), param);
    }

    private boolean checkDelete(BbsMasDTO param) {
        String msg;
        if(StringUtils.isBlank(param.getBbsUid())){
            msg = "필수 값이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg, new Throwable("BBS_UID 값이 없습니다."));
        }
        return true;
    }

    public String addHit(BbsMasDTO param) {
        if(checkDelete(param)){
            updateHit(param);
        }

        return null;
    }

    private void updateHit(BbsMasDTO param) {
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updateHit"), param);
    }


}
