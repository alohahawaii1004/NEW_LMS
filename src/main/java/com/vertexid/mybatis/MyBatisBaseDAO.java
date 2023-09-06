/*
 * @(#)MyBatisBaseDAO.java     
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
package com.vertexid.mybatis;

import com.vertexid.commons.utils.CaseConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.vertexid.viself.base.BaseDAO;
import com.vertexid.viself.base.BaseModelVO;

/**
 * <b>Description</b>
 * - db.mybatis.mapper.root 속성 설정필요
 * - db.base.dbms 속성 설정 필요
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class MyBatisBaseDAO extends BaseDAO {

    /**
     * package 구분자
     */
    private static final char PACKAGE_SEPARATOR = '.';

    @Value(value = "#{cmmProperties['db.mybatis.mapper.root']}")
    private String baseMyBatisMapperPackage;

    @Value(value = "#{cmmProperties['db.mybatis.mapper.vendorRoot']}")
    private String vendorMyBatisMapperPackage;

    @Value(value = "#{cmmProperties['db.base.dbms']}")
    private String defaultDbms;

    private String namespace;

    /**
     * base package 값 설정
     * @param baseMyBatisMapperPackage base package 값
     */
    public void setBaseMyBatisMapperPackage(String baseMyBatisMapperPackage) {
        this.baseMyBatisMapperPackage = baseMyBatisMapperPackage;
    }

    /**
     * DBMS 값 설정
     * @param defaultDbms DBMS 값
     */
    public void setDefaultDbms(String defaultDbms) {
        this.defaultDbms = defaultDbms;
    }

    /**
     * namespace 값 설정
     * @param namespace namespace 값
     */
    public void setNamespace(String namespace) {

        // DEBUG
        if (log.isDebugEnabled()) {
            log.debug("MyBatis namespace is ...................." + namespace);
        }

        this.namespace = namespace;
    }

    /**
     * base package 값 얻기
     * @return base package 값
     */
    public String getBaseMyBatisMapperPackage() {
        return baseMyBatisMapperPackage;
    }

    /**
     * DBMS 값 얻기
     * @return DBMS 값
     */
    public String getDefaultDbms() {
        return defaultDbms;
    }

    /**
     * namespace 값 얻기
     * @return namespace 값
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * mapper statement 얻기
     * @param namespace mapper namespace
     * @param id statement id
     * @return mapper statement
     */
    public String getStatement(final String namespace, final String id) {
        setNamespace(namespace);
        String statement = String.format("%s." + id, namespace);

        log.info("statement..............."+statement);
        
        return statement;
    }

    /**
     * mapper statement 얻기
     * <pre>
     *      setNamespace 를 이용해서 namespace를 설정한 뒤
     *      statement id 만으로 mapper statement 얻기
     * </pre>
     *
     * @param id statement id
     * @return mapper statement
     */
    public String getStatement(final String id) {
        return getStatement(this.namespace, id);
    }

    /**
     * mapper statement 얻기
     * model 정보를 활용해서 mapper statement 얻기
     * @param modelVO model 정보
     * @return mapper statement
     */
    public String getStatement(final BaseModelVO modelVO) {

        if (null == modelVO) {
            // WARN
            if (log.isWarnEnabled()) {
                log.warn(".................................modelInfo is null");
            }
            return null;
        }

        StringBuffer sbNamespace = new StringBuffer();

        sbNamespace.append(baseMyBatisMapperPackage);

        // system
        if (StringUtils.isNotEmpty(modelVO.getSystem())) {
            if(vendorMyBatisMapperPackage.equals(modelVO.getSystem())){
                sbNamespace = new StringBuffer();
                sbNamespace.append(vendorMyBatisMapperPackage);
            }else{
                sbNamespace.append(PACKAGE_SEPARATOR)
                        .append(CaseConverter.camelCase(modelVO.getSystem()));
            }
        }

        // app
        if (StringUtils.isNotEmpty(modelVO.getApp())) {
            sbNamespace.append(PACKAGE_SEPARATOR).append(modelVO.getApp());
        }

        // module
        if (StringUtils.isNotEmpty(modelVO.getModule())) {
            sbNamespace.append(PACKAGE_SEPARATOR).append(modelVO.getModule());
        }

        // sub module
        if (StringUtils.isNotEmpty(modelVO.getSub())) {
            sbNamespace.append(PACKAGE_SEPARATOR).append(modelVO.getSub());
        }

        // resource
        if (StringUtils.isNotEmpty(modelVO.getResource())) {
            sbNamespace.append(PACKAGE_SEPARATOR).append(modelVO.getResource());
        }

        sbNamespace.append(PACKAGE_SEPARATOR).append(defaultDbms);

        return getStatement(sbNamespace.toString(), modelVO.getAction());
    }

    /**
     * dbms 정보를 이용한 namespace 만들기
     * @param dbname dbms 명칭(예: mssql, oracle, mariadb)
     * @param namespace dbms 명칭이 없는 namespace
     * @return dbms 정보가 들어간 namespace
     */
    public String makeNsWithDb(final String dbname, final String namespace){
        String dbmsType = StringUtils.defaultIfEmpty(dbname, defaultDbms);

        StringBuffer sbNamespace = new StringBuffer(namespace);
        sbNamespace.append(PACKAGE_SEPARATOR).append(dbmsType);

        return sbNamespace.toString();
    }

    /**
     * 기본 dbms 정보를 이용한 namespace 만들기
     * @param namespace dbms 명칭이 없는 namespace
     * @return dbms 정보가 들어간 namespace
     */
    public String makeNs(final String namespace){
        return makeNsWithDb("", namespace);
    }

    /**
     * dbms 정보를 이용한 mapper statement 얻기
     * @param dbname dbms 명칭(예: mssql, oracle, mariadb)
     * @param namespace dbms 명칭이 없는 namespace
     * @param id statement id
     * @return mapper statement
     */
    public String getStmtByNsWithDb(final String dbname, final String namespace,
                                    final String id) {
        return getStatement(makeNsWithDb(dbname, namespace), id);
    }

    /**
     * mapper statement 얻기
     * @param namespace dbms 명칭이 없는 namespace
     * @param id statement id
     * @return mapper statement
     */
    public String getStmtByNS(final String namespace, final String id) {
        return getStatement(makeNs(namespace), id);
    }
}
