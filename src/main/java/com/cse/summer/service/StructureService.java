package com.cse.summer.service;

import com.cse.summer.domain.Structure;

import java.util.List;

/**
 * @author 王振琦
 */
public interface StructureService {
    /**
     * 添加库中的部套
     *
     * @param structure 部套对象
     */
    void addDbStructure(Structure structure);

    /**
     * 修改部套版本
     *
     * @param structure 部套对象
     */
    void updateStructureVersion(Structure structure);

    /**
     * 删除部套
     *
     * @param id 待删除部套的ID
     */
    void deleteStructure(Integer id);

    /**
     * 根据机器的名称获取部套
     *
     * @param machineName 机器名称
     * @return 查询获得的BomRecord列表对象
     */
    List<Structure> findStructureListByMachineName(String machineName);
}
