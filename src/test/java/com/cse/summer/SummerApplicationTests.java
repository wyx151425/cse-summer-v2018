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
//        user.setName("赵勇");
//        user.setUsername("zhaoyong");
//        user.setPassword("123456");
//        user.setRoles("ROLE_CHIEF_DESIGNER");
//        userRepository.save(user);
//
//        User user2 = new User();
//        user2.setObjectId(Generator.getObjectId());
//        user2.setStatus(1);
//        user2.setRole(3);
//        LocalDateTime dateTime2 = LocalDateTime.now().withNano(0);
//        user2.setCreateAt(dateTime2);
//        user2.setUpdateAt(dateTime2);
//        user2.setName("晏宏学");
//        user2.setUsername("yanhongxue");
//        user2.setPassword("123456");
//        user2.setRoles("ROLE_CHIEF_DESIGNER");
//        userRepository.save(user2);
//
//        User user3 = new User();
//        user3.setObjectId(Generator.getObjectId());
//        user3.setStatus(1);
//        user3.setRole(3);
//        LocalDateTime dateTime3 = LocalDateTime.now().withNano(0);
//        user3.setCreateAt(dateTime3);
//        user3.setUpdateAt(dateTime3);
//        user3.setName("洪维华");
//        user3.setUsername("hongweihua");
//        user3.setPassword("123456");
//        user3.setRoles("ROLE_CHIEF_DESIGNER");
//        userRepository.save(user3);
//
//        User user4 = new User();
//        user4.setObjectId(Generator.getObjectId());
//        user4.setStatus(1);
//        user4.setRole(3);
//        LocalDateTime dateTime4 = LocalDateTime.now().withNano(0);
//        user4.setCreateAt(dateTime4);
//        user4.setUpdateAt(dateTime4);
//        user4.setName("李业鹏");
//        user4.setUsername("liyepeng");
//        user4.setPassword("123456");
//        user4.setRoles("ROLE_CHIEF_DESIGNER");
//        userRepository.save(user4);
//
//        User user5 = new User();
//        user5.setObjectId(Generator.getObjectId());
//        user5.setStatus(1);
//        user5.setRole(3);
//        LocalDateTime dateTime5 = LocalDateTime.now().withNano(0);
//        user5.setCreateAt(dateTime5);
//        user5.setUpdateAt(dateTime5);
//        user5.setName("祖象欢");
//        user5.setUsername("zuxianghuan");
//        user5.setPassword("123456");
//        user5.setRoles("ROLE_CHIEF_DESIGNER");
//        userRepository.save(user5);
//    }

//    @Test
//    public void addPermission() {
//        Permission permission1 = Permission.newInstance();
//        permission1.setRole(Constant.Roles.ADMIN);
//        permission1.setCode(Constant.Permissions.EDIT_STRUCTURE_FEATURE);
//        permissionRepository.save(permission1);
//    }
}
