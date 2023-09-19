package com.sjy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author dogZ
 * @version 1.0
 * @data 2023/9/17 19:04
 *
 */
@Configuration
public class AppRedisConfiguration {
    /**
     * 允许Object类型的key-value，都可以被转为json进行存储。
     * @param redisConnectionFactory 自动配置好了连接工厂
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //把对象转为json字符串的序列化工具
//        JdkSerializationRedisSerializer和StringRedisSerializer是最基础的策略，
//        在设计时仍然不推荐直接使用后面两种，即JacksonJsonRedisSerializer和OxmSerializer，
//        因为无论是json还是xml，他们本身仍然是String。
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
//        template.setDefaultSerializer(new StringRedisSerializer());
        return template;
//        StringRedisTemplate是Spring Data Redis提供的用于操作Redis字符串数据的专用模板，
//        它默认使用的是StringRedisSerializer进行序列化和反序列化。
    }
}
