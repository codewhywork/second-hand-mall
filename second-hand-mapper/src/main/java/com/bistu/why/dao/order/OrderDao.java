package com.bistu.why.dao.order;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bistu.why.model.order.OrderEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author why
 * @email bistu.com
 * @date 2023-05-27 23:40:27
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
