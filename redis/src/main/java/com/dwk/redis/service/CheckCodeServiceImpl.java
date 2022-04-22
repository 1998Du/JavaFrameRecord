package com.dwk.redis.service;

import com.dwk.redis.info.CheckCode;
import com.dwk.redis.bean.CheckCodeBean;
import com.dwk.redis.result.ErrorType;
import com.dwk.redis.result.Result;
import com.dwk.redis.util.CheckCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 *
 * Description:
 *
 * @author: mushi
 * @Date: 2021/3/12 10:02
 */
@Service
public class CheckCodeServiceImpl implements CheckCode {

    private String KEY;

    @Autowired
    private RedisTemplate redisTemplate;

    /**生成验证码*/
    public String creatCheckCode(int num){
        return new CheckCodeUtil().createCheckCode(num);
    }

    /**添加验证码*/
    @Override
    public CheckCodeBean cacheCode(String openId ,String checkCode) {
        KEY = String.valueOf(UUID.nameUUIDFromBytes(openId.getBytes()));
        CheckCodeBean checkCodeBean = new CheckCodeBean();
        redisTemplate.opsForValue().set(KEY,checkCode,60,TimeUnit.SECONDS);
        checkCodeBean.setKey(KEY);
        checkCodeBean.setValue(checkCode);
        System.out.println("存入rdis的key:"+KEY);
        return checkCodeBean;
    }

    /**校验验证码*/
    @Override
    public Result checkCheckCode(CheckCodeBean checkCodeBean){

        if (redisTemplate.keys("*").contains(checkCodeBean.getKey())){
            String code = (String) redisTemplate.opsForValue().get(checkCodeBean.getKey());

            if (code.equals(checkCodeBean.getValue())){
                return Result.success("验证码正确");
            }else{
                return Result.fail(ErrorType.CHECKCODE_NOT_RIGHT);
            }
        }else{
            return Result.fail(ErrorType.CHECKCODE_HAD_EXPIRE);
        }



    }



}
