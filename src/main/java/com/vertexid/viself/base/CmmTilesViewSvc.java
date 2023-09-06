/*
 * @(#)CmmTilesViewSvc.java     
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

package com.vertexid.viself.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.vertexid.spring.utils.CmmProperties;
import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.hr.SysLoginVO;


/**
 * <b>Description</b>
 * <pre>
 * 주소 패턴에 따라 해당 뷰를 tiles뷰로 전환하는 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
public class CmmTilesViewSvc extends BaseSvc {

    private static final String PARTICLE_POSTFIX = ".particle";
    private static final String MODAL_POSTFIX = ".modal";
    private static final String POPUP_POSTFIX = ".popup";
    private static final String TILES_POSTFIX = ".tiles";
    private static final char PATH_SEPARATOR = '/';

    /**
     * view 정보를 이용해서 tiles view 패턴을 반환
     * @param viewInfo view info
     * @param model TODO
     * @return tiles view pattern
     */
    public String getTilesView(BaseViewVO viewInfo, ModelMap model) {

        if (null == viewInfo) {
            // WARN
            if (log.isWarnEnabled()) {
                log.warn(".......................................NO PAGE INFO");
            }
            return null;
        }

        StringBuffer sbTilesView = new StringBuffer();

        sbTilesView.append(PATH_SEPARATOR);

        // system
        if (StringUtils.isNotEmpty(viewInfo.getSystem())) {
            sbTilesView.append(viewInfo.getSystem()).append(PATH_SEPARATOR);
        }

        // app
        if (StringUtils.isNotEmpty(viewInfo.getApp())) {
            sbTilesView.append(viewInfo.getApp()).append(PATH_SEPARATOR);
        }

        // module
        if (StringUtils.isNotEmpty(viewInfo.getModule())) {
            sbTilesView.append(viewInfo.getModule()).append(PATH_SEPARATOR);
        }

        // sub module
        if (StringUtils.isNotEmpty(viewInfo.getSub())) {
            sbTilesView.append(viewInfo.getSub()).append(PATH_SEPARATOR);
        }

        // typePostfix 붙이기 (.popup , .modal , .include ....)
        if (StringUtils.isNotEmpty(viewInfo.getTypePostfix())) {
             sbTilesView.append(viewInfo.getPage()).append(".").append(viewInfo.getTypePostfix())
             .append(TILES_POSTFIX);
        }else {
            // normal page
            sbTilesView.append(viewInfo.getPage()).append(TILES_POSTFIX);
        }

        // DEBUG
        if (log.isDebugEnabled()) {
            log.debug("view info......................." + viewInfo.toString());
        }

        // INFO
        if (log.isInfoEnabled()) {
            log.info("tiles view..............................." + sbTilesView);
        }

        SysLoginVO loginUser = (SysLoginVO)SessionUtils.getLoginVO();

        model.addAttribute("loginInfo", loginUser);
        model.addAttribute("isProd", CmmProperties.isProd());
        model.addAttribute("isLocal", CmmProperties.isLocal());

        return sbTilesView.toString();
    }
}
