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
     * 确认部套发布
     *
     * @param id 部套ID
     */
    void confirmStructure(Integer id);

    /**
     * 根据物料号查询关联的部套
     *
     * @param materialNo 关联物料号
     * @return 部套数据集合
     */
    List<Structure> searchStructureListByAssociateMaterialNo(String materialNo);
}
