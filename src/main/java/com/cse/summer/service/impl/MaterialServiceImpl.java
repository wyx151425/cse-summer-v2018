package com.cse.summer.service.impl;

import com.cse.summer.domain.Material;
import com.cse.summer.domain.Structure;
import com.cse.summer.repository.MaterialRepository;
import com.cse.summer.repository.StructureRepository;
import com.cse.summer.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public List<Material> findDirectLevelMaterialListByMachineName(String machine) {
        List<Structure> structureList = structureRepository.findAllByMachineNameAndStatusOrderByStructureNo(machine, 1);
        List<Material> materialList = new ArrayList<>();
        for (Structure structure : structureList) {
            Material material = materialRepository.findMaterialByLevelAndStructureNoAndVersion(
                    0, structure.getStructureNo(), structure.getVersion());
            materialList.add(material);
        }
        return materialList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void useLatestVersionStructure(String productName, String structureNo) {
//        List<Material> oldRecordList = materialRepository.findAllByMachineNameAndStructureNoAndActive(productName, structureNo, true);
//        int latestVer = oldRecordList.get(0).getLatestVersion();
//        for (int index = 0; index < oldRecordList.size(); index++) {
//            oldRecordList.get(index).setActive(false);
//        }
//        materialRepository.saveAll(oldRecordList);
//        List<Material> newRecordList = materialRepository.findAllByMachineNameAndStructureNoAndVersion(productName, structureNo, latestVer);
//        for (int index = 0; index < newRecordList.size(); index++) {
//            newRecordList.get(index).setActive(true);
//        }
//        materialRepository.saveAll(newRecordList);
    }
}
