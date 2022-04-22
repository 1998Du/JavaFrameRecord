package com.dwk.weixin.service;

import com.dwk.weixin.info.ReceiveMsg;
import com.dwk.weixin.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Map;


/**
 *
 * Description:
 *          接收用户消息，被动回复
 * @author: mushi
 * @Date: 2021/1/21 9:33
 */
@Service
@Slf4j
public class ReceiveMsgService implements ReceiveMsg {

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private ReplyMsgService replyMsgService;


    /**关键字自动回复,微信无法发送空消息*/
    @Override
    public String receiveTextMsg(String timestamp, String nonce, String user, String text){
        log.info("用户"+user+"发来消息："+text);
        String reply;
        if (!(text==null||("").equals(text))){
            switch (text){
                case "云gis":
                    reply = "<a href=\"http://183.129.204.238:19031/?openId="+user+"\">云Gis官网</a>";
                    return replyMsgService.replyTextMsg(timestamp,nonce,user,reply);
                case "你好":
                    return replyMsgService.replyTextMsg(timestamp,nonce,user,"认识你很高兴！");
                case "666":
                    return replyMsgService.replyTextMsg(timestamp,nonce,user,"你有我6？");
                case "hello":
                    return replyMsgService.replyTextMsg(timestamp,nonce,user,"newBee！");
                default:
            }
        }
        return "success";
    }

    /**接收并处理图片消息*/
    @Override
    public String receiveImgMsg(String user, String mediaId) {
        log.info("用户"+user+"发送的图片消息："+mediaId);
        //图片地址
        String urlPrefix = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=";
        String urlstr = urlPrefix+accessTokenService.getAccessTokenFromRedis()+"&media_id="+mediaId;
        //读取图片
        Object img = UrlUtil.requestUrl(urlstr);
        //保存图片到本地
        byte[] bytes = null;
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(img);
            bytes = bo.toByteArray();
            bo.close();
            oo.close();
            //存储图片
            FileOutputStream os = new FileOutputStream("E:\\VK\\CodesFromGit\\basic.component\\authcenter\\authcenter.client.weixin\\src\\main\\resources\\static\\img\\"+mediaId+".jpg");
            os.write(bytes,0,bytes.length);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("图片链接："+urlstr);

        return "success";
    }

    /**接收并处理语音消息*/
    @Override
    public String receiveVoiceMsg(String user, String mediaId){
        log.info("用户"+user+"发送的语音消息："+mediaId);
        return "success";
    }

    /**接收并处理视频消息*/
    @Override
    public String receiveVideoMsg(String user, String mediaId){
        log.info("用户"+user+"发送的视频消息："+mediaId);
        return "success";
    }

    /**接收并处理小视频消息*/
    @Override
    public String receiveShortVideoMsg(String user, String mediaId){
        log.info("用户"+user+"发送的小视频消息："+mediaId);
        return "success";
    }

    /**接收并处理位置消息*/
    @Override
    public String receiveLocationMsg(String user, Map location){
        log.info("用户"+user+"发送的位置消息："+location);
        return "success";
    }

    /**接收并处理链接消息*/
    @Override
    public String receiveLinkMsg(String user, String mediaId){
        log.info("用户"+user+"发送的链接消息："+mediaId);
        return "success";
    }


}
