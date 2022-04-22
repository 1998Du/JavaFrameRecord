package com.dwk.weixin.info;

public interface AccessToken {

    /**实时请求accessToken，变量接收存在栈中*/
    String getAccessToken();

    /**请求accessToken并存入缓存*/
    String cacheAccessToken();

    /**从缓存中获取accessToken*/
    String getAccessTokenFromRedis();

}
