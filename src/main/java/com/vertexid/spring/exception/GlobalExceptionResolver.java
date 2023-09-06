/*
 * @(#)GlobalExceptionResolver.java     2020-10-26(026) 오전 8:30
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
import com.vertexid.viself.base.MessageCode;
import com.vertexid.viself.base.SystemPropertiesVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.vertexid.viself.base.MessageCode.ERROR;
import static com.vertexid.viself.base.ModelAttribute.ERROR_FLAG;
import static com.vertexid.viself.base.ModelAttribute.MSG;

/**
 * <b>Description</b>
 * <pre>
 *     전역 에러를 처리하기 위해만들어진 Exception Resolver
 *     Dispatcher Servlet 설정에 해당 resolver 설정을 추가해야 한다.
 *     * GlobalExceptionResolver 보다 GlobalExceptionHandler 권장
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class GlobalExceptionResolver extends SimpleMappingExceptionResolver {

    private static final String JSON_POSTFIX = "/json";
    private static final String ERROR_PAGE = "/error/page";
    private static final String ERROR_RESULT =
            MessageCode.ERROR.getResultCode();

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    SystemPropertiesVO systemPropertiesVO;

    protected boolean isProd() {
        return systemPropertiesVO.isProd();
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest req,
            HttpServletResponse res, Object handler, Exception e) {


        String viewName = determineViewName(e, req);
//        log.debug("??????????????????????????????????"+viewName);

        ModelAndView modelAndView;
        String reqPath = req.getRequestURI();
//        log.debug("reqPath??????????????????????????"+reqPath);

        String result = ERROR.getMsgCode();
        if (!isProd()) {
            result = (e.getCause() != null) ? e.getCause().toString() :
                    e.getMessage();
        }

        // 1. 로깅
//        req.setAttribute("msg", e.getMessage());
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

        // 2. 에러페이지
        if (StringUtils.containsIgnoreCase(reqPath, JSON_POSTFIX)) {

            Integer statusCode = determineStatusCode(req, viewName);
            if (statusCode != null) {
                applyStatusCodeIfPossible(req, res, statusCode);
            }

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
}
