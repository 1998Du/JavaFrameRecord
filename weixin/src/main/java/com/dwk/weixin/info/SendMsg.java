package com.dwk.weixin.info;

import com.dwk.weixin.result.Result;

import java.io.IOException;

public interface SendMsg {

    /**发送文本消息*/
    Result sendText(String openId, String content) throws IOException;

    /**发送图片消息*/
    Result sendImg(String openId, String mediaId) throws IOException;

    /**发送语音消息*/
    Result sendVoice(String openId, String mediaId) throws IOException;

    /**发送视频消息*/
    Result sendVideo(String openId, String mediaId) throws IOException;

    /**发送音乐消息*/
    Result sendMusic(String openId, String title, String description, String musicUrl, String hqMusicUrl, String thumbMediaId) throws IOException;

    /**发送图文消息（外链）*/
    Result sendNews(String openId, String title, String description, String url, String picUrl) throws IOException;

    /**发送图文消息（页面）*/
    Result sendNews(String openId, String mediaId) throws IOException;

}
