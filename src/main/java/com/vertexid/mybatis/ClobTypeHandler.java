/*
 * @(#)ClobTypeHandler.java     2021-10-08(008) 오후 1:53
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

/**
 * <b>Description</b>
 * <pre>
 *     Mybatis 에서 조회된 jdbcType=CLOB 을 , javaType=java.lang.Object 으로 변환하기 위한 TypeHandler
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class ClobTypeHandler extends org.apache.ibatis.type.ClobTypeHandler {
}
