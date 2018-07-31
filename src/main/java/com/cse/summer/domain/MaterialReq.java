package com.cse.summer.domain;

public class MaterialReq extends Material {
    private String parentMaterialNo;

    public String getParentMaterialNo() {
        return parentMaterialNo;
    }

    public void setParentMaterialNo(String parentMaterialNo) {
        this.parentMaterialNo = parentMaterialNo;
    }
}
