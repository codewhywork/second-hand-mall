package com.bistu.why.service.order;

import com.baomidou.mybatisplus.extension.service.IService;

import com.bistu.why.common.renren.utils.PageUtils;
import com.bistu.why.model.order.OrderEntity;
import com.bistu.why.model.dto.SaveOrderDto;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 
 *
 * @author why
 * @email bistu.com
 * @date 2023-05-27 23:40:27
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(SaveOrderDto saveOrderDto) throws ExecutionException, InterruptedException;

}

