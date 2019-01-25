package com.cse.summer.model.entity;

import com.cse.summer.util.Generator;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 王振琦
 */
@Entity
@Table(name = "cse_machine")
public class Machine extends SummerEntity {
    /**
     * 机器名
     */
    private String name;
    /**
     * 专利方类型
     */
    private String patent;
    /**
     * 机号
     */
    private String machineNo;
    /**
     * 船号
     */
    private String shipNo;
    /**
     * 适用型号
     */
    private String type;
    /**
     * 缸数
     */
    private Integer cylinderAmount;
    /**
     * 船级社
     */
    private String classificationSociety;
    /**
     * 信息是否已完善
     */
    private Boolean complete;

    @Transient
    private List<Structure> structureList;

    public Machine() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatent() {
        return patent;
    }

    public void setPatent(String patent) {
        this.patent = patent;
    }

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public Integer getCylinderAmount() {
        return cylinderAmount;
    }

    public void setCylinderAmount(Integer cylinderAmount) {
        this.cylinderAmount = cylinderAmount;
    }

    public String getShipNo() {
        return shipNo;
    }

    public void setShipNo(String shipNo) {
        this.shipNo = shipNo;
    }

    public String getClassificationSociety() {
        return classificationSociety;
    }

    public void setClassificationSociety(String classificationSociety) {
        this.classificationSociety = classificationSociety;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public List<Structure> getStructureList() {
        return structureList;
    }

    public void setStructureList(List<Structure> structureList) {
        this.structureList = structureList;
    }

    public static Machine getInstance(String name) {
        Machine machine = new Machine();
        machine.setObjectId(Generator.getObjectId());
        machine.setStatus(1);
        LocalDateTime dateTime = LocalDateTime.now();
        machine.setCreateAt(dateTime);
        machine.setUpdateAt(dateTime);
        machine.setName(name);
        machine.setPatent("MAN");
        machine.setMachineNo("");
        machine.setType("");
        machine.setCylinderAmount(0);
        machine.setShipNo("");
        machine.setClassificationSociety("");
        machine.setComplete(false);
        return machine;
    }
}
