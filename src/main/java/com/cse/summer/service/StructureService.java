package com.cse.summer.service;

import com.cse.summer.domain.Structure;

/**
 * @author 王振琦
 */
public interface StructureService {
    /**
     * 修改部套版本
     *
     * @param structure 部套对象
     */
    void updateStructureVersion(Structure structure);

    /**
     * 删除部套
     *
     * @param structure 部套对象
     */
    void deleteStructure(Structure structure);
}
