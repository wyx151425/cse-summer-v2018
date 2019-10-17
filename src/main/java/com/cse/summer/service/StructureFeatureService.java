package com.cse.summer.service;

import com.cse.summer.model.dto.Excel;
import com.cse.summer.model.entity.Material;
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
     * 根据部套查询部套特征
     *
     * @param material 部套
     * @return 部套特征信息
     */
    StructureFeature findStructureFeatureByMaterial(Material material);

    /**
     * 根据部套号和版本查询部套特征
     *
     * @param materialNo 部套物料号
     * @param version    部套版本
     * @return 部套特征信息
     */
    StructureFeature findStructureFeatureByMaterialNoAndVersion(String materialNo, Integer version);

    /**
     * 根据特征属性查询相关的部套及特征信息
     *
     * @param structureFeature 部套特征属性
     * @return 部套及部套特征信息
     */
    List<StructureFeature> findStructureFeatureListByProperty(StructureFeature structureFeature);

    /**
     * 根据查询条件导出部套
     *
     * @param structureFeature 部套特征
     * @return 包含部套数据的EXCEL文件
     */
    Excel exportStructureListByStructureFeature(StructureFeature structureFeature);
}
