package com.cse.summer.service.impl;

import com.cse.summer.model.entity.Material;
import com.cse.summer.model.dto.StructMater;
import com.cse.summer.model.dto.PageContext;
import com.cse.summer.model.entity.Structure;
import com.cse.summer.repository.MaterialRepository;
import com.cse.summer.repository.StructureRepository;
import com.cse.summer.service.MaterialService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
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
        List<StructMater> list = structureRepository.findAllStructureMaterial(machineName);
        List<Material> materials = new ArrayList<>();
        for (StructMater structMater : list) {
            Structure structure = structMater.getStructure();
            Material material = structMater.getMaterial();
            material.setStructureNo(structure.getStructureNo());
            materials.add(material);
        }
        return materials;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<Map<String, String>> findMaterialNoAndLatestVersion(String materialNo) {
        List<Material> materialList = materialRepository.findAllByMaterialNoLike(materialNo + "%");
        List<Map<String, String>> list = new ArrayList<>();
        for (Material material : materialList) {
            Map<String, String> map = new HashMap<>();
            map.put("materialNo", material.getMaterialNo());
            map.put("latestVersion", String.valueOf(material.getLatestVersion()));
            list.add(map);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public PageContext<Material> findNoChineseNameMaterials(Integer pageNum) {
        Page<Material> materialPage = materialRepository.findAll(new Specification<Material>() {
            @Override
            public Predicate toPredicate(Root<Material> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("chinese"), ""));
                predicates.add(criteriaBuilder.isNull(root.get("chinese")));
                return criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[0]))).getRestriction();
            }
        }, PageRequest.of(pageNum - 1, 30));

        PageContext<Material> pageContext = new PageContext<>();
        pageContext.setPageIndex(pageNum);
        pageContext.setPageSize(materialPage.getContent().size());
        pageContext.setDataTotal(materialPage.getTotalElements());
        pageContext.setPageTotal(materialPage.getTotalPages());
        pageContext.setData(materialPage.getContent());
        return pageContext;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMaterialChineseName(Material material) {
        Material target = materialRepository.findById(material.getId()).get();
        target.setName(material.getName().toUpperCase());
        target.setChinese(material.getChinese());
        materialRepository.save(target);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importPrefectMaterialNameMapping(MultipartFile file) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());

        Sheet sheet = workbook.getSheet("物料数据");

        CellStyle textStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        textStyle.setDataFormat(format.getFormat("@"));
        for (Row row : sheet) {
            for (Cell cell : row) {
                cell.setCellStyle(textStyle);
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            }
        }

        int index = 0;
        for (Row row : sheet) {
            if (0 == index) {
                index++;
            } else {
                if (null == row.getCell(0) || "".equals(row.getCell(0).toString())) {
                    break;
                }
                if (null != row.getCell(3) && !"".equals(row.getCell(3).toString())) {
                    List<Material> materials = materialRepository.findAllByMaterialNoAndNameAndChineseIsEmpty(
                            row.getCell(0).toString(), row.getCell(2).toString());
                    for (Material material : materials) {
                        material.setChinese(row.getCell(3).toString());
                        materialRepository.save(material);
                    }
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Workbook exportNoChineseNameMaterial() {
        List<Material> materials = materialRepository.findAllByChineseIsNullOrChineseIsEmpty();

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("物料数据");

        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 8);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);

        sheet.setColumnWidth(0, 160 * 32);
        sheet.setColumnWidth(1, 160 * 32);
        sheet.setColumnWidth(2, 160 * 32);
        sheet.setColumnWidth(3, 160 * 32);

        Row title = sheet.createRow(0);
        Cell codeTitle = title.createCell(0);
        codeTitle.setCellValue("英文名称");
        Cell drawTitle = title.createCell(1);
        drawTitle.setCellValue("中文名称");
        Cell enTitle = title.createCell(2);
        enTitle.setCellValue("物料号");
        Cell cnTitle = title.createCell(3);
        cnTitle.setCellValue("图号");

        int index = 1;
        for (Material material : materials) {
            Row row = sheet.createRow(index);
            Cell english = row.createCell(0);
            english.setCellValue(material.getName());
            Cell chinese = row.createCell(1);
            chinese.setCellValue(material.getChinese());
            Cell materialNo = row.createCell(2);
            materialNo.setCellValue(material.getMaterialNo());
            Cell drawingNo = row.createCell(3);
            drawingNo.setCellValue(material.getDrawingNo());
            index++;
        }

        return workbook;
    }
}
