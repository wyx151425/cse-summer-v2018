package com.cse.summer.controller;

import com.cse.summer.model.entity.Material;
import com.cse.summer.model.dto.Response;
import com.cse.summer.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 王振琦
 */
@RestController
@RequestMapping(value = "api")
public class MaterialController {

    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping(value = "materials")
    public Response<List<Material>> actionQueryBomRecordList(
            @RequestParam(value = "structureNo") String structureNo,
            @RequestParam(value = "parentId") String parentId
    ) {
        List<Material> materialList = materialService.findMaterialListByParentId(structureNo, parentId);
        return new Response<>(materialList);
    }

    @GetMapping(value = "machines/{machineName}/materials")
    public Response<List<Material>> actionQueryBomRecordListByProductName(@PathVariable(value = "machineName") String machineName) {
        List<Material> materialList = materialService.findDirectLevelMaterialListByMachineName(machineName);
        return new Response<>(materialList);
    }

    @GetMapping(value = "materials/query")
    public Response<List<Map<String, String>>> actionSearchStructureMaterialVersion(@RequestParam("materialNo") String materialNo) {
        List<Map<String, String>> list = materialService.findMaterialNoAndLatestVersion(materialNo);
        return new Response<>(list);
    }
}
