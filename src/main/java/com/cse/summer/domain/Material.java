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
    private String atRevision;
    /**
     * 物料名称
     */
    private String name;
    /**
     * 父物料的objectId
     */
    private String parentId;
    /**
     * 所在层级
     */
    private Integer level;
    /**
     * 源类型（1=MAN/2=WinGD）
     */
    private Integer type;
    /**
     * 原层级字符串
     */
    private String srcLevel;
    /**
     * 部件号
     */
    private String positionNo;
    /**
     * 排序号
     */
    private String sequenceNo;
    /**
     * 专利方版本
     */
    private String revision;
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
     * 图纸版本
     */
    private String drawingVersion;

    /**
     * 重量
     */
    private String weight;
    /**
     * 总数量
     */
    private Integer amount;
    /**
     * 数量
     */
    private Integer absoluteAmount;

    /**
     * 更改通知
     */
    private String modifyNote;
    /**
     * 页数
     */
    private String page;
    /**
     * 子节点数量
     */
    private Integer childCount;

    public Material() {
    }

    public Material(Integer type) {
        this.type = type;
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

    public String getAtRevision() {
        return atRevision;
    }

    public void setAtRevision(String atRevision) {
        this.atRevision = atRevision;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSrcLevel() {
        return srcLevel;
    }

    public void setSrcLevel(String srcLevel) {
        this.srcLevel = srcLevel;
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

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
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

    public String getDrawingVersion() {
        return drawingVersion;
    }

    public void setDrawingVersion(String drawingVersion) {
        this.drawingVersion = drawingVersion;
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

    @Override
    public String toString() {
        return "Material{" +
                "materialNo='" + materialNo  +
                ", revision='" + revision + '\'' +
                ", version=" + version + '\'' +
                ", latestVersion=" + latestVersion + '\'' +
                '}';
    }
}
