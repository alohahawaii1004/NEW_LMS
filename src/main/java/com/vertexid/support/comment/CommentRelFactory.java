/*
 * @(#)CommentRelFactory.java     2023-06-19(019) 오전 8:33
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
package com.vertexid.support.comment;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Component
public class CommentRelFactory {

    @Resource
    private List<CommentRelMasSvc> commentRelMasSvcList;

    public CommentRelMasSvc getMasSvc(String type){
        for(CommentRelMasSvc commentRelMasSvc: commentRelMasSvcList){
            if(type.equals(commentRelMasSvc.getType())){
                return commentRelMasSvc;
            }
        }// end of for
        return null;
    }
}
