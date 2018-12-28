package com.cse.summer.service.impl;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.model.entity.Permission;
import com.cse.summer.model.entity.User;
import com.cse.summer.repository.*;
import com.cse.summer.service.UserService;
import com.cse.summer.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王振琦
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MachineRepository machineRepository;
    private final StructureRepository structureRepository;
    private final MaterialRepository materialRepository;
    private final PermissionRepository permissionRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MachineRepository machineRepository, StructureRepository structureRepository, MaterialRepository materialRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.machineRepository = machineRepository;
        this.structureRepository = structureRepository;
        this.materialRepository = materialRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public User login(User user) {
        User targetUser = userRepository.findUserByUsername(user.getUsername());
        if (null == targetUser) {
            throw new SummerException(StatusCode.USER_UNREGISTER);
        } else {
            if (0 == targetUser.getStatus()) {
                throw new SummerException(StatusCode.USER_DISABLE);
            } else {
                if (targetUser.getPassword().equals(user.getPassword())) {
                    String role = targetUser.getRoles();
                    List<Permission> permissionList = permissionRepository.findAllByRole(role);
                    Map<String, Boolean> permissions = new HashMap<>(3);
                    for (Permission permission : permissionList) {
                        permissions.put(permission.getCode(), true);
                    }
                    targetUser.setPermissions(permissions);
                    return targetUser;
                } else {
                    throw new SummerException(StatusCode.USER_PASSWORD_ERROR);
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
