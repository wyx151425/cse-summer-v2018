package com.cse.summer.service;

import com.cse.summer.model.entity.StructureNote;

/**
 * @author WangZhenqi
 */
public interface StructureNoteService {
    /**
     * 根据物料号和版本查询部套备注
     *
     * @param materialNo 物料号
     * @param version    版本
     * @return 备注信息对象
     */
    StructureNote findStructureNoteByMaterialNoAndVersion(String materialNo, Integer version);
}
