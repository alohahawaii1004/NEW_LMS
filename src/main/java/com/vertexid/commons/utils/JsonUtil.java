/*
 * @(#)JsonUtil.java     2020-10-30(030) 오후 4:29
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
package com.vertexid.commons.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <b>Description</b>
 * <pre>
 *     JSON Util
 *
 *     출처: https://androi.tistory.com/335 [안드로이 스토리]
 *     [참고] https://zzznara2.tistory.com/687
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class JsonUtil {

    /**
     * Map을 json으로 변환한다.
     *
     * @param map Map<String, Object>.
     * @return JSONObject.
     */
    public static JSONObject getJsonObjectFromMap(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            jsonObject.put(key, value);
        }

        return jsonObject;
    }

    /**
     * Map을 json으로 변환한다.
     *
     * @param map Map<String, Object>.
     * @return JSONObject.
     */
    @JsonIgnore
    public static String getJsonStringFromMap(Map<String, Object> map) {

        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {

            json = mapper.writeValueAsString(map);
            System.out.println(json); // compact-print
            json = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(map);
            System.out.println(json); // pretty-print

        } catch (JsonProcessingException e) {
            Logger log = LoggerFactory.getLogger(JsonUtil.class);
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return json;
    }

    /**
     * List<Map>을 jsonArray로 변환한다.
     *
     * @param list List<Map<String, Object>>.
     * @return JSONArray.
     */
    public static JSONArray getJsonArrayFromList(
            List<Map<String, Object>> list) {
        JSONArray jsonArray = new JSONArray();
        for (Map<String, Object> map : list) {
            jsonArray.add(getJsonStringFromMap(map));
        }

        return jsonArray;
    }

    /**
     * List<Map>을 jsonString으로 변환한다.
     *
     * @param list List<Map<String, Object>>.
     * @return String.
     */
    public static String getJsonStringFromList(List<Map<String, Object>> list) {
        JSONArray jsonArray = getJsonArrayFromList(list);
        return jsonArray.toString();
    }

    /**
     * JsonObject를 Map<String, String>으로 변환한다.
     *
     * @param jsonObj JSONObject.
     * @return Map<String, Object>.
     */
    public static Map<String, Object> getMapFromJsonObject(JSONObject jsonObj) {
        Map<String, Object> map;

        try {

            map = getMapFromJsonObject(jsonObj.toString());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return map;
    }

    /**
     * json String 을 Map<String, String>으로 변환한다.
     *
     * @param jsonString json String.
     * @return Map<String, Object>.
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMapFromJsonObject(String jsonString) {
        Map<String, Object> map;

        try {

            map = new ObjectMapper().readValue(jsonString, Map.class);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return map;
    }

    /**
     * JsonArray를 List<Map<String, String>>으로 변환한다.
     *
     * @param jsonArray JSONArray.
     * @return List<Map < String, Object>>.
     */
    public static List<Object> getListMapFromJsonArray(JSONArray jsonArray) {
        List<Object> list = new ArrayList<>();

        if (jsonArray != null) {
            for (Object o : jsonArray) {
                Map<String, Object> map =
                        JsonUtil.getMapFromJsonObject((JSONObject) o);
                list.add(map);
            }
        }

        return list;
    }

    /**
     * parse object to json string
     *
     * @param value object
     * @return json string
     */
    public static String parseJsonString(Object value) {
        try {
//            ObjectMapper mapper = new ObjectMapper().configure(
//                    MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

            ObjectMapper mapper = JsonMapper.builder()
                    .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                    .build();

            return mapper.writeValueAsString(value);
        } catch (Exception ex) {
            System.out.println("Connection Exception occurred");
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    public static List<Object> parseJsonStringToList(String jsonString) {
        List<Object> rtnList = null;

        if (StringUtils.isNotBlank(jsonString)) {
            JSONArray jArr = new JSONArray();
            rtnList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                jArr = objectMapper.readValue(jsonString, JSONArray.class);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            for (Object o : jArr) {
                ParamMap<Object, Object> map = new ParamMap<>();
                JSONObject jobj = (JSONObject) o;

                Set set = jobj.keySet();
                for (Object key : set) {
                    map.put(key, jobj.get(key));
                }
                rtnList.add(map);
            }
        }
        return rtnList;
    }
}
