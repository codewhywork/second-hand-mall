package com.bistu.why.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bistu.why.admin.entity.Role;
import com.bistu.why.admin.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author why
 * @since 2023-05-09
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
    public List<Role> getRoles(@Param("id") Integer id);

    User selectByUserName(@Param("username") String s);
}
