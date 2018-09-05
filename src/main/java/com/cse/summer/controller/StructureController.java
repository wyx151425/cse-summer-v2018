package com.cse.summer.controller;

import com.cse.summer.domain.Response;
import com.cse.summer.domain.Structure;
import com.cse.summer.service.StructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        structureService.addDbStructure(structure);
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
        structureService.confirmStructure(id);
        return new Response<>();
    }

    @GetMapping(value= "structures/search")
    public Response<List<Structure>> actionStructuresSearch(@RequestParam("materialNo") String materialNo) {
        List<Structure> structureList = structureService.searchStructureListByAssociateMaterialNo(materialNo);
        return new Response<>(structureList);
    }
}
