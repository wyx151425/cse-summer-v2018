package com.cse.summer.service;

import com.cse.summer.model.dto.AnalyzeResult;
import com.cse.summer.model.dto.Excel;
import com.cse.summer.model.entity.Structure;
import com.cse.summer.model.entity.User;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.dom4j.DocumentException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 文件导入导出业务逻辑类
 *
 * @author 王振琦
 */
public interface FileService {
    /**
     * 导入并解析CSE BOM文件
     *
     * @param machineName 机器名
     * @param file        CSE BOM文件
     * @return 导入结果标识
     * @throws InvalidFormatException 格式错误异常
     * @throws IOException            输入输出异常
     */
    List<AnalyzeResult> importCSEMachineBOM(String machineName, MultipartFile file) throws InvalidFormatException, IOException;

    /**
     * 导入并解析MAN XML文件
     *
     * @param machineName 机器名
     * @param file        MAN XML文件
     * @return 导入结果标识
     * @throws DocumentException 文档处理异常
     * @throws IOException       输入输出异常
     */
    List<AnalyzeResult> importMANMachineBOM(String machineName, MultipartFile file) throws DocumentException, IOException;

    /**
     * 导入并解析WinGD Excel文件
     *
     * @param machineName 机器名
     * @param file        WinGD Excel文件
     * @return 导入结果标识
     * @throws InvalidFormatException 格式错误异常
     * @throws IOException            输入输出异常
     */
    List<AnalyzeResult> importWinGDMachineBOM(String machineName, MultipartFile file) throws InvalidFormatException, IOException;

    /**
     * 导入新部套
     *
     * @param structure 封装了部套信息的部套对象
     * @param file      部套Excel文件
     * @throws InvalidFormatException 格式错误异常
     * @throws IOException            输入输出异常
     */
    AnalyzeResult importNewStructureBOM(Structure structure, MultipartFile file) throws InvalidFormatException, IOException;

    /**
     * 导入新版本部套的BOM文件
     *
     * @param structure 部套
     * @param file      Excel文件
     * @throws InvalidFormatException 格式错误异常
     * @throws IOException            输入输出异常
     */
    void importNewVersionStructureBOM(Structure structure, MultipartFile file) throws InvalidFormatException, IOException;

    /**
     * 导出机器的完整BOM文件
     *
     * @param machineName 机器名
     * @return Excel文件
     */
    Excel exportMachineBOM(String machineName, Integer status);

    /**
     * 导出机器指定版本的部套的BOM文件
     *
     * @param structure 部套对象
     * @return Excel文件
     */
    Excel exportStructureBOM(User user, Structure structure);
}
