package com.cse.summer.repository;

import com.cse.summer.model.entity.StructureNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author WangZhenqi
 */
@Repository
public interface StructureNoteRepository extends JpaRepository<StructureNote, Integer> {

    /**
     * 根据部套ID查询部套备注
     *
     * @param structureId 部套ID
     * @return 部套备注对象
     */
    StructureNote findOneByStructureId(String structureId);

    /**
     * 根据物料号和版本查询部套备注
     *
     * @param materialNo 物料号
     * @param version    版本
     * @return 备注信息对象
     */
    StructureNote findOneByMaterialNoAndVersion(String materialNo, Integer version);
}
