package com.cse.summer.repository;

import com.cse.summer.domain.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 王振琦
 */
@Repository
public interface NameRepository extends JpaRepository<Name, Integer> {
    /**
     * 根据英文名获取Name对象
     *
     * @param english 英文名称
     * @return 名称对象
     */
    List<Name> findByEnglish(@Param("english") String english);
}
