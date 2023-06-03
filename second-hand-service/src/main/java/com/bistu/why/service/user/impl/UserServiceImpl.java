package com.bistu.why.service.user.impl;


import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bistu.why.common.constant.*;
import com.bistu.why.common.utils.JwtUtil;
import com.bistu.why.common.utils.PasswordEncoder;
import com.bistu.why.contextHolder.UserThreadLocal;
import com.bistu.why.dao.user.UserDao;
import com.bistu.why.model.threadlocalVo.UserAuthInfo;
import com.bistu.why.model.user.UserEntity;
import com.bistu.why.model.dto.RegUserDto;
import com.bistu.why.common.result.R;
import com.bistu.why.common.result.RRException;
import com.bistu.why.service.user.UserService;
import com.bistu.why.common.utils.PageUtils;
import com.bistu.why.utils.SendSmsUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * @author why
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    SendSmsUtils sendSmsUtils;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return new PageUtils(null, 0, 0, 0);
    }

    //注册
    @Override
    public R save(RegUserDto regUserDto) {
        UserEntity userEntity = new UserEntity();
        String passwd = regUserDto.getPasswd();
        if (regUserDto.getPhone() != null) {
            regUserDto.setUname(regUserDto.getPhone());
            String code = (String) redisTemplate.opsForValue().get(RedisConstant.USER_REG_SMS_KET_PREFIX + regUserDto.getPhone());
            if (code != null && !code.equals(regUserDto.getCode())) return R.error();
        }
        BeanUtils.copyProperties(regUserDto, userEntity);
        userEntity.setPasswd(PasswordEncoder.encode(passwd));
        this.save(userEntity);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitBindingConstant.USER_REG_EXCHANGE, "", JSON.toJSONString(userEntity), correlationData);
        return R.ok("200");
    }

    @Override
    public R doLogin(RegUserDto userVo, HttpSession httpSession, HttpServletResponse httpServletResponse) {
        String code = null;
        UserEntity userEntity = null;
        boolean loginTypePhone = false;
        LambdaQueryWrapper<UserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //判断是否是手机登录
        if (userVo.getPhone() != null) {
            loginTypePhone = true;
            code = (String) redisTemplate.opsForValue().get(RedisConstant.USER_REG_SMS_KET_PREFIX + userVo.getPhone());
        } else {
            code = (String) httpSession.getAttribute(VerifyCodeConstant.LOGIN_VERIFY_CODE);
        }
        //验证码不正确
        if (code == null || !code.equals(userVo.getCode())) {
            return R.error().put("400", "验证码错误");
        }
        //是手机登录
        if (loginTypePhone) {
            redisTemplate.delete(RedisConstant.USER_REG_SMS_KET_PREFIX + userVo.getPhone());
            lambdaQueryWrapper.eq(UserEntity::getUname, userVo.getPhone());
        } else {
            httpSession.removeAttribute(VerifyCodeConstant.LOGIN_VERIFY_CODE);
            lambdaQueryWrapper.eq(UserEntity::getUname, userVo.getUname());
        }
        userEntity = this.baseMapper.selectOne(lambdaQueryWrapper);
        if (userEntity == null) {
            throw new RRException("不存在该用户");
        }
        if (!loginTypePhone) {
            //原密码
            String encrypted = userEntity.getPasswd();
            //解密后
            boolean passwordValid = PasswordEncoder.isPasswordValid(encrypted, userVo.getPasswd());
            if (!passwordValid) {
                return R.error();
            }
        }
        ThreadLocal<UserAuthInfo> userThreadLocal = UserThreadLocal.geteUserThreadLocal();
        UserAuthInfo userAuthInfo = new UserAuthInfo();
        userAuthInfo.setAuth(true);
        userAuthInfo.setUname(userVo.getUname());
        userAuthInfo.setUserId(userEntity.getUserId());
        userAuthInfo.setImg(userEntity.getImg());
        userThreadLocal.set(userAuthInfo);

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String jwt = JwtUtil.createJwt(uuid);
        redisTemplate.opsForValue().set(RedisConstant.AUTHENTICATION_USER_LOGIN_KEY_PREFIX + uuid, JSON.toJSONString(userAuthInfo), RedisConstant.REDIS_EXPIRATION_KET_PREFIX, TimeUnit.SECONDS);
        Cookie cookie = new Cookie(JwtConstant.JWT_HEADER, jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(CookieConstant.COOKIE_EXPIRATION);
        cookie.setDomain("secondhand.com");
        httpServletResponse.addCookie(cookie);
        return R.ok().put(JwtConstant.JWT_HEADER, jwt);
    }

    //发送短信
    @Override
    public R regSms(String phone) {
        String code = sendSmsUtils.sendSms(phone);
        redisTemplate.opsForValue().set(RedisConstant.USER_REG_SMS_KET_PREFIX + phone, code, RedisConstant.USER_REG_SMS_TTL_KET_PREFIX, TimeUnit.SECONDS);
        return R.ok();
    }

    @Override
    public int subAccount(String userId, BigDecimal bigDecimal) {
        return this.baseMapper.subAccount(userId, bigDecimal);
    }
}