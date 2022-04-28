package com.dwk.redis.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁实现
 */
public class MicroLock {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**自动过期时间*/
    private static final Long KEY_EXPIRE = 3L;


    /**添加分布式锁*/
    public boolean addLock(Object key,Object value){
        this.notify();
        boolean returnBoolean;
        //添加锁，给锁设置过期时间 避免锁释放异常导致死锁，只有在key不存在时设置
        returnBoolean = redisTemplate.opsForValue().setIfAbsent(key, value, KEY_EXPIRE, TimeUnit.SECONDS);
        if (!returnBoolean){
            //加锁失败 - 等待锁过期重试
            Long expire = redisTemplate.opsForValue().getOperations().getExpire(key);
            try {
                this.wait(expire * 1000);
                addLock(key, value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return returnBoolean;
    }

    /**释放分布式锁*/
    public void unLock(Object key,Object value){
        //释放锁的时候判断锁对应的value是不是和当前要释放锁的value一致，避免因为过期删除了其他任务的锁
        Object o = redisTemplate.opsForValue().get(key);
        if (o != null && o.equals(value)){
            redisTemplate.opsForValue().getOperations().delete(key);
        }
    }
}
