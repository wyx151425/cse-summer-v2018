package com.cse.summer.controller;

import com.cse.summer.domain.Response;
import com.cse.summer.domain.User;
import com.cse.summer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        addHttpSessionUser(targetUser);
        return new Response<>();
    }
}
