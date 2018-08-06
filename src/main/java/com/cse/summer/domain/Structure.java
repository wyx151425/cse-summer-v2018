package com.cse.summer.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cse_structure")
public class Structure extends com.cse.summer.domain.Entity {
    private String machineName;
    private String structureNo;
    private String revision;
    private Integer version;

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
}
