package com.bistu.why.order.mq;


import com.bistu.why.common.constant.RabbitBindingConstant;
import com.bistu.why.model.order.OrderEntity;
import com.bistu.why.model.stock.StockEntity;
import com.bistu.why.service.order.OrderService;
import com.bistu.why.service.stock.StockService;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author why
 */
@Slf4j
@RabbitListener(queues = {RabbitBindingConstant.ORDER_DEAD_QUEUE})
@Component
public class OrderMqListener {

    @Autowired
    OrderService orderService;
    @Autowired
    StockService stockService;

    @RabbitHandler
    public void checkOrder(String orderId, CorrelationData correlationData, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            log.info("orderId：{}", orderId);
            OrderEntity order = orderService.getById(orderId);
            if (order.getStatus() == 0) {
                //没付款 那就关闭订单
                order.setStatus(4);
                orderService.updateById(order);
                //下单数量 回滚库存
                Integer saleCount = order.getSaleCount();
                StockEntity stockEntity = new StockEntity();
                Long skuId = order.getSkuId();
                stockEntity.setSkuId(skuId);
                stockService.addcount(skuId, saleCount);
            }
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            //记录表
            channel.basicNack(deliveryTag, false, false);
        }
    }
}
