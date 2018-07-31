package com.cse.summer.repository;

import com.cse.summer.domain.Material;
import org.springframework.data.jpa.repository.JpaRepository;
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
     * 根据产品名获取所有所属的物料
     *
     * @param productName 产品名
     * @return 查询获得的记录列表
     */
    List<Material> findAllByMachineName(String productName);

    /**
     * 根据产品名获取所有所属的物料
     *
     * @param productName 产品名
     * @param structureNo 部套号
     * @return 查询获得的记录列表
     */
    List<Material> findAllByMachineNameAndStructureNo(String productName, String structureNo);

    /**
     * 根据产品名获取所有所属的物料
     *
     * @param productName 产品名
     * @param structureNo 部套号
     * @param active      该部套是否在使用
     * @return 查询获得的记录列表
     */
    List<Material> findAllByMachineNameAndStructureNoAndActive(String productName, String structureNo, Boolean active);

    /**
     * 根据产品名和父级ObjectId获取父级所属的直接字级数据
     *
     * @param productName 产品名
     * @param parentId    父级ObjectId
     * @return 查询获得的BomRecord数据列表
     */
    List<Material> findAllByMachineNameAndParentId(String productName, String parentId);

    /**
     * 根据产品名和父级ObjectId获取父级所属的直接字级数据
     *
     * @param productName 产品名
     * @param parentId    父级ObjectId
     * @param active      该部套是否在使用
     * @return 查询获得的BomRecord数据列表
     */
    List<Material> findAllByMachineNameAndParentIdAndActiveOrderByStructureNo(String productName, String parentId, Boolean active);

    /**
     * 根据产品名部套号和版本获取记录
     *
     * @param productName 产品名
     * @param structureNo 部套号
     * @param version     版本
     * @return 记录列表
     */
    List<Material> findAllByMachineNameAndStructureNoAndVersion(String productName, String structureNo, Integer version);
}
