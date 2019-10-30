package com.cse.summer.service.impl;

import com.cse.summer.model.entity.Material;
import com.cse.summer.model.entity.Name;
import com.cse.summer.repository.MaterialRepository;
import com.cse.summer.repository.NameRepository;
import com.cse.summer.service.MaterialNameService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 物料名称业务逻辑实现
 *
 * @author WangZhenqi
 */
@Service(value = "materialNameService")
public class MaterialNameServiceImpl implements MaterialNameService {

    private final NameRepository nameRepository;
    private final MaterialRepository materialRepository;

    @Autowired
    public MaterialNameServiceImpl(NameRepository nameRepository, MaterialRepository materialRepository) {
        this.nameRepository = nameRepository;
        this.materialRepository = materialRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importMaterialNames(MultipartFile file) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());

        Sheet sheet = workbook.getSheet("名称对照表");

        CellStyle textStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        textStyle.setDataFormat(format.getFormat("@"));
        for (Row row : sheet) {
            for (Cell cell : row) {
                cell.setCellStyle(textStyle);
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            }
        }

        List<Name> nameList = new ArrayList<>();
        int index = 0;
        for (Row row : sheet) {
            if (0 == index) {
                index++;
            } else {
                if (null == row.getCell(0) || "".equals(row.getCell(0).toString())) {
                    break;
                }
                if (null != row.getCell(1) && !"".equals(row.getCell(1).toString())) {
                    Name name = Name.newInstance();
                    name.setEnglish(row.getCell(0).toString());
                    name.setChinese(row.getCell(1).toString());
                    nameList.add(name);

                    List<Material> materials = materialRepository.findAllByNameAndChineseIsEmpty(row.getCell(0).toString());
                    for (Material material : materials) {
                        material.setChinese(row.getCell(1).toString());
                        materialRepository.save(material);
                    }
                }
            }
        }
        nameRepository.saveAll(nameList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Workbook exportAllMaterialNames() {
        List<Name> nameList = nameRepository.findAll();

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("名称对照表");

        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 8);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);

        sheet.setColumnWidth(0, 160 * 32);
        sheet.setColumnWidth(1, 160 * 32);

        Row title = sheet.createRow(0);
        Cell enTitle = title.createCell(0);
        enTitle.setCellValue("英文名称");
        Cell cnTitle = title.createCell(1);
        cnTitle.setCellValue("中文名称");

        int index = 1;
        for (Name name : nameList) {
            Row row = sheet.createRow(index);
            Cell english = row.createCell(0);
            english.setCellValue(name.getEnglish());
            Cell chinese = row.createCell(1);
            chinese.setCellValue(name.getChinese());
            index++;
        }

        return workbook;
    }
}
