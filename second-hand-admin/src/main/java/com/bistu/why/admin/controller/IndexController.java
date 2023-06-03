package com.bistu.why.admin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class IndexController {
//    @RequestMapping("/index.html")
//    public String index(){
//        SecurityContext context = SecurityContextHolder.getContext();
//        return "index";
//    }
    @RequestMapping("/addUser")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @ResponseBody
    public String addUser(){
        SecurityContext context = SecurityContextHolder.getContext();
        return "add";
    }

    @GetMapping("/list")
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @ResponseBody
    public String list(){
        SecurityContext context = SecurityContextHolder.getContext();
        return "list";
    }
}
