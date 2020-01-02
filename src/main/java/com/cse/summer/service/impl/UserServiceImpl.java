package com.cse.summer.service.impl;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.model.entity.Material;
import com.cse.summer.model.entity.PageContext;
import com.cse.summer.model.entity.Permission;
import com.cse.summer.model.entity.User;
import com.cse.summer.repository.*;
import com.cse.summer.service.UserService;
import com.cse.summer.util.Constant;
import com.cse.summer.util.Generator;
import com.cse.summer.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public void saveAccount(User user, User currentUser) {
        if (!currentUser.getRoles().equals(Constant.Roles.ADMIN)) {
            throw new SummerException(StatusCode.USER_PERMISSION_DEFECT);
        }
        User target = userRepository.findUserByUsername(user.getUsername());
        if (null == target) {
            LocalDateTime dateTime = LocalDateTime.now().withNano(0);
            user.setObjectId(Generator.getObjectId());
            user.setStatus(Constant.Status.ENABLE);
            user.setCreateAt(dateTime);
            user.setUpdateAt(dateTime);
            user.setRole(1);
            userRepository.save(user);
        } else {
            throw new SummerException(StatusCode.USER_REGISTERED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAccountPassword(User user, User currentUser) {
        if (!currentUser.getRoles().equals(Constant.Roles.ADMIN)) {
            throw new SummerException(StatusCode.USER_PERMISSION_DEFECT);
        }
        User target = userRepository.findUserByUsername(user.getUsername());
        target.setPassword(user.getPassword());
        userRepository.save(target);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAccountRole(User user, User currentUser) {
        if (!currentUser.getRoles().equals(Constant.Roles.ADMIN)) {
            throw new SummerException(StatusCode.USER_PERMISSION_DEFECT);
        }
        User target = userRepository.findUserByUsername(user.getUsername());
        target.setRoles(user.getRoles());
        userRepository.save(target);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAccountStatus(User user, User currentUser) {
        if (!currentUser.getRoles().equals(Constant.Roles.ADMIN)) {
            throw new SummerException(StatusCode.USER_PERMISSION_DEFECT);
        }
        User target = userRepository.findUserByUsername(user.getUsername());
        target.setStatus(user.getStatus());
        userRepository.save(target);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearData() {
        materialRepository.deleteAll();
        structureRepository.deleteAll();
        machineRepository.deleteAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageContext<User> findAllAccounts(Integer pageNum, User user) {
        if (user.getPermissions().getOrDefault(Constant.Permissions.MANAGE_ACCOUNT, false)) {
            Page<User> materialPage = userRepository.findAll(new Specification<User>() {
                @Override
                public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    predicates.add(criteriaBuilder.notEqual(root.get("username"), "admin"));
                    return criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[0]))).getRestriction();
                }
            }, PageRequest.of(pageNum - 1, 20));

            PageContext<User> pageContext = new PageContext<>();
            pageContext.setPageNum(pageNum);
            pageContext.setPageSize(materialPage.getContent().size());
            pageContext.setTotal(materialPage.getTotalElements());
            pageContext.setPages(materialPage.getTotalPages());
            pageContext.setData(materialPage.getContent());
            return pageContext;
        } else {
            throw new SummerException(StatusCode.USER_PERMISSION_DEFECT);
        }
    }
}
