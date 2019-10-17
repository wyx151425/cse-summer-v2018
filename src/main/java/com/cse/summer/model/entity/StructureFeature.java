package com.cse.summer.model.entity;

import com.cse.summer.util.Constant;
import com.cse.summer.util.Generator;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 部套特征
 *
 * @author WangZhenqi
 */
@Entity
@Table(name = "cse_structure_feature")
public class StructureFeature extends SummerEntity {
    /**
     * 所属部套
     */
    @OneToOne(targetEntity = Material.class)
    @JoinColumn(name = "material_id", referencedColumnName = "id", unique = true)
    private Material material;
    /**
     * 功率
     */
    private String efficiency;
    /**
     * 转速
     */
    private String rotateRate;
    /**
     * 调试方式
     */
    private String debugMode;
    /**
     * 缸数
     */
    private String cylinderAmount;
    /**
     * 增压器型号
     */
    private String superchargerType;
    /**
     * 冰区加强
     */
    private String iceAreaEnhance;
    /**
     * 增压器布置
     */
    private String superchargerArrange;
    /**
     * 排气背压
     */
    private String exhaustBackPressure;
    /**
     * 主机转向
     */
    private String hostRotateDirection;
    /**
     * 螺旋桨类型
     */
    private String propellerType;
    /**
     * 主机电制
     */
    private String hostElectric;
    /**
     * 加热介质
     */
    private String heatingMedium;
    /**
     * 顶部支撑方式
     */
    private String topSupportMode;
    /**
     * 自由端二阶力矩补偿器
     */
    private String freeEndSecCompensator;
    /**
     * 输出端二阶力矩补偿器
     */
    private String outEndSecCompensator;
    /**
     * 阀杆材料
     */
    private String stemMaterial;
    /**
     * FIVA阀厂家
     */
    private String fivaValveManufacturer;
    /**
     * 电动液压启动阀厂家
     */
    private String electricStartPumpManufacturer;
    /**
     * 液压泵厂家
     */
    private String hydraulicPumpManufacturer;
    /**
     * 气缸注油器厂家
     */
    private String cylinderFuelInjectorManufacturer;
    /**
     * EGB
     */
    private String egb;
    /**
     * 扭振减震器
     */
    private String torsionalShockAbsorber;
    /**
     * 扫气箱灭火方式
     */
    private String scavengerFireExtMethod;
    /**
     * 液压油滤器厂家
     */
    private String hydraulicOilFilterManufacturer;
    /**
     * 遥控厂家
     */
    private String remoteControlManufacturer;
    /**
     * PMI传感器厂家
     */
    private String pmiSensorManufacturer;
    /**
     * 油雾探测器厂家
     */
    private String oilMistDetectorManufacturer;
    /**
     * PTO
     */
    private String pto;
    /**
     * 起吊方式
     */
    private String liftMethod;
    /**
     * SCR
     */
    private String scr;
    /**
     * 排气阀研磨机
     */
    private String exhaustValveGrinder;
    /**
     * 排气阀工作台
     */
    private String exhaustValveWorkbench;
    /**
     * 通用情况
     */
    private String universal;
    /**
     * 机型
     */
    private String machineType;
    /**
     * 待定
     */
    private String pending;
    /**
     * 燃油硫含量
     */
    private String fuelOilSulphurContent;

    public StructureFeature() {
    }

    public void initialize() {
        this.setObjectId(Generator.getObjectId());
        this.setStatus(Constant.Status.ENABLE);
        LocalDateTime dateTime = LocalDateTime.now().withNano(0);
        this.setCreateAt(dateTime);
        this.setUpdateAt(dateTime);
    }

    public static StructureFeature newInstance() {
        StructureFeature structureFeature = new StructureFeature();
        structureFeature.setObjectId(Generator.getObjectId());
        structureFeature.setStatus(Constant.Status.ENABLE);
        LocalDateTime dateTime = LocalDateTime.now().withNano(0);
        structureFeature.setCreateAt(dateTime);
        structureFeature.setUpdateAt(dateTime);

        structureFeature.setEfficiency("");
        structureFeature.setRotateRate("");
        structureFeature.setDebugMode("");
        structureFeature.setCylinderAmount("");
        structureFeature.setSuperchargerType("");
        structureFeature.setIceAreaEnhance("");
        structureFeature.setSuperchargerArrange("");
        structureFeature.setExhaustBackPressure("");
        structureFeature.setHostRotateDirection("");
        structureFeature.setPropellerType("");
        structureFeature.setHostElectric("");
        structureFeature.setHeatingMedium("");
        structureFeature.setTopSupportMode("");
        structureFeature.setFreeEndSecCompensator("");
        structureFeature.setOutEndSecCompensator("");
        structureFeature.setStemMaterial("");
        structureFeature.setFivaValveManufacturer("");
        structureFeature.setElectricStartPumpManufacturer("");
        structureFeature.setHydraulicPumpManufacturer("");
        structureFeature.setCylinderFuelInjectorManufacturer("");
        structureFeature.setEgb("");
        structureFeature.setTorsionalShockAbsorber("");
        structureFeature.setScavengerFireExtMethod("");
        structureFeature.setHydraulicOilFilterManufacturer("");
        structureFeature.setRemoteControlManufacturer("");
        structureFeature.setPmiSensorManufacturer("");
        structureFeature.setOilMistDetectorManufacturer("");
        structureFeature.setPto("");
        structureFeature.setLiftMethod("");
        structureFeature.setScr("");
        structureFeature.setExhaustValveGrinder("");
        structureFeature.setExhaustValveWorkbench("");
        structureFeature.setUniversal("");
        structureFeature.setMachineType("");
        structureFeature.setPending("");
        structureFeature.setFuelOilSulphurContent("");

        return structureFeature;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(String efficiency) {
        this.efficiency = efficiency;
    }

    public String getRotateRate() {
        return rotateRate;
    }

    public void setRotateRate(String rotateRate) {
        this.rotateRate = rotateRate;
    }

    public String getDebugMode() {
        return debugMode;
    }

    public void setDebugMode(String debugMode) {
        this.debugMode = debugMode;
    }

    public String getCylinderAmount() {
        return cylinderAmount;
    }

    public void setCylinderAmount(String cylinderAmount) {
        this.cylinderAmount = cylinderAmount;
    }

    public String getSuperchargerType() {
        return superchargerType;
    }

    public void setSuperchargerType(String superchargerType) {
        this.superchargerType = superchargerType;
    }

    public String getIceAreaEnhance() {
        return iceAreaEnhance;
    }

    public void setIceAreaEnhance(String iceAreaEnhance) {
        this.iceAreaEnhance = iceAreaEnhance;
    }

    public String getSuperchargerArrange() {
        return superchargerArrange;
    }

    public void setSuperchargerArrange(String superchargerArrange) {
        this.superchargerArrange = superchargerArrange;
    }

    public String getExhaustBackPressure() {
        return exhaustBackPressure;
    }

    public void setExhaustBackPressure(String exhaustBackPressure) {
        this.exhaustBackPressure = exhaustBackPressure;
    }

    public String getHostRotateDirection() {
        return hostRotateDirection;
    }

    public void setHostRotateDirection(String hostRotateDirection) {
        this.hostRotateDirection = hostRotateDirection;
    }

    public String getPropellerType() {
        return propellerType;
    }

    public void setPropellerType(String propellerType) {
        this.propellerType = propellerType;
    }

    public String getHostElectric() {
        return hostElectric;
    }

    public void setHostElectric(String hostElectric) {
        this.hostElectric = hostElectric;
    }

    public String getHeatingMedium() {
        return heatingMedium;
    }

    public void setHeatingMedium(String heatingMedium) {
        this.heatingMedium = heatingMedium;
    }

    public String getTopSupportMode() {
        return topSupportMode;
    }

    public void setTopSupportMode(String topSupportMode) {
        this.topSupportMode = topSupportMode;
    }

    public String getFreeEndSecCompensator() {
        return freeEndSecCompensator;
    }

    public void setFreeEndSecCompensator(String freeEndSecCompensator) {
        this.freeEndSecCompensator = freeEndSecCompensator;
    }

    public String getOutEndSecCompensator() {
        return outEndSecCompensator;
    }

    public void setOutEndSecCompensator(String outEndSecCompensator) {
        this.outEndSecCompensator = outEndSecCompensator;
    }

    public String getStemMaterial() {
        return stemMaterial;
    }

    public void setStemMaterial(String stemMaterial) {
        this.stemMaterial = stemMaterial;
    }

    public String getFivaValveManufacturer() {
        return fivaValveManufacturer;
    }

    public void setFivaValveManufacturer(String fivaValveManufacturer) {
        this.fivaValveManufacturer = fivaValveManufacturer;
    }

    public String getElectricStartPumpManufacturer() {
        return electricStartPumpManufacturer;
    }

    public void setElectricStartPumpManufacturer(String electricStartPumpManufacturer) {
        this.electricStartPumpManufacturer = electricStartPumpManufacturer;
    }

    public String getHydraulicPumpManufacturer() {
        return hydraulicPumpManufacturer;
    }

    public void setHydraulicPumpManufacturer(String hydraulicPumpManufacturer) {
        this.hydraulicPumpManufacturer = hydraulicPumpManufacturer;
    }

    public String getCylinderFuelInjectorManufacturer() {
        return cylinderFuelInjectorManufacturer;
    }

    public void setCylinderFuelInjectorManufacturer(String cylinderFuelInjectorManufacturer) {
        this.cylinderFuelInjectorManufacturer = cylinderFuelInjectorManufacturer;
    }

    public String getEgb() {
        return egb;
    }

    public void setEgb(String egb) {
        this.egb = egb;
    }

    public String getTorsionalShockAbsorber() {
        return torsionalShockAbsorber;
    }

    public void setTorsionalShockAbsorber(String torsionalShockAbsorber) {
        this.torsionalShockAbsorber = torsionalShockAbsorber;
    }

    public String getScavengerFireExtMethod() {
        return scavengerFireExtMethod;
    }

    public void setScavengerFireExtMethod(String scavengerFireExtMethod) {
        this.scavengerFireExtMethod = scavengerFireExtMethod;
    }

    public String getHydraulicOilFilterManufacturer() {
        return hydraulicOilFilterManufacturer;
    }

    public void setHydraulicOilFilterManufacturer(String hydraulicOilFilterManufacturer) {
        this.hydraulicOilFilterManufacturer = hydraulicOilFilterManufacturer;
    }

    public String getRemoteControlManufacturer() {
        return remoteControlManufacturer;
    }

    public void setRemoteControlManufacturer(String remoteControlManufacturer) {
        this.remoteControlManufacturer = remoteControlManufacturer;
    }

    public String getPmiSensorManufacturer() {
        return pmiSensorManufacturer;
    }

    public void setPmiSensorManufacturer(String pmiSensorManufacturer) {
        this.pmiSensorManufacturer = pmiSensorManufacturer;
    }

    public String getOilMistDetectorManufacturer() {
        return oilMistDetectorManufacturer;
    }

    public void setOilMistDetectorManufacturer(String oilMistDetectorManufacturer) {
        this.oilMistDetectorManufacturer = oilMistDetectorManufacturer;
    }

    public String getPto() {
        return pto;
    }

    public void setPto(String pto) {
        this.pto = pto;
    }

    public String getLiftMethod() {
        return liftMethod;
    }

    public void setLiftMethod(String liftMethod) {
        this.liftMethod = liftMethod;
    }

    public String getScr() {
        return scr;
    }

    public void setScr(String scr) {
        this.scr = scr;
    }

    public String getExhaustValveGrinder() {
        return exhaustValveGrinder;
    }

    public void setExhaustValveGrinder(String exhaustValveGrinder) {
        this.exhaustValveGrinder = exhaustValveGrinder;
    }

    public String getExhaustValveWorkbench() {
        return exhaustValveWorkbench;
    }

    public void setExhaustValveWorkbench(String exhaustValveWorkbench) {
        this.exhaustValveWorkbench = exhaustValveWorkbench;
    }

    public String getUniversal() {
        return universal;
    }

    public void setUniversal(String universal) {
        this.universal = universal;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getPending() {
        return pending;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }

    public String getFuelOilSulphurContent() {
        return fuelOilSulphurContent;
    }

    public void setFuelOilSulphurContent(String fuelOilSulphurContent) {
        this.fuelOilSulphurContent = fuelOilSulphurContent;
    }
}
