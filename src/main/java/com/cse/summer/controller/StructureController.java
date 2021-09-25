package com.cse.summer.controller;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.model.dto.AnalyzeResult;
import com.cse.summer.model.dto.Excel;
import com.cse.summer.model.dto.Response;
import com.cse.summer.model.dto.StructureRequest;
import com.cse.summer.model.entity.Structure;
import com.cse.summer.service.FileService;
import com.cse.summer.service.StructureService;
import com.cse.summer.util.Constant;
import com.cse.summer.util.StatusCode;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author 王振琦
 */
@RestController
@RequestMapping(value = "api")
public class StructureController extends SummerController {

    private final StructureService structureService;
    private final FileService fileService;

    @Autowired
    public StructureController(StructureService structureService, FileService fileService) {
        this.structureService = structureService;
        this.fileService = fileService;
    }

    @PostMapping(value = "structures/append")
    public Response<Structure> actionAppendStructure(@RequestBody Structure structure) {
        structureService.appendStructure(structure);
        return new Response<>();
    }

    @PutMapping(value = "structures/version")
    public Response<Structure> actionUpdateStructureVersion(@RequestBody Structure structure) {
        structureService.updateStructureVersion(structure);
        return new Response<>();
    }

    @DeleteMapping(value = "structures/{id}")
    public Response<Structure> actionDeleteStructure(@PathVariable(value = "id") Integer id) {
        structureService.deleteStructure(id);
        return new Response<>();
    }

    @PutMapping(value = "structures/{id}/release")
    public Response<Structure> actionConfirmStructure(@PathVariable("id") Integer id) {
        structureService.releaseStructure(id);
        return new Response<>();
    }

    @PutMapping(value = "structures/amount")
    public Response<Structure> actionUpdateStructureAmount(@RequestBody Structure structure) {
        structureService.updateStructureAmount(structure);
        return new Response<>();
    }

    @GetMapping(value = "structures/query")
    public Response<List<Structure>> actionStructuresSearch(@RequestParam("materialNo") String materialNo) {
        List<Structure> structureList = structureService.findRelationStructure(materialNo);
        return new Response<>(structureList);
    }

    @PostMapping(value = "structures/verify")
    public Response<List<AnalyzeResult>> actionVerifyStructureList(@RequestParam("file") MultipartFile file) {
        if (!Constant.DocType.XLSX.equals(file.getContentType())) {
            throw new SummerException(StatusCode.FILE_FORMAT_ERROR);
        } else {
            try {
                List<AnalyzeResult> resultList = structureService.verifyStructureList(file);
                return new Response<>(resultList);
            } catch (InvalidFormatException | IOException e) {
                throw new SummerException(e, StatusCode.FILE_RESOLVE_ERROR);
            }
        }
    }

    @GetMapping(value = "machines/{machineName}/structures")
    public Response<List<Structure>> actionQueryStructureListByMachineName(@PathVariable(value = "machineName") String machineName) {
        List<Structure> structureList = structureService.findStructureListByMachineName(machineName);
        return new Response<>(structureList);
    }

    @PostMapping(value = "structures/import")
    public Response<AnalyzeResult> actionImportNewStructureBOM(@ModelAttribute StructureRequest request) {
        if (!Constant.DocType.XLSX.equals(request.getFile().getContentType())) {
            throw new SummerException(StatusCode.FILE_FORMAT_ERROR);
        }
        AnalyzeResult result;
        try {
            result = fileService.importNewStructureBOM(request.getStructure(), request.getFile());
        } catch (InvalidFormatException | IOException e) {
            throw new SummerException(e, StatusCode.FILE_RESOLVE_ERROR);
        }
        return new Response<>(result);
    }

    @PostMapping(value = "structures/import/version")
    public Response<Object> actionImportNewVersionStructureBOM(@ModelAttribute StructureRequest request) {
        if (!Constant.DocType.XLSX.equals(request.getFile().getContentType())) {
            throw new SummerException(StatusCode.FILE_FORMAT_ERROR);
        }
        try {
            fileService.importNewVersionStructureBOM(request.getStructure(), request.getFile());
        } catch (InvalidFormatException | IOException e) {
            throw new SummerException(e, StatusCode.FILE_RESOLVE_ERROR);
        }
        return new Response<>();
    }

    @PostMapping(value = "structures/export")
    public void actionExportStructureFile(@RequestBody Structure structure) throws IOException {
        Excel excel = fileService.exportStructureBOM(getSessionUser(), structure);
        getResponse().reset();
        getResponse().setHeader("Content-Disposition", "attachment;filename="
                + URLEncoder.encode(excel.getName(), "UTF-8"));
        getResponse().setHeader("Content-Type", "application/octet-stream");
        OutputStream out = getResponse().getOutputStream();
        BufferedOutputStream buffer = new BufferedOutputStream(out);
        buffer.flush();
        excel.getWorkbook().write(buffer);
        buffer.close();
    }
}
