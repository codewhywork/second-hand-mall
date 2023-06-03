package com.bistu.why.dao.cart;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bistu.why.model.cart.CartEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author why
 * @email bistu.com
 * @date 2023-05-29 16:47:48
 */
@Mapper
public interface CartDao extends BaseMapper<CartEntity> {
	
}
