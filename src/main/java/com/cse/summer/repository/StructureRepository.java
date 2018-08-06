package com.cse.summer.repository;

import com.cse.summer.domain.Structure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 王振琦
 */
public interface StructureRepository extends JpaRepository<Structure, Integer> {
    /**
     * 根据机器名、部套号和状态标识获取指定部套
     *
     * @param machineName 机器名
     * @param structureNo 部套号
     * @param status      状态标识
     * @return 部套数据
     */
    Structure findStructureByMachineNameAndStructureNoAndStatusGreaterThanEqual(String machineName, String structureNo, Integer status);

    /**
     * 根据机器名和状态标识查询部套
     *
     * @param machineName 机器名
     * @param status      数据状态
     * @return 部套列表
     */
    List<Structure> findAllByMachineNameAndStatusOrderByStructureNo(String machineName, Integer status);
}
