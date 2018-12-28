package com.cse.summer.controller;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.model.dto.AnalyzeResult;
import com.cse.summer.model.dto.Excel;
import com.cse.summer.model.dto.Response;
import com.cse.summer.model.dto.StructureList;
import com.cse.summer.model.entity.Structure;
import com.cse.summer.service.FileService;
import com.cse.summer.util.Constant;
import com.cse.summer.util.StatusCode;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 王振琦
 */
@RestController
@RequestMapping(value = "api")
public class FileController extends BaseFacade {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    private void outputImportResult(String machineName, List<AnalyzeResult> resultList, boolean isStructure) {
        try {
            StringBuilder strBuilder = new StringBuilder();
            for (AnalyzeResult result : resultList) {
                String structureNo = result.getStructureNo();
                strBuilder.append(structureNo);
                strBuilder.append("  ");
                String resultStr;
                if (!isStructure) {
                    resultStr = result.getResult() ? "导入成功" : "使用库中部套";
                } else {
                    resultStr = result.getResult() ? "导入成功" : "导入失败";
                }
                strBuilder.append(resultStr);
                strBuilder.append("\r\n");
            }
            getResponse().reset();
            if (!isStructure) {
                getResponse().setHeader("content-disposition", "attachment;filename="
                        + URLEncoder.encode(machineName + "导入.txt", "UTF-8"));
            } else {
                getResponse().setHeader("content-disposition", "attachment;filename="
                        + URLEncoder.encode("导入.txt", "UTF-8"));
            }
            getResponse().setContentType(Constant.DocType.XLSX_UTF8);
            OutputStream out = getResponse().getOutputStream();
            BufferedOutputStream buffer = new BufferedOutputStream(out);
            buffer.write(strBuilder.toString().getBytes("UTF-8"));
            buffer.flush();
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "files/import/structure/new")
    public Response<Object> actionImportNewStructureExcel(StructureList structureList, HttpServletRequest request) {
        try {
            List<AnalyzeResult> resultList = new ArrayList<>();
            List<MultipartFile> fileList = ((MultipartHttpServletRequest) request).getFiles("strFile");
            for (int index = 0; index < fileList.size(); index++) {
                MultipartFile file = fileList.get(index);
                if (!file.isEmpty()) {
                    if (!Constant.DocType.XLSX.equals(file.getContentType())) {
                        throw new SummerException(StatusCode.FILE_FORMAT_ERROR);
                    }
                    Structure structure = structureList.getStructure(index);
                    AnalyzeResult analyzeResult = fileService.importNewStructureBOM(structure, file);
                    resultList.add(analyzeResult);
                }
            }
            outputImportResult(null, resultList, true);
        } catch (InvalidFormatException | IOException e) {
            throw new SummerException(e, StatusCode.FILE_RESOLVE_ERROR);
        }
        return new Response<>();
    }

    @PostMapping(value = "files/import/structure/newVersion")
    public Response<Object> actionImportNewVersionStructureExcel(
            Structure structure,
            @RequestParam("structureExcel") MultipartFile structureExcel
    ) {
        if (!Constant.DocType.XLSX.equals(structureExcel.getContentType())) {
            throw new SummerException(StatusCode.FILE_FORMAT_ERROR);
        }
        try {
            fileService.importNewVersionStructureBOM(structure, structureExcel);
        } catch (SummerException e) {
            throw e;
        } catch (InvalidFormatException | IOException e) {
            throw new SummerException(e, StatusCode.FILE_RESOLVE_ERROR);
        }
        return new Response<>();
    }

    @GetMapping(value = "files/export/machine")
    public void actionExportMachineExcel(
            @RequestParam("machineName") String machineName,
            @RequestParam("status") Integer status
    ) throws IOException {
        Excel excel = fileService.exportMachineBOM(machineName, status);
        getResponse().reset();
        getResponse().setHeader("content-disposition", "attachment;filename="
                + URLEncoder.encode(excel.getName(), "UTF-8"));
        getResponse().setContentType(Constant.DocType.XLSX_UTF8);
        OutputStream out = getResponse().getOutputStream();
        BufferedOutputStream buffer = new BufferedOutputStream(out);
        buffer.flush();
        excel.getWorkbook().write(buffer);
        buffer.close();
    }

    @GetMapping(value = "files/export/structure")
    public void actionExportStructureExcel(
            @ModelAttribute Structure structure
    ) throws IOException {
        Excel excel = fileService.exportStructureBOM(getSessionUser(), structure);
        getResponse().reset();
        getResponse().setHeader("content-disposition", "attachment;filename="
                + URLEncoder.encode(excel.getName(), "UTF-8"));
        getResponse().setContentType(Constant.DocType.XLSX_UTF8);
        OutputStream out = getResponse().getOutputStream();
        BufferedOutputStream buffer = new BufferedOutputStream(out);
        buffer.flush();
        excel.getWorkbook().write(buffer);
        buffer.close();
    }
}
