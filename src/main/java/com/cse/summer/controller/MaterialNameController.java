package com.cse.summer.controller;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.model.dto.Response;
import com.cse.summer.model.entity.Name;
import com.cse.summer.service.MaterialNameService;
import com.cse.summer.util.StatusCode;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 物料名称API
 *
 * @author WangZhenqi
 */
@RestController
@RequestMapping(value = "api")
public class MaterialNameController extends BaseFacade {

    private final MaterialNameService materialNameService;

    @Autowired
    public MaterialNameController(MaterialNameService materialNameService) {
        this.materialNameService = materialNameService;
    }

    @PostMapping(value = "materialNames/import")
    public Response<Name> actionImportMaterialNames(@RequestParam MultipartFile file) {
        try {
            materialNameService.importMaterialNames(file);
            return new Response<>();
        } catch (IOException | InvalidFormatException e) {
            throw new SummerException(StatusCode.FILE_RESOLVE_ERROR);
        }
    }

    @PostMapping(value = "materialNames/export")
    public void actionExportAllMaterialNames() throws IOException {
        Workbook workbook = materialNameService.exportAllMaterialNames();
        getResponse().reset();
        getResponse().setHeader("Content-Disposition", "attachment;filename="
                + URLEncoder.encode("名称对照表", "UTF-8"));
        getResponse().setHeader("Content-Type", "application/octet-stream");
        OutputStream out = getResponse().getOutputStream();
        BufferedOutputStream buffer = new BufferedOutputStream(out);
        buffer.flush();
        workbook.write(buffer);
        buffer.close();
    }
}
