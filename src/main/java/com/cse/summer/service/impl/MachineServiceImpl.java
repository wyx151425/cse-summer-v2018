package com.cse.summer.service.impl;

import com.cse.summer.domain.Machine;
import com.cse.summer.domain.Material;
import com.cse.summer.domain.Structure;
import com.cse.summer.repository.MaterialRepository;
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
    private final MaterialRepository materialRepository;
    private final StructureRepository structureRepository;

    @Autowired
    public MachineServiceImpl(MachineRepository machineRepository, MaterialRepository materialRepository, StructureRepository structureRepository) {
        this.machineRepository = machineRepository;
        this.materialRepository = materialRepository;
        this.structureRepository = structureRepository;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Machine findMachine(Integer id) {
        Machine machine = machineRepository.getOne(id);
        List<Material> materialList = new ArrayList<>();
        // 先根据机器名获取该机器所属的部套
        List<Structure> structureList = structureRepository.findAllByMachineNameAndStatusOrderByStructureNo(machine.getName(), 1);
        for (Structure structure : structureList) {
            Material material = materialRepository.findMaterialByLevelAndStructureNoAndVersion(
                    0, structure.getStructureNo(), structure.getVersion());
            materialList.add(material);
        }
        machine.setMaterialList(materialList);
        return machine;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<Machine> findMachineList() {
        return machineRepository.findAll();
    }
}
