package com.cse.summer;

import com.cse.summer.repository.NameRepository;
import com.cse.summer.repository.PermissionRepository;
import com.cse.summer.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SummerApplicationTests {

    @Autowired
    private NameRepository nameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

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

//    @Test
//    public void addUsers() {
//        List<User> users = new ArrayList<>();
//        User user1 = new User();
//        user1.setObjectId(Generator.getObjectId());
//        user1.setStatus(1);
//        LocalDateTime dateTime = LocalDateTime.now();
//        user1.setCreateAt(dateTime);
//        user1.setUpdateAt(dateTime);
//        user1.setName("魏汉雄");
//        user1.setUsername("weihanxiong");
//        user1.setPassword("123456");
//        user1.setRole(1);
//        user1.setRoles(Constant.Roles.STRUCTURE_MANAGER);
//        users.add(user1);
//        userRepository.saveAll(users);
//    }

//    @Test
//    public void updatePermission() {
//        List<User> userList = userRepository.findAll();
//        for (User user : userList) {
//            if (1 == user.getRole()) {
//                user.setRoles(Constant.Roles.STRUCTURE_MANAGER);
//            } else if (2 == user.getRole()) {
//                user.setRoles(Constant.Roles.PROJECT_MANAGER);
//            } else if (3 == user.getRole()) {
//                user.setRoles(Constant.Roles.CHIEF_DESIGNER);
//            } else if (4 == user.getRole()) {
//                user.setRoles(Constant.Roles.ADMIN);
//            }
//        }
//        userRepository.saveAll(userList);
//    }

//    @Test
//    public void addPermission() {
//        List<Permission> permissions = new ArrayList<>();
//
//        Permission permission1 = Permission.newInstance();
//        permission1.setRole(Constant.Roles.STRUCTURE_MANAGER);
//        permission1.setCode(Constant.Permissions.IMPORT_MACHINE_BOM);
//        permissions.add(permission1);
//        Permission permission2 = Permission.newInstance();
//        permission2.setRole(Constant.Roles.STRUCTURE_MANAGER);
//        permission2.setCode(Constant.Permissions.EXPORT_MACHINE_BOM);
//        permissions.add(permission2);
//        Permission permission3 = Permission.newInstance();
//        permission3.setRole(Constant.Roles.STRUCTURE_MANAGER);
//        permission3.setCode(Constant.Permissions.EXPORT_STRUCTURE);
//        permissions.add(permission3);
//
//        Permission permission4 = Permission.newInstance();
//        permission4.setRole(Constant.Roles.PROJECT_MANAGER);
//        permission4.setCode(Constant.Permissions.IMPORT_MACHINE_BOM);
//        permissions.add(permission4);
//        Permission permission5 = Permission.newInstance();
//        permission5.setRole(Constant.Roles.PROJECT_MANAGER);
//        permission5.setCode(Constant.Permissions.EXPORT_MACHINE_BOM);
//        permissions.add(permission5);
//        Permission permission6 = Permission.newInstance();
//        permission6.setRole(Constant.Roles.PROJECT_MANAGER);
//        permission6.setCode(Constant.Permissions.EXPORT_STRUCTURE);
//        permissions.add(permission6);
//        Permission permission7 = Permission.newInstance();
//        permission7.setRole(Constant.Roles.PROJECT_MANAGER);
//        permission7.setCode(Constant.Permissions.UPDATE_MACHINE_INFO);
//        permissions.add(permission7);
//        Permission permission8 = Permission.newInstance();
//        permission8.setRole(Constant.Roles.PROJECT_MANAGER);
//        permission8.setCode(Constant.Permissions.RELEASE_STRUCTURE);
//        permissions.add(permission8);
//        Permission permission9 = Permission.newInstance();
//        permission9.setRole(Constant.Roles.PROJECT_MANAGER);
//        permission9.setCode(Constant.Permissions.UPDATE_STRUCTURE_VERSION);
//        permissions.add(permission9);
//        Permission permission10 = Permission.newInstance();
//        permission10.setRole(Constant.Roles.PROJECT_MANAGER);
//        permission10.setCode(Constant.Permissions.APPEND_STRUCTURE);
//        permissions.add(permission10);
//
//        Permission permission11 = Permission.newInstance();
//        permission11.setRole(Constant.Roles.CHIEF_DESIGNER);
//        permission11.setCode(Constant.Permissions.IMPORT_NEW_MACHINE_BOM);
//        permissions.add(permission11);
//        Permission permission12 = Permission.newInstance();
//        permission12.setRole(Constant.Roles.CHIEF_DESIGNER);
//        permission12.setCode(Constant.Permissions.EXPORT_MACHINE_BOM);
//        permissions.add(permission12);
//        Permission permission13 = Permission.newInstance();
//        permission13.setRole(Constant.Roles.CHIEF_DESIGNER);
//        permission13.setCode(Constant.Permissions.IMPORT_STRUCTURE);
//        permissions.add(permission13);
//        Permission permission14 = Permission.newInstance();
//        permission14.setRole(Constant.Roles.CHIEF_DESIGNER);
//        permission14.setCode(Constant.Permissions.EXPORT_STRUCTURE);
//        permissions.add(permission14);
//        Permission permission15 = Permission.newInstance();
//        permission15.setRole(Constant.Roles.CHIEF_DESIGNER);
//        permission15.setCode(Constant.Permissions.DELETE_STRUCTURE);
//        permissions.add(permission15);
//
//        Permission permission16 = Permission.newInstance();
//        permission16.setRole(Constant.Roles.ADMIN);
//        permission16.setCode(Constant.Permissions.IMPORT_NEW_MACHINE_BOM);
//        permissions.add(permission16);
//        Permission permission17 = Permission.newInstance();
//        permission17.setRole(Constant.Roles.ADMIN);
//        permission17.setCode(Constant.Permissions.EXPORT_MACHINE_BOM);
//        permissions.add(permission17);
//        Permission permission18 = Permission.newInstance();
//        permission18.setRole(Constant.Roles.ADMIN);
//        permission18.setCode(Constant.Permissions.EXPORT_STRUCTURE);
//        permissions.add(permission18);
//        Permission permission19 = Permission.newInstance();
//        permission19.setRole(Constant.Roles.ADMIN);
//        permission19.setCode(Constant.Permissions.UPDATE_MACHINE_INFO);
//        permissions.add(permission19);
//        Permission permission20 = Permission.newInstance();
//        permission20.setRole(Constant.Roles.ADMIN);
//        permission20.setCode(Constant.Permissions.RELEASE_STRUCTURE);
//        permissions.add(permission20);
//        Permission permission21 = Permission.newInstance();
//        permission21.setRole(Constant.Roles.ADMIN);
//        permission21.setCode(Constant.Permissions.UPDATE_STRUCTURE_VERSION);
//        permissions.add(permission21);
//        Permission permission22 = Permission.newInstance();
//        permission22.setRole(Constant.Roles.ADMIN);
//        permission22.setCode(Constant.Permissions.APPEND_STRUCTURE);
//        permissions.add(permission22);
//        Permission permission23 = Permission.newInstance();
//        permission23.setRole(Constant.Roles.ADMIN);
//        permission23.setCode(Constant.Permissions.DELETE_STRUCTURE);
//        permissions.add(permission23);
//        Permission permission24 = Permission.newInstance();
//        permission24.setRole(Constant.Roles.ADMIN);
//        permission24.setCode(Constant.Permissions.IMPORT_STRUCTURE);
//        permissions.add(permission24);
//        Permission permission25 = Permission.newInstance();
//        permission25.setRole(Constant.Roles.ADMIN);
//        permission25.setCode(Constant.Permissions.DELETE_ALL_MACHINE);
//        permissions.add(permission25);
//        Permission permission26 = Permission.newInstance();
//        permission26.setRole(Constant.Roles.ADMIN);
//        permission26.setCode(Constant.Permissions.UPDATE_PERMISSION);
//        permissions.add(permission26);
//        permissionRepository.saveAll(permissions);
//    }
}
