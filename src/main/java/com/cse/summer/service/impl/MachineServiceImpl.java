package com.cse.summer.service.impl;

import com.cse.summer.domain.Machine;
import com.cse.summer.domain.StructMater;
import com.cse.summer.domain.Structure;
import com.cse.summer.repository.MachineRepository;
import com.cse.summer.repository.StructureRepository;
import com.cse.summer.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

        List<StructMater> list = structureRepository.findAllStructureMaterial(machine.getName());
        List<Structure> structures = new ArrayList<>();
        for (StructMater structMater : list) {
            Structure structure = structMater.getStructure();
            structure.setMaterial(structMater.getMaterial());
            structures.add(structure);
        }

        machine.setStructureList(structures);
        return machine;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<Machine> findMachineList() {
        return machineRepository.findAllByStatus(1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMachine(Machine machine) {
        Machine targetMac = machineRepository.getOne(machine.getId());
        targetMac.setType(machine.getType());
        targetMac.setClassificationSociety(machine.getClassificationSociety());
        targetMac.setCylinderAmount(machine.getCylinderAmount());
        targetMac.setMachineNo(machine.getMachineNo());
        targetMac.setShipNo(machine.getShipNo());
        targetMac.setPatent(machine.getPatent());
        machineRepository.save(targetMac);
    }
}
