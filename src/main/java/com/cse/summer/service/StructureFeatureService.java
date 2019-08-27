package com.cse.summer.service;

import com.cse.summer.model.entity.StructureFeature;

import java.util.List;

/**
 * 部套特征信息业务逻辑规范
 *
 * @author WangZhenqi
 */
public interface StructureFeatureService {
    /**
     * 保存部套特征信息
     *
     * @param structureFeature 部套特征信息
     */
    void saveStructureFeature(StructureFeature structureFeature);

    /**
     * 更新部套特征信息
     *
     * @param structureFeature 部套特征信息
     */
    void updateStructureFeature(StructureFeature structureFeature);

    /**
     * 根据部套ID查询部套特征
     *
     * @param materialId 部套特征
     * @return 部套特征信息
     */
    StructureFeature findStructureFeatureByMaterialId(Integer materialId);

    /**
     * 根据特征属性查询相关的部套及特征信息
     *
     * @param structureFeature 部套特征属性
     * @return 部套及部套特征信息
     */
    List<StructureFeature> findStructureFeatureListByProperty(StructureFeature structureFeature);
}
