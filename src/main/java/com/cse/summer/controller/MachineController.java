package com.cse.summer.controller;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.model.dto.AnalyzeResult;
import com.cse.summer.model.dto.Excel;
import com.cse.summer.model.dto.MachineRequest;
import com.cse.summer.model.entity.Machine;
import com.cse.summer.model.dto.Response;
import com.cse.summer.service.FileService;
import com.cse.summer.service.MachineService;
import com.cse.summer.util.Constant;
import com.cse.summer.util.StatusCode;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api")
public class MachineController extends BaseFacade {

    private final MachineService machineService;
    private final FileService fileService;

    @Autowired
    public MachineController(MachineService machineService, FileService fileService) {
        this.machineService = machineService;
        this.fileService = fileService;
    }

    @PostMapping(value = "machines/import")
    public Response<List<AnalyzeResult>> actionImportMachineBOM(@ModelAttribute MachineRequest machineRequest) {
        List<AnalyzeResult> resultList;
        String patentType = machineRequest.getPatent();
        if (Constant.MachineType.MAN.equals(patentType)) {
            if (!Constant.DocType.XML.equals(machineRequest.getFile().getContentType())) {
                throw new SummerException(StatusCode.FILE_FORMAT_ERROR);
            } else {
                try {
                    resultList = fileService.importMANMachineBOM(machineRequest.getName(), machineRequest.getFile());
                } catch (DocumentException | IOException e) {
                    throw new SummerException(e, StatusCode.FILE_RESOLVE_ERROR);
                }
            }
        } else {
            if (!Constant.DocType.XLSX.equals(machineRequest.getFile().getContentType())) {
                throw new SummerException(StatusCode.FILE_FORMAT_ERROR);
            } else {
                try {
                    if (Constant.MachineType.WIN_GD.equals(patentType)) {
                        resultList = fileService.importWinGDMachineBOM(machineRequest.getName(), machineRequest.getFile());
                    } else if (Constant.MachineType.CSE.equals(patentType)) {
                        resultList = fileService.importCSEMachineBOM(machineRequest.getName(), machineRequest.getFile());
                    } else {
                        resultList = new ArrayList<>();
                    }
                } catch (InvalidFormatException | IOException e) {
                    throw new SummerException(e, StatusCode.FILE_RESOLVE_ERROR);
                }
            }
        }
        return new Response<>(resultList);
    }

    @PostMapping(value = "machines/export")
    public void actionExportMachineBOM(@RequestBody MachineRequest request) throws IOException {
        Excel excel =  fileService.exportMachineBOM(request.getName(), request.getStatus());
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

    @PutMapping(value = "machines")
    public Response<Machine> actionUpdateMachine(@RequestBody Machine machine) {
        machineService.updateMachine(machine);
        return new Response<>();
    }

    @GetMapping(value = "machines")
    public Response<List<Machine>> actionQueryMachineList() {
        List<Machine> machineList = machineService.findMachineList();
        return new Response<>(machineList);
    }
}
