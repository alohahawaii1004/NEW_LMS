package com.vertexid.paragon.mail;

import com.vertexid.viself.base.CmmDTO;

public class MailDTO extends CmmDTO {
    private static final long serialVersionUID = -7385995146675728814L;

    private String emailUid;
    private String relUid;
    private String solMasUid;
    private String stuCd;
    private String stuDtl;
    private String send;
    private String rec;
    private String ref;
    private String secuRef;
    private String tit;
    private String content;

    private String fileRelUid;
    private String fileTpCd;

    private String sendDte;
    private String sendYn;
    private String htmlUseYn;
    private String resvDte;

    public MailDTO() {
    }

    public String getEmailUid() {
        return emailUid;
    }

    public void setEmailUid(String emailUid) {
        this.emailUid = emailUid;
    }

    public String getRelUid() {
        return relUid;
    }

    public void setRelUid(String relUid) {
        this.relUid = relUid;
    }

    public String getSolMasUid() {
        return solMasUid;
    }

    public void setSolMasUid(String solMasUid) {
        this.solMasUid = solMasUid;
    }

    public String getStuCd() {
        return stuCd;
    }

    public void setStuCd(String stuCd) {
        this.stuCd = stuCd;
    }

    public String getStuDtl() {
        return stuDtl;
    }

    public void setStuDtl(String stuDtl) {
        this.stuDtl = stuDtl;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getRec() {
        return rec;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getSecuRef() {
        return secuRef;
    }

    public void setSecuRef(String secuRef) {
        this.secuRef = secuRef;
    }

    public String getTit() {
        return tit;
    }

    public void setTit(String tit) {
        this.tit = tit;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileRelUid() {
        return fileRelUid;
    }

    public void setFileRelUid(String fileRelUid) {
        this.fileRelUid = fileRelUid;
    }

    public String getFileTpCd() {
        return fileTpCd;
    }

    public void setFileTpCd(String fileTpCd) {
        this.fileTpCd = fileTpCd;
    }

    public String getSendDte() {
        return sendDte;
    }

    public void setSendDte(String sendDte) {
        this.sendDte = sendDte;
    }

    public String getSendYn() {
        return sendYn;
    }

    public void setSendYn(String sendYn) {
        this.sendYn = sendYn;
    }

    public String getHtmlUseYn() {
        return htmlUseYn;
    }

    public void setHtmlUseYn(String htmlUseYn) {
        this.htmlUseYn = htmlUseYn;
    }

    public String getResvDte() {
        return resvDte;
    }

    public void setResvDte(String resvDte) {
        this.resvDte = resvDte;
    }
}
