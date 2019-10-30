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
            List<Predicate> orPredicate = new ArrayList<>();
            if (null != structureFeature.getEfficiency() && !"".equals(structureFeature.getEfficiency())) {
                orPredicate.add(criteriaBuilder.equal(root.get("efficiency"), structureFeature.getEfficiency()));
            }
            if (null != structureFeature.getRotateRate() && !"".equals(structureFeature.getRotateRate())) {
                orPredicate.add(criteriaBuilder.equal(root.get("rotateRate"), structureFeature.getRotateRate()));
            }
            if (null != structureFeature.getDebugMode() && !"".equals(structureFeature.getDebugMode())) {
                orPredicate.add(criteriaBuilder.equal(root.get("debugMode"), structureFeature.getDebugMode()));
            }
            if (null != structureFeature.getCylinderAmount() && !"".equals(structureFeature.getCylinderAmount())) {
                orPredicate.add(criteriaBuilder.equal(root.get("cylinderAmount"), structureFeature.getCylinderAmount()));
            }
            if (null != structureFeature.getIceAreaEnhance() && !"".equals(structureFeature.getIceAreaEnhance())) {
                orPredicate.add(criteriaBuilder.equal(root.get("iceAreaEnhance"), structureFeature.getIceAreaEnhance()));
            }
            if (null != structureFeature.getExhaustBackPressure() && !"".equals(structureFeature.getExhaustBackPressure())) {
                orPredicate.add(criteriaBuilder.equal(root.get("exhaustBackPressure"), structureFeature.getExhaustBackPressure()));
            }
            if (null != structureFeature.getHostRotateDirection() && !"".equals(structureFeature.getHostRotateDirection())) {
                orPredicate.add(criteriaBuilder.equal(root.get("hostRotateDirection"), structureFeature.getHostRotateDirection()));
            }
            if (null != structureFeature.getPropellerType() && !"".equals(structureFeature.getPropellerType())) {
                orPredicate.add(criteriaBuilder.equal(root.get("propellerType"), structureFeature.getPropellerType()));
            }
            if (null != structureFeature.getHostElectric() && !"".equals(structureFeature.getHostElectric())) {
                orPredicate.add(criteriaBuilder.equal(root.get("hostElectric"), structureFeature.getHostElectric()));
            }
            if (null != structureFeature.getHeatingMedium() && !"".equals(structureFeature.getHeatingMedium())) {
                orPredicate.add(criteriaBuilder.equal(root.get("heatingMedium"), structureFeature.getHeatingMedium()));
            }
            if (null != structureFeature.getTopSupportMode() && !"".equals(structureFeature.getTopSupportMode())) {
                orPredicate.add(criteriaBuilder.equal(root.get("topSupportMode"), structureFeature.getTopSupportMode()));
            }
            if (null != structureFeature.getFreeEndSecCompensator() && !"".equals(structureFeature.getFreeEndSecCompensator())) {
                orPredicate.add(criteriaBuilder.equal(root.get("freeEndSecCompensator"), structureFeature.getFreeEndSecCompensator()));
            }
            if (null != structureFeature.getOutEndSecCompensator() && !"".equals(structureFeature.getOutEndSecCompensator())) {
                orPredicate.add(criteriaBuilder.equal(root.get("outEndSecCompensator"), structureFeature.getOutEndSecCompensator()));
            }
            if (null != structureFeature.getStemMaterial() && !"".equals(structureFeature.getStemMaterial())) {
                orPredicate.add(criteriaBuilder.equal(root.get("stemMaterial"), structureFeature.getStemMaterial()));
            }
            if (null != structureFeature.getFivaValveManufacturer() && !"".equals(structureFeature.getFivaValveManufacturer())) {
                orPredicate.add(criteriaBuilder.equal(root.get("fivaValveManufacturer"), structureFeature.getFivaValveManufacturer()));
            }
            if (null != structureFeature.getElectricStartPumpManufacturer() && !"".equals(structureFeature.getElectricStartPumpManufacturer())) {
                orPredicate.add(criteriaBuilder.equal(root.get("electricStartPumpManufacturer"), structureFeature.getElectricStartPumpManufacturer()));
            }
            if (null != structureFeature.getHydraulicPumpManufacturer() && !"".equals(structureFeature.getHydraulicPumpManufacturer())) {
                orPredicate.add(criteriaBuilder.equal(root.get("hydraulicPumpManufacturer"), structureFeature.getHydraulicPumpManufacturer()));
            }
            if (null != structureFeature.getCylinderFuelInjectorManufacturer() && !"".equals(structureFeature.getCylinderFuelInjectorManufacturer())) {
                orPredicate.add(criteriaBuilder.equal(root.get("cylinderFuelInjectorManufacturer"), structureFeature.getCylinderFuelInjectorManufacturer()));
            }
            if (null != structureFeature.getEgb() && !"".equals(structureFeature.getEgb())) {
                orPredicate.add(criteriaBuilder.equal(root.get("egb"), structureFeature.getEgb()));
            }
            if (null != structureFeature.getTorsionalShockAbsorber() && !"".equals(structureFeature.getTorsionalShockAbsorber())) {
                orPredicate.add(criteriaBuilder.equal(root.get("torsionalShockAbsorber"), structureFeature.getTorsionalShockAbsorber()));
            }
            if (null != structureFeature.getScavengerFireExtMethod() && !"".equals(structureFeature.getScavengerFireExtMethod())) {
                orPredicate.add(criteriaBuilder.equal(root.get("scavengerFireExtMethod"), structureFeature.getScavengerFireExtMethod()));
            }
            if (null != structureFeature.getHydraulicOilFilterManufacturer() && !"".equals(structureFeature.getHydraulicOilFilterManufacturer())) {
                orPredicate.add(criteriaBuilder.equal(root.get("hydraulicOilFilterManufacturer"), structureFeature.getHydraulicOilFilterManufacturer()));
            }
            if (null != structureFeature.getRemoteControlManufacturer() && !"".equals(structureFeature.getRemoteControlManufacturer())) {
                orPredicate.add(criteriaBuilder.equal(root.get("remoteControlManufacturer"), structureFeature.getRemoteControlManufacturer()));
            }
            if (null != structureFeature.getPmiSensorManufacturer() && !"".equals(structureFeature.getPmiSensorManufacturer())) {
                orPredicate.add(criteriaBuilder.equal(root.get("pmiSensorManufacturer"), structureFeature.getPmiSensorManufacturer()));
            }
            if (null != structureFeature.getOilMistDetectorManufacturer() && !"".equals(structureFeature.getOilMistDetectorManufacturer())) {
                orPredicate.add(criteriaBuilder.equal(root.get("oilMistDetectorManufacturer"), structureFeature.getOilMistDetectorManufacturer()));
            }
            if (null != structureFeature.getPto() && !"".equals(structureFeature.getPto())) {
                orPredicate.add(criteriaBuilder.equal(root.get("pto"), structureFeature.getPto()));
            }
            if (null != structureFeature.getLiftMethod() && !"".equals(structureFeature.getLiftMethod())) {
                orPredicate.add(criteriaBuilder.equal(root.get("liftMethod"), structureFeature.getLiftMethod()));
            }
            if (null != structureFeature.getScr() && !"".equals(structureFeature.getScr())) {
                orPredicate.add(criteriaBuilder.equal(root.get("scr"), structureFeature.getScr()));
            }
            if (null != structureFeature.getExhaustValveGrinder() && !"".equals(structureFeature.getExhaustValveGrinder())) {
                orPredicate.add(criteriaBuilder.equal(root.get("exhaustValveGrinder"), structureFeature.getExhaustValveGrinder()));
            }
            if (null != structureFeature.getExhaustValveWorkbench() && !"".equals(structureFeature.getExhaustValveWorkbench())) {
                orPredicate.add(criteriaBuilder.equal(root.get("exhaustValveWorkbench"), structureFeature.getExhaustValveWorkbench()));
            }
            if (null != structureFeature.getFuelOilSulphurContent() && !"".equals(structureFeature.getFuelOilSulphurContent())) {
                orPredicate.add(criteriaBuilder.equal(root.get("fuelOilSulphurContent"), structureFeature.getFuelOilSulphurContent()));
            }
            if (null != structureFeature.getUniversal() && !"".equals(structureFeature.getUniversal())) {
                orPredicate.add(criteriaBuilder.equal(root.get("universal"), structureFeature.getUniversal()));
            }
            if (null != structureFeature.getPending() && !"".equals(structureFeature.getPending())) {
                orPredicate.add(criteriaBuilder.equal(root.get("pending"), structureFeature.getPending()));
            }
            List<Predicate> andPredicate = new ArrayList<>();
            if (null != structureFeature.getMachineType() && !"".equals(structureFeature.getMachineType())) {
                andPredicate.add(criteriaBuilder.like(root.get("machineType"), "%" + structureFeature.getMachineType() + "%"));
            }
            if (null != structureFeature.getSuperchargerType() && !"".equals(structureFeature.getSuperchargerType())) {
                andPredicate.add(criteriaBuilder.or(criteriaBuilder.equal(root.get("superchargerType"), structureFeature.getSuperchargerType()), criteriaBuilder.equal(root.get("superchargerType"), "")));
                if (null != structureFeature.getSuperchargerArrange() && !"".equals(structureFeature.getSuperchargerArrange())) {
                    andPredicate.add(criteriaBuilder.or(criteriaBuilder.equal(root.get("superchargerArrange"), structureFeature.getSuperchargerArrange()), criteriaBuilder.equal(root.get("superchargerArrange"), "")));
                }
            }

            if (orPredicate.size() > 0 && andPredicate.size() > 0) {
                Subquery<Integer> propertyQuery = criteriaQuery.subquery(Integer.class);
                Root<StructureFeature> from = propertyQuery.from(StructureFeature.class);
                propertyQuery.select(from.get("id")).where(criteriaBuilder.or(orPredicate.toArray(new Predicate[0])));
                return criteriaQuery.where(root.get("id").in(propertyQuery), criteriaBuilder.and(andPredicate.toArray(new Predicate[0]))).getRestriction();
            } else if (orPredicate.size() > 0 && andPredicate.size() == 0) {
                return criteriaQuery.where(criteriaBuilder.or(orPredicate.toArray(new Predicate[0]))).getRestriction();
            } else if (orPredicate.size() == 0 && andPredicate.size() > 0) {
                return criteriaQuery.where(criteriaBuilder.and(andPredicate.toArray(new Predicate[0]))).getRestriction();
            } else {
                return criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 0)).getRestriction();
            }
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
        XSSFCell titieCell33 = titleRow.createCell(3);
        titieCell33.setCellValue("英文名称");
        XSSFCell titieCell34 = titleRow.createCell(4);
        titieCell34.setCellValue("中文名称");
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

            Material material = materialRepository.findMaterialByMaterialNoAndVersionAndLevel(structure.getMaterialNo(), structure.getVersion(), 0);

            XSSFCell titleCell3 = dataRow.createCell(3);
            titleCell3.setCellValue(material.getName());
            XSSFCell titleCell4 = dataRow.createCell(4);
            titleCell4.setCellValue(material.getChinese());
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
            titleCell1.setCellValue("in-complete");

            Material material = materialRepository.findMaterialByMaterialNoAndVersionAndLevel(structure.getMaterialNo(), structure.getVersion(), 0);

            XSSFCell titleCell3 = dataRow.createCell(3);
            titleCell3.setCellValue(material.getName());
            XSSFCell titleCell4 = dataRow.createCell(4);
            titleCell4.setCellValue(material.getChinese());

            row++;
        }

        row++;

        XSSFRow promptRow = sheet.createRow(row);
        XSSFCell keyCell = promptRow.createCell(0);
        keyCell.setCellValue("查询条件");
        XSSFCell valueCell = promptRow.createCell(1);
        valueCell.setCellValue("查询值");
        row++;

        if (null != structureFeature.getMachineType() && !"".equals(structureFeature.getMachineType())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("机型");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getMachineType());
            row++;
        }
        if (null != structureFeature.getUniversal() && !"".equals(structureFeature.getUniversal())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("通用情况");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getUniversal());
            row++;
        }
        if (null != structureFeature.getPending() && !"".equals(structureFeature.getPending())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("待定情况");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getPending());
            row++;
        }
        if (null != structureFeature.getEfficiency() && !"".equals(structureFeature.getEfficiency())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("功率");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getEfficiency());
            row++;
        }
        if (null != structureFeature.getRotateRate() && !"".equals(structureFeature.getRotateRate())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("转速");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getRotateRate());
            row++;
        }
        if (null != structureFeature.getDebugMode() && !"".equals(structureFeature.getDebugMode())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("调试方式");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getDebugMode());
            row++;
        }
        if (null != structureFeature.getCylinderAmount() && !"".equals(structureFeature.getCylinderAmount())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("缸数");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getCylinderAmount());
            row++;
        }
        if (null != structureFeature.getSuperchargerType() && !"".equals(structureFeature.getSuperchargerType())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("增压器型号");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getSuperchargerType());
            row++;
        }
        if (null != structureFeature.getIceAreaEnhance() && !"".equals(structureFeature.getIceAreaEnhance())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("冰区加强");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getIceAreaEnhance());
            row++;
        }
        if (null != structureFeature.getSuperchargerArrange() && !"".equals(structureFeature.getSuperchargerArrange())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("增压器布置");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getSuperchargerArrange());
            row++;
        }
        if (null != structureFeature.getExhaustBackPressure() && !"".equals(structureFeature.getExhaustBackPressure())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("排气背压");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getExhaustBackPressure());
            row++;
        }
        if (null != structureFeature.getHostRotateDirection() && !"".equals(structureFeature.getHostRotateDirection())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("主机转向");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getHostRotateDirection());
            row++;
        }
        if (null != structureFeature.getPropellerType() && !"".equals(structureFeature.getPropellerType())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("螺旋桨类型");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getPropellerType());
            row++;
        }
        if (null != structureFeature.getHostElectric() && !"".equals(structureFeature.getHostElectric())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("主机电制");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getHostElectric());
            row++;
        }
        if (null != structureFeature.getHeatingMedium() && !"".equals(structureFeature.getHeatingMedium())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("加热介质");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getHeatingMedium());
            row++;
        }
        if (null != structureFeature.getTopSupportMode() && !"".equals(structureFeature.getTopSupportMode())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("顶部支撑方式");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getTopSupportMode());
            row++;
        }
        if (null != structureFeature.getFreeEndSecCompensator() && !"".equals(structureFeature.getFreeEndSecCompensator())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("自由端二阶力矩补偿器");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getFreeEndSecCompensator());
            row++;
        }
        if (null != structureFeature.getOutEndSecCompensator() && !"".equals(structureFeature.getOutEndSecCompensator())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("输出端二阶力矩补偿器");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getOutEndSecCompensator());
            row++;
        }
        if (null != structureFeature.getStemMaterial() && !"".equals(structureFeature.getStemMaterial())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("阀杆材料");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getStemMaterial());
            row++;
        }
        if (null != structureFeature.getFivaValveManufacturer() && !"".equals(structureFeature.getFivaValveManufacturer())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("FIVA阀厂家");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getFivaValveManufacturer());
            row++;
        }
        if (null != structureFeature.getElectricStartPumpManufacturer() && !"".equals(structureFeature.getElectricStartPumpManufacturer())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("电动液压启动阀厂家");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getElectricStartPumpManufacturer());
            row++;
        }
        if (null != structureFeature.getHydraulicPumpManufacturer() && !"".equals(structureFeature.getHydraulicPumpManufacturer())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("液压泵厂家");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getHydraulicPumpManufacturer());
            row++;
        }
        if (null != structureFeature.getCylinderFuelInjectorManufacturer() && !"".equals(structureFeature.getCylinderFuelInjectorManufacturer())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("气缸注油器厂家");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getCylinderFuelInjectorManufacturer());
            row++;
        }
        if (null != structureFeature.getEgb() && !"".equals(structureFeature.getEgb())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("EGB");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getEgb());
            row++;
        }
        if (null != structureFeature.getTorsionalShockAbsorber() && !"".equals(structureFeature.getTorsionalShockAbsorber())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("扭振减震器");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getTorsionalShockAbsorber());
            row++;
        }
        if (null != structureFeature.getScavengerFireExtMethod() && !"".equals(structureFeature.getScavengerFireExtMethod())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("扫气箱灭火方式");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getScavengerFireExtMethod());
            row++;
        }
        if (null != structureFeature.getHydraulicOilFilterManufacturer() && !"".equals(structureFeature.getHydraulicOilFilterManufacturer())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("液压油滤器厂家");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getHydraulicOilFilterManufacturer());
            row++;
        }
        if (null != structureFeature.getRemoteControlManufacturer() && !"".equals(structureFeature.getRemoteControlManufacturer())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("遥控厂家");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getRemoteControlManufacturer());
            row++;
        }
        if (null != structureFeature.getPmiSensorManufacturer() && !"".equals(structureFeature.getPmiSensorManufacturer())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("PMI传感器厂家");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getPmiSensorManufacturer());
            row++;
        }
        if (null != structureFeature.getOilMistDetectorManufacturer() && !"".equals(structureFeature.getOilMistDetectorManufacturer())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("油雾探测器厂家");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getOilMistDetectorManufacturer());
            row++;
        }
        if (null != structureFeature.getPto() && !"".equals(structureFeature.getPto())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("PTO");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getPto());
            row++;
        }
        if (null != structureFeature.getLiftMethod() && !"".equals(structureFeature.getLiftMethod())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("起吊方式");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getLiftMethod());
            row++;
        }
        if (null != structureFeature.getScr() && !"".equals(structureFeature.getScr())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("SCR");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getScr());
            row++;
        }
        if (null != structureFeature.getExhaustValveGrinder() && !"".equals(structureFeature.getExhaustValveGrinder())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("排气阀研磨机");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getExhaustValveGrinder());
            row++;
        }
        if (null != structureFeature.getExhaustValveWorkbench() && !"".equals(structureFeature.getExhaustValveWorkbench())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("排气阀工作台");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getExhaustValveWorkbench());
            row++;
        }
        if (null != structureFeature.getFuelOilSulphurContent() && !"".equals(structureFeature.getFuelOilSulphurContent())) {
            XSSFRow propertyRow = sheet.createRow(row);
            XSSFCell cell0 = propertyRow.createCell(0);
            cell0.setCellValue("燃油硫含量");
            XSSFCell cell1 = propertyRow.createCell(1);
            cell1.setCellValue(structureFeature.getFuelOilSulphurContent());
            row++;
        }

        return new Excel("excel", workbook);
    }
}
