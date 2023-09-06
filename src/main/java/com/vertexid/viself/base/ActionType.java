/*
 * @(#)ActionType.java     
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

package com.vertexid.viself.base;

import org.apache.commons.lang3.StringUtils;

/**
 * 조회액션 유형
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public enum ActionType {

    DATA("data"),
    LIST("list"),
    SELECT("select"),
    MODIFY("modify") ,
    UPDATE("update") ,
    INSERT("insert"),
    SAVE("save"),
    DELETE("delete");

    private final String actionId;

    ActionType(String actionId) {
        this.actionId = actionId;
    }

    public static ActionType findBy(String actionId) {

        for (ActionType action : values()) {
            if (StringUtils.equals(action.getActionId(), actionId) ||
                    StringUtils.containsIgnoreCase(actionId,
                            action.getActionId())) {
                return action;
            }
        }// end of for

        return null;
    }

    public String getActionId() {
        return actionId;
    }
}
