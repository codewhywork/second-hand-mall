package com.bistu.why.admin.config;

import com.bistu.why.admin.entity.Role;
import com.bistu.why.admin.entity.User;
import com.bistu.why.admin.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author why
 */
@Component
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("不存在该用户");
        }
        List<Role> roles = userMapper.getRoles(user.getId());
        user.setRoles(roles);
        return user;
    }
}
