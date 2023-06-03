package com.bistu.why.cart.interceptor;

import com.alibaba.fastjson2.JSON;
import com.bistu.why.common.constant.JwtConstant;
import com.bistu.why.common.constant.RedisConstant;
import com.bistu.why.common.utils.JwtUtil;
import com.bistu.why.contextHolder.UserThreadLocal;
import com.bistu.why.model.threadlocalVo.UserAuthInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author why
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwtToken = request.getHeader(JwtConstant.JWT_HEADER);
        if (jwtToken != null) {
            try {
                jwtToken = jwtToken.split("#")[1];
                Claims claims = JwtUtil.parserJwt(jwtToken);
                String uuid = (String) claims.get("data");
                String jsonObj = (String) redisTemplate.opsForValue().get(RedisConstant.AUTHENTICATION_USER_LOGIN_KEY_PREFIX + uuid);
                UserAuthInfo userAuthInfo = JSON.parseObject(jsonObj, UserAuthInfo.class);
                if (userAuthInfo != null) {
                    userAuthInfo.setAuth(true);
                    UserThreadLocal.geteUserThreadLocal().set(userAuthInfo);
                    return true;
                }
            } catch (JwtException jwtException) {
                response.getOutputStream().write("未登录".getBytes());
                response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
                response.setHeader("Location", "http://secondhand.com/api/user/login.html");
            }
        } else {
            ThreadLocal<UserAuthInfo> userThreadLocal = UserThreadLocal.geteUserThreadLocal();
            UserAuthInfo userAuthInfo = new UserAuthInfo();
            userAuthInfo.setAuth(false);
            userAuthInfo.setUname("临时用户");
            userThreadLocal.set(userAuthInfo);
            return true;
        }
        return false;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        ThreadLocal<UserAuthInfo> userVOThreadLocal = UserThreadLocal.geteUserThreadLocal();
        log.info("用户完成操作{}", userVOThreadLocal.get().getUserId());
        userVOThreadLocal.remove();
    }
}
