package com.dwk.weixin.bean;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 *
 * Description:
 *   客服主动发送的图文消息（外链/消息页面）
 * @author: mushi
 * @Date: 2021/3/2 10:29
 */
@Component
public class SendNewsMsgBean implements Serializable {

    /**粉丝openId*/
    private String touser;
    /**图文消息类型*/
    private String msgType;
    /**媒体id*/
    private String mediaId;
    /**图文标题*/
    private String title;
    /**图文描述*/
    private String description;
    /**图文链接*/
    private String url;
    /**缩略图链接*/
    private String picUrl;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
