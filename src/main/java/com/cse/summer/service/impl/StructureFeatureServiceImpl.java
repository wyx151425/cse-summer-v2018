package com.cse.summer.service.impl;

import com.cse.summer.model.entity.Material;
import com.cse.summer.model.entity.StructureFeature;
import com.cse.summer.repository.MaterialRepository;
import com.cse.summer.repository.StructureFeatureRepository;
import com.cse.summer.service.StructureFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    private final MaterialRepository materialRepository;
    private final StructureFeatureRepository structureFeatureRepository;

    @Autowired
    public StructureFeatureServiceImpl(MaterialRepository materialRepository, StructureFeatureRepository structureFeatureRepository) {
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
            List<Predicate> predicates = new ArrayList<>();
            if (null != structureFeature.getEfficiency() && !"".equals(structureFeature.getEfficiency())) {
                predicates.add(criteriaBuilder.equal(root.get("efficiency"), structureFeature.getEfficiency()));
            }
            if (null != structureFeature.getRotateRate() && !"".equals(structureFeature.getRotateRate())) {
                predicates.add(criteriaBuilder.equal(root.get("rotateRate"), structureFeature.getRotateRate()));
            }
            if (null != structureFeature.getDebugMode() && !"".equals(structureFeature.getDebugMode())) {
                predicates.add(criteriaBuilder.equal(root.get("debugMode"), structureFeature.getDebugMode()));
            }
            if (null != structureFeature.getCylinderAmount() && !"".equals(structureFeature.getCylinderAmount())) {
                predicates.add(criteriaBuilder.equal(root.get("cylinderAmount"), structureFeature.getCylinderAmount()));
            }
            if (null != structureFeature.getSuperchargerType() && !"".equals(structureFeature.getSuperchargerType())) {
                predicates.add(criteriaBuilder.equal(root.get("superchargerType"), structureFeature.getSuperchargerType()));
            }
            if (null != structureFeature.getIceAreaEnhance() && !"".equals(structureFeature.getIceAreaEnhance())) {
                predicates.add(criteriaBuilder.equal(root.get("iceAreaEnhance"), structureFeature.getIceAreaEnhance()));
            }
            if (null != structureFeature.getSuperchargerArrange() && !"".equals(structureFeature.getSuperchargerArrange())) {
                predicates.add(criteriaBuilder.equal(root.get("superchargerArrange"), structureFeature.getSuperchargerArrange()));
            }
            if (null != structureFeature.getExhaustBackPressure() && !"".equals(structureFeature.getExhaustBackPressure())) {
                predicates.add(criteriaBuilder.equal(root.get("exhaustBackPressure"), structureFeature.getExhaustBackPressure()));
            }
            if (null != structureFeature.getHostRotateDirection() && !"".equals(structureFeature.getHostRotateDirection())) {
                predicates.add(criteriaBuilder.equal(root.get("hostRotateDirection"), structureFeature.getHostRotateDirection()));
            }
            if (null != structureFeature.getPropellerType() && !"".equals(structureFeature.getPropellerType())) {
                predicates.add(criteriaBuilder.equal(root.get("propellerType"), structureFeature.getPropellerType()));
            }
            if (null != structureFeature.getHostElectric() && !"".equals(structureFeature.getHostElectric())) {
                predicates.add(criteriaBuilder.equal(root.get("hostElectric"), structureFeature.getHostElectric()));
            }
            if (null != structureFeature.getFireExtMedium() && !"".equals(structureFeature.getFireExtMedium())) {
                predicates.add(criteriaBuilder.equal(root.get("fireExtMedium"), structureFeature.getFireExtMedium()));
            }
            if (null != structureFeature.getTopSupportMode() && !"".equals(structureFeature.getTopSupportMode())) {
                predicates.add(criteriaBuilder.equal(root.get("topSupportMode"), structureFeature.getTopSupportMode()));
            }
            if (null != structureFeature.getFreeEndSecCompensator() && !"".equals(structureFeature.getFreeEndSecCompensator())) {
                predicates.add(criteriaBuilder.equal(root.get("freeEndSecCompensator"), structureFeature.getFreeEndSecCompensator()));
            }
            if (null != structureFeature.getOutEndSecCompensator() && !"".equals(structureFeature.getOutEndSecCompensator())) {
                predicates.add(criteriaBuilder.equal(root.get("outEndSecCompensator"), structureFeature.getOutEndSecCompensator()));
            }
            if (null != structureFeature.getStemMaterial() && !"".equals(structureFeature.getStemMaterial())) {
                predicates.add(criteriaBuilder.equal(root.get("stemMaterial"), structureFeature.getStemMaterial()));
            }
            if (null != structureFeature.getFivaValveManufacturer() && !"".equals(structureFeature.getFivaValveManufacturer())) {
                predicates.add(criteriaBuilder.equal(root.get("fivaValveManufacturer"), structureFeature.getFivaValveManufacturer()));
            }
            if (null != structureFeature.getElectricStartPumpManufacturer() && !"".equals(structureFeature.getElectricStartPumpManufacturer())) {
                predicates.add(criteriaBuilder.equal(root.get("electricStartPumpManufacturer"), structureFeature.getElectricStartPumpManufacturer()));
            }
            if (null != structureFeature.getHydraulicPumpManufacturer() && !"".equals(structureFeature.getHydraulicPumpManufacturer())) {
                predicates.add(criteriaBuilder.equal(root.get("hydraulicPumpManufacturer"), structureFeature.getHydraulicPumpManufacturer()));
            }
            if (null != structureFeature.getCylinderFuelInjectorManufacturer() && !"".equals(structureFeature.getCylinderFuelInjectorManufacturer())) {
                predicates.add(criteriaBuilder.equal(root.get("cylinderFuelInjectorManufacturer"), structureFeature.getCylinderFuelInjectorManufacturer()));
            }
            if (null != structureFeature.getEgb() && !"".equals(structureFeature.getEgb())) {
                predicates.add(criteriaBuilder.equal(root.get("egb"), structureFeature.getEgb()));
            }
            if (null != structureFeature.getTorsionalShockAbsorber() && !"".equals(structureFeature.getTorsionalShockAbsorber())) {
                predicates.add(criteriaBuilder.equal(root.get("torsionalShockAbsorber"), structureFeature.getTorsionalShockAbsorber()));
            }
            if (null != structureFeature.getScavengerFireExtMethod() && !"".equals(structureFeature.getScavengerFireExtMethod())) {
                predicates.add(criteriaBuilder.equal(root.get("scavengerFireExtMethod"), structureFeature));
            }
            if (null != structureFeature.getHydraulicOilFilterManufacturer() && !"".equals(structureFeature.getHydraulicOilFilterManufacturer())) {
                predicates.add(criteriaBuilder.equal(root.get("hydraulicOilFilterManufacturer"), structureFeature.getHydraulicOilFilterManufacturer()));
            }
            if (null != structureFeature.getRemoteControlManufacturer() && !"".equals(structureFeature.getRemoteControlManufacturer())) {
                predicates.add(criteriaBuilder.equal(root.get("remoteControlManufacturer"), structureFeature.getRemoteControlManufacturer()));
            }
            if (null != structureFeature.getPmiSensorManufacturer() && !"".equals(structureFeature.getPmiSensorManufacturer())) {
                predicates.add(criteriaBuilder.equal(root.get("pmiSensorManufacturer"), structureFeature.getPmiSensorManufacturer()));
            }
            if (null != structureFeature.getOilMistDetectorManufacturer() && !"".equals(structureFeature.getOilMistDetectorManufacturer())) {
                predicates.add(criteriaBuilder.equal(root.get("oilMistDetectorManufacturer"), structureFeature.getOilMistDetectorManufacturer()));
            }
            if (null != structureFeature.getPto() && !"".equals(structureFeature.getPto())) {
                predicates.add(criteriaBuilder.equal(root.get("pto"), structureFeature.getPto()));
            }
            if (null != structureFeature.getLiftMethod() && !"".equals(structureFeature.getLiftMethod())) {
                predicates.add(criteriaBuilder.equal(root.get("liftMethod"), structureFeature.getLiftMethod()));
            }
            if (null != structureFeature.getScr() && !"".equals(structureFeature.getScr())) {
                predicates.add(criteriaBuilder.equal(root.get("scr"), structureFeature.getScr()));
            }
            if (null != structureFeature.getExhaustValveGrinder() && !"".equals(structureFeature.getExhaustValveGrinder())) {
                predicates.add(criteriaBuilder.equal(root.get("exhaustValveGrinder"), structureFeature.getExhaustValveGrinder()));
            }
            if (null != structureFeature.getExhaustValveWorkbench() && !"".equals(structureFeature.getExhaustValveWorkbench())) {
                predicates.add(criteriaBuilder.equal(root.get("exhaustValveWorkbench"), structureFeature.getExhaustValveWorkbench()));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
        });
    }
}
