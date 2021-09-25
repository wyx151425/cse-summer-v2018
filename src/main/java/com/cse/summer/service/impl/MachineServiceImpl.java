package com.cse.summer.service.impl;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.model.dto.AnalyzeResult;
import com.cse.summer.model.entity.Machine;
import com.cse.summer.model.dto.StructMater;
import com.cse.summer.model.dto.PageContext;
import com.cse.summer.model.entity.Structure;
import com.cse.summer.model.entity.User;
import com.cse.summer.repository.*;
import com.cse.summer.service.MachineService;
import com.cse.summer.util.Constant;
import com.cse.summer.util.StatusCode;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    private final StructureFeatureRepository structureFeatureRepository;

    @Autowired
    public MachineServiceImpl(MachineRepository machineRepository, StructureRepository structureRepository, MaterialRepository materialRepository, ResultRepository resultRepository, StructureFeatureRepository structureFeatureRepository) {
        this.machineRepository = machineRepository;
        this.structureRepository = structureRepository;
        this.materialRepository = materialRepository;
        this.resultRepository = resultRepository;
        this.structureFeatureRepository = structureFeatureRepository;
    }

    @Override
    public List<AnalyzeResult> importMANMachineBOM(String machineName, MultipartFile file) throws DocumentException, IOException {
        return null;
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
        List<Machine> machines = machineRepository.findAllByStatus(1);
        Collections.reverse(machines);
        return machines;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public PageContext<Machine> findMachineListByPagination(Integer pageIndex, Integer pageSize) {
        // 指定排序参数对象：根据id，进行降序序查询
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        /*
         * 封装分页实体 Pageable
         * 参数一：pageIndex表示当前查询的第几页(默认从0开始，0表示第一页)
         * 参数二：表示每页展示多少数据，现在设置每页展示100条数据
         * 参数三：封装排序对象，根据该对象的参数指定根据id升序查询
         * */
        Page<Machine> machinePage;

        machinePage = machineRepository.findAll((Specification<Machine>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("status"), 1));
            return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
        }, PageRequest.of(pageIndex - 1, pageSize, sort));

        PageContext<Machine> pageContext = new PageContext<>();
        pageContext.setPageIndex(pageIndex);
        pageContext.setPageSize(pageSize);
        pageContext.setDataTotal(machinePage.getTotalElements());
        pageContext.setPageTotal(machinePage.getTotalPages());
        pageContext.setData(machinePage.getContent());
        return pageContext;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public PageContext<Machine> findMachineListByNameLikeAndPagination(String name, Integer pageIndex, Integer pageSize) {
        // 指定排序参数对象：根据id，进行降序序查询
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        /*
         * 封装分页实体 Pageable
         * 参数一：pageIndex表示当前查询的第几页(默认从0开始，0表示第一页)
         * 参数二：表示每页展示多少数据，现在设置每页展示100条数据
         * 参数三：封装排序对象，根据该对象的参数指定根据id升序查询
         * */
        Page<Machine> machinePage;

        machinePage = machineRepository.findAll((Specification<Machine>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("status"), 1));
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
        }, PageRequest.of(pageIndex - 1, pageSize, sort));

        PageContext<Machine> pageContext = new PageContext<>();
        pageContext.setPageIndex(pageIndex);
        pageContext.setPageSize(pageSize);
        pageContext.setDataTotal(machinePage.getTotalElements());
        pageContext.setPageTotal(machinePage.getTotalPages());
        pageContext.setData(machinePage.getContent());
        return pageContext;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public PageContext<Machine> findMachineListByPatentAndPagination(String patent, Integer pageIndex, Integer pageSize) {
        // 指定排序参数对象：根据id，进行降序序查询
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        /*
         * 封装分页实体 Pageable
         * 参数一：pageIndex表示当前查询的第几页(默认从0开始，0表示第一页)
         * 参数二：表示每页展示多少数据，现在设置每页展示100条数据
         * 参数三：封装排序对象，根据该对象的参数指定根据id升序查询
         * */
        Page<Machine> machinePage;

        machinePage = machineRepository.findAll((Specification<Machine>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("status"), 1));
            predicates.add(criteriaBuilder.like(root.get("patent"), patent));
            return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
        }, PageRequest.of(pageIndex - 1, pageSize, sort));

        PageContext<Machine> pageContext = new PageContext<>();
        pageContext.setPageIndex(pageIndex);
        pageContext.setPageSize(pageSize);
        pageContext.setDataTotal(machinePage.getTotalElements());
        pageContext.setPageTotal(machinePage.getTotalPages());
        pageContext.setData(machinePage.getContent());
        return pageContext;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllMachine(User user) {
        if (user.getPermissions().getOrDefault(Constant.Permissions.DELETE_ALL_MACHINE, false)) {
            structureFeatureRepository.deleteAll();
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
