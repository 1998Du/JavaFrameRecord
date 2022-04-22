package com.dwk.weixin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * Description:
 *              开发者相关配置信息
 * @author: mushi
 * @Date: 2021/1/19 9:24
 */
@Component
@ConfigurationProperties(prefix = "wx.config")
public class WeiXinConfig {

    /**开发者微信号*/
    private String wxNumber;

    /**开发者ID*/
    private String appId;

    /**开发者密码*/
    private String appSecret;

    /**用来做验证的token*/
    private String token;

    /**消息加密密钥*/
    private String encodingAesKey;

    /**用户关注时发送的消息*/
    private String welcome;

    public String getWelcome() {
        return welcome;
    }

    public void setWelcome(String welcome) {
        this.welcome = welcome;
    }

    public String getWxNumber() {
        return wxNumber;
    }

    public void setWxNumber(String wxNumber) {
        this.wxNumber = wxNumber;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEncodingAesKey() {
        return encodingAesKey;
    }

    public void setEncodingAesKey(String encodingAesKey) {
        this.encodingAesKey = encodingAesKey;
    }
}
