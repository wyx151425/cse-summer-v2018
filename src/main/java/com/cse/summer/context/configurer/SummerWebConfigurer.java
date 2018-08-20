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
                .addPathPatterns("/machine");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }
}
