package com.bistu.why.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author why
 */
@ConfigurationProperties(prefix = "thread")
@Data
public class MyThreadPoolConfig {
    private int coreSize;
    private int maxSize;
    private int keepAliveTime;
}
