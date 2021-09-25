package com.cse.summer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 王振琦
 * createAt 2018/10/22
 * updateAt 2018/10/22
 */
@Controller
public class RouteController extends SummerController {

    @RequestMapping(value = "logout")
    public String routeLogin() {
        removeSessionUser();
        return "login";
    }
}
