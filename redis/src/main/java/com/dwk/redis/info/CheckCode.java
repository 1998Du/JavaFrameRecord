package com.dwk.redis.info;

import com.dwk.redis.bean.CheckCodeBean;
import com.dwk.redis.result.Result;

public interface CheckCode {

    /**缓存验证码*/
    CheckCodeBean cacheCode(String openId, String value);

    /**校验验证码*/
    Result checkCheckCode(CheckCodeBean checkCodeBean);

}
