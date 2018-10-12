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
    /**
     * 层级
     */
    private Integer level;
    /**
     * 件号
     */
    private String positionNo;
    /**
     * 物料号
     */
    private String materialNo;
    /**
     * 图纸编号
     */
    private String drawingNo;
    /**
     * 图纸大小
     */
    private String drawingSize;
    /**
     * 英文名称
     */
    private String name;
    /**
     * 中文名称
     */
    private String chinese;
    /**
     * 专利材料
     */
    private String material;
    /**
     * 国标材料
     */
    private String standard;
    /**
     * 层数量
     */
    private Integer absoluteAmount;
    /**
     * 总数量
     */
    @Transient
    private Integer amount;
    /**
     * 货源
     */
    private String source;
    /**
     * 单重
     */
    private String weight;
    /**
     * 备件数
     */
    @Transient
    private Integer spare;
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
     * 更改
     */
    private String modifyNote;
    /**
     * ERP-父项
     */
    private String erpParent;

    /**
     * 排序号
     */
    private String sequenceNo;
    /**
     * 页数
     */
    private String page;
    /**
     * 备件表达式
     */
    private String spareExp;

    /**
     * 父物料的objectId
     */
    private String parentId;
    /**
     * 所在部套的顶层物料号
     */
    private String atNo;
    /**
     * 当前版本号
     */
    private Integer version;
    /**
     * 最新版本号
     */
    private Integer latestVersion;
    /**
     * 子节点数量
     */
    private Integer childCount;

    /**
     * 编制者
     */
    private String organizer;
    /**
     * 校对者
     */
    private String proofreader;
    /**
     * 审核者
     */
    private String auditor;

    public Material() {
    }

    public String getStructureNo() {
        return structureNo;
    }

    public void setStructureNo(String structureNo) {
        this.structureNo = structureNo;
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

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    public String getDrawingNo() {
        return drawingNo;
    }

    public void setDrawingNo(String drawingNo) {
        this.drawingNo = drawingNo;
    }

    public String getDrawingSize() {
        return drawingSize;
    }

    public void setDrawingSize(String drawingSize) {
        this.drawingSize = drawingSize;
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

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public Integer getAbsoluteAmount() {
        return absoluteAmount;
    }

    public void setAbsoluteAmount(Integer absoluteAmount) {
        this.absoluteAmount = absoluteAmount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Integer getSpare() {
        return spare;
    }

    public void setSpare(Integer spare) {
        this.spare = spare;
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

    public String getModifyNote() {
        return modifyNote;
    }

    public void setModifyNote(String modifyNote) {
        this.modifyNote = modifyNote;
    }

    public String getErpParent() {
        return erpParent;
    }

    public void setErpParent(String erpParent) {
        this.erpParent = erpParent;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSpareExp() {
        return spareExp;
    }

    public void setSpareExp(String spareExp) {
        this.spareExp = spareExp;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getAtNo() {
        return atNo;
    }

    public void setAtNo(String atNo) {
        this.atNo = atNo;
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

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getProofreader() {
        return proofreader;
    }

    public void setProofreader(String proofreader) {
        this.proofreader = proofreader;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }
}
