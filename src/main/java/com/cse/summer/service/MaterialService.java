package com.cse.summer.service;

import com.cse.summer.domain.Material;

import java.util.List;

/**
 * 物料业务逻辑类
 *
 * @author 王振琦
 */
public interface MaterialService {

    /**
     * 根据父节点的ObjectId获得父节点包含的直接子节点
     *
     * @param parentId 父节点ObjectId
     * @return 查询获得的BomRecord列表对象
     */
    List<Material> findMaterialListByParentId(String parentId);

    /**
     * 根据机器的名称获取直接子节点
     *
     * @param machineName 机器名称
     * @return 查询获得的BomRecord列表对象
     */
    List<Material> findDirectLevelMaterialListByMachineName(String machineName);

    /**
     * 更新机器 使用最新版本的部套
     *
     * @param machineName 机器名
     * @param structureNo 部套号
     */
    void useLatestVersionStructure(String machineName, String structureNo);
}
