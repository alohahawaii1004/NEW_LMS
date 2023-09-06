/*
 * @(#)AuthMngCtrl.java     2022-03-14(014) 오후 4:12
 *
 * Copyright 2022 JAYU.space
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
package com.vertexid.viself.auth;

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
import static com.vertexid.viself.base.MessageCode.COMPLETE;
import static com.vertexid.viself.base.MessageCode.ERROR;
import static com.vertexid.viself.base.ModelAttribute.*;

// 윤태영
import com.vertexid.spring.data.NexacroResult; 
import com.vertexid.spring.annotation.ParamDataSet;
import com.vertexid.viself.ma.ItemMstMngVo;
import com.vertexid.viself.ma.ItemMstMngSvc; 
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * <b>Description</b>
 * <pre>
 *     권한 관리 컨트롤러
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 * @author 윤태영 : 넥사크로 F/W 신규작업  
 */
@Controller
public class AuthMngCtrl extends BaseCtrl {

    @Resource
    AuthMngSvc authMngSvc;

    @Resource
    ListPerPageFunction listPerPageFunction;

    @Resource
    PagingParamFunction pagingParamFunction;

    @Resource
    private ReloadSecurityDataFunction reloadSecurityDataFunction;
    
    @Qualifier("com.vertexid.support.cmm.ItemMstMngSvc")
	private ItemMstMngSvc itemMstMngSvc;
    

    @RequestMapping(value = "/viself/auth/AuthMng/listPerPage/json",
            method = RequestMethod.POST)
    public String getListPerPage(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {

        modelMap.clear();
        SysLoginVO loingInfo = (SysLoginVO) SessionUtils.getLoginVO();
        log.info("윤태영1............." + loingInfo);
        
        
        param.put("loginInfo", loingInfo);
        pagingParamFunction.setOrderParam(param);
        listPerPageFunction.setNamespace(AuthMngSvc.NAMESPACE);
        modelMap.addAttribute(DATA.getAttributeId(),
                listPerPageFunction.getListPerPage(param));

        return JSON_VIEW.getViewId();
    }
    
    
    /**
	 * 조회 : 윤태영  (차후 기준정보로 이동할 임시 메소드 입니다.) 
	 * @param searchVo
	 * @return NexacroResult
	 * @throws Exception
	 */
	@RequestMapping("/search.do")
	public NexacroResult search(@ParamDataSet(name = "dsSearch") ItemMstMngVo searchVo) throws Exception 
	{	
		List<ItemMstMngVo> list = null;
		
		list = itemMstMngSvc.selectList(searchVo);
		
		 SysLoginVO loingInfo = (SysLoginVO) SessionUtils.getLoginVO();
	     log.info("윤태영2............." + loingInfo);
	       
	
		// 결과 설정
		NexacroResult result = new NexacroResult();
		//result.setErrorCode(NexacroConstants.MSG.INFO_CODE);		
		result.addDataSet("dsMain",list, ItemMstMngVo.class);
		/*	*/
		 
		
		return result;
		
	}
	
	


    @RequestMapping(value = "/viself/auth/AuthMng/insert/json",
            method = RequestMethod.POST)
    public String addAuthCd(ModelMap model,
            @RequestBody
                    AuthMngDTO params) {
        return getTransactionJsonView(model, authMngSvc.add(params));
    }

    @RequestMapping(value = "/viself/auth/AuthMng/update/json",
            method = RequestMethod.POST)
    public String updateAuthCd(ModelMap model,
            @RequestBody
                    AuthMngDTO params) {
        model.clear();

        // 1. 권한코드 변경
        String result = authMngSvc.update(params);

        // 2. 권한저장소 반영(reload): 실시간 적용
        reloadSecurityDataFunction.reload();

        model.addAttribute(ERROR_FLAG.getAttributeId(), ERROR_RESULT);

        if (StringUtils.isEmpty(result)) {
            result = COMPLETE.getMsgCode();
            model.addAttribute(ERROR_FLAG.getAttributeId(), COMPLETE_RESULT);
        } else if (isProd()) {
            result = ERROR.getMsgCode();
        }

        model.addAttribute(MSG.getAttributeId(), result);

        return JSON_VIEW.getViewId();
    }

    @RequestMapping(value = "/viself/auth/AuthMng/updateSort/json",
            method = RequestMethod.POST)
    public String updateAuthCdSort(ModelMap model,
            @RequestBody
                    AuthMngDTO params) {
        model.clear();

        String result = authMngSvc.updateSort(params);

        model.addAttribute(ERROR_FLAG.getAttributeId(), ERROR_RESULT);

        if (StringUtils.isEmpty(result)) {
            result = COMPLETE.getMsgCode();
            model.addAttribute(ERROR_FLAG.getAttributeId(), COMPLETE_RESULT);
        } else if (isProd()) {
            result = ERROR.getMsgCode();
        }

        model.addAttribute(MSG.getAttributeId(), result);

        return JSON_VIEW.getViewId();
    }

    @RequestMapping(value = "/viself/auth/AuthMng/delete/json",
            method = RequestMethod.POST)
    public String deleteAuthCd(ModelMap model,
            @RequestBody
                    AuthMngDTO params) {
        model.clear();

        // 1. 권한코드 삭제
        String result = authMngSvc.delete(params);

        // 2. 권한저장소 반영(reload): 실시간 적용
        reloadSecurityDataFunction.reload();

        model.addAttribute(ERROR_FLAG.getAttributeId(), ERROR_RESULT);

        if (StringUtils.isEmpty(result)) {
            result = COMPLETE.getMsgCode();
            model.addAttribute(ERROR_FLAG.getAttributeId(), COMPLETE_RESULT);
        } else if (isProd()) {
            result = ERROR.getMsgCode();
        }

        model.addAttribute(MSG.getAttributeId(), result);

        return JSON_VIEW.getViewId();
    }
}
