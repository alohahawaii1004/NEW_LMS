/*
 * @(#)BbsMasDTO.java     2022-07-28(028) 오전 10:06:43
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
package com.vertexid.paragon.bbs;

import org.apache.commons.lang3.StringUtils;
import com.vertexid.viself.base.CmmDTO;

public class BbsMasDTO extends CmmDTO{

    private static final long serialVersionUID = -6844329018997663984L;


    /**
     * 게시판 메인 키
     */
    private String bbsUid;
    /**
     * 게시판 유형
     */
    private String bbsTpCd;
    /**
     * 게시판 코드
     */
    private String bbsCd;
    /**
     * 카테고리 코드
     */
    private String bbsCtgryCd;
    /**
     * 제목
     */
    private String bbsTit;
    /**
     * 내용
     */
    private String bbsContent;
    /**
     * 조회수
     */
    private String bbsHitCnt;
    /**
     * 등록 로그인 ID
     */
    private String bbsRegLoginId;
    /**
     * 등록 일자
     */
    private String bbsRegDte;
    /**
     * 수정 로그인 ID
     */
    private String bbsUptLoginId;
    /**
     * 수정 일자
     */
    private String bbsUptDte;
    /**
     * 사용 YN
     */
    private String bbsUseYn;
    /**
     * 상단고정 여부
     */
    private String bbsTopYn;
    /**
     * 팝업여부
     */
    private String bbsPopupYn;
    /**
     * 팝업시작기간
     */
    private String bbsPopupStDte;
    /**
     * 팝업종료기간
     */
    private String bbsPopupEdDte;


    private String bbsViewAuth;    /*문서조회권한*/

    private String bbsTpCdNm      ;
    private String bbsRegLoginNm  ;
    private String bbsUptLoginNm  ;
    
    public BbsMasDTO() {
    }

    public String getBbsUid() {
        return bbsUid;
    }

    public void setBbsUid(String bbsUid) {
        this.bbsUid = bbsUid;
    }

    public String getBbsTpCd() {
        return bbsTpCd;
    }

    public void setBbsTpCd(String bbsTpCd) {
        this.bbsTpCd = bbsTpCd;
    }

    public String getBbsCd() {
        return bbsCd;
    }

    public void setBbsCd(String bbsCd) {
        this.bbsCd = bbsCd;
    }

    public String getBbsCtgryCd() {
        return bbsCtgryCd;
    }

    public void setBbsCtgryCd(String bbsCtgryCd) {
        this.bbsCtgryCd = StringUtils.defaultIfBlank(bbsCtgryCd, null);
    }

    public String getBbsTit() {
        return bbsTit;
    }

    public void setBbsTit(String bbsTit) {
        this.bbsTit = bbsTit;
    }

    public String getBbsContent() {
        return bbsContent;
    }

    public void setBbsContent(String bbsContent) {
        this.bbsContent = bbsContent;
    }

    public String getBbsHitCnt() {
        return bbsHitCnt;
    }

    public void setBbsHitCnt(String bbsHitCnt) {
        this.bbsHitCnt = bbsHitCnt;
    }

    public String getBbsRegLoginId() {
        return bbsRegLoginId;
    }

    public void setBbsRegLoginId(String bbsRegLoginId) {
        this.bbsRegLoginId = bbsRegLoginId;
    }

    public String getBbsRegDte() {
        return bbsRegDte;
    }

    public void setBbsRegDte(String bbsRegDte) {
        this.bbsRegDte = bbsRegDte;
    }

    public String getBbsUptLoginId() {
        return bbsUptLoginId;
    }

    public void setBbsUptLoginId(String bbsUptLoginId) {
        this.bbsUptLoginId = bbsUptLoginId;
    }

    public String getBbsUptDte() {
        return bbsUptDte;
    }

    public void setBbsUptDte(String bbsUptDte) {
        this.bbsUptDte = bbsUptDte;
    }

    public String getBbsUseYn() {
        return bbsUseYn;
    }

    public void setBbsUseYn(String bbsUseYn) {
        this.bbsUseYn = bbsUseYn;
    }

    public String getBbsTopYn() {
        return bbsTopYn;
    }

    public void setBbsTopYn(String bbsTopYn) {
        this.bbsTopYn = bbsTopYn;
    }

    public String getBbsPopupYn() {
        return bbsPopupYn;
    }

    public void setBbsPopupYn(String bbsPopupYn) {
        this.bbsPopupYn = bbsPopupYn;
    }

    public String getBbsPopupStDte() {
        return bbsPopupStDte;
    }

    public void setBbsPopupStDte(String bbsPopupStDte) {
        this.bbsPopupStDte = bbsPopupStDte;
    }

    public String getBbsPopupEdDte() {
        return bbsPopupEdDte;
    }

    public void setBbsPopupEdDte(String bbsPopupEdDte) {
        this.bbsPopupEdDte = bbsPopupEdDte;
    }

    public String getBbsViewAuth() {
        return bbsViewAuth;
    }

    public void setBbsViewAuth(String bbsViewAuth) {
        this.bbsViewAuth = bbsViewAuth;
    }

    public String getBbsTpCdNm() {
        return bbsTpCdNm;
    }

    public void setBbsTpCdNm(String bbsTpCdNm) {
        this.bbsTpCdNm = bbsTpCdNm;
    }

    public String getBbsRegLoginNm() {
        return bbsRegLoginNm;
    }

    public void setBbsRegLoginNm(String bbsRegLoginNm) {
        this.bbsRegLoginNm = bbsRegLoginNm;
    }

    public String getBbsUptLoginNm() {
        return bbsUptLoginNm;
    }

    public void setBbsUptLoginNm(String bbsUptLoginNm) {
        this.bbsUptLoginNm = bbsUptLoginNm;
    }
}
