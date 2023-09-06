package com.vertexid.paragon.file;

import com.vertexid.viself.base.CmmDTO;

public class FileDTO extends CmmDTO{

    /**
     *
     */
    private static final long serialVersionUID = -5209251986381536932L;

    private String atchUid;
    private String solMasUid;
    private String relUid;
    private int ordNo;
    private String fileTpCd;
    private String fileSaveNm;
    private String fileNm;
    private String fileSize;
    private String useYn;
    private String regLoginId;
    private String uptLoginId;
    private String migYn;
    private String migFilePath;



    public String getAtchUid() {
        return atchUid;
    }
    public void setAtchUid(String atchUid) {
        this.atchUid = atchUid;
    }
    public String getSolMasUid() {
        return solMasUid;
    }
    public void setSolMasUid(String solMasUid) {
        this.solMasUid = solMasUid;
    }
    public String getRelUid() {
        return relUid;
    }
    public void setRelUid(String relUid) {
        this.relUid = relUid;
    }

    public int getOrdNo() {
        return ordNo;
    }
    public void setOrdNo(int ordNo) {
        this.ordNo = ordNo;
    }
    public String getFileTpCd() {
        return fileTpCd;
    }
    public void setFileTpCd(String fileTpCd) {
        this.fileTpCd = fileTpCd;
    }
    public String getFileSaveNm() {
        return fileSaveNm;
    }
    public void setFileSaveNm(String fileSaveNm) {
        this.fileSaveNm = fileSaveNm;
    }
    public String getFileNm() {
        return fileNm;
    }
    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }
    public String getFileSize() {
        return fileSize;
    }
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
    public String getUseYn() {
        return useYn;
    }
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
    public String getRegLoginId() {
        return regLoginId;
    }
    public void setRegLoginId(String regLoginId) {
        this.regLoginId = regLoginId;
    }
    public String getUptLoginId() {
        return uptLoginId;
    }
    public void setUptLoginId(String uptLoginId) {
        this.uptLoginId = uptLoginId;
    }
	public String getMigYn() {
		return migYn;
	}
	public void setMigYn(String migYn) {
		this.migYn = migYn;
	}
	public String getMigFilePath() {
		return migFilePath;
	}
	public void setMigFilePath(String migFilePath) {
		this.migFilePath = migFilePath;
	}

}
