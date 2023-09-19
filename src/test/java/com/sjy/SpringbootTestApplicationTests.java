package com.sjy;

import com.sjy.pojos.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpringbootTestApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedis(){
        redisTemplate.opsForValue().set("string", "hello");
        System.out.println(redisTemplate.opsForValue().get("string"));
    }
    @Test
    public void testString(){
//        redisTemplate.opsForValue().set("name", "hello");
//        redisTemplate.opsForValue().setIfAbsent("name","world");
//        redisTemplate.opsForValue().setIfPresent("name","java");
//        System.out.println(redisTemplate.opsForValue().get("name"));
        redisTemplate.opsForValue().set("name", "hello");
        redisTemplate.opsForValue().append("name", "world");
        System.out.println(redisTemplate.opsForValue().get("name"));
//        System.out.println(redisTemplate.opsForValue().size("name"));
//        System.out.println(redisTemplate.opsForValue().getAndExpire("name", 10, TimeUnit.SECONDS));
        redisTemplate.opsForValue().set("String",124);
        redisTemplate.opsForValue().setIfAbsent("String", 123, 100,TimeUnit.SECONDS);
        redisTemplate.opsForValue().increment("String");
        System.out.println(redisTemplate.opsForValue().get("String"));
        redisTemplate.opsForValue().setIfPresent("String", "123");
//        redisTemplate.opsForValue().increment("String");
        System.out.println(redisTemplate.opsForValue().get("String"));
        stringRedisTemplate.opsForValue().set("key", "hello");
        stringRedisTemplate.opsForValue().append("key", "hello");
        System.out.println(stringRedisTemplate.opsForValue().get("key"));
        // 设置一个字符串键值对
        redisTemplate.opsForValue().set("myKey", "hello");

        // 使用append方法追加值
        redisTemplate.opsForValue().append("myKey", " world");

        // 获取最终的值
        String finalValue = (String) redisTemplate.opsForValue().get("myKey");
        System.out.println(finalValue); // 输出 "hello world"


    }
    @Test
    public void testList(){
        User user1 = new User("小红","78",20);
        User user2 = new User("小明","78",22);
        redisTemplate.opsForList().leftPush("list1", user1);
        redisTemplate.opsForList().leftPush("list1",user1,user2);
//        redisTemplate.opsForList().rightPop("list1",10);

        System.out.println(redisTemplate.opsForList().range("list1", 0, -1));

    }
    @Test
    public void testSet(){
        redisTemplate.opsForValue().set("skill:1", 100);
    }
    @Test
    public void test(){
        System.out.println(redisTemplate.opsForValue().get("skill:1"));

    }
}
