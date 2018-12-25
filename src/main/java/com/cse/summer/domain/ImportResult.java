package com.cse.summer.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

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
