package com.cse.summer.service.impl;

import com.cse.summer.model.dto.Excel;
import com.cse.summer.model.entity.Material;
import com.cse.summer.model.entity.Structure;
import com.cse.summer.model.entity.StructureFeature;
import com.cse.summer.repository.MaterialRepository;
import com.cse.summer.repository.StructureFeatureRepository;
import com.cse.summer.repository.StructureRepository;
import com.cse.summer.service.StructureFeatureService;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 部套特征信息业务逻辑实现
 *
 * @author WangZhenqi
 */
@Service(value = "structureFeatureService")
public class StructureFeatureServiceImpl implements StructureFeatureService {

    private final StructureRepository structureRepository;
    private final MaterialRepository materialRepository;
    private final StructureFeatureRepository structureFeatureRepository;

    @Autowired
    public StructureFeatureServiceImpl(StructureRepository structureRepository, MaterialRepository materialRepository, StructureFeatureRepository structureFeatureRepository) {
        this.structureRepository = structureRepository;
        this.materialRepository = materialRepository;
        this.structureFeatureRepository = structureFeatureRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveStructureFeature(StructureFeature structureFeature) {
        structureFeature.initialize();
        structureFeatureRepository.save(structureFeature);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStructureFeature(StructureFeature structureFeature) {
        structureFeature.setUpdateAt(LocalDateTime.now().withNano(0));
        structureFeatureRepository.save(structureFeature);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public StructureFeature findStructureFeatureByMaterial(Material material) {
        StructureFeature structureFeature = structureFeatureRepository.findStructureFeatureByMaterial(material);
        if (null == structureFeature) {
            structureFeature = StructureFeature.newInstance();
            structureFeature.setMaterial(material);
            return structureFeature;
        }
        return structureFeature;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public StructureFeature findStructureFeatureByMaterialNoAndVersion(String materialNo, Integer version) {
        Material material = materialRepository.findMaterialByMaterialNoAndVersionAndLevel(materialNo, version, 0);
        StructureFeature structureFeature = structureFeatureRepository.findStructureFeatureByMaterial(material);
        if (null == structureFeature) {
            structureFeature = StructureFeature.newInstance();
            structureFeature.setMaterial(material);
            return structureFeature;
        }
        return structureFeature;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<StructureFeature> findStructureFeatureListByProperty(StructureFeature structureFeature) {
        return structureFeatureRepository.findAll((Specification<StructureFeature>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates1 = new ArrayList<>();
            if (null != structureFeature.getEfficiency() && !"".equals(structureFeature.getEfficiency())) {
                predicates1.add(criteriaBuilder.equal(root.get("efficiency"), structureFeature.getEfficiency()));
            }
            if (null != structureFeature.getRotateRate() && !"".equals(structureFeature.getRotateRate())) {
                predicates1.add(criteriaBuilder.equal(root.get("rotateRate"), structureFeature.getRotateRate()));
            }
            if (null != structureFeature.getDebugMode() && !"".equals(structureFeature.getDebugMode())) {
                predicates1.add(criteriaBuilder.equal(root.get("debugMode"), structureFeature.getDebugMode()));
            }
            if (null != structureFeature.getCylinderAmount() && !"".equals(structureFeature.getCylinderAmount())) {
                predicates1.add(criteriaBuilder.equal(root.get("cylinderAmount"), structureFeature.getCylinderAmount()));
            }
            if (null != structureFeature.getSuperchargerType() && !"".equals(structureFeature.getSuperchargerType())) {
                predicates1.add(criteriaBuilder.equal(root.get("superchargerType"), structureFeature.getSuperchargerType()));
            }
            if (null != structureFeature.getIceAreaEnhance() && !"".equals(structureFeature.getIceAreaEnhance())) {
                predicates1.add(criteriaBuilder.equal(root.get("iceAreaEnhance"), structureFeature.getIceAreaEnhance()));
            }
            if (null != structureFeature.getSuperchargerArrange() && !"".equals(structureFeature.getSuperchargerArrange())) {
                predicates1.add(criteriaBuilder.equal(root.get("superchargerArrange"), structureFeature.getSuperchargerArrange()));
            }
            if (null != structureFeature.getExhaustBackPressure() && !"".equals(structureFeature.getExhaustBackPressure())) {
                predicates1.add(criteriaBuilder.equal(root.get("exhaustBackPressure"), structureFeature.getExhaustBackPressure()));
            }
            if (null != structureFeature.getHostRotateDirection() && !"".equals(structureFeature.getHostRotateDirection())) {
                predicates1.add(criteriaBuilder.equal(root.get("hostRotateDirection"), structureFeature.getHostRotateDirection()));
            }
            if (null != structureFeature.getPropellerType() && !"".equals(structureFeature.getPropellerType())) {
                predicates1.add(criteriaBuilder.equal(root.get("propellerType"), structureFeature.getPropellerType()));
            }
            if (null != structureFeature.getHostElectric() && !"".equals(structureFeature.getHostElectric())) {
                predicates1.add(criteriaBuilder.equal(root.get("hostElectric"), structureFeature.getHostElectric()));
            }
            if (null != structureFeature.getHeatingMedium() && !"".equals(structureFeature.getHeatingMedium())) {
                predicates1.add(criteriaBuilder.equal(root.get("heatingMedium"), structureFeature.getHeatingMedium()));
            }
            if (null != structureFeature.getTopSupportMode() && !"".equals(structureFeature.getTopSupportMode())) {
                predicates1.add(criteriaBuilder.equal(root.get("topSupportMode"), structureFeature.getTopSupportMode()));
            }
            if (null != structureFeature.getFreeEndSecCompensator() && !"".equals(structureFeature.getFreeEndSecCompensator())) {
                predicates1.add(criteriaBuilder.equal(root.get("freeEndSecCompensator"), structureFeature.getFreeEndSecCompensator()));
            }
            if (null != structureFeature.getOutEndSecCompensator() && !"".equals(structureFeature.getOutEndSecCompensator())) {
                predicates1.add(criteriaBuilder.equal(root.get("outEndSecCompensator"), structureFeature.getOutEndSecCompensator()));
            }
            if (null != structureFeature.getStemMaterial() && !"".equals(structureFeature.getStemMaterial())) {
                predicates1.add(criteriaBuilder.equal(root.get("stemMaterial"), structureFeature.getStemMaterial()));
            }
            if (null != structureFeature.getFivaValveManufacturer() && !"".equals(structureFeature.getFivaValveManufacturer())) {
                predicates1.add(criteriaBuilder.equal(root.get("fivaValveManufacturer"), structureFeature.getFivaValveManufacturer()));
            }
            if (null != structureFeature.getElectricStartPumpManufacturer() && !"".equals(structureFeature.getElectricStartPumpManufacturer())) {
                predicates1.add(criteriaBuilder.equal(root.get("electricStartPumpManufacturer"), structureFeature.getElectricStartPumpManufacturer()));
            }
            if (null != structureFeature.getHydraulicPumpManufacturer() && !"".equals(structureFeature.getHydraulicPumpManufacturer())) {
                predicates1.add(criteriaBuilder.equal(root.get("hydraulicPumpManufacturer"), structureFeature.getHydraulicPumpManufacturer()));
            }
            if (null != structureFeature.getCylinderFuelInjectorManufacturer() && !"".equals(structureFeature.getCylinderFuelInjectorManufacturer())) {
                predicates1.add(criteriaBuilder.equal(root.get("cylinderFuelInjectorManufacturer"), structureFeature.getCylinderFuelInjectorManufacturer()));
            }
            if (null != structureFeature.getEgb() && !"".equals(structureFeature.getEgb())) {
                predicates1.add(criteriaBuilder.equal(root.get("egb"), structureFeature.getEgb()));
            }
            if (null != structureFeature.getTorsionalShockAbsorber() && !"".equals(structureFeature.getTorsionalShockAbsorber())) {
                predicates1.add(criteriaBuilder.equal(root.get("torsionalShockAbsorber"), structureFeature.getTorsionalShockAbsorber()));
            }
            if (null != structureFeature.getScavengerFireExtMethod() && !"".equals(structureFeature.getScavengerFireExtMethod())) {
                predicates1.add(criteriaBuilder.equal(root.get("scavengerFireExtMethod"), structureFeature.getScavengerFireExtMethod()));
            }
            if (null != structureFeature.getHydraulicOilFilterManufacturer() && !"".equals(structureFeature.getHydraulicOilFilterManufacturer())) {
                predicates1.add(criteriaBuilder.equal(root.get("hydraulicOilFilterManufacturer"), structureFeature.getHydraulicOilFilterManufacturer()));
            }
            if (null != structureFeature.getRemoteControlManufacturer() && !"".equals(structureFeature.getRemoteControlManufacturer())) {
                predicates1.add(criteriaBuilder.equal(root.get("remoteControlManufacturer"), structureFeature.getRemoteControlManufacturer()));
            }
            if (null != structureFeature.getPmiSensorManufacturer() && !"".equals(structureFeature.getPmiSensorManufacturer())) {
                predicates1.add(criteriaBuilder.equal(root.get("pmiSensorManufacturer"), structureFeature.getPmiSensorManufacturer()));
            }
            if (null != structureFeature.getOilMistDetectorManufacturer() && !"".equals(structureFeature.getOilMistDetectorManufacturer())) {
                predicates1.add(criteriaBuilder.equal(root.get("oilMistDetectorManufacturer"), structureFeature.getOilMistDetectorManufacturer()));
            }
            if (null != structureFeature.getPto() && !"".equals(structureFeature.getPto())) {
                predicates1.add(criteriaBuilder.equal(root.get("pto"), structureFeature.getPto()));
            }
            if (null != structureFeature.getLiftMethod() && !"".equals(structureFeature.getLiftMethod())) {
                predicates1.add(criteriaBuilder.equal(root.get("liftMethod"), structureFeature.getLiftMethod()));
            }
            if (null != structureFeature.getScr() && !"".equals(structureFeature.getScr())) {
                predicates1.add(criteriaBuilder.equal(root.get("scr"), structureFeature.getScr()));
            }
            if (null != structureFeature.getExhaustValveGrinder() && !"".equals(structureFeature.getExhaustValveGrinder())) {
                predicates1.add(criteriaBuilder.equal(root.get("exhaustValveGrinder"), structureFeature.getExhaustValveGrinder()));
            }
            if (null != structureFeature.getExhaustValveWorkbench() && !"".equals(structureFeature.getExhaustValveWorkbench())) {
                predicates1.add(criteriaBuilder.equal(root.get("exhaustValveWorkbench"), structureFeature.getExhaustValveWorkbench()));
            }
            if (null != structureFeature.getFuelOilSulphurContent() && !"".equals(structureFeature.getFuelOilSulphurContent())) {
                predicates1.add(criteriaBuilder.equal(root.get("fuelOilSulphurContent"), structureFeature.getFuelOilSulphurContent()));
            }
            if (null != structureFeature.getUniversal() && !"".equals(structureFeature.getUniversal())) {
                predicates1.add(criteriaBuilder.equal(root.get("universal"), structureFeature.getUniversal()));
            }
            if (null != structureFeature.getPending() && !"".equals(structureFeature.getPending())) {
                predicates1.add(criteriaBuilder.equal(root.get("pending"), structureFeature.getPending()));
            }
            if (null != structureFeature.getMachineType() && !"".equals(structureFeature.getMachineType())) {
                if (predicates1.size() > 0) {
                    Subquery<Integer> propertyQuery = criteriaQuery.subquery(Integer.class);
                    Root<StructureFeature> from = propertyQuery.from(StructureFeature.class);
                    propertyQuery.select(from.get("id")).where(criteriaBuilder.or(predicates1.toArray(new Predicate[0])));

                    return criteriaQuery.where(root.get("id").in(propertyQuery), criteriaBuilder.like(root.get("machineType"), "%" + structureFeature.getMachineType() + "%"))
                            .getRestriction();
                } else {
                    return criteriaQuery.where(criteriaBuilder.like(root.get("machineType"), "%" + structureFeature.getMachineType() + "%")).getRestriction();
                }
            }
            return criteriaQuery.where(criteriaBuilder.or(predicates1.toArray(new Predicate[0])))
                    .getRestriction();
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Excel exportStructureListByStructureFeature(StructureFeature structureFeature) {
        List<StructureFeature> featureList = this.findStructureFeatureListByProperty(structureFeature);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();

        int row = 0;
        XSSFRow titleRow = sheet.createRow(row);
        XSSFCell titieCell30 = titleRow.createCell(0);
        titieCell30.setCellValue("部套号");
        XSSFCell titieCell31 = titleRow.createCell(1);
        titieCell31.setCellValue("物料编码");
        XSSFCell titieCell32 = titleRow.createCell(2);
        titieCell32.setCellValue("版本");
        row++;

        for (StructureFeature feature : featureList) {
            XSSFRow dataRow = sheet.createRow(row);
            XSSFCell titleCell0 = dataRow.createCell(0);
            Structure structure = structureRepository.findAllByMaterialNoAndVersion(feature.getMaterial().getMaterialNo(), feature.getMaterial().getVersion()).get(0);
            titleCell0.setCellValue(structure.getStructureNo());
            XSSFCell titleCell1 = dataRow.createCell(1);
            titleCell1.setCellValue(structure.getMaterialNo());
            XSSFCell titleCell2 = dataRow.createCell(2);
            titleCell2.setCellValue(structure.getVersion());
            row++;
        }

        List<StructureFeature> allFeature = structureFeatureRepository.findAll();
        allFeature.removeAll(featureList);

        for (StructureFeature feature : allFeature) {
            XSSFRow dataRow = sheet.createRow(row);
            XSSFCell titleCell0 = dataRow.createCell(0);
            Structure structure = structureRepository.findAllByMaterialNoAndVersion(feature.getMaterial().getMaterialNo(), feature.getMaterial().getVersion()).get(0);
            titleCell0.setCellValue(structure.getStructureNo());
            XSSFCell titleCell1 = dataRow.createCell(1);
            titleCell1.setCellValue("待定");
            row++;
        }

        row++;

        XSSFRow promptRow = sheet.createRow(row);
        XSSFCell promptCell = promptRow.createCell(0);
        promptCell.setCellValue("查询条件");
        row++;

        int column = 0;
        XSSFRow row0 = sheet.createRow(row);
        XSSFRow row1 = sheet.createRow(row + 1);
        if (null != structureFeature.getMachineType() && !"".equals(structureFeature.getMachineType())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("机型");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getMachineType());
            column++;
        }
        if (null != structureFeature.getUniversal() && !"".equals(structureFeature.getUniversal())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("通用情况");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getUniversal());
            column++;
        }
        if (null != structureFeature.getPending() && !"".equals(structureFeature.getPending())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("待定情况");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getPending());
            column++;
        }
        if (null != structureFeature.getEfficiency() && !"".equals(structureFeature.getEfficiency())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("功率");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getEfficiency());
            column++;
        }
        if (null != structureFeature.getRotateRate() && !"".equals(structureFeature.getRotateRate())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("转速");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getRotateRate());
            column++;
        }
        if (null != structureFeature.getDebugMode() && !"".equals(structureFeature.getDebugMode())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("调试方式");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getDebugMode());
            column++;
        }
        if (null != structureFeature.getCylinderAmount() && !"".equals(structureFeature.getCylinderAmount())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("缸数");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getCylinderAmount());
            column++;
        }
        if (null != structureFeature.getSuperchargerType() && !"".equals(structureFeature.getSuperchargerType())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("增压器型号");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getSuperchargerType());
            column++;
        }
        if (null != structureFeature.getIceAreaEnhance() && !"".equals(structureFeature.getIceAreaEnhance())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("冰区加强");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getIceAreaEnhance());
            column++;
        }
        if (null != structureFeature.getSuperchargerArrange() && !"".equals(structureFeature.getSuperchargerArrange())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("增压器布置");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getSuperchargerArrange());
            column++;
        }
        if (null != structureFeature.getExhaustBackPressure() && !"".equals(structureFeature.getExhaustBackPressure())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("排气背压");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getExhaustBackPressure());
            column++;
        }
        if (null != structureFeature.getHostRotateDirection() && !"".equals(structureFeature.getHostRotateDirection())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("主机转向");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getHostRotateDirection());
            column++;
        }
        if (null != structureFeature.getPropellerType() && !"".equals(structureFeature.getPropellerType())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("螺旋桨类型");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getPropellerType());
            column++;
        }
        if (null != structureFeature.getHostElectric() && !"".equals(structureFeature.getHostElectric())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("主机电制");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getHostElectric());
            column++;
        }
        if (null != structureFeature.getHeatingMedium() && !"".equals(structureFeature.getHeatingMedium())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("加热介质");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getHeatingMedium());
            column++;
        }
        if (null != structureFeature.getTopSupportMode() && !"".equals(structureFeature.getTopSupportMode())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("顶部支撑方式");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getTopSupportMode());
            column++;
        }
        if (null != structureFeature.getFreeEndSecCompensator() && !"".equals(structureFeature.getFreeEndSecCompensator())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("自由端二阶力矩补偿器");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getFreeEndSecCompensator());
            column++;
        }
        if (null != structureFeature.getOutEndSecCompensator() && !"".equals(structureFeature.getOutEndSecCompensator())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("输出端二阶力矩补偿器");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getOutEndSecCompensator());
            column++;
        }
        if (null != structureFeature.getStemMaterial() && !"".equals(structureFeature.getStemMaterial())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("阀杆材料");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getStemMaterial());
            column++;
        }
        if (null != structureFeature.getFivaValveManufacturer() && !"".equals(structureFeature.getFivaValveManufacturer())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("FIVA阀厂家");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getFivaValveManufacturer());
            column++;
        }
        if (null != structureFeature.getElectricStartPumpManufacturer() && !"".equals(structureFeature.getElectricStartPumpManufacturer())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("电动液压启动阀厂家");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getElectricStartPumpManufacturer());
            column++;
        }
        if (null != structureFeature.getHydraulicPumpManufacturer() && !"".equals(structureFeature.getHydraulicPumpManufacturer())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("液压泵厂家");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getHydraulicPumpManufacturer());
            column++;
        }
        if (null != structureFeature.getCylinderFuelInjectorManufacturer() && !"".equals(structureFeature.getCylinderFuelInjectorManufacturer())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("气缸注油器厂家");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getCylinderFuelInjectorManufacturer());
            column++;
        }
        if (null != structureFeature.getEgb() && !"".equals(structureFeature.getEgb())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("EGB");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getEgb());
            column++;
        }
        if (null != structureFeature.getTorsionalShockAbsorber() && !"".equals(structureFeature.getTorsionalShockAbsorber())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("扭振减震器");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getTorsionalShockAbsorber());
            column++;
        }
        if (null != structureFeature.getScavengerFireExtMethod() && !"".equals(structureFeature.getScavengerFireExtMethod())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("扫气箱灭火方式");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getScavengerFireExtMethod());
            column++;
        }
        if (null != structureFeature.getHydraulicOilFilterManufacturer() && !"".equals(structureFeature.getHydraulicOilFilterManufacturer())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("液压油滤器厂家");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getHydraulicOilFilterManufacturer());
            column++;
        }
        if (null != structureFeature.getRemoteControlManufacturer() && !"".equals(structureFeature.getRemoteControlManufacturer())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("遥控厂家");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getRemoteControlManufacturer());
            column++;
        }
        if (null != structureFeature.getPmiSensorManufacturer() && !"".equals(structureFeature.getPmiSensorManufacturer())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("PMI传感器厂家");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getPmiSensorManufacturer());
            column++;
        }
        if (null != structureFeature.getOilMistDetectorManufacturer() && !"".equals(structureFeature.getOilMistDetectorManufacturer())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("油雾探测器厂家");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getOilMistDetectorManufacturer());
            column++;
        }
        if (null != structureFeature.getPto() && !"".equals(structureFeature.getPto())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("PTO");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getPto());
            column++;
        }
        if (null != structureFeature.getLiftMethod() && !"".equals(structureFeature.getLiftMethod())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("起吊方式");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getLiftMethod());
            column++;
        }
        if (null != structureFeature.getScr() && !"".equals(structureFeature.getScr())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("SCR");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getScr());
            column++;
        }
        if (null != structureFeature.getExhaustValveGrinder() && !"".equals(structureFeature.getExhaustValveGrinder())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("排气阀研磨机");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getExhaustValveGrinder());
            column++;
        }
        if (null != structureFeature.getExhaustValveWorkbench() && !"".equals(structureFeature.getExhaustValveWorkbench())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("排气阀工作台");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getExhaustValveWorkbench());
            column++;
        }
        if (null != structureFeature.getFuelOilSulphurContent() && !"".equals(structureFeature.getFuelOilSulphurContent())) {
            XSSFCell cell0 = row0.createCell(column);
            cell0.setCellValue("燃油硫含量");
            XSSFCell cell1 = row1.createCell(column);
            cell1.setCellValue(structureFeature.getFuelOilSulphurContent());
            column++;
        }

        return new Excel("excel", workbook);
    }
}
