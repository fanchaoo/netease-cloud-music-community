package me.fanchaoo.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.fanchaoo.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Api(description = "test")
@RestController
public class TestController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "test1")
    @PostMapping(value = "/test1")
    public Object test1() {
        redisTemplate.opsForValue().set("key", UserContext.getLoginUser());
        Object obj = redisTemplate.opsForValue().get("key");

        redisTemplate.opsForValue().set("strkey", "strvalue123");
        Object str = redisTemplate.opsForValue().get("strkey");

        redisTemplate.opsForValue().set("listkey", Arrays.asList(UserContext.getLoginUser()));
        Object list = redisTemplate.opsForValue().get("listkey");

        return "success";
    }

}
