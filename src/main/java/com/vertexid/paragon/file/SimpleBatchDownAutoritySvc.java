/*
 * @(#)SimpleBatchDownAutoritySvc.java     2023-01-20(020) 오후 3:47:57
 *
 * Copyright 2023 JaYu.space
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
package com.vertexid.paragon.file;

import com.vertexid.viself.base.BaseSvc;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 *     단순 일괄다운로드 권한 서비스
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
public class SimpleBatchDownAutoritySvc extends BaseSvc implements
        BatchDownloadAuthoritySvc{
    @Override
    public boolean checkAuthority(Map<String, Object> param) {
        return false;
    }
}
