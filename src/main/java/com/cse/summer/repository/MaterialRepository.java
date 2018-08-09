package com.cse.summer.repository;

import com.cse.summer.domain.Material;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 王振琦
 */
@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

    /**
     * 根据父级objectId获取子级数据
     *
     * @param parentId 父级objectId
     * @return 子级数据集合
     */
    List<Material> findAllByParentId(String parentId);

    /**
     * 判断物料是否重复时使用的使用的方法，查询库中是否有物料号和专利方版本一起对应的物料
     *
     * @param materialNo      物料号
     * @param materialVersion 物料号版本
     * @param level           物料层级，此处只能为0
     * @return 物料数据
     */
    List<Material> findAllByMaterialNoAndMaterialVersionAndLevel(String materialNo, String materialVersion, Integer level);

    /**
     * 根据部套号获取指定的部套
     *
     * @param structureNo 部套号
     * @return 部套数据集合
     */
    List<Material> findAllByStructureNo(String structureNo);

    /**
     * 根据部套号和版本号获取指定的部套
     *
     * @param structureNo 部套号
     * @param version     版本号
     * @return 部套数据集合
     */
    List<Material> findAllByStructureNoAndVersion(String structureNo, Integer version);

    /**
     * 根据部套号、版本号和层级获取指定的部套
     *
     * @param structureNo 部套号
     * @param version     版本号
     * @param level       层级
     * @return 部套数据
     */
    Material findMaterialByStructureNoAndVersionAndLevel(String structureNo, Integer version, Integer level);

    /**
     * 根据物料号，专利方版本，版本号，层级精准查询物料
     *
     * @param materialNo      物料号
     * @param materialVersion 物料号版本
     * @param version         版本号
     * @param level           层级
     * @return 物料
     */
    Material findMaterialByMaterialNoAndMaterialVersionAndVersionAndLevel(String materialNo, String materialVersion, Integer version, Integer level);

    /**
     * 根据物料号查询物料
     *
     * @param materialNo 物料号
     * @return 物料号数据集合
     */
    @Query("select m from Material m where m.materialNo = :materialNo group by m.materialVersion")
    List<Material> findAllByMaterialNo(@Param("materialNo") String materialNo);
}
