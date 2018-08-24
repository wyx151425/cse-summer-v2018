package com.cse.summer.domain;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author 王振琦
 */
@javax.persistence.Entity
@Table(name = "cse_material")
public class Material extends Entity implements Serializable {
    /**
     * 部套号
     */
    @Transient
    private String structureNo;
    private String atNo;
    /**
     * 物料名称
     */
    private String name;
    /**
     * 中文名
     */
    private String chinese;
    /**
     * 父物料的objectId
     */
    private String parentId;
    /**
     * 所在层级
     */
    private Integer level;
    /**
     * 部件号
     */
    private String positionNo;
    /**
     * 排序号
     */
    private String sequenceNo;
    /**
     * 当前版本号
     */
    private Integer version;
    /**
     * 最新版本号
     */
    private Integer latestVersion;

    /**
     * 物料号
     */
    private String materialNo;
    /**
     * 材料名称
     */
    private String material;

    /**
     * 图纸大小
     */
    private String drawingSize;
    /**
     * 图纸编号
     */
    private String drawingNo;

    /**
     * 重量
     */
    private String weight;
    /**
     * 总数量
     */
    @Transient
    private Integer amount;
    /**
     * 数量
     */
    private Integer absoluteAmount;
    /**
     * 页数
     */
    private String page;
    /**
     * 子节点数量
     */
    private Integer childCount;
    /**
     * 备件数
     */
    @Transient
    private Integer spare;
    /**
     * 备件表达式
     */
    private String spareExp;
    /**
     * 备件货源
     */
    private String spareSrc;
    /**
     * 设计备注
     */
    private String designNote;
    /**
     * 喷漆防护
     */
    private String paintProtect;
    /**
     * 更改通知
     */
    private String modifyNote;

    public Material() {
    }

    public String getStructureNo() {
        return structureNo;
    }

    public void setStructureNo(String structureNo) {
        this.structureNo = structureNo;
    }

    public String getAtNo() {
        return atNo;
    }

    public void setAtNo(String atNo) {
        this.atNo = atNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPositionNo() {
        return positionNo;
    }

    public void setPositionNo(String positionNo) {
        this.positionNo = positionNo;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(Integer latestVersion) {
        this.latestVersion = latestVersion;
    }

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDrawingSize() {
        return drawingSize;
    }

    public void setDrawingSize(String drawingSize) {
        this.drawingSize = drawingSize;
    }

    public String getDrawingNo() {
        return drawingNo;
    }

    public void setDrawingNo(String drawingNo) {
        this.drawingNo = drawingNo;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAbsoluteAmount() {
        return absoluteAmount;
    }

    public void setAbsoluteAmount(Integer absoluteAmount) {
        this.absoluteAmount = absoluteAmount;
    }

    public String getModifyNote() {
        return modifyNote;
    }

    public void setModifyNote(String modifyNote) {
        this.modifyNote = modifyNote;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    public Integer getSpare() {
        return spare;
    }

    public void setSpare(Integer spare) {
        this.spare = spare;
    }

    public String getSpareExp() {
        return spareExp;
    }

    public void setSpareExp(String spareExp) {
        this.spareExp = spareExp;
    }

    public String getSpareSrc() {
        return spareSrc;
    }

    public void setSpareSrc(String spareSrc) {
        this.spareSrc = spareSrc;
    }

    public String getDesignNote() {
        return designNote;
    }

    public void setDesignNote(String designNote) {
        this.designNote = designNote;
    }

    public String getPaintProtect() {
        return paintProtect;
    }

    public void setPaintProtect(String paintProtect) {
        this.paintProtect = paintProtect;
    }

    @Override
    public String toString() {
        return "Material{" +
                "materialNo='" + materialNo  +
                ", version=" + version + '\'' +
                ", latestVersion=" + latestVersion + '\'' +
                '}';
    }
}
