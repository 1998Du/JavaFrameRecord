package com.dwk.weixin.controller;

import com.dwk.weixin.config.WeiXinConfig;
import com.dwk.weixin.service.UserService;
import com.dwk.weixin.service.ReceiveMsgService;
import com.dwk.weixin.service.WxService;
import com.dwk.weixin.util.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 *
 * Description:
 * #####################################微信消息入口#####################
 *        >在公众平台配置开发信息的时候必须先启动项目，并将服务器80端口穿透到开发环境<
 *          粉丝和公众号交互的消息都来自post请求，消息为xml格式
 *          接收到粉丝消息后要返回success或空字符，否则公众号显示服务异常
 * @author: mushi
 * @Date: 2021/1/15 9:13
 */
@RestController
@Slf4j
public class WxRequestController {

    /**用于判断是否是事件(关注/取关)消息*/
    private static final String EVENT = "event";

    /**用于获取xml消息中的MsgType*/
    private static final String MSG_TYPE = "MsgType";

    /**时间戳*/
    private String timestamp;

    /**微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数*/
    private String signature;

    /**随机数*/
    private String nonce;

    /**随机字符串*/
    private String echostr;

    /**消息加密类型*/
    private String encType;

    @Autowired
    private WxService wxService;

    /**
     * 接收微信的get请求
     * 成为开发者入口
     * @param request
     * @return
     */
    @GetMapping(value = "/weixin",produces = "text/plain;charset=utf-8")
    public String test(HttpServletRequest request) {

        this.signature = request.getParameter("signature");
        this.timestamp = request.getParameter("timestamp");
        this.nonce  = request.getParameter("nonce");
        this.echostr = request.getParameter("echostr");

        if(StringUtils.isAnyBlank(signature,timestamp,nonce,echostr)){
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        if(wxService.checkSignature(signature, timestamp, nonce)){
            log.info("验证签名成功");
            //微信服务器会接收这个echostr接收到则验证成功，如果不返回会认为验证失败
            return echostr;
        }else{
            log.info("验证签名失败");
            return "非法请求";
        }

    }


    /**
     * 接收微信的post请求
     * 接收事件、消息等
     * @param request
     * @return
     */
    @PostMapping(value = "/weixin",produces = "application/xml; charset=UTF-8")
    public String getMsg(HttpServletRequest request) {

        this.signature = request.getParameter("signature");
        this.timestamp = request.getParameter("timestamp");
        this.nonce  = request.getParameter("nonce");
        this.encType = request.getParameter("encrypt_type");

        //验证消息是否来自微信服务器
        if (!wxService.checkSignature(signature,timestamp,nonce)){
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求!");
        }

        //消息类型
        String msgType = null;
        //请求中携带的xml数据转成map
        Map<String, String> xmlMap = MsgUtil.xmlToMap(request);

        //*处理事件消息
        if (EVENT.equals(xmlMap.get(MSG_TYPE))){
            return wxService.handleEvent(xmlMap,timestamp,nonce);
        }

        String aes = "aes";
        //明文模式，可以直接获取消息类型等数据
        if (encType==null){
            msgType = xmlMap.get("MsgType");
        }
        //加密模式，先解密才可以获取数据
        else if (aes.equalsIgnoreCase(encType)){
            //接收者（开发者）
            String toUserName = xmlMap.get("ToUserName");
            //密文
            String encrypt = xmlMap.get("Encrypt");
            //密文数字签名
            String secretSignature = request.getParameter("msg_signature");
            xmlMap = wxService.handleSecretMsg(secretSignature,toUserName, encrypt,timestamp,nonce);
            msgType = xmlMap.get("MsgType");
        }

        /**各消息格式和参数参考：https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Receiving_standard_messages.html*/
        //*对不同消息类型进行处理
        return wxService.handleMsg(xmlMap,msgType,timestamp,nonce);

    }

}
