package com.bistu.why.service.order.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bistu.why.common.constant.RabbitBindingConstant;
import com.bistu.why.common.renren.utils.Query;


import com.bistu.why.contextHolder.UserThreadLocal;
import com.bistu.why.dao.order.OrderDao;
import com.bistu.why.model.order.OrderEntity;
import com.bistu.why.model.threadlocalVo.UserAuthInfo;
import com.bistu.why.model.dto.SaveOrderDto;
import com.bistu.why.service.order.OrderService;
import com.bistu.why.service.stock.StockService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bistu.why.common.renren.utils.PageUtils;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author why
 */
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    StockService stockService;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Query query = new Query(params);
        IPage<OrderEntity> page = this.page(
                new Page<>(query.getPage(), query.getLimit()),
                new QueryWrapper<>()
        );
        return new PageUtils(page.getRecords(), (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }

    @Override
    @Transactional
    public void save(SaveOrderDto saveOrderDto) throws ExecutionException, InterruptedException {
        UserAuthInfo userAuthInfo = UserThreadLocal.geteUserThreadLocal().get();
        String uname = userAuthInfo.getUname();
        String userId = userAuthInfo.getUserId();
        BigDecimal payAmount = saveOrderDto.getPayAmount();

        CompletableFuture<Long> saveOrderFuture = CompletableFuture.supplyAsync(() -> {
            OrderEntity orderEntity = new OrderEntity();
            BeanUtils.copyProperties(saveOrderDto, orderEntity);
            orderEntity.setSaleCount(saveOrderDto.getSaleCount());
            orderEntity.setOrderSn(UUID.randomUUID().toString().replace("-", ""));
            orderEntity.setUname(uname);
            orderEntity.setStatus(0);
            orderEntity.setPayAmount(payAmount);
            orderEntity.setUserId(userId);
            orderEntity.setCreateTime(new Date());
            this.save(orderEntity);
            return orderEntity.getOrderId();
        }, threadPoolExecutor);

        CompletableFuture<Void> subCountFuture = CompletableFuture.runAsync(() -> {
            //TODO 扣减库存
            stockService.subcount(saveOrderDto.getSkuId(), saveOrderDto.getSaleCount());
        }, threadPoolExecutor);
        //发送消息 检查订单状态
        Long orderId = saveOrderFuture.get();
        rabbitTemplate.convertAndSend(RabbitBindingConstant.ORDER_TTL_EXCHANGE, RabbitBindingConstant.ORDER_TTL_ROUTING_KEY, orderId.toString());
        CompletableFuture.allOf(subCountFuture, saveOrderFuture);
    }

}