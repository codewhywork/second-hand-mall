package com.bistu.why.product.mq;

import com.alibaba.fastjson2.JSON;
import com.bistu.why.common.constant.RabbitBindingConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author why
 */
@RabbitListener(bindings =
@QueueBinding(value = @Queue(value = RabbitBindingConstant.ITEM_STATIC_QUEUE, durable = "true"), exchange =
@Exchange(value = RabbitBindingConstant.ITEM_STATIC_EXCHANGE, type = ExchangeTypes.FANOUT))
)
@Slf4j
@Component
public class ProductMqListener {
    @Value("${file.upload}")
    public String path;
    @Autowired
    TemplateEngine templateEngine;

    @RabbitHandler
    public void createStaticHtml(String message, CorrelationData correlationData, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        Map<String, Object> hashMap = null;
        PrintWriter writer = null;
        try {
            Context context = new Context();
            hashMap = JSON.parseObject(message, HashMap.class);
            context.setVariable("data", hashMap);
            String fileName = hashMap.get("skuId") + ".html";
            channel.basicAck(deliveryTag, true);
            ApplicationHome applicationHome = new ApplicationHome(this.getClass());
            String dir = applicationHome.getDir().getParentFile()
                    .getParentFile().getAbsolutePath() + path;
            writer = new PrintWriter(new File(dir, fileName));
            templateEngine.process("item.html", context, writer);
        } catch (IOException e) {
            channel.basicNack(deliveryTag, false, false);
        } finally {
            writer.close();
        }
    }
}
