package com.cse.summer.service;

import com.cse.summer.domain.ImportResult;

import java.util.List;

/**
 * @author WangZhenqi
 */
public interface ResultService {
    /**
     * 根据机器名查询该机器的部套导入记录
     *
     * @param machineName 机器名
     * @return 导入结果数据集合
     */
    List<ImportResult> findImportResultList(String machineName);
}
