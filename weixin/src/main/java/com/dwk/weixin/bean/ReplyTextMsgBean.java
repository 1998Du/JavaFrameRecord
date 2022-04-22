package com.dwk.weixin.bean;

import org.springframework.stereotype.Component;

import java.io.Serializable;


/**
 *
 * Description:
 *          回复文本消息模板
 *          变量名在xml反射时要使用到，不能修改
 * @author: mushi
 * @Date: 2021/1/21 10:22
 */
@Component
public class ReplyTextMsgBean implements Serializable {

    /**接收方账号，用户openID*/
    private String ToUserName;
    /**开发者微信号*/
    private String FromUserName;
    /**消息创建时间*/
    private Long CreateTime;
    /**消息类型*/
    private String MsgType;
    /**消息内容*/
    private String Content;


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

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
