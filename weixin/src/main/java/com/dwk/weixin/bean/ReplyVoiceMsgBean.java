package com.dwk.weixin.bean;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 *
 * Description:
 *          回复语音消息模板
 *          变量名在xml反射时要使用到，不能修改
 * @author: mushi
 * @Date: 2021/1/21 13:59
 */
@Component
public class ReplyVoiceMsgBean implements Serializable {

    /**接收方账号，openId*/
    private String ToUserName;
    /**开发者微信号*/
    private String FromUserName;
    /**消息创建时间戳*/
    private Long CreateTime;
    /**消息类型*/
    private String MsgType;
    /**通过素材管理中的接口上传多媒体文件得到的id*/
    private String MediaId;

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

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
