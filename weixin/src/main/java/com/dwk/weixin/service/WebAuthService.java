package com.dwk.weixin.service;

import com.dwk.weixin.info.WebAuth;
import com.dwk.weixin.config.WeiXinConfig;
import com.dwk.weixin.util.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Description:
 *      网络授权服务类
 *      用于：用户点击菜单跳转官网的同时获取用户的openId
 *      官方文档参考：https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html
 * @author: mushi
 * @Date: 2021/1/18 16:17
 */
@Service
public class WebAuthService implements WebAuth {

    @Autowired
    private WeiXinConfig weiXinConfig;

    /**网页授权获取用户信息接口地址前缀*/
    private static final String URL_PREFIX ="https://api.weixin.qq.com/sns/oauth2/access_token?appid=";

    /**通过网页授权的code获取用户信息*/
    @Override
    public Object getOpenId(String code){
        //请求该url后可以获取用户的OpenId
        String url = URL_PREFIX + weiXinConfig.getAppId()+"&secret="+ weiXinConfig.getAppSecret()+"&code="+code+"&grant_type=authorization_code";
        return UrlUtil.requestUrl(url);
    }

}
