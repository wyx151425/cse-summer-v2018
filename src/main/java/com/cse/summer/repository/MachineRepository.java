package com.cse.summer.repository;

import com.cse.summer.model.entity.Machine;
import com.cse.summer.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 王振琦
 */
@Repository
public interface MachineRepository extends JpaRepository<Machine, Integer>, JpaSpecificationExecutor<Machine> {
    /**
     * 根据机器名查询机器数据
     *
     * @param name 机器名
     * @param status 状态码
     * @return 机器数据
     */
    Machine findMachineByNameAndStatus(String name, Integer status);

    /**
     * 根据状态获取相关机器
     * @param status 状态码
     * @return 机器列表
     */
    List<Machine> findAllByStatus(Integer status);
}
