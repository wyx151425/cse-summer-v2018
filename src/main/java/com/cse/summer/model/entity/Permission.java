package com.cse.summer.model.entity;

import com.cse.summer.util.Constant;
import com.cse.summer.util.Generator;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 权限类
 *
 * @author WangZhenqi
 */
@Entity
@Table(name = "cse_permission")
public class Permission extends SummerEntity {
    /**
     * 权限所属角色
     */
    private String role;
    /**
     * 权限编号
     */
    private String code;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static Permission newInstance() {
        Permission permission = new Permission();
        permission.setObjectId(Generator.getObjectId());
        permission.setStatus(Constant.Status.ENABLE);
        LocalDateTime dateTime = LocalDateTime.now();
        permission.setCreateAt(dateTime);
        permission.setUpdateAt(dateTime);
        return permission;
    }
}
