package com.sjy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author dogZ
 * @version 1.0
 * @data 2023/9/18 18:47
 *
 */
@RestController
public class SkillController {
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/skill/{id}")
    public String skill(@PathVariable("id") Integer id) {
        if ((Integer) redisTemplate.opsForValue().get("skill:" + id) > 0) {
            redisTemplate.opsForValue().decrement("skill:" + id);
            return "秒杀成功";
        }
        return "秒杀失败";
    }

    @GetMapping("skill2/{id}")
    public String skill2(@PathVariable("id") Integer id) {
        //1. 声明一个SessionCallback对象
        //SessionCallback: 会将多个命令放入到一个Redis的连接中，然后一次性发送给redis，减少网络开销
        SessionCallback<List> sessionCallback = new SessionCallback() {
            @Override
            public List execute(RedisOperations operations) throws DataAccessException {
                //1. watch
                operations.watch("skill:" + id);
                //2. 判断库存
                Integer stock = (Integer) operations.opsForValue().get("skill:" + id);
                if (stock > 0) {
                    //3. 开启事务
                    operations.multi();
                    //说明可以秒杀
                    //4. 扣减库存
                    operations.opsForValue().decrement("skill:" + id);
                    return operations.exec();
                }
                return null;
            }
        };
        //2. 执行execute方法
        Object result = redisTemplate.execute(sessionCallback);
        if (result != null) {
            return "秒杀成功";
        } else {
            return "秒杀失败";
        }
    }

    @GetMapping("/skill3/{id}")
    public String skill3(@PathVariable("id") Integer id) {
        RedisScript<Boolean> redisScript = RedisScript.of(new ClassPathResource("lua/skill.lua"),Boolean.class);
        boolean object = (boolean) redisTemplate.execute(redisScript, Arrays.asList("skill:" + id));
        if (object) {
            return "秒杀成功";
        } else {
            return "秒杀失败";
        }
    }

}
