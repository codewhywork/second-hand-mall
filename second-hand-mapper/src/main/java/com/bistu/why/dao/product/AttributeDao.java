package com.bistu.why.dao.product;

import com.bistu.why.model.product.AttributeEntity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品属性
 * 
 * @author why
 * @email bistu.com
 * @date 2023-05-26 22:30:47
 */
@Mapper
public interface AttributeDao extends BaseMapper<AttributeEntity> {
	
}
