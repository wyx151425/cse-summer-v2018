package com.cse.summer.model.entity;

import com.cse.summer.util.Constant;
import com.cse.summer.util.Generator;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 部套备注类
 *
 * @author WangZhenqi
 */
@Entity
@Table(name = "cse_structure_note")
public class StructureNote extends SummerEntity {
    /**
     * 所属部套的ID
     */
    private String structureId;
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
    private Integer version;
    /**
     * 部套版本备注信息
     */
    private String note;

    public String getStructureId() {
        return structureId;
    }

    public void setStructureId(String structureId) {
        this.structureId = structureId;
    }

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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public static StructureNote newInstance() {
        StructureNote structureNote = new StructureNote();
        structureNote.setObjectId(Generator.getObjectId());
        structureNote.setStatus(Constant.Status.ENABLE);
        LocalDateTime dateTime = LocalDateTime.now().withNano(0);
        structureNote.setCreateAt(dateTime);
        structureNote.setUpdateAt(dateTime);
        return structureNote;
    }
}
