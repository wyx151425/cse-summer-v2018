package com.cse.summer.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 物料名称业务逻辑规范
 *
 * @author WangZhenqi
 */
public interface MaterialNameService {

    /**
     * 导入物料名称
     *
     * @param file 文件
     * @throws IOException            文件异常
     * @throws InvalidFormatException 格式错误异常
     */
    void importMaterialNames(MultipartFile file) throws IOException, InvalidFormatException;

    /**
     * 导出所有的物料名称
     *
     * @return 工作表
     */
    Workbook exportAllMaterialNames();
}
