package com.bistu.why.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bistu.why.model.user.UserEntity;
import com.bistu.why.model.dto.RegUserDto;
import com.bistu.why.common.result.R;
import com.bistu.why.common.utils.PageUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-25 16:52:10
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    R save(RegUserDto regUserDto);

    R doLogin(RegUserDto userVo, HttpSession httpSession, HttpServletResponse httpServletResponse);

    int subAccount(String userId, BigDecimal bigDecimal);

    R regSms(String phone);
}

