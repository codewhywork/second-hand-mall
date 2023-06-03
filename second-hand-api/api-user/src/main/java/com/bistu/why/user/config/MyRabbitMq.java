package com.bistu.why.user.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
            String id = correlationData.getId();
            if (ack) {
                log.info("当前消息id：{}，success{}", id, msg);
            } else {
                log.info("当前消息id：{}，error{}", id, msg);
            }
        });
    }
}
