package com.cse.summer.repository;

import com.cse.summer.model.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 权限数据仓库
 *
 * @author WangZhenqi
 */
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    /**
     * 根据角色获取其所有的权限
     *
     * @param role 角色
     * @return 权限数据集合
     */
    List<Permission> findAllByRole(String role);
}
