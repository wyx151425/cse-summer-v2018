package com.cse.summer.context.configurer;

import com.cse.summer.context.interceptor.SummerInterceptor;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class SummerWebConfigurer implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SummerInterceptor())
                .addPathPatterns("/api/**")
                .addPathPatterns("")
                .addPathPatterns("/")
                .addPathPatterns("/index")
                .addPathPatterns("/machine")
                .addPathPatterns("/machineDetail")
                .addPathPatterns("/importResult")
                .addPathPatterns("/accountList")
                .addPathPatterns("/manageAccount")
                .addPathPatterns("/manageName");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("").setViewName("index2");
        registry.addViewController("/").setViewName("index2");
        registry.addViewController("/index").setViewName("index2");
        registry.addViewController("/machine").setViewName("machine");
        registry.addViewController("/machineDetail").setViewName("machine-detail");
        registry.addViewController("/importResult").setViewName("import-result");
        registry.addViewController("/manageName").setViewName("manage-name");
        registry.addViewController("/manageAccount").setViewName("account-manage");
        registry.addViewController("/login").setViewName("login");
    }
}
