package com.cse.summer.domain;

import javax.persistence.*;

/**
 * @author 王振琦
 */
@javax.persistence.Entity
@Table(name = "cse_structure")
public class Structure extends SummerEntity {
    /**
     * 0-删除/1-设计/2-发布
     * private Integer status;
     */
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
     * 物料版本
     */
    private Integer version;
    /**
     * 总数量
     */
    private Integer amount;

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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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
                ", version=" + version + '\'' +
                ", material=" + material + '\'' +
                '}';
    }
}
