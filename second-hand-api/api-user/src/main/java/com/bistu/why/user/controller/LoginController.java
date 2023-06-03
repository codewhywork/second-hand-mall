package com.bistu.why.user.controller;

import com.bistu.why.common.constant.VerifyCodeConstant;
import com.bistu.why.model.dto.RegUserDto;
import com.bistu.why.common.result.R;
import com.bistu.why.service.user.UserService;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author why
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    Producer producer;

    @Autowired
    UserService userService;

    @PostMapping("/doLogin")
    public R doLogin(RegUserDto userVo, HttpSession httpSession, HttpServletResponse httpServletResponse) {
        return userService.doLogin(userVo, httpSession, httpServletResponse);
    }

    @RequestMapping("/vc.jpg")
    public void verifyCode(HttpServletResponse response, HttpSession session) throws IOException {
        String verifyCode = producer.createText();
        session.setAttribute(VerifyCodeConstant.LOGIN_VERIFY_CODE, verifyCode);
        session.setMaxInactiveInterval(60);
        BufferedImage bufferedImage = producer.createImage(verifyCode);
        response.setContentType("image/png");
        ServletOutputStream os = response.getOutputStream();
        ImageIO.write(bufferedImage, "jpg", os);
    }
}
