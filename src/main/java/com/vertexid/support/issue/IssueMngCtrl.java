/*
 * @(#)IssueMngCtrl.java     2023-03-11 011 오후 7:39:01
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

import com.vertexid.mybatis.ListPerPageFunction;
import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.base.BaseCtrl;
import com.vertexid.viself.base.PagingParamFunction;
import com.vertexid.viself.hr.SysLoginVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Map;

import static com.vertexid.commons.view.ViewType.JSON_VIEW;
import static com.vertexid.support.issue.IssueStuInfo.*;
import static com.vertexid.viself.base.ModelAttribute.DATA;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class IssueMngCtrl extends BaseCtrl {

    private static final String SUPPORT_MNGS = "CMM_SYS,SM_MNG,SM_ADMIN";
    private static final String NORMAL_USER_URL =
            "redirect:/support/issue/issueList.include.tiles";
    private static final String SM_MGR_URL =
            "redirect:/support/issue/issueMngList.include.tiles";


    @Resource
    ListPerPageFunction listPerPageFunction;

    @Resource
    PagingParamFunction pagingParamFunction;

    @Resource
    IssueMngSvc issueMngSvc;

    @Resource
    IssueMailSvc issueMailSvc;


    /**
     * 이슈리스트 페이지 이동
     *
     * @param modelMap model
     * @param extParam parameters
     * @return view
     */
    @RequestMapping(value = "/support/issue/issueList.include",
            method = RequestMethod.POST)
    public String goIssueList(ModelMap modelMap,
            @RequestParam
            Map<String, Object> extParam) {

        SysLoginVO loingInfo = (SysLoginVO) SessionUtils.getLoginVO();
        String redirectUrl = NORMAL_USER_URL;
//        String redirectUrl = SM_MGR_URL;
        if (loingInfo.containsAuthorities(SUPPORT_MNGS)) {
            redirectUrl = SM_MGR_URL;
        }

        return redirectUrl;
    }

    /**
     * 이슈 리스트
     *
     * @param modelMap model
     * @param param    parameters
     * @return view
     */
    @RequestMapping(value = "/support/issue/listPerPage/json",
            method = RequestMethod.POST)
    public String getListPerPage(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {

        modelMap.clear();
        SysLoginVO loingInfo = (SysLoginVO) SessionUtils.getLoginVO();
        param.put("loginInfo", loingInfo);
        pagingParamFunction.setOrderParam(param);
        listPerPageFunction.setNamespace(IssueMngSvc.NAMESPACE);
        modelMap.addAttribute(DATA.getAttributeId(),
                listPerPageFunction.getListPerPage(param));

        return JSON_VIEW.getViewId();
    }

    /**
     * 이슈 정보 얻기
     *
     * @param modelMap model
     * @param param    parameters
     * @return view
     */
    @RequestMapping(value = "/support/issue/viewInfo/json",
            method = RequestMethod.POST)
    public String getIssueViewInfo(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {
        modelMap.clear();
        SysLoginVO loingInfo = (SysLoginVO) SessionUtils.getLoginVO();
        param.put("loginInfo", loingInfo);

        // 1. get issue info
        modelMap.addAttribute(DATA.getAttributeId(),
                setAction(issueMngSvc.getIssueInfo(param)));

        return JSON_VIEW.getViewId();
    }

    /**
     * 이슈 현황 얻기
     *
     * @param modelMap model
     * @param param    parameters
     * @return view
     */
    @RequestMapping(value = "/support/issue/getStatsData/json",
            method = RequestMethod.POST)
    public String getStatsData(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {
        modelMap.clear();
        SysLoginVO loingInfo = (SysLoginVO) SessionUtils.getLoginVO();
        param.put("loginInfo", loingInfo);

        // 1. get issue info
        modelMap.addAttribute(DATA.getAttributeId(),
                setAction(issueMngSvc.getStatsData(param)));

        return JSON_VIEW.getViewId();
    }


    /**
     * 이슈 요청
     *
     * @param modelMap model
     * @param param    parameters
     * @return view
     */
    @RequestMapping(value = "/support/issue/insertReq/json",
            method = RequestMethod.POST)
    public String insertReq(ModelMap modelMap,
            @RequestBody
            IssueReqDTO param) {
        param.setIssueStuCd("10");
        String rslt = issueMngSvc.insertReq(param);

        // 메일발송
        if (StringUtils.isEmpty(rslt)) {
            issueMailSvc.insertMail(param.getIssueId(), REQ.getStuId());
        }

        return getTransactionJsonView(modelMap, rslt);
    }

    /**
     * 이슈 삭제
     *
     * @param modelMap model
     * @param param    parameters
     * @return view
     */
    @RequestMapping(value = "/support/issue/deleteIssue/json",
            method = RequestMethod.POST)
    public String deleteIssue(ModelMap modelMap,
            @RequestBody
            IssueReqDTO param) {

        String rslt = issueMngSvc.deleteIssue(param);
        // 메일발송
//        if (StringUtils.isEmpty(rslt)) {
//            issueMailSvc.insertMail(param.getIssueId(), DEL.getStuId());
//        }
        return getTransactionJsonView(modelMap, rslt);
    }

    /**
     * 이슈 접수
     *
     * @param modelMap model
     * @param param    parameters
     * @return view
     */
    @RequestMapping(value = "/support/issue/insertRcv/json",
            method = RequestMethod.POST)
    public String insertRcv(ModelMap modelMap,
            @RequestBody
            IssueReqDTO param) {
        param.setIssueStuCd("50");
        String rslt = issueMngSvc.insertRcv(param);
        // 메일발송
        if (StringUtils.isEmpty(rslt)) {
            issueMailSvc.insertMail(param.getIssueId(), RCV.getStuId());
        }
        return getTransactionJsonView(modelMap, rslt);
    }

    /**
     * 이슈 완료
     *
     * @param modelMap model
     * @param param    parameters
     * @return view
     */
    @RequestMapping(value = "/support/issue/insertRslt/json",
            method = RequestMethod.POST)
    public String insertRslt(ModelMap modelMap,
            @RequestBody
            IssueRsltDTO param) {

        String rslt = issueMngSvc.insertRslt(param);
        // 메일발송
        if (StringUtils.isEmpty(rslt)) {
            issueMailSvc.insertMail(param.getIssueId(), RSLT.getStuId());
        }
        return getTransactionJsonView(modelMap, rslt);
    }

    /**
     * 이슈 검수
     *
     * @param modelMap model
     * @param param    parameters
     * @return view
     */
    @RequestMapping(value = "/support/issue/insertCfm/json",
            method = RequestMethod.POST)
    public String insertCfm(ModelMap modelMap,
            @RequestBody
            IssueRsltDTO param) {
        param.setCfmTpCd("90");
        String rslt = issueMngSvc.insertCfm(param);
        // 메일발송
//        if (StringUtils.isEmpty(rslt)) {
//            issueMailSvc.insertMail(param.getIssueId(), CFM.getStuId());
//        }

        return getTransactionJsonView(modelMap, rslt);
    }

    /**
     * 이슈 반려
     *
     * @param modelMap model
     * @param param    parameters
     * @return view
     */
    @RequestMapping(value = "/support/issue/insertReject/json",
            method = RequestMethod.POST)
    public String insertReject(ModelMap modelMap,
            @RequestBody
            IssueRsltDTO param) {
        param.setCfmTpCd("91");
        String rslt = issueMngSvc.insertCfm(param);
        // 메일발송
        if (StringUtils.isEmpty(rslt)) {
            issueMailSvc.insertMail(param.getIssueId(), CFM.getStuId());
        }
        return getTransactionJsonView(modelMap, rslt);
    }


    /**
     * 액션 설정
     *
     * @param issueInfo 이슈정보
     * @return 액션정보
     */
    private Map<String, Object> setAction(Map<String, Object> issueInfo) {

        if (issueInfo == null) {
            return null;
        }

        String issueStuCd = String.valueOf(issueInfo.get("issueStuCd"));
        String vTp1Cd = String.valueOf(issueInfo.get("vTp1Cd"));
        String vTp2Cd = String.valueOf(issueInfo.get("vTp2Cd"));

        if ("NONE".equals(vTp1Cd) && "NONE".equals(vTp2Cd)) {
            return issueInfo;
        }

        switch (issueStuCd) {
            case "10": // 접수대기
                StringBuilder sbAction = new StringBuilder();
                int idx = 0;
                if ("REQ".equals(vTp1Cd)) {
                    // 삭제
                    sbAction.append("DELETE,MODIFY");
                    idx += 1;
                }
                if ("RCV".equals(vTp2Cd)) {
                    // 접수
                    if (idx != 0) {
                        sbAction.append(",");
                    }
                    sbAction.append("RCV");
                }
                issueInfo.put("nextAction", sbAction.toString());
                break;
            case "50": // 처리중
            case "91": // 검수반려
                if ("RCV".equals(vTp2Cd)) {
                    // 결과작성
                    issueInfo.put("nextAction", "RSLT");
                }
                break;
            case "80": // 검수대기
                if ("REQ".equals(vTp1Cd)) {
                    // 검수 작성
                    issueInfo.put("nextAction", "CFM");
                }
                break;
            default:
                break;
        }
        return issueInfo;
    }

}
