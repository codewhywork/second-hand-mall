package com.bistu.why.user.config;

import com.bistu.why.user.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Autowired
    LoginInterceptor loginFilter;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user/login.html").setViewName("login");
        registry.addViewController("/").setViewName("login");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePathPatterns = new ArrayList<>();
        excludePathPatterns.add("/user/login");
        excludePathPatterns.add("/user/reg");
        excludePathPatterns.add("/user/doLogin");
        excludePathPatterns.add("/user/vc.jpg");
        excludePathPatterns.add("/user/login.html");
        excludePathPatterns.add("/favicon.ico");
        excludePathPatterns.add("/error");
        //swagger
        excludePathPatterns.add("/swagger-resources/**");
        excludePathPatterns.add("/doc.html/**");
        excludePathPatterns.add("/webjars/**");
        excludePathPatterns.add("/v2/**");
        excludePathPatterns.add("/swagger-ui.html/**");
        registry.addInterceptor(loginFilter).addPathPatterns("/**").excludePathPatterns(excludePathPatterns);
    }
}
