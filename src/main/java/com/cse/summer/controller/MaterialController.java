package com.cse.summer.controller;

import com.cse.summer.domain.Material;
import com.cse.summer.domain.Response;
import com.cse.summer.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @RequestParam(value = "parentId") String parentId
    ) {
        List<Material> materialList = materialService.findMaterialListByParentId(parentId);
        return new Response<>(materialList);
    }

    @GetMapping(value = "machines/{machineName}/materials")
    public Response<List<Material>> actionQueryBomRecordListByProductName(
            @PathVariable(value = "machineName") String machineName
    ) {
        List<Material> materialList = materialService.findDirectLevelMaterialListByMachineName(machineName);
        return new Response<>(materialList);
    }

//    @PutMapping(value = "materials/useLatestVersion")
//    public Response<Material> actionUseLatestVersion(
//        @RequestBody Material material
//    ) {
//        materialService.useLatestVersionStructure(material.getMachineName(), material.getStructureNo());
//        return new Response<>();
//    }
}
