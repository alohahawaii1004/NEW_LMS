/*
 * @(#)CmmMailCtrl.java     2022-11-15(015) 오전 10:45:51
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
package com.vertexid.paragon.mail;

import com.vertexid.viself.base.BaseCtrl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 *     공용 메일 발송 컨트롤러
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class CmmMailCtrl extends BaseCtrl {

    @Resource
    SimpleMailSvc simpleMailSvc;

    @RequestMapping(value = "/cmm/mail/mailWrite/save/json",
            method = RequestMethod.POST)
    public String sendMail(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) throws Exception {
        return getTransactionJsonView(modelMap,
                simpleMailSvc.sendMessage(param));
    }
}
