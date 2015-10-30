package com.wonders.lxw.project.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.lxw.project.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * Created by lixuanwu on 15/10/30.
 */
@Component
public class InitData {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean(name = "cacheManager")
    public CacheManager cacheManager() {
        Jackson2JsonRedisSerializer<User> serializer = new Jackson2JsonRedisSerializer(User.class);
        serializer.setObjectMapper(objectMapper);
        redisTemplate.setValueSerializer(serializer);
        return new RedisCacheManager(redisTemplate);
    }
}
