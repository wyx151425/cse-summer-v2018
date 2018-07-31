package com.cse.summer.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 控制器基类
 *
 * @author 王振琦  2018/07/06
 */
public class BaseFacade {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpSession session;

    protected HttpServletRequest getHttpServletRequest() {
        return request;
    }

    protected HttpServletResponse getHttpServletResponse() {
        return response;
    }

    protected HttpSession getHttpSession() {
        return session;
    }
}
