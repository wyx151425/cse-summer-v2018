package com.cse.summer.domain;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author 王振琦
 */
public class Excel {
    private String name;
    private XSSFWorkbook workbook;

    public Excel(String name, XSSFWorkbook workbook) {
        this.name = name;
        this.workbook = workbook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public XSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(XSSFWorkbook workbook) {
        this.workbook = workbook;
    }
}
