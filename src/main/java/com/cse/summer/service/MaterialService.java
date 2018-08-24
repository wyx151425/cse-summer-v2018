package com.cse.summer.service;

import com.cse.summer.domain.Material;

import java.util.List;
import java.util.Map;

/**
 * 物料业务逻辑类
 *
 * @author 王振琦
 */
public interface MaterialService {

    /**
     * 根据父节点的ObjectId获得父节点包含的直接子节点
     *
     * @param structureNo 部套号
     * @param parentId    父节点ObjectId
     * @return 查询获得的BomRecord列表对象
     */
    List<Material> findMaterialListByParentId(String structureNo, String parentId);

    /**
     * 根据机器的名称获取直接子节点
     *
     * @param machineName 机器名称
     * @return 查询获得的BomRecord列表对象
     */
    List<Material> findDirectLevelMaterialListByMachineName(String machineName);

    /**
     * 根据物料号查询该物料的所有专利方版本和每个专利方版本的最新版本号
     *
     * @param materialNo 物料号
     * @return 数据传输对象
     */
    List<Map<String, String>> findMaterialNoAndLatestVersion(String materialNo);
}
