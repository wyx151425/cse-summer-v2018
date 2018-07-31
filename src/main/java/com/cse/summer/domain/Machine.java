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

    @Transient
    private List<Material> materialList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Material> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<Material> materialList) {
        this.materialList = materialList;
    }
}
