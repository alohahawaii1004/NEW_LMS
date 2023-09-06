/*
 * @(#)CodeMngCtrl.java     2020-09-24(024) 오전 10:30
 *
 * Copyright 2020 JAYU.space
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
package com.vertexid.viself.code;

import static com.vertexid.commons.view.ViewType.JSON_VIEW;
import static com.vertexid.viself.base.MessageCode.COMPLETE;
import static com.vertexid.viself.base.MessageCode.ERROR;
import static com.vertexid.viself.base.ModelAttribute.ERROR_FLAG;
import static com.vertexid.viself.base.ModelAttribute.MSG;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vertexid.viself.base.BaseCtrl;
import com.vertexid.viself.base.ModelAttribute;

/**
 * <b>Description</b>
 * <pre>
 *
 *     코드관리 컨트롤러
 *
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class CodeMngCtrl extends BaseCtrl {

    @Resource
    private CodeTypeSvc codeTypeSvc;

    @Resource
    private CodeSvc codeSvc;


    /**
     * 상세 코드 저장
     *
     * @param model  모델
     * @param params 유형 DTO
     * @return 저장결과 JSON
     */
    @RequestMapping(value = "/viself/code/CodeMng/saveCode/json",
            method = RequestMethod.POST)
    public String saveCode(ModelMap model,
            @RequestBody
                    CodeDTO params) {

        model.clear();
        String result = codeSvc.save(params);
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

    /**
     * 코드 삭제: 데이터 삭제 불가-미사용으로 전환
     *
     * @param model  모델
     * @param params 유형 DTO
     * @return 삭제 결과 JSON
     */
    @RequestMapping(value = "/viself/code/CodeMng/deleteCode/json",
            method = RequestMethod.POST)
    public String deleteCode(ModelMap model,
            @RequestBody
            CodeDTO params) {
        model.clear();

        String result = codeSvc.delete(params);
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

    /**
     * 코드 조회: 읽기 전용
     *
     * @param model  모델
     * @param params 유형 DTO
     * @return 삭제 결과 JSON
     */
    @RequestMapping(value = "/viself/code/CodeMng/listCodeForDTO/json",
            method = RequestMethod.POST)
    public String listCodeForDTO(ModelMap model,
            @RequestBody
            CodeDTO params) {
        model.clear();

        model.addAttribute(ModelAttribute.DATA.getAttributeId(), codeSvc.getListCdForDTO(model, params));

        return JSON_VIEW.getViewId();
    }

}
