package com.cse.summer.repository;

import com.cse.summer.model.entity.StructureFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 部套特征数据仓库
 *
 * @author WangZhenqi
 */
@Repository
public interface StructureFeatureRepository extends JpaRepository<StructureFeature, Integer> {
    /**
     * 根据部套ID查询部套特征信息
     *
     * @param materialId 部套ID
     * @return 部套特征信息
     */
    StructureFeature findStructureFeatureByMaterialId(Integer materialId);
}
