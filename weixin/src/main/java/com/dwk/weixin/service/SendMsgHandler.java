package com.dwk.weixin.service;

import com.alibaba.fastjson.JSONObject;
import com.dwk.weixin.info.SendMsg;
import com.dwk.weixin.result.Result;
import com.dwk.weixin.util.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;


/**
 *
 * Description:
 *          通过openId主动发消息给用户
 *          微信接口:https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN    ACCESS_TOKEN从缓存中取
 *          需要发送的参数（json格式）：{touser:openId、msgtype:text、text:{content:"要发送的消息"}}
 * @author: mushi
 * @Date: 2021/1/20 16:43
 */
@Service
public class SendMsgHandler implements SendMsg {

    private String urlPrefix = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

    @Autowired
    private AccessTokenService accessTokenService;

    /**主动发送文本消息给用户,对api的调用并非是对请求的返回所以不需加密
     * @param openId 用户和公众号唯一标识
     * @param content  文本内容
     * @throws IOException
     * */
    @Override
    public Result sendText(String openId, String content) throws IOException {
        String urlstr = urlPrefix+accessTokenService.getAccessTokenFromRedis();
        String data = "{\n" +
                "    \"touser\":\""+openId+"\",\n" +
                "    \"msgtype\":\"text\",\n" +
                "    \"text\":\n" +
                "    {\n" +
                "         \"content\":\""+content+"\"\n" +
                "    }\n" +
                "}";
        Object dataJson = JSONObject.parse(data);
        System.out.println(dataJson);
        return UrlUtil.requestUrlWithJson(dataJson,urlstr);
    }

    /**发送图片消息
     * @param openId 用户和公众号唯一标识
     * @param mediaId 图片在素材库的媒体id
     * @throws IOException
     * */
    @Override
    public Result sendImg(String openId, String mediaId) throws IOException {
        String urlstr = urlPrefix+accessTokenService.getAccessTokenFromRedis();
        String data = "{\n" +
                "    \"touser\":\""+openId+"\",\n" +
                "    \"msgtype\":\"image\",\n" +
                "    \"image\":\n" +
                "    {\n" +
                "      \"media_id\":\""+mediaId+"\"\n" +
                "    }\n" +
                "}";
        Object dataJson = JSONObject.parse(data);
        return UrlUtil.requestUrlWithJson(dataJson,urlstr);
    }

    /**发送语音消息
     * @param openId 用户和公众号唯一标识
     * @param mediaId 语音在素材库的媒体id
     * @throws IOException
     * */
    @Override
    public Result sendVoice(String openId, String mediaId) throws IOException {
        String urlstr = urlPrefix+accessTokenService.getAccessTokenFromRedis();
        String data = "{\n" +
                "    \"touser\":\""+openId+"\",\n" +
                "    \"msgtype\":\"voice\",\n" +
                "    \"voice\":\n" +
                "    {\n" +
                "      \"media_id\":\""+mediaId+"\"\n" +
                "    }\n" +
                "}";
        Object dataJson = JSONObject.parse(data);
        return UrlUtil.requestUrlWithJson(dataJson,urlstr);
    }

    /**发送视频消息
     * @param openId 用户和公众号唯一标识
     * @param mediaId 视频在素材库的媒体id
     * @throws IOException
     * */
    @Override
    public Result sendVideo(String openId, String mediaId) throws IOException{
        String urlstr = urlPrefix+accessTokenService.getAccessTokenFromRedis();
        String data = "{\n" +
                "    \"touser\":\""+openId+"\",\n" +
                "    \"msgtype\":\"video\",\n" +
                "    \"video\":\n" +
                "    {\n" +
                "      \"media_id\":\""+mediaId+"\",\n" +
                "      \"thumb_media_id\":\""+mediaId+"\",\n" +
                "      \"title\":\"TITLE\",\n" +
                "      \"description\":\"DESCRIPTION\"\n" +
                "    }\n" +
                "}";
        Object dataJson = JSONObject.parse(data);
        return UrlUtil.requestUrlWithJson(dataJson,urlstr);
    }

    /**发送音乐消息
     * @param openId 用户和公众号的唯一标识
     * @param title 音乐标题
     * @param description 音乐描述
     * @param musicUrl 音乐地址
     * @param hqMusicUrl 高品质音乐地址
     * @param thumbMediaId 缩略图在媒体库id
     * @throws IOException
     * */
    @Override
    public Result sendMusic(String openId, String title, String description, String musicUrl, String hqMusicUrl, String thumbMediaId) throws IOException{
        String urlstr = urlPrefix+accessTokenService.getAccessTokenFromRedis();
        String data = "{\n" +
                "    \"touser\":\""+openId+"\",\n" +
                "    \"msgtype\":\"music\",\n" +
                "    \"music\":\n" +
                "    {\n" +
                "      \"title\":\""+title+"\",\n" +
                "      \"description\":\""+description+"\",\n" +
                "      \"musicurl\":\""+musicUrl+"\",\n" +
                "      \"hqmusicurl\":\""+hqMusicUrl+"\",\n" +
                "      \"thumb_media_id\":\""+thumbMediaId+"\" \n" +
                "    }\n" +
                "}";
        Object dataJson = JSONObject.parse(data);
        return UrlUtil.requestUrlWithJson(dataJson,urlstr);
    }

    /**发送图文消息（外链）
     * @param openId 用户和公众号的唯一标识
     * @param title 图文消息标题
     * @param description 图文消息描述
     * @param url 图文消息的地址
     * @param picUrl 图文消息封面图片的地址
     * @throws IOException
     * */
    @Override
    public Result sendNews(String openId, String title, String description, String url, String picUrl) throws IOException {
        String urlstr = urlPrefix+accessTokenService.getAccessTokenFromRedis();
        String data = "{\n" +
                "    \"touser\":\""+openId+"\",\n" +
                "    \"msgtype\":\"news\",\n" +
                "    \"news\":{\n" +
                "        \"articles\": [\n" +
                "         {\n" +
                "             \"title\":\""+title+"\",\n" +
                "             \"description\":\""+description+"\",\n" +
                "             \"url\":\""+url+"\",\n" +
                "             \"picurl\":\""+picUrl+"\"\n" +
                "         }\n" +
                "         ]\n" +
                "    }\n" +
                "}";
        Object dataJson = JSONObject.parse(data);
        return UrlUtil.requestUrlWithJson(dataJson,urlstr);
    }

    /**发送图文消息（图文消息页面）
     * @param openId 用户和公众号的唯一标识
     * @param mediaId 图文消息在素材库的媒体id
     * @throws IOException
     * */
    @Override
    public Result sendNews(String openId, String mediaId) throws IOException {
        String urlstr = urlPrefix+accessTokenService.getAccessTokenFromRedis();
        String data = "{\n" +
                "    \"touser\":\""+openId+"\",\n" +
                "    \"msgtype\":\"mpnews\",\n" +
                "    \"mpnews\":\n" +
                "    {\n" +
                "         \"media_id\":\""+mediaId+"\"\n" +
                "    }\n" +
                "}";
        Object dataJson = JSONObject.parse(data);
        return UrlUtil.requestUrlWithJson(dataJson,urlstr);
    }


}
