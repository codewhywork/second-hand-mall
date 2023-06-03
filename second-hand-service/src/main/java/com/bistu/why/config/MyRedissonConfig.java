package com.bistu.why.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author why
 */
@Configuration
public class MyRedissonConfig {
    @Bean
    public RedissonClient redissonClient() throws IOException {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.58.190:6379");
        return Redisson.create(config);
    }
}
