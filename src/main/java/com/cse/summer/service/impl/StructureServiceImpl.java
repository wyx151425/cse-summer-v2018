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

import java.util.List;

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
        Material material = materialRepository.findMaterialByMaterialNoAndVersionAndLevel(structure.getMaterialNo(), structure.getVersion(), 0);
        if (null == material) {
            // 检查库中是否有该物料
            throw new SummerException(StatusCode.MATERIAL_NO_EXIST);
        } else {
            // 检查该部套是否已经与该物料关联
            Structure target = structureRepository.findStructureByMachineNameAndMaterialNoAndStatusGreaterThanEqual(
                    structure.getMachineName(), structure.getMaterialNo(), 1);
            if (null == target) {
                structure.setObjectId(Generator.getObjectId());
                structure.setStatus(1);
                structureRepository.save(structure);
            } else {
                throw new SummerException(StatusCode.STRUCTURE_EXIST);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStructureVersion(Structure structure) {
        Structure targetStruct = structureRepository.findStructureByMachineNameAndStructureNoAndMaterialNoAndStatusGreaterThanEqual(
                structure.getMachineName(), structure.getStructureNo(), structure.getMaterialNo(), 1);
        if (!structure.getVersion().equals(targetStruct.getVersion())) {
            targetStruct.setStatus(1);
            targetStruct.setVersion(structure.getVersion());
            structureRepository.save(targetStruct);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteStructure(Integer id) {
        Structure structure = structureRepository.getOne(id);
        structure.setStatus(0);
        structureRepository.save(structure);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmStructure(Integer id) {
        Structure structure = structureRepository.getOne(id);
        structure.setStatus(2);
        structureRepository.save(structure);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Structure> searchStructureListByAssociateMaterialNo(String materialNo) {
        return structureRepository.findAllByMaterialNo(materialNo);
    }
}
