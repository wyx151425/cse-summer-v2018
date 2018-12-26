package com.cse.summer.model.entity;

import com.cse.summer.util.Generator;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author WangZhenqi
 */
@Entity
@Table(name = "cse_import_result")
public class ImportResult extends SummerEntity {
    /**
     * 机器名
     */
    private String machineName;
    /**
     * 部套号
     */
    private String structureNo;
    /**
     * 物料号
     */
    private String materialNo;
    /**
     * 导入标记
     */
    private Boolean mark;

    public ImportResult() {
    }

    public ImportResult(String machineName, String structureNo, String materialNo, Boolean mark) {
        super(Generator.getObjectId(), 1, LocalDateTime.now(), LocalDateTime.now());
        this.machineName = machineName;
        this.structureNo = structureNo;
        this.materialNo = materialNo;
        this.mark = mark;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getStructureNo() {
        return structureNo;
    }

    public void setStructureNo(String structureNo) {
        this.structureNo = structureNo;
    }

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    public Boolean getMark() {
        return mark;
    }

    public void setMark(Boolean mark) {
        this.mark = mark;
    }
}
