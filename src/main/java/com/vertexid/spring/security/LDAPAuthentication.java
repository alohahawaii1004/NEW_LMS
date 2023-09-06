/*
 * @(#)LDAPAuthentication.java     2022-12-15(015) 오후 3:39:27
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
package com.vertexid.spring.security;

import com.vertexid.spring.utils.BaseProperties;
import com.vertexid.viself.base.BaseSvc;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Hashtable;

/**
 * <b>Description</b>
 * <pre>
 *     LDAP 인증
 * </pre>
 */
@Component
public class LDAPAuthentication extends BaseSvc {
    private String LDAP_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    private String LDAP_SECURITY = "simple";
    private String LDAP_URL = "";                    //ldap://covimdm.com
    private String LDAP_DOMAIN;                    //ldap.domain
    private String LDAP_SEARCH_DN;                //dc=covision,dc=co,dc=kr
    private String LDAP_BIND_ACCOUNT;
    private String LDAP_BIND_PASSWORD;

    public boolean authenticate(String pLdapURL, String pLdapDomain,
            String pLdapSearchDN, String pLdapBindingAccount,
            String pLdapBindingPassword, String pLoginID, String pLoginPWD) {
        boolean bResult = false;
        DirContext ctx = null;
        String strSamAccountName = "";
        try {
            LDAP_URL = pLdapURL;
            LDAP_DOMAIN = pLdapDomain;
            LDAP_SEARCH_DN = pLdapSearchDN;
            LDAP_BIND_ACCOUNT = pLdapBindingAccount + "@" + pLdapDomain;
            LDAP_BIND_PASSWORD = pLdapBindingPassword;
            strSamAccountName = getSamAccountName(pLoginID);

            log.debug("2. strSamAccountName......"+strSamAccountName);

            if (strSamAccountName.indexOf("[ERROR]") == -1) {
                Hashtable property = new Hashtable();
                property.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_FACTORY);
                property.put(Context.SECURITY_AUTHENTICATION, LDAP_SECURITY);
                property.put(Context.PROVIDER_URL, LDAP_URL);
                property.put(Context.SECURITY_PRINCIPAL,
                        strSamAccountName + "@" + pLdapDomain);
                property.put(Context.SECURITY_CREDENTIALS, pLoginPWD);

                log.debug("3. LDAP authenticate properties......"+property);

                try {
                    ctx = new InitialDirContext(property);
                    bResult = true;
                } catch (NamingException e) {
                    log.warn(e.toString());
                    //e.printStackTrace();
                    bResult = false;
                } finally {
                    if (ctx != null) {
                        ctx.close();
                    }
                }
            }
        } catch (Exception ex) {
            log.warn(ex.toString());
            //ex.printStackTrace();
            bResult = false;
        }

        log.info("4. bResult........."+bResult);

        return bResult;
    }


    private String getSamAccountName(String pStrSchCN) throws Exception {
        String sResult = "";
        DirContext ctx = null;

        //템플릿 계정으로 LDAP 바인딩
        Hashtable propertyAdmin = new Hashtable();
        propertyAdmin.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_FACTORY);
        propertyAdmin.put(Context.SECURITY_AUTHENTICATION, LDAP_SECURITY);
        propertyAdmin.put(Context.PROVIDER_URL, LDAP_URL);
        propertyAdmin.put(Context.SECURITY_PRINCIPAL, LDAP_BIND_ACCOUNT);
        propertyAdmin.put(Context.SECURITY_CREDENTIALS, LDAP_BIND_PASSWORD);

        log.debug("1. LDAP getSamAccountName properties......"+propertyAdmin);

        try {
            ctx = new InitialDirContext(propertyAdmin);

            //ActiveDirectory 바이딩 후 CN=사번 사용자 개체를 찾음
            String requireAttribute = "sAMAccountName";
            String[] arrAttribute = requireAttribute.split(",");
            SearchControls sc = new SearchControls();
            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
            sc.setReturningAttributes(arrAttribute);
            String searchFilter = "(&(objectClass=user)(CN=" + pStrSchCN + "))";

            //@SuppressWarnings("rawtypes")
            NamingEnumeration results =
                    ctx.search(LDAP_SEARCH_DN, searchFilter, sc);

            SearchResult sr = (SearchResult) results.next();
            Attributes attrs = sr.getAttributes();
            String strAttribute = attrs.get("sAMAccountName").toString();

            //Attribute 를 꺼내면 "name: value" 형태로 되어있음
            String[] strAttr = strAttribute.split(": ");
            sResult = strAttr[1];

        } catch (Exception e) {
            String msg = "[ERROR]" + e.getMessage();
            log.warn(msg);
            //e.printStackTrace();
            sResult = "";
        } finally {
            if (ctx != null) {
                ctx.close();
            }
        }
        return sResult;
    }

    public boolean checkLdapAuthenticate(String loginId, String loginPw) {
        String url = BaseProperties.get("ldap.url");
        String domain = BaseProperties.get("ldap.domain");
        String dn = BaseProperties.get("ldap.baseDn");
        String bindAccount = BaseProperties.get("ldap.username");
        String bindPassword = BaseProperties.get("ldap.password");
        return authenticate(url, domain, dn, bindAccount, bindPassword, loginId, loginPw);
    }
}
