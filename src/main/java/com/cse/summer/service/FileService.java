package com.cse.summer.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.DocumentException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件导入导出业务逻辑类
 *
 * @author 王振琦
 */
public interface FileService {
    /**
     * 导入并解析MAN XML文件
     *
     * @param machineName 机器名
     * @param file        MAN XML文件
     * @throws DocumentException 文档处理异常
     * @throws IOException       输入输出异常
     */
    void importMANXml(String machineName, MultipartFile file) throws DocumentException, IOException;

    /**
     * 导入并解析WinGD Excel文件
     *
     * @param machineName 机器名
     * @param file        WinGD Excel文件
     * @throws InvalidFormatException 格式错误异常
     * @throws IOException            输入输出异常
     */
    void importWinGDExcel(String machineName, MultipartFile file) throws InvalidFormatException, IOException;

    /**
     * 导出机器的完整BOM文件
     *
     * @param machineName 机器名
     * @return Excel文件
     */
    XSSFWorkbook exportMachineExcel(String machineName);

    /**
     * 导入新版本部套的BOM文件
     *
     * @param machineName 机器名
     * @param structureNo 部套号
     * @param file        Excel文件
     * @throws InvalidFormatException 格式错误异常
     * @throws IOException            输入输出异常
     */
    void importNewVersionStructureExcel(String machineName, String structureNo, MultipartFile file) throws InvalidFormatException, IOException;

    /**
     * 导出机器指定版本的部套的BOM文件
     *
     * @param machineName 机器名
     * @param structureNo 部套号
     * @param version     版本号
     * @return Excel文件
     */
    XSSFWorkbook exportStructureExcel(String machineName, String structureNo, Integer version);
}
