package com.cse.summer.service;

import com.cse.summer.model.entity.Material;
import com.cse.summer.model.entity.PageContext;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
     * @param parentId    父节点ObjectId
     * @return 查询获得的BomRecord列表对象
     */
    List<Material> findMaterialListByParentId(String parentId);

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

    /**
     * 查询没有中文名称的物料
     *
     * @param pageNum 分页页码
     * @return 查询到的物料
     */
    PageContext<Material> findNoChineseNameMaterials(Integer pageNum);

    /**
     * 更新物料的中文名称
     *
     * @param material 物料对象
     */
    void updateMaterialChineseName(Material material);

    /**
     * 导入已完善的物料名称关联
     *
     * @param file 关联映射文件
     */
    void importPrefectMaterialNameMapping(MultipartFile file) throws IOException, InvalidFormatException;

    /**
     * 导出没有中文名称的物料
     *
     * @return EXCEL表格
     */
    Workbook exportNoChineseNameMaterial();
}
