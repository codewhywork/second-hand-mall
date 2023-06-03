package com.bistu.why.user.controller;

import com.bistu.why.model.dto.RegUserDto;
import com.bistu.why.common.result.R;
import com.bistu.why.controller.BaseController;
import com.bistu.why.service.user.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

/**
 * @author why
 */

@RestController
@RequestMapping("/user")
@Api
public class RegisterUserController extends BaseController {

    @Autowired
    UserService userService;


    @PostMapping("/reg/user")
    public R regUser(@RequestBody @Valid RegUserDto regUserDto, BindingResult bindingResult) {
        extractedExceptionMethod(bindingResult);
        return userService.save(regUserDto);
    }

    //发送短信
    @GetMapping("/reg/sms")
    public R regSms(@RequestParam String phone) throws Exception {
        return userService.regSms(phone);
    }

}
