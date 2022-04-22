package com.dwk.weixin.controller;

import com.dwk.weixin.bean.*;
import com.dwk.weixin.result.ErrorType;
import com.dwk.weixin.result.Result;
import com.dwk.weixin.service.SendMsgHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 *
 * Description:
 *              发送消息给用户
 *              消息格式参考：https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Service_Center_messages.html#7
 * @author: mushi
 * @Date: 2021/1/21 15:58
 */
@RestController
public class SendMsgToUserController {

    @Autowired
    private SendMsgHandler sendMsgHandler;

    /**
     * 发送文本消息
     * {
     *     "touser":"o4qswwCoGxpD128WGupA3_e2R8cc",
     *     "content":"液位计1水位报警"
     * }
     */
    @PostMapping("/sendTextMsg")
    public Result sendTextMsg(@RequestBody SendTextMsgBean textMsgBean){

        String openId = textMsgBean.getTouser();
        String content = textMsgBean.getContent();
        try {
            sendMsgHandler.sendText(openId,content);
            return Result.success();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.fail(ErrorType.MSG_SEND_FAIL);
    }


    /**
     * 发送图片消息
     * {
     *     "touser":"o4qswwCoGxpD128WGupA3_e2R8cc",
     *     "mediaId":"mediaId"
     * }
     */
    @PostMapping("/sendImgMsg")
    public Result sendImgMsg(@RequestBody SendImgMsgBean imgMsgBean){
        String openId = imgMsgBean.getTouser();
        String mediaId = imgMsgBean.getMediaId();
        try {
            sendMsgHandler.sendImg(openId,mediaId);
            return Result.success();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.fail(ErrorType.MSG_SEND_FAIL);
    }


    /**
     * 发送语音消息
     *    {
     *     "touser":"osCnx5o11iOwTll3m2ye-z0SjV4g",
     *     "mediaId":"mediaId"
     *    }
     */
    @PostMapping("/sendVoiceMsg")
    public Result sendVoiceMsg(@RequestBody SendVoiceMsgBean voiceMsgBean){
        String openId = voiceMsgBean.getTouser();
        String mediaId = voiceMsgBean.getMediaId();
        try {
            sendMsgHandler.sendVoice(openId,mediaId);
            return Result.success();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.fail(ErrorType.MSG_SEND_FAIL);
    }


    /**
     * 发送视频消息
     *  {
     *     "touser":"osCnx5o11iOwTll3m2ye-z0SjV4g",
     *     "mediaId":"mediaId"
     *  }
     */
    @PostMapping("/sendVideoMsg")
    public Result sendVideoMsg(@RequestBody SendVideoMsgBean videoMsgBean){
        String openId = videoMsgBean.getTouser();
        String mediaId = videoMsgBean.getMediaId();
        try {
            sendMsgHandler.sendVideo(openId,mediaId);
            return Result.success();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.fail(ErrorType.MSG_SEND_FAIL);
    }


    /**
     * 发送音乐消息，请求中要包含音乐的mediaId
     *  {
     *     "touser":"osCnx5o11iOwTll3m2ye-z0SjV4g",
     *     "title":"歌名"
     *     "description":"歌曲描述"
     *     "musicUrl":"歌曲链接"
     *     "hqMusicUrl":"高品质歌曲链接"
     *     "thumbMediaId":"缩略图id"
     *  }
     */
    @PostMapping("/sendMusicMsg")
    public Result sendMusicMsg(@RequestBody SendMusicMsgBean musicMsgBean){
        String openId = musicMsgBean.getTouser();
        String title = musicMsgBean.getTitle();
        String description = musicMsgBean.getDescription();
        String musicUrl = musicMsgBean.getMusicUrl();
        String hqMusicUrl = musicMsgBean.getHqMusicUrl();
        String thumbMediaId = musicMsgBean.getThumbMediaId();
        try {
            sendMsgHandler.sendMusic(openId,title,description,musicUrl,hqMusicUrl,thumbMediaId);
            return Result.success();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.fail(ErrorType.MSG_SEND_FAIL);
    }

    /**
     * 发送图文消息
     * 外链方式：
     * {
     *     "touser":"osCnx5o11iOwTll3m2ye-z0SjV4g",
     *     "msgType":"news"
     *     "title":"标题"
     *     "description"图文描述"
     *     "url":"图文链接"
     *     "picUrl":"缩略图链接"
     * }
     * 消息页面方式：
     * {
     *     "touser":"osCnx5o11iOwTll3m2ye-z0SjV4g",
     *     "msgType":"mpnews"
     *     "mediaId":"mediaId"
     * }
     */
    @PostMapping("/sendNewsMsg")
    public Result sendNewsMsg(@RequestBody SendNewsMsgBean newsMsgBean){
        String typeNews = "news";
        String typeMpNews = "mpnews";
        String openId = newsMsgBean.getTouser();
        if (typeNews.equals(newsMsgBean.getMsgType())){
            //外链方式
            String title = newsMsgBean.getTitle();
            String description = newsMsgBean.getDescription();
            String url = newsMsgBean.getUrl();
            String picUrl = newsMsgBean.getPicUrl();

            try {
                sendMsgHandler.sendNews(openId,title,description,url,picUrl);
                return Result.success();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if (typeMpNews.equals(newsMsgBean.getMsgType())){
            //消息页面方式
            String mediaId = newsMsgBean.getMediaId();
            try {
                sendMsgHandler.sendNews(openId,mediaId);
                return Result.success();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Result.fail(ErrorType.MSG_SEND_FAIL);
    }


}
