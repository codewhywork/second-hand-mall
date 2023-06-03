package com.bistu.why.search.mq;

import com.alibaba.fastjson2.JSON;
import com.bistu.why.common.constant.RabbitBindingConstant;
import com.bistu.why.search.dao.ProductRepository;
import com.bistu.why.search.entity.Product;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author why
 */
@RabbitListener(bindings =
@QueueBinding(value = @Queue(value = RabbitBindingConstant.PRODUCT_SEARCH_QUEUE, durable = "true"), exchange =
@Exchange(value = RabbitBindingConstant.PRODUCT_SEARCH_EXCHANGE, type = ExchangeTypes.FANOUT))
)
@Slf4j
@Component
public class ProductMqListener {

    @Autowired
    ProductRepository productRepository;

    @RabbitHandler
    public void saveProduct(String message, CorrelationData correlationData, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        Product product = null;
        try {
            product = JSON.parseObject(message, Product.class);
            log.info("新添加的商品：{}", product);
            productRepository.save(product);
            channel.basicAck(deliveryTag, true);
        } catch (IOException e) {
            //报错则不重新发送消息 记录下来 用于人工验证
            channel.basicNack(deliveryTag, false, false);
        }
    }
}
