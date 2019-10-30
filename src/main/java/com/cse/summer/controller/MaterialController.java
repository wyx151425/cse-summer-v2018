package com.cse.summer.controller;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.model.entity.Material;
import com.cse.summer.model.dto.Response;
import com.cse.summer.model.entity.PageContext;
import com.cse.summer.model.entity.StructureNote;
import com.cse.summer.service.MaterialService;
import com.cse.summer.service.StructureNoteService;
import com.cse.summer.util.Constant;
import com.cse.summer.util.StatusCode;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @author 王振琦
 */
@RestController
@RequestMapping(value = "api")
public class MaterialController extends BaseFacade {

    private final MaterialService materialService;
    private final StructureNoteService structureNoteService;

    @Autowired
    public MaterialController(MaterialService materialService, StructureNoteService structureNoteService) {
        this.materialService = materialService;
        this.structureNoteService = structureNoteService;
    }

    @GetMapping(value = "materials")
    public Response<List<Material>> actionQueryBomRecordList(
            @RequestParam(value = "parentId") String parentId
    ) {
        List<Material> materialList = materialService.findMaterialListByParentId(parentId);
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

    @GetMapping(value = "structureNotes")
    public Response<StructureNote> actionGetStructureNoteByMaterialNoAndVersion(
            @RequestParam(value = "materialNo") String materialNo,
            @RequestParam(value = "version") Integer version
    ) {
        StructureNote structureNote = structureNoteService.findStructureNoteByMaterialNoAndVersion(materialNo, version);
        return new Response<>(structureNote);
    }

    @GetMapping(value = "materials/queryNoChinese")
    public Response<PageContext<Material>> actionQueryNoChineseMaterials(@RequestParam(value = "pageNum") Integer pageNum) {
        PageContext<Material> materials = materialService.findNoChineseNameMaterials(pageNum);
        return new Response<>(materials);
    }

    @PutMapping(value = "materials/chineseName")
    public Response<PageContext<Material>> actionUpdateMaterialChineseName(@RequestBody Material material) {
        materialService.updateMaterialChineseName(material);
        return new Response<>();
    }

    @PostMapping(value = "materials/exportNoChinese")
    public void actionExportNoChineseMaterials() throws IOException {
        Workbook workbook = materialService.exportNoChineseNameMaterial();
        getResponse().reset();
        getResponse().setHeader("Content-Disposition", "attachment;filename="
                + URLEncoder.encode("无中文名称物料", "UTF-8"));
        getResponse().setHeader("Content-Type", "application/octet-stream");
        OutputStream out = getResponse().getOutputStream();
        BufferedOutputStream buffer = new BufferedOutputStream(out);
        buffer.flush();
        workbook.write(buffer);
        buffer.close();
    }

    @PostMapping(value = "materials/importPerfectChinese")
    public Response<Material> actionImportPerfectChinese(@RequestParam MultipartFile file) {
        try {
            materialService.importPrefectMaterialNameMapping(file);
            return new Response<>();
        } catch (IOException | InvalidFormatException e) {
            throw new SummerException(e, StatusCode.FILE_RESOLVE_ERROR);
        }
    }
}
