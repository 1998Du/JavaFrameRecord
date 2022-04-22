package com.dwk.weixin.bean;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 *
 * Description:
 *          回复音乐消息模板
 *          变量名在xml反射时要使用到，不能修改
 * @author: mushi
 * @Date: 2021/1/21 13:59
 */
@Component
public class ReplyMusicMsgBean implements Serializable {

    /**接收方账号，openId*/
    private String ToUserName;
    /**开发者微信号*/
    private String FromUserName;
    /**消息创建时间戳*/
    private Long CreateTime;
    /**消息类型*/
    private String MsgType;
    /**音乐标题*/
    private String Title;
    /**音乐描述*/
    private String Description;
    /**音乐链接*/
    private String MusicUrl;
    /**高质量音乐链接，wifi环境优先使用该链接播放音乐*/
    private String HQMusicUrl;
    /**缩略图的媒体id，通过素材管理中的接口上传多媒体文件得到的id*/
    private String ThumbMediaId;


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

    public String getMusicUrl() {
        return MusicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        MusicUrl = musicUrl;
    }

    public String getHQMusicUrl() {
        return HQMusicUrl;
    }

    public void setHQMusicUrl(String HQMusicUrl) {
        this.HQMusicUrl = HQMusicUrl;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }
}
