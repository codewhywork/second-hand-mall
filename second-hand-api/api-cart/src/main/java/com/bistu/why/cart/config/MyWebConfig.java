package com.bistu.why.cart.config;

import com.bistu.why.cart.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Autowired
    LoginInterceptor loginFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePathPatterns = new ArrayList<>();
//        excludePathPatterns.add("/cart/list/**");
//        excludePathPatterns.add("/cart/addCart/**");
        excludePathPatterns.add("/favicon.ico");

        excludePathPatterns.add("/webjars/**");
        excludePathPatterns.add("/v2/**");
        excludePathPatterns.add("/swagger-ui.html/**");
        excludePathPatterns.add("/doc.html/**");
        excludePathPatterns.add("/swagger-resources/**");
        registry.addInterceptor(loginFilter).addPathPatterns("/**").excludePathPatterns(excludePathPatterns);

    }
}
