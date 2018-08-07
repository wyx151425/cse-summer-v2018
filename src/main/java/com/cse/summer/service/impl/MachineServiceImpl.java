package com.cse.summer.service.impl;

import com.cse.summer.domain.Machine;
import com.cse.summer.domain.Material;
import com.cse.summer.repository.MachineRepository;
import com.cse.summer.repository.StructureRepository;
import com.cse.summer.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 王振琦
 */
@Service(value = "machineService")
public class MachineServiceImpl implements MachineService {

    private final MachineRepository machineRepository;
    private final StructureRepository structureRepository;

    @Autowired
    public MachineServiceImpl(MachineRepository machineRepository, StructureRepository structureRepository) {
        this.machineRepository = machineRepository;
        this.structureRepository = structureRepository;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Machine findMachine(Integer id) {
        Machine machine = machineRepository.getOne(id);
        List<Material> materialList = structureRepository.findStructureMaterial(machine.getName());
        machine.setMaterialList(materialList);
        return machine;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<Machine> findMachineList() {
        return machineRepository.findAll();
    }
}
