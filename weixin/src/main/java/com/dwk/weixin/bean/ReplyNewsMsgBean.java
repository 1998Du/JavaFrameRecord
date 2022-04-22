package com.dwk.weixin.bean;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 *
 * Description:
 *          回复图文消息模板
 *          变量名在xml反射时要使用到，不能修改
 * @author: mushi
 * @Date: 2021/1/21 13:59
 */
@Component
public class ReplyNewsMsgBean implements Serializable {

    /**接收方账号，openId*/
    private String ToUserName;
    /**开发者微信号*/
    private String FromUserName;
    /**消息创建时间戳*/
    private Long CreateTime;
    /**消息类型*/
    private String MsgType;
    /**图文消息个数；当用户发送文本、图片、语音、视频、图文、地理位置这六种消息时，开发者只能回复1条图文消息；其余场景最多可回复8条图文消息*/
    private String ArticleCount;
    /**图文消息标题*/
    private String Title;
    /**图文消息描述*/
    private String Description;
    /**图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200*/
    private String PicUrl;
    /**点击图文消息跳转链接*/
    private String Url;


    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(String articleCount) {
        ArticleCount = articleCount;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
