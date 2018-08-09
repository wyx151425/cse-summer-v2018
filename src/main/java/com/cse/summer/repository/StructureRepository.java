package com.cse.summer.repository;

import com.cse.summer.domain.Material;
import com.cse.summer.domain.StructMater;
import com.cse.summer.domain.Structure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author 王振琦
 */
public interface StructureRepository extends JpaRepository<Structure, Integer> {
    /**
     * 根据机器名、部套号和状态标识获取指定部套
     *
     * @param machineName 机器名
     * @param structureNo      部套号
     * @param status      状态标识
     * @return 部套数据
     */
    Structure findStructureByMachineNameAndStructureNoAndStatusGreaterThanEqual(String machineName, String structureNo, Integer status);

    /**
     * 精准查询部套是否存在
     *
     * @param machineName 机器名
     * @param structureNo 部套号
     * @param materialNo  物料号
     * @param revision    版本
     * @return 查询到的部套
     */
    @Query("select s from Structure s where s.machineName = :machineName and s.structureNo = :structureNo and s.materialNo = :materialNo and s.revision = :revision and s.status = 1")
    Structure findExistStructure(@Param("machineName") String machineName, @Param("structureNo") String structureNo,
                                 @Param("materialNo") String materialNo, @Param("revision") String revision);

    /**
     * 根据机器名和状态标识查询部套
     *
     * @param machineName 机器名
     * @param status      数据状态
     * @return 部套列表
     */
    List<Structure> findAllByMachineNameAndStatusOrderByStructureNo(String machineName, Integer status);

    /**
     * 连接查询机器部套
     *
     * @param machineName 机器名
     * @return 部套对应的物料数据集合
     */
    @Query("select m from Structure s left join Material m on s.materialNo = m.materialNo and s.revision = m.revision and s.version = m.version where s.status = 1 and s.machineName = :machineName and m.level = 0 order by s.structureNo")
    List<Material> findStructureMaterial(@Param("machineName") String machineName);

    /**
     * 连接查询机器部套
     *
     * @param machineName 机器名
     * @return 部套对应的物料数据集合
     */
    @Query("select new com.cse.summer.domain.StructMater(s, m) from Structure s left join Material m on s.materialNo = m.materialNo and s.revision = m.revision and s.version = m.version where s.status = 1 and s.machineName = :machineName and m.level = 0 order by s.structureNo")
    List<StructMater> findAllStructureAndMaterial(@Param("machineName") String machineName);

    /**
     * 保存部套时使用，检查部套是否已经与物料关联
     *
     * @param machineName 机器名
     * @param materialNo  物料号
     * @param revision    专利方版本
     * @param version     版本号
     * @param status      状态标识
     * @return 查询到的部套
     */
    Structure findStructureByMachineNameAndMaterialNoAndRevisionAndVersionAndStatus(
            String machineName, String materialNo, String revision, Integer version, Integer status
    );
}
