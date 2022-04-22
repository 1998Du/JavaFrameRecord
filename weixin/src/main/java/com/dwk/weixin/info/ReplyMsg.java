package com.dwk.weixin.info;

/**
 *
 * Description:
 *
 * @author: mushi
 * @Date: 2021/3/8 10:55
 */
public interface ReplyMsg {

    /**回复文本消息*/
    String replyTextMsg(String timestamp,String nonce,String user, String reply);

    /**回复图片消息*/
    String replyImgMsg(String timestamp,String nonce,String user,String mediaId);

    /**回复语音消息*/
    String replyVoiceMsg(String timestamp,String nonce,String openId);

    /**回复视频消息*/
    String replyVideoMsg(String timestamp,String nonce,String openId);

    /**回复图文消息*/
    String replyNewsMsg(String timestamp,String nonce,String openId);

}
