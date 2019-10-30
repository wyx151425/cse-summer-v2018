package com.cse.summer.repository;

import com.cse.summer.model.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 王振琦
 */
@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer>, JpaSpecificationExecutor<Material> {

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
     * @param materialNo 物料号
     * @param level      物料层级，此处只能为0
     * @return 物料数据
     */
    List<Material> findAllByMaterialNoAndLevel(String materialNo, Integer level);

    /**
     * 根据物料号，专利方版本，版本号，层级精准查询物料
     *
     * @param materialNo 物料号
     * @param version    版本号
     * @param level      层级
     * @return 物料
     */
    Material findMaterialByMaterialNoAndVersionAndLevel(String materialNo, Integer version, Integer level);

    /**
     * 根据所属物料号，所属专利方版本和版本号
     *
     * @param atNo 所属物料号
     * @return 物料数据集合
     */
    List<Material> findAllByAtNo(String atNo);

    /**
     * 根据所属物料号，所属专利方版本和版本号
     *
     * @param atNo    所属物料号
     * @param version 版本
     * @return 物料数据集合
     */
    List<Material> findAllByAtNoAndVersion(String atNo, Integer version);

    /**
     * 根据物料号查询物料
     *
     * @param materialNo 物料号
     * @return 物料号数据集合
     */
    @Query("select m from Material m where m.materialNo like :materialNo and level = 0 group by m.materialNo")
    List<Material> findAllByMaterialNoLike(@Param("materialNo") String materialNo);

    /**
     * 根据物料英文名称查询物料
     *
     * @param name 物料名称
     * @return 查询到的物料数据
     */
    @Query("select m from Material m where m.name = :name and (m.chinese is null or m.chinese = '')")
    List<Material> findAllByNameAndChineseIsEmpty(@Param("name") String name);

    /**
     * 根据物料英文名称查询物料
     *
     * @param materialNo 物料号
     * @param name       物料名称
     * @return 查询到的物料数据
     */
    @Query("select m from Material m where m.materialNo = :materialNo and m.name = :name and (m.chinese is null or m.chinese = '')")
    List<Material> findAllByMaterialNoAndNameAndChineseIsEmpty(@Param("materialNo") String materialNo, @Param("name") String name);

    /**
     * 查询所有无中文名称的物料
     *
     * @return 物料号
     */
    @Query("select m from Material m where m.chinese is null or m.chinese = '' group by m.materialNo order by m.id")
    List<Material> findAllByChineseIsNullOrChineseIsEmpty();
}
