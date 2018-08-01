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
    private String type;

    @Transient
    private List<Material> materialList;

    public Machine() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Material> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<Material> materialList) {
        this.materialList = materialList;
    }
}
