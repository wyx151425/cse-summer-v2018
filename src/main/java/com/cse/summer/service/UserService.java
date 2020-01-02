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
     * 保存用户账号
     *
     * @param user        用户对象
     * @param currentUser 当前用户
     */
    void saveAccount(User user, User currentUser);

    /**
     * 更新账户密码
     *
     * @param user        用户对象
     * @param currentUser 当前用户
     */
    void updateAccountPassword(User user, User currentUser);

    /**
     * 更新账户角色
     *
     * @param user        用户对象
     * @param currentUser 当前用户
     */
    void updateAccountRole(User user, User currentUser);

    /**
     * 更新账户状态
     *
     * @param user        用户对象
     * @param currentUser 当前用户
     */
    void updateAccountStatus(User user, User currentUser);

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
