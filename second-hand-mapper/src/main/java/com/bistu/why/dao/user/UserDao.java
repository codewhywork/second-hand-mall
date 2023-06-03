package com.bistu.why.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bistu.why.model.user.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 
 * 
 * @author why
 * @email bistu.com
 * @date 2023-05-25 16:52:10
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    int subAccount(@Param("userId") String userId, @Param("bigDecimal") BigDecimal bigDecimal);
}
