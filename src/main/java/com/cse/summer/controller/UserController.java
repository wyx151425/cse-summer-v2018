package com.cse.summer.controller;

import com.cse.summer.domain.Response;
import com.cse.summer.domain.User;
import com.cse.summer.service.UserService;
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
}
