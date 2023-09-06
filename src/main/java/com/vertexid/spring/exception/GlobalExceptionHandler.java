/*
 * @(#)GlobalExceptionHandler.java     2020-10-22(022) 오후 4:34
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
package com.vertexid.spring.exception;

import com.vertexid.commons.view.ViewType;
import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.base.BaseCtrl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.vertexid.viself.base.MessageCode.ERROR;
import static com.vertexid.viself.base.ModelAttribute.ERROR_FLAG;
import static com.vertexid.viself.base.ModelAttribute.MSG;

/**
 * <b>Description</b>
 * <pre>
 *     전역 에러 처리 핸들러
 *     Dispatcher Servlet 내부의 Exception에 대해서
 *     Spring의 ControllerAdvice 를 사용하여
 *     Application 전체 에러를 로깅하고 처리한다.
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@ControllerAdvice
public class GlobalExceptionHandler extends BaseCtrl {

    private static final String JSON_POSTFIX = "/json";
    private static final String ERROR_PAGE = "/error";
    //    private static final String ERROR_JSON = "/error/json";
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest req,
            HttpServletResponse res, Exception e) throws Exception {

        ModelAndView modelAndView;
        String reqPath = req.getRequestURI();

        String result = ERROR.getMsgCode();
        if (isNotProd()) {
            result = (e.getCause() != null) ? e.getCause().toString() :
                    e.getMessage();
        }

        // 1. 로깅
//        req.setAttribute("msg", e.getMessage());
        errorLogging(reqPath, e);


        // 2. 에러페이지
        if (StringUtils.containsIgnoreCase(reqPath, JSON_POSTFIX) ||
                SessionUtils.isAjaxRequest(req)) {
            // JSON으로 보내야 할 것
            modelAndView = new ModelAndView(ViewType.JSON_VIEW.getViewId());
        } else {
            // 에러 페이지로 보내야 할 것
            modelAndView = new ModelAndView(ERROR_PAGE);
        }

        modelAndView.addObject(ERROR_FLAG.getAttributeId(), ERROR_RESULT);
        modelAndView.addObject(MSG.getAttributeId(), result);

        return modelAndView;
    }

    private void errorLogging(String reqPath, Exception e) {
        StringBuffer sbError = new StringBuffer();
        sbError.append("\n    ===============================================");
        sbError.append("\n        !!! EXCEPTION !!! ");
        sbError.append("\n    -----------------------------------------------");
        sbError.append("\n    * REQUEST URI: ").append(reqPath);
        sbError.append("\n    * CLASS: ").append(e.getClass());
        sbError.append("\n    * CAUSE: ").append(e.getCause());
        sbError.append("\n    * MESSAGE: ").append(e.getMessage());

        sbError.append("\n    * STACK TRACE: ");
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        int sLen = stackTraceElements.length;
        for (int i = 0; i < sLen; i += 1) {
            sbError.append("\n        ").append(i + ": ")
                    .append(stackTraceElements[i].toString());
        }// end of for

        sbError.append("\n    * SUPPRESSED: ");
        Throwable[] throwables = e.getSuppressed();
        int tLen = throwables.length;
        for (int i = 0; i < tLen; i += 1) {
            sbError.append("\n        ").append(i + ": ")
                    .append(throwables[i].toString());
        }// end of for

        sbError.append("\n    ===============================================");
        log.error(sbError.toString());
    }
}
