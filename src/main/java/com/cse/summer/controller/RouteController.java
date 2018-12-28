package com.cse.summer.controller;

import com.cse.summer.model.entity.Machine;
import com.cse.summer.model.entity.Structure;
import com.cse.summer.repository.MachineRepository;
import com.cse.summer.service.MachineService;
import com.cse.summer.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author 王振琦
 */
@Controller
public class RouteController {

    private final MachineService machineService;
    private final MachineRepository machineRepository;

    @Autowired
    public RouteController(MachineService machineService, MachineRepository machineRepository) {
        this.machineService = machineService;
        this.machineRepository = machineRepository;
    }

    @RequestMapping(value = "login")
    public String routeLoginPage() {
        return "login";
    }

    @RequestMapping(value = {"", "/", "index"})
    public ModelAndView routeIndexPage() {
        ModelAndView mv = new ModelAndView();
        List<Machine> machineList = machineService.findMachineList();
        mv.addObject("machineList", machineList);
        mv.setViewName("index");
        return mv;
    }

    @RequestMapping(value = "machine")
    public ModelAndView routeMachinePage(
            @RequestParam(value = "machineName") String machineName
    ) {
        ModelAndView mv = new ModelAndView();
        Machine machine = machineRepository.findMachineByNameAndStatus(machineName, Constant.Status.ENABLE);
        machine = machineService.findMachine(machine.getId());
        List<Structure> structureList = machine.getStructureList();
        machine.setStructureList(null);
        mv.addObject("machine", machine);
        mv.addObject("structureList", structureList);
        mv.setViewName("machine");
        return mv;
    }

    @RequestMapping(value = "importResult")
    public String routeImportResultPage() {
        return "import-result";
    }
}
