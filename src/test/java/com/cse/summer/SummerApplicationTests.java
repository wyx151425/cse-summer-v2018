package com.cse.summer;

import com.cse.summer.domain.Name;
import com.cse.summer.domain.User;
import com.cse.summer.repository.NameRepository;
import com.cse.summer.repository.UserRepository;
import com.cse.summer.util.Generator;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SummerApplicationTests {

    @Autowired
    private NameRepository nameRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void contextLoads() {
    }

//    @Test
//    public void addUser() {
//        User user = new User();
//        user.setObjectId(Generator.getObjectId());
//        user.setStatus(1);
//        user.setUsername("sumengmeng");
//        user.setName("苏猛猛");
//        user.setPassword("123456");
//        user.setRole(2);
//        userRepository.save(user);
//    }
//
//    @Test
//    public void addNameRelation() {
//        InputStream in = null;
//        try {
//            in = new FileInputStream(new File("name.xlsx"));
//            Workbook workbook = WorkbookFactory.create(in);
//            Sheet sheet = workbook.getSheet("Dictionary");
//            List<Name> names = new ArrayList<>();
//            int index = 0;
//            for (Row row : sheet) {
//                if (index < 1) {
//                    index++;
//                } else {
//                    Name name = new Name();
//                    name.setObjectId(Generator.getObjectId());
//                    if (null != row.getCell(0) && null != row.getCell(0).toString() && !"".equals(row.getCell(0).toString().trim())) {
//                        name.setEnglish(row.getCell(0).toString());
//
//                        if (null != row.getCell(1)) {
//                            name.setChinese(row.getCell(1).toString());
//                        } else {
//                            name.setChinese("");
//                        }
//                        names.add(name);
//                    }
//                }
//            }
//            nameRepository.saveAll(names);
//        } catch (InvalidFormatException | IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//
//            }
//        }
//    }
//
//    @Test
//    public void addUsers() {
//        InputStream in = null;
//        try {
//            in = new FileInputStream(new File("role.xlsx"));
//            Workbook workbook = WorkbookFactory.create(in);
//            Sheet sheet = workbook.getSheet("BOM管理系统DMS权限");
//            List<User> users = new ArrayList<>();
//            for (Row row : sheet) {
//                User user = new User();
//                user.setObjectId(Generator.getObjectId());
//                user.setStatus(1);
//                user.setName(row.getCell(1).toString());
//                user.setUsername(row.getCell(2).toString());
//                user.setPassword("123456");
//                String role = row.getCell(3).toString();
//                if ("用户1".equals(role)) {
//                    user.setRole(1);
//                } else if ("用户2".equals(role)) {
//                    user.setRole(2);
//                } else if ("用户3".equals(role)) {
//                    user.setRole(3);
//                }
//                users.add(user);
//            }
//            userRepository.saveAll(users);
//        } catch (InvalidFormatException | IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void addUsers() {
//        List<User> users = new ArrayList<>();
//        User user1 = new User();
//        user1.setObjectId(Generator.getObjectId());
//        user1.setStatus(1);
//        user1.setName("丁玮");
//        user1.setUsername("dingwei");
//        user1.setPassword("123456");
//        user1.setRole(1);
//        users.add(user1);
//        User user2 = new User();
//        user2.setObjectId(Generator.getObjectId());
//        user2.setStatus(1);
//        user2.setName("熊丽君");
//        user2.setUsername("xionglijun");
//        user2.setPassword("123456");
//        user2.setRole(1);
//        users.add(user2);
//        User user3 = new User();
//        user3.setObjectId(Generator.getObjectId());
//        user3.setStatus(1);
//        user3.setName("乔霈轶");
//        user3.setUsername("qiaopeiyi");
//        user3.setPassword("123456");
//        user3.setRole(1);
//        users.add(user3);
//        userRepository.saveAll(users);
//    }
}
