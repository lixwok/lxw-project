package com.wonders.lxw.project.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by lixuanwu on 15/10/23.
 */
@Configuration
public class RedisConfiguration {

    @Bean
    public StringRedisTemplate redisTemplate(RedisConnectionFactory factory) {
        System.out.println("factory = [" + factory + "]");
        System.out.println(factory.getConnection());
        return new StringRedisTemplate(factory);
    }


}
