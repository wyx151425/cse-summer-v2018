package com.cse.summer.service.impl;

import com.cse.summer.domain.Machine;
import com.cse.summer.domain.Material;
import com.cse.summer.repository.MaterialRepository;
import com.cse.summer.repository.MachineRepository;
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
    private final MaterialRepository materialRepository;

    @Autowired
    public MachineServiceImpl(MachineRepository machineRepository, MaterialRepository materialRepository) {
        this.machineRepository = machineRepository;
        this.materialRepository = materialRepository;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Machine findMachine(Integer id) {
        Machine machine = machineRepository.getOne(id);
        List<Material> materialList = materialRepository.findAllByMachineNameAndParentIdAndActiveOrderByStructureNo(machine.getName(), null, true);
        machine.setMaterialList(materialList);
        return machine;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<Machine> findMachineList() {
        return machineRepository.findAll();
    }
}
