package com.cse.summer.model.dto;

import com.cse.summer.model.entity.Structure;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author WangZhenqi
 */
public class StructureRequest {
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
     * 总数量
     */
    private Integer amount;
    /**
     * 文件
     */
    private MultipartFile file;

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Structure getStructure() {
        Structure structure = new Structure();
        structure.setMachineName(this.machineName);
        structure.setStructureNo(this.structureNo);
        structure.setMaterialNo(this.materialNo);
        structure.setAmount(this.amount);
        return structure;
    }
}
