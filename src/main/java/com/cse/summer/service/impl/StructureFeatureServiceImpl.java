package com.cse.summer.service.impl;

import com.cse.summer.model.entity.StructureFeature;
import com.cse.summer.repository.StructureFeatureRepository;
import com.cse.summer.service.StructureFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 部套特征信息业务逻辑实现
 *
 * @author WangZhenqi
 */
@Service(value = "structureFeatureService")
public class StructureFeatureServiceImpl implements StructureFeatureService {

    private final StructureFeatureRepository structureFeatureRepository;

    @Autowired
    public StructureFeatureServiceImpl(StructureFeatureRepository structureFeatureRepository) {
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
    @Transactional(rollbackFor = Exception.class)
    public StructureFeature findStructureFeatureByMaterialId(Integer materialId) {
        return structureFeatureRepository.findStructureFeatureByMaterialId(materialId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<StructureFeature> findStructureFeatureListByProperty(StructureFeature structureFeature) {
        return null;
    }
}
