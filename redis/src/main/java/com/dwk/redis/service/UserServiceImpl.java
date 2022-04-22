package com.dwk.redis.service;

import com.dwk.redis.bean.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 *
 * Description:
 *  用户服务类
 * @author: mushi
 * @Date: 2021/3/12 16:29
 */
@Service
public class UserServiceImpl {

    @Autowired
    private RedisTemplate redisTemplate;

    /**根据用户名判断用户是否存在*/
    public boolean userIsExit(String userName){
        //先从缓存中查找
        UUID key = UUID.nameUUIDFromBytes(userName.getBytes());
        UserBean user = (UserBean) redisTemplate.opsForValue().get(key);
        if (user == null){
            //缓存中不存在，调用jpa服务到数据库中查找

        }
        return true;
    }


}
