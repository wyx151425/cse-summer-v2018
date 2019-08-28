package com.cse.summer.repository;

import com.cse.summer.model.entity.Material;
import com.cse.summer.model.entity.StructureFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 部套特征数据仓库
 *
 * @author WangZhenqi
 */
@Repository
public interface StructureFeatureRepository extends JpaRepository<StructureFeature, Integer>, JpaSpecificationExecutor<StructureFeature> {
    /**
     * 根据部套ID查询部套特征信息
     *
     * @param material 部套
     * @return 部套特征信息
     */
    StructureFeature findStructureFeatureByMaterial(Material material);
}
