package com.cse.summer.service.impl;

import com.cse.summer.domain.Structure;
import com.cse.summer.repository.StructureRepository;
import com.cse.summer.service.StructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 王振琦
 */
@Service(value = "structureService")
public class StructureServiceImpl implements StructureService {

    private final StructureRepository structureRepository;

    @Autowired
    public StructureServiceImpl(StructureRepository structureRepository) {
        this.structureRepository = structureRepository;
    }

    @Override
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
