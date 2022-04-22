package com.dwk.weixin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dwk.weixin.config.WeiXinConfig;
import com.dwk.weixin.info.User;
import com.dwk.weixin.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Map;

/**
 * Description:
 *
 * @author: mushi
 * @Date: 2021/2/25 16:16
 */
@Service
@Slf4j
public class UserService implements User {

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private ReceiveMsgService receiveTextMsg;

    @Autowired
    private ReplyMsgService replyMsgService;

    @Autowired
    private WeiXinConfig weiXinConfig;

    private static final String URL_PREFIX = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=";

    private static final String URL_SUFFIX = "&lang=zh_CN";

    /**获取用户的详细信息*/
    @Override
    public JSON getUserInfo(String openId){
        String urlstr = URL_PREFIX +accessTokenService.getAccessTokenFromRedis()+"&openid="+openId+ URL_SUFFIX;
        String info = UrlUtil.requestUrl(urlstr).toString();
        JSON userInfoJson = JSONObject.parseObject(info);
        return userInfoJson;
    }

    /**用户关注*/
    @Override
    public String subscribe(String fans, String timestamp,String nonce){
        log.info(fans+"关注了公众号");
        Map userInfo = getUserInfo(fans).toJavaObject(Map.class);
        String reply = String.format(weiXinConfig.getWelcome(),userInfo.get("nickname"));
        return replyMsgService.replyTextMsg(timestamp, nonce, fans, reply);
    }

    /**用户取消关注，发送取关消息给客服，取关信息保存可做运营分析*/
    @Override
    public String unsubscribe(String fans){
        log.info(fans+"取关了公众号");
        return "";
    }


}
