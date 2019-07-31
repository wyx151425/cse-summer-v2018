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
//
//    @Test
//    public void addUser() {
//        User user = new User();
//        user.setObjectId(Generator.getObjectId());
//        user.setStatus(1);
//        user.setRole(3);
//        LocalDateTime dateTime = LocalDateTime.now().withNano(0);
//        user.setCreateAt(dateTime);
//        user.setUpdateAt(dateTime);
//        user.setName("田文博");
//        user.setUsername("tianwenbo");
//        user.setPassword("123456");
//        user.setRoles("ROLE_CHIEF_DESIGNER");
//        userRepository.save(user);
//    }
}
