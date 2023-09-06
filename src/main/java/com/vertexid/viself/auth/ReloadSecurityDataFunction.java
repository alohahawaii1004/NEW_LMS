/*
 * @(#)ReloadSecurityDataFunction.java     2022-07-21(021) 오후 12:09:11
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
package com.vertexid.viself.auth;

import com.vertexid.viself.security.SimpleReloadableSecurityMetadataSource;
import com.vertexid.viself.security.SimpleSecureObjectSvc;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <b>Description</b>
 * <pre>
 *     권한저장소 반영(reload): 실시간 적용 Function
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Component
public class ReloadSecurityDataFunction {

    @Resource
    private SimpleSecureObjectSvc simpleSecureObjectSvc;

    @Resource
    private SimpleReloadableSecurityMetadataSource
            simpleReloadableSecurityMetadataSource;

    public void reload() {
        // 2. 권한저장소 반영(reload): 실시간 적용
        simpleReloadableSecurityMetadataSource.setSecureObjectSvc(simpleSecureObjectSvc);
        simpleReloadableSecurityMetadataSource.reload();
    }
}
