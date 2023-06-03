package com.bistu.why.admin.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * @author why
 */
@Controller
@RequestMapping("/admin")
public class LoginController {
    @RequestMapping("/hello")
    @ResponseBody
    public HashMap hello() {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("code", "200");
        hashMap.put("msg", "hello");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        hashMap.put("auth", authentication);
        hashMap.put("role", authentication.getAuthorities());
        return hashMap;
    }
}
