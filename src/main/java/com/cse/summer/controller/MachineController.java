package com.cse.summer.controller;

import com.cse.summer.model.entity.Machine;
import com.cse.summer.model.dto.Response;
import com.cse.summer.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api")
public class MachineController extends BaseFacade {

    private final MachineService machineService;

    @Autowired
    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @PutMapping(value = "machines")
    public Response<Machine> actionUpdateMachine(@RequestBody Machine machine) {
        machineService.updateMachine(machine);
        return new Response<>();
    }
}
