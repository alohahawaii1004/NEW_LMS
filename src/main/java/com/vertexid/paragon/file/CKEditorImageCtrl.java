/*
 * @(#)CKEditorImageCtrl.java     2022-02-08(008) 오전 9:13
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
package com.vertexid.paragon.file;

import com.vertexid.viself.base.BaseCtrl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.vertexid.commons.view.ViewType.JSON_VIEW;

/**
 * <b>Description</b>
 * <pre>
 *     CKEditor 용 이미지 관련 유틸
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class CKEditorImageCtrl extends BaseCtrl {

    @Resource
    CKEditorImageSvc ckEditorImageSvc;

    /**
     * CKEditor 이미지 업로드
     * @param req request
     * @param model model
     * @return json view
     */
    @RequestMapping(value = "/editor/imageUpload/json",
            method = RequestMethod.POST)
    public String imageUpload(HttpServletRequest req, ModelMap model) {
        model.clear();

        Map<String, Object> rtnMap = ckEditorImageSvc.upload(req);

        model.addAttribute("fileName", rtnMap.get("fileName"));
        model.addAttribute("uploaded", "1");
        model.addAttribute("url", rtnMap.get("url"));

        return JSON_VIEW.getViewId();
    }

    /**
     * CKEditor 이미비 보이기
     * @param uid uid
     * @param fileName file name
     * @param res response
     */
    @RequestMapping(value = "/editor/viewImg")
    public void viewImage(
            @RequestParam(value = "nowMonth")
                    String nowMonth,
            @RequestParam(value = "uid")
                    String uid,
            @RequestParam(value = "fileName")
                    String fileName,
            HttpServletResponse res) {

        ckEditorImageSvc.viewImage(nowMonth, uid, fileName, res);
    }
}
