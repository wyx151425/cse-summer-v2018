package com.cse.summer.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ExcelUtil {
    /**
     * 将Excel的每一个单元格格式化为文本格式
     *
     * @param file       文件
     * @param sheetName  工作簿名称
     * @return 工作簿
     * @throws InvalidFormatException 文件格式错误
     * @throws IOException            输入输出异常
     */
    public static Sheet formatExcelBOM(MultipartFile file, String sheetName) throws InvalidFormatException, IOException {
        // 获取工作簿
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        // 获取BOM的Sheet
        Sheet sheet = null;
        if (null != sheetName) {
            sheet = workbook.getSheet(sheetName);
        } else {
            sheet = workbook.getSheetAt(0);
        }
        // 创建统一风格，格式化每一个单元格
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
        for (Row row : sheet) {
            for (Cell cell : row) {
                cell.setCellStyle(cellStyle);
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            }
        }
        return sheet;
    }
}
