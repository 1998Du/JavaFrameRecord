package com.dwk.weixin.service;

import com.dwk.weixin.config.WeiXinConfig;
import com.dwk.weixin.util.MsgUtil;
import com.dwk.weixin.util.Sha1Util;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

/**
 *
 * Description:
 *          验证微信请求
 * @author: mushi
 * @Date: 2021/1/22 9:28
 */
@Service
@Slf4j
public class WxService {

    @Autowired
    private WeiXinConfig weiXinConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private ReceiveMsgService userMsgHandler;

    /**检查signature*/
    public boolean checkSignature(String signature,String timestamp,String nonce){
        String token= weiXinConfig.getToken();
        String[] str = new String[]{token,timestamp,nonce};
        //排序
        Arrays.sort(str);
        //拼接字符串
        StringBuffer buffer = new StringBuffer();
        for(int i =0 ;i<str.length;i++){
            buffer.append(str[i]);
        }
        //进行sha1加密
        String temp = Sha1Util.encode(buffer.toString());
        //与微信提供的signature进行匹对
        return signature.equals(temp);
    }

    /**关注、取消关注事件*/
    public String handleEvent(Map<String,String> xmlMap,String timestamp,String nonce){
        String event = xmlMap.get("Event");
        String fans = xmlMap.get("FromUserName");
        String subscribe = "subscribe";
        String unsubscribe= "unsubscribe";
        if (subscribe.equals(event)){
            return userService.subscribe(fans,timestamp,nonce);
        }else if (unsubscribe.equals(event)){
            return userService.unsubscribe(fans);
        }
        return "";
    }

    /**
     * 处理加密消息
     * @param toUserName 开发者
     * @param encrypt 密文
     * @return result 解密后的消息体
     * */
    public Map<String,String> handleSecretMsg(String signature,String toUserName ,String encrypt,String timestamp,String nonce){
        Map<String,String> result;
        //密文
        String xmlPrefix = "<xml>";
        String xmlSuffix = "</xml>";
        String toUserNamePrefix = "<ToUserName><![CDATA[";
        String toUserNameSuffix = "]]></ToUserName>";
        String encryptPrefix = "<Encrypt><![CDATA[";
        String encryptSuffix = "]]></Encrypt>";
        //拼接密文
        String secretMsg = xmlPrefix + toUserNamePrefix + toUserName + toUserNameSuffix + encryptPrefix + encrypt + encryptSuffix + xmlSuffix;
        log.info("收到密文："+secretMsg+"\n====开始解密====");
        String decryption = MsgUtil.decryption(signature,timestamp,nonce,secretMsg, weiXinConfig);
        result = MsgUtil.xmlToMap(decryption);
        log.info("解密后的消息："+result);
        return result;
    }

    /**根据消息类型进行业务处理*/
    public String handleMsg(Map<String,String>xmlMap , String msgType, String timestamp, String nonce){
        //用户openId
        String user = xmlMap.get("FromUserName");
        //媒体文件id
        String mediaId;
        //可以用策略模式拆分
        switch (msgType){
            case "text":
                String text = xmlMap.get("Content");
                return userMsgHandler.receiveTextMsg(timestamp,nonce,user,text);
            case "image":
                mediaId = xmlMap.get("MediaId");
                return userMsgHandler.receiveImgMsg(user,mediaId);
            case "voice":
                log.info("语音消息");
                mediaId = xmlMap.get("MediaId");
                return userMsgHandler.receiveVoiceMsg(user,mediaId);
            case "video":
                log.info("视频消息");
                mediaId = xmlMap.get("MediaId");
                return userMsgHandler.receiveVideoMsg(user,mediaId);
            case "shortvideo":
                log.info("小视频消息");
                mediaId = xmlMap.get("MediaId");
                return userMsgHandler.receiveShortVideoMsg(user,mediaId);
            case "location":
                String locationX = xmlMap.get("Location_X");
                String locationY = xmlMap.get("Location_Y");
                String label = xmlMap.get("Label");
                log.info("经纬度：("+locationX+","+locationY+")\n"+"地理位置信息："+label);
                return userMsgHandler.receiveLocationMsg(user,xmlMap);
            case "link":
                log.info("链接消息");
                mediaId = xmlMap.get("MediaId");
                return userMsgHandler.receiveLinkMsg(user,mediaId);
            default: return "";
        }
    }

}
