package com.cse.summer.service.impl;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.model.entity.Machine;
import com.cse.summer.model.dto.StructMater;
import com.cse.summer.model.entity.Structure;
import com.cse.summer.model.entity.User;
import com.cse.summer.repository.MachineRepository;
import com.cse.summer.repository.MaterialRepository;
import com.cse.summer.repository.ResultRepository;
import com.cse.summer.repository.StructureRepository;
import com.cse.summer.service.MachineService;
import com.cse.summer.util.Constant;
import com.cse.summer.util.StatusCode;
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
    private final MaterialRepository materialRepository;
    private final ResultRepository resultRepository;

    @Autowired
    public MachineServiceImpl(MachineRepository machineRepository, StructureRepository structureRepository, MaterialRepository materialRepository, ResultRepository resultRepository) {
        this.machineRepository = machineRepository;
        this.structureRepository = structureRepository;
        this.materialRepository = materialRepository;
        this.resultRepository = resultRepository;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Machine findMachine(String machineName) {
        Machine machine = machineRepository.findMachineByNameAndStatus(machineName, 1);

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
    public void deleteAllMachine(User user) {
        if (user.getPermissions().getOrDefault(Constant.Permissions.DELETE_ALL_MACHINE, false)) {
            materialRepository.deleteAll();
            structureRepository.deleteAll();
            machineRepository.deleteAll();
            resultRepository.deleteAll();
        } else {
            throw new SummerException(StatusCode.USER_PERMISSION_DEFECT);
        }
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
        targetMac.setComplete(true);
        machineRepository.save(targetMac);
    }
}
