package com.dwk.weixin.info;

import java.util.Map;

public interface ReceiveMsg {

    /**接收文本消息*/
    String receiveTextMsg(String timestamp, String nonce, String user, String text);

    /**接收图片消息*/
    String receiveImgMsg(String user, String mediaId);

    /**接收语音消息*/
    String receiveVoiceMsg(String user, String mediaId);

    /**接收视频消息*/
    String receiveVideoMsg(String user, String mediaId);

    /**接收短视频消息*/
    String receiveShortVideoMsg(String user, String mediaId);

    /**接收地理位置消息*/
    String receiveLocationMsg(String user, Map location);

    /**接收链接消息*/
    String receiveLinkMsg(String user, String mediaId);

}
