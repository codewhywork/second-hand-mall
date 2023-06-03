package com.bistu.why.product.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author why
 */
@Component
@Slf4j
public class MyRabbitMq {

    @Autowired
    public RabbitTemplate rabbitTemplate;

    @Autowired
    MessageConverter messageConverter;


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
