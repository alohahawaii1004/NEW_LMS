/*
 * @(#)DummyDrmSvc.java     2022-02-09(009) 오후 5:10
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
package com.vertexid.paragon.file;

import com.vertexid.commons.utils.ParamMap;
import com.vertexid.viself.base.BaseSvc;
import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 *      더미 DRM 서비스
 *      - 실제 해당 서비스가 필요할 경우 구현하고 applicationCont.xml 에 설정한다.
 *      [bean id="drmSvc" class= "com.vertexid.고객사.ips.file.구현클래스"/]
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Service
@Transactional
public class DummyDrmSvc extends BaseSvc implements DrmSvc{
    @Override
    public Map<String, Object> unPackingFile(ParamMap<String, Object> fileMap) {
        return null;
    }

    @Override
    public Map<String, Object> packingFile(Map<String, Object> drmMap) {
        return null;
    }

    @Override
    public Map<String, Object> packingFile(Map<String, Object> params,
            FileItem item) {
        return null;
    }
}
