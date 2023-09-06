/*
 * @(#)BaseModelVO.java     
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

import com.vertexid.commons.utils.CaseConverter;

/**
 * <b>Description</b>
 * <pre>
 * SELF 에서 사용하는 기본 Model 정보
 *
 * 리소스에대한 액션 정보를 가지고 있다.
 *
 * 데이터에 관한 처리를 위해 CmmJsonViewCtrl ~/json 패턴에서 사용된다.
 *
 * 모델정보에대한 패턴은
 *
 *     /시스템/앱/모듈/서브모듈/리소스/액션/반환데이터 유형(json)
 *
 * 형태이다.
 *
 * 리소스에 대한 naming case 는 JAVA의 package, class, method naming 을 따르며
 *
 * 리소스는 Pascal case, 액션은 camel case, 그외에는 lower snake case 로 변환된다.
 *
 *   ex) /시스템/앱/리소스/액션/반환데이터 유형(json) 형태
 *
 *       RequestMapping(/viself/auth-app/test-mng/test-list/json)
 *
 *       =>
 *           - system: viself
 *           - app: auth_app
 *           - resource: TestMng
 *           - action: testList
 *
 *           /viself/auth_app/TestMng/testList
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class BaseModelVO extends BaseVO {

    private static final long serialVersionUID = 4305971050476143426L;

    /** 시스템 */
    private final String system;

    /** 앱 */
    private final String app;

    /** 모듈(메뉴) */
    private final String module;

    /** 서브모듈 */
    private final String sub;

    /** 리소스 */
    private final String resource;

    /** 액션 */
    private final String action;

    private final ActionType actionType;

    /**
     * 모델 정보
     * @param system 시스템
     * @param app 앱
     * @param module 모듈
     * @param sub 서브모듈
     * @param resource
     * @param action 액션
     */
    public BaseModelVO(String system, String app, String module, String sub,
            String resource, String action) {
//        this.system = CaseConverter.lowerSnakeCase(system);
//        this.app = CaseConverter.lowerSnakeCase(app);
//        this.module = CaseConverter.lowerSnakeCase(module);
//        this.sub = CaseConverter.lowerSnakeCase(sub);
        this.system = system;
        this.app = app;
        this.module = module;
        this.sub = sub;
        // 주의: resource 만 Pascal case
        this.resource = CaseConverter.pascalCase(resource);
        this.action = CaseConverter.camelCase(action);
        this.actionType = ActionType.findBy(action);

        log.debug(this.toString());
        // REVIEW
        /*
         * 1. 모델정보 구성 패턴의 깊이가 늘어날 가능성은 없는가?
         *    늘어날경우 어떻게 처리해야 하나?
         *
         * 2. 반환데이터 유형은 관리하지 않아도 되는가?
         */
    }

    /**
     * 시스템
     * @return 시스템명
     */
    public String getSystem() {
        return system;
    }

    /**
     * 앱 얻기
     * @return 앱
     */
    public String getApp() {
        return app;
    }

    /**
     * 모듈 얻기
     * @return 모듈
     */
    public String getModule() {
        return module;
    }

    /**
     * 서브모듈 얻기
     * @return 서브모듈
     */
    public String getSub() {
        return sub;
    }

    /**
     * 리소스 얻기
     * @return 리소스
     */
    public String getResource() {
        return resource;
    }

    /**
     * 액션 얻기
     * @return 액션
     */
    public String getAction() {
        return action;
    }

    /**
     * 액션유형 얻기
     * @return 액션유형
     */
    public ActionType getActionType() {
        return actionType;
    }


}
