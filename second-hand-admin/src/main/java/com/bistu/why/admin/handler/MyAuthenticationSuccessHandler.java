package com.bistu.why.admin.handler;

import com.alibaba.fastjson2.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final static String ACCESS_TOKEN = "accessToken";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String jwt = (String) request.getAttribute(ACCESS_TOKEN);
        HashMap<String, Object> stringHashMap = new HashMap<>();
        stringHashMap.put("code", HttpStatus.OK.value());
        stringHashMap.put(ACCESS_TOKEN, jwt);
        authentication.isAuthenticated();
        response.getOutputStream().write(JSON.toJSONBytes(stringHashMap));
    }
}
