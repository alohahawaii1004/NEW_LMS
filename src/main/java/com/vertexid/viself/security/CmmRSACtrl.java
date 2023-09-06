/*
 * @(#)CmmRSACtrl.java     
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

package com.vertexid.viself.security;

import com.vertexid.commons.view.ViewType;
import com.vertexid.viself.base.BaseCtrl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 공용 RSA 컨트롤러
 * 세션에 신규 RSA 공개키를 전달
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class CmmRSACtrl extends BaseCtrl {

    @Resource
    RSACryptographicSvc rsaCryptographicSvc;

    /**
     * RSA 초기화(Key 재발행)
     * <p>
     * 세션에서 발행하는 공개키가 세션 만료시간이후에는 키도 만료가 되므로
     * 로그인 시에 새로 받아서 처리하는 방식으로 변경
     *
     * @param status 세션상태
     * @param req request
     * @param model model
     * @param params parameters
     * @return view
     */
    @RequestMapping(value = "/rsa/init", method = RequestMethod.POST)
    public String initRsa(SessionStatus status, HttpServletRequest req,
            ModelMap model,
            @RequestParam
                    Map<String, Object> params) {

        Map<String, Object> newPubKey =
                rsaCryptographicSvc.setRSAKey(req);

        model.clear();
        model.put(RSAAttributeKey.PUBLIC_KEY_MODULUS,
                newPubKey.get(RSAAttributeKey.PUBLIC_KEY_MODULUS));
        model.put(RSAAttributeKey.PUBLIC_KEY_EXPONENT,
                newPubKey.get(RSAAttributeKey.PUBLIC_KEY_EXPONENT));

        return ViewType.JSON_VIEW.getViewId();
    }
}
