package com.wonders.lxw.project.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.lxw.project.entity.User;
import com.wonders.lxw.project.repository.UserRepository;
import org.jboss.logging.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lixuanwu on 15/10/30.
 */

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "testUser", method = RequestMethod.POST)
    public Object testUser(@RequestParam String username, @RequestParam String password) {

        String token = "ssss";

        User user = userRepository.findByToken(token);
        if (null == user) {
            user = new User(username, password, token);
            userRepository.save(user);
            String key = "lxw:user:token"+ user.getId();
            redisTemplate.opsForList().set(key.getBytes(),user.getId(),user);
        }
        return user;
    }

}
