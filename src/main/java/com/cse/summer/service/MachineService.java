package com.cse.summer.service;

import com.cse.summer.domain.Machine;

import java.util.List;

/**
 * @author 王振琦
 */
public interface MachineService {
    /**
     * 获得机器数据
     *
     * @param id 机器ID
     * @return 机器数据
     */
    Machine findMachine(Integer id);

    /**
     * 获取所有机器数据
     *
     * @return 机器数据列表
     */
    List<Machine> findMachineList();

    /**
     * 更新机器信息
     *
     * @param machine 机器数据
     */
    void updateMachine(Machine machine);
}
