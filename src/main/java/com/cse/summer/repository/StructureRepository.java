package com.cse.summer.repository;

import com.cse.summer.model.dto.StructMater;
import com.cse.summer.model.entity.Structure;
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
     * @param structureNo 部套号
     * @param materialNo  物料号
     * @param status      状态标识
     * @return 部套数据
     */
    Structure findStructureByMachineNameAndStructureNoAndMaterialNoAndStatusGreaterThanEqual(String machineName, String structureNo, String materialNo, Integer status);

    /**
     * 添加库中部套时使用，精准查询部套是否存在
     *
     * @param machineName 机器名
     * @param structureNo 部套号
     * @param materialNo  物料号
     * @return 查询到的部套
     */
    @Query("select s from Structure s where s.machineName = :machineName and s.structureNo = :structureNo and s.materialNo = :materialNo and s.status > 0")
    Structure findExistStructure(@Param("machineName") String machineName, @Param("structureNo") String structureNo, @Param("materialNo") String materialNo);

    /**
     * 根据机器名和状态标识查询部套
     *
     * @param machineName 机器名
     * @param status      数据状态
     * @return 部套列表
     */
    List<Structure> findAllByMachineNameAndStatusOrderByStructureNo(String machineName, Integer status);

    /**
     * 根据机器名和状态标识查询部套
     *
     * @param machineName 机器名
     * @param status      数据状态
     * @return 部套列表
     */
    List<Structure> findAllByMachineNameAndStatusGreaterThanEqualOrderByStructureNo(String machineName, Integer status);

    /**
     * 连接查询机器部套
     *
     * @param machineName 机器名
     * @return 部套对应的物料数据集合
     */
    @Query("select new com.cse.summer.model.dto.StructMater(s, m) from Structure s left join Material m on s.materialNo = m.materialNo and s.version = m.version where s.status > 0 and s.machineName = :machineName and m.level = 0 order by s.structureNo, s.materialNo")
    List<StructMater> findAllStructureMaterial(@Param("machineName") String machineName);

    /**
     * 保存部套时使用，检查部套是否已经与物料关联
     *
     * @param machineName 机器名
     * @param materialNo  物料号
     * @param status      状态标识
     * @return 查询到的部套
     */
    Structure findStructureByMachineNameAndMaterialNoAndStatusGreaterThanEqual(
            String machineName, String materialNo, Integer status
    );

    /**
     * 根据物料号查询关联的部套
     *
     * @param materialNo 物料号
     * @return 关联的部套数据集合
     */
    @Query("select s from Structure s where s.materialNo = :materialNo and s.status > 0")
    List<Structure> findAllByMaterialNo(@Param("materialNo") String materialNo);
}
