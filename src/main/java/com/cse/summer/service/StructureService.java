package com.cse.summer.service;

import com.cse.summer.model.dto.AnalyzeResult;
import com.cse.summer.model.entity.Structure;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author 王振琦
 */
public interface StructureService {
    /**
     * 添加库中的部套
     *
     * @param structure 部套对象
     */
    void appendStructure(Structure structure);

    /**
     * 修改部套版本
     *
     * @param structure 部套对象
     */
    void updateStructureVersion(Structure structure);

    /**
     * 更新部套总数量
     *
     * @param structure 部套对象
     */
    void updateStructureAmount(Structure structure);

    /**
     * 删除部套
     *
     * @param id 待删除部套的ID
     */
    void deleteStructure(Integer id);

    /**
     * 确认部套发布
     *
     * @param id 部套ID
     */
    void releaseStructure(Integer id);

    /**
     * 取消发布部套
     *
     * @param id 部套ID
     */
    void cancelStructure(Integer id);

    /**
     * 根据物料号查询关联的部套
     *
     * @param materialNo 关联物料号
     * @return 部套数据集合
     */
    List<Structure> findRelationStructure(String materialNo);

    /**
     * 检查部套是否存在
     *
     * @param file CSE BOM文件
     * @return 检查结果
     * @throws IOException            输入输出异常
     * @throws InvalidFormatException 格式错误异常
     */
    List<AnalyzeResult> verifyStructureList(MultipartFile file) throws IOException, InvalidFormatException;

    /**
     * 根据机器名查询部套数据集合
     *
     * @param machineName 机器名
     * @return 部套数据集合
     */
    List<Structure> findStructureListByMachineName(String machineName);
}
