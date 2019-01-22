package com.cse.summer.service;

import com.cse.summer.model.entity.Machine;
import com.cse.summer.model.entity.User;

import java.util.List;

/**
 * @author 王振琦
 */
public interface MachineService {
    /**
     * 获得机器数据
     *
     * @param machineName 机器名称
     * @return 机器数据
     */
    Machine findMachine(String machineName);

    /**
     * 获取所有机器数据
     *
     * @return 机器数据列表
     */
    List<Machine> findMachineList();

    /**
     * 删除所有机器数据
     *
     * @param user 用户对象
     */
    void deleteAllMachine(User user);

    /**
     * 更新机器信息
     *
     * @param machine 机器数据
     */
    void updateMachine(Machine machine);
}
