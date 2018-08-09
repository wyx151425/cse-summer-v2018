package com.cse.summer.domain;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author 王振琦
 */
@javax.persistence.Entity
@Table(name = "cse_machine")
public class Machine extends Entity {
    private String name;
    private String number;
    private Integer cylinderAmount;
    private String shipNo;
    private String classificationSociety;
    private String patent;
    private String type;

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getPatent() {
        return patent;
    }

    public void setPatent(String patent) {
        this.patent = patent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Structure> getStructureList() {
        return structureList;
    }

    public void setStructureList(List<Structure> structureList) {
        this.structureList = structureList;
    }
}
