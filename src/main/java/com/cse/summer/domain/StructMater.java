package com.cse.summer.domain;

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
