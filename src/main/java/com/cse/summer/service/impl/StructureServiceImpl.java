package com.cse.summer.service.impl;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.model.dto.AnalyzeResult;
import com.cse.summer.model.dto.StructMater;
import com.cse.summer.model.entity.Material;
import com.cse.summer.model.entity.Structure;
import com.cse.summer.repository.MaterialRepository;
import com.cse.summer.repository.StructureRepository;
import com.cse.summer.service.StructureService;
import com.cse.summer.util.ExcelUtil;
import com.cse.summer.util.Generator;
import com.cse.summer.util.StatusCode;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public void appendStructure(Structure structure) {
        Material material = materialRepository.findMaterialByMaterialNoAndVersionAndLevel(structure.getMaterialNo(), structure.getVersion(), 0);
        if (null == material) {
            // 检查库中是否有该物料
            throw new SummerException(StatusCode.STRUCTURE_NOT_EXIST);
        } else {
            // 检查该部套是否已经与该物料关联
            Structure target = structureRepository.findStructureByMachineNameAndMaterialNoAndStatusGreaterThanEqual(
                    structure.getMachineName(), structure.getMaterialNo(), 1);
            if (null == target) {
                structure.setObjectId(Generator.getObjectId());
                structure.setStatus(1);
                structureRepository.save(structure);
            } else {
                throw new SummerException(StatusCode.STRUCTURE_HAS_RELATED);
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
        structure.setStatus(1);
        structureRepository.save(structure);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void releaseStructure(Integer id) {
        Structure structure = structureRepository.getOne(id);
        structure.setStatus(2);
        structureRepository.save(structure);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelStructure(Integer id) {
        Structure structure = structureRepository.getOne(id);
        structure.setStatus(1);
        structureRepository.save(structure);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Structure> findRelationStructure(String materialNo) {
        return structureRepository.findAllByMaterialNo(materialNo + "%");
    }

    @Override
    public List<AnalyzeResult> verifyStructureList(MultipartFile file) throws IOException, InvalidFormatException {
        Sheet sheet = ExcelUtil.formatExcelBOM(file, "整机BOM");
        int rowIndex = 0;
        List<AnalyzeResult> results = new ArrayList<>();
        for (Row row : sheet) {
            if (rowIndex < 3) {
                rowIndex++;
            } else {
                if (0 == Integer.parseInt(row.getCell(1).toString().trim())) {
                    String structureNo = row.getCell(0).toString().trim();
                    String code = row.getCell(3).toString().trim();
                    List<Material> materials = materialRepository.findAllByMaterialNoAndLevel(code, 0);
                    if (materials.size() > 0) {
                        results.add(new AnalyzeResult(structureNo + "(" + code +")", false));
                    } else {
                        results.add(new AnalyzeResult(structureNo + "(" + code +")", true));
                    }
                }
            }
        }
        return results;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Structure> findStructureListByMachineName(String machineName) {
        List<StructMater> list = structureRepository.findAllStructureMaterial(machineName);
        List<Structure> structureList = new ArrayList<>();
        for (StructMater structMater : list) {
            Structure structure = structMater.getStructure();
            structure.setMaterial(structMater.getMaterial());
            structureList.add(structure);
        }
        return structureList;
    }
}
