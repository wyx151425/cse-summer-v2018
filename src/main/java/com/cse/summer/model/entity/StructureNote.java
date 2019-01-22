package com.cse.summer.model.entity;

/**
 * 部套备注类
 *
 * @author WangZhenqi
 */
public class StructureNote extends SummerEntity {
    /**
     * 编制者
     */
    private String organizer;
    /**
     * 校对者
     */
    private String proofreader;
    /**
     * 审核者
     */
    private String auditor;
    /**
     * 部套的物料号
     */
    private String materialNo;
    /**
     * 部套的版本号
     */
    private String version;
    /**
     * 部套版本备注信息
     */
    private String note;

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getProofreader() {
        return proofreader;
    }

    public void setProofreader(String proofreader) {
        this.proofreader = proofreader;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
