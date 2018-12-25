package com.cse.summer.controller;

import com.cse.summer.domain.ImportResult;
import com.cse.summer.domain.Response;
import com.cse.summer.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WangZhenqi
 */
@RestController
@RequestMapping(value = "api")
public class ResultController {

    private final ResultService resultService;

    @Autowired
    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping(value = "machines/{machineName}/results")
    public Response<List<ImportResult>> actionQueryMachineImportResult(@PathVariable("machineName") String machineName) {
        List<ImportResult> results = resultService.findImportResultList(machineName);
        return new Response<>(results);
    }
}
