/*
 * @(#)BaseProperties.java     2020-12-29(029) 오전 8:35
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
package com.vertexid.spring.utils;

//import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RegExUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * <b>Description</b>
 * <pre>
 *     기본 프로퍼티 유틸
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class BaseProperties {

    private static Logger log = LoggerFactory.getLogger(BaseProperties.class);

    private static Properties properties;
    private static ResourceLoader resourceLoader = new DefaultResourceLoader();
    public static final String PROPERTIES_CLASS_PATH = "classpath:properties.xml";

    public static String get(final String key){

        initProperties();

        return properties.getProperty(key);
    }

    public static String get(final String key, final String defaultVal){

        initProperties();

        return properties.getProperty(key, defaultVal);
    }

    public static List<String> getList(final String key){

//        String data = StringUtils.replace(get(key), " ", "");
        String data = RegExUtils.replaceAll(get(key), "( |\\t)*", "");
        return Arrays.asList(StringUtils.split(data, "\n"));
    }

    public static void reload(){
        if(null == properties){
            properties = new Properties();
        }else{
            properties.clear();
        }

        loadProperties();
    }

    private static void loadProperties() {
        String hostNm = "";

        try {
            hostNm = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String propertiesPath = PROPERTIES_CLASS_PATH;
        if(StringUtils.isNotBlank(hostNm)) {
            propertiesPath = "classpath:properties."+hostNm+".xml";
            log.debug("IS PROPERTIES NAME = " + propertiesPath);
        }
        Resource resource = resourceLoader.getResource(propertiesPath);
        if(!resource.isReadable()) {
            log.debug("IS DEFAULT Properteis Loaded!!");
            resource = resourceLoader.getResource(PROPERTIES_CLASS_PATH);
        }
        try {
            properties.loadFromXML(resource.getInputStream());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private static void initProperties() {
        if(null == properties){
            properties = new Properties();
            loadProperties();
        }
    }
}
