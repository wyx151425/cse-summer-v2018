package com.cse.summer.context.interceptor;

import com.cse.summer.model.dto.Response;
import com.cse.summer.model.entity.User;
import com.cse.summer.util.Constant;
import com.cse.summer.util.StatusCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 请求拦截器
 *
 * @author 王振琦
 */
public class SummerInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(SummerInterceptor.class);

    private static final String LOGIN_PAGE_URI = "%s/login";
    private static final String USER_LOGIN_URI = "%s/api/users/login";
    private static final String MATERIAL_IMPORT = "%s/api/materialNames/import";
    private static final String MATERIAL_EXPORT = "%s/api/materialNames/export";
    private static final String MATERIA_NO_QUERY = "%s/api/materials/queryNoChinese";
    private static final String MATERIA_chineseN = "%s/api/materials/chineseName";
    private static final String MATERIA_2 = "%s/api/materials/exportNoChinese";
    private static final String MATERIA_3 = "%s/api/materials/importPerfectChinese";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("NemoInterceptor: preHandle");
        logger.info("User Address: " + request.getRemoteHost());
        logger.info("Request URL: " + request.getRequestURL().toString());
        logger.info("Request Method: " + request.getMethod());

        /* 1.请求相关数据 操作 */
        String contextPath = request.getContextPath();
        String uri = request.getRequestURI();

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");

        /* 2.非拦截路由 检查 */
        if (String.format(LOGIN_PAGE_URI, contextPath).equals(uri)
                || String.format(USER_LOGIN_URI, contextPath).equals(uri)
                || String.format(MATERIAL_IMPORT, contextPath).equals(uri)
                || String.format(MATERIAL_EXPORT, contextPath).equals(uri)
                || String.format(MATERIA_NO_QUERY, contextPath).equals(uri)
                || String.format(MATERIA_chineseN, contextPath).equals(uri)
                || String.format(MATERIA_2, contextPath).equals(uri)
                || String.format(MATERIA_3, contextPath).equals(uri)) {
            return true;
        }

        /* 3.Token 检查 */
        User user = (User) request.getSession().getAttribute(Constant.USER);
        if (null == user) {
            if (uri.contains("/api")) {
                ObjectMapper om = new ObjectMapper();
                PrintWriter out = response.getWriter();
                out.print(om.writeValueAsString(new Response<String>(StatusCode.USER_LOGIN_TIMEOUT)));
                out.flush();
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        logger.info("NemoInterceptor: postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        logger.info("NemoInterceptor: afterCompletion");
    }
}

