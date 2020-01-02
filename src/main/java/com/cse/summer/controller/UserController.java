package com.cse.summer.controller;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.model.dto.Response;
import com.cse.summer.model.entity.PageContext;
import com.cse.summer.model.entity.User;
import com.cse.summer.service.UserService;
import com.cse.summer.util.Constant;
import com.cse.summer.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

/**
 * @author 王振琦
 */
@RestController
@RequestMapping(value = "api")
public class UserController extends BaseFacade {

    private final UserService userService;
    private final Environment env;

    @Autowired
    public UserController(UserService userService, Environment env) {
        this.userService = userService;
        this.env = env;
    }

    @PostMapping(value = "users/login")
    public Response<User> actionUserLogin(@RequestBody User user) {
        User targetUser = userService.login(user);
        String dbUrl = env.getProperty("spring.datasource.url");
        if (dbUrl.contains(Constant.Database.Name.DMS_DEV)) {
            targetUser.setToken(Constant.Database.DEV);
        } else if (dbUrl.contains(Constant.Database.Name.DMS_UAT)) {
            targetUser.setToken(Constant.Database.UAT);
        } else if (dbUrl.contains(Constant.Database.Name.DMS_PRO)) {
            targetUser.setToken(Constant.Database.PRO);
        } else {
            targetUser.setToken(Constant.Database.DEV);
        }
        addSessionUser(targetUser);
        return new Response<>(targetUser);
    }

    @PostMapping(value = "users/logout")
    public Response<User> actionUserLogout() {
        removeSessionUser();
        return new Response<>();
    }

    @PostMapping(value = "data/clear")
    public Response<Object> actionClearData() {
        User user = getSessionUser();
        if (4 != user.getRole()) {
            throw new SummerException(StatusCode.USER_PERMISSION_DEFECT);
        } else {
            userService.clearData();
        }
        return new Response<>();
    }

    @GetMapping(value = "accounts/query")
    public Response<PageContext<User>> actionQueryAccounts(@RequestParam(value = "pageNum") Integer pageNum) {
        User user = getSessionUser();
        PageContext<User> pageContext = userService.findAllAccounts(pageNum, user);
        return new Response<>(pageContext);
    }

    @PostMapping(value = "accounts")
    public Response<User> actionSaveUserAccount(@RequestBody User reqUser) {
        User currentUser = getSessionUser();
        userService.saveAccount(reqUser, currentUser);
        return new Response<>();
    }

    @PutMapping(value = "accounts/password")
    public Response<User> actionUpdatePassword(@RequestBody User reqUser) {
        User currentUser = getSessionUser();
        userService.updateAccountPassword(reqUser, currentUser);
        return new Response<>();
    }

    @PutMapping(value = "accounts/role")
    public Response<User> actionUpdateAccountRole(@RequestBody User reqUser) {
        User currentUser = getSessionUser();
        userService.updateAccountRole(reqUser, currentUser);
        return new Response<>();
    }

    @PutMapping(value = "accounts/status")
    public Response<User> actionUpdateAccountStatus(@RequestBody User reqUser) {
        User currentUser = getSessionUser();
        userService.updateAccountStatus(reqUser, currentUser);
        return new Response<>();
    }
}
