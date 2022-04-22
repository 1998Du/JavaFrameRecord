package com.dwk.weixin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dwk.weixin.info.AccessToken;
import com.dwk.weixin.bean.AccessTokenBean;
import com.dwk.weixin.config.WeiXinConfig;
import com.dwk.weixin.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 *
 * Description:
 *      access_token是调用微信接口的全局唯一凭据，有效期两小时
 *      每次调用时都会判断是否过期，并判断是否要重新获取
 * @author: mushi
 * @Date: 2021/1/19 9:41
 */
@Service
@Slf4j
public class AccessTokenService implements AccessToken {

    private static final String urlPrefix = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=";
    public static final String ACCESS_TOKEN_KEY = "WeiXinGongZhongHao";

    @Autowired
    private WeiXinConfig weiXinConfig;

    @Autowired
    private RedisTemplate redisTemplate;

    private long nowTime;

    private long useTime;

    private String accessToken;


    /**
     * 获取access token，不缓存，每次获取前根据时间判断是否已经过期，过期的话重新获取
     */
    @Override
    public String getAccessToken() {

        nowTime = System.currentTimeMillis();

        if (useTime < nowTime) {
            //接口地址
            String urlStr = urlPrefix + weiXinConfig.getAppId() + "&secret=" + weiXinConfig.getAppSecret() + "";
            //请求地址后返回的结果,包含accessToken和过期时间
            Object result;
            //有效时间，秒
            long expiresIn;

            result = UrlUtil.requestUrl(urlStr);
            JSONObject jsonObject = JSONObject.parseObject(result.toString());
            boolean errCode = jsonObject.containsKey("errcode");
            if (errCode){
                throw new RuntimeException("获取accessToken失败，请重试！");
            }
            this.accessToken = jsonObject.get("access_token").toString();
            expiresIn = Integer.valueOf(jsonObject.get("expires_in").toString());
            useTime = nowTime + (expiresIn * 1000);

            log.info("新accessToken，有效时间"+expiresIn+"秒");
            return this.accessToken;

        }else{
            log.info("老accessToken，还有"+(useTime - nowTime)/1000+"秒过期");
            return this.accessToken;
        }

    }

    /**请求access token并缓存*/
    @Override
    public String cacheAccessToken(){
        //接口地址
        String urlStr = urlPrefix + weiXinConfig.getAppId() + "&secret=" + weiXinConfig.getAppSecret() + "";
        //有效时间，秒
        long expiresIn;

        log.info("请求accessToken...");
        AccessTokenBean accessTokenBean = JSON.parseObject((UrlUtil.requestUrl(urlStr)).toString(),AccessTokenBean.class);

        if (!accessTokenBean.success()){
            throw new RuntimeException("获取accessToken失败，请重试！");
        }

        //获取凭证和有效时间
        this.accessToken = accessTokenBean.getAccess_token();
        expiresIn = accessTokenBean.getExpires_in();

        redisTemplate.opsForValue().set(ACCESS_TOKEN_KEY,this.accessToken,expiresIn,TimeUnit.SECONDS);

        return this.accessToken;

    }

    /**从缓存中获取access token*/
    @Override
    public String getAccessTokenFromRedis(){
        boolean accessTokenExit = redisTemplate.keys("*").contains(ACCESS_TOKEN_KEY);
        if (accessTokenExit){
            log.info("从缓存中获取accessToken");
            return (String) redisTemplate.opsForValue().get(ACCESS_TOKEN_KEY);
        }else{
            return cacheAccessToken();
        }
    }


}
