package com.dwk.weixin.bean;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 *
 * Description:
 *     客服主动给粉丝发送文本消息
 * @author: mushi
 * @Date: 2021/3/2 9:51
 */
@Component
public class SendTextMsgBean implements Serializable {

    /**粉丝openId*/
    private String touser;

    /**文本消息内容*/
    private String content;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
