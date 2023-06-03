package com.bistu.why.admin.jwtAuthConfigurer;

import com.bistu.why.admin.entity.Role;
import com.bistu.why.admin.entity.User;
import com.bistu.why.admin.mapper.UserMapper;
import com.bistu.why.common.utils.SpringAppContextBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AdminAuthenticationProvider implements AuthenticationProvider {

    //    @Autowired
//    PlainPasswordEncoder passwordEncoder;


    private static UserMapper userMapper;

    public AdminAuthenticationProvider() {
        userMapper = SpringAppContextBeanUtils.getBean(UserMapper.class);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String uname = (String) authentication.getPrincipal();
        String passwd = (String) authentication.getCredentials();
        log.info("uname:{},passwd:{}", uname, passwd);
        User user = userMapper.selectByUserName(uname);
        if (user == null) {
            return SecurityContextHolder.createEmptyContext().getAuthentication();
        }
        List<Role> roles = userMapper.getRoles(user.getId());
        user.setRoles(roles);
        return new AdminAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AdminAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
