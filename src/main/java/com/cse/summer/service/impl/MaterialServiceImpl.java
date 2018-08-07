package com.cse.summer.service.impl;

import com.cse.summer.domain.Material;
import com.cse.summer.repository.MaterialRepository;
import com.cse.summer.repository.StructureRepository;
import com.cse.summer.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 王振琦
 */
@Service(value = "materialService")
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final StructureRepository structureRepository;

    @Autowired
    public MaterialServiceImpl(MaterialRepository materialRepository, StructureRepository structureRepository) {
        this.materialRepository = materialRepository;
        this.structureRepository = structureRepository;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<Material> findMaterialListByParentId(String parentId) {
        return materialRepository.findAllByParentId(parentId);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<Material> findDirectLevelMaterialListByMachineName(String machineName) {
        return structureRepository.findStructureMaterial(machineName);
    }
}
