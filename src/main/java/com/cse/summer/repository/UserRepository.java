package com.cse.summer.repository;

import com.cse.summer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 王振琦
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * 根据用户名查询用户对象
     *
     * @param username 用户名
     * @return 用户对象
     */
    User findUserByUsername(String username);
}
