package com.cse.summer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SummerApplicationTests {

    @Test
    public void contextLoads() {
    }

//    @Test
//    public void addUser() {
//        User user = new User();
//        user.setObjectId(Generator.getObjectId());
//        user.setStatus(1);
//        user.setRole(3);
//        LocalDateTime dateTime = LocalDateTime.now().withNano(0);
//        user.setCreateAt(dateTime);
//        user.setUpdateAt(dateTime);
//        user.setName("秦芬芬");
//        user.setUsername("qinfenfen");
//        user.setPassword("123456");
//        user.setRoles("ROLE_PROJECT_MANAGER");
//        userRepository.save(user);
//    }

//    @Test
//    public void addPermission() {
//        Permission permission = Permission.newInstance();
//        permission.setRole(Constant.Roles.ADMIN);
//        permission.setCode(Constant.Permissions.EDIT_STRUCTURE_FEATURE);
//        permissionRepository.save(permission);
//    }
}
