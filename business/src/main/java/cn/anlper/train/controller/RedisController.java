package cn.anlper.train.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RedisController {

    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("/redis/set/{key}/{value}")
    public String set(@PathVariable String key, @PathVariable String value) {
        redisTemplate.opsForValue().set(key, value);
        log.info("key: {}, value:{}", key, value);
        return "success";
    }

    @RequestMapping("/redis/get/{key}")
    public Object get(@PathVariable String key) {
        Object o = redisTemplate.opsForValue().get(key);
        log.info("key: {}, value: {}", key, o);
        return o;
    }

}
