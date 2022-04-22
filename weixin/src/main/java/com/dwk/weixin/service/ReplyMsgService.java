package com.dwk.weixin.service;

import com.dwk.weixin.info.ReplyMsg;
import com.dwk.weixin.bean.ReplyImgMsgBean;
import com.dwk.weixin.bean.ReplyTextMsgBean;
import com.dwk.weixin.config.WeiXinConfig;
import com.dwk.weixin.util.MsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Description:
 *  回复消息服务类
 * @author: mushi
 * @Date: 2021/3/8 10:44
 */
@Service
public class ReplyMsgService implements ReplyMsg {

    @Autowired
    private WeiXinConfig weiXinConfig;


    /**回复文本消息*/
    @Override
    public String replyTextMsg(String timestamp, String nonce, String openId, String content){
        ReplyTextMsgBean replyTextMsgBean = new ReplyTextMsgBean();
        replyTextMsgBean.setToUserName(openId);
        replyTextMsgBean.setFromUserName(weiXinConfig.getWxNumber());
        replyTextMsgBean.setCreateTime(System.currentTimeMillis());
        replyTextMsgBean.setMsgType("text");
        replyTextMsgBean.setContent(content);
        String replyMsg = MsgUtil.textMsgTurnXml(replyTextMsgBean);
        //调用msgUtil对replyMsg加密,测试号无法设置加密，使用的话无法回复消息给粉丝
        return replyMsg;
        //return msgUtil.encryption(timestamp,nonce,replyMsg,weiXinConfig);
    }

    /**回复图片消息*/
    @Override
    public String replyImgMsg(String timestamp,String nonce,String openId,String mediaId){
        ReplyImgMsgBean replyImgMsgBean = new ReplyImgMsgBean();

        return "success";
    }

    /**回复语音消息*/
    @Override
    public String replyVoiceMsg(String timestamp,String nonce,String openId){

        return "success";
    }

    /**回复视频消息*/
    @Override
    public String replyVideoMsg(String timestamp,String nonce,String openId){

        return "success";
    }

    /**回复图文消息*/
    @Override
    public String replyNewsMsg(String timestamp,String nonce,String openId){

        return "success";
    }

}
