package com.cse.summer.model.dto;

import com.cse.summer.model.entity.Material;
import com.cse.summer.model.entity.Structure;

public class StructMater {
    private Structure structure;
    private Material material;

    public StructMater(Structure structure, Material material) {
        this.structure = structure;
        this.material = material;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
