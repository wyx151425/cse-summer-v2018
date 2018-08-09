package com.cse.summer.service.impl;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.domain.Material;
import com.cse.summer.domain.StructMater;
import com.cse.summer.domain.Structure;
import com.cse.summer.repository.MaterialRepository;
import com.cse.summer.repository.StructureRepository;
import com.cse.summer.service.StructureService;
import com.cse.summer.util.Generator;
import com.cse.summer.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        Material material = materialRepository.findMaterialByMaterialNoAndRevisionAndVersionAndLevel(
                structure.getMaterialNo(), structure.getRevision(), structure.getVersion(), 0);
        if (null == material) {
            // 检查库中是否有该物料
            throw new SummerException(StatusCode.MATERIAL_NO_EXIST);
        } else {
            // 检查该部套是否已经与该物料关联
            Structure target = structureRepository.findStructureByMachineNameAndMaterialNoAndRevisionAndVersionAndStatus(
                    structure.getMachineName(), structure.getMaterialNo(), structure.getRevision(), structure.getVersion(), 1);
            if (null == target) {
                structure.setObjectId(Generator.getObjectId());
                structure.setStatus(1);
                structure.setStructureNo(material.getStructureNo());
                structureRepository.save(structure);
            } else {
                throw new SummerException(StatusCode.STRUCTURE_EXIST);
            }
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
    public void deleteStructure(Integer id) {
        Structure targetStructure = structureRepository.getOne(id);
        targetStructure.setStatus(0);
        structureRepository.save(targetStructure);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<Structure> findStructureListByMachineName(String machineName) {
        List<StructMater> list = structureRepository.findAllStructureAndMaterial(machineName);
        List<Structure> structures = new ArrayList<>();
        for (StructMater structMater : list) {
            Structure structure = structMater.getStructure();
            structure.setMaterial(structMater.getMaterial());
            structures.add(structure);
        }
        return structures;
    }
}
