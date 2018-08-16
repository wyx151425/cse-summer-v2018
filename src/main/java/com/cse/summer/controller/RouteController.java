package com.cse.summer.controller;

import com.cse.summer.domain.Machine;
import com.cse.summer.domain.Material;
import com.cse.summer.domain.Structure;
import com.cse.summer.service.MachineService;
import com.cse.summer.service.StructureService;
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

    @Autowired
    public RouteController(MachineService machineService) {
        this.machineService = machineService;
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
            @RequestParam(value = "machineId") Integer machineId
    ) {
        ModelAndView mv = new ModelAndView();
        Machine machine = machineService.findMachine(machineId);
        List<Structure> structureList = machine.getStructureList();
        machine.setStructureList(null);
        mv.addObject("machine", machine);
        mv.addObject("structureList", structureList);
        mv.setViewName("machine");
        return mv;
    }
}
