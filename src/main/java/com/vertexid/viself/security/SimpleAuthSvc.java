/*
 * @(#)SimpleAuthSvc.java     2021-03-10(010) 오전 10:36
 *
 * Copyright 2021 JAYU.space
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

import com.vertexid.commons.utils.CaseConverter;
import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import com.vertexid.viself.base.SystemPropertiesVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Component
public class SimpleAuthSvc extends BaseSvc implements AuthSvc {

    private static final String NAMESPACE = "com.vertexid.viself.auth.AuthMenu";
    private static final String JSON_POST_PATH = "/json";
    private static final String PATH_SEPERATOR = "/";
    private static final String JS_MODULE_ROOT_PATH = "/js/module";
    private static final String JS_EXT = ".js";
    private static final String EXT_POST_PATH = "/**";
    private static final String POST_EXT = ".*";
    private static final String CONTEXT_ROOT_URL = "/";

    @Resource(name = "cmmDAO")
    private CmmDAO cmmDAO;

    @Resource
    SystemPropertiesVO systemPropertiesVO;

    @Override
    public LinkedHashMap<Object, List<ConfigAttribute>> getRolesAndUrl() {

        /*
         * Ant 패턴의 URL별 Role 리스트의 형태로 정보를 가져온다.
         */
        LinkedHashMap<Object, List<ConfigAttribute>> resourcesMap =
                new LinkedHashMap<>();

        // DB에서 읽어오기
        Map<String, Object> param = new HashMap<>();
        param.put("alwYn", "Y");
        List<Map<String, Object>> list = getRoleAndUrlList(param);

        // DEBUG
        log.debug("get role and menu(URL) list....\n" + list);

        Iterator<Map<String, Object>> itr = list.iterator();

        Map<String, Object> tmpMap;
        while (itr.hasNext()) {
            tmpMap = itr.next();


            String accUrl = (String) tmpMap.get("accesUrl");
            String authCd = (String) tmpMap.get("authCd");
//            log.debug("accUrl .........................." + accUrl);

            if (CONTEXT_ROOT_URL.equals(accUrl)) {
                setUrlPattern(resourcesMap, accUrl, authCd);
            } else if (accUrl.lastIndexOf(JS_EXT) != -1) {
                // case 1. ~.js 로 끝날경우: "/js/module" 을 url 맨 앞에 추가한다.
                String jsUrlPattern = JS_MODULE_ROOT_PATH + accUrl;
                setUrlPattern(resourcesMap, jsUrlPattern, authCd);
            } else if (accUrl.lastIndexOf(JSON_POST_PATH) != -1 ||
                    accUrl.lastIndexOf(POST_EXT) != -1) {
                // case 2. ~/json 으로 끝나거나 ~.* 로 끝나는 경우: 그냥 추가
                setUrlPattern(resourcesMap, accUrl, authCd);
            } else if (accUrl.lastIndexOf(EXT_POST_PATH) != -1) {

                // case 3. ~/** 로 끝날 경우

                String camelPath = toCamelPath(accUrl);
                //                String pascalPath = toPascalPath(accUrl);

                // [주의]
                // 아래 순서 중요

                // 1. JS extends: /js/module/~/**
                setUrlPattern(resourcesMap,
                        JS_MODULE_ROOT_PATH + camelPath + EXT_POST_PATH,
                        authCd);

                // 2. extends path:  ~/**
                if (!accUrl.contains(camelPath)) {
                    setUrlPattern(resourcesMap, camelPath + EXT_POST_PATH,
                            authCd);
                }

                // 1. default: ~/**
                setUrlPattern(resourcesMap, accUrl, authCd);

            } else {

                // case 4. 기본

                // [주의]
                // 아래 순서 중요

                String camelPath = toCamelPath(accUrl);

                log.debug(accUrl + ":camelPath..." + camelPath);

                // 1. JS(camel case): /js/module/~.js
                setUrlPattern(resourcesMap,
                        JS_MODULE_ROOT_PATH + camelPath + JS_EXT, authCd);

                // 2. JS(camel case) extends: /js/module/~/**
                setUrlPattern(resourcesMap,
                        JS_MODULE_ROOT_PATH + camelPath + EXT_POST_PATH,
                        authCd);

                // 3. default
                setUrlPattern(resourcesMap, accUrl, authCd);

                // 4. extends: /~.*
                setUrlPattern(resourcesMap, accUrl + POST_EXT, authCd);

                // 5. extends path: /~/**
                setUrlPattern(resourcesMap, accUrl + EXT_POST_PATH, authCd);
            }


        } // end of while

        if (systemPropertiesVO.isNotProd()) {
            log.info("resourcesMap...........\n" +
                    resourcesMap.toString().replace(", Ant ", "\n, Ant "));
        } else {
            log.info("resourcesMap...........\n" + resourcesMap);
        }

        return resourcesMap;
    }

//    /**
//     * url path 를 came case 로 변경하되
//     * 최종 경로를 Pascal Case 로 변경해사 반환
//     *
//     * @param accUrl 원본 Url
//     * @return pascal case url (ex: /sss/fff/Test)
//     */
//    private String toPascalPath(String accUrl) {
//        if (StringUtils.isEmpty(accUrl)) {
//            return accUrl;
//        }
//
//        String[] pathTokens = StringUtils.split(accUrl, PATH_SEPERATOR);
//        StringBuilder sb = new StringBuilder();
//        int iLen = pathTokens.length;
//        int lastIdx = iLen - 1;
//        for (int i = 0; i < iLen; i += 1) {
//
//            if (i == lastIdx) {
//                sb.append(PATH_SEPERATOR)
//                        .append(CaseConverter.pascalCase(pathTokens[i]));
//            } else {
//                sb.append(PATH_SEPERATOR)
//                        .append(CaseConverter.camelCase(pathTokens[i]));
//            }
//
//        }// end of for
//        return sb.toString();
//    }

    /**
     * COBOL-CASE 로 넘어왔을 url 에 대해 camelCase 로 변경한다.
     *
     * @param accUrl url
     * @return camelCase Url
     */
    private String toCamelPath(String accUrl) {
        if (StringUtils.isEmpty(accUrl)) {
            return accUrl;
        }

        String[] pathTokens = StringUtils.split(accUrl, PATH_SEPERATOR);
        StringBuilder sb = new StringBuilder();
        for (String pathToken : pathTokens) {
            sb.append(PATH_SEPERATOR)
                    .append(CaseConverter.camelCase(pathToken));
        }// end of for
        return sb.toString();
    }

    /**
     * URL pattern 과 권한을 맵핑해서 설정
     *
     * @param resourcesMap url 패턴, 권한 맵(검사 및 추가)
     * @param urlPattern   url ant pattern
     * @param authId       권한코드
     */
    private void setUrlPattern(
            LinkedHashMap<Object, List<ConfigAttribute>> resourcesMap,
            String urlPattern, String authId) {

        String presentResourceStr;
        Object presentResource;

        presentResourceStr = urlPattern;
        presentResource = new AntPathRequestMatcher(presentResourceStr);
        List<ConfigAttribute> configList = new LinkedList<>();

        if (resourcesMap.containsKey(presentResource)) {
            List<ConfigAttribute> preAuthList =
                    resourcesMap.get(presentResource);

            for (ConfigAttribute configAttribute : preAuthList) {


                SecurityConfig tmpConfig = (SecurityConfig) configAttribute;
                if(!configList.contains(tmpConfig)){
                    configList.add(tmpConfig);
                }

            }// end of while
        }

//        configList.add(new SecurityConfig(authId));

        SecurityConfig tmpConfig = new SecurityConfig(authId);
        if(!configList.contains(tmpConfig)){
            configList.add(tmpConfig);
        }
        resourcesMap.put(presentResource, configList);

        // DEBUG
//        log.debug("2.1 moduleUrl...." + presentResourceStr);
//        log.debug("2.2 presentResource: AntPathRequestMatcher...." +
//                presentResource);
//        log.debug("2.3. configList..." + configList.toString());
    }

    public List<Map<String, Object>> getRoleAndUrlList(
            Map<String, Object> params) {
        return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "authNUrlList"),
                params);
    }
}
