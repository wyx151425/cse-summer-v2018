package com.cse.summer.service.impl;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.model.entity.User;
import com.cse.summer.repository.MachineRepository;
import com.cse.summer.repository.MaterialRepository;
import com.cse.summer.repository.StructureRepository;
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
    private final MachineRepository machineRepository;
    private final StructureRepository structureRepository;
    private final MaterialRepository materialRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MachineRepository machineRepository, StructureRepository structureRepository, MaterialRepository materialRepository) {
        this.userRepository = userRepository;
        this.machineRepository = machineRepository;
        this.structureRepository = structureRepository;
        this.materialRepository = materialRepository;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearData() {
        materialRepository.deleteAll();
        structureRepository.deleteAll();
        machineRepository.deleteAll();
    }
}
