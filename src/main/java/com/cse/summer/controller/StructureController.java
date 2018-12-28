package com.cse.summer.controller;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.model.dto.AnalyzeResult;
import com.cse.summer.model.dto.Response;
import com.cse.summer.model.entity.Structure;
import com.cse.summer.service.StructureService;
import com.cse.summer.util.Constant;
import com.cse.summer.util.StatusCode;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author 王振琦
 */
@RestController
@RequestMapping(value = "api")
public class StructureController extends BaseFacade {

    private final StructureService structureService;

    @Autowired
    public StructureController(StructureService structureService) {
        this.structureService = structureService;
    }

    @PostMapping(value = "structures/db")
    public Response<Structure> actionAddDbStructure(@RequestBody Structure structure) {
        structureService.appendStructure(structure);
        return new Response<>();
    }

    @PutMapping(value = "structures/version")
    public Response<Structure> actionUpdateStructureBersion(@RequestBody Structure structure) {
        structureService.updateStructureVersion(structure);
        return new Response<>();
    }

    @DeleteMapping(value = "structures")
    public Response<Structure> actionDeleteStructure(@RequestBody Structure structure) {
        structureService.deleteStructure(structure.getId());
        return new Response<>();
    }

    @PutMapping(value = "structures/{id}/confirm")
    public Response<Structure> actionConfirmStructure(@PathVariable("id") Integer id) {
        structureService.releaseStructure(id);
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
}
