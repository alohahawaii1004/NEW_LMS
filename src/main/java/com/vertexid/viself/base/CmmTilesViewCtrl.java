/*
 * @(#)CmmTilesViewCtrl.java     
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

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <b>Description</b>
 * <pre>
 * URL 패턴에 따른 apache tiles를 활용한 view를 처리하는 공통 컨트롤러
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class CmmTilesViewCtrl extends BaseCtrl {

    private static final String ERROR_TILES = "error.tiles";
    @Resource
    CmmTilesViewSvc cmmTilesViewSvc;

    /**
     * tiles 뷰 처리
     *
     * @param system system
     * @param app application
     * @param module module
     * @param sub submodule
     * @param page page
     * @return tiles view
     */
    @RequestMapping("/{system}/{app}/{module}/{sub}/{page:.+}")
    public String view(
            @PathVariable
                    String system,
            @PathVariable
                    String app,
            @PathVariable
                    String module,
            @PathVariable
                    String sub,
            @PathVariable
                    String page,
            ModelMap model
            ) {

        BaseViewVO viewInfo = new BaseViewVO(system, app, module, sub, page);
        return cmmTilesViewSvc.getTilesView(viewInfo, model);
    }

    /**
     * tiles 뷰 처리
     *
     * @param system system
     * @param app application
     * @param module module
     * @param page page
     * @return tiles view
     */
    @RequestMapping("/{system}/{app}/{module}/{page:.+}")
    public String view(
            @PathVariable
                    String system,
            @PathVariable
                    String app,
            @PathVariable
                    String module,
            @PathVariable
                    String page,
            ModelMap model
            ) {

        BaseViewVO viewInfo = new BaseViewVO(system, app, module, null, page);
        return cmmTilesViewSvc.getTilesView(viewInfo, model);
    }

    /**
     * tiles 뷰 처리
     *
     * @param system system
     * @param app application
     * @param page page
     * @return tiles view
     */
    @RequestMapping("/{system}/{app}/{page:.+}")
    public String view(
            @PathVariable
                    String system,
            @PathVariable
                    String app,
            @PathVariable
                    String page,
            ModelMap model) {
        BaseViewVO viewInfo = new BaseViewVO(system, app, null, null, page);
        return cmmTilesViewSvc.getTilesView(viewInfo, model);
    }

    /**
     * tiles 뷰 처리
     *
     * @param system system
     * @param page page
     * @return tiles view
     */
    @RequestMapping("/{system}/{page:.+}")
    public String view(
            @PathVariable
                    String system,
            @PathVariable
                    String page,
            ModelMap model) {

        log.debug("system.............."+system);
        log.debug("page.............."+page);

        BaseViewVO viewInfo = new BaseViewVO(system, null, null, null, page);
        return cmmTilesViewSvc.getTilesView(viewInfo, model);
    }

    /**
     * tiles 뷰 처리
     *
     * @param page page
     * @return tiles view
     */
    @RequestMapping("/{page:.+}")
    public String view(
            @PathVariable
                    String page,
                    ModelMap model) {

        BaseViewVO viewInfo = new BaseViewVO(null, null, null, null, page);
        return cmmTilesViewSvc.getTilesView(viewInfo, model);
    }

    /**
     * 에러 tiles 뷰 처리
     * @return
     */
    @RequestMapping("/error")
    public String viewError(){
        return ERROR_TILES;
    }
}
