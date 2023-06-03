package com.bistu.why.order.config;

import com.bistu.why.common.constant.RabbitBindingConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
/**
 * @author why
 */
@Configuration
@Slf4j
public class MyRabbitMq {

    @Autowired
    public RabbitTemplate rabbitTemplate;

    @Autowired
    MessageConverter messageConverter;

    @Bean(name = RabbitBindingConstant.ORDER_TTL_QUEUE)
    public Queue orderTtlQueue() {
        return QueueBuilder.durable(RabbitBindingConstant.ORDER_TTL_QUEUE).deadLetterExchange(RabbitBindingConstant.ORDER_TTL_EXCHANGE).deadLetterRoutingKey(RabbitBindingConstant.ORDER_DEAD_ROUTING_KEY).ttl(RabbitBindingConstant.ORDER_EXPIRES_TIME).build();
    }

    @Bean(name = RabbitBindingConstant.ORDER_DEAD_QUEUE)
    public Queue orderDeadQueue() {
        return QueueBuilder.durable(RabbitBindingConstant.ORDER_DEAD_QUEUE).build();
    }


    @Bean(name = RabbitBindingConstant.ORDER_TTL_EXCHANGE)
    public Exchange orderTtlExchange() {
        return new ExchangeBuilder(RabbitBindingConstant.ORDER_TTL_EXCHANGE, ExchangeTypes.DIRECT).build();
    }

    @Bean(name = "order-ttl-binding")
    public Binding orderTtlBinding() {
        return new Binding(RabbitBindingConstant.ORDER_TTL_QUEUE, Binding.DestinationType.QUEUE, RabbitBindingConstant.ORDER_TTL_EXCHANGE, RabbitBindingConstant.ORDER_TTL_ROUTING_KEY, null);
    }

    @Bean(name = "order-dead-binding")
    public Binding orderDeadBinding() {
        return new Binding(RabbitBindingConstant.ORDER_DEAD_QUEUE, Binding.DestinationType.QUEUE, RabbitBindingConstant.ORDER_TTL_EXCHANGE, RabbitBindingConstant.ORDER_DEAD_ROUTING_KEY, null);
    }

    @PostConstruct
    public void confirmCallBack() {
        rabbitTemplate.setReturnsCallback(returned -> {
            log.info("发生消息：{}", returned.getMessage());
            log.info("回应码：{}", returned.getReplyCode());
            log.info("回应信息：{}", returned.getReplyText());
            log.info("交换机：{}", returned.getExchange());
            log.info("路由键：{}", returned.getRoutingKey());
        });
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setConfirmCallback((correlationData, ack, msg) -> {
            if (correlationData != null) {
                String id = correlationData.getId();
                log.info("当前消息id：{}", id);
            }
            if (ack) {
                log.info("mq的成功回调success:{}", msg);
            } else {
                log.error("mq的失败回调error{}", msg);
            }
        });
    }
}


