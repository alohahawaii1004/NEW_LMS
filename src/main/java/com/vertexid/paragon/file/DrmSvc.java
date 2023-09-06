/*
 * @(#)DrmSvc.java     2022-02-07(007) 오후 4:50
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
import org.apache.commons.fileupload.FileItem;

import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 *     file drm service
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public interface DrmSvc {

    /**
     * DRM unPacking
     * <pre>
     *     fileMap 사항
     *         - strEncFilePath    암호화된 파일 실제 위치
     *         - strFileName       파일명
     *         - strDecFilePath    디코딩할 파일명
     * </pre>
     * @param fileMap file map
     * @return unPacking Map
     */
    Map<String, Object> unPackingFile(ParamMap<String, Object> fileMap);

    /**
     * DRM packing
     * <pre>
     *     drmMap 사향
     *         - strOrgFilePath 파일 실제 경로
     *         - strEncFilePath 암호화할 경로
     *         - strFileName 파일명
     * </pre>
     * @param drmMap drm map
     * @return packing map
     */
    Map<String, Object> packingFile(Map<String, Object> drmMap);

    /**
     * DRM packing
     * @param params request param
     * @param item file item
     * @return packing map
     */
    Map<String, Object> packingFile(Map<String, Object> params, FileItem item);
}
