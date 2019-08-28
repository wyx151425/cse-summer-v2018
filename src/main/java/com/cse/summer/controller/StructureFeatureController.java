package com.cse.summer.controller;

import com.cse.summer.model.dto.Response;
import com.cse.summer.model.entity.Material;
import com.cse.summer.model.entity.StructureFeature;
import com.cse.summer.service.StructureFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部套特征信息数据接口
 *
 * @author WangZhenqi
 */
@RestController
@RequestMapping(value = "api")
public class StructureFeatureController {

    private final StructureFeatureService structureFeatureService;

    @Autowired
    public StructureFeatureController(StructureFeatureService structureFeatureService) {
        this.structureFeatureService = structureFeatureService;
    }
    @PostMapping(value = "structureFeatures")
    public Response<StructureFeature> saveStructureFeature(@RequestBody StructureFeature structureFeature) {
        structureFeatureService.saveStructureFeature(structureFeature);
        return new Response<>(structureFeature);
    }

    @PutMapping(value = "structureFeatures")
    public Response<StructureFeature> updateStructureFeature(@RequestBody StructureFeature structureFeature) {
        structureFeatureService.updateStructureFeature(structureFeature);
        return new Response<>(structureFeature);
    }

    @GetMapping(value = "materials/{materialId}/structureFeatures")
    public Response<StructureFeature> findStructureFeatureByMaterialId(@PathVariable(value = "materialId") Integer materialId) {
        Material material = new Material();
        material.setId(materialId);
        StructureFeature structureFeature = structureFeatureService.findStructureFeatureByMaterial(material);
        return new Response<>(structureFeature);
    }

    @GetMapping(value = "structureFeatures/materialNo")
    public Response<StructureFeature> findStructureFeatureByMaterialNo(
            @RequestParam(value = "materialNo") String materialNo,
            @RequestParam(value = "version") Integer version
    ) {
        StructureFeature structureFeature = structureFeatureService.findStructureFeatureByMaterialNoAndVersion(materialNo, version);
        return new Response<>(structureFeature);
    }

    @PostMapping(value = "structureFeatures/property")
    public Response<List<StructureFeature>> findStructureFeatureListByProperty(@RequestBody StructureFeature structureFeature) {
        List<StructureFeature> structureFeatures = structureFeatureService.findStructureFeatureListByProperty(structureFeature);
        return new Response<>(structureFeatures);
    }
}
