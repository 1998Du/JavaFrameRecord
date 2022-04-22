package com.dwk.redis.bean;

import org.springframework.stereotype.Component;

/**
 *
 * Description:
 *      验证码
 * @author: mushi
 * @Date: 2021/3/12 9:50
 */
@Component
public class CheckCodeBean {

    /**缓存在redis中验证码的key*/
    private String key;

    /**用户输入的验证码*/
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
