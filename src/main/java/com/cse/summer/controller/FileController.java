package com.cse.summer.controller;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.domain.Excel;
import com.cse.summer.domain.Response;
import com.cse.summer.domain.Structure;
import com.cse.summer.service.FileService;
import com.cse.summer.util.StatusCode;
import com.cse.summer.util.Constant;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

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

    @PostMapping(value = "files/import/cse")
    public Response<Object> actionImportCSEBOM(
            @RequestParam("machineName") String machineName,
            @RequestParam("csebom") MultipartFile cseBom
    ) {
        if (!Constant.DocType.XLSX.equals(cseBom.getContentType())) {
            throw new SummerException(StatusCode.FILE_FORMAT_ERROR);
        }
        try {
            fileService.importCSEBOM(machineName, cseBom);
        } catch (InvalidFormatException | IOException e) {
            throw new SummerException(e, StatusCode.FILE_RESOLVE_ERROR);
        }
        return new Response<>();
    }

    @PostMapping(value = "files/import/xml")
    public Response<Object> actionImportMANXml(
            @RequestParam("machineName") String machineName,
            @RequestParam("manXml") MultipartFile manXml
    ) {
        if (!Constant.DocType.XML.equals(manXml.getContentType())) {
            throw new SummerException(StatusCode.FILE_FORMAT_ERROR);
        }
        try {
            fileService.importMANXml(machineName, manXml);
        } catch (DocumentException | IOException e) {
            throw new SummerException(e, StatusCode.FILE_RESOLVE_ERROR);
        }
        return new Response<>();
    }

    @PostMapping(value = "files/import/excel")
    public Response<Object> actionImportWinGDExcel(
            @RequestParam("machineName") String machineName,
            @RequestParam("winGDExcel") MultipartFile winGDExcel
    ) {
        if (!Constant.DocType.XLSX.equals(winGDExcel.getContentType())) {
            throw new SummerException(StatusCode.FILE_FORMAT_ERROR);
        }
        try {
            fileService.importWinGDExcel(machineName, winGDExcel);
        } catch (InvalidFormatException | IOException e) {
            throw new SummerException(e, StatusCode.FILE_RESOLVE_ERROR);
        }
        return new Response<>();
    }

    @PostMapping(value = "files/import/structure/new")
    public Response<Object> actionImportNewStructureExcel(
            Structure structure,
            @RequestParam("newStructureExcel") MultipartFile structureExcel
    ) {
        if (!Constant.DocType.XLSX.equals(structureExcel.getContentType())) {
            throw new SummerException(StatusCode.FILE_FORMAT_ERROR);
        }
        try {
            fileService.importNewStructureExcel(structure, structureExcel);
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
            fileService.importNewVersionStructureExcel(structure, structureExcel);
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
        Excel excel = fileService.exportMachineExcel(machineName, status);
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
        Excel excel = fileService.exportStructureExcel(getSessionUser(), structure);
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
