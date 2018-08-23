package com.cse.summer.service.impl;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.domain.User;
import com.cse.summer.repository.UserRepository;
import com.cse.summer.service.UserService;
import com.cse.summer.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 王振琦
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public User login(User user) {
        User targetUser = userRepository.findUserByUsername(user.getName());
        if (null == targetUser) {
            throw new SummerException(StatusCode.USER_UNREGISTER);
        } else {
            if (0 == targetUser.getStatus()) {
                throw new SummerException(StatusCode.USER_DISABLED);
            } else {
                if (targetUser.getPassword().equals(user.getPassword())) {
                    return targetUser;
                } else {
                    throw new SummerException(StatusCode.USER_LOGIN_PASSWORD_ERROR);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(User user) {
        userRepository.save(user);
    }
}
