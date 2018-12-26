package com.cse.summer.controller;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.model.dto.Response;
import com.cse.summer.model.entity.User;
import com.cse.summer.service.UserService;
import com.cse.summer.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 王振琦
 */
@RestController
@RequestMapping(value = "api")
public class UserController extends BaseFacade {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "users/login")
    public Response<User> actionUserLogin(@RequestBody User user) {
        User targetUser = userService.login(user);
        addSessionUser(targetUser);
        return new Response<>();
    }

    @PostMapping(value = "users/logout")
    public Response<User> actionUserLogout() {
        removeSessionUser();
        return new Response<>();
    }

    @PutMapping(value = "users/password")
    public Response<User> actionUpdatePassword(@RequestBody User reqUser) {
        User user = getSessionUser();
        user.setPassword(reqUser.getPassword());
        userService.updatePassword(user);
        return new Response<>();
    }

    @PostMapping(value = "data/clear")
    public Response<Object> actionClearData() {
        User user = getSessionUser();
        if (4 != user.getRole()) {
            throw new SummerException(StatusCode.SYSTEM_ERROR);
        } else {
            userService.clearData();
        }
        return new Response<>();
    }
}
