package com.dwk.weixin.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 *
 * Description:
 *              使用redisTemplate要更改默认的序列化器，使用stringredistemplate不用
 * @author: mushi
 * @Date: 2020/12/8 10:33
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {


    /**
     * 更改默认的序列化器,存入redis的数据都会被序列化器处理之后再存入
     * 使用Jackson2JsonRedisSerializer存入redis的value中没有对象类型和类路径
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        //Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        template.setDefaultSerializer(serializer);
        return template;
    }


    /**
     * 重写生成key方法
     */
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName()).append(".").append(method.getName()).append(Arrays.toString(objects));
                return sb.toString();
            }
        };
    }


    /**
     * 定义redis监听配置
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }


    /**
     * 监听redis中所有db的过期事件，针对失效事件进行处理
     */

}
