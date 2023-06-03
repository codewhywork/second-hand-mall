package com.bistu.why.admin.handler;

import com.alibaba.fastjson2.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author why
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("code", HttpStatus.UNAUTHORIZED.value());
        stringStringHashMap.put("msg", "您还未登录");
        JSON.writeTo(response.getOutputStream(),stringStringHashMap);
        response.setContentType("application/json; charset=utf-8");
    }
}
