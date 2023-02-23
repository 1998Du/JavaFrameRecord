package com.dwk.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.dwk.info.WebSocketInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class WebSocketService implements WebSocketInfo {

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    /**点对点消息*/
    @Override
    public JSON pointToPoint(String message, String topic) {
        log.info("客户端请求message接口发来消息"+message);
        Map<String,String> response = new HashMap<>();
        response.put("message", "服务端收到：" + message);
        JSON parse = JSONUtil.parse(response);
        String now = DateUtil.now();
        log.info(now + "响应主题：" + topic);
        return parse;
    }


    /**广播消息*/
    @Override
    @Scheduled(cron = "0/3 * * * * ?")
    public void responseAllByScheduled() {
//        log.info("广播消息.....");
//        Map<String,String> message = new HashMap<>();
//        message.put("message", "服务端定时发送的广播消息!");
//        JSON parse = JSONUtil.parse(message);
        //simpMessageSendingOperations.convertAndSend("/topic/greetings",parse);
    }

    @Override
    public void responseAll(String message,String topic) {
        log.info("连接成功.....");
        Map<String,String> msg = new HashMap<>();
        msg.put("message", message);
        JSON parse = JSONUtil.parse(msg);
        simpMessageSendingOperations.convertAndSend(topic,parse);
    }


}
