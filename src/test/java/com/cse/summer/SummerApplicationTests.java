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

import java.io.*;
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

    @Test
    public void addUser() {
        User user = new User();
        user.setObjectId(Generator.getObjectId());
        user.setStatus(1);
        user.setUsername("wangzhenqi");
        user.setName("王振琦");
        user.setPassword("151425");
        userRepository.save(user);
    }

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
//                    if (null != row.getCell(0)) {
//                        name.setEnglish(row.getCell(0).toString());
//                    } else {
//                        name.setEnglish("");
//                    }
//                    if (null != row.getCell(1)) {
//                        name.setChinese(row.getCell(1).toString());
//                    } else {
//                        name.setChinese("");
//                    }
//                    names.add(name);
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
//                if ("只读".equals(role)) {
//                    user.setRole(1);
//                } else if ("编辑".equals(role)) {
//                    user.setRole(2);
//                } else {
//                    user.setRole(3);
//                }
//                users.add(user);
//            }
//            userRepository.saveAll(users);
//        } catch (InvalidFormatException | IOException e) {
//            e.printStackTrace();
//        }
//    }
}
