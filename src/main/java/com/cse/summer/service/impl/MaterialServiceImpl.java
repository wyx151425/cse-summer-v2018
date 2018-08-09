package com.cse.summer.service.impl;

import com.cse.summer.domain.Material;
import com.cse.summer.repository.MaterialRepository;
import com.cse.summer.repository.StructureRepository;
import com.cse.summer.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<Map<String, String>> findRevisionAndLatestVersion(String materialNo) {
        List<Material> materialList = materialRepository.findAllByMaterialNo(materialNo);
        List<Map<String, String>> list = new ArrayList<>();
        for (Material material : materialList) {
            Map<String, String> map = new HashMap<>();
            map.put("revision", material.getMaterialVersion());
            map.put("latestVersion", String.valueOf(material.getLatestVersion()));
            list.add(map);
        }
        return list;
    }
}
