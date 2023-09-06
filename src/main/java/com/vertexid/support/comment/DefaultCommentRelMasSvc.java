/*
 * @(#)DefaultCommentRelMasSvc.java     2023-06-19(019) 오전 8:37
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

import com.vertexid.paragon.mail.MailDTO;
import com.vertexid.viself.base.BaseSvc;

/**
 * <b>Description</b>
 * <pre>
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class DefaultCommentRelMasSvc extends BaseSvc
        implements CommentRelMasSvc {

    public static final String FORM_PATH = "/form/mail/sys_mail_support.html";

    @Override
    public String getType() {
        return null;
    }

    @Override
    public MailDTO getMailDTO(CommentDTO param) {
        return null;
    }
}
