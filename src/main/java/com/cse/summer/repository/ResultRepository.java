package com.cse.summer.repository;

import com.cse.summer.domain.ImportResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WangZhenqi
 */
@Repository
public interface ResultRepository extends JpaRepository<ImportResult, Integer> {
    /**
     * 根据机器名查询该机器的部套导入记录
     *
     * @param machineName 机器名
     * @return 导入结果数据集合
     */
    List<ImportResult> findAllByMachineName(String machineName);
}
