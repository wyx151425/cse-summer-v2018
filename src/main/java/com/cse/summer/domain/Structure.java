package com.cse.summer.domain;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * @author 王振琦
 */
@Entity
@Table(name = "cse_structure")
public class Structure extends com.cse.summer.domain.Entity {
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
     * 物料专利方版本
     */
    private String revision;
    /**
     * 物料版本
     */
    private Integer version;

    @Transient
    private Material material;

    public Structure() {
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

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "Structure{" +
                "machineName='" + machineName + '\'' +
                ", structureNo='" + structureNo + '\'' +
                ", materialNo='" + materialNo + '\'' +
                ", revision='" + revision + '\'' +
                ", version=" + version + '\'' +
                ", material=" + material + '\'' +
                '}';
    }
}
