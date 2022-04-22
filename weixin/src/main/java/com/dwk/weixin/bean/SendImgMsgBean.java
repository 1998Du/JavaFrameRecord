package com.dwk.weixin.bean;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 *
 * Description:
 *   客服主动向粉丝发送消息
 * @author: mushi
 * @Date: 2021/3/2 10:03
 */
@Component
public class SendImgMsgBean implements Serializable {

    /**粉丝openId*/
    private String touser;
    /**消息的媒体id*/
    private String mediaId;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
