/*
 * @(#)IssueStuInfo.java     2023-04-03 오후 4:11
 *
 * Copyright 2023 JAYU.space
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
package com.vertexid.support.issue;

import com.vertexid.viself.base.ActionType;
import org.apache.commons.lang3.StringUtils;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public enum IssueStuInfo {


    REQ("REQ", "이슈가 요청되었습니다.", "mgr"), RCV("RCV", "이슈가 접수되었습니다.", "req"),
    RSLT("RSLT", "이슈가 완료되었습니다.", "req"), CFM("CFM", "이슈가 검수되었습니다.", "mgr"),
    DEL("DEL", "이슈가 삭제되었습니다.", "mgr");
    private final String stuId;
    private final String mailTitlePostFix;

    private final String mailRecType;

    IssueStuInfo(String stuId, String mailTitlePostFix, String mailRecType) {
        this.stuId = stuId;
        this.mailTitlePostFix = mailTitlePostFix;
        this.mailRecType = mailRecType;
    }

    public String getStuId() {
        return stuId;
    }

    public String getMailTitlePostFix() {
        return mailTitlePostFix;
    }

    public String getMailRecType() {
        return mailRecType;
    }

    public static IssueStuInfo findBy(String getStuId) {

        for (IssueStuInfo action : values()) {
            if (StringUtils.equals(action.getStuId(), getStuId) ||
                    StringUtils.containsIgnoreCase(getStuId,
                            action.getStuId())) {
                return action;
            }
        }// end of for

        return null;
    }
}
