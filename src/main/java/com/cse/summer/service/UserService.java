package com.cse.summer.service;

import com.cse.summer.model.entity.PageContext;
import com.cse.summer.model.entity.User;

import java.util.List;

/**
 * @author 王振琦
 */
public interface UserService {
    /**
     * 用户登录
     *
     * @param user 用户对象
     * @return 登录成功后返回的用户对象
     */
    User login(User user);

    /**
     * 更新密码
     *
     * @param user 用户对象
     */
    void updatePassword(User user);

    /**
     * 清空数据库中的数据
     */
    void clearData();

    /**
     * 分页查询账号
     *
     * @param pageNum 分页页码
     * @param user    当前用户
     * @return 查询到的账号及分页信息
     */
    PageContext<User> findAllAccounts(Integer pageNum, User user);
}
