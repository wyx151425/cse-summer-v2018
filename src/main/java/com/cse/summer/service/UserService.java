package com.cse.summer.service;

import com.cse.summer.model.entity.User;

/**
 * @author 王振琦
 */
public interface UserService {
    /**
     * 用户登录
     * @param user 用户对象
     * @return 登录成功后返回的用户对象
     */
    User login(User user);

    /**
     * 更新密码
     * @param user 用户对象
     */
    void updatePassword(User user);

    /**
     * 清空数据库中的数据
     */
    void clearData();
}
