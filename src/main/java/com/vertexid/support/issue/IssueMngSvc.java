/*
 * @(#)IssueMngSvc.java     2023-03-11 011 오후 7:53:45
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

import com.vertexid.commons.utils.IDUtils;
import com.vertexid.commons.utils.ParamMap;
import com.vertexid.paragon.file.FileSvc;
import com.vertexid.support.system.SysMgrDTO;
import com.vertexid.support.system.SysMgrMngSvc;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import com.vertexid.viself.hr.SysLoginVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
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
public class IssueMngSvc extends BaseSvc {

    public static final String NAMESPACE =
            "com.vertexid.support.issue.IssueMng";

    @Resource
    CmmDAO cmmDAO;

    @Resource
    IssueReqSvc issueReqSvc;

    @Resource
    IssueRsltSvc issueRsltSvc;

    @Resource
    SysMgrMngSvc sysMgrMngSvc;

    @Resource
    IssueHisSvc issueHisSvc;

    @Resource
    FileSvc fileSvc;

    /**
     * 이슈요청/수정
     * @param param 이슈정보
     * @return 오류메시지
     */
    public String insertReq(IssueReqDTO param) {

        if (chkReq(param)) {

            String historyConts = "";
            ParamMap<String, Object> data = getData(param);
            if (data == null) {
                historyConts = "이슈요청";
                issueReqSvc.insertReq(param);
            } else {
                historyConts = "이슈요청수정";
                issueReqSvc.updateReq(param);
            }

            // 이력기록
            IssueHisDTO issueHisDTO = new IssueHisDTO();
            issueHisDTO.setIssueId(param.getIssueId());
            issueHisDTO.setContent(historyConts);
            issueHisDTO.setIssueStuCd(param.getIssueStuCd());
            issueHisDTO.setRequestParam(param.toString());
            writeHistory(issueHisDTO);

            // TODO 메일발송 -> 담당자
        }

        return null;
    }

    /**
     * 이슈 이력 기록
     * @param param 이슈 이력
     */
    private void writeHistory(IssueHisDTO param) {
        issueHisSvc.insertHis(param);
    }

    private boolean chkReq(IssueReqDTO param) {
        String msg;
        if (StringUtils.isEmpty(param.getSystemId())) {
            msg = "systemId 가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }
        if (StringUtils.isEmpty(param.getReqIssueTitle())) {
            msg = "issue title 이 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        ParamMap<String, Object> data = getData(param);
        if (data == null) {
            return true;
        } else {
            SysLoginVO loingInfo = (SysLoginVO) param.getLoginInfo();
            if (data != null &&
                    !(loingInfo.getLoginId()).equals(data.getString("reqId"))) {
                msg = "해당 서비스 요청자가 아닙니다.";
                log.error(msg);
                throw new RuntimeException(msg,
                        new Throwable("loingInfo..." + loingInfo.getLoginId()));
            }
        }

        return true;
    }

    @Transactional(readOnly = true)
    public <T> T getIssueInfo(Map<String, Object> param) {
        return cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "getIssueInfo"),
                param);
    }

    /**
     * 이슈 요청 접수
     * @param param 접수 정보
     * @return 오류메시지
     */
    public String insertRcv(IssueReqDTO param) {
        if (chkRcv(param)) {
            issueReqSvc.insertRcv(param);

            // 이력기록
            IssueHisDTO issueHisDTO = new IssueHisDTO();
            issueHisDTO.setIssueId(param.getIssueId());
            issueHisDTO.setIssueStuCd(param.getIssueStuCd());
            issueHisDTO.setContent("이슈접수");
            issueHisDTO.setRequestParam(param.toString());
            writeHistory(issueHisDTO);

            // TODO 메일발송 -> 요청자
        }

        return null;
    }

    private boolean chkRcv(IssueReqDTO param) {
        String msg;
        if (StringUtils.isEmpty(param.getIssueId())) {
            msg = "issue id 가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        ParamMap<String, Object> data = getData(param);
        if (!("10").equals(data.getString("issueStuCd"))) {
            msg = "접수할 수 있는 상태가 아닙니다.";
            log.error(msg);
            throw new RuntimeException(msg, new Throwable(
                    "issueSutCd..." + data.getString("issueStuCd")));
        }
        SysLoginVO loingInfo = (SysLoginVO) param.getLoginInfo();
        SysMgrDTO mgrInfo = getMgrInfo(data, loingInfo);
        if (mgrInfo == null) {
            msg = "해당 서비스 담당자가 아닙니다.";
            log.error(msg);
            throw new RuntimeException(msg,
                    new Throwable("loingInfo..." + loingInfo.getLoginId()));
        }

        return true;
    }

    @Transactional(readOnly = true)
    public <T> T getMgrInfo(ParamMap<String, Object> issueData,
            SysLoginVO loginInfo) {
        SysMgrDTO param = new SysMgrDTO();
        param.setSystemId(issueData.getString("systemId"));
        param.setLoginId(loginInfo.getLoginId());
        return sysMgrMngSvc.getMgrData(param);
    }

    @Transactional(readOnly = true)
    public <T> T getData(IssueReqDTO param) {
        return cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "getIssueInfo"),
                param);
    }

    /**
     * 이슈 결과 보고
     * @param param 결과 정보
     * @return 오류 메시지
     */
    public String insertRslt(IssueRsltDTO param) {

        if (chkRslt(param)) {
            // 1. 결과 입력
            // 1.1. 과거 데이터 삭제
            issueRsltSvc.deleteRslt(param);
            deleteOldCfmFile(param);

            // 1.2. 신규 결과 입력
            issueRsltSvc.insertRslt(param);

            // 상태변경
            IssueReqDTO issueReqDTO = new IssueReqDTO();
            issueReqDTO.setIssueId(param.getIssueId());
            issueReqDTO.setIssueStuCd("80");
            issueReqSvc.updateStu(issueReqDTO);

            // 이력기록
            IssueHisDTO issueHisDTO = new IssueHisDTO();
            issueHisDTO.setIssueId(param.getIssueId());
            issueHisDTO.setContent("이슈완료");
            issueHisDTO.setIssueStuCd(issueReqDTO.getIssueStuCd());
            issueHisDTO.setRequestParam(param.toString());
            writeHistory(issueHisDTO);

            // TODO 메일발송 -> 요청자
        }

        return null;
    }

    private void deleteOldCfmFile(IssueRsltDTO param) {
        Map<String, Object> fileParam = new HashMap<>();
        fileParam.put("relUid", param.getIssueId());
        fileParam.put("fileTpCd", "issue/cfm");
        fileSvc.deleteRelFiles(fileParam);
    }

    private boolean chkRslt(IssueRsltDTO param) {
        String msg;
        if (StringUtils.isEmpty(param.getIssueId())) {
            msg = "issue id 가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        IssueReqDTO issueReqDTO = new IssueReqDTO();
        issueReqDTO.setIssueId(param.getIssueId());
        ParamMap<String, Object> data = getData(issueReqDTO);
        if (!("50").equals(data.getString("issueStuCd")) &&
                !("91").equals(data.getString("issueStuCd"))) {
            msg = "완료보고할 수 있는 상태가 아닙니다.";
            log.error(msg);
            throw new RuntimeException(msg, new Throwable(
                    "issueSutCd..." + data.getString("issueStuCd")));
        }
        SysLoginVO loingInfo = (SysLoginVO) param.getLoginInfo();
        SysMgrDTO mgrInfo = getMgrInfo(data, loingInfo);
        if (mgrInfo == null) {
            msg = "해당 서비스 담당자가 아닙니다.";
            log.error(msg);
            throw new RuntimeException(msg,
                    new Throwable("loingInfo..." + loingInfo.getLoginId()));
        }

        return true;
    }

    /**
     * 이슈 검수
     * @param param 검수 정보
     * @return 오류 메시지
     */
    public String insertCfm(IssueRsltDTO param) {
        if (chkCfm(param)) {
            issueRsltSvc.insertCfm(param);

            IssueReqDTO issueReqDTO = new IssueReqDTO();
            issueReqDTO.setIssueId(param.getIssueId());
            issueReqDTO.setIssueStuCd(param.getCfmTpCd());
            issueReqSvc.updateStu(issueReqDTO);

            // 이력기록
            IssueHisDTO issueHisDTO = new IssueHisDTO();
            issueHisDTO.setIssueId(param.getIssueId());
            issueHisDTO.setContent("이슈검수");
            issueHisDTO.setIssueStuCd(issueReqDTO.getIssueStuCd());
            issueHisDTO.setRequestParam(param.toString());
            writeHistory(issueHisDTO);

            // TODO 메일발송 -> 담당자
        }

        return null;
    }

    private boolean chkCfm(IssueRsltDTO param) {
        String msg;
        if (StringUtils.isEmpty(param.getIssueId())) {
            msg = "issue id 가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        IssueReqDTO issueReqDTO = new IssueReqDTO();
        issueReqDTO.setIssueId(param.getIssueId());
        ParamMap<String, Object> data = getData(issueReqDTO);
        SysLoginVO loingInfo = (SysLoginVO) param.getLoginInfo();
        if (!(loingInfo.getLoginId()).equals(data.getString("reqId"))) {
            msg = "해당 서비스 요청자가 아닙니다.";
            log.error(msg);
            throw new RuntimeException(msg,
                    new Throwable("loingInfo..." + loingInfo.getLoginId()));
        }

        return true;
    }

    private boolean chkDelete(IssueReqDTO param) {
        String msg;
        if (StringUtils.isEmpty(param.getIssueId())) {
            msg = "issue id 가 없습니다.";
            log.error(msg);
            throw new RuntimeException(msg);
        }

        ParamMap<String, Object> data = getData(param);
        SysLoginVO loingInfo = (SysLoginVO) param.getLoginInfo();
        if (!(loingInfo.getLoginId()).equals(data.getString("reqId"))) {
            msg = "해당 서비스 요청자가 아닙니다.";
            log.error(msg);
            throw new RuntimeException(msg,
                    new Throwable("loingInfo..." + loingInfo.getLoginId()));
        }

        return true;
    }


    /**
     * 이슈 삭제
     * @param param 이슈 정보
     * @return 오류 메시지
     */
    public String deleteIssue(IssueReqDTO param) {
        if (chkDelete(param)) {
            issueReqSvc.deleteIssue(param);

            // 이력기록
            IssueHisDTO issueHisDTO = new IssueHisDTO();
            issueHisDTO.setIssueId(param.getIssueId());
            issueHisDTO.setIssueStuCd("");
            issueHisDTO.setContent("이슈삭제");
            issueHisDTO.setRequestParam(param.toString());
            writeHistory(issueHisDTO);

            // TODO 메일발송 -> 담당자
        }

        return null;
    }

    @Transactional(readOnly = true)
    public List<ParamMap<String, Object>> getManagerMailList(
            Map<String, Object> param) {
        return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "getIssueMgrMailInfo"), param);
    }

    @Transactional(readOnly = true)
    public List<ParamMap<String, Object>> getReqMailList(Map<String, Object> param) {
        return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "getIssueReqMailInfo"), param);
    }

    @Transactional(readOnly = true)
    public <T> T getStatsData(Map<String, Object> param) {
        return cmmDAO.getOne(cmmDAO.getStmtByNS(NAMESPACE, "getStatsData"), param);
    }
}
