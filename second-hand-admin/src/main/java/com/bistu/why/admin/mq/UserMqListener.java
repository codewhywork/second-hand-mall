package com.bistu.why.admin.mq;

import com.alibaba.fastjson2.JSON;
import com.bistu.why.common.constant.RabbitBindingConstant;
import com.bistu.why.model.user.UserEntity;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author why
 */
@Component
@Slf4j
@RabbitListener(bindings = {
        @QueueBinding(value = @Queue(value = RabbitBindingConstant.USER_REG_QUEUE, durable = "true"),
                exchange = @Exchange(value = RabbitBindingConstant.USER_REG_EXCHANGE, type = ExchangeTypes.FANOUT))
})
/*用来验证用户注册*/
public class UserMqListener {

    @RabbitHandler
    public void regUser(String message, CorrelationData correlationData, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        UserEntity userEntity = null;
        try {
            userEntity = JSON.parseObject(message, UserEntity.class);
            //验证用户信息是否违法
            log.info("用户注册：{}", userEntity);
            channel.basicAck(deliveryTag, true);
        } catch (IOException e) {
            //报错则不重新发送消息 记录下来 用于人工验证
            channel.basicNack(deliveryTag, false, false);
        }
    }
}
