package com.dwk.weixin.bean;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 *
 * Description:
 *  客服主动向粉丝发送音乐消息
 * @author: mushi
 * @Date: 2021/3/2 10:15
 */
@Component
public class SendMusicMsgBean implements Serializable {

    /**粉丝openId*/
    private String touser;
    /**音乐标题,歌名*/
    private String title;
    /**描述*/
    private String description;
    /**音乐链接*/
    private String musicUrl;
    /**高品质音乐链接*/
    private String hqMusicUrl;
    /**封面图片id*/
    private String thumbMediaId;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
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

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String getHqMusicUrl() {
        return hqMusicUrl;
    }

    public void setHqMusicUrl(String hqMusicUrl) {
        this.hqMusicUrl = hqMusicUrl;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }
}
