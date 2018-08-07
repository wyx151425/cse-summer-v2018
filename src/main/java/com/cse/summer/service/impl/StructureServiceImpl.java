package com.cse.summer.service.impl;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.domain.Material;
import com.cse.summer.domain.Structure;
import com.cse.summer.repository.MaterialRepository;
import com.cse.summer.repository.StructureRepository;
import com.cse.summer.service.StructureService;
import com.cse.summer.util.Generator;
import com.cse.summer.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 王振琦
 */
@Service(value = "structureService")
public class StructureServiceImpl implements StructureService {

    private final StructureRepository structureRepository;
    private final MaterialRepository materialRepository;

    @Autowired
    public StructureServiceImpl(StructureRepository structureRepository, MaterialRepository materialRepository) {
        this.structureRepository = structureRepository;
        this.materialRepository = materialRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDbStructure(Structure structure) {
        Material material = materialRepository.findMaterialByStructureNoAndVersionAndLevel(
                structure.getStructureNo(), structure.getVersion(), 0);
        if (null == material) {
            throw new SummerException(StatusCode.STRUCTURE_NO_EXIST);
        } else {
            Structure targetStruct = new Structure();
            targetStruct.setObjectId(Generator.getObjectId());
            targetStruct.setStatus(1);
            targetStruct.setStructureNo(material.getStructureNo());
            targetStruct.setMachineName(structure.getMachineName());
            targetStruct.setVersion(material.getVersion());
            targetStruct.setRevision(material.getMaterialVersion());
            structureRepository.save(targetStruct);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStructureVersion(Structure structure) {
        Structure targetStructure = structureRepository.findStructureByMachineNameAndStructureNoAndStatusGreaterThanEqual(
                structure.getMachineName(), structure.getStructureNo(), 1);
        targetStructure.setVersion(structure.getVersion());
        structureRepository.save(targetStructure);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteStructure(Structure structure) {
        Structure targetStructure = structureRepository.findStructureByMachineNameAndStructureNoAndStatusGreaterThanEqual(
                structure.getMachineName(), structure.getStructureNo(), 1);
        targetStructure.setStatus(0);
        structureRepository.save(targetStructure);
    }
}
